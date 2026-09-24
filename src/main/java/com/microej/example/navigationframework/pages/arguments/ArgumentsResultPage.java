/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages.arguments;

import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.motion.cubic.CubicEaseOutFunction;
import ej.mwt.Widget;
import ej.navigation.transition.SlideTransition;
import ej.navigation.transition.Transition;
import ej.widget.container.List;

/**
 * The page displaying the selection the {@link ArgumentsPage} carried as the argument of the
 * navigation.
 */
public class ArgumentsResultPage extends BasePage {

    private static final String CHECKED_CHOICES_TEXT = "Checked:";
    private static final String SELECTED_CHOICE_TEXT = "Selected:";
    private static final String NOTHING_SELECTED_TEXT = "Nothing selected";

    private static final int SLIDE_DURATION = 400;

    private final ArgumentsSelection selection;

    /**
     * Creates a page displaying the given selection.
     *
     * @param selection
     *            the selection to display
     */
    public ArgumentsResultPage(ArgumentsSelection selection) {
        this.selection = selection;
    }

    @Override
    public String getTitle() {
        return "Result";
    }

    @Override
    protected Transition getExitTransition() {
        return createSlideTransition();
    }

    @Override
    protected String getDescription() {
        return "The selection carried here as the argument of the navigation";
    }

    @Override
    protected Widget getCenterWidget() {
        List centerList = createContentCard();

        String[] checkedChoices = this.selection.getCheckedChoices();
        String selectedChoice = this.selection.getSelectedChoice();
        if (checkedChoices.length == 0 && selectedChoice == null) {
            centerList.addChild(new VectorLabel(NOTHING_SELECTED_TEXT));
            return centerList;
        }

        if (checkedChoices.length != 0) {
            centerList.addChild(createTitleLabel(CHECKED_CHOICES_TEXT));
            for (int i = 0; i < checkedChoices.length; i++) {
                centerList.addChild(new VectorLabel(checkedChoices[i]));
            }
        }

        if (selectedChoice != null) {
            centerList.addChild(createTitleLabel(SELECTED_CHOICE_TEXT));
            centerList.addChild(new VectorLabel(selectedChoice));
        }

        return centerList;
    }

    /**
     * Creates the transition sliding this page in from the bottom edge of the content area.
     * <p>
     * The direction is mirrored on a back navigation, so this page slides back down when it is
     * left.
     *
     * @return the transition bringing this page in
     */
    /* package */ static Transition createSlideTransition() {
        return new SlideTransition(SlideTransition.BOTTOM_TO_TOP, SLIDE_DURATION,
                CubicEaseOutFunction.INSTANCE);
    }

}
