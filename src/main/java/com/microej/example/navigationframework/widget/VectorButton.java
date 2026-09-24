/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import ej.annotation.Nullable;
import ej.widget.basic.OnClickListener;
import ej.widget.event.Clickable;

/**
 * A push button displaying a text with a vector font.
 */
public class VectorButton extends VectorLabel implements Clickable {

    private final ClickState clickState;

    /**
     * Creates a button displaying an empty text.
     */
    public VectorButton() {
        this("");
    }

    /**
     * Creates a button displaying the given text.
     *
     * @param text
     *            the text to display
     */
    public VectorButton(String text) {
        super(text, true);

        this.clickState = new ClickState(this, this);
    }

    /**
     * Sets the listener notified when this button is clicked.
     *
     * @param listener
     *            the listener to notify, {@code null} to remove the previous one
     */
    public void setOnClickListener(@Nullable OnClickListener listener) {
        this.clickState.setOnClickListener(listener);
    }

    @Override
    public boolean handleEvent(int event) {
        return this.clickState.handleEvent(event);
    }

    @Override
    public boolean isInState(int state) {
        return this.clickState.isInState(state) || super.isInState(state);
    }

    @Override
    public void setPressed(boolean pressed) {
        this.clickState.setPressed(pressed);
    }

}
