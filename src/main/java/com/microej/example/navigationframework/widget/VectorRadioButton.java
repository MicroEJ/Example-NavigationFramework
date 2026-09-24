/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import ej.drawing.ShapePainter;
import ej.microui.display.GraphicsContext;

/**
 * A radio button drawing a circle, filled with a disc when it is the checked button of its group.
 * <p>
 * Clicking the radio button checks it, unchecking the previously checked button of its group.
 */
public class VectorRadioButton extends VectorSelector {

    private static final int BOX_SIZE_SPACING = 3;
    private static final int CIRCLE_OFFSET = 1;
    private static final int CIRCLE_THICKNESS = 1;
    private static final int FADE = 1;
    private static final int DIAMETER_RATIO = 2;

    private final RadioButtonGroup group;

    /**
     * Creates an unchecked radio button displaying the given text.
     *
     * @param text
     *            the text to display on the right of the circle
     * @param group
     *            the group holding the radio buttons among which a single one is checked
     */
    public VectorRadioButton(String text, RadioButtonGroup group) {
        super(text, BOX_SIZE_SPACING);
        this.group = group;
    }

    @Override
    public boolean isChecked() {
        return this.group.isChecked(this);
    }

    /**
     * Checks this radio button, unchecking the previously checked radio button of its group.
     */
    public void check() {
        this.group.setChecked(this);
    }

    @Override
    protected void onClicked() {
        check();
    }

    @Override
    protected void drawBox(GraphicsContext g, int boxX, int boxY, int boxSize) {
        ShapePainter.drawThickFadedCircle(g, boxX + CIRCLE_OFFSET, boxY + CIRCLE_OFFSET,
                boxSize - 2 * CIRCLE_OFFSET, CIRCLE_THICKNESS, FADE);
    }

    @Override
    protected void drawCheckedMark(GraphicsContext g, int boxX, int boxY, int boxSize) {
        int radius = boxSize / DIAMETER_RATIO;
        ShapePainter.drawThickFadedPoint(g, boxX + radius, boxY + radius, radius, FADE);
    }

}
