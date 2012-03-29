/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.facebook.model;

import org.jboss.seam.social.exception.SeamSocialException;

/**
 * Exception thrown when attempting to perform an operation on a page by a user who is not a page administrator.
 * 
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class PageAdministrationException extends SeamSocialException {

    public PageAdministrationException(String pageId) {
        super("The user is not an administrator of the page with ID " + pageId);
    }

}
