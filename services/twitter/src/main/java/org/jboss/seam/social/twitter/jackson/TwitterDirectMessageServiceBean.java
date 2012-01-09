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

import java.util.List;

import org.jboss.seam.social.URLUtils;
import org.jboss.seam.social.twitter.DirectMessage;
import org.jboss.seam.social.twitter.DirectMessageService;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Implementation of {@link DirectMessageOperations}, providing a binding to Twitter's direct message-oriented REST resources.
 * 
 * @author Craig Walls
 */
class TwitterDirectMessageServiceBean extends TwitterBaseServiceImpl implements DirectMessageService {

    /**
     * 
     */
    private static final long serialVersionUID = -5971770114974803187L;

    public List<DirectMessage> getDirectMessagesReceived() {
        return getDirectMessagesReceived(1, 20, 0, 0);
    }

    public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize) {
        return getDirectMessagesReceived(page, pageSize, 0, 0);
    }

    public List<DirectMessage> getDirectMessagesReceived(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("direct_messages.json", parameters), DirectMessageList.class);
    }

    public List<DirectMessage> getDirectMessagesSent() {
        return getDirectMessagesSent(1, 20, 0, 0);
    }

    public List<DirectMessage> getDirectMessagesSent(int page, int pageSize) {
        return getDirectMessagesSent(page, pageSize, 0, 0);
    }

    public List<DirectMessage> getDirectMessagesSent(int page, int pageSize, long sinceId, long maxId) {
        requireAuthorization();
        Multimap<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return requestObject(buildUri("direct_messages/sent.json", parameters), DirectMessageList.class);
    }

    public DirectMessage getDirectMessage(long id) {
        requireAuthorization();
        return requestObject(buildUri("direct_messages/show/" + id + ".json"), DirectMessage.class);
    }

    public DirectMessage sendDirectMessage(String toScreenName, String text) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        data.put("screen_name", String.valueOf(toScreenName));
        data.put("text", text);
        return postObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    public DirectMessage sendDirectMessage(long toUserId, String text) {
        requireAuthorization();
        Multimap<String, Object> data = LinkedListMultimap.create();
        data.put("user_id", String.valueOf(toUserId));
        data.put("text", text);
        return postObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    public void deleteDirectMessage(long messageId) {
        requireAuthorization();
        delete(buildUri("direct_messages/destroy/" + messageId + ".json"));
    }

}
