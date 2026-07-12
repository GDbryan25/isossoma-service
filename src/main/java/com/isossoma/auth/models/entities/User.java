package com.isossoma.auth.models.entities;

import com.isossoma.auth.models.valueobjects.UserInformation;
import com.isossoma.shared.model.entities.Auditable;
import com.isossoma.shared.model.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "isossoma_user")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void update(
            String username,
            String email,
            String firstname,
            String lastname,
            Set<Role> roles
    ) {
        if (this.status == RecordStatus.INACTIVE) {
            throw new IllegalStateException("Inactive users cannot be modified.");
        }

        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role.");
        }

        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;

        this.roles.clear();
        this.roles.addAll(roles);
    }

    public User(UserInformation user, Set<Role> roles) {
        this.username = user.username();
        this.email = user.email();
        this.firstname = user.firstName();
        this.lastname = user.lastName();
        this.password = user.password();
        this.status = RecordStatus.ACTIVE;
        this.roles = (roles != null) ? new HashSet<>(roles) : new HashSet<>();
    }

    public User(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.status = RecordStatus.ACTIVE;
    }

    public void deactivate() {
        if (this.status == RecordStatus.INACTIVE) {
            return;
        }

        this.status = RecordStatus.INACTIVE;
        this.roles.clear();
    }

    public void reactivate() {
        if (this.status == RecordStatus.ACTIVE) {
            return;
        }

        this.status = RecordStatus.ACTIVE;
    }
}