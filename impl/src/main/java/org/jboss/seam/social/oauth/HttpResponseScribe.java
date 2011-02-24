/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.scribe.model.Response;



/**
 * @author Antoine Sabot-Durand
 *
 */
public class HttpResponseScribe implements HttpResponse
{

   private Response delegate;

   protected HttpResponseScribe(Response response) throws IOException
   {
     delegate=response;
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HttpResponse#getBody()
    */
   @Override
   public String getBody()
   {
      return delegate.getBody();
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HttpResponse#getStream()
    */
   @Override
   public InputStream getStream()
   {
      return delegate.getStream();
   }

   public int hashCode()
   {
      return delegate.hashCode();
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HttpResponse#getCode()
    */
   @Override
   public int getCode()
   {
      return delegate.getCode();
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HttpResponse#getHeaders()
    */
   @Override
   public Map<String, String> getHeaders()
   {
      return delegate.getHeaders();
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.HttpResponse#getHeader(java.lang.String)
    */
   @Override
   public String getHeader(String name)
   {
      return delegate.getHeader(name);
   }

   public boolean equals(Object obj)
   {
      return delegate.equals(obj);
   }

   public String toString()
   {
      return delegate.toString();
   }

  
  
   

  
   
}
