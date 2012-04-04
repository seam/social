/**
 * 
 */
package org.jboss.seam.social.twitter.test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.seam.social.SocialNetworkServicesHub;
import org.jboss.seam.social.Twitter;
import org.jboss.seam.social.TwitterServicesHub;
import org.jboss.seam.social.oauth.OAuthApplication;

/**
 * @author antoine
 * 
 */
public class TwitterServiceProducer {

    @Twitter
    @ApplicationScoped
    @OAuthApplication(apiKey = "FQzlQC49UhvbMZoxUIvHTQ", apiSecret = "VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k")
    @Produces
    public SocialNetworkServicesHub OAuthSettinsProducer(TwitterServicesHub service) {
        return service;
    }

}
