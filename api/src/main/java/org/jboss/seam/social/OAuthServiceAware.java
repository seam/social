/**
 * 
 */
package org.jboss.seam.social;

import org.jboss.seam.social.oauth.OAuthService;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public interface OAuthServiceAware {

    /**
     * @return
     */
    OAuthService getService();

}