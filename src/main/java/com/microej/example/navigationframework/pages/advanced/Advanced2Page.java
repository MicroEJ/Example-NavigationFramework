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
 * The second page of the advanced navigation demonstration, swapping the current page of the
 * history.
 */
public class Advanced2Page extends AdvancedPage {

    private static final String REPLACE_WITH_BUTTON_TEXT = "replaceWith Page 3";

    @Override
    public String getTitle() {
        return "Page 2";
    }

    @Override
    protected String getDescription() {
        return "replaceWith swaps the current page of the history";
    }

    @Override
    protected void addButtons(List buttonsList) {
        buttonsList.addChild(createButton(REPLACE_WITH_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().replaceWith(Pages.ADVANCED_3_PAGE);
            }
        }));
    }

}
