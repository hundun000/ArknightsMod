package arknights.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

import static arknights.ArknightsMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.characters.Doctor;
import arknights.powers.MagicStrengthPower;

@AutoAdd.Ignore
@Deprecated
public class MagicStrengthGain extends AbstractModCard {


    public static final String ID = ArknightsMod.makeID(MagicStrengthGain.class);
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int STACK = 1;
    private static final int UPGRADE_PLUS_STACK = 1;


    public MagicStrengthGain() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(STACK)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(UPGRADE_PLUS_STACK)
                );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                    player,
                    player,
                    new MagicStrengthPower(player, player, this.magicNumber),
                    this.magicNumber
                )
            );
    }

}