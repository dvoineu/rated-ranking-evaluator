/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sease.rre.core.evaluation;

import io.sease.rre.core.evaluation.impl.AsynchronousEvaluationManager;
import io.sease.rre.core.evaluation.impl.AsynchronousQueryEvaluationManager;
import io.sease.rre.core.evaluation.impl.SynchronousEvaluationManager;
import io.sease.rre.core.template.QueryTemplateManager;
import io.sease.rre.persistence.PersistenceManager;
import io.sease.rre.search.api.SearchPlatform;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the evaluation manager factory.
 *
 * @author Matt Pearce (matt@flax.co.uk)
 */
public class EvaluationManagerFactoryTest {

    private final SearchPlatform platform = mock(SearchPlatform.class);
    private final PersistenceManager persistenceManager = mock(PersistenceManager.class);
    private final QueryTemplateManager templateManager = mock(QueryTemplateManager.class);
    private final String[] fields = new String[0];
    private final Collection<String> versions = Collections.singletonList("v1.0");
    private final String versionTimestamp = null;

    @Test
    public void instantiateReturnsSynchronous() {
        final EvaluationConfiguration config = new EvaluationConfiguration(false, false, 0);
        final EvaluationManager test = EvaluationManagerFactory.instantiateEvaluationManager(config, platform, persistenceManager, templateManager, fields, versions, versionTimestamp);

        assertNotNull(test);
        assertTrue(test instanceof SynchronousEvaluationManager);
    }

    @Test
    public void instantiateReturnsAsynchronous() {
        final EvaluationConfiguration config = new EvaluationConfiguration(true, false, 4);
        final EvaluationManager test = EvaluationManagerFactory.instantiateEvaluationManager(config, platform, persistenceManager, templateManager, fields, versions, versionTimestamp);

        assertNotNull(test);
        assertTrue(test instanceof AsynchronousEvaluationManager);
    }

    @Test
    public void instantiateReturnsAsynchronousQuery() {
        final EvaluationConfiguration config = new EvaluationConfiguration(true, true, 4);
        final EvaluationManager test = EvaluationManagerFactory.instantiateEvaluationManager(config, platform, persistenceManager, templateManager, fields, versions, versionTimestamp);

        assertNotNull(test);
        assertTrue(test instanceof AsynchronousQueryEvaluationManager);
    }
}
