package com.example.app;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import junit.framework.TestCase;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.slf4j.event.EventRecodingLogger;
import org.slf4j.event.Level;
import org.slf4j.event.SubstituteLoggingEvent;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.SubstituteLogger;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.everyItem;

public class AppVersionTest extends TestCase
{

    public void testLog() {
        AppVersion version = new AppVersion("0.0.1", "test-author", "test-branch", "test-hash", "test-message", 123L);

        Queue<SubstituteLoggingEvent> eventsQueue = new ArrayDeque<>();
        SubstituteLogger substituteLogger = new SubstituteLogger("AppVersionTest", eventsQueue, false);
        EventRecodingLogger eventRecodingLogger = new EventRecodingLogger(substituteLogger, eventsQueue);

        version.log(eventRecodingLogger);

        // All items are logged
        // TODO Its a little obscure to check that all logging events correspond to one message instead of the opposite.
        Assert.assertThat(eventsQueue, everyItem(
            anyOf(
                new MessageMatcher("Loading app 0.0.1"),
                new MessageMatcher("Author test-author"),
                new MessageMatcher("Branch test-branch"),
                new MessageMatcher("Hash test-hash"),
                new MessageMatcher("Message test-message"),
                new MessageMatcher("Timestamp 123")
            )
        ));
    }

    public void testVersion()
    {
        AppVersion version = new AppVersion("0.0.1", null, null, null, null, null);
        String expected = "0.0.1";
        String actual = version.version();

        Assert.assertEquals(expected, actual);
    }

    public void testEqualsContract()
    {
        EqualsVerifier.simple().forClass(AppVersion.class).verify();
    }

    public void testToString()
    {
        ToStringVerifier.forClass(AppVersion.class).withClassName(NameStyle.SIMPLE_NAME).verify();
    }

    private static class MessageMatcher extends TypeSafeMatcher<SubstituteLoggingEvent>
    {
        private final String expectedMessage;
        private final Level level;

        private MessageMatcher(String expectedMessage)
        {
            this.expectedMessage = expectedMessage;
            this.level = Level.DEBUG;
        }

        @Override
        protected boolean matchesSafely(SubstituteLoggingEvent item)
        {
            String formatted = MessageFormatter.arrayFormat(item.getMessage(), item.getArgumentArray()).getMessage();
            return Objects.equals(item.getLevel(), level) && Objects.equals(formatted, expectedMessage);
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("should match " + expectedMessage + " with level " + level);
        }
    }
}