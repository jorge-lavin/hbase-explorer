package com.example.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ResourcesFileAppVersionLoader implements AppVersionLoader
{
    private final static Logger logger = LoggerFactory.getLogger(ResourcesFileAppVersionLoader.class);
    private final static String DEFAULT_FILE_PATH = "version.properties";
    private final static ClassLoader DEFAULT_CLASS_LOADER = ResourcesFileAppVersionLoader.class.getClassLoader();

    private final static String VERSION_PROPERTY_NAME = "version";
    private final static String AUTHOR_PROPERTY_NAME = "author";
    private final static String BRANCH_PROPERTY_NAME = "branch";
    private final static String HASH_PROPERTY_NAME = "hash";
    private final static String MESSAGE_PROPERTY_NAME = "message";
    private final static String TIMESTAMP_PROPERTY_NAME = "timestamp";

    // TODO Why a ClassLoader instead of a simple path.
    private final ClassLoader classloader;
    private final String filePath;


    ResourcesFileAppVersionLoader(ClassLoader classloader, String filePath)
    {
        this.classloader = classloader;
        this.filePath = filePath;
    }

    public ResourcesFileAppVersionLoader(String filePath)
    {
        this(DEFAULT_CLASS_LOADER, filePath);
    }

    public ResourcesFileAppVersionLoader()
    {
        this(DEFAULT_FILE_PATH);
    }

    @Override
    public AppVersion load()
    {
        URL url = classloader.getResource(filePath);
        if (url == null)
        {
            logger.debug("No version file located at {}", filePath);
            return null;
        }

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
        catch (Exception ex)
        {
            logger.error("An error happened loading version file", ex);
            return null;
        }
    }
}
