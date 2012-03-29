/**
 * 
 */
package org.jboss.seam.social.linkedin.cdi;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.seam.social.LinkedIn;
import org.jboss.seam.social.event.SocialEvent.Status;
import org.jboss.seam.social.event.StatusUpdated;
import org.jboss.seam.social.linkedin.NetworkUpdateService;
import org.jboss.seam.social.linkedin.model.NewShare;

@Decorator
/**
 * @author Antoine Sabot-Durand
 *
 */
public abstract class NetworkUpdateServiceDecorator implements NetworkUpdateService {

    /**
     * 
     */
    @Inject
    @Delegate
    @Any
    private NetworkUpdateService delegate;

    @Inject
    @LinkedIn
    private Event<StatusUpdated> statusUpdateEventProducer;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.jboss.seam.social.linkedin.api.services.NetworkUpdateService#share(org.jboss.seam.social.linkedin.model.NewShare)
     */
    @Override
    public String share(NewShare share) {
        String res = delegate.share(share);
        statusUpdateEventProducer.fire(new StatusUpdated(Status.SUCCESS, share.getComment(), res));
        return res;
    }

}
