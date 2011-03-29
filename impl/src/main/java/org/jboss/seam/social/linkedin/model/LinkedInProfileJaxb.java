/**
 * 
 */
package org.jboss.seam.social.linkedin.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;




/**
 * @author antoine
 *
 */
@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkedInProfileJaxb implements LinkedInProfile
{

   @XmlElement(name="first-name")
   private String firstName;
   
   @XmlElement(name="last-name")
   private String lastName;
   

   private String headline;
   
   @XmlElement(name="picture-url")
   private String pictureUrl;
   
   @XmlElementWrapper(name="site-standard-profile-request")
   @XmlElements({@XmlElement(name="url")})
   private List<String> standardProfileUrl;
   
 


   @Override
  
   public String getPictureUrl()
   {
      return pictureUrl;
   }


   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.OAuthUserProfile#getFullName()
    */
   @Override
   public String getFullName()
   {
     return firstName +" "+lastName;
   }



   public String getFirstName()
   {
      return firstName;
   }


   @Override
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }


   @Override
  
   public String getLastName()
   {
      return lastName;
   }


   @Override
   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }


   @Override
   public String getHeadline()
   {
      return headline;
   }


   @Override
   public void setHeadline(String headline)
   {
      this.headline = headline;
   }


 

   /**
    * @return the standardProfileUrl
    */
   @Override
   public String getStandardProfileUrl()
   {
      return standardProfileUrl.get(0);
   }

   

}
