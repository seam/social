package org.jboss.seam.social.twitter.test;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.oauth.OAuthConfiguration;
import org.jboss.seam.social.scribe.OAuthTokenScribe;
import org.jboss.seam.social.twitter.TwitterService;
import org.jboss.seam.social.twitter.model.SuggestionCategory;
import org.jboss.seam.social.twitter.model.Tweet;
import org.jboss.seam.social.twitter.model.TwitterProfile;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TwitterTest {
    @Inject
    @OAuthConfiguration(apiKey = "FQzlQC49UhvbMZoxUIvHTQ", apiSecret = "VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k")
    TwitterService twitterService;

    @Deployment
    public static Archive<?> createTestArchive() {

        WebArchive ret = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(
                ShrinkWrap.create(ZipImporter.class, "seam-social-twitter.jar")
                        .importFrom(new File("target/seam-social-twitter.jar")).as(JavaArchive.class));

        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml").goOffline().artifact("org.jboss.seam.social:seam-social").artifact("org.jboss.solder:solder-impl");

        if ("weld-ee-embedded-1.1".equals(System.getProperty("arquillian"))) {
            // Exclude guava for weld embedded as it seems to cause conflict with weld's version
            resolver = resolver.exclusion("com.google.guava:guava");
        }

        ret.addAsLibraries(resolver.resolveAs(GenericArchive.class));

        return ret;
    }

    @Before
    public void init() {
        twitterService.getSession().setAccessToken(
                new OAuthTokenScribe("334872715-u75bjYqWyQSYjFMnKeTDZUn8i0QAExjUQ4ENZXH3",
                        "08QG7HVqDjkr1oH1YfBRWmd0n8EG73CuzJgTjFI0sk"));
        twitterService.initAccessToken();
    }

    @Test
    public void authorizationUrlTest() {
        Assert.assertTrue(twitterService.getAuthorizationUrl().startsWith("http"));
    }

    @Test
    public void sendATweet() {
        Tweet tweet = (Tweet) twitterService.updateStatus("Tweet sent from JUnit at " + new Date().toString());
        Assert.assertFalse(tweet.getId() == 0);

    }

    @Test
    public void searchUser() {
        init();
        List<TwitterProfile> res = twitterService.searchForUsers("antoine");
        Assert.assertFalse(res.isEmpty());

    }

    @Test
    public void SuggestionCaegoriesNotEmpty() {
        List<SuggestionCategory> res = twitterService.getSuggestionCategories();
        Assert.assertFalse(res.isEmpty());

    }
}
