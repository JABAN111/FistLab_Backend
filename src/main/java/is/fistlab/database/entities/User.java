package is.fistlab.database.entities;

import is.fistlab.database.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@ToString
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;


    @Column(nullable = false)
    private String password;
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(nullable = false, unique = true)
//    private Person person;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);// && Objects.equals(person, user.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);//, person);
    }

}