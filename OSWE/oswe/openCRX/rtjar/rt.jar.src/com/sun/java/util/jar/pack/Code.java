/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Code
/*     */   extends Attribute.Holder
/*     */ {
/*     */   Package.Class.Method m;
/*     */   
/*     */   public Package.Class.Method getMethod() {
/*     */     return this.m;
/*     */   }
/*     */   
/*     */   public Package.Class thisClass() {
/*     */     return this.m.thisClass();
/*     */   }
/*     */   
/*     */   public Package getPackage() {
/*     */     return this.m.thisClass().getPackage();
/*     */   }
/*     */   
/*     */   public ConstantPool.Entry[] getCPMap() {
/*     */     return this.m.getCPMap();
/*     */   }
/*     */   
/*     */   private static final ConstantPool.Entry[] noRefs = ConstantPool.noRefs;
/*     */   int max_stack;
/*     */   int max_locals;
/*     */   ConstantPool.Entry[] handler_class;
/*     */   int[] handler_start;
/*     */   int[] handler_end;
/*     */   int[] handler_catch;
/*     */   byte[] bytes;
/*     */   Fixups fixups;
/*     */   Object insnMap;
/*     */   
/*     */   public Code(Package.Class.Method paramMethod) {
/*  65 */     this.handler_class = noRefs;
/*  66 */     this.handler_start = Constants.noInts;
/*  67 */     this.handler_end = Constants.noInts;
/*  68 */     this.handler_catch = Constants.noInts;
/*     */     this.m = paramMethod;
/*     */   }
/*     */   static final boolean shrinkMaps = true;
/*     */   
/*     */   int getLength() {
/*  74 */     return this.bytes.length;
/*     */   }
/*     */   int getMaxStack() {
/*  77 */     return this.max_stack;
/*     */   }
/*     */   void setMaxStack(int paramInt) {
/*  80 */     this.max_stack = paramInt;
/*     */   }
/*     */   
/*     */   int getMaxNALocals() {
/*  84 */     int i = this.m.getArgumentSize();
/*  85 */     return this.max_locals - i;
/*     */   }
/*     */   void setMaxNALocals(int paramInt) {
/*  88 */     int i = this.m.getArgumentSize();
/*  89 */     this.max_locals = i + paramInt;
/*     */   }
/*     */   
/*     */   int getHandlerCount() {
/*  93 */     assert this.handler_class.length == this.handler_start.length;
/*  94 */     assert this.handler_class.length == this.handler_end.length;
/*  95 */     assert this.handler_class.length == this.handler_catch.length;
/*  96 */     return this.handler_class.length;
/*     */   }
/*     */   void setHandlerCount(int paramInt) {
/*  99 */     if (paramInt > 0) {
/* 100 */       this.handler_class = new ConstantPool.Entry[paramInt];
/* 101 */       this.handler_start = new int[paramInt];
/* 102 */       this.handler_end = new int[paramInt];
/* 103 */       this.handler_catch = new int[paramInt];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void setBytes(byte[] paramArrayOfbyte) {
/* 109 */     this.bytes = paramArrayOfbyte;
/* 110 */     if (this.fixups != null) {
/* 111 */       this.fixups.setBytes(paramArrayOfbyte);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void setInstructionMap(int[] paramArrayOfint, int paramInt) {
/* 117 */     this.insnMap = allocateInstructionMap(paramArrayOfint, paramInt);
/*     */   }
/*     */   
/*     */   void setInstructionMap(int[] paramArrayOfint) {
/* 121 */     setInstructionMap(paramArrayOfint, paramArrayOfint.length);
/*     */   }
/*     */   
/*     */   int[] getInstructionMap() {
/* 125 */     return expandInstructionMap(getInsnMap());
/*     */   }
/*     */   
/*     */   void addFixups(Collection<Fixups.Fixup> paramCollection) {
/* 129 */     if (this.fixups == null) {
/* 130 */       this.fixups = new Fixups(this.bytes);
/*     */     }
/* 132 */     assert this.fixups.getBytes() == this.bytes;
/* 133 */     this.fixups.addAll(paramCollection);
/*     */   }
/*     */   
/*     */   public void trimToSize() {
/* 137 */     if (this.fixups != null) {
/* 138 */       this.fixups.trimToSize();
/* 139 */       if (this.fixups.size() == 0)
/* 140 */         this.fixups = null; 
/*     */     } 
/* 142 */     super.trimToSize();
/*     */   }
/*     */   
/*     */   protected void visitRefs(int paramInt, Collection<ConstantPool.Entry> paramCollection) {
/* 146 */     int i = (getPackage()).verbose;
/* 147 */     if (i > 2)
/* 148 */       System.out.println("Reference scan " + this); 
/* 149 */     paramCollection.addAll(Arrays.asList(this.handler_class));
/* 150 */     if (this.fixups != null) {
/* 151 */       this.fixups.visitRefs(paramCollection);
/*     */     } else {
/*     */       
/* 154 */       ConstantPool.Entry[] arrayOfEntry = getCPMap();
/* 155 */       for (Instruction instruction = instructionAt(0); instruction != null; instruction = instruction.next()) {
/* 156 */         if (i > 4)
/* 157 */           System.out.println(instruction); 
/* 158 */         int j = instruction.getCPIndex();
/* 159 */         if (j >= 0) {
/* 160 */           paramCollection.add(arrayOfEntry[j]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     super.visitRefs(paramInt, paramCollection);
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
/*     */   private Object allocateInstructionMap(int[] paramArrayOfint, int paramInt) {
/* 180 */     int i = getLength();
/* 181 */     if (i <= 255) {
/* 182 */       byte[] arrayOfByte = new byte[paramInt + 1];
/* 183 */       for (byte b = 0; b < paramInt; b++) {
/* 184 */         arrayOfByte[b] = (byte)(paramArrayOfint[b] + -128);
/*     */       }
/* 186 */       arrayOfByte[paramInt] = (byte)(i + -128);
/* 187 */       return arrayOfByte;
/* 188 */     }  if (i < 65535) {
/* 189 */       short[] arrayOfShort = new short[paramInt + 1];
/* 190 */       for (byte b = 0; b < paramInt; b++) {
/* 191 */         arrayOfShort[b] = (short)(paramArrayOfint[b] + -32768);
/*     */       }
/* 193 */       arrayOfShort[paramInt] = (short)(i + -32768);
/* 194 */       return arrayOfShort;
/*     */     } 
/* 196 */     int[] arrayOfInt = Arrays.copyOf(paramArrayOfint, paramInt + 1);
/* 197 */     arrayOfInt[paramInt] = i;
/* 198 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private int[] expandInstructionMap(Object paramObject) {
/*     */     int[] arrayOfInt;
/* 203 */     if (paramObject instanceof byte[]) {
/* 204 */       byte[] arrayOfByte = (byte[])paramObject;
/* 205 */       arrayOfInt = new int[arrayOfByte.length - 1];
/* 206 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 207 */         arrayOfInt[b] = arrayOfByte[b] - -128;
/*     */       }
/* 209 */     } else if (paramObject instanceof short[]) {
/* 210 */       short[] arrayOfShort = (short[])paramObject;
/* 211 */       arrayOfInt = new int[arrayOfShort.length - 1];
/* 212 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 213 */         arrayOfInt[b] = arrayOfShort[b] - -128;
/*     */       }
/*     */     } else {
/* 216 */       int[] arrayOfInt1 = (int[])paramObject;
/* 217 */       arrayOfInt = Arrays.copyOfRange(arrayOfInt1, 0, arrayOfInt1.length - 1);
/*     */     } 
/* 219 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   
/*     */   Object getInsnMap() {
/* 224 */     if (this.insnMap != null) {
/* 225 */       return this.insnMap;
/*     */     }
/* 227 */     int[] arrayOfInt = new int[getLength()];
/* 228 */     byte b = 0;
/* 229 */     for (Instruction instruction = instructionAt(0); instruction != null; instruction = instruction.next()) {
/* 230 */       arrayOfInt[b++] = instruction.getPC();
/*     */     }
/*     */     
/* 233 */     this.insnMap = allocateInstructionMap(arrayOfInt, b);
/*     */     
/* 235 */     return this.insnMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int encodeBCI(int paramInt) {
/*     */     int i, j;
/* 246 */     if (paramInt <= 0 || paramInt > getLength()) return paramInt; 
/* 247 */     Object object = getInsnMap();
/*     */     
/* 249 */     if (object instanceof byte[]) {
/* 250 */       byte[] arrayOfByte = (byte[])object;
/* 251 */       j = arrayOfByte.length;
/* 252 */       i = Arrays.binarySearch(arrayOfByte, (byte)(paramInt + -128));
/* 253 */     } else if (object instanceof short[]) {
/* 254 */       short[] arrayOfShort = (short[])object;
/* 255 */       j = arrayOfShort.length;
/* 256 */       i = Arrays.binarySearch(arrayOfShort, (short)(paramInt + -32768));
/*     */     } else {
/* 258 */       int[] arrayOfInt = (int[])object;
/* 259 */       j = arrayOfInt.length;
/* 260 */       i = Arrays.binarySearch(arrayOfInt, paramInt);
/*     */     } 
/* 262 */     assert i != -1;
/* 263 */     assert i != 0;
/* 264 */     assert i != j;
/* 265 */     assert i != -j - 1;
/* 266 */     return (i >= 0) ? i : (j + paramInt - -i - 1);
/*     */   } public int decodeBCI(int paramInt) {
/*     */     int i, j;
/* 269 */     if (paramInt <= 0 || paramInt > getLength()) return paramInt; 
/* 270 */     Object object = getInsnMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (object instanceof byte[]) {
/* 290 */       byte[] arrayOfByte = (byte[])object;
/* 291 */       j = arrayOfByte.length;
/* 292 */       if (paramInt < j)
/* 293 */         return arrayOfByte[paramInt] - -128; 
/* 294 */       i = Arrays.binarySearch(arrayOfByte, (byte)(paramInt + -128));
/* 295 */       if (i < 0) i = -i - 1; 
/* 296 */       int k = paramInt - j + -128;
/*     */       
/* 298 */       while (arrayOfByte[i - 1] - i - 1 > k)
/*     */         i--; 
/* 300 */     } else if (object instanceof short[]) {
/* 301 */       short[] arrayOfShort = (short[])object;
/* 302 */       j = arrayOfShort.length;
/* 303 */       if (paramInt < j)
/* 304 */         return arrayOfShort[paramInt] - -32768; 
/* 305 */       i = Arrays.binarySearch(arrayOfShort, (short)(paramInt + -32768));
/* 306 */       if (i < 0) i = -i - 1; 
/* 307 */       int k = paramInt - j + -32768;
/*     */       
/* 309 */       while (arrayOfShort[i - 1] - i - 1 > k)
/*     */         i--; 
/*     */     } else {
/* 312 */       int[] arrayOfInt = (int[])object;
/* 313 */       j = arrayOfInt.length;
/* 314 */       if (paramInt < j)
/* 315 */         return arrayOfInt[paramInt]; 
/* 316 */       i = Arrays.binarySearch(arrayOfInt, paramInt);
/* 317 */       if (i < 0) i = -i - 1; 
/* 318 */       int k = paramInt - j;
/*     */       
/* 320 */       while (arrayOfInt[i - 1] - i - 1 > k)
/*     */         i--; 
/*     */     } 
/* 323 */     return paramInt - j + i;
/*     */   }
/*     */   
/*     */   public void finishRefs(ConstantPool.Index paramIndex) {
/* 327 */     if (this.fixups != null) {
/* 328 */       this.fixups.finishRefs(paramIndex);
/* 329 */       this.fixups = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   Instruction instructionAt(int paramInt) {
/* 335 */     return Instruction.at(this.bytes, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean flagsRequireCode(int paramInt) {
/* 341 */     return ((paramInt & 0x500) == 0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 345 */     return this.m + ".Code";
/*     */   }
/*     */   
/*     */   public int getInt(int paramInt) {
/* 349 */     return Instruction.getInt(this.bytes, paramInt);
/* 350 */   } public int getShort(int paramInt) { return Instruction.getShort(this.bytes, paramInt); }
/* 351 */   public int getByte(int paramInt) { return Instruction.getByte(this.bytes, paramInt); }
/* 352 */   void setInt(int paramInt1, int paramInt2) { Instruction.setInt(this.bytes, paramInt1, paramInt2); }
/* 353 */   void setShort(int paramInt1, int paramInt2) { Instruction.setShort(this.bytes, paramInt1, paramInt2); } void setByte(int paramInt1, int paramInt2) {
/* 354 */     Instruction.setByte(this.bytes, paramInt1, paramInt2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/Code.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */