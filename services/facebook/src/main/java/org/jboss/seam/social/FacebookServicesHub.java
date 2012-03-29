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
import org.jboss.seam.social.facebook.impl.UserServiceImpl;
import org.jboss.solder.logging.Logger;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class FacebookServicesHub extends AbstractSocialNetworkServicesHub {

    @Inject
    Instance<FacebookBaseService> services;

    @Inject
    Logger log;

    @Override
    public Annotation getQualifier() {
        return FacebookLiteral.INSTANCE;
    }

    public void initMyProfile(@Observes @Facebook OAuthComplete oauthComplete) {
        log.debug("Initializing Facebook profile");
        if (oauthComplete.getStatus() == Status.SUCCESS)
            oauthComplete.getEventData().setUserProfile(services.select(UserServiceImpl.class).get().getUserProfile());
    }

    @Override
    public String getVerifierParamName() {
        return "code";
    }

}
