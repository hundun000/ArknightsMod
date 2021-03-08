package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsCardTag;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.variables.ExtraVariable;

public class StewardStrike extends ArknightsModCard {
	
	public static final String ID = ArknightsMod.makeID(StewardStrike.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 2;
	
	
	public StewardStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(12)
                .enableSpellDamageType()
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(6)
                );
        
    }

	

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		addToBot(new DamageAction(monster, new DamageInfo(player, damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
	}
	

}
