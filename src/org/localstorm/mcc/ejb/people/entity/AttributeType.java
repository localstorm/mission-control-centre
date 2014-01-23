package org.localstorm.mcc.ejb.people.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="ATTRIBUTE_TYPES")
@NamedQueries({
    @NamedQuery(
        name = AttributeType.Queries.FIND_ALL,
        query= "SELECT o FROM AttributeType o ORDER BY o.name"
    )
})
public class AttributeType extends AbstractEntity implements Identifiable, Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @Column(name="view_type", unique=false, updatable=true, nullable=false )
    private String viewType;

    @Column(name="token", unique=false, updatable=true, nullable=false )
    private String token;

    @Column(name="is_email", unique=false, updatable=true, nullable=false )
    private boolean email;
    private static final long serialVersionUID = 9107804959460701660L;

    @Override
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(boolean email)
    {
        this.email = email;
    }

    public boolean isEmail()
    {
        return email;
    }

    public static interface Queries {
        public static final String FIND_ALL = "findAllAttributeTypes";
    }
}
