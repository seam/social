/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.twitter.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.HasStatus;
import org.jboss.seam.social.Twitter;
import org.jboss.seam.social.oauth.OAuthProvider;
import org.jboss.seam.social.oauth.OAuthSession;
import org.jboss.seam.social.oauth.OAuthToken;
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
    @Twitter
    OAuthSession session;

    @Inject
    @Twitter
    TwitterService twitterService;

    @Inject
    @Twitter
    OAuthProvider provider;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        File beanFile = new File("src/test/resources/META-INF/beans.xml");
        if (!beanFile.exists())
            throw new FileNotFoundException();

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addAsLibraries(
                        ShrinkWrap.create(ZipImporter.class, "seam-social-api.jar")
                                .importFrom(new File("../../api/target/seam-social-api.jar")).as(JavaArchive.class),
                        ShrinkWrap.create(ZipImporter.class, "seam-social.jar")
                                .importFrom(new File("../../impl/target/seam-social.jar")).as(JavaArchive.class),
                        ShrinkWrap.create(ZipImporter.class, "seam-social-twitter.jar")
                                .importFrom(new File("target/seam-social-twitter.jar")).as(JavaArchive.class))
                .addAsResource(beanFile, "META-INF/beans.xml");

        if ("weld-ee-embedded-1.1".equals(System.getProperty("arquillian"))) {
            // Don't embed dependencies that are already in the CL in the embedded container from surefire
            ret.addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../../settings.xml")
                    .loadMetadataFromPom("../../impl/pom.xml").artifact("org.jboss.solder:solder-impl")
                    .resolveAs(GenericArchive.class));
        } else {
            ret.addAsLibraries(DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../../settings.xml")
                    .loadMetadataFromPom("../../impl/pom.xml").artifact("org.jboss.solder:solder-impl")
                    .artifact("org.scribe:scribe").artifact("org.apache.commons:commons-lang3")
                    .artifact("org.codehaus.jackson:jackson-mapper-asl").artifact("com.google.guava:guava")
                    .resolveAs(GenericArchive.class));
        }
        return ret;
    }

    @Before
    public void init() {
        OAuthToken token = new OAuthTokenScribe("334872715-u75bjYqWyQSYjFMnKeTDZUn8i0QAExjUQ4ENZXH3",
                "08QG7HVqDjkr1oH1YfBRWmd0n8EG73CuzJgTjFI0sk");
        session.setAccessToken(token);
        twitterService.initAccessToken();
    }

    @Test
    public void authorizationUrlTest() {
        Assert.assertTrue(twitterService.getAuthorizationUrl().startsWith("http"));
    }

    @Test
    public void sendATweet() {
        Tweet tweet = (Tweet) ((HasStatus) twitterService).updateStatus("Tweet sent from JUnit at " + new Date().toString());
        Assert.assertFalse(tweet.getId() == 0);

    }

    @Test
    public void searchUser() {
        List<TwitterProfile> res = ((TwitterService) twitterService).searchForUsers("antoine");
        Assert.assertFalse(res.isEmpty());

    }

    @Test
    public void SuggestionCaegoriesNotEmpty() {
        List<SuggestionCategory> res = ((TwitterService) twitterService).getSuggestionCategories();
        Assert.assertFalse(res.isEmpty());

    }
}
