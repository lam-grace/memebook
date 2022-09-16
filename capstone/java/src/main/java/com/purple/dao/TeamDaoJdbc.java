package com.purple.dao;


import com.purple.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDaoJdbc implements TeamDao {
    public final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TeamDaoJdbc(DataSource datasource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
    }

    @Override
    public Team getTeam(Long id){
        String sql = "select t.* from team t where t.team_id = :id;";
        Map<String,Object> params = new HashMap<>();
        params.put("team_id",id);
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        if (results.next()) {
            return mapRowToTeam(results);
        }
        return null;
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        String sql = """
                select
                        t.*
                    from
                    team t;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        List<Team> team = new ArrayList<>();
        while (results.next()){
            teams.add(mapRowToTeam(results));
        }
        return teams;
    }

    @Override
    public List<Team> getAllMembersOfTeam() {
        List<Team> teams = new ArrayList<>();
        String sql = """
                select
                        p.username
                    from
                    team t
                    join team_member tm on t.team_id = tm.team_id
                    join person p on p.person_id = tm.person_id
                    where t.team_name ilike :team_name;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        List<Team> team = new ArrayList<>();
        while (results.next()){
            teams.add(mapRowToTeam(results));
        }
        return teams;
    }

    @Override
    public List<Team> getAllTeamsJoined() {
        List<Team> teams = new ArrayList<>();
        String sql = """
                select
                        t.team_name
                    from
                    team t
                    join team_member tm on t.team_id = tm.team_id
                    join person p on p.person_id = tm.person_id
                    where p.person_id = :person_id;""";
        Map<String,Object> params = new HashMap<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);
        List<Team> team = new ArrayList<>();
        while (results.next()){
            teams.add(mapRowToTeam(results));
        }
        return teams;
    }

    @Override
    public Team mapRowToTeam(SqlRowSet results) {
        Team team = new Team();
        team.setTeamId(results.getLong("team_id"));
        team.setTeamName(results.getString("team_name"));
        team.setActive(results.getBoolean("active"));
        return team;
    }
}
