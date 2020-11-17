package arknights.cards;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.BaseVanguardDeploy;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.derivations.ChargeAlpha;
import arknights.cards.derivations.SwordRain;
import arknights.characters.Doctor;

/**
 * @author hundun
 * Created on 2020/11/05
 */
public class FourStarVanguardDeploy extends BaseVanguardDeploy {

    public static final String ID = DefaultMod.makeID(FourStarVanguardDeploy.class);
    public static final String IMG = DefaultMod.makeCardPngPath(BaseVanguardDeploy.class);

    private static final CardRarity RARITY = CardRarity.COMMON;     
    
    private static final int COST = 1;  
    private static final AbstractCard giveCard = new ChargeAlpha();
    
    public FourStarVanguardDeploy() { 
        super(ID, IMG, COST, RARITY);
        initBaseFields(new BasicSetting()
                .setDamage(3)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(2)
                );
        setGiveCardPrototype(giveCard);
    }


}
