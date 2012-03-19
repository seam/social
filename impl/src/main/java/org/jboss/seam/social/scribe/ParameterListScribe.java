/**
 * 
 */
package org.jboss.seam.social.scribe;

import java.util.Map;

import org.jboss.seam.social.rest.RestParameter;
import org.jboss.seam.social.rest.RestParameterList;
import org.scribe.model.ParameterList;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class ParameterListScribe implements RestParameterList {

    private final ParameterList delegate;

    ParameterListScribe(ParameterList delegate) {
        super();
        this.delegate = delegate;
    }

    public ParameterListScribe() {
        delegate = new ParameterList();
    }

    public ParameterListScribe(Map<String, String> params) {
        delegate = new ParameterList(params);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#add(java.lang.String, java.lang.String)
     */
    @Override
    public void add(String key, String value) {
        delegate.add(key, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#appendTo(java.lang.String)
     */
    @Override
    public String appendTo(String url) {
        return delegate.appendTo(url);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#asOauthBaseString()
     */
    @Override
    public String asOauthBaseString() {
        return delegate.asOauthBaseString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#hashCode()
     */
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#asFormUrlEncodedString()
     */
    @Override
    public String asFormUrlEncodedString() {
        return delegate.asFormUrlEncodedString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#addAll(org.scribe.model.ParameterList)
     */
    @Override
    public void addAll(RestParameterList other) {

        delegate.addAll(((ParameterListScribe) other).delegate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#addQuerystring(java.lang.String)
     */
    @Override
    public void addQuerystring(String queryString) {
        delegate.addQuerystring(queryString);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#contains(org.jboss.seam.social.scribe.ParameterScribe)
     */
    @Override
    public boolean contains(RestParameter param) {
        return delegate.contains(((ParameterScribe) param).getDelegate());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#size()
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#sort()
     */
    @Override
    public RestParameterList sort() {
        return new ParameterListScribe(delegate.sort());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.scribe.RestParameterList#toString()
     */
    @Override
    public String toString() {
        return delegate.toString();
    }

}
