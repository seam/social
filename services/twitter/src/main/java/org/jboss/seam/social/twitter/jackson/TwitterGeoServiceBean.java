/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.twitter.jackson;

import java.util.List;

import org.jboss.seam.social.twitter.GeoService;
import org.jboss.seam.social.twitter.Place;
import org.jboss.seam.social.twitter.PlacePrototype;
import org.jboss.seam.social.twitter.PlaceType;
import org.jboss.seam.social.twitter.SimilarPlaces;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class TwitterGeoServiceBean extends TwitterBaseServiceImpl implements GeoService {

    public Place getPlace(String placeId) {
        return requestObject(buildUri("geo/id/" + placeId + ".json"), Place.class);
    }

    public List<Place> reverseGeoCode(double latitude, double longitude) {
        return reverseGeoCode(latitude, longitude, null, null);
    }

    public List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy) {
        Multimap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, null);
        return requestObject(buildUri("geo/reverse_geocode.json", parameters), PlacesList.class).getList();
    }

    public List<Place> search(double latitude, double longitude) {
        return search(latitude, longitude, null, null, null);
    }

    public List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
        Multimap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, query);
        return requestObject(buildUri("geo/search.json", parameters), PlacesList.class).getList();
    }

    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name) {
        return findSimilarPlaces(latitude, longitude, name, null, null);
    }

    public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name, String streetAddress,
            String containedWithin) {
        Multimap<String, String> parameters = buildPlaceParameters(latitude, longitude, name, streetAddress, containedWithin);
        SimilarPlacesResponse response = requestObject(buildUri("geo/similar_places.json", parameters),
                SimilarPlacesResponse.class);
        PlacePrototype placePrototype = new PlacePrototype(response.getToken(), latitude, longitude, name, streetAddress,
                containedWithin);

        return new SimilarPlaces(response.getPlaces(), placePrototype);
    }

    public Place createPlace(PlacePrototype placePrototype) {
        Multimap<String, String> request = buildPlaceParameters(placePrototype.getLatitude(), placePrototype.getLongitude(),
                placePrototype.getName(), placePrototype.getStreetAddress(), placePrototype.getContainedWithin());
        request.put("token", placePrototype.getCreateToken());
        return postObject("https://api.twitter.com/1/geo/place.json", request, Place.class);
    }

    // private helpers

    private Multimap<String, String> buildGeoParameters(double latitude, double longitude, PlaceType granularity,
            String accuracy, String query) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("lat", String.valueOf(latitude));
        parameters.put("long", String.valueOf(longitude));
        if (granularity != null) {
            parameters.put("granularity", granularity.equals(PlaceType.POINT_OF_INTEREST) ? "poi" : granularity.toString()
                    .toLowerCase());
        }
        if (accuracy != null) {
            parameters.put("accuracy", accuracy);
        }
        if (query != null) {
            parameters.put("query", query);
        }
        return parameters;
    }

    private Multimap<String, String> buildPlaceParameters(double latitude, double longitude, String name, String streetAddress,
            String containedWithin) {
        Multimap<String, String> parameters = LinkedListMultimap.create();
        parameters.put("lat", String.valueOf(latitude));
        parameters.put("long", String.valueOf(longitude));
        parameters.put("name", name);
        if (streetAddress != null) {
            parameters.put("attribute:street_address", streetAddress);
        }
        if (containedWithin != null) {
            parameters.put("contained_within", containedWithin);
        }
        return parameters;
    }

}
