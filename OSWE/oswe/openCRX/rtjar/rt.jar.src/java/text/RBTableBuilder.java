/*     */ package java.text;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import sun.text.ComposedCharIter;
/*     */ import sun.text.IntHashtable;
/*     */ import sun.text.UCompactIntArray;
/*     */ import sun.text.normalizer.NormalizerImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RBTableBuilder
/*     */ {
/*     */   static final int CHARINDEX = 1879048192;
/*     */   private static final int IGNORABLEMASK = 65535;
/*     */   private static final int PRIMARYORDERINCREMENT = 65536;
/*     */   private static final int SECONDARYORDERINCREMENT = 256;
/*     */   private static final int TERTIARYORDERINCREMENT = 1;
/*     */   private static final int INITIALTABLESIZE = 20;
/*     */   private static final int MAXKEYSIZE = 5;
/*     */   private RBCollationTables.BuildAPI tables;
/*     */   private MergeCollation mPattern;
/*     */   private boolean isOverIgnore;
/*     */   private char[] keyBuf;
/*     */   private IntHashtable contractFlags;
/*     */   private boolean frenchSec;
/*     */   private boolean seAsianSwapping;
/*     */   private UCompactIntArray mapping;
/*     */   private Vector<Vector<EntryPair>> contractTable;
/*     */   private Vector<int[]> expandTable;
/*     */   private short maxSecOrder;
/*     */   private short maxTerOrder;
/*     */   
/*     */   public RBTableBuilder(RBCollationTables.BuildAPI paramBuildAPI) {
/* 600 */     this.tables = null;
/* 601 */     this.mPattern = null;
/* 602 */     this.isOverIgnore = false;
/* 603 */     this.keyBuf = new char[5];
/* 604 */     this.contractFlags = new IntHashtable(100);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 609 */     this.frenchSec = false;
/* 610 */     this.seAsianSwapping = false;
/*     */     
/* 612 */     this.mapping = null;
/* 613 */     this.contractTable = null;
/* 614 */     this.expandTable = null;
/*     */     
/* 616 */     this.maxSecOrder = 0;
/* 617 */     this.maxTerOrder = 0;
/*     */     this.tables = paramBuildAPI;
/*     */   }
/*     */   
/*     */   public void build(String paramString, int paramInt) throws ParseException {
/*     */     boolean bool = true;
/*     */     byte b = 0;
/*     */     if (paramString.length() == 0)
/*     */       throw new ParseException("Build rules empty.", 0); 
/*     */     this.mapping = new UCompactIntArray(-1);
/*     */     paramString = NormalizerImpl.canonicalDecomposeWithSingleQuotation(paramString);
/*     */     this.mPattern = new MergeCollation(paramString);
/*     */     int i = 0;
/*     */     for (b = 0; b < this.mPattern.getCount(); b++) {
/*     */       PatternEntry patternEntry = this.mPattern.getItemAt(b);
/*     */       if (patternEntry != null) {
/*     */         String str2 = patternEntry.getChars();
/*     */         if (str2.length() > 1)
/*     */           switch (str2.charAt(str2.length() - 1)) {
/*     */             case '@':
/*     */               this.frenchSec = true;
/*     */               str2 = str2.substring(0, str2.length() - 1);
/*     */               break;
/*     */             case '!':
/*     */               this.seAsianSwapping = true;
/*     */               str2 = str2.substring(0, str2.length() - 1);
/*     */               break;
/*     */           }  
/*     */         i = increment(patternEntry.getStrength(), i);
/*     */         String str1 = patternEntry.getExtension();
/*     */         if (str1.length() != 0) {
/*     */           addExpandOrder(str2, str1, i);
/*     */         } else if (str2.length() > 1) {
/*     */           char c = str2.charAt(0);
/*     */           if (Character.isHighSurrogate(c) && str2.length() == 2) {
/*     */             addOrder(Character.toCodePoint(c, str2.charAt(1)), i);
/*     */           } else {
/*     */             addContractOrder(str2, i);
/*     */           } 
/*     */         } else {
/*     */           char c = str2.charAt(0);
/*     */           addOrder(c, i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     addComposedChars();
/*     */     commit();
/*     */     this.mapping.compact();
/*     */     this.tables.fillInTables(this.frenchSec, this.seAsianSwapping, this.mapping, this.contractTable, this.expandTable, this.contractFlags, this.maxSecOrder, this.maxTerOrder);
/*     */   }
/*     */   
/*     */   private void addComposedChars() throws ParseException {
/*     */     ComposedCharIter composedCharIter = new ComposedCharIter();
/*     */     int i;
/*     */     while ((i = composedCharIter.next()) != -1) {
/*     */       if (getCharOrder(i) == -1) {
/*     */         String str = composedCharIter.decomposition();
/*     */         if (str.length() == 1) {
/*     */           int k = getCharOrder(str.charAt(0));
/*     */           if (k != -1)
/*     */             addOrder(i, k); 
/*     */           continue;
/*     */         } 
/*     */         if (str.length() == 2) {
/*     */           char c = str.charAt(0);
/*     */           if (Character.isHighSurrogate(c)) {
/*     */             int k = getCharOrder(str.codePointAt(0));
/*     */             if (k != -1)
/*     */               addOrder(i, k); 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         int j = getContractOrder(str);
/*     */         if (j != -1) {
/*     */           addOrder(i, j);
/*     */           continue;
/*     */         } 
/*     */         boolean bool = true;
/*     */         for (byte b = 0; b < str.length(); b++) {
/*     */           if (getCharOrder(str.charAt(b)) == -1) {
/*     */             bool = false;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */         if (bool)
/*     */           addExpandOrder(i, str, -1); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void commit() {
/*     */     if (this.expandTable != null)
/*     */       for (byte b = 0; b < this.expandTable.size(); b++) {
/*     */         int[] arrayOfInt = this.expandTable.elementAt(b);
/*     */         for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/*     */           int i = arrayOfInt[b1];
/*     */           if (i < 2113929216 && i > 1879048192) {
/*     */             int j = i - 1879048192;
/*     */             int k = getCharOrder(j);
/*     */             if (k == -1) {
/*     */               arrayOfInt[b1] = 0xFFFF & j;
/*     */             } else {
/*     */               arrayOfInt[b1] = k;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   private final int increment(int paramInt1, int paramInt2) {
/*     */     switch (paramInt1) {
/*     */       case 0:
/*     */         paramInt2 += 65536;
/*     */         paramInt2 &= 0xFFFF0000;
/*     */         this.isOverIgnore = true;
/*     */         break;
/*     */       case 1:
/*     */         paramInt2 += 256;
/*     */         paramInt2 &= 0xFFFFFF00;
/*     */         if (!this.isOverIgnore)
/*     */           this.maxSecOrder = (short)(this.maxSecOrder + 1); 
/*     */         break;
/*     */       case 2:
/*     */         paramInt2++;
/*     */         if (!this.isOverIgnore)
/*     */           this.maxTerOrder = (short)(this.maxTerOrder + 1); 
/*     */         break;
/*     */     } 
/*     */     return paramInt2;
/*     */   }
/*     */   
/*     */   private final void addOrder(int paramInt1, int paramInt2) {
/*     */     int i = this.mapping.elementAt(paramInt1);
/*     */     if (i >= 2130706432) {
/*     */       int j = 1;
/*     */       if (Character.isSupplementaryCodePoint(paramInt1)) {
/*     */         j = Character.toChars(paramInt1, this.keyBuf, 0);
/*     */       } else {
/*     */         this.keyBuf[0] = (char)paramInt1;
/*     */       } 
/*     */       addContractOrder(new String(this.keyBuf, 0, j), paramInt2);
/*     */     } else {
/*     */       this.mapping.setElementAt(paramInt1, paramInt2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void addContractOrder(String paramString, int paramInt) {
/*     */     addContractOrder(paramString, paramInt, true);
/*     */   }
/*     */   
/*     */   private final void addContractOrder(String paramString, int paramInt, boolean paramBoolean) {
/*     */     if (this.contractTable == null)
/*     */       this.contractTable = new Vector<>(20); 
/*     */     int i = paramString.codePointAt(0);
/*     */     int j = this.mapping.elementAt(i);
/*     */     Vector<EntryPair> vector = getContractValuesImpl(j - 2130706432);
/*     */     if (vector == null) {
/*     */       int m = 2130706432 + this.contractTable.size();
/*     */       vector = new Vector<>(20);
/*     */       this.contractTable.addElement(vector);
/*     */       vector.addElement(new EntryPair(paramString.substring(0, Character.charCount(i)), j));
/*     */       this.mapping.setElementAt(i, m);
/*     */     } 
/*     */     int k = RBCollationTables.getEntry(vector, paramString, paramBoolean);
/*     */     if (k != -1) {
/*     */       EntryPair entryPair = vector.elementAt(k);
/*     */       entryPair.value = paramInt;
/*     */     } else {
/*     */       EntryPair entryPair = vector.lastElement();
/*     */       if (paramString.length() > entryPair.entryName.length()) {
/*     */         vector.addElement(new EntryPair(paramString, paramInt, paramBoolean));
/*     */       } else {
/*     */         vector.insertElementAt(new EntryPair(paramString, paramInt, paramBoolean), vector.size() - 1);
/*     */       } 
/*     */     } 
/*     */     if (paramBoolean && paramString.length() > 1) {
/*     */       addContractFlags(paramString);
/*     */       addContractOrder((new StringBuffer(paramString)).reverse().toString(), paramInt, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getContractOrder(String paramString) {
/*     */     int i = -1;
/*     */     if (this.contractTable != null) {
/*     */       int j = paramString.codePointAt(0);
/*     */       Vector<EntryPair> vector = getContractValues(j);
/*     */       if (vector != null) {
/*     */         int k = RBCollationTables.getEntry(vector, paramString, true);
/*     */         if (k != -1) {
/*     */           EntryPair entryPair = vector.elementAt(k);
/*     */           i = entryPair.value;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   private final int getCharOrder(int paramInt) {
/*     */     int i = this.mapping.elementAt(paramInt);
/*     */     if (i >= 2130706432) {
/*     */       Vector<EntryPair> vector = getContractValuesImpl(i - 2130706432);
/*     */       EntryPair entryPair = vector.firstElement();
/*     */       i = entryPair.value;
/*     */     } 
/*     */     return i;
/*     */   }
/*     */   
/*     */   private Vector<EntryPair> getContractValues(int paramInt) {
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
/*     */   private final void addExpandOrder(String paramString1, String paramString2, int paramInt) throws ParseException {
/*     */     int i = addExpansion(paramInt, paramString2);
/*     */     if (paramString1.length() > 1) {
/*     */       char c = paramString1.charAt(0);
/*     */       if (Character.isHighSurrogate(c) && paramString1.length() == 2) {
/*     */         char c1 = paramString1.charAt(1);
/*     */         if (Character.isLowSurrogate(c1))
/*     */           addOrder(Character.toCodePoint(c, c1), i); 
/*     */       } else {
/*     */         addContractOrder(paramString1, i);
/*     */       } 
/*     */     } else {
/*     */       addOrder(paramString1.charAt(0), i);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void addExpandOrder(int paramInt1, String paramString, int paramInt2) throws ParseException {
/*     */     int i = addExpansion(paramInt2, paramString);
/*     */     addOrder(paramInt1, i);
/*     */   }
/*     */   
/*     */   private int addExpansion(int paramInt, String paramString) {
/*     */     if (this.expandTable == null)
/*     */       this.expandTable = (Vector)new Vector<>(20); 
/*     */     byte b1 = (paramInt == -1) ? 0 : 1;
/*     */     int[] arrayOfInt = new int[paramString.length() + b1];
/*     */     if (b1 == 1)
/*     */       arrayOfInt[0] = paramInt; 
/*     */     byte b2 = b1;
/*     */     int i;
/*     */     for (i = 0; i < paramString.length(); i++) {
/*     */       char c1;
/*     */       char c = paramString.charAt(i);
/*     */       if (Character.isHighSurrogate(c)) {
/*     */         char c2;
/*     */         if (++i == paramString.length() || !Character.isLowSurrogate(c2 = paramString.charAt(i)))
/*     */           break; 
/*     */         c1 = Character.toCodePoint(c, c2);
/*     */       } else {
/*     */         c1 = c;
/*     */       } 
/*     */       int j = getCharOrder(c1);
/*     */       if (j != -1) {
/*     */         arrayOfInt[b2++] = j;
/*     */       } else {
/*     */         arrayOfInt[b2++] = 1879048192 + c1;
/*     */       } 
/*     */     } 
/*     */     if (b2 < arrayOfInt.length) {
/*     */       int[] arrayOfInt1 = new int[b2];
/*     */       while (--b2 >= 0)
/*     */         arrayOfInt1[b2] = arrayOfInt[b2]; 
/*     */       arrayOfInt = arrayOfInt1;
/*     */     } 
/*     */     i = 2113929216 + this.expandTable.size();
/*     */     this.expandTable.addElement(arrayOfInt);
/*     */     return i;
/*     */   }
/*     */   
/*     */   private void addContractFlags(String paramString) {
/*     */     int i = paramString.length();
/*     */     for (byte b = 0; b < i; b++) {
/*     */       char c = paramString.charAt(b);
/*     */       boolean bool = Character.isHighSurrogate(c) ? Character.toCodePoint(c, paramString.charAt(++b)) : c;
/*     */       this.contractFlags.put(bool, 1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/RBTableBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */