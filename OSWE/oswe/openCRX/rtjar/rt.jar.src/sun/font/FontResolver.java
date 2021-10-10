/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import sun.text.CodePointIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontResolver
/*     */ {
/*     */   private Font[] allFonts;
/*     */   private Font[] supplementaryFonts;
/*     */   private int[] supplementaryIndices;
/*     */   private static final int DEFAULT_SIZE = 12;
/*  62 */   private Font defaultFont = new Font("Dialog", 0, 12);
/*     */ 
/*     */   
/*     */   private static final int SHIFT = 9;
/*     */ 
/*     */   
/*     */   private static final int BLOCKSIZE = 128;
/*     */ 
/*     */   
/*     */   private static final int MASK = 127;
/*     */ 
/*     */   
/*  74 */   private int[][] blocks = new int[512][];
/*     */   
/*     */   private static FontResolver INSTANCE;
/*     */ 
/*     */   
/*     */   private Font[] getAllFonts() {
/*  80 */     if (this.allFonts == null) {
/*  81 */       this
/*  82 */         .allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
/*  83 */       for (byte b = 0; b < this.allFonts.length; b++) {
/*  84 */         this.allFonts[b] = this.allFonts[b].deriveFont(12.0F);
/*     */       }
/*     */     } 
/*  87 */     return this.allFonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getIndexFor(char paramChar) {
/*  98 */     if (this.defaultFont.canDisplay(paramChar)) {
/*  99 */       return 1;
/*     */     }
/* 101 */     for (byte b = 0; b < (getAllFonts()).length; b++) {
/* 102 */       if (this.allFonts[b].canDisplay(paramChar)) {
/* 103 */         return b + 2;
/*     */       }
/*     */     } 
/* 106 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private Font[] getAllSCFonts() {
/* 111 */     if (this.supplementaryFonts == null) {
/* 112 */       ArrayList<Font> arrayList = new ArrayList();
/* 113 */       ArrayList<Integer> arrayList1 = new ArrayList();
/*     */       int i;
/* 115 */       for (i = 0; i < (getAllFonts()).length; i++) {
/* 116 */         Font font = this.allFonts[i];
/* 117 */         Font2D font2D = FontUtilities.getFont2D(font);
/* 118 */         if (font2D.hasSupplementaryChars()) {
/* 119 */           arrayList.add(font);
/* 120 */           arrayList1.add(Integer.valueOf(i));
/*     */         } 
/*     */       } 
/*     */       
/* 124 */       i = arrayList.size();
/* 125 */       this.supplementaryIndices = new int[i];
/* 126 */       for (byte b = 0; b < i; b++) {
/* 127 */         this.supplementaryIndices[b] = ((Integer)arrayList1.get(b)).intValue();
/*     */       }
/* 129 */       this.supplementaryFonts = arrayList.<Font>toArray(new Font[i]);
/*     */     } 
/* 131 */     return this.supplementaryFonts;
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
/*     */   private int getIndexFor(int paramInt) {
/* 144 */     if (this.defaultFont.canDisplay(paramInt)) {
/* 145 */       return 1;
/*     */     }
/*     */     
/* 148 */     for (byte b = 0; b < (getAllSCFonts()).length; b++) {
/* 149 */       if (this.supplementaryFonts[b].canDisplay(paramInt)) {
/* 150 */         return this.supplementaryIndices[b] + 2;
/*     */       }
/*     */     } 
/* 153 */     return 1;
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
/*     */   public int getFontIndex(char paramChar) {
/* 165 */     int i = paramChar >> 9;
/* 166 */     int[] arrayOfInt = this.blocks[i];
/* 167 */     if (arrayOfInt == null) {
/* 168 */       arrayOfInt = new int[128];
/* 169 */       this.blocks[i] = arrayOfInt;
/*     */     } 
/*     */     
/* 172 */     int j = paramChar & 0x7F;
/* 173 */     if (arrayOfInt[j] == 0) {
/* 174 */       arrayOfInt[j] = getIndexFor(paramChar);
/*     */     }
/* 176 */     return arrayOfInt[j];
/*     */   }
/*     */   
/*     */   public int getFontIndex(int paramInt) {
/* 180 */     if (paramInt < 65536) {
/* 181 */       return getFontIndex((char)paramInt);
/*     */     }
/* 183 */     return getIndexFor(paramInt);
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
/*     */   public int nextFontRunIndex(CodePointIterator paramCodePointIterator) {
/* 196 */     int i = paramCodePointIterator.next();
/* 197 */     int j = 1;
/* 198 */     if (i != -1) {
/* 199 */       j = getFontIndex(i);
/*     */       
/* 201 */       while ((i = paramCodePointIterator.next()) != -1) {
/* 202 */         if (getFontIndex(i) != j) {
/* 203 */           paramCodePointIterator.prev();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 208 */     return j;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont(int paramInt, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 226 */     Font font = this.defaultFont;
/*     */     
/* 228 */     if (paramInt >= 2) {
/* 229 */       font = this.allFonts[paramInt - 2];
/*     */     }
/*     */     
/* 232 */     return font.deriveFont(paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontResolver getInstance() {
/* 241 */     if (INSTANCE == null) {
/* 242 */       INSTANCE = new FontResolver();
/*     */     }
/* 244 */     return INSTANCE;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */