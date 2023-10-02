package com.app.security.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROLE_TBL")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /*@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users;*/

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"),
            inverseJoinColumns =@JoinColumn(name = "privilege_id", referencedColumnName = "privilegeId"
            )
    )
    private Collection<Privilege> privileges = new ArrayList<>();

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPrivileges()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority( this.name));
        return authorities;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
