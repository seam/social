/**
 * 
 */
package org.jboss.seam.social.linkedin.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author antoine
 *
 */
public interface Update
{

   @XmlAttribute(required = true)
   public String getLocale();

   public void setLocale(String locale);

   @XmlElement(name = "content_type", required = true)
   public String getContentType();

   public void setContentType(String contentType);

   @XmlElement(required = true)
   public String getBody();

   public void setBody(String body);

}