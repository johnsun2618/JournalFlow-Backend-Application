package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.Journal;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
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
            user.setUserName(null);
            userService.saveEntry(user);
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occur while saving the entry", e);
        }
    }

    public void saveEntry(Journal journal){
        journalEntryRepository.save(journal);
    }

    public List<Journal> fetchAllJournal(){
        return journalEntryRepository.findAll();
    }

    public Optional<Journal> fetchJournalById(Long journalId){
        return journalEntryRepository.findById(journalId);
    }

    public void deleteById(Long journalId, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getJournalId().equals(journalId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(journalId);
    }

}
