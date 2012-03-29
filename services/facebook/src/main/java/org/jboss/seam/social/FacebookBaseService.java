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
public class FacebookBaseService extends AbstractSocialNetworkService {

    @Override
    public Annotation getQualifier() {
        return FacebookLiteral.INSTANCE;
    }

    @Override
    public String getApiRootUrl() {
        return null;
    }

}
