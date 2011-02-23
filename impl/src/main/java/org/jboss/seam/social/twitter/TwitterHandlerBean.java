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
package org.jboss.seam.social.twitter;

import java.io.IOException;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.social.oauth.HttpResponse;
import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.twitter.domain.Credential;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;


/**
 * @author antoine
 *
 */
@Typed(TwitterHandler.class)
public class TwitterHandlerBean extends OAuthServiceHandlerScribe implements TwitterHandler
{
   
 
   
   private static final long serialVersionUID = 6806035986656777834L;
   static final String VERIFY_CREDENTIALS_URL = "https://api.twitter.com/1/account/verify_credentials.json";
   static final String FRIENDS_STATUSES_URL = "https://api.twitter.com/1/statuses/friends.json?screen_name={screen_name}";
   static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
   static final String TWEET_URL = "https://api.twitter.com/1/statuses/update.json";
   static final String RETWEET_URL = "https://api.twitter.com/1/statuses/retweet/{tweet_id}.json";
   static final Class<? extends Api> API_CLASS = TwitterApi.class;

   @Inject
   ObjectMapper jsonMapper;
   
   @Override @Inject 
   public void setSettings(@Twitter OAuthServiceSettings settings)
   {
      super.setSettings(settings);
     
   }

 

  @Override
  public Object updateStatus(String message)
  {
     return sendSignedRequest(RestVerb.POST, TWEET_URL, "status", message);
  }




/* (non-Javadoc)
 * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getApiClass()
 */
@Override
protected Class<? extends Api> getApiClass()
{
 return API_CLASS;
}



/* (non-Javadoc)
 * @see org.jboss.seam.social.twitter.TwitterHandler#verifyCrendentials()
 */
@Override
public Object verifyCrendentials()
{
   HttpResponse resp = sendSignedRequest(RestVerb.GET, VERIFY_CREDENTIALS_URL);
   Credential credential=null;
   try
   {
      credential=jsonMapper.readValue(resp.getStream(), Credential.class);
   }
   catch (JsonParseException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
   catch (JsonMappingException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
   catch (IOException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
   return credential;
}
   
   
   
}
