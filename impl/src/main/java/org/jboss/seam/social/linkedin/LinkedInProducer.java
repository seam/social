/**
 * 
 */
package org.jboss.seam.social.linkedin;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.seam.social.oauth.Setted;
import org.jboss.seam.social.oauth.SettedHandlerProducer;

/**
 * @author antoine
 *
 */
public class LinkedInProducer extends SettedHandlerProducer
{

   @Produces @Setted
   public LinkedInHandler produceLinkedInHandler(InjectionPoint ip, LinkedInHandler hdl)
   {
      return setService(ip, hdl);
   }
   
}
