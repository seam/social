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
package org.jboss.seam.social.twitter.cdi;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.seam.social.Twitter;
import org.jboss.seam.social.event.SocialEvent.Status;
import org.jboss.seam.social.event.StatusUpdated;
import org.jboss.seam.social.twitter.Tweet;
import org.jboss.seam.social.twitter.TwitterTimelineService;

@Decorator
/**
 * 
 * @author Antoine Sabot-Durand
 *
 */
public abstract class TwitterTimelineServiceDecorator implements TwitterTimelineService {

    /**
     * 
     */
    @Inject
    @Delegate
    @Any
    private TwitterTimelineService delegate;

    @Inject
    @Twitter
    private Event<StatusUpdated> statusUpdateEventProducer;

    @Override
    public Tweet updateStatus(String status) {
        Tweet res = delegate.updateStatus(status);
        statusUpdateEventProducer.fire(new StatusUpdated(Status.SUCCESS, status, res));
        return res;
    }

}
