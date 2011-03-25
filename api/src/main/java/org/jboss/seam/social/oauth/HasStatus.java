/**
 * 
 */
package org.jboss.seam.social.oauth;

/**
 * @author antoine
 *
 */
public interface HasStatus
{
public String getStatus();
public void setStatus(String status);

public Object updateStatus();

public Object updateStatus(String message);
}
