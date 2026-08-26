/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.pages.transitions.CustomTransition;
import com.microej.example.navigationframework.widget.VectorButton;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.navigation.transition.FadeTransition;
import ej.navigation.Navigator;
import ej.navigation.transition.SlideTransition;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

public class HomePage extends BasePage {

    @Override
    public String getTitle() {
        return "Home Page";
    }

    @Override
    protected Widget getCenterWidget() {
        SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);

        VectorLabel titleLabel = new VectorLabel("Navigation Framework Capabilities");
        titleLabel.addClassSelector(Main.CS_HOME_TITLE);

        dock.setFirstChild(titleLabel);

        List list = new List(LayoutOrientation.VERTICAL);

        VectorButton immediateButton = new VectorButton("Immediate Transition");
        immediateButton.addClassSelector(Main.CS_LIST_ITEM);
        immediateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.IMMEDIATE);
            }
        });
        list.addChild(immediateButton);

        VectorButton slideButton = new VectorButton("Slide Transition");
        slideButton.addClassSelector(Main.CS_LIST_ITEM);
        slideButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.SLIDE, new SlideTransition());
            }
        });
        list.addChild(slideButton);

        VectorButton fadeButton = new VectorButton("Fade Transition");
        fadeButton.addClassSelector(Main.CS_LIST_ITEM);
        fadeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.FADE, new FadeTransition());
            }
        });
        list.addChild(fadeButton);

        VectorButton customButton = new VectorButton("Custom Transition");
        customButton.addClassSelector(Main.CS_LIST_ITEM);
        customButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.CUSTOM, new CustomTransition());
            }
        });
        list.addChild(customButton);

        VectorButton argumentsButton = new VectorButton("Passing Arguments between Pages");
        argumentsButton.addClassSelector(Main.CS_LIST_ITEM);
        argumentsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.ARGUMENTS);
            }
        });
        list.addChild(argumentsButton);

        VectorButton advancedButton = new VectorButton("Advanced Navigation");
        advancedButton.addClassSelector(Main.CS_LIST_ITEM);
        advancedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.ADVANCED_1_PAGE);
            }
        });
        list.addChild(advancedButton);

        dock.setCenterChild(list);

        return dock;
    }

}
