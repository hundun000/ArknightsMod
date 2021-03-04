package arknights.monster;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import arknights.ArknightsMod;

/**
 * @author hundun
 * Created on 2020/12/11
 */
public class Puncturer extends AbstractMonster {
    /*     */   public static final String ID = ArknightsMod.makeID(Puncturer.class);
    /*  23 */   private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    /*  24 */   public static final String NAME = monsterStrings.NAME;
    /*  25 */   public static final String[] MOVES = monsterStrings.MOVES;
    /*  26 */   public static final String[] DIALOG = monsterStrings.DIALOG; 
                //public static final String MURDER_ENCOUNTER_KEY = "Murder of Cultists"; 
                private static final int HP_MIN = 48; 
                private static final int HP_MAX = 54; 
                private static final int A_2_HP_MIN = 50; 
                private static final int A_2_HP_MAX = 56; 
                private static final float HB_X = -8.0F;
    /*     */   private static final float HB_Y = 10.0F;
    /*     */   private static final float HB_W = 230.0F;
    /*  29 */   private static final String PREPARE_TALK_TEXT = MOVES[2];
    /*     */   
    /*     */   private static final float HB_H = 240.0F;
    /*     */   
    /*     */   private static final int ATTACK_DMG = 6;
    /*     */   
    /*     */   private static final int RITUAL_AMT = 3;
    /*     */   private static final int A_2_RITUAL_AMT = 4;
    /*  39 */   private int addDamageSpeed = 0; 
                private static final byte DARK_STRIKE = 1;
    /*     */   private static final byte INCANTATION = 3;
    /*     */   private boolean talky = true;
    /*     */   
    
    private static final int INTANGIBLE_POWER_TURN = 2;
    
    private int turnCount = 0;
    private enum AnimationState {
        WAVING("waving")
        ;
        private final String id;
        AnimationState(String id) {
            this.id = id;
        }
        
        public String getId() {
            return id;
        }
    }
    
    
    
    /*     */   public Puncturer(float x, float y, boolean talk) {
    /*  44 */     super(NAME, ID, 54, HB_X, HB_Y, HB_W, HB_H, null, x, y);
    /*     */     
    /*  46 */     if (AbstractDungeon.ascensionLevel >= 7) {
    /*  47 */       setHp(50, 56);
    /*     */     } else {
    /*  49 */       setHp(48, 54);
    /*     */     } 
    /*     */     
    /*  52 */     this.dialogX = -50.0F * Settings.scale;
    /*  53 */     this.dialogY = 50.0F * Settings.scale;
    /*     */     
    /*  55 */     if (AbstractDungeon.ascensionLevel >= 2) {
    /*  56 */       this.addDamageSpeed = 3;
    /*     */     } else {
    /*  58 */       this.addDamageSpeed = 2;
    /*     */     } 
    /*     */     
    /*  61 */     this.damage.add(new DamageInfo(this, 1));
    /*     */     
    /*  63 */     this.talky = talk;
    /*  64 */     if (Settings.FAST_MODE) {
    /*  65 */       this.talky = false;
    /*     */     }
    /*     */     
    /*  68 */     loadAnimation(ArknightsMod.makeMonsterSkeletonAtlasPath(this.getClass()), ArknightsMod.makeMonsterSkeletonJsonPath(this.getClass()), 1.0F);
    /*     */ 
    /*     */ 
    /*     */     
    /*  72 */     TrackEntry e = this.state.setAnimation(0, AnimationState.WAVING.getId(), true);
    /*  73 */     e.setTime(e.getEndTime() * MathUtils.random());
    /*     */   }
    /*     */   
    /*     */   public Puncturer(float x, float y) {
    /*  77 */     this(x, y, true);
    /*     */   }
    /*     */    
    
    @Override
    public void usePreBattleAction() {
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, INTANGIBLE_POWER_TURN)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RitualPower(this, this.addDamageSpeed, false)));
    }
    
    @Override
    public void takeTurn() {
        this.turnCount++;
        
        
    /*     */     int temp;
    /*  82 */     switch (this.nextMove) {
    /*     */       case PREPARE_MOVE_CODE:
    /*  84 */         temp = MathUtils.random(1, 10);
    /*  85 */         if (this.talky) {
    /*  86 */           playSfx();
    /*  87 */           if (temp < 4) {
    /*  88 */             AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 2.0F));
    /*  90 */           } else if (temp < 7) {
    /*  91 */             AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1], 1.0F, 2.0F));
    /*     */           } 
    /*     */         } 
    /*  94 */         break;
    /*     */ 
    /*     */       
    /*     */       case ATTACK_MOVE_CODE:
    /* 103 */         AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
    /* 104 */         AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage
    /* 105 */               .get(0), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    /*     */         break;
    /*     */     } 
    /*     */ 
    /*     */ 
    /*     */     
    /* 111 */     AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    /*     */   }
    /*     */   
    /*     */   private void playSfx() {
//    /* 115 */     int roll = MathUtils.random(2);
//    /* 116 */     if (roll == 0) {
    /* 117 */       AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
//    /* 118 */     } else if (roll == 1) {
//    /* 119 */       AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_CULTIST_1B"));
//    /*     */     } else {
//    /* 121 */       AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_CULTIST_1C"));
//    /*     */     } 
    /*     */   }
    /*     */   
    /*     */   private void playDeathSfx() {
//    /* 126 */     int roll = MathUtils.random(2);
//    /* 127 */     if (roll == 0) {
    /* 128 */       CardCrawlGame.sound.play("VO_CULTIST_2A");
//    /* 129 */     } else if (roll == 1) {
//    /* 130 */       CardCrawlGame.sound.play("VO_CULTIST_2B");
//    /*     */     } else {
//    /* 132 */       CardCrawlGame.sound.play("VO_CULTIST_2C");
//    /*     */     } 
    /*     */   }
    /*     */ 
    /*     */   
    /*     */   @Override
    public void die() {
    /* 138 */     playDeathSfx();
    /* 139 */     this.state.setTimeScale(0.1F);
    /* 140 */     useShakeAnimation(5.0F);
    /* 141 */     if (this.talky) {
    /* 143 */       AbstractDungeon.effectList.add(new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[2], false));
    /*     */ 
    /*     */       
    /* 146 */       this.deathTimer += 1.5F;
    /*     */     } 
    /*     */     
    /* 149 */     super.die();
    /*     */   }

    private static final byte PREPARE_MOVE_CODE = (byte)3;
    private static final byte ATTACK_MOVE_CODE = (byte)1;
     
    @Override
    protected void getMove(int num) {
        if (this.turnCount < INTANGIBLE_POWER_TURN) {
            setMove(PREPARE_TALK_TEXT, PREPARE_MOVE_CODE, AbstractMonster.Intent.BUFF);
        } else {
            setMove(ATTACK_MOVE_CODE, AbstractMonster.Intent.ATTACK, this.damage.get(0).base);
        }
    }
}
