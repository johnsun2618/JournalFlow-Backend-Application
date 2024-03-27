package com.example.JournalApp.Controller;

import com.example.JournalApp.Entity.Journal;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<Journal> FetchALl = user.getJournalEntries();
        if (FetchALl != null && !FetchALl.isEmpty()){
            return new ResponseEntity<>(FetchALl, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{userName}")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry, @PathVariable String userName){
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("journalId/{myId}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long myId){
        Optional<Journal> journal = journalEntryService.fetchJournalById(myId);
        if (journal.isPresent()){
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("journalId/{journalId}")
    public ResponseEntity<Journal> updateJournalById(
            @PathVariable Long journalId,
            @RequestBody Journal newEntries,
            @PathVariable String userName){
        Journal oldEntries = journalEntryService.fetchJournalById(journalId).orElse(null);
        if (oldEntries != null){
            oldEntries.setTitle(newEntries.getTitle() != null && !newEntries.getTitle().equals("") ? newEntries.getTitle() : oldEntries.getTitle());
            oldEntries.setContent(newEntries.getContent() != null && !newEntries.getContent().equals("") ? newEntries.getContent() : oldEntries.getContent());
            journalEntryService.saveEntry(oldEntries);
            return new ResponseEntity<>(oldEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("journalId/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable Long myId, @PathVariable String userName){
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
