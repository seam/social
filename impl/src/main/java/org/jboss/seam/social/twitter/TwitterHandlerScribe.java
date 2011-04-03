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

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.oauth.HttpResponse;
import org.jboss.seam.social.oauth.JsonMapper;
import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.oauth.User;
import org.jboss.seam.social.twitter.model.Tweet;
import org.jboss.seam.social.twitter.model.TweetJackson;
import org.jboss.seam.social.twitter.model.TwitterCredentialJackson;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * @author antoine
 * 
 */
//@Typed(TwitterHandler.class)
@Named("twitterHdl")
@SessionScoped
public class TwitterHandlerScribe extends OAuthServiceHandlerScribe implements TwitterHandler
{

   private static final long serialVersionUID = 6806035986656777834L;
   static final String VERIFY_CREDENTIALS_URL = "https://api.twitter.com/1/account/verify_credentials.json";
   static final String FRIENDS_STATUSES_URL = "https://api.twitter.com/1/statuses/friends.json?screen_name={screen_name}";
   static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
   static final String TWEET_URL = "https://api.twitter.com/1/statuses/update.json";
   static final String RETWEET_URL = "https://api.twitter.com/1/statuses/retweet/{tweet_id}.json";
   static final Class<? extends Api> API_CLASS = TwitterApi.class;
   static final String LOGO_URL = "http://twitter-badges.s3.amazonaws.com/twitter-a.png";
   static final String TYPE="Twitter";


   
   @Inject
   private JsonMapper jsonMapper;
   
   @Override
   @Inject
   public void setSettings(@Twitter OAuthServiceSettings settings)
   {
      super.setSettings(settings);

   }

   @Override
   public Tweet updateStatus(String message)
   {
      HttpResponse resp = sendSignedRequest(RestVerb.POST, TWEET_URL, "status", message);
      System.out.println("update satus is " + message);
      setStatus("");
      return jsonMapper.readValue(resp, TweetJackson.class);

   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getApiClass()
    */
   @Override
   protected Class<? extends Api> getApiClass()
   {
      return API_CLASS;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.twitter.TwitterHandler#verifyCrendentials()
    */
   @Override
   public User verifyCrendentials()
   {

      return getUser();
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getServiceLogo()
    */
   @Override
   public String getServiceLogo()
   {
      return LOGO_URL;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getUserProfile()
    */
   @Override
   public User getUser()
   {
      if (userProfile == null)
      {
         HttpResponse resp = sendSignedRequest(RestVerb.GET, VERIFY_CREDENTIALS_URL);
         userProfile = jsonMapper.readValue(resp, TwitterCredentialJackson.class);
      }
      return userProfile;
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getType()
    */
   @Override
   public String getType()
   {
      return TYPE;
   }

  

   /* (non-Javadoc)
    * @see org.jboss.seam.social.twitter.TwitterHandler#updateStatus()
    */
   @Override
   public Tweet updateStatus()
   {
      return updateStatus(getStatus());
   }

}
