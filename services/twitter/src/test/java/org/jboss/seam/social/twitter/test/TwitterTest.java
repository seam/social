package org.jboss.seam.social.twitter.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.oauth.ConfigureOAuth;
import org.jboss.seam.social.scribe.OAuthTokenScribe;
import org.jboss.seam.social.twitter.Twitter;
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
    @ConfigureOAuth(apiKey = "FQzlQC49UhvbMZoxUIvHTQ", apiSecret = "VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k")
    Twitter twitter;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {
        /*
         * File beanFile = new File("src/test/resources/META-INF/beans.xml"); if (!beanFile.exists()) throw new
         * FileNotFoundException();
         */
        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addAsLibraries(
                        ShrinkWrap.create(
                                ZipImporter.class, "seam-social-api.jar")
                                .importFrom(new File("../../api/target/seam-social-api.jar"))
                                .as(JavaArchive.class),
                        ShrinkWrap.create(
                                ZipImporter.class, "seam-social.jar")
                                .importFrom(new File("../../impl/target/seam-social.jar"))
                                .as(JavaArchive.class),                        
                        ShrinkWrap.create(
                                ZipImporter.class, "seam-social-twitter.jar")
                                .importFrom(new File("target/seam-social-twitter.jar"))
                                .as(JavaArchive.class)
                        );
                
        if ("weld-ee-embedded-1.1".equals(System.getProperty("arquillian"))) {
            // Don't embed dependencies that are already in the CL in the embedded container from surefire
            ret.addAsLibraries(
                    DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../../settings.xml")
                            .loadMetadataFromPom("../../impl/pom.xml")
                            .artifact("org.jboss.solder:solder-impl")                            
                            .resolveAs(GenericArchive.class));
        }
        else {
            ret.addAsLibraries(
                    DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../../settings.xml")
                            .loadMetadataFromPom("../../impl/pom.xml")
                            .artifact("org.jboss.solder:solder-impl")
                            .artifact("org.scribe:scribe")
                            .artifact("org.apache.commons:commons-lang3")
                            .artifact("org.codehaus.jackson:jackson-mapper-asl")
                            .artifact("com.google.guava:guava")
                            .resolveAs(GenericArchive.class));
        }
        return ret;
    }

    @Before
    public void init() {
        twitter.getSession().setAccessToken(
                new OAuthTokenScribe("334872715-u75bjYqWyQSYjFMnKeTDZUn8i0QAExjUQ4ENZXH3",
                        "08QG7HVqDjkr1oH1YfBRWmd0n8EG73CuzJgTjFI0sk"));
        twitter.initAccessToken();
    }

    @Test
    public void authorizationUrlTest() {
        Assert.assertTrue(twitter.getAuthorizationUrl().startsWith("http"));
    }

    @Test
    public void sendATweet() {
        Tweet tweet = (Tweet) twitter.updateStatus("Tweet sent from JUnit at " + new Date().toString());
        Assert.assertFalse(tweet.getId() == 0);

    }

    @Test
    public void searchUser() {
        init();
        List<TwitterProfile> res = twitter.searchForUsers("antoine");
        Assert.assertFalse(res.isEmpty());

    }

    @Test
    public void SuggestionCaegoriesNotEmpty() {
        List<SuggestionCategory> res = twitter.getSuggestionCategories();
        Assert.assertFalse(res.isEmpty());

    }
}
