/**
 * 
 */
package org.jboss.seam.social.linkedin;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.jboss.seam.social.LinkedIn;
import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.rest.RestResponse;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Decorator
public abstract class OAuthServiceLinkedInDecorator implements OAuthService {

    /**
     * 
     */
    private static final long serialVersionUID = -2865190923795248493L;
    @Inject
    @Delegate
    @LinkedIn
    private OAuthService delegate;

    @Override
    public RestResponse sendSignedRequest(OAuthRequest request) {
        request.addHeader("x-li-format", "json");
        return delegate.sendSignedRequest(request);
    }

}