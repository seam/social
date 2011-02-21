/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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