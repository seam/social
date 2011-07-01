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
package org.jboss.seam.social.core;

import java.io.InputStream;
import java.util.Map;

/**
 * Implementation of this interface represent an Http Response
 *
 * @author Antoine Sabot-Durand
 */
public interface HttpResponse {

    /**
     * @return the body of the response in a {@link String}
     */
    public String getBody();

    /**
     * @return the body of the response in a {@link InputStream}
     */
    public InputStream getStream();

    /**
     * @return the HTTP return code of the response
     */
    public int getCode();

    /**
     * @return the HTTP Response headers in {@link Map}
     */
    public Map<String, String> getHeaders();

    /**
     * @param name of the HTTP header
     * @return the value of the HTTP header
     */
    public String getHeader(String name);

}
