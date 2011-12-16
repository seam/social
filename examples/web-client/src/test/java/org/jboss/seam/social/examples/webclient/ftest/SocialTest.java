package org.jboss.seam.social.examples.webclient.ftest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
import org.jboss.arquillian.ajocado.locator.XPathLocator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.seam.social.SeamSocialException;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.jboss.arquillian.ajocado.locator.LocatorFactory.xp;
import static org.junit.Assert.*;
import static org.jboss.arquillian.ajocado.Ajocado.*;


/**
 * A functional test for the Web Client example
 * 
 * @author Marek Schmidt
 */
@RunWith(Arquillian.class)
public class SocialTest {

    private static final int checkInterval = 1000;
    private static final int modelTimeout = 30000;

    private static Properties properties = null;
    private static String PROPERTY_FILE = "ftest.properties";

    protected final XPathLocator HOME_TWITTER_LINK = xp("//a[contains(text(),'Twitter')]");
    protected final XPathLocator HOME_LINKEDIN_LINK = xp("//a[contains(text(),'LinkedIn')]");
    protected final XPathLocator HOME_FACEBOOK_LINK = xp("//a[contains(text(),'Facebook')]");

    protected final XPathLocator CALLBACK_HEADER = xp("//h1[text()='Seam Social client Example']");
    protected final XPathLocator CALLBACK_CLIENT = xp("//a[text()='client']");

    protected final XPathLocator TWITTER_USERNAME = xp("//input[contains(@id,'username')]");
    protected final XPathLocator TWITTER_PASSWORD = xp("//input[contains(@id,'password')]");
    protected final XPathLocator TWITTER_ALLOW = xp("//input[contains(@value,'Authorize app')]");

    protected final XPathLocator LINKEDIN_EMAIL = xp("//input[contains(@name,'session_key')]");
    protected final XPathLocator LINKEDIN_PASSWORD = xp("//input[contains(@name,'session_password')]");
    protected final XPathLocator LINKEDIN_AUTHORIZE = xp("//input[contains(@name,'authorize')]");

    protected final XPathLocator FACEBOOK_EMAIL = xp("//input[@id='email']");
    protected final XPathLocator FACEBOOK_PASSWORD = xp("//input[@id='pass']");
    protected final XPathLocator FACEBOOK_LOGIN = xp("//input[@name='login']");
    protected final XPathLocator FACEBOOK_ALLOW = xp("//input[@name='grant_clicked']");
    
    protected static final XPathLocator LINKEDIN_CLOSE_CONNECTION = xp("//a[contains(text(),'LinkedIn ')]");
    protected static final XPathLocator TWITTER_CLOSE_CONNECTION = xp("//a[contains(text(),'Twitter ')]");
    protected static final XPathLocator FACEBOOK_CLOSE_CONNECTION = xp("//a[contains(text(),'Facebook ')]");
    private static final String LINKEDIN_CONNECTION = "You are now working with LinkedIn ";
    private static final String TWITTER_CONNECTION = "You are now working with Twitter ";
    private static final String FACEBOOK_CONNECTION = "You are now working with Facebook ";
    
    public static final String ARCHIVE_NAME = "social-web-client.war";
    public static final String BUILD_DIRECTORY = "target";
    
    
    @ArquillianResource
    URL contextRoot;
    
    @Drone
    AjaxSelenium selenium;
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(ZipImporter.class, ARCHIVE_NAME).importFrom(new File(BUILD_DIRECTORY + '/' + ARCHIVE_NAME))
                .as(WebArchive.class);
    }

    @Before
    public void openStartUrl() throws MalformedURLException {
        contextRoot = new URL(contextRoot.toString().replaceAll("127.0.0.1", "localhost"));
        selenium.setSpeed(300);
        selenium.open(new URL(contextRoot.toString()));
    }

    @Test
    public void testTwitterOAuth() {
        selenium.click(HOME_TWITTER_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(TWITTER_USERNAME));

        // We are on the twitter oauth page now
        assertEquals(selenium.getLocation().toString().contains("api.twitter.com/oauth"), true);

        selenium.type(TWITTER_USERNAME, getProperty("twitter.username"));
        selenium.type(TWITTER_PASSWORD, getProperty("twitter.password"));
        waitForHttp(selenium).click(TWITTER_ALLOW);

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        // We go to the twitter client
        waitForHttp(selenium).click(CALLBACK_CLIENT);
        
        //closing connection
        waitForHttp(selenium).click(TWITTER_CLOSE_CONNECTION);
        assertFalse("Can't close active connection!",selenium.isTextPresent(TWITTER_CONNECTION));
    }

    @Test
    public void testLinkedInOAuth() {
        selenium.click(HOME_LINKEDIN_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(LINKEDIN_EMAIL));

        // We are on the LinkedIn page now
        assertEquals(selenium.getLocation().toString().contains("www.linkedin.com/uas/oauth"), true);

        selenium.type(LINKEDIN_EMAIL, getProperty("linkedin.email"));
        selenium.type(LINKEDIN_PASSWORD, getProperty("linkedin.password"));
        waitForHttp(selenium).click(LINKEDIN_AUTHORIZE);

        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        // We go to the LinkedIn client
        waitForHttp(selenium).click(CALLBACK_CLIENT);
       
        //closing connection
        waitForHttp(selenium).click(LINKEDIN_CLOSE_CONNECTION);
        assertFalse("Can't close active connection!",selenium.isTextPresent(LINKEDIN_CONNECTION));
    }

    @Test
    public void testFacebookOAuth() {
        selenium.click(HOME_FACEBOOK_LINK);
        waitModel.interval(checkInterval).timeout(modelTimeout).until(elementPresent.locator(FACEBOOK_EMAIL));

        // We are on the Facebook login page now
        assertEquals(selenium.getLocation().toString().contains("www.facebook.com/login"), true);

        selenium.type(FACEBOOK_EMAIL, getProperty("facebook.email"));
        selenium.type(FACEBOOK_PASSWORD, getProperty("facebook.password"));
        waitForHttp(selenium).click(FACEBOOK_LOGIN);

        // We wait for potential redirect
        waitModel.timeout(3000).waitForTimeout();

        // Now there are two possibilities, the account has already authorized the app, or not
        if (selenium.getLocation().toString().contains("www.facebook.com/connect")) {
            // We are on the connect page, need to authorize the app
            selenium.click(FACEBOOK_ALLOW);
        }

        // Now we expect to be redirected to our callback page
        waitModel.interval(checkInterval).timeout(modelTimeout / 2).until(elementPresent.locator(CALLBACK_HEADER));

        // We go to the Facebook client
        waitForHttp(selenium).click(CALLBACK_CLIENT);
 
        //closing connection
        waitForHttp(selenium).click(FACEBOOK_CLOSE_CONNECTION);
        assertFalse("Can't close active connection!",selenium.isTextPresent(FACEBOOK_CONNECTION));
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
