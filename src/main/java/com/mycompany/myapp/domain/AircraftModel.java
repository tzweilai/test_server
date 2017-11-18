package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AircraftModel.
 */
@Entity
@Table(name = "aircraft_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SQLDelete(sql = "UPDATE aircraft_model SET deleted = 1 WHERE id = ?")
public class AircraftModel extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "comment")
    private String comment;

    public String getName() {
        return name;
    }

    public AircraftModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public AircraftModel comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AircraftModel aircraftModel = (AircraftModel) o;
        if (aircraftModel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, aircraftModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AircraftModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
