package arknights.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;

import static arknights.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.DefaultMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.characters.Doctor;

public class FourStarSingleTargetMedicDeploy extends AbstractModCard {
    
    public static final String ID = DefaultMod.makeID(FourStarSingleTargetMedicDeploy.class);
    public static final String IMG = DefaultMod.makeCardPngPath(AbstractModCard.class);


    
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;



    public FourStarSingleTargetMedicDeploy() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        setBasicInfo(new BasicSetting()
                .setBlock(5)
                );
        
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                );
        this.tags.add(CardTags.STARTER_DEFEND); 
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

}
