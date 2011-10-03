package org.jboss.seam.social.test;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.HasStatus;
import org.jboss.seam.social.HttpResponse;
import org.jboss.seam.social.JsonMapper;
import org.jboss.seam.social.OAuthServiceBase;
import org.jboss.seam.social.OAuthServiceSettingsImpl;
import org.jboss.seam.social.OAuthSessionSettingsImpl;
import org.jboss.seam.social.RestVerb;
import org.jboss.seam.social.UserProfile;
import org.jboss.seam.social.cdi.RelatedTo;
import org.jboss.seam.social.oauth.ConfigureOAuth;
import org.jboss.seam.social.oauth.OAuthProvider;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.OAuthSessionSettings;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.seam.social.scribe.OAuthProviderScribe;
import org.jboss.seam.social.scribe.OAuthTokenScribe;
import org.jboss.seam.social.twitter.Twitter;
import org.jboss.seam.social.twitter.jackson.TwitterJackson;
import org.jboss.seam.social.twitter.model.SuggestionCategory;
import org.jboss.seam.social.twitter.model.Tweet;
import org.jboss.seam.social.twitter.model.TwitterProfile;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
        Archive<?> ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(TwitterJackson.class, Twitter.class, OAuthService.class, OAuthServiceBase.class,
                        OAuthServiceSettings.class, OAuthServiceSettingsImpl.class, OAuthSessionSettings.class,
                        OAuthSessionSettingsImpl.class, RelatedTo.class, JsonMapper.class, HasStatus.class,
                        OAuthProvider.class, OAuthProviderScribe.class, OAuthToken.class, RestVerb.class, HttpResponse.class,
                        UserProfile.class, Tweet.class, OAuthTokenScribe.class)

                .addAsLibraries(
                        DependencyResolvers.use(MavenDependencyResolver.class)
                                .configureFrom("../../settings.xml")
                                .loadReposFromPom("pom.xml")
                                .artifact("org.jboss.solder:solder-impl").artifact("org.scribe:scribe")
                                .resolveAs(GenericArchive.class));
        // ret.as(ZipExporter.class).exportTo(new File("mytest.war"), true);

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
