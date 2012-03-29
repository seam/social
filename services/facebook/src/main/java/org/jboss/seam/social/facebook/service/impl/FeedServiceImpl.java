/**
 * 
 */
package org.jboss.seam.social.facebook.service.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.jboss.seam.social.exception.SeamSocialException;
import org.jboss.seam.social.facebook.FacebookBaseService;
import org.jboss.seam.social.facebook.model.FacebookLink;
import org.jboss.seam.social.facebook.model.LinkPost;
import org.jboss.seam.social.facebook.model.NotePost;
import org.jboss.seam.social.facebook.model.Post;
import org.jboss.seam.social.facebook.model.Post.PostType;
import org.jboss.seam.social.facebook.model.StatusPost;
import org.jboss.seam.social.facebook.service.FeedService;
import org.jboss.seam.social.facebook.service.GraphApi;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@Named("facebookFeed")
public class FeedServiceImpl extends FacebookBaseService implements FeedService {

    @Inject
    private GraphApi graphApi;

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public List<Post> getFeed() {
        return getFeed("me", 0, 25);
    }

    @Override
    public List<Post> getFeed(int offset, int limit) {
        return getFeed("me", offset, limit);
    }

    @Override
    public List<Post> getFeed(String ownerId) {
        return getFeed(ownerId, 0, 25);
    }

    @Override
    public List<Post> getFeed(String ownerId, int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/" + ownerId + "/feed", offset, limit);
        return deserializeList(responseNode, null, Post.class);
    }

    @Override
    public List<Post> getHomeFeed() {
        return getHomeFeed(0, 25);
    }

    @Override
    public java.util.List<Post> getHomeFeed(int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/me/home", offset, limit);
        return deserializeList(responseNode, null, Post.class);
    }

    @Override
    public List<StatusPost> getStatuses() {
        return getStatuses("me", 0, 25);
    }

    @Override
    public List<StatusPost> getStatuses(int offset, int limit) {
        return getStatuses("me", offset, limit);
    }

    @Override
    public List<StatusPost> getStatuses(String userId) {
        return getStatuses(userId, 0, 25);
    }

    @Override
    public List<StatusPost> getStatuses(String userId, int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/" + userId + "/statuses", offset, limit);
        return deserializeList(responseNode, "status", StatusPost.class);
    }

    @Override
    public List<LinkPost> getLinks() {
        return getLinks("me", 0, 25);
    }

    @Override
    public List<LinkPost> getLinks(int offset, int limit) {
        return getLinks("me", offset, limit);
    }

    @Override
    public List<LinkPost> getLinks(String ownerId) {
        return getLinks(ownerId, 0, 25);
    }

    @Override
    public List<LinkPost> getLinks(String ownerId, int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/" + ownerId + "/links", offset, limit);
        return deserializeList(responseNode, "link", LinkPost.class);
    }

    @Override
    public List<NotePost> getNotes() {
        return getNotes("me", 0, 25);
    }

    @Override
    public List<NotePost> getNotes(int offset, int limit) {
        return getNotes("me", offset, limit);
    }

    @Override
    public List<NotePost> getNotes(String ownerId) {
        return getNotes(ownerId, 0, 25);
    }

    @Override
    public List<NotePost> getNotes(String ownerId, int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/" + ownerId + "/notes", offset, limit);
        return deserializeList(responseNode, "note", NotePost.class);
    }

    @Override
    public List<Post> getPosts() {
        return getPosts("me", 0, 25);
    }

    @Override
    public List<Post> getPosts(int offset, int limit) {
        return getPosts("me", offset, limit);
    }

    @Override
    public List<Post> getPosts(String ownerId) {
        return getPosts(ownerId, 0, 25);
    }

    @Override
    public List<Post> getPosts(String ownerId, int offset, int limit) {

        JsonNode responseNode = fetchConnectionList("https://graph.facebook.com/" + ownerId + "/posts", offset, limit);
        return deserializeList(responseNode, null, Post.class);
    }

    @Override
    public Post getPost(String entryId) {

        ObjectNode responseNode = (ObjectNode) getService().getForObject("https://graph.facebook.com/" + entryId,
                JsonNode.class);
        return deserializePost(null, Post.class, responseNode);
    }

    @Override
    public String updateStatus(String message) {
        return post("me", message);
    }

