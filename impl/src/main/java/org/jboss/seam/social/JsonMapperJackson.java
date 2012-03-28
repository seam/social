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

import static org.codehaus.jackson.map.DeserializationConfig.Feature.READ_ENUMS_USING_TO_STRING;
import static org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_ENUMS_USING_TO_STRING;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.jboss.seam.social.exception.SeamSocialException;
import org.jboss.seam.social.exception.SeamSocialRestException;
import org.jboss.seam.social.rest.RestResponse;

/**
 * This Bean manage the Json Mapper to serialize and de-serialize Json data coming from and sent to services It uses an
 * {@link ObjectMapper} Jackson.
 * 
 * @author Antoine Sabot-Durand
 */
@ApplicationScoped
public class JsonMapperJackson implements JsonMapper {

    private static final long serialVersionUID = -2012295612034078749L;

    private final ObjectMapper delegate = new ObjectMapper();

    @Inject
    @Any
    protected Instance<Module> moduleInstances;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.JsonMapper#requestObject(org.jboss.seam.social.rest.RestResponse, java.lang.Class)
     */
    @Override
    public <T> T mapToObject(RestResponse resp, Class<T> clazz) {
        try {
            String msg = resp.getBody();
            if (resp.getCode() != 200) {
                throw new SeamSocialRestException(resp.getCode(), resp.getUrl(), msg);
            }
            return delegate.readValue(msg, clazz);
        } catch (IOException e) {
            throw new SeamSocialException("Unable to map Json response", e);
        }
    }

    @Override
    public String ObjectToJsonString(Object obj) {
        try {
            return delegate.writeValueAsString(obj);
        } catch (Exception e) {
            throw new SeamSocialException("Unable to map a " + obj.getClass().getName() + " to json", e);
        }
    }

    /**
     * 
     * Register a Jackson configuration {@link Module} to set special rules for de-serialization
     * 
     * @param module to register
     */
    public void registerModule(Module module) {
        delegate.registerModule(module);
    }

    @PostConstruct
    protected void init() {
        delegate.enable(WRITE_ENUMS_USING_TO_STRING).setSerializationInclusion(Inclusion.NON_NULL);
        delegate.enable(READ_ENUMS_USING_TO_STRING);
        for (Module module : moduleInstances) {
            registerModule(module);
        }

    }
}
