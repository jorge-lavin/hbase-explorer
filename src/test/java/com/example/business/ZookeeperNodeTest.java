package com.example.business;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import junit.framework.TestCase;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;

public class ZookeeperNodeTest extends TestCase
{
    public void testEqualsContract()
    {
        EqualsVerifier.simple().forClass(ZookeeperNode.class).verify();
    }

    public void testToString()
    {
        String expected = "host:123";
        String actual = new ZookeeperNode("host", 123).toString();

        Assert.assertEquals(expected, actual);
    }
}
