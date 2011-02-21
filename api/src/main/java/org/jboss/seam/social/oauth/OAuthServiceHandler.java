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

import java.util.Map;

/**
 * Implementation of this interface is used to manage a generic OAuth Service
 * @author Antoine Sabot-Durand
 * 
 */
public interface OAuthServiceHandler
{

   public void init();

   /**
    * Send an OAuth request signed without any parameter
    * @param verb a REST verb
    * @param uri
    * @return an Object containing the response. It could be in various format (json, xml, string)
    */
   public Object sendSignedRequest(RestVerb verb, String uri);

   /**
    * Initialize the OAuth access token 
    */
   public void initAccessToken();

   public String getAuthorizationUrl();

   public Object sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params);

   public Object sendSignedRequest(RestVerb verb, String uri, String key, Object value);

   public void setVerifier(String verifierStr);

   public String getVerifier();

   public String getAccessToken();
   
   public OAuthServiceSettings getSettings();
   
   public void setSettings(OAuthServiceSettings settings);

}
