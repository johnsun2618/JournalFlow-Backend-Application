package com.example.JournalApp.Repository;

import com.example.JournalApp.Entity.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<Journal, Long> {
}
