package com.matix.carrent.repositories;

import com.matix.carrent.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
