/**
 * 
 */
package org.jboss.seam.social;

import org.jboss.seam.social.oauth.OAuthService;

/**
 * @author antoine
 * 
 */
public interface ServiceConfiguration {

    public Class<? extends OAuthService> getServiceClass();

}
