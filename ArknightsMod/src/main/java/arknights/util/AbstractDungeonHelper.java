package arknights.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.SwiftStrike;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;
import arknights.ArknightsMod;
import arknights.cards.base.ArknightsCardTag;

/**
 * @author hundun
 * Created on 2020/12/24
 */
public class AbstractDungeonHelper {
    
    public static CardGroup operatorCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    
    /**
     * copy from  AbstractDungeon.addColorlessCards
     */
    public static void addOperatorCards() {
        for (Map.Entry<String, AbstractCard> c : (Iterable<Map.Entry<String, AbstractCard>>)CardLibrary.cards.entrySet()) {
            AbstractCard card = c.getValue();
            if (card.hasTag(ArknightsCardTag.DEPLOY)) {
                operatorCardPool.addToTop(card);
            }
        } 
        ArknightsMod.logger.info("operator CARDS: " + operatorCardPool.size());
    }
    
    /**
     * copy from AbstractRoom.addCardToRewards
     */
    public static void setSpecifiedCardAsSingleReward(AbstractCard... cards) {
        RewardItem cardReward = new RewardItem();
        cardReward.cards.clear();
        cardReward.cards.addAll(Arrays.asList(cards));
        
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addCardReward(cardReward);
        
        ArknightsMod.logger.info("after setSpecifiedCardAsSingleReward size " + cards.length + ", getCurrRoom().rewards.size = " + AbstractDungeon.getCurrRoom().rewards.size());
    }
    
    /**
     * copy from  AbstractDungeon.returnColorlessCard
     */
    public static AbstractCard returnOperatorCard(AbstractCard.CardRarity rarity) {
        Collections.shuffle(operatorCardPool.group);

        
        for (AbstractCard c : operatorCardPool.group) {
            if (c.rarity == rarity || rarity == null) {
                return c.makeCopy();
            }
        } 
    
        if (rarity == AbstractCard.CardRarity.RARE) {
            for (AbstractCard c : operatorCardPool.group) {
                if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    return c.makeCopy();
                }
            } 
        }
        return new SwiftStrike();
    }

}
