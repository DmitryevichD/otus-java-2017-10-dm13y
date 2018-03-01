package by.dm13y.study.msgsys.api;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Header implements Serializable {
    private Integer id;
    private RecipientType type;

    public Header(RecipientType type){
        this.type = type;
    }

}
