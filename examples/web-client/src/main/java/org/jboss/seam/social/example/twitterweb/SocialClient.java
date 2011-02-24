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
package org.jboss.seam.social.example.twitterweb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.oauth.Setted;
import org.jboss.seam.social.twitter.Twitter;
import org.jboss.seam.social.twitter.TwitterHandler;
import org.jboss.seam.social.twitter.domain.Credential;



@Named
@SessionScoped
public class SocialClient implements Serializable
{

   private String status;
   
   private Credential cred;
  
   public String getAccessToken()
   {
      return service.getAccessToken();
   }
   
   public String getVerifier()
   {
      return service.getVerifier();
   }

   public void setVerifier(String verifier)
   {
      service.setVerifier(verifier);
   }

   /**
    * 
    */
   private static final long serialVersionUID = 3723552335163650582L;
   
   @Inject
   @Setted(apiKey="FQzlQC49UhvbMZoxUIvHTQ", apiSecret="VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k",callback="http://localhost:8080/seam-social-web-client/callback.jsf")
   TwitterHandler service;
   
   
   public String getAuthorizationURL()
   {
      return service.getAuthorizationUrl();
   }

  public void initAccessToken()
  {
     service.initAccessToken();
  }

/**
 * @param status the status to set
 */
public void setStatus(String status)
{
   this.status = status;
}

/**
 * @return the status
 */
public String getStatus()
{
   return status;
}

public String updateStatus()
{
   service.updateStatus(status);
   return "ok";
}

public Credential getCred()
{
   if (cred==null)
      cred=service.verifyCrendentials();
   return cred;
}




  

   
}
