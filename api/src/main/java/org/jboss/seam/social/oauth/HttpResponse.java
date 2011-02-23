/**
 * 
 */
package org.jboss.seam.social.oauth;

import java.io.InputStream;
import java.util.Map;

/**
 * @author antoine
 *
 */
public interface HttpResponse
{

   public String getBody();

   public InputStream getStream();

   public int getCode();

   public Map<String, String> getHeaders();

   public String getHeader(String name);

}