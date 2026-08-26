/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.transitions;

import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.navigation.transition.Transition;
import ej.navigation.transition.TransitionContext;
import ej.navigation.transition.TransitionListener;

/**
 * A transition that splits the outgoing content in two halves and reveals the incoming content
 * through a vertical band, centered on the content area, that widens as the animation goes on.
 * <p>
 * The two halves slide away from the center exactly as fast as the band widens, so the band is the gap
 * they leave behind. At the end of the animation the band covers the whole content area and both
 * halves have left the screen.
 * <p>
 * Clipping the incoming content to the band is beyond what a {@link TransitionContext} offers, so this
 * transition relies on the incoming content being wrapped in a {@link SplitRevealContainer}, which
 * performs the rendering. When it is not, or when there is no outgoing content, the content change is
 * immediate.
 *
 * @see SplitRevealContainer
 */
public class CustomTransition implements Transition {

    private final int duration;

    /**
     * Creates a split reveal with the default duration.
     */
    public CustomTransition() {
        this(DEFAULT_DURATION_MILLIS);
    }

    /**
     * Creates a split reveal with the given duration.
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
    public void run(TransitionContext context, TransitionListener listener) {
        Widget incoming = context.getIncomingContent();
        Widget outgoing = context.getOutgoingContent();
        if (outgoing == null || !(incoming instanceof SplitRevealContainer)) {
            listener.onTransitionEnd();
            return;
        }

        SplitRevealContainer revealContainer = (SplitRevealContainer) incoming;
        revealContainer.startSplit(outgoing);
        renderFrame(context);
        context.animate(new SplitAnimation(context, revealContainer, listener, this.duration));
    }

    /**
     * Renders a frame of the split.
     * <p>
     * The navigator attaches the incoming content without showing it, so the container performing the
     * split cannot request its own render. Both contents keep their natural position, at the origin of
     * the content area: positioning them is only the way for a transition to ask the navigator for a
     * frame.
     *
     * @param context
     *            the context of the transition.
     */
    private static void renderFrame(TransitionContext context) {
        context.positionContents(0, 0, 0, 0);
    }

    /**
     * Computes the width of the revealing band for a running frame, interpolating linearly from a closed
     * band at {@code elapsed == 0} to a band covering the whole content area at
     * {@code elapsed == duration}.
     *
     * @param elapsed
     *            the time elapsed since the animation started, in milliseconds; not negative.
     * @param duration
     *            the total animation duration, in milliseconds; strictly positive.
     * @param contentWidth
     *            the content width, in pixels.
     * @return the band width, in pixels.
     */
    /* package */ static int bandWidthAt(long elapsed, int duration, int contentWidth) {
        if (elapsed >= duration) {
            return contentWidth;
        }
        return (int) ((elapsed * contentWidth) / duration);
    }

    /**
     * The animation driving a single split reveal, widening the band linearly over the configured
     * duration.
     */
    private static class SplitAnimation implements Animation {

        private final TransitionContext context;
        private final SplitRevealContainer revealContainer;
        private final TransitionListener listener;
        private final int duration;
        private final int contentWidth;
        private long startTime;
        private boolean started;

        /* package */ SplitAnimation(TransitionContext context, SplitRevealContainer revealContainer,
                TransitionListener listener, int duration) {
            this.context = context;
            this.revealContainer = revealContainer;
            this.listener = listener;
            this.duration = duration;
            this.contentWidth = context.getContentWidth();
        }

        @Override
        public boolean tick(long platformTimeMillis) {
            if (!this.started) {
                this.started = true;
                this.startTime = platformTimeMillis;
            }
            long elapsed = platformTimeMillis - this.startTime;
            if (elapsed >= this.duration) {
                this.revealContainer.endSplit();
                renderFrame(this.context);
                this.listener.onTransitionEnd();
                return false;
            }
            this.revealContainer.setBandWidth(bandWidthAt(elapsed, this.duration, this.contentWidth));
            renderFrame(this.context);
            return true;
        }
    }
}
