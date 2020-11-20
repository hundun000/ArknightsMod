package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

import arknights.ArknightsMod;
import arknights.actions.DiscardWantTargetCardAction;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.DisplacementPower;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/19
 */
public class SteamPump extends AbstractModCard {

    public static final String ID = ArknightsMod.makeID(SteamPump.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);
    
    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.SKILL;       
    private static final int COST = 1;
    
    
    public SteamPump() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setBlock(8)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new DiscardWantTargetCardAction(player, 1, Burn.class));
        
    }

}
