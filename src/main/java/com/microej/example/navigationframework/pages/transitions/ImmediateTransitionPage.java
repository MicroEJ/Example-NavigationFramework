/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.transitions;

import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorImageWidget;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;

public class ImmediateTransitionPage extends BasePage {

    @Override
    public String getTitle() {
        return "Immediate Transition Page";
    }

    @Override
    protected Widget getCenterWidget() {
        return new VectorImageWidget("/images/mascot.svg");
    }
}
