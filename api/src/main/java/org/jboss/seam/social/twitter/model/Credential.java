/**
 *
 */
package org.jboss.seam.social.twitter.model;

import org.jboss.seam.social.oauth.UserProfile;
import org.jboss.seam.social.twitter.Twitter;

/**
 * Implementation of this interface contains information about SetTwitter Credential of the current user It is returned by
 * {@link Twitter#verifyCrendentials()}
 *
 * @author Antoine Sabot-Durand
 */
public interface Credential extends UserProfile {

    /**
     * @return the SetTwitter screen Name
     */
    public String getScreenName();

}
