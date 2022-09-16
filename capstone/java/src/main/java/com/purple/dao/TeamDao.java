package com.purple.dao;

import com.purple.model.Team;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TeamDao {
    Team getTeam(Long id);
    List<Team> getAllTeams();
    List<Team> getAllTeamsJoined();
    List<Team> getAllMembersOfTeam();
    Team mapRowToTeam(SqlRowSet results);
}
