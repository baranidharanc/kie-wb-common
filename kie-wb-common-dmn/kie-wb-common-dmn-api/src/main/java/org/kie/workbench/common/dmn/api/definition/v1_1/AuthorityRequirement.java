/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.dmn.api.definition.v1_1;

import java.util.HashSet;
import java.util.Set;

import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.dmn.api.rules.AcyclicDirectedGraphRule;
import org.kie.workbench.common.dmn.api.rules.SingleConnectorPerTypeGraphRule;
import org.kie.workbench.common.dmn.api.validation.NoValidation;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Description;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Title;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.rule.annotation.CanConnect;
import org.kie.workbench.common.stunner.core.rule.annotation.RuleExtension;

@Portable
@Bindable
@Definition(graphFactory = EdgeFactory.class, builder = AuthorityRequirement.AuthorityRequirementBuilder.class)
@FormDefinition(policy = FieldPolicy.ONLY_MARKED)
@CanConnect(startRole = "knowledge-source", endRole = "decision")
@CanConnect(startRole = "knowledge-source", endRole = "business-knowledge-model")
@CanConnect(startRole = "knowledge-source", endRole = "knowledge-source")
@CanConnect(startRole = "input-data", endRole = "knowledge-source")
@RuleExtension(handler = AcyclicDirectedGraphRule.class, typeArguments = {AuthorityRequirement.class})
@RuleExtension(handler = SingleConnectorPerTypeGraphRule.class, typeArguments = {AuthorityRequirement.class})
@NoValidation
public class AuthorityRequirement extends DMNModelInstrumentedBase {

    @Category
    public static final transient String stunnerCategory = Categories.CONNECTORS;

    @Title
    public static final transient String stunnerTitle = "DMN AuthorityRequirement";

    @Description
    public static final transient String stunnerDescription = "DMN AuthorityRequirement";

    @Labels
    private final Set<String> stunnerLabels = new HashSet<String>() {{
        add("authority-requirement");
    }};

    @NonPortable
    public static class AuthorityRequirementBuilder extends BaseNodeBuilder<AuthorityRequirement> {

        @Override
        public AuthorityRequirement build() {
            return new AuthorityRequirement();
        }
    }

    // -----------------------
    // Stunner core properties
    // -----------------------

    public String getStunnerCategory() {
        return stunnerCategory;
    }

    public String getStunnerTitle() {
        return stunnerTitle;
    }

    public String getStunnerDescription() {
        return stunnerDescription;
    }

    public Set<String> getStunnerLabels() {
        return stunnerLabels;
    }
}
