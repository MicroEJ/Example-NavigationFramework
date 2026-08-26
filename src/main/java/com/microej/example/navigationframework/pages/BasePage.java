/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.widget.VectorImageButton;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.navigation.Page;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

public abstract class BasePage extends Page {

    private static final String BACK_ICON_PATH = "/images/back.svg";
    private static final String HOME_ICON_PATH = "/images/home.svg";

    @Override
    protected Widget getContent() {
        SimpleDock root = new SimpleDock(LayoutOrientation.VERTICAL);
        root.addClassSelector(Main.CS_ROOT);

        SimpleDock topBar = new SimpleDock(LayoutOrientation.HORIZONTAL);

        VectorImageButton home = new VectorImageButton(HOME_ICON_PATH);
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateBackTo(Pages.HOME);
            }
        });
        topBar.setFirstChild(home);

        VectorLabel title = new VectorLabel(this.getTitle());
        topBar.setCenterChild(title);

        VectorImageButton back = new VectorImageButton(BACK_ICON_PATH);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateBack();
            }
        });
        topBar.setLastChild(back);

        root.setFirstChild(topBar);
        Widget centerWidget = getCenterWidget();
        centerWidget.addClassSelector(Main.CS_CENTER_WIDGET);
        root.setCenterChild(centerWidget);

        return root;
    }

    public abstract String getTitle();

    protected abstract Widget getCenterWidget();

}
