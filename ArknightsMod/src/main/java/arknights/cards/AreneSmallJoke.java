package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.actions.RangedGuardTwiceDamageAction;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class AreneSmallJoke extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(AreneSmallJoke.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
    
    public AreneSmallJoke() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(8)
                .setBlock(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
        setSpThreshold(4, GainSpType.ON_DRAWN);
        
        initializeDescription();
    }

    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new RangedGuardTwiceDamageAction(monster, player, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        if (isSpCountReachThreshold()) {
            addToBot(new RangedGuardTwiceDamageAction(monster, player, damage, damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        }
        handleSpAfterUse();
    }


}
