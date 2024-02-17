package com.hb.model.repositories;


import com.hb.model.documents.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo, Long> {
    List<Todo> findByAccountId(Long accountId);
}
