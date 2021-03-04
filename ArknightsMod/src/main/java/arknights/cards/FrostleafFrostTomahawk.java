package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.SlowOneTurnPower;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class FrostleafFrostTomahawk extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(FrostleafFrostTomahawk.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    
    public FrostleafFrostTomahawk() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(2)
                .setMagicNumber(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusBlock(1)
                );
        initSpThreshold(4, GainSpType.ON_USE);
    }

    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainBlockAction(player, player, block));
        if (isSpCountReachThreshold()) {
            addToBot(new ApplyPowerAction(monster, player, new SlowOneTurnPower(monster, 1)));
        }
        handleSpAfterUse();
    }
    
    @Override
    public void applyPowers() {
        if (isSpCountReachThreshold()) {
            applyPowers(magicNumber);
        } else {
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster arg0) {
        if (isSpCountReachThreshold()) {
            calculateCardDamage(arg0, magicNumber);
        } else {
            super.calculateCardDamage(arg0);
        }
    }

}
