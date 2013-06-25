package fileSubFile;

import static org.junit.Assert.*;
import static folderSubFolder.FinderSubFolder.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import folderSubFolder.FinderSubFolder;

public class FileSubFileTest {
    public FinderSubFolder find = new FinderSubFolder();
    //@Test
    public void sameFileTest() {
        List<File> file = new ArrayList<File>();
        file.add(new File("img/1.png"));
        file.add(new File("img/1.png"));
        find.createMapPaths(file);
        find.checkParentPaths();
    }
    //@Test
    public void diffFileTest() {
        List<File> file = new ArrayList<File>();
        file.add(new File("img/1.png"));
        file.add(new File("img/rectangle_qr.png"));
        find.createMapPaths(file);
        find.checkParentPaths();
    }
    //@Test
    public void diffFolderFileTest() {
        List<File> file = new ArrayList<File>();
        file.add(new File("img/1.png"));
        file.add(new File("img/rectangle_qr.png"));
        file.add(new File("img/scaned_files_check/new_sorted/"));
        file.add(new File("img/scaned_files_check/new_sorted/Brett Boe/SKMBT_22313061714420.pdf"));
        find.createMapPaths(file);
        System.out.println(find.checkParentPaths());
        
    }
    //@Test
    public void diffGrandPaFolderFileTest() {
        List<File> file = new ArrayList<File>();
        file.add(new File("img/1.png"));
        file.add(new File("img/rectangle_qr.png"));
        file.add(new File("img/scaned_files_check/new_sorted/"));
        file.add(new File("img/scaned_files_check/new_sorted/Brett Boe/SKMBT_22313061714420.pdf"));
        find.createMapPaths(file);
        System.out.println(find.checkParentPaths());
        
    }
    @Test
    public void diffGrandGrandPaFolderFileTest() {
        List<File> file = new ArrayList<File>();
        file.add(new File("img/1.png"));
        file.add(new File("img/rectangle_qr.png"));
        file.add(new File("img/scaned_files_check/new_sorted/"));
        file.add(new File("img/scaned_files_check/new_sorted/Brett Boe/SKMBT_22313061714420.pdf"));
        file.add(new File("img/scaned_files_check/new/pdf/SKMBT_22313061714070.pdf"));
        file.add(new File("img/scaned_files_check/new/pdf/"));
        find.createMapPaths(file);
        Map<String, String> list = find.checkParentPaths();
        for(Map.Entry<String, String> entry : list.entrySet()) {
            System.out.println(entry.getKey() + "              " +entry.getValue());
        }
        
    }
}
