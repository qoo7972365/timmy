/*     */ package sun.net.www;
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
/*     */ public class HeaderParser
/*     */ {
/*     */   String raw;
/*     */   String[][] tab;
/*     */   int nkeys;
/*  55 */   int asize = 10;
/*     */   
/*     */   public HeaderParser(String paramString) {
/*  58 */     this.raw = paramString;
/*  59 */     this.tab = new String[this.asize][2];
/*  60 */     parse();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private HeaderParser() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderParser subsequence(int paramInt1, int paramInt2) {
/*  71 */     if (paramInt1 == 0 && paramInt2 == this.nkeys) {
/*  72 */       return this;
/*     */     }
/*  74 */     if (paramInt1 < 0 || paramInt1 >= paramInt2 || paramInt2 > this.nkeys)
/*  75 */       throw new IllegalArgumentException("invalid start or end"); 
/*  76 */     HeaderParser headerParser = new HeaderParser();
/*  77 */     headerParser.tab = new String[this.asize][2];
/*  78 */     headerParser.asize = this.asize;
/*  79 */     System.arraycopy(this.tab, paramInt1, headerParser.tab, 0, paramInt2 - paramInt1);
/*  80 */     headerParser.nkeys = paramInt2 - paramInt1;
/*  81 */     return headerParser;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parse() {
/*  86 */     if (this.raw != null) {
/*  87 */       this.raw = this.raw.trim();
/*  88 */       char[] arrayOfChar = this.raw.toCharArray();
/*  89 */       byte b1 = 0, b2 = 0, b3 = 0;
/*  90 */       boolean bool1 = true;
/*  91 */       boolean bool2 = false;
/*  92 */       int i = arrayOfChar.length;
/*  93 */       while (b2 < i) {
/*  94 */         char c = arrayOfChar[b2];
/*  95 */         if (c == '=' && !bool2) {
/*  96 */           this.tab[b3][0] = (new String(arrayOfChar, b1, b2 - b1)).toLowerCase();
/*  97 */           bool1 = false;
/*     */           
/*  99 */           b1 = ++b2;
/* 100 */         } else if (c == '"') {
/* 101 */           if (bool2) {
/* 102 */             this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/* 103 */             bool2 = false;
/*     */             do {
/* 105 */               b2++;
/* 106 */             } while (b2 < i && (arrayOfChar[b2] == ' ' || arrayOfChar[b2] == ','));
/* 107 */             bool1 = true;
/* 108 */             b1 = b2;
/*     */           } else {
/* 110 */             bool2 = true;
/*     */             
/* 112 */             b1 = ++b2;
/*     */           } 
/* 114 */         } else if (c == ' ' || c == ',') {
/* 115 */           if (bool2) {
/* 116 */             b2++; continue;
/*     */           } 
/* 118 */           if (bool1) {
/* 119 */             this.tab[b3++][0] = (new String(arrayOfChar, b1, b2 - b1)).toLowerCase();
/*     */           } else {
/* 121 */             this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/*     */           } 
/* 123 */           while (b2 < i && (arrayOfChar[b2] == ' ' || arrayOfChar[b2] == ',')) {
/* 124 */             b2++;
/*     */           }
/* 126 */           bool1 = true;
/* 127 */           b1 = b2;
/*     */         } else {
/* 129 */           b2++;
/*     */         } 
/* 131 */         if (b3 == this.asize) {
/* 132 */           this.asize *= 2;
/* 133 */           String[][] arrayOfString = new String[this.asize][2];
/* 134 */           System.arraycopy(this.tab, 0, arrayOfString, 0, this.tab.length);
/* 135 */           this.tab = arrayOfString;
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       if (--b2 > b1) {
/* 140 */         if (!bool1) {
/* 141 */           if (arrayOfChar[b2] == '"') {
/* 142 */             this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1);
/*     */           } else {
/* 144 */             this.tab[b3++][1] = new String(arrayOfChar, b1, b2 - b1 + 1);
/*     */           } 
/*     */         } else {
/* 147 */           this.tab[b3++][0] = (new String(arrayOfChar, b1, b2 - b1 + 1)).toLowerCase();
/*     */         } 
/* 149 */       } else if (b2 == b1) {
/* 150 */         if (!bool1) {
/* 151 */           if (arrayOfChar[b2] == '"') {
/* 152 */             this.tab[b3++][1] = String.valueOf(arrayOfChar[b2 - 1]);
/*     */           } else {
/* 154 */             this.tab[b3++][1] = String.valueOf(arrayOfChar[b2]);
/*     */           } 
/*     */         } else {
/* 157 */           this.tab[b3++][0] = String.valueOf(arrayOfChar[b2]).toLowerCase();
/*     */         } 
/*     */       } 
/* 160 */       this.nkeys = b3;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String findKey(int paramInt) {
/* 166 */     if (paramInt < 0 || paramInt > this.asize)
/* 167 */       return null; 
/* 168 */     return this.tab[paramInt][0];
/*     */   }
/*     */   
/*     */   public String findValue(int paramInt) {
/* 172 */     if (paramInt < 0 || paramInt > this.asize)
/* 173 */       return null; 
/* 174 */     return this.tab[paramInt][1];
/*     */   }
/*     */   
/*     */   public String findValue(String paramString) {
/* 178 */     return findValue(paramString, null);
/*     */   }
/*     */   
/*     */   public String findValue(String paramString1, String paramString2) {
/* 182 */     if (paramString1 == null)
/* 183 */       return paramString2; 
/* 184 */     paramString1 = paramString1.toLowerCase();
/* 185 */     for (byte b = 0; b < this.asize; b++) {
/* 186 */       if (this.tab[b][0] == null)
/* 187 */         return paramString2; 
/* 188 */       if (paramString1.equals(this.tab[b][0])) {
/* 189 */         return this.tab[b][1];
/*     */       }
/*     */     } 
/* 192 */     return paramString2;
/*     */   }
/*     */   
/*     */   class ParserIterator implements Iterator<String> {
/*     */     int index;
/*     */     boolean returnsValue;
/*     */     
/*     */     ParserIterator(boolean param1Boolean) {
/* 200 */       this.returnsValue = param1Boolean;
/*     */     }
/*     */     public boolean hasNext() {
/* 203 */       return (this.index < HeaderParser.this.nkeys);
/*     */     }
/*     */     public String next() {
/* 206 */       return HeaderParser.this.tab[this.index++][this.returnsValue ? 1 : 0];
/*     */     }
/*     */     public void remove() {
/* 209 */       throw new UnsupportedOperationException("remove not supported");
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<String> keys() {
/* 214 */     return new ParserIterator(false);
/*     */   }
/*     */   
/*     */   public Iterator<String> values() {
/* 218 */     return new ParserIterator(true);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 222 */     Iterator<String> iterator = keys();
/* 223 */     StringBuffer stringBuffer = new StringBuffer();
/* 224 */     stringBuffer.append("{size=" + this.asize + " nkeys=" + this.nkeys + " ");
/* 225 */     for (byte b = 0; iterator.hasNext(); b++) {
/* 226 */       String str1 = iterator.next();
/* 227 */       String str2 = findValue(b);
/* 228 */       if (str2 != null && "".equals(str2)) {
/* 229 */         str2 = null;
/*     */       }
/* 231 */       stringBuffer.append(" {" + str1 + ((str2 == null) ? "" : ("," + str2)) + "}");
/* 232 */       if (iterator.hasNext()) {
/* 233 */         stringBuffer.append(",");
/*     */       }
/*     */     } 
/* 236 */     stringBuffer.append(" }");
/* 237 */     return new String(stringBuffer);
/*     */   }
/*     */   
/*     */   public int findInt(String paramString, int paramInt) {
/*     */     try {
/* 242 */       return Integer.parseInt(findValue(paramString, String.valueOf(paramInt)));
/* 243 */     } catch (Throwable throwable) {
/* 244 */       return paramInt;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/HeaderParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */