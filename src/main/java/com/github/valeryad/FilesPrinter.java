package com.github.valeryad;

import java.io.File;
import java.io.PrintStream;

public class FilesPrinter {
    private PrintStream printer;
    private FilesProcessor filesProcessor;
    private File topDir;

    public FilesPrinter(PrintStream printer) {
        this.printer = printer;
        filesProcessor = new FilesProcessor();
    }

    public void printDirectoryTree(File topDir) {
        this.topDir = topDir;
        printer.print(topDir.getAbsolutePath() + '\n');

        printRecursive(topDir);
    }

    private void printRecursive(File file) {
        StringBuilder line;

        for(File tempFile : filesProcessor.sortFiles(file.listFiles())){

            line = formLineByHierarchy(tempFile, file, new StringBuilder());
            printer.print(line);

            if(tempFile.isFile()){
                if(filesProcessor.isLastFile(tempFile)){
                    printer.print(line.toString().replace(tempFile.getName(),""));
                }
            }else{
                printRecursive(tempFile);
            }
        }
    }


    private StringBuilder formLineByHierarchy(File file, File originalParent, StringBuilder line) {
        if (!file.getParentFile().equals(topDir)) {
            formLineByHierarchy(file.getParentFile(), originalParent, line);
        }

        if (file.getParentFile().equals(originalParent)) {
            return line.append(formLineByFileType(file));
        }

        if (filesProcessor.isParentHaveSubDirBelow(file)) {
            line.append('|');
        } else {
            line.append(' ');
        }
        line.append("   ");

        return line;
    }

    private StringBuilder formLineByFileType(File file) {
        StringBuilder line = new StringBuilder();

        if (file.isFile()) {
            if (filesProcessor.isParentHaveSubDirBelow(file)) {
                line.append('|');
            } else {
                line.append(' ');
            }
            line.append("   " + file.getName());
        }

        if (file.isDirectory()) {
            if (filesProcessor.isLastDir(file)) {
                line.append('\\');
            } else {
                line.append('+');
            }
            line.append("---" + file.getName());
        }
        return line.append('\n');
    }
}
