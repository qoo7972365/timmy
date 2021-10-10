/*     */ package javax.sound.midi.spi;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.sound.midi.Sequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MidiFileWriter
/*     */ {
/*     */   public abstract int[] getMidiFileTypes();
/*     */   
/*     */   public abstract int[] getMidiFileTypes(Sequence paramSequence);
/*     */   
/*     */   public boolean isFileTypeSupported(int paramInt) {
/*  74 */     int[] arrayOfInt = getMidiFileTypes();
/*  75 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  76 */       if (paramInt == arrayOfInt[b]) {
/*  77 */         return true;
/*     */       }
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFileTypeSupported(int paramInt, Sequence paramSequence) {
/*  94 */     int[] arrayOfInt = getMidiFileTypes(paramSequence);
/*  95 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  96 */       if (paramInt == arrayOfInt[b]) {
/*  97 */         return true;
/*     */       }
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public abstract int write(Sequence paramSequence, int paramInt, OutputStream paramOutputStream) throws IOException;
/*     */   
/*     */   public abstract int write(Sequence paramSequence, int paramInt, File paramFile) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/spi/MidiFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */