package com.kartik.journalapp.service;

import com.kartik.journalapp.entity.User;
import com.kartik.journalapp.entity.JournalEntry;
import com.kartik.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component public class JournalEntryService {


    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveEntry(JournalEntry JournalEntry) {
        journalEntryRepository.save(JournalEntry);

    }
        public List<JournalEntry> getAll(){
            return journalEntryRepository.findAll();
        }

    public void deleteEntryById(ObjectId myId, String username) {
        User user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(journalentry -> journalentry.getId().equals(myId));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(myId);

    }

    public Optional<JournalEntry> findById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }
}
