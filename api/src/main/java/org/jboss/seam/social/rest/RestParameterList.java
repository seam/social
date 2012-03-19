/**
 * 
 */
package org.jboss.seam.social.rest;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public interface RestParameterList {

    public void add(String key, String value);

    public String appendTo(String url);

    public String asOauthBaseString();

    public String asFormUrlEncodedString();

    public void addAll(RestParameterList other);

    public void addQuerystring(String queryString);

    public boolean contains(RestParameter param);

    public int size();

    public RestParameterList sort();

}