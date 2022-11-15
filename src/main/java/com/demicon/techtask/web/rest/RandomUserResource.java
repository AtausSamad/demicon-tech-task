package com.demicon.techtask.web.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demicon.techtask.config.ApplicationProperties;
import com.demicon.techtask.repository.RandomUserRepository.CustomCountry;
import com.demicon.techtask.service.RandomUserService;
import com.demicon.techtask.utils.Utils;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/api")
public class RandomUserResource {
    
    private final RandomUserService randomUserService;
    private final ApplicationProperties applicationProperties;

    public RandomUserResource(RandomUserService randomUserService, ApplicationProperties applicationProperties) {
        this.randomUserService = randomUserService;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/fetch-and-persis-random-users")
    public ResponseEntity<Void> fetchAndPersistRandomUsers() {
        this.randomUserService.persistRandomUsers(this.randomUserService.fetchRandomUsers());
        return ResponseEntity.ok().headers(Utils.createEntityAlert("RandomUser", this.applicationProperties.getUserSize())).build();
    }

    // @GetMapping("/get-random-users-by-nat-group-by-country/{nat}")
    // public Map<String, List<RandomUserDTO>> getRandomUsersByCountry(@PathVariable String nat) {
    //     return this.randomUserService.getRandomUsersByNatGroupByCountry(nat);
    // }

    @GetMapping("/get-all-random-users-group-by-country/{nat}")
    public JSONObject getRandomUsersByCountry(@PathVariable String nat) {
        return this.randomUserService.populateExpectedJson(this.randomUserService.getRandomUsersByNatGroupByCountry(nat));
    }

    @GetMapping("/get-all-random-users-group-by-country")
    public JSONObject getAllRandomUsers() {
        return this.randomUserService.populateExpectedJson(this.randomUserService.getRandomUsersByNatGroupByCountry(null));
    }

    @GetMapping("/get-all-countries")
    public List<CustomCountry> getAllCountries() {
        return this.randomUserService.getAllCountries();
    }
}
