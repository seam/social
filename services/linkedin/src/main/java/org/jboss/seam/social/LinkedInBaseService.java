/**
 * 
 */
package org.jboss.seam.social;

import java.lang.annotation.Annotation;

import org.jboss.seam.social.AbstractSocialNetworkService;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public abstract class LinkedInBaseService extends AbstractSocialNetworkService {

    private static String API_ROOT = "https://api.linkedin.com/v1/";

    protected static final String BASE_URL = "https://api.linkedin.com/v1/people/";

    @Override
    public Annotation getQualifier() {
        return LinkedInLiteral.INSTANCE;
    }

    @Override
    public String getApiRootUrl() {
        return API_ROOT;
    }

}
