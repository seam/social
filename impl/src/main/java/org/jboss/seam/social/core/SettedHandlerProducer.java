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

import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.seam.social.core.OAuthService;
import org.jboss.seam.social.core.OAuthServiceSettings;
import org.jboss.seam.social.core.Setted;

/**
 * @author Antoine Sabot-Durand
 */
public abstract class SettedHandlerProducer {

    protected <T extends OAuthService> T setService(InjectionPoint ip, T hdl) {
        if (ip == null || ip.getAnnotated() == null || hdl == null)
            return null;
        Setted setted = ip.getAnnotated().getAnnotation(Setted.class);
        OAuthServiceSettings settings = hdl.getSettings();

        String apiKey = setted.apiKey();
        String apiSecret = setted.apiSecret();
        String callback = setted.callback();

        settings.setApiKey(apiKey);
        settings.setApiSecret(apiSecret);
        settings.setCallback(callback);

        return hdl;
    }

}
