package com.isossoma.auth.models.entities;

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
@Table(name = "role")
public class Role extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "status", nullable = false)
    private RecordStatus status;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name, String description, Set<Permission> permissions){
        this.name = name;
        this.description = description;
        this.status = RecordStatus.ACTIVE;
        this.permissions.addAll(permissions);
    }

    public void update(String name, String description, Set<Permission> permissions){
        if(status == RecordStatus.INACTIVE){
            throw new RuntimeException("Inactive roles cannot be modified.");
        }

        this.name = name;
        this.description = description;

        this.permissions.clear();
        this.permissions.addAll(permissions);
    }

    public void deactivate() {
        if (status == RecordStatus.INACTIVE) {
            throw new RuntimeException("Role is already inactive.");
        }

        this.status = RecordStatus.INACTIVE;
        this.permissions.clear();
        this.markAsDeleted("anonymousUser");
    }

    public void reactivate() {
        if (status == RecordStatus.ACTIVE) {
            throw new RuntimeException("Role is already active.");
        }

        this.status = RecordStatus.ACTIVE;
        this.unmarkDeleted();
    }
}