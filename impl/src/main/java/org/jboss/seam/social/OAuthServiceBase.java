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

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.seam.social.oauth.OAuthConfiguration;
import org.jboss.seam.social.oauth.OAuthProvider;
import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.OAuthSessionSettings;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.seam.social.rest.RestResponse;
import org.jboss.seam.social.rest.RestVerb;
import org.jboss.solder.logging.Logger;

/**
 * This Abstract implementation of {@link OAuthService} uses an {@link OAuthProvider} to deal with remote OAuth Services
 * 
 * 
 * @author Antoine Sabot-Durand
 */

public abstract class OAuthServiceBase implements OAuthService, HasStatus {

    private static final long serialVersionUID = -8423894021913341674L;
    private static final String VERIFIER_PARAM_NAME = "oauth_verifier";

    private OAuthServiceSettings settings;

    @Inject
    private OAuthProvider provider;

    @Inject
    private Logger log;

    @Inject
    protected OAuthSessionSettings sessionSettings;

    @Inject
    @Any
    private Instance<OAuthServiceSettings> settingsInstances;

    @Inject
    protected InjectionPoint ip;

    @Inject
    protected SeamSocialExtension socialConfig;

    private String type;
    private Annotation qualifier;

    protected UserProfile myProfile;

    private boolean connected = false;
    private String status;

    /**
     * This method tries to find a configuration for the current service. It does it in this order :
     * <ol>
     * <li>looks for an {@link OAuthConfiguration} annotation to get value in it and build an {@link OAuthServiceSettings} from
     * these</li>
     * <li>looks for a {@link OAuthServiceSettings} bean with the same service qualifier (having the {@link ServiceRelated} meta
     * annotation)</li>
     * </ol>
     * 
     * 
     */
    // TODO : later we should replace this by a SettingsResolver Bean
    protected void init() {

        OAuthServiceSettings setting;

        if (ip.getAnnotated().isAnnotationPresent(OAuthConfiguration.class)) {
            OAuthConfiguration oAuthConfiguration = ip.getAnnotated().getAnnotation(OAuthConfiguration.class);

            String apiKey = oAuthConfiguration.apiKey();
            String apiSecret = oAuthConfiguration.apiSecret();
            String callback = oAuthConfiguration.callback();
            String scope = oAuthConfiguration.scope();
            setting = new OAuthServiceSettingsImpl(apiKey, apiSecret, callback, scope, getType());
        } else
            try {
                setting = settingsInstances.select(getQualifier()).get();
            } catch (Exception e) {
                throw new SeamSocialException("Unable to find settings for service " + getType(), e);
                // TODO later we can provide another way to get those settings (properties, jpa, etc...)
            }

        setSettings(setting);

        provider.initProvider(settings);
    }

    public String getType() {
        if (StringUtils.isEmpty(type))
            type = socialConfig.getServicesToQualifier().get(getQualifier());
        return type;
    }

    @Override
    public OAuthSessionSettings getSession() {
        return sessionSettings;
    }

    @Override
    public void setSession(OAuthSessionSettings session) {
        this.sessionSettings = session;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return getType() + " - " + (connected ? getMyProfile().getFullName() : "not connected");
    }

    @Override
    public OAuthServiceSettings getSettings() {
        return settings;
    }

    public void setSettings(OAuthServiceSettings settings) {
        this.settings = settings;
    }

    @Override
    public String getAuthorizationUrl() {
        return getProvider().getAuthorizationUrl(getRequestToken());
    }

    private OAuthProvider getProvider() {
        return provider;
    }

    protected OAuthToken getRequestToken() {
        if (sessionSettings.getRequestToken() == null)
            sessionSettings.setRequestToken(getProvider().getRequestToken());
        return sessionSettings.getRequestToken();
    }

