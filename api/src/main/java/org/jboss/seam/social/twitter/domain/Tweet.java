/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.social.twitter.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Tweet
{
   
   private long id;
   

   private String text;
   
   @JsonProperty(value="created_at")
   private String createdAt;
   
   @JsonProperty(value="from_user")
   private String fromUser;
   
   @JsonProperty(value="profile_image_url")
   private String profileImageUrl;
   
   @JsonProperty(value="to_user_id")
   private Long toUserId;
   
   @JsonProperty(value="from_user_id")
   private long fromUserId;
   
   @JsonProperty(value="language_code")
   private String languageCode;
   
   private String source;

   @JsonProperty(value="from_user_id_str")
   private String fromUserIdStr;

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
   }

   public String getText()
   {
      return text;
   }

   public void setText(String text)
   {
      this.text = text;
   }

   public String getCreatedAt()
   {
      return createdAt;
   }

   public void setCreatedAt(String createdAt)
   {
      this.createdAt = createdAt;
   }

   public String getFromUser()
   {
      return fromUser;
   }

   public void setFromUser(String fromUser)
   {
      this.fromUser = fromUser;
   }

   public String getProfileImageUrl()
   {
      return profileImageUrl;
   }

   public void setProfileImageUrl(String profileImageUrl)
   {
      this.profileImageUrl = profileImageUrl;
   }

   public Long getToUserId()
   {
      return toUserId;
   }

   public void setToUserId(Long toUserId)
   {
      this.toUserId = toUserId;
   }

   public long getFromUserId()
   {
      return fromUserId;
   }

   public void setFromUserId(long fromUserId)
   {
      this.fromUserId = fromUserId;
   }

   public String getLanguageCode()
   {
      return languageCode;
   }

   public void setLanguageCode(String languageCode)
   {
      this.languageCode = languageCode;
   }

   public String getSource()
   {
      return source;
   }

   public void setSource(String source)
   {
      this.source = source;
   }

   public String getFromUserIdStr()
   {
      return fromUserIdStr;
   }

   public void setFromUserIdStr(String fromUserIdStr)
   {
      this.fromUserIdStr = fromUserIdStr;
   }
   
   
}