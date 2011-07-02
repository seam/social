/**
 *
 */
package org.jboss.seam.social.twitter.model;

import org.jboss.seam.social.core.UserProfile;
import org.jboss.seam.social.twitter.Twitter;

/**
 * Implementation of this interface contains information about TwitterRelated Credential of the current user It is returned by
 * {@link Twitter#verifyCrendentials()}
 * 
 * @author Antoine Sabot-Durand
 */
public interface Credential extends UserProfile {

    /**
     * @return the TwitterRelated screen Name
     */
    public String getScreenName();

}
