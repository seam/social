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

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import org.jboss.seam.social.TwitterLiteral;
import org.jboss.seam.social.oauth.OAuthBaseServiceImpl;
import org.jboss.seam.social.twitter.TwitterBaseService;
import org.jboss.seam.social.twitter.TwitterProfile;
import org.jboss.solder.logging.Logger;

/**
 * @author Antoine Sabot-Durand
 * @author Craig Walls
 * 
 */
public class TwitterBaseServiceImpl extends OAuthBaseServiceImpl implements TwitterBaseService {

    private static final long serialVersionUID = 6806035986656777834L;
    static final String API_ROOT = "https://api.twitter.com/1/";
    static final String VERIFY_CREDENTIALS_URL = "account/verify_credentials.json";

    static final String FRIENDS_STATUSES_URL = "/statuses/friends.json?screen_name={screen_name}";

    // static final String SEARCH_URL = "https://search.twitter.com/search.json?q={query}&rpp={rpp}&page={page}";
    static final String TWEET_URL = "/statuses/update.json";
    static final String RETWEET_URL = "/statuses/retweet/{tweet_id}.json";
    static final String LOGO_URL = "https://d2l6uygi1pgnys.cloudfront.net/2-2-08/images/buttons/twitter_connect.png";

    @Inject
    Logger log;

    @Override
    public String getApiRootUrl() {
        return API_ROOT;
    }

    @Override
    protected void initMyProfile() {
        getSession().setUserProfile(getUserProfile());
    }

    public TwitterProfile getUserProfile() {
        requireAuthorization();
        return requestObject(buildUri(VERIFY_CREDENTIALS_URL), TwitterProfile.class);
    }

    @Override
    public String getServiceLogo() {
        return LOGO_URL;
    }

    @Override
    public TwitterProfile getMyProfile() {
        return (TwitterProfile) getSession().getUserProfile();
    }

    @Override
    public Annotation getQualifier() {
        return TwitterLiteral.INSTANCE;
    }

}
