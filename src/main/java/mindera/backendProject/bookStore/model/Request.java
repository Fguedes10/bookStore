package mindera.backendProject.bookStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "client_id", nullable = false)
//    private Client client;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Download> downloads;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    //Missing order status and download link??

}
