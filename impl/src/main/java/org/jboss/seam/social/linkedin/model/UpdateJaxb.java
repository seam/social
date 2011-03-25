/**
 * 
 */
package org.jboss.seam.social.linkedin.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author antoine
 *
 */
@XmlRootElement(name="activity")
public class UpdateJaxb implements Update
{
   private String locale="en_US";
   
   private String contentType="linkedin-html";
   
   private String body;

   @Override
   @XmlAttribute(required=true)
   public String getLocale()
   {
      return locale;
   }

   
   @Override
   public void setLocale(String locale)
   {
      this.locale = locale;
   }

   @Override
   @XmlElement(name="content_type",required=true)
   public String getContentType()
   {
      return contentType;
   }

   @Override
   public void setContentType(String contentType)
   {
      this.contentType = contentType;
   }

   @Override
   @XmlElement(required=true)
   public String getBody()
   {
      return body;
   }



   @Override
   public void setBody(String body)
   {
      this.body = body;
   }
   
   

}
