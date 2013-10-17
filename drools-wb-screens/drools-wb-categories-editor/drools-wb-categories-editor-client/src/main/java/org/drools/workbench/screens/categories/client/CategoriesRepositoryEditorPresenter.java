/*
 * Copyright 2012 JBoss Inc
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

package org.drools.workbench.screens.categories.client;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.drools.workbench.screens.categories.client.type.CategoryDefinitionResourceType;
import org.guvnor.common.services.project.context.ProjectContext;
import org.guvnor.common.services.shared.metadata.CategoriesService;
import org.guvnor.common.services.shared.metadata.model.CategoriesModelContent;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.workbench.common.widgets.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.kie.workbench.common.widgets.client.menu.FileMenuBuilder;
import org.kie.workbench.common.widgets.client.popups.file.CommandWithCommitMessage;
import org.kie.workbench.common.widgets.client.popups.file.SaveOperationService;
import org.kie.workbench.common.widgets.client.resources.i18n.CommonConstants;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.annotations.WorkbenchEditor;
import org.uberfire.client.annotations.WorkbenchMenu;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.lifecycle.IsDirty;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnMayClose;
import org.uberfire.lifecycle.OnSave;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.events.NotificationEvent;
import org.uberfire.workbench.model.menu.Menus;
import org.uberfire.workbench.type.FileNameUtil;

/**
 *
 */
@Dependent
@WorkbenchScreen(identifier = "CategoryManager")
public class CategoriesRepositoryEditorPresenter {

    @Inject
    private CategoriesEditorView view;

    @Inject
    private Caller<CategoriesService> categoryService;

    @Inject
    private Event<NotificationEvent> notification;

    @Inject
    private FileMenuBuilder menuBuilder;

    @Inject
    private CategoryDefinitionResourceType type;

    @Inject
    protected ProjectContext context;

    private Menus menus;

    private CategoriesModelContent categoriesModelContent;

    @OnStartup
    public void onStartup() {

        makeMenuBar();

        view.showBusyIndicator(CommonConstants.INSTANCE.Loading());
        categoryService.call(getModelSuccessCallback(),
                new HasBusyIndicatorDefaultErrorCallback(view)).getContentByRoot(context.getActiveRepository().getRoot());
    }

    private void makeMenuBar() {
        menus = menuBuilder.addSave(new Command() {
            @Override
            public void execute() {
                onSave();
            }
        }).build();
    }

    private RemoteCallback<CategoriesModelContent> getModelSuccessCallback() {
        return new RemoteCallback<CategoriesModelContent>() {

            @Override
            public void callback(final CategoriesModelContent content) {
                CategoriesRepositoryEditorPresenter.this.categoriesModelContent = content;

                view.setContent(content.getCategories());
                view.hideBusyIndicator();
            }
        };
    }

    @OnSave
    public void onSave() {
        new SaveOperationService().save(categoriesModelContent.getPath(),
                new CommandWithCommitMessage() {
                    @Override
                    public void execute(final String commitMessage) {
                        view.showBusyIndicator(CommonConstants.INSTANCE.Saving());
                        categoryService.call(getSaveSuccessCallback(),
                                new HasBusyIndicatorDefaultErrorCallback(view)).save(categoriesModelContent.getPath(),
                                view.getContent());
                    }
                });
    }

    private RemoteCallback<Path> getSaveSuccessCallback() {
        return new RemoteCallback<Path>() {

            @Override
            public void callback(final Path path) {
                view.setNotDirty();
                view.hideBusyIndicator();
                notification.fire(new NotificationEvent(CommonConstants.INSTANCE.ItemSavedSuccessfully()));
            }
        };
    }

    @IsDirty
    public boolean isDirty() {
        return view.isDirty();
    }

    @OnMayClose
    public boolean checkIfDirty() {
        if (isDirty()) {
            return view.confirmClose();
        }
        return true;
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return "Categories Editor";
//        [" + FileNameUtil.removeExtension(categoriesModelContent.getPath(),
//                type) + "]";
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return view;
    }

    @WorkbenchMenu
    public Menus getMenus() {
        return menus;
    }

}