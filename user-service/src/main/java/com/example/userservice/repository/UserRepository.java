package com.example.userservice.repository;

import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @RestResource(path = "find-username")
    public User findByUsername(@Param("name") String username);

    @Query("select u from User u where u.username = ?1")
    public User getByUsername(String username);
}
