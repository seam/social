/*
 * Copyright 2010 the original author or authors.
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
package org.jboss.seam.social.facebook.model.jackson;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.jboss.seam.social.facebook.model.Reference;
import org.jboss.seam.social.facebook.model.Tag;

/**
 * Annotated mixin to add Jackson annotations to PhotoPost.
 * 
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class PhotoPostMixin extends PostMixin {

    @JsonCreator
    PhotoPostMixin(@JsonProperty("id") String id, @JsonProperty("from") Reference from,
            @JsonProperty("created_time") Date createdTime, @JsonProperty("updated_time") Date updatedTime) {
        super(id, from, createdTime, updatedTime);
    }

    @JsonProperty("object_id")
    String photoId;

    @JsonProperty("tags")
    @JsonDeserialize(using = TagListDeserializer.class)
    List<Tag> tags;

}
