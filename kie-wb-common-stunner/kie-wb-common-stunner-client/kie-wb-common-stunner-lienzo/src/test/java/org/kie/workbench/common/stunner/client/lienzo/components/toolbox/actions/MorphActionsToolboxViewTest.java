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

package org.kie.workbench.common.stunner.client.lienzo.components.toolbox.actions;

import java.util.function.Consumer;

import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeMouseEnterHandler;
import com.ait.lienzo.client.core.event.NodeMouseExitHandler;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.test.LienzoMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.client.shape.view.event.MouseClickEvent;
import org.kie.workbench.common.stunner.core.definition.shape.Glyph;
import org.kie.workbench.common.stunner.lienzo.toolbox.grid.FixedLayoutGrid;
import org.kie.workbench.common.stunner.lienzo.toolbox.grid.Point2DGrid;
import org.kie.workbench.common.stunner.lienzo.toolbox.items.ButtonGridItem;
import org.kie.workbench.common.stunner.lienzo.toolbox.items.ButtonItem;
import org.kie.workbench.common.stunner.lienzo.toolbox.items.DecoratorItem;
import org.kie.workbench.common.stunner.lienzo.toolbox.items.TooltipItem;
import org.kie.workbench.common.stunner.lienzo.toolbox.items.decorator.BoxDecorator;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(LienzoMockitoTestRunner.class)
public class MorphActionsToolboxViewTest
        extends AbstractActionsToolboxViewTest {

    @Mock
    private ButtonGridItem buttonGridItem;

    @Mock
    private BoxDecorator buttonDecorator;

    private MorphActionsToolboxView tested;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() throws Exception {
        super.init();
        when(buttonsFactory.dropRight(any(Group.class))).thenReturn(buttonGridItem);
        when(decoratorsFactory.button()).thenReturn(buttonDecorator);
        when(buttonDecorator.configure(any(Consumer.class))).thenReturn(buttonDecorator);
        when(buttonDecorator.setBoundingBox(any(BoundingBox.class))).thenReturn(buttonDecorator);
        when(buttonDecorator.setPadding(anyDouble())).thenReturn(buttonDecorator);
        when(buttonDecorator.copy()).thenReturn(buttonDecorator);
        when(buttonGridItem.tooltip(any(TooltipItem.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.grid(any(Point2DGrid.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.decorate(any(DecoratorItem.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.decorateGrid(any(DecoratorItem.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.onMouseEnter(any(NodeMouseEnterHandler.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.onMouseExit(any(NodeMouseExitHandler.class))).thenReturn(buttonGridItem);
        when(buttonGridItem.onClick(any(NodeMouseClickHandler.class))).thenReturn(buttonGridItem);
        this.tested = new MorphActionsToolboxView(glyphRenderers,
                                                  toolboxFactory);
        when(toolbox.getView()).thenReturn(tested);
    }

    @Test
    public void testInit() {
        final MorphActionsToolboxView cascade = doInit();
        assertEquals(tested,
                     cascade);
        verify(toolboxFactory,
               times(1)).forWiresShape(eq(shape));
        verify(toolboxView,
               times(1)).attachTo(eq(layer));
        assertConfigureToolbox();
        assertConfigureButtonGridItem();
        assertTooltip();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddButton() {
        doInit();
        final Consumer<MouseClickEvent> eventConsumer = mock(Consumer.class);
        tested.addButton(mock(Glyph.class),
                         "title1",
                         eventConsumer);
        super.testAddButton(eventConsumer);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddButtonIntoParent() {
        doInit();
        final ButtonItem buttonItem = mock(ButtonItem.class);
        tested.addButton(buttonItem);
        verify(toolboxView,
               times(1)).add(eq(buttonGridItem));
        verify(buttonGridItem,
               times(1)).add(buttonItem);
    }

    private MorphActionsToolboxView doInit() {
        return tested.init(toolbox,
                           canvas,
                           shape);
    }

    private void assertConfigureToolbox() {
        verify(toolboxView,
               times(1))
                .at(eq(MorphActionsToolboxView.TOOLBOX_AT));
        final ArgumentCaptor<Point2DGrid> gridCaptor = ArgumentCaptor.forClass(Point2DGrid.class);
        verify(toolboxView,
               times(1))
                .grid(gridCaptor.capture());
        final FixedLayoutGrid grid = (FixedLayoutGrid) gridCaptor.getValue();
        assertEquals(1,
                     grid.getRows());
        assertEquals(1,
                     grid.getCols());
        assertEquals(AbstractActionsToolboxView.BUTTON_SIZE,
                     grid.getIconSize(),
                     0);
        assertEquals(AbstractActionsToolboxView.BUTTON_PADDING,
                     grid.getPadding(),
                     0);
    }

    private void assertConfigureButtonGridItem() {
        final ArgumentCaptor<Point2DGrid> gridCaptor = ArgumentCaptor.forClass(Point2DGrid.class);
        verify(buttonGridItem,
               times(1))
                .grid(gridCaptor.capture());
        final FixedLayoutGrid grid = (FixedLayoutGrid) gridCaptor.getValue();
        assertEquals(1,
                     grid.getRows());
        assertEquals(2,
                     grid.getCols());
        assertEquals(AbstractActionsToolboxView.BUTTON_SIZE,
                     grid.getIconSize(),
                     0);
        assertEquals(AbstractActionsToolboxView.BUTTON_PADDING,
                     grid.getPadding(),
                     0);
        verify(buttonGridItem,
               times(1))
                .decorate(eq(buttonDecorator));
        verify(buttonGridItem,
               times(1))
                .decorateGrid(eq(buttonDecorator));
        verify(toolboxView,
               times(1)).add(eq(buttonGridItem));
    }

    private void assertTooltip() {
        verify(toolboxTooltip,
               times(1)).at(MorphActionsToolboxView.TOOLTIP_AT);
        verify(toolboxTooltip,
               times(1)).towards(MorphActionsToolboxView.TOOLTIP_TOWARDS);
    }
}
