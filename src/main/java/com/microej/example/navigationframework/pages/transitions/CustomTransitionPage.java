/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages.transitions;

import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorImageWidget;
import ej.mwt.Widget;
import ej.navigation.transition.Transition;

/**
 * The page demonstrating {@link CustomTransition}, a transition written for this example.
 */
public class CustomTransitionPage extends BasePage {

    @Override
    public String getTitle() {
        return "Custom";
    }

    @Override
    protected String getDescription() {
        return "This page is revealed through a band widening from the center";
    }

    @Override
    protected Widget getCenterWidget() {
        return new VectorImageWidget("/images/mascot.svg");
    }

    @Override
    protected Transition getExitTransition() {
        return new CustomTransition();
    }
}
