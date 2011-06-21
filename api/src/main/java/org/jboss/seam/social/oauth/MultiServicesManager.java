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
package org.jboss.seam.social.oauth;

import java.util.List;
import java.util.Set;


/**
 * Implementation of this interface allow to manage multiple OAuth connection. The connection to service are backed by a Set to
 * avoid null or double connection. Uniqueness of a connection is based on service type and User name on the service
 * 
 * 
 * @author Antoine Sabot-Durand
 * 
 */
public interface MultiServicesManager {

    /**
     * @return Set of available service to connect to
     */
    public Set<String> getListOfServices();

    /**
     * Add a service to the Set of services to manage
     * 
     * @param service
     */
    public void addService(OAuthService service);

    /**
     * @return the Set of services connected
     */
    public Set<OAuthService> getServices();

   
    /**
     * Instantiate a new service from a Service String name
     * 
     * @param serviceName the name of the service
     * @return the Bean of the new service
     */
    public OAuthService getNewService(String serviceName);

    /**
     * @return the current service
     */
    OAuthService getCurrentService();

    /**
     * Set the current service. The service which is active at the moment
     * 
     * @param currentService
     */
    public void setCurrentService(OAuthService currentService);

    /**
     * @return the status of the current service.
     */
    public boolean isCurrentServiceConnected();

    /**
     * Connect the current service at the end of the OAuth process
     */
    public void connectCurrentService();

    /**
     * Instantiate a new Service which become the new current service
     * 
     * @param servType the type of the service to Instantiate
     * @return the authorization url to call to start the OAuth process
     */
    public String initNewService(String type);

    /**
     * Unconnect the current service and remove it from Set of managed service. Reset the currentService to null
     */
    public void destroyCurrentService();

}