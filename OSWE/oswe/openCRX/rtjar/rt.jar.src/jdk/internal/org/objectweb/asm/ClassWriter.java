/*      */ package jdk.internal.org.objectweb.asm;
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
/*      */ public class ClassWriter
/*      */   extends ClassVisitor
/*      */ {
/*      */   public static final int COMPUTE_MAXS = 1;
/*      */   public static final int COMPUTE_FRAMES = 2;
/*      */   static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
/*      */   static final int TO_ACC_SYNTHETIC = 64;
/*      */   static final int NOARG_INSN = 0;
/*      */   static final int SBYTE_INSN = 1;
/*      */   static final int SHORT_INSN = 2;
/*      */   static final int VAR_INSN = 3;
/*      */   static final int IMPLVAR_INSN = 4;
/*      */   static final int TYPE_INSN = 5;
/*      */   static final int FIELDORMETH_INSN = 6;
/*      */   static final int ITFMETH_INSN = 7;
/*      */   static final int INDYMETH_INSN = 8;
/*      */   static final int LABEL_INSN = 9;
/*      */   static final int LABELW_INSN = 10;
/*      */   static final int LDC_INSN = 11;
/*      */   static final int LDCW_INSN = 12;
/*      */   static final int IINC_INSN = 13;
/*      */   static final int TABL_INSN = 14;
/*      */   static final int LOOK_INSN = 15;
/*      */   static final int MANA_INSN = 16;
/*      */   static final int WIDE_INSN = 17;
/*      */   static final byte[] TYPE;
/*      */   static final int CLASS = 7;
/*      */   static final int FIELD = 9;
/*      */   static final int METH = 10;
/*      */   static final int IMETH = 11;
/*      */   static final int STR = 8;
/*      */   static final int INT = 3;
/*      */   static final int FLOAT = 4;
/*      */   static final int LONG = 5;
/*      */   static final int DOUBLE = 6;
/*      */   static final int NAME_TYPE = 12;
/*      */   static final int UTF8 = 1;
/*      */   static final int MTYPE = 16;
/*      */   static final int HANDLE = 15;
/*      */   static final int INDY = 18;
/*      */   static final int HANDLE_BASE = 20;
/*      */   static final int TYPE_NORMAL = 30;
/*      */   static final int TYPE_UNINIT = 31;
/*      */   static final int TYPE_MERGED = 32;
/*      */   static final int BSM = 33;
/*      */   ClassReader cr;
/*      */   int version;
/*      */   int index;
/*      */   final ByteVector pool;
/*      */   Item[] items;
/*      */   int threshold;
/*      */   final Item key;
/*      */   final Item key2;
/*      */   final Item key3;
/*      */   final Item key4;
/*      */   Item[] typeTable;
/*      */   private short typeCount;
/*      */   private int access;
/*      */   private int name;
/*      */   String thisName;
/*      */   private int signature;
/*      */   private int superName;
/*      */   private int interfaceCount;
/*      */   private int[] interfaces;
/*      */   private int sourceFile;
/*      */   private ByteVector sourceDebug;
/*      */   private int enclosingMethodOwner;
/*      */   private int enclosingMethod;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private AnnotationWriter tanns;
/*      */   private AnnotationWriter itanns;
/*      */   private Attribute attrs;
/*      */   private int innerClassesCount;
/*      */   private ByteVector innerClasses;
/*      */   int bootstrapMethodsCount;
/*      */   ByteVector bootstrapMethods;
/*      */   FieldWriter firstField;
/*      */   FieldWriter lastField;
/*      */   MethodWriter firstMethod;
/*      */   MethodWriter lastMethod;
/*      */   private boolean computeMaxs;
/*      */   private boolean computeFrames;
/*      */   boolean invalidFrames;
/*      */   
/*      */   static {
/*  545 */     byte[] arrayOfByte = new byte[220];
/*  546 */     String str = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKJJJJJJJJJJJJJJJJJJ";
/*      */ 
/*      */ 
/*      */     
/*  550 */     for (byte b = 0; b < arrayOfByte.length; b++) {
/*  551 */       arrayOfByte[b] = (byte)(str.charAt(b) - 65);
/*      */     }
/*  553 */     TYPE = arrayOfByte;
/*      */   }
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
/*      */   public ClassWriter(int paramInt) {
/*  637 */     super(327680);
/*  638 */     this.index = 1;
/*  639 */     this.pool = new ByteVector();
/*  640 */     this.items = new Item[256];
/*  641 */     this.threshold = (int)(0.75D * this.items.length);
/*  642 */     this.key = new Item();
/*  643 */     this.key2 = new Item();
/*  644 */     this.key3 = new Item();
/*  645 */     this.key4 = new Item();
/*  646 */     this.computeMaxs = ((paramInt & 0x1) != 0);
/*  647 */     this.computeFrames = ((paramInt & 0x2) != 0);
/*      */   }
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
/*      */   public ClassWriter(ClassReader paramClassReader, int paramInt) {
/*  683 */     this(paramInt);
/*  684 */     paramClassReader.copyPool(this);
/*  685 */     this.cr = paramClassReader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void visit(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  696 */     this.version = paramInt1;
/*  697 */     this.access = paramInt2;
/*  698 */     this.name = newClass(paramString1);
/*  699 */     this.thisName = paramString1;
/*  700 */     if (paramString2 != null) {
/*  701 */       this.signature = newUTF8(paramString2);
/*      */     }
/*  703 */     this.superName = (paramString3 == null) ? 0 : newClass(paramString3);
/*  704 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  705 */       this.interfaceCount = paramArrayOfString.length;
/*  706 */       this.interfaces = new int[this.interfaceCount];
/*  707 */       for (byte b = 0; b < this.interfaceCount; b++) {
/*  708 */         this.interfaces[b] = newClass(paramArrayOfString[b]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void visitSource(String paramString1, String paramString2) {
/*  715 */     if (paramString1 != null) {
/*  716 */       this.sourceFile = newUTF8(paramString1);
/*      */     }
/*  718 */     if (paramString2 != null) {
/*  719 */       this.sourceDebug = (new ByteVector()).encodeUTF8(paramString2, 0, 2147483647);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void visitOuterClass(String paramString1, String paramString2, String paramString3) {
/*  727 */     this.enclosingMethodOwner = newClass(paramString1);
/*  728 */     if (paramString2 != null && paramString3 != null) {
/*  729 */       this.enclosingMethod = newNameType(paramString2, paramString3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  739 */     ByteVector byteVector = new ByteVector();
/*      */     
/*  741 */     byteVector.putShort(newUTF8(paramString)).putShort(0);
/*  742 */     AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, 2);
/*  743 */     if (paramBoolean) {
/*  744 */       annotationWriter.next = this.anns;
/*  745 */       this.anns = annotationWriter;
/*      */     } else {
/*  747 */       annotationWriter.next = this.ianns;
/*  748 */       this.ianns = annotationWriter;
/*      */     } 
/*  750 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  759 */     ByteVector byteVector = new ByteVector();
/*      */     
/*  761 */     AnnotationWriter.putTarget(paramInt, paramTypePath, byteVector);
/*      */     
/*  763 */     byteVector.putShort(newUTF8(paramString)).putShort(0);
/*  764 */     AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, byteVector.length - 2);
/*      */     
/*  766 */     if (paramBoolean) {
/*  767 */       annotationWriter.next = this.tanns;
/*  768 */       this.tanns = annotationWriter;
/*      */     } else {
/*  770 */       annotationWriter.next = this.itanns;
/*  771 */       this.itanns = annotationWriter;
/*      */     } 
/*  773 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */   
/*      */   public final void visitAttribute(Attribute paramAttribute) {
/*  778 */     paramAttribute.next = this.attrs;
/*  779 */     this.attrs = paramAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void visitInnerClass(String paramString1, String paramString2, String paramString3, int paramInt) {
/*  785 */     if (this.innerClasses == null) {
/*  786 */       this.innerClasses = new ByteVector();
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
/*      */     
/*  798 */     Item item = newClassItem(paramString1);
/*  799 */     if (item.intVal == 0) {
/*  800 */       this.innerClassesCount++;
/*  801 */       this.innerClasses.putShort(item.index);
/*  802 */       this.innerClasses.putShort((paramString2 == null) ? 0 : newClass(paramString2));
/*  803 */       this.innerClasses.putShort((paramString3 == null) ? 0 : newUTF8(paramString3));
/*  804 */       this.innerClasses.putShort(paramInt);
/*  805 */       item.intVal = this.innerClassesCount;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final FieldVisitor visitField(int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/*  816 */     return new FieldWriter(this, paramInt, paramString1, paramString2, paramString3, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final MethodVisitor visitMethod(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  822 */     return new MethodWriter(this, paramInt, paramString1, paramString2, paramString3, paramArrayOfString, this.computeMaxs, this.computeFrames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void visitEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] toByteArray() {
/*  840 */     if (this.index > 65535) {
/*  841 */       throw new RuntimeException("Class file too large!");
/*      */     }
/*      */     
/*  844 */     int i = 24 + 2 * this.interfaceCount;
/*  845 */     byte b1 = 0;
/*  846 */     FieldWriter fieldWriter = this.firstField;
/*  847 */     while (fieldWriter != null) {
/*  848 */       b1++;
/*  849 */       i += fieldWriter.getSize();
/*  850 */       fieldWriter = (FieldWriter)fieldWriter.fv;
/*      */     } 
/*  852 */     byte b2 = 0;
/*  853 */     MethodWriter methodWriter = this.firstMethod;
/*  854 */     while (methodWriter != null) {
/*  855 */       b2++;
/*  856 */       i += methodWriter.getSize();
/*  857 */       methodWriter = (MethodWriter)methodWriter.mv;
/*      */     } 
/*  859 */     int j = 0;
/*  860 */     if (this.bootstrapMethods != null) {
/*      */ 
/*      */       
/*  863 */       j++;
/*  864 */       i += 8 + this.bootstrapMethods.length;
/*  865 */       newUTF8("BootstrapMethods");
/*      */     } 
/*  867 */     if (this.signature != 0) {
/*  868 */       j++;
/*  869 */       i += 8;
/*  870 */       newUTF8("Signature");
/*      */     } 
/*  872 */     if (this.sourceFile != 0) {
/*  873 */       j++;
/*  874 */       i += 8;
/*  875 */       newUTF8("SourceFile");
/*      */     } 
/*  877 */     if (this.sourceDebug != null) {
/*  878 */       j++;
/*  879 */       i += this.sourceDebug.length + 6;
/*  880 */       newUTF8("SourceDebugExtension");
/*      */     } 
/*  882 */     if (this.enclosingMethodOwner != 0) {
/*  883 */       j++;
/*  884 */       i += 10;
/*  885 */       newUTF8("EnclosingMethod");
/*      */     } 
/*  887 */     if ((this.access & 0x20000) != 0) {
/*  888 */       j++;
/*  889 */       i += 6;
/*  890 */       newUTF8("Deprecated");
/*      */     } 
/*  892 */     if ((this.access & 0x1000) != 0 && ((
/*  893 */       this.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
/*      */       
/*  895 */       j++;
/*  896 */       i += 6;
/*  897 */       newUTF8("Synthetic");
/*      */     } 
/*      */     
/*  900 */     if (this.innerClasses != null) {
/*  901 */       j++;
/*  902 */       i += 8 + this.innerClasses.length;
/*  903 */       newUTF8("InnerClasses");
/*      */     } 
/*  905 */     if (this.anns != null) {
/*  906 */       j++;
/*  907 */       i += 8 + this.anns.getSize();
/*  908 */       newUTF8("RuntimeVisibleAnnotations");
/*      */     } 
/*  910 */     if (this.ianns != null) {
/*  911 */       j++;
/*  912 */       i += 8 + this.ianns.getSize();
/*  913 */       newUTF8("RuntimeInvisibleAnnotations");
/*      */     } 
/*  915 */     if (this.tanns != null) {
/*  916 */       j++;
/*  917 */       i += 8 + this.tanns.getSize();
/*  918 */       newUTF8("RuntimeVisibleTypeAnnotations");
/*      */     } 
/*  920 */     if (this.itanns != null) {
/*  921 */       j++;
/*  922 */       i += 8 + this.itanns.getSize();
/*  923 */       newUTF8("RuntimeInvisibleTypeAnnotations");
/*      */     } 
/*  925 */     if (this.attrs != null) {
/*  926 */       j += this.attrs.getCount();
/*  927 */       i += this.attrs.getSize(this, null, 0, -1, -1);
/*      */     } 
/*  929 */     i += this.pool.length;
/*      */ 
/*      */     
/*  932 */     ByteVector byteVector = new ByteVector(i);
/*  933 */     byteVector.putInt(-889275714).putInt(this.version);
/*  934 */     byteVector.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
/*  935 */     int k = 0x60000 | (this.access & 0x40000) / 64;
/*      */     
/*  937 */     byteVector.putShort(this.access & (k ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.superName);
/*  938 */     byteVector.putShort(this.interfaceCount); int m;
/*  939 */     for (m = 0; m < this.interfaceCount; m++) {
/*  940 */       byteVector.putShort(this.interfaces[m]);
/*      */     }
/*  942 */     byteVector.putShort(b1);
/*  943 */     fieldWriter = this.firstField;
/*  944 */     while (fieldWriter != null) {
/*  945 */       fieldWriter.put(byteVector);
/*  946 */       fieldWriter = (FieldWriter)fieldWriter.fv;
/*      */     } 
/*  948 */     byteVector.putShort(b2);
/*  949 */     methodWriter = this.firstMethod;
/*  950 */     while (methodWriter != null) {
/*  951 */       methodWriter.put(byteVector);
/*  952 */       methodWriter = (MethodWriter)methodWriter.mv;
/*      */     } 
/*  954 */     byteVector.putShort(j);
/*  955 */     if (this.bootstrapMethods != null) {
/*  956 */       byteVector.putShort(newUTF8("BootstrapMethods"));
/*  957 */       byteVector.putInt(this.bootstrapMethods.length + 2).putShort(this.bootstrapMethodsCount);
/*      */       
/*  959 */       byteVector.putByteArray(this.bootstrapMethods.data, 0, this.bootstrapMethods.length);
/*      */     } 
/*  961 */     if (this.signature != 0) {
/*  962 */       byteVector.putShort(newUTF8("Signature")).putInt(2).putShort(this.signature);
/*      */     }
/*  964 */     if (this.sourceFile != 0) {
/*  965 */       byteVector.putShort(newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
/*      */     }
/*  967 */     if (this.sourceDebug != null) {
/*  968 */       m = this.sourceDebug.length;
/*  969 */       byteVector.putShort(newUTF8("SourceDebugExtension")).putInt(m);
/*  970 */       byteVector.putByteArray(this.sourceDebug.data, 0, m);
/*      */     } 
/*  972 */     if (this.enclosingMethodOwner != 0) {
/*  973 */       byteVector.putShort(newUTF8("EnclosingMethod")).putInt(4);
/*  974 */       byteVector.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
/*      */     } 
/*  976 */     if ((this.access & 0x20000) != 0) {
/*  977 */       byteVector.putShort(newUTF8("Deprecated")).putInt(0);
/*      */     }
/*  979 */     if ((this.access & 0x1000) != 0 && ((
/*  980 */       this.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*      */     {
/*  982 */       byteVector.putShort(newUTF8("Synthetic")).putInt(0);
/*      */     }
/*      */     
/*  985 */     if (this.innerClasses != null) {
/*  986 */       byteVector.putShort(newUTF8("InnerClasses"));
/*  987 */       byteVector.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
/*  988 */       byteVector.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
/*      */     } 
/*  990 */     if (this.anns != null) {
/*  991 */       byteVector.putShort(newUTF8("RuntimeVisibleAnnotations"));
/*  992 */       this.anns.put(byteVector);
/*      */     } 
/*  994 */     if (this.ianns != null) {
/*  995 */       byteVector.putShort(newUTF8("RuntimeInvisibleAnnotations"));
/*  996 */       this.ianns.put(byteVector);
/*      */     } 
/*  998 */     if (this.tanns != null) {
/*  999 */       byteVector.putShort(newUTF8("RuntimeVisibleTypeAnnotations"));
/* 1000 */       this.tanns.put(byteVector);
/*      */     } 
/* 1002 */     if (this.itanns != null) {
/* 1003 */       byteVector.putShort(newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 1004 */       this.itanns.put(byteVector);
/*      */     } 
/* 1006 */     if (this.attrs != null) {
/* 1007 */       this.attrs.put(this, null, 0, -1, -1, byteVector);
/*      */     }
/* 1009 */     if (this.invalidFrames) {
/* 1010 */       this.anns = null;
/* 1011 */       this.ianns = null;
/* 1012 */       this.attrs = null;
/* 1013 */       this.innerClassesCount = 0;
/* 1014 */       this.innerClasses = null;
/* 1015 */       this.bootstrapMethodsCount = 0;
/* 1016 */       this.bootstrapMethods = null;
/* 1017 */       this.firstField = null;
/* 1018 */       this.lastField = null;
/* 1019 */       this.firstMethod = null;
/* 1020 */       this.lastMethod = null;
/* 1021 */       this.computeMaxs = false;
/* 1022 */       this.computeFrames = true;
/* 1023 */       this.invalidFrames = false;
/* 1024 */       (new ClassReader(byteVector.data)).accept(this, 4);
/* 1025 */       return toByteArray();
/*      */     } 
/* 1027 */     return byteVector.data;
/*      */   }
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
/*      */   Item newConstItem(Object paramObject) {
/* 1046 */     if (paramObject instanceof Integer) {
/* 1047 */       int i = ((Integer)paramObject).intValue();
/* 1048 */       return newInteger(i);
/* 1049 */     }  if (paramObject instanceof Byte) {
/* 1050 */       int i = ((Byte)paramObject).intValue();
/* 1051 */       return newInteger(i);
/* 1052 */     }  if (paramObject instanceof Character) {
/* 1053 */       char c = ((Character)paramObject).charValue();
/* 1054 */       return newInteger(c);
/* 1055 */     }  if (paramObject instanceof Short) {
/* 1056 */       int i = ((Short)paramObject).intValue();
/* 1057 */       return newInteger(i);
/* 1058 */     }  if (paramObject instanceof Boolean) {
/* 1059 */       boolean bool = ((Boolean)paramObject).booleanValue() ? true : false;
/* 1060 */       return newInteger(bool);
/* 1061 */     }  if (paramObject instanceof Float) {
/* 1062 */       float f = ((Float)paramObject).floatValue();
/* 1063 */       return newFloat(f);
/* 1064 */     }  if (paramObject instanceof Long) {
/* 1065 */       long l = ((Long)paramObject).longValue();
/* 1066 */       return newLong(l);
/* 1067 */     }  if (paramObject instanceof Double) {
/* 1068 */       double d = ((Double)paramObject).doubleValue();
/* 1069 */       return newDouble(d);
/* 1070 */     }  if (paramObject instanceof String)
/* 1071 */       return newString((String)paramObject); 
/* 1072 */     if (paramObject instanceof Type) {
/* 1073 */       Type type = (Type)paramObject;
/* 1074 */       int i = type.getSort();
/* 1075 */       if (i == 10)
/* 1076 */         return newClassItem(type.getInternalName()); 
/* 1077 */       if (i == 11) {
/* 1078 */         return newMethodTypeItem(type.getDescriptor());
/*      */       }
/* 1080 */       return newClassItem(type.getDescriptor());
/*      */     } 
/* 1082 */     if (paramObject instanceof Handle) {
/* 1083 */       Handle handle = (Handle)paramObject;
/* 1084 */       return newHandleItem(handle.tag, handle.owner, handle.name, handle.desc);
/*      */     } 
/* 1086 */     throw new IllegalArgumentException("value " + paramObject);
/*      */   }
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
/*      */   public int newConst(Object paramObject) {
/* 1104 */     return (newConstItem(paramObject)).index;
/*      */   }
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
/*      */   public int newUTF8(String paramString) {
/* 1118 */     this.key.set(1, paramString, null, null);
/* 1119 */     Item item = get(this.key);
/* 1120 */     if (item == null) {
/* 1121 */       this.pool.putByte(1).putUTF8(paramString);
/* 1122 */       item = new Item(this.index++, this.key);
/* 1123 */       put(item);
/*      */     } 
/* 1125 */     return item.index;
/*      */   }
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
/*      */   Item newClassItem(String paramString) {
/* 1139 */     this.key2.set(7, paramString, null, null);
/* 1140 */     Item item = get(this.key2);
/* 1141 */     if (item == null) {
/* 1142 */       this.pool.put12(7, newUTF8(paramString));
/* 1143 */       item = new Item(this.index++, this.key2);
/* 1144 */       put(item);
/*      */     } 
/* 1146 */     return item;
/*      */   }
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
/*      */   public int newClass(String paramString) {
/* 1160 */     return (newClassItem(paramString)).index;
/*      */   }
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
/*      */   Item newMethodTypeItem(String paramString) {
/* 1174 */     this.key2.set(16, paramString, null, null);
/* 1175 */     Item item = get(this.key2);
/* 1176 */     if (item == null) {
/* 1177 */       this.pool.put12(16, newUTF8(paramString));
/* 1178 */       item = new Item(this.index++, this.key2);
/* 1179 */       put(item);
/*      */     } 
/* 1181 */     return item;
/*      */   }
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
/*      */   public int newMethodType(String paramString) {
/* 1196 */     return (newMethodTypeItem(paramString)).index;
/*      */   }
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
/*      */   Item newHandleItem(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 1223 */     this.key4.set(20 + paramInt, paramString1, paramString2, paramString3);
/* 1224 */     Item item = get(this.key4);
/* 1225 */     if (item == null) {
/* 1226 */       if (paramInt <= 4) {
/* 1227 */         put112(15, paramInt, newField(paramString1, paramString2, paramString3));
/*      */       } else {
/* 1229 */         put112(15, paramInt, 
/*      */             
/* 1231 */             newMethod(paramString1, paramString2, paramString3, (paramInt == 9)));
/*      */       } 
/*      */       
/* 1234 */       item = new Item(this.index++, this.key4);
/* 1235 */       put(item);
/*      */     } 
/* 1237 */     return item;
/*      */   }
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
/*      */   public int newHandle(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 1265 */     return (newHandleItem(paramInt, paramString1, paramString2, paramString3)).index;
/*      */   }
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
/*      */   Item newInvokeDynamicItem(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/*      */     int n;
/* 1288 */     ByteVector byteVector = this.bootstrapMethods;
/* 1289 */     if (byteVector == null) {
/* 1290 */       byteVector = this.bootstrapMethods = new ByteVector();
/*      */     }
/*      */     
/* 1293 */     int i = byteVector.length;
/*      */     
/* 1295 */     int j = paramHandle.hashCode();
/* 1296 */     byteVector.putShort(newHandle(paramHandle.tag, paramHandle.owner, paramHandle.name, paramHandle.desc));
/*      */ 
/*      */     
/* 1299 */     int k = paramVarArgs.length;
/* 1300 */     byteVector.putShort(k);
/*      */     
/* 1302 */     for (byte b = 0; b < k; b++) {
/* 1303 */       Object object = paramVarArgs[b];
/* 1304 */       j ^= object.hashCode();
/* 1305 */       byteVector.putShort(newConst(object));
/*      */     } 
/*      */     
/* 1308 */     byte[] arrayOfByte = byteVector.data;
/* 1309 */     int m = 2 + k << 1;
/* 1310 */     j &= Integer.MAX_VALUE;
/* 1311 */     Item item = this.items[j % this.items.length];
/* 1312 */     label34: while (item != null) {
/* 1313 */       if (item.type != 33 || item.hashCode != j) {
/* 1314 */         item = item.next;
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1320 */       n = item.intVal;
/* 1321 */       for (byte b1 = 0; b1 < m; b1++) {
/* 1322 */         if (arrayOfByte[i + b1] != arrayOfByte[n + b1]) {
/* 1323 */           item = item.next;
/*      */ 
/*      */           
/*      */           continue label34;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1331 */     if (item != null) {
/* 1332 */       n = item.index;
/* 1333 */       byteVector.length = i;
/*      */     } else {
/* 1335 */       n = this.bootstrapMethodsCount++;
/* 1336 */       item = new Item(n);
/* 1337 */       item.set(i, j);
/* 1338 */       put(item);
/*      */     } 
/*      */ 
/*      */     
/* 1342 */     this.key3.set(paramString1, paramString2, n);
/* 1343 */     item = get(this.key3);
/* 1344 */     if (item == null) {
/* 1345 */       put122(18, n, newNameType(paramString1, paramString2));
/* 1346 */       item = new Item(this.index++, this.key3);
/* 1347 */       put(item);
/*      */     } 
/* 1349 */     return item;
/*      */   }
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
/*      */   public int newInvokeDynamic(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 1372 */     return (newInvokeDynamicItem(paramString1, paramString2, paramHandle, paramVarArgs)).index;
/*      */   }
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
/*      */   Item newFieldItem(String paramString1, String paramString2, String paramString3) {
/* 1388 */     this.key3.set(9, paramString1, paramString2, paramString3);
/* 1389 */     Item item = get(this.key3);
/* 1390 */     if (item == null) {
/* 1391 */       put122(9, newClass(paramString1), newNameType(paramString2, paramString3));
/* 1392 */       item = new Item(this.index++, this.key3);
/* 1393 */       put(item);
/*      */     } 
/* 1395 */     return item;
/*      */   }
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
/*      */   public int newField(String paramString1, String paramString2, String paramString3) {
/* 1413 */     return (newFieldItem(paramString1, paramString2, paramString3)).index;
/*      */   }
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
/*      */   Item newMethodItem(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1432 */     byte b = paramBoolean ? 11 : 10;
/* 1433 */     this.key3.set(b, paramString1, paramString2, paramString3);
/* 1434 */     Item item = get(this.key3);
/* 1435 */     if (item == null) {
/* 1436 */       put122(b, newClass(paramString1), newNameType(paramString2, paramString3));
/* 1437 */       item = new Item(this.index++, this.key3);
/* 1438 */       put(item);
/*      */     } 
/* 1440 */     return item;
/*      */   }
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
/*      */   public int newMethod(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 1461 */     return (newMethodItem(paramString1, paramString2, paramString3, paramBoolean)).index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newInteger(int paramInt) {
/* 1473 */     this.key.set(paramInt);
/* 1474 */     Item item = get(this.key);
/* 1475 */     if (item == null) {
/* 1476 */       this.pool.putByte(3).putInt(paramInt);
/* 1477 */       item = new Item(this.index++, this.key);
/* 1478 */       put(item);
/*      */     } 
/* 1480 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newFloat(float paramFloat) {
/* 1492 */     this.key.set(paramFloat);
/* 1493 */     Item item = get(this.key);
/* 1494 */     if (item == null) {
/* 1495 */       this.pool.putByte(4).putInt(this.key.intVal);
/* 1496 */       item = new Item(this.index++, this.key);
/* 1497 */       put(item);
/*      */     } 
/* 1499 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newLong(long paramLong) {
/* 1511 */     this.key.set(paramLong);
/* 1512 */     Item item = get(this.key);
/* 1513 */     if (item == null) {
/* 1514 */       this.pool.putByte(5).putLong(paramLong);
/* 1515 */       item = new Item(this.index, this.key);
/* 1516 */       this.index += 2;
/* 1517 */       put(item);
/*      */     } 
/* 1519 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Item newDouble(double paramDouble) {
/* 1531 */     this.key.set(paramDouble);
/* 1532 */     Item item = get(this.key);
/* 1533 */     if (item == null) {
/* 1534 */       this.pool.putByte(6).putLong(this.key.longVal);
/* 1535 */       item = new Item(this.index, this.key);
/* 1536 */       this.index += 2;
/* 1537 */       put(item);
/*      */     } 
/* 1539 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item newString(String paramString) {
/* 1551 */     this.key2.set(8, paramString, null, null);
/* 1552 */     Item item = get(this.key2);
/* 1553 */     if (item == null) {
/* 1554 */       this.pool.put12(8, newUTF8(paramString));
/* 1555 */       item = new Item(this.index++, this.key2);
/* 1556 */       put(item);
/*      */     } 
/* 1558 */     return item;
/*      */   }
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
/*      */   public int newNameType(String paramString1, String paramString2) {
/* 1574 */     return (newNameTypeItem(paramString1, paramString2)).index;
/*      */   }
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
/*      */   Item newNameTypeItem(String paramString1, String paramString2) {
/* 1588 */     this.key2.set(12, paramString1, paramString2, null);
/* 1589 */     Item item = get(this.key2);
/* 1590 */     if (item == null) {
/* 1591 */       put122(12, newUTF8(paramString1), newUTF8(paramString2));
/* 1592 */       item = new Item(this.index++, this.key2);
/* 1593 */       put(item);
/*      */     } 
/* 1595 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int addType(String paramString) {
/* 1607 */     this.key.set(30, paramString, null, null);
/* 1608 */     Item item = get(this.key);
/* 1609 */     if (item == null) {
/* 1610 */       item = addType(this.key);
/*      */     }
/* 1612 */     return item.index;
/*      */   }
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
/*      */   int addUninitializedType(String paramString, int paramInt) {
/* 1628 */     this.key.type = 31;
/* 1629 */     this.key.intVal = paramInt;
/* 1630 */     this.key.strVal1 = paramString;
/* 1631 */     this.key.hashCode = Integer.MAX_VALUE & 31 + paramString.hashCode() + paramInt;
/* 1632 */     Item item = get(this.key);
/* 1633 */     if (item == null) {
/* 1634 */       item = addType(this.key);
/*      */     }
/* 1636 */     return item.index;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item addType(Item paramItem) {
/* 1648 */     this.typeCount = (short)(this.typeCount + 1);
/* 1649 */     Item item = new Item(this.typeCount, this.key);
/* 1650 */     put(item);
/* 1651 */     if (this.typeTable == null) {
/* 1652 */       this.typeTable = new Item[16];
/*      */     }
/* 1654 */     if (this.typeCount == this.typeTable.length) {
/* 1655 */       Item[] arrayOfItem = new Item[2 * this.typeTable.length];
/* 1656 */       System.arraycopy(this.typeTable, 0, arrayOfItem, 0, this.typeTable.length);
/* 1657 */       this.typeTable = arrayOfItem;
/*      */     } 
/* 1659 */     this.typeTable[this.typeCount] = item;
/* 1660 */     return item;
/*      */   }
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
/*      */   int getMergedType(int paramInt1, int paramInt2) {
/* 1676 */     this.key2.type = 32;
/* 1677 */     this.key2.longVal = paramInt1 | paramInt2 << 32L;
/* 1678 */     this.key2.hashCode = Integer.MAX_VALUE & 32 + paramInt1 + paramInt2;
/* 1679 */     Item item = get(this.key2);
/* 1680 */     if (item == null) {
/* 1681 */       String str1 = (this.typeTable[paramInt1]).strVal1;
/* 1682 */       String str2 = (this.typeTable[paramInt2]).strVal1;
/* 1683 */       this.key2.intVal = addType(getCommonSuperClass(str1, str2));
/* 1684 */       item = new Item(0, this.key2);
/* 1685 */       put(item);
/*      */     } 
/* 1687 */     return item.intVal;
/*      */   }
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
/*      */   protected String getCommonSuperClass(String paramString1, String paramString2) {
/*      */     Class<?> clazz1, clazz2;
/* 1708 */     ClassLoader classLoader = getClass().getClassLoader();
/*      */     try {
/* 1710 */       clazz1 = Class.forName(paramString1.replace('/', '.'), false, classLoader);
/* 1711 */       clazz2 = Class.forName(paramString2.replace('/', '.'), false, classLoader);
/* 1712 */     } catch (Exception exception) {
/* 1713 */       throw new RuntimeException(exception.toString());
/*      */     } 
/* 1715 */     if (clazz1.isAssignableFrom(clazz2)) {
/* 1716 */       return paramString1;
/*      */     }
/* 1718 */     if (clazz2.isAssignableFrom(clazz1)) {
/* 1719 */       return paramString2;
/*      */     }
/* 1721 */     if (clazz1.isInterface() || clazz2.isInterface()) {
/* 1722 */       return "java/lang/Object";
/*      */     }
/*      */     while (true) {
/* 1725 */       clazz1 = clazz1.getSuperclass();
/* 1726 */       if (clazz1.isAssignableFrom(clazz2)) {
/* 1727 */         return clazz1.getName().replace('.', '/');
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Item get(Item paramItem) {
/* 1741 */     Item item = this.items[paramItem.hashCode % this.items.length];
/* 1742 */     while (item != null && (item.type != paramItem.type || !paramItem.isEqualTo(item))) {
/* 1743 */       item = item.next;
/*      */     }
/* 1745 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void put(Item paramItem) {
/* 1756 */     if (this.index + this.typeCount > this.threshold) {
/* 1757 */       int j = this.items.length;
/* 1758 */       int k = j * 2 + 1;
/* 1759 */       Item[] arrayOfItem = new Item[k];
/* 1760 */       for (int m = j - 1; m >= 0; m--) {
/* 1761 */         Item item = this.items[m];
/* 1762 */         while (item != null) {
/* 1763 */           int n = item.hashCode % arrayOfItem.length;
/* 1764 */           Item item1 = item.next;
/* 1765 */           item.next = arrayOfItem[n];
/* 1766 */           arrayOfItem[n] = item;
/* 1767 */           item = item1;
/*      */         } 
/*      */       } 
/* 1770 */       this.items = arrayOfItem;
/* 1771 */       this.threshold = (int)(k * 0.75D);
/*      */     } 
/* 1773 */     int i = paramItem.hashCode % this.items.length;
/* 1774 */     paramItem.next = this.items[i];
/* 1775 */     this.items[i] = paramItem;
/*      */   }
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
/*      */   private void put122(int paramInt1, int paramInt2, int paramInt3) {
/* 1789 */     this.pool.put12(paramInt1, paramInt2).putShort(paramInt3);
/*      */   }
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
/*      */   private void put112(int paramInt1, int paramInt2, int paramInt3) {
/* 1803 */     this.pool.put11(paramInt1, paramInt2).putShort(paramInt3);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/ClassWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */