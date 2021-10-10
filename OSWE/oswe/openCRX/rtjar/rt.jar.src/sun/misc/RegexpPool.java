/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegexpPool
/*     */ {
/*  39 */   private RegexpNode prefixMachine = new RegexpNode();
/*  40 */   private RegexpNode suffixMachine = new RegexpNode();
/*     */   private static final int BIG = 2147483647;
/*  42 */   private int lastDepth = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String paramString, Object paramObject) throws REException {
/*  59 */     add(paramString, paramObject, false);
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
/*     */   public void replace(String paramString, Object paramObject) {
/*     */     try {
/*  76 */       add(paramString, paramObject, true);
/*  77 */     } catch (Exception exception) {}
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
/*     */   public Object delete(String paramString) {
/*  89 */     Object object = null;
/*  90 */     RegexpNode regexpNode1 = this.prefixMachine;
/*  91 */     RegexpNode regexpNode2 = regexpNode1;
/*  92 */     int i = paramString.length() - 1;
/*     */     
/*  94 */     boolean bool = true;
/*     */     
/*  96 */     if (!paramString.startsWith("*") || 
/*  97 */       !paramString.endsWith("*")) {
/*  98 */       i++;
/*     */     }
/* 100 */     if (i <= 0) {
/* 101 */       return null;
/*     */     }
/*     */     int j;
/* 104 */     for (j = 0; regexpNode1 != null; j++) {
/* 105 */       if (regexpNode1.result != null && regexpNode1.depth < Integer.MAX_VALUE && (!regexpNode1.exact || j == i))
/*     */       {
/* 107 */         regexpNode2 = regexpNode1;
/*     */       }
/* 109 */       if (j >= i)
/*     */         break; 
/* 111 */       regexpNode1 = regexpNode1.find(paramString.charAt(j));
/*     */     } 
/*     */ 
/*     */     
/* 115 */     regexpNode1 = this.suffixMachine;
/* 116 */     for (j = i; --j >= 0 && regexpNode1 != null; ) {
/* 117 */       if (regexpNode1.result != null && regexpNode1.depth < Integer.MAX_VALUE) {
/* 118 */         bool = false;
/* 119 */         regexpNode2 = regexpNode1;
/*     */       } 
/* 121 */       regexpNode1 = regexpNode1.find(paramString.charAt(j));
/*     */     } 
/*     */ 
/*     */     
/* 125 */     if (bool) {
/* 126 */       if (paramString.equals(regexpNode2.re)) {
/* 127 */         object = regexpNode2.result;
/* 128 */         regexpNode2.result = null;
/*     */       }
/*     */     
/*     */     }
/* 132 */     else if (paramString.equals(regexpNode2.re)) {
/* 133 */       object = regexpNode2.result;
/* 134 */       regexpNode2.result = null;
/*     */     } 
/*     */     
/* 137 */     return object;
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
/*     */   public Object match(String paramString) {
/* 154 */     return matchAfter(paramString, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object matchNext(String paramString) {
/* 162 */     return matchAfter(paramString, this.lastDepth);
/*     */   }
/*     */   private void add(String paramString, Object paramObject, boolean paramBoolean) throws REException {
/*     */     RegexpNode regexpNode;
/* 166 */     int i = paramString.length();
/*     */     
/* 168 */     if (paramString.charAt(0) == '*') {
/* 169 */       regexpNode = this.suffixMachine;
/* 170 */       while (i > 1)
/* 171 */         regexpNode = regexpNode.add(paramString.charAt(--i)); 
/*     */     } else {
/* 173 */       boolean bool = false;
/* 174 */       if (paramString.charAt(i - 1) == '*') {
/* 175 */         i--;
/*     */       } else {
/* 177 */         bool = true;
/* 178 */       }  regexpNode = this.prefixMachine;
/* 179 */       for (byte b = 0; b < i; b++)
/* 180 */         regexpNode = regexpNode.add(paramString.charAt(b)); 
/* 181 */       regexpNode.exact = bool;
/*     */     } 
/*     */     
/* 184 */     if (regexpNode.result != null && !paramBoolean) {
/* 185 */       throw new REException(paramString + " is a duplicate");
/*     */     }
/* 187 */     regexpNode.re = paramString;
/* 188 */     regexpNode.result = paramObject;
/*     */   }
/*     */   
/*     */   private Object matchAfter(String paramString, int paramInt) {
/* 192 */     RegexpNode regexpNode1 = this.prefixMachine;
/* 193 */     RegexpNode regexpNode2 = regexpNode1;
/* 194 */     int i = 0;
/* 195 */     int j = 0;
/* 196 */     int k = paramString.length();
/*     */     
/* 198 */     if (k <= 0)
/* 199 */       return null; 
/*     */     int m;
/* 201 */     for (m = 0; regexpNode1 != null; m++) {
/* 202 */       if (regexpNode1.result != null && regexpNode1.depth < paramInt && (!regexpNode1.exact || m == k)) {
/*     */         
/* 204 */         this.lastDepth = regexpNode1.depth;
/* 205 */         regexpNode2 = regexpNode1;
/* 206 */         i = m;
/* 207 */         j = k;
/*     */       } 
/* 209 */       if (m >= k)
/*     */         break; 
/* 211 */       regexpNode1 = regexpNode1.find(paramString.charAt(m));
/*     */     } 
/*     */     
/* 214 */     regexpNode1 = this.suffixMachine;
/* 215 */     for (m = k; --m >= 0 && regexpNode1 != null; ) {
/* 216 */       if (regexpNode1.result != null && regexpNode1.depth < paramInt) {
/* 217 */         this.lastDepth = regexpNode1.depth;
/* 218 */         regexpNode2 = regexpNode1;
/* 219 */         i = 0;
/* 220 */         j = m + 1;
/*     */       } 
/* 222 */       regexpNode1 = regexpNode1.find(paramString.charAt(m));
/*     */     } 
/* 224 */     Object object = regexpNode2.result;
/* 225 */     if (object != null && object instanceof RegexpTarget)
/* 226 */       object = ((RegexpTarget)object).found(paramString.substring(i, j)); 
/* 227 */     return object;
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
/*     */   public void reset() {
/* 240 */     this.lastDepth = Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void print(PrintStream paramPrintStream) {
/* 245 */     paramPrintStream.print("Regexp pool:\n");
/* 246 */     if (this.suffixMachine.firstchild != null) {
/* 247 */       paramPrintStream.print(" Suffix machine: ");
/* 248 */       this.suffixMachine.firstchild.print(paramPrintStream);
/* 249 */       paramPrintStream.print("\n");
/*     */     } 
/* 251 */     if (this.prefixMachine.firstchild != null) {
/* 252 */       paramPrintStream.print(" Prefix machine: ");
/* 253 */       this.prefixMachine.firstchild.print(paramPrintStream);
/* 254 */       paramPrintStream.print("\n");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/RegexpPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */