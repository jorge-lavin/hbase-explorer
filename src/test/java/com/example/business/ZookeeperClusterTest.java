package com.example.business;

import com.example.app.UserPreferences;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import junit.framework.TestCase;
import nl.jqno.equalsverifier.EqualsVerifier;

public class ZookeeperClusterTest extends TestCase
{
    public void testEqualsContract()
    {
        EqualsVerifier.simple().forClass(ZookeeperCluster.class).verify();
    }

    public void testToString()
    {
        ToStringVerifier.forClass(ZookeeperCluster.class).withClassName(NameStyle.SIMPLE_NAME).verify();
    }
}
