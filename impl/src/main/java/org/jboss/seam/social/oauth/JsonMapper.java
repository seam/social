/**
 * 
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
