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
