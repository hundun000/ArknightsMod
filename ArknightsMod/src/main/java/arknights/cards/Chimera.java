package arknights.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.MagicStrengthPower;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/17
 */
public class Chimera extends AbstractModCard {
    
    private static final Logger logger = LogManager.getLogger(Chimera.class.getName());

    public static final String ID = ArknightsMod.makeID(Chimera.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    
    private static final int STACK = 5;
    private static final int UPGRADE_PLUS_STACK = 3;
    private static final int ATTACK_TIMES = 4;
    private static final int UPGRADE_PLUS_ATTACK_TIMES = 2;
    
    private static final int MAGIC_DAMAGE = 6;

    
    public Chimera() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(MAGIC_DAMAGE)
                .setMagicNumber(STACK)
                .setExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, ATTACK_TIMES)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(UPGRADE_PLUS_STACK)
                .setPlusExtraMagicNumber(ExtraVariable.GENERAL_2nd_MAGIC_NUMBER_INDEX, UPGRADE_PLUS_ATTACK_TIMES)
                );
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player,this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(player, player, new EndTurnDeathPower(player)));
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        
    }
    
    
    

}
