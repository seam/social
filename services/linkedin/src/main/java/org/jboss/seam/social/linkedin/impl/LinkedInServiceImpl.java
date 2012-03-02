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
package org.jboss.seam.social.linkedin.impl;

import static org.jboss.seam.social.LinkedInLiteral.INSTANCE;

import java.lang.annotation.Annotation;

import org.jboss.seam.social.UserProfile;
import org.jboss.seam.social.linkedin.api.model.LinkedInProfile;
import org.jboss.seam.social.linkedin.api.model.LinkedInProfileFull;
import org.jboss.seam.social.linkedin.api.model.LinkedInProfiles;
import org.jboss.seam.social.linkedin.api.model.ProfileField;
import org.jboss.seam.social.linkedin.api.model.SearchParameters;
import org.jboss.seam.social.linkedin.api.services.LinkedInBaseService;
import org.jboss.seam.social.linkedin.api.services.ProfileService;
import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.oauth.OAuthServiceImpl;
import org.jboss.seam.social.rest.RestResponse;

/**
 * @author Antoine Sabot-Durand
 */
public class LinkedInServiceImpl extends OAuthServiceImpl implements LinkedInBaseService, ProfileService {

    private static final long serialVersionUID = -6718362913575146613L;

    static final String BASE_URL = "https://api.linkedin.com/v1/people/";

    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/1-9-05/images/buttons/linkedin_connect.png";

    static final String NETWORK_UPDATE_URL = "http://api.linkedin.com/v1/people/~/person-activities";

    @Override
    protected RestResponse sendSignedRequest(OAuthRequest request) {
        request.addHeader("x-li-format", "json");
        return super.sendSignedRequest(request);
    }

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
     * @see org.jboss.seam.social.oauth.OAuthService#getUserProfile()
     */
    @Override
    public UserProfile getMyProfile() {
        return getSession().getUserProfile();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.OAuthServiceBase#initMyProfile()
     */
    @Override
    protected void initMyProfile() {
        getSession().setUserProfile(getUserProfile());

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

    // ***** Profile Service

    static {
        StringBuffer b = new StringBuffer();
        b.append(BASE_URL).append("{id}:(");
        boolean first = true;
        for (ProfileField f : ProfileField.values()) {
            switch (f) {
                case CONNECTIONS:
                    break;
                default:
                    if (first) {
                        first = false;
                    } else {
                        b.append(',');
                    }
                    b.append(f);
            }
        }
        b.append(")?format=json");

        PROFILE_URL_FULL = b.toString();
    }

    static final String PROFILE_URL = BASE_URL
            + "{0}:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary)?format=json";

    static final String PROFILE_URL_FULL;

    static final String PEOPLE_SEARCH_URL = "https://api.linkedin.com/v1/people-search:(people:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url,summary,api-standard-profile-request))?{&keywords}{&first-name}{&last-name}{&company-name}{&current-company}{&title}{&current-title}{&school-name}{&current-school}{&country-code}{&postal-code}{&distance}{&start}{&count}{&sort}";

    @Override
    public String getProfileId() {
        return getUserProfile().getId();
    }

    @Override
    public String getProfileUrl() {
        return getUserProfile().getPublicProfileUrl();
    }

    @Override
    public LinkedInProfile getUserProfile() {
        return getForObject(PROFILE_URL, LinkedInProfile.class, "~");
    }

    @Override
    public LinkedInProfileFull getUserProfileFull() {
        return getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, "~");
    }

    @Override
    public LinkedInProfile getProfileById(String id) {
        return getForObject(PROFILE_URL, LinkedInProfile.class, id);
    }

    @Override
    public LinkedInProfile getProfileByPublicUrl(String url) {
        return getForObject(PROFILE_URL, LinkedInProfile.class, url);
    }

    @Override
    public LinkedInProfileFull getProfileFullById(String id) {
        return getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, id);
    }

    @Override
    public LinkedInProfileFull getProfileFullByPublicUrl(String url) {
        return getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, url);
    }

    @Override
    public LinkedInProfiles search(SearchParameters parameters) {
        // TODO : create a linkedIn Search Api 2.0 compatible code
        throw new UnsupportedOperationException("Searc not implemented yet");

        /*
         * String uri = StringUtils.processPlaceHolders(PEOPLE_SEARCH_URL, parameters); JsonNode node =
         * jsonService.mapToObject(expand(PEOPLE_SEARCH_URL, parameters), JsonNode.class); try { return
         * objectMapper.readValue(node.path("people"), LinkedInProfiles.class); } catch (Exception e) { throw new
         * RuntimeException(e); }
         */

    }

}