    @Override
    public String postLink(String message, FacebookLink link) {
        return postLink("me", message, link);
    }

    @Override
    public String postLink(String ownerId, String message, FacebookLink link) {

        Map<String, Object> map = newHashMap();
        map.put("link", link.getLink());
        map.put("name", link.getName());
        map.put("caption", link.getCaption());
        map.put("description", link.getDescription());
        map.put("message", message);
        return graphApi.publish(ownerId, "feed", map);
    }

    @Override
    public String post(String ownerId, String message) {

        Map<String, Object> map = newHashMap();
        map.put("message", message);
        return graphApi.publish(ownerId, "feed", map);
    }

    @Override
    public void deletePost(String id) {

        graphApi.delete(id);
    }

    @Override
    public List<Post> searchPublicFeed(String query) {
        return searchPublicFeed(query, 0, 25);
    }

    @Override
    public List<Post> searchPublicFeed(String query, int offset, int limit) {
        Map<String, Object> params = newHashMap();
        params.put("q", query);
        params.put("type", "post");
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(limit));
        String uri = buildUri("https://graph.facebook.com/search", params);
        JsonNode responseNode = getService().getForObject(uri, JsonNode.class);
        return deserializeList(responseNode, null, Post.class);
    }

    @Override
    public List<Post> searchHomeFeed(String query) {
        return searchHomeFeed(query, 0, 25);
    }

    @Override
    public List<Post> searchHomeFeed(String query, int offset, int limit) {
        Map<String, Object> params = newHashMap();
        params.put("q", query);
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(limit));
        String uri = buildUri("https://graph.facebook.com/me/home", params);
        JsonNode responseNode = getService().getForObject(uri, JsonNode.class);
        return deserializeList(responseNode, null, Post.class);
    }

    @Override
    public List<Post> searchUserFeed(String query) {
        return searchUserFeed("me", query, 0, 25);
    }

    @Override
    public List<Post> searchUserFeed(String query, int offset, int limit) {
        return searchUserFeed("me", query, offset, limit);
    }

    @Override
    public List<Post> searchUserFeed(String userId, String query) {
        return searchUserFeed(userId, query, 0, 25);
    }

    @Override
    public List<Post> searchUserFeed(String userId, String query, int offset, int limit) {
        Map<String, Object> params = newHashMap();
        params.put("q", query);
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(limit));
        String uri = buildUri("https://graph.facebook.com/" + userId + "/feed", params);
        JsonNode responseNode = getService().getForObject(uri, JsonNode.class);
        return deserializeList(responseNode, null, Post.class);
    }

    // private helpers

    private JsonNode fetchConnectionList(String baseUri, int offset, int limit) {
        Map<String, Object> params = newHashMap();
        params.put("offset", String.valueOf(offset));
        params.put("limit", String.valueOf(limit));
        String uri = buildUri(baseUri, params);
        JsonNode responseNode = getService().getForObject(uri, JsonNode.class);
        return responseNode;
    }

    private <T> List<T> deserializeList(JsonNode jsonNode, String postType, Class<T> type) {
        JsonNode dataNode = jsonNode.get("data");
        List<T> posts = new ArrayList<T>();
        for (Iterator<JsonNode> iterator = dataNode.iterator(); iterator.hasNext();) {
            posts.add(deserializePost(postType, type, (ObjectNode) iterator.next()));
        }
        return posts;
    }

    private <T> T deserializePost(String postType, Class<T> type, ObjectNode node) {
        try {
            if (postType == null) {
                postType = determinePostType(node);
            }
            // Must have separate postType field for polymorphic deserialization. If we key off of the "type" field, then it
            // will
            // be null when trying to deserialize the type property.
            node.put("postType", postType); // used for polymorphic deserialization
            node.put("type", postType); // used to set Post's type property
            return objectMapper.readValue(node, type);
        } catch (IOException shouldntHappen) {
            throw new SeamSocialException("Error deserializing " + postType + " post", shouldntHappen);
        }
    }

    private String determinePostType(ObjectNode node) {
        if (node.has("type")) {
            try {
                String type = node.get("type").getTextValue();
                PostType.valueOf(type.toUpperCase());
                return type;
            } catch (IllegalArgumentException e) {
                return "post";
            }
        }
        return "post";
    }

}
