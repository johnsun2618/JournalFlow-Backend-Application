package com.example.JournalApp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor(force = true)
public class Journal {

    @Id
    private Long journalId;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

}
