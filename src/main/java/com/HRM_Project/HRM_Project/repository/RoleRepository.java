package com.HRM_Project.HRM_Project.repository;

import com.HRM_Project.HRM_Project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Find a role by its name
     * 
     * @param name The role name to search for
     * @return An Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role with the given name exists
     * 
     * @param name The role name to check
     * @return true if a role with the name exists, false otherwise
     */
    boolean existsByName(String name);
}
