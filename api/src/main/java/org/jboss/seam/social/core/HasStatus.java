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

/**
 * Social network Handler implementing this interface will support status update
 *
 * @author Antoine Sabot-Durand
 */
public interface HasStatus {

    /**
     * @return the status update
     */
    public String getStatus();

    /**
     * Set the status to update
     *
     * @param status
     */
    public void setStatus(String status);

    /**
     * Send the status to the social network
     *
     * @return an Object corresponding to the update
     */
    public Object updateStatus();

    /**
     * Send the status in parameter
     *
     * @param message the status to send
     * @return an Object corresponding to the update
     */
    public Object updateStatus(String message);
}
