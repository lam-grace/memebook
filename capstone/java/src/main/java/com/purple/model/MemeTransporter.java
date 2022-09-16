package com.purple.model;

import java.util.List;

public class MemeTransporter {
//    {imageId: 2, height: 450, width: 800, textList: Array(3)}
//    height: 450
//    imageId: 2
//    textList:
//    Array(3)
//  0: {memeText: '', xPos: 0, yPos: 0}
//  1: {index: '0', memeText: 'hello', xPos: 569, yPos: 18}
//  2: {index: '1', memeText: 'there', xPos: 273, yPos: -21}

    private Long imageId;
    private Long height;
    private Long width;
    private Integer fontSize;
    private String color;
    private List<MemeText> textList;

    public MemeTransporter() {
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public List<MemeText> getTextList() {
        return textList;
    }

    public void setTextList(List<MemeText> textList) {
        this.textList = textList;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
