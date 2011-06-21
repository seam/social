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

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.social.oauth.MultiServicesManager;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.seam.social.oauth.UserProfile;

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

   
    
    
    

    public OAuthService getCurrentService() {
        return manager.getCurrentService();
    }

    public void setCurrentService(OAuthService currentService) {
        manager.setCurrentService(currentService);
    }

    
    public boolean isCurrentServiceOk() {
        return manager.isCurrentServiceConnected();
    }

    public Map<String, OAuthService> getServicesMap() {
        return Maps.uniqueIndex(getServices(), new Function<OAuthService, String>() {

            @Override
            public String apply(OAuthService arg0) {

                return arg0.getName();
            }
        });
    }

  

    public Set<OAuthService> getServices() {
        return manager.getServices();
    }



    public OAuthToken getAccessToken() {
        return getCurrentService().getAccessToken();
    }

    public String getVerifier() {
        return getCurrentService().getVerifier();
    }

    public void setVerifier(String verifier) {
        getCurrentService().setVerifier(verifier);
    }
 

      public void connectCurrentService() {
        manager.connectCurrentService();
    }

    public UserProfile getUser() {
        UserProfile res = getCurrentService() == null ? null : getCurrentService().getSession().getUserProfile();

        return res;
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
        if (isCurrentServiceOk())
            return "/WEB-INF/fragments/" + getCurrentService().getType().toLowerCase() + ".xhtml";
        return "";
    }

    public void serviceInit(String servType) throws IOException {
        
        redirectToAuthorizationURL(manager.initNewService(servType));

    }
    
    public void resetConnection()
    {
        manager.destroyCurrentService();
    }

}
