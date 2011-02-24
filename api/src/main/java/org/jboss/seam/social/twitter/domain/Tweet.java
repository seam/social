/**
 * 
 */
package org.jboss.seam.social.twitter.domain;

/**
 * @author antoine
 *
 */
public interface Tweet
{

   public long getId();

   public void setId(long id);

   public String getText();

   public void setText(String text);

   public String getCreatedAt();

   public void setCreatedAt(String createdAt);

   public String getFromUser();

   public void setFromUser(String fromUser);

   public String getProfileImageUrl();

   public void setProfileImageUrl(String profileImageUrl);

   public Long getToUserId();

   public void setToUserId(Long toUserId);

   public long getFromUserId();

   public void setFromUserId(long fromUserId);

   public String getLanguageCode();

   public void setLanguageCode(String languageCode);

   public String getSource();

   public void setSource(String source);

   public String getFromUserIdStr();

   public void setFromUserIdStr(String fromUserIdStr);

}