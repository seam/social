
/**
 * 
 */
package org.jboss.seam.social.twitter;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.twitter.domain.SearchResult;
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
 * @see org.jboss.seam.social.twitter.TwitterHandler#search(java.lang.String)
 */
@Override
public SearchResult search(String string)
{
   // TODO Auto-generated method stub
   return null;
}







/* (non-Javadoc)
 * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getApiClass()
 */
@Override
protected Class<? extends Api> getApiClass()
{
 return API_CLASS;
}
   
   
   
}
