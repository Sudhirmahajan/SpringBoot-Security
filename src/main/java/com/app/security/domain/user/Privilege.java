package com.app.security.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRIVILEGE_TBL")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privilegeId;
    private String permission;
    /*@ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    private Collection<Role> roles;*/

    @Override
    public String toString() {
        return "Privilege{" +
                "privilegeId=" + privilegeId +
                ", permission='" + permission + '\'' +
                '}';
    }
}
