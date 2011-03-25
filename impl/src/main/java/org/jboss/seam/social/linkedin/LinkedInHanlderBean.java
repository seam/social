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
package org.jboss.seam.social.linkedin;

import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jboss.seam.social.linkedin.model.Update;
import org.jboss.seam.social.linkedin.model.UpdateJaxb;
import org.jboss.seam.social.oauth.HttpResponse;
import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.oauth.User;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;

/**
 * @author antoine
 * 
 */
//@Typed(LinkedInHandler.class)
@Named("linkedHdl")
@SessionScoped
public class LinkedInHanlderBean extends OAuthServiceHandlerScribe implements LinkedInHandler
{

   private static final long serialVersionUID = -6718362913575146613L;

   static final String USER_PROFILE_URL = "http://api.linkedin.com/v1/people/~:(picture-url)";
   static final Class<? extends Api> API_CLASS = LinkedInApi.class;
   static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/1-9-05/images/buttons/linkedin_connect.png";
   static final String TYPE="LinkedIn";
   static final String NETWORK_UPDATE_URL="http://api.linkedin.com/v1/people/~/person-activities";
   
   JAXBContext context;
   Unmarshaller unmarshaller;
   Marshaller marshaller;

  @PostConstruct
   protected void init()
   {
      try
      {
         context = JAXBContext.newInstance("org.jboss.seam.social.linkedin.model");
         unmarshaller = context.createUnmarshaller();
         marshaller=context.createMarshaller();
      }
      catch (JAXBException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Override
   @Inject
   public void setSettings(@LinkedIn OAuthServiceSettings settings)
   {
      super.setSettings(settings);

   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandlerScribe#getApiClass()
    */
   @Override
   protected Class<? extends Api> getApiClass()
   {
      return API_CLASS;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getServiceLogo()
    */
   @Override
   public String getServiceLogo()
   {
      return LOGO_URL;
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getUserProfile()
    */
   @Override
   public User getUser()
   {
      if (userProfile == null)
      {
         HttpResponse resp = sendSignedRequest(RestVerb.GET, USER_PROFILE_URL);
         try
         {
            userProfile = (User) unmarshaller.unmarshal(resp.getStream());
         }
         catch (JAXBException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      return userProfile;
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.OAuthServiceHandler#getType()
    */
   @Override
   public String getType()
   {
     return TYPE;
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HasStatus#updateStatus()
    */
   @Override
   public Object updateStatus()
   {
     
      return updateStatus(getStatus());
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HasStatus#updateStatus(java.lang.String)
    */
   @Override
   public Update updateStatus(String message)
   {
     Update upd=new UpdateJaxb();
     upd.setBody(message);
     StringWriter writer=new StringWriter();
     try
   {
      marshaller.marshal(upd, writer);
   }
   catch (JAXBException e)
   {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
   String msg=writer.toString();
     HttpResponse resp = sendSignedRequest(RestVerb.POST, NETWORK_UPDATE_URL, msg);
      return upd;
   }

}
