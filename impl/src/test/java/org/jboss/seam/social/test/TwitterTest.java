package org.jboss.seam.social.test;

import java.io.File;
import java.io.FileNotFoundException;

import javax.inject.Inject;


import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.OAuthSessionSettings;
import org.jboss.seam.social.oauth.OAuthSessionSettingsImpl;
import org.jboss.seam.social.oauth.RelatedTo;
import org.jboss.seam.social.oauth.Service;
import org.jboss.seam.social.twitter.Twitter;
import org.jboss.seam.social.twitter.TwitterScribe;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.logging.LoggerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TwitterTest {
    @Inject
    Twitter twitter;

    @Deployment
    public static JavaArchive createTestArchive() throws FileNotFoundException {
        File beanFile = new File("src/test/resources/META-INF/beans.xml");
        if (!beanFile.exists())
            throw new FileNotFoundException();
        return ShrinkWrap
                .create(JavaArchive.class, "test.jar")
                .addClasses(TwitterScribe.class, Twitter.class, OAuthService.class, OAuthServiceScribe.class,
                        OAuthServiceSettings.class, OAuthServiceSettings.class, OAuthSessionSettings.class,
                        OAuthSessionSettingsImpl.class,RelatedTo.class,Service.class)
                .addAsManifestResource(new FileAsset(beanFile),
                        ArchivePaths.create("beans.xml"));
    }
    
    @Test
    public void authorizationUrlTest()
    {
        Assert.assertTrue(twitter.getAuthorizationUrl().startsWith("http"));
    }
}
