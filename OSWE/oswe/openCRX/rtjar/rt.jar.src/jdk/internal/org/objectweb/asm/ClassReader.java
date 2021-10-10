/*      */ package jdk.internal.org.objectweb.asm;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassReader
/*      */ {
/*      */   static final boolean SIGNATURES = true;
/*      */   static final boolean ANNOTATIONS = true;
/*      */   static final boolean FRAMES = true;
/*      */   static final boolean WRITER = true;
/*      */   static final boolean RESIZE = true;
/*      */   public static final int SKIP_CODE = 1;
/*      */   public static final int SKIP_DEBUG = 2;
/*      */   public static final int SKIP_FRAMES = 4;
/*      */   public static final int EXPAND_FRAMES = 8;
/*      */   public final byte[] b;
/*      */   private final int[] items;
/*      */   private final String[] strings;
/*      */   private final int maxStringLength;
/*      */   public final int header;
/*      */   
/*      */   public ClassReader(byte[] paramArrayOfbyte) {
/*  182 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*      */   public ClassReader(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  196 */     this.b = paramArrayOfbyte;
/*      */     
/*  198 */     if (readShort(paramInt1 + 6) > 52) {
/*  199 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  202 */     this.items = new int[readUnsignedShort(paramInt1 + 8)];
/*  203 */     int i = this.items.length;
/*  204 */     this.strings = new String[i];
/*  205 */     int j = 0;
/*  206 */     int k = paramInt1 + 10;
/*  207 */     for (byte b = 1; b < i; b++) {
/*  208 */       int m; this.items[b] = k + 1;
/*      */       
/*  210 */       switch (paramArrayOfbyte[k]) {
/*      */         case 3:
/*      */         case 4:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 18:
/*  218 */           m = 5;
/*      */           break;
/*      */         case 5:
/*      */         case 6:
/*  222 */           m = 9;
/*  223 */           b++;
/*      */           break;
/*      */         case 1:
/*  226 */           m = 3 + readUnsignedShort(k + 1);
/*  227 */           if (m > j) {
/*  228 */             j = m;
/*      */           }
/*      */           break;
/*      */         case 15:
/*  232 */           m = 4;
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  238 */           m = 3;
/*      */           break;
/*      */       } 
/*  241 */       k += m;
/*      */     } 
/*  243 */     this.maxStringLength = j;
/*      */     
/*  245 */     this.header = k;
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
/*      */   public int getAccess() {
/*  258 */     return readUnsignedShort(this.header);
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
/*      */   public String getClassName() {
/*  270 */     return readClass(this.header + 2, new char[this.maxStringLength]);
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
/*      */   public String getSuperName() {
/*  284 */     return readClass(this.header + 4, new char[this.maxStringLength]);
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
/*      */   public String[] getInterfaces() {
/*  297 */     int i = this.header + 6;
/*  298 */     int j = readUnsignedShort(i);
/*  299 */     String[] arrayOfString = new String[j];
/*  300 */     if (j > 0) {
/*  301 */       char[] arrayOfChar = new char[this.maxStringLength];
/*  302 */       for (byte b = 0; b < j; b++) {
/*  303 */         i += 2;
/*  304 */         arrayOfString[b] = readClass(i, arrayOfChar);
/*      */       } 
/*      */     } 
/*  307 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyPool(ClassWriter paramClassWriter) {
/*  318 */     char[] arrayOfChar = new char[this.maxStringLength];
/*  319 */     int i = this.items.length;
/*  320 */     Item[] arrayOfItem = new Item[i]; int j;
/*  321 */     for (j = 1; j < i; j++) {
/*  322 */       int m; String str; int k = this.items[j];
/*  323 */       byte b = this.b[k - 1];
/*  324 */       Item item = new Item(j);
/*      */       
/*  326 */       switch (b) {
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*  330 */           m = this.items[readUnsignedShort(k + 2)];
/*  331 */           item.set(b, readClass(k, arrayOfChar), readUTF8(m, arrayOfChar), 
/*  332 */               readUTF8(m + 2, arrayOfChar));
/*      */           break;
/*      */         case 3:
/*  335 */           item.set(readInt(k));
/*      */           break;
/*      */         case 4:
/*  338 */           item.set(Float.intBitsToFloat(readInt(k)));
/*      */           break;
/*      */         case 12:
/*  341 */           item.set(b, readUTF8(k, arrayOfChar), readUTF8(k + 2, arrayOfChar), null);
/*      */           break;
/*      */         
/*      */         case 5:
/*  345 */           item.set(readLong(k));
/*  346 */           j++;
/*      */           break;
/*      */         case 6:
/*  349 */           item.set(Double.longBitsToDouble(readLong(k)));
/*  350 */           j++;
/*      */           break;
/*      */         case 1:
/*  353 */           str = this.strings[j];
/*  354 */           if (str == null) {
/*  355 */             k = this.items[j];
/*  356 */             str = this.strings[j] = readUTF(k + 2, 
/*  357 */                 readUnsignedShort(k), arrayOfChar);
/*      */           } 
/*  359 */           item.set(b, str, null, null);
/*      */           break;
/*      */         
/*      */         case 15:
/*  363 */           n = this.items[readUnsignedShort(k + 1)];
/*  364 */           m = this.items[readUnsignedShort(n + 2)];
/*  365 */           item.set(20 + readByte(k), 
/*  366 */               readClass(n, arrayOfChar), 
/*  367 */               readUTF8(m, arrayOfChar), readUTF8(m + 2, arrayOfChar));
/*      */           break;
/*      */         
/*      */         case 18:
/*  371 */           if (paramClassWriter.bootstrapMethods == null) {
/*  372 */             copyBootstrapMethods(paramClassWriter, arrayOfItem, arrayOfChar);
/*      */           }
/*  374 */           m = this.items[readUnsignedShort(k + 2)];
/*  375 */           item.set(readUTF8(m, arrayOfChar), readUTF8(m + 2, arrayOfChar), 
/*  376 */               readUnsignedShort(k));
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  382 */           item.set(b, readUTF8(k, arrayOfChar), null, null);
/*      */           break;
/*      */       } 
/*      */       
/*  386 */       int n = item.hashCode % arrayOfItem.length;
/*  387 */       item.next = arrayOfItem[n];
/*  388 */       arrayOfItem[n] = item;
/*      */     } 
/*      */     
/*  391 */     j = this.items[1] - 1;
/*  392 */     paramClassWriter.pool.putByteArray(this.b, j, this.header - j);
/*  393 */     paramClassWriter.items = arrayOfItem;
/*  394 */     paramClassWriter.threshold = (int)(0.75D * i);
/*  395 */     paramClassWriter.index = i;
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
/*      */   private void copyBootstrapMethods(ClassWriter paramClassWriter, Item[] paramArrayOfItem, char[] paramArrayOfchar) {
/*  408 */     int i = getAttributes();
/*  409 */     boolean bool = false; int j;
/*  410 */     for (j = readUnsignedShort(i); j > 0; j--) {
/*  411 */       String str = readUTF8(i + 2, paramArrayOfchar);
/*  412 */       if ("BootstrapMethods".equals(str)) {
/*  413 */         bool = true;
/*      */         break;
/*      */       } 
/*  416 */       i += 6 + readInt(i + 4);
/*      */     } 
/*  418 */     if (!bool) {
/*      */       return;
/*      */     }
/*      */     
/*  422 */     j = readUnsignedShort(i + 8); int k, m;
/*  423 */     for (k = 0, m = i + 10; k < j; k++) {
/*  424 */       int n = m - i - 10;
/*  425 */       int i1 = readConst(readUnsignedShort(m), paramArrayOfchar).hashCode();
/*  426 */       for (int i2 = readUnsignedShort(m + 2); i2 > 0; i2--) {
/*  427 */         i1 ^= readConst(readUnsignedShort(m + 4), paramArrayOfchar).hashCode();
/*  428 */         m += 2;
/*      */       } 
/*  430 */       m += 4;
/*  431 */       Item item = new Item(k);
/*  432 */       item.set(n, i1 & Integer.MAX_VALUE);
/*  433 */       int i3 = item.hashCode % paramArrayOfItem.length;
/*  434 */       item.next = paramArrayOfItem[i3];
/*  435 */       paramArrayOfItem[i3] = item;
/*      */     } 
/*  437 */     k = readInt(i + 4);
/*  438 */     ByteVector byteVector = new ByteVector(k + 62);
/*  439 */     byteVector.putByteArray(this.b, i + 10, k - 2);
/*  440 */     paramClassWriter.bootstrapMethodsCount = j;
/*  441 */     paramClassWriter.bootstrapMethods = byteVector;
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
/*      */   public ClassReader(InputStream paramInputStream) throws IOException {
/*  453 */     this(readClass(paramInputStream, false));
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
/*      */   public ClassReader(String paramString) throws IOException {
/*  465 */     this(readClass(
/*  466 */           ClassLoader.getSystemResourceAsStream(paramString.replace('.', '/') + ".class"), true));
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
/*      */   private static byte[] readClass(InputStream paramInputStream, boolean paramBoolean) throws IOException {
/*  483 */     if (paramInputStream == null) {
/*  484 */       throw new IOException("Class not found");
/*      */     }
/*      */     try {
/*  487 */       byte[] arrayOfByte = new byte[paramInputStream.available()];
/*  488 */       int i = 0;
/*      */       while (true) {
/*  490 */         int j = paramInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
/*  491 */         if (j == -1) {
/*  492 */           if (i < arrayOfByte.length) {
/*  493 */             byte[] arrayOfByte1 = new byte[i];
/*  494 */             System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, i);
/*  495 */             arrayOfByte = arrayOfByte1;
/*      */           } 
/*  497 */           return arrayOfByte;
/*      */         } 
/*  499 */         i += j;
/*  500 */         if (i == arrayOfByte.length) {
/*  501 */           int k = paramInputStream.read();
/*  502 */           if (k < 0) {
/*  503 */             return arrayOfByte;
/*      */           }
/*  505 */           byte[] arrayOfByte1 = new byte[arrayOfByte.length + 1000];
/*  506 */           System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, i);
/*  507 */           arrayOfByte1[i++] = (byte)k;
/*  508 */           arrayOfByte = arrayOfByte1;
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  512 */       if (paramBoolean) {
/*  513 */         paramInputStream.close();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void accept(ClassVisitor paramClassVisitor, int paramInt) {
/*  535 */     accept(paramClassVisitor, new Attribute[0], paramInt);
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
/*      */   public void accept(ClassVisitor paramClassVisitor, Attribute[] paramArrayOfAttribute, int paramInt) {
/*  561 */     int i = this.header;
/*  562 */     char[] arrayOfChar = new char[this.maxStringLength];
/*      */     
/*  564 */     Context context = new Context();
/*  565 */     context.attrs = paramArrayOfAttribute;
/*  566 */     context.flags = paramInt;
/*  567 */     context.buffer = arrayOfChar;
/*      */ 
/*      */     
/*  570 */     int j = readUnsignedShort(i);
/*  571 */     String str1 = readClass(i + 2, arrayOfChar);
/*  572 */     String str2 = readClass(i + 4, arrayOfChar);
/*  573 */     String[] arrayOfString = new String[readUnsignedShort(i + 6)];
/*  574 */     i += 8;
/*  575 */     for (byte b = 0; b < arrayOfString.length; b++) {
/*  576 */       arrayOfString[b] = readClass(i, arrayOfChar);
/*  577 */       i += 2;
/*      */     } 
/*      */ 
/*      */     
/*  581 */     String str3 = null;
/*  582 */     String str4 = null;
/*  583 */     String str5 = null;
/*  584 */     String str6 = null;
/*  585 */     String str7 = null;
/*  586 */     String str8 = null;
/*  587 */     int k = 0;
/*  588 */     int m = 0;
/*  589 */     int n = 0;
/*  590 */     int i1 = 0;
/*  591 */     int i2 = 0;
/*  592 */     Attribute attribute = null;
/*      */     
/*  594 */     i = getAttributes(); int i3;
/*  595 */     for (i3 = readUnsignedShort(i); i3 > 0; i3--) {
/*  596 */       String str = readUTF8(i + 2, arrayOfChar);
/*      */ 
/*      */       
/*  599 */       if ("SourceFile".equals(str)) {
/*  600 */         str4 = readUTF8(i + 8, arrayOfChar);
/*  601 */       } else if ("InnerClasses".equals(str)) {
/*  602 */         i2 = i + 8;
/*  603 */       } else if ("EnclosingMethod".equals(str)) {
/*  604 */         str6 = readClass(i + 8, arrayOfChar);
/*  605 */         int i4 = readUnsignedShort(i + 10);
/*  606 */         if (i4 != 0) {
/*  607 */           str7 = readUTF8(this.items[i4], arrayOfChar);
/*  608 */           str8 = readUTF8(this.items[i4] + 2, arrayOfChar);
/*      */         } 
/*  610 */       } else if ("Signature".equals(str)) {
/*  611 */         str3 = readUTF8(i + 8, arrayOfChar);
/*  612 */       } else if ("RuntimeVisibleAnnotations"
/*  613 */         .equals(str)) {
/*  614 */         k = i + 8;
/*  615 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  616 */         .equals(str)) {
/*  617 */         n = i + 8;
/*  618 */       } else if ("Deprecated".equals(str)) {
/*  619 */         j |= 0x20000;
/*  620 */       } else if ("Synthetic".equals(str)) {
/*  621 */         j |= 0x41000;
/*      */       }
/*  623 */       else if ("SourceDebugExtension".equals(str)) {
/*  624 */         int i4 = readInt(i + 4);
/*  625 */         str5 = readUTF(i + 8, i4, new char[i4]);
/*  626 */       } else if ("RuntimeInvisibleAnnotations"
/*  627 */         .equals(str)) {
/*  628 */         m = i + 8;
/*  629 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  630 */         .equals(str)) {
/*  631 */         i1 = i + 8;
/*  632 */       } else if ("BootstrapMethods".equals(str)) {
/*  633 */         int[] arrayOfInt = new int[readUnsignedShort(i + 8)]; byte b1; int i4;
/*  634 */         for (b1 = 0, i4 = i + 10; b1 < arrayOfInt.length; b1++) {
/*  635 */           arrayOfInt[b1] = i4;
/*  636 */           i4 += 2 + readUnsignedShort(i4 + 2) << 1;
/*      */         } 
/*  638 */         context.bootstrapMethods = arrayOfInt;
/*      */       } else {
/*  640 */         Attribute attribute1 = readAttribute(paramArrayOfAttribute, str, i + 8, 
/*  641 */             readInt(i + 4), arrayOfChar, -1, null);
/*  642 */         if (attribute1 != null) {
/*  643 */           attribute1.next = attribute;
/*  644 */           attribute = attribute1;
/*      */         } 
/*      */       } 
/*  647 */       i += 6 + readInt(i + 4);
/*      */     } 
/*      */ 
/*      */     
/*  651 */     paramClassVisitor.visit(readInt(this.items[1] - 7), j, str1, str3, str2, arrayOfString);
/*      */ 
/*      */ 
/*      */     
/*  655 */     if ((paramInt & 0x2) == 0 && (str4 != null || str5 != null))
/*      */     {
/*  657 */       paramClassVisitor.visitSource(str4, str5);
/*      */     }
/*      */ 
/*      */     
/*  661 */     if (str6 != null) {
/*  662 */       paramClassVisitor.visitOuterClass(str6, str7, str8);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  667 */     if (k != 0) {
/*  668 */       int i4; for (i3 = readUnsignedShort(k), i4 = k + 2; i3 > 0; i3--) {
/*  669 */         i4 = readAnnotationValues(i4 + 2, arrayOfChar, true, paramClassVisitor
/*  670 */             .visitAnnotation(readUTF8(i4, arrayOfChar), true));
/*      */       }
/*      */     } 
/*  673 */     if (m != 0) {
/*  674 */       int i4; for (i3 = readUnsignedShort(m), i4 = m + 2; i3 > 0; i3--) {
/*  675 */         i4 = readAnnotationValues(i4 + 2, arrayOfChar, true, paramClassVisitor
/*  676 */             .visitAnnotation(readUTF8(i4, arrayOfChar), false));
/*      */       }
/*      */     } 
/*  679 */     if (n != 0) {
/*  680 */       int i4; for (i3 = readUnsignedShort(n), i4 = n + 2; i3 > 0; i3--) {
/*  681 */         i4 = readAnnotationTarget(context, i4);
/*  682 */         i4 = readAnnotationValues(i4 + 2, arrayOfChar, true, paramClassVisitor
/*  683 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  684 */               readUTF8(i4, arrayOfChar), true));
/*      */       } 
/*      */     } 
/*  687 */     if (i1 != 0) {
/*  688 */       int i4; for (i3 = readUnsignedShort(i1), i4 = i1 + 2; i3 > 0; i3--) {
/*  689 */         i4 = readAnnotationTarget(context, i4);
/*  690 */         i4 = readAnnotationValues(i4 + 2, arrayOfChar, true, paramClassVisitor
/*  691 */             .visitTypeAnnotation(context.typeRef, context.typePath, 
/*  692 */               readUTF8(i4, arrayOfChar), false));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  697 */     while (attribute != null) {
/*  698 */       Attribute attribute1 = attribute.next;
/*  699 */       attribute.next = null;
/*  700 */       paramClassVisitor.visitAttribute(attribute);
/*  701 */       attribute = attribute1;
/*      */     } 
/*      */ 
/*      */     
/*  705 */     if (i2 != 0) {
/*  706 */       i3 = i2 + 2;
/*  707 */       for (int i4 = readUnsignedShort(i2); i4 > 0; i4--) {
/*  708 */         paramClassVisitor.visitInnerClass(readClass(i3, arrayOfChar), 
/*  709 */             readClass(i3 + 2, arrayOfChar), readUTF8(i3 + 4, arrayOfChar), 
/*  710 */             readUnsignedShort(i3 + 6));
/*  711 */         i3 += 8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  716 */     i = this.header + 10 + 2 * arrayOfString.length;
/*  717 */     for (i3 = readUnsignedShort(i - 2); i3 > 0; i3--) {
/*  718 */       i = readField(paramClassVisitor, context, i);
/*      */     }
/*  720 */     i += 2;
/*  721 */     for (i3 = readUnsignedShort(i - 2); i3 > 0; i3--) {
/*  722 */       i = readMethod(paramClassVisitor, context, i);
/*      */     }
/*      */ 
/*      */     
/*  726 */     paramClassVisitor.visitEnd();
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
/*      */   private int readField(ClassVisitor paramClassVisitor, Context paramContext, int paramInt) {
/*  743 */     char[] arrayOfChar = paramContext.buffer;
/*  744 */     int i = readUnsignedShort(paramInt);
/*  745 */     String str1 = readUTF8(paramInt + 2, arrayOfChar);
/*  746 */     String str2 = readUTF8(paramInt + 4, arrayOfChar);
/*  747 */     paramInt += 6;
/*      */ 
/*      */     
/*  750 */     String str3 = null;
/*  751 */     int j = 0;
/*  752 */     int k = 0;
/*  753 */     int m = 0;
/*  754 */     int n = 0;
/*  755 */     Object object = null;
/*  756 */     Attribute attribute = null;
/*      */     
/*  758 */     for (int i1 = readUnsignedShort(paramInt); i1 > 0; i1--) {
/*  759 */       String str = readUTF8(paramInt + 2, arrayOfChar);
/*      */ 
/*      */       
/*  762 */       if ("ConstantValue".equals(str)) {
/*  763 */         int i2 = readUnsignedShort(paramInt + 8);
/*  764 */         object = (i2 == 0) ? null : readConst(i2, arrayOfChar);
/*  765 */       } else if ("Signature".equals(str)) {
/*  766 */         str3 = readUTF8(paramInt + 8, arrayOfChar);
/*  767 */       } else if ("Deprecated".equals(str)) {
/*  768 */         i |= 0x20000;
/*  769 */       } else if ("Synthetic".equals(str)) {
/*  770 */         i |= 0x41000;
/*      */       }
/*  772 */       else if ("RuntimeVisibleAnnotations"
/*  773 */         .equals(str)) {
/*  774 */         j = paramInt + 8;
/*  775 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  776 */         .equals(str)) {
/*  777 */         m = paramInt + 8;
/*  778 */       } else if ("RuntimeInvisibleAnnotations"
/*  779 */         .equals(str)) {
/*  780 */         k = paramInt + 8;
/*  781 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  782 */         .equals(str)) {
/*  783 */         n = paramInt + 8;
/*      */       } else {
/*  785 */         Attribute attribute1 = readAttribute(paramContext.attrs, str, paramInt + 8, 
/*  786 */             readInt(paramInt + 4), arrayOfChar, -1, null);
/*  787 */         if (attribute1 != null) {
/*  788 */           attribute1.next = attribute;
/*  789 */           attribute = attribute1;
/*      */         } 
/*      */       } 
/*  792 */       paramInt += 6 + readInt(paramInt + 4);
/*      */     } 
/*  794 */     paramInt += 2;
/*      */ 
/*      */     
/*  797 */     FieldVisitor fieldVisitor = paramClassVisitor.visitField(i, str1, str2, str3, object);
/*      */     
/*  799 */     if (fieldVisitor == null) {
/*  800 */       return paramInt;
/*      */     }
/*      */ 
/*      */     
/*  804 */     if (j != 0) {
/*  805 */       for (int i2 = readUnsignedShort(j), i3 = j + 2; i2 > 0; i2--) {
/*  806 */         i3 = readAnnotationValues(i3 + 2, arrayOfChar, true, fieldVisitor
/*  807 */             .visitAnnotation(readUTF8(i3, arrayOfChar), true));
/*      */       }
/*      */     }
/*  810 */     if (k != 0) {
/*  811 */       for (int i2 = readUnsignedShort(k), i3 = k + 2; i2 > 0; i2--) {
/*  812 */         i3 = readAnnotationValues(i3 + 2, arrayOfChar, true, fieldVisitor
/*  813 */             .visitAnnotation(readUTF8(i3, arrayOfChar), false));
/*      */       }
/*      */     }
/*  816 */     if (m != 0) {
/*  817 */       for (int i2 = readUnsignedShort(m), i3 = m + 2; i2 > 0; i2--) {
/*  818 */         i3 = readAnnotationTarget(paramContext, i3);
/*  819 */         i3 = readAnnotationValues(i3 + 2, arrayOfChar, true, fieldVisitor
/*  820 */             .visitTypeAnnotation(paramContext.typeRef, paramContext.typePath, 
/*  821 */               readUTF8(i3, arrayOfChar), true));
/*      */       } 
/*      */     }
/*  824 */     if (n != 0) {
/*  825 */       for (int i2 = readUnsignedShort(n), i3 = n + 2; i2 > 0; i2--) {
/*  826 */         i3 = readAnnotationTarget(paramContext, i3);
/*  827 */         i3 = readAnnotationValues(i3 + 2, arrayOfChar, true, fieldVisitor
/*  828 */             .visitTypeAnnotation(paramContext.typeRef, paramContext.typePath, 
/*  829 */               readUTF8(i3, arrayOfChar), false));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  834 */     while (attribute != null) {
/*  835 */       Attribute attribute1 = attribute.next;
/*  836 */       attribute.next = null;
/*  837 */       fieldVisitor.visitAttribute(attribute);
/*  838 */       attribute = attribute1;
/*      */     } 
/*      */ 
/*      */     
/*  842 */     fieldVisitor.visitEnd();
/*      */     
/*  844 */     return paramInt;
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
/*      */   private int readMethod(ClassVisitor paramClassVisitor, Context paramContext, int paramInt) {
/*  861 */     char[] arrayOfChar = paramContext.buffer;
/*  862 */     paramContext.access = readUnsignedShort(paramInt);
/*  863 */     paramContext.name = readUTF8(paramInt + 2, arrayOfChar);
/*  864 */     paramContext.desc = readUTF8(paramInt + 4, arrayOfChar);
/*  865 */     paramInt += 6;
/*      */ 
/*      */     
/*  868 */     int i = 0;
/*  869 */     int j = 0;
/*  870 */     String[] arrayOfString = null;
/*  871 */     String str = null;
/*  872 */     int k = 0;
/*  873 */     int m = 0;
/*  874 */     int n = 0;
/*  875 */     int i1 = 0;
/*  876 */     int i2 = 0;
/*  877 */     int i3 = 0;
/*  878 */     int i4 = 0;
/*  879 */     int i5 = 0;
/*  880 */     int i6 = paramInt;
/*  881 */     Attribute attribute = null;
/*      */     
/*  883 */     for (int i7 = readUnsignedShort(paramInt); i7 > 0; i7--) {
/*  884 */       String str1 = readUTF8(paramInt + 2, arrayOfChar);
/*      */ 
/*      */       
/*  887 */       if ("Code".equals(str1)) {
/*  888 */         if ((paramContext.flags & 0x1) == 0) {
/*  889 */           i = paramInt + 8;
/*      */         }
/*  891 */       } else if ("Exceptions".equals(str1)) {
/*  892 */         arrayOfString = new String[readUnsignedShort(paramInt + 8)];
/*  893 */         j = paramInt + 10;
/*  894 */         for (byte b = 0; b < arrayOfString.length; b++) {
/*  895 */           arrayOfString[b] = readClass(j, arrayOfChar);
/*  896 */           j += 2;
/*      */         } 
/*  898 */       } else if ("Signature".equals(str1)) {
/*  899 */         str = readUTF8(paramInt + 8, arrayOfChar);
/*  900 */       } else if ("Deprecated".equals(str1)) {
/*  901 */         paramContext.access |= 0x20000;
/*  902 */       } else if ("RuntimeVisibleAnnotations"
/*  903 */         .equals(str1)) {
/*  904 */         m = paramInt + 8;
/*  905 */       } else if ("RuntimeVisibleTypeAnnotations"
/*  906 */         .equals(str1)) {
/*  907 */         i1 = paramInt + 8;
/*  908 */       } else if ("AnnotationDefault".equals(str1)) {
/*  909 */         i3 = paramInt + 8;
/*  910 */       } else if ("Synthetic".equals(str1)) {
/*  911 */         paramContext.access |= 0x41000;
/*      */       }
/*  913 */       else if ("RuntimeInvisibleAnnotations"
/*  914 */         .equals(str1)) {
/*  915 */         n = paramInt + 8;
/*  916 */       } else if ("RuntimeInvisibleTypeAnnotations"
/*  917 */         .equals(str1)) {
/*  918 */         i2 = paramInt + 8;
/*  919 */       } else if ("RuntimeVisibleParameterAnnotations"
/*  920 */         .equals(str1)) {
/*  921 */         i4 = paramInt + 8;
/*  922 */       } else if ("RuntimeInvisibleParameterAnnotations"
/*  923 */         .equals(str1)) {
/*  924 */         i5 = paramInt + 8;
/*  925 */       } else if ("MethodParameters".equals(str1)) {
/*  926 */         k = paramInt + 8;
/*      */       } else {
/*  928 */         Attribute attribute1 = readAttribute(paramContext.attrs, str1, paramInt + 8, 
/*  929 */             readInt(paramInt + 4), arrayOfChar, -1, null);
/*  930 */         if (attribute1 != null) {
/*  931 */           attribute1.next = attribute;
/*  932 */           attribute = attribute1;
/*      */         } 
/*      */       } 
/*  935 */       paramInt += 6 + readInt(paramInt + 4);
/*      */     } 
/*  937 */     paramInt += 2;
/*      */ 
/*      */     
/*  940 */     MethodVisitor methodVisitor = paramClassVisitor.visitMethod(paramContext.access, paramContext.name, paramContext.desc, str, arrayOfString);
/*      */     
/*  942 */     if (methodVisitor == null) {
/*  943 */       return paramInt;
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
/*      */     
/*  956 */     if (methodVisitor instanceof MethodWriter) {
/*  957 */       MethodWriter methodWriter = (MethodWriter)methodVisitor;
/*  958 */       if (methodWriter.cw.cr == this && str == methodWriter.signature) {
/*  959 */         boolean bool = false;
/*  960 */         if (arrayOfString == null) {
/*  961 */           bool = (methodWriter.exceptionCount == 0) ? true : false;
/*  962 */         } else if (arrayOfString.length == methodWriter.exceptionCount) {
/*  963 */           bool = true;
/*  964 */           for (int i8 = arrayOfString.length - 1; i8 >= 0; i8--) {
/*  965 */             j -= 2;
/*  966 */             if (methodWriter.exceptions[i8] != readUnsignedShort(j)) {
/*  967 */               bool = false;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  972 */         if (bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  978 */           methodWriter.classReaderOffset = i6;
/*  979 */           methodWriter.classReaderLength = paramInt - i6;
/*  980 */           return paramInt;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  986 */     if (k != 0) {
/*  987 */       int i9; for (int i8 = this.b[k] & 0xFF; i8 > 0; i8--, i9 += 4) {
/*  988 */         methodVisitor.visitParameter(readUTF8(i9, arrayOfChar), readUnsignedShort(i9 + 2));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  993 */     if (i3 != 0) {
/*  994 */       AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
/*  995 */       readAnnotationValue(i3, arrayOfChar, null, annotationVisitor);
/*  996 */       if (annotationVisitor != null) {
/*  997 */         annotationVisitor.visitEnd();
/*      */       }
/*      */     } 
/* 1000 */     if (m != 0) {
/* 1001 */       for (int i8 = readUnsignedShort(m), i9 = m + 2; i8 > 0; i8--) {
/* 1002 */         i9 = readAnnotationValues(i9 + 2, arrayOfChar, true, methodVisitor
/* 1003 */             .visitAnnotation(readUTF8(i9, arrayOfChar), true));
/*      */       }
/*      */     }
/* 1006 */     if (n != 0) {
/* 1007 */       for (int i8 = readUnsignedShort(n), i9 = n + 2; i8 > 0; i8--) {
/* 1008 */         i9 = readAnnotationValues(i9 + 2, arrayOfChar, true, methodVisitor
/* 1009 */             .visitAnnotation(readUTF8(i9, arrayOfChar), false));
/*      */       }
/*      */     }
/* 1012 */     if (i1 != 0) {
/* 1013 */       for (int i8 = readUnsignedShort(i1), i9 = i1 + 2; i8 > 0; i8--) {
/* 1014 */         i9 = readAnnotationTarget(paramContext, i9);
/* 1015 */         i9 = readAnnotationValues(i9 + 2, arrayOfChar, true, methodVisitor
/* 1016 */             .visitTypeAnnotation(paramContext.typeRef, paramContext.typePath, 
/* 1017 */               readUTF8(i9, arrayOfChar), true));
/*      */       } 
/*      */     }
/* 1020 */     if (i2 != 0) {
/* 1021 */       for (int i8 = readUnsignedShort(i2), i9 = i2 + 2; i8 > 0; i8--) {
/* 1022 */         i9 = readAnnotationTarget(paramContext, i9);
/* 1023 */         i9 = readAnnotationValues(i9 + 2, arrayOfChar, true, methodVisitor
/* 1024 */             .visitTypeAnnotation(paramContext.typeRef, paramContext.typePath, 
/* 1025 */               readUTF8(i9, arrayOfChar), false));
/*      */       } 
/*      */     }
/* 1028 */     if (i4 != 0) {
/* 1029 */       readParameterAnnotations(methodVisitor, paramContext, i4, true);
/*      */     }
/* 1031 */     if (i5 != 0) {
/* 1032 */       readParameterAnnotations(methodVisitor, paramContext, i5, false);
/*      */     }
/*      */ 
/*      */     
/* 1036 */     while (attribute != null) {
/* 1037 */       Attribute attribute1 = attribute.next;
/* 1038 */       attribute.next = null;
/* 1039 */       methodVisitor.visitAttribute(attribute);
/* 1040 */       attribute = attribute1;
/*      */     } 
/*      */ 
/*      */     
/* 1044 */     if (i != 0) {
/* 1045 */       methodVisitor.visitCode();
/* 1046 */       readCode(methodVisitor, paramContext, i);
/*      */     } 
/*      */ 
/*      */     
/* 1050 */     methodVisitor.visitEnd();
/*      */     
/* 1052 */     return paramInt;
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
/*      */   private void readCode(MethodVisitor paramMethodVisitor, Context paramContext, int paramInt) {
/* 1067 */     byte[] arrayOfByte = this.b;
/* 1068 */     char[] arrayOfChar = paramContext.buffer;
/* 1069 */     int i = readUnsignedShort(paramInt);
/* 1070 */     int j = readUnsignedShort(paramInt + 2);
/* 1071 */     int k = readInt(paramInt + 4);
/* 1072 */     paramInt += 8;
/*      */ 
/*      */     
/* 1075 */     int m = paramInt;
/* 1076 */     int n = paramInt + k;
/* 1077 */     Label[] arrayOfLabel = paramContext.labels = new Label[k + 2];
/* 1078 */     readLabel(k + 1, arrayOfLabel);
/* 1079 */     while (paramInt < n) {
/* 1080 */       int i10, i8 = paramInt - m;
/* 1081 */       int i9 = arrayOfByte[paramInt] & 0xFF;
/* 1082 */       switch (ClassWriter.TYPE[i9]) {
/*      */         case 0:
/*      */         case 4:
/* 1085 */           paramInt++;
/*      */           continue;
/*      */         case 9:
/* 1088 */           readLabel(i8 + readShort(paramInt + 1), arrayOfLabel);
/* 1089 */           paramInt += 3;
/*      */           continue;
/*      */         case 10:
/* 1092 */           readLabel(i8 + readInt(paramInt + 1), arrayOfLabel);
/* 1093 */           paramInt += 5;
/*      */           continue;
/*      */         case 17:
/* 1096 */           i9 = arrayOfByte[paramInt + 1] & 0xFF;
/* 1097 */           if (i9 == 132) {
/* 1098 */             paramInt += 6; continue;
/*      */           } 
/* 1100 */           paramInt += 4;
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 14:
/* 1105 */           paramInt = paramInt + 4 - (i8 & 0x3);
/*      */           
/* 1107 */           readLabel(i8 + readInt(paramInt), arrayOfLabel);
/* 1108 */           for (i10 = readInt(paramInt + 8) - readInt(paramInt + 4) + 1; i10 > 0; i10--) {
/* 1109 */             readLabel(i8 + readInt(paramInt + 12), arrayOfLabel);
/* 1110 */             paramInt += 4;
/*      */           } 
/* 1112 */           paramInt += 12;
/*      */           continue;
/*      */         
/*      */         case 15:
/* 1116 */           paramInt = paramInt + 4 - (i8 & 0x3);
/*      */           
/* 1118 */           readLabel(i8 + readInt(paramInt), arrayOfLabel);
/* 1119 */           for (i10 = readInt(paramInt + 4); i10 > 0; i10--) {
/* 1120 */             readLabel(i8 + readInt(paramInt + 12), arrayOfLabel);
/* 1121 */             paramInt += 8;
/*      */           } 
/* 1123 */           paramInt += 8;
/*      */           continue;
/*      */         case 1:
/*      */         case 3:
/*      */         case 11:
/* 1128 */           paramInt += 2;
/*      */           continue;
/*      */         case 2:
/*      */         case 5:
/*      */         case 6:
/*      */         case 12:
/*      */         case 13:
/* 1135 */           paramInt += 3;
/*      */           continue;
/*      */         case 7:
/*      */         case 8:
/* 1139 */           paramInt += 5;
/*      */           continue;
/*      */       } 
/*      */       
/* 1143 */       paramInt += 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     for (int i1 = readUnsignedShort(paramInt); i1 > 0; i1--) {
/* 1150 */       Label label1 = readLabel(readUnsignedShort(paramInt + 2), arrayOfLabel);
/* 1151 */       Label label2 = readLabel(readUnsignedShort(paramInt + 4), arrayOfLabel);
/* 1152 */       Label label3 = readLabel(readUnsignedShort(paramInt + 6), arrayOfLabel);
/* 1153 */       String str = readUTF8(this.items[readUnsignedShort(paramInt + 8)], arrayOfChar);
/* 1154 */       paramMethodVisitor.visitTryCatchBlock(label1, label2, label3, str);
/* 1155 */       paramInt += 8;
/*      */     } 
/* 1157 */     paramInt += 2;
/*      */ 
/*      */     
/* 1160 */     int[] arrayOfInt1 = null;
/* 1161 */     int[] arrayOfInt2 = null;
/* 1162 */     byte b1 = 0;
/* 1163 */     byte b2 = 0;
/* 1164 */     byte b3 = -1;
/* 1165 */     byte b4 = -1;
/* 1166 */     int i2 = 0;
/* 1167 */     int i3 = 0;
/* 1168 */     boolean bool1 = true;
/* 1169 */     boolean bool2 = ((paramContext.flags & 0x8) != 0) ? true : false;
/* 1170 */     int i4 = 0;
/* 1171 */     int i5 = 0;
/* 1172 */     int i6 = 0;
/* 1173 */     Context context = null;
/* 1174 */     Attribute attribute = null;
/*      */     int i7;
/* 1176 */     for (i7 = readUnsignedShort(paramInt); i7 > 0; i7--) {
/* 1177 */       String str = readUTF8(paramInt + 2, arrayOfChar);
/* 1178 */       if ("LocalVariableTable".equals(str)) {
/* 1179 */         if ((paramContext.flags & 0x2) == 0) {
/* 1180 */           i2 = paramInt + 8;
/* 1181 */           for (int i8 = readUnsignedShort(paramInt + 8), i9 = paramInt; i8 > 0; i8--) {
/* 1182 */             int i10 = readUnsignedShort(i9 + 10);
/* 1183 */             if (arrayOfLabel[i10] == null) {
/* 1184 */               (readLabel(i10, arrayOfLabel)).status |= 0x1;
/*      */             }
/* 1186 */             i10 += readUnsignedShort(i9 + 12);
/* 1187 */             if (arrayOfLabel[i10] == null) {
/* 1188 */               (readLabel(i10, arrayOfLabel)).status |= 0x1;
/*      */             }
/* 1190 */             i9 += 10;
/*      */           } 
/*      */         } 
/* 1193 */       } else if ("LocalVariableTypeTable".equals(str)) {
/* 1194 */         i3 = paramInt + 8;
/* 1195 */       } else if ("LineNumberTable".equals(str)) {
/* 1196 */         if ((paramContext.flags & 0x2) == 0) {
/* 1197 */           for (int i8 = readUnsignedShort(paramInt + 8), i9 = paramInt; i8 > 0; i8--) {
/* 1198 */             int i10 = readUnsignedShort(i9 + 10);
/* 1199 */             if (arrayOfLabel[i10] == null) {
/* 1200 */               (readLabel(i10, arrayOfLabel)).status |= 0x1;
/*      */             }
/* 1202 */             (arrayOfLabel[i10]).line = readUnsignedShort(i9 + 12);
/* 1203 */             i9 += 4;
/*      */           } 
/*      */         }
/* 1206 */       } else if ("RuntimeVisibleTypeAnnotations"
/* 1207 */         .equals(str)) {
/* 1208 */         arrayOfInt1 = readTypeAnnotations(paramMethodVisitor, paramContext, paramInt + 8, true);
/*      */         
/* 1210 */         b3 = (arrayOfInt1.length == 0 || readByte(arrayOfInt1[0]) < 67) ? -1 : readUnsignedShort(arrayOfInt1[0] + 1);
/* 1211 */       } else if ("RuntimeInvisibleTypeAnnotations"
/* 1212 */         .equals(str)) {
/* 1213 */         arrayOfInt2 = readTypeAnnotations(paramMethodVisitor, paramContext, paramInt + 8, false);
/*      */         
/* 1215 */         b4 = (arrayOfInt2.length == 0 || readByte(arrayOfInt2[0]) < 67) ? -1 : readUnsignedShort(arrayOfInt2[0] + 1);
/* 1216 */       } else if ("StackMapTable".equals(str)) {
/* 1217 */         if ((paramContext.flags & 0x4) == 0) {
/* 1218 */           i4 = paramInt + 10;
/* 1219 */           i5 = readInt(paramInt + 4);
/* 1220 */           i6 = readUnsignedShort(paramInt + 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1240 */       else if ("StackMap".equals(str)) {
/* 1241 */         if ((paramContext.flags & 0x4) == 0) {
/* 1242 */           bool1 = false;
/* 1243 */           i4 = paramInt + 10;
/* 1244 */           i5 = readInt(paramInt + 4);
/* 1245 */           i6 = readUnsignedShort(paramInt + 8);
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1253 */         for (byte b = 0; b < paramContext.attrs.length; b++) {
/* 1254 */           if ((paramContext.attrs[b]).type.equals(str)) {
/* 1255 */             Attribute attribute1 = paramContext.attrs[b].read(this, paramInt + 8, 
/* 1256 */                 readInt(paramInt + 4), arrayOfChar, m - 8, arrayOfLabel);
/* 1257 */             if (attribute1 != null) {
/* 1258 */               attribute1.next = attribute;
/* 1259 */               attribute = attribute1;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1264 */       paramInt += 6 + readInt(paramInt + 4);
/*      */     } 
/* 1266 */     paramInt += 2;
/*      */ 
/*      */     
/* 1269 */     if (i4 != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1275 */       context = paramContext;
/* 1276 */       context.offset = -1;
/* 1277 */       context.mode = 0;
/* 1278 */       context.localCount = 0;
/* 1279 */       context.localDiff = 0;
/* 1280 */       context.stackCount = 0;
/* 1281 */       context.local = new Object[j];
/* 1282 */       context.stack = new Object[i];
/* 1283 */       if (bool2) {
/* 1284 */         getImplicitFrame(paramContext);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       for (i7 = i4; i7 < i4 + i5 - 2; i7++) {
/* 1298 */         if (arrayOfByte[i7] == 8) {
/* 1299 */           int i8 = readUnsignedShort(i7 + 1);
/* 1300 */           if (i8 >= 0 && i8 < k && (
/* 1301 */             arrayOfByte[m + i8] & 0xFF) == 187) {
/* 1302 */             readLabel(i8, arrayOfLabel);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     paramInt = m;
/* 1311 */     while (paramInt < n) {
/* 1312 */       int i9, i10, i11, arrayOfInt[]; String str1; Handle handle; Label[] arrayOfLabel1; String str2; int i12; byte b5; String str3; Object[] arrayOfObject; byte b6; String str4, str5; i7 = paramInt - m;
/*      */ 
/*      */       
/* 1315 */       Label label = arrayOfLabel[i7];
/* 1316 */       if (label != null) {
/* 1317 */         paramMethodVisitor.visitLabel(label);
/* 1318 */         if ((paramContext.flags & 0x2) == 0 && label.line > 0) {
/* 1319 */           paramMethodVisitor.visitLineNumber(label.line, label);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1324 */       while (context != null && (context.offset == i7 || context.offset == -1)) {
/*      */ 
/*      */ 
/*      */         
/* 1328 */         if (context.offset != -1) {
/* 1329 */           if (!bool1 || bool2) {
/* 1330 */             paramMethodVisitor.visitFrame(-1, context.localCount, context.local, context.stackCount, context.stack);
/*      */           } else {
/*      */             
/* 1333 */             paramMethodVisitor.visitFrame(context.mode, context.localDiff, context.local, context.stackCount, context.stack);
/*      */           } 
/*      */         }
/*      */         
/* 1337 */         if (i6 > 0) {
/* 1338 */           i4 = readFrame(i4, bool1, bool2, context);
/* 1339 */           i6--; continue;
/*      */         } 
/* 1341 */         context = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1346 */       int i8 = arrayOfByte[paramInt] & 0xFF;
/* 1347 */       switch (ClassWriter.TYPE[i8]) {
/*      */         case 0:
/* 1349 */           paramMethodVisitor.visitInsn(i8);
/* 1350 */           paramInt++;
/*      */           break;
/*      */         case 4:
/* 1353 */           if (i8 > 54) {
/* 1354 */             i8 -= 59;
/* 1355 */             paramMethodVisitor.visitVarInsn(54 + (i8 >> 2), i8 & 0x3);
/*      */           } else {
/*      */             
/* 1358 */             i8 -= 26;
/* 1359 */             paramMethodVisitor.visitVarInsn(21 + (i8 >> 2), i8 & 0x3);
/*      */           } 
/* 1361 */           paramInt++;
/*      */           break;
/*      */         case 9:
/* 1364 */           paramMethodVisitor.visitJumpInsn(i8, arrayOfLabel[i7 + readShort(paramInt + 1)]);
/* 1365 */           paramInt += 3;
/*      */           break;
/*      */         case 10:
/* 1368 */           paramMethodVisitor.visitJumpInsn(i8 - 33, arrayOfLabel[i7 + readInt(paramInt + 1)]);
/* 1369 */           paramInt += 5;
/*      */           break;
/*      */         case 17:
/* 1372 */           i8 = arrayOfByte[paramInt + 1] & 0xFF;
/* 1373 */           if (i8 == 132) {
/* 1374 */             paramMethodVisitor.visitIincInsn(readUnsignedShort(paramInt + 2), readShort(paramInt + 4));
/* 1375 */             paramInt += 6; break;
/*      */           } 
/* 1377 */           paramMethodVisitor.visitVarInsn(i8, readUnsignedShort(paramInt + 2));
/* 1378 */           paramInt += 4;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 14:
/* 1383 */           paramInt = paramInt + 4 - (i7 & 0x3);
/*      */           
/* 1385 */           i9 = i7 + readInt(paramInt);
/* 1386 */           i10 = readInt(paramInt + 4);
/* 1387 */           i11 = readInt(paramInt + 8);
/* 1388 */           arrayOfLabel1 = new Label[i11 - i10 + 1];
/* 1389 */           paramInt += 12;
/* 1390 */           for (b5 = 0; b5 < arrayOfLabel1.length; b5++) {
/* 1391 */             arrayOfLabel1[b5] = arrayOfLabel[i7 + readInt(paramInt)];
/* 1392 */             paramInt += 4;
/*      */           } 
/* 1394 */           paramMethodVisitor.visitTableSwitchInsn(i10, i11, arrayOfLabel[i9], arrayOfLabel1);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 15:
/* 1399 */           paramInt = paramInt + 4 - (i7 & 0x3);
/*      */           
/* 1401 */           i9 = i7 + readInt(paramInt);
/* 1402 */           i10 = readInt(paramInt + 4);
/* 1403 */           arrayOfInt = new int[i10];
/* 1404 */           arrayOfLabel1 = new Label[i10];
/* 1405 */           paramInt += 8;
/* 1406 */           for (b5 = 0; b5 < i10; b5++) {
/* 1407 */             arrayOfInt[b5] = readInt(paramInt);
/* 1408 */             arrayOfLabel1[b5] = arrayOfLabel[i7 + readInt(paramInt + 4)];
/* 1409 */             paramInt += 8;
/*      */           } 
/* 1411 */           paramMethodVisitor.visitLookupSwitchInsn(arrayOfLabel[i9], arrayOfInt, arrayOfLabel1);
/*      */           break;
/*      */         
/*      */         case 3:
/* 1415 */           paramMethodVisitor.visitVarInsn(i8, arrayOfByte[paramInt + 1] & 0xFF);
/* 1416 */           paramInt += 2;
/*      */           break;
/*      */         case 1:
/* 1419 */           paramMethodVisitor.visitIntInsn(i8, arrayOfByte[paramInt + 1]);
/* 1420 */           paramInt += 2;
/*      */           break;
/*      */         case 2:
/* 1423 */           paramMethodVisitor.visitIntInsn(i8, readShort(paramInt + 1));
/* 1424 */           paramInt += 3;
/*      */           break;
/*      */         case 11:
/* 1427 */           paramMethodVisitor.visitLdcInsn(readConst(arrayOfByte[paramInt + 1] & 0xFF, arrayOfChar));
/* 1428 */           paramInt += 2;
/*      */           break;
/*      */         case 12:
/* 1431 */           paramMethodVisitor.visitLdcInsn(readConst(readUnsignedShort(paramInt + 1), arrayOfChar));
/* 1432 */           paramInt += 3;
/*      */           break;
/*      */         case 6:
/*      */         case 7:
/* 1436 */           i9 = this.items[readUnsignedShort(paramInt + 1)];
/* 1437 */           i10 = (arrayOfByte[i9 - 1] == 11) ? 1 : 0;
/* 1438 */           str1 = readClass(i9, arrayOfChar);
/* 1439 */           i9 = this.items[readUnsignedShort(i9 + 2)];
/* 1440 */           str2 = readUTF8(i9, arrayOfChar);
/* 1441 */           str3 = readUTF8(i9 + 2, arrayOfChar);
/* 1442 */           if (i8 < 182) {
/* 1443 */             paramMethodVisitor.visitFieldInsn(i8, str1, str2, str3);
/*      */           } else {
/* 1445 */             paramMethodVisitor.visitMethodInsn(i8, str1, str2, str3, i10);
/*      */           } 
/* 1447 */           if (i8 == 185) {
/* 1448 */             paramInt += 5; break;
/*      */           } 
/* 1450 */           paramInt += 3;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 8:
/* 1455 */           i9 = this.items[readUnsignedShort(paramInt + 1)];
/* 1456 */           i10 = paramContext.bootstrapMethods[readUnsignedShort(i9)];
/* 1457 */           handle = (Handle)readConst(readUnsignedShort(i10), arrayOfChar);
/* 1458 */           i12 = readUnsignedShort(i10 + 2);
/* 1459 */           arrayOfObject = new Object[i12];
/* 1460 */           i10 += 4;
/* 1461 */           for (b6 = 0; b6 < i12; b6++) {
/* 1462 */             arrayOfObject[b6] = readConst(readUnsignedShort(i10), arrayOfChar);
/* 1463 */             i10 += 2;
/*      */           } 
/* 1465 */           i9 = this.items[readUnsignedShort(i9 + 2)];
/* 1466 */           str4 = readUTF8(i9, arrayOfChar);
/* 1467 */           str5 = readUTF8(i9 + 2, arrayOfChar);
/* 1468 */           paramMethodVisitor.visitInvokeDynamicInsn(str4, str5, handle, arrayOfObject);
/* 1469 */           paramInt += 5;
/*      */           break;
/*      */         
/*      */         case 5:
/* 1473 */           paramMethodVisitor.visitTypeInsn(i8, readClass(paramInt + 1, arrayOfChar));
/* 1474 */           paramInt += 3;
/*      */           break;
/*      */         case 13:
/* 1477 */           paramMethodVisitor.visitIincInsn(arrayOfByte[paramInt + 1] & 0xFF, arrayOfByte[paramInt + 2]);
/* 1478 */           paramInt += 3;
/*      */           break;
/*      */         
/*      */         default:
/* 1482 */           paramMethodVisitor.visitMultiANewArrayInsn(readClass(paramInt + 1, arrayOfChar), arrayOfByte[paramInt + 3] & 0xFF);
/* 1483 */           paramInt += 4;
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1488 */       while (arrayOfInt1 != null && b1 < arrayOfInt1.length && b3 <= i7) {
/* 1489 */         if (b3 == i7) {
/* 1490 */           i9 = readAnnotationTarget(paramContext, arrayOfInt1[b1]);
/* 1491 */           readAnnotationValues(i9 + 2, arrayOfChar, true, paramMethodVisitor
/* 1492 */               .visitInsnAnnotation(paramContext.typeRef, paramContext.typePath, 
/* 1493 */                 readUTF8(i9, arrayOfChar), true));
/*      */         } 
/*      */         
/* 1496 */         b3 = (++b1 >= arrayOfInt1.length || readByte(arrayOfInt1[b1]) < 67) ? -1 : readUnsignedShort(arrayOfInt1[b1] + 1);
/*      */       } 
/* 1498 */       while (arrayOfInt2 != null && b2 < arrayOfInt2.length && b4 <= i7) {
/* 1499 */         if (b4 == i7) {
/* 1500 */           i9 = readAnnotationTarget(paramContext, arrayOfInt2[b2]);
/* 1501 */           readAnnotationValues(i9 + 2, arrayOfChar, true, paramMethodVisitor
/* 1502 */               .visitInsnAnnotation(paramContext.typeRef, paramContext.typePath, 
/* 1503 */                 readUTF8(i9, arrayOfChar), false));
/*      */         } 
/*      */ 
/*      */         
/* 1507 */         b4 = (++b2 >= arrayOfInt2.length || readByte(arrayOfInt2[b2]) < 67) ? -1 : readUnsignedShort(arrayOfInt2[b2] + 1);
/*      */       } 
/*      */     } 
/* 1510 */     if (arrayOfLabel[k] != null) {
/* 1511 */       paramMethodVisitor.visitLabel(arrayOfLabel[k]);
/*      */     }
/*      */ 
/*      */     
/* 1515 */     if ((paramContext.flags & 0x2) == 0 && i2 != 0) {
/* 1516 */       int[] arrayOfInt = null;
/* 1517 */       if (i3 != 0) {
/* 1518 */         paramInt = i3 + 2;
/* 1519 */         arrayOfInt = new int[readUnsignedShort(i3) * 3];
/* 1520 */         for (int i9 = arrayOfInt.length; i9 > 0; ) {
/* 1521 */           arrayOfInt[--i9] = paramInt + 6;
/* 1522 */           arrayOfInt[--i9] = readUnsignedShort(paramInt + 8);
/* 1523 */           arrayOfInt[--i9] = readUnsignedShort(paramInt);
/* 1524 */           paramInt += 10;
/*      */         } 
/*      */       } 
/* 1527 */       paramInt = i2 + 2;
/* 1528 */       for (int i8 = readUnsignedShort(i2); i8 > 0; i8--) {
/* 1529 */         int i9 = readUnsignedShort(paramInt);
/* 1530 */         int i10 = readUnsignedShort(paramInt + 2);
/* 1531 */         int i11 = readUnsignedShort(paramInt + 8);
/* 1532 */         String str = null;
/* 1533 */         if (arrayOfInt != null) {
/* 1534 */           for (byte b = 0; b < arrayOfInt.length; b += 3) {
/* 1535 */             if (arrayOfInt[b] == i9 && arrayOfInt[b + 1] == i11) {
/* 1536 */               str = readUTF8(arrayOfInt[b + 2], arrayOfChar);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/* 1541 */         paramMethodVisitor.visitLocalVariable(readUTF8(paramInt + 4, arrayOfChar), readUTF8(paramInt + 6, arrayOfChar), str, arrayOfLabel[i9], arrayOfLabel[i9 + i10], i11);
/*      */ 
/*      */         
/* 1544 */         paramInt += 10;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1549 */     if (arrayOfInt1 != null) {
/* 1550 */       for (i7 = 0; i7 < arrayOfInt1.length; i7++) {
/* 1551 */         if (readByte(arrayOfInt1[i7]) >> 1 == 32) {
/* 1552 */           int i8 = readAnnotationTarget(paramContext, arrayOfInt1[i7]);
/* 1553 */           i8 = readAnnotationValues(i8 + 2, arrayOfChar, true, paramMethodVisitor
/* 1554 */               .visitLocalVariableAnnotation(paramContext.typeRef, paramContext.typePath, paramContext.start, paramContext.end, paramContext.index, 
/*      */                 
/* 1556 */                 readUTF8(i8, arrayOfChar), true));
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1561 */     if (arrayOfInt2 != null) {
/* 1562 */       for (i7 = 0; i7 < arrayOfInt2.length; i7++) {
/* 1563 */         if (readByte(arrayOfInt2[i7]) >> 1 == 32) {
/* 1564 */           int i8 = readAnnotationTarget(paramContext, arrayOfInt2[i7]);
/* 1565 */           i8 = readAnnotationValues(i8 + 2, arrayOfChar, true, paramMethodVisitor
/* 1566 */               .visitLocalVariableAnnotation(paramContext.typeRef, paramContext.typePath, paramContext.start, paramContext.end, paramContext.index, 
/*      */                 
/* 1568 */                 readUTF8(i8, arrayOfChar), false));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1575 */     while (attribute != null) {
/* 1576 */       Attribute attribute1 = attribute.next;
/* 1577 */       attribute.next = null;
/* 1578 */       paramMethodVisitor.visitAttribute(attribute);
/* 1579 */       attribute = attribute1;
/*      */     } 
/*      */ 
/*      */     
/* 1583 */     paramMethodVisitor.visitMaxs(i, j);
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
/*      */   private int[] readTypeAnnotations(MethodVisitor paramMethodVisitor, Context paramContext, int paramInt, boolean paramBoolean) {
/* 1604 */     char[] arrayOfChar = paramContext.buffer;
/* 1605 */     int[] arrayOfInt = new int[readUnsignedShort(paramInt)];
/* 1606 */     paramInt += 2;
/* 1607 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1608 */       arrayOfInt[b] = paramInt;
/* 1609 */       int i = readInt(paramInt);
/* 1610 */       switch (i >>> 24) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 22:
/* 1614 */           paramInt += 2;
/*      */           break;
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/* 1619 */           paramInt++;
/*      */           break;
/*      */         case 64:
/*      */         case 65:
/* 1623 */           for (j = readUnsignedShort(paramInt + 1); j > 0; j--) {
/* 1624 */             int k = readUnsignedShort(paramInt + 3);
/* 1625 */             int m = readUnsignedShort(paramInt + 5);
/* 1626 */             readLabel(k, paramContext.labels);
/* 1627 */             readLabel(k + m, paramContext.labels);
/* 1628 */             paramInt += 6;
/*      */           } 
/* 1630 */           paramInt += 3;
/*      */           break;
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/* 1637 */           paramInt += 4;
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/* 1649 */           paramInt += 3;
/*      */           break;
/*      */       } 
/* 1652 */       int j = readByte(paramInt);
/* 1653 */       if (i >>> 24 == 66) {
/* 1654 */         TypePath typePath = (j == 0) ? null : new TypePath(this.b, paramInt);
/* 1655 */         paramInt += 1 + 2 * j;
/* 1656 */         paramInt = readAnnotationValues(paramInt + 2, arrayOfChar, true, paramMethodVisitor
/* 1657 */             .visitTryCatchAnnotation(i, typePath, 
/* 1658 */               readUTF8(paramInt, arrayOfChar), paramBoolean));
/*      */       } else {
/* 1660 */         paramInt = readAnnotationValues(paramInt + 3 + 2 * j, arrayOfChar, true, null);
/*      */       } 
/*      */     } 
/* 1663 */     return arrayOfInt;
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
/*      */   private int readAnnotationTarget(Context paramContext, int paramInt) {
/*      */     byte b;
/* 1681 */     int i = readInt(paramInt);
/* 1682 */     switch (i >>> 24) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 22:
/* 1686 */         i &= 0xFFFF0000;
/* 1687 */         paramInt += 2;
/*      */         break;
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/* 1692 */         i &= 0xFF000000;
/* 1693 */         paramInt++;
/*      */         break;
/*      */       case 64:
/*      */       case 65:
/* 1697 */         i &= 0xFF000000;
/* 1698 */         j = readUnsignedShort(paramInt + 1);
/* 1699 */         paramContext.start = new Label[j];
/* 1700 */         paramContext.end = new Label[j];
/* 1701 */         paramContext.index = new int[j];
/* 1702 */         paramInt += 3;
/* 1703 */         for (b = 0; b < j; b++) {
/* 1704 */           int k = readUnsignedShort(paramInt);
/* 1705 */           int m = readUnsignedShort(paramInt + 2);
/* 1706 */           paramContext.start[b] = readLabel(k, paramContext.labels);
/* 1707 */           paramContext.end[b] = readLabel(k + m, paramContext.labels);
/* 1708 */           paramContext.index[b] = readUnsignedShort(paramInt + 4);
/* 1709 */           paramInt += 6;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/* 1718 */         i &= 0xFF0000FF;
/* 1719 */         paramInt += 4;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1731 */         i &= (i >>> 24 < 67) ? -256 : -16777216;
/* 1732 */         paramInt += 3;
/*      */         break;
/*      */     } 
/* 1735 */     int j = readByte(paramInt);
/* 1736 */     paramContext.typeRef = i;
/* 1737 */     paramContext.typePath = (j == 0) ? null : new TypePath(this.b, paramInt);
/* 1738 */     return paramInt + 1 + 2 * j;
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
/*      */   private void readParameterAnnotations(MethodVisitor paramMethodVisitor, Context paramContext, int paramInt, boolean paramBoolean) {
/* 1757 */     int i = this.b[paramInt++] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1764 */     int j = (Type.getArgumentTypes(paramContext.desc)).length - i;
/*      */     byte b;
/* 1766 */     for (b = 0; b < j; b++) {
/*      */       
/* 1768 */       AnnotationVisitor annotationVisitor = paramMethodVisitor.visitParameterAnnotation(b, "Ljava/lang/Synthetic;", false);
/* 1769 */       if (annotationVisitor != null) {
/* 1770 */         annotationVisitor.visitEnd();
/*      */       }
/*      */     } 
/* 1773 */     char[] arrayOfChar = paramContext.buffer;
/* 1774 */     for (; b < i + j; b++) {
/* 1775 */       int k = readUnsignedShort(paramInt);
/* 1776 */       paramInt += 2;
/* 1777 */       for (; k > 0; k--) {
/* 1778 */         AnnotationVisitor annotationVisitor = paramMethodVisitor.visitParameterAnnotation(b, readUTF8(paramInt, arrayOfChar), paramBoolean);
/* 1779 */         paramInt = readAnnotationValues(paramInt + 2, arrayOfChar, true, annotationVisitor);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readAnnotationValues(int paramInt, char[] paramArrayOfchar, boolean paramBoolean, AnnotationVisitor paramAnnotationVisitor) {
/* 1803 */     int i = readUnsignedShort(paramInt);
/* 1804 */     paramInt += 2;
/* 1805 */     if (paramBoolean) {
/* 1806 */       for (; i > 0; i--) {
/* 1807 */         paramInt = readAnnotationValue(paramInt + 2, paramArrayOfchar, readUTF8(paramInt, paramArrayOfchar), paramAnnotationVisitor);
/*      */       }
/*      */     } else {
/* 1810 */       for (; i > 0; i--) {
/* 1811 */         paramInt = readAnnotationValue(paramInt, paramArrayOfchar, null, paramAnnotationVisitor);
/*      */       }
/*      */     } 
/* 1814 */     if (paramAnnotationVisitor != null) {
/* 1815 */       paramAnnotationVisitor.visitEnd();
/*      */     }
/* 1817 */     return paramInt;
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
/*      */   private int readAnnotationValue(int paramInt, char[] paramArrayOfchar, String paramString, AnnotationVisitor paramAnnotationVisitor) {
/*      */     byte b;
/*      */     int i;
/*      */     byte[] arrayOfByte;
/*      */     boolean[] arrayOfBoolean;
/*      */     short[] arrayOfShort;
/*      */     char[] arrayOfChar;
/*      */     int[] arrayOfInt;
/*      */     long[] arrayOfLong;
/*      */     float[] arrayOfFloat;
/*      */     double[] arrayOfDouble;
/* 1839 */     if (paramAnnotationVisitor == null) {
/* 1840 */       switch (this.b[paramInt] & 0xFF) {
/*      */         case 101:
/* 1842 */           return paramInt + 5;
/*      */         case 64:
/* 1844 */           return readAnnotationValues(paramInt + 3, paramArrayOfchar, true, null);
/*      */         case 91:
/* 1846 */           return readAnnotationValues(paramInt + 1, paramArrayOfchar, false, null);
/*      */       } 
/* 1848 */       return paramInt + 3;
/*      */     } 
/*      */     
/* 1851 */     switch (this.b[paramInt++] & 0xFF) {
/*      */       case 68:
/*      */       case 70:
/*      */       case 73:
/*      */       case 74:
/* 1856 */         paramAnnotationVisitor.visit(paramString, readConst(readUnsignedShort(paramInt), paramArrayOfchar));
/* 1857 */         paramInt += 2;
/*      */         break;
/*      */       case 66:
/* 1860 */         paramAnnotationVisitor.visit(paramString, 
/* 1861 */             Byte.valueOf((byte)readInt(this.items[readUnsignedShort(paramInt)])));
/* 1862 */         paramInt += 2;
/*      */         break;
/*      */       case 90:
/* 1865 */         paramAnnotationVisitor.visit(paramString, 
/* 1866 */             (readInt(this.items[readUnsignedShort(paramInt)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
/*      */         
/* 1868 */         paramInt += 2;
/*      */         break;
/*      */       case 83:
/* 1871 */         paramAnnotationVisitor.visit(paramString, 
/* 1872 */             Short.valueOf((short)readInt(this.items[readUnsignedShort(paramInt)])));
/* 1873 */         paramInt += 2;
/*      */         break;
/*      */       case 67:
/* 1876 */         paramAnnotationVisitor.visit(paramString, 
/* 1877 */             Character.valueOf((char)readInt(this.items[readUnsignedShort(paramInt)])));
/* 1878 */         paramInt += 2;
/*      */         break;
/*      */       case 115:
/* 1881 */         paramAnnotationVisitor.visit(paramString, readUTF8(paramInt, paramArrayOfchar));
/* 1882 */         paramInt += 2;
/*      */         break;
/*      */       case 101:
/* 1885 */         paramAnnotationVisitor.visitEnum(paramString, readUTF8(paramInt, paramArrayOfchar), readUTF8(paramInt + 2, paramArrayOfchar));
/* 1886 */         paramInt += 4;
/*      */         break;
/*      */       case 99:
/* 1889 */         paramAnnotationVisitor.visit(paramString, Type.getType(readUTF8(paramInt, paramArrayOfchar)));
/* 1890 */         paramInt += 2;
/*      */         break;
/*      */       case 64:
/* 1893 */         paramInt = readAnnotationValues(paramInt + 2, paramArrayOfchar, true, paramAnnotationVisitor
/* 1894 */             .visitAnnotation(paramString, readUTF8(paramInt, paramArrayOfchar)));
/*      */         break;
/*      */       case 91:
/* 1897 */         i = readUnsignedShort(paramInt);
/* 1898 */         paramInt += 2;
/* 1899 */         if (i == 0) {
/* 1900 */           return readAnnotationValues(paramInt - 2, paramArrayOfchar, false, paramAnnotationVisitor
/* 1901 */               .visitArray(paramString));
/*      */         }
/* 1903 */         switch (this.b[paramInt++] & 0xFF) {
/*      */           case 66:
/* 1905 */             arrayOfByte = new byte[i];
/* 1906 */             for (b = 0; b < i; b++) {
/* 1907 */               arrayOfByte[b] = (byte)readInt(this.items[readUnsignedShort(paramInt)]);
/* 1908 */               paramInt += 3;
/*      */             } 
/* 1910 */             paramAnnotationVisitor.visit(paramString, arrayOfByte);
/* 1911 */             paramInt--;
/*      */             break;
/*      */           case 90:
/* 1914 */             arrayOfBoolean = new boolean[i];
/* 1915 */             for (b = 0; b < i; b++) {
/* 1916 */               arrayOfBoolean[b] = (readInt(this.items[readUnsignedShort(paramInt)]) != 0);
/* 1917 */               paramInt += 3;
/*      */             } 
/* 1919 */             paramAnnotationVisitor.visit(paramString, arrayOfBoolean);
/* 1920 */             paramInt--;
/*      */             break;
/*      */           case 83:
/* 1923 */             arrayOfShort = new short[i];
/* 1924 */             for (b = 0; b < i; b++) {
/* 1925 */               arrayOfShort[b] = (short)readInt(this.items[readUnsignedShort(paramInt)]);
/* 1926 */               paramInt += 3;
/*      */             } 
/* 1928 */             paramAnnotationVisitor.visit(paramString, arrayOfShort);
/* 1929 */             paramInt--;
/*      */             break;
/*      */           case 67:
/* 1932 */             arrayOfChar = new char[i];
/* 1933 */             for (b = 0; b < i; b++) {
/* 1934 */               arrayOfChar[b] = (char)readInt(this.items[readUnsignedShort(paramInt)]);
/* 1935 */               paramInt += 3;
/*      */             } 
/* 1937 */             paramAnnotationVisitor.visit(paramString, arrayOfChar);
/* 1938 */             paramInt--;
/*      */             break;
/*      */           case 73:
/* 1941 */             arrayOfInt = new int[i];
/* 1942 */             for (b = 0; b < i; b++) {
/* 1943 */               arrayOfInt[b] = readInt(this.items[readUnsignedShort(paramInt)]);
/* 1944 */               paramInt += 3;
/*      */             } 
/* 1946 */             paramAnnotationVisitor.visit(paramString, arrayOfInt);
/* 1947 */             paramInt--;
/*      */             break;
/*      */           case 74:
/* 1950 */             arrayOfLong = new long[i];
/* 1951 */             for (b = 0; b < i; b++) {
/* 1952 */               arrayOfLong[b] = readLong(this.items[readUnsignedShort(paramInt)]);
/* 1953 */               paramInt += 3;
/*      */             } 
/* 1955 */             paramAnnotationVisitor.visit(paramString, arrayOfLong);
/* 1956 */             paramInt--;
/*      */             break;
/*      */           case 70:
/* 1959 */             arrayOfFloat = new float[i];
/* 1960 */             for (b = 0; b < i; b++) {
/* 1961 */               arrayOfFloat[b] = 
/* 1962 */                 Float.intBitsToFloat(readInt(this.items[readUnsignedShort(paramInt)]));
/* 1963 */               paramInt += 3;
/*      */             } 
/* 1965 */             paramAnnotationVisitor.visit(paramString, arrayOfFloat);
/* 1966 */             paramInt--;
/*      */             break;
/*      */           case 68:
/* 1969 */             arrayOfDouble = new double[i];
/* 1970 */             for (b = 0; b < i; b++) {
/* 1971 */               arrayOfDouble[b] = 
/* 1972 */                 Double.longBitsToDouble(readLong(this.items[readUnsignedShort(paramInt)]));
/* 1973 */               paramInt += 3;
/*      */             } 
/* 1975 */             paramAnnotationVisitor.visit(paramString, arrayOfDouble);
/* 1976 */             paramInt--;
/*      */             break;
/*      */         } 
/* 1979 */         paramInt = readAnnotationValues(paramInt - 3, paramArrayOfchar, false, paramAnnotationVisitor.visitArray(paramString));
/*      */         break;
/*      */     } 
/* 1982 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getImplicitFrame(Context paramContext) {
/* 1993 */     String str = paramContext.desc;
/* 1994 */     Object[] arrayOfObject = paramContext.local;
/* 1995 */     byte b1 = 0;
/* 1996 */     if ((paramContext.access & 0x8) == 0) {
/* 1997 */       if ("<init>".equals(paramContext.name)) {
/* 1998 */         arrayOfObject[b1++] = Opcodes.UNINITIALIZED_THIS;
/*      */       } else {
/* 2000 */         arrayOfObject[b1++] = readClass(this.header + 2, paramContext.buffer);
/*      */       } 
/*      */     }
/* 2003 */     byte b2 = 1;
/*      */     while (true) {
/* 2005 */       byte b = b2;
/* 2006 */       switch (str.charAt(b2++)) {
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'I':
/*      */         case 'S':
/*      */         case 'Z':
/* 2012 */           arrayOfObject[b1++] = Opcodes.INTEGER;
/*      */           continue;
/*      */         case 'F':
/* 2015 */           arrayOfObject[b1++] = Opcodes.FLOAT;
/*      */           continue;
/*      */         case 'J':
/* 2018 */           arrayOfObject[b1++] = Opcodes.LONG;
/*      */           continue;
/*      */         case 'D':
/* 2021 */           arrayOfObject[b1++] = Opcodes.DOUBLE;
/*      */           continue;
/*      */         case '[':
/* 2024 */           while (str.charAt(b2) == '[') {
/* 2025 */             b2++;
/*      */           }
/* 2027 */           if (str.charAt(b2) == 'L') {
/* 2028 */             b2++;
/* 2029 */             while (str.charAt(b2) != ';') {
/* 2030 */               b2++;
/*      */             }
/*      */           } 
/* 2033 */           arrayOfObject[b1++] = str.substring(b, ++b2);
/*      */           continue;
/*      */         case 'L':
/* 2036 */           while (str.charAt(b2) != ';') {
/* 2037 */             b2++;
/*      */           }
/* 2039 */           arrayOfObject[b1++] = str.substring(b + 1, b2++);
/*      */           continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/* 2045 */     paramContext.localCount = b1;
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
/*      */   private int readFrame(int paramInt, boolean paramBoolean1, boolean paramBoolean2, Context paramContext) {
/*      */     char c;
/*      */     int i;
/* 2064 */     char[] arrayOfChar = paramContext.buffer;
/* 2065 */     Label[] arrayOfLabel = paramContext.labels;
/*      */ 
/*      */     
/* 2068 */     if (paramBoolean1) {
/* 2069 */       c = this.b[paramInt++] & 0xFF;
/*      */     } else {
/* 2071 */       c = 'ÿ';
/* 2072 */       paramContext.offset = -1;
/*      */     } 
/* 2074 */     paramContext.localDiff = 0;
/* 2075 */     if (c < '@') {
/* 2076 */       i = c;
/* 2077 */       paramContext.mode = 3;
/* 2078 */       paramContext.stackCount = 0;
/* 2079 */     } else if (c < '') {
/* 2080 */       i = c - 64;
/* 2081 */       paramInt = readFrameType(paramContext.stack, 0, paramInt, arrayOfChar, arrayOfLabel);
/* 2082 */       paramContext.mode = 4;
/* 2083 */       paramContext.stackCount = 1;
/*      */     } else {
/* 2085 */       i = readUnsignedShort(paramInt);
/* 2086 */       paramInt += 2;
/* 2087 */       if (c == '÷') {
/* 2088 */         paramInt = readFrameType(paramContext.stack, 0, paramInt, arrayOfChar, arrayOfLabel);
/* 2089 */         paramContext.mode = 4;
/* 2090 */         paramContext.stackCount = 1;
/* 2091 */       } else if (c >= 'ø' && c < 'û') {
/*      */         
/* 2093 */         paramContext.mode = 2;
/* 2094 */         paramContext.localDiff = 251 - c;
/* 2095 */         paramContext.localCount -= paramContext.localDiff;
/* 2096 */         paramContext.stackCount = 0;
/* 2097 */       } else if (c == 'û') {
/* 2098 */         paramContext.mode = 3;
/* 2099 */         paramContext.stackCount = 0;
/* 2100 */       } else if (c < 'ÿ') {
/* 2101 */         byte b = paramBoolean2 ? paramContext.localCount : 0;
/* 2102 */         for (int j = c - 251; j > 0; j--) {
/* 2103 */           paramInt = readFrameType(paramContext.local, b++, paramInt, arrayOfChar, arrayOfLabel);
/*      */         }
/*      */         
/* 2106 */         paramContext.mode = 1;
/* 2107 */         paramContext.localDiff = c - 251;
/* 2108 */         paramContext.localCount += paramContext.localDiff;
/* 2109 */         paramContext.stackCount = 0;
/*      */       } else {
/* 2111 */         paramContext.mode = 0;
/* 2112 */         int j = readUnsignedShort(paramInt);
/* 2113 */         paramInt += 2;
/* 2114 */         paramContext.localDiff = j;
/* 2115 */         paramContext.localCount = j; byte b;
/* 2116 */         for (b = 0; j > 0; j--) {
/* 2117 */           paramInt = readFrameType(paramContext.local, b++, paramInt, arrayOfChar, arrayOfLabel);
/*      */         }
/*      */         
/* 2120 */         j = readUnsignedShort(paramInt);
/* 2121 */         paramInt += 2;
/* 2122 */         paramContext.stackCount = j;
/* 2123 */         for (b = 0; j > 0; j--) {
/* 2124 */           paramInt = readFrameType(paramContext.stack, b++, paramInt, arrayOfChar, arrayOfLabel);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2129 */     paramContext.offset += i + 1;
/* 2130 */     readLabel(paramContext.offset, arrayOfLabel);
/* 2131 */     return paramInt;
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
/*      */   private int readFrameType(Object[] paramArrayOfObject, int paramInt1, int paramInt2, char[] paramArrayOfchar, Label[] paramArrayOfLabel) {
/* 2155 */     int i = this.b[paramInt2++] & 0xFF;
/* 2156 */     switch (i)
/*      */     { case 0:
/* 2158 */         paramArrayOfObject[paramInt1] = Opcodes.TOP;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2186 */         return paramInt2;case 1: paramArrayOfObject[paramInt1] = Opcodes.INTEGER; return paramInt2;case 2: paramArrayOfObject[paramInt1] = Opcodes.FLOAT; return paramInt2;case 3: paramArrayOfObject[paramInt1] = Opcodes.DOUBLE; return paramInt2;case 4: paramArrayOfObject[paramInt1] = Opcodes.LONG; return paramInt2;case 5: paramArrayOfObject[paramInt1] = Opcodes.NULL; return paramInt2;case 6: paramArrayOfObject[paramInt1] = Opcodes.UNINITIALIZED_THIS; return paramInt2;case 7: paramArrayOfObject[paramInt1] = readClass(paramInt2, paramArrayOfchar); paramInt2 += 2; return paramInt2; }  paramArrayOfObject[paramInt1] = readLabel(readUnsignedShort(paramInt2), paramArrayOfLabel); paramInt2 += 2; return paramInt2;
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
/*      */   protected Label readLabel(int paramInt, Label[] paramArrayOfLabel) {
/* 2203 */     if (paramArrayOfLabel[paramInt] == null) {
/* 2204 */       paramArrayOfLabel[paramInt] = new Label();
/*      */     }
/* 2206 */     return paramArrayOfLabel[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getAttributes() {
/* 2216 */     int i = this.header + 8 + readUnsignedShort(this.header + 6) * 2;
/*      */     int j;
/* 2218 */     for (j = readUnsignedShort(i); j > 0; j--) {
/* 2219 */       for (int k = readUnsignedShort(i + 8); k > 0; k--) {
/* 2220 */         i += 6 + readInt(i + 12);
/*      */       }
/* 2222 */       i += 8;
/*      */     } 
/* 2224 */     i += 2;
/* 2225 */     for (j = readUnsignedShort(i); j > 0; j--) {
/* 2226 */       for (int k = readUnsignedShort(i + 8); k > 0; k--) {
/* 2227 */         i += 6 + readInt(i + 12);
/*      */       }
/* 2229 */       i += 8;
/*      */     } 
/*      */     
/* 2232 */     return i + 2;
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
/*      */   private Attribute readAttribute(Attribute[] paramArrayOfAttribute, String paramString, int paramInt1, int paramInt2, char[] paramArrayOfchar, int paramInt3, Label[] paramArrayOfLabel) {
/* 2271 */     for (byte b = 0; b < paramArrayOfAttribute.length; b++) {
/* 2272 */       if ((paramArrayOfAttribute[b]).type.equals(paramString)) {
/* 2273 */         return paramArrayOfAttribute[b].read(this, paramInt1, paramInt2, paramArrayOfchar, paramInt3, paramArrayOfLabel);
/*      */       }
/*      */     } 
/* 2276 */     return (new Attribute(paramString)).read(this, paramInt1, paramInt2, null, -1, null);
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
/*      */   public int getItemCount() {
/* 2289 */     return this.items.length;
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
/*      */   public int getItem(int paramInt) {
/* 2303 */     return this.items[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxStringLength() {
/* 2314 */     return this.maxStringLength;
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
/*      */   public int readByte(int paramInt) {
/* 2327 */     return this.b[paramInt] & 0xFF;
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
/*      */   public int readUnsignedShort(int paramInt) {
/* 2340 */     byte[] arrayOfByte = this.b;
/* 2341 */     return (arrayOfByte[paramInt] & 0xFF) << 8 | arrayOfByte[paramInt + 1] & 0xFF;
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
/*      */   public short readShort(int paramInt) {
/* 2354 */     byte[] arrayOfByte = this.b;
/* 2355 */     return (short)((arrayOfByte[paramInt] & 0xFF) << 8 | arrayOfByte[paramInt + 1] & 0xFF);
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
/*      */   public int readInt(int paramInt) {
/* 2368 */     byte[] arrayOfByte = this.b;
/* 2369 */     return (arrayOfByte[paramInt] & 0xFF) << 24 | (arrayOfByte[paramInt + 1] & 0xFF) << 16 | (arrayOfByte[paramInt + 2] & 0xFF) << 8 | arrayOfByte[paramInt + 3] & 0xFF;
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
/*      */   public long readLong(int paramInt) {
/* 2383 */     long l1 = readInt(paramInt);
/* 2384 */     long l2 = readInt(paramInt + 4) & 0xFFFFFFFFL;
/* 2385 */     return l1 << 32L | l2;
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
/*      */   public String readUTF8(int paramInt, char[] paramArrayOfchar) {
/* 2402 */     int i = readUnsignedShort(paramInt);
/* 2403 */     if (paramInt == 0 || i == 0) {
/* 2404 */       return null;
/*      */     }
/* 2406 */     String str = this.strings[i];
/* 2407 */     if (str != null) {
/* 2408 */       return str;
/*      */     }
/* 2410 */     paramInt = this.items[i];
/* 2411 */     this.strings[i] = readUTF(paramInt + 2, readUnsignedShort(paramInt), paramArrayOfchar); return readUTF(paramInt + 2, readUnsignedShort(paramInt), paramArrayOfchar);
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
/*      */   private String readUTF(int paramInt1, int paramInt2, char[] paramArrayOfchar) {
/* 2427 */     int i = paramInt1 + paramInt2;
/* 2428 */     byte[] arrayOfByte = this.b;
/* 2429 */     byte b1 = 0;
/*      */     
/* 2431 */     byte b2 = 0;
/* 2432 */     char c = Character.MIN_VALUE;
/* 2433 */     while (paramInt1 < i) {
/* 2434 */       int j; byte b = arrayOfByte[paramInt1++];
/* 2435 */       switch (b2) {
/*      */         case false:
/* 2437 */           j = b & 0xFF;
/* 2438 */           if (j < 128) {
/* 2439 */             paramArrayOfchar[b1++] = (char)j; continue;
/* 2440 */           }  if (j < 224 && j > 191) {
/* 2441 */             c = (char)(j & 0x1F);
/* 2442 */             b2 = 1; continue;
/*      */           } 
/* 2444 */           c = (char)(j & 0xF);
/* 2445 */           b2 = 2;
/*      */ 
/*      */ 
/*      */         
/*      */         case true:
/* 2450 */           paramArrayOfchar[b1++] = (char)(c << 6 | j & 0x3F);
/* 2451 */           b2 = 0;
/*      */ 
/*      */         
/*      */         case true:
/* 2455 */           c = (char)(c << 6 | j & 0x3F);
/* 2456 */           b2 = 1;
/*      */       } 
/*      */     
/*      */     } 
/* 2460 */     return new String(paramArrayOfchar, 0, b1);
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
/*      */   public String readClass(int paramInt, char[] paramArrayOfchar) {
/* 2480 */     return readUTF8(this.items[readUnsignedShort(paramInt)], paramArrayOfchar);
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
/*      */   public Object readConst(int paramInt, char[] paramArrayOfchar) {
/* 2498 */     int i = this.items[paramInt];
/* 2499 */     switch (this.b[i - 1]) {
/*      */       case 3:
/* 2501 */         return Integer.valueOf(readInt(i));
/*      */       case 4:
/* 2503 */         return Float.valueOf(Float.intBitsToFloat(readInt(i)));
/*      */       case 5:
/* 2505 */         return Long.valueOf(readLong(i));
/*      */       case 6:
/* 2507 */         return Double.valueOf(Double.longBitsToDouble(readLong(i)));
/*      */       case 7:
/* 2509 */         return Type.getObjectType(readUTF8(i, paramArrayOfchar));
/*      */       case 8:
/* 2511 */         return readUTF8(i, paramArrayOfchar);
/*      */       case 16:
/* 2513 */         return Type.getMethodType(readUTF8(i, paramArrayOfchar));
/*      */     } 
/* 2515 */     int j = readByte(i);
/* 2516 */     int[] arrayOfInt = this.items;
/* 2517 */     int k = arrayOfInt[readUnsignedShort(i + 1)];
/* 2518 */     String str1 = readClass(k, paramArrayOfchar);
/* 2519 */     k = arrayOfInt[readUnsignedShort(k + 2)];
/* 2520 */     String str2 = readUTF8(k, paramArrayOfchar);
/* 2521 */     String str3 = readUTF8(k + 2, paramArrayOfchar);
/* 2522 */     return new Handle(j, str1, str2, str3);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/ClassReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */