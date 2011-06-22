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

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;


/**
 * @author Antoine Sabot-Durand
 */

public abstract class OAuthServiceScribe implements OAuthService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8423894021913341674L;
    private static final String VERIFIER_PARAM_NAME = "oauth_verifier";

    private org.scribe.oauth.OAuthService service;

    private OAuthServiceSettings settings;

    @Inject
    protected OAuthSessionSettings session;

    @Override
    public OAuthSessionSettings getSession() {
        return session;
    }

    @Override
    public void setSession(OAuthSessionSettings session) {
        this.session = session;
    }

    public String getStatus() {
        return session.getStatus();
    }

    public void setStatus(String status) {
        this.session.setStatus(status);
    }

    protected org.scribe.oauth.OAuthService getService() {
        if (service == null)
            initService();
        return service;
    }

    private void initService() {
        Class<? extends Api> apiClass = getApiClass();
        ServiceBuilder serviceBuilder = new ServiceBuilder().provider(apiClass).apiKey(getSettings().getApiKey())
                .apiSecret(getSettings().getApiSecret());
        if (getSettings().getCallback() != null && !("".equals(getSettings().getCallback())))
            serviceBuilder.callback(getSettings().getCallback());
        if (getSettings().getScope()!=null && !("".equals(getSettings().getScope()))){
            serviceBuilder.scope(getSettings().getScope());
        }
        service = serviceBuilder.build();

    }

    @Override
    public String getName()
    {
        return getType() + " - " + getSession().getUserProfile().getFullName();
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthServiceScribe#getSettings()
     */
    @Override
    public OAuthServiceSettings getSettings() {
        return settings;
    }

    public void setSettings(OAuthServiceSettings settings) {
        this.settings = settings;
    }

    protected abstract Class<? extends Api> getApiClass();

    @Override
    public String getAuthorizationUrl() {
        return getService().getAuthorizationUrl(getScribeRequestToken());
    }

    protected OAuthToken getRequestToken() {
        if (session.getRequestToken() == null)
            session.setRequestToken(new OAuthTokenScribe(getService().getRequestToken()));
        return session.getRequestToken();
    }

    @Override
    public void initAccessToken() {
        if (session.getAccessToken() == null) {
            session.setAccessToken(new OAuthTokenScribe(getService().getAccessToken(getScribeRequestToken(), new Verifier(session.getVerifier()))));
            if (session.getAccessToken() != null) {
                session.setConnected(Boolean.TRUE);
                session.setRequestToken(null);
                session.setUserProfile(getUser());
                
            } else {
                // Launch an exception !!
            }
        }

    }

    @Override
    public void resetConnection() {
        session.setUserProfile(null);
        session.setAccessToken(null);
        session.setVerifier(null);
        session.setConnected(Boolean.FALSE);

    }

    protected HttpResponse sendSignedRequest(OAuthRequest request) {
        getService().signRequest(getScribeAccessToken(), request);
        HttpResponse resp = null;
        try {
            resp = new HttpResponseScribe(request.send());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri) {
        OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);

        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri, String key, Object value) {
        OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);

        request.addBodyParameter(key, value.toString());

        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedXmlRequest(RestVerb verb, String uri, String payload) {
        OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);
        request.addHeader("Content-Length", Integer.toString(payload.length()));
        request.addHeader("Content-Type", "text/xml");

        request.addPayload(payload);

        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params) {
        OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);
        for (Entry<String, Object> ent : params.entrySet()) {
            request.addBodyParameter(ent.getKey(), ent.getValue().toString());
        }
        return sendSignedRequest(request);

    }

    @Override
    public void setVerifier(String verifierStr) {
        session.setVerifier(verifierStr);
    }

    @Override
    public String getVerifier() {
        return session.getVerifier();
    }

    @Override
    public OAuthToken getAccessToken() {
        return session.getAccessToken();
    }

    protected Token getScribeAccessToken()
    {
        return ((OAuthTokenScribe)getAccessToken()).delegate;
    }
    
    protected Token getScribeRequestToken()
    {
        return ((OAuthTokenScribe)getRequestToken()).delegate;
    }
    
    
    
    @Override
    public Boolean isConnected() {
        return session.isConnected();
    }

    @Override
    public void setAccessToken(String token, String secret) {
        session.setAccessToken(new OAuthTokenScribe(token, secret));

    }

    @Override
    public void setAccessToken(OAuthToken token) {
        session.setAccessToken(new OAuthTokenScribe(token.getToken(), token.getSecret()));

    }

    @Override
    public String toString() {
        return getType();
    }

    @Override
    public String getVerifierParamName() {
        return VERIFIER_PARAM_NAME;
    }

    /**
     * 
     */
    protected abstract UserProfile getUser();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((session == null) ? 0 : session.hashCode());
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
        OAuthServiceScribe other = (OAuthServiceScribe) obj;
        if (session == null) {
            if (other.session != null)
                return false;
        } else if (!session.equals(other.session))
            return false;
        return true;
    }

    
    
    
}
