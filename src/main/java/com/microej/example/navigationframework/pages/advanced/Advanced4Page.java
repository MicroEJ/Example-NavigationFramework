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
 * The last page of the advanced navigation demonstration, popping the history down to the first
 * page of the demonstration.
 */
public class Advanced4Page extends AdvancedPage {

    private static final String NAVIGATE_BACK_TO_BUTTON_TEXT = "navigateBackTo Page 1";

    @Override
    public String getTitle() {
        return "Page 4";
    }

    @Override
    protected String getDescription() {
        return "navigateBackTo pops the history down to a given page";
    }

    @Override
    protected void addButtons(List buttonsList) {
        addBackButton(buttonsList);
        buttonsList.addChild(createButton(NAVIGATE_BACK_TO_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateBackTo(Pages.ADVANCED_1_PAGE);
            }
        }));
    }

}
