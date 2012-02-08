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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jboss.seam.social.JsonMapperJackson;
import org.jboss.seam.social.SeamSocialException;
import org.jboss.seam.social.rest.RestResponse;
import org.junit.Before;
import org.junit.Test;

public class JsonMapperJacksonTest {
    JsonMapperJackson jm;

    @Before
    public void setUp() {
        jm = new JsonMapperJackson();
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullResponse() {
        jm.mapToObject(null, Object.class);
    }

    @Test(expected = SeamSocialException.class)
    public void testReadEmptyBody() {
        RestResponse resp = mock(RestResponse.class);
        when(resp.getBody()).thenReturn("");

        jm.mapToObject(resp, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullBody() {
        RestResponse resp = mock(RestResponse.class);
        when(resp.getBody()).thenReturn(null);

        jm.mapToObject(resp, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullModule() {

        jm.registerModule(null);

    }
}
