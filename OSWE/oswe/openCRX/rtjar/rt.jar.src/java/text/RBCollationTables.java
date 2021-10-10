/*     */ package java.text;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import sun.text.IntHashtable;
/*     */ import sun.text.UCompactIntArray;
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
/*     */ final class RBCollationTables
/*     */ {
/*     */   static final int EXPANDCHARINDEX = 2113929216;
/*     */   static final int CONTRACTCHARINDEX = 2130706432;
/*     */   static final int UNMAPPED = -1;
/*     */   static final int PRIMARYORDERMASK = -65536;
/*     */   static final int SECONDARYORDERMASK = 65280;
/*     */   static final int TERTIARYORDERMASK = 255;
/*     */   static final int PRIMARYDIFFERENCEONLY = -65536;
/*     */   static final int SECONDARYDIFFERENCEONLY = -256;
/*     */   static final int PRIMARYORDERSHIFT = 16;
/*     */   static final int SECONDARYORDERSHIFT = 8;
/*     */   private String rules;
/*     */   private boolean frenchSec;
/*     */   private boolean seAsianSwapping;
/*     */   private UCompactIntArray mapping;
/*     */   private Vector<Vector<EntryPair>> contractTable;
/*     */   private Vector<int[]> expandTable;
/*     */   private IntHashtable contractFlags;
/*     */   private short maxSecOrder;
/*     */   private short maxTerOrder;
/*     */   
/*     */   public RBCollationTables(String paramString, int paramInt) throws ParseException {
/* 290 */     this.rules = null;
/* 291 */     this.frenchSec = false;
/* 292 */     this.seAsianSwapping = false;
/*     */     
/* 294 */     this.mapping = null;
/* 295 */     this.contractTable = null;
/* 296 */     this.expandTable = null;
/* 297 */     this.contractFlags = null;
/*     */     
/* 299 */     this.maxSecOrder = 0;
/* 300 */     this.maxTerOrder = 0;
/*     */     this.rules = paramString;
/*     */     RBTableBuilder rBTableBuilder = new RBTableBuilder(new BuildAPI());
/*     */     rBTableBuilder.build(paramString, paramInt);
/*     */   }
/*     */   
/*     */   final class BuildAPI {
/*     */     private BuildAPI() {}
/*     */     
/*     */     void fillInTables(boolean param1Boolean1, boolean param1Boolean2, UCompactIntArray param1UCompactIntArray, Vector<Vector<EntryPair>> param1Vector, Vector<int[]> param1Vector1, IntHashtable param1IntHashtable, short param1Short1, short param1Short2) {
/*     */       RBCollationTables.this.frenchSec = param1Boolean1;
/*     */       RBCollationTables.this.seAsianSwapping = param1Boolean2;
/*     */       RBCollationTables.this.mapping = param1UCompactIntArray;
/*     */       RBCollationTables.this.contractTable = param1Vector;
/*     */       RBCollationTables.this.expandTable = param1Vector1;
/*     */       RBCollationTables.this.contractFlags = param1IntHashtable;
/*     */       RBCollationTables.this.maxSecOrder = param1Short1;
/*     */       RBCollationTables.this.maxTerOrder = param1Short2;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getRules() {
/*     */     return this.rules;
/*     */   }
/*     */   
/*     */   public boolean isFrenchSec() {
/*     */     return this.frenchSec;
/*     */   }
/*     */   
/*     */   public boolean isSEAsianSwapping() {
/*     */     return this.seAsianSwapping;
/*     */   }
/*     */   
/*     */   Vector<EntryPair> getContractValues(int paramInt) {
/*     */     int i = this.mapping.elementAt(paramInt);
/*     */     return getContractValuesImpl(i - 2130706432);
/*     */   }
/*     */   
/*     */   private Vector<EntryPair> getContractValuesImpl(int paramInt) {
/*     */     if (paramInt >= 0)
/*     */       return this.contractTable.elementAt(paramInt); 
/*     */     return null;
/*     */   }
/*     */   
/*     */   boolean usedInContractSeq(int paramInt) {
/*     */     return (this.contractFlags.get(paramInt) == 1);
/*     */   }
/*     */   
/*     */   int getMaxExpansion(int paramInt) {
/*     */     int i = 1;
/*     */     if (this.expandTable != null)
/*     */       for (byte b = 0; b < this.expandTable.size(); b++) {
/*     */         int[] arrayOfInt = this.expandTable.elementAt(b);
/*     */         int j = arrayOfInt.length;
/*     */         if (j > i && arrayOfInt[j - 1] == paramInt)
/*     */           i = j; 
/*     */       }  
/*     */     return i;
/*     */   }
/*     */   
/*     */   final int[] getExpandValueList(int paramInt) {
/*     */     return this.expandTable.elementAt(paramInt - 2113929216);
/*     */   }
/*     */   
/*     */   int getUnicodeOrder(int paramInt) {
/*     */     return this.mapping.elementAt(paramInt);
/*     */   }
/*     */   
/*     */   short getMaxSecOrder() {
/*     */     return this.maxSecOrder;
/*     */   }
/*     */   
/*     */   short getMaxTerOrder() {
/*     */     return this.maxTerOrder;
/*     */   }
/*     */   
/*     */   static void reverse(StringBuffer paramStringBuffer, int paramInt1, int paramInt2) {
/*     */     int i = paramInt1;
/*     */     int j = paramInt2 - 1;
/*     */     while (i < j) {
/*     */       char c = paramStringBuffer.charAt(i);
/*     */       paramStringBuffer.setCharAt(i, paramStringBuffer.charAt(j));
/*     */       paramStringBuffer.setCharAt(j, c);
/*     */       i++;
/*     */       j--;
/*     */     } 
/*     */   }
/*     */   
/*     */   static final int getEntry(Vector<EntryPair> paramVector, String paramString, boolean paramBoolean) {
/*     */     for (byte b = 0; b < paramVector.size(); b++) {
/*     */       EntryPair entryPair = paramVector.elementAt(b);
/*     */       if (entryPair.fwd == paramBoolean && entryPair.entryName.equals(paramString))
/*     */         return b; 
/*     */     } 
/*     */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/RBCollationTables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */