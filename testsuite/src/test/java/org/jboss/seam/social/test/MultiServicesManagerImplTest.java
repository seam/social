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

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.MultiServicesManagerImpl;
import org.jboss.seam.social.UserProfile;
import org.jboss.seam.social.oauth.OAuthBaseService;
import org.jboss.seam.social.oauth.OAuthBaseServiceImpl;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MultiServicesManagerImplTest {

    @Deployment(name = "MultiServicesManagerImpl")
    public static Archive<?> createTestArchive() throws FileNotFoundException {
        return Deployments.baseDeployment();
    }

    @Inject
    MultiServicesManagerImpl msm;

    static UserProfile u1;
    static UserProfile u2;

    static OAuthBaseService serv1;
    static OAuthBaseService serv2;

    @BeforeClass
    public static void initClass() {
        u1 = mock(UserProfile.class);
        u2 = mock(UserProfile.class);
        when(u1.getId()).thenReturn("u1");
        when(u1.getFullName()).thenReturn("User 1");

        when(u2.getId()).thenReturn("u2");
        when(u2.getFullName()).thenReturn("User 2");

        serv1 = mock(OAuthBaseServiceImpl.class);
        serv2 = mock(OAuthBaseServiceImpl.class);

        when(serv1.getMyProfile()).thenReturn(u1);
        when(serv2.getMyProfile()).thenReturn(u2);

    }

    @Before
    public void initTest() {
        msm.getActiveSessions().clear();
    }

    @Test(expected = NullPointerException.class)
    public void testGetNewServiceWithNonExistingService() {
        msm.initNewSession("__NONEXIXTINGSERVICE__");

    }

    @Test(expected = NullPointerException.class)
    public void testGetNewServiceWithNullService() {
        msm.initNewSession(null);

    }

    @Test(expected = NullPointerException.class)
    public void testGetNewServiceWithEmptyService() {
        msm.initNewSession("");

    }

    @Test(expected = NullPointerException.class)
    public void testDestroyCurrentServiceWithNoService() {
        msm.destroyCurrentSession();
        assertEquals(msm.getActiveSessions().size(), 0);
        assertEquals(msm.getCurrentService(), null);

    }

    /*
     * @Test public void testDestroyCurrentServiceWithTwoServices() { Set<OAuthSession> services = msm.getActiveSessions();
     * services.add(serv1); services.add(serv2); msm.setCurrentSession(serv2); msm.destroyCurrentService();
     * assertEquals(msm.getServices().size(), 1); assertEquals(msm.getCurrentService(), serv1);
     * 
     * }
     */
}
