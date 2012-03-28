/**
 * 
 */
package org.jboss.seam.social.linkedin.impl;

import static java.util.Collections.singletonMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.jboss.seam.social.linkedin.LinkedInBaseService;
import org.jboss.seam.social.linkedin.api.model.Comment;
import org.jboss.seam.social.linkedin.api.model.Comments;
import org.jboss.seam.social.linkedin.api.model.CurrentShare;
import org.jboss.seam.social.linkedin.api.model.Likes;
import org.jboss.seam.social.linkedin.api.model.LinkedInNetworkUpdate;
import org.jboss.seam.social.linkedin.api.model.LinkedInNetworkUpdates;
import org.jboss.seam.social.linkedin.api.model.LinkedInProfile;
import org.jboss.seam.social.linkedin.api.model.NetworkUpdateParameters;
import org.jboss.seam.social.linkedin.api.model.NewShare;
import org.jboss.seam.social.linkedin.api.model.UpdateContentShare;
import org.jboss.seam.social.linkedin.api.model.UpdateTypeInput;
import org.jboss.seam.social.linkedin.api.services.NetworkUpdateService;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Named
public class NetworkUpdateServiceImpl extends LinkedInBaseService implements NetworkUpdateService {

    static {
        /*
         * The UPDATE_TYPE_ALL is required because by default the linked in Network Updates API does not return VIRL (Viral)
         * Updates such as User Liking or Commenting on another Users post
         */

        StringBuffer b = new StringBuffer();
        for (UpdateTypeInput t : UpdateTypeInput.values()) {
            b.append("&type=").append(t);
        }
        UPDATE_TYPE_ALL_STRING = b.toString();
    }

    @Override
    public List<LinkedInNetworkUpdate> getNetworkUpdates() {
        NetworkUpdateParameters parameters = new NetworkUpdateParameters(null, false, DEFAULT_START, DEFAULT_COUNT, null, null,
                true, false, Collections.<UpdateTypeInput> emptyList());
        return getNetworkUpdates(parameters);
    }

    @Override
    public List<LinkedInNetworkUpdate> getNetworkUpdates(int start, int count) {
        NetworkUpdateParameters parameters = new NetworkUpdateParameters(null, false, start, count, null, null, true, false,
                Collections.<UpdateTypeInput> emptyList());
        return getNetworkUpdates(parameters);
    }

    @Override
    public List<LinkedInNetworkUpdate> getNetworkUpdates(NetworkUpdateParameters parameters) {
        return getNetworkUpdates(parameters, LinkedInNetworkUpdates.class).getUpdates();
    }

    @Override
    public List<Comment> getNetworkUpdateComments(String updateKey) {
        return getService().getForObject(UPDATE_COMMENTS_URL, Comments.class, updateKey).getComments();
    }

    @Override
    public void createNetworkUpdate(String update) {
        Map<String, String> activity = new HashMap<String, String>();
        activity.put("contentType", "linkedin-html");
        activity.put("body", update);
        getService().put(ACTIVITY_URL, activity);
    }

    @Override
    public CurrentShare getCurrentShare() {
        return getService().getForObject(CURRENT_SHARE_URL, UpdateContentShare.class).getCurrentShare();
    }

    @Override
    public String share(NewShare share) {
        return getService().postForLocation(SHARE_URL, share);
    }

    @Override
    public void likeNetworkUpdate(String updateKey) {
        getService().put(UPDATE_IS_LIKED_URL, Boolean.TRUE, updateKey);
    }

    @Override
    public void unlikeNetworkUpdate(String updateKey) {
        getService().put(UPDATE_IS_LIKED_URL, Boolean.FALSE, updateKey);
    }

    @Override
    public void commentOnNetworkUpdate(String updateKey, String comment) {
        getService().put(UPDATE_COMMENTS_URL, singletonMap("comment", comment), updateKey);
    }

    @Override
    public List<LinkedInProfile> getNetworkUpdateLikes(String updateKey) {
        return getService().getForObject(UPDATE_LIKES_URL, Likes.class, updateKey).getLikes();
    }

    @Override
    public String getNetworkUpdatesJson(NetworkUpdateParameters parameters) {
        return getNetworkUpdates(parameters, String.class);
    }

    private <T> T getNetworkUpdates(NetworkUpdateParameters parameters, Class<T> responseType) {

        return getService().getForObject(buildUri(UPDATES_URL, parameters), responseType);
    }

    public enum networkUpdatesFields {

        COUNT("count"), START("start"), SCOPE("scope"), BEFORE("before"), AFTER("after"), SHOW_HIDDEN_MEMBERS(
                "show-hidden-members");

        private final String urlName;

        private networkUpdatesFields(String urlName) {
            this.urlName = urlName;
        }

        @Override
        public String toString() {
            return urlName;
        }

    }

    static final String UPDATES_URL = BASE_URL + "{0}/network/updates?format=json";

    static final String UPDATE_COMMENTS_URL = BASE_URL + "~/network/updates/key={0}/update-comments?format=json";

    static final String UPDATE_LIKES_URL = BASE_URL + "~/network/updates/key={0}/likes?format=json";

    static final String UPDATE_IS_LIKED_URL = BASE_URL + "~/network/updates/key={0}/is-liked?format=json";

    static final String ACTIVITY_URL = BASE_URL + "~/person-activities";

    static final String CURRENT_SHARE_URL = BASE_URL + "~:(current-share)";

    static final String SHARE_URL = BASE_URL + "~/shares";

    public static final int DEFAULT_START = 0;

    public static final int DEFAULT_COUNT = 10;

    private static final String UPDATE_TYPE_ALL_STRING;

}
