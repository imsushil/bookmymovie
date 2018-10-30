package com.movie.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.movie.booking.entity.ScreenEntity;

public interface ScreenRepository extends MongoRepository<ScreenEntity, String> {
	public ScreenEntity findByName(String screenName);
}
