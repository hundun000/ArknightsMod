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
import arknights.cards.AbstractDynamicCard.UpgradeInfo;
import arknights.characters.Doctor;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2020/11/05
 */
@AutoAdd.Ignore
public class IfritScorchedEarth extends AbstractDynamicCard {

 // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(IfritScorchedEarth.class.getSimpleName()); // DELETE THIS ONE.
    public static final String IMG = makeCardPath(IfritScorchedEarth.class.getSimpleName() + ".png");// "public static final String IMG = makeCardPath("${NAME}.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       
    public static final CardColor COLOR = Doctor.Enums.COLOR_GRAY;
    private static final int COST = 2;  
    
    // special const
    private static final int GIVE_BURNS_CARD_NUM = 2;  
    private static final int UPGRADED_GIVE_BURNS_CARD_NUM = 1;  
    
    public IfritScorchedEarth() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBasicInfo(new BasicInfo()
                .setDamage(15)
                .setMagicNumber(GIVE_BURNS_CARD_NUM)
                );
        setUpgradeInfo(new UpgradeInfo()
                .setPlusDamage(5)
                .setNewMagicNumber(UPGRADED_GIVE_BURNS_CARD_NUM)
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
