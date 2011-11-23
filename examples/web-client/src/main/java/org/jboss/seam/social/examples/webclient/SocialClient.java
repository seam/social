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
package org.jboss.seam.social.examples.webclient;

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.MultiServicesManager;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.solder.logging.Logger;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

@Named
@SessionScoped
public class SocialClient implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3723552335163650582L;

    @Inject
    private MultiServicesManager manager;

    @Inject
    private Logger log;

    public MultiServicesManager getManager() {
        return manager;
    }

    public void setManager(MultiServicesManager manager) {
        this.manager = manager;
    }

    @Produces
    @Named
    public OAuthService getCurrentService() {
        return manager.getCurrentService();
    }

    public void setCurrentService(OAuthService currentService) {
        manager.setCurrentService(currentService);
    }

    public Map<String, OAuthService> getServicesMap() {
        return Maps.uniqueIndex(getServices(), new Function<OAuthService, String>() {

            @Override
            public String apply(OAuthService arg0) {

                return arg0.getName();
            }
        });
    }

    public List<OAuthService> getServices() {
        return newArrayList(manager.getServices());
    }

    public OAuthToken getAccessToken() {
        return getCurrentService().getAccessToken();
    }

    public void connectCurrentService() {
        manager.connectCurrentService();
    }

    public String getCurrentServiceName() {
        return getCurrentService() == null ? "" : getCurrentService().getName();
    }

    public void setCurrentServiceName(String cursrvHdlStr) {
        setCurrentService(getServicesMap().get(cursrvHdlStr));
    }

    public void redirectToAuthorizationURL(String url) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(url);
    }

    public String getTimeLineUrl() {
        if (getCurrentService() != null && getCurrentService().isConnected())
            return "/WEB-INF/fragments/" + getCurrentService().getType().toLowerCase() + ".xhtml";
        return "";
    }

    public void serviceInit(String servType) throws IOException {

        redirectToAuthorizationURL(manager.initNewService(servType));

    }

    public void resetConnection() {
        manager.destroyCurrentService();
    }

}
