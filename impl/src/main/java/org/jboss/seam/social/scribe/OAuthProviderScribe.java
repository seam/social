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
package org.jboss.seam.social.scribe;

import org.jboss.seam.social.RestVerb;
import org.jboss.seam.social.SeamSocialException;
import org.jboss.seam.social.oauth.OAuthProvider;
import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.OAuthToken;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class OAuthProviderScribe implements OAuthProvider {

    private static final String SCRIBE_API_PREFIX = "org.scribe.builder.api.";
    private static final String SCRIBE_API_SUFFIX = "Api";

    private org.scribe.oauth.OAuthService service;

    org.scribe.oauth.OAuthService getService() {
        if (service == null)
            throw new IllegalStateException("OAuthProvider can be used before it is initialized");
        return service;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#getRequestToken()
     */
    @Override
    public OAuthToken getRequestToken() {
        return new OAuthTokenScribe("2.0".equals(getVersion()) ? null : getService().getRequestToken());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#getAccessToken(org.jboss.seam.social.oauth.OAuthToken, java.lang.String)
     */
    @Override
    public OAuthToken getAccessToken(OAuthToken requestToken, String verifier) {
        return createToken(getService().getAccessToken(extractToken(requestToken), new Verifier(verifier)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#signRequest(org.jboss.seam.social.oauth.OAuthToken,
     * org.jboss.seam.social.oauth.OAuthRequest)
     */
    @Override
    public void signRequest(OAuthToken accessToken, OAuthRequest request) {
        getService().signRequest(extractToken(accessToken), ((OAuthRequestScribe) request).getDelegate());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#getVersion()
     */
    @Override
    public String getVersion() {
        return getService().getVersion();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#getAuthorizationUrl(org.jboss.seam.social.oauth.OAuthToken)
     */
    @Override
    public String getAuthorizationUrl(OAuthToken tok) {
        return getService().getAuthorizationUrl(extractToken(tok));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#initProvider(org.jboss.seam.social.oauth.OAuthServiceSettings)
     */
    @Override
    public void initProvider(OAuthServiceSettings settings) {
        Class<? extends Api> apiClass = getApiClass(settings.getServiceName());
        ServiceBuilder serviceBuilder = new ServiceBuilder().provider(apiClass).apiKey(settings.getApiKey())
                .apiSecret(settings.getApiSecret());
        if (settings.getCallback() != null && !("".equals(settings.getCallback())))
            serviceBuilder.callback(settings.getCallback());
        if (settings.getScope() != null && !("".equals(settings.getScope()))) {
            serviceBuilder.scope(settings.getScope());
        }
        service = serviceBuilder.build();

    }

    /**
     * @param serviceName
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<? extends Api> getApiClass(String serviceName) {
        String className = SCRIBE_API_PREFIX + serviceName + SCRIBE_API_SUFFIX;
        try {
            return (Class<? extends Api>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new SeamSocialException("There no such Scribe Api class " + className, e);
        }
    }

    protected Token extractToken(OAuthToken tok) {
        return ((OAuthTokenScribe) tok).delegate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#createRequest(org.jboss.seam.social.oauth.RestVerb, java.lang.String)
     */
    @Override
    public OAuthRequest requestFactory(RestVerb verb, String uri) {
        return new OAuthRequestScribe(verb, uri);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthProvider#createToken(java.lang.String, java.lang.String)
     */
    @Override
    public OAuthToken tokenFactory(String token, String secret) {
        return new OAuthTokenScribe(token, secret);
    }

    private OAuthToken createToken(Token token) {
        return new OAuthTokenScribe(token);
    }
}
