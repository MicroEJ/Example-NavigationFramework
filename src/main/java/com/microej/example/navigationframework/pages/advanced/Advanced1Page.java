/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.advanced;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorButton;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

import java.util.Vector;

public class Advanced1Page extends BasePage {

    @Override
    public String getTitle() {
        return "Page 1";
    }

    @Override
    protected Widget getCenterWidget() {
        List mainList = new List(LayoutOrientation.VERTICAL);

        VectorLabel historyLabel = new VectorLabel("Navigation History: ");
        historyLabel.addClassSelector(Main.CS_TITLE);
        mainList.addChild(historyLabel);

        VectorLabel history = new VectorLabel(AdvancedHelper.getHistoryString());
        mainList.addChild(history);

        VectorButton navigateTo = new VectorButton("navigateTo Page 2");
        navigateTo.addClassSelector(Main.CS_BUTTON);
        navigateTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.ADVANCED_2_PAGE);
            }
        });

        mainList.addChild(navigateTo);

        return mainList;
    }

}
