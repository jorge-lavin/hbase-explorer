package com.example.app;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;

import static org.junit.Assert.*;

public class ResourcesFileAppVersionLoaderTest  extends TestCase
{
    public void testLoad()
    {
        AppVersion expected = new AppVersion("", "", "", "", "", 123L);
        AppVersion actual = new ResourcesFileAppVersionLoader().load();

        Assert.assertEquals(expected, actual);
    }

    public void testLoadNotPresent()
    {
        Assert.assertNull(new ResourcesFileAppVersionLoader("foobar").load());
    }
}