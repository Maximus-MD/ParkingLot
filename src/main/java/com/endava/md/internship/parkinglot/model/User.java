package com.endava.md.internship.parkinglot.model;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Builder;
=======
>>>>>>> 02a431b (INTP0002PD-5633 Created RegistrationRequestDto and updated pom.xml)
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
<<<<<<< HEAD
@AllArgsConstructor
@Builder
=======
>>>>>>> 02a431b (INTP0002PD-5633 Created RegistrationRequestDto and updated pom.xml)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, unique = true)
=======
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(unique = true)
>>>>>>> 02a431b (INTP0002PD-5633 Created RegistrationRequestDto and updated pom.xml)
    private String phone;
}
