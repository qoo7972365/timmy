/*      */ package com.sun.java.util.jar.pack;
/*      */ 
/*      */ import java.util.AbstractList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ abstract class ConstantPool
/*      */ {
/*      */   static int verbose() {
/*   47 */     return Utils.currentPropMap().getInteger("com.sun.java.util.jar.pack.verbose");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized Utf8Entry getUtf8Entry(String paramString) {
/*   55 */     Map<String, Utf8Entry> map = Utils.getTLGlobals().getUtf8Entries();
/*   56 */     Utf8Entry utf8Entry = map.get(paramString);
/*   57 */     if (utf8Entry == null) {
/*   58 */       utf8Entry = new Utf8Entry(paramString);
/*   59 */       map.put(utf8Entry.stringValue(), utf8Entry);
/*      */     } 
/*   61 */     return utf8Entry;
/*      */   }
/*      */   
/*      */   public static ClassEntry getClassEntry(String paramString) {
/*   65 */     Map<String, ClassEntry> map = Utils.getTLGlobals().getClassEntries();
/*   66 */     ClassEntry classEntry = map.get(paramString);
/*   67 */     if (classEntry == null) {
/*   68 */       classEntry = new ClassEntry(getUtf8Entry(paramString));
/*   69 */       assert paramString.equals(classEntry.stringValue());
/*   70 */       map.put(classEntry.stringValue(), classEntry);
/*      */     } 
/*   72 */     return classEntry;
/*      */   }
/*      */   
/*      */   public static LiteralEntry getLiteralEntry(Comparable<?> paramComparable) {
/*   76 */     Map<Object, LiteralEntry> map = Utils.getTLGlobals().getLiteralEntries();
/*   77 */     LiteralEntry literalEntry = map.get(paramComparable);
/*   78 */     if (literalEntry == null) {
/*   79 */       if (paramComparable instanceof String) {
/*   80 */         literalEntry = new StringEntry(getUtf8Entry((String)paramComparable));
/*      */       } else {
/*   82 */         literalEntry = new NumberEntry((Number)paramComparable);
/*   83 */       }  map.put(paramComparable, literalEntry);
/*      */     } 
/*   85 */     return literalEntry;
/*      */   }
/*      */   
/*      */   public static StringEntry getStringEntry(String paramString) {
/*   89 */     return (StringEntry)getLiteralEntry(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SignatureEntry getSignatureEntry(String paramString) {
/*   94 */     Map<String, SignatureEntry> map = Utils.getTLGlobals().getSignatureEntries();
/*   95 */     SignatureEntry signatureEntry = map.get(paramString);
/*   96 */     if (signatureEntry == null) {
/*   97 */       signatureEntry = new SignatureEntry(paramString);
/*   98 */       assert signatureEntry.stringValue().equals(paramString);
/*   99 */       map.put(paramString, signatureEntry);
/*      */     } 
/*  101 */     return signatureEntry;
/*      */   }
/*      */   
/*      */   public static SignatureEntry getSignatureEntry(Utf8Entry paramUtf8Entry, ClassEntry[] paramArrayOfClassEntry) {
/*  105 */     return getSignatureEntry(SignatureEntry.stringValueOf(paramUtf8Entry, paramArrayOfClassEntry));
/*      */   }
/*      */ 
/*      */   
/*      */   public static DescriptorEntry getDescriptorEntry(Utf8Entry paramUtf8Entry, SignatureEntry paramSignatureEntry) {
/*  110 */     Map<String, DescriptorEntry> map = Utils.getTLGlobals().getDescriptorEntries();
/*  111 */     String str = DescriptorEntry.stringValueOf(paramUtf8Entry, paramSignatureEntry);
/*  112 */     DescriptorEntry descriptorEntry = map.get(str);
/*  113 */     if (descriptorEntry == null) {
/*  114 */       descriptorEntry = new DescriptorEntry(paramUtf8Entry, paramSignatureEntry);
/*  115 */       assert descriptorEntry.stringValue().equals(str) : descriptorEntry
/*  116 */         .stringValue() + " != " + str;
/*  117 */       map.put(str, descriptorEntry);
/*      */     } 
/*  119 */     return descriptorEntry;
/*      */   }
/*      */   
/*      */   public static DescriptorEntry getDescriptorEntry(Utf8Entry paramUtf8Entry1, Utf8Entry paramUtf8Entry2) {
/*  123 */     return getDescriptorEntry(paramUtf8Entry1, getSignatureEntry(paramUtf8Entry2.stringValue()));
/*      */   }
/*      */ 
/*      */   
/*      */   public static MemberEntry getMemberEntry(byte paramByte, ClassEntry paramClassEntry, DescriptorEntry paramDescriptorEntry) {
/*  128 */     Map<String, MemberEntry> map = Utils.getTLGlobals().getMemberEntries();
/*  129 */     String str = MemberEntry.stringValueOf(paramByte, paramClassEntry, paramDescriptorEntry);
/*  130 */     MemberEntry memberEntry = map.get(str);
/*  131 */     if (memberEntry == null) {
/*  132 */       memberEntry = new MemberEntry(paramByte, paramClassEntry, paramDescriptorEntry);
/*  133 */       assert memberEntry.stringValue().equals(str) : memberEntry
/*  134 */         .stringValue() + " != " + str;
/*  135 */       map.put(str, memberEntry);
/*      */     } 
/*  137 */     return memberEntry;
/*      */   }
/*      */ 
/*      */   
/*      */   public static MethodHandleEntry getMethodHandleEntry(byte paramByte, MemberEntry paramMemberEntry) {
/*  142 */     Map<String, MethodHandleEntry> map = Utils.getTLGlobals().getMethodHandleEntries();
/*  143 */     String str = MethodHandleEntry.stringValueOf(paramByte, paramMemberEntry);
/*  144 */     MethodHandleEntry methodHandleEntry = map.get(str);
/*  145 */     if (methodHandleEntry == null) {
/*  146 */       methodHandleEntry = new MethodHandleEntry(paramByte, paramMemberEntry);
/*  147 */       assert methodHandleEntry.stringValue().equals(str);
/*  148 */       map.put(str, methodHandleEntry);
/*      */     } 
/*  150 */     return methodHandleEntry;
/*      */   }
/*      */ 
/*      */   
/*      */   public static MethodTypeEntry getMethodTypeEntry(SignatureEntry paramSignatureEntry) {
/*  155 */     Map<String, MethodTypeEntry> map = Utils.getTLGlobals().getMethodTypeEntries();
/*  156 */     String str = paramSignatureEntry.stringValue();
/*  157 */     MethodTypeEntry methodTypeEntry = map.get(str);
/*  158 */     if (methodTypeEntry == null) {
/*  159 */       methodTypeEntry = new MethodTypeEntry(paramSignatureEntry);
/*  160 */       assert methodTypeEntry.stringValue().equals(str);
/*  161 */       map.put(str, methodTypeEntry);
/*      */     } 
/*  163 */     return methodTypeEntry;
/*      */   }
/*      */   public static MethodTypeEntry getMethodTypeEntry(Utf8Entry paramUtf8Entry) {
/*  166 */     return getMethodTypeEntry(getSignatureEntry(paramUtf8Entry.stringValue()));
/*      */   }
/*      */ 
/*      */   
/*      */   public static InvokeDynamicEntry getInvokeDynamicEntry(BootstrapMethodEntry paramBootstrapMethodEntry, DescriptorEntry paramDescriptorEntry) {
/*  171 */     Map<String, InvokeDynamicEntry> map = Utils.getTLGlobals().getInvokeDynamicEntries();
/*  172 */     String str = InvokeDynamicEntry.stringValueOf(paramBootstrapMethodEntry, paramDescriptorEntry);
/*  173 */     InvokeDynamicEntry invokeDynamicEntry = map.get(str);
/*  174 */     if (invokeDynamicEntry == null) {
/*  175 */       invokeDynamicEntry = new InvokeDynamicEntry(paramBootstrapMethodEntry, paramDescriptorEntry);
/*  176 */       assert invokeDynamicEntry.stringValue().equals(str);
/*  177 */       map.put(str, invokeDynamicEntry);
/*      */     } 
/*  179 */     return invokeDynamicEntry;
/*      */   }
/*      */ 
/*      */   
/*      */   public static BootstrapMethodEntry getBootstrapMethodEntry(MethodHandleEntry paramMethodHandleEntry, Entry[] paramArrayOfEntry) {
/*  184 */     Map<String, BootstrapMethodEntry> map = Utils.getTLGlobals().getBootstrapMethodEntries();
/*  185 */     String str = BootstrapMethodEntry.stringValueOf(paramMethodHandleEntry, paramArrayOfEntry);
/*  186 */     BootstrapMethodEntry bootstrapMethodEntry = map.get(str);
/*  187 */     if (bootstrapMethodEntry == null) {
/*  188 */       bootstrapMethodEntry = new BootstrapMethodEntry(paramMethodHandleEntry, paramArrayOfEntry);
/*  189 */       assert bootstrapMethodEntry.stringValue().equals(str);
/*  190 */       map.put(str, bootstrapMethodEntry);
/*      */     } 
/*  192 */     return bootstrapMethodEntry;
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class Entry
/*      */     implements Comparable<Object>
/*      */   {
/*      */     protected final byte tag;
/*      */     protected int valueHash;
/*      */     
/*      */     protected Entry(byte param1Byte) {
/*  203 */       this.tag = param1Byte;
/*      */     }
/*      */     
/*      */     public final byte getTag() {
/*  207 */       return this.tag;
/*      */     }
/*      */     
/*      */     public final boolean tagEquals(int param1Int) {
/*  211 */       return (getTag() == param1Int);
/*      */     }
/*      */     
/*      */     public Entry getRef(int param1Int) {
/*  215 */       return null;
/*      */     }
/*      */     
/*      */     public boolean eq(Entry param1Entry) {
/*  219 */       assert param1Entry != null;
/*  220 */       return (this == param1Entry || equals(param1Entry));
/*      */     }
/*      */     
/*      */     public abstract boolean equals(Object param1Object);
/*      */     
/*      */     public final int hashCode() {
/*  226 */       if (this.valueHash == 0) {
/*  227 */         this.valueHash = computeValueHash();
/*  228 */         if (this.valueHash == 0) this.valueHash = 1; 
/*      */       } 
/*  230 */       return this.valueHash;
/*      */     }
/*      */     protected abstract int computeValueHash();
/*      */     
/*      */     public abstract int compareTo(Object param1Object);
/*      */     
/*      */     protected int superCompareTo(Object param1Object) {
/*  237 */       Entry entry = (Entry)param1Object;
/*      */       
/*  239 */       if (this.tag != entry.tag) {
/*  240 */         return ConstantPool.TAG_ORDER[this.tag] - ConstantPool.TAG_ORDER[entry.tag];
/*      */       }
/*      */       
/*  243 */       return 0;
/*      */     }
/*      */     
/*      */     public final boolean isDoubleWord() {
/*  247 */       return (this.tag == 6 || this.tag == 5);
/*      */     }
/*      */     public final boolean tagMatches(int param1Int) {
/*      */       byte[] arrayOfByte;
/*  251 */       if (this.tag == param1Int) {
/*  252 */         return true;
/*      */       }
/*  254 */       switch (param1Int) {
/*      */         case 50:
/*  256 */           return true;
/*      */         case 13:
/*  258 */           return (this.tag == 1);
/*      */         case 51:
/*  260 */           arrayOfByte = ConstantPool.LOADABLE_VALUE_TAGS;
/*      */           break;
/*      */         case 52:
/*  263 */           arrayOfByte = ConstantPool.ANY_MEMBER_TAGS;
/*      */           break;
/*      */         case 53:
/*  266 */           arrayOfByte = ConstantPool.FIELD_SPECIFIC_TAGS;
/*      */           break;
/*      */         default:
/*  269 */           return false;
/*      */       } 
/*  271 */       for (byte b : arrayOfByte) {
/*  272 */         if (b == this.tag)
/*  273 */           return true; 
/*      */       } 
/*  275 */       return false;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  279 */       String str = stringValue();
/*  280 */       if (ConstantPool.verbose() > 4) {
/*  281 */         if (this.valueHash != 0)
/*  282 */           str = str + " hash=" + this.valueHash; 
/*  283 */         str = str + " id=" + System.identityHashCode(this);
/*      */       } 
/*  285 */       return ConstantPool.tagName(this.tag) + "=" + str;
/*      */     }
/*      */     
/*      */     public abstract String stringValue();
/*      */   }
/*      */   
/*      */   public static class Utf8Entry extends Entry {
/*      */     final String value;
/*      */     
/*      */     Utf8Entry(String param1String) {
/*  295 */       super((byte)1);
/*  296 */       this.value = param1String.intern();
/*  297 */       hashCode();
/*      */     }
/*      */     protected int computeValueHash() {
/*  300 */       return this.value.hashCode();
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  304 */       return (param1Object != null && param1Object.getClass() == Utf8Entry.class && ((Utf8Entry)param1Object).value
/*  305 */         .equals(this.value));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  308 */       int i = superCompareTo(param1Object);
/*  309 */       if (i == 0) {
/*  310 */         i = this.value.compareTo(((Utf8Entry)param1Object).value);
/*      */       }
/*  312 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  315 */       return this.value;
/*      */     }
/*      */   }
/*      */   
/*      */   static boolean isMemberTag(byte paramByte) {
/*  320 */     switch (paramByte) {
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  324 */         return true;
/*      */     } 
/*  326 */     return false;
/*      */   }
/*      */   
/*      */   static byte numberTagOf(Number paramNumber) {
/*  330 */     if (paramNumber instanceof Integer) return 3; 
/*  331 */     if (paramNumber instanceof Float) return 4; 
/*  332 */     if (paramNumber instanceof Long) return 5; 
/*  333 */     if (paramNumber instanceof Double) return 6; 
/*  334 */     throw new RuntimeException("bad literal value " + paramNumber);
/*      */   }
/*      */   
/*      */   static boolean isRefKind(byte paramByte) {
/*  338 */     return (1 <= paramByte && paramByte <= 9);
/*      */   }
/*      */   
/*      */   public static abstract class LiteralEntry
/*      */     extends Entry {
/*      */     protected LiteralEntry(byte param1Byte) {
/*  344 */       super(param1Byte);
/*      */     }
/*      */     
/*      */     public abstract Comparable<?> literalValue();
/*      */   }
/*      */   
/*      */   public static class NumberEntry extends LiteralEntry {
/*      */     final Number value;
/*      */     
/*      */     NumberEntry(Number param1Number) {
/*  354 */       super(ConstantPool.numberTagOf(param1Number));
/*  355 */       this.value = param1Number;
/*  356 */       hashCode();
/*      */     }
/*      */     protected int computeValueHash() {
/*  359 */       return this.value.hashCode();
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  363 */       return (param1Object != null && param1Object.getClass() == NumberEntry.class && ((NumberEntry)param1Object).value
/*  364 */         .equals(this.value));
/*      */     }
/*      */     
/*      */     public int compareTo(Object param1Object) {
/*  368 */       int i = superCompareTo(param1Object);
/*  369 */       if (i == 0) {
/*      */         
/*  371 */         Comparable<Number> comparable = (Comparable)this.value;
/*  372 */         i = comparable.compareTo(((NumberEntry)param1Object).value);
/*      */       } 
/*  374 */       return i;
/*      */     }
/*      */     public Number numberValue() {
/*  377 */       return this.value;
/*      */     }
/*      */     public Comparable<?> literalValue() {
/*  380 */       return (Comparable)this.value;
/*      */     }
/*      */     public String stringValue() {
/*  383 */       return this.value.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StringEntry extends LiteralEntry { final ConstantPool.Utf8Entry ref;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  390 */       return (param1Int == 0) ? this.ref : null;
/*      */     }
/*      */     StringEntry(ConstantPool.Entry param1Entry) {
/*  393 */       super((byte)8);
/*  394 */       this.ref = (ConstantPool.Utf8Entry)param1Entry;
/*  395 */       hashCode();
/*      */     }
/*      */     protected int computeValueHash() {
/*  398 */       return this.ref.hashCode() + this.tag;
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  401 */       return (param1Object != null && param1Object.getClass() == StringEntry.class && ((StringEntry)param1Object).ref
/*  402 */         .eq(this.ref));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  405 */       int i = superCompareTo(param1Object);
/*  406 */       if (i == 0) {
/*  407 */         i = this.ref.compareTo(((StringEntry)param1Object).ref);
/*      */       }
/*  409 */       return i;
/*      */     }
/*      */     public Comparable<?> literalValue() {
/*  412 */       return this.ref.stringValue();
/*      */     }
/*      */     public String stringValue() {
/*  415 */       return this.ref.stringValue();
/*      */     } }
/*      */   
/*      */   public static class ClassEntry extends Entry {
/*      */     final ConstantPool.Utf8Entry ref;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  422 */       return (param1Int == 0) ? this.ref : null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  425 */       return this.ref.hashCode() + this.tag;
/*      */     }
/*      */     ClassEntry(ConstantPool.Entry param1Entry) {
/*  428 */       super((byte)7);
/*  429 */       this.ref = (ConstantPool.Utf8Entry)param1Entry;
/*  430 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  433 */       return (param1Object != null && param1Object.getClass() == ClassEntry.class && ((ClassEntry)param1Object).ref
/*  434 */         .eq(this.ref));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  437 */       int i = superCompareTo(param1Object);
/*  438 */       if (i == 0) {
/*  439 */         i = this.ref.compareTo(((ClassEntry)param1Object).ref);
/*      */       }
/*  441 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  444 */       return this.ref.stringValue();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DescriptorEntry extends Entry {
/*      */     final ConstantPool.Utf8Entry nameRef;
/*      */     final ConstantPool.SignatureEntry typeRef;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  453 */       if (param1Int == 0) return this.nameRef; 
/*  454 */       if (param1Int == 1) return this.typeRef; 
/*  455 */       return null;
/*      */     }
/*      */     DescriptorEntry(ConstantPool.Entry param1Entry1, ConstantPool.Entry param1Entry2) {
/*  458 */       super((byte)12);
/*  459 */       if (param1Entry2 instanceof ConstantPool.Utf8Entry) {
/*  460 */         param1Entry2 = ConstantPool.getSignatureEntry(param1Entry2.stringValue());
/*      */       }
/*  462 */       this.nameRef = (ConstantPool.Utf8Entry)param1Entry1;
/*  463 */       this.typeRef = (ConstantPool.SignatureEntry)param1Entry2;
/*  464 */       hashCode();
/*      */     }
/*      */     protected int computeValueHash() {
/*  467 */       int i = this.typeRef.hashCode();
/*  468 */       return this.nameRef.hashCode() + (i << 8) ^ i;
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  471 */       if (param1Object == null || param1Object.getClass() != DescriptorEntry.class) {
/*  472 */         return false;
/*      */       }
/*  474 */       DescriptorEntry descriptorEntry = (DescriptorEntry)param1Object;
/*  475 */       return (this.nameRef.eq(descriptorEntry.nameRef) && this.typeRef
/*  476 */         .eq(descriptorEntry.typeRef));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  479 */       int i = superCompareTo(param1Object);
/*  480 */       if (i == 0) {
/*  481 */         DescriptorEntry descriptorEntry = (DescriptorEntry)param1Object;
/*      */         
/*  483 */         i = this.typeRef.compareTo(descriptorEntry.typeRef);
/*  484 */         if (i == 0)
/*  485 */           i = this.nameRef.compareTo(descriptorEntry.nameRef); 
/*      */       } 
/*  487 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  490 */       return stringValueOf(this.nameRef, this.typeRef);
/*      */     }
/*      */     
/*      */     static String stringValueOf(ConstantPool.Entry param1Entry1, ConstantPool.Entry param1Entry2) {
/*  494 */       return ConstantPool.qualifiedStringValue(param1Entry2, param1Entry1);
/*      */     }
/*      */     
/*      */     public String prettyString() {
/*  498 */       return this.nameRef.stringValue() + this.typeRef.prettyString();
/*      */     }
/*      */     
/*      */     public boolean isMethod() {
/*  502 */       return this.typeRef.isMethod();
/*      */     }
/*      */     
/*      */     public byte getLiteralTag() {
/*  506 */       return this.typeRef.getLiteralTag();
/*      */     }
/*      */   }
/*      */   
/*      */   static String qualifiedStringValue(Entry paramEntry1, Entry paramEntry2) {
/*  511 */     return qualifiedStringValue(paramEntry1.stringValue(), paramEntry2.stringValue());
/*      */   }
/*      */   
/*      */   static String qualifiedStringValue(String paramString1, String paramString2) {
/*  515 */     assert paramString1.indexOf(".") < 0;
/*  516 */     return paramString1 + "." + paramString2;
/*      */   }
/*      */   
/*      */   public static class MemberEntry extends Entry {
/*      */     final ConstantPool.ClassEntry classRef;
/*      */     final ConstantPool.DescriptorEntry descRef;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  524 */       if (param1Int == 0) return this.classRef; 
/*  525 */       if (param1Int == 1) return this.descRef; 
/*  526 */       return null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  529 */       int i = this.descRef.hashCode();
/*  530 */       return this.classRef.hashCode() + (i << 8) ^ i;
/*      */     }
/*      */     
/*      */     MemberEntry(byte param1Byte, ConstantPool.ClassEntry param1ClassEntry, ConstantPool.DescriptorEntry param1DescriptorEntry) {
/*  534 */       super(param1Byte);
/*  535 */       assert ConstantPool.isMemberTag(param1Byte);
/*  536 */       this.classRef = param1ClassEntry;
/*  537 */       this.descRef = param1DescriptorEntry;
/*  538 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  541 */       if (param1Object == null || param1Object.getClass() != MemberEntry.class) {
/*  542 */         return false;
/*      */       }
/*  544 */       MemberEntry memberEntry = (MemberEntry)param1Object;
/*  545 */       return (this.classRef.eq(memberEntry.classRef) && this.descRef
/*  546 */         .eq(memberEntry.descRef));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  549 */       int i = superCompareTo(param1Object);
/*  550 */       if (i == 0) {
/*  551 */         MemberEntry memberEntry = (MemberEntry)param1Object;
/*  552 */         if (Utils.SORT_MEMBERS_DESCR_MAJOR)
/*      */         {
/*  554 */           i = this.descRef.compareTo(memberEntry.descRef);
/*      */         }
/*  556 */         if (i == 0)
/*  557 */           i = this.classRef.compareTo(memberEntry.classRef); 
/*  558 */         if (i == 0)
/*  559 */           i = this.descRef.compareTo(memberEntry.descRef); 
/*      */       } 
/*  561 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  564 */       return stringValueOf(this.tag, this.classRef, this.descRef);
/*      */     }
/*      */     
/*      */     static String stringValueOf(byte param1Byte, ConstantPool.ClassEntry param1ClassEntry, ConstantPool.DescriptorEntry param1DescriptorEntry) {
/*  568 */       assert ConstantPool.isMemberTag(param1Byte);
/*      */       
/*  570 */       switch (param1Byte) { case 9:
/*  571 */           str = "Field:";
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  576 */           return str + ConstantPool.qualifiedStringValue(param1ClassEntry, param1DescriptorEntry);case 10: str = "Method:"; return str + ConstantPool.qualifiedStringValue(param1ClassEntry, param1DescriptorEntry);case 11: str = "IMethod:"; return str + ConstantPool.qualifiedStringValue(param1ClassEntry, param1DescriptorEntry); }  String str = param1Byte + "???"; return str + ConstantPool.qualifiedStringValue(param1ClassEntry, param1DescriptorEntry);
/*      */     }
/*      */     
/*      */     public boolean isMethod() {
/*  580 */       return this.descRef.isMethod();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SignatureEntry extends Entry {
/*      */     final ConstantPool.Utf8Entry formRef;
/*      */     final ConstantPool.ClassEntry[] classRefs;
/*      */     String value;
/*      */     ConstantPool.Utf8Entry asUtf8Entry;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  591 */       if (param1Int == 0) return this.formRef; 
/*  592 */       return (param1Int - 1 < this.classRefs.length) ? this.classRefs[param1Int - 1] : null;
/*      */     }
/*      */     SignatureEntry(String param1String) {
/*  595 */       super((byte)13);
/*  596 */       param1String = param1String.intern();
/*  597 */       this.value = param1String;
/*  598 */       String[] arrayOfString = ConstantPool.structureSignature(param1String);
/*  599 */       this.formRef = ConstantPool.getUtf8Entry(arrayOfString[0]);
/*  600 */       this.classRefs = new ConstantPool.ClassEntry[arrayOfString.length - 1];
/*  601 */       for (byte b = 1; b < arrayOfString.length; b++) {
/*  602 */         this.classRefs[b - 1] = ConstantPool.getClassEntry(arrayOfString[b]);
/*      */       }
/*  604 */       hashCode();
/*      */     }
/*      */     protected int computeValueHash() {
/*  607 */       stringValue();
/*  608 */       return this.value.hashCode() + this.tag;
/*      */     }
/*      */     
/*      */     public ConstantPool.Utf8Entry asUtf8Entry() {
/*  612 */       if (this.asUtf8Entry == null) {
/*  613 */         this.asUtf8Entry = ConstantPool.getUtf8Entry(stringValue());
/*      */       }
/*  615 */       return this.asUtf8Entry;
/*      */     }
/*      */     
/*      */     public boolean equals(Object param1Object) {
/*  619 */       return (param1Object != null && param1Object.getClass() == SignatureEntry.class && ((SignatureEntry)param1Object).value
/*  620 */         .equals(this.value));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  623 */       int i = superCompareTo(param1Object);
/*  624 */       if (i == 0) {
/*  625 */         SignatureEntry signatureEntry = (SignatureEntry)param1Object;
/*  626 */         i = ConstantPool.compareSignatures(this.value, signatureEntry.value);
/*      */       } 
/*  628 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  631 */       if (this.value == null) {
/*  632 */         this.value = stringValueOf(this.formRef, this.classRefs);
/*      */       }
/*  634 */       return this.value;
/*      */     }
/*      */     
/*      */     static String stringValueOf(ConstantPool.Utf8Entry param1Utf8Entry, ConstantPool.ClassEntry[] param1ArrayOfClassEntry) {
/*  638 */       String[] arrayOfString = new String[1 + param1ArrayOfClassEntry.length];
/*  639 */       arrayOfString[0] = param1Utf8Entry.stringValue();
/*  640 */       for (byte b = 1; b < arrayOfString.length; b++) {
/*  641 */         arrayOfString[b] = param1ArrayOfClassEntry[b - 1].stringValue();
/*      */       }
/*  643 */       return ConstantPool.flattenSignature(arrayOfString).intern();
/*      */     }
/*      */     
/*      */     public int computeSize(boolean param1Boolean) {
/*  647 */       String str = this.formRef.stringValue();
/*  648 */       byte b1 = 0;
/*  649 */       int i = 1;
/*  650 */       if (isMethod()) {
/*  651 */         b1 = 1;
/*  652 */         i = str.indexOf(')');
/*      */       } 
/*  654 */       byte b2 = 0;
/*  655 */       for (byte b3 = b1; b3 < i; b3++) {
/*  656 */         switch (str.charAt(b3)) {
/*      */           case 'D':
/*      */           case 'J':
/*  659 */             if (param1Boolean) {
/*  660 */               b2++;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  675 */             b2++; break;case '[': while (str.charAt(b3) == '[') b3++;  b2++; break;case ';': break;default: assert 0 <= "BSCIJFDZLV([".indexOf(str.charAt(b3)); b2++; break;
/*      */         } 
/*  677 */       }  return b2;
/*      */     }
/*      */     public boolean isMethod() {
/*  680 */       return (this.formRef.stringValue().charAt(0) == '(');
/*      */     }
/*      */     public byte getLiteralTag() {
/*  683 */       switch (this.formRef.stringValue().charAt(0)) { case 'I':
/*  684 */           return 3;
/*  685 */         case 'J': return 5;
/*  686 */         case 'F': return 4;
/*  687 */         case 'D': return 6;
/*      */         case 'B': case 'C': case 'S': case 'Z':
/*  689 */           return 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 'L':
/*  703 */           return 8; }
/*      */       
/*      */       assert false;
/*  706 */       return 0;
/*      */     }
/*      */     public String prettyString() {
/*      */       String str;
/*  710 */       if (isMethod()) {
/*  711 */         str = this.formRef.stringValue();
/*  712 */         str = str.substring(0, 1 + str.indexOf(')'));
/*      */       } else {
/*  714 */         str = "/" + this.formRef.stringValue();
/*      */       } 
/*      */       int i;
/*  717 */       while ((i = str.indexOf(';')) >= 0) {
/*  718 */         str = str.substring(0, i) + str.substring(i + 1);
/*      */       }
/*  720 */       return str;
/*      */     }
/*      */   }
/*      */   
/*      */   static int compareSignatures(String paramString1, String paramString2) {
/*  725 */     return compareSignatures(paramString1, paramString2, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   static int compareSignatures(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2) {
/*  730 */     char c1 = paramString1.charAt(0);
/*  731 */     char c2 = paramString2.charAt(0);
/*      */     
/*  733 */     if (c1 != '(' && c2 == '(') return -1; 
/*  734 */     if (c2 != '(' && c1 == '(') return 1; 
/*  735 */     if (paramArrayOfString1 == null) paramArrayOfString1 = structureSignature(paramString1); 
/*  736 */     if (paramArrayOfString2 == null) paramArrayOfString2 = structureSignature(paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  744 */     if (paramArrayOfString1.length != paramArrayOfString2.length) return paramArrayOfString1.length - paramArrayOfString2.length; 
/*  745 */     int i = paramArrayOfString1.length;
/*  746 */     for (int j = i; --j >= 0; ) {
/*  747 */       int k = paramArrayOfString1[j].compareTo(paramArrayOfString2[j]);
/*  748 */       if (k != 0) return k; 
/*      */     } 
/*  750 */     assert paramString1.equals(paramString2);
/*  751 */     return 0;
/*      */   }
/*      */   
/*      */   static int countClassParts(Utf8Entry paramUtf8Entry) {
/*  755 */     byte b1 = 0;
/*  756 */     String str = paramUtf8Entry.stringValue();
/*  757 */     for (byte b2 = 0; b2 < str.length(); b2++) {
/*  758 */       if (str.charAt(b2) == 'L') b1++; 
/*      */     } 
/*  760 */     return b1;
/*      */   }
/*      */   
/*      */   static String flattenSignature(String[] paramArrayOfString) {
/*  764 */     String str = paramArrayOfString[0];
/*  765 */     if (paramArrayOfString.length == 1) return str; 
/*  766 */     int i = str.length();
/*  767 */     for (byte b1 = 1; b1 < paramArrayOfString.length; b1++) {
/*  768 */       i += paramArrayOfString[b1].length();
/*      */     }
/*  770 */     char[] arrayOfChar = new char[i];
/*  771 */     int j = 0;
/*  772 */     byte b2 = 1;
/*  773 */     for (byte b3 = 0; b3 < str.length(); b3++) {
/*  774 */       char c = str.charAt(b3);
/*  775 */       arrayOfChar[j++] = c;
/*  776 */       if (c == 'L') {
/*  777 */         String str1 = paramArrayOfString[b2++];
/*  778 */         str1.getChars(0, str1.length(), arrayOfChar, j);
/*  779 */         j += str1.length();
/*      */       } 
/*      */     } 
/*      */     
/*  783 */     assert j == i;
/*  784 */     assert b2 == paramArrayOfString.length;
/*  785 */     return new String(arrayOfChar);
/*      */   }
/*      */   
/*      */   private static int skipTo(char paramChar, String paramString, int paramInt) {
/*  789 */     paramInt = paramString.indexOf(paramChar, paramInt);
/*  790 */     return (paramInt >= 0) ? paramInt : paramString.length();
/*      */   }
/*      */   
/*      */   static String[] structureSignature(String paramString) {
/*  794 */     int i = paramString.indexOf('L');
/*  795 */     if (i < 0) {
/*  796 */       return new String[] { paramString };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     char[] arrayOfChar = null;
/*  808 */     String[] arrayOfString = null;
/*  809 */     for (byte b = 0; b <= 1; b++) {
/*      */       
/*  811 */       int j = 0;
/*  812 */       byte b1 = 1;
/*  813 */       int k = 0, m = 0;
/*  814 */       int n = 0; int i1;
/*  815 */       for (i1 = i + 1; i1 > 0; i1 = paramString.indexOf('L', i2) + 1) {
/*      */ 
/*      */         
/*  818 */         if (k < i1) k = skipTo(';', paramString, i1); 
/*  819 */         if (m < i1) m = skipTo('<', paramString, i1); 
/*  820 */         int i2 = (k < m) ? k : m;
/*  821 */         if (b != 0) {
/*  822 */           paramString.getChars(n, i1, arrayOfChar, j);
/*  823 */           arrayOfString[b1] = paramString.substring(i1, i2);
/*      */         } 
/*  825 */         j += i1 - n;
/*  826 */         b1++;
/*  827 */         n = i2;
/*      */       } 
/*  829 */       if (b != 0) {
/*  830 */         paramString.getChars(n, paramString.length(), arrayOfChar, j);
/*      */         break;
/*      */       } 
/*  833 */       j += paramString.length() - n;
/*  834 */       arrayOfChar = new char[j];
/*  835 */       arrayOfString = new String[b1];
/*      */     } 
/*  837 */     arrayOfString[0] = new String(arrayOfChar);
/*      */     
/*  839 */     return arrayOfString;
/*      */   }
/*      */   
/*      */   public static class MethodHandleEntry extends Entry {
/*      */     final int refKind;
/*      */     final ConstantPool.MemberEntry memRef;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  847 */       return (param1Int == 0) ? this.memRef : null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  850 */       int i = this.refKind;
/*  851 */       return this.memRef.hashCode() + (i << 8) ^ i;
/*      */     }
/*      */     
/*      */     MethodHandleEntry(byte param1Byte, ConstantPool.MemberEntry param1MemberEntry) {
/*  855 */       super((byte)15);
/*  856 */       assert ConstantPool.isRefKind(param1Byte);
/*  857 */       this.refKind = param1Byte;
/*  858 */       this.memRef = param1MemberEntry;
/*  859 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  862 */       if (param1Object == null || param1Object.getClass() != MethodHandleEntry.class) {
/*  863 */         return false;
/*      */       }
/*  865 */       MethodHandleEntry methodHandleEntry = (MethodHandleEntry)param1Object;
/*  866 */       return (this.refKind == methodHandleEntry.refKind && this.memRef
/*  867 */         .eq(methodHandleEntry.memRef));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  870 */       int i = superCompareTo(param1Object);
/*  871 */       if (i == 0) {
/*  872 */         MethodHandleEntry methodHandleEntry = (MethodHandleEntry)param1Object;
/*  873 */         if (Utils.SORT_HANDLES_KIND_MAJOR)
/*      */         {
/*  875 */           i = this.refKind - methodHandleEntry.refKind;
/*      */         }
/*  877 */         if (i == 0)
/*  878 */           i = this.memRef.compareTo(methodHandleEntry.memRef); 
/*  879 */         if (i == 0)
/*  880 */           i = this.refKind - methodHandleEntry.refKind; 
/*      */       } 
/*  882 */       return i;
/*      */     }
/*      */     public static String stringValueOf(int param1Int, ConstantPool.MemberEntry param1MemberEntry) {
/*  885 */       return ConstantPool.refKindName(param1Int) + ":" + param1MemberEntry.stringValue();
/*      */     }
/*      */     public String stringValue() {
/*  888 */       return stringValueOf(this.refKind, this.memRef);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MethodTypeEntry extends Entry {
/*      */     final ConstantPool.SignatureEntry typeRef;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  896 */       return (param1Int == 0) ? this.typeRef : null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  899 */       return this.typeRef.hashCode() + this.tag;
/*      */     }
/*      */     
/*      */     MethodTypeEntry(ConstantPool.SignatureEntry param1SignatureEntry) {
/*  903 */       super((byte)16);
/*  904 */       this.typeRef = param1SignatureEntry;
/*  905 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  908 */       if (param1Object == null || param1Object.getClass() != MethodTypeEntry.class) {
/*  909 */         return false;
/*      */       }
/*  911 */       MethodTypeEntry methodTypeEntry = (MethodTypeEntry)param1Object;
/*  912 */       return this.typeRef.eq(methodTypeEntry.typeRef);
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  915 */       int i = superCompareTo(param1Object);
/*  916 */       if (i == 0) {
/*  917 */         MethodTypeEntry methodTypeEntry = (MethodTypeEntry)param1Object;
/*  918 */         i = this.typeRef.compareTo(methodTypeEntry.typeRef);
/*      */       } 
/*  920 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  923 */       return this.typeRef.stringValue();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class InvokeDynamicEntry
/*      */     extends Entry {
/*      */     final ConstantPool.BootstrapMethodEntry bssRef;
/*      */     final ConstantPool.DescriptorEntry descRef;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  933 */       if (param1Int == 0) return this.bssRef; 
/*  934 */       if (param1Int == 1) return this.descRef; 
/*  935 */       return null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  938 */       int i = this.descRef.hashCode();
/*  939 */       return this.bssRef.hashCode() + (i << 8) ^ i;
/*      */     }
/*      */     
/*      */     InvokeDynamicEntry(ConstantPool.BootstrapMethodEntry param1BootstrapMethodEntry, ConstantPool.DescriptorEntry param1DescriptorEntry) {
/*  943 */       super((byte)18);
/*  944 */       this.bssRef = param1BootstrapMethodEntry;
/*  945 */       this.descRef = param1DescriptorEntry;
/*  946 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/*  949 */       if (param1Object == null || param1Object.getClass() != InvokeDynamicEntry.class) {
/*  950 */         return false;
/*      */       }
/*  952 */       InvokeDynamicEntry invokeDynamicEntry = (InvokeDynamicEntry)param1Object;
/*  953 */       return (this.bssRef.eq(invokeDynamicEntry.bssRef) && this.descRef
/*  954 */         .eq(invokeDynamicEntry.descRef));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/*  957 */       int i = superCompareTo(param1Object);
/*  958 */       if (i == 0) {
/*  959 */         InvokeDynamicEntry invokeDynamicEntry = (InvokeDynamicEntry)param1Object;
/*  960 */         if (Utils.SORT_INDY_BSS_MAJOR)
/*      */         {
/*  962 */           i = this.bssRef.compareTo(invokeDynamicEntry.bssRef);
/*      */         }
/*  964 */         if (i == 0)
/*  965 */           i = this.descRef.compareTo(invokeDynamicEntry.descRef); 
/*  966 */         if (i == 0)
/*  967 */           i = this.bssRef.compareTo(invokeDynamicEntry.bssRef); 
/*      */       } 
/*  969 */       return i;
/*      */     }
/*      */     public String stringValue() {
/*  972 */       return stringValueOf(this.bssRef, this.descRef);
/*      */     }
/*      */     
/*      */     static String stringValueOf(ConstantPool.BootstrapMethodEntry param1BootstrapMethodEntry, ConstantPool.DescriptorEntry param1DescriptorEntry) {
/*  976 */       return "Indy:" + param1BootstrapMethodEntry.stringValue() + "." + param1DescriptorEntry.stringValue();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class BootstrapMethodEntry
/*      */     extends Entry {
/*      */     final ConstantPool.MethodHandleEntry bsmRef;
/*      */     final ConstantPool.Entry[] argRefs;
/*      */     
/*      */     public ConstantPool.Entry getRef(int param1Int) {
/*  986 */       if (param1Int == 0) return this.bsmRef; 
/*  987 */       if (param1Int - 1 < this.argRefs.length) return this.argRefs[param1Int - 1]; 
/*  988 */       return null;
/*      */     }
/*      */     protected int computeValueHash() {
/*  991 */       int i = this.bsmRef.hashCode();
/*  992 */       return Arrays.hashCode((Object[])this.argRefs) + (i << 8) ^ i;
/*      */     }
/*      */     
/*      */     BootstrapMethodEntry(ConstantPool.MethodHandleEntry param1MethodHandleEntry, ConstantPool.Entry[] param1ArrayOfEntry) {
/*  996 */       super((byte)17);
/*  997 */       this.bsmRef = param1MethodHandleEntry;
/*  998 */       this.argRefs = (ConstantPool.Entry[])param1ArrayOfEntry.clone();
/*  999 */       hashCode();
/*      */     }
/*      */     public boolean equals(Object param1Object) {
/* 1002 */       if (param1Object == null || param1Object.getClass() != BootstrapMethodEntry.class) {
/* 1003 */         return false;
/*      */       }
/* 1005 */       BootstrapMethodEntry bootstrapMethodEntry = (BootstrapMethodEntry)param1Object;
/* 1006 */       return (this.bsmRef.eq(bootstrapMethodEntry.bsmRef) && 
/* 1007 */         Arrays.equals((Object[])this.argRefs, (Object[])bootstrapMethodEntry.argRefs));
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/* 1010 */       int i = superCompareTo(param1Object);
/* 1011 */       if (i == 0) {
/* 1012 */         BootstrapMethodEntry bootstrapMethodEntry = (BootstrapMethodEntry)param1Object;
/* 1013 */         if (Utils.SORT_BSS_BSM_MAJOR)
/*      */         {
/* 1015 */           i = this.bsmRef.compareTo(bootstrapMethodEntry.bsmRef);
/*      */         }
/* 1017 */         if (i == 0)
/* 1018 */           i = compareArgArrays(this.argRefs, bootstrapMethodEntry.argRefs); 
/* 1019 */         if (i == 0)
/* 1020 */           i = this.bsmRef.compareTo(bootstrapMethodEntry.bsmRef); 
/*      */       } 
/* 1022 */       return i;
/*      */     }
/*      */     public String stringValue() {
/* 1025 */       return stringValueOf(this.bsmRef, this.argRefs);
/*      */     }
/*      */     
/*      */     static String stringValueOf(ConstantPool.MethodHandleEntry param1MethodHandleEntry, ConstantPool.Entry[] param1ArrayOfEntry) {
/* 1029 */       StringBuffer stringBuffer = new StringBuffer(param1MethodHandleEntry.stringValue());
/*      */ 
/*      */       
/* 1032 */       byte b = 60;
/* 1033 */       boolean bool = false;
/* 1034 */       for (ConstantPool.Entry entry : param1ArrayOfEntry) {
/* 1035 */         stringBuffer.append(b).append(entry.stringValue());
/* 1036 */         b = 59;
/*      */       } 
/* 1038 */       if (b == 60) stringBuffer.append(b); 
/* 1039 */       stringBuffer.append('>');
/* 1040 */       return stringBuffer.toString();
/*      */     }
/*      */     
/*      */     static int compareArgArrays(ConstantPool.Entry[] param1ArrayOfEntry1, ConstantPool.Entry[] param1ArrayOfEntry2) {
/* 1044 */       int i = param1ArrayOfEntry1.length - param1ArrayOfEntry2.length;
/* 1045 */       if (i != 0) return i; 
/* 1046 */       for (byte b = 0; b < param1ArrayOfEntry1.length; b++) {
/* 1047 */         i = param1ArrayOfEntry1[b].compareTo(param1ArrayOfEntry2[b]);
/* 1048 */         if (i != 0)
/*      */           break; 
/* 1050 */       }  return i;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 1055 */   protected static final Entry[] noRefs = new Entry[0];
/* 1056 */   protected static final ClassEntry[] noClassRefs = new ClassEntry[0];
/*      */   
/*      */   public static final class Index extends AbstractList<Entry> { protected String debugName;
/*      */     protected ConstantPool.Entry[] cpMap;
/*      */     protected boolean flattenSigs;
/*      */     protected ConstantPool.Entry[] indexKey;
/*      */     protected int[] indexValue;
/*      */     
/*      */     protected ConstantPool.Entry[] getMap() {
/* 1065 */       return this.cpMap;
/*      */     }
/*      */     protected Index(String param1String) {
/* 1068 */       this.debugName = param1String;
/*      */     }
/*      */     protected Index(String param1String, ConstantPool.Entry[] param1ArrayOfEntry) {
/* 1071 */       this(param1String);
/* 1072 */       setMap(param1ArrayOfEntry);
/*      */     }
/*      */     protected void setMap(ConstantPool.Entry[] param1ArrayOfEntry) {
/* 1075 */       clearIndex();
/* 1076 */       this.cpMap = param1ArrayOfEntry;
/*      */     }
/*      */     protected Index(String param1String, Collection<ConstantPool.Entry> param1Collection) {
/* 1079 */       this(param1String);
/* 1080 */       setMap(param1Collection);
/*      */     }
/*      */     protected void setMap(Collection<ConstantPool.Entry> param1Collection) {
/* 1083 */       this.cpMap = new ConstantPool.Entry[param1Collection.size()];
/* 1084 */       param1Collection.toArray(this.cpMap);
/* 1085 */       setMap(this.cpMap);
/*      */     }
/*      */     public int size() {
/* 1088 */       return this.cpMap.length;
/*      */     }
/*      */     public ConstantPool.Entry get(int param1Int) {
/* 1091 */       return this.cpMap[param1Int];
/*      */     }
/*      */     
/*      */     public ConstantPool.Entry getEntry(int param1Int) {
/* 1095 */       return this.cpMap[param1Int];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int findIndexOf(ConstantPool.Entry param1Entry) {
/* 1106 */       if (this.indexKey == null) {
/* 1107 */         initializeIndex();
/*      */       }
/* 1109 */       int i = findIndexLocation(param1Entry);
/* 1110 */       if (this.indexKey[i] != param1Entry) {
/* 1111 */         if (this.flattenSigs && param1Entry.tag == 13) {
/* 1112 */           ConstantPool.SignatureEntry signatureEntry = (ConstantPool.SignatureEntry)param1Entry;
/* 1113 */           return findIndexOf(signatureEntry.asUtf8Entry());
/*      */         } 
/* 1115 */         return -1;
/*      */       } 
/* 1117 */       int j = this.indexValue[i];
/* 1118 */       assert param1Entry.equals(this.cpMap[j]);
/* 1119 */       return j;
/*      */     }
/*      */     public boolean contains(ConstantPool.Entry param1Entry) {
/* 1122 */       return (findIndexOf(param1Entry) >= 0);
/*      */     }
/*      */     
/*      */     public int indexOf(ConstantPool.Entry param1Entry) {
/* 1126 */       int i = findIndexOf(param1Entry);
/* 1127 */       if (i < 0 && ConstantPool.verbose() > 0) {
/* 1128 */         System.out.println("not found: " + param1Entry);
/* 1129 */         System.out.println("       in: " + dumpString());
/* 1130 */         Thread.dumpStack();
/*      */       } 
/* 1132 */       assert i >= 0;
/* 1133 */       return i;
/*      */     }
/*      */     public int lastIndexOf(ConstantPool.Entry param1Entry) {
/* 1136 */       return indexOf(param1Entry);
/*      */     }
/*      */     
/*      */     public boolean assertIsSorted() {
/* 1140 */       for (byte b = 1; b < this.cpMap.length; b++) {
/* 1141 */         if (this.cpMap[b - 1].compareTo(this.cpMap[b]) > 0) {
/* 1142 */           System.out.println("Not sorted at " + (b - 1) + "/" + b + ": " + dumpString());
/* 1143 */           return false;
/*      */         } 
/*      */       } 
/* 1146 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void clearIndex() {
/* 1153 */       this.indexKey = null;
/* 1154 */       this.indexValue = null;
/*      */     }
/*      */     private int findIndexLocation(ConstantPool.Entry param1Entry) {
/* 1157 */       int i = this.indexKey.length;
/* 1158 */       int j = param1Entry.hashCode();
/* 1159 */       int k = j & i - 1;
/* 1160 */       int m = (j >>> 8 | 0x1) & i - 1;
/*      */       while (true) {
/* 1162 */         ConstantPool.Entry entry = this.indexKey[k];
/* 1163 */         if (entry == param1Entry || entry == null)
/* 1164 */           return k; 
/* 1165 */         k += m;
/* 1166 */         if (k >= i) k -= i; 
/*      */       } 
/*      */     }
/*      */     private void initializeIndex() {
/* 1170 */       if (ConstantPool.verbose() > 2)
/* 1171 */         System.out.println("initialize Index " + this.debugName + " [" + size() + "]"); 
/* 1172 */       int i = (int)((this.cpMap.length + 10) * 1.5D);
/* 1173 */       int j = 1;
/* 1174 */       while (j < i) {
/* 1175 */         j <<= 1;
/*      */       }
/* 1177 */       this.indexKey = new ConstantPool.Entry[j];
/* 1178 */       this.indexValue = new int[j];
/* 1179 */       for (byte b = 0; b < this.cpMap.length; b++) {
/* 1180 */         ConstantPool.Entry entry = this.cpMap[b];
/* 1181 */         if (entry != null) {
/* 1182 */           int k = findIndexLocation(entry);
/* 1183 */           assert this.indexKey[k] == null;
/* 1184 */           this.indexKey[k] = entry;
/* 1185 */           this.indexValue[k] = b;
/*      */         } 
/*      */       } 
/*      */     } public ConstantPool.Entry[] toArray(ConstantPool.Entry[] param1ArrayOfEntry) {
/* 1189 */       int i = size();
/* 1190 */       if (param1ArrayOfEntry.length < i) return (ConstantPool.Entry[])toArray((Object[])param1ArrayOfEntry); 
/* 1191 */       System.arraycopy(this.cpMap, 0, param1ArrayOfEntry, 0, i);
/* 1192 */       if (param1ArrayOfEntry.length > i) param1ArrayOfEntry[i] = null; 
/* 1193 */       return param1ArrayOfEntry;
/*      */     }
/*      */     public ConstantPool.Entry[] toArray() {
/* 1196 */       return toArray(new ConstantPool.Entry[size()]);
/*      */     }
/*      */     public Object clone() {
/* 1199 */       return new Index(this.debugName, (ConstantPool.Entry[])this.cpMap.clone());
/*      */     }
/*      */     public String toString() {
/* 1202 */       return "Index " + this.debugName + " [" + size() + "]";
/*      */     }
/*      */     public String dumpString() {
/* 1205 */       String str = toString();
/* 1206 */       str = str + " {\n";
/* 1207 */       for (byte b = 0; b < this.cpMap.length; b++) {
/* 1208 */         str = str + "    " + b + ": " + this.cpMap[b] + "\n";
/*      */       }
/* 1210 */       str = str + "}";
/* 1211 */       return str;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Index makeIndex(String paramString, Entry[] paramArrayOfEntry) {
/* 1219 */     return new Index(paramString, paramArrayOfEntry);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Index makeIndex(String paramString, Collection<Entry> paramCollection) {
/* 1224 */     return new Index(paramString, paramCollection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(Index paramIndex) {
/* 1231 */     paramIndex.clearIndex();
/* 1232 */     Arrays.sort((Object[])paramIndex.cpMap);
/* 1233 */     if (verbose() > 2) {
/* 1234 */       System.out.println("sorted " + paramIndex.dumpString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Index[] partition(Index paramIndex, int[] paramArrayOfint) {
/* 1245 */     ArrayList<List> arrayList = new ArrayList();
/* 1246 */     Entry[] arrayOfEntry = paramIndex.cpMap;
/* 1247 */     assert paramArrayOfint.length == arrayOfEntry.length;
/* 1248 */     for (byte b1 = 0; b1 < paramArrayOfint.length; b1++) {
/* 1249 */       int i = paramArrayOfint[b1];
/* 1250 */       if (i >= 0) {
/* 1251 */         while (i >= arrayList.size()) {
/* 1252 */           arrayList.add(null);
/*      */         }
/* 1254 */         List<Entry> list = arrayList.get(i);
/* 1255 */         if (list == null) {
/* 1256 */           arrayList.set(i, list = new ArrayList());
/*      */         }
/* 1258 */         list.add(arrayOfEntry[b1]);
/*      */       } 
/* 1260 */     }  Index[] arrayOfIndex = new Index[arrayList.size()];
/* 1261 */     for (byte b2 = 0; b2 < arrayOfIndex.length; b2++) {
/* 1262 */       List<Entry> list = arrayList.get(b2);
/* 1263 */       if (list != null) {
/* 1264 */         arrayOfIndex[b2] = new Index(paramIndex.debugName + "/part#" + b2, list);
/* 1265 */         assert arrayOfIndex[b2].indexOf(list.get(0)) == 0;
/*      */       } 
/* 1267 */     }  return arrayOfIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Index[] partitionByTag(Index paramIndex) {
/* 1272 */     Entry[] arrayOfEntry = paramIndex.cpMap;
/* 1273 */     int[] arrayOfInt = new int[arrayOfEntry.length];
/* 1274 */     for (byte b1 = 0; b1 < arrayOfInt.length; b1++) {
/* 1275 */       Entry entry = arrayOfEntry[b1];
/* 1276 */       arrayOfInt[b1] = (entry == null) ? -1 : entry.tag;
/*      */     } 
/* 1278 */     Index[] arrayOfIndex = partition(paramIndex, arrayOfInt);
/* 1279 */     for (byte b2 = 0; b2 < arrayOfIndex.length; b2++) {
/* 1280 */       if (arrayOfIndex[b2] != null)
/* 1281 */         (arrayOfIndex[b2]).debugName = tagName(b2); 
/*      */     } 
/* 1283 */     if (arrayOfIndex.length < 19) {
/* 1284 */       Index[] arrayOfIndex1 = new Index[19];
/* 1285 */       System.arraycopy(arrayOfIndex, 0, arrayOfIndex1, 0, arrayOfIndex.length);
/* 1286 */       arrayOfIndex = arrayOfIndex1;
/*      */     } 
/* 1288 */     return arrayOfIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class IndexGroup
/*      */   {
/* 1294 */     private ConstantPool.Index[] indexByTag = new ConstantPool.Index[19];
/*      */     
/*      */     private ConstantPool.Index[] indexByTagGroup;
/*      */     private int[] untypedFirstIndexByTag;
/*      */     private int totalSizeQQ;
/*      */     private ConstantPool.Index[][] indexByTagAndClass;
/*      */     
/*      */     private ConstantPool.Index makeTagGroupIndex(byte param1Byte, byte[] param1ArrayOfbyte) {
/* 1302 */       if (this.indexByTagGroup == null)
/* 1303 */         this.indexByTagGroup = new ConstantPool.Index[4]; 
/* 1304 */       int i = param1Byte - 50;
/* 1305 */       assert this.indexByTagGroup[i] == null;
/* 1306 */       int j = 0;
/* 1307 */       ConstantPool.Entry[] arrayOfEntry = null;
/* 1308 */       for (byte b = 1; b <= 2; b++) {
/* 1309 */         untypedIndexOf(null);
/* 1310 */         for (byte b1 : param1ArrayOfbyte) {
/* 1311 */           ConstantPool.Index index = this.indexByTag[b1];
/* 1312 */           if (index != null) {
/* 1313 */             int k = index.cpMap.length;
/* 1314 */             if (k != 0)
/* 1315 */               if ($assertionsDisabled || ((param1Byte == 50) ? (j == this.untypedFirstIndexByTag[b1]) : (j < this.untypedFirstIndexByTag[b1])))
/*      */               
/*      */               { 
/* 1318 */                 if (arrayOfEntry != null) {
/* 1319 */                   assert arrayOfEntry[j] == null;
/* 1320 */                   assert arrayOfEntry[j + k - 1] == null;
/* 1321 */                   System.arraycopy(index.cpMap, 0, arrayOfEntry, j, k);
/*      */                 } 
/* 1323 */                 j += k; } else { throw new AssertionError(); }  
/*      */           } 
/* 1325 */         }  if (arrayOfEntry == null) {
/* 1326 */           assert b == 1;
/*      */           
/* 1328 */           arrayOfEntry = new ConstantPool.Entry[j];
/* 1329 */           j = 0;
/*      */         } 
/*      */       } 
/* 1332 */       this.indexByTagGroup[i] = new ConstantPool.Index(ConstantPool.tagName(param1Byte), arrayOfEntry);
/* 1333 */       return this.indexByTagGroup[i];
/*      */     }
/*      */     
/*      */     public int untypedIndexOf(ConstantPool.Entry param1Entry) {
/* 1337 */       if (this.untypedFirstIndexByTag == null) {
/* 1338 */         this.untypedFirstIndexByTag = new int[20];
/* 1339 */         int j = 0;
/* 1340 */         for (byte b1 = 0; b1 < ConstantPool.TAGS_IN_ORDER.length; b1++) {
/* 1341 */           byte b2 = ConstantPool.TAGS_IN_ORDER[b1];
/* 1342 */           ConstantPool.Index index1 = this.indexByTag[b2];
/* 1343 */           if (index1 != null) {
/* 1344 */             int k = index1.cpMap.length;
/* 1345 */             this.untypedFirstIndexByTag[b2] = j;
/* 1346 */             j += k;
/*      */           } 
/* 1348 */         }  this.untypedFirstIndexByTag[19] = j;
/*      */       } 
/* 1350 */       if (param1Entry == null) return -1; 
/* 1351 */       byte b = param1Entry.tag;
/* 1352 */       ConstantPool.Index index = this.indexByTag[b];
/* 1353 */       if (index == null) return -1; 
/* 1354 */       int i = index.findIndexOf(param1Entry);
/* 1355 */       if (i >= 0)
/* 1356 */         i += this.untypedFirstIndexByTag[b]; 
/* 1357 */       return i;
/*      */     }
/*      */     
/*      */     public void initIndexByTag(byte param1Byte, ConstantPool.Index param1Index) {
/* 1361 */       assert this.indexByTag[param1Byte] == null;
/* 1362 */       ConstantPool.Entry[] arrayOfEntry = param1Index.cpMap;
/* 1363 */       for (byte b = 0; b < arrayOfEntry.length; b++)
/*      */       {
/* 1365 */         assert (arrayOfEntry[b]).tag == param1Byte;
/*      */       }
/* 1367 */       if (param1Byte == 1)
/*      */       {
/* 1369 */         assert arrayOfEntry.length == 0 || arrayOfEntry[0].stringValue().equals("");
/*      */       }
/* 1371 */       this.indexByTag[param1Byte] = param1Index;
/*      */       
/* 1373 */       this.untypedFirstIndexByTag = null;
/* 1374 */       this.indexByTagGroup = null;
/* 1375 */       if (this.indexByTagAndClass != null) {
/* 1376 */         this.indexByTagAndClass[param1Byte] = null;
/*      */       }
/*      */     }
/*      */     
/*      */     public ConstantPool.Index getIndexByTag(byte param1Byte) {
/* 1381 */       if (param1Byte >= 50)
/* 1382 */         return getIndexByTagGroup(param1Byte); 
/* 1383 */       ConstantPool.Index index = this.indexByTag[param1Byte];
/* 1384 */       if (index == null) {
/*      */         
/* 1386 */         index = new ConstantPool.Index(ConstantPool.tagName(param1Byte), new ConstantPool.Entry[0]);
/* 1387 */         this.indexByTag[param1Byte] = index;
/*      */       } 
/* 1389 */       return index;
/*      */     }
/*      */ 
/*      */     
/*      */     private ConstantPool.Index getIndexByTagGroup(byte param1Byte) {
/* 1394 */       if (this.indexByTagGroup != null) {
/* 1395 */         ConstantPool.Index index = this.indexByTagGroup[param1Byte - 50];
/* 1396 */         if (index != null) return index; 
/*      */       } 
/* 1398 */       switch (param1Byte) {
/*      */         case 50:
/* 1400 */           return makeTagGroupIndex((byte)50, ConstantPool.TAGS_IN_ORDER);
/*      */         case 51:
/* 1402 */           return makeTagGroupIndex((byte)51, ConstantPool.LOADABLE_VALUE_TAGS);
/*      */         case 52:
/* 1404 */           return makeTagGroupIndex((byte)52, ConstantPool.ANY_MEMBER_TAGS);
/*      */         
/*      */         case 53:
/* 1407 */           return null;
/*      */       } 
/* 1409 */       throw new AssertionError("bad tag group " + param1Byte);
/*      */     }
/*      */ 
/*      */     
/*      */     public ConstantPool.Index getMemberIndex(byte param1Byte, ConstantPool.ClassEntry param1ClassEntry) {
/* 1414 */       if (param1ClassEntry == null)
/* 1415 */         throw new RuntimeException("missing class reference for " + ConstantPool.tagName(param1Byte)); 
/* 1416 */       if (this.indexByTagAndClass == null)
/* 1417 */         this.indexByTagAndClass = new ConstantPool.Index[19][]; 
/* 1418 */       ConstantPool.Index index = getIndexByTag((byte)7);
/* 1419 */       ConstantPool.Index[] arrayOfIndex = this.indexByTagAndClass[param1Byte];
/* 1420 */       if (arrayOfIndex == null) {
/*      */ 
/*      */         
/* 1423 */         ConstantPool.Index index1 = getIndexByTag(param1Byte);
/* 1424 */         int[] arrayOfInt = new int[index1.size()]; byte b;
/* 1425 */         for (b = 0; b < arrayOfInt.length; b++) {
/* 1426 */           ConstantPool.MemberEntry memberEntry = (ConstantPool.MemberEntry)index1.get(b);
/* 1427 */           int j = index.indexOf(memberEntry.classRef);
/* 1428 */           arrayOfInt[b] = j;
/*      */         } 
/* 1430 */         arrayOfIndex = ConstantPool.partition(index1, arrayOfInt);
/* 1431 */         for (b = 0; b < arrayOfIndex.length; b++) {
/* 1432 */           assert arrayOfIndex[b] == null || arrayOfIndex[b]
/* 1433 */             .assertIsSorted();
/*      */         }
/* 1435 */         this.indexByTagAndClass[param1Byte] = arrayOfIndex;
/*      */       } 
/* 1437 */       int i = index.indexOf(param1ClassEntry);
/* 1438 */       return arrayOfIndex[i];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getOverloadingIndex(ConstantPool.MemberEntry param1MemberEntry) {
/* 1444 */       ConstantPool.Index index = getMemberIndex(param1MemberEntry.tag, param1MemberEntry.classRef);
/* 1445 */       ConstantPool.Utf8Entry utf8Entry = param1MemberEntry.descRef.nameRef;
/* 1446 */       byte b1 = 0;
/* 1447 */       for (byte b2 = 0; b2 < index.cpMap.length; b2++) {
/* 1448 */         ConstantPool.MemberEntry memberEntry = (ConstantPool.MemberEntry)index.cpMap[b2];
/* 1449 */         if (memberEntry.equals(param1MemberEntry))
/* 1450 */           return b1; 
/* 1451 */         if (memberEntry.descRef.nameRef.equals(utf8Entry))
/*      */         {
/* 1453 */           b1++; } 
/*      */       } 
/* 1455 */       throw new RuntimeException("should not reach here");
/*      */     }
/*      */ 
/*      */     
/*      */     public ConstantPool.MemberEntry getOverloadingForIndex(byte param1Byte, ConstantPool.ClassEntry param1ClassEntry, String param1String, int param1Int) {
/* 1460 */       assert param1String.equals(param1String.intern());
/* 1461 */       ConstantPool.Index index = getMemberIndex(param1Byte, param1ClassEntry);
/* 1462 */       int i = 0;
/* 1463 */       for (byte b = 0; b < index.cpMap.length; b++) {
/* 1464 */         ConstantPool.MemberEntry memberEntry = (ConstantPool.MemberEntry)index.cpMap[b];
/* 1465 */         if (memberEntry.descRef.nameRef.stringValue().equals(param1String)) {
/* 1466 */           if (i == param1Int) return memberEntry; 
/* 1467 */           i++;
/*      */         } 
/*      */       } 
/* 1470 */       throw new RuntimeException("should not reach here");
/*      */     }
/*      */     
/*      */     public boolean haveNumbers() {
/* 1474 */       for (byte b : ConstantPool.NUMBER_TAGS) {
/* 1475 */         if (getIndexByTag(b).size() > 0) return true; 
/*      */       } 
/* 1477 */       return false;
/*      */     }
/*      */     
/*      */     public boolean haveExtraTags() {
/* 1481 */       for (byte b : ConstantPool.EXTRA_TAGS) {
/* 1482 */         if (getIndexByTag(b).size() > 0) return true; 
/*      */       } 
/* 1484 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void completeReferencesIn(Set<Entry> paramSet, boolean paramBoolean) {
/* 1495 */     completeReferencesIn(paramSet, paramBoolean, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void completeReferencesIn(Set<Entry> paramSet, boolean paramBoolean, List<BootstrapMethodEntry> paramList) {
/* 1501 */     paramSet.remove(null);
/*      */     
/* 1503 */     ListIterator<?> listIterator = (new ArrayList(paramSet)).listIterator(paramSet.size());
/* 1504 */     label27: while (listIterator.hasPrevious()) {
/* 1505 */       Entry entry = (Entry)listIterator.previous();
/* 1506 */       listIterator.remove();
/* 1507 */       assert entry != null;
/* 1508 */       if (paramBoolean && entry.tag == 13) {
/* 1509 */         SignatureEntry signatureEntry = (SignatureEntry)entry;
/* 1510 */         Utf8Entry utf8Entry = signatureEntry.asUtf8Entry();
/*      */         
/* 1512 */         paramSet.remove(signatureEntry);
/* 1513 */         paramSet.add(utf8Entry);
/* 1514 */         entry = utf8Entry;
/*      */       } 
/* 1516 */       if (paramList != null && entry.tag == 17) {
/* 1517 */         BootstrapMethodEntry bootstrapMethodEntry = (BootstrapMethodEntry)entry;
/* 1518 */         paramSet.remove(bootstrapMethodEntry);
/*      */         
/* 1520 */         if (!paramList.contains(bootstrapMethodEntry)) {
/* 1521 */           paramList.add(bootstrapMethodEntry);
/*      */         }
/*      */       } 
/*      */       
/* 1525 */       for (byte b = 0;; b++) {
/* 1526 */         Entry entry1 = entry.getRef(b);
/* 1527 */         if (entry1 == null)
/*      */           continue label27; 
/* 1529 */         if (paramSet.add(entry1))
/* 1530 */           listIterator.add(entry1); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static double percent(int paramInt1, int paramInt2) {
/* 1536 */     return (int)(10000.0D * paramInt1 / paramInt2 + 0.5D) / 100.0D;
/*      */   }
/*      */   
/*      */   public static String tagName(int paramInt) {
/* 1540 */     switch (paramInt) { case 1:
/* 1541 */         return "Utf8";
/* 1542 */       case 3: return "Integer";
/* 1543 */       case 4: return "Float";
/* 1544 */       case 5: return "Long";
/* 1545 */       case 6: return "Double";
/* 1546 */       case 7: return "Class";
/* 1547 */       case 8: return "String";
/* 1548 */       case 9: return "Fieldref";
/* 1549 */       case 10: return "Methodref";
/* 1550 */       case 11: return "InterfaceMethodref";
/* 1551 */       case 12: return "NameandType";
/* 1552 */       case 15: return "MethodHandle";
/* 1553 */       case 16: return "MethodType";
/* 1554 */       case 18: return "InvokeDynamic";
/*      */       
/*      */       case 50:
/* 1557 */         return "**All";
/* 1558 */       case 0: return "**None";
/* 1559 */       case 51: return "**LoadableValue";
/* 1560 */       case 52: return "**AnyMember";
/* 1561 */       case 53: return "*FieldSpecific";
/* 1562 */       case 13: return "*Signature";
/* 1563 */       case 17: return "*BootstrapMethod"; }
/*      */     
/* 1565 */     return "tag#" + paramInt;
/*      */   }
/*      */   
/*      */   public static String refKindName(int paramInt) {
/* 1569 */     switch (paramInt) { case 1:
/* 1570 */         return "getField";
/* 1571 */       case 2: return "getStatic";
/* 1572 */       case 3: return "putField";
/* 1573 */       case 4: return "putStatic";
/* 1574 */       case 5: return "invokeVirtual";
/* 1575 */       case 6: return "invokeStatic";
/* 1576 */       case 7: return "invokeSpecial";
/* 1577 */       case 8: return "newInvokeSpecial";
/* 1578 */       case 9: return "invokeInterface"; }
/*      */     
/* 1580 */     return "refKind#" + paramInt;
/*      */   }
/*      */ 
/*      */   
/* 1584 */   static final byte[] TAGS_IN_ORDER = new byte[] { 1, 3, 4, 5, 6, 8, 7, 13, 12, 9, 10, 11, 15, 16, 17, 18 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1606 */   static final byte[] TAG_ORDER = new byte[19]; static {
/* 1607 */     for (byte b = 0; b < TAGS_IN_ORDER.length; b++) {
/* 1608 */       TAG_ORDER[TAGS_IN_ORDER[b]] = (byte)(b + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1617 */   static final byte[] NUMBER_TAGS = new byte[] { 3, 4, 5, 6 };
/*      */ 
/*      */   
/* 1620 */   static final byte[] EXTRA_TAGS = new byte[] { 15, 16, 17, 18 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1625 */   static final byte[] LOADABLE_VALUE_TAGS = new byte[] { 3, 4, 5, 6, 8, 7, 15, 16 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1630 */   static final byte[] ANY_MEMBER_TAGS = new byte[] { 9, 10, 11 };
/*      */ 
/*      */   
/* 1633 */   static final byte[] FIELD_SPECIFIC_TAGS = new byte[] { 3, 4, 5, 6, 8 };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/* 1639 */     assert verifyTagOrder(TAGS_IN_ORDER) && 
/* 1640 */       verifyTagOrder(NUMBER_TAGS) && 
/* 1641 */       verifyTagOrder(EXTRA_TAGS) && 
/* 1642 */       verifyTagOrder(LOADABLE_VALUE_TAGS) && 
/* 1643 */       verifyTagOrder(ANY_MEMBER_TAGS) && 
/* 1644 */       verifyTagOrder(FIELD_SPECIFIC_TAGS);
/*      */   }
/*      */   
/*      */   private static boolean verifyTagOrder(byte[] paramArrayOfbyte) {
/* 1648 */     byte b = -1;
/* 1649 */     for (byte b1 : paramArrayOfbyte) {
/* 1650 */       byte b2 = TAG_ORDER[b1];
/* 1651 */       assert b2 > 0 : "tag not found: " + b1;
/* 1652 */       assert TAGS_IN_ORDER[b2 - 1] == b1 : "tag repeated: " + b1 + " => " + b2 + " => " + TAGS_IN_ORDER[b2 - true];
/* 1653 */       assert b < b2 : "tags not in order: " + Arrays.toString(paramArrayOfbyte) + " at " + b1;
/* 1654 */       b = b2;
/*      */     } 
/* 1656 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/ConstantPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */