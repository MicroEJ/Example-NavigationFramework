/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.pages.transitions.CustomTransition;
import com.microej.example.navigationframework.widget.NavigationRow;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.annotation.Nullable;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.navigation.transition.FadeTransition;
import ej.navigation.transition.SlideTransition;
import ej.navigation.transition.Transition;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * The page listing the capabilities of the Navigation Framework, grouped in sections.
 */
public class HomePage extends BasePage {

    private static final String IMMEDIATE_ICON_PATH = "/images/immediate.svg";
    private static final String SLIDE_ICON_PATH = "/images/slide.svg";
    private static final String FADE_ICON_PATH = "/images/fade.svg";
    private static final String CUSTOM_ICON_PATH = "/images/custom.svg";
    private static final String ARGUMENTS_ICON_PATH = "/images/arguments.svg";
    private static final String ADVANCED_ICON_PATH = "/images/advanced.svg";

    @Override
    public String getTitle() {
        return "Home Page";
    }

    @Override
    @Nullable
    protected String getEyebrow() {
        return "FRAMEWORK";
    }

    @Override
    protected String getHeaderTitle() {
        return "Navigation capabilities";
    }

    @Override
    protected String getDescription() {
        return "Six live demos of page transitions and routing";
    }

    @Override
    protected boolean showsBackControl() {
        return false;
    }

    @Override
    protected Widget getCenterWidget() {
        List sections = new List(LayoutOrientation.VERTICAL);

        sections.addChild(createSectionLabel("TRANSITIONS"));
        sections.addChild(createTransitionsCard());
        sections.addChild(createSectionLabel("ROUTING"));
        sections.addChild(createRoutingCard());

        return sections;
    }

    private static Widget createTransitionsCard() {
        List card = createCard();

        addRow(card, IMMEDIATE_ICON_PATH, Main.CS_ROW_ICON_ACCENT, "Immediate",
                "No animation, instant swap", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.IMMEDIATE, Transition.IMMEDIATE);
                    }
                });

        addRow(card, SLIDE_ICON_PATH, Main.CS_ROW_ICON_ACCENT, "Slide",
                "Slides in over this page, mirrored on back", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.SLIDE, new SlideTransition());
                    }
                });

        addRow(card, FADE_ICON_PATH, Main.CS_ROW_ICON_ACCENT, "Fade",
                "Fades in over this page", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.FADE, new FadeTransition());
                    }
                });

        addRow(card, CUSTOM_ICON_PATH, Main.CS_ROW_ICON_ACCENT, "Custom",
                "Band reveal written for this demo", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.CUSTOM, new CustomTransition());
                    }
                });

        showDividers(card);

        return card;
    }

    private static Widget createRoutingCard() {
        List card = createCard();

        addRow(card, ARGUMENTS_ICON_PATH, Main.CS_ROW_ICON_NEUTRAL, "Passing arguments",
                "Send data between pages", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.ARGUMENTS, new SlideTransition());
                    }
                });

        addRow(card, ADVANCED_ICON_PATH, Main.CS_ROW_ICON_NEUTRAL, "Advanced navigation",
                "Chained pages and history control", new OnClickListener() {
                    @Override
                    public void onClick() {
                        Navigator.getInstance().navigateTo(Pages.ADVANCED_1_PAGE, new SlideTransition());
                    }
                });

        showDividers(card);

        return card;
    }

    private static List createCard() {
        List card = new List(LayoutOrientation.VERTICAL);
        card.addClassSelector(Main.CS_CARD);

        return card;
    }

    /**
     * Adds a row navigating to the page it presents to the given card.
     *
     * @param card
     *            the card holding the rows
     * @param iconPath
     *            the path of the icon of the row
     * @param iconClassSelector
     *            the class selector styling the icon tile of the row
     * @param title
     *            the title of the row
     * @param subtitle
     *            the subtitle of the row
     * @param listener
     *            the listener navigating to the page the row presents
     */
    private static void addRow(List card, String iconPath, int iconClassSelector, String title,
            String subtitle, OnClickListener listener) {
        NavigationRow row = new NavigationRow(iconPath, iconClassSelector, title, subtitle);
        row.setOnClickListener(listener);
        card.addChild(row);
    }

    /**
     * Displays a divider under every row of the given card but the last one.
     *
     * @param card
     *            the card holding the rows
     */
    private static void showDividers(List card) {
        int lastIndex = card.getChildrenCount() - 1;
        for (int i = 0; i < lastIndex; i++) {
            ((NavigationRow) card.getChild(i)).showDivider();
        }
    }

    private static VectorLabel createSectionLabel(String text) {
        VectorLabel label = new VectorLabel(text);
        label.addClassSelector(Main.CS_SECTION_LABEL);

        return label;
    }

}
