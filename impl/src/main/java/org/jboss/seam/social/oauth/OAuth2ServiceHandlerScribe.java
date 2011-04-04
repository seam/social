/**
 * 
 */
package org.jboss.seam.social.oauth;



/**
 * @author antoine
 *
 */
public abstract class OAuth2ServiceHandlerScribe extends OAuthServiceHandlerScribe
{

 
   /**
    * 
    */
   private static final long serialVersionUID = 3436501339795099869L;
   private static final String VERIFIER_PARAM_NAME="code";

   @Override
   protected OAuthTokenScribe getRequestToken()
   {
     return new OAuthTokenScribe(null);
   }

   @Override
   public String getVerifierParamName()
   {
     return VERIFIER_PARAM_NAME;
   }

  


  
   

}
