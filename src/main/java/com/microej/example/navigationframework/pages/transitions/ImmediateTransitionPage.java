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
 * The page demonstrating {@link Transition#IMMEDIATE}.
 */
public class ImmediateTransitionPage extends BasePage {

    @Override
    public String getTitle() {
        return "Immediate";
    }

    @Override
    protected String getDescription() {
        return "The next page replaces this one instantly, with no animation";
    }

    @Override
    protected Widget getCenterWidget() {
        return new VectorImageWidget("/images/mascot.svg");
    }
}
