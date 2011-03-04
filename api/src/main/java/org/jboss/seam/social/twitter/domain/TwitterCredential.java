/**
 * 
 */
package org.jboss.seam.social.twitter.domain;

import org.jboss.seam.social.oauth.OAuthUser;
import org.jboss.seam.social.twitter.TwitterHandler;

/**
*
* Implementation of this interface contains information about Twitter Credential of the current user
* It is returned by {@link TwitterHandler#verifyCrendentials()}
* 
* @author Antoine Sabot-Durand
*
*/
public interface TwitterCredential extends OAuthUser
{


   public String getName();

   public void setName(String name);

   public String getScreenName();

   public void setScreenName(String screenName);

   public String getProfileImageUrl();

   public void setProfileImageUrl(String profileImageUrl);
   
}
