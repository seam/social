/**
 * 
 */
package org.jboss.seam.social;

import java.lang.annotation.Annotation;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class FacebookBaseService extends AbstractSocialNetworkService {

    @Override
    public Annotation getQualifier() {
        return FacebookLiteral.INSTANCE;
    }

    @Override
    public String getApiRootUrl() {
        return "";
    }

}
