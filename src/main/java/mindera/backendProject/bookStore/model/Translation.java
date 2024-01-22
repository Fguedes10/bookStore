package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;

@Entity
@Table
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
