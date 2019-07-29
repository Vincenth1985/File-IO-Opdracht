package intecbrussel.be;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.TreeSet;

public class SortingSoftware {

    public static void main(String[] args) {


        //Path from the unsorted folder
        String folder = "/Users/vincenthonca/Desktop/dossier/unsorted";
        Path unsortedFolder = Paths.get(folder);

        if (unsortedFolder.toFile().exists()) {

            for (String s : unsortedFolder.toFile().list((dir, name) -> !name.equals(".DS_Store"))) {
                creatingFileTypeFolder(unsortedFolder.toString(), getFileType(s));

                if (Paths.get(folder, s).toFile().isFile()) {
                    try {
                        Files.move(Paths.get(folder, s), Paths.get(folder, getFileType(s), s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException IOE) {
                        IOE.printStackTrace();
                    }
                }
            }

            unsortedFolder.toFile().renameTo(Paths.get(unsortedFolder.getParent().toString(), "Sorted").toFile());

        } else System.out.println("No folder with this name found. Please check the unsorted folder unsortedFolder\n");




        /* I write a method that will allow on the basis of a type of file,
           to search in all the folders and sub folders the corresponding file,
           and then move them to the folders that group all files of the same type.*/


        Path sortedFolder = Paths.get(unsortedFolder.getParent().toString(), "Sorted");
        String fileType = "pdf";
        moveFileTypeToTargetFolder(sortedFolder.toFile(), fileType, Paths.get(sortedFolder.toString(), fileType).toFile());



        /* I search all folders if there are any hidden files,
           move them to an appropriate folder */
        try {
            if (!Paths.get(sortedFolder.toString(), "HiddenFiles").toFile().exists()) {
                Files.createDirectory(Paths.get(sortedFolder.toString(), "HiddenFiles"));

            } else putAllHiddenFilesInHiddenFolder(sortedFolder.toFile(), sortedFolder.toFile());
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }

        /* Looking for empty folders and delete them */

        deleteEmptyFolders(sortedFolder.toFile());


        /* create here a treeset that will serve me as a summary of my files and file*/

        TreeSet<File> summary = new TreeSet<File>();
        summary = collectFolderToSet(sortedFolder.toFile(), summary);


        /* I create a method that will print the contents of the given folder in parameter*/

        summaryToConsole(summary);

        /* Methode to write summerize to text file */

        


    }


    private static String getFileType(String s) {

        String extension;
        return extension = s.substring(s.lastIndexOf(".") + 1);

    }

    private static void creatingFileTypeFolder(String parentFolder, String extension) {

        Path paths = Paths.get(parentFolder, extension);

        try {
            if (!paths.toFile().exists())
                Files.createDirectory(paths);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void moveFileTypeToTargetFolder(File sourceFolder, String fileType, File targetFolder) {

        try {
            for (File file : sourceFolder.listFiles()) {
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

    private static void putAllHiddenFilesInHiddenFolder(File sourceFolder, File sortedFolder) throws IOException {

        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                putAllHiddenFilesInHiddenFolder(file, sortedFolder);
            } else if (file.isHidden()) {
                Files.move(file.toPath().toAbsolutePath(), Paths.get(sortedFolder.toString(), "HiddenFiles", file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }


        }
    }

    private static void deleteEmptyFolders(File sourceFolder) {

        for (File e : sourceFolder.listFiles()) {

            if (e.isDirectory()) {
                deleteEmptyFolders(e);
                e.delete();
            }
        }

    }

    private static TreeSet<File> collectFolderToSet(File sourceFolder, TreeSet<File> folderToSummerize) {

        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                folderToSummerize.add(file);
                collectFolderToSet(file, folderToSummerize);
            } else
                folderToSummerize.add(file);
        }
        return folderToSummerize;
    }

    private static void summaryToConsole(TreeSet<File> summary) {

        for (File file : summary) {
            if (file.isDirectory()) {
                System.out.println("Directory : " + file.getAbsolutePath());
                System.out.println("-".repeat(file.getAbsolutePath().length() + 12));
            } else if (file.isFile()) {
                System.out.println("File : " + file.getName());
                System.out.println();
            }
        }
    }


}
