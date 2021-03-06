/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.guided.rule.backend.server;

import java.util.HashMap;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;

import org.drools.workbench.models.datamodel.oracle.PackageDataModelOracle;
import org.drools.workbench.screens.guided.rule.model.GuidedEditorContent;
import org.drools.workbench.screens.guided.rule.type.GuidedRuleDSLRResourceTypeDefinition;
import org.guvnor.common.services.shared.metadata.model.Overview;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.services.datamodel.backend.server.service.DataModelService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.uberfire.backend.vfs.Path;
import org.uberfire.io.IOService;
import org.uberfire.rpc.SessionInfo;
import org.uberfire.workbench.events.ResourceOpenedEvent;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GuidedRuleEditorServiceImplTest {

    @Mock
    private Event<ResourceOpenedEvent> resourceOpenedEventEvent;

    @Mock
    private SessionInfo sessionInfo;

    @Mock
    private GuidedRuleDSLRResourceTypeDefinition dslrResourceTypeDefinition;

    @Mock
    private GuidedRuleEditorServiceUtilities utilities;

    @Mock
    private DataModelService dataModelService;

    @Mock
    private IOService ioService;

    @InjectMocks
    GuidedRuleEditorServiceImpl service = new GuidedRuleEditorServiceImpl(sessionInfo,
                                                                          mock(Instance.class));

    @Test
    public void checkConstructContentPopulateProjectCollectionTypes() {
        final Path path = mock(Path.class);
        final Overview overview = mock(Overview.class);
        final PackageDataModelOracle oracle = mock(PackageDataModelOracle.class);
        when(path.toURI()).thenReturn("default://project/src/main/resources/mypackage/rule.rdrl");
        when(dataModelService.getDataModel(any())).thenReturn(oracle);
        when(oracle.getProjectCollectionTypes()).thenReturn(new HashMap<String, Boolean>() {{
            put("java.util.List",
                true);
            put("java.util.Set",
                true);
            put("java.util.Collection",
                true);
            put("java.util.UnknownCollection",
                false);
        }});

        final GuidedEditorContent content = service.constructContent(path,
                                                                     overview);
        assertEquals(3,
                     content.getDataModel().getCollectionTypes().size());
        assertTrue(content.getDataModel().getCollectionTypes().containsKey("java.util.Collection"));
        assertTrue(content.getDataModel().getCollectionTypes().containsKey("java.util.List"));
        assertTrue(content.getDataModel().getCollectionTypes().containsKey("java.util.Set"));
    }
}
