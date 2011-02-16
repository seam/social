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
package org.jboss.seam.social.twitter.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchResult
{
   private List<Tweet> results;

   @JsonProperty(value = "max_id")
   private long maxId;

   @JsonProperty(value = "since_id")
   private long sinceId;

   public List<Tweet> getResults()
   {
      return results;
   }

   public void setResults(List<Tweet> results)
   {
      this.results = results;
   }

   public long getMaxId()
   {
      return maxId;
   }

   public void setMaxId(long maxId)
   {
      this.maxId = maxId;
   }

   public long getSinceId()
   {
      return sinceId;
   }

   public void setSinceId(long sinceId)
   {
      this.sinceId = sinceId;
   }
   
   
}