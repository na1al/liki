package ua.catalog.liki.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@JsonIgnoreProperties(value = {"createdAt","updatedAt"})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Media extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String url;

}
