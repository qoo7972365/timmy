/*      */ package com.sun.xml.internal.ws.org.objectweb.asm;
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
/*      */   public ClassReader(byte[] b) {
/*  183 */     this(b, 0, b.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(byte[] b, int off, int len) {
/*  194 */     this.b = b;
/*      */     
/*  196 */     this.items = new int[readUnsignedShort(off + 8)];
/*  197 */     int n = this.items.length;
/*  198 */     this.strings = new String[n];
/*  199 */     int max = 0;
/*  200 */     int index = off + 10;
/*  201 */     for (int i = 1; i < n; i++) {
/*  202 */       int size; this.items[i] = index + 1;
/*      */       
/*  204 */       switch (b[index]) {
/*      */         case 3:
/*      */         case 4:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*  211 */           size = 5;
/*      */           break;
/*      */         case 5:
/*      */         case 6:
/*  215 */           size = 9;
/*  216 */           i++;
/*      */           break;
/*      */         case 1:
/*  219 */           size = 3 + readUnsignedShort(index + 1);
/*  220 */           if (size > max) {
/*  221 */             max = size;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  227 */           size = 3;
/*      */           break;
/*      */       } 
/*  230 */       index += size;
/*      */     } 
/*  232 */     this.maxStringLength = max;
/*      */     
/*  234 */     this.header = index;
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
/*  247 */     return readUnsignedShort(this.header);
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
/*  259 */     return readClass(this.header + 2, new char[this.maxStringLength]);
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
/*  273 */     int n = this.items[readUnsignedShort(this.header + 4)];
/*  274 */     return (n == 0) ? null : readUTF8(n, new char[this.maxStringLength]);
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
/*  287 */     int index = this.header + 6;
/*  288 */     int n = readUnsignedShort(index);
/*  289 */     String[] interfaces = new String[n];
/*  290 */     if (n > 0) {
/*  291 */       char[] buf = new char[this.maxStringLength];
/*  292 */       for (int i = 0; i < n; i++) {
/*  293 */         index += 2;
/*  294 */         interfaces[i] = readClass(index, buf);
/*      */       } 
/*      */     } 
/*  297 */     return interfaces;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void copyPool(ClassWriter classWriter) {
/*  307 */     char[] buf = new char[this.maxStringLength];
/*  308 */     int ll = this.items.length;
/*  309 */     Item[] items2 = new Item[ll];
/*  310 */     for (int i = 1; i < ll; i++) {
/*  311 */       int nameType; String s; int index = this.items[i];
/*  312 */       int tag = this.b[index - 1];
/*  313 */       Item item = new Item(i);
/*      */       
/*  315 */       switch (tag) {
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*  319 */           nameType = this.items[readUnsignedShort(index + 2)];
/*  320 */           item.set(tag, 
/*  321 */               readClass(index, buf), 
/*  322 */               readUTF8(nameType, buf), 
/*  323 */               readUTF8(nameType + 2, buf));
/*      */           break;
/*      */         
/*      */         case 3:
/*  327 */           item.set(readInt(index));
/*      */           break;
/*      */         
/*      */         case 4:
/*  331 */           item.set(Float.intBitsToFloat(readInt(index)));
/*      */           break;
/*      */         
/*      */         case 12:
/*  335 */           item.set(tag, 
/*  336 */               readUTF8(index, buf), 
/*  337 */               readUTF8(index + 2, buf), null);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 5:
/*  342 */           item.set(readLong(index));
/*  343 */           i++;
/*      */           break;
/*      */         
/*      */         case 6:
/*  347 */           item.set(Double.longBitsToDouble(readLong(index)));
/*  348 */           i++;
/*      */           break;
/*      */         
/*      */         case 1:
/*  352 */           s = this.strings[i];
/*  353 */           if (s == null) {
/*  354 */             index = this.items[i];
/*  355 */             s = this.strings[i] = readUTF(index + 2, 
/*  356 */                 readUnsignedShort(index), buf);
/*      */           } 
/*      */           
/*  359 */           item.set(tag, s, null, null);
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         default:
/*  366 */           item.set(tag, readUTF8(index, buf), null, null);
/*      */           break;
/*      */       } 
/*      */       
/*  370 */       int index2 = item.hashCode % items2.length;
/*  371 */       item.next = items2[index2];
/*  372 */       items2[index2] = item;
/*      */     } 
/*      */     
/*  375 */     int off = this.items[1] - 1;
/*  376 */     classWriter.pool.putByteArray(this.b, off, this.header - off);
/*  377 */     classWriter.items = items2;
/*  378 */     classWriter.threshold = (int)(0.75D * ll);
/*  379 */     classWriter.index = ll;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(InputStream is) throws IOException {
/*  389 */     this(readClass(is));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassReader(String name) throws IOException {
/*  399 */     this(ClassLoader.getSystemResourceAsStream(name.replace('.', '/') + ".class"));
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
/*      */   private static byte[] readClass(InputStream is) throws IOException {
/*  411 */     if (is == null) {
/*  412 */       throw new IOException("Class not found");
/*      */     }
/*  414 */     byte[] b = new byte[is.available()];
/*  415 */     int len = 0;
/*      */     while (true) {
/*  417 */       int n = is.read(b, len, b.length - len);
/*  418 */       if (n == -1) {
/*  419 */         if (len < b.length) {
/*  420 */           byte[] c = new byte[len];
/*  421 */           System.arraycopy(b, 0, c, 0, len);
/*  422 */           b = c;
/*      */         } 
/*  424 */         return b;
/*      */       } 
/*  426 */       len += n;
/*  427 */       if (len == b.length) {
/*  428 */         byte[] c = new byte[b.length + 1000];
/*  429 */         System.arraycopy(b, 0, c, 0, len);
/*  430 */         b = c;
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
/*      */   public void accept(ClassVisitor classVisitor, int flags) {
/*  450 */     accept(classVisitor, new Attribute[0], flags);
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
/*      */   public void accept(ClassVisitor classVisitor, Attribute[] attrs, int flags) {
/*  476 */     byte[] b = this.b;
/*  477 */     char[] c = new char[this.maxStringLength];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     int anns = 0;
/*  488 */     int ianns = 0;
/*  489 */     Attribute cattrs = null;
/*      */ 
/*      */     
/*  492 */     int u = this.header;
/*  493 */     int access = readUnsignedShort(u);
/*  494 */     String name = readClass(u + 2, c);
/*  495 */     int v = this.items[readUnsignedShort(u + 4)];
/*  496 */     String superClassName = (v == 0) ? null : readUTF8(v, c);
/*  497 */     String[] implementedItfs = new String[readUnsignedShort(u + 6)];
/*  498 */     int w = 0;
/*  499 */     u += 8; int i;
/*  500 */     for (i = 0; i < implementedItfs.length; i++) {
/*  501 */       implementedItfs[i] = readClass(u, c);
/*  502 */       u += 2;
/*      */     } 
/*      */     
/*  505 */     boolean skipCode = ((flags & 0x1) != 0);
/*  506 */     boolean skipDebug = ((flags & 0x2) != 0);
/*  507 */     boolean unzip = ((flags & 0x8) != 0);
/*      */ 
/*      */     
/*  510 */     v = u;
/*  511 */     i = readUnsignedShort(v);
/*  512 */     v += 2;
/*  513 */     for (; i > 0; i--) {
/*  514 */       int j = readUnsignedShort(v + 6);
/*  515 */       v += 8;
/*  516 */       for (; j > 0; j--) {
/*  517 */         v += 6 + readInt(v + 2);
/*      */       }
/*      */     } 
/*  520 */     i = readUnsignedShort(v);
/*  521 */     v += 2;
/*  522 */     for (; i > 0; i--) {
/*  523 */       int j = readUnsignedShort(v + 6);
/*  524 */       v += 8;
/*  525 */       for (; j > 0; j--) {
/*  526 */         v += 6 + readInt(v + 2);
/*      */       }
/*      */     } 
/*      */     
/*  530 */     String signature = null;
/*  531 */     String sourceFile = null;
/*  532 */     String sourceDebug = null;
/*  533 */     String enclosingOwner = null;
/*  534 */     String enclosingName = null;
/*  535 */     String enclosingDesc = null;
/*      */     
/*  537 */     i = readUnsignedShort(v);
/*  538 */     v += 2;
/*  539 */     for (; i > 0; i--) {
/*  540 */       String attrName = readUTF8(v, c);
/*      */ 
/*      */       
/*  543 */       if ("SourceFile".equals(attrName)) {
/*  544 */         sourceFile = readUTF8(v + 6, c);
/*  545 */       } else if ("InnerClasses".equals(attrName)) {
/*  546 */         w = v + 6;
/*  547 */       } else if ("EnclosingMethod".equals(attrName)) {
/*  548 */         enclosingOwner = readClass(v + 6, c);
/*  549 */         int item = readUnsignedShort(v + 8);
/*  550 */         if (item != 0) {
/*  551 */           enclosingName = readUTF8(this.items[item], c);
/*  552 */           enclosingDesc = readUTF8(this.items[item] + 2, c);
/*      */         } 
/*  554 */       } else if ("Signature".equals(attrName)) {
/*  555 */         signature = readUTF8(v + 6, c);
/*  556 */       } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  557 */         anns = v + 6;
/*  558 */       } else if ("Deprecated".equals(attrName)) {
/*  559 */         access |= 0x20000;
/*  560 */       } else if ("Synthetic".equals(attrName)) {
/*  561 */         access |= 0x1000;
/*  562 */       } else if ("SourceDebugExtension".equals(attrName)) {
/*  563 */         int len = readInt(v + 2);
/*  564 */         sourceDebug = readUTF(v + 6, len, new char[len]);
/*  565 */       } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  566 */         ianns = v + 6;
/*      */       } else {
/*  568 */         Attribute attr = readAttribute(attrs, attrName, v + 6, 
/*      */ 
/*      */             
/*  571 */             readInt(v + 2), c, -1, null);
/*      */ 
/*      */ 
/*      */         
/*  575 */         if (attr != null) {
/*  576 */           attr.next = cattrs;
/*  577 */           cattrs = attr;
/*      */         } 
/*      */       } 
/*  580 */       v += 6 + readInt(v + 2);
/*      */     } 
/*      */     
/*  583 */     classVisitor.visit(readInt(4), access, name, signature, superClassName, implementedItfs);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  591 */     if (!skipDebug && (sourceFile != null || sourceDebug != null)) {
/*  592 */       classVisitor.visitSource(sourceFile, sourceDebug);
/*      */     }
/*      */ 
/*      */     
/*  596 */     if (enclosingOwner != null) {
/*  597 */       classVisitor.visitOuterClass(enclosingOwner, enclosingName, enclosingDesc);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  604 */     for (i = 1; i >= 0; i--) {
/*  605 */       v = (i == 0) ? ianns : anns;
/*  606 */       if (v != 0) {
/*  607 */         int j = readUnsignedShort(v);
/*  608 */         v += 2;
/*  609 */         for (; j > 0; j--) {
/*  610 */           v = readAnnotationValues(v + 2, c, true, classVisitor
/*      */ 
/*      */               
/*  613 */               .visitAnnotation(readUTF8(v, c), (i != 0)));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  620 */     while (cattrs != null) {
/*  621 */       Attribute attr = cattrs.next;
/*  622 */       cattrs.next = null;
/*  623 */       classVisitor.visitAttribute(cattrs);
/*  624 */       cattrs = attr;
/*      */     } 
/*      */ 
/*      */     
/*  628 */     if (w != 0) {
/*  629 */       i = readUnsignedShort(w);
/*  630 */       w += 2;
/*  631 */       for (; i > 0; i--) {
/*  632 */         classVisitor.visitInnerClass((readUnsignedShort(w) == 0) ? null : 
/*      */             
/*  634 */             readClass(w, c), (readUnsignedShort(w + 2) == 0) ? null : 
/*      */             
/*  636 */             readClass(w + 2, c), (readUnsignedShort(w + 4) == 0) ? null : 
/*      */             
/*  638 */             readUTF8(w + 4, c), readUnsignedShort(w + 6));
/*  639 */         w += 8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  644 */     i = readUnsignedShort(u);
/*  645 */     u += 2;
/*  646 */     for (; i > 0; i--) {
/*  647 */       access = readUnsignedShort(u);
/*  648 */       name = readUTF8(u + 2, c);
/*  649 */       String desc = readUTF8(u + 4, c);
/*      */ 
/*      */       
/*  652 */       int fieldValueItem = 0;
/*  653 */       signature = null;
/*  654 */       anns = 0;
/*  655 */       ianns = 0;
/*  656 */       cattrs = null;
/*      */       
/*  658 */       int j = readUnsignedShort(u + 6);
/*  659 */       u += 8;
/*  660 */       for (; j > 0; j--) {
/*  661 */         String attrName = readUTF8(u, c);
/*      */ 
/*      */         
/*  664 */         if ("ConstantValue".equals(attrName)) {
/*  665 */           fieldValueItem = readUnsignedShort(u + 6);
/*  666 */         } else if ("Signature".equals(attrName)) {
/*  667 */           signature = readUTF8(u + 6, c);
/*  668 */         } else if ("Deprecated".equals(attrName)) {
/*  669 */           access |= 0x20000;
/*  670 */         } else if ("Synthetic".equals(attrName)) {
/*  671 */           access |= 0x1000;
/*  672 */         } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  673 */           anns = u + 6;
/*  674 */         } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  675 */           ianns = u + 6;
/*      */         } else {
/*  677 */           Attribute attr = readAttribute(attrs, attrName, u + 6, 
/*      */ 
/*      */               
/*  680 */               readInt(u + 2), c, -1, null);
/*      */ 
/*      */ 
/*      */           
/*  684 */           if (attr != null) {
/*  685 */             attr.next = cattrs;
/*  686 */             cattrs = attr;
/*      */           } 
/*      */         } 
/*  689 */         u += 6 + readInt(u + 2);
/*      */       } 
/*      */       
/*  692 */       FieldVisitor fv = classVisitor.visitField(access, name, desc, signature, (fieldValueItem == 0) ? null : 
/*      */ 
/*      */ 
/*      */           
/*  696 */           readConst(fieldValueItem, c));
/*      */       
/*  698 */       if (fv != null) {
/*      */         
/*  700 */         for (j = 1; j >= 0; j--) {
/*  701 */           v = (j == 0) ? ianns : anns;
/*  702 */           if (v != 0) {
/*  703 */             int k = readUnsignedShort(v);
/*  704 */             v += 2;
/*  705 */             for (; k > 0; k--) {
/*  706 */               v = readAnnotationValues(v + 2, c, true, fv
/*      */ 
/*      */                   
/*  709 */                   .visitAnnotation(readUTF8(v, c), (j != 0)));
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  714 */         while (cattrs != null) {
/*  715 */           Attribute attr = cattrs.next;
/*  716 */           cattrs.next = null;
/*  717 */           fv.visitAttribute(cattrs);
/*  718 */           cattrs = attr;
/*      */         } 
/*  720 */         fv.visitEnd();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  725 */     i = readUnsignedShort(u);
/*  726 */     u += 2;
/*  727 */     for (; i > 0; i--) {
/*  728 */       String[] exceptions; int u0 = u + 6;
/*  729 */       access = readUnsignedShort(u);
/*  730 */       name = readUTF8(u + 2, c);
/*  731 */       String desc = readUTF8(u + 4, c);
/*  732 */       signature = null;
/*  733 */       anns = 0;
/*  734 */       ianns = 0;
/*  735 */       int dann = 0;
/*  736 */       int mpanns = 0;
/*  737 */       int impanns = 0;
/*  738 */       cattrs = null;
/*  739 */       v = 0;
/*  740 */       w = 0;
/*      */ 
/*      */       
/*  743 */       int j = readUnsignedShort(u + 6);
/*  744 */       u += 8;
/*  745 */       for (; j > 0; j--) {
/*  746 */         String attrName = readUTF8(u, c);
/*  747 */         int attrSize = readInt(u + 2);
/*  748 */         u += 6;
/*      */ 
/*      */         
/*  751 */         if ("Code".equals(attrName)) {
/*  752 */           if (!skipCode) {
/*  753 */             v = u;
/*      */           }
/*  755 */         } else if ("Exceptions".equals(attrName)) {
/*  756 */           w = u;
/*  757 */         } else if ("Signature".equals(attrName)) {
/*  758 */           signature = readUTF8(u, c);
/*  759 */         } else if ("Deprecated".equals(attrName)) {
/*  760 */           access |= 0x20000;
/*  761 */         } else if ("RuntimeVisibleAnnotations".equals(attrName)) {
/*  762 */           anns = u;
/*  763 */         } else if ("AnnotationDefault".equals(attrName)) {
/*  764 */           dann = u;
/*  765 */         } else if ("Synthetic".equals(attrName)) {
/*  766 */           access |= 0x1000;
/*  767 */         } else if ("RuntimeInvisibleAnnotations".equals(attrName)) {
/*  768 */           ianns = u;
/*  769 */         } else if ("RuntimeVisibleParameterAnnotations".equals(attrName)) {
/*      */           
/*  771 */           mpanns = u;
/*  772 */         } else if ("RuntimeInvisibleParameterAnnotations".equals(attrName)) {
/*      */           
/*  774 */           impanns = u;
/*      */         } else {
/*  776 */           Attribute attr = readAttribute(attrs, attrName, u, attrSize, c, -1, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  783 */           if (attr != null) {
/*  784 */             attr.next = cattrs;
/*  785 */             cattrs = attr;
/*      */           } 
/*      */         } 
/*  788 */         u += attrSize;
/*      */       } 
/*      */ 
/*      */       
/*  792 */       if (w == 0) {
/*  793 */         exceptions = null;
/*      */       } else {
/*  795 */         exceptions = new String[readUnsignedShort(w)];
/*  796 */         w += 2;
/*  797 */         for (j = 0; j < exceptions.length; j++) {
/*  798 */           exceptions[j] = readClass(w, c);
/*  799 */           w += 2;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  804 */       MethodVisitor mv = classVisitor.visitMethod(access, name, desc, signature, exceptions);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  810 */       if (mv != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  822 */         if (mv instanceof MethodWriter) {
/*  823 */           MethodWriter mw = (MethodWriter)mv;
/*  824 */           if (mw.cw.cr == this && 
/*  825 */             signature == mw.signature) {
/*  826 */             boolean sameExceptions = false;
/*  827 */             if (exceptions == null) {
/*  828 */               sameExceptions = (mw.exceptionCount == 0);
/*      */             }
/*  830 */             else if (exceptions.length == mw.exceptionCount) {
/*  831 */               sameExceptions = true;
/*  832 */               for (j = exceptions.length - 1; j >= 0; j--) {
/*      */                 
/*  834 */                 w -= 2;
/*  835 */                 if (mw.exceptions[j] != readUnsignedShort(w)) {
/*      */                   
/*  837 */                   sameExceptions = false;
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             } 
/*  843 */             if (sameExceptions) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  850 */               mw.classReaderOffset = u0;
/*  851 */               mw.classReaderLength = u - u0;
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  858 */         if (dann != 0) {
/*  859 */           AnnotationVisitor dv = mv.visitAnnotationDefault();
/*  860 */           readAnnotationValue(dann, c, null, dv);
/*  861 */           if (dv != null) {
/*  862 */             dv.visitEnd();
/*      */           }
/*      */         } 
/*      */         
/*  866 */         for (j = 1; j >= 0; j--) {
/*  867 */           w = (j == 0) ? ianns : anns;
/*  868 */           if (w != 0) {
/*  869 */             int k = readUnsignedShort(w);
/*  870 */             w += 2;
/*  871 */             for (; k > 0; k--) {
/*  872 */               w = readAnnotationValues(w + 2, c, true, mv
/*      */ 
/*      */                   
/*  875 */                   .visitAnnotation(readUTF8(w, c), (j != 0)));
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  880 */         if (mpanns != 0) {
/*  881 */           readParameterAnnotations(mpanns, desc, c, true, mv);
/*      */         }
/*  883 */         if (impanns != 0) {
/*  884 */           readParameterAnnotations(impanns, desc, c, false, mv);
/*      */         }
/*  886 */         while (cattrs != null) {
/*  887 */           Attribute attr = cattrs.next;
/*  888 */           cattrs.next = null;
/*  889 */           mv.visitAttribute(cattrs);
/*  890 */           cattrs = attr;
/*      */         } 
/*      */       } 
/*      */       
/*  894 */       if (mv != null && v != 0) {
/*  895 */         int maxStack = readUnsignedShort(v);
/*  896 */         int maxLocals = readUnsignedShort(v + 2);
/*  897 */         int codeLength = readInt(v + 4);
/*  898 */         v += 8;
/*      */         
/*  900 */         int codeStart = v;
/*  901 */         int codeEnd = v + codeLength;
/*      */         
/*  903 */         mv.visitCode();
/*      */ 
/*      */ 
/*      */         
/*  907 */         Label[] labels = new Label[codeLength + 2];
/*  908 */         readLabel(codeLength + 1, labels);
/*  909 */         while (v < codeEnd) {
/*  910 */           w = v - codeStart;
/*  911 */           int opcode = b[v] & 0xFF;
/*  912 */           switch (ClassWriter.TYPE[opcode]) {
/*      */             case 0:
/*      */             case 4:
/*  915 */               v++;
/*      */               continue;
/*      */             case 8:
/*  918 */               readLabel(w + readShort(v + 1), labels);
/*  919 */               v += 3;
/*      */               continue;
/*      */             case 9:
/*  922 */               readLabel(w + readInt(v + 1), labels);
/*  923 */               v += 5;
/*      */               continue;
/*      */             case 16:
/*  926 */               opcode = b[v + 1] & 0xFF;
/*  927 */               if (opcode == 132) {
/*  928 */                 v += 6; continue;
/*      */               } 
/*  930 */               v += 4;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 13:
/*  935 */               v = v + 4 - (w & 0x3);
/*      */               
/*  937 */               readLabel(w + readInt(v), labels);
/*  938 */               j = readInt(v + 8) - readInt(v + 4) + 1;
/*  939 */               v += 12;
/*  940 */               for (; j > 0; j--) {
/*  941 */                 readLabel(w + readInt(v), labels);
/*  942 */                 v += 4;
/*      */               } 
/*      */               continue;
/*      */             
/*      */             case 14:
/*  947 */               v = v + 4 - (w & 0x3);
/*      */               
/*  949 */               readLabel(w + readInt(v), labels);
/*  950 */               j = readInt(v + 4);
/*  951 */               v += 8;
/*  952 */               for (; j > 0; j--) {
/*  953 */                 readLabel(w + readInt(v + 4), labels);
/*  954 */                 v += 8;
/*      */               } 
/*      */               continue;
/*      */             case 1:
/*      */             case 3:
/*      */             case 10:
/*  960 */               v += 2;
/*      */               continue;
/*      */             case 2:
/*      */             case 5:
/*      */             case 6:
/*      */             case 11:
/*      */             case 12:
/*  967 */               v += 3;
/*      */               continue;
/*      */             case 7:
/*  970 */               v += 5;
/*      */               continue;
/*      */           } 
/*      */           
/*  974 */           v += 4;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  979 */         j = readUnsignedShort(v);
/*  980 */         v += 2;
/*  981 */         for (; j > 0; j--) {
/*  982 */           Label start = readLabel(readUnsignedShort(v), labels);
/*  983 */           Label end = readLabel(readUnsignedShort(v + 2), labels);
/*  984 */           Label handler = readLabel(readUnsignedShort(v + 4), labels);
/*  985 */           int type = readUnsignedShort(v + 6);
/*  986 */           if (type == 0) {
/*  987 */             mv.visitTryCatchBlock(start, end, handler, null);
/*      */           } else {
/*  989 */             mv.visitTryCatchBlock(start, end, handler, 
/*      */ 
/*      */                 
/*  992 */                 readUTF8(this.items[type], c));
/*      */           } 
/*  994 */           v += 8;
/*      */         } 
/*      */ 
/*      */         
/*  998 */         int varTable = 0;
/*  999 */         int varTypeTable = 0;
/* 1000 */         int stackMap = 0;
/* 1001 */         int frameCount = 0;
/* 1002 */         int frameMode = 0;
/* 1003 */         int frameOffset = 0;
/* 1004 */         int frameLocalCount = 0;
/* 1005 */         int frameLocalDiff = 0;
/* 1006 */         int frameStackCount = 0;
/* 1007 */         Object[] frameLocal = null;
/* 1008 */         Object[] frameStack = null;
/* 1009 */         boolean zip = true;
/* 1010 */         cattrs = null;
/* 1011 */         j = readUnsignedShort(v);
/* 1012 */         v += 2;
/* 1013 */         for (; j > 0; j--) {
/* 1014 */           String attrName = readUTF8(v, c);
/* 1015 */           if ("LocalVariableTable".equals(attrName)) {
/* 1016 */             if (!skipDebug) {
/* 1017 */               varTable = v + 6;
/* 1018 */               int k = readUnsignedShort(v + 6);
/* 1019 */               w = v + 8;
/* 1020 */               for (; k > 0; k--) {
/* 1021 */                 int label = readUnsignedShort(w);
/* 1022 */                 if (labels[label] == null) {
/* 1023 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/* 1025 */                 label += readUnsignedShort(w + 2);
/* 1026 */                 if (labels[label] == null) {
/* 1027 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/* 1029 */                 w += 10;
/*      */               } 
/*      */             } 
/* 1032 */           } else if ("LocalVariableTypeTable".equals(attrName)) {
/* 1033 */             varTypeTable = v + 6;
/* 1034 */           } else if ("LineNumberTable".equals(attrName)) {
/* 1035 */             if (!skipDebug) {
/* 1036 */               int k = readUnsignedShort(v + 6);
/* 1037 */               w = v + 8;
/* 1038 */               for (; k > 0; k--) {
/* 1039 */                 int label = readUnsignedShort(w);
/* 1040 */                 if (labels[label] == null) {
/* 1041 */                   (readLabel(label, labels)).status |= 0x1;
/*      */                 }
/* 1043 */                 (labels[label]).line = readUnsignedShort(w + 2);
/* 1044 */                 w += 4;
/*      */               } 
/*      */             } 
/* 1047 */           } else if ("StackMapTable".equals(attrName)) {
/* 1048 */             if ((flags & 0x4) == 0) {
/* 1049 */               stackMap = v + 8;
/* 1050 */               frameCount = readUnsignedShort(v + 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1068 */           else if ("StackMap".equals(attrName)) {
/* 1069 */             if ((flags & 0x4) == 0) {
/* 1070 */               stackMap = v + 8;
/* 1071 */               frameCount = readUnsignedShort(v + 6);
/* 1072 */               zip = false;
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1080 */             for (int k = 0; k < attrs.length; k++) {
/* 1081 */               if ((attrs[k]).type.equals(attrName)) {
/* 1082 */                 Attribute attr = attrs[k].read(this, v + 6, 
/*      */                     
/* 1084 */                     readInt(v + 2), c, codeStart - 8, labels);
/*      */ 
/*      */ 
/*      */                 
/* 1088 */                 if (attr != null) {
/* 1089 */                   attr.next = cattrs;
/* 1090 */                   cattrs = attr;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/* 1095 */           v += 6 + readInt(v + 2);
/*      */         } 
/*      */ 
/*      */         
/* 1099 */         if (stackMap != 0) {
/*      */ 
/*      */           
/* 1102 */           frameLocal = new Object[maxLocals];
/* 1103 */           frameStack = new Object[maxStack];
/* 1104 */           if (unzip) {
/* 1105 */             int local = 0;
/* 1106 */             if ((access & 0x8) == 0) {
/* 1107 */               if ("<init>".equals(name)) {
/* 1108 */                 frameLocal[local++] = Opcodes.UNINITIALIZED_THIS;
/*      */               } else {
/* 1110 */                 frameLocal[local++] = readClass(this.header + 2, c);
/*      */               } 
/*      */             }
/* 1113 */             j = 1;
/*      */             while (true) {
/* 1115 */               int k = j;
/* 1116 */               switch (desc.charAt(j++)) {
/*      */                 case 'B':
/*      */                 case 'C':
/*      */                 case 'I':
/*      */                 case 'S':
/*      */                 case 'Z':
/* 1122 */                   frameLocal[local++] = Opcodes.INTEGER;
/*      */                   continue;
/*      */                 case 'F':
/* 1125 */                   frameLocal[local++] = Opcodes.FLOAT;
/*      */                   continue;
/*      */                 case 'J':
/* 1128 */                   frameLocal[local++] = Opcodes.LONG;
/*      */                   continue;
/*      */                 case 'D':
/* 1131 */                   frameLocal[local++] = Opcodes.DOUBLE;
/*      */                   continue;
/*      */                 case '[':
/* 1134 */                   while (desc.charAt(j) == '[') {
/* 1135 */                     j++;
/*      */                   }
/* 1137 */                   if (desc.charAt(j) == 'L') {
/* 1138 */                     j++;
/* 1139 */                     while (desc.charAt(j) != ';') {
/* 1140 */                       j++;
/*      */                     }
/*      */                   } 
/* 1143 */                   frameLocal[local++] = desc.substring(k, ++j);
/*      */                   continue;
/*      */                 case 'L':
/* 1146 */                   while (desc.charAt(j) != ';') {
/* 1147 */                     j++;
/*      */                   }
/* 1149 */                   frameLocal[local++] = desc.substring(k + 1, j++);
/*      */                   continue;
/*      */               } 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/* 1156 */             frameLocalCount = local;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1164 */           frameOffset = -1;
/*      */         } 
/* 1166 */         v = codeStart;
/*      */         
/* 1168 */         while (v < codeEnd) {
/* 1169 */           int label, min, max; Label[] table; int[] keys; Label[] values; int cpIndex; String iowner, iname, idesc; w = v - codeStart;
/*      */           
/* 1171 */           Label label1 = labels[w];
/* 1172 */           if (label1 != null) {
/* 1173 */             mv.visitLabel(label1);
/* 1174 */             if (!skipDebug && label1.line > 0) {
/* 1175 */               mv.visitLineNumber(label1.line, label1);
/*      */             }
/*      */           } 
/*      */           
/* 1179 */           while (frameLocal != null && (frameOffset == w || frameOffset == -1)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1185 */             if (!zip || unzip) {
/* 1186 */               mv.visitFrame(-1, frameLocalCount, frameLocal, frameStackCount, frameStack);
/*      */ 
/*      */ 
/*      */             
/*      */             }
/* 1191 */             else if (frameOffset != -1) {
/* 1192 */               mv.visitFrame(frameMode, frameLocalDiff, frameLocal, frameStackCount, frameStack);
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1199 */             if (frameCount > 0) {
/*      */               int tag, delta;
/* 1201 */               if (zip) {
/* 1202 */                 tag = b[stackMap++] & 0xFF;
/*      */               } else {
/* 1204 */                 tag = 255;
/* 1205 */                 frameOffset = -1;
/*      */               } 
/* 1207 */               frameLocalDiff = 0;
/* 1208 */               if (tag < 64) {
/*      */                 
/* 1210 */                 delta = tag;
/* 1211 */                 frameMode = 3;
/* 1212 */                 frameStackCount = 0;
/* 1213 */               } else if (tag < 128) {
/* 1214 */                 delta = tag - 64;
/*      */                 
/* 1216 */                 stackMap = readFrameType(frameStack, 0, stackMap, c, labels);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1221 */                 frameMode = 4;
/* 1222 */                 frameStackCount = 1;
/*      */               } else {
/* 1224 */                 delta = readUnsignedShort(stackMap);
/* 1225 */                 stackMap += 2;
/* 1226 */                 if (tag == 247) {
/*      */                   
/* 1228 */                   stackMap = readFrameType(frameStack, 0, stackMap, c, labels);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1233 */                   frameMode = 4;
/* 1234 */                   frameStackCount = 1;
/* 1235 */                 } else if (tag >= 248 && tag < 251) {
/*      */ 
/*      */                   
/* 1238 */                   frameMode = 2;
/* 1239 */                   frameLocalDiff = 251 - tag;
/*      */                   
/* 1241 */                   frameLocalCount -= frameLocalDiff;
/* 1242 */                   frameStackCount = 0;
/* 1243 */                 } else if (tag == 251) {
/*      */                   
/* 1245 */                   frameMode = 3;
/* 1246 */                   frameStackCount = 0;
/* 1247 */                 } else if (tag < 255) {
/* 1248 */                   j = unzip ? frameLocalCount : 0;
/* 1249 */                   int k = tag - 251;
/* 1250 */                   for (; k > 0; k--)
/*      */                   {
/* 1252 */                     stackMap = readFrameType(frameLocal, j++, stackMap, c, labels);
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1258 */                   frameMode = 1;
/* 1259 */                   frameLocalDiff = tag - 251;
/*      */                   
/* 1261 */                   frameLocalCount += frameLocalDiff;
/* 1262 */                   frameStackCount = 0;
/*      */                 } else {
/* 1264 */                   frameMode = 0;
/* 1265 */                   int n = frameLocalDiff = frameLocalCount = readUnsignedShort(stackMap);
/* 1266 */                   stackMap += 2;
/* 1267 */                   for (j = 0; n > 0; n--) {
/* 1268 */                     stackMap = readFrameType(frameLocal, j++, stackMap, c, labels);
/*      */                   }
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1274 */                   n = frameStackCount = readUnsignedShort(stackMap);
/* 1275 */                   stackMap += 2;
/* 1276 */                   for (j = 0; n > 0; n--) {
/* 1277 */                     stackMap = readFrameType(frameStack, j++, stackMap, c, labels);
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1285 */               frameOffset += delta + 1;
/* 1286 */               readLabel(frameOffset, labels);
/*      */               
/* 1288 */               frameCount--; continue;
/*      */             } 
/* 1290 */             frameLocal = null;
/*      */           } 
/*      */ 
/*      */           
/* 1294 */           int opcode = b[v] & 0xFF;
/* 1295 */           switch (ClassWriter.TYPE[opcode]) {
/*      */             case 0:
/* 1297 */               mv.visitInsn(opcode);
/* 1298 */               v++;
/*      */               continue;
/*      */             case 4:
/* 1301 */               if (opcode > 54) {
/* 1302 */                 opcode -= 59;
/* 1303 */                 mv.visitVarInsn(54 + (opcode >> 2), opcode & 0x3);
/*      */               } else {
/*      */                 
/* 1306 */                 opcode -= 26;
/* 1307 */                 mv.visitVarInsn(21 + (opcode >> 2), opcode & 0x3);
/*      */               } 
/*      */               
/* 1310 */               v++;
/*      */               continue;
/*      */             case 8:
/* 1313 */               mv.visitJumpInsn(opcode, labels[w + 
/* 1314 */                     readShort(v + 1)]);
/* 1315 */               v += 3;
/*      */               continue;
/*      */             case 9:
/* 1318 */               mv.visitJumpInsn(opcode - 33, labels[w + 
/* 1319 */                     readInt(v + 1)]);
/* 1320 */               v += 5;
/*      */               continue;
/*      */             case 16:
/* 1323 */               opcode = b[v + 1] & 0xFF;
/* 1324 */               if (opcode == 132) {
/* 1325 */                 mv.visitIincInsn(readUnsignedShort(v + 2), 
/* 1326 */                     readShort(v + 4));
/* 1327 */                 v += 6; continue;
/*      */               } 
/* 1329 */               mv.visitVarInsn(opcode, 
/* 1330 */                   readUnsignedShort(v + 2));
/* 1331 */               v += 4;
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 13:
/* 1336 */               v = v + 4 - (w & 0x3);
/*      */               
/* 1338 */               label = w + readInt(v);
/* 1339 */               min = readInt(v + 4);
/* 1340 */               max = readInt(v + 8);
/* 1341 */               v += 12;
/* 1342 */               table = new Label[max - min + 1];
/* 1343 */               for (j = 0; j < table.length; j++) {
/* 1344 */                 table[j] = labels[w + readInt(v)];
/* 1345 */                 v += 4;
/*      */               } 
/* 1347 */               mv.visitTableSwitchInsn(min, max, labels[label], table);
/*      */               continue;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             case 14:
/* 1354 */               v = v + 4 - (w & 0x3);
/*      */               
/* 1356 */               label = w + readInt(v);
/* 1357 */               j = readInt(v + 4);
/* 1358 */               v += 8;
/* 1359 */               keys = new int[j];
/* 1360 */               values = new Label[j];
/* 1361 */               for (j = 0; j < keys.length; j++) {
/* 1362 */                 keys[j] = readInt(v);
/* 1363 */                 values[j] = labels[w + readInt(v + 4)];
/* 1364 */                 v += 8;
/*      */               } 
/* 1366 */               mv.visitLookupSwitchInsn(labels[label], keys, values);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 3:
/* 1371 */               mv.visitVarInsn(opcode, b[v + 1] & 0xFF);
/* 1372 */               v += 2;
/*      */               continue;
/*      */             case 1:
/* 1375 */               mv.visitIntInsn(opcode, b[v + 1]);
/* 1376 */               v += 2;
/*      */               continue;
/*      */             case 2:
/* 1379 */               mv.visitIntInsn(opcode, readShort(v + 1));
/* 1380 */               v += 3;
/*      */               continue;
/*      */             case 10:
/* 1383 */               mv.visitLdcInsn(readConst(b[v + 1] & 0xFF, c));
/* 1384 */               v += 2;
/*      */               continue;
/*      */             case 11:
/* 1387 */               mv.visitLdcInsn(readConst(readUnsignedShort(v + 1), c));
/*      */               
/* 1389 */               v += 3;
/*      */               continue;
/*      */             case 6:
/*      */             case 7:
/* 1393 */               cpIndex = this.items[readUnsignedShort(v + 1)];
/* 1394 */               iowner = readClass(cpIndex, c);
/* 1395 */               cpIndex = this.items[readUnsignedShort(cpIndex + 2)];
/* 1396 */               iname = readUTF8(cpIndex, c);
/* 1397 */               idesc = readUTF8(cpIndex + 2, c);
/* 1398 */               if (opcode < 182) {
/* 1399 */                 mv.visitFieldInsn(opcode, iowner, iname, idesc);
/*      */               } else {
/* 1401 */                 mv.visitMethodInsn(opcode, iowner, iname, idesc);
/*      */               } 
/* 1403 */               if (opcode == 185) {
/* 1404 */                 v += 5; continue;
/*      */               } 
/* 1406 */               v += 3;
/*      */               continue;
/*      */             
/*      */             case 5:
/* 1410 */               mv.visitTypeInsn(opcode, readClass(v + 1, c));
/* 1411 */               v += 3;
/*      */               continue;
/*      */             case 12:
/* 1414 */               mv.visitIincInsn(b[v + 1] & 0xFF, b[v + 2]);
/* 1415 */               v += 3;
/*      */               continue;
/*      */           } 
/*      */           
/* 1419 */           mv.visitMultiANewArrayInsn(readClass(v + 1, c), b[v + 3] & 0xFF);
/*      */           
/* 1421 */           v += 4;
/*      */         } 
/*      */ 
/*      */         
/* 1425 */         Label l = labels[codeEnd - codeStart];
/* 1426 */         if (l != null) {
/* 1427 */           mv.visitLabel(l);
/*      */         }
/*      */         
/* 1430 */         if (!skipDebug && varTable != 0) {
/* 1431 */           int[] typeTable = null;
/* 1432 */           if (varTypeTable != 0) {
/* 1433 */             int m = readUnsignedShort(varTypeTable) * 3;
/* 1434 */             w = varTypeTable + 2;
/* 1435 */             typeTable = new int[m];
/* 1436 */             while (m > 0) {
/* 1437 */               typeTable[--m] = w + 6;
/* 1438 */               typeTable[--m] = readUnsignedShort(w + 8);
/* 1439 */               typeTable[--m] = readUnsignedShort(w);
/* 1440 */               w += 10;
/*      */             } 
/*      */           } 
/* 1443 */           int k = readUnsignedShort(varTable);
/* 1444 */           w = varTable + 2;
/* 1445 */           for (; k > 0; k--) {
/* 1446 */             int start = readUnsignedShort(w);
/* 1447 */             int length = readUnsignedShort(w + 2);
/* 1448 */             int index = readUnsignedShort(w + 8);
/* 1449 */             String vsignature = null;
/* 1450 */             if (typeTable != null) {
/* 1451 */               for (int a = 0; a < typeTable.length; a += 3) {
/* 1452 */                 if (typeTable[a] == start && typeTable[a + 1] == index) {
/*      */ 
/*      */                   
/* 1455 */                   vsignature = readUTF8(typeTable[a + 2], c);
/*      */                   break;
/*      */                 } 
/*      */               } 
/*      */             }
/* 1460 */             mv.visitLocalVariable(readUTF8(w + 4, c), 
/* 1461 */                 readUTF8(w + 6, c), vsignature, labels[start], labels[start + length], index);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1466 */             w += 10;
/*      */           } 
/*      */         } 
/*      */         
/* 1470 */         while (cattrs != null) {
/* 1471 */           Attribute attr = cattrs.next;
/* 1472 */           cattrs.next = null;
/* 1473 */           mv.visitAttribute(cattrs);
/* 1474 */           cattrs = attr;
/*      */         } 
/*      */         
/* 1477 */         mv.visitMaxs(maxStack, maxLocals);
/*      */       } 
/*      */       
/* 1480 */       if (mv != null) {
/* 1481 */         mv.visitEnd();
/*      */       }
/*      */       
/*      */       continue;
/*      */     } 
/* 1486 */     classVisitor.visitEnd();
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
/*      */   private void readParameterAnnotations(int v, String desc, char[] buf, boolean visible, MethodVisitor mv) {
/* 1509 */     int n = this.b[v++] & 0xFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1516 */     int synthetics = (Type.getArgumentTypes(desc)).length - n;
/*      */     int i;
/* 1518 */     for (i = 0; i < synthetics; i++) {
/*      */       
/* 1520 */       AnnotationVisitor av = mv.visitParameterAnnotation(i, "Ljava/lang/Synthetic;", false);
/* 1521 */       if (av != null) {
/* 1522 */         av.visitEnd();
/*      */       }
/*      */     } 
/* 1525 */     for (; i < n + synthetics; i++) {
/* 1526 */       int j = readUnsignedShort(v);
/* 1527 */       v += 2;
/* 1528 */       for (; j > 0; j--) {
/* 1529 */         AnnotationVisitor av = mv.visitParameterAnnotation(i, readUTF8(v, buf), visible);
/* 1530 */         v = readAnnotationValues(v + 2, buf, true, av);
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
/*      */   private int readAnnotationValues(int v, char[] buf, boolean named, AnnotationVisitor av) {
/* 1553 */     int i = readUnsignedShort(v);
/* 1554 */     v += 2;
/* 1555 */     if (named) {
/* 1556 */       for (; i > 0; i--) {
/* 1557 */         v = readAnnotationValue(v + 2, buf, readUTF8(v, buf), av);
/*      */       }
/*      */     } else {
/* 1560 */       for (; i > 0; i--) {
/* 1561 */         v = readAnnotationValue(v, buf, null, av);
/*      */       }
/*      */     } 
/* 1564 */     if (av != null) {
/* 1565 */       av.visitEnd();
/*      */     }
/* 1567 */     return v;
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
/*      */   private int readAnnotationValue(int v, char[] buf, String name, AnnotationVisitor av) {
/*      */     int i;
/*      */     int size;
/*      */     byte[] bv;
/*      */     boolean[] zv;
/*      */     short[] sv;
/*      */     char[] cv;
/*      */     int[] iv;
/*      */     long[] lv;
/*      */     float[] fv;
/*      */     double[] dv;
/* 1589 */     if (av == null) {
/* 1590 */       switch (this.b[v] & 0xFF) {
/*      */         case 101:
/* 1592 */           return v + 5;
/*      */         case 64:
/* 1594 */           return readAnnotationValues(v + 3, buf, true, null);
/*      */         case 91:
/* 1596 */           return readAnnotationValues(v + 1, buf, false, null);
/*      */       } 
/* 1598 */       return v + 3;
/*      */     } 
/*      */     
/* 1601 */     switch (this.b[v++] & 0xFF) {
/*      */       case 68:
/*      */       case 70:
/*      */       case 73:
/*      */       case 74:
/* 1606 */         av.visit(name, readConst(readUnsignedShort(v), buf));
/* 1607 */         v += 2;
/*      */         break;
/*      */       case 66:
/* 1610 */         av.visit(name, new Byte(
/* 1611 */               (byte)readInt(this.items[readUnsignedShort(v)])));
/* 1612 */         v += 2;
/*      */         break;
/*      */       case 90:
/* 1615 */         av.visit(name, (readInt(this.items[readUnsignedShort(v)]) == 0) ? Boolean.FALSE : Boolean.TRUE);
/*      */ 
/*      */         
/* 1618 */         v += 2;
/*      */         break;
/*      */       case 83:
/* 1621 */         av.visit(name, new Short(
/* 1622 */               (short)readInt(this.items[readUnsignedShort(v)])));
/* 1623 */         v += 2;
/*      */         break;
/*      */       case 67:
/* 1626 */         av.visit(name, new Character(
/* 1627 */               (char)readInt(this.items[readUnsignedShort(v)])));
/* 1628 */         v += 2;
/*      */         break;
/*      */       case 115:
/* 1631 */         av.visit(name, readUTF8(v, buf));
/* 1632 */         v += 2;
/*      */         break;
/*      */       case 101:
/* 1635 */         av.visitEnum(name, readUTF8(v, buf), readUTF8(v + 2, buf));
/* 1636 */         v += 4;
/*      */         break;
/*      */       case 99:
/* 1639 */         av.visit(name, Type.getType(readUTF8(v, buf)));
/* 1640 */         v += 2;
/*      */         break;
/*      */       case 64:
/* 1643 */         v = readAnnotationValues(v + 2, buf, true, av
/*      */ 
/*      */             
/* 1646 */             .visitAnnotation(name, readUTF8(v, buf)));
/*      */         break;
/*      */       case 91:
/* 1649 */         size = readUnsignedShort(v);
/* 1650 */         v += 2;
/* 1651 */         if (size == 0) {
/* 1652 */           return readAnnotationValues(v - 2, buf, false, av
/*      */ 
/*      */               
/* 1655 */               .visitArray(name));
/*      */         }
/* 1657 */         switch (this.b[v++] & 0xFF) {
/*      */           case 66:
/* 1659 */             bv = new byte[size];
/* 1660 */             for (i = 0; i < size; i++) {
/* 1661 */               bv[i] = (byte)readInt(this.items[readUnsignedShort(v)]);
/* 1662 */               v += 3;
/*      */             } 
/* 1664 */             av.visit(name, bv);
/* 1665 */             v--;
/*      */             break;
/*      */           case 90:
/* 1668 */             zv = new boolean[size];
/* 1669 */             for (i = 0; i < size; i++) {
/* 1670 */               zv[i] = (readInt(this.items[readUnsignedShort(v)]) != 0);
/* 1671 */               v += 3;
/*      */             } 
/* 1673 */             av.visit(name, zv);
/* 1674 */             v--;
/*      */             break;
/*      */           case 83:
/* 1677 */             sv = new short[size];
/* 1678 */             for (i = 0; i < size; i++) {
/* 1679 */               sv[i] = (short)readInt(this.items[readUnsignedShort(v)]);
/* 1680 */               v += 3;
/*      */             } 
/* 1682 */             av.visit(name, sv);
/* 1683 */             v--;
/*      */             break;
/*      */           case 67:
/* 1686 */             cv = new char[size];
/* 1687 */             for (i = 0; i < size; i++) {
/* 1688 */               cv[i] = (char)readInt(this.items[readUnsignedShort(v)]);
/* 1689 */               v += 3;
/*      */             } 
/* 1691 */             av.visit(name, cv);
/* 1692 */             v--;
/*      */             break;
/*      */           case 73:
/* 1695 */             iv = new int[size];
/* 1696 */             for (i = 0; i < size; i++) {
/* 1697 */               iv[i] = readInt(this.items[readUnsignedShort(v)]);
/* 1698 */               v += 3;
/*      */             } 
/* 1700 */             av.visit(name, iv);
/* 1701 */             v--;
/*      */             break;
/*      */           case 74:
/* 1704 */             lv = new long[size];
/* 1705 */             for (i = 0; i < size; i++) {
/* 1706 */               lv[i] = readLong(this.items[readUnsignedShort(v)]);
/* 1707 */               v += 3;
/*      */             } 
/* 1709 */             av.visit(name, lv);
/* 1710 */             v--;
/*      */             break;
/*      */           case 70:
/* 1713 */             fv = new float[size];
/* 1714 */             for (i = 0; i < size; i++) {
/* 1715 */               fv[i] = Float.intBitsToFloat(readInt(this.items[readUnsignedShort(v)]));
/* 1716 */               v += 3;
/*      */             } 
/* 1718 */             av.visit(name, fv);
/* 1719 */             v--;
/*      */             break;
/*      */           case 68:
/* 1722 */             dv = new double[size];
/* 1723 */             for (i = 0; i < size; i++) {
/* 1724 */               dv[i] = Double.longBitsToDouble(readLong(this.items[readUnsignedShort(v)]));
/* 1725 */               v += 3;
/*      */             } 
/* 1727 */             av.visit(name, dv);
/* 1728 */             v--;
/*      */             break;
/*      */         } 
/* 1731 */         v = readAnnotationValues(v - 3, buf, false, av
/*      */ 
/*      */             
/* 1734 */             .visitArray(name));
/*      */         break;
/*      */     } 
/* 1737 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int readFrameType(Object[] frame, int index, int v, char[] buf, Label[] labels) {
/* 1747 */     int type = this.b[v++] & 0xFF;
/* 1748 */     switch (type)
/*      */     { case 0:
/* 1750 */         frame[index] = Opcodes.TOP;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1778 */         return v;case 1: frame[index] = Opcodes.INTEGER; return v;case 2: frame[index] = Opcodes.FLOAT; return v;case 3: frame[index] = Opcodes.DOUBLE; return v;case 4: frame[index] = Opcodes.LONG; return v;case 5: frame[index] = Opcodes.NULL; return v;case 6: frame[index] = Opcodes.UNINITIALIZED_THIS; return v;case 7: frame[index] = readClass(v, buf); v += 2; return v; }  frame[index] = readLabel(readUnsignedShort(v), labels); v += 2; return v;
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
/*      */   protected Label readLabel(int offset, Label[] labels) {
/* 1793 */     if (labels[offset] == null) {
/* 1794 */       labels[offset] = new Label();
/*      */     }
/* 1796 */     return labels[offset];
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
/*      */   private Attribute readAttribute(Attribute[] attrs, String type, int off, int len, char[] buf, int codeOff, Label[] labels) {
/* 1833 */     for (int i = 0; i < attrs.length; i++) {
/* 1834 */       if ((attrs[i]).type.equals(type)) {
/* 1835 */         return attrs[i].read(this, off, len, buf, codeOff, labels);
/*      */       }
/*      */     } 
/* 1838 */     return (new Attribute(type)).read(this, off, len, null, -1, null);
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
/*      */   public int getItem(int item) {
/* 1855 */     return this.items[item];
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
/*      */   public int readByte(int index) {
/* 1867 */     return this.b[index] & 0xFF;
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
/*      */   public int readUnsignedShort(int index) {
/* 1879 */     byte[] b = this.b;
/* 1880 */     return (b[index] & 0xFF) << 8 | b[index + 1] & 0xFF;
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
/*      */   public short readShort(int index) {
/* 1892 */     byte[] b = this.b;
/* 1893 */     return (short)((b[index] & 0xFF) << 8 | b[index + 1] & 0xFF);
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
/*      */   public int readInt(int index) {
/* 1905 */     byte[] b = this.b;
/* 1906 */     return (b[index] & 0xFF) << 24 | (b[index + 1] & 0xFF) << 16 | (b[index + 2] & 0xFF) << 8 | b[index + 3] & 0xFF;
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
/*      */   public long readLong(int index) {
/* 1919 */     long l1 = readInt(index);
/* 1920 */     long l0 = readInt(index + 4) & 0xFFFFFFFFL;
/* 1921 */     return l1 << 32L | l0;
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
/*      */   public String readUTF8(int index, char[] buf) {
/* 1936 */     int item = readUnsignedShort(index);
/* 1937 */     String s = this.strings[item];
/* 1938 */     if (s != null) {
/* 1939 */       return s;
/*      */     }
/* 1941 */     index = this.items[item];
/* 1942 */     this.strings[item] = readUTF(index + 2, readUnsignedShort(index), buf); return readUTF(index + 2, readUnsignedShort(index), buf);
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
/*      */   private String readUTF(int index, int utfLen, char[] buf) {
/* 1955 */     int endIndex = index + utfLen;
/* 1956 */     byte[] b = this.b;
/* 1957 */     int strLen = 0;
/*      */     
/* 1959 */     while (index < endIndex) {
/* 1960 */       int c = b[index++] & 0xFF;
/* 1961 */       switch (c >> 4) {
/*      */         
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/* 1971 */           buf[strLen++] = (char)c;
/*      */           continue;
/*      */         
/*      */         case 12:
/*      */         case 13:
/* 1976 */           d = b[index++];
/* 1977 */           buf[strLen++] = (char)((c & 0x1F) << 6 | d & 0x3F);
/*      */           continue;
/*      */       } 
/*      */       
/* 1981 */       int d = b[index++];
/* 1982 */       int e = b[index++];
/* 1983 */       buf[strLen++] = (char)((c & 0xF) << 12 | (d & 0x3F) << 6 | e & 0x3F);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1988 */     return new String(buf, 0, strLen);
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
/*      */   public String readClass(int index, char[] buf) {
/* 2006 */     return readUTF8(this.items[readUnsignedShort(index)], buf);
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
/*      */   public Object readConst(int item, char[] buf) {
/* 2022 */     int index = this.items[item];
/* 2023 */     switch (this.b[index - 1]) {
/*      */       case 3:
/* 2025 */         return new Integer(readInt(index));
/*      */       case 4:
/* 2027 */         return new Float(Float.intBitsToFloat(readInt(index)));
/*      */       case 5:
/* 2029 */         return new Long(readLong(index));
/*      */       case 6:
/* 2031 */         return new Double(Double.longBitsToDouble(readLong(index)));
/*      */       case 7:
/* 2033 */         return Type.getObjectType(readUTF8(index, buf));
/*      */     } 
/*      */     
/* 2036 */     return readUTF8(index, buf);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/org/objectweb/asm/ClassReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */