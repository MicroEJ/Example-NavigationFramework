/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * A check box drawing a square box, filled with a smaller square when it is checked.
 * <p>
 * Clicking the check box toggles it.
 */
public class VectorCheckBox extends VectorSelector {

    private static final int BOX_SIZE_SPACING = 6;
    private static final int BORDER_THICKNESS = 1;
    private static final int INNER_BOX_OFFSET = 7;

    private boolean checked;

    /**
     * Creates an unchecked check box displaying the given text.
     *
     * @param text
     *            the text to display on the right of the box
     */
    public VectorCheckBox(String text) {
        super(text, BOX_SIZE_SPACING);
    }

    @Override
    public boolean isChecked() {
        return this.checked;
    }

    /**
     * Sets the checked state of this check box.
     *
     * @param checked
     *            the checked state to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
        requestRender();
    }

    @Override
    protected void onClicked() {
        setChecked(!isChecked());
    }

    @Override
    protected void drawBox(GraphicsContext g, int boxX, int boxY, int boxSize) {
        Painter.drawRectangle(g, boxX, boxY, boxSize, boxSize);
        Painter.drawRectangle(g, boxX + BORDER_THICKNESS, boxY + BORDER_THICKNESS,
                boxSize - 2 * BORDER_THICKNESS, boxSize - 2 * BORDER_THICKNESS);
    }

    @Override
    protected void drawCheckedMark(GraphicsContext g, int boxX, int boxY, int boxSize) {
        int innerBoxSize = boxSize - INNER_BOX_OFFSET * 2;
        Painter.fillRectangle(g, boxX + INNER_BOX_OFFSET, boxY + INNER_BOX_OFFSET, innerBoxSize,
                innerBoxSize);
    }

}
