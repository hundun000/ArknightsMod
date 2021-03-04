package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.SunderAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.ArknightsModCard.RawDescriptionState;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.variables.ExtraVariable;

public class VignaHammerOn extends ArknightsModCard {
	
	public static final String ID = ArknightsMod.makeID(VignaHammerOn.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;
	
	
	public VignaHammerOn() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(6)
                .setMagicNumber(6)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusMagicNumber(3)
                );
        initSpThreshold(4, GainSpType.ON_DRAWN);
    }

	

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    addToBot((AbstractGameAction)new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
	    addToBot((AbstractGameAction)new WaitAction(0.8F));
	    addToBot((AbstractGameAction)new SunderAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), 1));
	}
	
	@Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
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
