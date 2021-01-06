package ua.catalog.liki.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Region {

    @Id
    private long id;

    @Basic
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @Getter
    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now() not null", updatable = false)
    protected Date createdAt;

}
