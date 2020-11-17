package arknights.cards.derivations;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;

import arknights.DefaultMod;
import arknights.cards.FiveStarVanguardDeploy;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.demo.DefaultCommonAttack;
import arknights.characters.Doctor;
import basemod.abstracts.CustomCard;

/**
 * @author hundun
 * Created on 2020/11/13
 */
public class SwordRain extends AbstractModCard {
    
    public static final String ID = DefaultMod.makeID(SwordRain.class.getSimpleName()); 
    public static final String IMG = DefaultMod.makeCardPngPath(AbstractModCard.class);

    private static final CardRarity RARITY = CardRarity.SPECIAL; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       
    public static final CardColor COLOR = Doctor.Enums.COLOR_GRAY;
    private static final int COST = 0;
    
    private static final int WEAK_STACK_NUM = 1;
    private static final int GIVE_ENERGY_NUM = 1;
    
    private static final int PLUS_WEAK_STACK_NUM = 1;
    private static final int PLUS_GIVE_ENERGY_NUM = 1;
    
    
    public SwordRain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(7)
                .setMagicNumber(GIVE_ENERGY_NUM)
                .setExtraMagicNumber(0, WEAK_STACK_NUM)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                .setPlusMagicNumber(PLUS_GIVE_ENERGY_NUM)
                .setPlusExtraMagicNumber(0, PLUS_WEAK_STACK_NUM)
                );
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(player, player, new EnergizedPower(player, magicNumber), magicNumber));
        
        addToBot(new VFXAction(player, new IntimidateEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 1.0F));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, player, new WeakPower(mo, this.extraMagicNumbers[0], false)));
            addToBot(new ApplyPowerAction(mo, player, new SlowPower(mo, 1)));
        }
    }

}
