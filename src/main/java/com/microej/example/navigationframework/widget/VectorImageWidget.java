/*
Copyright 2026 MicroEJ Corp. All rights reserved.
Use of this source code is governed by a BSD-style license that can be found with this software
*/
package com.microej.example.navigationframework.widget;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microvg.ResourceVectorImage;
import ej.microvg.VectorGraphicsPainter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

public class VectorImageWidget extends Widget {

    private String imagePath;

    @Nullable
    private ResourceVectorImage image;

    public VectorImageWidget(String imagePath) {
        this.imagePath = imagePath;
    }

    public VectorImageWidget(String imagePath, boolean enabled) {
        super(enabled);
        this.imagePath = imagePath;
    }

    @Override
    protected void onAttached() {
        super.onAttached();

        loadImage();
    }

    @Override
    protected void onDetached() {
        super.onDetached();

        closeImage();
    }

    private void loadImage() {
        this.image = ResourceVectorImage.loadImage(this.imagePath);
    }

    private void closeImage() {
        ResourceVectorImage image = this.image;
        if (image != null) {
            image.close();
            this.image = null;
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        if (isAttached()) {
            closeImage();
            loadImage();
        }
    }


    @Override
    protected void computeContentOptimalSize(Size size) {
        ResourceVectorImage image = this.image;
        if (image != null) {
            double width = Math.ceil(image.getWidth());
            double height = Math.ceil(image.getHeight());
            size.setSize((int) width, (int) height);
        } else {
            size.setSize(0, 0);
        }

    }

    @Override
    protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
        ResourceVectorImage image = this.image;
        if (image != null) {
            Style style = getStyle();
            g.setColor(style.getColor());
            int imageX = Alignment.computeLeftX((int) image.getWidth(), 0 ,contentWidth, style.getHorizontalAlignment());
            int imageY = Alignment.computeTopY((int) image.getHeight(), 0, contentHeight, style.getVerticalAlignment());
            VectorGraphicsPainter.drawImage(g, image, imageX, imageY);
        }
    }
}
