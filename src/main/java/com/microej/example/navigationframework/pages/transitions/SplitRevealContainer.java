/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.transitions;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * A container that reveals its content through a vertical band, centered on the content area, while
 * the content it replaces is split in two halves sliding away from that band.
 * <p>
 * The navigation framework only lets a transition move and cross-fade the two contents, so a
 * transition cannot clip the incoming content by itself. This container provides that missing piece:
 * on every frame it draws the two halves of the outgoing content shifted apart, then its own content
 * clipped to the band left between them. {@link CustomTransition} drives it, so a page animated by
 * that transition must wrap its content in this container.
 * <p>
 * The outgoing content is snapshotted once, when the split starts, and the halves are then blitted
 * from that snapshot. Rendering the outgoing widget itself on every frame instead would re-render its
 * whole vector content twice per frame, which halves the frame rate of the animation. The snapshot
 * costs one screen-sized image for the duration of the transition, as the framework's own fade does.
 * <p>
 * Outside of a transition, the content is rendered as-is.
 *
 * @see CustomTransition
 */
public class SplitRevealContainer extends Container {

    private final Widget content;
    @Nullable
    private Widget outgoingContent;
    @Nullable
    private BufferedImage outgoingSnapshot;
    private int bandWidth;

    /**
     * Creates a container revealing the given content.
     *
     * @param content
     *            the content to reveal.
     */
    public SplitRevealContainer(Widget content) {
        this.content = content;
        addChild(content);
    }

    @Override
    protected void computeContentOptimalSize(Size size) {
        Widget localContent = this.content;
        computeChildOptimalSize(localContent, size.getWidth(), size.getHeight());
        size.setSize(localContent.getWidth(), localContent.getHeight());
    }

    @Override
    protected void layOutChildren(int contentWidth, int contentHeight) {
        Widget localContent = this.content;
        computeChildOptimalSize(localContent, contentWidth, contentHeight);
        layOutChild(localContent, 0, 0, contentWidth, contentHeight);
    }

    /**
     * Renders the content alone when no split is in progress. Otherwise blits the left half of the
     * outgoing content shifted to the left, its right half shifted to the right, and renders the
     * content of this container clipped to the band the two halves leave in the middle.
     * <p>
     * The navigator detaches the outgoing content when it finishes a transition, and when it
     * interrupts one to start a new navigation. A detached outgoing content therefore means the split
     * is over, whether it completed or not.
     */
    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        BufferedImage snapshot = this.outgoingSnapshot;
        Widget outgoing = this.outgoingContent;
        if (snapshot == null || outgoing == null || !outgoing.isAttached()) {
            releaseOutgoingContent();
            renderChild(this.content, g);
            return;
        }

        int band = this.bandWidth;
        int bandLeft = (contentWidth - band) / 2;
        int bandRight = bandLeft + band;
        int middle = contentWidth / 2;

        // The half that used to end at the middle of the content area now ends at the left edge of the band.
        if (bandLeft > 0) {
            Painter.drawImageRegion(g, snapshot, middle - bandLeft, 0, bandLeft, contentHeight, 0, 0);
        }
        // The half that used to start at the middle of the content area now starts at its right edge.
        int rightWidth = contentWidth - bandRight;
        if (rightWidth > 0) {
            Painter.drawImageRegion(g, snapshot, middle, 0, rightWidth, contentHeight, bandRight, 0);
        }

        g.intersectClip(bandLeft, 0, band, contentHeight);
        renderChild(this.content, g);
    }

    /**
     * Starts splitting the given outgoing content, with a closed band: nothing of the content of this
     * container is visible yet.
     * <p>
     * Rendering the new state is up to the caller. The navigator attaches the incoming content without
     * showing it, and {@link #requestRender()} does nothing on a widget that is not shown, so the
     * frames of a split have to be requested through the transition context.
     *
     * @param outgoingContent
     *            the content to split in two halves.
     */
    /* package */ void startSplit(Widget outgoingContent) {
        releaseOutgoingContent();
        this.outgoingContent = outgoingContent;
        this.outgoingSnapshot = snapshot(outgoingContent);
        this.bandWidth = 0;
    }

    /**
     * Sets the width of the band showing the content of this container. Rendering the new state is up
     * to the caller, as for {@link #startSplit(Widget)}.
     *
     * @param bandWidth
     *            the band width, in pixels, from {@code 0} (closed) to the content width (fully open).
     */
    /* package */ void setBandWidth(int bandWidth) {
        this.bandWidth = bandWidth;
    }

    /**
     * Ends the split, so that the content of this container is rendered alone again, and releases the
     * outgoing content. Rendering the new state is up to the caller, as for
     * {@link #startSplit(Widget)}.
     */
    /* package */ void endSplit() {
        releaseOutgoingContent();
        this.bandWidth = 0;
    }

    private void releaseOutgoingContent() {
        BufferedImage localSnapshot = this.outgoingSnapshot;
        if (localSnapshot != null) {
            localSnapshot.close();
        }
        this.outgoingSnapshot = null;
        this.outgoingContent = null;
    }

    /**
     * Takes a snapshot of the given content.
     * <p>
     * The content belongs to the navigator, not to this container, so it is drawn through its public
     * rendering entry point rather than as a child of this container.
     *
     * @param outgoing
     *            the content to snapshot.
     * @return the snapshot, or {@code null} if the given content has nothing to show.
     */
    @Nullable
    private static BufferedImage snapshot(Widget outgoing) {
        int width = outgoing.getWidth();
        int height = outgoing.getHeight();
        if (width <= 0 || height <= 0) {
            return null;
        }
        BufferedImage image = new BufferedImage(width, height);
        outgoing.render(image.getGraphicsContext());
        return image;
    }
}
