package com.example.app;

import org.slf4j.Logger;

import java.util.Objects;

/** Represents the current version of the application **/
public class AppVersion
{
    /** The version, for example 1.0-SNAPSHOT **/
    private final String version;

    private final String author;
    private final String branch;
    private final String hash;
    private final String message;
    private final Long timestamp;


    public AppVersion(String version, String author, String branch, String hash, String message, Long timestamp)
    {
        this.version = version;
        this.author = author;
        this.branch = branch;
        this.hash = hash;
        this.message = message;
        this.timestamp = timestamp;
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

    public String version()
    {
        return version;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "version='" + version + '\'' +
                ", author='" + author + '\'' +
                ", branch='" + branch + '\'' +
                ", hash='" + hash + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppVersion that = (AppVersion) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(author, that.author) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(message, that.message) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, author, branch, hash, message, timestamp);
    }
}
