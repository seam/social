/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.rest;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.jboss.seam.social.UserProfile;

import com.google.common.collect.Multimap;

/**
 * @author antoine
 * 
 */
public interface RestService extends Serializable {

    /**
     * @return
     */
    public UserProfile getMyProfile();

    /**
     * Returns the logo of the service
     * 
     * @return the URL of the logo for the service
     */
    public String getServiceLogo();

    /**
     * Returns the status of this ServiceHndler
     * 
     * @return true if the connection process is over and successful
     */
    public boolean isConnected();

    /**
     * Returns the name/type of the Social Network we're connected to
     * 
     * @return name of the service
     */
    public String getType();

    /**
     * Returns the Qualifier used for this social network
     * 
     * @return Annotation being a Qualifier
     */
    public Annotation getQualifier();

    /**
     * Close connexion if needed
     */
    public void resetConnection();

    /**
     * @return
     */
    public String getApiRootUrl();

    /**
     * @param url
     * @param key
     * @param value
     * @return
     */
    public String buildUri(String url, String key, String value);

    /**
     * @param url
     * @param parameters
     * @return
     */
    public String buildUri(String url, Multimap<String, String> parameters);

}
