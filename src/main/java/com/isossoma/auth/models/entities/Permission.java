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
@Table(name = "permission")
public class Permission extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String description;

    @Column(unique = true, length = 100)
    private String code;

    @Column(name = "menu_key", length = 100)
    private String menuKey;

    @Column(name = "submenu_key", length = 100)
    private String submenuKey;

    @Column(name = "action_key", length = 100)
    private String actionKey;

    @Column(length = 255)
    private String route;

    @Column(name = "status", nullable = false)
    private RecordStatus status = RecordStatus.ACTIVE;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}