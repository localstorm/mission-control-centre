package org.localstorm.mcc.ejb.gtd.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.AbstractEntity;


/**
 * @author localstorm
 */
@Entity
@Table(name="NOTES")
public class Note extends AbstractEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @Column(name="note", unique=false, updatable=true, nullable=false )
    private String note;
    
    @Column(name="type", unique=false, updatable=true, nullable=false )
    private String type;
    
    @Column(name="description", unique=false, updatable=true, nullable=false )
    private String description;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    private static final long serialVersionUID = 1828496921758323883L;

    public Note() 
    {
        this.note       = "";
        this.creation   = new Date();
        this.type       = "DEFAULT";
    }

    public Note( String note, String type ) {
        this.note       = note;
        this.creation   = new Date();
        this.type       = type;
    }
    
    public Integer getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public Date getCreation() {
        return creation;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
