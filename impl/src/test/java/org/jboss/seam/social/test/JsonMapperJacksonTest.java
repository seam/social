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

import static org.mockito.Mockito.when;

import org.jboss.seam.social.JsonMapperJackson;
import org.jboss.seam.social.exception.SeamSocialException;
import org.jboss.seam.social.exception.SeamSocialRestException;
import org.jboss.seam.social.rest.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonMapperJacksonTest {

    @InjectMocks
    JsonMapperJackson jm;

    @Mock
    RestResponse resp;

    @Test(expected = NullPointerException.class)
    public void testReadNullResponse() {
        jm.mapToObject(null, Object.class);
    }

    @Test(expected = SeamSocialException.class)
    public void testReadEmptyBody() {
        when(resp.getBody()).thenReturn("");
        when(resp.getCode()).thenReturn(200);

        jm.mapToObject(resp, Object.class);
    }

    @Test(expected = SeamSocialRestException.class)
    public void testErrorReturnCode() {
        when(resp.getBody()).thenReturn("");
        when(resp.getCode()).thenReturn(400);
        when(resp.getBody()).thenReturn("An error\nMessage");

        jm.mapToObject(resp, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullBody() {
        when(resp.getCode()).thenReturn(200);
        when(resp.getBody()).thenReturn(null);

        jm.mapToObject(resp, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullModule() {

        jm.registerModule(null);

    }
}
