package server.database;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.transform.Result;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL, dynamicUpdate = true, selectBeforeUpdate = true)
@Table(name = "game", uniqueConstraints = {
        @UniqueConstraint(columnNames = "idGame")})
public class DBGame {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGame", unique = true, nullable = false)
    private Integer id;

    @Column(name = "date", unique = true, nullable = false)
    private Date date;

    @Column(name = "result", unique = false, nullable = false)
    private Character result;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="Game_idGame")
    private List<DBMove> moves;

    public DBGame(Character result){
        date = new Date();
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Character getResult() {
        return result;
    }

    public void setResult(Character result) {
        this.result = result;
    }
}
