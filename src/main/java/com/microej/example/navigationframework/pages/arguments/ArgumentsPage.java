/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages.arguments;

import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.RadioButtonGroup;
import com.microej.example.navigationframework.widget.VectorCheckBox;
import com.microej.example.navigationframework.widget.VectorRadioButton;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * The page letting the user pick a few values and carry them to the {@link ArgumentsResultPage} as
 * the argument of the navigation.
 */
public class ArgumentsPage extends BasePage {

    private static final String CHECK_BOX_LABEL_TEXT = "Check:";
    private static final String RADIO_BUTTON_LABEL_TEXT = "Select:";
    private static final String VALIDATE_BUTTON_TEXT = "Validate";

    private static final String[] CHECK_BOX_TEXTS = { "Red", "Green", "Blue" };
    private static final String[] RADIO_BUTTON_TEXTS = { "Monday", "Tuesday", "Wednesday" };
    private static final int NO_CHECKED_RADIO_BUTTON = -1;

    private final VectorCheckBox[] checkBoxes;
    private final VectorRadioButton[] radioButtons;
    private final boolean[] checkBoxStates;
    private int checkedRadioButtonIndex;

    /**
     * Creates a page with no value selected.
     */
    public ArgumentsPage() {
        this.checkBoxes = new VectorCheckBox[CHECK_BOX_TEXTS.length];
        this.radioButtons = new VectorRadioButton[RADIO_BUTTON_TEXTS.length];
        this.checkBoxStates = new boolean[CHECK_BOX_TEXTS.length];
        this.checkedRadioButtonIndex = NO_CHECKED_RADIO_BUTTON;
    }

    @Override
    public String getTitle() {
        return "Passing arguments";
    }

    @Override
    protected String getDescription() {
        return "Pick a few values and carry them to the next page";
    }

    @Override
    protected Widget getCenterWidget() {
        List mainList = createContentCard();
        mainList.addChild(createCheckBoxList());
        mainList.addChild(createRadioButtonList());
        mainList.addChild(createButton(VALIDATE_BUTTON_TEXT, new OnClickListener() {
            @Override
            public void onClick() {
                // The factory builds the result page before this page is exited, so capture the
                // selection now and carry it as the argument of the navigation.
                captureSelection();
                Navigator.getInstance().navigateTo(Pages.ARGUMENTS_RESULT, createSelection(),
                        ArgumentsResultPage.createSlideTransition());
            }
        }));

        return mainList;
    }

    @Override
    protected void onExited() {
        captureSelection();
    }

    private Widget createCheckBoxList() {
        List checkBoxList = new List(LayoutOrientation.VERTICAL);
        checkBoxList.addChild(createTitleLabel(CHECK_BOX_LABEL_TEXT));

        VectorCheckBox[] checkBoxes = this.checkBoxes;
        boolean[] checkBoxStates = this.checkBoxStates;
        for (int i = 0; i < CHECK_BOX_TEXTS.length; i++) {
            VectorCheckBox checkBox = new VectorCheckBox(CHECK_BOX_TEXTS[i]);
            checkBox.setChecked(checkBoxStates[i]);
            checkBoxes[i] = checkBox;
            checkBoxList.addChild(checkBox);
        }

        return checkBoxList;
    }

    private Widget createRadioButtonList() {
        List radioButtonList = new List(LayoutOrientation.VERTICAL);
        radioButtonList.addChild(createTitleLabel(RADIO_BUTTON_LABEL_TEXT));

        VectorRadioButton[] radioButtons = this.radioButtons;
        int checkedIndex = this.checkedRadioButtonIndex;
        RadioButtonGroup group = new RadioButtonGroup();
        for (int i = 0; i < RADIO_BUTTON_TEXTS.length; i++) {
            VectorRadioButton radioButton = new VectorRadioButton(RADIO_BUTTON_TEXTS[i], group);
            if (i == checkedIndex) {
                radioButton.check();
            }
            radioButtons[i] = radioButton;
            radioButtonList.addChild(radioButton);
        }

        return radioButtonList;
    }

    /**
     * Stores what the user selected, so that it survives the destruction of the widgets when this
     * page is exited.
     */
    private void captureSelection() {
        VectorCheckBox[] checkBoxes = this.checkBoxes;
        boolean[] checkBoxStates = this.checkBoxStates;
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxStates[i] = checkBoxes[i].isChecked();
        }

        VectorRadioButton[] radioButtons = this.radioButtons;
        int checkedRadioButtonIndex = NO_CHECKED_RADIO_BUTTON;
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                checkedRadioButtonIndex = i;
            }
        }
        this.checkedRadioButtonIndex = checkedRadioButtonIndex;
    }

    private ArgumentsSelection createSelection() {
        boolean[] checkBoxStates = this.checkBoxStates;
        String[] allChoices = new String[checkBoxStates.length];
        int checkedCount = 0;
        for (int i = 0; i < checkBoxStates.length; i++) {
            if (checkBoxStates[i]) {
                allChoices[checkedCount] = CHECK_BOX_TEXTS[i];
                checkedCount++;
            }
        }

        String[] checkedChoices = new String[checkedCount];
        System.arraycopy(allChoices, 0, checkedChoices, 0, checkedCount);

        int radioButtonIndex = this.checkedRadioButtonIndex;
        String selectedChoice = (radioButtonIndex == NO_CHECKED_RADIO_BUTTON) ? null
                : RADIO_BUTTON_TEXTS[radioButtonIndex];

        return new ArgumentsSelection(checkedChoices, selectedChoice);
    }

}
