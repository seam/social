/**
 * 
 */
package org.jboss.seam.social.event;

import org.jboss.seam.social.oauth.OAuthSession;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class OAuthComplete extends SocialEvent<OAuthSession> {

    public OAuthComplete(Status status, String message, OAuthSession payload) {
        super(status, message, payload);
    }

}
