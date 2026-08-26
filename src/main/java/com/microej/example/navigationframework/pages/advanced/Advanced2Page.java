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

public class Advanced2Page extends BasePage {

    @Override
    public String getTitle() {
        return "Page 2";
    }

    @Override
    protected Widget getCenterWidget() {
        List mainList = new List(LayoutOrientation.VERTICAL);

        VectorLabel historyLabel = new VectorLabel("Navigation History: ");
        historyLabel.addClassSelector(Main.CS_TITLE);
        mainList.addChild(historyLabel);

        VectorLabel history = new VectorLabel(AdvancedHelper.getHistoryString());
        mainList.addChild(history);

        List buttonsList = new List(LayoutOrientation.HORIZONTAL);

        VectorButton replaceWith = new VectorButton("replaceWith Page 3");
        replaceWith.addClassSelector(Main.CS_BUTTON);
        replaceWith.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().replaceWith(Pages.ADVANCED_3_PAGE);
            }
        });

        buttonsList.addChild(replaceWith);

        mainList.addChild(buttonsList);

        return mainList;
    }

}
