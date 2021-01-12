package org.mddarr.containerstestingservice.models;


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


}
