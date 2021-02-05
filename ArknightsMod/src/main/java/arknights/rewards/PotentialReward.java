package arknights.rewards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.BaseDeployCard;
import basemod.abstracts.CustomReward;

/**
 * @author hundun
 * Created on 2021/02/01
 */
public class PotentialReward extends CustomReward {
    private static final Texture ICON = new Texture(ArknightsMod.makeRewardPngPath(PotentialReward.class));
    
    public String operatorCardId;
    
    public PotentialReward(String operatorCardId, String potentialText) {
        super(ICON, potentialText, PotentialRewardTypePatch.POTENTIAL);
        this.operatorCardId = operatorCardId;
    }

    @Override
    public boolean claimReward() {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.cardID.equals(operatorCardId) && card instanceof BaseDeployCard) {
                ArknightsMod.logger.info("PotentialReward claiming for operatorCardId = " + ((ArknightsModCard)card).toIdString());
                ((BaseDeployCard)card).addPotentialCount();
                AbstractDungeon.player.bottledCardUpgradeCheck(card);
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
                return true;
            }
        }
        return false;
    }

}
