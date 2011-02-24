/**
 * 
 */
package org.jboss.seam.social.twitter.domain;

/**
 * @author antoine
 *
 */
public interface Credential
{

   public String getName();

   public void setName(String name);

   public String getScreenName();

   public void setScreenName(String screenName);

   public String getProfileImageUrl();

   public void setProfileImageUrl(String profileImageUrl);

}