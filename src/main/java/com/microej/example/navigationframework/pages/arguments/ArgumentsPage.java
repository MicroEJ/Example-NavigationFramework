/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.arguments;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorCheckBox;
import com.microej.example.navigationframework.widget.VectorRadioButton;
import com.microej.example.navigationframework.widget.RadioButtonGroup;
import com.microej.example.navigationframework.widget.VectorButton;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

public class ArgumentsPage extends BasePage {

    private static final String[] CHECK_BOX_TEXTS = { "Red", "Green", "Blue" };
    private static final String[] RADIO_BUTTON_TEXTS = { "Monday", "Tuesday", "Wednesday" };
    private static final int NO_CHECKED_RADIO_BUTTON = -1;

    private final VectorCheckBox[] checkBoxes;
    private final VectorRadioButton[] radioButtons;
    private final boolean[] checkBoxStates;
    private int checkedRadioButtonIndex;

    public ArgumentsPage() {
        this.checkBoxes = new VectorCheckBox[CHECK_BOX_TEXTS.length];
        this.radioButtons = new VectorRadioButton[RADIO_BUTTON_TEXTS.length];
        this.checkBoxStates = new boolean[CHECK_BOX_TEXTS.length];
        this.checkedRadioButtonIndex = NO_CHECKED_RADIO_BUTTON;
    }

    @Override
    public String getTitle() {
        return "Arguments Page";
    }

    @Override
    protected Widget getCenterWidget() {
        List mainList = new List(LayoutOrientation.VERTICAL);

        List checkBoxList = new List(LayoutOrientation.VERTICAL);
        VectorLabel checkBoxLabel = new VectorLabel("Check:");
        checkBoxLabel.addClassSelector(Main.CS_TITLE);
        checkBoxList.addChild(checkBoxLabel);
        for (int i = 0; i < CHECK_BOX_TEXTS.length; i++) {
            VectorCheckBox checkBox = new VectorCheckBox(CHECK_BOX_TEXTS[i]);
            checkBox.setChecked(this.checkBoxStates[i]);
            this.checkBoxes[i] = checkBox;
            checkBoxList.addChild(checkBox);
        }

        List radioButtonList = new List(LayoutOrientation.VERTICAL);
        VectorLabel radioLabel = new VectorLabel("Select:");
        radioLabel.addClassSelector(Main.CS_TITLE);
        radioButtonList.addChild(radioLabel);
        RadioButtonGroup group = new RadioButtonGroup();
        for (int i = 0; i < RADIO_BUTTON_TEXTS.length; i++) {
            VectorRadioButton radioButton = new VectorRadioButton(RADIO_BUTTON_TEXTS[i], group);
            if (i == this.checkedRadioButtonIndex) {
                radioButton.check();
            }
            this.radioButtons[i] = radioButton;
            radioButtonList.addChild(radioButton);
        }

        mainList.addChild(checkBoxList);
        mainList.addChild(radioButtonList);
        VectorButton button = new VectorButton("Validate");
        button.addClassSelector(Main.CS_BUTTON);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                // The factory builds the result page before this page is exited, so capture the
                // selection now and carry it as the argument of the navigation.
                captureSelection();
                Navigator.getInstance().navigateTo(Pages.ARGUMENTS_RESULT, createSelection());
            }
        });
        mainList.addChild(button);

        return mainList;
    }

    @Override
    protected void onExited() {
        captureSelection();
    }

    private void captureSelection() {
        VectorCheckBox[] checkBoxes = this.checkBoxes;
        for (int i = 0; i < checkBoxes.length; i++) {
            this.checkBoxStates[i] = checkBoxes[i].isChecked();
        }

        VectorRadioButton[] radioButtons = this.radioButtons;
        this.checkedRadioButtonIndex = NO_CHECKED_RADIO_BUTTON;
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].isChecked()) {
                this.checkedRadioButtonIndex = i;
            }
        }
    }

    private ArgumentsSelection createSelection() {
        boolean[] checkBoxStates = this.checkBoxStates;
        int checkedCount = 0;
        for (int i = 0; i < checkBoxStates.length; i++) {
            if (checkBoxStates[i]) {
                checkedCount++;
            }
        }

        String[] checkedChoices = new String[checkedCount];
        int checkedIndex = 0;
        for (int i = 0; i < checkBoxStates.length; i++) {
            if (checkBoxStates[i]) {
                checkedChoices[checkedIndex] = CHECK_BOX_TEXTS[i];
                checkedIndex++;
            }
        }

        int radioButtonIndex = this.checkedRadioButtonIndex;
        String selectedChoice = (radioButtonIndex == NO_CHECKED_RADIO_BUTTON) ? null
                : RADIO_BUTTON_TEXTS[radioButtonIndex];

        return new ArgumentsSelection(checkedChoices, selectedChoice);
    }

}
