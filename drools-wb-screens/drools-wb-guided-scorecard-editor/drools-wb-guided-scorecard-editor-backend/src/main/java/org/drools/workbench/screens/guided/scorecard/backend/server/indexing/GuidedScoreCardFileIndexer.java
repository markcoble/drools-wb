/*
 * Copyright 2014 JBoss, by Red Hat, Inc
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
package org.drools.workbench.screens.guided.scorecard.backend.server.indexing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.appformer.project.datamodel.oracle.ProjectDataModelOracle;
import org.drools.workbench.models.guided.scorecard.backend.GuidedScoreCardXMLPersistence;
import org.drools.workbench.models.guided.scorecard.shared.ScoreCardModel;
import org.drools.workbench.screens.guided.scorecard.type.GuidedScoreCardResourceTypeDefinition;
import org.kie.workbench.common.services.datamodel.backend.server.service.DataModelService;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.AbstractFileIndexer;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.DefaultIndexBuilder;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.java.nio.file.Path;

@ApplicationScoped
public class GuidedScoreCardFileIndexer extends AbstractFileIndexer {

    @Inject
    private DataModelService dataModelService;

    @Inject
    protected GuidedScoreCardResourceTypeDefinition type;

    @Override
    public boolean supportsPath( final Path path ) {
        return type.accept( Paths.convert( path ) );
    }

    @Override
    public DefaultIndexBuilder fillIndexBuilder(final Path path) throws Exception {
        final String content = ioService.readAllString( path );
        final ScoreCardModel model = GuidedScoreCardXMLPersistence.getInstance().unmarshall( content );

        final ProjectDataModelOracle dmo = getProjectDataModelOracle( path );

        final DefaultIndexBuilder builder = getIndexBuilder(path);
        if( builder == null ) {
            return null;
        }

        final GuidedScoreCardIndexVisitor visitor = new GuidedScoreCardIndexVisitor( dmo, model );
        visitor.visit();
        addReferencedResourcesToIndexBuilder(builder, visitor);

        return builder;
    }

    //Delegate resolution of DMO to method to assist testing
    protected ProjectDataModelOracle getProjectDataModelOracle( final Path path ) {
        return dataModelService.getProjectDataModel( Paths.convert( path ) );
    }

}
