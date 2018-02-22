package com.company.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String surname;
    private String firstName;
    private String middleName;
    private String avatar;
    private long phone;
    private String login;
    private String password;
    private Date registerDate;
    private Group group;
    private Position position;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "surname", nullable = false, length = 20)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "firstName", nullable = false, length = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "middleName", nullable = false, length = 20)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "avatar", nullable = false, length = 50)
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 12)
    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "login", unique = true, nullable = false, length = 20, updatable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "registerDate", nullable = false)
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group", referencedColumnName = "id", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "position", referencedColumnName = "id", nullable = false)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User that = (User) o;
        if (id != that.id) return false;
        if (phone != that.phone) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null)
            return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null)
            return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registerDate != null ? !registerDate.equals(that.registerDate) : that.registerDate != null)
            return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Long.hashCode(id);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + Long.hashCode(phone);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registerDate != null ? registerDate.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    public String basicInfo() {
        return surname + " " +
                firstName + " " +
                middleName;
    }

    @Override
    public String toString() {
        return id + ", " +
                surname + ", " +
                firstName + ", " +
                middleName + ", " +
                avatar + ", " +
                phone + ", " +
                login + ", " +
                password + ", " +
                registerDate + ", " +
                group + ", " +
                position;
    }
}