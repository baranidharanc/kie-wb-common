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

package org.kie.workbench.common.stunner.basicset.definition.property.background;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldLabel;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.slider.type.SliderFieldType;
import org.kie.workbench.common.stunner.core.definition.annotation.Name;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.forms.model.ColorPickerFieldType;

@Portable
@Bindable
@PropertySet
@FormDefinition(startElement = "bgColor")
public class BackgroundAndBorderSet {

    @Name
    @FieldLabel
    public static final transient String propertySetName = "Background And Borders";

    @Property
    @FormField(type = ColorPickerFieldType.class)
    @Valid
    private BgColor bgColor;

    @Property
    @FormField(type = ColorPickerFieldType.class, afterElement = "bgColor")
    @Valid
    private BorderColor borderColor;

    @Property
    @FormField(
            type = SliderFieldType.class,
            afterElement = "borderColor",
            settings = {
                    @FieldParam(name = "min", value = "0.0"),
                    @FieldParam(name = "max", value = "5.0"),
                    @FieldParam(name = "step", value = "0.5")
            }
    )
    @Valid
    private BorderSize borderSize;

    public BackgroundAndBorderSet() {
        this(new BgColor(),
             new BorderColor(),
             new BorderSize());
    }

    public BackgroundAndBorderSet(final @MapsTo("bgColor") BgColor bgColor,
                                  final @MapsTo("borderColor") BorderColor borderColor,
                                  final @MapsTo("borderSize") BorderSize borderSize) {
        this.bgColor = bgColor;
        this.borderColor = borderColor;
        this.borderSize = borderSize;
    }

    public BackgroundAndBorderSet(final String bgColor,
                                  final String borderColor,
                                  final Double borderSize) {
        this.bgColor = new BgColor(bgColor);
        this.borderColor = new BorderColor(borderColor);
        this.borderSize = new BorderSize(borderSize);
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public BgColor getBgColor() {
        return bgColor;
    }

    public BorderColor getBorderColor() {
        return borderColor;
    }

    public BorderSize getBorderSize() {
        return borderSize;
    }

    public void setBgColor(final BgColor bgColor) {
        this.bgColor = bgColor;
    }

    public void setBorderColor(final BorderColor borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderSize(final BorderSize borderSize) {
        this.borderSize = borderSize;
    }
}
