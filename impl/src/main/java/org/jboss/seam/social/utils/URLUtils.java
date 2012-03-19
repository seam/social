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
package org.jboss.seam.social.utils;

import static com.google.common.collect.Maps.newHashMap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.jboss.seam.social.exception.SeamSocialException;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.collect.Maps;

/**
 * Utils to deal with URL and url-encodings
 * 
 * @author Pablo Fernandez
 * @author Antoine Sabot-Durand
 */
public class URLUtils {

    private static class formUrlEncodeFunc implements Function<Object, String> {

        @Override
        public String apply(Object input) {
            // TODO Auto-generated method stub
            return formURLEncode(input.toString());
        }
    }

    private static final String EMPTY_STRING = "";
    private static final String UTF_8 = "UTF-8";
    private static final String PAIR_SEPARATOR = "=";
    private static final String PARAM_SEPARATOR = "&";
    private static final char MULTI_VALUE_SEPARATOR = ',';
    private static final char QUERY_STRING_SEPARATOR = '?';

    private static final String ERROR_MSG = String.format("Cannot find specified encoding: %s", UTF_8);

    private static final Set<EncodingRule> ENCODING_RULES;

    public static Joiner commaJoiner = Joiner.on(MULTI_VALUE_SEPARATOR).skipNulls();
    private static MapJoiner queryMapJoiner = Joiner.on(PARAM_SEPARATOR).withKeyValueSeparator(PAIR_SEPARATOR);

    static {
        Set<EncodingRule> rules = new HashSet<EncodingRule>();
        rules.add(new EncodingRule("*", "%2A"));
        rules.add(new EncodingRule("+", "%20"));
        rules.add(new EncodingRule("%7E", "~"));
        ENCODING_RULES = Collections.unmodifiableSet(rules);
    }

    /**
     * Turns a map into a form-urlencoded string
     * 
     * @param parameters any map
     * @return form-url-encoded string
     */
    public static String formURLEncodeMap(Map<String, ? extends Object> parameters) {
        return (parameters.size() <= 0) ? EMPTY_STRING : doFormUrlEncode(parameters);
    }

    public static String doFormUrlEncode(Map<String, ? extends Object> params) {
        Map<String, String> urlEncodeMap = Maps.transformValues(params, new formUrlEncodeFunc());
        return queryMapJoiner.join(urlEncodeMap);
    }

    /**
     * Percent encodes a string
     * 
     * @param string plain string
     * @return percent encoded string
     */
    public static String percentEncode(String string) {
        String encoded = formURLEncode(string);
        for (EncodingRule rule : ENCODING_RULES) {
            encoded = rule.apply(encoded);
        }
        return encoded;
    }

    /**
     * Translates a string into application/x-www-form-urlencoded format
     * 
     * @param plain
     * @return form-urlencoded string
     */
    public static String formURLEncode(String string) {
        try {
            return URLEncoder.encode(string, UTF_8);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(ERROR_MSG, uee);
        }
    }

    /**
     * Decodes a application/x-www-form-urlencoded string
     * 
     * @param string form-urlencoded string
     * @return plain string
     */
    public static String formURLDecode(String string) {
        try {
            return URLDecoder.decode(string, UTF_8);
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalStateException(ERROR_MSG, uee);
        }
    }

    /**
     * Append given parameters to the query string of the url
     * 
     * @param url the url to append parameters to
     * @param parameters any map
     * @return new url with parameters on query string
     */
    public static String buildUri(String url, Map<String, ? extends Object> parameters) {
        String queryString = URLUtils.formURLEncodeMap(parameters);
        if (queryString.equals(EMPTY_STRING)) {
            return url;
        } else {
            url += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
            url += queryString;
            return url;
        }
    }

    /**
     * Append given parameters to the query string of the url
     * 
     * @param url the url to append parameters to
     * @param params any map
     * @return new url with parameters on query string
     */
    public static String buildUri(String url, String key, String value) {
        if ("".equals(key) || key == null) {
            return url;
        } else {
            url += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
            url += key + PAIR_SEPARATOR + formURLEncode(value);
            return url;
        }
    }

    /**
     * Concats a key-value map into a querystring-like String
     * 
     * @param params key-value map
     * @return querystring-like String
     */
    // TODO Move to MapUtils
    public static String concatSortedPercentEncodedParams(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (String key : params.keySet()) {
            result.append(key).append(PAIR_SEPARATOR);
            result.append(params.get(key)).append(PARAM_SEPARATOR);
        }
        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * Parses and form-urldecodes a querystring-like string into a map
     * 
     * @param queryString querystring-like String
     * @return a map with the form-urldecoded parameters
     */
    // TODO Move to MapUtils
    public static Map<String, String> queryStringToMap(String queryString) {
        Map<String, String> result = new HashMap<String, String>();
        if (queryString != null && queryString.length() > 0) {
            for (String param : queryString.split(PARAM_SEPARATOR)) {
                String pair[] = param.split(PAIR_SEPARATOR);
                String key = formURLDecode(pair[0]);
                String value = pair.length > 1 ? formURLDecode(pair[1]) : EMPTY_STRING;
                result.put(key, value);
            }
        }
        return result;
    }

    private static final class EncodingRule {
        private final String ch;
        private final String toCh;

        EncodingRule(String ch, String toCh) {
            this.ch = ch;
            this.toCh = toCh;
        }

        String apply(String string) {
            return string.replace(ch, toCh);
        }
    }

    public static Map<String, String> buildPagingParametersWithCount(int page, int pageSize, long sinceId, long maxId) {
        Map<String, String> parameters = newHashMap();
        parameters.put("page", String.valueOf(page));
        parameters.put("count", String.valueOf(pageSize));
        if (sinceId > 0) {
            parameters.put("since_id", String.valueOf(sinceId));
        }
        if (maxId > 0) {
            parameters.put("max_id", String.valueOf(maxId));
        }
        return parameters;
    }

    public static Map<String, String> buildPagingParametersWithPerPage(int page, int pageSize, long sinceId, long maxId) {
        Map<String, String> parameters = newHashMap();
        parameters.put("page", String.valueOf(page));
        parameters.put("per_page", String.valueOf(pageSize));
        if (sinceId > 0) {
            parameters.put("since_id", String.valueOf(sinceId));
        }
        if (maxId > 0) {
            parameters.put("max_id", String.valueOf(maxId));
        }
        return parameters;
    }

    /**
     * This methods looks for place holders with the format {placeholder} in a given String and replace it with the value
     * associated to the corresponding key in a given map
     * 
     * @param in
     * @param values
     * @return
     */
    public static String processPlaceHolders(String in, Map<String, ? extends Object> values) {
        String out = new String(in);

        for (String key : values.keySet()) {
            String toLook = "{" + key + "}";
            String value = values.get(key).toString();
            try {
                out.replace(toLook, URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new SeamSocialException("unable to encode " + value, e);
            }

        }
        return out;
    }

    public static String processPlaceHolders(String in, Object pojo) {
        @SuppressWarnings("unchecked")
        Map<String, ? extends Object> paramMap = new BeanMap(pojo);
        return processPlaceHolders(in, paramMap);

    }
}
