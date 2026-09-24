/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.transitions;

import ej.bon.Constants;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.navigation.transition.Transition;
import ej.navigation.transition.TransitionContext;

/**
 * A transition revealing the incoming page through bands that widen until they cover the content
 * area.
 * <p>
 * A forward navigation opens one band at the center of the content area. A back navigation opens one
 * band at each edge instead, so that leaving a page undoes visually what entering it did.
 * <p>
 * A frame draws only the region of the incoming page its bands cover, taken from
 * {@link TransitionContext#getIncomingImage()}. The outgoing page keeps showing everywhere else,
 * because a frame draws over what the previous one left on the display.
 * <p>
 * The bands are regions of an image, so this transition needs the snapshot the framework captures.
 * When an application removes it through {@link Transition#SNAPSHOTS_CONSTANT},
 * {@link #getDuration()} reports {@code 0} and the page change is instant.
 */
public class CustomTransition implements Transition {

    /**
     * Whether the incoming page can be captured, and therefore drawn region by region.
     */
    private static final boolean SNAPSHOTS_ENABLED = Constants.getBoolean(SNAPSHOTS_CONSTANT);

    private final int duration;

    /**
     * Creates a band reveal with the default duration.
     */
    public CustomTransition() {
        this(DEFAULT_DURATION_MILLIS);
    }

    /**
     * Creates a band reveal with the given duration.
     *
     * @param duration
     *            the animation duration, in milliseconds; must be strictly positive.
     * @throws IllegalArgumentException
     *             if the given duration is not strictly positive.
     */
    public CustomTransition(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException();
        }
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return SNAPSHOTS_ENABLED ? this.duration : 0;
    }

    @Override
    public void start(TransitionContext context) {
        // Nothing to prepare: a band is computed from the progress and the context alone.
    }

    @Override
    public void render(GraphicsContext g, TransitionContext context, float progress) {
        Image incoming = context.getIncomingImage();
        // The duration is 0 without the snapshots, so no frame is drawn then.
        assert incoming != null;

        int bandWidth = (int) (progress * context.getContentWidth());
        if (context.getNavigationKind() == TransitionContext.BACK) {
            drawEdgeBands(g, context, incoming, bandWidth);
        } else {
            drawCenterBand(g, context, incoming, bandWidth);
        }
    }

    /**
     * Draws the region of the incoming page covered by one band centered on the content area.
     *
     * @param g
     *            the graphics context of the frame being drawn.
     * @param context
     *            the context of the page change being animated.
     * @param incoming
     *            the incoming page, as an image the size of the content area.
     * @param bandWidth
     *            the width of the band, in pixels.
     */
    private static void drawCenterBand(GraphicsContext g, TransitionContext context, Image incoming,
            int bandWidth) {
        if (bandWidth <= 0) {
            return;
        }

        int contentHeight = context.getContentHeight();
        int bandLeft = (context.getContentWidth() - bandWidth) / 2;
        Painter.drawImageRegion(g, incoming, bandLeft, 0, bandWidth, contentHeight, bandLeft, 0);
    }

    /**
     * Draws the regions of the incoming page covered by two bands, one growing from the left edge of
     * the content area and one growing from its right edge.
     *
     * @param g
     *            the graphics context of the frame being drawn.
     * @param context
     *            the context of the page change being animated.
     * @param incoming
     *            the incoming page, as an image the size of the content area.
     * @param bandWidth
     *            the width the two bands cover together, in pixels.
     */
    private static void drawEdgeBands(GraphicsContext g, TransitionContext context, Image incoming,
            int bandWidth) {
        int edgeWidth = bandWidth / 2;
        if (edgeWidth <= 0) {
            return;
        }

        int contentHeight = context.getContentHeight();
        Painter.drawImageRegion(g, incoming, 0, 0, edgeWidth, contentHeight, 0, 0);

        int rightBandLeft = context.getContentWidth() - edgeWidth;
        Painter.drawImageRegion(g, incoming, rightBandLeft, 0, edgeWidth, contentHeight,
                rightBandLeft, 0);
    }

}
