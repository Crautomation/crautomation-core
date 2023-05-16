package com.github.crautomation.core.common.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Utility for reading resources as files.
 */
public class PropertiesReader {
    public static File readFile(final String property) {
        InputStream fileLoader = null;
        File file = new File(System.getProperty("user.dir")
                + "/target/classes/" + property);

        try {
            file.getParentFile().mkdirs();
            fileLoader = PropertiesReader.class.getClassLoader().getResourceAsStream(property);
            Files.write(file.toPath(), IOUtils.toByteArray(fileLoader), StandardOpenOption.CREATE);
            file.setExecutable(true);
        } catch (NullPointerException | IOException e) {
            assertThat("Unable to load file, thrown".concat(e.toString()), false);
        }

        return file;
    }
}