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
package org.jboss.seam.social.twitter.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * A POJO to map Json response from SetTwitter verify_crendentials response to Java. Here is an example of data sent from SetTwitter
 * 
 * <pre>
 * {
 *   "profile_sidebar_border_color": "C0DEED",
 *   "name": "Antoine Sabot-Durand",
 *   "profile_background_tile": false,
 *   "profile_sidebar_fill_color": "DDEEF6",
 *   "profile_image_url": "http:\/\/a2.twimg.com\/profile_images\/1115657884\/image_normal.jpg",
 *   "created_at": "Wed Apr 08 15:13:18 +0000 2009",
 *   "location": "Paris France",
 *   "follow_request_sent": false,
 *   "profile_link_color": "0084B4",
 *   "is_translator": false,
 *   "id_str": "29736416",
 *   "url": "http:\/\/www.next-presso.fr",
 *   "favourites_count": 87,
 *   "contributors_enabled": false,
 *   "utc_offset": 3600,
 *   "id": 29736416,
 *   "listed_count": 15,
 *   "profile_use_background_image": true,
 *   "protected": false,
 *   "followers_count": 359,
 *   "profile_text_color": "333333",
 *   "lang": "fr",
 *   "notifications": false,
 *   "verified": false,
 *   "geo_enabled": true,
 *   "time_zone": "Paris",
 *   "description": "Senior Java architect and IT fan. I work at Ippon Technologies (Paris) and  I tweet about Java, Java EE 6, JBoss Seam, Mac, iPhone and my Digital Life.",
 *   "profile_background_color": "C0DEED",
 *   "friends_count": 330,
 *   "profile_background_image_url": "http:\/\/a3.twimg.com\/a\/1297400184\/images\/themes\/theme1\/bg.png",
 *   "status": {
 *     "coordinates": null,
 *     "favorited": false,
 *     "truncated": false,
 *     "created_at": "Tue Feb 22 08:08:06 +0000 2011",
 *     "id_str": "39959605119287296",
 *     "in_reply_to_user_id_str": "17415957",
 *     "contributors": null,
 *     "text": "@OlivierCroisier you should annotate your client with @Deprecated",
 *     "retweet_count": 0,
 *     "id": 39959605119287296,
 *     "in_reply_to_status_id_str": "39956367208878080",
 *     "geo": null,
 *     "retweeted": false,
 *     "in_reply_to_user_id": 17415957,
 *     "in_reply_to_screen_name": "OlivierCroisier",
 *     "source": "\u003Ca href=\"http:\/\/itunes.apple.com\/app\/twitter\/id333903271?mt=8\" rel=\"nofollow\"\u003ETwitter for iPad\u003C\/a\u003E",
 *     "place": null,
 *     "in_reply_to_status_id": 39956367208878080
 *   },
 *   "statuses_count": 2190,
 *   "show_all_inline_media": false,
 *   "screen_name": "antoine_sd",
 *   "following": false
 * }
 * </pre>
 * 
 * @author Antoine Sabot-Durand
 * @author Todd Morrison
 * 
 * 
 * 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialJackson implements Credential {
	
	@JsonProperty
	private String id;
	
    @JsonProperty
    private String name;

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("profile_image_url")
    private String pictureUrl;

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.twitter.domain.Credential#getScreenName()
     */
    @Override
    public String getScreenName() {
        return screenName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jboss.seam.social.oauth.OAuthUserProfile#getFullName()
     */
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
    public String getPictureUrl() {
        return pictureUrl;
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
