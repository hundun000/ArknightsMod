package arknights.cards;
import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import arknights.DefaultMod;
import arknights.characters.Doctor;

/**
 * 游戏设计模式/子类沙箱模式 https://gpp.tkchu.me/subclass-sandbox.html
 */
public abstract class AbstractModCard extends CustomCard {

    protected UpgradeSetting upgradeSetting = new UpgradeSetting();
    
    /**
     * auto generate fields which always same or similar
     */
    public AbstractModCard(
            final String id,
            final String img,
            final int cost,
            final CardType type,
            final CardRarity rarity,
            final CardTarget target) {
        this(id, img, cost, type, Doctor.Enums.COLOR_GRAY, rarity, target);
    }
    
    /**
     * template-project recommend
     */
    public AbstractModCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        super.isCostModified = false;
        super.isCostModifiedForTurn = false;
        super.isDamageModified = false;
        super.isBlockModified = false;
        super.isMagicNumberModified = false;
    }
    
    protected void setBasicInfo(BasicSetting basicSetting) {
        if (basicSetting.getDamage() != null) {
            this.baseDamage = basicSetting.getDamage();
        }
        if (basicSetting.getBlock() != null) {
            this.baseBlock = basicSetting.getBlock();
        }
        if (basicSetting.getMagicNumber() != null) {
            this.baseMagicNumber = basicSetting.getMagicNumber();
            this.magicNumber = basicSetting.getMagicNumber();
        }
    }
    
    public class BasicSetting {
        private Integer damage;
        private Integer block;
        private Integer magicNumber;
        public Integer getDamage() {
            return damage;
        }
        public BasicSetting setDamage(Integer damage) {
            this.damage = damage;
            return this;
        }
        public Integer getBlock() {
            return block;
        }
        public BasicSetting setBlock(Integer block) {
            this.block = block;
            return this;
        }
        public Integer getMagicNumber() {
            return magicNumber;
        }
        public BasicSetting setMagicNumber(Integer magicNumber) {
            this.magicNumber = magicNumber;
            return this;
        }
    }
    
    
    public class UpgradeSetting {
        private Integer plusDamage;
        private Integer plusBlock;
        private Integer newCost;
        private Integer newMagicNumber;
        public Integer getPlusDamage() {
            return plusDamage;
        }
        public UpgradeSetting setPlusDamage(Integer upgradePlusDamage) {
            this.plusDamage = upgradePlusDamage;
            return this;
        }
        public Integer getPlusBlock() {
            return plusBlock;
        }
        public UpgradeSetting setPlusBlock(Integer upgradePlusBlock) {
            this.plusBlock = upgradePlusBlock;
            return this;
        }
        public Integer getNewCost() {
            return newCost;
        }
        public UpgradeSetting setNewCost(Integer upgradedCost) {
            this.newCost = upgradedCost;
            return this;
        }
        public Integer getNewMagicNumber() {
            return newMagicNumber;
        }
        public UpgradeSetting setNewMagicNumber(Integer newMagicNumber) {
            this.newMagicNumber = newMagicNumber;
            return this;
        }
        
        
    }
    
    protected void setUpgradeInfo(UpgradeSetting upgradeSetting) {
        this.upgradeSetting = upgradeSetting;
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (upgradeSetting.getPlusDamage() != null) {
                upgradeDamage(upgradeSetting.getPlusDamage());
            }
            if (upgradeSetting.getNewCost() != null) {
                upgradeBaseCost(upgradeSetting.getNewCost());
            }
            if (upgradeSetting.getPlusBlock() != null) {
                upgradeBlock(upgradeSetting.getPlusBlock());
            }
            if (upgradeSetting.getNewMagicNumber() != null) {
                upgradeMagicNumber(upgradeSetting.getNewMagicNumber());
            }
            initializeDescription();
        }
    }
}