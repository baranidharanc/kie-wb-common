/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.core.client.components.views;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import org.kie.workbench.common.stunner.core.client.shape.view.event.HandlerRegistrationImpl;
import org.uberfire.mvp.Command;

import javax.enterprise.context.Dependent;

import static org.uberfire.commons.validation.PortablePreconditions.checkNotNull;

/**
 * Floating view implementation for generic GWT Widgets.
 */
@Dependent
public class FloatingWidgetView implements FloatingView<IsWidget> {

    private double ox;
    private double oy;
    private double x;
    private double y;
    private boolean attached;
    private Timer timer;
    private int timeout = 800;
    private boolean visible;
    private Command hideCallback;
    private final FlowPanel panel = new FlowPanel();
    private final HandlerRegistrationImpl handlerRegistrationManager = new HandlerRegistrationImpl();

    public FloatingWidgetView() {
        this.attached = false;
        this.ox = 0;
        this.oy = 0;
        this.visible = false;
        this.hideCallback = () -> {};
    }

    @Override
    public void add( final IsWidget item ) {
        panel.add( item );
    }

    @Override
    public FloatingView<IsWidget> setOffsetX( final double ox ) {
        this.ox = ox;
        return this;
    }

    @Override
    public FloatingView<IsWidget> setOffsetY( final double oy ) {
        this.oy = oy;
        return this;
    }

    @Override
    public FloatingWidgetView setX( final double x ) {
        this.x = x;
        return this;
    }

    @Override
    public FloatingWidgetView setY( final double y ) {
        this.y = y;
        return this;
    }

    @Override
    public FloatingWidgetView setTimeOut( final int timeout ) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public FloatingView<IsWidget> clearTimeOut() {
        setTimeOut( -1 );
        return this;
    }

    @Override
    public FloatingView<IsWidget> setHideCallback( final Command hideCallback ) {
        checkNotNull( "hideCallback", hideCallback );
        this.hideCallback = hideCallback;
        return this;
    }

    @Override
    public void clear() {
        panel.clear();
    }

    @Override
    public FloatingWidgetView show() {
        if ( !visible )  {
            visible = true;
            attach();
            startTimeout();
            panel.getElement().getStyle().setLeft( ox + x, Style.Unit.PX );
            panel.getElement().getStyle().setTop( oy + y, Style.Unit.PX );
            doShow();
        }
        return this;
    }

    @Override
    public FloatingWidgetView hide() {
        if ( visible ) {
            this.visible = false;
            stopTimeout();
            doHide();
        }
        return this;
    }

    protected void doShow() {
        panel.getElement().getStyle().setDisplay( Style.Display.INLINE );
    }

    protected void doHide() {
        panel.getElement().getStyle().setDisplay( Style.Display.NONE );
        hideCallback.execute();
    }

    private void attach() {
        if ( !attached ) {
            RootPanel.get().add( panel );
            registerHoverEventHandlers();
            panel.getElement().getStyle().setPosition( Style.Position.FIXED );
            panel.getElement().getStyle().setZIndex( Integer.MAX_VALUE );
            doHide();
            attached = true;

        }

    }

    @Override
    public void destroy() {
        detach();
    }

    private void detach() {
        if ( attached ) {
            handlerRegistrationManager.removeHandler();
            RootPanel.get().remove( panel );
            attached = false;
        }

    }

    public void startTimeout() {
        if ( timeout > 0 &&
                ( null == timer || !timer.isRunning() ) ) {
            timer = new Timer() {
                @Override
                public void run() {
                    FloatingWidgetView.this.doHide();
                }
            };
            timer.schedule( timeout );

        }

    }

    public void stopTimeout() {
        if ( null != timer && timer.isRunning() ) {
            timer.cancel();
        }

    }

    protected FlowPanel getPanel() {
        return panel;
    }

    private void registerHoverEventHandlers() {
        handlerRegistrationManager.register(
                panel.addDomHandler( mouseOverEvent -> stopTimeout(), MouseOverEvent.getType() )
        );
        handlerRegistrationManager.register(
                panel.addDomHandler( mouseOutEvent -> startTimeout(), MouseOutEvent.getType() )
        );

    }

}