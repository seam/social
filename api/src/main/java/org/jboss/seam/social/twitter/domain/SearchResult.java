/**
 * 
 */
package org.jboss.seam.social.twitter.domain;

import java.util.List;

/**
 * @author antoine
 *
 */
public interface SearchResult
{

   public List<Tweet> getResults();

   public void setResults(List<Tweet> results);

   public long getMaxId();

   public void setMaxId(long maxId);

   public long getSinceId();

   public void setSinceId(long sinceId);

}