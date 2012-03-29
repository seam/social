/**
 * 
 */
package org.jboss.seam.social.facebook.cdi;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.seam.social.Facebook;
import org.jboss.seam.social.event.SocialEvent.Status;
import org.jboss.seam.social.event.StatusUpdated;
import org.jboss.seam.social.facebook.service.FeedService;

@Decorator
/**
 * @author Antoine Sabot-Durand
 *
 */
public abstract class FeedServiceDecorator implements FeedService {

    /**
     * 
     */
    @Inject
    @Delegate
    @Any
    private FeedService delegate;

    @Inject
    @Facebook
    private Event<StatusUpdated> statusUpdateEventProducer;

    @Override
    public String updateStatus(String message) {
        String res = delegate.updateStatus(message);
        statusUpdateEventProducer.fire(new StatusUpdated(Status.SUCCESS, message, res));
        return res;

    }

}
