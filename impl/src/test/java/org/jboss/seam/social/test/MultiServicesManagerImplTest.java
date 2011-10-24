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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.MultiServicesManagerImpl;
import org.jboss.seam.social.OAuthServiceBase;
import org.jboss.seam.social.UserProfile;
import org.jboss.seam.social.oauth.OAuthService;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MultiServicesManagerImplTest {

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        Archive<?> ret = ShrinkWrap
                .create(WebArchive.class, "social.war")
                .addClasses(MultiServicesManagerImpl.class)

                .addAsLibraries(
                        DependencyResolvers.use(MavenDependencyResolver.class).configureFrom("../settings.xml")
                                .loadMetadataFromPom("pom.xml").artifact("org.jboss.solder:solder-impl")
                                .artifact("org.scribe:scribe").resolveAs(GenericArchive.class));
        // ret.as(ZipExporter.class).exportTo(new File("mytest.war"), true);

        return ret;
    }

    @Inject
    MultiServicesManagerImpl msm;

    static UserProfile u1;
    static UserProfile u2;

    static OAuthService serv1;
    static OAuthService serv2;

    @BeforeClass
    public static void initClass() {
        u1 = mock(UserProfile.class);
        u2 = mock(UserProfile.class);
        when(u1.getId()).thenReturn("u1");
        when(u1.getFullName()).thenReturn("User 1");

        when(u2.getId()).thenReturn("u2");
        when(u2.getFullName()).thenReturn("User 2");

        serv1 = mock(OAuthServiceBase.class);
        serv2 = mock(OAuthServiceBase.class);

        when(serv1.getMyProfile()).thenReturn(u1);
        when(serv2.getMyProfile()).thenReturn(u2);

    }

    @Before
    public void initTest() {
        msm.getServices().clear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNewServiceWithNonExistingService() {
        msm.getNewService("__NONEXIXTINGSERVICE__");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNewServiceWithNullService() {
        msm.getNewService(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNewServiceWithEmptyService() {
        msm.getNewService("");

    }

    @Test
    public void testDestroyCurrentServiceWithNoService() {
        msm.destroyCurrentService();
        assertEquals(msm.getServices().size(), 0);
        assertEquals(msm.getCurrentService(), null);

    }

    @Test
    public void testDestroyCurrentServiceWithTwoServices() {
        Set<OAuthService> services = msm.getServices();
        services.add(serv1);
        services.add(serv2);
        msm.setCurrentService(serv2);
        msm.destroyCurrentService();
        assertEquals(msm.getServices().size(), 1);
        assertEquals(msm.getCurrentService(), serv1);

    }
}
