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
package org.jboss.seam.social.twitter.jackson;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.seam.social.TwitterLiteral;
import org.jboss.seam.social.URLUtils;
import org.jboss.seam.social.oauth.OAuthServiceImpl;
import org.jboss.seam.social.twitter.CursoredList;
import org.jboss.seam.social.twitter.DirectMessage;
import org.jboss.seam.social.twitter.ImageSize;
import org.jboss.seam.social.twitter.Place;
import org.jboss.seam.social.twitter.PlacePrototype;
import org.jboss.seam.social.twitter.PlaceType;
import org.jboss.seam.social.twitter.RateLimitStatus;
import org.jboss.seam.social.twitter.SimilarPlaces;
import org.jboss.seam.social.twitter.StatusDetails;
import org.jboss.seam.social.twitter.SuggestionCategory;
import org.jboss.seam.social.twitter.Tweet;
import org.jboss.seam.social.twitter.TwitterBaseService;
import org.jboss.seam.social.twitter.TwitterBlockService;
import org.jboss.seam.social.twitter.TwitterDirectMessageService;
import org.jboss.seam.social.twitter.TwitterFriendService;
import org.jboss.seam.social.twitter.TwitterProfile;
import org.jboss.seam.social.twitter.TwitterTimelineService;
import org.jboss.seam.social.twitter.TwitterUserService;
import org.jboss.solder.logging.Logger;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author Antoine Sabot-Durand
 * @author Craig Walls
 * 
 */
