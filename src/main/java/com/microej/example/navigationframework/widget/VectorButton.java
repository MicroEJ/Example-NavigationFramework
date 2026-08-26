/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.annotation.Nullable;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.widget.basic.OnClickListener;
import ej.widget.event.ClickEventHandler;
import ej.widget.event.Clickable;

public class VectorButton extends VectorLabel implements Clickable {

    private final ClickEventHandler eventHandler;

    private boolean pressed;

    public VectorButton() {
        this("");
    }

    public VectorButton(String text) {
        super(text, true);

        this.eventHandler = new ClickEventHandler(this, this);
        this.pressed = false;
    }

    public void setOnClickListener(@Nullable OnClickListener listener) {
        this.eventHandler.setOnClickListener(listener);
    }

    @Override
    public boolean handleEvent(int event) {
        return this.eventHandler.handleEvent(event);
    }

    @Override
    public boolean isInState(int state) {
        return (state == StateSelector.ACTIVE && this.pressed) || super.isInState(state);
    }

    @Override
    public void setPressed(boolean b) {
        this.pressed = pressed;
        updateStyle();
        requestRender();
    }

}
