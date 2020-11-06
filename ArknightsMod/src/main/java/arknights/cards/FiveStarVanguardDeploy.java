package arknights.cards;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import arknights.DefaultMod;
import arknights.characters.Doctor;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FiveStarVanguardDeploy extends AbstractDynamicCard {

 // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(FiveStarVanguardDeploy.class.getSimpleName()); // DELETE THIS ONE.
    public static final String IMG = makeCardPath(FiveStarVanguardDeploy.class.getSimpleName() + ".png");// "public static final String IMG = makeCardPath("${NAME}.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       
    public static final CardColor COLOR = Doctor.Enums.COLOR_GRAY;
    private static final int COST = 1;  
    
    // special const
    private static final int GIVE_ENERGY_NUM = 2;  
    
    public FiveStarVanguardDeploy() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBasicInfo(new BasicInfo()
                .setDamage(7)
                .setMagicNumber(GIVE_ENERGY_NUM)
                );
        setUpgradeInfo(new UpgradeInfo()
                .setPlusDamage(3)
                );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                    p,
                    p,
                    new EnergizedBluePower(p, this.magicNumber),
                    this.magicNumber
                )
            );
    }
}
