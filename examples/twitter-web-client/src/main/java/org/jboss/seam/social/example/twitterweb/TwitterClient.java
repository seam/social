package org.jboss.seam.social.example.twitterweb;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.twitter.Twitter;
import org.jboss.seam.social.twitter.TwitterHandler;



@Named("twitterClient")
@SessionScoped
public class TwitterClient implements Serializable
{

   private String status;
  
   public String getAccessToken()
   {
      return service.getAccessToken();
   }
   
   public String getVerifier()
   {
      return service.getVerifier();
   }

   public void setVerifier(String verifier)
   {
      service.setVerifier(verifier);
   }

   /**
    * 
    */
   private static final long serialVersionUID = 3723552335163650582L;
   
   @Inject
   @Twitter
   TwitterHandler service;
   
   public String getAuthorizationURL()
   {
      return service.getAuthorizationUrl();
   }

  public void initAccessToken()
  {
     service.initAccessToken();
  }

/**
 * @param status the status to set
 */
public void setStatus(String status)
{
   this.status = status;
}

/**
 * @return the status
 */
public String getStatus()
{
   return status;
}

public String updateStatus()
{
   service.updateStatus(status);
   return "ok";
}
  
   
}
