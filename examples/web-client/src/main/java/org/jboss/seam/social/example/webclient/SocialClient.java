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
package org.jboss.seam.social.example.webclient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.jboss.logging.Logger;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthToken;
import org.jboss.seam.social.oauth.RelatedTo;
import org.jboss.seam.social.oauth.Service;
import org.jboss.seam.social.oauth.UserProfile;
import org.jboss.seam.social.twitter.Twitter;

@Named
@SessionScoped
public class SocialClient implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3723552335163650582L;

    @Inject
    @Any
    private Instance<OAuthService> serviceHandlerInstances;

    private OAuthService currentServiceHdl;
    private String status;

    private List<OAuthService> serviceHandlers;

    private Map<String, OAuthService> serviceHandlersMap;
    
    @Inject
    private Logger log;

    public List<OAuthService> getServiceHandlers() {
        return serviceHandlers;
    }

    @PostConstruct
    public void init() {

        /* example of bean selection with a given value for RelatedTo Qualifier */
        OAuthService temp=serviceHandlerInstances.select(new RelatedTo.RelatedToLiteral(Service.Twitter)).get();
        log.info("Type of Twitter bean "+temp.getType());
        
        serviceHandlersMap = Maps.uniqueIndex(serviceHandlerInstances, new Function<OAuthService, String>() {

            @Override
            public String apply(OAuthService arg0) {

                return arg0.getType();
            }
        });
        serviceHandlers = new ArrayList<OAuthService>(serviceHandlersMap.values());

    }

    public List<OAuthService> getConnectedServices() {
        return Lists.newArrayList(Iterables.filter(serviceHandlers, new Predicate<OAuthService>() {

            @Override
            public boolean apply(OAuthService arg0) {
                return arg0.isConnected();
            }
        }));
    }

    public List<OAuthService> getUnconnectedServices() {
        return Lists.newArrayList(Iterables.filter(serviceHandlers, new Predicate<OAuthService>() {

            @Override
            public boolean apply(OAuthService arg0) {
                return !arg0.isConnected();
            }
        }));
    }

    public OAuthToken getAccessToken() {
        return currentServiceHdl.getAccessToken();
    }

    public String getVerifier() {
        return currentServiceHdl.getVerifier();
    }

    public void setVerifier(String verifier) {
        currentServiceHdl.setVerifier(verifier);
    }

    public String getAuthorizationURL() {
        return currentServiceHdl.getAuthorizationUrl();
    }

    public void initAccessToken() {
        currentServiceHdl.initAccessToken();
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /*
     * public String updateStatus() { twitterHdl.updateStatus(status); status=""; return "ok"; }
     */

    public UserProfile getUser() {
        UserProfile res = currentServiceHdl == null ? null : currentServiceHdl.getUser();

        return res;
    }

    public OAuthService getCurrentServiceHdl() {
        return currentServiceHdl;
    }

    public void setCurrentServiceHdl(OAuthService currentServiceHdl) {
        this.currentServiceHdl = currentServiceHdl;
    }

    public String getCurrentServiceName() {
        return currentServiceHdl == null ? "" : currentServiceHdl.getType();
    }

    public void setCurrentServiceName(String cursrvHdlStr) {
        this.currentServiceHdl = serviceHandlersMap.get(cursrvHdlStr);
    }

    public void gotoAuthorizationURL(OAuthService service) throws IOException {
        setCurrentServiceHdl(service);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(getAuthorizationURL());
    }

    public boolean isCurrentServiceOk() {
        return currentServiceHdl != null && currentServiceHdl.isConnected();
    }

    public String getTimeLineUrl() {
        if (isCurrentServiceOk())
            return "/WEB-INF/fragments/" + currentServiceHdl.getType().toLowerCase() + ".xhtml";
        return "";
    }
}
