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

package org.jboss.seam.social;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.seam.social.oauth.OAuthService;

/**
 * 
 * Default implementation of {@link MultiServicesManager}
 * 
 * @author Antoine Sabot-Durand
 * 
 */
public class MultiServicesManagerImpl implements MultiServicesManager, Serializable {

    private static final long serialVersionUID = 2681869484541158766L;

    @Inject
    @Any
    private Instance<OAuthService> serviceInstances;

    @Inject
    private SeamSocialExtension socialConfig;

    private List<String> listOfServices;

    private Set<OAuthService> services;

    private OAuthService currentService;

    @PostConstruct
    void init() {
        listOfServices = newArrayList(socialConfig.getSocialRelated());
    }

    @Override
    public List<String> getListOfServices() {
        return listOfServices;
    }

    public MultiServicesManagerImpl() {
        super();
        services = newHashSet();
    }

    @Override
    public OAuthService getNewService(String serviceName) {
        if (isEmpty(serviceName))
            throw new IllegalArgumentException("Empty service name provided");
        if (!(listOfServices.contains(serviceName)))
            throw new IllegalArgumentException("Service " + serviceName + " is not available");
        OAuthService service = serviceInstances.select(socialConfig.getServicesToQualifier().inverse().get(serviceName)).get();
        return service;
    }

    @Override
    public Set<OAuthService> getServices() {
        return services;
    }

    private void addService(OAuthService service) {
        services.add(service);
    }

    @Override
    public OAuthService getCurrentService() {
        return currentService;
    }

    @Override
    public void setCurrentService(OAuthService currentService) {
        this.currentService = currentService;
    }

    @Override
    public boolean isCurrentServiceConnected() {
        return getCurrentService() != null && getCurrentService().isConnected();
    }

    @Override
    public void connectCurrentService() {
        getCurrentService().initAccessToken();
        addService(getCurrentService());
    }

    @Override
    public String initNewService(String servType) {
        setCurrentService(getNewService(servType));
        return getCurrentService().getAuthorizationUrl();

    }

    @Override
    public void destroyCurrentService() {
        if (getCurrentService() != null) {
            getServices().remove(getCurrentService());
            getCurrentService().resetConnection();
            setCurrentService(getServices().size() > 0 ? getServices().iterator().next() : null);
        }
    }

}
