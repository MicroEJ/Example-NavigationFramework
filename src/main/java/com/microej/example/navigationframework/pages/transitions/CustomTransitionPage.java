/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.transitions;

import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorImageWidget;
import ej.mwt.Widget;

public class CustomTransitionPage extends BasePage {

    /**
     * Wraps the content of this page so that {@link CustomTransition} can reveal it through its
     * widening band.
     */
    @Override
    protected Widget getContent() {
        return new SplitRevealContainer(super.getContent());
    }

    @Override
    public String getTitle() {
        return "Custom Transition Page";
    }

    @Override
    protected Widget getCenterWidget() {
        return new VectorImageWidget("/images/mascot.svg");
    }
}
