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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.codehaus.jackson.map.Module;
import org.jboss.seam.social.rest.RestResponse;

/**
 * 
 * This Bean drives all the upper services for serialize/deserialize Object to/from Json It also register all the Jackson Mixin
 * module declared in social service modules.
 * 
 * @author Antoine Sabot-Durand
 * 
 */
@ApplicationScoped
public class JsonServiceJackson implements Serializable {

    private static final long serialVersionUID = -7806134655399349774L;

    @Inject
    protected JsonMapper jsonMapper;

    @Inject
    @Any
    protected Instance<Module> moduleInstances;

    /**
     * @return
     */
    public <T> T requestObject(RestResponse resp, Class<T> clazz) {
        return jsonMapper.readValue(resp, clazz);
    }

    @PostConstruct
    protected void init() {
        for (Module module : moduleInstances) {
            jsonMapper.registerModule(module);
        }
    }

}
