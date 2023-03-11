

package com.huststore.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.huststore.jpa.entities"})
@EnableJpaRepositories(basePackages = {"com.huststore.jpa.repositories"})
public class JpaConfig {
}
