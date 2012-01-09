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
package org.jboss.seam.social.twitter;

import java.util.Date;

/**
 * Represents a direct message.
 * 
 * @author Craig Walls
 * @author Antoine Sabot-Durand
 */
public class DirectMessage {
    private final long id;
    private final String text;
    private final TwitterProfile sender;
    private final TwitterProfile recipient;
    private final Date createdAt;

    public DirectMessage(long id, String text, TwitterProfile sender, TwitterProfile recipient, Date createdAt) {
        this.id = id;
        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public TwitterProfile getSender() {
        return sender;
    }

    public TwitterProfile getRecipient() {
        return recipient;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
