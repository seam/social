/**
 * 
 */
package org.jboss.seam.social.examples.webclient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.linkedin.api.model.NewShare;
import org.jboss.seam.social.linkedin.api.model.NewShare.NewShareVisibility;
import org.jboss.seam.social.linkedin.api.model.NewShare.NewShareVisibilityCode;
import org.jboss.seam.social.linkedin.api.services.NetworkUpdateService;

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

    @PostConstruct
    public void init() {
        linkedInShare = new NewShare("", null, new NewShareVisibility(NewShareVisibilityCode.CONNECTIONS_ONLY));
    }

    public String sendUpdate() {
        updateService.share(linkedInShare);
        return "ok";
    }
}
