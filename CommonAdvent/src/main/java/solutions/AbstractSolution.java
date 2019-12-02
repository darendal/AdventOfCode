/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class AbstractSolution {

    private final int solutionNumber;
    private final int dayNumber;

    protected AbstractSolution(final int solutionNumber, final int dayNumber) {
        this.solutionNumber = solutionNumber;
        this.dayNumber = dayNumber;
    }

    public void run() {
        this.printSolutionHeader();
    }

    protected void printSolutionHeader() {
        System.out.println(String.format("\n\n====== Running Solution #%s for day %s ======\n\n", solutionNumber, dayNumber));
    }

    protected List<String> getInput(final String filename) throws IllegalArgumentException {

        final File file = getFileFromResources(filename);

        try {
            return FileUtils.readLines(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File does not exist");
        }

    }

    private File getFileFromResources(final String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    protected <T> void printResult(final T result) {
        System.out.println(String.format("Answer is %s", result));
    }



}
