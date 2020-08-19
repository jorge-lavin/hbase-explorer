package com.example.app;

import junit.framework.TestCase;
import org.junit.Assert;

public class ResourcesFileAppVersionLoaderTest  extends TestCase
{
    public void testLoad()
    {
        AppVersion expected = new AppVersion("test-version", "test-author", "test-branch", "test-hash", "test-message", 123L);
        ClassLoader classLoader = getClass().getClassLoader();
        AppVersion actual = new ResourcesFileAppVersionLoader(classLoader, "version.test.properties").load();

        Assert.assertEquals(expected, actual);
    }

    public void testLoadEmptyFile()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        AppVersion actual = new ResourcesFileAppVersionLoader(classLoader, "version.empty.properties").load();

        Assert.assertNull(actual);
    }

    public void testLoadNotPresent()
    {
        Assert.assertNull(new ResourcesFileAppVersionLoader("foobar").load());
    }
}