/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages.advanced;

import java.util.Iterator;

import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.navigation.Page;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * A page of the advanced navigation demonstration.
 * <p>
 * Every such page displays the current navigation history above the buttons demonstrating the ways
 * of navigating from it, so that the effect of each way on the history is visible.
 */
public abstract class AdvancedPage extends BasePage {

    private static final String HISTORY_LABEL_TEXT = "Navigation History: ";
    private static final String HISTORY_SEPARATOR = " > ";
    private static final String BACK_BUTTON_TEXT = "Back";

    @Override
    protected Widget getCenterWidget() {
        List mainList = createContentCard();
        mainList.addChild(createTitleLabel(HISTORY_LABEL_TEXT));
        mainList.addChild(new VectorLabel(getHistoryText()));

        List buttonsList = new List(LayoutOrientation.HORIZONTAL);
        addButtons(buttonsList);
        mainList.addChild(buttonsList);

        return mainList;
    }

    /**
     * Adds the buttons demonstrating the navigation of this page to the given list.
     *
     * @param buttonsList
     *            the list holding the buttons of this page
     */
    protected abstract void addButtons(List buttonsList);

    /**
     * Adds a button navigating back to the previous page to the given list.
     *
     * @param buttonsList
     *            the list holding the buttons of this page
     */
    protected static void addBackButton(List buttonsList) {
        buttonsList.addChild(createButton(BACK_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateBack();
            }
        }));
    }

    /**
     * Returns the titles of the pages currently held by the navigation history, from the oldest to
     * the most recent one.
     *
     * @return the text describing the navigation history
     */
    private static String getHistoryText() {
        StringBuilder builder = new StringBuilder();

        Iterator<Page> iterator = Navigator.getInstance().getHistory().iterator();
        while (iterator.hasNext()) {
            Page nextPage = iterator.next();
            assert (nextPage instanceof BasePage);
            builder.append(((BasePage) nextPage).getTitle());
            if (iterator.hasNext()) {
                builder.append(HISTORY_SEPARATOR);
            }
        }

        return builder.toString();
    }

}
