/**
 * 
 */
package org.jboss.seam.social.facebook.impl;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.jboss.seam.social.FacebookBaseService;
import org.jboss.seam.social.exception.SeamSocialException;
import org.jboss.seam.social.facebook.GraphApi;
import org.jboss.seam.social.facebook.model.ImageType;

import com.google.common.base.Joiner;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class GraphApiImpl extends FacebookBaseService implements GraphApi {

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public <T> T fetchObject(String objectId, Class<T> type) {
        String uri = GRAPH_API_URL + objectId;
        return getService().getForObject(uri, type);
    }

    @Override
    public <T> T fetchObject(String objectId, Class<T> type, Map<String, String> queryParameters) {
        String uri = buildUri(GRAPH_API_URL + objectId, queryParameters);
        return getService().getForObject(uri, type);
    }

    @Override
    public <T> List<T> fetchConnections(String objectId, String connectionType, Class<T> type, String... fields) {
        Map<String, String> queryParameters = newHashMap();
        if (fields.length > 0) {
            String joinedFields = Joiner.on(',').join(fields);
            queryParameters.put("fields", joinedFields);
        }
        return fetchConnections(objectId, connectionType, type, queryParameters);
    }

    @Override
    public <T> List<T> fetchConnections(String objectId, String connectionType, Class<T> type,
            Map<String, String> queryParameters) {
        String connectionPath = connectionType != null && connectionType.length() > 0 ? "/" + connectionType : "";
        String uri = buildUri(GRAPH_API_URL + objectId + connectionPath, queryParameters);
        JsonNode dataNode = getService().getForObject(uri, JsonNode.class);
        return deserializeDataList(dataNode.get("data"), type);
    }

    @Override
    public byte[] fetchImage(String objectId, String connectionType, ImageType type) {
        // String uri = GRAPH_API_URL + objectId + "/" + connectionType + "?type=" + type.toString().toLowerCase();
        // ResponseEntity<byte[]> response = getService().getForEntity(uri, byte[].class);
        // if (response.getStatusCode() == HttpStatus.FOUND) {
        // throw new UnsupportedOperationException(
        // "Attempt to fetch image resulted in a redirect which could not be followed. Add Apache HttpComponents HttpClient to the classpath "
        // + "to be able to follow redirects.");
        // }
        // return response.getBody();
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    @SuppressWarnings("unchecked")
    public String publish(String objectId, String connectionType, Map<String, Object> data) {
        String uri = GRAPH_API_URL + objectId + "/" + connectionType;
        Map<String, Object> response = getService().postForObject(uri, data, Map.class);
        return (String) response.get("id");
    }

    @Override
    public void post(String objectId, String connectionType, Map<String, String> data) {
        String uri = GRAPH_API_URL + objectId + "/" + connectionType;
        getService().postForObject(uri, data, String.class);
    }

    @Override
    public void delete(String objectId) {
        Map<String, String> deleteRequest = newHashMap();
        deleteRequest.put("method", "delete");
        String uri = GRAPH_API_URL + objectId;
        getService().postForObject(uri, deleteRequest, String.class);
    }

    @Override
    public void delete(String objectId, String connectionType) {
        Map<String, String> deleteRequest = newHashMap();
        deleteRequest.put("method", "delete");
        String uri = GRAPH_API_URL + objectId + "/" + connectionType;
        getService().postForObject(uri, deleteRequest, String.class);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> deserializeDataList(JsonNode jsonNode, final Class<T> elementType) {
        try {
            CollectionType listType = TypeFactory.defaultInstance().constructCollectionType(List.class, elementType);
            return (List<T>) objectMapper.readValue(jsonNode, listType);
        } catch (IOException e) {
            throw new SeamSocialException("Error deserializing data from Facebook: " + e.getMessage(), e);
        }
    }

}
