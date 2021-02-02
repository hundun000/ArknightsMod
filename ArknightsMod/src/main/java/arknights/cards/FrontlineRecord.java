package arknights.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import arknights.ArknightsMod;
import arknights.cards.base.ArknightsModCard;
import arknights.cards.base.component.BasicSetting;
import arknights.cards.base.component.UpgradeSetting;
import arknights.relics.BattleRecords;

/**
 * @author hundun
 * Created on 2020/11/24
 */
public class FrontlineRecord extends ArknightsModCard {
    
    public static final String ID = ArknightsMod.makeID(FrontlineRecord.class.getSimpleName()); 
    public static final String IMG = ArknightsMod.makeCardPngPath(ArknightsModCard.class);

    private static final CardRarity RARITY = CardRarity.UNCOMMON; 
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;  
    private static final CardType TYPE = CardType.ATTACK;       

    private static final int COST = 1;

    
    public FrontlineRecord() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        initBaseFields(new BasicSetting()
                .setMagicNumber(2)
                );
        setUpgradeInfo(new UpgradeSetting()
                .setPlusMagicNumber(2)
                );
        this.exhaust = true;
    }
    
    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (AbstractDungeon.player.hasRelic(BattleRecords.ID)) {
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                    break;
                } 
            }
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (player.hasRelic(BattleRecords.ID)) {
            BattleRecords relic = (BattleRecords) player.getRelic(BattleRecords.ID);
            if (!monster.isDeadOrEscaped() && monster.getIntentBaseDmg() >= 0) {
                relic.addCounter(2 * magicNumber);
            } else {
                relic.addCounter(magicNumber);
            }
            AbstractDungeon.player.getRelic(BattleRecords.ID).flash();
        }
    }
 
}
