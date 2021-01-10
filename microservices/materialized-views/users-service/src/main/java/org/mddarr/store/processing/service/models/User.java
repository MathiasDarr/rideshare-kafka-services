package org.mddarr.store.processing.service.models;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="users",schema = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userid;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Date update_ts;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }



    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Date getUpdate_ts() {
        return update_ts;
    }

    public void setUpdate_ts(Date update_ts) {
        this.update_ts = update_ts;
    }
}
