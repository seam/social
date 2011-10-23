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

import org.jboss.seam.social.HasStatus;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.twitter.model.SuggestionCategory;
import org.jboss.seam.social.twitter.model.TwitterProfile;

/**
 * A specialization of {@link OAuthService} to add TwitterRelated specific methods
 * 
 * @author Antoine Sabot-Durand
 */

public interface TwitterService extends OAuthService, HasStatus {
    static final String TYPE = "Twitter";

    /**
     * Retrieves the authenticated user's Twitter ID.
     * 
     * @return the user's ID at Twitter
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    String getProfileId();

    /**
     * Retrieves the authenticated user's Twitter screen name
     * 
     * @return the user's screen name
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    String getScreenName();

    /**
     * Retrieves the authenticated user's Twitter profile details.
     * 
     * @return a {@link TwitterProfile} object representing the user's profile.
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    TwitterProfile getMyProfile();

    /**
     * Retrieves a specific user's Twitter profile details. Note that this method does not require authentication.
     * 
     * @param screenName the screen name for the user whose details are to be retrieved.
     * @return a {@link TwitterProfile} object representing the user's profile.
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    TwitterProfile getUserProfile(String screenName);

    /**
     * Retrieves a specific user's Twitter profile details. Note that this method does not require authentication.
     * 
     * @param userId the user ID for the user whose details are to be retrieved.
     * @return a {@link TwitterProfile} object representing the user's profile.
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    TwitterProfile getUserProfile(long userId);

    /**
     * Retrieves a list of Twitter profiles for the given list of user IDs.
     * 
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    List<TwitterProfile> getUsers(Long... userIds);

    /**
     * Retrieves a list of Twitter profiles for the given list of screen names.
     * 
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    List<TwitterProfile> getUsers(String... screenNames);

    /**
     * Searches for users that match a given query.
     * 
     * @throws ApiException if there is an error while communicating with Twitter.
     * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     */
    List<TwitterProfile> searchForUsers(String query);

    /**
     * Retrieves a list of categories from which suggested users to follow may be found.
     * 
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    List<SuggestionCategory> getSuggestionCategories();

    /**
     * Retrieves a list of suggestions of users to follow for a given category.
     * 
     * @param slug the category's slug
     * @throws ApiException if there is an error while communicating with Twitter.
     */
    List<TwitterProfile> getSuggestions(String slug);

}
