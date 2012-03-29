/**
 * 
 */
package org.jboss.seam.social.twitter.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.seam.social.TwitterBaseService;
import org.jboss.seam.social.twitter.TwitterBlockService;
import org.jboss.seam.social.twitter.impl.TwitterUserServiceImpl.TwitterProfileList;
import org.jboss.seam.social.twitter.model.TwitterProfile;
import org.jboss.seam.social.utils.URLUtils;

/**
 * @author Antoine Sabot-Durand
 * @author Craig Walls
 * 
 */
public class TwitterBlockServiceImpl extends TwitterBaseService implements TwitterBlockService {

    @SuppressWarnings("serial")
    private static class LongList extends ArrayList<Long> {
    }

    @Override
    public TwitterProfile block(long userId) {
        Map<String, String> request = newHashMap();
        request.put("user_id", String.valueOf(userId));
        return getService().postForObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile block(String screenName) {
        Map<String, String> request = newHashMap();
        request.put("screen_name", screenName);
        return getService().postForObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile unblock(long userId) {
        Map<String, String> request = newHashMap();
        request.put("user_id", String.valueOf(userId));
        return getService().postForObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile unblock(String screenName) {
        Map<String, String> request = newHashMap();
        request.put("screen_name", screenName);
        return getService().postForObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    @Override
    public List<TwitterProfile> getBlockedUsers() {
        return getBlockedUsers(1, 20);
    }

    @Override
    public List<TwitterProfile> getBlockedUsers(int page, int pageSize) {
        Map<String, String> parameters = URLUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
        return getService().getForObject(buildUri("blocks/blocking.json", parameters), TwitterProfileList.class);
    }

    @Override
    public List<Long> getBlockedUserIds() {
        return getService().getForObject(buildUri("blocks/blocking/ids.json"), LongList.class);
    }

    @Override
    public boolean isBlocking(long userId) {
        return isBlocking(buildUri("blocks/exists.json", "user_id", String.valueOf(userId)));
    }

    @Override
    public boolean isBlocking(String screenName) {
        return isBlocking(buildUri("blocks/exists.json", "screen_name", screenName));
    }

}
