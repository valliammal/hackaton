package com.hcl.hackaton.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.hackaton.model.Acct;

/**
 * The interface User repository.
 *
 * @author Denhaag team
 */
@Repository
public interface AcctRepository extends JpaRepository<Acct, Long> {}
