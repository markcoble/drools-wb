/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.drools.workbench.screens.guided.dtable.client.widget.table.columns;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.ListBox;
import org.drools.workbench.screens.guided.dtable.client.widget.table.GuidedDecisionTablePresenter;
import org.drools.workbench.screens.guided.dtable.client.widget.table.GuidedDecisionTableView;
import org.drools.workbench.screens.guided.dtable.client.widget.table.columns.dom.listbox.ListBoxDOMElement;
import org.drools.workbench.screens.guided.dtable.client.widget.table.columns.dom.listbox.MultiValueSingletonDOMElementFactory;
import org.drools.workbench.screens.guided.dtable.client.widget.table.columns.dom.textbox.SingleValueSingletonDOMElementFactory;
import org.drools.workbench.screens.guided.dtable.client.widget.table.columns.dom.textbox.TextBoxDOMElement;
import org.uberfire.ext.widgets.common.client.common.NumericBigIntegerTextBox;
import org.uberfire.ext.wires.core.grids.client.model.GridCell;
import org.uberfire.ext.wires.core.grids.client.widget.context.GridBodyCellRenderContext;

public class EnumSingleSelectBigIntegerUiColumn extends BaseEnumSingleSelectUiColumn<BigInteger, ListBox, NumericBigIntegerTextBox, ListBoxDOMElement<BigInteger, ListBox>, TextBoxDOMElement<BigInteger, NumericBigIntegerTextBox>> {

    public EnumSingleSelectBigIntegerUiColumn( final List<HeaderMetaData> headerMetaData,
                                               final double width,
                                               final boolean isResizable,
                                               final boolean isVisible,
                                               final GuidedDecisionTablePresenter.Access access,
                                               final MultiValueSingletonDOMElementFactory<BigInteger, ListBox, ListBoxDOMElement<BigInteger, ListBox>> multiValueFactory,
                                               final SingleValueSingletonDOMElementFactory<BigInteger, NumericBigIntegerTextBox, TextBoxDOMElement<BigInteger, NumericBigIntegerTextBox>> singleValueFactory,
                                               final GuidedDecisionTableView.Presenter presenter,
                                               final String factType,
                                               final String factField ) {
        super( headerMetaData,
               width,
               isResizable,
               isVisible,
               access,
               multiValueFactory,
               singleValueFactory,
               presenter,
               factType,
               factField );
    }

    @Override
    protected void initialiseMultiValueDomElement( final GridCell<BigInteger> cell,
                                                   final GridBodyCellRenderContext context,
                                                   final Map<String, String> enumLookups ) {
        factory.attachDomElement( context,
                                  CallbackFactory.makeOnCreationCallback( factory,
                                                                          cell,
                                                                          enumLookups ),
                                  CallbackFactory.makeOnDisplayListBoxCallback() );
    }

    @Override
    protected void initialiseSingleValueDomElement( final GridCell<BigInteger> cell,
                                                    final GridBodyCellRenderContext context ) {
        singleValueFactory.attachDomElement( context,
                                             CallbackFactory.makeOnCreationCallback( singleValueFactory,
                                                                                     cell ),
                                             CallbackFactory.makeOnDisplayTextBoxCallback() );
    }

}
