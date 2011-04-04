/**
 * 
 */
package org.jboss.seam.social.facebook.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Antoine Sabot-Durand
 * 
 * {
   "id": "739958969",
   "name": "Antoine Sabot-Durand",
   "first_name": "Antoine",
   "last_name": "Sabot-Durand",
   "link": "http://www.facebook.com/antoine.sabotdurand",
   "username": "antoine.sabotdurand",
   "birthday": "05/18/1971",
   "hometown": {
      "id": "110774245616525",
      "name": "Paris, France"
   },
   "location": {
      "id": "106108736094844",
      "name": "Villejuif, France"
   },
   "bio": "L'exp\u00e9rience est une lanterne qui n'\u00e9claire que celui qui la porte.",
   "work": [
      {
         "employer": {
            "id": "109956419029713",
            "name": "Ippon Technologies"
         },
         "position": {
            "id": "137677519604193",
            "name": "Manager Technique"
         },
         "start_date": "2009-09"
      },
      {
         "employer": {
            "id": "113944145284963",
            "name": "Quip Marketing"
         },
         "location": {
            "id": "110774245616525",
            "name": "Paris, France"
         },
         "position": {
            "id": "137148166320485",
            "name": "Directeur Technique"
         },
         "start_date": "2007-04",
         "end_date": "2008-12"
      }
   ],
   "education": [
      {
         "school": {
            "id": "115119648501580",
            "name": "EPITA"
         },
         "year": {
            "id": "137409666290034",
            "name": "1995"
         },
         "concentration": [
            {
               "id": "129225533813483",
               "name": "T\u00e9l\u00e9com"
            }
         ],
         "type": "College"
      },
      {
         "school": {
            "id": "110750642282837",
            "name": "Saint Michel de Picpus"
         },
         "year": {
            "id": "112936752090738",
            "name": "1989"
         },
         "type": "High School"
      }
   ],
   "gender": "male",
   "meeting_for": [
      "Friendship",
      "Networking"
   ],
   "relationship_status": "Married",
   "website": "http://www.next-presso.fr",
   "timezone": 2,
   "locale": "fr_FR",
   "languages": [
      {
         "id": "103803232991647",
         "name": "Anglais"
      },
      {
         "id": "112264595467201",
         "name": "Fran\u00e7ais"
      }
   ],
   "verified": true,
   "updated_time": "2011-04-03T12:03:15+0000"
}
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserJackson implements User
{

   private static String API_URL="http://graph.facebook.com/";
   
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
   public String getFullName()
   {
     return name;
   }

   /* (non-Javadoc)
    * @see org.jboss.seam.social.oauth.User#getPictureUrl()
    */
   @Override
   public String getPictureUrl()
   {
      return API_URL + id + "/picture";
   }

}
