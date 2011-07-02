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
package org.jboss.seam.social.twitter.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Antoine Sabot-Durand
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetJackson implements Tweet {

    private long id;

    private String text;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "from_user")
    private String fromUser;

    @JsonProperty(value = "profile_image_url")
    private String profileImageUrl;

    @JsonProperty(value = "to_user_id")
    private Long toUserId;

    @JsonProperty(value = "from_user_id")
    private long fromUserId;

    @JsonProperty(value = "language_code")
    private String languageCode;

    private String source;

    @JsonProperty(value = "from_user_id_str")
    private String fromUserIdStr;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getId()
     */
    @Override
    public long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getText()
     */
    @Override
    public String getText() {
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getCreatedAt()
     */
    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setCreatedAt(java.lang.String)
     */
    @Override
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getFromUser()
     */
    @Override
    public String getFromUser() {
        return fromUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setFromUser(java.lang.String)
     */
    @Override
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getProfileImageUrl()
     */
    @Override
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setProfileImageUrl(java.lang.String)
     */
    @Override
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getToUserId()
     */
    @Override
    public Long getToUserId() {
        return toUserId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setToUserId(java.lang.Long)
     */
    @Override
    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getFromUserId()
     */
    @Override
    public long getFromUserId() {
        return fromUserId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setFromUserId(long)
     */
    @Override
    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getLanguageCode()
     */
    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setLanguageCode(java.lang.String)
     */
    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getSource()
     */
    @Override
    public String getSource() {
        return source;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setSource(java.lang.String)
     */
    @Override
    public void setSource(String source) {
        this.source = source;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#getFromUserIdStr()
     */
    @Override
    public String getFromUserIdStr() {
        return fromUserIdStr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Tweet#setFromUserIdStr(java.lang.String)
     */
    @Override
    public void setFromUserIdStr(String fromUserIdStr) {
        this.fromUserIdStr = fromUserIdStr;
    }

}
