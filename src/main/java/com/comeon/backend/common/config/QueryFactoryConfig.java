package com.comeon.backend.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QueryFactoryConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager jpaEntityManager) {
        return new JPAQueryFactory(jpaEntityManager);
    }
}
