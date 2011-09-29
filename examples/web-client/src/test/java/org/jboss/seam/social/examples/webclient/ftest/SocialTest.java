package org.jboss.seam.social.examples.webclient.ftest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.jboss.seam.social.SeamSocialException;
import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.testng.Assert.assertEquals;

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

    protected final XpathLocator HOME_TWITTER_LINK = xp("//a[contains(text(),'Twitter')]");
    protected final XpathLocator HOME_LINKEDIN_LINK = xp("//a[contains(text(),'LinkedIn')]");
    protected final XpathLocator HOME_FACEBOOK_LINK = xp("//a[contains(text(),'Facebook')]");

    protected final XpathLocator CALLBACK_HEADER = xp("//h1[text()='Seam Social client Example']");
    protected final XpathLocator CALLBACK_CLIENT = xp("//a[text()='client']");

    protected final XpathLocator TWITTER_USERNAME = xp("//input[contains(@id,'username')]");
    protected final XpathLocator TWITTER_PASSWORD = xp("//input[contains(@id,'password')]");
    protected final XpathLocator TWITTER_ALLOW = xp("//input[contains(@value,'Authorize app')]");

    protected final XpathLocator LINKEDIN_EMAIL = xp("//input[contains(@name,'session_key')]");
    protected final XpathLocator LINKEDIN_PASSWORD = xp("//input[contains(@name,'session_password')]");
    protected final XpathLocator LINKEDIN_AUTHORIZE = xp("//input[contains(@name,'authorize')]");

    protected final XpathLocator FACEBOOK_EMAIL = xp("//input[@id='email']");
    protected final XpathLocator FACEBOOK_PASSWORD = xp("//input[@id='pass']");
    protected final XpathLocator FACEBOOK_LOGIN = xp("//input[@name='login']");
    protected final XpathLocator FACEBOOK_ALLOW = xp("//input[@name='grant_clicked']");

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

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        // We are back now
        assertEquals(selenium.getLocation().toString().contains(contextRoot.toString()), true);

        // We go to the twitter client
        waitHttp(selenium).click(CALLBACK_CLIENT);
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

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        assertEquals(selenium.getLocation().toString().contains(contextRoot.toString()), true);
        // We go to the LinkedIn client
        waitHttp(selenium).click(CALLBACK_CLIENT);
    }

    @Test
    public void testFacebookOAuth() {
        selenium.click(HOME_FACEBOOK_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(FACEBOOK_EMAIL));

        // We are on the Facebook login page now
        assertEquals(selenium.getLocation().toString().contains("www.facebook.com/login"), true);

        selenium.type(FACEBOOK_EMAIL, getProperty("facebook.email"));
        selenium.type(FACEBOOK_PASSWORD, getProperty("facebook.password"));
        waitHttp(selenium).click(FACEBOOK_LOGIN);

        // We wait for potential redirect
        waitModel.timeout(3000).waitForTimeout();

        // Now there are two possibilities, the account has already authorized the app, or not
        if (selenium.getLocation().toString().contains("www.facebook.com/connect")) {
            // We are on the connect page, need to authorize the app
            selenium.click(FACEBOOK_ALLOW);
        }

        // Now we expect to be redirected to our callback page
        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        assertEquals(selenium.getLocation().toString().contains(contextRoot.toString()), true);

        // We go to the Facebook client
        waitHttp(selenium).click(CALLBACK_CLIENT);
    }

    public String getProperty(String key) {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE));
            } catch (IOException e) {
                throw new SeamSocialException("Error while reading properties", e);
            }
        }

        return properties.getProperty(key);
    }
}
