/**
 * 
 */
package org.jboss.seam.social.linkedin;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.rest.RestResponse;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Decorator
public abstract class OAuthServiceLinkedInDecorator implements OAuthService {

    @Inject
    @Delegate
    @Any
    private OAuthService delegate;

    @Override
    public RestResponse sendSignedRequest(OAuthRequest request) {
        request.addHeader("x-li-format", "json");
        return delegate.sendSignedRequest(request);
    }

    @Override
    public String postForLocation(String uri, Object toPost, Object... urlParams) {
        System.out.println("in the decorator");
        return delegate.postForLocation(uri, toPost, urlParams);
    }

}