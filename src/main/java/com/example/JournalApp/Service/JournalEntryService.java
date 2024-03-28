package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.Journal;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(Journal journal, String userName){
        try {
            User user = userService.findByUserName(userName);
            journal.setDate(LocalDateTime.now());
            Journal saved =  journalEntryRepository.save(journal);
            user.getJournalEntries().add(saved);
//            user.setUserName(null);
            userService.saveUser(user);
        } catch (Exception e){
//            System.out.println(e);
            throw new RuntimeException("An error occur while saving the entry.", e);
        }
    }

    public void saveEntry(Journal journal){
        journalEntryRepository.save(journal);
    }

    public List<Journal> fetchAllJournal(){
        return journalEntryRepository.findAll();
    }

    public Optional<Journal> fetchJournalById(ObjectId journalId){
        return journalEntryRepository.findById(journalId);
    }

    @Transactional
    public boolean deleteById(ObjectId journalId, String userName){
        boolean removed = false;
        try{
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getJournalId().equals(journalId));
            if (removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(journalId);
            }
        } catch (Exception e){
            log.error("Error ",e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }

}
