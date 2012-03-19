/**
 * 
 */
package org.jboss.seam.social.scribe;

import org.jboss.seam.social.rest.RestParameter;
import org.scribe.model.Parameter;

/**
 * @author Antoine Sabot-Durand
 * 
 */
public class ParameterScribe implements Comparable<ParameterScribe>, RestParameter {

    private final Parameter delegate;

    public ParameterScribe(String key, String value) {
        super();
        delegate = new Parameter(key, value);
    }

    ParameterScribe(Parameter parameter) {
        delegate = parameter;
    }

    @Override
    public int compareTo(ParameterScribe o) {
        return delegate.compareTo(o.delegate);
    }

    @Override
    public String asUrlEncodedPair() {
        return delegate.asUrlEncodedPair();
    }

    @Override
    public boolean equals(Object other) {
        return delegate.equals(other);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    Parameter getDelegate() {
        return delegate;
    }

}
