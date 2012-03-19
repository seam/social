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
package org.jboss.seam.social.oauth;

import java.lang.annotation.Annotation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.seam.social.SocialNetworkServicesHub;
import org.jboss.seam.social.scribe.OAuthProviderScribe;
import org.jboss.solder.bean.generic.ApplyScope;
import org.jboss.solder.bean.generic.Generic;
import org.jboss.solder.bean.generic.GenericConfiguration;
import org.jboss.solder.logging.Logger;

/**
 * @author Antoine
 * 
 */
@GenericConfiguration(OAuthApplication.class)
public class OAuthGenericManager {

    Annotation qual;

    @Inject
    @Generic
    SocialNetworkServicesHub servicesHub;

    @Inject
    @Generic
    OAuthApplication app;

    @Inject
    Logger log;

    @Produces
    @ApplyScope
    protected OAuthService produceService(OAuthServiceImpl service) {
        service.setQualifier(qual);
        return service;
    }

    @Produces
    @ApplyScope
    OAuthServiceSettings settings;

    @Produces
    @ApplyScope
    public OAuthProvider produceProvider() {
        return new OAuthProviderScribe(settings);
    }

    @Produces
    @SessionScoped
    public OAuthSession produceSession() {
        return new OAuthSessionImpl(qual);
    }

    @PostConstruct
    void init() {
        log.info("****** in OAuthGenericManager ");
        String apiKey = app.apiKey();
        String apiSecret = app.apiSecret();
        String callback = app.callback();
        String scope = app.scope();
        qual = servicesHub.getQualifier();
        settings = new OAuthServiceSettingsImpl(qual, apiKey, apiSecret, callback, scope);
    }

}
