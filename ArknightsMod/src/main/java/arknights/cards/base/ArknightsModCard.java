package arknights.cards.base;
import basemod.AutoAdd;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.characters.ArknightsPlayer;
import arknights.manager.MoreGameActionManager;
import arknights.powers.IModifyRegainBlockPower;
import arknights.util.LocalizationUtils;
import arknights.util.Utils;
import arknights.variables.ExtraVariable;

public abstract class ArknightsModCard extends CustomCard {
    
    
	protected final static DamageType SPELL_DAMAGE_TYPE = DamageType.HP_LOSS;
	
	private static final CardStrings PUBLIC_CARDSTRINGS = CardCrawlGame.languagePack.getCardStrings(ArknightsMod.makeID(ArknightsModCard.class));
    private static final String RAW_SP_HINT = PUBLIC_CARDSTRINGS.EXTENDED_DESCRIPTION[0];
    private static final String RAW_REGAIN_BLOCK_HINT = PUBLIC_CARDSTRINGS.EXTENDED_DESCRIPTION[1];
    private static final String RAW_USE_TIMES_HINT = PUBLIC_CARDSTRINGS.EXTENDED_DESCRIPTION[2];
    
    public static final Color SP_TEXT_COLOR = new Color(0x0BFB7CFF);
    
    private static final String RAW_SP_HINT_PLACEHOLDER = "{SPC_HINT}";
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
    
    protected int useTimes;
    public List<AbstractCard> moreCardsToPreview = new ArrayList<>();
    
    protected GainSpType gainSpType;
    
    public enum GainSpType {
        NO_SP,
        ON_DRAWN,
        ON_USE,
        
    }
    
    protected RawDescriptionState rawDescriptionState;
    
    public enum RawDescriptionState {
        BASE(false, false, false, false),
        BASE_AND_SP_HINT(true, false, false, false),
        BASE_AND_USE_TIMES_HINT(true, false, false, true),
        BASE_AND_REGAIN_BLOCK_HINT(false, true, false, false),
        BASE_AND_SP_HINT_AND_REGAIN_BLOCK_HINT(true, true, false, false),
        BASE_AND_SP_HINT_AND_EX0(true, false, true, false),
        ;
        
        public final boolean hasSpHint;
        public final boolean hasRegainBlockHint;
        public final boolean hasEx0;
        public final boolean hasUseTimes;
        
        private RawDescriptionState(
                boolean hasSpHint, 
                boolean hasRegainBlockHint, 
                boolean hasEx0,
                boolean hasUseTimes
                ) {
            this.hasSpHint = hasSpHint;
            this.hasRegainBlockHint = hasRegainBlockHint;
            this.hasEx0 = hasEx0;
            this.hasUseTimes = hasUseTimes;
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
        this.useTimes = 0;
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
        if (basicSetting.isSpellDamageType()) {
            this.damageType = SPELL_DAMAGE_TYPE;
            this.damageTypeForTurn = damageType;
            this.tags.add(ArknightsCardTag.SPELL_DAMAGE);
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


    protected void initSpThreshold(Integer spThreshold, GainSpType gainSpType) {
        this.spThreshold = spThreshold;
        this.gainSpType = gainSpType;
        initializeDescription();
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
    	if (!canUpgrade()) {
    		return;
    	}
    	
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
    
    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();
        
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
        
        ArknightsMod.logger.info("{} applyPowers done. this.damage = {}, this.block = {}, this.regainBlock = {}", this.toIdString(), this.damage, this.block, this.regainBlock);
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
        
        ArknightsMod.logger.info("{} calculateCardDamage to {} done. this.damage = {}, this.block = {}, this.regainBlock = {}", this.toIdString(), this.damage, this.block, this.regainBlock);
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
        ArknightsMod.logger.info("{} makeCopy {}. caller = {}", this.toIdString(), copy.toIdString(), Utils.getCallerInfo());
        return copy;
    }
    
    protected void customPostMakeCopy(ArknightsModCard from) {
    	this.spCount = from.spCount;
    	this.spThreshold = from.spThreshold;
    }
    
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
    
    protected String getFormattedExtendDescription(int index) {
		return cardStrings.EXTENDED_DESCRIPTION[index];
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
            newRawDescription += getFormattedExtendDescription(0);
        }
        if (rawDescriptionState.hasUseTimes) {
            newRawDescription += LocalizationUtils.formatDescription(RAW_USE_TIMES_HINT, this.useTimes);
        }
        if (rawDescriptionState.hasSpHint) {
            newRawDescription = newRawDescription.replace(RAW_SP_HINT_PLACEHOLDER, RAW_SP_HINT);
        } else {
            newRawDescription = newRawDescription.replace(RAW_SP_HINT_PLACEHOLDER, "");
        }
        if (rawDescriptionState.hasRegainBlockHint) {
            newRawDescription = newRawDescription.replace(RAW_REGAIN_BLOCK_HINT_PLACEHOLDER, LocalizationUtils.formatDescription(RAW_REGAIN_BLOCK_HINT, this.currentRegainBlockAmountLimit));
        } else {
            newRawDescription = newRawDescription.replace(RAW_REGAIN_BLOCK_HINT_PLACEHOLDER, "");
        }
        
        this.rawDescription = newRawDescription;
        this.initializeDescription();
        
        if (ArknightsMod.DEBUG_DESCROPTION) ArknightsMod.logger.info("{} updateRawDescription done; \n"
                + "this.rawDescription = {}; \n"
                + "this.descriptionToString = {}; \n", this.toIdString(), this.rawDescription, descriptionToString(this.description));
    }

    @Override
    public void initializeDescriptionCN() {
        if (ArknightsMod.DEBUG_DESCROPTION) ArknightsMod.logger.info("{} before initializeDescriptionCN, this.rawDescription.split(\" \") = {}", this.toIdString(), this.rawDescription.split(" "));
        super.initializeDescriptionCN();
        if (ArknightsMod.DEBUG_DESCROPTION) ArknightsMod.logger.info("{} after initializeDescriptionCN, his.descriptionToString = {}", this.toIdString(), descriptionToString(this.description));
    }
    
    
//    @Override
//    public void render(SpriteBatch sb, boolean selected) {
//        ArknightsMod.logger.info("{} before render; \n"
//                + "this.descriptionToString = {}; \n", this.toIdString(), descriptionToString(this.description));
//        super.render(sb, selected);
//    }
    
    public static String descriptionToString(ArrayList<DescriptionLine> description) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < description.size(); i++) {
            DescriptionLine line = description.get(i);
            builder.append("line").append(i).append(":").append(Arrays.toString(line.getCachedTokenizedTextCN())).append("\n");
        }
        return builder.toString();
    }


    
    private Color renderSpGainTypeColor = Color.WHITE.cpy();
    
    
    /**
     * call by patch
     * @param sb
     */
    public void renderSpForSingleCardViewPopup(SpriteBatch sb, boolean forceRender) {
        boolean darken = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "darken");
        boolean renderCondition = this.spThreshold > 0 && !darken && !this.isLocked && this.isSeen;
        if (forceRender || renderCondition) {
        	float baseX = Settings.WIDTH / 2.0F;
        	float baseY = Settings.HEIGHT / 2.0F;
        	float gainSpTypeXOffset = (280.0F  - 2.0F * 32.0F) * Settings.scale;
        	float gainSpTypeYOffset = (380.0F + 2.0F * 32.0F) * Settings.scale;
        	float gainSpCountXOffset = (280.0F) * Settings.scale;
        	float gainSpCountYOffset = (380.0F) * Settings.scale;
        	float rotatedTextAngle = 0.0F;
        	BitmapFont spTypeFont = FontHelper.SCP_cardDescFont;
        	spTypeFont.getData().setScale(1.0F);
        	BitmapFont spCountFont = FontHelper.SCP_cardEnergyFont;
        	spTypeFont.getData().setScale(1.0F);
        	float spTypeBackImageScale = 3.0F * Settings.scale;
            renderSp(sb, baseX, baseY, gainSpTypeXOffset, gainSpTypeYOffset, rotatedTextAngle, gainSpCountXOffset, gainSpCountYOffset, spTypeFont, spCountFont, spTypeBackImageScale);
        }
    }
    
    /**
     * call by patch
     * @param sb
     */
    public void renderSpForCard(SpriteBatch sb, boolean forceRender) {
        boolean darken = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "darken");
        boolean renderCondition = this.spThreshold > 0 && !darken && !this.isLocked && this.isSeen;
        if (forceRender || renderCondition) {
        	float baseX = this.current_x;
        	float baseY = this.current_y;
        	float gainSpTypeXOffset = (125.0F  - 32.0F) * this.drawScale * Settings.scale;
        	float gainSpTypeYOffset = (180.0F + 32.0F) * this.drawScale * Settings.scale;
        	float rotatedTextAngle = this.angle;
        	float gainSpCountXOffset = (125.0F) * this.drawScale * Settings.scale;
        	float gainSpCountYOffset = (180.0F) * this.drawScale * Settings.scale;
        	BitmapFont spTypeFont = FontHelper.cardDescFont_N;
        	spTypeFont.getData().setScale(this.drawScale);
        	BitmapFont spCountFont = FontHelper.cardEnergyFont_L;
        	spCountFont.getData().setScale(this.drawScale);
        	float spTypeBackImageScale = 1.5F * this.drawScale * Settings.scale;
			renderSp(sb, baseX, baseY, gainSpTypeXOffset, gainSpTypeYOffset, rotatedTextAngle, gainSpCountXOffset, gainSpCountYOffset, spTypeFont, spCountFont, spTypeBackImageScale);
        }
    }
    
    /**
     * call by patch
     * @param sb
     * @param spCountFont 
     * @param spTypeFont 
     */
    private void renderSp(SpriteBatch sb, float baseX, float baseY, float gainSpTypeXOffset, float gainSpTypeYOffset, float rotatedTextAngle,
    		float gainSpCountXOffset, float gainSpCountYOffset, BitmapFont spTypeFont, BitmapFont spCountFont, float spTypeBackImageScale) {

    		
    		
    	
            AtlasRegion gainSpTypeIcon = null;
            String gainSpTypeText = null;
            if (this.gainSpType == GainSpType.ON_USE) {
            	gainSpTypeText = "抽牌回复";
                gainSpTypeIcon = ArknightsMod.GAIN_SP_ON_USE_ATLAS;
                
            } else if (this.gainSpType == GainSpType.ON_DRAWN) {
            	gainSpTypeText = "出牌回复";
                gainSpTypeIcon = ArknightsMod.GAIN_SP_ON_DRAW_ATLAS;
            }
            if (gainSpTypeIcon != null && gainSpTypeText != null) {
            	renderHelper(sb, this.renderSpGainTypeColor, gainSpTypeIcon, baseX + gainSpTypeXOffset, baseY + gainSpTypeYOffset, spTypeBackImageScale);
                FontHelper.renderRotatedText(sb, spTypeFont, gainSpTypeText, baseX, baseY, gainSpTypeXOffset, gainSpTypeYOffset, rotatedTextAngle, false, renderSpGainTypeColor);
            }
            
            
            Color spColor = SP_TEXT_COLOR; 
            String spCountText = "";
            
            if (this.rawDescriptionState.hasSpHint) {
                spCountText += this.spCount + "/" + this.spThreshold;
            } else {
                spCountText += "" + this.spThreshold;
            }
            if (spCountText.length() > 0) {
                FontHelper.renderRotatedText(sb, spCountFont, spCountText, baseX, baseY, gainSpCountXOffset, gainSpCountYOffset, rotatedTextAngle, false, spColor);
            }
    }
    
    
    @Override
    public void renderCardPreview(SpriteBatch sb) {
        super.renderCardPreview(sb);
        
        if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
            return;
        }
        renderMoreCardPreviews(sb);
        ArknightsMod.logger.info("render by Preview: {}", this.toString());
    }
    
    protected void renderMoreCardPreviews(SpriteBatch sb) {
        for (int i = 0; i < moreCardsToPreview.size(); i++) {
            AbstractCard cardToPreview = moreCardsToPreview.get(i);
            cardToPreview.current_x = (this.current_x - (0 + 1) * RAW_W) * Settings.scale;
            cardToPreview.current_y = (this.current_y + (i + 1) * RAW_H) * Settings.scale;
            cardToPreview.drawScale = 0.8F;
            cardToPreview.render(sb);
        }
    }
    
    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        super.renderCardPreviewInSingleView(sb);
        renderMoreCardPreviewsInSingleView(sb);
    }
    
    protected void renderMoreCardPreviewsInSingleView(SpriteBatch sb) {
        ArknightsMod.logger.info("render by SingleView: {}", this.toString());
        for (int i = 0; i < moreCardsToPreview.size(); i++) {
            AbstractCard cardToPreview = moreCardsToPreview.get(i);
            
            cardToPreview.current_x = (1435.0F + (-1 + 1) * RAW_W) * Settings.scale;
            cardToPreview.current_y = (795.0F + (i + 1) * RAW_H) * Settings.scale;
            cardToPreview.drawScale = 0.8F;
            cardToPreview.render(sb);
        }
    }
    
    private void renderHelper(SpriteBatch sb, Color color, TextureAtlas.AtlasRegion img, float drawX, float drawY, float scale) {
    	/* 1427 */     sb.setColor(color);
    	/* 1428 */     sb.draw((TextureRegion)img, drawX + img.offsetX - img.originalWidth / 2.0F, drawY + img.offsetY - img.originalHeight / 2.0F, img.originalWidth / 2.0F - img.offsetX, img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, scale, scale, this.angle);
    	/*      */   }
    
    
    private BitmapFont getDescFont() {
    	/* 2554 */     BitmapFont font = null;
    	/*      */     
    	/* 2556 */     if (this.angle == 0.0F && this.drawScale == 1.0F) {
    	/* 2557 */       font = FontHelper.cardDescFont_N;
    	/*      */     } else {
    	/* 2559 */       font = FontHelper.cardDescFont_L;
    	/*      */     } 
    	/*      */     
    	/* 2562 */     font.getData().setScale(this.drawScale);
    	/* 2563 */     return font;
    	/*      */   }

}