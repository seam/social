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

/**
 * @author Antoine Sabot-Durand
 * 
 *         Implementation of this Interface will provide low level service for OAuth management. It allows to create different
 *         provider to deal with Social Network
 * 
 */
public interface OAuthProvider {

    public OAuthToken getRequestToken();

    public OAuthToken getAccessToken(OAuthToken requestToken, String verifier);

    public void signRequest(OAuthToken accessToken, OAuthRequest request);

    public String getVersion();

    public String getAuthorizationUrl(OAuthToken tok);

    public void initProvider(OAuthServiceSettings settings);

    public OAuthRequest createRequest(RestVerb verb, String uri);

    public OAuthToken createToken(String token, String secret);

}