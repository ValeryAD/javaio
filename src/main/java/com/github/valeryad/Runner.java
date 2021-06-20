package com.github.valeryad;

import java.io.*;

public class Runner {

    private static final String TOO_MUCH_ARGUMENTS_MESSAGE = "Too much arguments, should be one";
    private static final String WRONG_PARAMETER_MESSAGE = "Wrong parameter";
    private static final String DON_T_HAVE_ACCESS_TO_FILE_MESSAGE = "Don't have access to file";
    private static final String OUTPUT_FILE_PATH = "data\\info.txt";
    private static final String JAVA_IO_EXCEPTION_MESSAGE = "Java IO exception";
    private static final String TXT_PATTERN = ".txt";
    public static final String TXT_FILE_STATISTICS_REPORT = "File %s contains:%n" +
            "%d names of files;%n" +
            "%d names of directories;%n" +
            "%.2f - average files in directory amount;%n" +
            "%.2f - average length of file name";
    private static final String WRONG_PATH_MESSAGE = "Wrong path:\n" +
            "Should be either directory or .txt file";

    public static void main(String[] args) {

        File file = getFile(args);

        if (file.isDirectory()) {
            printTree(file);
        }

        if (file.isFile()) {
            if (file.getName().toLowerCase().endsWith(TXT_PATTERN)) {
                readFileAndPrintStatisticsToConsole(file);
            } else {
                System.out.println(WRONG_PATH_MESSAGE);
            }
        }
    }

    private static File getFile(String[] args) {
        if (args.length > 1) {
            System.out.println(TOO_MUCH_ARGUMENTS_MESSAGE);
            System.exit(1);
        }

        File file;

        if (args.length < 1) {
            file = new File("data");
        } else {
            file = new File(args[0]);
        }

        if (!file.exists()) {
            System.out.println(WRONG_PARAMETER_MESSAGE);
            System.exit(2);
        }
        return file;
    }

    private static void printTree(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE_PATH);
             PrintStream printStream = new PrintStream(fileOutputStream)) {

            FilesPrinter filesPrinter = new FilesPrinter(printStream);
            filesPrinter.printDirectoryTree(file);

        } catch (FileNotFoundException e) {
            System.err.println(DON_T_HAVE_ACCESS_TO_FILE_MESSAGE + OUTPUT_FILE_PATH);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(JAVA_IO_EXCEPTION_MESSAGE);
            System.exit(1);
        }
    }

    private static void readFileAndPrintStatisticsToConsole(File file) {
        TxtFileProcessor txtFileProcessor = new TxtFileProcessor(file);
        System.out.printf(TXT_FILE_STATISTICS_REPORT,
                file.getName(),
                txtFileProcessor.getFilesAmount(),
                txtFileProcessor.getDirectoriesAmount(),
                txtFileProcessor.getAverageFilesInDirectoryAmount(),
                txtFileProcessor.getAverageLengthOfFileName());
    }
}
