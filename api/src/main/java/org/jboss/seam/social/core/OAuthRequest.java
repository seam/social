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

import java.net.HttpURLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of this interface Represent an OAuth Request
 * 
 * @author Antoine Sabot-Durand
 * 
 */
public interface OAuthRequest {

    /**
     * Adds an OAuth parameter.
     * 
     * @param key name of the parameter
     * @param value value of the parameter
     * 
     */
    public void addOAuthParameter(String name, String value);

    /**
     * Execute the request and return a {@link HttpResonse}
     * 
     * @return Http Response
     */
    public HttpResponse send();

    /**
     * Returns the {@link Map} containing the key-value pair of parameters.
     * 
     * @return parameters as map
     */
    public Map<String, String> getOauthParameters();

    /**
     * Add an HTTP Header to the Request
     * 
     * @param key the header name
     * @param value the header value
     */
    public void addHeader(String key, String value);

    /**
     * Add a body Parameter (for POST/ PUT Requests)
     * 
     * @param key the parameter name
     * @param value the parameter value
     */
    public void addBodyParameter(String key, String value);

    /**
     * Add a QueryString parameter
     * 
     * @param key the parameter name
     * @param value the parameter value
     */
    public void addQuerystringParameter(String key, String value);

    /**
     * Add body payload.
     * 
     * This method is used when the HTTP body is not a form-url-encoded string, but another thing. Like for example XML.
     * 
     * Note: The contents are not part of the OAuth signature
     * 
     * @param payload the body of the request
     */
    public void addPayload(String payload);

    /**
     * Get a {@link Map} of the query string parameters.
     * 
     * @return a map containing the query string parameters
     * @throws OAuthException if the URL is not valid
     */
    public Map<String, String> getQueryStringParams();

    /**
     * Obtains a {@link Map} of the body parameters.
     * 
     * @return a map containing the body parameters.
     */
    public Map<String, String> getBodyParams();

    /**
     * Obtains the URL of the HTTP Request.
     * 
     * @return the original URL of the HTTP Request
     */
    public String getUrl();

    /**
     * Returns the URL without the port and the query string part.
     * 
     * @return the OAuth-sanitized URL
     */
    public String getSanitizedUrl();

    /**
     * Returns the URL without the port and the query string part.
     * 
     * @return the OAuth-sanitized URL
     */
    public String getBodyContents();

    /**
     * Returns the HTTP Verb
     * 
     * @return the verb
     */
    public RestVerb getVerb();

    /**
     * Returns the connection headers as a {@link Map}
     * 
     * @return map of headers
     */
    public Map<String, String> getHeaders();

    /**
     * Sets the connect timeout for the underlying {@link HttpURLConnection}
     * 
     * @param duration duration of the timeout
     * 
     * @param unit unit of time (milliseconds, seconds, etc)
     */
    public void setConnectTimeout(int duration, TimeUnit unit);

    /**
     * Sets the read timeout for the underlying {@link HttpURLConnection}
     * 
     * @param duration duration of the timeout
     * 
     * @param unit unit of time (milliseconds, seconds, etc)
     */
    public void setReadTimeout(int duration, TimeUnit unit);

}