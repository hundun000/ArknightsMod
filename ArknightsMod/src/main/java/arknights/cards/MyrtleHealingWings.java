package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.actions.RegainBlockAction;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.MetalCrabStrike;
import arknights.manager.MoreGameActionManager;
import arknights.util.LocalizationUtils;

/**
 * @author hundun
 * Created on 2021/02/22
 */
public class MyrtleHealingWings extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(MyrtleHealingWings.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    
    public MyrtleHealingWings() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(3)
                .setRegainBlock(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(2)
                );
        setSpThreshold(4, GainSpType.ON_DRAWN);
    }
    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        addSpCount(3);
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT_AND_REGAIN_BLOCK_HINT);
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (isSpCountReachThreshold()) {
            addToBot(new GainEnergyAction(2));
            addToBot(new RegainBlockAction(player, this.regainBlock, currentRegainBlockAmountLimit));
        } else {
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
        }
        handleSpAfterUse();
    }

}
