/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Instruction
/*     */ {
/*     */   protected byte[] bytes;
/*     */   protected int pc;
/*     */   protected int bc;
/*     */   protected int w;
/*     */   protected int length;
/*     */   protected boolean special;
/*     */   
/*     */   protected Instruction(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  47 */     reset(paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   private void reset(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  50 */     this.bytes = paramArrayOfbyte;
/*  51 */     this.pc = paramInt1;
/*  52 */     this.bc = paramInt2;
/*  53 */     this.w = paramInt3;
/*  54 */     this.length = paramInt4;
/*     */   }
/*     */   
/*     */   public int getBC() {
/*  58 */     return this.bc;
/*     */   }
/*     */   public boolean isWide() {
/*  61 */     return (this.w != 0);
/*     */   }
/*     */   public byte[] getBytes() {
/*  64 */     return this.bytes;
/*     */   }
/*     */   public int getPC() {
/*  67 */     return this.pc;
/*     */   }
/*     */   public int getLength() {
/*  70 */     return this.length;
/*     */   }
/*     */   public int getNextPC() {
/*  73 */     return this.pc + this.length;
/*     */   }
/*     */   
/*     */   public Instruction next() {
/*  77 */     int i = this.pc + this.length;
/*  78 */     if (i == this.bytes.length) {
/*  79 */       return null;
/*     */     }
/*  81 */     return at(this.bytes, i, this);
/*     */   }
/*     */   
/*     */   public boolean isNonstandard() {
/*  85 */     return isNonstandard(this.bc);
/*     */   }
/*     */   
/*     */   public void setNonstandardLength(int paramInt) {
/*  89 */     assert isNonstandard();
/*  90 */     this.length = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction forceNextPC(int paramInt) {
/*  96 */     int i = paramInt - this.pc;
/*  97 */     return new Instruction(this.bytes, this.pc, -1, -1, i);
/*     */   }
/*     */   
/*     */   public static Instruction at(byte[] paramArrayOfbyte, int paramInt) {
/* 101 */     return at(paramArrayOfbyte, paramInt, null);
/*     */   }
/*     */   
/*     */   public static Instruction at(byte[] paramArrayOfbyte, int paramInt, Instruction paramInstruction) {
/* 105 */     int i = getByte(paramArrayOfbyte, paramInt);
/* 106 */     byte b = -1;
/* 107 */     boolean bool = false;
/* 108 */     byte b1 = BC_LENGTH[bool][i];
/* 109 */     if (b1 == 0)
/*     */     {
/* 111 */       switch (i) {
/*     */         case 196:
/* 113 */           i = getByte(paramArrayOfbyte, paramInt + 1);
/* 114 */           bool = true;
/* 115 */           b1 = BC_LENGTH[bool][i];
/* 116 */           if (b1 == 0)
/*     */           {
/* 118 */             b1 = 1;
/*     */           }
/*     */           break;
/*     */         case 170:
/* 122 */           return new TableSwitch(paramArrayOfbyte, paramInt);
/*     */         case 171:
/* 124 */           return new LookupSwitch(paramArrayOfbyte, paramInt);
/*     */         
/*     */         default:
/* 127 */           b1 = 1;
/*     */           break;
/*     */       } 
/*     */     }
/* 131 */     assert b1 > 0;
/* 132 */     assert paramInt + b1 <= paramArrayOfbyte.length;
/*     */     
/* 134 */     if (paramInstruction != null && !paramInstruction.special) {
/* 135 */       paramInstruction.reset(paramArrayOfbyte, paramInt, i, bool, b1);
/* 136 */       return paramInstruction;
/*     */     } 
/* 138 */     return new Instruction(paramArrayOfbyte, paramInt, i, bool, b1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getCPTag() {
/* 143 */     return BC_TAG[this.w][this.bc];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCPIndex() {
/* 148 */     byte b = BC_INDEX[this.w][this.bc];
/* 149 */     if (b == 0) return -1; 
/* 150 */     assert this.w == 0;
/* 151 */     if (this.length == 2) {
/* 152 */       return getByte(this.bytes, this.pc + b);
/*     */     }
/* 154 */     return getShort(this.bytes, this.pc + b);
/*     */   }
/*     */   
/*     */   public void setCPIndex(int paramInt) {
/* 158 */     byte b = BC_INDEX[this.w][this.bc];
/* 159 */     assert b != 0;
/* 160 */     if (this.length == 2) {
/* 161 */       setByte(this.bytes, this.pc + b, paramInt);
/*     */     } else {
/* 163 */       setShort(this.bytes, this.pc + b, paramInt);
/* 164 */     }  assert getCPIndex() == paramInt;
/*     */   }
/*     */   
/*     */   public ConstantPool.Entry getCPRef(ConstantPool.Entry[] paramArrayOfEntry) {
/* 168 */     int i = getCPIndex();
/* 169 */     return (i < 0) ? null : paramArrayOfEntry[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocalSlot() {
/* 174 */     byte b = BC_SLOT[this.w][this.bc];
/* 175 */     if (b == 0) return -1; 
/* 176 */     if (this.w == 0) {
/* 177 */       return getByte(this.bytes, this.pc + b);
/*     */     }
/* 179 */     return getShort(this.bytes, this.pc + b);
/*     */   }
/*     */   
/*     */   public int getBranchLabel() {
/*     */     int i;
/* 184 */     byte b = BC_BRANCH[this.w][this.bc];
/* 185 */     if (b == 0) return -1; 
/* 186 */     assert this.w == 0;
/* 187 */     assert this.length == 3 || this.length == 5;
/*     */     
/* 189 */     if (this.length == 3) {
/* 190 */       i = (short)getShort(this.bytes, this.pc + b);
/*     */     } else {
/* 192 */       i = getInt(this.bytes, this.pc + b);
/* 193 */     }  assert i + this.pc >= 0;
/* 194 */     assert i + this.pc <= this.bytes.length;
/* 195 */     return i + this.pc;
/*     */   }
/*     */   
/*     */   public void setBranchLabel(int paramInt) {
/* 199 */     byte b = BC_BRANCH[this.w][this.bc];
/* 200 */     assert b != 0;
/* 201 */     if (this.length == 3) {
/* 202 */       setShort(this.bytes, this.pc + b, paramInt - this.pc);
/*     */     } else {
/* 204 */       setInt(this.bytes, this.pc + b, paramInt - this.pc);
/* 205 */     }  assert paramInt == getBranchLabel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getConstant() {
/* 211 */     byte b = BC_CON[this.w][this.bc];
/* 212 */     if (b == 0) return 0; 
/* 213 */     switch (this.length - b) { case 1:
/* 214 */         return (byte)getByte(this.bytes, this.pc + b);
/* 215 */       case 2: return (short)getShort(this.bytes, this.pc + b); }
/*     */     
/*     */     assert false;
/* 218 */     return 0;
/*     */   }
/*     */   
/*     */   public void setConstant(int paramInt) {
/* 222 */     byte b = BC_CON[this.w][this.bc];
/* 223 */     assert b != 0;
/* 224 */     switch (this.length - b) { case 1:
/* 225 */         setByte(this.bytes, this.pc + b, paramInt); break;
/* 226 */       case 2: setShort(this.bytes, this.pc + b, paramInt); break; }
/*     */     
/* 228 */     assert paramInt == getConstant();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class Switch
/*     */     extends Instruction
/*     */   {
/*     */     protected int apc;
/*     */ 
/*     */ 
/*     */     
/*     */     public int getDefaultLabel() {
/* 241 */       return intAt(0) + this.pc; } public void setDefaultLabel(int param1Int) {
/* 242 */       setIntAt(0, param1Int - this.pc);
/*     */     }
/*     */     
/* 245 */     protected int intAt(int param1Int) { return getInt(this.bytes, this.apc + param1Int * 4); } protected void setIntAt(int param1Int1, int param1Int2) {
/* 246 */       setInt(this.bytes, this.apc + param1Int1 * 4, param1Int2);
/*     */     } protected Switch(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 248 */       super(param1ArrayOfbyte, param1Int1, param1Int2, 0, 0);
/* 249 */       this.apc = alignPC(param1Int1 + 1);
/* 250 */       this.special = true;
/* 251 */       this.length = getLength(getCaseCount());
/*     */     } public int getAlignedPC() {
/* 253 */       return this.apc;
/*     */     } public String toString() {
/* 255 */       String str = super.toString();
/* 256 */       str = str + " Default:" + labstr(getDefaultLabel());
/* 257 */       int i = getCaseCount();
/* 258 */       for (byte b = 0; b < i; b++) {
/* 259 */         str = str + "\n\tCase " + getCaseValue(b) + ":" + labstr(getCaseLabel(b));
/*     */       }
/* 261 */       return str;
/*     */     } public abstract int getCaseCount(); public abstract int getCaseValue(int param1Int); public abstract int getCaseLabel(int param1Int); public abstract void setCaseCount(int param1Int);
/*     */     public static int alignPC(int param1Int) {
/* 264 */       for (; param1Int % 4 != 0; param1Int++);
/* 265 */       return param1Int;
/*     */     }
/*     */     public abstract void setCaseValue(int param1Int1, int param1Int2);
/*     */     public abstract void setCaseLabel(int param1Int1, int param1Int2);
/*     */     protected abstract int getLength(int param1Int); }
/*     */   public static class TableSwitch extends Switch { public int getLowCase() {
/* 271 */       return intAt(1); }
/* 272 */     public int getHighCase() { return intAt(2); }
/* 273 */     public int getCaseCount() { return intAt(2) - intAt(1) + 1; }
/* 274 */     public int getCaseValue(int param1Int) { return getLowCase() + param1Int; } public int getCaseLabel(int param1Int) {
/* 275 */       return intAt(3 + param1Int) + this.pc;
/*     */     }
/* 277 */     public void setLowCase(int param1Int) { setIntAt(1, param1Int); }
/* 278 */     public void setHighCase(int param1Int) { setIntAt(2, param1Int); } public void setCaseLabel(int param1Int1, int param1Int2) {
/* 279 */       setIntAt(3 + param1Int1, param1Int2 - this.pc);
/*     */     } public void setCaseCount(int param1Int) {
/* 281 */       setHighCase(getLowCase() + param1Int - 1);
/* 282 */       this.length = getLength(param1Int);
/*     */     }
/*     */     public void setCaseValue(int param1Int1, int param1Int2) {
/* 285 */       if (param1Int1 != 0) throw new UnsupportedOperationException(); 
/* 286 */       int i = getCaseCount();
/* 287 */       setLowCase(param1Int2);
/* 288 */       setCaseCount(i);
/*     */     }
/*     */     
/*     */     TableSwitch(byte[] param1ArrayOfbyte, int param1Int) {
/* 292 */       super(param1ArrayOfbyte, param1Int, 170);
/*     */     }
/*     */     protected int getLength(int param1Int) {
/* 295 */       return this.apc - this.pc + (3 + param1Int) * 4;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class LookupSwitch extends Switch {
/*     */     public int getCaseCount() {
/* 301 */       return intAt(1);
/* 302 */     } public int getCaseValue(int param1Int) { return intAt(2 + param1Int * 2 + 0); } public int getCaseLabel(int param1Int) {
/* 303 */       return intAt(2 + param1Int * 2 + 1) + this.pc;
/*     */     }
/*     */     public void setCaseCount(int param1Int) {
/* 306 */       setIntAt(1, param1Int);
/* 307 */       this.length = getLength(param1Int);
/*     */     }
/* 309 */     public void setCaseValue(int param1Int1, int param1Int2) { setIntAt(2 + param1Int1 * 2 + 0, param1Int2); } public void setCaseLabel(int param1Int1, int param1Int2) {
/* 310 */       setIntAt(2 + param1Int1 * 2 + 1, param1Int2 - this.pc);
/*     */     }
/*     */     LookupSwitch(byte[] param1ArrayOfbyte, int param1Int) {
/* 313 */       super(param1ArrayOfbyte, param1Int, 171);
/*     */     }
/*     */     protected int getLength(int param1Int) {
/* 316 */       return this.apc - this.pc + (2 + param1Int * 2) * 4;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 322 */     return (paramObject != null && paramObject.getClass() == Instruction.class && 
/* 323 */       equals((Instruction)paramObject));
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 327 */     int i = 3;
/* 328 */     i = 11 * i + Arrays.hashCode(this.bytes);
/* 329 */     i = 11 * i + this.pc;
/* 330 */     i = 11 * i + this.bc;
/* 331 */     i = 11 * i + this.w;
/* 332 */     i = 11 * i + this.length;
/* 333 */     return i;
/*     */   }
/*     */   
/*     */   public boolean equals(Instruction paramInstruction) {
/* 337 */     if (this.pc != paramInstruction.pc) return false; 
/* 338 */     if (this.bc != paramInstruction.bc) return false; 
/* 339 */     if (this.w != paramInstruction.w) return false; 
/* 340 */     if (this.length != paramInstruction.length) return false; 
/* 341 */     for (byte b = 1; b < this.length; b++) {
/* 342 */       if (this.bytes[this.pc + b] != paramInstruction.bytes[paramInstruction.pc + b])
/* 343 */         return false; 
/*     */     } 
/* 345 */     return true;
/*     */   }
/*     */   
/*     */   static String labstr(int paramInt) {
/* 349 */     if (paramInt >= 0 && paramInt < 100000)
/* 350 */       return ((100000 + paramInt) + "").substring(1); 
/* 351 */     return paramInt + "";
/*     */   }
/*     */   public String toString() {
/* 354 */     return toString(null);
/*     */   }
/*     */   public String toString(ConstantPool.Entry[] paramArrayOfEntry) {
/* 357 */     String str1 = labstr(this.pc) + ": ";
/* 358 */     if (this.bc >= 202) {
/* 359 */       str1 = str1 + Integer.toHexString(this.bc);
/* 360 */       return str1;
/*     */     } 
/* 362 */     if (this.w == 1) str1 = str1 + "wide "; 
/* 363 */     String str2 = (this.bc < BC_NAME.length) ? BC_NAME[this.bc] : null;
/* 364 */     if (str2 == null) {
/* 365 */       return str1 + "opcode#" + this.bc;
/*     */     }
/* 367 */     str1 = str1 + str2;
/* 368 */     byte b = getCPTag();
/* 369 */     if (b != 0) str1 = str1 + " " + ConstantPool.tagName(b) + ":"; 
/* 370 */     int i = getCPIndex();
/* 371 */     if (i >= 0) str1 = str1 + ((paramArrayOfEntry == null) ? ("" + i) : ("=" + paramArrayOfEntry[i].stringValue())); 
/* 372 */     int j = getLocalSlot();
/* 373 */     if (j >= 0) str1 = str1 + " Local:" + j; 
/* 374 */     int k = getBranchLabel();
/* 375 */     if (k >= 0) str1 = str1 + " To:" + labstr(k); 
/* 376 */     int m = getConstant();
/* 377 */     if (m != 0) str1 = str1 + " Con:" + m; 
/* 378 */     return str1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntAt(int paramInt) {
/* 387 */     return getInt(this.bytes, this.pc + paramInt);
/*     */   }
/*     */   public int getShortAt(int paramInt) {
/* 390 */     return getShort(this.bytes, this.pc + paramInt);
/*     */   }
/*     */   public int getByteAt(int paramInt) {
/* 393 */     return getByte(this.bytes, this.pc + paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getInt(byte[] paramArrayOfbyte, int paramInt) {
/* 398 */     return (getShort(paramArrayOfbyte, paramInt + 0) << 16) + (getShort(paramArrayOfbyte, paramInt + 2) << 0);
/*     */   }
/*     */   public static int getShort(byte[] paramArrayOfbyte, int paramInt) {
/* 401 */     return (getByte(paramArrayOfbyte, paramInt + 0) << 8) + (getByte(paramArrayOfbyte, paramInt + 1) << 0);
/*     */   }
/*     */   public static int getByte(byte[] paramArrayOfbyte, int paramInt) {
/* 404 */     return paramArrayOfbyte[paramInt] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 409 */     setShort(paramArrayOfbyte, paramInt1 + 0, paramInt2 >> 16);
/* 410 */     setShort(paramArrayOfbyte, paramInt1 + 2, paramInt2 >> 0);
/*     */   }
/*     */   public static void setShort(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 413 */     setByte(paramArrayOfbyte, paramInt1 + 0, paramInt2 >> 8);
/* 414 */     setByte(paramArrayOfbyte, paramInt1 + 1, paramInt2 >> 0);
/*     */   }
/*     */   public static void setByte(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 417 */     paramArrayOfbyte[paramInt1] = (byte)paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNonstandard(int paramInt) {
/* 424 */     return (BC_LENGTH[0][paramInt] < 0);
/*     */   }
/*     */   
/*     */   public static int opLength(int paramInt) {
/* 428 */     byte b = BC_LENGTH[0][paramInt];
/* 429 */     assert b > 0;
/* 430 */     return b;
/*     */   }
/*     */   public static int opWideLength(int paramInt) {
/* 433 */     byte b = BC_LENGTH[1][paramInt];
/* 434 */     assert b > 0;
/* 435 */     return b;
/*     */   }
/*     */   
/*     */   public static boolean isLocalSlotOp(int paramInt) {
/* 439 */     return (paramInt < (BC_SLOT[0]).length && BC_SLOT[0][paramInt] > 0);
/*     */   }
/*     */   
/*     */   public static boolean isBranchOp(int paramInt) {
/* 443 */     return (paramInt < (BC_BRANCH[0]).length && BC_BRANCH[0][paramInt] > 0);
/*     */   }
/*     */   
/*     */   public static boolean isCPRefOp(int paramInt) {
/* 447 */     if (paramInt < (BC_INDEX[0]).length && BC_INDEX[0][paramInt] > 0) return true; 
/* 448 */     if (paramInt >= 233 && paramInt < 242) return true; 
/* 449 */     if (paramInt == 242 || paramInt == 243) return true; 
/* 450 */     return false;
/*     */   }
/*     */   
/*     */   public static byte getCPRefOpTag(int paramInt) {
/* 454 */     if (paramInt < (BC_INDEX[0]).length && BC_INDEX[0][paramInt] > 0) return BC_TAG[0][paramInt]; 
/* 455 */     if (paramInt >= 233 && paramInt < 242) return 51; 
/* 456 */     if (paramInt == 243 || paramInt == 242) return 11; 
/* 457 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isFieldOp(int paramInt) {
/* 461 */     return (paramInt >= 178 && paramInt <= 181);
/*     */   }
/*     */   
/*     */   public static boolean isInvokeInitOp(int paramInt) {
/* 465 */     return (paramInt >= 230 && paramInt < 233);
/*     */   }
/*     */   
/*     */   public static boolean isSelfLinkerOp(int paramInt) {
/* 469 */     return (paramInt >= 202 && paramInt < 230);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 474 */   private static final byte[][] BC_LENGTH = new byte[2][256];
/* 475 */   private static final byte[][] BC_INDEX = new byte[2][256];
/* 476 */   private static final byte[][] BC_TAG = new byte[2][256];
/* 477 */   private static final byte[][] BC_BRANCH = new byte[2][256];
/* 478 */   private static final byte[][] BC_SLOT = new byte[2][256];
/* 479 */   private static final byte[][] BC_CON = new byte[2][256];
/* 480 */   private static final String[] BC_NAME = new String[256];
/* 481 */   private static final String[][] BC_FORMAT = new String[2][202]; static {
/*     */     byte b1;
/* 483 */     for (b1 = 0; b1 < 'Ê'; b1++) {
/* 484 */       BC_LENGTH[0][b1] = -1;
/* 485 */       BC_LENGTH[1][b1] = -1;
/*     */     } 
/* 487 */     def("b", 0, 15);
/* 488 */     def("bx", 16);
/* 489 */     def("bxx", 17);
/* 490 */     def("bk", 18);
/* 491 */     def("bkk", 19, 20);
/* 492 */     def("blwbll", 21, 25);
/* 493 */     def("b", 26, 53);
/* 494 */     def("blwbll", 54, 58);
/* 495 */     def("b", 59, 131);
/* 496 */     def("blxwbllxx", 132);
/* 497 */     def("b", 133, 152);
/* 498 */     def("boo", 153, 168);
/* 499 */     def("blwbll", 169);
/* 500 */     def("", 170, 171);
/* 501 */     def("b", 172, 177);
/* 502 */     def("bkf", 178, 181);
/* 503 */     def("bkm", 182, 184);
/* 504 */     def("bkixx", 185);
/* 505 */     def("bkyxx", 186);
/* 506 */     def("bkc", 187);
/* 507 */     def("bx", 188);
/* 508 */     def("bkc", 189);
/* 509 */     def("b", 190, 191);
/* 510 */     def("bkc", 192, 193);
/* 511 */     def("b", 194, 195);
/* 512 */     def("", 196);
/* 513 */     def("bkcx", 197);
/* 514 */     def("boo", 198, 199);
/* 515 */     def("boooo", 200, 201);
/* 516 */     for (b1 = 0; b1 < 'Ê'; b1++) {
/*     */ 
/*     */       
/* 519 */       if (BC_LENGTH[0][b1] != -1)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 524 */         if (BC_LENGTH[1][b1] == -1)
/* 525 */           BC_LENGTH[1][b1] = (byte)(1 + BC_LENGTH[0][b1]); 
/*     */       }
/*     */     } 
/* 528 */     String str = "nop aconst_null iconst_m1 iconst_0 iconst_1 iconst_2 iconst_3 iconst_4 iconst_5 lconst_0 lconst_1 fconst_0 fconst_1 fconst_2 dconst_0 dconst_1 bipush sipush ldc ldc_w ldc2_w iload lload fload dload aload iload_0 iload_1 iload_2 iload_3 lload_0 lload_1 lload_2 lload_3 fload_0 fload_1 fload_2 fload_3 dload_0 dload_1 dload_2 dload_3 aload_0 aload_1 aload_2 aload_3 iaload laload faload daload aaload baload caload saload istore lstore fstore dstore astore istore_0 istore_1 istore_2 istore_3 lstore_0 lstore_1 lstore_2 lstore_3 fstore_0 fstore_1 fstore_2 fstore_3 dstore_0 dstore_1 dstore_2 dstore_3 astore_0 astore_1 astore_2 astore_3 iastore lastore fastore dastore aastore bastore castore sastore pop pop2 dup dup_x1 dup_x2 dup2 dup2_x1 dup2_x2 swap iadd ladd fadd dadd isub lsub fsub dsub imul lmul fmul dmul idiv ldiv fdiv ddiv irem lrem frem drem ineg lneg fneg dneg ishl lshl ishr lshr iushr lushr iand land ior lor ixor lxor iinc i2l i2f i2d l2i l2f l2d f2i f2l f2d d2i d2l d2f i2b i2c i2s lcmp fcmpl fcmpg dcmpl dcmpg ifeq ifne iflt ifge ifgt ifle if_icmpeq if_icmpne if_icmplt if_icmpge if_icmpgt if_icmple if_acmpeq if_acmpne goto jsr ret tableswitch lookupswitch ireturn lreturn freturn dreturn areturn return getstatic putstatic getfield putfield invokevirtual invokespecial invokestatic invokeinterface invokedynamic new newarray anewarray arraylength athrow checkcast instanceof monitorenter monitorexit wide multianewarray ifnull ifnonnull goto_w jsr_w ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 550 */     for (byte b2 = 0; str.length() > 0; b2++) {
/* 551 */       int i = str.indexOf(' ');
/* 552 */       BC_NAME[b2] = str.substring(0, i);
/* 553 */       str = str.substring(i + 1);
/*     */     } 
/*     */   }
/*     */   public static String byteName(int paramInt) {
/*     */     String str;
/* 558 */     if (paramInt < BC_NAME.length && BC_NAME[paramInt] != null)
/* 559 */     { str = BC_NAME[paramInt]; }
/* 560 */     else if (isSelfLinkerOp(paramInt))
/* 561 */     { int i = paramInt - 202;
/* 562 */       boolean bool1 = (i >= 14) ? true : false;
/* 563 */       if (bool1) i -= 14; 
/* 564 */       boolean bool2 = (i >= 7) ? true : false;
/* 565 */       if (bool2) i -= 7; 
/* 566 */       int j = 178 + i;
/* 567 */       assert j >= 178 && j <= 184;
/* 568 */       str = BC_NAME[j];
/* 569 */       str = str + (bool1 ? "_super" : "_this");
/* 570 */       if (bool2) str = "aload_0&" + str; 
/* 571 */       str = "*" + str; }
/* 572 */     else if (isInvokeInitOp(paramInt))
/* 573 */     { int i = paramInt - 230;
/* 574 */       switch (i)
/*     */       { case 0:
/* 576 */           str = "*invokespecial_init_this";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 600 */           return str;case 1: str = "*invokespecial_init_super"; return str; }  assert i == 2; str = "*invokespecial_init_new"; } else { switch (paramInt) { case 234: str = "*ildc"; return str;case 235: str = "*fldc"; return str;case 237: str = "*ildc_w"; return str;case 238: str = "*fldc_w"; return str;case 239: str = "*dldc2_w"; return str;case 233: str = "*cldc"; return str;case 236: str = "*cldc_w"; return str;case 240: str = "*qldc"; return str;case 241: str = "*qldc_w"; return str;case 254: str = "*byte_escape"; return str;case 253: str = "*ref_escape"; return str;case 255: str = "*end"; return str; }  str = "*bc#" + paramInt; }  return str;
/*     */   }
/* 602 */   private static int BW = 4;
/*     */   private static void def(String paramString, int paramInt) {
/* 604 */     def(paramString, paramInt, paramInt);
/*     */   }
/*     */   private static void def(String paramString, int paramInt1, int paramInt2) {
/* 607 */     String[] arrayOfString = { paramString, null };
/* 608 */     if (paramString.indexOf('w') > 0) {
/* 609 */       arrayOfString[1] = paramString.substring(paramString.indexOf('w'));
/* 610 */       arrayOfString[0] = paramString.substring(0, paramString.indexOf('w'));
/*     */     } 
/* 612 */     for (byte b = 0; b <= 1; b++) {
/* 613 */       paramString = arrayOfString[b];
/* 614 */       if (paramString != null) {
/* 615 */         int i = paramString.length();
/* 616 */         int j = Math.max(0, paramString.indexOf('k'));
/* 617 */         byte b1 = 0;
/* 618 */         int k = Math.max(0, paramString.indexOf('o'));
/* 619 */         int m = Math.max(0, paramString.indexOf('l'));
/* 620 */         int n = Math.max(0, paramString.indexOf('x'));
/* 621 */         if (j > 0 && j + 1 < i) {
/* 622 */           switch (paramString.charAt(j + 1)) { case 'c':
/* 623 */               b1 = 7; break;
/* 624 */             case 'k': b1 = 51; break;
/* 625 */             case 'f': b1 = 9; break;
/* 626 */             case 'm': b1 = 10; break;
/* 627 */             case 'i': b1 = 11; break;
/* 628 */             case 'y': b1 = 18; break; }
/*     */           
/* 630 */           assert b1 != 0;
/* 631 */         } else if (j > 0 && i == 2) {
/* 632 */           assert paramInt1 == 18;
/* 633 */           b1 = 51;
/*     */         } 
/* 635 */         for (int i1 = paramInt1; i1 <= paramInt2; i1++) {
/* 636 */           BC_FORMAT[b][i1] = paramString;
/* 637 */           assert BC_LENGTH[b][i1] == -1;
/* 638 */           BC_LENGTH[b][i1] = (byte)i;
/* 639 */           BC_INDEX[b][i1] = (byte)j;
/* 640 */           BC_TAG[b][i1] = (byte)b1;
/* 641 */           assert j != 0 || b1 == 0;
/* 642 */           BC_BRANCH[b][i1] = (byte)k;
/* 643 */           BC_SLOT[b][i1] = (byte)m;
/* 644 */           assert k == 0 || m == 0;
/* 645 */           assert k == 0 || j == 0;
/* 646 */           assert m == 0 || j == 0;
/* 647 */           BC_CON[b][i1] = (byte)n;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void opcodeChecker(byte[] paramArrayOfbyte, ConstantPool.Entry[] paramArrayOfEntry, Package.Version paramVersion) throws FormatException {
/* 654 */     Instruction instruction = at(paramArrayOfbyte, 0);
/* 655 */     while (instruction != null) {
/* 656 */       int i = instruction.getBC();
/* 657 */       if (i < 0 || i > 201) {
/* 658 */         String str = "illegal opcode: " + i + " " + instruction;
/* 659 */         throw new FormatException(str);
/*     */       } 
/* 661 */       ConstantPool.Entry entry = instruction.getCPRef(paramArrayOfEntry);
/* 662 */       if (entry != null) {
/* 663 */         byte b = instruction.getCPTag();
/* 664 */         boolean bool = entry.tagMatches(b);
/* 665 */         if (!bool && (instruction.bc == 183 || instruction.bc == 184) && entry
/*     */           
/* 667 */           .tagMatches(11) && paramVersion
/* 668 */           .greaterThan(Constants.JAVA7_MAX_CLASS_VERSION)) {
/* 669 */           bool = true;
/*     */         }
/* 671 */         if (!bool) {
/*     */ 
/*     */           
/* 674 */           String str = "illegal reference, expected type=" + ConstantPool.tagName(b) + ": " + instruction.toString(paramArrayOfEntry);
/* 675 */           throw new FormatException(str);
/*     */         } 
/*     */       } 
/* 678 */       instruction = instruction.next();
/*     */     } 
/*     */   }
/*     */   
/*     */   static class FormatException extends IOException { private static final long serialVersionUID = 3175572275651367015L;
/*     */     
/*     */     FormatException(String param1String) {
/* 685 */       super(param1String);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Instruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */