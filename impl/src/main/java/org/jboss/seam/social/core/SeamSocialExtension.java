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
package org.jboss.seam.social.core;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import org.jboss.logging.Logger;

/**
 * @author Antoine Sabot-Durand
 * 
 */
@ApplicationScoped
public class SeamSocialExtension implements Extension {

    private Set<String> servicesNames = new HashSet<String>();
    private static final Logger log = Logger.getLogger(SeamSocialExtension.class);

    // public void processSetting(@Observes ProcessBean<TwitterTest> event, BeanManager manager)
    // {
    //
    // for (InjectionPoint ip : event.getBean().getInjectionPoints())
    // {
    // Setted setting=AnnotationInspector.getAnnotation(ip.getAnnotated(),Setted.class, manager);
    //
    //
    // if(setting != null)
    // {
    //
    // String api = setting.apiKey();
    // String secret = setting.apiSecret();
    // String callBack = setting.callback();
    // try {
    // Class clazz=(Class) ip.getType();
    // String type = (String) clazz.getField("TYPE").get(null);
    // System.out.println(type);
    // } catch (SecurityException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (NoSuchFieldException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IllegalArgumentException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    //
    // }
    // }
    // }

    public void processBeans(@Observes ProcessBean<OAuthServiceSettings> pbean, BeanManager beanManager) {

        log.info("Starting enumeration of existing service settings");
        Annotated anno = pbean.getAnnotated();
        if (anno.isAnnotationPresent(RelatedTo.class)) {
            String name = anno.getAnnotation(RelatedTo.class).value();

            log.infof("Found configuration for service %s", name);
            servicesNames.add(anno.getAnnotation(RelatedTo.class).value());
        }
    }

    public Set<String> getSocialRelated() {
        return servicesNames;
    }

}
