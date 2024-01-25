package mindera.backendProject.bookStore.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Rating {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;
}
