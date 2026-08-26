/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.microui.display.GraphicsContext;
import ej.microvg.VectorFont;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

public class VectorLabel extends Widget {

    public static final int FONT_FIELD = 0;
    public static final int FONT_SIZE_FIELD = 1;

    private static final String DEFAULT_FONT_PATH = "/fonts/SourceSansPro-Regular.ttf";
    private static final int DEFAULT_SIZE = 15;

    private String text;
    private double stringWidth;
    private double stringHeight;

    public VectorLabel() {
        this("");
    }

    public VectorLabel(String text) {
        this.text = text;
    }

    protected VectorLabel(String text, boolean enabled) {
        super(enabled);
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void computeContentOptimalSize(Size size) {
        VectorFont font = getStyle().getExtraObject(FONT_FIELD, VectorFont.class, VectorFont.loadFont(DEFAULT_FONT_PATH));
        int fontSize = getStyle().getExtraInt(VectorLabel.FONT_SIZE_FIELD, DEFAULT_SIZE);

        this.stringWidth = Math.ceil(font.measureStringWidth(this.text, fontSize));
        this.stringHeight = Math.ceil(font.getHeight(fontSize));
        size.setSize((int) this.stringWidth, (int) this.stringHeight);
    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        Style style = getStyle();
        g.setColor(style.getColor());
        VectorFont font = style.getExtraObject(FONT_FIELD, VectorFont.class, VectorFont.loadFont(DEFAULT_FONT_PATH));
        int fontSize = style.getExtraInt(VectorLabel.FONT_SIZE_FIELD, DEFAULT_SIZE);
        int labelX = Alignment.computeLeftX((int) this.stringWidth, 0 ,contentWidth, style.getHorizontalAlignment());
        int labelY = Alignment.computeTopY((int) this.stringHeight, 0, contentHeight, style.getVerticalAlignment());

        VectorGraphicsPainter.drawString(g, this.text, font, fontSize, labelX, labelY);
    }

}
