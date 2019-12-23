package com.edu.micro.repository;

import com.edu.micro.model.UserCatalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCatalogRepository extends MongoRepository<UserCatalog, String> {

}
