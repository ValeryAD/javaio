package com.github.valeryad;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilesProcessor {

    public boolean isLastFile(File file) {
        List<File> listFiles = Arrays.stream(file.getParentFile().listFiles()).
                filter(tempFile -> tempFile.isFile()).
                sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());

        return listFiles.get(listFiles.size() - 1).equals(file);
    }

    public boolean isLastDir(File dir) {
        if (dir.isDirectory()) {
            File[] siblings = sortFiles(dir.getParentFile().listFiles());
            if (dir.equals(siblings[siblings.length - 1])) {
                return true;
            }
        }
        return false;
    }

    public File[] sortFiles(File[] files) {
        if (files == null) {
            return new File[0];
        }
        List<File> listFiles = Arrays.stream(files).
                filter(file -> file.isFile() && file != null).
                sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());

        List<File> listDirs = Arrays.stream(files).
                filter(file -> file.isDirectory()).
                sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).
                collect(Collectors.toList());

        listFiles.addAll(listDirs);

        return listFiles.toArray(new File[0]);
    }


    public boolean isParentHaveSubDirBelow(File file) {

        List<File> subFiles = Arrays.asList(sortFiles(file.getParentFile().listFiles()));

        if (subFiles.indexOf(file) == subFiles.size() - 1) {
            return false;
        }

        subFiles = subFiles.subList(subFiles.indexOf(file) + 1, subFiles.size());
        return subFiles.stream().anyMatch(tempFile -> tempFile.isDirectory());
    }
}
