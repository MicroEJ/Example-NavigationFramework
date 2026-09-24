/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import com.microej.example.navigationframework.Main;
import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;
import ej.widget.event.Clickable;

/**
 * A tappable entry of a navigation list: an icon, a title, a subtitle and a trailing chevron.
 * <p>
 * The row draws its own divider, from the right edge of the icon to the right edge of the row, so
 * that it lines up with the text rather than with the icon. It is displayed only on the entries
 * for which {@link #showDivider()} is called, and its color and thickness come from the
 * {@link #DIVIDER_COLOR_FIELD} and {@link #DIVIDER_THICKNESS_FIELD} extra style fields.
 */
public class NavigationRow extends SimpleDock implements Clickable {

    /** The extra field ID of the color of the divider. */
    public static final int DIVIDER_COLOR_FIELD = 0;
    /** The extra field ID of the thickness of the divider, in pixels. A missing field draws no divider. */
    public static final int DIVIDER_THICKNESS_FIELD = 1;

    private static final String CHEVRON_ICON_PATH = "/images/chevron.svg";
    private static final int NO_DIVIDER = 0;

    private final ClickState clickState;
    private final VectorImageWidget icon;

    /**
     * Creates a navigation row.
     *
     * @param iconPath
     *            the path of the vector image displayed in the icon tile
     * @param iconClassSelector
     *            the class selector styling the icon tile
     * @param title
     *            the title of the entry
     * @param subtitle
     *            the text displayed below the title
     */
    public NavigationRow(String iconPath, int iconClassSelector, String title, String subtitle) {
        super(LayoutOrientation.HORIZONTAL);
        setEnabled(true);

        this.clickState = new ClickState(this, this);

        addClassSelector(Main.CS_ROW);

        VectorImageWidget icon = new VectorImageWidget(iconPath);
        icon.addClassSelector(iconClassSelector);
        setFirstChild(icon);
        this.icon = icon;

        List texts = new List(LayoutOrientation.VERTICAL);
        texts.addClassSelector(Main.CS_ROW_TEXTS);

        VectorLabel titleLabel = new VectorLabel(title);
        titleLabel.addClassSelector(Main.CS_ROW_TITLE);
        texts.addChild(titleLabel);

        VectorLabel subtitleLabel = new VectorLabel(subtitle);
        subtitleLabel.addClassSelector(Main.CS_ROW_SUBTITLE);
        texts.addChild(subtitleLabel);

        setCenterChild(texts);

        VectorImageWidget chevron = new VectorImageWidget(CHEVRON_ICON_PATH);
        chevron.addClassSelector(Main.CS_ROW_CHEVRON);
        setLastChild(chevron);
    }

    /**
     * Sets the listener notified when this row is tapped.
     *
     * @param listener
     *            the listener to notify, {@code null} to remove the previous one
     */
    public void setOnClickListener(@Nullable OnClickListener listener) {
        this.clickState.setOnClickListener(listener);
    }

    /**
     * Displays a divider under this row.
     */
    public void showDivider() {
        addClassSelector(Main.CS_ROW_DIVIDER);
    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        // The children leave the translation and the clip of the last child in place: save them
        // before they render, so that the divider is drawn in the coordinates of this row.
        int translateX = g.getTranslationX();
        int translateY = g.getTranslationY();
        int clipX = g.getClipX();
        int clipY = g.getClipY();
        int clipWidth = g.getClipWidth();
        int clipHeight = g.getClipHeight();

        super.renderContent(g, contentWidth, contentHeight);

        Style style = getStyle();
        int thickness = style.getExtraInt(DIVIDER_THICKNESS_FIELD, NO_DIVIDER);
        if (thickness > NO_DIVIDER) {
            g.setTranslation(translateX, translateY);
            g.setClip(clipX, clipY, clipWidth, clipHeight);
            VectorImageWidget icon = this.icon;
            int dividerX = icon.getX() + icon.getWidth();
            g.setColor(style.getExtraInt(DIVIDER_COLOR_FIELD, style.getColor()));
            Painter.fillRectangle(g, dividerX, contentHeight - thickness, contentWidth - dividerX, thickness);
        }
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
