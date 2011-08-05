/**
 * 
 */
package org.jboss.seam.social.core;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.codehaus.jackson.map.Module;

/**
 * @author antoine
 * 
 */

public abstract class OAuthServiceJackson extends OAuthServiceBase {

    /**
     * @param injectionPoint
     * 
     *        protected OAuthServiceJackson(InjectionPoint injectionPoint) { super(injectionPoint); }
     */

    /**
     * 
     */
    private static final long serialVersionUID = -7806134655399349774L;

    @Inject
    protected JsonMapper jsonMapper;

    @PostConstruct
    protected void init() {
        super.init();
        Module module = getJacksonModule();
        if (module != null)
            jsonMapper.registerModule(module);
    }

    protected abstract Module getJacksonModule();

}
