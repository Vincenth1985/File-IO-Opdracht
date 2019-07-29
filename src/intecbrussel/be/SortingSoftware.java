package intecbrussel.be;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SortingSoftware {

    public static void main(String[] args) {


        //Path from the unsorted folder
        String folder = "/Users/vincenthonca/Desktop/dossier";

        Path path = Paths.get(folder);


        if (path.toFile().exists()) {

            for (String s : path.toFile().list((dir, name) -> !name.equals(".DS_Store"))){
                creatingfileTypeFolder(path.toString(),getFileType(s));

            }
        }


    }
}
