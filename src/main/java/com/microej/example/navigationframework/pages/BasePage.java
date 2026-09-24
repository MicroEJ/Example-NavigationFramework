/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.widget.HeaderCard;
import com.microej.example.navigationframework.widget.VectorButton;
import com.microej.example.navigationframework.widget.VectorImageButton;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.navigation.Page;
import ej.navigation.transition.SlideTransition;
import ej.navigation.transition.Transition;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;

/**
 * A page made of a top bar, a header card introducing the page and the content of the page.
 */
public abstract class BasePage extends Page {

    private static final String BACK_ICON_PATH = "/images/back.svg";
    private static final String HOME_ICON_PATH = "/images/home.svg";

    @Override
    protected Widget getContent() {
        SimpleDock root = new SimpleDock(LayoutOrientation.VERTICAL);
        root.addClassSelector(Main.CS_ROOT);

        List topContent = new List(LayoutOrientation.VERTICAL);
        topContent.addChild(createTopBar());
        topContent.addChild(createHeaderCard());

        Widget centerWidget = getCenterWidget();
        centerWidget.addClassSelector(Main.CS_CENTER_WIDGET);

        root.setFirstChild(topContent);
        root.setCenterChild(centerWidget);

        return root;
    }

    /**
     * Returns the title of this page, displayed in its top bar.
     *
     * @return the title of this page
     */
    public abstract String getTitle();

    /**
     * Returns the one line description of the capability this page demonstrates, displayed in its
     * header card.
     *
     * @return the description of this page
     */
    protected abstract String getDescription();

    /**
     * Returns the content this page displays below its header card.
     *
     * @return the content of this page
     */
    protected abstract Widget getCenterWidget();

    /**
     * Returns the short text displayed above the title of the header card.
     *
     * @return the eyebrow of the header card, {@code null} to display none
     */
    @Nullable
    protected String getEyebrow() {
        return null;
    }

    /**
     * Returns the title displayed in the header card, which is the title of the page unless a
     * subclass overrides it.
     *
     * @return the title of the header card
     */
    protected String getHeaderTitle() {
        return getTitle();
    }

    /**
     * Returns whether the top bar of this page displays a back control.
     *
     * @return {@code true} to display a back control, {@code false} otherwise
     */
    protected boolean showsBackControl() {
        return true;
    }

    /**
     * Returns the transition animating the navigations the top bar of this page requests.
     * <p>
     * A page returns the transition that brought it in, so that leaving it undoes visually what
     * entering it did.
     *
     * @return the transition of the back control and of the home control
     */
    protected Transition getExitTransition() {
        return new SlideTransition();
    }

    /**
     * Creates the card a page fills with its content.
     *
     * @return the card holding the content of a page
     */
    protected static List createContentCard() {
        List card = new List(LayoutOrientation.VERTICAL);
        card.addClassSelector(Main.CS_CONTENT_CARD);

        return card;
    }

    /**
     * Creates the label introducing a group of values displayed in a content card.
     *
     * @param text
     *            the text of the label
     * @return the label introducing the group of values
     */
    protected static VectorLabel createTitleLabel(String text) {
        VectorLabel label = new VectorLabel(text);
        label.addClassSelector(Main.CS_TITLE);

        return label;
    }

    /**
     * Creates a push button notifying the given listener when it is clicked.
     *
     * @param text
     *            the text of the button
     * @param listener
     *            the listener notified when the button is clicked
     * @return the push button
     */
    protected static VectorButton createButton(String text, OnClickListener listener) {
        VectorButton button = new VectorButton(text);
        button.addClassSelector(Main.CS_BUTTON);
        button.setOnClickListener(listener);

        return button;
    }

    private Widget createHeaderCard() {
        return new HeaderCard(getEyebrow(), getHeaderTitle(), getDescription());
    }

    private Widget createTopBar() {
        SimpleDock topBar = new SimpleDock(LayoutOrientation.HORIZONTAL);
        topBar.addClassSelector(Main.CS_TOP_BAR);

        final Transition exitTransition = getExitTransition();

        if (showsBackControl()) {
            VectorImageButton back = new VectorImageButton(BACK_ICON_PATH);
            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    Navigator.getInstance().navigateBack(exitTransition);
                }
            });
            topBar.setFirstChild(back);
        }

        VectorLabel title = new VectorLabel(getTitle());
        title.addClassSelector(Main.CS_TOP_BAR_TITLE);
        topBar.setCenterChild(title);

        VectorImageButton home = new VectorImageButton(HOME_ICON_PATH);
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                Navigator.getInstance().navigateBackTo(Pages.HOME, exitTransition);
            }
        });
        topBar.setLastChild(home);

        return topBar;
    }

}
