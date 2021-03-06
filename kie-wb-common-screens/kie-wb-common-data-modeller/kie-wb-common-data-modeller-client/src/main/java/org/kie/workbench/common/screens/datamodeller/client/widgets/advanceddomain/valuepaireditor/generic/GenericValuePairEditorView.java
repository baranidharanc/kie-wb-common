/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.screens.datamodeller.client.widgets.advanceddomain.valuepaireditor.generic;

import org.kie.workbench.common.screens.datamodeller.client.widgets.advanceddomain.valuepaireditor.HasErrorMessage;
import org.kie.workbench.common.screens.datamodeller.client.widgets.advanceddomain.valuepaireditor.ValuePairEditorView;

public interface GenericValuePairEditorView
    extends ValuePairEditorView<GenericValuePairEditorView.Presenter>,
        HasErrorMessage {

    interface Presenter {

        void onValidate();

        void onValueChange();

    }

    void setValue( String text );

    String getValue();

    void showValidateButton( boolean show );

    void clear();

    void refresh();
}
