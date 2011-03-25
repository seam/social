/**
 * 
 */
package org.jboss.seam.social.example.webclient;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthServiceHandler;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

/**
 * @author antoine
 *
 */
@FacesConverter(value="socialConverter",forClass=OAuthServiceHandler.class)
public class SocialServiceConverter implements Converter
{

   @Inject @Any
   private Instance<OAuthServiceHandler> serviceHandlerInstances;
   
   private Map<String, OAuthServiceHandler> services;
   
  
   private  Map<String, OAuthServiceHandler> getServices()
   {
      if (services==null)
      {
      services=Maps.uniqueIndex(serviceHandlerInstances, new Function<OAuthServiceHandler, String>()
      {

         @Override
         public String apply(OAuthServiceHandler arg0)
         {
       
            return arg0.getType();
         }
      });
      }
      return services;
   }
   
   /* (non-Javadoc)
    * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
    */
   @Override
   public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
   {
      Object res=getServices().get(arg2);
      return ((OAuthServiceHandler) res);
   }

   /* (non-Javadoc)
    * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
    */
   @Override
   public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
   {
      String res = ((OAuthServiceHandler)arg2).getType();
      return res;
   }

}
