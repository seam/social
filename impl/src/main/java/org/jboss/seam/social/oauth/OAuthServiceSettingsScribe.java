/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.io.Serializable;

/**
 * @author antoine
 *
 */
public class OAuthServiceSettingsScribe implements OAuthServiceSettings,Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = -8018722725677732853L;

   private String apiKey;
   
   private String apiSecret;
   
   private String callback;
   
   private Provider provider;

   public String getApiKey()
   {
      return apiKey;
   }

   public void setApiKey(String apiKey)
   {
      this.apiKey = apiKey;
   }

   public String getApiSecret()
   {
      return apiSecret;
   }

   public void setApiSecret(String apiSecret)
   {
      this.apiSecret = apiSecret;
   }

   public Provider getProvider()
   {
      return provider;
   }

   public void setProvider(Provider provider)
   {
      this.provider = provider;
   }

   public String getCallback()
   {
      return callback;
   }

   public void setCallback(String callback)
   {
      this.callback = callback;
   }

   public OAuthServiceSettingsScribe(String apiKey, String apiSecret, String callback, Provider provider)
   {
      super();
      this.apiKey = apiKey;
      this.apiSecret = apiSecret;
      this.callback = callback;
      this.provider = provider;
   }

   public OAuthServiceSettingsScribe()
   {
      super();
   
   }
   
   

   

}
