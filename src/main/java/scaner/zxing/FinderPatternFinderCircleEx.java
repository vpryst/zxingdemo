package scaner.zxing;

/*
 * Copyright 2007 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import scaner.zxing.FinderPatternEx;

/**
 * <p>
 * This class attempts to find finder patterns in a QR Code. Finder patterns are the square markers at three corners of a QR Code.
 * </p>
 * <p>
 * This class is thread-safe but not reentrant. Each thread must allocate its own object.
 * 
 * @author Sean Owen
 */
public class FinderPatternFinderCircleEx {

    private final int CENTER_QUORUM = 2;
    protected final int MIN_SKIP = 3; // 1 pixel/module times 3 modules/center
    protected final int MAX_MODULES = 57; // support up to version 10 for mobile clients
    private final int INTEGER_MATH_SHIFT = 8;

    private BinaryBitmap binaryBitmap;
    private BitMatrix image;
    private List<FinderPatternEx> possibleCenters = new ArrayList<FinderPatternEx>();
    private boolean hasSkipped;
    private int[] crossCheckStateCount = new int[3];
    private ResultPointCallback resultPointCallback;

    public FinderPatternFinderCircleEx(BufferedImage img) {
        binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
        try {
            image = binaryBitmap.getBlackMatrix();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
//    public List<FinderPatternEx> find() {
//        
//    }
    public List<FinderPatternEx> find(int x, int y, int w, int h) {
        int maxI = h;// image.getHeight();
        int maxJ = w;// image.getWidth();

        int iSkip = MIN_SKIP;

        boolean done = false;
        int[] stateCount = new int[3];
        for (int i = y + iSkip - 1; i < maxI && !done; i += iSkip) {
            // Get a row of black/white values
            stateCount[0] = 0;
            stateCount[1] = 0;
            stateCount[2] = 0;
            int currentState = 0;
            for (int j = x; j < maxJ; j++) {
                if (image.get(j, i)) {
                    // Black pixel
                    if ((currentState & 1) == 1) { // Counting white pixels
                        currentState++;
                    }
                    stateCount[currentState]++;
                } else { // White pixel
                    if ((currentState & 1) == 0) { // Counting black pixels
                        if (currentState == 2) { // A winner?
                            if (foundPatternCross(stateCount)) { // Yes
                                boolean confirmed = handlePossibleCenter(stateCount, i, j);
                                if (confirmed) {
                                    // Start examining every other line. Checking each line turned out to be too
                                    // expensive and didn't improve performance.
                                    iSkip = 2;
                                    if (hasSkipped) {
                                        done = haveMultiplyConfirmedCenters();
                                    } else {
                                        int rowSkip = findRowSkip();
                                        if (rowSkip > stateCount[2]) {
                                            // Skip rows between row of lower confirmed center
                                            // and top of presumed third confirmed center
                                            // but back up a bit to get a full chance of detecting
                                            // it, entire width of center of finder pattern

                                            // Skip by rowSkip, but back off by stateCount[2] (size of last center
                                            // of pattern we saw) to be conservative, and also back off by iSkip which
                                            // is about to be re-added
                                            i += rowSkip - stateCount[2] - iSkip;
                                            j = maxJ - 1;
                                        }
                                    }
                                } else {
                                    stateCount[0] = stateCount[2];
                                    stateCount[1] = 1;
                                    stateCount[2] = 0;
                                    currentState = 1;
                                    continue;
                                }
                                // Clear state to start looking again
                                currentState = 0;
                                stateCount[0] = 0;
                                stateCount[1] = 0;
                                stateCount[2] = 0;
                            } else { // No, shift counts back by two
                                stateCount[0] = stateCount[2];
                                stateCount[1] = 1;
                                stateCount[2] = 0;
                                currentState = 1;
                            }
                        } else {
                            stateCount[++currentState]++;
                        }
                    } else { // Counting white pixels
                        stateCount[currentState]++;
                    }
                }
            }
            if (foundPatternCross(stateCount)) {
                boolean confirmed = handlePossibleCenter(stateCount, i, maxJ);
                if (confirmed) {
                    iSkip = stateCount[0];
                    if (hasSkipped) {
                        // Found a third one
                        done = haveMultiplyConfirmedCenters();
                    }
                }
            }
        }
        return possibleCenters;
    }

    /**
     * Given a count of black/white/black/white/black pixels just seen and an end position, figures the location of the center of this run.
     */
    private float centerFromEnd(int[] stateCount, int end) {
        return (float) (end - stateCount[2]) - stateCount[1] / 2.0f;
    }

    /**
     * @param stateCount count of black/white/black/white/black pixels just read
     * @return true iff the proportions of the counts is close enough to the 1/1/3/1/1 ratios used by finder patterns to be considered a
     *         match
     */
    protected boolean foundPatternCross(int[] stateCount) {
        int totalModuleSize = 0;
        for (int i = 0; i < 3; i++) {
            int count = stateCount[i];
            if (count == 0) {
                return false;
            }
            totalModuleSize += count;
        }
        if (totalModuleSize < 7) { // //minimal size
            return false;
        }
        int moduleSize = (totalModuleSize << INTEGER_MATH_SHIFT) / 7;
        int maxVariance = moduleSize / 2;
        // Allow less than 50% variance from 1-1-3-1-1 proportions
        // return Math.abs(moduleSize - (stateCount[0] << INTEGER_MATH_SHIFT)) < maxVariance &&
        // Math.abs(moduleSize - (stateCount[1] << INTEGER_MATH_SHIFT)) < maxVariance &&
        // Math.abs(moduleSize - (stateCount[2] << INTEGER_MATH_SHIFT)) < maxVariance;
        return true;
    }

    private int[] getCrossCheckStateCount() {
        crossCheckStateCount[0] = 0;
        crossCheckStateCount[1] = 0;
        crossCheckStateCount[2] = 0;
        return crossCheckStateCount;
    }

    /**
     * <p>
     * After a horizontal scan finds a potential finder pattern, this method "cross-checks" by scanning down vertically through the center
     * of the possible finder pattern to see if the same proportion is detected.
     * </p>
     * 
     * @param startI row where a finder pattern was detected
     * @param centerJ center of the section that appears to cross a finder pattern
     * @param maxCount maximum reasonable number of modules that should be observed in any reading state, based on the results of the
     *            horizontal scan
     * @return vertical center of finder pattern, or {@link Float#NaN} if not found
     */
    private float crossCheckVertical(int startI, int centerJ, int maxCount, int originalStateCountTotal) {
        BitMatrix image = this.image;

        int maxI = image.getHeight();
        int[] stateCount = getCrossCheckStateCount();

        // Start counting up from center
        int i = startI;
        while (i >= 0 && !image.get(centerJ, i)) {
            stateCount[1]++;
            i--;
        }
        if (i < 0) {
            return Float.NaN;
        }
        while (i >= 0 && image.get(centerJ, i) && stateCount[0] <= maxCount) {
            stateCount[0]++;
            i--;
        }
        // If already too many modules in this state or ran off the edge:
        if (i < 0 || stateCount[0] > maxCount) {
            return Float.NaN;
        }
        // while (i >= 0 && image.get(centerJ, i) && stateCount[0] <= maxCount) {
        // stateCount[0]++;
        // i--;
        // }
        // if (stateCount[0] > maxCount) {
        // return Float.NaN;
        // }

        // Now also count down from center
        i = startI + 1;
        while (i < maxI && !image.get(centerJ, i)) {
            stateCount[1]++;
            i++;
        }
        if (i == maxI) {
            return Float.NaN;
        }
        while (i < maxI && image.get(centerJ, i) && stateCount[2] < maxCount) {
            stateCount[2]++;
            i++;
        }
        if (i == maxI || stateCount[2] >= maxCount) {
            return Float.NaN;
        }
        // while (i < maxI && image.get(centerJ, i) && stateCount[4] < maxCount) {
        // stateCount[4]++;
        // i++;
        // }
        // if (stateCount[4] >= maxCount) {
        // return Float.NaN;
        // }

        // If we found a finder-pattern-like section, but its size is more than 40% different than
        // the original, assume it's a false positive
        int stateCountTotal = stateCount[0] + stateCount[1] + stateCount[2];
        if (3 * Math.abs(stateCountTotal - originalStateCountTotal) >= 2 * originalStateCountTotal) {
            return Float.NaN;
        }

        return foundPatternCross(stateCount) ? centerFromEnd(stateCount, i) : Float.NaN;
    }

    /**
     * <p>
     * Like {@link #crossCheckVertical(int, int, int, int)}, and in fact is basically identical, except it reads horizontally instead of
     * vertically. This is used to cross-cross check a vertical cross check and locate the real center of the alignment pattern.
     * </p>
     */
    private float crossCheckHorizontal(int startJ, int centerI, int maxCount, int originalStateCountTotal) {
        BitMatrix image = this.image;

        int maxJ = image.getWidth();
        int[] stateCount = getCrossCheckStateCount();

        int j = startJ;
        while (j >= 0 && !image.get(j, centerI)) {
            stateCount[1]++;
            j--;
        }
        if (j < 0) {
            return Float.NaN;
        }
        while (j >= 0 && image.get(j, centerI) && stateCount[0] <= maxCount) {
            stateCount[0]++;
            j--;
        }
        if (j < 0 || stateCount[0] > maxCount) {
            return Float.NaN;
        }
        // while (j >= 0 && image.get(j, centerI) && stateCount[0] <= maxCount) {
        // stateCount[0]++;
        // j--;
        // }
        // if (stateCount[0] > maxCount) {
        // return Float.NaN;
        // }

        j = startJ + 1;
        while (j < maxJ && !image.get(j, centerI)) {
            stateCount[1]++;
            j++;
        }
        if (j == maxJ) {
            return Float.NaN;
        }
        while (j < maxJ && image.get(j, centerI) && stateCount[2] < maxCount) {
            stateCount[2]++;
            j++;
        }
        if (j == maxJ || stateCount[2] >= maxCount) {
            return Float.NaN;
        }
        // while (j < maxJ && image.get(j, centerI) && stateCount[4] < maxCount) {
        // stateCount[4]++;
        // j++;
        // }
        // if (stateCount[4] >= maxCount) {
        // return Float.NaN;
        // }

        // If we found a finder-pattern-like section, but its size is significantly different than
        // the original, assume it's a false positive
        int stateCountTotal = stateCount[0] + stateCount[1] + stateCount[2];
        if (3 * Math.abs(stateCountTotal - originalStateCountTotal) >= originalStateCountTotal) {
            return Float.NaN;
        }

        return foundPatternCross(stateCount) ? centerFromEnd(stateCount, j) : Float.NaN;
    }

    /**
     * <p>
     * This is called when a horizontal scan finds a possible alignment pattern. It will cross check with a vertical scan, and if
     * successful, will, ah, cross-cross-check with another horizontal scan. This is needed primarily to locate the real horizontal center
     * of the pattern in cases of extreme skew.
     * </p>
     * <p>
     * If that succeeds the finder pattern location is added to a list that tracks the number of times each location has been nearly-matched
     * as a finder pattern. Each additional find is more evidence that the location is in fact a finder pattern center
     * 
     * @param stateCount reading state module counts from horizontal scan
     * @param i row where finder pattern may be found
     * @param j end of possible finder pattern in row
     * @return true if a finder pattern candidate was found this time
     */
    protected final boolean handlePossibleCenter(int[] stateCount, int i, int j) {
        int stateCountTotal = stateCount[0] + stateCount[1] + stateCount[2];
        float centerJ = centerFromEnd(stateCount, j);
        float centerI = crossCheckVertical(i, (int) centerJ, stateCount[1], stateCountTotal);
        if (!Float.isNaN(centerI)) {
            // Re-cross check
            centerJ = crossCheckHorizontal((int) centerJ, (int) centerI, stateCount[1], stateCountTotal);
            if (!Float.isNaN(centerJ)) {
                float estimatedModuleSize = (float) stateCountTotal / 7.0f;
                boolean found = false;
                for (int index = 0; index < possibleCenters.size(); index++) {
                    FinderPatternEx center = possibleCenters.get(index);
                    // Look for about the same center and module size:
                    if (center.aboutEquals(estimatedModuleSize, centerI, centerJ)) {
                        possibleCenters.set(index, center.combineEstimate(centerI, centerJ, estimatedModuleSize));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    FinderPatternEx point = new FinderPatternEx(centerJ, centerI, estimatedModuleSize);
                    possibleCenters.add(point);
                    if (resultPointCallback != null) {
                        resultPointCallback.foundPossibleResultPoint(point);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @return number of rows we could safely skip during scanning, based on the first two finder patterns that have been located. In some
     *         cases their position will allow us to infer that the third pattern must lie below a certain point farther down in the image.
     */
    private int findRowSkip() {
        int max = possibleCenters.size();
        if (max <= 1) {
            return 0;
        }
        FinderPatternEx firstConfirmedCenter = null;
        for (FinderPatternEx center : possibleCenters) {
            if (center.getCount() >= CENTER_QUORUM) {
                if (firstConfirmedCenter == null) {
                    firstConfirmedCenter = center;
                } else {
                    // We have two confirmed centers
                    // How far down can we skip before resuming looking for the next
                    // pattern? In the worst case, only the difference between the
                    // difference in the x / y coordinates of the two centers.
                    // This is the case where you find top left last.
                    hasSkipped = true;
                    return (int) (Math.abs(firstConfirmedCenter.getX() - center.getX()) - Math.abs(firstConfirmedCenter.getY() -
                        center.getY())) / 2;
                }
            }
        }
        return 0;
    }

    /**
     * @return true iff we have found at least 3 finder patterns that have been detected at least {@link #CENTER_QUORUM} times each, and,
     *         the estimated module size of the candidates is "pretty similar"
     */
    private boolean haveMultiplyConfirmedCenters() {
        int confirmedCount = 0;
        float totalModuleSize = 0.0f;
        int max = possibleCenters.size();
        for (FinderPatternEx pattern : possibleCenters) {
            if (pattern.getCount() >= CENTER_QUORUM) {
                confirmedCount++;
                totalModuleSize += pattern.getEstimatedModuleSize();
            }
        }
        if (confirmedCount < 3) {
            return false;
        }
        // OK, we have at least 3 confirmed centers, but, it's possible that one is a "false positive"
        // and that we need to keep looking. We detect this by asking if the estimated module sizes
        // vary too much. We arbitrarily say that when the total deviation from average exceeds
        // 5% of the total module size estimates, it's too much.
        float average = totalModuleSize / (float) max;
        float totalDeviation = 0.0f;
        for (FinderPatternEx pattern : possibleCenters) {
            totalDeviation += Math.abs(pattern.getEstimatedModuleSize() - average);
        }
        return totalDeviation <= 0.05f * totalModuleSize;
    }

    public static void main(String[] args) throws IOException, NotFoundException {
        File file = new File("img/scaned_files/gray_scan/SKMBT_22313060714040_0039_oi.jpg");
        BufferedImage img = ImageIO.read(file);
        // BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img.getSubimage(0, 0, 2420,
        // 2200))));
        // FinderPatternFinderEx demo = new FinderPatternFinderEx(binaryBitmap.getBlackMatrix());
        FinderPatternFinderCircleEx finder = new FinderPatternFinderCircleEx(img);
        System.out.println(finder.find(700, 1124, 1245, 1429).toString());
    }
}
