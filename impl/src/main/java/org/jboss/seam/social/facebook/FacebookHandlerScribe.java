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

package org.jboss.seam.social.facebook;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.facebook.model.UserJackson;
import org.jboss.seam.social.oauth.HttpResponse;
import org.jboss.seam.social.oauth.JsonMapper;
import org.jboss.seam.social.oauth.OAuth2ServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.oauth.UserProfile;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Named("facebookHdl")
@SessionScoped
public class FacebookHandlerScribe extends OAuth2ServiceHandlerScribe implements FacebookHandler {

    static final String USER_PROFILE_URL = "https://graph.facebook.com/me";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/facebook_connect.png";
    static final String TYPE = "Facebook";
    static final String NETWORK_UPDATE_URL = "";
    static final Class<? extends Api> API_CLASS = FacebookApi.class;

    @Inject
    private JsonMapper jsonMapper;

    @Override
    @Inject
    public void setSettings(@Facebook OAuthServiceSettings settings) {
        super.setSettings(settings);

    }

    /**
    * 
    */
    private static final long serialVersionUID = -1388022067022793683L;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getServiceLogo()
     */
    @Override
    public String getServiceLogo() {
        return LOGO_URL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getUser()
     */
    @Override
    public UserProfile getUser() {
        if (userProfile == null) {
            HttpResponse resp = sendSignedRequest(RestVerb.GET, USER_PROFILE_URL);
            userProfile = jsonMapper.readValue(resp, UserJackson.class);
        }
        return userProfile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getType()
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus()
     */
    @Override
    public Object updateStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus(java.lang.String)
     */
    @Override
    public Object updateStatus(String message) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getApiClass()
     */
    @Override
    protected Class<? extends Api> getApiClass() {
        return API_CLASS;
    }

}
