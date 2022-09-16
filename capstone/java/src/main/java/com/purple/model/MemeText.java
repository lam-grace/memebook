package com.purple.model;

public class MemeText {
    private Long textId;
    private Long memeId;
    private String memeText;
    private Long xPos;
    private Long yPos;

//    2: {index: '1', memeText: 'there', xPos: 273, yPos: -21}

    public MemeText() {
    }

    public MemeText(Long textId, Long memeId, String memeText, String color, Integer fontSize, Long xPos, Long yPos) {
        this.textId = textId;
        this.memeId = memeId;
        this.memeText = memeText;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public Long getMemeId() {
        return memeId;
    }

    public void setMemeId(Long memeId) {
        this.memeId = memeId;
    }

    public String getMemeText() {
        return memeText;
    }

    public void setMemeText(String memeText) {
        this.memeText = memeText;
    }

    public Long getxPos() {
        return xPos;
    }

    public void setxPos(Long xPos) {
        this.xPos = xPos;
    }

    public Long getyPos() {
        return yPos;
    }

    public void setyPos(Long yPos) {
        this.yPos = yPos;
    }
}
