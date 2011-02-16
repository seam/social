/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.social.oauth;

import java.util.Map;

/**
 * Implementation of this interface is used to manage a generic OAuth Service
 * @author Antoine Sabot-Durand
 * 
 */
public interface OAuthServiceHandler
{

   public void init();

   /**
    * Send an OAuth request signed without any parameter
    * @param verb a REST verb
    * @param uri
    * @return an Object containing the response. It could be in various format (json, xml, string)
    */
   public Object sendSignedRequest(RestVerb verb, String uri);

   /**
    * Initialize the OAuth access token 
    */
   public void initAccessToken();

   public String getAuthorizationUrl();

   public Object sendSignedRequest(RestVerb verb, String uri, Map<String, Object> params);

   public Object sendSignedRequest(RestVerb verb, String uri, String key, Object value);

   public void setVerifier(String verifierStr);

   public String getVerifier();

   public String getAccessToken();

}
