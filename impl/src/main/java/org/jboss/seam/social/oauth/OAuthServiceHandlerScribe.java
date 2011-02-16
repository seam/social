/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

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

public class OAuthServiceHandlerScribe implements OAuthServiceHandler, Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -8423894021913341674L;

   @Inject
   private OAuthServiceSettings settings;

   private OAuthService service;

   private Token requestToken;
   private Token accessToken;

   private Verifier verifier;

   @Override
   @PostConstruct
   public void init()
   {
      Class<? extends Api> apiClass = getApiClass(settings.getProvider());
      ServiceBuilder serviceBuilder = new ServiceBuilder().provider(apiClass).apiKey(settings.getApiKey()).apiSecret(settings.getApiSecret());
      if (settings.getCallback() != null && !("".equals(settings.getCallback())))
         serviceBuilder.callback(settings.getCallback());
      service = serviceBuilder.build();

   }

   /**
    * @param provider
    * @return
    * @throws ClassNotFoundException
    */
   @SuppressWarnings("unchecked")
   private Class<? extends Api> getApiClass(Provider provider)
   {
      String className = provider.name().concat("Api");
      Class<? extends Api> result;
      try
      {
         result = (Class<? extends Api>) Class.forName("org.scribe.builder.api." + className);
      }
      catch (ClassNotFoundException e)
      {
         throw new IllegalArgumentException("Error producing OAuthService, class : " + className + " doesn't exist", e);
      }
      return result;
   }

  
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
