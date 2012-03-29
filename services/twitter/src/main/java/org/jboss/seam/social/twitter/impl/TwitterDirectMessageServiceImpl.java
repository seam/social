/**
 * 
 */
package org.jboss.seam.social.twitter.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.seam.social.TwitterBaseService;
import org.jboss.seam.social.twitter.TwitterDirectMessageService;
import org.jboss.seam.social.twitter.model.DirectMessage;
import org.jboss.seam.social.utils.URLUtils;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class TwitterDirectMessageServiceImpl extends TwitterBaseService implements TwitterDirectMessageService {

    @SuppressWarnings("serial")
    class DirectMessageList extends ArrayList<DirectMessage> {
    }

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

        Map<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return getService().getForObject(buildUri("direct_messages.json", parameters), DirectMessageList.class);
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

        Map<String, String> parameters = URLUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
        return getService().getForObject(buildUri("direct_messages/sent.json", parameters), DirectMessageList.class);
    }

    @Override
    public DirectMessage getDirectMessage(long id) {

        return getService().getForObject(buildUri("direct_messages/show/" + id + ".json"), DirectMessage.class);
    }

    @Override
    public DirectMessage sendDirectMessage(String toScreenName, String text) {

        Map<String, Object> data = newHashMap();
        data.put("screen_name", String.valueOf(toScreenName));
        data.put("text", text);
        return getService().postForObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    @Override
    public DirectMessage sendDirectMessage(long toUserId, String text) {

        Map<String, Object> data = newHashMap();
        data.put("user_id", String.valueOf(toUserId));
        data.put("text", text);
        return getService().postForObject(buildUri("direct_messages/new.json"), data, DirectMessage.class);
    }

    @Override
    public void deleteDirectMessage(long messageId) {

        getService().delete(buildUri("direct_messages/destroy/" + messageId + ".json"));
    }

}
