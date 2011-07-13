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
package org.jboss.seam.social.twitter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.social.core.HttpResponse;
import org.jboss.seam.social.core.JsonMapper;
import org.jboss.seam.social.core.OAuthService;
import org.jboss.seam.social.core.OAuthServiceBase;
import org.jboss.seam.social.core.RelatedTo;
import org.jboss.seam.social.core.RestVerb;
import org.jboss.seam.social.core.URLUtils;
import org.jboss.seam.social.twitter.model.Tweet;
import org.jboss.seam.social.twitter.model.TweetJackson;
import org.jboss.seam.social.twitter.model.TwitterProfile;

/**
 * @author Antoine Sabot-Durand
 */

public class TwitterJackson extends OAuthServiceBase implements Twitter {

    private static final long serialVersionUID = 6806035986656777834L;
    static final String VERIFY_CREDENTIALS_URL = "https://api.twitter.com/1/account/verify_credentials.json";
    static final String GET_USER_PROFILE_URL = "https://api.twitter.com/1/users/show.json";
    static final String FRIENDS_STATUSES_URL = "https://api.twitter.com/1/statuses/friends.json?screen_name={screen_name}";
    static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
    static final String TWEET_URL = "https://api.twitter.com/1/statuses/update.json";
    static final String RETWEET_URL = "https://api.twitter.com/1/statuses/retweet/{tweet_id}.json";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/twitter_connect.png";

    @Inject
    Logger log;

    @Inject
    private JsonMapper jsonMapper;

    @Produces
    @RelatedTo(TwitterJackson.TYPE)
    protected OAuthService qualifiedTwitterProducer(@New TwitterJackson service) {
        return service;
    }

    @Override
    public Tweet updateStatus(String message) {
        HttpResponse resp = sendSignedRequest(RestVerb.POST, TWEET_URL, "status", message);
        System.out.println("update satus is " + message);
        setStatus("");
        return jsonMapper.readValue(resp, TweetJackson.class);

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
     * @see org.jboss.seam.social.oauth.OAuthService#getType()
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.Twitter#updateStatus()
     */
    @Override
    public Tweet updateStatus() {
        return updateStatus(getStatus());
    }

    @PostConstruct
    void init() {
        log.info("== Creating a new Twitter Bean : " + this.hashCode());
        jsonMapper.registerModule(new TwitterModule());
    }

    @PreDestroy
    void destroy() {
        log.info("== Destroying Twitter Bean : " + this.hashCode());
    }

    public long getProfileId() {
        requireAuthorization();
        return getMyProfile().getId();
    }

    public String getScreenName() {
        requireAuthorization();
        return getMyProfile().getScreenName();
    }

    @Override
    public TwitterProfile getMyProfile() {
        return (TwitterProfile) myProfile;
    }

    public TwitterProfile getUserProfile(String screenName) {
        return jsonMapper.readValue(
                sendSignedRequest(RestVerb.GET,
                        URLUtils.appendParametersToQueryString(GET_USER_PROFILE_URL, "screen_name", screenName)),
                TwitterProfile.class);
    }

    public TwitterProfile getUserProfile(long userId) {
        return jsonMapper.readValue(
                sendSignedRequest(RestVerb.GET,
                        URLUtils.appendParametersToQueryString(GET_USER_PROFILE_URL, "user_id", String.valueOf(userId))),
                TwitterProfile.class);
    }

    /*
     * public byte[] getUserProfileImage(String screenName) { return getUserProfileImage(screenName, ImageSize.NORMAL); }
     * 
     * public byte[] getUserProfileImage(String screenName, ImageSize size) { LinkedMultiValueMap<String, String> parameters =
     * new LinkedMultiValueMap<String, String>(); parameters.set("screen_name", screenName); parameters.set("size",
     * size.toString().toLowerCase()); ResponseEntity<byte[]> response =
     * restTemplate.getForEntity(buildUri("users/profile_image", parameters), byte[].class); if (response.getStatusCode() ==
     * HttpStatus.FOUND) { throw new UnsupportedOperationException(
     * "Attempt to fetch image resulted in a redirect which could not be followed. Add Apache HttpComponents HttpClient to the classpath "
     * + "to be able to follow redirects."); } return response.getBody(); }
     */
    @Override
    public List<TwitterProfile> getUsers(Long... userIds) {
        String joinedIds = URLUtils.commaJoiner.join(userIds);
        return jsonMapper.readValue(
                sendSignedRequest(RestVerb.GET,
                        URLUtils.appendParametersToQueryString(GET_USER_PROFILE_URL, "user_id", joinedIds)),
                TwitterProfileList.class);
    }

    @Override
    public List<TwitterProfile> getUsers(String... screenNames) {
        String joinedScreenNames = URLUtils.commaJoiner.join(screenNames);
        return jsonMapper.readValue(
                sendSignedRequest(RestVerb.GET,
                        URLUtils.appendParametersToQueryString(GET_USER_PROFILE_URL, "user_id", joinedScreenNames)),
                TwitterProfileList.class);
    }

    //
    // public List<TwitterProfile> searchForUsers(String query) {
    // requireAuthorization();
    // return restTemplate.getForObject(buildUri("users/search.json", "q", query), TwitterProfileList.class);
    // }
    //
    // public List<SuggestionCategory> getSuggestionCategories() {
    // return restTemplate.getForObject(buildUri("users/suggestions.json"), SuggestionCategoryList.class);
    // }
    //
    // public List<TwitterProfile> getSuggestions(String slug) {
    // return restTemplate.getForObject(buildUri("users/suggestions/" + slug + ".json"), TwitterProfileUsersList.class)
    // .getList();
    // }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.Twitter#getUserProfileImage(java.lang.String)
     */
    @Override
    public byte[] getUserProfileImage(String screenName) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.Twitter#searchForUsers(java.lang.String)
     */
    @Override
    public List<TwitterProfile> searchForUsers(String query) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.core.OAuthServiceBase#initMyProfile()
     */
    @Override
    protected void initMyProfile() {
        myProfile = jsonMapper.readValue(sendSignedRequest(RestVerb.GET, VERIFY_CREDENTIALS_URL), TwitterProfile.class);

    }

}