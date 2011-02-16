/**
 * 
 */
package org.jboss.seam.social.jackson;

import javax.enterprise.inject.Produces;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author antoine
 *
 */
public class ObjectMapperProducer
{
   @Produces
   public ObjectMapper produce()
   {
      return new ObjectMapper();
   }

}
