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
package org.jboss.seam.social.example.webclient;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.oauth.OAuthServiceHandler;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.seam.social.oauth.User;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;



@Named
@SessionScoped
public class SocialClient implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 3723552335163650582L;
     
   
   @Inject @Any
   private Instance<OAuthServiceHandler> serviceHandlerInstances;
  
  OAuthServiceHandler currentServiceHdl;
   private String status;
   

  
  private List<OAuthServiceHandler> serviceHandlers;
  
  
  
  public List<OAuthServiceHandler> getServiceHandlers()
{
   return serviceHandlers;
}


  
  @PostConstruct
  public void init()
  {
     
    serviceHandlers=Lists.newArrayList(serviceHandlerInstances);
  
  }
  
  
  public List<OAuthServiceHandler> getConnectedServices()
  {
     return Lists.newArrayList(Iterables.filter(serviceHandlers, new Predicate<OAuthServiceHandler>()
   {

      @Override
      public boolean apply(OAuthServiceHandler arg0)
      {
         return arg0.isConnected();
      }
   }));
  }
  
  
  public List<OAuthServiceHandler> getUnconnectedServices()
  {
     return Lists.newArrayList(Iterables.filter(serviceHandlers, new Predicate<OAuthServiceHandler>()
   {

      @Override
      public boolean apply(OAuthServiceHandler arg0)
      {
         return !arg0.isConnected();
      }
   }));
  }

   public OAuthToken getAccessToken()
   {
      return currentServiceHdl.getAccessToken();
   }
   
   public String getVerifier()
   {
      return currentServiceHdl.getVerifier();
   }

   public void setVerifier(String verifier)
   {
      currentServiceHdl.setVerifier(verifier);
   }

   
   
   
   public String getAuthorizationURL()
   {
      return currentServiceHdl.getAuthorizationUrl();
   }

  public void initAccessToken()
  {
     currentServiceHdl.initAccessToken();
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
/*
public String updateStatus()
{
   twitterHdl.updateStatus(status);
   status="";
   return "ok";
}*/

public User getUser()
{
      return currentServiceHdl.getUser();
}

public OAuthServiceHandler getCurrentServiceHdl()
{
   return currentServiceHdl;
}




public void setCurrentServiceHdl(OAuthServiceHandler currentServiceHdl)
{
   this.currentServiceHdl = currentServiceHdl;
}


public void gotoAuthorizationURL(OAuthServiceHandler service) throws IOException
{
   setCurrentServiceHdl(service);
   ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
   externalContext.redirect(getAuthorizationURL());
}
  

   
}
