package com.example.hrcab.repos;

import com.example.hrcab.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Long>
{ Users findByUsername(String username);}