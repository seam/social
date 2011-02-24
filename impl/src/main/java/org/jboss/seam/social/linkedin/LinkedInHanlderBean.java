/**
 * 
 */
package org.jboss.seam.social.linkedin;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.twitter.Twitter;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.builder.api.TwitterApi;

/**
 * @author antoine
 *
 */
//@Typed(LinkedInHanlderBean.class)
public class LinkedInHanlderBean extends OAuthServiceHandlerScribe implements LinkedInHandler
{

  
   private static final long serialVersionUID = -6718362913575146613L;
   static final Class<? extends Api> API_CLASS = LinkedInApi.class;

   

   @Override
   @Inject
   public void setSettings(@LinkedIn OAuthServiceSettings settings)
   {
      super.setSettings(settings);

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
