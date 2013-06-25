package folderSubFolder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinderSubFolder {
    private final int lastCheck = 1;
    private List<String> name;
    private Map<String, List<String>> map = new HashMap<String, List<String>>();

    public List<String> getParentPath(File file) {
        File fileP = file.getParentFile();
        if (fileP != null) {
            getParentPath(fileP);
            name.add(fileP.toString());
        }
        return name;
    }

    public void createMapPaths(List<File> file) {
        for (int i = 0; i < file.size(); i++) {
            name = new ArrayList<String>();
            map.put(file.get(i).toString(), getParentPath(file.get(i)));
        }
    }

    public Map<String, String> checkParentPaths() {
        Map<String, String> FatherChiled = new HashMap<String, String>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (Map.Entry<String, List<String>> entryCheck : map.entrySet()) {
                if (entry.getValue().size() > entryCheck.getValue().size()) {
                    for (int i = entry.getValue().size() - 1; i >= lastCheck; i--) {
                        for (int j = entryCheck.getValue().size() - 1; j >= lastCheck; j--) {
                            if (entry.getValue().get(i).equals(entryCheck.getValue().get(j))) {
                                FatherChiled.put(entryCheck.getKey(), entry.getKey());
                            }
                        }
                    }
                }
            }
        }
        return FatherChiled;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String[] filename =
            {"img/1.png", "img/11.png", "img/12.png", "img/13.png", "img/14.png", "img/15.png", "img/16.png", "img/17.png", "img/18.png",
                "img/19.png", "img/2.png", "img/21.png", "img/22.png", "img/23.png", "img/24.png", "img/25.png", "img/26.png",
                "img/27.png", "img/28.png", "img/3.png", "img/4.png", "img/5.png", "img/6.png", "img/7.png", "img/8.png", "img/9.png",
                "img/blackness", "img/bugs", "img/Circle", "img/example.png", "img/rectangle.png", "img/rectangle_qr.png",
                "img/rectangle_qr_flip_h.jpg", "img/rectangle_qr_flip_v.jpg", "img/rectangle_qr_rot.png", "img/rectangle_qr_rot_180.jpg",
                "img/rectangle_qr_rot_r_15.jpg", "img/rectangle_qr_rot_r_30.jpg", "img/rectangle_qr_rot_r_45.jpg",
                "img/rectangle_qr_rot_r_50.jpg", "img/rectangle_qr_rot_r_60.jpg", "img/rectangle_qr_rot_r_90.jpg", "img/res",
                "img/resource", "img/scaned_files", "img/scaned_files_check", "img/scaned_files_check.zip.tt", "img/template.png",
                "img/scaned_files/check", "img/scaned_files/color_scan", "img/scaned_files/first_page_adobe.jpg",
                "img/scaned_files/first_page_adobe.png", "img/scaned_files/first_page_foxit.png", "img/scaned_files/first_page_gs.png",
                "img/scaned_files/first_page_gs_ed.png", "img/scaned_files/first_page_gs_ed_rt15.png", "img/scaned_files/gray_scan",
                "img/scaned_files_check/jpg", "img/scaned_files_check/new", "img/scaned_files_check/new_sorted",
                "img/scaned_files_check/new_sorted.zip", "img/scaned_files_check/pdf", "img/scaned_files_check/tiff",};

        List<File> file = new ArrayList<File>();
        for (int i = 0; i < filename.length; i++) {
            file.add(new File(filename[i]));
        }
        FinderSubFolder find = new FinderSubFolder();
        find.createMapPaths(file);
        find.checkParentPaths();

    }

}
