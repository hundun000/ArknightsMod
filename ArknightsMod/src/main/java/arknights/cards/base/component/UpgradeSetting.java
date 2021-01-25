package arknights.cards.base.component;

import java.util.HashMap;
import java.util.Map;

import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/11/16
 */
public class UpgradeSetting {
    private Integer plusDamage;
    private Integer plusBlock;
    private Integer newCost;
    private Integer plusMagicNumber;
    boolean upgradeCardToPreview = false;
    private Map<Integer, Integer> plusExtraMagicNumbers = new HashMap<>(ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE);
    
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
    public Integer getPlusMagicNumber() {
        return plusMagicNumber;
    }
    public UpgradeSetting setPlusMagicNumber(Integer plusMagicNumber) {
        this.plusMagicNumber = plusMagicNumber;
        return this;
    }
    public Integer getPlusExtraMagicNumber(int index) {
        return plusExtraMagicNumbers.get(index);
    }
    public UpgradeSetting setPlusExtraMagicNumber(int index, Integer plusMagicNumber) {
        this.plusExtraMagicNumbers.put(index, plusMagicNumber);
        return this;
    }
    public boolean isUpgradeCardToPreview() {
        return upgradeCardToPreview;
    }
    public UpgradeSetting setUpgradeCardToPreview(boolean upgradeCardToPreview) {
        this.upgradeCardToPreview = upgradeCardToPreview;
        return this;
    }
    
}
