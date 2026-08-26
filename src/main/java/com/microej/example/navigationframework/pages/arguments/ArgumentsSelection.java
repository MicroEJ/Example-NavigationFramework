/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.arguments;

import ej.annotation.Nullable;

/**
 * What the user selected in the {@link ArgumentsPage}: the texts of the checked check boxes and the text of
 * the checked radio button.
 * <p>
 * This is the per-navigation argument the arguments page hands the navigator, which forwards it to the page
 * factory so it reaches the {@link ArgumentsResultPage} constructor.
 */
public class ArgumentsSelection {

    private final String[] checkedChoices;
    @Nullable
    private final String selectedChoice;

    public ArgumentsSelection(String[] checkedChoices, @Nullable String selectedChoice) {
        this.checkedChoices = checkedChoices.clone();
        this.selectedChoice = selectedChoice;
    }

    public String[] getCheckedChoices() {
        return this.checkedChoices.clone();
    }

    @Nullable
    public String getSelectedChoice() {
        return this.selectedChoice;
    }

}
