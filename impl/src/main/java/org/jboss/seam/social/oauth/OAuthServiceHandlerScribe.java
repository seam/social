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

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * @author antoine
 * 
 */

public abstract class OAuthServiceHandlerScribe implements OAuthServiceHandler, Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -8423894021913341674L;

  
   private OAuthService service;

   private Token requestToken;
   private Token accessToken;

   private Verifier verifier;
   
   private OAuthServiceSettings settings;

   @Override
   public void init()
   {
      //Class<? extends Api> apiClass = getApiClass(getSettings().getProvider());
      Class<? extends Api> apiClass = getApiClass();
      ServiceBuilder serviceBuilder = new ServiceBuilder().provider(apiClass).apiKey(getSettings().getApiKey()).apiSecret(getSettings().getApiSecret());
      if (getSettings().getCallback() != null && !("".equals(getSettings().getCallback())))
         serviceBuilder.callback(getSettings().getCallback());
      service = serviceBuilder.build();

   }
   
   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getSettings()
    */
   @Override
   public OAuthServiceSettings getSettings()
   {
      return settings;
   }



   public void setSettings(OAuthServiceSettings settings)
   {
      this.settings = settings;
   }
   
   protected abstract Class<? extends Api> getApiClass();

  
   @Override
   public String getAuthorizationUrl()
   {
      if(requestToken==null)
         requestToken = service.getRequestToken();
      return service.getAuthorizationUrl(requestToken);
   }

   @Override
   public void initAccessToken()
   {
      if(accessToken==null)
      accessToken = service.getAccessToken(requestToken, verifier);
   }

   @Override
   public Object sendSignedRequest(RestVerb verb, String uri)
   {
      OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);
      service.signRequest(accessToken, request);
      return request.send();

   }

   @Override
   public Object sendSignedRequest(RestVerb verb, String uri, String key, Object value)
   {
      Response resp;
      OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);

      request.addBodyParameter(key, value.toString());

      service.signRequest(accessToken, request);
      resp = request.send();
      return resp;

   }

   @Override
   public Object sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params)
   {
      OAuthRequest request = new OAuthRequest(Verb.valueOf(verb.toString()), uri);
      for (Entry<String, Object> ent : params.entrySet())
      {
         request.addBodyParameter(ent.getKey(), ent.getValue().toString());
      }
      service.signRequest(accessToken, request);
      return request.send();

   }
   
   @Override
   public void setVerifier(String verifierStr)
   {
      verifier=new Verifier(verifierStr);
   }

   
   @Override
   public String getVerifier()
   {
      return verifier==null ?null:verifier.getValue();
   }

   @Override
   public String getAccessToken()
   {
      return accessToken.toString();
   }
   
   

}
