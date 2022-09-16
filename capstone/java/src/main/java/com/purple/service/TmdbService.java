//package com.purple.service;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.purple.model.tmdb.TmdbMovie;
//import org.apache.cxf.jaxrs.client.WebClient;
//import org.springframework.boot.json.JsonParser;
//import org.springframework.boot.json.JsonParserFactory;
//import org.springframework.stereotype.Service;
//
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.Map;
//
//@Service
//public class TmdbService implements ExternalMovieService {
//    @Override
//    public void getMovieFromExternal(Movie movie){
//        try {
//            Response response = WebClient.create(getMovieAddress(movie)).accept(MediaType.APPLICATION_JSON).get();
//            String json = response.readEntity(String.class);
//            mapToMovie(movie,json);
//        } catch (Exception ex) {
//            //should be logging the failure here
//            ex.printStackTrace();
//        }
//
//    }
//    private String getMovieAddress(Movie movie) {
//        return "https://api.themoviedb.org/3/movie/" + movie.getId() + "?api_key=ccd39c65b293fd5dd2a82e39b4ee7404&language=en-US";
//    }
//
//    private void mapToMovie(Movie movie, String json){
//        JsonParser springParser = JsonParserFactory.getJsonParser();
//        Map<String,Object> map = springParser.parseMap(json);
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        TmdbMovie tmdbMovie = mapper.convertValue(map, TmdbMovie.class);
//        movie.setBudget(tmdbMovie.getBudget());
//        movie.setRevenue(tmdbMovie.getRevenue());
//        movie.setVoteCount(tmdbMovie.getVoteCount());
//        movie.setVoteAverage(tmdbMovie.getVoteAverage());
//        movie.setPopularity(tmdbMovie.getPopularity());
//    }
//
//
//}
