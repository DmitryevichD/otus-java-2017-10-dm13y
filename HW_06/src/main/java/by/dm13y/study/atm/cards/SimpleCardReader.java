package by.dm13y.study.atm.cards;

public class SimpleCardReader extends CardReader {
    @Override
    public void processCard(Card card) {
        if(card != null){
            if(card.getNumber() > 0){
                update(card);
            }
        }
    }
}
