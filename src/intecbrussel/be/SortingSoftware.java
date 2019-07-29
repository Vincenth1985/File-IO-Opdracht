package intecbrussel.be;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SortingSoftware {

    public static void main(String[] args) {


        //Path from the unsorted folder
        String folder = "/Users/vincenthonca/Desktop/dossier/unsorted";
        Path path = Paths.get(folder);

        if (path.toFile().exists()) {

            for (String s : path.toFile().list((dir, name) -> !name.equals(".DS_Store"))) {
                creatingfileTypeFolder(path.toString(), getFileType(s));

                if (Paths.get(folder, s).toFile().isFile()) {
                    try {
                        Files.move(Paths.get(folder, s), Paths.get(folder, getFileType(s), s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException IOE) {
                        IOE.printStackTrace();
                    }
                }
            }

            path.toFile().renameTo(Paths.get(path.getParent().toString(), "Sorted").toFile());

        } else System.out.println("No folder with this name found. Please check the unsorted folder path");


        /*I write a method that will allow on the basis of a type of file,
         to search in all the folders and sub folders the corresponding file and then move them to the folders that group all files of the same type.
         */

        String fileType = "pdf";

        moveFileTypeToTargetFolder(Paths.get(path.getParent().toString(), "Sorted").toFile(), fileType, Paths.get(path.getParent().toString(), "Sorted", fileType).toFile());


    }


    private static String getFileType(String s) {

        String extension;
        return extension = s.substring(s.lastIndexOf(".") + 1);

    }

    private static void creatingfileTypeFolder(String parentFolder, String extension) {

        Path paths = Paths.get(parentFolder, extension);

        try {
            if (!paths.toFile().exists())
                Files.createDirectory(paths);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void moveFileTypeToTargetFolder(File source, String fileType, File targetFolder) {

        try {
            for (File file : source.listFiles()) {
                if (file.isDirectory()) {
                    moveFileTypeToTargetFolder(file, fileType, targetFolder);
                } else {
                    if (getFileType(file.getName()).matches(fileType)) {
                        Files.move(Paths.get(file.getAbsolutePath()), Paths.get(targetFolder.toString(), file.getName()), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }


}
