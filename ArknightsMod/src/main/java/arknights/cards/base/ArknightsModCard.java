package arknights.cards.base;
import basemod.AutoAdd;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.cards.operator.PromotionState;
import arknights.characters.ArknightsPlayer;
import arknights.manager.MoreGameActionManager;
import arknights.powers.IModifyRegainBlockPower;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

public abstract class ArknightsModCard extends CustomCard {
	protected final static DamageType SPELL_DAMAGE_TYPE = DamageType.HP_LOSS;
	private static final CardStrings PUBLIC_CARDSTRINGS = CardCrawlGame.languagePack.getCardStrings(ArknightsMod.makeID(ArknightsModCard.class));
    private static final String RAW_SP_HINT = PUBLIC_CARDSTRINGS.EXTENDED_DESCRIPTION[0];
    private static final String RAW_REGAIN_BLOCK_HINT = PUBLIC_CARDSTRINGS.EXTENDED_DESCRIPTION[1];
    private static final String RAW_SP_HINT_PLACEHOLDER = "{SP_HINT}";
    private static final String RAW_REGAIN_BLOCK_HINT_PLACEHOLDER = "{RB_HINT}";
    
	protected final CardStrings cardStrings;
    
    protected UpgradeSetting upgradeSetting = new UpgradeSetting();
    
    /*
     * XXXX:  当前属性值，结算了power加成等
     * baseXXX: 基础值，未结算power加成等。卡片效果是“基础伤害下降”，指的是该属性
     * XXXXUpgraded: instance当前的baseXXX和instance初始化时的baseXXX不一样。如升级或卡片效果是“基础伤害下降”。
     * XXXModified: instance当前的XXX和instance的baseXXX不一样。如因为power。
     */
    // extra magic number slots
    protected int[] extraMagicNumbers = new int[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];        
    public int[] baseExtraMagicNumbers = new int[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] extraMagicNumberUpgradeds = new boolean[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    public boolean[] extraMagicNumberModifieds = new boolean[ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE];
    
    public int regainBlock = 0;
    public int baseRegainBlock = 0;
    public boolean regainBlockUpgraded = false;
    public boolean regainBlockModified = false;
    public int currentRegainBlockAmountLimit = 0;
    
    private int spCount = -1;
    private int spThreshold = -1;
    private boolean firstTimeDrawn = true;   
    
    
    protected GainSpType gainSpType;
    
    public enum GainSpType {
        NO_SP,
        ON_DRAWN,
        ON_USE,
        
    }
    
    protected RawDescriptionState rawDescriptionState;
    
    public enum RawDescriptionState {
        BASE(false, false, false),
        BASE_AND_SP_HINT(true, false, false),
        BASE_AND_REGAIN_BLOCK_HINT(false, true, false),
        BASE_AND_SP_HINT_AND_REGAIN_BLOCK_HINT(true, true, false),
        BASE_AND_SP_HINT_AND_EX0(true, false, true),
        ;
        
        public final boolean hasSpHint;
        public final boolean hasRegainBlockHint;
        public final boolean hasEx0;
        
        private RawDescriptionState(
                boolean hasSpHint, 
                boolean hasRegainBlockHint, 
                boolean hasEx0) {
            this.hasSpHint = hasSpHint;
            this.hasRegainBlockHint = hasRegainBlockHint;
            this.hasEx0 = hasEx0;
        } 
        
        
    }
    
    /**
     * auto generate fields which always same or similar
     */
    public ArknightsModCard(
            final String id,
            final String img,
            final int cost,
            final CardType type,
            final CardRarity rarity,
            final CardTarget target) {
        this(id, img, cost, type, ArknightsPlayer.Enums.ARKNIGHTS_CARD_COLOR, rarity, target);
    }
    
    /**
     * template-project recommend
     */
    public ArknightsModCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
//        super.isCostModified = false;
//        super.isCostModifiedForTurn = false;
//        super.isDamageModified = false;
//        super.isBlockModified = false;
//        super.isMagicNumberModified = false;
        
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.spCount = 0;
        this.gainSpType = GainSpType.NO_SP;
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE);
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
        if (basicSetting.getRegainBlock() != null) {
            this.baseRegainBlock = basicSetting.getRegainBlock();
            this.regainBlock = this.baseRegainBlock;
        }
        if (basicSetting.getDamageType() != null) {
            this.damageType = basicSetting.getDamageType();
            this.damageTypeForTurn = damageType;
        }
        for (int i = 0; i < ExtraVariable.EXTRA_MAGIC_NUMBER_SIZE; i++) {
            if (basicSetting.getExtraMagicNumber(i) != null) {
                this.baseExtraMagicNumbers[i] = basicSetting.getExtraMagicNumber(i);
                this.extraMagicNumbers[i] =  this.baseExtraMagicNumbers[i];
            }
        }
        
