package com.example.JournalApp.Controller;

import com.example.JournalApp.Entity.Journal;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> FetchALl = user.getJournalEntries();
        if (FetchALl != null && !FetchALl.isEmpty()){
            return new ResponseEntity<>(FetchALl, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("journalId/{myId}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getJournalId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<Journal> journalEntry = journalEntryService.fetchJournalById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("journalId/{journalId}")
    public ResponseEntity<Journal> updateJournalById(@PathVariable ObjectId journalId, @RequestBody Journal newEntries){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getJournalId().equals(journalId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<Journal> journalEntry = journalEntryService.fetchJournalById(journalId);
            if (journalEntry.isPresent()){
                Journal old = journalEntry.get();
                old.setTitle(newEntries.getTitle() != null && !newEntries.getTitle().equals("") ? newEntries.getTitle() : old.getTitle());
                old.setContent(newEntries.getContent() != null && !newEntries.getContent().equals("") ? newEntries.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("journalId/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
