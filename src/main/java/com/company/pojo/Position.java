package com.company.pojo;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position {
    private int positionId;
    private String positionName;
    private String positionDescription;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id", nullable = false)
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    @Basic
    @Column(name = "position_name", unique = true, nullable = false, length = 20)
    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Basic
    @Column(name = "position_description", nullable = false, length = 30)
    public String getPositionDescription() {
        return positionDescription;
    }

    public void setPositionDescription(String positionDescription) {
        this.positionDescription = positionDescription;
    }

    public Position() {
    }

    public Position(String positionName, String positionDescription) {
        this.positionName = positionName;
        this.positionDescription = positionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position that = (Position) o;

        if (positionId != that.positionId) return false;
        if (positionName != null ? !positionName.equals(that.positionName) : that.positionName != null) return false;
        if (positionDescription != null ? !positionDescription.equals(that.positionDescription) : that.positionDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = positionId;
        result = 31 * result + (positionName != null ? positionName.hashCode() : 0);
        result = 31 * result + (positionDescription != null ? positionDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return positionId + ", " +
                positionName + ", " +
                positionDescription;
    }
}
