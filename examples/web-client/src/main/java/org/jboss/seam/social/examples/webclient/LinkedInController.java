/**
 * 
 */
package org.jboss.seam.social.examples.webclient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.LinkedIn;
import org.jboss.seam.social.event.SocialEvent;
import org.jboss.seam.social.event.StatusUpdated;
import org.jboss.seam.social.linkedin.NetworkUpdateService;
import org.jboss.seam.social.linkedin.model.NewShare;
import org.jboss.seam.social.linkedin.model.NewShare.NewShareVisibility;
import org.jboss.seam.social.linkedin.model.NewShare.NewShareVisibilityCode;
import org.jboss.solder.logging.Logger;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Named
@RequestScoped
public class LinkedInController {

    @Produces
    @Named
    private NewShare linkedInShare;

    @Inject
    private NetworkUpdateService updateService;

    @Inject
    Logger log;

    @PostConstruct
    public void init() {
        linkedInShare = new NewShare("", null, new NewShareVisibility(NewShareVisibilityCode.CONNECTIONS_ONLY));
    }

    public String sendUpdate() {
        updateService.share(linkedInShare);
        return "ok";
    }

    protected void statusUpdateObserver(@Observes @LinkedIn StatusUpdated statusUpdate) {
        if (statusUpdate.getStatus().equals(SocialEvent.Status.SUCCESS)) {
            log.debugf("Status update with : %s ", statusUpdate.getMessage());
            init();
        }
    }
}
