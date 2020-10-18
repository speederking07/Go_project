package server.database;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import moves.Move;
import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.ALL, dynamicUpdate = true, selectBeforeUpdate = true)
@Table(name = "move", uniqueConstraints = {
        @UniqueConstraint(columnNames = "idMove"),
       })
public class DBMove {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @Column(name = "idMove", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "turn", unique = false, nullable = false)
    private Integer turn;

    @ManyToOne
    @JoinColumn(name = "game_idGame")
    private DBGame game;

    @Column(name = "move", unique = false, nullable = false, length = 45)
    private String move;

    public DBMove(Integer turn, DBGame game, Move move)
    {
        this.turn = turn;
        this.game = game;
        this.move = move.toString();
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public DBGame getGame() {
        return game;
    }

    public void setGame(DBGame game) {
        this.game = game;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
