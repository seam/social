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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessProducer;

import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.solder.logging.Logger;
import org.jboss.solder.reflection.AnnotationInspector;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@ApplicationScoped
public class SeamSocialExtension implements Extension {

    private Set<String> servicesNames = new HashSet<String>();
    private Set<Annotation> servicesQualifiersConfigured = new HashSet<Annotation>();
    private Set<Annotation> servicesQualifiersAvailable = new HashSet<Annotation>();
    private BiMap<Annotation, String> servicesToQualifier = HashBiMap.create();
    private Map<Type, Annotation> classToQualifier = new HashMap<Type, Annotation>();

    // private Map<AnnotatedType<? extends OAuthService>, String> servicesBean = new HashMap<AnnotatedType<? extends
    // OAuthService>, String>();

    private static final Logger log = Logger.getLogger(SeamSocialExtension.class);

    public void processSettingsBeans(@Observes ProcessBean<OAuthServiceSettings> pbean, BeanManager beanManager) {

        log.info("Starting enumeration of existing service settings");
        Annotated annotated = pbean.getAnnotated();

        servicesQualifiersConfigured.addAll(AnnotationInspector.getAnnotations(annotated, ServiceRelated.class));
    }

    /**
     * This Listener build the List of existing OAuthServices with a Qualifier having the meta annotation @ServiceRelated
     * 
     * @param pbean
     */

    public void processServicesBeans(@Observes ProcessProducer<?, OAuthService> pbean) {
        Annotated annotated = pbean.getAnnotatedMember();
        Type type = annotated.getBaseType();
        Set<Annotation> qualifiers = AnnotationInspector.getAnnotations(annotated, ServiceRelated.class);
        servicesQualifiersAvailable.addAll(qualifiers);
        if (qualifiers.size() > 0)
            if (qualifiers.size() > 1)
                log.errorf("The bean of type %s has more than one Service Related Qualifier", type);
            else
                classToQualifier.put(type, qualifiers.iterator().next());
    }

    public Set<String> getSocialRelated() {
        return servicesNames;
    }

    public void processAfterDeploymentValidation(@Observes AfterDeploymentValidation adv) {
        log.info("validation phase");
        for (Annotation qual : servicesQualifiersAvailable) {
            String path = qual.annotationType().getName();
            String name = "";
            log.infof("Found service qualifier : %s", path);
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(path);
                name = bundle.getString("service.name");
            } catch (MissingResourceException e) {
                log.warnf("No properties configuration file found for %s creating default service name", path);
                name = StringUtils.substringAfterLast(path, ".");
            } finally {
                servicesToQualifier.put(qual, name);
            }

        }
        for (Annotation annot : servicesQualifiersAvailable) {
            servicesNames.add(servicesToQualifier.get(annot));
        }

    }

    public BiMap<Annotation, String> getServicesToQualifier() {
        return servicesToQualifier;
    }

    public Map<Type, Annotation> getClassToQualifier() {
        return classToQualifier;
    }

}
