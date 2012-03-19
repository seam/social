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

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jboss.seam.social.exception.SeamSocialRestException;
import org.jboss.seam.social.oauth.OAuthRequest;
import org.jboss.seam.social.rest.RestParameterList;
import org.jboss.seam.social.rest.RestResponse;
import org.jboss.seam.social.rest.RestVerb;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Verb;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class OAuthRequestScribe implements OAuthRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 6560621737726192278L;
    private final org.scribe.model.OAuthRequest request;

    org.scribe.model.OAuthRequest getDelegate() {
        return request;
    }

    OAuthRequestScribe(org.scribe.model.OAuthRequest request) {
        super();
        this.request = request;
    }

    /**
     * @param verb
     * @param uri
     */
    public OAuthRequestScribe(RestVerb verb, String url) {
        request = new org.scribe.model.OAuthRequest(Verb.valueOf(verb.toString()), url);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#addOAuthParameter(java.lang.String, java.lang.String)
     */
    @Override
    public void addOAuthParameter(String key, String value) {
        request.addOAuthParameter(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#send()
     */
    @Override
    public RestResponse send() {

        try {
            return new RestResponseScribe(request.send(), request.getUrl());
        } catch (OAuthException e) {
            throw new SeamSocialRestException(request.getUrl(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getOauthParameters()
     */
    @Override
    public Map<String, String> getOauthParameters() {
        return request.getOauthParameters();
    }

    @Override
    public String toString() {
        return request.toString();
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#addHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void addHeader(String key, String value) {
        request.addHeader(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#addBodyParameter(java.lang.String, java.lang.String)
     */
    @Override
    public void addBodyParameter(String key, String value) {
        request.addBodyParameter(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#addQuerystringParameter(java.lang.String, java.lang.String)
     */
    @Override
    public void addQuerystringParameter(String key, String value) {
        request.addQuerystringParameter(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#addPayload(java.lang.String)
     */
    @Override
    public void addPayload(String payload) {
        request.addPayload(payload);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getQueryStringParams()
     */
    @Override
    public RestParameterList getQueryStringParams() {
        return new ParameterListScribe(request.getQueryStringParams());
    }

    @Override
    public boolean equals(Object obj) {
        return request.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getBodyParams()
     */
    @Override
    public RestParameterList getBodyParams() {
        return new ParameterListScribe(request.getBodyParams());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getUrl()
     */
    @Override
    public String getUrl() {
        return request.getUrl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getSanitizedUrl()
     */
    @Override
    public String getSanitizedUrl() {
        return request.getSanitizedUrl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getBodyContents()
     */
    @Override
    public String getBodyContents() {
        return request.getBodyContents();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getVerb()
     */
    @Override
    public RestVerb getVerb() {
        return RestVerb.valueOf(request.getVerb().toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() {
        return request.getHeaders();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#setConnectTimeout(int, java.util.concurrent.TimeUnit)
     */
    @Override
    public void setConnectTimeout(int duration, TimeUnit unit) {
        request.setConnectTimeout(duration, unit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthRequest#setReadTimeout(int, java.util.concurrent.TimeUnit)
     */
    @Override
    public void setReadTimeout(int duration, TimeUnit unit) {
        request.setReadTimeout(duration, unit);
    }

    @Override
    public String getCompleteUrl() {
        return request.getCompleteUrl();
    }

    @Override
    public void addPayload(byte[] payload) {
        request.addPayload(payload);
    }

    @Override
    public String getCharset() {
        return request.getCharset();
    }

    @Override
    public void setCharset(String charsetName) {
        request.setCharset(charsetName);
    }

    @Override
    public void setConnectionKeepAlive(boolean connectionKeepAlive) {
        request.setConnectionKeepAlive(connectionKeepAlive);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.rest.RestRequest#addBodyParameters(java.util.Map)
     */
    @Override
    public void addBodyParameters(Map<String, ? extends Object> toAdd) {
        for (String key : toAdd.keySet()) {
            addBodyParameter(key, toAdd.get(key).toString());

        }
    }

}
