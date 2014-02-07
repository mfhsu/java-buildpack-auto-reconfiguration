/*
 * Copyright 2011-2014 the original author or authors.
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

package org.cloudfoundry.reconfiguration.spring;

import org.springframework.util.StringUtils;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import java.util.Set;
import java.util.logging.Logger;

/**
 * An implementation of {@link ServletContainerInitializer} that sets Spring's {@code globalInitializerClasses} {@link
 * ServletContext} init param
 */
public final class AutoReconfigurationServletContainerInitializer implements ServletContainerInitializer {

    private static final String APPLICATION_CONTEXT_INITIALIZERS = StringUtils.arrayToCommaDelimitedString(new String[]{
            CloudProfileApplicationContextInitializer.class.getCanonicalName(),
            CloudPropertySourceApplicationContextInitializer.class.getCanonicalName(),
            CloudAutoReconfigurationApplicationContextInitializer.class.getCanonicalName()
    });

    private static final String GLOBAL_INITIALIZER_CLASSES_PARAM = "globalInitializerClasses";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) {
        this.logger.info("Initializing ServletContext with Auto-reconfiguration ApplicationContextInitializers");
        ctx.setInitParameter(GLOBAL_INITIALIZER_CLASSES_PARAM, APPLICATION_CONTEXT_INITIALIZERS);
    }

}
