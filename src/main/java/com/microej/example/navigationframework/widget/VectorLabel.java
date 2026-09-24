/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import java.util.ArrayList;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microvg.VectorFont;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * Displays a text with a vector font.
 * <p>
 * A text which is wider than the width available to the widget is wrapped on several lines, broken
 * on the spaces it contains. A word which is wider than the available width on its own is kept on
 * its line.
 */
public class VectorLabel extends Widget {

    /**
     * The identifier of the style extra field holding the {@link VectorFont} used to draw the text.
     */
    public static final int FONT_FIELD = 0;
    /**
     * The identifier of the style extra field holding the size of the font used to draw the text.
     */
    public static final int FONT_SIZE_FIELD = 1;

    private static final String DEFAULT_FONT_PATH = "/fonts/SourceSansPro-Regular.ttf";
    private static final int DEFAULT_SIZE = 15;
    private static final char SPACE = ' ';

    /**
     * The font used when the style does not set one, loaded the first time it is needed and kept
     * afterwards: loading a vector font parses its file, which is too costly to do on every render.
     */
    @Nullable
    private static VectorFont defaultFont;

    private String text;
    private String[] lines;
    /** The width of each line, measured when the lines are computed so that rendering does not. */
    private int[] lineWidths;
    private boolean linesValid;
    private int linesAvailableWidth;
    private int widestLineWidth;
    private int lineHeight;

    /**
     * Creates a label displaying an empty text.
     */
    public VectorLabel() {
        this("");
    }

    /**
     * Creates a label displaying the given text.
     *
     * @param text
     *            the text to display
     */
    public VectorLabel(String text) {
        this(text, false);
    }

    /**
     * Creates a label displaying the given text, enabled or not.
     *
     * @param text
     *            the text to display
     * @param enabled
     *            {@code true} if the label handles events, {@code false} otherwise
     */
    protected VectorLabel(String text, boolean enabled) {
        super(enabled);
        this.text = text;
        this.lines = new String[] { text };
        this.lineWidths = new int[1];
    }

    /**
     * Returns the text displayed by this label.
     *
     * @return the displayed text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text displayed by this label.
     *
     * @param text
     *            the text to display
     */
    public void setText(String text) {
        this.text = text;
        this.linesValid = false;
    }

    @Override
    public void updateStyle() {
        super.updateStyle();

        // the font and its size come from the style: the lines have to be computed again.
        this.linesValid = false;
    }

    @Override
    protected void computeContentOptimalSize(Size size) {
        layOutText(size.getWidth());
        size.setSize(this.widestLineWidth, this.lineHeight * this.lines.length);
    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        layOutText(contentWidth);

        Style style = getStyle();
        g.setColor(style.getColor());
        VectorFont font = getFont(style);
        int fontSize = getFontSize(style);
        String[] lines = this.lines;
        int[] lineWidths = this.lineWidths;
        int lineHeight = this.lineHeight;
        int horizontalAlignment = style.getHorizontalAlignment();
        int y = Alignment.computeTopY(lineHeight * lines.length, 0, contentHeight,
                style.getVerticalAlignment());

        for (int i = 0; i < lines.length; i++) {
            int x = Alignment.computeLeftX(lineWidths[i], 0, contentWidth, horizontalAlignment);
            float lineY = y + (float) i * lineHeight;
            VectorGraphicsPainter.drawString(g, lines[i], font, fontSize, x, lineY);
        }
    }

    /**
     * Breaks the text in lines fitting in the given width, unless it has already been done for that
     * width.
     *
     * @param availableWidth
     *            the width available to the text, {@link Widget#NO_CONSTRAINT} when unconstrained
     */
    private void layOutText(int availableWidth) {
        if (this.linesValid && this.linesAvailableWidth == availableWidth) {
            return;
        }

        Style style = getStyle();
        VectorFont font = getFont(style);
        int fontSize = getFontSize(style);

        String[] lines = splitInLines(this.text, font, fontSize, availableWidth);
        int[] lineWidths = new int[lines.length];
        int widestLineWidth = 0;
        for (int i = 0; i < lines.length; i++) {
            int lineWidth = (int) Math.ceil(font.measureStringWidth(lines[i], fontSize));
            lineWidths[i] = lineWidth;
            if (lineWidth > widestLineWidth) {
                widestLineWidth = lineWidth;
            }
        }

        this.lines = lines;
        this.lineWidths = lineWidths;
        this.widestLineWidth = widestLineWidth;
        this.lineHeight = (int) Math.ceil(font.getHeight(fontSize));
        this.linesAvailableWidth = availableWidth;
        this.linesValid = true;
    }

    /**
     * Breaks the given text on its spaces so that every line fits in the given width.
     *
     * @param text
     *            the text to break
     * @param font
     *            the font the text is drawn with
     * @param fontSize
     *            the size the text is drawn at
     * @param availableWidth
     *            the width available to the text, {@link Widget#NO_CONSTRAINT} when unconstrained
     * @return the lines to draw, at least one
     */
    private static String[] splitInLines(String text, VectorFont font, int fontSize,
            int availableWidth) {
        if (availableWidth <= 0 || font.measureStringWidth(text, fontSize) <= availableWidth) {
            return new String[] { text };
        }

        ArrayList<String> lines = new ArrayList<>();
        int length = text.length();
        int lineStart = 0;
        int lastBreak = -1;
        for (int i = 0; i <= length; i++) {
            if (i < length && text.charAt(i) != SPACE) {
                continue;
            }

            // the text from the start of the line up to here is a candidate line.
            boolean tooWide = font.measureStringWidth(text.substring(lineStart, i),
                    fontSize) > availableWidth;
            if (tooWide && lastBreak > lineStart) {
                lines.add(text.substring(lineStart, lastBreak));
                lineStart = lastBreak + 1;
            }
            lastBreak = i;
        }
        lines.add(text.substring(lineStart));

        return lines.toArray(new String[lines.size()]);
    }

    /* package */ static VectorFont getFont(Style style) {
        return style.getExtraObject(FONT_FIELD, VectorFont.class, getDefaultFont());
    }

    private static VectorFont getDefaultFont() {
        VectorFont font = defaultFont;
        if (font == null) {
            font = VectorFont.loadFont(DEFAULT_FONT_PATH);
            defaultFont = font;
        }
        return font;
    }

    /* package */ static int getFontSize(Style style) {
        return style.getExtraInt(FONT_SIZE_FIELD, DEFAULT_SIZE);
    }

}
