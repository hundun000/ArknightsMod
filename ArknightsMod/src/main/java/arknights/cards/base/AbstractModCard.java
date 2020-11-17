package arknights.cards.base;
import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import arknights.DefaultMod;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.characters.Doctor;

/**
 * 游戏设计模式/子类沙箱模式 https://gpp.tkchu.me/subclass-sandbox.html
 */
public abstract class AbstractModCard extends CustomCard {
    private final CardStrings cardStrings;
    
    protected UpgradeSetting upgradeSetting = new UpgradeSetting();

    public static final int EXTRA_MAGIC_NUMBER_SIZE = 1;
    public int[] extraMagicNumbers = new int[EXTRA_MAGIC_NUMBER_SIZE];        
    public int[] baseExtraMagicNumbers = new int[EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] upgradedExtraMagicNumbers = new boolean[EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] extraMagicNumberModifieds = new boolean[EXTRA_MAGIC_NUMBER_SIZE];

    
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
        
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
    }
    
    protected void initBaseFields(BasicSetting basicSetting) {
        if (basicSetting.getDamage() != null) {
            this.baseDamage = basicSetting.getDamage();
        }
        if (basicSetting.getBlock() != null) {
            this.baseBlock = basicSetting.getBlock();
        }
        if (basicSetting.getMagicNumber() != null) {
            this.baseMagicNumber = basicSetting.getMagicNumber();
            this.magicNumber = this.baseMagicNumber;
        }
        
        for (int i = 0; i < EXTRA_MAGIC_NUMBER_SIZE; i++) {
            if (basicSetting.getExtraMagicNumber(i) != null) {
                this.baseExtraMagicNumbers[i] = basicSetting.getExtraMagicNumber(i);
                this.extraMagicNumbers[i] =  this.baseExtraMagicNumbers[i];
            }
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
            if (upgradeSetting.getPlusMagicNumber() != null) {
                upgradeMagicNumber(upgradeSetting.getPlusMagicNumber());
            }
            
            for (int i = 0; i < EXTRA_MAGIC_NUMBER_SIZE; i++) {
                if (upgradeSetting.getPlusExtraMagicNumber(i) != null) {
                    upgradeExtraMagicNumber(i, upgradeSetting.getPlusExtraMagicNumber(i));
                }
            }
            
            if (cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    protected void upgradeExtraMagicNumber(int index, int amount) {
        baseExtraMagicNumbers[index] += amount; 
        extraMagicNumbers[index] = baseExtraMagicNumbers[index];
        upgradedExtraMagicNumbers[index] = true;
    }
}