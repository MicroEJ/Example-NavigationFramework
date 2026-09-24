/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.widget.basic.OnClickListener;
import ej.widget.event.ClickEventHandler;
import ej.widget.event.Clickable;

/**
 * The click detection and the pressed state shared by the clickable widgets of this application.
 * <p>
 * Each clickable widget already inherits from the widget it makes clickable, and a class has a
 * single superclass, so this behavior cannot be inherited too: a clickable widget holds a click
 * state and forwards the {@link Clickable} contract to it.
 */
public class ClickState {

    private final Widget widget;
    private final ClickEventHandler eventHandler;

    private boolean pressed;

    /**
     * Creates the click state of the given widget.
     *
     * @param widget
     *            the widget to style and render again when its pressed state changes
     * @param clickable
     *            the clickable notified of the presses on the given widget
     */
    public ClickState(Widget widget, Clickable clickable) {
        this.widget = widget;
        this.eventHandler = new ClickEventHandler(widget, clickable);
    }

    /**
     * Sets the listener notified when the widget is clicked.
     *
     * @param listener
     *            the listener to notify, {@code null} to remove the previous one
     */
    public void setOnClickListener(@Nullable OnClickListener listener) {
        this.eventHandler.setOnClickListener(listener);
    }

    /**
     * Handles the given event.
     *
     * @param event
     *            the event to handle
     * @return {@code true} if the event has been handled
     */
    public boolean handleEvent(int event) {
        return this.eventHandler.handleEvent(event);
    }

    /**
     * Returns whether the widget is pressed and the given state is the active state.
     *
     * @param state
     *            the state to check
     * @return {@code true} if the widget is in the given state
     */
    public boolean isInState(int state) {
        return state == StateSelector.ACTIVE && this.pressed;
    }

    /**
     * Sets the pressed state of the widget, styling and rendering it again.
     *
     * @param pressed
     *            {@code true} if the widget is pressed, {@code false} otherwise
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        this.widget.updateStyle();
        this.widget.requestRender();
    }

}
