/**
 * 
 */
package org.jboss.seam.social.facebook;

import java.lang.annotation.Annotation;

import org.jboss.seam.social.AbstractSocialNetworkService;
import org.jboss.seam.social.FacebookLiteral;

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
