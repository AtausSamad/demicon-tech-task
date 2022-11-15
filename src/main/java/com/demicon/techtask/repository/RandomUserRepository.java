package com.demicon.techtask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demicon.techtask.domain.RandomUser;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface RandomUserRepository extends JpaRepository<RandomUser, Long> {
    List<RandomUser> findAllByNatIgnoreCase(String nat);

    public interface CustomCountry {
        String getCountry();
        String getNat();
    }
    @Query("Select user.country as country, user.nat as nat from RandomUser user")
    List<CustomCountry> getAllCountries();
}
