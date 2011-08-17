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

/**
 * @author antoine
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
 
    

    /**
     * 
     */
    private String status;

   

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getRequestToken()
     */
    @Override
    public OAuthToken getRequestToken() {
        return requestToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setRequestToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setRequestToken(OAuthToken requestToken) {
        this.requestToken = requestToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getAccessToken()
     */
    @Override
    public OAuthToken getAccessToken() {
        return accessToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setAccessToken(org.jboss.seam.social.oauth.OAuthTokenScribe)
     */
    @Override
    public void setAccessToken(OAuthToken accessToken) {
        this.accessToken = accessToken;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#getVerifier()
     */
    @Override
    public String getVerifier() {
        return verifier;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthSessionSettings#setVerifier(java.lang.String)
     */
    @Override
    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }


   
}