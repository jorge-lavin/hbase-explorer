package com.example.app;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import junit.framework.TestCase;
import nl.jqno.equalsverifier.EqualsVerifier;

public class UserPreferencesTest extends TestCase
{
    public void testEqualsContract()
    {
        EqualsVerifier.simple().forClass(UserPreferences.class).verify();
    }

    public void testToString()
    {
        ToStringVerifier.forClass(UserPreferences.class).withClassName(NameStyle.SIMPLE_NAME).verify();
    }
}