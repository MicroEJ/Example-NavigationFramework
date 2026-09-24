/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.navigation;

/**
 * The keys identifying the pages of the application, which {@link ExamplePageFactory} turns into
 * page instances.
 */
public class Pages {

    /** The key of the splash page. */
    public static final int SPLASH = 0;
    /** The key of the home page. */
    public static final int HOME = 1;
    /** The key of the page demonstrating the immediate transition. */
    public static final int IMMEDIATE = 2;
    /** The key of the page demonstrating the slide transition. */
    public static final int SLIDE = 3;
    /** The key of the page demonstrating the fade transition. */
    public static final int FADE = 4;
    /** The key of the page demonstrating a custom transition. */
    public static final int CUSTOM = 5;
    /** The key of the page passing a selection to the next page. */
    public static final int ARGUMENTS = 6;
    /** The key of the page displaying the selection it receives as an argument. */
    public static final int ARGUMENTS_RESULT = 7;
    /** The key of the first page of the advanced navigation demonstration. */
    public static final int ADVANCED_1_PAGE = 8;
    /** The key of the second page of the advanced navigation demonstration. */
    public static final int ADVANCED_2_PAGE = 9;
    /** The key of the third page of the advanced navigation demonstration. */
    public static final int ADVANCED_3_PAGE = 10;
    /** The key of the last page of the advanced navigation demonstration. */
    public static final int ADVANCED_4_PAGE = 11;

    private Pages() {
        // Prevents the instantiation of this class holding only constants.
    }

}
