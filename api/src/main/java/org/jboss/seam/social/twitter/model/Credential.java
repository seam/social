/**
 * 
 */
package org.jboss.seam.social.twitter.model;

import org.jboss.seam.social.oauth.UserProfile;
import org.jboss.seam.social.twitter.TwitterHandler;

/**
*
* Implementation of this interface contains information about Twitter Credential of the current user
* It is returned by {@link TwitterHandler#verifyCrendentials()}
* 
* @author Antoine Sabot-Durand
*
*/
public interface Credential extends UserProfile
{


   public String getName();

   public String getScreenName();

   
}