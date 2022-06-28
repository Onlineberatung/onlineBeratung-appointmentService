package com.vi.appointmentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vi.appointmentservice.api.model.CalcomTeam;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CalComTeamService extends CalComService{

    @Autowired
    public CalComTeamService(RestTemplate restTemplate, @Value("${calcom.apiUrl}") String calcomApiUrl, @Value("${calcom.apiKey}") String calcomApiKey) {
        super(restTemplate, calcomApiUrl, calcomApiKey);
    }

    private String generateSlug(String name){
        name = name.toLowerCase();
        String regex = "[^\\w]+"; // Relace everyting but word characters (digits, numbers)
        String subst = "-";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.replaceAll(subst);
    }

    public List<CalcomTeam> getAllTeams() throws JsonProcessingException {
        String response = this.restTemplate.getForObject(String.format(this.buildUri("/v1/teams"), calcomApiUrl, calcomApiKey), String.class);
        JSONObject jsonObject = new JSONObject(response);
        response = jsonObject.getJSONArray("teams").toString();
        ObjectMapper mapper = new ObjectMapper();
        CalcomTeam[] result = mapper.readValue(response, CalcomTeam[].class);
        return List.of(Objects.requireNonNull(result));
    }

    public CalcomTeam getTeamById(Long teamId) throws JsonProcessingException {
        String response = restTemplate.getForObject(String.format(this.buildUri("/v1/teams/" + teamId), calcomApiUrl, calcomApiKey), String.class);
        JSONObject jsonObject = new JSONObject(response);
        response = jsonObject.getJSONObject("team").toString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, CalcomTeam.class);
    }

    public ResponseEntity<CalcomTeam> createTeam(CalcomTeam team){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", team.getName());
        map.add("slug", generateSlug(team.getName()));
        map.add("bio", team.getBio());
        map.add("logo", team.getLogo());
        map.add("hideBranding", String.valueOf(team.getHideBranding()));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        return restTemplate.postForEntity(this.buildUri("/v1/teams"), request , CalcomTeam.class );
    }

    public ResponseEntity<CalcomTeam> editTeam(CalcomTeam team){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        if(team.getName() != null){
            map.add("name", team.getName());
            map.add("slug", generateSlug(team.getName()));
        }
        if(team.getBio() != null){
            map.add("bio", team.getBio());
        }
        if(team.getLogo() != null){
            map.add("logo", team.getLogo());
        }
        if(team.getHideBranding() != null){
            map.add("hideBranding", String.valueOf(team.getHideBranding()));
        }
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        return restTemplate.postForEntity(this.buildUri("/v1/teams/"+team.getId()), request , CalcomTeam.class );
    }


}
