package server.database;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.*;

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

    @Column(name = "date", unique = false, nullable = false)
    private Date date;
    
    @Column(name = "board_size", unique = false, nullable = false)
    private int boardSize;

    @Column(name = "result", unique = false, nullable = false)
    private String result;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="Game_idGame")
    private List<DBMove> moves;

    public DBGame(){
        date = new Date();
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
    
    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
