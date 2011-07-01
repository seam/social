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

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.logging.Logger;





/**
 * @author Antoine Sabot-Durand
 */

public abstract class OAuthServiceBase implements OAuthService, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8423894021913341674L;
    private static final String VERIFIER_PARAM_NAME = "oauth_verifier";

    private OAuthServiceSettings settings;
    
    @Inject
    private OAuthProvider provider;
    
    @Inject @Any
    protected Instance<OAuthServiceSettings> settingsInstances;
   

    @Inject
    private Logger log;
    
    @Inject
    protected OAuthSessionSettings session;
    
    @PostConstruct
    protected void postConstruct()
    {
         String type=getType();
         try {     
            setSettings(settingsInstances.select(new RelatedTo.RelatedToLiteral(type)).get());
        } catch (Exception e) {
           throw new SeamSocialException("Unable to find settings for service " + type,e);
           // TODO : later we can provide another way to get those settings (properties, jpa, etc...)
        }
    }

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

    @Override
    public String getName()
    {
        return getType() + " - " + (getSession().isConnected() ? getSession().getUserProfile().getFullName() : "not connected");
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthServiceBase#getSettings()
     */
    @Override
    public OAuthServiceSettings getSettings() {
        return settings;
    }

    public void setSettings(OAuthServiceSettings settings) {
        this.settings = settings;
        provider.initProvider(settings);
    }

    @Override
    public String getAuthorizationUrl() {
        return getService().getAuthorizationUrl(getRequestToken());
    }

    /**
     * @return
     */
    private OAuthProvider getService() {
        return provider;
    }

    protected OAuthToken getRequestToken() {
        if (session.getRequestToken() == null)
            session.setRequestToken(getService().getRequestToken());
        return session.getRequestToken();
    }

    @Override
    public void initAccessToken() {
        if (session.getAccessToken() == null) {
            session.setAccessToken(getService().getAccessToken(getRequestToken(), session.getVerifier()));
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
        getService().signRequest(getAccessToken(), request);
        return request.send();
    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri) {
        OAuthRequest request = provider.createRequest(verb, uri);
        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri, String key, Object value) {
        OAuthRequest request = provider.createRequest(verb, uri);

        request.addBodyParameter(key, value.toString());

        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedXmlRequest(RestVerb verb, String uri, String payload) {
        OAuthRequest request = provider.createRequest(verb, uri);
        request.addHeader("Content-Length", Integer.toString(payload.length()));
        request.addHeader("Content-Type", "text/xml");

        request.addPayload(payload);

        return sendSignedRequest(request);

    }

    @Override
    public HttpResponse sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params) {
        OAuthRequest request = provider.createRequest(verb, uri);
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

   
    
    
    @Override
    public Boolean isConnected() {
        return session.isConnected();
    }

    @Override
    public void setAccessToken(String token, String secret) {
        session.setAccessToken(provider.createToken(token, secret));

    }

    @Override
    public void setAccessToken(OAuthToken token) {
        session.setAccessToken(token);

    }

    @Override
    public String toString() {
        return getName();
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
        OAuthServiceBase other = (OAuthServiceBase) obj;
        if (session == null) {
            if (other.session != null)
                return false;
        } else if (!session.equals(other.session))
            return false;
        return true;
    }

    
}
