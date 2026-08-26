/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.pages.advanced;

import com.microej.example.navigationframework.pages.BasePage;
import ej.navigation.Navigator;
import ej.navigation.Page;

import java.util.Iterator;
import java.util.List;

public class AdvancedHelper {

    private AdvancedHelper() {
    }

    static String getHistoryString() {
        StringBuilder builder = new StringBuilder();

        List<Page> history = Navigator.getInstance().getHistory();
        Iterator<Page> iterator = history.iterator();

        while (iterator.hasNext()) {
            Page nextPage = iterator.next();
            assert (nextPage instanceof BasePage);
            builder.append(((BasePage)nextPage).getTitle());
            if (iterator.hasNext()) {
                builder.append(" > ");
            }
        }

        return builder.toString();
    }

}
