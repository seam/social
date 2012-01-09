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

/**
 * Exception indicating that an attempt was made to post a status or send a direct message where the length exceeds Twitter's
 * 140 character limit.
 * 
 * @author Craig Walls
 * @author Antoine Sabot-Durand
 */
@SuppressWarnings("serial")
public class MessageTooLongException extends IllegalArgumentException {

    public MessageTooLongException(String message) {
        super(message);
    }

}
