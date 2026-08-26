/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.microvg.VectorFont;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

public class VectorCheckBox extends Widget {

    public static final int FONT_FIELD = 0;
    public static final int FONT_SIZE_FIELD = 1;
    public static final int CHECKED_COLOR_FIELD = 3;

    private static final String DEFAULT_FONT_PATH = "/fonts/SourceSansPro-Regular.ttf";
    private static final int DEFAULT_SIZE = 15;

    private static final int INNER_BOX_OFFSET = 7;
    private static final int BOX_SIZE_SPACING = 6;

    private final String text;
    private boolean checked;
    private double width;
    private double height;

    public VectorCheckBox(String text) {
        super(true);
        this.text = text;
        this.checked = false;
    }

    /**
     * Returns whether this check box is checked.
     *
     * @return {@code true} if this check box is checked.
     */
    public boolean isChecked() {
        return this.checked;
    }

    /**
     * Sets the checked state of this check box.
     *
     * @param checked
     *            the checked state to set.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
        requestRender();
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
        Painter.drawRectangle(g, boxX, boxY, boxSize, boxSize);
        Painter.drawRectangle(g, boxX + 1, boxY + 1, boxSize - 2, boxSize - 2);

        // fill box
        if (this.checked) {
            g.setColor(getCheckedColor(style));
            int innerBoxSize = boxSize - INNER_BOX_OFFSET * 2;
            Painter.fillRectangle(g, boxX + INNER_BOX_OFFSET, boxY + INNER_BOX_OFFSET, innerBoxSize, innerBoxSize);
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
                this.checked = !this.checked;
                requestRender();
                return true;
            }
        }

        return super.handleEvent(event);
    }

}
