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
package org.jboss.seam.social.linkedin.model;

import org.jboss.seam.social.core.UserProfile;

/**
 * Model class containing a user's LinkedIn profile information.
 * 
 * @author Craig Walls
 * @author Antoine Sabot-Durand
 */
public class LinkedInProfile extends UserProfile {

    private static final long serialVersionUID = -7211847959164588503L;

    private final String firstName;

    private final String lastName;

    private final String headline;

    private final String industry;

    private final String standardProfileUrl;

    private final String publicProfileUrl;

    private final String profilePictureUrl;

    public LinkedInProfile(String id, String firstName, String lastName, String headline, String industry,
            String publicProfileUrl, String standardProfileUrl, String profilePictureUrl) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.headline = headline;
        this.industry = industry;
        this.publicProfileUrl = publicProfileUrl;
        this.standardProfileUrl = standardProfileUrl;
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * The user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The user's headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * The user's industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * A URL to the user's standard profile. The content shown at this profile will depend upon what the requesting user is
     * allowed to see.
     */
    public String getStandardProfileUrl() {
        return standardProfileUrl;
    }

    /**
     * A URL to the user's public profile. The content shown at this profile is intended for public display and is determined by
     * the user's privacy settings. May be null if the user's profile isn't public.
     */
    public String getPublicProfileUrl() {
        return publicProfileUrl;
    }

    /**
     * A URL to the user's profile picture.
     */
    public String getProfileImageUrl() {
        return profilePictureUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.core.UserProfile#getFullName()
     */
    @Override
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
