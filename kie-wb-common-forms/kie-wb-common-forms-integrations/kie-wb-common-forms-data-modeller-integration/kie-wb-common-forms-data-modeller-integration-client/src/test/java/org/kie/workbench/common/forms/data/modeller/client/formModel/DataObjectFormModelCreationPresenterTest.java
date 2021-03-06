/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.forms.data.modeller.client.formModel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.forms.data.modeller.client.resources.i18n.DataModellerIntegrationConstants;
import org.kie.workbench.common.forms.data.modeller.model.DataObjectFormModel;
import org.kie.workbench.common.forms.data.modeller.service.DataObjectFormModelCreationService;
import org.uberfire.backend.vfs.Path;
import org.uberfire.mocks.CallerMock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class DataObjectFormModelCreationPresenterTest {

    private DataObjectFormModelCreationService dataObjectFormModelCreationService;

    private CallerMock<DataObjectFormModelCreationService> dataObjectFormModelCreationServiceCallerMock;

    private DataObjectFormModelCreationView view;

    private Path path;

    private DataObjectFormModelCreationPresenterManager presenter;

    private List<DataObjectFormModel> formModels = new ArrayList<>();

    private TranslationService translationService;

    @Before
    public void setup() {

        path = mock(Path.class);

        formModels.add(new DataObjectFormModel("employee",
                                               "org.kie.wb.test.Employee"));
        formModels.add(new DataObjectFormModel("address",
                                               "org.kie.wb.test.Address"));
        formModels.add(new DataObjectFormModel("company",
                                               "org.kie.wb.test.Company"));
        formModels.add(new DataObjectFormModel("department",
                                               "org.kie.wb.test.Department"));

        dataObjectFormModelCreationService = mock(DataObjectFormModelCreationService.class);

        when(dataObjectFormModelCreationService.getAvailableDataObjects(path)).thenReturn(formModels);

        dataObjectFormModelCreationServiceCallerMock = new CallerMock<>(dataObjectFormModelCreationService);

        view = mock(DataObjectFormModelCreationView.class);

        translationService = mock(TranslationService.class);

        presenter = new DataObjectFormModelCreationPresenterManager(dataObjectFormModelCreationServiceCallerMock,
                                                                    view,
                                                                    translationService);
    }

    @Test
    public void testGeneralFunctionallity() {
        presenter.getPriority();

        presenter.reset();
        verify(view).reset();

        presenter.init(path);

        presenter.getLabel();
        verify(translationService).getTranslation(DataModellerIntegrationConstants.DataObject);

        verify(dataObjectFormModelCreationService).getAvailableDataObjects(path);
        verify(view).setFormModels(formModels);

        boolean valid = presenter.isValid();

        assertFalse(valid);
        verify(translationService).getTranslation(DataModellerIntegrationConstants.InvalidDataObject);

        presenter.getFormModel();
        verify(view,
               times(2)).getSelectedFormModel();
    }
}
