package com.purple.model;

public class Team {
    private Long teamId;
    private String teamName;
    private boolean active;

    public Team() {
    }

    public Team(Long teamId, String teamName, boolean active) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.active = active;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
