package arknights.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/**
 * @author hundun
 * Created on 2020/12/24
 */
public class AbstractDungeonHelper {
    
    /**
     * patch AbstractRoom.addCardToRewards
     */
    public static void setSpecifiedCardAsSingleReward(AbstractCard card) {
        RewardItem cardReward = new RewardItem();
        cardReward.cards.clear();
        cardReward.cards.add(card);
        
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().rewards.add(cardReward);
    }

}
