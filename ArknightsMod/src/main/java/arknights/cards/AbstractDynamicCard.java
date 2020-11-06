package arknights.cards;
import basemod.AutoAdd;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

/**
 * 游戏设计模式/子类沙箱模式 https://gpp.tkchu.me/subclass-sandbox.html
 */
public abstract class AbstractDynamicCard extends AbstractDefaultCard {

    protected UpgradeInfo upgradeInfo = new UpgradeInfo();
    
    public AbstractDynamicCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

    }
    
    protected void setBasicInfo(BasicInfo basicInfo) {
        if (basicInfo.getDamage() != null) {
            this.baseDamage = basicInfo.getDamage();
        }
        if (basicInfo.getBlock() != null) {
            this.baseBlock = basicInfo.getBlock();
        }
        if (basicInfo.getMagicNumber() != null) {
            this.baseMagicNumber = basicInfo.getMagicNumber();
            this.magicNumber = basicInfo.getMagicNumber();
        }
    }
    
    public class BasicInfo {
        private Integer damage;
        private Integer block;
        private Integer magicNumber;
        public Integer getDamage() {
            return damage;
        }
        public BasicInfo setDamage(Integer damage) {
            this.damage = damage;
            return this;
        }
        public Integer getBlock() {
            return block;
        }
        public BasicInfo setBlock(Integer block) {
            this.block = block;
            return this;
        }
        public Integer getMagicNumber() {
            return magicNumber;
        }
        public BasicInfo setMagicNumber(Integer magicNumber) {
            this.magicNumber = magicNumber;
            return this;
        }
    }
    
    
    public class UpgradeInfo {
        private Integer plusDamage;
        private Integer plusBlock;
        private Integer newCost;
        private Integer newMagicNumber;
        public Integer getPlusDamage() {
            return plusDamage;
        }
        public UpgradeInfo setPlusDamage(Integer upgradePlusDamage) {
            this.plusDamage = upgradePlusDamage;
            return this;
        }
        public Integer getPlusBlock() {
            return plusBlock;
        }
        public UpgradeInfo setPlusBlock(Integer upgradePlusBlock) {
            this.plusBlock = upgradePlusBlock;
            return this;
        }
        public Integer getNewCost() {
            return newCost;
        }
        public UpgradeInfo setNewCost(Integer upgradedCost) {
            this.newCost = upgradedCost;
            return this;
        }
        public Integer getNewMagicNumber() {
            return newMagicNumber;
        }
        public UpgradeInfo setNewMagicNumber(Integer newMagicNumber) {
            this.newMagicNumber = newMagicNumber;
            return this;
        }
        
        
    }
    
    protected void setUpgradeInfo(UpgradeInfo upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (upgradeInfo.getPlusDamage() != null) {
                upgradeDamage(upgradeInfo.getPlusDamage());
            }
            if (upgradeInfo.getNewCost() != null) {
                upgradeBaseCost(upgradeInfo.getNewCost());
            }
            if (upgradeInfo.getPlusBlock() != null) {
                upgradeBlock(upgradeInfo.getPlusBlock());
            }
            if (upgradeInfo.getNewMagicNumber() != null) {
                upgradeMagicNumber(upgradeInfo.getNewMagicNumber());
            }
            initializeDescription();
        }
    }
}