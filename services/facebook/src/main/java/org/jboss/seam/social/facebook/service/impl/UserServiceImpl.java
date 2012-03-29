/**
 * 
 */
package org.jboss.seam.social.facebook.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;
import org.jboss.seam.social.facebook.FacebookBaseService;
import org.jboss.seam.social.facebook.model.FacebookProfile;
import org.jboss.seam.social.facebook.model.ImageType;
import org.jboss.seam.social.facebook.model.Reference;
import org.jboss.seam.social.facebook.service.GraphApi;
import org.jboss.seam.social.facebook.service.UserService;

import com.google.common.collect.Maps;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class UserServiceImpl extends FacebookBaseService implements UserService {

    @Inject
    private GraphApi graphApi;

    @Override
    public FacebookProfile getUserProfile() {
        return getUserProfile("me");
    }

    @Override
    public FacebookProfile getUserProfile(String facebookId) {
        return graphApi.fetchObject(facebookId, FacebookProfile.class);
    }

    @Override
    public byte[] getUserProfileImage() {
        ;
        return getUserProfileImage("me", ImageType.NORMAL);
    }

    @Override
    public byte[] getUserProfileImage(String userId) {
        return getUserProfileImage(userId, ImageType.NORMAL);
    }

    @Override
    public byte[] getUserProfileImage(ImageType imageType) {
        ;
        return getUserProfileImage("me", imageType);
    }

    @Override
    public byte[] getUserProfileImage(String userId, ImageType imageType) {
        return graphApi.fetchImage(userId, "picture", imageType);
    }

    @Override
    public List<String> getUserPermissions() {
        JsonNode responseNode = getService().getForObject("https://graph.facebook.com/me/permissions", JsonNode.class);
        return deserializePermissionsNodeToList(responseNode);
    }

    @Override
    public List<Reference> search(String query) {
        Map<String, String> queryMap = Maps.newHashMap();
        queryMap.put("q", query);
        queryMap.put("type", "user");
        return graphApi.fetchConnections("search", null, Reference.class, queryMap);
    }

    private List<String> deserializePermissionsNodeToList(JsonNode jsonNode) {
        JsonNode dataNode = jsonNode.get("data");
        List<String> permissions = new ArrayList<String>();
        for (Iterator<JsonNode> elementIt = dataNode.getElements(); elementIt.hasNext();) {
            JsonNode permissionsElement = elementIt.next();
            for (Iterator<String> fieldNamesIt = permissionsElement.getFieldNames(); fieldNamesIt.hasNext();) {
                permissions.add(fieldNamesIt.next());
            }
        }
        return permissions;
    }

}
