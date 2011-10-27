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

package org.jboss.seam.social.test;

import java.io.File;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;


/**
 * Prepares basic deployment of seam social and its dependencies
 */
public class Deployments
{
    public static WebArchive baseDeployment() {
        WebArchive archive = ShrinkWrap
            .create(WebArchive.class, "test.war")
            .addAsLibraries(
                ShrinkWrap.create(
                        ZipImporter.class, "seam-social-api.jar")
                        .importFrom(new File("../api/target/seam-social-api.jar"))
                        .as(JavaArchive.class),
                ShrinkWrap.create(
                        ZipImporter.class, "seam-social.jar")
                        .importFrom(new File("../impl/target/seam-social.jar"))
                        .as(JavaArchive.class));
        
        if ("weld-ee-embedded-1.1".equals(System.getProperty("arquillian"))) {
            // Don't embed dependencies that are already in the CL in the embedded container from surefire
            archive.addAsLibraries(
                    DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../settings.xml")
                            .loadMetadataFromPom("../impl/pom.xml")
                            .artifact("org.jboss.solder:solder-impl")                            
                            .resolveAs(GenericArchive.class));
        }
        else {
            archive.addAsLibraries(
                    DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../settings.xml")
                            .loadMetadataFromPom("../impl/pom.xml")
                            .artifact("org.jboss.solder:solder-impl")
                            .artifact("org.scribe:scribe")
                            .artifact("org.apache.commons:commons-lang3")
                            .artifact("org.codehaus.jackson:jackson-mapper-asl")
                            .artifact("com.google.guava:guava")
                            .resolveAs(GenericArchive.class));
        }
        
        return archive;
    }
}