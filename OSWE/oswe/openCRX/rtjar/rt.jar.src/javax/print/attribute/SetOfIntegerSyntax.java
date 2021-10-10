/*     */ package javax.print.attribute;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SetOfIntegerSyntax
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 3666874174847632203L;
/*     */   private int[][] members;
/*     */   
/*     */   protected SetOfIntegerSyntax(String paramString) {
/* 106 */     this.members = parse(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] parse(String paramString) {
/* 115 */     Vector vector = new Vector();
/*     */ 
/*     */     
/* 118 */     byte b1 = (paramString == null) ? 0 : paramString.length();
/* 119 */     byte b2 = 0;
/* 120 */     byte b3 = 0;
/* 121 */     int i = 0;
/* 122 */     int j = 0;
/*     */ 
/*     */     
/* 125 */     while (b2 < b1) {
/* 126 */       int k; char c = paramString.charAt(b2++);
/* 127 */       switch (b3) {
/*     */         
/*     */         case false:
/* 130 */           if (Character.isWhitespace(c)) {
/* 131 */             b3 = 0; continue;
/*     */           } 
/* 133 */           if ((k = Character.digit(c, 10)) != -1) {
/* 134 */             i = k;
/* 135 */             b3 = 1; continue;
/*     */           } 
/* 137 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 142 */           if (Character.isWhitespace(c)) {
/* 143 */             b3 = 2; continue;
/* 144 */           }  if ((k = Character.digit(c, 10)) != -1) {
/* 145 */             i = 10 * i + k;
/* 146 */             b3 = 1; continue;
/* 147 */           }  if (c == '-' || c == ':') {
/* 148 */             b3 = 3; continue;
/* 149 */           }  if (c == ',') {
/* 150 */             accumulate(vector, i, i);
/* 151 */             b3 = 6; continue;
/*     */           } 
/* 153 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 158 */           if (Character.isWhitespace(c)) {
/* 159 */             b3 = 2; continue;
/*     */           } 
/* 161 */           if (c == '-' || c == ':') {
/* 162 */             b3 = 3; continue;
/*     */           } 
/* 164 */           if (c == ',') {
/* 165 */             accumulate(vector, i, i);
/* 166 */             b3 = 6; continue;
/*     */           } 
/* 168 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 173 */           if (Character.isWhitespace(c)) {
/* 174 */             b3 = 3; continue;
/* 175 */           }  if ((k = Character.digit(c, 10)) != -1) {
/* 176 */             j = k;
/* 177 */             b3 = 4; continue;
/*     */           } 
/* 179 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 184 */           if (Character.isWhitespace(c)) {
/* 185 */             b3 = 5; continue;
/* 186 */           }  if ((k = Character.digit(c, 10)) != -1) {
/* 187 */             j = 10 * j + k;
/* 188 */             b3 = 4; continue;
/* 189 */           }  if (c == ',') {
/* 190 */             accumulate(vector, i, j);
/* 191 */             b3 = 6; continue;
/*     */           } 
/* 193 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 198 */           if (Character.isWhitespace(c)) {
/* 199 */             b3 = 5; continue;
/* 200 */           }  if (c == ',') {
/* 201 */             accumulate(vector, i, j);
/* 202 */             b3 = 6; continue;
/*     */           } 
/* 204 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case true:
/* 209 */           if (Character.isWhitespace(c)) {
/* 210 */             b3 = 6; continue;
/* 211 */           }  if ((k = Character.digit(c, 10)) != -1) {
/* 212 */             i = k;
/* 213 */             b3 = 1; continue;
/*     */           } 
/* 215 */           throw new IllegalArgumentException();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 222 */     switch (b3) {
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 227 */         accumulate(vector, i, i);
/*     */         break;
/*     */       case 4:
/*     */       case 5:
/* 231 */         accumulate(vector, i, j);
/*     */         break;
/*     */       case 3:
/*     */       case 6:
/* 235 */         throw new IllegalArgumentException();
/*     */     } 
/*     */ 
/*     */     
/* 239 */     return canonicalArrayForm(vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void accumulate(Vector<int[]> paramVector, int paramInt1, int paramInt2) {
/* 248 */     if (paramInt1 <= paramInt2) {
/*     */       
/* 250 */       paramVector.add(new int[] { paramInt1, paramInt2 });
/*     */ 
/*     */ 
/*     */       
/* 254 */       for (int i = paramVector.size() - 2; i >= 0; i--) {
/*     */         
/* 256 */         int[] arrayOfInt1 = paramVector.elementAt(i);
/* 257 */         int j = arrayOfInt1[0];
/* 258 */         int k = arrayOfInt1[1];
/* 259 */         int[] arrayOfInt2 = paramVector.elementAt(i + 1);
/* 260 */         int m = arrayOfInt2[0];
/* 261 */         int n = arrayOfInt2[1];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 269 */         if (Math.max(j, m) - Math.min(k, n) <= 1) {
/*     */ 
/*     */           
/* 272 */           paramVector.setElementAt(new int[] {
/* 273 */                 Math.min(j, m), 
/* 274 */                 Math.max(k, n) }i);
/* 275 */           paramVector.remove(i + 1);
/* 276 */         } else if (j > m) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 281 */           paramVector.setElementAt(arrayOfInt2, i);
/* 282 */           paramVector.setElementAt(arrayOfInt1, i + 1);
/*     */         } else {
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] canonicalArrayForm(Vector paramVector) {
/* 297 */     return (int[][])paramVector.toArray((Object[])new int[paramVector.size()][]);
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
/*     */ 
/*     */   
/*     */   protected SetOfIntegerSyntax(int[][] paramArrayOfint) {
/* 317 */     this.members = parse(paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] parse(int[][] paramArrayOfint) {
/* 326 */     Vector vector = new Vector();
/*     */ 
/*     */     
/* 329 */     byte b1 = (paramArrayOfint == null) ? 0 : paramArrayOfint.length;
/* 330 */     for (byte b2 = 0; b2 < b1; b2++) {
/*     */       int i, j;
/*     */       
/* 333 */       if ((paramArrayOfint[b2]).length == 1) {
/* 334 */         i = j = paramArrayOfint[b2][0];
/* 335 */       } else if ((paramArrayOfint[b2]).length == 2) {
/* 336 */         i = paramArrayOfint[b2][0];
/* 337 */         j = paramArrayOfint[b2][1];
/*     */       } else {
/* 339 */         throw new IllegalArgumentException();
/*     */       } 
/*     */ 
/*     */       
/* 343 */       if (i <= j && i < 0) {
/* 344 */         throw new IllegalArgumentException();
/*     */       }
/*     */ 
/*     */       
/* 348 */       accumulate(vector, i, j);
/*     */     } 
/*     */ 
/*     */     
/* 352 */     return canonicalArrayForm(vector);
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
/*     */   protected SetOfIntegerSyntax(int paramInt) {
/* 365 */     if (paramInt < 0) {
/* 366 */       throw new IllegalArgumentException();
/*     */     }
/* 368 */     this.members = new int[][] { { paramInt, paramInt } };
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
/*     */   protected SetOfIntegerSyntax(int paramInt1, int paramInt2) {
/* 384 */     if (paramInt1 <= paramInt2 && paramInt1 < 0) {
/* 385 */       throw new IllegalArgumentException();
/*     */     }
/* 387 */     (new int[2])[0] = paramInt1; (new int[2])[1] = paramInt2; (new int[1][])[0] = new int[2]; this.members = (paramInt1 <= paramInt2) ? new int[1][] : new int[0][];
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
/*     */   public int[][] getMembers() {
/* 401 */     int i = this.members.length;
/* 402 */     int[][] arrayOfInt = new int[i][];
/* 403 */     for (byte b = 0; b < i; b++) {
/* 404 */       (new int[2])[0] = this.members[b][0]; (new int[2])[1] = this.members[b][1]; arrayOfInt[b] = new int[2];
/*     */     } 
/* 406 */     return arrayOfInt;
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
/*     */   public boolean contains(int paramInt) {
/* 419 */     int i = this.members.length;
/* 420 */     for (byte b = 0; b < i; b++) {
/* 421 */       if (paramInt < this.members[b][0])
/* 422 */         return false; 
/* 423 */       if (paramInt <= this.members[b][1]) {
/* 424 */         return true;
/*     */       }
/*     */     } 
/* 427 */     return false;
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
/*     */   public boolean contains(IntegerSyntax paramIntegerSyntax) {
/* 440 */     return contains(paramIntegerSyntax.getValue());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int next(int paramInt) {
/* 468 */     int i = this.members.length;
/* 469 */     for (byte b = 0; b < i; b++) {
/* 470 */       if (paramInt < this.members[b][0])
/* 471 */         return this.members[b][0]; 
/* 472 */       if (paramInt < this.members[b][1]) {
/* 473 */         return paramInt + 1;
/*     */       }
/*     */     } 
/* 476 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 499 */     if (paramObject != null && paramObject instanceof SetOfIntegerSyntax) {
/* 500 */       int[][] arrayOfInt1 = this.members;
/* 501 */       int[][] arrayOfInt2 = ((SetOfIntegerSyntax)paramObject).members;
/* 502 */       int i = arrayOfInt1.length;
/* 503 */       int j = arrayOfInt2.length;
/* 504 */       if (i == j) {
/* 505 */         for (byte b = 0; b < i; b++) {
/* 506 */           if (arrayOfInt1[b][0] != arrayOfInt2[b][0] || arrayOfInt1[b][1] != arrayOfInt2[b][1])
/*     */           {
/* 508 */             return false;
/*     */           }
/*     */         } 
/* 511 */         return true;
/*     */       } 
/* 513 */       return false;
/*     */     } 
/*     */     
/* 516 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 526 */     int i = 0;
/* 527 */     int j = this.members.length;
/* 528 */     for (byte b = 0; b < j; b++) {
/* 529 */       i += this.members[b][0] + this.members[b][1];
/*     */     }
/* 531 */     return i;
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
/*     */   public String toString() {
/* 543 */     StringBuffer stringBuffer = new StringBuffer();
/* 544 */     int i = this.members.length;
/* 545 */     for (byte b = 0; b < i; b++) {
/* 546 */       if (b > 0) {
/* 547 */         stringBuffer.append(',');
/*     */       }
/* 549 */       stringBuffer.append(this.members[b][0]);
/* 550 */       if (this.members[b][0] != this.members[b][1]) {
/* 551 */         stringBuffer.append('-');
/* 552 */         stringBuffer.append(this.members[b][1]);
/*     */       } 
/*     */     } 
/* 555 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/attribute/SetOfIntegerSyntax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */