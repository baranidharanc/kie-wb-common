/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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
package org.kie.workbench.common.forms.editor.backend.indexing;

import org.kie.workbench.common.forms.model.FieldDefinition;
import org.kie.workbench.common.forms.model.FormDefinition;
import org.kie.workbench.common.services.refactoring.Resource;
import org.kie.workbench.common.services.refactoring.backend.server.impact.ResourceReferenceCollector;
import org.kie.workbench.common.services.refactoring.service.PartType;
import org.kie.workbench.common.services.refactoring.service.ResourceType;

public class FormDefinitionVisitor extends ResourceReferenceCollector {

    private final Resource resParts;

    public FormDefinitionVisitor(Resource resParts) {
        this.resParts = resParts;
    }

    public void visit(FormDefinition formDefinition) {
        addResource(formDefinition.getId(),
                    ResourceType.FORM);
        formDefinition.getFields().forEach(fieldDefinition -> visit(fieldDefinition));
    }

    public void visit(FieldDefinition fieldDefinition) {
        resParts.addPart(fieldDefinition.getName(),
                         PartType.FORM_FIELD);
        addResourceReference(fieldDefinition.getStandaloneClassName(),
                             ResourceType.JAVA);
    }
}
