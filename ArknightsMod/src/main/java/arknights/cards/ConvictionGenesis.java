package arknights.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsCardTag;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.CardTemplant;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;

/**
 * @author hundun
 * Created on 2021/02/27
 */
public class ConvictionGenesis extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(ConvictionGenesis.class); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    
    public ConvictionGenesis() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setDamage(5)
                );
        setUpgradeInfo(new UpgradeSetting()
                
                );
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        boolean success = AbstractDungeon.cardRandomRng.randomBoolean();
        if (success) {
            addToBot(new DamageAllEnemiesAction(player, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
            boolean fightingBoss = (monster.type == AbstractMonster.EnemyType.BOSS);
            if (!fightingBoss) {
                for (AbstractPower pow : monster.powers) {
                    if (pow.type == AbstractPower.PowerType.BUFF) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, player, pow));
                        break;
                    }
                }
            }
        } else {
            addToBot((AbstractGameAction)new PressEndTurnButtonAction());
        }
    }

}
