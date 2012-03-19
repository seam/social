/**
 * 
 */
package org.jboss.seam.social;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthSession;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public abstract class AbstractOAuthServiceAwareImpl implements OAuthServiceAware, QualifierAware {

    @Inject
    @Any
    protected Instance<OAuthService> serviceInstances;

    @Override
    public OAuthService getService() {
        return serviceInstances.select(getQualifier()).get();
    }

    public OAuthSession getSession() {
        return getService().getSession();
    }

}