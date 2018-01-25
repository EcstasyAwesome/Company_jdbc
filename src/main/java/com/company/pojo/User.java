package com.company.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    private int userId;
    private String userSurname;
    private String userFirstName;
    private String userSecondName;
    private long userPhoneNumber;
    private String userLogin;
    private String userPassword;
    private Date userRegisterDate;
    private boolean userIsAdmin;
    private Position positionByPositionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_surname", nullable = false, length = 20)
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    @Basic
    @Column(name = "user_firstName", nullable = false, length = 20)
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Basic
    @Column(name = "user_secondName", nullable = false, length = 20)
    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }

    @Basic
    @Column(name = "user_phoneNumber", nullable = false, length = 12)
    public long getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(long userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    @Basic
    @Column(name = "user_login", unique = true, nullable = false, length = 20)
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Basic
    @Column(name = "user_password", nullable = false, length = 20)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "user_registerDate", nullable = false)
    public Date getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(Date userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    @Basic
    @Column(name = "user_isAdmin", nullable = false)
    public boolean getUserIsAdmin() {
        return userIsAdmin;
    }

    public void setUserIsAdmin(boolean userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    public User() {
    }

    public User(String userSurname, String userFirstName, String userSecondName, long userPhoneNumber,
                String userLogin, String userPassword, Date userRegisterDate, boolean userIsAdmin,
                Position positionByPositionId) {
        this.userSurname = userSurname;
        this.userFirstName = userFirstName;
        this.userSecondName = userSecondName;
        this.userPhoneNumber = userPhoneNumber;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userRegisterDate = userRegisterDate;
        this.userIsAdmin = userIsAdmin;
        this.positionByPositionId = positionByPositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (userId != that.userId) return false;
        if (userPhoneNumber != that.userPhoneNumber) return false;
        if (userSurname != null ? !userSurname.equals(that.userSurname) : that.userSurname != null) return false;
        if (userFirstName != null ? !userFirstName.equals(that.userFirstName) : that.userFirstName != null)
            return false;
        if (userSecondName != null ? !userSecondName.equals(that.userSecondName) : that.userSecondName != null)
            return false;
        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (userRegisterDate != null ? !userRegisterDate.equals(that.userRegisterDate) : that.userRegisterDate != null)
            return false;
        if (userIsAdmin != that.userIsAdmin) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);
        result = 31 * result + (userFirstName != null ? userFirstName.hashCode() : 0);
        result = 31 * result + (userSecondName != null ? userSecondName.hashCode() : 0);
        result = 31 * result + (int) (userPhoneNumber ^ (userPhoneNumber >>> 32));
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (userRegisterDate != null ? userRegisterDate.hashCode() : 0);
        result = 31 * result + (userIsAdmin ? 1231 : 1237);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "position_id", nullable = false)
    public Position getPositionByPositionId() {
        return positionByPositionId;
    }

    public void setPositionByPositionId(Position positionByPositionId) {
        this.positionByPositionId = positionByPositionId;
    }

    @Override
    public String toString() {
        return userId + ", " +
                userSurname + ", " +
                userFirstName + ", " +
                userSecondName + ", " +
                userPhoneNumber + ", " +
                userLogin + ", " +
                userPassword + ", " +
                userRegisterDate + ", " +
                userIsAdmin + ", " +
                positionByPositionId;
    }
}
