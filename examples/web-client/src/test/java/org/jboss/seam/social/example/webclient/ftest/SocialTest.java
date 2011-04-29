package org.jboss.seam.social.example.webclient.ftest;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;

/**
 * A functional test for the Web Client example
 * 
 * @author Marek Schmidt
 */
public class SocialTest extends AbstractTestCase {

    private static final int checkInterval = 1000;
    private static final int modelTimeout = 30000;

    private static Properties properties = null;
    private static String PROPERTY_FILE = "ftest.properties";

    protected final XpathLocator HOME_TWITTER_LINK = xp("//a/img[contains(@src,'twitter')]/..");
    protected final XpathLocator HOME_LINKEDIN_LINK = xp("//a/img[contains(@src,'linkedin')]/..");

    protected final XpathLocator CALLBACK_TWITTER_HEADER = xp("//h1[text()='Seam Social Twitter client Example']");
    protected final XpathLocator CALLBACK_TWITTER_CLIENT = xp("//a[text()='Twitter client']");

    protected final XpathLocator TWITTER_USERNAME = xp("//input[contains(@id,'username')]");
    protected final XpathLocator TWITTER_PASSWORD = xp("//input[contains(@id,'password')]");
    protected final XpathLocator TWITTER_ALLOW = xp("//input[contains(@value,'Allow')]");

    protected final XpathLocator LINKEDIN_EMAIL = xp("//input[contains(@name,'login')]");
    protected final XpathLocator LINKEDIN_PASSWORD = xp("//input[contains(@name,'password')]");
    protected final XpathLocator LINKEDIN_AUTHORIZE = xp("//input[contains(@name,'authorize')]");

    @BeforeMethod
    public void openStartUrl() throws MalformedURLException {
        selenium.setSpeed(300);
        selenium.open(new URL(contextPath.toString()));
    }

    @Test
    public void testTwitterOAuth() {
        selenium.click(HOME_TWITTER_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(TWITTER_USERNAME));

        // We are on the twitter oauth page now
        assertEquals(selenium.getLocation().toString().contains("api.twitter.com/oauth"), true);

        selenium.type(TWITTER_USERNAME, getProperty("twitter.username"));
        selenium.type(TWITTER_PASSWORD, getProperty("twitter.password"));
        waitHttp(selenium).click(TWITTER_ALLOW);

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_TWITTER_HEADER));

        // We are back now
        assertEquals(selenium.getLocation().toString().contains(contextRoot.toString()), true);
        assertEquals(selenium.isTextPresent("You're back and your verifier is"), true);

        // We go to the twitter client
        waitHttp(selenium).click(CALLBACK_TWITTER_CLIENT);
    }

    @Test
    public void testLinkedInOAuth() {
        selenium.click(HOME_LINKEDIN_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(LINKEDIN_EMAIL));

        // We are on the LinkedIn page now
        assertEquals(selenium.getLocation().toString().contains("www.linkedin.com/uas/oauth"), true);

        selenium.type(LINKEDIN_EMAIL, getProperty("linkedin.email"));
        selenium.type(LINKEDIN_PASSWORD, getProperty("linkedin.password"));
        waitHttp(selenium).click(LINKEDIN_AUTHORIZE);

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_TWITTER_HEADER));

        assertEquals(selenium.getLocation().toString().contains(contextRoot.toString()), true);
        assertEquals(selenium.isTextPresent("You're back and your verifier is"), true);
    }

    public String getProperty(String key) {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties.getProperty(key);
    }
}
