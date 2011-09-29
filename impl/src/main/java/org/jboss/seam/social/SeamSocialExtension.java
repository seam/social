/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessManagedBean;

import org.jboss.seam.logging.Logger;
import org.jboss.seam.social.cdi.RelatedTo;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceSettings;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@ApplicationScoped
public class SeamSocialExtension implements Extension {

    private Set<String> servicesNames = new HashSet<String>();
    private Map<AnnotatedType<? extends OAuthService>, String> servicesBean = new HashMap<AnnotatedType<? extends OAuthService>, String>();
    private static final Logger log = Logger.getLogger(SeamSocialExtension.class);

    public void processSettingsBeans(@Observes ProcessBean<OAuthServiceSettings> pbean) {

        log.info("Starting enumeration of existing service settings");
        Annotated annotated = pbean.getAnnotated();

        if (annotated.isAnnotationPresent(RelatedTo.class)) {
            RelatedTo related = annotated.getAnnotation(RelatedTo.class);
            String name = related.value();

            log.infof("Found configuration for service %s", name);
            servicesNames.add(name);

        }
    }

    /**
     * This Listener build the List of existing OAuthServices with a RelatedTo Qualifier
     * 
     * @param pbean
     */
    public void processServicesBeans(@Observes ProcessManagedBean<OAuthService> pbean) {
        Annotated annotated = pbean.getAnnotated();
        if (annotated.isAnnotationPresent(RelatedTo.class)) {
            RelatedTo related = annotated.getAnnotation(RelatedTo.class);
            String name = related.value();
            servicesBean.put(pbean.getAnnotatedBeanClass(), name);
        }
    }

    /**
     * This listener register a new Bean without Qualifier for each bean with RalatedTo qualifier
     * 
     * @param abd
     * @param bm
     * @SuppressWarnings({ "unchecked", "rawtypes" }) public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd,
     *                     BeanManager bm) { for (String type : servicesBean.keySet()) { AnnotatedType<? extends OAuthService>
     *                     annotatedType = servicesBean.get(type);
     * 
     *                     AnnotatedTypeBuilder annoBuilder = new
     *                     AnnotatedTypeBuilder().readFromType(annotatedType).removeFromClass( RelatedTo.class); AnnotatedType
     *                     myAnnotatedType = annoBuilder.create();
     * 
     *                     BeanBuilder beanBuilder = new BeanBuilder(bm).readFromType(myAnnotatedType);
     *                     abd.addBean(beanBuilder.create()); } }
     */
    public Set<String> getSocialRelated() {
        return servicesNames;
    }

}
