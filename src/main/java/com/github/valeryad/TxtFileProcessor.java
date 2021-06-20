package com.github.valeryad;

import java.io.*;

public class TxtFileProcessor {
    private static final String DON_T_HAVE_ACCESS_TO_FILE_MESSAGE = "Don't have access to file";
    private static final String JAVA_IO_EXCEPTION_MESSAGE = "Java IO exception";
    private static final String DIRECTORY_REGEX = ".+--+.+";
    private static final String FILE_TRIM_REGEX = "\\s*\\|*\\s\\s+";
    private static final String LINE_NOT_REPRESENTING_FILE_OR_DIR_REGEX = "(\\s*\\|*\\s*)*$";

    int directoriesAmount;
    int filesAmount;
    double averageFilesInDirectoryAmount;
    double averageLengthOfFileName;


    public TxtFileProcessor(File file) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            StringBuilder lineRepresentingFileCollector = new StringBuilder();
            StringBuilder lineRepresentingDirCollector = new StringBuilder();

            reader.readLine();
            int dirCounter = 0;
            int fileCounter = 0;
            int symbolsCounter = 0;

            while ((line = reader.readLine()) != null) {

                if (!line.matches(LINE_NOT_REPRESENTING_FILE_OR_DIR_REGEX)) {
                    if (line.matches(DIRECTORY_REGEX)) {
                        dirCounter++;
                    } else {
                        fileCounter++;
                        symbolsCounter += line.replaceAll(FILE_TRIM_REGEX, "").length();
                    }
                }

                directoriesAmount = dirCounter;
                filesAmount = fileCounter;
                averageFilesInDirectoryAmount = ((double) filesAmount) / directoriesAmount;
                averageLengthOfFileName = ((double) symbolsCounter) / filesAmount;

            }

        } catch (FileNotFoundException e) {
            System.err.println(DON_T_HAVE_ACCESS_TO_FILE_MESSAGE + file.getAbsolutePath());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(JAVA_IO_EXCEPTION_MESSAGE);
            System.exit(1);
        }
    }

    public int getDirectoriesAmount() {
        return directoriesAmount;
    }

    public int getFilesAmount() {
        return filesAmount;
    }

    public double getAverageFilesInDirectoryAmount() {
        return averageFilesInDirectoryAmount;
    }

    public double getAverageLengthOfFileName() {
        return averageLengthOfFileName;
    }
}
