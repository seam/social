/**
 * 
 */
package org.jboss.seam.social.linkedin.impl;

import org.jboss.seam.social.LinkedInBaseService;
import org.jboss.seam.social.linkedin.ProfileService;
import org.jboss.seam.social.linkedin.model.LinkedInProfile;
import org.jboss.seam.social.linkedin.model.LinkedInProfileFull;
import org.jboss.seam.social.linkedin.model.LinkedInProfiles;
import org.jboss.seam.social.linkedin.model.ProfileField;
import org.jboss.seam.social.linkedin.model.SearchParameters;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class ProfileServiceImpl extends LinkedInBaseService implements ProfileService {

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
        return getService().getForObject(PROFILE_URL, LinkedInProfile.class, "~");
    }

    @Override
    public LinkedInProfileFull getUserProfileFull() {
        return getService().getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, "~");
    }

    @Override
    public LinkedInProfile getProfileById(String id) {
        return getService().getForObject(PROFILE_URL, LinkedInProfile.class, id);
    }

    @Override
    public LinkedInProfile getProfileByPublicUrl(String url) {
        return getService().getForObject(PROFILE_URL, LinkedInProfile.class, url);
    }

    @Override
    public LinkedInProfileFull getProfileFullById(String id) {
        return getService().getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, id);
    }

    @Override
    public LinkedInProfileFull getProfileFullByPublicUrl(String url) {
        return getService().getForObject(PROFILE_URL_FULL, LinkedInProfileFull.class, url);
    }

    @Override
    public LinkedInProfiles search(SearchParameters parameters) {
        // TODO : create a linkedIn Search Api 2.0 compatible code
        throw new UnsupportedOperationException("Search not implemented yet");

        /*
         * String uri = StringUtils.processPlaceHolders(PEOPLE_SEARCH_URL, parameters); JsonNode node =
         * jsonService.mapToObject(expand(PEOPLE_SEARCH_URL, parameters), JsonNode.class); try { return
         * objectMapper.readValue(node.path("people"), LinkedInProfiles.class); } catch (Exception e) { throw new
         * RuntimeException(e); }
         */

    }

}
