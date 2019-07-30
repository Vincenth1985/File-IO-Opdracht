package intecbrussel.be.Services;


import java.io.File;
import java.nio.file.Path;
import java.util.TreeSet;

public interface FolderOperations {

    String getFileType(String s);

    void putAllHiddenFilesInHiddenFolder(File sourceFolder, File sortedFolder);

    void creatingFileTypeFolder(String parentFolder, String extension);

    void deleteEmptyFolders(File sourceFolder);

    void moveFileTypeToTargetFolder(File sourceFolder, String fileType, File targetFolder);

    TreeSet<File> collectFolderToSet(File sourceFolder, TreeSet<File> folderToSummerize);

    void summaryToConsole(TreeSet<File> summary);

    void summaryToText(TreeSet<File> summary, Path path);
}

