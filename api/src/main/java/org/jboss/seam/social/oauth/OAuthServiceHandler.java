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

   /**
    * 
    * @return the access token for the OAuth service
    */
   public OAuthToken getAccessToken();

   /**
    * Returns the url to the OAuth service to ask an authorization to access the service.
    * @return the REST URL to use request access
    */
   public String getAuthorizationUrl();

   /**
    * 
    * @return the settings of the service
    */
   public OAuthServiceSettings getSettings();

   /**
    * Access to OAuth verifier
    * @return the OAUth verifier
    */
   public String getVerifier();


   /**
    * Initialize the OAuth access token after the service gave an authorization with the Verifier
    */
   public void initAccessToken();

   /**
    * Send an OAuth request signed without any parameter
    * @param verb a REST verb
    * @param uri the REST address of the request
    * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
    */
   public HttpResponse sendSignedRequest(RestVerb verb, String uri);

   /**
    * Send an OAuth request signed with a list a parameter
    * @param verb a REST verb
    * @param uri the REST address of the request
    * @param params a Map of key value parameters to send in the header of the request
    * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
    */
   public HttpResponse sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params);

   /**
    * Send an OAuth request signed with a single parameter
    * @param verb a REST verb
    * @param uri the REST address of the request
    * @param key name of the parameter
    * @param value value of the parameter
    * @return an HttpResponse containing the response. It could be in various format (json, xml, string)
    */
   public HttpResponse sendSignedRequest(RestVerb verb, String uri, String key, Object value);
   
   /**
    * Initialize OAuth settings
    * @param settings
    */
   public void setSettings(OAuthServiceSettings settings);
   
   /**
    * Used to initialize verifier code returned by OAuth service
    * @param verifierStr
    */
   public void setVerifier(String verifierStr);
   
   
   /**
    * Gives the logo of the service
    * @return the URL of the logo for the service
    */
   public String getServiceLogo();
   
   public Boolean isConnected();
      
   public UserProfile getUser();
   
   public String getType();
   
   public void setAccessToken(String api, String secret);
   
   public void setAccessToken(OAuthToken token);
   
   public void resetConnexion();

   /**
    * @param verb
    * @param uri
    * @param payload
    * @return
    */
   HttpResponse sendSignedXmlRequest(RestVerb verb, String uri, String payload);

   /**
    * @return
    */
   String getVerifierParamName();
   

}
