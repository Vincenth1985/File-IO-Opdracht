package intecbrussel.be.Main;

import intecbrussel.be.Services.OperationClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.TreeSet;

public class SortingSoftware {

    public static void main(String[] args) {

        //Path from the unsorted folder.Here we have to give the path from the Unsorted Folder.
        String folder = "/Users/vincenthonca/Desktop/dossier/unsorted";
        Path unsortedFolder = Paths.get(folder);
        OperationClass operations = new OperationClass();

        if (unsortedFolder.toFile().exists()) {

            for (String s : unsortedFolder.toFile().list((dir, name) -> !name.equals(".DS_Store"))) {
                operations.creatingFileTypeFolder(unsortedFolder.toString(), operations.getFileType(s));

                if (Paths.get(folder, s).toFile().isFile()) {
                    try {
                        Files.move(Paths.get(folder, s), Paths.get(folder, operations.getFileType(s), s), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException IOE) {
                        IOE.printStackTrace();
                    }
                }
            }

            unsortedFolder.toFile().renameTo(Paths.get(unsortedFolder.getParent().toString(), "Sorted").toFile());

        } else System.out.println("No folder with this name found. Please check the unsorted folder unsortedFolder\n");

        /* I write a method that will allow on the basis of a type of file,
           to search in all the folders and sub folders the corresponding files,
           and then move them to the folders that group all files of the same type.*/

        Path sortedFolder = Paths.get(unsortedFolder.getParent().toString(), "Sorted");
        String fileType = "pdf";
        operations.moveFileTypeToTargetFolder(sortedFolder.toFile(), fileType, Paths.get(sortedFolder.toString(), fileType).toFile());


        /* I search all folders if there are any hidden files,
           move them to an appropriate folder */

        operations.putAllHiddenFilesInHiddenFolder(sortedFolder.toFile(), sortedFolder.toFile());

        /* Looking for empty folders and delete them */

        operations.deleteEmptyFolders(sortedFolder.toFile());

        /* create here a treeset that will serve me as a summary of my files and file*/

        TreeSet<File> summary = new TreeSet<File>();
        summary = operations.collectFolderToSet(sortedFolder.toFile(), summary);

        /* I create a method that will print the contents of the given folder in parameter*/

        operations.summaryToConsole(summary);

        /* Methode to write summerize to text file */

        operations.summaryToText(summary, Paths.get(sortedFolder.getParent().toString(), "Summary.txt"));


    }


}
















