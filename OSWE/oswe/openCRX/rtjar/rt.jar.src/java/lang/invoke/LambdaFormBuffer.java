/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.LambdaForm;
/*     */ import java.lang.invoke.LambdaFormBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LambdaFormBuffer
/*     */ {
/*     */   private int arity;
/*     */   private int length;
/*     */   private LambdaForm.Name[] names;
/*     */   private LambdaForm.Name[] originalNames;
/*     */   private byte flags;
/*     */   private int firstChange;
/*     */   private LambdaForm.Name resultName;
/*     */   private String debugName;
/*     */   private ArrayList<LambdaForm.Name> dups;
/*     */   private static final int F_TRANS = 16;
/*     */   private static final int F_OWNED = 3;
/*     */   
/*     */   LambdaFormBuffer(LambdaForm paramLambdaForm) {
/*  49 */     this.arity = paramLambdaForm.arity;
/*  50 */     setNames(paramLambdaForm.names);
/*  51 */     int i = paramLambdaForm.result;
/*  52 */     if (i == -2) i = this.length - 1; 
/*  53 */     if (i >= 0 && (paramLambdaForm.names[i]).type != LambdaForm.BasicType.V_TYPE)
/*  54 */       this.resultName = paramLambdaForm.names[i]; 
/*  55 */     this.debugName = paramLambdaForm.debugName;
/*  56 */     assert paramLambdaForm.nameRefsAreLegal();
/*     */   }
/*     */   
/*     */   private LambdaForm lambdaForm() {
/*  60 */     assert !inTrans();
/*  61 */     return new LambdaForm(this.debugName, this.arity, nameArray(), resultIndex());
/*     */   }
/*     */   
/*     */   LambdaForm.Name name(int paramInt) {
/*  65 */     assert paramInt < this.length;
/*  66 */     return this.names[paramInt];
/*     */   }
/*     */   
/*     */   LambdaForm.Name[] nameArray() {
/*  70 */     return Arrays.<LambdaForm.Name>copyOf(this.names, this.length);
/*     */   }
/*     */   
/*     */   int resultIndex() {
/*  74 */     if (this.resultName == null) return -1; 
/*  75 */     int i = indexOf(this.resultName, this.names);
/*  76 */     assert i >= 0;
/*  77 */     return i;
/*     */   }
/*     */   
/*     */   void setNames(LambdaForm.Name[] paramArrayOfName) {
/*  81 */     this.names = this.originalNames = paramArrayOfName;
/*  82 */     this.length = paramArrayOfName.length;
/*  83 */     this.flags = 0;
/*     */   }
/*     */   private boolean verifyArity() {
/*     */     int i;
/*  87 */     for (i = 0; i < this.arity && i < this.firstChange; i++) {
/*  88 */       assert this.names[i].isParam() : "#" + i + "=" + this.names[i];
/*     */     }
/*  90 */     for (i = this.arity; i < this.length; i++) {
/*  91 */       assert !this.names[i].isParam() : "#" + i + "=" + this.names[i];
/*     */     }
/*  93 */     for (i = this.length; i < this.names.length; i++) {
/*  94 */       assert this.names[i] == null : "#" + i + "=" + this.names[i];
/*     */     }
/*     */     
/*  97 */     if (this.resultName != null) {
/*  98 */       i = indexOf(this.resultName, this.names);
/*  99 */       assert i >= 0 : "not found: " + this.resultName.exprString() + Arrays.asList((T[])this.names);
/* 100 */       assert this.names[i] == this.resultName;
/*     */     } 
/* 102 */     return true;
/*     */   }
/*     */   
/*     */   private boolean verifyFirstChange() {
/* 106 */     assert inTrans();
/* 107 */     for (byte b = 0; b < this.length; b++) {
/* 108 */       if (this.names[b] != this.originalNames[b]) {
/* 109 */         assert this.firstChange == b : Arrays.asList((T[])new Object[] { Integer.valueOf(this.firstChange), Integer.valueOf(b), this.originalNames[b].exprString(), Arrays.asList(this.names) });
/* 110 */         return true;
/*     */       } 
/*     */     } 
/* 113 */     assert this.firstChange == this.length : Arrays.asList((T[])new Object[] { Integer.valueOf(this.firstChange), Arrays.asList(this.names) });
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   private static int indexOf(LambdaForm.NamedFunction paramNamedFunction, LambdaForm.NamedFunction[] paramArrayOfNamedFunction) {
/* 118 */     for (byte b = 0; b < paramArrayOfNamedFunction.length; b++) {
/* 119 */       if (paramArrayOfNamedFunction[b] == paramNamedFunction) return b; 
/*     */     } 
/* 121 */     return -1;
/*     */   }
/*     */   
/*     */   private static int indexOf(LambdaForm.Name paramName, LambdaForm.Name[] paramArrayOfName) {
/* 125 */     for (byte b = 0; b < paramArrayOfName.length; b++) {
/* 126 */       if (paramArrayOfName[b] == paramName) return b; 
/*     */     } 
/* 128 */     return -1;
/*     */   }
/*     */   
/*     */   boolean inTrans() {
/* 132 */     return ((this.flags & 0x10) != 0);
/*     */   }
/*     */   
/*     */   int ownedCount() {
/* 136 */     return this.flags & 0x3;
/*     */   }
/*     */   
/*     */   void growNames(int paramInt1, int paramInt2) {
/* 140 */     int i = this.length;
/* 141 */     int j = i + paramInt2;
/* 142 */     int k = ownedCount();
/* 143 */     if (k == 0 || j > this.names.length) {
/* 144 */       this.names = Arrays.<LambdaForm.Name>copyOf(this.names, (this.names.length + paramInt2) * 5 / 4);
/* 145 */       if (k == 0) {
/* 146 */         this.flags = (byte)(this.flags + 1);
/* 147 */         k++;
/* 148 */         assert ownedCount() == k;
/*     */       } 
/*     */     } 
/* 151 */     if (this.originalNames != null && this.originalNames.length < this.names.length) {
/* 152 */       this.originalNames = Arrays.<LambdaForm.Name>copyOf(this.originalNames, this.names.length);
/* 153 */       if (k == 1) {
/* 154 */         this.flags = (byte)(this.flags + 1);
/* 155 */         k++;
/* 156 */         assert ownedCount() == k;
/*     */       } 
/*     */     } 
/* 159 */     if (paramInt2 == 0)
/* 160 */       return;  int m = paramInt1 + paramInt2;
/* 161 */     int n = i - paramInt1;
/* 162 */     System.arraycopy(this.names, paramInt1, this.names, m, n);
/* 163 */     Arrays.fill((Object[])this.names, paramInt1, m, (Object)null);
/* 164 */     if (this.originalNames != null) {
/* 165 */       System.arraycopy(this.originalNames, paramInt1, this.originalNames, m, n);
/* 166 */       Arrays.fill((Object[])this.originalNames, paramInt1, m, (Object)null);
/*     */     } 
/* 168 */     this.length = j;
/* 169 */     if (this.firstChange >= paramInt1) {
/* 170 */       this.firstChange += paramInt2;
/*     */     }
/*     */   }
/*     */   
/*     */   int lastIndexOf(LambdaForm.Name paramName) {
/* 175 */     byte b = -1;
/* 176 */     for (byte b1 = 0; b1 < this.length; b1++) {
/* 177 */       if (this.names[b1] == paramName) b = b1; 
/*     */     } 
/* 179 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void noteDuplicate(int paramInt1, int paramInt2) {
/* 186 */     LambdaForm.Name name = this.names[paramInt1];
/* 187 */     assert name == this.names[paramInt2];
/* 188 */     assert this.originalNames[paramInt1] != null;
/* 189 */     assert this.originalNames[paramInt2] == null || this.originalNames[paramInt2] == name;
/* 190 */     if (this.dups == null) {
/* 191 */       this.dups = new ArrayList<>();
/*     */     }
/* 193 */     this.dups.add(name);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearDuplicatesAndNulls() {
/* 198 */     if (this.dups != null) {
/*     */       
/* 200 */       assert ownedCount() >= 1;
/* 201 */       for (LambdaForm.Name name : this.dups) {
/* 202 */         for (int k = this.firstChange; k < this.length; k++) {
/* 203 */           if (this.names[k] == name && this.originalNames[k] != name) {
/* 204 */             this.names[k] = null;
/* 205 */             assert Arrays.<LambdaForm.Name>asList(this.names).contains(name);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 210 */       this.dups.clear();
/*     */     } 
/*     */     
/* 213 */     int i = this.length;
/* 214 */     for (int j = this.firstChange; j < this.length; j++) {
/* 215 */       if (this.names[j] == null) {
/* 216 */         System.arraycopy(this.names, j + 1, this.names, j, --this.length - j);
/* 217 */         j--;
/*     */       } 
/*     */     } 
/* 220 */     if (this.length < i) {
/* 221 */       Arrays.fill((Object[])this.names, this.length, i, (Object)null);
/*     */     }
/* 223 */     assert !Arrays.<LambdaForm.Name>asList(this.names).subList(0, this.length).contains(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startEdit() {
/* 230 */     assert verifyArity();
/* 231 */     int i = ownedCount();
/* 232 */     assert !inTrans();
/* 233 */     this.flags = (byte)(this.flags | 0x10);
/* 234 */     LambdaForm.Name[] arrayOfName1 = this.names;
/* 235 */     LambdaForm.Name[] arrayOfName2 = (i == 2) ? this.originalNames : null;
/* 236 */     assert arrayOfName2 != arrayOfName1;
/* 237 */     if (arrayOfName2 != null && arrayOfName2.length >= this.length) {
/* 238 */       this.names = copyNamesInto(arrayOfName2);
/*     */     }
/*     */     else {
/*     */       
/* 242 */       this.names = Arrays.<LambdaForm.Name>copyOf(arrayOfName1, Math.max(this.length + 2, arrayOfName1.length));
/* 243 */       if (i < 2) this.flags = (byte)(this.flags + 1); 
/* 244 */       assert ownedCount() == i + 1;
/*     */     } 
/* 246 */     this.originalNames = arrayOfName1;
/* 247 */     assert this.originalNames != this.names;
/* 248 */     this.firstChange = this.length;
/* 249 */     assert inTrans();
/*     */   }
/*     */   
/*     */   private void changeName(int paramInt, LambdaForm.Name paramName) {
/* 253 */     assert inTrans();
/* 254 */     assert paramInt < this.length;
/* 255 */     LambdaForm.Name name = this.names[paramInt];
/* 256 */     assert name == this.originalNames[paramInt];
/* 257 */     assert verifyFirstChange();
/* 258 */     if (ownedCount() == 0)
/* 259 */       growNames(0, 0); 
/* 260 */     this.names[paramInt] = paramName;
/* 261 */     if (this.firstChange > paramInt) {
/* 262 */       this.firstChange = paramInt;
/*     */     }
/* 264 */     if (this.resultName != null && this.resultName == name) {
/* 265 */       this.resultName = paramName;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void setResult(LambdaForm.Name paramName) {
/* 271 */     assert paramName == null || lastIndexOf(paramName) >= 0;
/* 272 */     this.resultName = paramName;
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaForm endEdit() {
/* 277 */     assert verifyFirstChange();
/*     */ 
/*     */     
/* 280 */     for (int i = Math.max(this.firstChange, this.arity); i < this.length; i++) {
/* 281 */       LambdaForm.Name name = this.names[i];
/* 282 */       if (name != null) {
/* 283 */         LambdaForm.Name name1 = name.replaceNames(this.originalNames, this.names, this.firstChange, i);
/* 284 */         if (name1 != name) {
/* 285 */           this.names[i] = name1;
/* 286 */           if (this.resultName == name)
/* 287 */             this.resultName = name1; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 291 */     assert inTrans();
/* 292 */     this.flags = (byte)(this.flags & 0xFFFFFFEF);
/* 293 */     clearDuplicatesAndNulls();
/* 294 */     this.originalNames = null;
/*     */ 
/*     */ 
/*     */     
/* 298 */     if (this.firstChange < this.arity) {
/* 299 */       LambdaForm.Name[] arrayOfName = new LambdaForm.Name[this.arity - this.firstChange];
/* 300 */       int j = this.firstChange; byte b = 0;
/* 301 */       for (int k = this.firstChange; k < this.arity; k++) {
/* 302 */         LambdaForm.Name name = this.names[k];
/* 303 */         if (name.isParam()) {
/* 304 */           this.names[j++] = name;
/*     */         } else {
/* 306 */           arrayOfName[b++] = name;
/*     */         } 
/*     */       } 
/* 309 */       assert b == this.arity - j;
/*     */       
/* 311 */       System.arraycopy(arrayOfName, 0, this.names, j, b);
/*     */       
/* 313 */       this.arity -= b;
/*     */     } 
/* 315 */     assert verifyArity();
/* 316 */     return lambdaForm();
/*     */   }
/*     */   
/*     */   private LambdaForm.Name[] copyNamesInto(LambdaForm.Name[] paramArrayOfName) {
/* 320 */     System.arraycopy(this.names, 0, paramArrayOfName, 0, this.length);
/* 321 */     Arrays.fill((Object[])paramArrayOfName, this.length, paramArrayOfName.length, (Object)null);
/* 322 */     return paramArrayOfName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LambdaFormBuffer replaceFunctions(LambdaForm.NamedFunction[] paramArrayOfNamedFunction1, LambdaForm.NamedFunction[] paramArrayOfNamedFunction2, Object... paramVarArgs) {
/* 331 */     assert inTrans();
/* 332 */     if (paramArrayOfNamedFunction1.length == 0) return this; 
/* 333 */     for (int i = this.arity; i < this.length; i++) {
/* 334 */       LambdaForm.Name name = this.names[i];
/* 335 */       int j = indexOf(name.function, paramArrayOfNamedFunction1);
/* 336 */       if (j >= 0 && Arrays.equals(name.arguments, paramVarArgs)) {
/* 337 */         changeName(i, new LambdaForm.Name(paramArrayOfNamedFunction2[j], name.arguments));
/*     */       }
/*     */     } 
/* 340 */     return this;
/*     */   }
/*     */   
/*     */   private void replaceName(int paramInt, LambdaForm.Name paramName) {
/* 344 */     assert inTrans();
/* 345 */     assert verifyArity();
/* 346 */     assert paramInt < this.arity;
/* 347 */     LambdaForm.Name name = this.names[paramInt];
/* 348 */     assert name.isParam();
/* 349 */     assert name.type == paramName.type;
/* 350 */     changeName(paramInt, paramName);
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaFormBuffer renameParameter(int paramInt, LambdaForm.Name paramName) {
/* 355 */     assert paramName.isParam();
/* 356 */     replaceName(paramInt, paramName);
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaFormBuffer replaceParameterByNewExpression(int paramInt, LambdaForm.Name paramName) {
/* 362 */     assert !paramName.isParam();
/* 363 */     assert lastIndexOf(paramName) < 0;
/* 364 */     replaceName(paramInt, paramName);
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaFormBuffer replaceParameterByCopy(int paramInt1, int paramInt2) {
/* 370 */     assert paramInt1 != paramInt2;
/* 371 */     replaceName(paramInt1, this.names[paramInt2]);
/* 372 */     noteDuplicate(paramInt1, paramInt2);
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   private void insertName(int paramInt, LambdaForm.Name paramName, boolean paramBoolean) {
/* 377 */     assert inTrans();
/* 378 */     assert verifyArity(); assert false;
/* 379 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LambdaFormBuffer insertExpression(int paramInt, LambdaForm.Name paramName) {
/* 387 */     assert !paramName.isParam();
/* 388 */     insertName(paramInt, paramName, false);
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   LambdaFormBuffer insertParameter(int paramInt, LambdaForm.Name paramName) {
/* 394 */     assert paramName.isParam();
/* 395 */     insertName(paramInt, paramName, true);
/* 396 */     return this;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/LambdaFormBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */