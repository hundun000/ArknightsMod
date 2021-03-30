package arknights.cards.simple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.manager.MoreGameActionManager;
import arknights.util.LocalizationUtils;

/**
 * @author hundun
 * Created on 2021/02/06
 */
public abstract class SimpleRegainBlock extends ArknightsModCard {
    
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.SKILL;       

    
    public SimpleRegainBlock(String ID, String IMG, CardRarity RARITY, int COST) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int finalRegainAmount = Math.min(currentRegainBlockAmountLimit, this.regainBlock);
        MoreGameActionManager.countRegainBlock(finalRegainAmount);
        addToBot(new GainBlockAction(player, player, finalRegainAmount));
    }
    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_REGAIN_BLOCK_HINT);
        super.triggerWhenFirstTimeDrawn();
    }


}
