/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import javax.sound.midi.Instrument;
/*    */ import javax.sound.midi.Patch;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ModelInstrumentComparator
/*    */   implements Comparator<Instrument>
/*    */ {
/*    */   public int compare(Instrument paramInstrument1, Instrument paramInstrument2) {
/* 40 */     Patch patch1 = paramInstrument1.getPatch();
/* 41 */     Patch patch2 = paramInstrument2.getPatch();
/* 42 */     int i = patch1.getBank() * 128 + patch1.getProgram();
/* 43 */     int j = patch2.getBank() * 128 + patch2.getProgram();
/* 44 */     if (patch1 instanceof ModelPatch) {
/* 45 */       i += ((ModelPatch)patch1).isPercussion() ? 2097152 : 0;
/*    */     }
/* 47 */     if (patch2 instanceof ModelPatch) {
/* 48 */       j += ((ModelPatch)patch2).isPercussion() ? 2097152 : 0;
/*    */     }
/* 50 */     return i - j;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/ModelInstrumentComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */