package arknights.cards.operator;
/**
 * @author hundun
 * Created on 2021/02/06
 */
@Deprecated
public enum PromotionState {
    NO_PROMOTION(-1, 0),
    ZERO(1, 1),
    ONE(3, 2),
    TWO(10, 3),
    ;
    
    private int maxLevel;
    private int giveCardRange;
    private PromotionState(int maxLevel, int giveCardRange) {
        this.maxLevel = maxLevel;
        this.giveCardRange = giveCardRange;
    }
    
    public int getMaxLevel() {
        return maxLevel;
    }
    
    public int getGiveCardRange() {
        return giveCardRange;
    }

    public PromotionState next() {
        switch (this) {
            case ZERO:
                return ONE;
            case ONE:
                return TWO;
            default:
                return this;
        }
    }

}
