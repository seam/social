/**
 * 
 */
package org.jboss.seam.social.linkedin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author antoine
 *
 */
@XmlRootElement(name="activity")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateJaxb implements Update
{
   @XmlAttribute(required=true)
   private String locale="en_US";
   
   @XmlElement(name="content-type",required=true)
   private String contentType="linkedin-html";
   
   @XmlElement(name="body", required=true)
   private String body;

   @Override
  
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
