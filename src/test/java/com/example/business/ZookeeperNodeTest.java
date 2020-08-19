package com.example.business;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import junit.framework.TestCase;
import nl.jqno.equalsverifier.EqualsVerifier;

public class ZookeeperNodeTest extends TestCase
{
    public void testEqualsContract()
    {
        EqualsVerifier.simple().forClass(ZookeeperNode.class).verify();
    }

    public void testToString()
    {
        ToStringVerifier.forClass(ZookeeperNode.class).withClassName(NameStyle.SIMPLE_NAME).verify();
    }
}