    @Override
    public void initAccessToken() {
        if (sessionSettings.getAccessToken() == null)
            sessionSettings.setAccessToken(getProvider().getAccessToken(getRequestToken(), sessionSettings.getVerifier()));
        if (sessionSettings.getAccessToken() != null) {
            connected = true;
            sessionSettings.setRequestToken(null);
            initMyProfile();
            // TODO Should we fire an event ?
        } else {
            // FIXME Launch an exception !!
        }

    }

    abstract protected void initMyProfile();

    @Override
    public void resetConnection() {
        sessionSettings.setAccessToken(null);
        sessionSettings.setVerifier(null);
        connected = false;
        myProfile = null;

    }

    protected RestResponse sendSignedRequest(OAuthRequest request) {
        getProvider().signRequest(getAccessToken(), request);
        return request.send();
    }

    @Override
    public RestResponse sendSignedRequest(RestVerb verb, String uri) {
        OAuthRequest request = provider.requestFactory(verb, uri);
        return sendSignedRequest(request);

    }

    @Override
    public RestResponse sendSignedRequest(RestVerb verb, String uri, String key, Object value) {
        OAuthRequest request = provider.requestFactory(verb, uri);

        request.addBodyParameter(key, value.toString());

        return sendSignedRequest(request);

    }

    @Override
    public RestResponse sendSignedXmlRequest(RestVerb verb, String uri, String payload) {
        OAuthRequest request = provider.requestFactory(verb, uri);
        /*
         * Useless with Scribe 1.2.3 //TODO have a test on Facebook and LinkedIn to check that
         * 
         * request.addHeader("Content-Length", Integer.toString(payload.length())); request.addHeader("Content-Type",
         * "text/xml");
         */

        request.addPayload(payload);

        return sendSignedRequest(request);

    }

    @Override
    public RestResponse sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params) {
        OAuthRequest request = provider.requestFactory(verb, uri);
        for (Entry<String, Object> ent : params.entrySet()) {
            request.addBodyParameter(ent.getKey(), ent.getValue().toString());
        }
        return sendSignedRequest(request);

    }

    @Override
    public void setVerifier(String verifierStr) {
        sessionSettings.setVerifier(verifierStr);
    }

    @Override
    public String getVerifier() {
        return sessionSettings.getVerifier();
    }

    @Override
    public OAuthToken getAccessToken() {
        return sessionSettings.getAccessToken();
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    protected void requireAuthorization() {
        if (!isConnected()) {
            throw new SeamSocialException("This action requires an OAuth connexion");
        }
    }

    @Override
    public void setAccessToken(String token, String secret) {
        sessionSettings.setAccessToken(provider.tokenFactory(token, secret));

    }

    @Override
    public void setAccessToken(OAuthToken token) {
        sessionSettings.setAccessToken(token);

    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getVerifierParamName() {
        return VERIFIER_PARAM_NAME;
    }

    @Override
    public UserProfile getMyProfile() {
        return myProfile;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myProfile == null) ? 0 : myProfile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OAuthServiceBase other = (OAuthServiceBase) obj;
        if (myProfile == null) {
            if (other.myProfile != null)
                return false;
        } else if (!myProfile.equals(other.myProfile))
            return false;
        return true;
    }

    @Override
    public Annotation getQualifier() {
        if (qualifier == null) {
            log.debugf("building the list of direct ancestors (Interface and Class) for bean %s", this.getClass().toString());
            List<Type> allTypes = newArrayList(Arrays.asList(this.getClass().getGenericInterfaces()));
            Type superClass = this.getClass().getGenericSuperclass();
            if (superClass != null)
                allTypes.add(superClass);
            log.debugf("This bean implents or extends %s others type", allTypes.size());
            for (Type type : socialConfig.getClassToQualifier().keySet()) {
                log.debugf("Comparing the type of the bean with %s", type);

                if (allTypes.contains(type)) {
                    log.debugf("Found that bean has type %s", type);

                    qualifier = socialConfig.getClassToQualifier().get(type);
                    break;
                }
            }
            if (qualifier == null)
                throw new SeamSocialException("Unable tho find Service Related Qualifier for bean of class "
                        + this.getClass().toString());
        }
        return qualifier;
    }
}