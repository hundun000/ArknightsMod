package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.AbstractModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.powers.DisplacementPower;
import basemod.AutoAdd;

/**
 * @author hundun
 * Created on 2020/11/19
 */
@AutoAdd.Ignore
@Deprecated
public class DisplacementTrap extends AbstractModCard {

    public static final String ID = ArknightsMod.makeID(DisplacementTrap.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(AbstractModCard.class);
    private static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        
    private static final CardRarity RARITY = CardRarity.COMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       
    private static final int COST = 1;
    
    
    public DisplacementTrap() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                .setBlock(0)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusDamage(3)
                );
    }

    @Override
    public void calculateCardDamage(AbstractMonster monster) {
      //super.calculateCardDamage(mo);
      int count = getCounter(monster);
      modifyBlock(count);
      this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
      initializeDescription();
      ArknightsMod.logger.info(
          "DisplacementTrap : applyPowers : damage :"
              + this.damage
              + " ; counter : " + count
              + " ; block :" + this.block
      );
    }

    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
      int count = getCounter(monster);
      for (int i = 0; i < count; i++) {
          addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
      }
    }
    
    @Override
    public void onMoveToDiscard() {
      this.rawDescription = DESCRIPTION;
      initializeDescription();
    }

    private int getCounter(AbstractMonster monster) {
      int count = 0;
      if (monster.hasPower(DisplacementPower.POWER_ID)) { 
          count += monster.getPower(DisplacementPower.POWER_ID).amount;
      }
      if (upgraded) {
          count++;
      }
      return count;
    }

    private void modifyBlock(int count) {
      if (count > 0) {
        this.isBlockModified = true;
        this.block = this.baseBlock = count;
      } else {
        this.isBlockModified = false;
        this.block = this.baseBlock = 0;
      }
    }

}
