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
package org.jboss.seam.social.linkedin;

import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.jboss.seam.social.linkedin.model.Profile;
import org.jboss.seam.social.linkedin.model.Update;
import org.jboss.seam.social.linkedin.model.UpdateJaxb;
import org.jboss.seam.social.oauth.HttpResponse;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.seam.social.oauth.OAuthServiceScribe;
import org.jboss.seam.social.oauth.OAuthServiceSettings;
import org.jboss.seam.social.oauth.RelatedTo;
import org.jboss.seam.social.oauth.RestVerb;
import org.jboss.seam.social.oauth.UserProfile;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;

/**
 * @author Antoine Sabot-Durand
 */
@RelatedTo(LinkedInScribe.TYPE)
public class LinkedInScribe extends OAuthServiceScribe implements LinkedIn {

    private static final long serialVersionUID = -6718362913575146613L;

    static final String USER_PROFILE_URL = "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,picture-url,site-standard-profile-request:(url))";
    static final Class<? extends Api> API_CLASS = LinkedInApi.class;
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/1-9-05/images/buttons/linkedin_connect.png";
    public static final String TYPE = "LinkedIn";
    static final String NETWORK_UPDATE_URL = "http://api.linkedin.com/v1/people/~/person-activities";

    JAXBContext context;
    Unmarshaller unmarshaller;
    Marshaller marshaller;

    @PostConstruct
    protected void init() {
        try {
            context = JAXBContext.newInstance("org.jboss.seam.social.linkedin.model");
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    @Inject
    public void setSettings(@RelatedTo(LinkedInScribe.TYPE) OAuthServiceSettings settings) {
        super.setSettings(settings);

    }
    
    

    @Produces
    protected OAuthService qualifiedLinkedInProducer(@New LinkedInScribe service) {
        return service;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthServiceScribe#getApiClass()
     */
    @Override
    protected Class<? extends Api> getApiClass() {
        return API_CLASS;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthService#getServiceLogo()
     */
    @Override
    public String getServiceLogo() {
        return LOGO_URL;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthService#getUserProfile()
     */
    @Override
    protected UserProfile getUser() {
       
            HttpResponse resp = sendSignedRequest(RestVerb.GET, USER_PROFILE_URL);
            try {
                
               return(UserProfile) unmarshaller.unmarshal(resp.getStream());
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       return null;
    }

    protected Profile getLinkedInProfile() {
        return (Profile) getUser();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.OAuthService#getType()
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus()
     */
    @Override
    public Object updateStatus() {

        return updateStatus(getStatus());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.seam.social.oauth.HasStatus#updateStatus(java.lang.String)
     */
    @Override
    public Update updateStatus(String message) {
        Update upd = new UpdateJaxb();
        String msg = "<a href=\"" + getLinkedInProfile().getStandardProfileUrl() + "\">" + getUser().getFullName() + "</a> "
                + message;
        upd.setBody(msg);
        StringWriter writer = new StringWriter();
        try {
            marshaller.marshal(upd, writer);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String update = writer.toString();

        HttpResponse resp = sendSignedXmlRequest(RestVerb.POST, NETWORK_UPDATE_URL, update);
        if (resp.getCode() == 201) {
            setStatus("");
            // everything is ok should notify caller
        } else {
            // something went wrong should we throw an exception ?
        }
        return upd;
    }

}
