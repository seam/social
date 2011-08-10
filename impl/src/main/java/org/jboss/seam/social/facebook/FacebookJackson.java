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

import javax.inject.Inject;

import org.codehaus.jackson.map.Module;
import org.jboss.logging.Logger;
import org.jboss.seam.social.core.HttpResponse;
import org.jboss.seam.social.core.OAuthServiceJackson;
import org.jboss.seam.social.core.RestVerb;
import org.jboss.seam.social.core.UserProfile;
import org.jboss.seam.social.facebook.model.UserJackson;

/**
 * @author Antoine Sabot-Durand
 */

public class FacebookJackson extends OAuthServiceJackson implements Facebook {

    static final String USER_PROFILE_URL = "https://graph.facebook.com/me";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/facebook_connect.png";
    static final String STATUS_UPDATE_URL = "https://graph.facebook.com/me/feed";
    private static final String VERIFIER_PARAM_NAME = "code";

    @Inject
    private Logger log;

    private UserJackson myProfile;

    /**
     *
     */
    private static final long serialVersionUID = -1388022067022793683L;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthService#getServiceLogo()
     */
    @Override
    public String getServiceLogo() {
        return LOGO_URL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthService#getUser()
     */
    @Override
    public UserProfile getMyProfile() {
        return myProfile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthService#getType()
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
        return updateStatus(getStatus());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus(java.lang.String)
     */
    @Override
    public Object updateStatus(String message) {
        HttpResponse resp = sendSignedRequest(RestVerb.POST, STATUS_UPDATE_URL, "message", message);
        log.debugf("Update staut is %s", message);
        setStatus("");
        log.debugf("Response is : %s", resp.getBody());
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.core.OAuthServiceBase#initMyProfile()
     */
    @Override
    protected void initMyProfile() {
        HttpResponse resp = sendSignedRequest(RestVerb.GET, USER_PROFILE_URL);
        myProfile = jsonMapper.readValue(resp, UserJackson.class);

    }

    @Override
    public String getVerifierParamName() {
        return VERIFIER_PARAM_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.core.OAuthServiceJackson#getJacksonModule()
     */
    @Override
    protected Module getJacksonModule() {
        // TODO Auto-generated method stub
        return null;
    }

}
