package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.actions.RegainBlockAction;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.ArknightsModCard.GainSpType;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.manager.MoreGameActionManager;
import arknights.util.LocalizationUtils;
import arknights.variables.ExtraVariable;

/**
 * @author hundun
 * Created on 2020/12/02
 */
public class SpotDefend extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(SpotDefend.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.SELF;  
    private static final CardType TYPE = CardType.SKILL;       

    private static final int COST = 1;
    
    public SpotDefend() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setBlock(8)
                .setRegainBlock(5)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusBlock(3)
                );
        setSpThreshold(4, GainSpType.ON_DRAWN);
        
        initializeDescription();
    }

    
    @Override
    public void triggerWhenFirstTimeDrawn() {
        super.triggerWhenFirstTimeDrawn();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT_AND_REGAIN_BLOCK_HINT);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        
        
        addToBot(new GainBlockAction(player, player, block));

        if (isSpCountReachThreshold()) {
            addToBot(new RegainBlockAction(player, this.regainBlock, currentRegainBlockAmountLimit));
        }
        handleSpAfterUse();
    }

    
    @Override
    protected boolean needSetBorderOnGlow() {
        return isSpCountReachThreshold();
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        StringBuilder before = new StringBuilder();
        ArknightsMod.logger.info("before this.cardStrings.DESCRIPTION = {}", this.cardStrings.DESCRIPTION);
        ArknightsMod.logger.info("before this.rawDescription = {}", this.rawDescription);
        this.description.forEach(item -> before.append(item.text));
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT_AND_REGAIN_BLOCK_HINT);
        StringBuilder after = new StringBuilder();
        this.description.forEach(item -> after.append(item.text));
        ArknightsMod.logger.info("{} description init.", this.toIdString());
        ArknightsMod.logger.info("from = {}", before.toString());
        ArknightsMod.logger.info("to = {}", after.toString());
        ArknightsMod.logger.info("after this.cardStrings.DESCRIPTION = {}", this.cardStrings.DESCRIPTION);
        ArknightsMod.logger.info("after this.rawDescription = {}", this.rawDescription);
    }
   
    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        
        updateRawDescriptionByStateAndInitializeDescription(RawDescriptionState.BASE_AND_SP_HINT);
    }

}
