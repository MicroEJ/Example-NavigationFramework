/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages.advanced;

import com.microej.example.navigationframework.navigation.Pages;
import ej.navigation.Navigator;
import ej.widget.basic.OnClickListener;
import ej.widget.container.List;

/**
 * The first page of the advanced navigation demonstration, pushing a new page on the history.
 */
public class Advanced1Page extends AdvancedPage {

    private static final String NAVIGATE_TO_BUTTON_TEXT = "navigateTo Page 2";

    @Override
    public String getTitle() {
        return "Page 1";
    }

    @Override
    protected String getDescription() {
        return "navigateTo pushes a new page on top of the history";
    }

    @Override
    protected void addButtons(List buttonsList) {
        buttonsList.addChild(createButton(NAVIGATE_TO_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.ADVANCED_2_PAGE);
            }
        }));
    }

}
