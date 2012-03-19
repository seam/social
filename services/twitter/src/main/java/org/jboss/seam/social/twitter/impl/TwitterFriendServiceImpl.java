/**
 * 
 */
package org.jboss.seam.social.twitter.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.seam.social.twitter.CursoredList;
import org.jboss.seam.social.twitter.TwitterBaseService;
import org.jboss.seam.social.twitter.TwitterProfile;
import org.jboss.seam.social.twitter.impl.TwitterUserServiceImpl.TwitterProfileList;
import org.jboss.seam.social.twitter.jackson.CursoredLongList;
import org.jboss.seam.social.utils.URLUtils;

/**
 * @author Antoine Sabot-Durand
 * @author Craig Walls
 * 
 */
public class TwitterFriendServiceImpl extends TwitterBaseService implements org.jboss.seam.social.twitter.TwitterFriendService {

    @Override
    public CursoredList<TwitterProfile> getFriends() {
        return getFriendsInCursor(-1);
    }

    @Override
    public CursoredList<TwitterProfile> getFriendsInCursor(long cursor) {
        CursoredList<Long> friendIds = getFriendIdsInCursor(cursor);
        return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
    }

    @Override
    public CursoredList<TwitterProfile> getFriends(long userId) {
        return getFriendsInCursor(userId, -1);
    }

    @Override
    public CursoredList<TwitterProfile> getFriendsInCursor(long userId, long cursor) {
        CursoredList<Long> friendIds = getFriendIdsInCursor(userId, cursor);
        return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
    }

    @Override
    public CursoredList<TwitterProfile> getFriends(String screenName) {
        return getFriendsInCursor(screenName, -1);
    }

    @Override
    public CursoredList<TwitterProfile> getFriendsInCursor(String screenName, long cursor) {
        CursoredList<Long> friendIds = getFriendIdsInCursor(screenName, cursor);
        return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
    }

    @Override
    public CursoredList<Long> getFriendIds() {
        return getFriendIdsInCursor(-1);
    }

