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

package org.kie.workbench.common.stunner.core;

import java.util.Optional;

import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.rule.context.CardinalityContext;
import org.kie.workbench.common.stunner.core.rule.context.ConnectorCardinalityContext;
import org.kie.workbench.common.stunner.core.rule.context.EdgeCardinalityContext;
import org.kie.workbench.common.stunner.core.rule.context.ElementCardinalityContext;
import org.kie.workbench.common.stunner.core.rule.context.GraphConnectionContext;
import org.kie.workbench.common.stunner.core.rule.context.NodeContainmentContext;
import org.kie.workbench.common.stunner.core.rule.context.NodeDockingContext;

import static org.junit.Assert.*;

/**
 * Some testing utils for graph stuff.
 */
public class TestingGraphUtils {

    public static void verifyContainment(final NodeContainmentContext containmentContext,
                                         final Element<? extends Definition<?>> parent,
                                         final Node<? extends Definition<?>, ? extends Edge> candidate) {
        assertNotNull(containmentContext);
        final Element<? extends Definition<?>> source = containmentContext.getParent();
        final Node<? extends Definition<?>, ? extends Edge> target = containmentContext.getCandidate();
        assertNotNull(source);
        assertEquals(parent,
                     source);
        assertNotNull(target);
        assertEquals(candidate,
                     target);
    }

    public static void verifyDocking(final NodeDockingContext context,
                                     final Element<? extends Definition<?>> parent,
                                     final Node<? extends Definition<?>, ? extends Edge> candidate) {
        assertNotNull(context);
        final Element<? extends Definition<?>> source = context.getParent();
        final Node<? extends Definition<?>, ? extends Edge> target = context.getCandidate();
        assertNotNull(source);
        assertNotNull(target);
        assertEquals(parent,
                     source);
        assertEquals(candidate,
                     target);
    }

    public static void verifyConnection(final GraphConnectionContext context,
                                        final Edge<? extends View<?>, ? extends Node> connector,
                                        final Node<? extends View<?>, ? extends Edge> sourceNode,
                                        final Node<? extends View<?>, ? extends Edge> targetNode) {
        assertNotNull(context);
        final Edge<? extends View<?>, ? extends Node> connector1 = context.getConnector();
        final Optional<Node<? extends View<?>, ? extends Edge>> source = context.getSource();
        final Optional<Node<? extends View<?>, ? extends Edge>> target = context.getTarget();
        assertNotNull(connector1);
        assertEquals(connector,
                     connector1);
        if (null != sourceNode) {
            assertEquals(sourceNode,
                         source.get());
        }
        if (null != targetNode) {
            assertEquals(targetNode,
                         target.get());
        }
    }

    public static void verifyCardinality(final ElementCardinalityContext context,
                                         final Graph graph,
                                         final Element<? extends View<?>> candidate,
                                         final CardinalityContext.Operation operation) {
        assertNotNull(context);
        final Graph graph1 = context.getGraph();
        final Optional<Element<? extends View<?>>> candidate1 = context.getCandidate();
        final Optional<CardinalityContext.Operation> operation1 = context.getOperation();
        assertNotNull(graph1);
        assertNotNull(operation1);
        assertEquals(graph,
                     graph1);
        assertEquals(candidate,
                     candidate1.orElse(null));
        assertEquals(operation,
                     operation1.orElse(null));
    }

    public static void verifyCardinality(final ElementCardinalityContext context,
                                         final Graph graph) {
        assertNotNull(context);
        final Graph graph1 = context.getGraph();
        final Optional<Element<? extends View<?>>> candidate1 = context.getCandidate();
        final Optional<CardinalityContext.Operation> operation1 = context.getOperation();
        assertNotNull(graph1);
        assertNotNull(operation1);
        assertEquals(graph,
                     graph1);
    }

    public static void verifyConnectorCardinality(final ConnectorCardinalityContext context,
                                                  final Graph graph,
                                                  final Element<? extends View<?>> candidate,
                                                  final Edge<? extends View<?>, Node> edge,
                                                  final EdgeCardinalityContext.Direction direction,
                                                  final Optional<CardinalityContext.Operation> operation) {
        assertNotNull(context);
        final EdgeCardinalityContext.Direction direction1 = context.getDirection();
        final Edge<? extends View<?>, Node> edge1 = context.getEdge();
        final Element<? extends View<?>> candidate1 = context.getCandidate();
        final Graph graph1 = context.getGraph();
        final Optional<CardinalityContext.Operation> operation1 = context.getOperation();
        assertNotNull(direction1);
        assertNotNull(edge1);
        assertNotNull(candidate1);
        assertNotNull(graph1);
        assertNotNull(operation1);
        assertEquals(direction,
                     direction1);
        assertEquals(edge,
                     edge1);
        assertEquals(operation,
                     operation1);
        assertEquals(candidate,
                     candidate1);
        assertEquals(graph,
                     graph1);
        assertEquals(operation,
                     operation1);
    }
}
