package arknights.cards;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.NightmarePower;

import arknights.DefaultMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.BaseVanguardDeploy;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.SwordRain;
import arknights.characters.Doctor;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FiveStarVanguardDeploy extends BaseVanguardDeploy {

    public static final String ID = DefaultMod.makeID(FiveStarVanguardDeploy.class.getSimpleName()); // DELETE THIS ONE.
    public static final String IMG = DefaultMod.makeCardPngPath(BaseVanguardDeploy.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;      
    private static final int COST = 1;  
  
    private static final AbstractCard giveCard = new SwordRain();
    
    public FiveStarVanguardDeploy() { 
        super(ID, IMG, COST, RARITY);
        setBasicInfo(new BasicSetting()
                .setDamage(7)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
        setGiveCardPrototype(giveCard);
    }

}
