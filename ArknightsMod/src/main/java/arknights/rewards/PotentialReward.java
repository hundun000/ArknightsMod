package arknights.rewards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import basemod.abstracts.CustomReward;

/**
 * @author hundun
 * Created on 2021/02/01
 */
public class PotentialReward extends CustomReward {
    private static final Texture ICON = new Texture(ArknightsMod.makeRewardPngPath(PotentialReward.class));
    
    public String operatorCardId;
    public int amount;
    
    public PotentialReward(String operatorCardId, String potentialText, int amount) {
        super(ICON, potentialText, PotentialRewardTypePatch.POTENTIAL);
        this.operatorCardId = operatorCardId;
        this.amount = amount;
    }

    @Override
    public boolean claimReward() {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.cardID.equals(operatorCardId) && card instanceof ArknightsModCard) {
                ArknightsMod.logger.info("PotentialReward claiming for operatorCardId = " + ((ArknightsModCard)card).toIdString());
                ((ArknightsModCard)card).addPotentialCount(amount);
                return true;
            }
        }
        return false;
    }

}
