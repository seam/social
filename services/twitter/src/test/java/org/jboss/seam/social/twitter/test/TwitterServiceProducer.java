/**
 * 
 */
package org.jboss.seam.social.twitter.test;

import javax.enterprise.inject.Produces;

import org.jboss.seam.social.Twitter;
import org.jboss.seam.social.oauth.OAuthApplication;
import org.jboss.seam.social.twitter.TwitterBaseService;

/**
 * @author antoine
 * 
 */
public class TwitterServiceProducer {

    @OAuthApplication(apiKey = "FQzlQC49UhvbMZoxUIvHTQ", apiSecret = "VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k")
    @Twitter
    @Produces
    TwitterBaseService twitterServiceProducer(TwitterBaseService ts) {
        return ts;
    }

}