public class TwitterServiceImpl extends OAuthServiceImpl implements TwitterBaseService, TwitterUserService,
        TwitterTimelineService, TwitterFriendService, TwitterBlockService, TwitterDirectMessageService {

    private static final long serialVersionUID = 6806035986656777834L;
    static final String API_ROOT = "https://api.twitter.com/1/";
    static final String VERIFY_CREDENTIALS_URL = "account/verify_credentials.json";

    static final String FRIENDS_STATUSES_URL = "/statuses/friends.json?screen_name={screen_name}";

    // static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
    static final String TWEET_URL = "/statuses/update.json";
    static final String RETWEET_URL = "/statuses/retweet/{tweet_id}.json";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/twitter_connect.png";

    @Inject
    Logger log;

    @Override
    public String getApiRootUrl() {
        return API_ROOT;
    }

    @Override
    protected void initMyProfile() {
        getSession().setUserProfile(getUserProfile());
    }

    @Override
    public TwitterProfile getUserProfile() {
        requireAuthorization();
        return requestObject(buildUri(VERIFY_CREDENTIALS_URL), TwitterProfile.class);
    }

    @Override
    public String getServiceLogo() {
        return LOGO_URL;
    }

    @Override
    public TwitterProfile getMyProfile() {
        return (TwitterProfile) getSession().getUserProfile();
    }

    @Override
    public Annotation getQualifier() {
        return TwitterLiteral.INSTANCE;
    }

    // **** TwitterUserService Implementation

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

    // **********************************************
    // **** TwitterTimelineService Implementation
    // **********************************************

    private static final String USER_TIMELINE_URL = "statuses/user_timeline.json";
    private static final String HOME_TIMELINE_URL = "statuses/home_timeline.json";
    private static final String PUBLIC_TIMELINE_URL = "statuses/public_timeline.json";

    @Override
    public List<Tweet> getPublicTimeline() {
        return requestObject(buildUri(PUBLIC_TIMELINE_URL), TweetList.class);
    }

    @Override
    public List<Tweet> getHomeTimeline() {
        return getHomeTimeline(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getHomeTimeline(int page, int pageSize) {
        return getHomeTimeline(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getHomeTimeline(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri(HOME_TIMELINE_URL, parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getUserTimeline() {
        return getUserTimeline(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(int page, int pageSize) {
        return getUserTimeline(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri(USER_TIMELINE_URL, parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getUserTimeline(String screenName) {
        return getUserTimeline(screenName, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(String screenName, int page, int pageSize) {
        return getUserTimeline(screenName, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(String screenName, int page, int pageSize, long sinceId, long maxId) {
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("screen_name", screenName);
        return requestObject(buildUri(USER_TIMELINE_URL, parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getUserTimeline(long userId) {
        return getUserTimeline(userId, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(long userId, int page, int pageSize) {
        return getUserTimeline(userId, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getUserTimeline(long userId, int page, int pageSize, long sinceId, long maxId) {
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("user_id", String.valueOf(userId));
        return requestObject(buildUri(USER_TIMELINE_URL, parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getMentions() {
        return getMentions(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getMentions(int page, int pageSize) {
        return getMentions(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getMentions(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("statuses/mentions.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedByMe() {
        return getRetweetedByMe(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByMe(int page, int pageSize) {
        return getRetweetedByMe(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByMe(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("statuses/retweeted_by_me.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedByUser(long userId) {
        return getRetweetedByUser(userId, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByUser(long userId, int page, int pageSize) {
        return getRetweetedByUser(userId, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByUser(long userId, int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("user_id", String.valueOf(userId));
        return requestObject(buildUri("statuses/retweeted_by_user.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedByUser(String screenName) {
        return getRetweetedByUser(screenName, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByUser(String screenName, int page, int pageSize) {
        return getRetweetedByUser(screenName, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedByUser(String screenName, int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("screen_name", screenName);
        return requestObject(buildUri("statuses/retweeted_by_user.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedToMe() {
        return getRetweetedToMe(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToMe(int page, int pageSize) {
        return getRetweetedToMe(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToMe(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("statuses/retweeted_to_me.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedToUser(long userId) {
        return getRetweetedToUser(userId, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToUser(long userId, int page, int pageSize) {
        return getRetweetedToUser(userId, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToUser(long userId, int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("user_id", String.valueOf(userId));
        return requestObject(buildUri("statuses/retweeted_to_user.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetedToUser(String screenName) {
        return getRetweetedToUser(screenName, 1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToUser(String screenName, int page, int pageSize) {
        return getRetweetedToUser(screenName, page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetedToUser(String screenName, int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        parameters.put("screen_name", screenName);
        return requestObject(buildUri("statuses/retweeted_to_user.json", parameters), TweetList.class);
    }

    @Override
    public List<Tweet> getRetweetsOfMe() {
        return getRetweetsOfMe(1, 20, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetsOfMe(int page, int pageSize) {
        return getRetweetsOfMe(page, pageSize, 0, 0);
    }

    @Override
    public List<Tweet> getRetweetsOfMe(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("statuses/retweets_of_me.json", parameters), TweetList.class);
    }

    @Override
    public Tweet getStatus(long tweetId) {
        return requestObject(buildUri("statuses/show/" + tweetId + ".json"), Tweet.class);
    }

    @Override
    public Tweet updateStatus(String message) {
        return updateStatus(message, new StatusDetails());
    }

    // public Tweet updateStatus(String message, Resource media) {
    // return updateStatus(message, media, new StatusDetails());
    // }

    public Tweet updateStatus(String message, StatusDetails details) {
        requireAuthorization();
        Multimap<String, Object> tweetParams = LinkedListMultimap.create();
        tweetParams.put("status", message);
        tweetParams.putAll(details.toParameterMap());
        return postObject(buildUri("statuses/update.json"), tweetParams, Tweet.class);
    }

    // public Tweet updateStatus(String message, Resource media, StatusDetails details) {
    // requireAuthorization();
    // Multimap<String, Object> tweetParams = LinkedHashMultimap.create();
    // tweetParams.add("status", message);
    // tweetParams.add("media", media);
    // tweetParams.putAll(details.toParameterMap());
    // return restTemplate.postForObject("https://upload.twitter.com/1/statuses/update_with_media.json", tweetParams,
    // Tweet.class);
    // }

    @Override
    public void deleteStatus(long tweetId) {
        requireAuthorization();
        delete(buildUri("statuses/destroy/" + tweetId + ".json"));
    }

    @Override
    public void retweet(long tweetId) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        postObject(buildUri("statuses/retweet/" + tweetId + ".json"), data, String.class);
    }

    @Override
    public List<Tweet> getRetweets(long tweetId) {
        return getRetweets(tweetId, 100);
    }

    @Override
    public List<Tweet> getRetweets(long tweetId, int count) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("count", String.valueOf(count));
        return requestObject(buildUri("statuses/retweets/" + tweetId + ".json", parameters), TweetList.class);
    }

    @Override
    public List<TwitterProfile> getRetweetedBy(long tweetId) {
        return getRetweetedBy(tweetId, 1, 100);
    }

    @Override
    public List<TwitterProfile> getRetweetedBy(long tweetId, int page, int pageSize) {
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
        return requestObject(buildUri("statuses/" + tweetId + "/retweeted_by.json", parameters), TwitterProfileList.class);
    }

    @Override
    public List<Long> getRetweetedByIds(long tweetId) {
        return getRetweetedByIds(tweetId, 1, 100);
    }

    @Override
    public List<Long> getRetweetedByIds(long tweetId, int page, int pageSize) {
        requireAuthorization(); // requires authentication, even though getRetweetedBy() does not.
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
        return requestObject(buildUri("statuses/" + tweetId + "/retweeted_by/ids.json", parameters), LongList.class);
    }

    @Override
    public List<Tweet> getFavorites() {
        return getFavorites(1, 20);
    }

    @Override
    public List<Tweet> getFavorites(int page, int pageSize) {
        requireAuthorization(); // Note: The documentation for favorites.json doesn't list the count parameter, but it works
                                // anyway.
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
        return requestObject(buildUri("favorites.json", parameters), TweetList.class);
    }

    @Override
    public void addToFavorites(long tweetId) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        postObject(buildUri("favorites/create/" + tweetId + ".json"), data, String.class);
    }

    @Override
    public void removeFromFavorites(long tweetId) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        postObject(buildUri("favorites/destroy/" + tweetId + ".json"), data, String.class);
    }

    @SuppressWarnings("serial")
    private static class LongList extends ArrayList<Long> {
    }

    @SuppressWarnings("serial")
    private static class TweetList extends ArrayList<Tweet> {
    }

    // ***** TwitterFriendService Implementation

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
        requireAuthorization();
        return requestObject(buildUri("friends/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFriendIds(long userId) {
        return getFriendIdsInCursor(userId, -1);
    }

    @Override
    public CursoredList<Long> getFriendIdsInCursor(long userId, long cursor) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("user_id", String.valueOf(userId));
        return requestObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFriendIds(String screenName) {
        return getFriendIdsInCursor(screenName, -1);
    }

    @Override
    public CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("screen_name", screenName);
        return requestObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
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
        requireAuthorization();
        return requestObject(buildUri("followers/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class)
                .getList();
    }

    @Override
    public CursoredList<Long> getFollowerIds(long userId) {
        return getFollowerIdsInCursor(userId, -1);
    }

    @Override
    public CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("user_id", String.valueOf(userId));
        return requestObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public CursoredList<Long> getFollowerIds(String screenName) {
        return getFollowerIdsInCursor(screenName, -1);
    }

    @Override
    public CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("cursor", String.valueOf(cursor));
        parameters.put("screen_name", screenName);
        return requestObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
    }

    @Override
    public String follow(long userId) {
        requireAuthorization();
        return (String) postObject(buildUri("friendships/create.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                Map.class).get("screen_name");
    }

    @Override
    public String follow(String screenName) {
        requireAuthorization();
        return (String) postObject(buildUri("friendships/create.json", "screen_name", screenName), EMPTY_DATA, Map.class).get(
                "screen_name");
    }

    @Override
    public String unfollow(long userId) {
        requireAuthorization();
        return (String) postObject(buildUri("friendships/destroy.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                Map.class).get("screen_name");
    }

    @Override
    public String unfollow(String screenName) {
        requireAuthorization();
        return (String) postObject(buildUri("friendships/destroy.json", "screen_name", screenName), EMPTY_DATA, Map.class).get(
                "screen_name");
    }

    @Override
    public TwitterProfile enableNotifications(long userId) {
        requireAuthorization();
        return postObject(buildUri("notifications/follow.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile enableNotifications(String screenName) {
        requireAuthorization();
        return postObject(buildUri("notifications/follow.json", "screen_name", screenName), EMPTY_DATA, TwitterProfile.class);
    }

    @Override
    public TwitterProfile disableNotifications(long userId) {
        requireAuthorization();
        return postObject(buildUri("notifications/leave.json", "user_id", String.valueOf(userId)), EMPTY_DATA,
                TwitterProfile.class);
    }

    @Override
    public TwitterProfile disableNotifications(String screenName) {
        requireAuthorization();
        return postObject(buildUri("notifications/leave.json", "screen_name", screenName), EMPTY_DATA, TwitterProfile.class);
    }

    // doesn't require authentication
    @Override
    public boolean friendshipExists(String userA, String userB) {
        Multimap<String, String> params = LinkedListMultimap.create();
        params.put("user_a", userA);
        params.put("user_b", userB);
        return requestObject(buildUri("friendships/exists.json", params), boolean.class);
    }

    @Override
    public CursoredList<Long> getIncomingFriendships() {
        return getIncomingFriendships(-1);
    }

    @Override
    public CursoredList<Long> getIncomingFriendships(long cursor) {
        requireAuthorization();
        return requestObject(buildUri("friendships/incoming.json", "cursor", String.valueOf(cursor)), CursoredLongList.class)
                .getList();
    }

    @Override
    public CursoredList<Long> getOutgoingFriendships() {
        return getOutgoingFriendships(-1);
    }

    @Override
    public CursoredList<Long> getOutgoingFriendships(long cursor) {
        requireAuthorization();
        return requestObject(buildUri("friendships/outgoing.json", "cursor", String.valueOf(cursor)), CursoredLongList.class)
                .getList();
    }

    private CursoredList<TwitterProfile> getCursoredProfileList(List<Long> userIds, long previousCursor, long nextCursor) {
        // TODO: Would be good to figure out how to retrieve profiles in a tighter-than-cursor granularity.
        List<List<Long>> chunks = chunkList(userIds, 100);
        CursoredList<TwitterProfile> users = new CursoredList<TwitterProfile>(userIds.size(), previousCursor, nextCursor);
        for (List<Long> userIdChunk : chunks) {
            String joinedIds = URLUtils.commaJoiner.join(userIdChunk);
            users.addAll(requestObject(buildUri("users/lookup.json", "user_id", joinedIds), TwitterProfileList.class));
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

    private static final Multimap<String, Object> EMPTY_DATA = LinkedListMultimap.create();

    // *** TwitterBlockService Implementation

    @Override
    public TwitterProfile block(long userId) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("user_id", String.valueOf(userId));
        return postObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile block(String screenName) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("screen_name", screenName);
        return postObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile unblock(long userId) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("user_id", String.valueOf(userId));
        return postObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    @Override
    public TwitterProfile unblock(String screenName) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("screen_name", screenName);
        return postObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    @Override
    public List<TwitterProfile> getBlockedUsers() {
        return getBlockedUsers(1, 20);
    }

    @Override
    public List<TwitterProfile> getBlockedUsers(int page, int pageSize) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
        return requestObject(buildUri("blocks/blocking.json", parameters), TwitterProfileList.class);
    }

    @Override
    public List<Long> getBlockedUserIds() {
        requireAuthorization();
        return requestObject(buildUri("blocks/blocking/ids.json"), LongList.class);
    }

    @Override
    public boolean isBlocking(long userId) {
        return isBlocking(buildUri("blocks/exists.json", "user_id", String.valueOf(userId)));
    }

    @Override
    public boolean isBlocking(String screenName) {
        return isBlocking(buildUri("blocks/exists.json", "screen_name", screenName));
    }

    // ********************************
    // **** TwitterDirectMessageService Implementation
    // ********************************

    @Override
    public List<DirectMessage> getDirectMessagesReceived() {
        return getDirectMessagesReceived(1, 20, 0, 0);
    }

    @Override
    public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize) {
        return getDirectMessagesReceived(page, pageSize, 0, 0);
    }

    @Override
    public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("direct_messages.json", parameters), DirectMessageList.class);
    }

    @Override
    public List<DirectMessage> getDirectMessagesSent() {
        return getDirectMessagesSent(1, 20, 0, 0);
    }

    @Override
    public List<DirectMessage> getDirectMessagesSent(int page, int pageSize) {
        return getDirectMessagesSent(page, pageSize, 0, 0);
    }

    @Override
    public List<DirectMessage> getDirectMessagesSent(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("direct_messages/sent.json", parameters), DirectMessageList.class);
    }

    @Override
    public DirectMessage getDirectMessage(long id) {
        requireAuthorization();
        return requestObject(buildUri("direct_messages/show/" + id + ".json"), DirectMessage.class);
    }

    @Override
    public DirectMessage sendDirectMessage(String toScreenName, String text) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        data.put("screen_name", String.valueOf(toScreenName));
        data.put("text", text);
        return postObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    @Override
    public DirectMessage sendDirectMessage(long toUserId, String text) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        data.put("user_id", String.valueOf(toUserId));
        data.put("text", text);
        return postObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    @Override
    public void deleteDirectMessage(long messageId) {
        requireAuthorization();
        delete(buildUri("direct_messages/destroy/" + messageId + ".json"));
    }

    // ****************************************
    // **** TwitterGeoService implementation
    // ****************************************

    public Place getPlace(String placeId) {
        return requestObject(buildUri("geo/id/" + placeId + ".json"), Place.class);
    }

    public List<Place> reverseGeoCode(double latitude, double longitude) {
        return reverseGeoCode(latitude, longitude, null, null);
    }

    public List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy) {
        Multimap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, null);
        return requestObject(buildUri("geo/reverse_geocode.json", parameters), PlacesList.class).getList();
    }

    public List<Place> search(double latitude, double longitude) {
        return search(latitude, longitude, null, null, null);
    }

    public List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
        Multimap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, query);
        return requestObject(buildUri("geo/search.json", parameters), PlacesList.class).getList();
    }

    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name) {
        return findSimilarPlaces(latitude, longitude, name, null, null);
    }

    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name, String streetAddress,
            String containedWithin) {
        Multimap<String, String> parameters = buildPlaceParameters(latitude, longitude, name, streetAddress, containedWithin);
        SimilarPlacesResponse response = requestObject(buildUri("geo/similar_places.json", parameters),
                SimilarPlacesResponse.class);
        PlacePrototype placePrototype = new PlacePrototype(response.getToken(), latitude, longitude, name, streetAddress,
                containedWithin);

        return new SimilarPlaces(response.getPlaces(), placePrototype);
    }

    public Place createPlace(PlacePrototype placePrototype) {
        Multimap<String, String> request = buildPlaceParameters(placePrototype.getLatitude(), placePrototype.getLongitude(),
                placePrototype.getName(), placePrototype.getStreetAddress(), placePrototype.getContainedWithin());
        request.put("token", placePrototype.getCreateToken());
        return postObject("https://api.twitter.com/1/geo/place.json", request, Place.class);
    }

    // private helpers

    private Multimap<String, String> buildGeoParameters(double latitude, double longitude, PlaceType granularity,
            String accuracy, String query) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("lat", String.valueOf(latitude));
        parameters.put("long", String.valueOf(longitude));
        if (granularity != null) {
            parameters.put("granularity", granularity.equals(PlaceType.POINT_OF_INTEREST) ? "poi" : granularity.toString()
                    .toLowerCase());
        }
        if (accuracy != null) {
            parameters.put("accuracy", accuracy);
        }
        if (query != null) {
            parameters.put("query", query);
        }
        return parameters;
    }

    private Multimap<String, String> buildPlaceParameters(double latitude, double longitude, String name, String streetAddress,
            String containedWithin) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("lat", String.valueOf(latitude));
        parameters.put("long", String.valueOf(longitude));
        parameters.put("name", name);
        if (streetAddress != null) {
            parameters.put("attribute:street_address", streetAddress);
        }
        if (containedWithin != null) {
            parameters.put("contained_within", containedWithin);
        }
        return parameters;
    }
}
