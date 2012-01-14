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
package org.jboss.seam.social.twitter.event;

import java.io.Serializable;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.seam.social.SocialEvent.Status;
import org.jboss.seam.social.StatusUpdated;
import org.jboss.seam.social.Twitter;
import org.jboss.seam.social.twitter.Tweet;
import org.jboss.seam.social.twitter.TwitterTimelineService;

@Decorator
/**
 * 
 * @author Antoine Sabot-Durand
 *
 */
public abstract class TwitterTimelineServiceDecorator implements TwitterTimelineService, Serializable {

    /**
     * 
     */
    @Inject
    @Delegate
    @Any
    private TwitterTimelineService twitterTimelineService;

    @Inject
    @Twitter
    private Event<StatusUpdated> statusUpdateEventProducer;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.TwitterTimelineService#updateStatus(java.lang.String)
     */
    @Override
    public Tweet updateStatus(String status) {
        Tweet res = twitterTimelineService.updateStatus(status);
        statusUpdateEventProducer.fire(new StatusUpdated(Status.SUCCESS, status, res));
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.TwitterTimelineService#deleteStatus(long)
     */
    @Override
    public void deleteStatus(long tweetId) {
        // TODO Auto-generated method stub
        twitterTimelineService.deleteStatus(tweetId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.TwitterTimelineService#retweet(long)
     */
    @Override
    public void retweet(long tweetId) {
        // TODO Auto-generated method stub
        twitterTimelineService.retweet(tweetId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.TwitterTimelineService#addToFavorites(long)
     */
    @Override
    public void addToFavorites(long id) {
        // TODO Auto-generated method stub
        twitterTimelineService.addToFavorites(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.TwitterTimelineService#removeFromFavorites(long)
     */
    @Override
    public void removeFromFavorites(long id) {
        // TODO Auto-generated method stub
        twitterTimelineService.removeFromFavorites(id);
    }

}
