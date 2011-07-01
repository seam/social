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
package org.jboss.seam.social.core;

import org.jboss.seam.social.core.OAuthSessionSettings;
import org.jboss.seam.social.core.OAuthToken;
import org.jboss.seam.social.core.UserProfile;

/**
 * @author  antoine
 */
public class OAuthSessionSettingsImpl implements OAuthSessionSettings {
    /**
     * 
     */
    private OAuthToken requestToken;
    /**
     * 
     */
    private OAuthToken accessToken;
    /**
     * 
     */
    private String verifier;
    /**
     * 
     */
    private Boolean connected;
    /**
     * 
     */
    private UserProfile userProfile;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userProfile == null) ? 0 : userProfile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OAuthSessionSettingsImpl other = (OAuthSessionSettingsImpl) obj;
        if (userProfile == null) {
            if (other.userProfile != null)
                return false;
        } else if (!userProfile.equals(other.userProfile))
            return false;
        return true;
    }

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    public OAuthSessionSettingsImpl() {
        this.connected = Boolean.FALSE;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getRequestToken()
     */
    @Override
    public OAuthToken getRequestToken() {
        return requestToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setRequestToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setRequestToken(OAuthToken requestToken) {
        this.requestToken = requestToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getAccessToken()
     */
    @Override
    public OAuthToken getAccessToken() {
        return accessToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setAccessToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setAccessToken(OAuthToken accessToken) {
        this.accessToken = accessToken;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getVerifier()
     */
    @Override
    public String getVerifier() {
        return verifier;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setVerifier(java.lang.String)
     */
    @Override
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#isConnected()
     */
    @Override
    public Boolean isConnected() {
        return connected;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setConnected(java.lang.Boolean)
     */
    @Override
    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getUserProfile()
     */
    @Override
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setUserProfile(org.jboss.seam.social.oauth.UserProfile)
     */
    @Override
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getStatus()
     */
    @Override
    public String getStatus() {
        return status;
    }

    /* (non-Javadoc)
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setStatus(java.lang.String)
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}