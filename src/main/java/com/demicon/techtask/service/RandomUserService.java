package com.demicon.techtask.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demicon.techtask.config.ApplicationProperties;
import com.demicon.techtask.domain.RandomUser;
import com.demicon.techtask.repository.RandomUserRepository;
import com.demicon.techtask.repository.RandomUserRepository.CustomCountry;
import com.demicon.techtask.service.dto.RandomUserDTO;
import com.demicon.techtask.service.mapper.RandomUserMapper;
import com.demicon.techtask.utils.Utils;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service
@Transactional
public class RandomUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final RandomUserRepository randomUserRepository;
    private final RandomUserMapper randomUserMapper;
    private final ApplicationProperties applicationProperties;

    public RandomUserService(RestTemplate restTemplate, RandomUserRepository randomUserRepository,
            RandomUserMapper randomUserMapper, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.randomUserRepository = randomUserRepository;
        this.randomUserMapper = randomUserMapper;
        this.applicationProperties = applicationProperties;
        
        this.adjustApplicationPropertiesIfNeeded();
    }

    private void adjustApplicationPropertiesIfNeeded() {
        if(this.applicationProperties != null && this.applicationProperties.getUserSize() > 5000) {
            this.applicationProperties.setUserSize(5000);
        }
    }

    public void persistRandomUsers(List<RandomUser> randomUsers) {
        if (!Utils.isEmpty(randomUsers)) {
            this.randomUserRepository.saveAll(randomUsers);
        }
    }

    public List<RandomUser> fetchRandomUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            String url = this.applicationProperties.getUrl() + "/?results=" + this.applicationProperties.getUserSize();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
                    entity, String.class);

            if (response.getBody() != null && response.getStatusCode().value() == 200) {
                return convertToRandomUSer(
                        JsonPath.read(response.getBody(), "$.results..['gender', 'name', 'location', 'email', 'nat']"));
            } else {
                // In case of an unsuccessful synchronization attempt,
                //  the Connector should return data from the last successful synchronization ?
            }
        } catch (RestClientException | ClassCastException exception) {
            this.logger.error("Exception while fetching random users", exception);
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    private List<RandomUser> convertToRandomUSer(List<Object> result) throws ClassCastException {
        List<RandomUser> randomUsers = new ArrayList<>();
        for (Object item : result) {
            RandomUser randomUser = new RandomUser();

            Map<String, Object> overallMap = (Map<String, Object>) item;
            randomUser.setGender(overallMap.get("gender").toString());
            randomUser.setEmail(overallMap.get("email").toString());
            randomUser.setNat(overallMap.get("nat").toString());

            Map<String, String> nameMap = (Map<String, String>) overallMap.get("name");
            randomUser.setTitle(nameMap.get("title"));
            randomUser.setFirstName(nameMap.get("first"));
            randomUser.setLastName(nameMap.get("last"));

            Map<String, Object> locationMap = (Map<String, Object>) overallMap.get("location");
            randomUser.setCity(locationMap.get("city").toString());
            randomUser.setState(locationMap.get("state").toString());
            randomUser.setCountry(locationMap.get("country").toString());
            randomUser.setPostcode(locationMap.get("postcode").toString());

            Map<String, Object> streetMap = (Map<String, Object>) locationMap.get("street");
            randomUser.setStreetName(streetMap.get("name").toString());
            randomUser.setStreetNumber((int) streetMap.get("number"));

            Map<String, String> coordinatesMap = (Map<String, String>) locationMap.get("coordinates");
            randomUser.setLatitude(coordinatesMap.get("latitude"));
            randomUser.setLongitude(coordinatesMap.get("longitude"));

            Map<String, String> timezoneMap = (Map<String, String>) locationMap.get("timezone");
            randomUser.setTimezoneOffset(timezoneMap.get("offset"));
            randomUser.setTimezoneDescription(timezoneMap.get("description"));

            randomUsers.add(randomUser);
        }
        return randomUsers;
    }

    public Map<String, List<RandomUserDTO>> getRandomUsersByNatGroupByCountry(String optionalNat) {
        List<RandomUser> randomUsers;
        if (!Utils.isEmpty(optionalNat)) {
            randomUsers = this.randomUserRepository.findAllByNatIgnoreCase(optionalNat);
        } else {
            randomUsers = this.randomUserRepository.findAll();
        }
        return randomUsers.stream().map(randomUserMapper::toDto)
                .collect(Collectors.groupingBy(RandomUserDTO::getCountry));
    }

    public List<CustomCountry> getAllCountries() {
        return this.randomUserRepository.getAllCountries();
    }

    public JSONObject populateExpectedJson(Map<String, List<RandomUserDTO>> usersGroupByCountry) {
        JSONArray ja = new JSONArray();
        for (Map.Entry<String, List<RandomUserDTO>> entry : usersGroupByCountry.entrySet()) {
            JSONObject jo = new JSONObject();
            jo.put("name", entry.getKey());
            jo.put("users", entry.getValue());
            ja.add(jo);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("countries", ja);
        return mainObj;
    }

    @Scheduled(fixedDelayString = "${application.period}", timeUnit = TimeUnit.SECONDS)
    public void scheduleFetchAndPersistRandomUsers() {
        logger.info("Scheduled task started on current thread name : {}", Thread.currentThread().getName());
        this.persistRandomUsers(this.fetchRandomUsers());
    }
}
