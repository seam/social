/**
 * 
 */
package org.jboss.seam.social;

import java.util.Map;

import org.jboss.seam.social.utils.URLUtils;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public abstract class AbstractSocialNetworkService extends AbstractOAuthServiceAwareImpl {

    public String buildUri(String url, String key, String value) {
        return URLUtils.buildUri(buildUri(url), key, value);
    }

    /**
     * @param searchUserUrl
     * @param parameters
     * @return
     */
    public String buildUri(String url, Map<String, ? extends Object> parameters) {
        return URLUtils.buildUri(buildUri(url), parameters);
    }

    public abstract String getApiRootUrl();

    public String buildUri(String url) {
        return getApiRootUrl() + url;
    }

    public String buildUri(String url, Object pojo) {
        return URLUtils.buildUri(buildUri(url), pojo);
    }

}
