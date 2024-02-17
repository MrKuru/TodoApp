package com.hb.model.repositories;


import com.hb.model.documents.Account;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CouchbaseRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
