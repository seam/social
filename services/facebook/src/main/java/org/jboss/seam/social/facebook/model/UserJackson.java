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
package org.jboss.seam.social.facebook.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.jboss.seam.social.UserProfile;

/**
 * @author Antoine Sabot-Durand
 * @author Todd Morrison
 *         <p/>
 *         { "id": "1234567", "name": "Antoine Sabot-Durand", "first_name": "Antoine", "last_name": "Sabot-Durand", "link":
 *         "http://www.facebook.com/antoine.sabotdurand", "username": "ASD", "birthday": "05/18/19XX", "hometown": { "id":
 *         "110774245616525", "name": "Paris, France" }, "location": { "id": "106108736094844", "name": "Villejuif, France" },
 *         "bio": "L'exp\u00e9rience est une lanterne qui n'\u00e9claire que celui qui la porte.", "work": [ { "employer": {
 *         "id": "109956419029713", "name": "Ippon Technologies" }, "position": { "id": "137677519604193", "name":
 *         "Manager Technique" }, "start_date": "2009-09" }, { "employer": { "id": "113944145284963", "name": "Quip Marketing"
 *         }, "location": { "id": "110774245616525", "name": "Paris, France" }, "position": { "id": "137148166320485", "name":
 *         "Directeur Technique" }, "start_date": "2007-04", "end_date": "2008-12" } ], "education": [ { "school": { "id":
 *         "115119648501580", "name": "EPITA" }, "year": { "id": "137409666290034", "name": "1995" }, "concentration": [ { "id":
 *         "129225533813483", "name": "T\u00e9l\u00e9com" } ], "type": "College" }, { "school": { "id": "110750642282837",
 *         "name": "Saint Michel de Picpus" }, "year": { "id": "112936752090738", "name": "1989" }, "type": "High School" } ],
 *         "gender": "male", "meeting_for": [ "Friendship", "Networking" ], "relationship_status": "Married", "website":
 *         "http://www.next-presso.fr", "timezone": 2, "locale": "fr_FR", "languages": [ { "id": "103803232991647", "name":
 *         "Anglais" }, { "id": "112264595467201", "name": "Fran\u00e7ais" } ], "verified": true, "updated_time":
 *         "2011-04-03T12:03:15+0000" }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserJackson extends UserProfile implements User {

    /**
     * @param id
     */
    protected UserJackson(String id) {
        super(id);
    }

    protected UserJackson() {

        super(null);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -6006391095667297569L;

    private static String API_URL = "http://graph.facebook.com/";

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty
    private String link;

    @Override
    public String getFullName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.User#getPictureUrl()
     */
    @Override
    public String getProfileImageUrl() {
        return API_URL + id + "/picture";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.User#getId()
     */
    @Override
    public String getId() {
        return this.id;
    }

}
