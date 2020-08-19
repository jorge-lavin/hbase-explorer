package com.example.app;

import com.example.business.ZookeeperCluster;
import com.example.business.ZookeeperNode;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserPreferencesFileRepositoryTest extends TestCase
{
    private static final Path hBaseExplorerFolder = Paths.get("src","test","resources", ".hbase-explorer");
    private static final UserPreferences testPreferences = new UserPreferences(Arrays.asList(
        new ZookeeperCluster("foo", Stream.of(new ZookeeperNode("foo-1", 2181), new ZookeeperNode("foo-2", 2181)).collect(Collectors.toSet())),
        new ZookeeperCluster("bar", Stream.of(new ZookeeperNode("bar-1", 2181), new ZookeeperNode("bar-2", 2181)).collect(Collectors.toSet()))
    ));

    public void testLoad()
    {
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository(hBaseExplorerFolder.resolve("preferences-loaded.json").toFile());
        UserPreferences actual = repo.load();

        Assert.assertEquals(testPreferences, actual);
    }

    public void testLoadNotFound()
    {
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository(hBaseExplorerFolder.resolve("does-not-exists.json").toFile());
        Assert.assertNull(repo.load());
    }

    public void testLoadDefault()
    {
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository();
        // If the file is present, we dont know its contents. Just test for null or not null accordingly.
        if (UserPreferencesFileRepository.defaultSettingsFile.exists())
        {
            Assert.assertNotNull(repo.load());
        }
        else
        {
            Assert.assertNull(repo.load());
        }
    }

    public void testStore()
    {
        File preferencesFile = hBaseExplorerFolder.resolve("preferences-stored.json").toFile();
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository(preferencesFile);
        repo.store(testPreferences);

        Assert.assertEquals(testPreferences, repo.load());
    }

    // TODO testStore forcing IOException.

    public void testStoreDefault() throws IOException
    {
        withBackupForDefault(repo ->
        {
            repo.store(testPreferences);
            Assert.assertEquals(testPreferences, repo.load());
        });
    }

    public void testDelete()
    {
        File preferencesFile = hBaseExplorerFolder.resolve("preferences-deleted.json").toFile();
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository(preferencesFile);
        repo.store(testPreferences);
        repo.delete();

        Assert.assertFalse(preferencesFile.exists());
    }

    public void testDeleteDefault() throws IOException
    {
        withBackupForDefault(repo ->
        {
            repo.store(testPreferences);
            Assert.assertTrue(UserPreferencesFileRepository.defaultSettingsFile.exists());

            repo.delete();
            Assert.assertFalse(UserPreferencesFileRepository.defaultSettingsFile.exists());
        });
    }

    public void testDeleteNotFound()
    {
        File preferencesFile = Paths.get("foo","bar","baz").toFile();
        UserPreferencesFileRepository repo = new UserPreferencesFileRepository(preferencesFile);
        repo.delete();
    }

    private void withBackupForDefault(Consumer<UserPreferencesFileRepository> action) throws IOException
    {
        Path backupTemporalFile = Files.createTempFile("preferences-json", null);
        backup(backupTemporalFile);

        UserPreferencesFileRepository repo = new UserPreferencesFileRepository();
        try
        {
            action.accept(repo);
        }
        finally
        {
            restore(backupTemporalFile);
            //noinspection ResultOfMethodCallIgnored
            backupTemporalFile.toFile().delete();
        }
    }

    private void backup(Path backupTemporalFile) throws IOException
    {
        copyReplacing(UserPreferencesFileRepository.defaultSettingsFile.toPath(), backupTemporalFile);
    }

    private void restore(Path backupTemporalFile) throws IOException
    {
        copyReplacing(backupTemporalFile, UserPreferencesFileRepository.defaultSettingsFile.toPath());
    }

    private void copyReplacing(Path source, Path target) throws IOException
    {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}