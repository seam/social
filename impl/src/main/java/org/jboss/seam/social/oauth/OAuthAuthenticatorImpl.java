/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.AuthenticationException;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.events.DeferredAuthenticationEvent;
import org.jboss.seam.security.management.picketlink.IdentitySessionProducer;
import org.jboss.seam.social.MultiServicesManager;
import org.jboss.solder.logging.Logger;
import org.picketlink.idm.api.Group;
import org.picketlink.idm.api.IdentitySession;
import org.picketlink.idm.api.Role;
import org.picketlink.idm.api.RoleType;
import org.picketlink.idm.api.User;
import org.picketlink.idm.common.exception.FeatureNotSupportedException;
import org.picketlink.idm.common.exception.IdentityException;

/**
 * An Authenticator implementation that uses OAuth to authenticate the user.
 * 
 * Based on OpenIdAuthenticator from Seam Security External module.
 * 
 * @author maschmid
 *
 */
@Named("oauthAuthenticator")
@SessionScoped
public class OAuthAuthenticatorImpl extends BaseAuthenticator implements OAuthAuthenticator, Authenticator, Serializable {

    private static final long serialVersionUID = 3431696230531662201L;

    @Inject
    @Any
    private Instance<OAuthService> oauthService;
    
    private String serviceName = null;
    
    /**
     * If this property is set to true (the default) then user roles and attributes will be managed using the Identity
     * Management API.
     */
    private boolean identityManaged = true;
    
    @Inject
    Instance<Identity> identity;
    
    @Inject
    MultiServicesManager multiServicesManager;
    
    @Inject
    Instance<IdentitySession> identitySession;
    
    @Inject
    Instance<IdentitySessionProducer> identitySessionProducer;
    
    @Inject
    Logger log;
    
    @Inject BeanManager beanManager;
    
    public boolean isIdentityManaged() {
        return identityManaged;
    }

    public void setIdentityManaged(boolean identityManaged) {
        this.identityManaged = identityManaged;
    }
    
    @Override
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    @Override
    public String getServiceName() {
        return serviceName;
    }
    
    @Override
    public List<String> getListOfServices() {
        return multiServicesManager.getListOfServices();
    }
    
    @Override
    public void authenticate() {
               
        if (serviceName == null) {
            
            log.debug("Service name null, authenticating with unamgiguous oauthService");
            
            if (!oauthService.isUnsatisfied() && !oauthService.isAmbiguous()) {
                OAuthService unambiguousOAuthService = oauthService.get();
                
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(unambiguousOAuthService.getAuthorizationUrl());
                    setStatus(AuthenticationStatus.DEFERRED);
                } catch (IOException e) {
                    log.error("Failed to redirect ", e);
                    setStatus(AuthenticationStatus.FAILURE);            
                }
            }
            else {
                throw new IllegalStateException("Service name not set and there is no unambiguous OAuthService available");
            }
        }
        else {
            
            log.debug("authenticating service \"" + serviceName + "\"");
            
            String serviceUrl = multiServicesManager.initNewSession(serviceName);
            
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(serviceUrl);
                setStatus(AuthenticationStatus.DEFERRED);
            } catch (IOException e) {
                log.error("Failed to redirect ", e);
                setStatus(AuthenticationStatus.FAILURE);
            }
        }
    }
    
    @Override
    public String getVerifierParamName() {
        if (serviceName != null) {
            return multiServicesManager.getCurrentService().getVerifierParamName();
        }
        else {            
            if (!oauthService.isUnsatisfied() && !oauthService.isAmbiguous()) {
                return oauthService.get().getVerifierParamName();
            }
            else {
                throw new IllegalStateException("Service name not set and there is no unambiguous OAuthService available");
            }
        }
    }
    
    @Override
    public String getVerifier() {
        if (serviceName != null) {
            return multiServicesManager.getCurrentSession().getVerifier();
        }
        else {
            if (!oauthService.isUnsatisfied() && !oauthService.isAmbiguous()) {
                return oauthService.get().getVerifier();
            }
            else {
                throw new IllegalStateException("Service name not set and there is no unambiguous OAuthService available");
            }
        }
    }
    
    @Override
    public void setVerifier(String verifier) {
        if (serviceName != null) {
            multiServicesManager.getCurrentSession().setVerifier(verifier);
        }
        else {        
            if (!oauthService.isUnsatisfied() && !oauthService.isAmbiguous()) {
                oauthService.get().setVerifier(verifier);
            }
            else {
                throw new IllegalStateException("Service name not set and there is no unambiguous OAuthService available");
            }
        }
    }

    @Override
    public void connect() {
        
        OAuthService currentService;
        OAuthSession currentSession;

        if (serviceName != null) {
            multiServicesManager.connectCurrentService();
           
            currentService = multiServicesManager.getCurrentService();
            currentSession = multiServicesManager.getCurrentSession();
        }
        else {
            if (!oauthService.isUnsatisfied() && !oauthService.isAmbiguous()) {
                currentService = oauthService.get();
                currentSession = currentService.getSession();
                
                currentService.initAccessToken();
            }
            else {
                throw new IllegalStateException("Service name not set and there is no unambiguous OAuthService available");
            }
        }
        
            
        OAuthUser user = new OAuthUser(currentService.getType(), currentSession.getUserProfile());
                
        if (isIdentityManaged()) {
            // By default we set the status to FAILURE, if we manage to get to the end
            // of this method we get rewarded with a SUCCESS
            setStatus(AuthenticationStatus.FAILURE);
                
            if (identitySessionProducer.get().isConfigured()) {
                validateManagedUser(user);
            }
        }
            
        setUser(user);
        setStatus(AuthenticationStatus.SUCCESS);
            
        beanManager.fireEvent(new DeferredAuthenticationEvent(true));
    }
    
    protected void validateManagedUser(OAuthUser principal) {
        IdentitySession session = identitySession.get();
        
        try {            
            // Check that the user's identity exists
            if (session.getPersistenceManager().findUser(principal.getId()) == null) {
                // The user wasn't found, let's create them
                
                User user = session.getPersistenceManager().createUser(principal.getId());
                
                // TODO allow the OAuth -> IDM attribute mapping to be configured
                // e.g.
                //session.getAttributesManager().addAttribute(user, "fullName", principal.getUserProfile().getFullName());
                //session.getAttributesManager().addAttribute(user, "profileImageUrl", principal.getUserProfile().getProfileImageUrl());
                     
                // Load the user's roles and groups        
                try {            
                    Collection<RoleType> roleTypes = session.getRoleManager().findUserRoleTypes(user);

                    for (RoleType roleType : roleTypes) {
                        for (Role role : session.getRoleManager().findRoles(user, roleType)) {
                            identity.get().addRole(role.getRoleType().getName(),
                                    role.getGroup().getName(), role.getGroup().getGroupType());
                        }
                    }
                    
                    for (Group g : session.getRelationshipManager().findAssociatedGroups(user)) {
                        identity.get().addGroup(g.getName(), g.getGroupType());
                    }
                } catch (FeatureNotSupportedException ex) {
                    throw new AuthenticationException("Error loading user's roles and groups", ex);
                } catch (IdentityException ex) {
                    throw new AuthenticationException("Error loading user's roles and groups", ex);
                }          
                
            }
        } catch (IdentityException ex) {
            throw new AuthenticationException("Error locating User record for OAuth user", ex);
        }     
    }
}
