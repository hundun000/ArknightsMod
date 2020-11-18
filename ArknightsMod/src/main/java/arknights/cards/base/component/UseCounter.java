package arknights.cards.base.component;
/**
 * @author hundun
 * Created on 2020/11/18
 */
public class UseCounter {
    int count;
    
    public UseCounter() {
        reset();
    }
    
    public void reset() {
        count = 0;
    }
    
    public void add() {
        count++;
    }
    
    public int getCount() {
        return count;
    }
}
