
package com.purple.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/*
{
  "budget": 94000000,
  "popularity": 232.874,
  "poster_path": "/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
  "revenue": 1118888979,
  "runtime": 201,
  "vote_average": 8.48,
  "vote_count": 20091
}
 */
public class TmdbMovie {
    private Long budget;
    private Double popularity;
    private Long runtime;
    private Long revenue;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Long voteCount;

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
