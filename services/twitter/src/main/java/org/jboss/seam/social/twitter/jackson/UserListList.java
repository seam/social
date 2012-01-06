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
package org.jboss.seam.social.twitter.jackson;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.seam.social.twitter.CursoredList;
import org.jboss.seam.social.twitter.UserList;

/**
 * Holder for list of UserList, pulled from JSON object's "lists" property.
 * 
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class UserListList {
    private final CursoredList<UserList> list;

    @JsonCreator
    public UserListList(@JsonProperty("lists") List<UserList> list, @JsonProperty("previous_cursor") long previousCursor,
            @JsonProperty("next_cursor") long nextCursor) {
        this.list = new CursoredList<UserList>(list, previousCursor, nextCursor);
    }

    @JsonIgnore
    public CursoredList<UserList> getList() {
        return list;
    }
}
