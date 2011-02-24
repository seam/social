/**
 * 
 */
package org.jboss.seam.social.oauth;

import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author antoine
 * 
 */
public abstract class SettedHandlerProducer
{

   protected <T extends OAuthServiceHandler> T setService(InjectionPoint ip, T hdl)
   {
      Setted setted = ip.getAnnotated().getAnnotation(Setted.class);
      OAuthServiceSettings settings=hdl.getSettings();

      String apiKey = setted.apiKey();
      String apiSecret = setted.apiSecret();
      String callback = setted.callback();

      settings.setApiKey(apiKey);
      settings.setApiSecret(apiSecret);
      settings.setCallback(callback);
      
      return hdl;
   }

}
