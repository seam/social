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
package org.jboss.seam.social.oauth;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.jboss.seam.social.UserProfile;

/**
 * Instances of this interface contains data for a session connection to a given service.
 * 
 * @see OAuthBaseService
 * @author Antoine Sabot-Durand
 * 
 */
public interface OAuthSession extends Serializable {

    /**
     * @return the requestToken
     */
    public OAuthToken getRequestToken();

    /**
     * @param requestToken the requestToken to set
     */
    public void setRequestToken(OAuthToken requestToken);

    /**
     * @return the accessToken
     */
    public OAuthToken getAccessToken();

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(OAuthToken accessToken);

    /**
     * @return the verifier
     */
    public String getVerifier();

    /**
     * @param verifier the verifier to set
     */
    public void setVerifier(String verifier);

    public void setUserProfile(UserProfile userProfile);

    public UserProfile getUserProfile();

    public Annotation getServiceQualifier();

    /**
     * @return
     */
    public boolean isConnected();

    public String getName();
}