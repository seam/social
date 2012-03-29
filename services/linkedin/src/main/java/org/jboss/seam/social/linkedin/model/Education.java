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

import java.io.Serializable;

/**
 * Model class representing education details for a Profile on LinkedIn
 * 
 * @author Robert Drysdale
 * @author Antoine Sabot-Durand
 */
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String activities;

    private final String degree;

    private final String fieldOfStudy;

    private final String id;

    private final String notes;

    private final String schoolName;

    private final LinkedInDate startDate;

    private final LinkedInDate endDate;

    public Education(String activities, String degree, String fieldOfStudy, String id, String notes, String schoolName,
            LinkedInDate startDate, LinkedInDate endDate) {
        this.activities = activities;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.id = id;
        this.notes = notes;
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getActivities() {
        return activities;
    }

    public String getDegree() {
        return degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getId() {
        return id;
    }

    public String getNotes() {
        return notes;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public LinkedInDate getStartDate() {
        return startDate;
    }

    public LinkedInDate getEndDate() {
        return endDate;
    }

}