    @Override
    public CursoredList<Long> getFriendIdsInCursor(long cursor) {

        return getService()
                .getForObject(buildUri("friends/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFriendIds(long userId) {
        return getFriendIdsInCursor(userId, -1);
    }

    @Override
    public CursoredList<Long> getFriendIdsInCursor(long userId, long cursor) {
        Map<String, String> parameters = newHashMap();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("user_id", String.valueOf(userId));
        return getService().getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFriendIds(String screenName) {
        return getFriendIdsInCursor(screenName, -1);
    }

    @Override
    public CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor) {
        Map<String, String> parameters = newHashMap();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("screen_name", screenName);
        return getService().getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<TwitterProfile> getFollowers() {
        return getFollowersInCursor(-1);
    }

    @Override
    public CursoredList<TwitterProfile> getFollowersInCursor(long cursor) {
        CursoredList<Long> followerIds = getFollowerIdsInCursor(cursor);
        return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
    }

    @Override
    public CursoredList<TwitterProfile> getFollowers(long userId) {
        return getFollowersInCursor(userId, -1);
    }

    @Override
    public CursoredList<TwitterProfile> getFollowersInCursor(long userId, long cursor) {
        CursoredList<Long> followerIds = getFollowerIdsInCursor(userId, cursor);
        return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
    }

    @Override
    public CursoredList<TwitterProfile> getFollowers(String screenName) {
        return getFollowersInCursor(screenName, -1);
    }

    @Override
    public CursoredList<TwitterProfile> getFollowersInCursor(String screenName, long cursor) {
        CursoredList<Long> followerIds = getFollowerIdsInCursor(screenName, cursor);
        return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
    }

    @Override
    public CursoredList<Long> getFollowerIds() {
        return getFollowerIdsInCursor(-1);
    }

    @Override
    public CursoredList<Long> getFollowerIdsInCursor(long cursor) {

        return getService().getForObject(buildUri("followers/ids.json", "cursor", String.valueOf(cursor)),
                CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFollowerIds(long userId) {
        return getFollowerIdsInCursor(userId, -1);
    }

    @Override
    public CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor) {
        Map<String, String> parameters = newHashMap();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("user_id", String.valueOf(userId));
        return getService().getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFollowerIds(String screenName) {
        return getFollowerIdsInCursor(screenName, -1);
    }

    @Override
    public CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor) {
        Map<String, String> parameters = newHashMap();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("screen_name", screenName);
        return getService().getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public String follow(long userId) {

        return (String) getService().postForObject(buildUri("friendships/create.json", "user_id", String.valueOf(userId)),
                EMPTY_DATA, Map.class).get("screen_name");
    }

    @Override
    public String follow(String screenName) {

        return (String) getService().postForObject(buildUri("friendships/create.json", "screen_name", screenName), EMPTY_DATA,
                Map.class).get("screen_name");
    }

    @Override
    public String unfollow(long userId) {

        return (String) getService().postForObject(buildUri("friendships/destroy.json", "user_id", String.valueOf(userId)),
                EMPTY_DATA, Map.class).get("screen_name");
    }

    @Override
    public String unfollow(String screenName) {

        return (String) getService().postForObject(buildUri("friendships/destroy.json", "screen_name", screenName), EMPTY_DATA,
                Map.class).get("screen_name");
    }

    @Override
    public TwitterProfile enableNotifications(long userId) {

        return getService().postForObject(buildUri("notifications/follow.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile enableNotifications(String screenName) {

        return getService().postForObject(buildUri("notifications/follow.json", "screen_name", screenName), EMPTY_DATA,
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile disableNotifications(long userId) {

        return getService().postForObject(buildUri("notifications/leave.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile disableNotifications(String screenName) {

        return getService().postForObject(buildUri("notifications/leave.json", "screen_name", screenName), EMPTY_DATA,
                TwitterProfile.class);
    }

    // doesn't require authentication
    @Override
    public boolean friendshipExists(String userA, String userB) {
        Map<String, String> params = newHashMap();
        return getService().getForObject(buildUri("friendships/exists.json", params), boolean.class);
    }

    @Override
    public CursoredList<Long> getIncomingFriendships() {
        return getIncomingFriendships(-1);
    }

    @Override
    public CursoredList<Long> getIncomingFriendships(long cursor) {

        return getService().getForObject(buildUri("friendships/incoming.json", "cursor", String.valueOf(cursor)),
                CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getOutgoingFriendships() {
        return getOutgoingFriendships(-1);
    }

    @Override
    public CursoredList<Long> getOutgoingFriendships(long cursor) {

        return getService().getForObject(buildUri("friendships/outgoing.json", "cursor", String.valueOf(cursor)),
                CursoredLongList.class).getList();
    }

    private CursoredList<TwitterProfile> getCursoredProfileList(List<Long> userIds, long previousCursor, long nextCursor) {
        // TODO: Would be good to figure out how to retrieve profiles in a tighter-than-cursor granularity.
        List<List<Long>> chunks = chunkList(userIds, 100);
        CursoredList<TwitterProfile> users = new CursoredList<TwitterProfile>(userIds.size(), previousCursor, nextCursor);
        for (List<Long> userIdChunk : chunks) {
            String joinedIds = URLUtils.commaJoiner.join(userIdChunk);
            users.addAll(getService().getForObject(buildUri("users/lookup.json", "user_id", joinedIds),
                    TwitterProfileList.class));
        }
        return users;
    }

    private List<List<Long>> chunkList(List<Long> list, int chunkSize) {
        List<List<Long>> chunkedList = new ArrayList<List<Long>>();
        int start = 0;
        while (start < list.size()) {
            int end = Math.min(chunkSize + start, list.size());
            chunkedList.add(list.subList(start, end));
            start = end;
        }
        return chunkedList;
    }

    private static final Map<String, Object> EMPTY_DATA = newHashMap();

}
