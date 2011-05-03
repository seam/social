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
package org.jboss.seam.social.oauth;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * A CDI Qualifier annotation to qualify and set OAuthService directly in the code It can be used like this :
 * 
 * <pre>
 * &#064;Inject
 * &#064;Setted(apiKey = &quot;a consumer key&quot;, apiSecret = &quot;a consumer secret&quot;, callback = &quot;a call back URL&quot;)
 * Twitter service;
 * </pre>
 * 
 * It's one of the alternates solution initialize an OAuth service configuration
 * 
 * @author Antoine Sabot-Durand
 * 
 */

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
public @interface Setted {

    @Nonbinding
    String apiKey() default "";

    @Nonbinding
    String apiSecret() default "";

    @Nonbinding
    String callback() default "oob";
}
