package intecbrussel.be.Services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.TreeSet;

public class OperationClass implements FolderOperations {

    @Override
    public String getFileType(String s) {
        String extension;
        return extension = s.substring(s.lastIndexOf(".") + 1);
    }

    @Override
    public void creatingFileTypeFolder(String parentFolder, String extension) {
        Path paths = Paths.get(parentFolder, extension);

        try {
            if (!paths.toFile().exists())
                Files.createDirectory(paths);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void putAllHiddenFilesInHiddenFolder(File sourceFolder, File sortedFolder) {

        if (!Paths.get(sortedFolder.toString(), "HiddenFiles").toFile().exists()) {
            try {
                Files.createDirectory(Paths.get(sortedFolder.toString(), "HiddenFiles"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                putAllHiddenFilesInHiddenFolder(file, sortedFolder);
            } else if (file.isHidden()) {

                try {
                    Files.move(file.toPath().toAbsolutePath(), Paths.get(sortedFolder.toString(), "HiddenFiles", file.getName())
                            , StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    

    @Override
    public void deleteEmptyFolders(File sourceFolder) {

        for (File e : sourceFolder.listFiles()) {

            if (e.isDirectory()) {
                deleteEmptyFolders(e);
                e.delete();
            }
        }

    }

    @Override
    public void moveFileTypeToTargetFolder(File sourceFolder, String fileType, File targetFolder) {
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

    @Override
    public TreeSet<File> collectFolderToSet(File sourceFolder, TreeSet<File> folderToSummerize) {
        for (File file : sourceFolder.listFiles()) {
            if (file.isDirectory()) {
                folderToSummerize.add(file);
                collectFolderToSet(file, folderToSummerize);
            } else
                folderToSummerize.add(file);
        }
        return folderToSummerize;
    }

    @Override
    public void summaryToConsole(TreeSet<File> summary) {
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

    @Override
    public void summaryToText(TreeSet<File> summary, Path path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()))) {

            for (File file : summary) {

                if (file.isDirectory()) {
                    bufferedWriter.write("Directory : " + file);
                    bufferedWriter.newLine();
                    bufferedWriter.write("-".repeat(98));
                    bufferedWriter.newLine();
                } else if (file.isFile()) {
                    bufferedWriter.write(String.format("File :%-60sReadable:%-10bWritable:%4b", file.getName()
                            , Files.isReadable(file.toPath())
                            , Files.isWritable(file.toPath())));
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }

}

