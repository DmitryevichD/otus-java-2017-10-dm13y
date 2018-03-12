package by.dm13y.study.msgsys.api;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Sender implements Serializable {
    private Integer id;
    private SenderType type;

    public Sender(SenderType type){
        this.type = type;
    }

    @Override
    public String toString(){
        return "Sender(id: " + id + ", type: " + type + ")";
    }

}
