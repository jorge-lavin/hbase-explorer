package com.example.app;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/** A File backed UserPreferencesRepository **/
public class UserPreferencesFileRepository implements UserPreferencesRepository
{
    // TODO Store AppVersion on file.
    private final static Logger logger = LoggerFactory.getLogger(UserPreferencesFileRepository.class);
    private final static Gson gson = new Gson();

    // ~/.hbase-explorer/preferences.json
    public static final File defaultSettingsFile = Paths.get(System.getProperty("user.home"))
            .resolve(".hbase-explorer")
            .resolve("preferences.json").toFile();

    /** File that stores the user preferences, as a json. **/
    private final File settingsFile;


    UserPreferencesFileRepository(File settingsFile)
    {
        this.settingsFile = settingsFile;
    }

    public UserPreferencesFileRepository()
    {
        // FIXME What if user.home does not exists?
        // FIXME Create .hbase-explorer folder if not exists.
        this(defaultSettingsFile);
    }


    @Override
    public UserPreferences load()
    {
        try(FileReader reader = new FileReader(settingsFile))
        {
            return gson.fromJson(reader, UserPreferences.class);
        }
        catch (IOException e)
        {
            logger.error("An error happened loading preferences file", e);
            return null;
        }
    }

    @Override
    public void store(UserPreferences preferences)
    {
        if (!settingsFile.exists()) //noinspection ResultOfMethodCallIgnored
            settingsFile.getParentFile().mkdirs();

        try(FileWriter writer = new FileWriter(settingsFile))
        {
            gson.toJson(preferences, writer);
        }
        catch (IOException e)
        {
            logger.error("An error happened storing preferences file", e);
        }
    }

    @Override
    public void delete()
    {
        //noinspection ResultOfMethodCallIgnored
        settingsFile.delete();
    }
}
