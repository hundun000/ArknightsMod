package arknights.cards.base;
import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.base.component.UseCounter;
import arknights.characters.Doctor;
import arknights.powers.MagicStrengthPower;
import arknights.variables.ExtraVariable;

public abstract class AbstractModCard extends CustomCard {
    protected final CardStrings cardStrings;
    
    protected UpgradeSetting upgradeSetting = new UpgradeSetting();
    
    
    // extra magic number slots
    public int[] extraMagicNumbers = new int[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];        
    public int[] baseExtraMagicNumbers = new int[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] extraMagicNumberUpgradeds = new boolean[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] extraMagicNumberModifieds = new boolean[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    
    
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
        this(id, img, cost, type, Doctor.Enums.ARKNIGHTS_CARD_COLOR, rarity, target);
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
        
        for (int i = 0; i < ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE; i++) {
            if (basicSetting.getExtraMagicNumber(i) != null) {
                this.baseExtraMagicNumbers[i] = basicSetting.getExtraMagicNumber(i);
                this.extraMagicNumbers[i] =  this.baseExtraMagicNumbers[i];
            }
        }
    }

    
    @Override
    public void applyPowers() {
        super.applyPowers();
        //calculateMagicDamage();
    }
    
    @Deprecated
    private void calculateMagicDamage() {
        AbstractPower power = AbstractDungeon.player.getPower(MagicStrengthPower.POWER_ID);
        if (power != null) {
            this.extraMagicNumbers[ExtraVariable.MAGIC_DAMAGE_INDEX] = Math.max(0, this.baseExtraMagicNumbers[ExtraVariable.MAGIC_DAMAGE_INDEX] + power.amount);
        } else {
            this.extraMagicNumbers[ExtraVariable.MAGIC_DAMAGE_INDEX] = this.baseExtraMagicNumbers[ExtraVariable.MAGIC_DAMAGE_INDEX];
        }
    }

    protected void setUpgradeInfo(UpgradeSetting upgradeSetting) {
        this.upgradeSetting = upgradeSetting;
    }
    
    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        for (int i = 0; i < ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE; i++) {
            if (extraMagicNumberUpgradeds[i]) {
                extraMagicNumbers[i] = baseExtraMagicNumbers[i];
                extraMagicNumberModifieds[i] = true;
            }
        }
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
            
            for (int i = 0; i < ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE; i++) {
                if (upgradeSetting.getPlusExtraMagicNumber(i) != null) {
                    upgradeExtraMagicNumber(i, upgradeSetting.getPlusExtraMagicNumber(i));
                }
            }
            
            if (cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            
            if (upgradeSetting.isUpgradeCardToPreview()) {
                this.cardsToPreview.update();
            }
            
            initializeDescription();
        }
    }

    protected void upgradeExtraMagicNumber(int index, int amount) {
        baseExtraMagicNumbers[index] += amount; 
        extraMagicNumbers[index] = baseExtraMagicNumbers[index];
        extraMagicNumberUpgradeds[index] = true;
    }

}