/**
 * 
 */
package org.jboss.seam.social;

import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.social.event.OAuthComplete;
import org.jboss.seam.social.event.SocialEvent.Status;
import org.jboss.seam.social.twitter.TwitterBaseService;
import org.jboss.seam.social.twitter.impl.TwitterUserServiceImpl;

/**
 * @author Antoine Sabot-Durand
 * 
 */

// @Twitter
public class TwitterServicesHub extends AbstractSocialNetworkServicesHub {

    @Inject
    Instance<TwitterBaseService> services;

    @Override
    public Annotation getQualifier() {
        return TwitterLiteral.INSTANCE;
    }

    @Override
    protected void initMyProfile(@Observes @Twitter OAuthComplete oauthComplete) {
        if (oauthComplete.getStatus() == Status.SUCCESS)
            oauthComplete.getEventData().setUserProfile(services.select(TwitterUserServiceImpl.class).get().getUserProfile());

    }

}
