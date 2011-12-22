package org.jboss.seam.social.twitter.jackson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.seam.social.URLUtils;
import org.jboss.seam.social.twitter.RateLimitStatus;
import org.jboss.seam.social.twitter.TwitterUserService;
import org.jboss.seam.social.twitter.model.ImageSize;
import org.jboss.seam.social.twitter.model.SuggestionCategory;
import org.jboss.seam.social.twitter.model.TwitterProfile;

import com.google.common.collect.Multimap;

/**
 * @author Antoine Sabot-Durand
 * @author Craig Walls
 */

public class TwitterUserServiceImpl extends TwitterBaseServiceImpl implements TwitterUserService {

    /**
     * 
     */
    private static final long serialVersionUID = 8769766657635858140L;

    /**
     * Typed list of TwitterProfile. This helps Jackson know which type to deserialize list contents into.
     * 
     * @author Craig Walls
     * @author Antoine Sabot-Durand
     */
    @SuppressWarnings("serial")
    static class TwitterProfileList extends ArrayList<TwitterProfile> {
    }

    @SuppressWarnings("serial")
    static class SuggestionCategoryList extends ArrayList<SuggestionCategory> {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class TwitterProfileUsersList {

        private final List<TwitterProfile> list;

        @JsonCreator
        public TwitterProfileUsersList(@JsonProperty("users") List<TwitterProfile> list) {
            this.list = list;
        }

        public List<TwitterProfile> getList() {
            return list;
        }
    }

    static final String GET_USER_PROFILE_URL = "users/show.json";
    static final String SEARCH_USER_URL = "users/search.json";
    static final String SUGGESTION_CATEGORIES = "users/suggestions.json";
    static final String LOOKUP = "users/lookup.json";
    static final String RATE_LIMIT_STATUS = "account/rate_limit_status.json";

    @Override
    public String getProfileId() {
        requireAuthorization();
        return getMyProfile().getId();
    }

    @Override
    public String getScreenName() {
        requireAuthorization();
        return getMyProfile().getScreenName();
    }

    @Override
    public TwitterProfile getUserProfile(String screenName) {
        return requestObject(buildUri(GET_USER_PROFILE_URL, "screen_name", screenName), TwitterProfile.class);
    }

    @Override
    public TwitterProfile getUserProfile(long userId) {
        return requestObject(buildUri(GET_USER_PROFILE_URL, "user_id", String.valueOf(userId)), TwitterProfile.class);
    }

    @Override
    public byte[] getUserProfileImage(String screenName) {
        return getUserProfileImage(screenName, ImageSize.NORMAL);
    }

    @Override
    public byte[] getUserProfileImage(String screenName, ImageSize size) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<TwitterProfile> getUsers(String... userIds) {
        String joinedIds = URLUtils.commaJoiner.join(userIds);
        return requestObject(buildUri(LOOKUP, "user_id", joinedIds), TwitterProfileList.class);
    }

    @Override
    public List<TwitterProfile> getUsersByName(String... screenNames) {
        String joinedScreenNames = URLUtils.commaJoiner.join(screenNames);
        return requestObject(buildUri(LOOKUP, "screen_name", joinedScreenNames), TwitterProfileList.class);
    }

    @Override
    public List<TwitterProfile> searchForUsers(String query) {
        return searchForUsers(query, 1, 20);
    }

    @Override
    public List<TwitterProfile> searchForUsers(String query, int page, int pageSize) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
        parameters.put("q", query);
        return requestObject(buildUri(SEARCH_USER_URL, parameters), TwitterProfileList.class);
    }

    @Override
    public List<SuggestionCategory> getSuggestionCategories() {
        return requestObject(buildUri(SUGGESTION_CATEGORIES), SuggestionCategoryList.class);
    }

    @Override
    public List<TwitterProfile> getSuggestions(String slug) {
        return requestObject(buildUri("users/suggestions/" + slug + ".json"), TwitterProfileUsersList.class).getList();
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return requestObject(buildUri(RATE_LIMIT_STATUS), RateLimitStatus.class);
    }

}
