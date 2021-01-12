package org.mddarr.containerstestingservice.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private String userid;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;




}
