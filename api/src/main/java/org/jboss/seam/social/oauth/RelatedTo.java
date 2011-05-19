/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
/**
 * @author antoine
 *
 */
public @interface RelatedTo {
    
    Service value();
    
    /**
     * Annotation literal for @{link RelatedTo} qualifier.
     */
    @SuppressWarnings("all")
    public static class RelatedToLiteral extends AnnotationLiteral<RelatedTo> implements RelatedTo
    {

        private final Service value;
        
        public RelatedToLiteral(Service value) {
            super();
            this.value = value;
        }

        /* (non-Javadoc)
         * @see org.jboss.seam.social.oauth.RelatedTo#service()
         */
        @Override
        public Service value() {
           return value;
        }
        
    }

}
