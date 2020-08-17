package com.example;


import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;

public class AppVersion
{
    private final static AppVersion EMPTY_VERSION = new AppVersion();
    private final static String VERSION_RESOURCE_FILE_PATH = "version.properties";

    private final static String VERSION_PROPERTY_NAME = "version";

    private final static String AUTHOR_PROPERTY_NAME = "author";
    private final static String BRANCH_PROPERTY_NAME = "branch";
    private final static String HASH_PROPERTY_NAME = "hash";
    private final static String MESSAGE_PROPERTY_NAME = "message";
    private final static String TIMESTAMP_PROPERTY_NAME = "timestamp";

    private final String version;
    // Git related. Maybe present, or not.
    private final String author;
    private final String branch;
    private final String hash;
    private final String message;
    private final Long timestamp;


    private AppVersion(String version, String author, String branch, String hash, String message, Long timestamp)
    {
        this.version = version;
        this.author = author;
        this.branch = branch;
        this.hash = hash;
        this.message = message;
        this.timestamp = timestamp;
    }

    private AppVersion()
    {
        this(null, null, null, null, null, null);
    }

    @Override
    public String toString()
    {
        return version;
    }

    public void log(Logger logger)
    {
        logger.debug("Loading app {}", version);
        logger.debug("Author {}", author);
        logger.debug("Branch {}", branch);
        logger.debug("Hash {}", hash);
        logger.debug("Message {}", message);
        logger.debug("Timestamp {}", timestamp);
    }

    public static AppVersion fromVersionFile()
    {
        URL url = AppVersion.class.getClassLoader().getResource(VERSION_RESOURCE_FILE_PATH);
        if (url == null) return EMPTY_VERSION;

        try(InputStream is = url.openStream())
        {
            Properties properties = new Properties();
            properties.load(is);
            String version = properties.getProperty(VERSION_PROPERTY_NAME);
            String author = properties.getProperty(AUTHOR_PROPERTY_NAME);
            String branch = properties.getProperty(BRANCH_PROPERTY_NAME);
            String hash = properties.getProperty(HASH_PROPERTY_NAME);
            String message = properties.getProperty(MESSAGE_PROPERTY_NAME);
            Long timestamp = Long.valueOf(properties.getProperty(TIMESTAMP_PROPERTY_NAME));
            return new AppVersion(version, author, branch, hash, message, timestamp);
        }
        catch (IOException ex)
        {
            // TODO Log me.
            return EMPTY_VERSION;
        }
    }
}
