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

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthServiceSettings;

/**
 * @author Antoine Sabot-Durand
 */

public class OAuthServiceSettingsImpl implements OAuthServiceSettings, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8018722725677732853L;

    private String apiKey;

    private String apiSecret;

    private String callback;

    private String scope;

    private String serviceName;

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    @Override
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    @Override
    public String getCallback() {
        return callback;
    }

    @Override
    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Inject
    public OAuthServiceSettingsImpl(InjectionPoint ip, SeamSocialExtension config) {

        for (Annotation quali : ip.getQualifiers()) {
            if (quali.annotationType().isAnnotationPresent(ServiceRelated.class))
                this.serviceName = config.getServicesToQualifier().get(quali);
        }
    }

    OAuthServiceSettingsImpl(String apiKey, String apiSecret, String callback, String scope, String serviceName) {
        super();
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.callback = callback;
        this.scope = scope;
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "OAuthServiceSettingsImpl [apiKey=" + apiKey + ", apiSecret=" + apiSecret + ", callback=" + callback
                + ", scope=" + scope + ", serviceName=" + serviceName + "]";
    }

}
