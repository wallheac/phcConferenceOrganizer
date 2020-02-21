package com.jph.organizer.repository;

import com.jph.organizer.domain.DateDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<DateDomain, Integer> {

        List<DateDomain> findAll();
    }

