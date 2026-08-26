/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.drawing.ShapePainter;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.microvg.VectorFont;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

public class VectorRadioButton extends Widget {

    public static final int FONT_FIELD = 0;
    public static final int FONT_SIZE_FIELD = 1;
    public static final int CHECKED_COLOR_FIELD = 3;

    private static final String DEFAULT_FONT_PATH = "/fonts/SourceSansPro-Regular.ttf";
    private static final int DEFAULT_SIZE = 15;

    private static final int INNER_BOX_OFFSET = 4;
    private static final int BOX_SIZE_SPACING = 3;

    private final String text;
    private double width;
    private double height;
    private final RadioButtonGroup group;

    public VectorRadioButton(String text, RadioButtonGroup group) {
        super(true);
        this.text = text;
        this.group = group;
    }

    /**
     * Returns whether this radio button is the checked one of its group.
     *
     * @return {@code true} if this radio button is checked.
     */
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
    protected void computeContentOptimalSize(Size size) {
        VectorFont font = getStyle().getExtraObject(FONT_FIELD, VectorFont.class, VectorFont.loadFont(DEFAULT_FONT_PATH));
        int fontSize = getStyle().getExtraInt(FONT_SIZE_FIELD, DEFAULT_SIZE);

        this.width = Math.ceil(computeWidth(this.text, font, fontSize));
        this.height = Math.ceil(font.getHeight(fontSize));
        size.setSize((int) this.width, (int) this.height);
    }

    private float computeWidth(String text, VectorFont font, int fontSize) {
        return computeBoxSize(font, fontSize) + computeSpacing(font, fontSize) + font.measureStringWidth(text, fontSize);
    }

    private float computeBoxSize(VectorFont font, int fontSize) {
        return (float) this.height - BOX_SIZE_SPACING;
    }

    private float computeSpacing(VectorFont font, int fontSize) {
        return (float) this.height / 2;
    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        Style style = getStyle();
        g.setColor(style.getColor());
        VectorFont font = style.getExtraObject(FONT_FIELD, VectorFont.class, VectorFont.loadFont(DEFAULT_FONT_PATH));
        int fontSize = style.getExtraInt(VectorLabel.FONT_SIZE_FIELD, DEFAULT_SIZE);
        int horizontalAlignment =  style.getHorizontalAlignment();
        int verticalAlignment = style.getVerticalAlignment();

        // draw box
        int boxSize = (int) Math.ceil(computeBoxSize(font, fontSize));
        int boxX = Alignment.computeLeftX((int) computeWidth(this.text, font, fontSize), 0, contentWidth, horizontalAlignment);
        int boxY = Alignment.computeTopY(boxSize, 0, contentHeight, verticalAlignment);
        g.setColor(style.getColor());
        ShapePainter.drawThickFadedCircle(g, boxX + 1, boxY + 1, boxSize - 2, 1, 1);

        // fill box
        if (this.group.isChecked(this)) {
            g.setColor(getCheckedColor(style));
            ShapePainter.drawThickFadedPoint(g, boxX + boxSize / 2, boxY + boxSize / 2, boxSize / 2, 1);
        }

        // draw text
        int textX = boxX + boxSize + (int) computeSpacing(font, fontSize);
        int textY = Alignment.computeTopY((int) this.height, 0, contentHeight, verticalAlignment);
        g.setColor(style.getColor());
        VectorGraphicsPainter.drawString(g, this.text, font, fontSize, textX, textY);
    }

    private static int getCheckedColor(Style style) {
        return style.getExtraInt(CHECKED_COLOR_FIELD, style.getColor());
    }

    @Override
    public boolean handleEvent(int event) {
        int type = Event.getType(event);
        if (type == Pointer.EVENT_TYPE) {
            int action = Buttons.getAction(event);
            if (action == Buttons.RELEASED) {
                this.group.setChecked(this);
                return true;
            }
        }

        return super.handleEvent(event);
    }

}
