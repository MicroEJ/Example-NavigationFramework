/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.widget;

import com.microej.example.navigationframework.Main;
import ej.annotation.Nullable;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * The card introducing a page: an optional eyebrow, a title and a description.
 * <p>
 * The description is laid out on as many lines as it needs.
 */
public class HeaderCard extends List {

    /**
     * Creates a header card.
     *
     * @param eyebrow
     *            the short text displayed above the title, {@code null} to display none
     * @param title
     *            the title of the card
     * @param description
     *            the text displayed below the title
     */
    public HeaderCard(@Nullable String eyebrow, String title, String description) {
        super(LayoutOrientation.VERTICAL);

        addClassSelector(Main.CS_HEADER_CARD);

        if (eyebrow != null) {
            VectorLabel eyebrowLabel = new VectorLabel(eyebrow);
            eyebrowLabel.addClassSelector(Main.CS_HEADER_EYEBROW);
            addChild(eyebrowLabel);
        }

        VectorLabel titleLabel = new VectorLabel(title);
        titleLabel.addClassSelector(Main.CS_HEADER_TITLE);
        addChild(titleLabel);

        VectorLabel descriptionLabel = new VectorLabel(description);
        descriptionLabel.addClassSelector(Main.CS_HEADER_DESCRIPTION);
        addChild(descriptionLabel);
    }

}
