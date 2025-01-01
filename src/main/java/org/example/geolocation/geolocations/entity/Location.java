package org.example.geolocation.geolocations.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created")
    private Instant created;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "modified_at")
    private Instant modifiedAt;

    @ColumnDefault("1")
    @Column(name = "is_public")
    private Boolean isPublic = true;

    @ColumnDefault("0")
    @Column(name = "deleted")
    private Boolean deleted = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "coordinate", columnDefinition = "geometry not null")
    private Point<G2D> coordinate;

    public Point<G2D> getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(Point<G2D> coordinate) {
        this.coordinate = coordinate;
    }

    @PrePersist
    public void onCreate() {
        this.created = Instant.now();
        this.modifiedAt = Instant.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = Instant.now();
    }
}