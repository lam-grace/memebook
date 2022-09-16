package com.purple.model;

public class Tag {
    private Long tagId;
    private String tagName;
    private boolean active;

    public Tag() {
    }

    public Tag(Long tagId, String tagName, boolean active) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.active = active;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
