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

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;
import org.jboss.seam.social.linkedin.model.LinkedInConnections;
import org.jboss.seam.social.linkedin.model.LinkedInProfile;

/**
 * Jackson module for registering mixin annotations against LinkedIn model classes.
 * 
 * @author Craig Walls
 * @author Antoine Sabot-Durand
 */
class LinkedInModule extends SimpleModule {

    public LinkedInModule() {
        super("LinkedInModule", new Version(1, 0, 0, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(LinkedInConnections.class, LinkedInConnectionsMixin.class);
        context.setMixInAnnotations(LinkedInProfile.class, LinkedInProfileMixin.class);
    }

}
