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
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.social.MultiServicesManager;
import org.jboss.seam.social.SocialEvent;
import org.jboss.seam.social.StatusUpdated;
import org.jboss.seam.social.oauth.OAuthBaseService;
import org.jboss.seam.social.oauth.OAuthSession;
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

    private String Status;

    @Inject
    private Logger log;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Inject
    private MultiServicesManager manager;

    public MultiServicesManager getManager() {
        return manager;
    }

    public void setManager(MultiServicesManager manager) {
        this.manager = manager;
    }

    public OAuthSession getCurrentSession() {
        return manager.getCurrentSession();
    }

    public void setCurrentSession(OAuthSession currentSession) {
        manager.setCurrentSession(currentSession);
    }

    public Map<String, OAuthSession> getSessionsMap() {
        return Maps.uniqueIndex(getSessions(), new Function<OAuthSession, String>() {

            @Override
            public String apply(OAuthSession arg0) {

                return arg0.toString();
            }

        });
    }

    @Produces
    @Named
    public OAuthBaseService getCurrentService() {
        return manager.getCurrentService();
    }

    public List<OAuthSession> getSessions() {
        return newArrayList(manager.getActiveSessions());
    }

    public OAuthToken getAccessToken() {
        return getCurrentSession().getAccessToken();
    }

    public void connectCurrentService() {
        manager.connectCurrentService();
    }

    public String getCurrentSessionName() {
        return manager.getCurrentSession() == null ? "" : manager.getCurrentSession().toString();
    }

    public void setCurrentSessionName(String cursrvHdlStr) {
        setCurrentSession(getSessionsMap().get(cursrvHdlStr));
    }

    public void redirectToAuthorizationURL(String url) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(url);
    }

    public String getTimeLineUrl() {
        if (getCurrentSession() != null && getCurrentSession().isConnected())
            return "/WEB-INF/fragments/" + getManager().getCurrentService().getType().toLowerCase() + ".xhtml";
        return "";
    }

    public void serviceInit(String servType) throws IOException {

        redirectToAuthorizationURL(manager.initNewSession(servType));

    }

    protected void statusUpdateObserver(@Observes @Any StatusUpdated statusUpdate) {
        if (statusUpdate.getStatus().equals(SocialEvent.Status.SUCCESS)) {
            log.debugf("Status update with : %s ", statusUpdate.getMessage());
            setStatus("");
        }
    }

    public void resetConnection() {
        manager.destroyCurrentSession();
    }

}
