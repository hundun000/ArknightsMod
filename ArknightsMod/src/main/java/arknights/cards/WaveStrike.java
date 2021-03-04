package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/26
 */
public class WaveStrike extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(WaveStrike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    
    
    public WaveStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(8)
                .setBlock(3)
                .setMagicNumber(2)
                .setExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, 2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(4)
                .setPlusBlock(2)
                .setPlusMagicNumber(1)
                );
    }
    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_USE_TIMES_HINT);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        useTimes++;
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainBlockAction(player, player, block));
        if (useTimes == magicNumber) {
            addToBot(new ModifyDamageAction(this.uuid, - getExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX)));
            initializeDescription();
        }
    }


}
