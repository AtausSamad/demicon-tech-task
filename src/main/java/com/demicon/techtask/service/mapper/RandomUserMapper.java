package com.demicon.techtask.service.mapper;

import org.mapstruct.Mapper;

import com.demicon.techtask.domain.RandomUser;
import com.demicon.techtask.service.dto.RandomUserDTO;

@Mapper(componentModel = "spring", uses = {})
public interface RandomUserMapper extends EntityMapper<RandomUserDTO, RandomUser> {
    
    RandomUserDTO toDto(RandomUser randomUser);
    
    // RandomUser toEntity(RandomUserDTO randomUserDTO);

    // default RandomUser fromId(Long id) {
    //     if (id == null) {
    //         return null;
    //     }
    //     RandomUser randomUser = new RandomUser();
    //     randomUser.setId(id);
    //     return randomUser;
    // }
}
