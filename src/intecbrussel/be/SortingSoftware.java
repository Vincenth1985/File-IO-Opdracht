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
        }


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
}
