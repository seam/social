/**
 * 
 */
package org.jboss.seam.social;

import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthSession;

/**
 * @author antoine
 * 
 */
public interface SocialNetworkServicesHub extends OAuthServiceAware, QualifierAware {

    /**
     * @return
     */
    public UserProfile getMyProfile();

    /**
     * @return the session settings of the given service
     */
    public OAuthSession getSession();

    /**
     * Close connexion if needed
     */
    public void resetConnection();

    /**
     * @return
     */
    boolean isConnected();

    /**
     * @param service
     */
    public void configureService(OAuthService service);

    public String getVerifierParamName();

}
