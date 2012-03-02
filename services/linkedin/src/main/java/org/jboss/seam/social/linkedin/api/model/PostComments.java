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
package org.jboss.seam.social.linkedin.api.model;

import java.util.List;

/**
 * Structure which contains list of post comments inside
 * 
 * @author Robert Drysdale
 * @author Antoine Sabot-Durand
 */
public class PostComments extends SearchResult {

    private static final long serialVersionUID = 1L;

    private List<PostComment> comments;

    public PostComments(int count, int start, int total) {
        super(count, start, total);
    }

    public List<PostComment> getComments() {
        return comments;
    }

}
