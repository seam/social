/**
 * 
 */
package org.jboss.seam.social.twitter;

import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.seam.social.oauth.OAuthServiceSettingsImpl;
import org.jboss.seam.social.oauth.Setted;

/**
 * @author antoine
 *
 */
public class TwitterProducer
{
   @Produces @Setted
   public TwitterHandler produceTwitterHandler(InjectionPoint ip,TwitterHandler twitterHdl)
   {
      Setted setted=ip.getAnnotated().getAnnotation(Setted.class);
      
      String apiKey=setted.apiKey();
      String apiSecret=setted.apiSecret();
      String callback=setted.callback();
      twitterHdl.setSettings(new OAuthServiceSettingsImpl(apiKey,apiSecret,callback));
      return twitterHdl;
   }

}
