/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.arguments;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.pages.BasePage;
import com.microej.example.navigationframework.widget.VectorLabel;
import ej.mwt.Widget;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

public class ArgumentsResultPage extends BasePage {

    private static final String CHECKED_CHOICES_TEXT = "Checked:";
    private static final String SELECTED_CHOICE_TEXT = "Selected:";
    private static final String NOTHING_SELECTED_TEXT = "Nothing selected";

    private final ArgumentsSelection selection;

    public ArgumentsResultPage(ArgumentsSelection selection) {
        this.selection = selection;
    }

    @Override
    public String getTitle() {
        return "Arguments Result Page";
    }

    @Override
    protected Widget getCenterWidget() {
        List centerList = new List(LayoutOrientation.VERTICAL);

        String[] checkedChoices = this.selection.getCheckedChoices();
        String selectedChoice = this.selection.getSelectedChoice();
        if (checkedChoices.length == 0 && selectedChoice == null) {
            centerList.addChild(new VectorLabel(NOTHING_SELECTED_TEXT));
            return centerList;
        }

        if (checkedChoices.length != 0) {
            VectorLabel checked = new VectorLabel(CHECKED_CHOICES_TEXT);
            checked.addClassSelector(Main.CS_TITLE);
            centerList.addChild(checked);
            for (int i = 0; i < checkedChoices.length; i++) {
                centerList.addChild(new VectorLabel(checkedChoices[i]));
            }
        }

        if (selectedChoice != null) {
            VectorLabel selected = new VectorLabel(SELECTED_CHOICE_TEXT);
            selected.addClassSelector(Main.CS_TITLE);
            centerList.addChild(selected);
            centerList.addChild(new VectorLabel(selectedChoice));
        }

        return centerList;
    }
}
