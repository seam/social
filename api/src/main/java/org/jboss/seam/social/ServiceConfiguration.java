/**
 * 
 */
package org.jboss.seam.social;

import org.jboss.seam.social.oauth.OAuthBaseService;

/**
 * @author antoine
 * 
 */
public interface ServiceConfiguration {

    public Class<? extends OAuthBaseService> getServiceClass();

}
