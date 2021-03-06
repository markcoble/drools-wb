/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package org.drools.workbench.client.resources.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

/**
 * This uses GWT to provide client side compile time resolving of locales. See:
 * http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&s=google-web-
 * toolkit-doc-1-5&t=DevGuideInternationalization (for more information).
 * <p/>
 * Each method name matches up with a key in HomeConstants.properties (the
 * properties file can still be used on the server). To use this, use
 * <code>GWT.create(HomeConstants.class)</code>.
 */
public interface AppConstants
        extends
        Messages {

    AppConstants INSTANCE = GWT.create(AppConstants.class);

    String SignOut();

    String WelcomeUser();

    String newItem();

    String AdministrationPerspectiveName();

    String MenuExplore();

    String MenuExploreFiles();

    String MenuRepositories();

    String MenuListRepositories();

    String MenuCloneRepository();

    String MenuNewRepository();

    String MenuOrganizationalUnits();

    String MenuManageOrganizationalUnits();

    String Explore();

    String Projects();

    String IncomingChanges();

    String RecentlyEdited();

    String RecentlyOpened();

    String New();

    String Project();

    String Repository();

    String Home();

    String Perspectives();

    String Logout();

    String Find();

    String Upload();

    String Refresh();

    String Role();

    String logoBannerError();

    String assetSearch();

    String Examples();

    String Settings();

    String Library();

    String ArtifactRepository();

    String AdminPreferences();

    String LogoTitle();

    String License();
}
