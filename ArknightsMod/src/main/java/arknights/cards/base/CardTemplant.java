package arknights.cards.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2020/11/20
 */
@AutoAdd.Ignore
public class CardTemplant extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(CardTemplant.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.NONE;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 0;

    
    public CardTemplant() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                
                );
        setUpgradeInfo(new UpgradeSetting()
                
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainBlockAction(player, player, block));
    }

}
