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

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author antoine
 *
 */
@ApplicationScoped
public class JsonMapper implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -2012295612034078749L;

   private final ObjectMapper delegate=new ObjectMapper();
   
 
   public <T> T readValue(HttpResponse resp, Class<T> clazz)
   {
      T res = null;
      try
      {
         res = delegate.readValue(resp.getStream(), clazz);
      }
      catch (JsonParseException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (JsonMappingException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return res;
   }
 

}
