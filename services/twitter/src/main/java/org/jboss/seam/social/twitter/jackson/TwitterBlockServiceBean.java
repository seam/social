/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.twitter.jackson;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.social.URLUtils;
import org.jboss.seam.social.twitter.BlockService;
import org.jboss.seam.social.twitter.TwitterProfile;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Implementation of {@link BlockOperations}, providing a binding to Twitter's block REST resources.
 * 
 * @author Craig Walls
 */
class TwitterBlockServiceBean extends TwitterBaseServiceImpl implements BlockService {

    /**
     * 
     */
    private static final long serialVersionUID = 7602084044394715397L;

    public TwitterProfile block(long userId) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("user_id", String.valueOf(userId));
        return postObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    public TwitterProfile block(String screenName) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("screen_name", screenName);
        return postObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
    }

    public TwitterProfile unblock(long userId) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("user_id", String.valueOf(userId));
        return postObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    public TwitterProfile unblock(String screenName) {
        requireAuthorization();
        Multimap<String, String> request = LinkedListMultimap.create();
        request.put("screen_name", screenName);
        return postObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
    }

    public List<TwitterProfile> getBlockedUsers() {
        return getBlockedUsers(1, 20);
    }

    public List<TwitterProfile> getBlockedUsers(int page, int pageSize) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
        return requestObject(buildUri("blocks/blocking.json", parameters), TwitterProfileList.class);
    }

    public List<Long> getBlockedUserIds() {
        requireAuthorization();
        return requestObject(buildUri("blocks/blocking/ids.json"), LongList.class);
    }

    public boolean isBlocking(long userId) {
        return isBlocking(buildUri("blocks/exists.json", "user_id", String.valueOf(userId)));
    }

    public boolean isBlocking(String screenName) {
        return isBlocking(buildUri("blocks/exists.json", "screen_name", screenName));
    }

    // private helpers

    private boolean isBlocking(URI blockingExistsUri) {
        try {
            requestObject(blockingExistsUri.toString(), String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("serial")
    private static class LongList extends ArrayList<Long> {
    }

}
