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
import arknights.characters.Doctor;

public class FourStarSingleTargetMedicDeploy extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(FourStarSingleTargetMedicDeploy.class.getSimpleName());
    public static final String IMG = makeCardPath(FourStarSingleTargetMedicDeploy.class.getSimpleName() + ".png");	

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Doctor.Enums.COLOR_GRAY;

    private static final int COST = 1;



    public FourStarSingleTargetMedicDeploy() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBaseDamageAndBlock(null, 5);
        setUpgradeInfo(null, 3, COST);
        this.tags.add(CardTags.STARTER_DEFEND); 
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

}
