/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import ej.microui.display.GraphicsContext;
import ej.microvg.VectorFont;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.OnClickListener;
import ej.widget.event.Clickable;

/**
 * A widget selecting a value, made of a box drawn on the left of a text drawn with a vector font.
 * <p>
 * Clicking the widget changes what is selected, which subclasses define along with the shape of
 * the box and the mark filling it when the widget is checked.
 */
public abstract class VectorSelector extends Widget implements Clickable {

    /**
     * The identifier of the style extra field holding the color of the mark drawn in the box when
     * the widget is checked.
     */
    public static final int CHECKED_COLOR_FIELD = 3;

    private static final int SPACING_RATIO = 2;

    private final String text;
    private final int boxSizeSpacing;
    private final ClickState clickState;

    /**
     * The height of a line of text, which the size of the box and the spacing preceding the text
     * are derived from. It is computed when the optimal size of this widget is computed.
     */
    private double textHeight;

    /**
     * Creates a selector displaying the given text.
     *
     * @param text
     *            the text to display on the right of the box
     * @param boxSizeSpacing
     *            the height subtracted from the height of the text to get the size of the box
     */
    protected VectorSelector(String text, int boxSizeSpacing) {
        super(true);
        this.text = text;
        this.boxSizeSpacing = boxSizeSpacing;

        this.clickState = new ClickState(this, this);
        this.clickState.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                onClicked();
            }
        });
    }

    /**
     * Returns whether this widget is checked.
     *
     * @return {@code true} if this widget is checked, {@code false} otherwise
     */
    public abstract boolean isChecked();

    /**
     * Changes what this widget selects, called when it is clicked.
     */
    protected abstract void onClicked();

    /**
     * Draws the box of this widget.
     *
     * @param g
     *            the graphics context to draw on, its color being already set
     * @param boxX
     *            the x coordinate of the box
     * @param boxY
     *            the y coordinate of the box
     * @param boxSize
     *            the width and the height of the box
     */
    protected abstract void drawBox(GraphicsContext g, int boxX, int boxY, int boxSize);

    /**
     * Draws the mark filling the box of this widget when it is checked.
     *
     * @param g
     *            the graphics context to draw on, its color being already set
     * @param boxX
     *            the x coordinate of the box
     * @param boxY
     *            the y coordinate of the box
     * @param boxSize
     *            the width and the height of the box
     */
    protected abstract void drawCheckedMark(GraphicsContext g, int boxX, int boxY, int boxSize);

    @Override
    protected void computeContentOptimalSize(Size size) {
        Style style = getStyle();
        VectorFont font = VectorLabel.getFont(style);
        int fontSize = VectorLabel.getFontSize(style);

        int width = (int) Math.ceil(computeWidth(font, fontSize));
        this.textHeight = Math.ceil(font.getHeight(fontSize));
        size.setSize(width, (int) this.textHeight);
    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        Style style = getStyle();
        VectorFont font = VectorLabel.getFont(style);
        int fontSize = VectorLabel.getFontSize(style);
        int verticalAlignment = style.getVerticalAlignment();

        int boxSize = (int) Math.ceil(computeBoxSize());
        int boxX = Alignment.computeLeftX((int) computeWidth(font, fontSize), 0, contentWidth,
                style.getHorizontalAlignment());
        int boxY = Alignment.computeTopY(boxSize, 0, contentHeight, verticalAlignment);
        g.setColor(style.getColor());
        drawBox(g, boxX, boxY, boxSize);

        if (isChecked()) {
            g.setColor(style.getExtraInt(CHECKED_COLOR_FIELD, style.getColor()));
            drawCheckedMark(g, boxX, boxY, boxSize);
        }

        int textX = boxX + boxSize + (int) computeSpacing();
        int textY = Alignment.computeTopY((int) this.textHeight, 0, contentHeight,
                verticalAlignment);
        g.setColor(style.getColor());
        VectorGraphicsPainter.drawString(g, this.text, font, fontSize, textX, textY);
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

    private float computeWidth(VectorFont font, int fontSize) {
        return computeBoxSize() + computeSpacing() + font.measureStringWidth(this.text, fontSize);
    }

    private float computeBoxSize() {
        return (float) this.textHeight - this.boxSizeSpacing;
    }

    private float computeSpacing() {
        return (float) this.textHeight / SPACING_RATIO;
    }

}
