/*     */ package sun.text.normalizer;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ public class UnicodeSetIterator
/*     */ {
/*  81 */   public static int IS_STRING = -1;
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
/*     */   public int codepoint;
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
/*     */   public int codepointEnd;
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
/*     */   public String string;
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
/*     */   private UnicodeSet set;
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
/*     */   private int endRange;
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
/*     */   private int range;
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
/*     */   protected int endElement;
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
/*     */   protected int nextElement;
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
/*     */   private Iterator<String> stringIterator;
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
/*     */   public UnicodeSetIterator(UnicodeSet paramUnicodeSet) {
/* 196 */     this.endRange = 0;
/* 197 */     this.range = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     this.stringIterator = null;
/*     */     reset(paramUnicodeSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadRange(int paramInt) {
/* 216 */     this.nextElement = this.set.getRangeStart(paramInt);
/* 217 */     this.endElement = this.set.getRangeEnd(paramInt);
/*     */   }
/*     */   
/*     */   public boolean nextRange() {
/*     */     if (this.nextElement <= this.endElement) {
/*     */       this.codepointEnd = this.endElement;
/*     */       this.codepoint = this.nextElement;
/*     */       this.nextElement = this.endElement + 1;
/*     */       return true;
/*     */     } 
/*     */     if (this.range < this.endRange) {
/*     */       loadRange(++this.range);
/*     */       this.codepointEnd = this.endElement;
/*     */       this.codepoint = this.nextElement;
/*     */       this.nextElement = this.endElement + 1;
/*     */       return true;
/*     */     } 
/*     */     if (this.stringIterator == null)
/*     */       return false; 
/*     */     this.codepoint = IS_STRING;
/*     */     this.string = this.stringIterator.next();
/*     */     if (!this.stringIterator.hasNext())
/*     */       this.stringIterator = null; 
/*     */     return true;
/*     */   }
/*     */   
/*     */   public void reset(UnicodeSet paramUnicodeSet) {
/*     */     this.set = paramUnicodeSet;
/*     */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*     */     this.endRange = this.set.getRangeCount() - 1;
/*     */     this.range = 0;
/*     */     this.endElement = -1;
/*     */     this.nextElement = 0;
/*     */     if (this.endRange >= 0)
/*     */       loadRange(this.range); 
/*     */     this.stringIterator = null;
/*     */     if (this.set.strings != null) {
/*     */       this.stringIterator = this.set.strings.iterator();
/*     */       if (!this.stringIterator.hasNext())
/*     */         this.stringIterator = null; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/UnicodeSetIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */