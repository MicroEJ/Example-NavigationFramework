/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.annotation.Nullable;

public class RadioButtonGroup {

    private @Nullable VectorRadioButton checkedRadioButton;

    public boolean isChecked(VectorRadioButton radioButton) {
        return (radioButton == this.checkedRadioButton);
    }

    public void setChecked(VectorRadioButton radioButton) {
        VectorRadioButton oldCheckedRadioButton = this.checkedRadioButton;
        this.checkedRadioButton = radioButton;

        if (oldCheckedRadioButton != null) {
            oldCheckedRadioButton.requestRender();
        }

        radioButton.requestRender();
    }

}
