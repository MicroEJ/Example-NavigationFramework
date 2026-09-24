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
 * The third page of the advanced navigation demonstration, growing the chain of pages further.
 */
public class Advanced3Page extends AdvancedPage {

    private static final String NAVIGATE_TO_BUTTON_TEXT = "navigateTo Page 4";

    @Override
    public String getTitle() {
        return "Page 3";
    }

    @Override
    protected String getDescription() {
        return "navigateTo again to grow the chain of pages";
    }

    @Override
    protected void addButtons(List buttonsList) {
        addBackButton(buttonsList);
        buttonsList.addChild(createButton(NAVIGATE_TO_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateTo(Pages.ADVANCED_4_PAGE);
            }
        }));
    }

}
