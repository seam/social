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

/**
 * Implementation of this interface contains a Tweet
 *
 * @author Antoine Sabot-Durand
 */
public interface Tweet {

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
