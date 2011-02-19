/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.io.Serializable;


/**
 * @author antoine
 *
 */
public class OAuthServiceSettingsImpl implements OAuthServiceSettings,Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = -8018722725677732853L;
   
   private String apiKey;
   
   private String apiSecret;
   
   private String callback;
   
   public String getApiKey()
   {
      return apiKey;
   }

   @Override
   public void setApiKey(String apiKey)
   {
      this.apiKey = apiKey;
      System.out.println("****** " +apiKey+" *********");
   }

   public String getApiSecret()
   {
      return apiSecret;
   }

   @Override
   public void setApiSecret(String apiSecret)
   {
      this.apiSecret = apiSecret;
   }


   @Override
   public String getCallback()
   {
      return callback;
   }

   @Override
   public void setCallback(String callback)
   {
      this.callback = callback;
   }

  

   protected OAuthServiceSettingsImpl()
   {
      super();
   }

   
   public OAuthServiceSettingsImpl(String apiKey, String apiSecret, String callback)
   {
      super();
      this.apiKey = apiKey;
      this.apiSecret = apiSecret;
      this.callback = callback;
   }

}
