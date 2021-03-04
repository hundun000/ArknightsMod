package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.MetalCrabStrike;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2021/02/22
 */
public class ScavengerCommandAttack extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(ScavengerCommandAttack.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    
    public ScavengerCommandAttack() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(6)
                .setMagicNumber(3)
                .setExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, 4)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusMagicNumber(1)
                );
        initSpThreshold(4, GainSpType.ON_DRAWN);
    }
    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        addSpCount(3);
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT_AND_EX0);
    }
    
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
    }


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn)));
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - 1 < getExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX)) {
            addToTop((AbstractGameAction)new DrawCardAction((AbstractCreature)AbstractDungeon.player, 1));
        }
        if (isSpCountReachThreshold()) {
            addToBot(new GainEnergyAction(1));
        }
        handleSpAfterUse();
    }

}