        initializeDescription();
    }



    protected void setUpgradeInfo(UpgradeSetting upgradeSetting) {
        this.upgradeSetting = upgradeSetting;
    }
    
    
    public void addSpCount(int amount) {
    	ArknightsMod.logger.info("{} addSpCount {} + {}", this.toIdString(), spCount, amount);
    	spCount += amount;
        if (isSpCountReachThreshold()) {
            spCount = spThreshold;
        }
        initializeDescription();
    }


    protected void setSpThreshold(Integer spThreshold, GainSpType gainSpType) {
        this.spThreshold = spThreshold;
        this.gainSpType = gainSpType;
    }
    
    public Integer getSpThreshold() {
        return spThreshold;
    }
    
    public int getSpCount() {
        return spCount;
    }
    
    public boolean isSpCountReachThreshold() {
        return spThreshold != -1 && spCount >= spThreshold;
    }

    
    /**
     * 检查各个custom变量组，若又需要，则将customValue重设为baseCustomValue
     */
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
    
    
    protected void handleSpAfterUse() {
        if (isSpCountReachThreshold()) {
            clearSpCount();
        } else {
            if (this.gainSpType == GainSpType.ON_USE) {
                addSpCount(1);
            }
        }
    }
    
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        
        if (this.gainSpType == GainSpType.ON_DRAWN) {
            addSpCount(1);
        }
        
        if (firstTimeDrawn) {
            firstTimeDrawn = false;
            triggerWhenFirstTimeDrawn();
        }
    }
    
    public void triggerWhenFirstTimeDrawn() {}
    
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
            
            updateRawDescriptionByStateAndInitializeDescription();
        }
    }
    
    @Override
    public void applyPowers() {
        
    }
    
    /**
     * 拓展super.applyPowers()
     */
    public void applyPowersToBlock(int tempAddBlock) {
        int originBaseBlock = this.baseBlock;
        this.baseBlock += tempAddBlock;
        
        super.applyPowersToBlock();
        
        this.baseBlock = originBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
        
        // update currentRegainAmount
        currentRegainBlockAmountLimit = MoreGameActionManager.getCurrentRegainBlockAmountLimit();
        applyPowersToRegainBlock();
        
    }
    
    protected void applyPowersToRegainBlock() {
        this.regainBlockModified = false;
        float tmp = this.baseRegainBlock;
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof IModifyRegainBlockPower) {
                tmp = ((IModifyRegainBlockPower)p).modifyRegainBlock(tmp, this);
            }
        }
  
        if (this.baseRegainBlock != MathUtils.floor(tmp)) {
            this.regainBlockModified = true;
        }
        
        if (tmp < 0.0F) {
            tmp = 0.0F;
        }
        this.regainBlock = MathUtils.floor(tmp);
    }
    
    
    /**
     * 拓展super.applyPowers()
     */
    public void applyPowers(int tempAddDamage) {
        int originBaseDamage = this.baseDamage;
        this.baseDamage += tempAddDamage;
        
        super.applyPowers();
        
        this.baseDamage = originBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
        
    }
    
    /**
     * 拓展super.calculateCardDamage(arg0)
     */
    public void calculateCardDamage(AbstractMonster arg0, int tempAddDamage) {
        int originBaseDamage = this.baseDamage;
        this.baseDamage += tempAddDamage;
        
        super.calculateCardDamage(arg0);
        
        this.baseDamage = originBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    
    protected void upgradeExtraMagicNumber(int index, int amount) {
        modifyBaseExtraMagicNumber(index, amount, true);
    }
    
    public int getExtraMagicNumber(int index) {
        return extraMagicNumbers[index];
    }
    
    protected void resetBaseExtraMagicNumber(int index, int resetValue) {
        baseExtraMagicNumbers[index] = resetValue; 
        extraMagicNumbers[index] = baseExtraMagicNumbers[index];
        extraMagicNumberUpgradeds[index] = false;
    }
    
    /**
     * 直接用途是修改baseValue；
     * 如果修改源自upgrade，则Upgraded=true；
     * 因为baseValue被修改，所以value已过时，重置为base，等待重新计算其他加成。
     */
    protected void modifyBaseExtraMagicNumber(int index, int amount, boolean manualUpgraded) {
        baseExtraMagicNumbers[index] += amount; 
        extraMagicNumbers[index] = baseExtraMagicNumbers[index];
        extraMagicNumberUpgradeds[index] = manualUpgraded;
    }
    
    
    
    private void clearSpCount() {
        this.spCount = 0;
        ArknightsMod.logger.info("{} SpCount cleared", this.toIdString());
        initializeDescription();
    }
    
    protected boolean needSetBorderOnGlow() {
        return isSpCountReachThreshold();
    }
    
    @Override
    public void triggerOnGlowCheck() {
        if (needSetBorderOnGlow()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    
    public String toIdString() {
        return this.cardID + "[" + this.uuid + "]";
    }
    
    /**
     * 应把牌组中牌的必要的field传递给抽牌堆中的copy
     */
    @Override
    public AbstractCard makeCopy() {
        ArknightsModCard copy = (ArknightsModCard)super.makeCopy();
        copy.customPostMakeCopy(this);
        return copy;
    }
    
    protected void customPostMakeCopy(ArknightsModCard from) {}
    
    protected void updateNameWithPromotionLevel() {

        if (this.upgraded) {
            if (this.timesUpgraded == 1) {
                this.name = cardStrings.NAME + "+";
            } else {
                this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            }
            
        } else {
            this.name = cardStrings.NAME;
        }

    }
    
    
    
    
    protected void updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState newState) {
        this.rawDescriptionState = newState;
        updateRawDescriptionByStateAndInitializeDescription();
    }
    
    /**
     * 根据rawDescriptionState和upgraded自动计算更新rawDescription，然后initializeDescription()
     */
    protected void updateRawDescriptionByStateAndInitializeDescription() {
        String newRawDescription;
        if (this.upgraded && cardStrings.UPGRADE_DESCRIPTION != null) {
            newRawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            newRawDescription = cardStrings.DESCRIPTION;
        }
        
        if (rawDescriptionState.hasEx0) {
            newRawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        if (rawDescriptionState.hasSpHint) {
            newRawDescription = newRawDescription.replace(RAW_SP_HINT_PLACEHOLDER, RAW_SP_HINT);
        } else {
            newRawDescription = newRawDescription.replace(RAW_SP_HINT_PLACEHOLDER, "");
        }
        if (rawDescriptionState.hasRegainBlockHint) {
            newRawDescription = newRawDescription.replace(RAW_REGAIN_BLOCK_HINT_PLACEHOLDER, RAW_SP_HINT);
        } else {
            newRawDescription = newRawDescription.replace(RAW_REGAIN_BLOCK_HINT_PLACEHOLDER, LocalizationUtils.formatDescription(RAW_REGAIN_BLOCK_HINT, this.currentRegainBlockAmountLimit));
        }
        
        this.rawDescription = newRawDescription;
        this.initializeDescription();
    }



    
    
    

}