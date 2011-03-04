/**
 * 
 */
package org.jboss.seam.social.linkedin.model;

import org.jboss.seam.social.oauth.OAuthUser;

/**
 * @author antoine
 * 
 */
public interface LinkedInProfile extends OAuthUser
{

   public void setHeadline(String headline);

   public String getHeadline();

   public void setLastName(String lastName);

   public String getLastName();

   public void setFirstName(String firstName);

}
