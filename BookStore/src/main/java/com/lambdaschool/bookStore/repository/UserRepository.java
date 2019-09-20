package com.lambdaschool.bookStore.repository;

import com.lambdaschool.bookStore.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
