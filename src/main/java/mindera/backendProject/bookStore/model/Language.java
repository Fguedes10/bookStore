package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;

@Entity
@Table
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
