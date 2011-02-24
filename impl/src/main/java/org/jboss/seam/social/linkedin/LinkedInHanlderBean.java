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

import javax.enterprise.inject.Typed;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthServiceHandlerScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;

/**
 * @author antoine
 *
 */
@Typed(LinkedInHandler.class)
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
