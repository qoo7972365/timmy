/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassReader
/*     */ {
/*     */   int verbose;
/*     */   Package pkg;
/*     */   Package.Class cls;
/*     */   long inPos;
/*  57 */   long constantPoolLimit = -1L;
/*     */   DataInputStream in;
/*     */   Map<Attribute.Layout, Attribute> attrDefs;
/*     */   Map<Attribute.Layout, String> attrCommands;
/*  61 */   String unknownAttrCommand = "error";
/*     */   
/*     */   ClassReader(Package.Class paramClass, InputStream paramInputStream) throws IOException {
/*  64 */     this.pkg = paramClass.getPackage();
/*  65 */     this.cls = paramClass;
/*  66 */     this.verbose = this.pkg.verbose;
/*  67 */     this.in = new DataInputStream(new FilterInputStream(paramInputStream) {
/*     */           public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/*  69 */             int i = super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*  70 */             if (i >= 0) ClassReader.this.inPos += i; 
/*  71 */             return i;
/*     */           }
/*     */           public int read() throws IOException {
/*  74 */             int i = super.read();
/*  75 */             if (i >= 0) ClassReader.this.inPos++; 
/*  76 */             return i;
/*     */           }
/*     */           public long skip(long param1Long) throws IOException {
/*  79 */             long l = super.skip(param1Long);
/*  80 */             if (l >= 0L) ClassReader.this.inPos += l; 
/*  81 */             return l;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setAttrDefs(Map<Attribute.Layout, Attribute> paramMap) {
/*  87 */     this.attrDefs = paramMap;
/*     */   }
/*     */   
/*     */   public void setAttrCommands(Map<Attribute.Layout, String> paramMap) {
/*  91 */     this.attrCommands = paramMap;
/*     */   }
/*     */   
/*     */   private void skip(int paramInt, String paramString) throws IOException {
/*  95 */     Utils.log.warning("skipping " + paramInt + " bytes of " + paramString);
/*  96 */     long l = 0L;
/*  97 */     while (l < paramInt) {
/*  98 */       long l1 = this.in.skip(paramInt - l);
/*  99 */       assert l1 > 0L;
/* 100 */       l += l1;
/*     */     } 
/* 102 */     assert l == paramInt;
/*     */   }
/*     */   
/*     */   private int readUnsignedShort() throws IOException {
/* 106 */     return this.in.readUnsignedShort();
/*     */   }
/*     */   
/*     */   private int readInt() throws IOException {
/* 110 */     return this.in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.Entry readRef() throws IOException {
/* 115 */     int i = this.in.readUnsignedShort();
/* 116 */     return (i == 0) ? null : this.cls.cpMap[i];
/*     */   }
/*     */   
/*     */   private ConstantPool.Entry readRef(byte paramByte) throws IOException {
/* 120 */     ConstantPool.Entry entry = readRef();
/* 121 */     assert !(entry instanceof UnresolvedEntry);
/* 122 */     checkTag(entry, paramByte);
/* 123 */     return entry;
/*     */   }
/*     */   
/*     */   private ConstantPool.Entry checkValid(ConstantPool.Entry paramEntry) {
/* 127 */     if (paramEntry == INVALID_ENTRY) {
/* 128 */       throw new IllegalStateException("Invalid constant pool reference");
/*     */     }
/* 130 */     return paramEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.Entry checkTag(ConstantPool.Entry paramEntry, byte paramByte) throws ClassFormatException {
/* 135 */     if (paramEntry == null || !paramEntry.tagMatches(paramByte)) {
/* 136 */       String str1 = (this.inPos == this.constantPoolLimit) ? " in constant pool" : (" at pos: " + this.inPos);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       String str2 = (paramEntry == null) ? "null CP index" : ("type=" + ConstantPool.tagName(paramEntry.tag));
/* 142 */       throw new ClassFormatException("Bad constant, expected type=" + 
/* 143 */           ConstantPool.tagName(paramByte) + " got " + str2 + ", in File: " + this.cls.file.nameString + str1);
/*     */     } 
/*     */     
/* 146 */     return paramEntry;
/*     */   }
/*     */   private ConstantPool.Entry checkTag(ConstantPool.Entry paramEntry, byte paramByte, boolean paramBoolean) throws ClassFormatException {
/* 149 */     return (paramBoolean && paramEntry == null) ? null : checkTag(paramEntry, paramByte);
/*     */   }
/*     */   
/*     */   private ConstantPool.Entry readRefOrNull(byte paramByte) throws IOException {
/* 153 */     ConstantPool.Entry entry = readRef();
/* 154 */     checkTag(entry, paramByte, true);
/* 155 */     return entry;
/*     */   }
/*     */   
/*     */   private ConstantPool.Utf8Entry readUtf8Ref() throws IOException {
/* 159 */     return (ConstantPool.Utf8Entry)readRef((byte)1);
/*     */   }
/*     */   
/*     */   private ConstantPool.ClassEntry readClassRef() throws IOException {
/* 163 */     return (ConstantPool.ClassEntry)readRef((byte)7);
/*     */   }
/*     */   
/*     */   private ConstantPool.ClassEntry readClassRefOrNull() throws IOException {
/* 167 */     return (ConstantPool.ClassEntry)readRefOrNull((byte)7);
/*     */   }
/*     */ 
/*     */   
/*     */   private ConstantPool.SignatureEntry readSignatureRef() throws IOException {
/* 172 */     ConstantPool.Entry entry = readRef((byte)13);
/* 173 */     return (entry != null && entry.getTag() == 1) ? 
/* 174 */       ConstantPool.getSignatureEntry(entry.stringValue()) : (ConstantPool.SignatureEntry)entry;
/*     */   }
/*     */ 
/*     */   
/*     */   void read() throws IOException {
/* 179 */     boolean bool = false;
/*     */     try {
/* 181 */       readMagicNumbers();
/* 182 */       readConstantPool();
/* 183 */       readHeader();
/* 184 */       readMembers(false);
/* 185 */       readMembers(true);
/* 186 */       readAttributes(0, this.cls);
/* 187 */       fixUnresolvedEntries();
/* 188 */       this.cls.finishReading();
/* 189 */       assert 0 >= this.in.read(new byte[1]);
/* 190 */       bool = true;
/*     */     } finally {
/* 192 */       if (!bool && 
/* 193 */         this.verbose > 0) Utils.log.warning("Erroneous data at input offset " + this.inPos + " of " + this.cls.file);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   void readMagicNumbers() throws IOException {
/* 199 */     this.cls.magic = this.in.readInt();
/* 200 */     if (this.cls.magic != -889275714) {
/* 201 */       throw new Attribute.FormatException("Bad magic number in class file " + 
/*     */           
/* 203 */           Integer.toHexString(this.cls.magic), 0, "magic-number", "pass");
/*     */     }
/* 205 */     short s1 = (short)readUnsignedShort();
/* 206 */     short s2 = (short)readUnsignedShort();
/* 207 */     this.cls.version = Package.Version.of(s2, s1);
/*     */ 
/*     */     
/* 210 */     String str = checkVersion(this.cls.version);
/* 211 */     if (str != null) {
/* 212 */       throw new Attribute.FormatException("classfile version too " + str + ": " + this.cls.version + " in " + this.cls.file, 0, "version", "pass");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String checkVersion(Package.Version paramVersion) {
/* 220 */     short s1 = paramVersion.major;
/* 221 */     short s2 = paramVersion.minor;
/* 222 */     if (s1 < this.pkg.minClassVersion.major || (s1 == this.pkg.minClassVersion.major && s2 < this.pkg.minClassVersion.minor))
/*     */     {
/*     */       
/* 225 */       return "small";
/*     */     }
/* 227 */     if (s1 > this.pkg.maxClassVersion.major || (s1 == this.pkg.maxClassVersion.major && s2 > this.pkg.maxClassVersion.minor))
/*     */     {
/*     */       
/* 230 */       return "large";
/*     */     }
/* 232 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 236 */   private static final ConstantPool.Entry INVALID_ENTRY = new ConstantPool.Entry((byte)-1)
/*     */     {
/*     */       public boolean equals(Object param1Object) {
/* 239 */         throw new IllegalStateException("Should not call this");
/*     */       }
/*     */ 
/*     */       
/*     */       protected int computeValueHash() {
/* 244 */         throw new IllegalStateException("Should not call this");
/*     */       }
/*     */ 
/*     */       
/*     */       public int compareTo(Object param1Object) {
/* 249 */         throw new IllegalStateException("Should not call this");
/*     */       }
/*     */ 
/*     */       
/*     */       public String stringValue() {
/* 254 */         throw new IllegalStateException("Should not call this");
/*     */       }
/*     */     };
/*     */   boolean haveUnresolvedEntry;
/*     */   void readConstantPool() throws IOException {
/* 259 */     int i = this.in.readUnsignedShort();
/*     */ 
/*     */     
/* 262 */     int[] arrayOfInt = new int[i * 4];
/* 263 */     byte b1 = 0;
/*     */     
/* 265 */     ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[i];
/* 266 */     arrayOfEntry[0] = INVALID_ENTRY; byte b2;
/* 267 */     for (b2 = 1; b2 < i; b2++) {
/*     */       
/* 269 */       byte b = this.in.readByte();
/* 270 */       switch (b) {
/*     */         case 1:
/* 272 */           arrayOfEntry[b2] = ConstantPool.getUtf8Entry(this.in.readUTF());
/*     */           break;
/*     */         
/*     */         case 3:
/* 276 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Integer.valueOf(this.in.readInt()));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 4:
/* 281 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Float.valueOf(this.in.readFloat()));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 5:
/* 286 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Long.valueOf(this.in.readLong()));
/* 287 */           arrayOfEntry[++b2] = INVALID_ENTRY;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 6:
/* 292 */           arrayOfEntry[b2] = ConstantPool.getLiteralEntry(Double.valueOf(this.in.readDouble()));
/* 293 */           arrayOfEntry[++b2] = INVALID_ENTRY;
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 7:
/*     */         case 8:
/*     */         case 16:
/* 301 */           arrayOfInt[b1++] = b2;
/* 302 */           arrayOfInt[b1++] = b;
/* 303 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/* 304 */           arrayOfInt[b1++] = -1;
/*     */           break;
/*     */         case 9:
/*     */         case 10:
/*     */         case 11:
/*     */         case 12:
/* 310 */           arrayOfInt[b1++] = b2;
/* 311 */           arrayOfInt[b1++] = b;
/* 312 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/* 313 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         case 18:
/* 316 */           arrayOfInt[b1++] = b2;
/* 317 */           arrayOfInt[b1++] = b;
/* 318 */           arrayOfInt[b1++] = 0xFFFFFFFF ^ this.in.readUnsignedShort();
/* 319 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         case 15:
/* 322 */           arrayOfInt[b1++] = b2;
/* 323 */           arrayOfInt[b1++] = b;
/* 324 */           arrayOfInt[b1++] = 0xFFFFFFFF ^ this.in.readUnsignedByte();
/* 325 */           arrayOfInt[b1++] = this.in.readUnsignedShort();
/*     */           break;
/*     */         default:
/* 328 */           throw new ClassFormatException("Bad constant pool tag " + b + " in File: " + this.cls.file.nameString + " at pos: " + this.inPos);
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 333 */     this.constantPoolLimit = this.inPos;
/*     */ 
/*     */     
/* 336 */     while (b1 > 0) {
/* 337 */       if (this.verbose > 3)
/* 338 */         Utils.log.fine("CP fixups [" + (b1 / 4) + "]"); 
/* 339 */       b2 = b1;
/* 340 */       b1 = 0;
/* 341 */       for (byte b = 0; b < b2; ) {
/* 342 */         ConstantPool.ClassEntry classEntry; ConstantPool.DescriptorEntry descriptorEntry1; ConstantPool.Utf8Entry utf8Entry1, utf8Entry2; byte b3; ConstantPool.MemberEntry memberEntry; ConstantPool.DescriptorEntry descriptorEntry2; int j = arrayOfInt[b++];
/* 343 */         int k = arrayOfInt[b++];
/* 344 */         int m = arrayOfInt[b++];
/* 345 */         int n = arrayOfInt[b++];
/* 346 */         if (this.verbose > 3)
/* 347 */           Utils.log.fine("  cp[" + j + "] = " + ConstantPool.tagName(k) + "{" + m + "," + n + "}"); 
/* 348 */         if ((m >= 0 && checkValid(arrayOfEntry[m]) == null) || (n >= 0 && checkValid(arrayOfEntry[n]) == null)) {
/*     */           
/* 350 */           arrayOfInt[b1++] = j;
/* 351 */           arrayOfInt[b1++] = k;
/* 352 */           arrayOfInt[b1++] = m;
/* 353 */           arrayOfInt[b1++] = n;
/*     */           continue;
/*     */         } 
/* 356 */         switch (k) {
/*     */           case 7:
/* 358 */             arrayOfEntry[j] = ConstantPool.getClassEntry(arrayOfEntry[m].stringValue());
/*     */             continue;
/*     */           case 8:
/* 361 */             arrayOfEntry[j] = ConstantPool.getStringEntry(arrayOfEntry[m].stringValue());
/*     */             continue;
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/* 366 */             classEntry = (ConstantPool.ClassEntry)checkTag(arrayOfEntry[m], (byte)7);
/* 367 */             descriptorEntry1 = (ConstantPool.DescriptorEntry)checkTag(arrayOfEntry[n], (byte)12);
/* 368 */             arrayOfEntry[j] = ConstantPool.getMemberEntry((byte)k, classEntry, descriptorEntry1);
/*     */             continue;
/*     */           case 12:
/* 371 */             utf8Entry1 = (ConstantPool.Utf8Entry)checkTag(arrayOfEntry[m], (byte)1);
/* 372 */             utf8Entry2 = (ConstantPool.Utf8Entry)checkTag(arrayOfEntry[n], (byte)13);
/* 373 */             arrayOfEntry[j] = ConstantPool.getDescriptorEntry(utf8Entry1, utf8Entry2);
/*     */             continue;
/*     */           case 16:
/* 376 */             arrayOfEntry[j] = ConstantPool.getMethodTypeEntry((ConstantPool.Utf8Entry)checkTag(arrayOfEntry[m], (byte)13));
/*     */             continue;
/*     */           case 15:
/* 379 */             b3 = (byte)(0xFFFFFFFF ^ m);
/* 380 */             memberEntry = (ConstantPool.MemberEntry)checkTag(arrayOfEntry[n], (byte)52);
/* 381 */             arrayOfEntry[j] = ConstantPool.getMethodHandleEntry(b3, memberEntry);
/*     */             continue;
/*     */           case 18:
/* 384 */             descriptorEntry2 = (ConstantPool.DescriptorEntry)checkTag(arrayOfEntry[n], (byte)12);
/* 385 */             arrayOfEntry[j] = new UnresolvedEntry((byte)k, new Object[] { Integer.valueOf(0xFFFFFFFF ^ m), descriptorEntry2 });
/*     */             continue;
/*     */         } 
/*     */ 
/*     */         
/*     */         assert false;
/*     */       } 
/* 392 */       assert b1 < b2;
/*     */     } 
/*     */     
/* 395 */     this.cls.cpMap = arrayOfEntry;
/*     */   }
/*     */   
/*     */   private class UnresolvedEntry extends ConstantPool.Entry {
/*     */     final Object[] refsOrIndexes;
/*     */     
/* 401 */     UnresolvedEntry(byte param1Byte, Object... param1VarArgs) { super(param1Byte);
/* 402 */       this.refsOrIndexes = param1VarArgs;
/* 403 */       ClassReader.this.haveUnresolvedEntry = true; } ConstantPool.Entry resolve() {
/*     */       ConstantPool.BootstrapMethodEntry bootstrapMethodEntry;
/*     */       ConstantPool.DescriptorEntry descriptorEntry;
/* 406 */       Package.Class clazz = ClassReader.this.cls;
/*     */       
/* 408 */       switch (this.tag) {
/*     */         case 18:
/* 410 */           bootstrapMethodEntry = clazz.bootstrapMethods.get(((Integer)this.refsOrIndexes[0]).intValue());
/* 411 */           descriptorEntry = (ConstantPool.DescriptorEntry)this.refsOrIndexes[1];
/* 412 */           return ConstantPool.getInvokeDynamicEntry(bootstrapMethodEntry, descriptorEntry);
/*     */       } 
/*     */       
/* 415 */       throw new AssertionError();
/*     */     }
/*     */     
/*     */     private void unresolved() {
/* 419 */       throw new RuntimeException("unresolved entry has no string");
/* 420 */     } public int compareTo(Object param1Object) { unresolved(); return 0; }
/* 421 */     public boolean equals(Object param1Object) { unresolved(); return false; }
/* 422 */     protected int computeValueHash() { unresolved(); return 0; }
/* 423 */     public String stringValue() { unresolved(); return toString(); } public String toString() {
/* 424 */       return "(unresolved " + ConstantPool.tagName(this.tag) + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private void fixUnresolvedEntries() {
/* 429 */     if (!this.haveUnresolvedEntry)
/* 430 */       return;  ConstantPool.Entry[] arrayOfEntry = this.cls.getCPMap();
/* 431 */     for (byte b = 0; b < arrayOfEntry.length; b++) {
/* 432 */       ConstantPool.Entry entry = arrayOfEntry[b];
/* 433 */       if (entry instanceof UnresolvedEntry) {
/* 434 */         arrayOfEntry[b] = entry = ((UnresolvedEntry)entry).resolve();
/* 435 */         assert !(entry instanceof UnresolvedEntry);
/*     */       } 
/*     */     } 
/* 438 */     this.haveUnresolvedEntry = false;
/*     */   }
/*     */   
/*     */   void readHeader() throws IOException {
/* 442 */     this.cls.flags = readUnsignedShort();
/* 443 */     this.cls.thisClass = readClassRef();
/* 444 */     this.cls.superClass = readClassRefOrNull();
/* 445 */     int i = readUnsignedShort();
/* 446 */     this.cls.interfaces = new ConstantPool.ClassEntry[i];
/* 447 */     for (byte b = 0; b < i; b++) {
/* 448 */       this.cls.interfaces[b] = readClassRef();
/*     */     }
/*     */   }
/*     */   
/*     */   void readMembers(boolean paramBoolean) throws IOException {
/* 453 */     int i = readUnsignedShort();
/* 454 */     for (byte b = 0; b < i; b++)
/* 455 */       readMember(paramBoolean); 
/*     */   }
/*     */   
/*     */   void readMember(boolean paramBoolean) throws IOException {
/*     */     Package.Class.Method method;
/* 460 */     int i = readUnsignedShort();
/* 461 */     ConstantPool.Utf8Entry utf8Entry = readUtf8Ref();
/* 462 */     ConstantPool.SignatureEntry signatureEntry = readSignatureRef();
/* 463 */     ConstantPool.DescriptorEntry descriptorEntry = ConstantPool.getDescriptorEntry(utf8Entry, signatureEntry);
/*     */     
/* 465 */     if (!paramBoolean) {
/* 466 */       this.cls.getClass(); Package.Class.Field field = new Package.Class.Field(this.cls, i, descriptorEntry);
/*     */     } else {
/* 468 */       this.cls.getClass(); method = new Package.Class.Method(this.cls, i, descriptorEntry);
/* 469 */     }  readAttributes(!paramBoolean ? 1 : 2, method);
/*     */   }
/*     */   
/*     */   void readAttributes(int paramInt, Attribute.Holder paramHolder) throws IOException {
/* 473 */     int i = readUnsignedShort();
/* 474 */     if (i == 0)
/* 475 */       return;  if (this.verbose > 3)
/* 476 */       Utils.log.fine("readAttributes " + paramHolder + " [" + i + "]"); 
/* 477 */     for (byte b = 0; b < i; ) {
/* 478 */       String str = readUtf8Ref().stringValue();
/* 479 */       int j = readInt();
/*     */       
/* 481 */       if (this.attrCommands != null) {
/* 482 */         Attribute.Layout layout = Attribute.keyForLookup(paramInt, str);
/* 483 */         String str1 = this.attrCommands.get(layout);
/* 484 */         if (str1 != null) {
/* 485 */           Attribute attribute; boolean bool; String str2; long l; String str3, str4; switch (str1) {
/*     */             case "pass":
/* 487 */               str3 = "passing attribute bitwise in " + paramHolder;
/* 488 */               throw new Attribute.FormatException(str3, paramInt, str, str1);
/*     */             case "error":
/* 490 */               str4 = "attribute not allowed in " + paramHolder;
/* 491 */               throw new Attribute.FormatException(str4, paramInt, str, str1);
/*     */             case "strip":
/* 493 */               skip(j, str + " attribute in " + paramHolder);
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             default:
/* 499 */               attribute = Attribute.lookup(Package.attrDefs, paramInt, str);
/* 500 */               if (this.verbose > 4 && attribute != null)
/* 501 */                 Utils.log.fine("pkg_attribute_lookup " + str + " = " + attribute); 
/* 502 */               if (attribute == null) {
/* 503 */                 attribute = Attribute.lookup(this.attrDefs, paramInt, str);
/* 504 */                 if (this.verbose > 4 && attribute != null)
/* 505 */                   Utils.log.fine("this " + str + " = " + attribute); 
/*     */               } 
/* 507 */               if (attribute == null) {
/* 508 */                 attribute = Attribute.lookup(null, paramInt, str);
/* 509 */                 if (this.verbose > 4 && attribute != null)
/* 510 */                   Utils.log.fine("null_attribute_lookup " + str + " = " + attribute); 
/*     */               } 
/* 512 */               if (attribute == null && j == 0)
/*     */               {
/*     */ 
/*     */                 
/* 516 */                 attribute = Attribute.find(paramInt, str, "");
/*     */               }
/*     */ 
/*     */               
/* 520 */               bool = (paramInt == 3 && (str.equals("StackMap") || str.equals("StackMapX"))) ? true : false;
/* 521 */               if (bool) {
/*     */                 
/* 523 */                 Code code = (Code)paramHolder;
/*     */                 
/* 525 */                 if (code.max_stack >= 65536 || code.max_locals >= 65536 || code
/*     */                   
/* 527 */                   .getLength() >= 65536 || str
/* 528 */                   .endsWith("X"))
/*     */                 {
/*     */                   
/* 531 */                   attribute = null;
/*     */                 }
/*     */               } 
/* 534 */               if (attribute == null) {
/* 535 */                 if (bool) {
/*     */                   
/* 537 */                   str2 = "unsupported StackMap variant in " + paramHolder;
/* 538 */                   throw new Attribute.FormatException(str2, paramInt, str, "pass");
/*     */                 } 
/* 540 */                 if ("strip".equals(this.unknownAttrCommand)) {
/*     */                   
/* 542 */                   skip(j, "unknown " + str + " attribute in " + paramHolder);
/*     */                   break;
/*     */                 } 
/* 545 */                 str2 = " is unknown attribute in class " + paramHolder;
/* 546 */                 throw new Attribute.FormatException(str2, paramInt, str, this.unknownAttrCommand);
/*     */               } 
/*     */ 
/*     */               
/* 550 */               l = this.inPos;
/* 551 */               if (attribute.layout() == Package.attrCodeEmpty) {
/*     */                 
/* 553 */                 Package.Class.Method method = (Package.Class.Method)paramHolder;
/* 554 */                 method.code = new Code(method);
/*     */                 try {
/* 556 */                   readCode(method.code);
/* 557 */                 } catch (FormatException formatException) {
/* 558 */                   String str5 = formatException.getMessage() + " in " + paramHolder;
/* 559 */                   throw new ClassFormatException(str5, formatException);
/*     */                 } 
/* 561 */                 assert j == this.inPos - l;
/*     */               } else {
/* 563 */                 if (attribute.layout() == Package.attrBootstrapMethodsEmpty) {
/* 564 */                   assert paramHolder == this.cls;
/* 565 */                   readBootstrapMethods(this.cls);
/* 566 */                   assert j == this.inPos - l;
/*     */                   break;
/*     */                 } 
/* 569 */                 if (attribute.layout() == Package.attrInnerClassesEmpty) {
/*     */                   
/* 571 */                   assert paramHolder == this.cls;
/* 572 */                   readInnerClasses(this.cls);
/* 573 */                   assert j == this.inPos - l;
/*     */                 }
/* 575 */                 else if (j > 0) {
/* 576 */                   byte[] arrayOfByte = new byte[j];
/* 577 */                   this.in.readFully(arrayOfByte);
/* 578 */                   attribute = attribute.addContent(arrayOfByte);
/*     */                 } 
/* 580 */               }  if (attribute.size() == 0 && !attribute.layout().isEmpty()) {
/* 581 */                 throw new ClassFormatException(str + ": attribute length cannot be zero, in " + paramHolder);
/*     */               }
/*     */               
/* 584 */               paramHolder.addAttribute(attribute);
/* 585 */               if (this.verbose > 2)
/* 586 */                 Utils.log.fine("read " + attribute);  break;
/*     */           } 
/*     */           b++;
/*     */         } 
/*     */       } 
/* 591 */     }  } void readCode(Code paramCode) throws IOException { paramCode.max_stack = readUnsignedShort();
/* 592 */     paramCode.max_locals = readUnsignedShort();
/* 593 */     paramCode.bytes = new byte[readInt()];
/* 594 */     this.in.readFully(paramCode.bytes);
/* 595 */     ConstantPool.Entry[] arrayOfEntry = this.cls.getCPMap();
/* 596 */     Instruction.opcodeChecker(paramCode.bytes, arrayOfEntry, this.cls.version);
/* 597 */     int i = readUnsignedShort();
/* 598 */     paramCode.setHandlerCount(i);
/* 599 */     for (byte b = 0; b < i; b++) {
/* 600 */       paramCode.handler_start[b] = readUnsignedShort();
/* 601 */       paramCode.handler_end[b] = readUnsignedShort();
/* 602 */       paramCode.handler_catch[b] = readUnsignedShort();
/* 603 */       paramCode.handler_class[b] = readClassRefOrNull();
/*     */     } 
/* 605 */     readAttributes(3, paramCode); }
/*     */ 
/*     */   
/*     */   void readBootstrapMethods(Package.Class paramClass) throws IOException {
/* 609 */     ConstantPool.BootstrapMethodEntry[] arrayOfBootstrapMethodEntry = new ConstantPool.BootstrapMethodEntry[readUnsignedShort()];
/* 610 */     for (byte b = 0; b < arrayOfBootstrapMethodEntry.length; b++) {
/* 611 */       ConstantPool.MethodHandleEntry methodHandleEntry = (ConstantPool.MethodHandleEntry)readRef((byte)15);
/* 612 */       ConstantPool.Entry[] arrayOfEntry = new ConstantPool.Entry[readUnsignedShort()];
/* 613 */       for (byte b1 = 0; b1 < arrayOfEntry.length; b1++) {
/* 614 */         arrayOfEntry[b1] = readRef((byte)51);
/*     */       }
/* 616 */       arrayOfBootstrapMethodEntry[b] = ConstantPool.getBootstrapMethodEntry(methodHandleEntry, arrayOfEntry);
/*     */     } 
/* 618 */     paramClass.setBootstrapMethods(Arrays.asList(arrayOfBootstrapMethodEntry));
/*     */   }
/*     */   
/*     */   void readInnerClasses(Package.Class paramClass) throws IOException {
/* 622 */     int i = readUnsignedShort();
/* 623 */     ArrayList<Package.InnerClass> arrayList = new ArrayList(i);
/* 624 */     for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 629 */       Package.InnerClass innerClass = new Package.InnerClass(readClassRef(), readClassRefOrNull(), (ConstantPool.Utf8Entry)readRefOrNull((byte)1), readUnsignedShort());
/* 630 */       arrayList.add(innerClass);
/*     */     } 
/* 632 */     paramClass.innerClasses = arrayList;
/*     */   }
/*     */   
/*     */   static class ClassFormatException
/*     */     extends IOException {
/*     */     private static final long serialVersionUID = -3564121733989501833L;
/*     */     
/*     */     public ClassFormatException(String param1String) {
/* 640 */       super(param1String);
/*     */     }
/*     */     
/*     */     public ClassFormatException(String param1String, Throwable param1Throwable) {
/* 644 */       super(param1String, param1Throwable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/ClassReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */