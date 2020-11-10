package arknights.cards;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import arknights.DefaultMod;
import arknights.cards.AbstractModCard.BasicSetting;
import arknights.cards.AbstractModCard.UpgradeSetting;
import arknights.cards.base.BaseSummonCard;
import arknights.characters.Doctor;
import arknights.orbs.MeeBooAttackTypeOrb;

/**
 * @author hundun
 * Created on 2020/11/10
 */
public class MeebooAttackTypeSummon extends BaseSummonCard {

    public static final String ID = DefaultMod.makeID(MeebooAttackTypeSummon.class);
    public static final String IMG = DefaultMod.makeCardPngPath(MeebooAttackTypeSummon.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    
    private static final int COST = 1;  
    
    // special const
    private static final int SUMMON_NUM = 1;  
    private static final int UPGRADED_SUMMON_NUM = 2;  
    
    public MeebooAttackTypeSummon() { 
        super(ID, IMG, COST, RARITY);
        setBasicInfo(new BasicSetting()
                .setMagicNumber(SUMMON_NUM)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setNewMagicNumber(UPGRADED_SUMMON_NUM)
                );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new ChannelAction(new MeeBooAttackTypeOrb()));
        }
    }


}
