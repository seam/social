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

package org.jboss.seam.social.facebook.jackson;

import static org.jboss.seam.social.FacebookLiteral.INSTANCE;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import org.jboss.seam.social.UserProfile;
import org.jboss.seam.social.facebook.FacebookService;
import org.jboss.seam.social.facebook.model.UserJackson;
import org.jboss.seam.social.oauth.OAuthServiceImpl;
import org.jboss.seam.social.rest.RestResponse;
import org.jboss.seam.social.rest.RestVerb;
import org.jboss.solder.logging.Logger;

/**
 * @author Antoine Sabot-Durand
 */
public class FacebookServiceJackson extends OAuthServiceImpl implements FacebookService {

    static final String USER_PROFILE_URL = "https://graph.facebook.com/me";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/facebook_connect.png";
    static final String STATUS_UPDATE_URL = "https://graph.facebook.com/me/feed";
    private static final String VERIFIER_PARAM_NAME = "code";

    @Inject
    private Logger log;
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
        return getSession().getUserProfile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus(java.lang.String)
     */

    public Object updateStatus(String message) {
        RestResponse resp = sendSignedRequest(RestVerb.POST, STATUS_UPDATE_URL, "message", message);
        log.debugf("Update staut is %s", message);
        log.debugf("Response is : %s", resp.getBody());
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.OAuthServiceBase#initMyProfile()
     */
    @Override
    protected void initMyProfile() {
        getSession().setUserProfile(
                jsonService.mapToObject(sendSignedRequest(RestVerb.GET, USER_PROFILE_URL), UserJackson.class));

    }

    @Override
    public String getVerifierParamName() {
        return VERIFIER_PARAM_NAME;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.rest.RestService#getQualifier()
     */
    @Override
    public Annotation getQualifier() {
        return INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthServiceImpl#getApiRootUrl()
     */
    @Override
    public String getApiRootUrl() {
        // TODO Auto-generated method stub
        return null;
    }

}
