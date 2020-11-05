package arknights.cards;
import basemod.AutoAdd;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

/**
 * 游戏设计模式/子类沙箱模式 https://gpp.tkchu.me/subclass-sandbox.html
 */
public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    protected Integer upgradePlusDamage;
    protected Integer upgradePlusBlock;
    protected Integer upgradedCost;
    
    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }
    
    protected void setBaseDamageAndBlock(Integer baseDamage, Integer baseBlock) {
        if (baseDamage != null) {
            this.baseDamage = baseDamage;
        }
        if (baseBlock != null) {
            this.baseBlock = baseBlock;
        }
    }
    
    protected void setUpgradeInfo(Integer upgradePlusDamage, Integer upgradePlusBlock, Integer upgradedCost) {
        if (upgradePlusDamage != null) {
            this.upgradePlusDamage = upgradePlusDamage;
        }
        if (upgradePlusBlock != null) {
            this.upgradePlusBlock = upgradePlusBlock;
        }
        if (upgradedCost != null) {
            this.upgradedCost = upgradedCost;
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (upgradePlusDamage != null) {
                upgradeDamage(upgradePlusDamage);
            }
            if (upgradedCost != null) {
                upgradeBaseCost(upgradedCost);
            }
            if (upgradePlusBlock != null) {
                upgradeBlock(upgradePlusBlock);
            }
            initializeDescription();
        }
    }
}