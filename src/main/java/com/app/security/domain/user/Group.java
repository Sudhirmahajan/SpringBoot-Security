package com.app.security.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "GROUP_TBL")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_ROLE_MAPPING",
            joinColumns = @JoinColumn(name = "GROUP_ID",
                    referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "ROLE_ID", referencedColumnName = "roleId"))
    private Collection<Role> roles = new ArrayList<>();
}
