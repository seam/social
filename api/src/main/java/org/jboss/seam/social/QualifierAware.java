/**
 * 
 */
package org.jboss.seam.social;

import java.lang.annotation.Annotation;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public interface QualifierAware {

    /**
     * Returns the Qualifier used for this social network
     * 
     * @return Annotation being a Qualifier
     */
    public Annotation getQualifier();

}
