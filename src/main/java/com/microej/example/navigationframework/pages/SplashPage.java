/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software

Build: 7E4D1F7C
*/
package com.microej.example.navigationframework.pages;

import com.microej.example.navigationframework.Main;
import com.microej.example.navigationframework.navigation.Pages;
import com.microej.example.navigationframework.widget.VectorImageWidget;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.mwt.Widget;
import ej.navigation.Navigator;
import ej.navigation.Page;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

public class SplashPage extends Page {

    private static final int SPLASH_DURATION = 2_000;

    @Override
    protected Widget getContent() {

        SimpleDock root = new SimpleDock(LayoutOrientation.VERTICAL);
        root.addClassSelector(Main.CS_ROOT);
        root.setCenterChild(new VectorImageWidget("/images/mascot.svg"));

        return root;
    }

    @Override
    protected void onEntered() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                MicroUI.callSerially(new Runnable() {
                    @Override
                    public void run() {
                        Navigator.getInstance().replaceWith(Pages.HOME);
                    }
                });
            }
        };

        timer.schedule(timerTask, SPLASH_DURATION);
    }

}
