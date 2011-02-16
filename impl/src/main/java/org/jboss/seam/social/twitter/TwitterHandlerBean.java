
/**
 * 
 */
package org.jboss.seam.social.twitter;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.twitter.domain.SearchResult;


/**
 * @author antoine
 *
 */
@Twitter
public class TwitterHandlerBean extends OAuthServiceHandlerScribe implements TwitterHandler
{
   
 
   /**
    * 
    */
   private static final long serialVersionUID = 6806035986656777834L;
   static final String VERIFY_CREDENTIALS_URL = "https://api.twitter.com/1/account/verify_credentials.json";
   static final String FRIENDS_STATUSES_URL = "https://api.twitter.com/1/statuses/friends.json?screen_name={screen_name}";
   static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
   static final String TWEET_URL = "https://api.twitter.com/1/statuses/update.json";
   static final String RETWEET_URL = "https://api.twitter.com/1/statuses/retweet/{tweet_id}.json";

   @Inject
   private ObjectMapper mapper;

  

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
   
   
   
}
