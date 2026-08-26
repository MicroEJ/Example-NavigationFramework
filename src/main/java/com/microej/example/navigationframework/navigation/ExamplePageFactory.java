/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.navigation;

import com.microej.example.navigationframework.pages.advanced.Advanced1Page;
import com.microej.example.navigationframework.pages.advanced.Advanced2Page;
import com.microej.example.navigationframework.pages.advanced.Advanced3Page;
import com.microej.example.navigationframework.pages.advanced.Advanced4Page;
import com.microej.example.navigationframework.pages.arguments.ArgumentsPage;
import com.microej.example.navigationframework.pages.arguments.ArgumentsResultPage;
import com.microej.example.navigationframework.pages.arguments.ArgumentsSelection;
import com.microej.example.navigationframework.pages.transitions.CustomTransitionPage;
import com.microej.example.navigationframework.pages.transitions.FadeTransitionPage;
import com.microej.example.navigationframework.pages.HomePage;
import com.microej.example.navigationframework.pages.transitions.ImmediateTransitionPage;
import com.microej.example.navigationframework.pages.transitions.SlideTransitionPage;
import com.microej.example.navigationframework.pages.SplashPage;
import ej.annotation.Nullable;
import ej.navigation.Page;
import ej.navigation.PageFactory;

public class ExamplePageFactory implements PageFactory {

    @Override
    public Page create(int key, @Nullable Object argument) throws IllegalArgumentException {
        switch (key) {
            case Pages.SPLASH:
                return new SplashPage();
            case Pages.HOME:
                return new HomePage();
            case Pages.IMMEDIATE:
                return new ImmediateTransitionPage();
            case Pages.SLIDE:
                return new SlideTransitionPage();
            case Pages.FADE:
                return new FadeTransitionPage();
            case Pages.CUSTOM:
                return new CustomTransitionPage();
            case Pages.ARGUMENTS:
                return new ArgumentsPage();
            case Pages.ARGUMENTS_RESULT:
                // The arguments page carries the selection to display as the argument of the navigation.
                if (!(argument instanceof ArgumentsSelection)) {
                    throw new IllegalArgumentException("The arguments result page requires an arguments selection.");
                }
                return new ArgumentsResultPage((ArgumentsSelection) argument);
            case Pages.ADVANCED_1_PAGE:
                return new Advanced1Page();
            case Pages.ADVANCED_2_PAGE:
                return new Advanced2Page();
            case Pages.ADVANCED_3_PAGE:
                return new Advanced3Page();
            case Pages.ADVANCED_4_PAGE:
                return new Advanced4Page();
        }

        throw new IllegalArgumentException("Unknown key.");
    }

}
