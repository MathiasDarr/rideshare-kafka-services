package org.mddarr.containerstestingservice.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    private String title;

}
