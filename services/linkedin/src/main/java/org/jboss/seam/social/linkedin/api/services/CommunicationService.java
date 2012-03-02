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
package org.jboss.seam.social.linkedin.api.services;

import java.util.List;

import org.jboss.seam.social.linkedin.api.model.ApiStandardProfileRequest;

/**
 * Operations related to sending messages and sending connect invitations to other users on LinkedIn
 * 
 * @author Robert Drysdale
 * @author Antoine Sabot-Durand
 * 
 */
public interface CommunicationService {

    /**
     * Send a textual message to a list of recipientIds Requires id from LinkedInProfile object
     * 
     * @param subject The subject of message
     * @param body The body or text of message (does not support html)
     * @param recipientIds List of ids
     */
    void sendMessage(String subject, String body, List<String> recipientIds);

    /**
     * Send a textual message to recipientId(s) Requires id from LinkedInProfile object
     * 
     * @param subject The subject of message
     * @param body The body or text of message (does not support html)
     * @param recipientIds One of more ids
     */
    void sendMessage(String subject, String body, String... recipientIds);

    /**
     * Send a connect invitation message to recipientId Requires id from LinkedInProfile object
     * 
     * @param subject The subject of message
     * @param body The body or text of message (does not support html)
     * @param recipientId Id of recipient
     * @param apiStandardProfileRequest Part of LinkedInProfile returned when perfroming a search
     */
    void connectTo(String subject, String body, String recipientId, ApiStandardProfileRequest apiStandardProfileRequest);

    /**
     * Send a connect invitation message to and email (for users not on LinkedIn)
     * 
     * @param subject The subject of message
     * @param body The body or text of message (does not support html)
     * @param email Email address of recipient
     * @param firstName First Name of recipient
     * @param lastName Last Name of recipient
     */
    void connectTo(String subject, String body, String email, String firstName, String lastName);

}
