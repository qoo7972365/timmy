/*     */ package com.sun.xml.internal.ws.org.objectweb.asm;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Type
/*     */ {
/*     */   public static final int VOID = 0;
/*     */   public static final int BOOLEAN = 1;
/*     */   public static final int CHAR = 2;
/*     */   public static final int BYTE = 3;
/*     */   public static final int SHORT = 4;
/*     */   public static final int INT = 5;
/*     */   public static final int FLOAT = 6;
/*     */   public static final int LONG = 7;
/*     */   public static final int DOUBLE = 8;
/*     */   public static final int ARRAY = 9;
/*     */   public static final int OBJECT = 10;
/* 132 */   public static final Type VOID_TYPE = new Type(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   public static final Type BOOLEAN_TYPE = new Type(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static final Type CHAR_TYPE = new Type(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static final Type BYTE_TYPE = new Type(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final Type SHORT_TYPE = new Type(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static final Type INT_TYPE = new Type(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public static final Type FLOAT_TYPE = new Type(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public static final Type LONG_TYPE = new Type(7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final Type DOUBLE_TYPE = new Type(8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int sort;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final char[] buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int off;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int len;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type(int sort) {
/* 211 */     this(sort, null, 0, 1);
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
/*     */   private Type(int sort, char[] buf, int off, int len) {
/* 224 */     this.sort = sort;
/* 225 */     this.buf = buf;
/* 226 */     this.off = off;
/* 227 */     this.len = len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(String typeDescriptor) {
/* 237 */     return getType(typeDescriptor.toCharArray(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getObjectType(String internalName) {
/* 247 */     char[] buf = internalName.toCharArray();
/* 248 */     return new Type((buf[0] == '[') ? 9 : 10, buf, 0, buf.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class<int> c) {
/* 258 */     if (c.isPrimitive()) {
/* 259 */       if (c == int.class)
/* 260 */         return INT_TYPE; 
/* 261 */       if (c == void.class)
/* 262 */         return VOID_TYPE; 
/* 263 */       if (c == boolean.class)
/* 264 */         return BOOLEAN_TYPE; 
/* 265 */       if (c == byte.class)
/* 266 */         return BYTE_TYPE; 
/* 267 */       if (c == char.class)
/* 268 */         return CHAR_TYPE; 
/* 269 */       if (c == short.class)
/* 270 */         return SHORT_TYPE; 
/* 271 */       if (c == double.class)
/* 272 */         return DOUBLE_TYPE; 
/* 273 */       if (c == float.class) {
/* 274 */         return FLOAT_TYPE;
/*     */       }
/* 276 */       return LONG_TYPE;
/*     */     } 
/*     */     
/* 279 */     return getType(getDescriptor(c));
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
/*     */   public static Type[] getArgumentTypes(String methodDescriptor) {
/* 292 */     char[] buf = methodDescriptor.toCharArray();
/* 293 */     int off = 1;
/* 294 */     int size = 0;
/*     */     while (true) {
/* 296 */       char car = buf[off++];
/* 297 */       if (car == ')')
/*     */         break; 
/* 299 */       if (car == 'L') {
/* 300 */         while (buf[off++] != ';');
/*     */         
/* 302 */         size++; continue;
/* 303 */       }  if (car != '[') {
/* 304 */         size++;
/*     */       }
/*     */     } 
/* 307 */     Type[] args = new Type[size];
/* 308 */     off = 1;
/* 309 */     size = 0;
/* 310 */     while (buf[off] != ')') {
/* 311 */       args[size] = getType(buf, off);
/* 312 */       off += (args[size]).len + (((args[size]).sort == 10) ? 2 : 0);
/* 313 */       size++;
/*     */     } 
/* 315 */     return args;
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
/*     */   public static Type[] getArgumentTypes(Method method) {
/* 327 */     Class[] classes = method.getParameterTypes();
/* 328 */     Type[] types = new Type[classes.length];
/* 329 */     for (int i = classes.length - 1; i >= 0; i--) {
/* 330 */       types[i] = getType(classes[i]);
/*     */     }
/* 332 */     return types;
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
/*     */   public static Type getReturnType(String methodDescriptor) {
/* 344 */     char[] buf = methodDescriptor.toCharArray();
/* 345 */     return getType(buf, methodDescriptor.indexOf(')') + 1);
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
/*     */   public static Type getReturnType(Method method) {
/* 357 */     return getType(method.getReturnType());
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
/*     */   private static Type getType(char[] buf, int off) {
/* 369 */     switch (buf[off]) {
/*     */       case 'V':
/* 371 */         return VOID_TYPE;
/*     */       case 'Z':
/* 373 */         return BOOLEAN_TYPE;
/*     */       case 'C':
/* 375 */         return CHAR_TYPE;
/*     */       case 'B':
/* 377 */         return BYTE_TYPE;
/*     */       case 'S':
/* 379 */         return SHORT_TYPE;
/*     */       case 'I':
/* 381 */         return INT_TYPE;
/*     */       case 'F':
/* 383 */         return FLOAT_TYPE;
/*     */       case 'J':
/* 385 */         return LONG_TYPE;
/*     */       case 'D':
/* 387 */         return DOUBLE_TYPE;
/*     */       case '[':
/* 389 */         len = 1;
/* 390 */         while (buf[off + len] == '[') {
/* 391 */           len++;
/*     */         }
/* 393 */         if (buf[off + len] == 'L') {
/* 394 */           len++;
/* 395 */           while (buf[off + len] != ';') {
/* 396 */             len++;
/*     */           }
/*     */         } 
/* 399 */         return new Type(9, buf, off, len + 1);
/*     */     } 
/*     */     
/* 402 */     int len = 1;
/* 403 */     while (buf[off + len] != ';') {
/* 404 */       len++;
/*     */     }
/* 406 */     return new Type(10, buf, off + 1, len - 1);
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
/*     */   public int getSort() {
/* 424 */     return this.sort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 434 */     int i = 1;
/* 435 */     while (this.buf[this.off + i] == '[') {
/* 436 */       i++;
/*     */     }
/* 438 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getElementType() {
/* 448 */     return getType(this.buf, this.off + getDimensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*     */     StringBuffer b;
/*     */     int i;
/* 457 */     switch (this.sort) {
/*     */       case 0:
/* 459 */         return "void";
/*     */       case 1:
/* 461 */         return "boolean";
/*     */       case 2:
/* 463 */         return "char";
/*     */       case 3:
/* 465 */         return "byte";
/*     */       case 4:
/* 467 */         return "short";
/*     */       case 5:
/* 469 */         return "int";
/*     */       case 6:
/* 471 */         return "float";
/*     */       case 7:
/* 473 */         return "long";
/*     */       case 8:
/* 475 */         return "double";
/*     */       case 9:
/* 477 */         b = new StringBuffer(getElementType().getClassName());
/* 478 */         for (i = getDimensions(); i > 0; i--) {
/* 479 */           b.append("[]");
/*     */         }
/* 481 */         return b.toString();
/*     */     } 
/*     */     
/* 484 */     return (new String(this.buf, this.off, this.len)).replace('/', '.');
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
/*     */   public String getInternalName() {
/* 497 */     return new String(this.buf, this.off, this.len);
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
/*     */   public String getDescriptor() {
/* 510 */     StringBuffer buf = new StringBuffer();
/* 511 */     getDescriptor(buf);
/* 512 */     return buf.toString();
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
/*     */   public static String getMethodDescriptor(Type returnType, Type[] argumentTypes) {
/* 528 */     StringBuffer buf = new StringBuffer();
/* 529 */     buf.append('(');
/* 530 */     for (int i = 0; i < argumentTypes.length; i++) {
/* 531 */       argumentTypes[i].getDescriptor(buf);
/*     */     }
/* 533 */     buf.append(')');
/* 534 */     returnType.getDescriptor(buf);
/* 535 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getDescriptor(StringBuffer buf) {
/* 545 */     switch (this.sort) {
/*     */       case 0:
/* 547 */         buf.append('V');
/*     */         return;
/*     */       case 1:
/* 550 */         buf.append('Z');
/*     */         return;
/*     */       case 2:
/* 553 */         buf.append('C');
/*     */         return;
/*     */       case 3:
/* 556 */         buf.append('B');
/*     */         return;
/*     */       case 4:
/* 559 */         buf.append('S');
/*     */         return;
/*     */       case 5:
/* 562 */         buf.append('I');
/*     */         return;
/*     */       case 6:
/* 565 */         buf.append('F');
/*     */         return;
/*     */       case 7:
/* 568 */         buf.append('J');
/*     */         return;
/*     */       case 8:
/* 571 */         buf.append('D');
/*     */         return;
/*     */       case 9:
/* 574 */         buf.append(this.buf, this.off, this.len);
/*     */         return;
/*     */     } 
/*     */     
/* 578 */     buf.append('L');
/* 579 */     buf.append(this.buf, this.off, this.len);
/* 580 */     buf.append(';');
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
/*     */   public static String getInternalName(Class c) {
/* 598 */     return c.getName().replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Class c) {
/* 608 */     StringBuffer buf = new StringBuffer();
/* 609 */     getDescriptor(buf, c);
/* 610 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getConstructorDescriptor(Constructor c) {
/* 620 */     Class[] parameters = c.getParameterTypes();
/* 621 */     StringBuffer buf = new StringBuffer();
/* 622 */     buf.append('(');
/* 623 */     for (int i = 0; i < parameters.length; i++) {
/* 624 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 626 */     return buf.append(")V").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMethodDescriptor(Method m) {
/* 636 */     Class[] parameters = m.getParameterTypes();
/* 637 */     StringBuffer buf = new StringBuffer();
/* 638 */     buf.append('(');
/* 639 */     for (int i = 0; i < parameters.length; i++) {
/* 640 */       getDescriptor(buf, parameters[i]);
/*     */     }
/* 642 */     buf.append(')');
/* 643 */     getDescriptor(buf, m.getReturnType());
/* 644 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getDescriptor(StringBuffer buf, Class<int> c) {
/* 654 */     Class<int> d = c;
/*     */     while (true) {
/* 656 */       if (d.isPrimitive()) {
/*     */         char car;
/* 658 */         if (d == int.class) {
/* 659 */           car = 'I';
/* 660 */         } else if (d == void.class) {
/* 661 */           car = 'V';
/* 662 */         } else if (d == boolean.class) {
/* 663 */           car = 'Z';
/* 664 */         } else if (d == byte.class) {
/* 665 */           car = 'B';
/* 666 */         } else if (d == char.class) {
/* 667 */           car = 'C';
/* 668 */         } else if (d == short.class) {
/* 669 */           car = 'S';
/* 670 */         } else if (d == double.class) {
/* 671 */           car = 'D';
/* 672 */         } else if (d == float.class) {
/* 673 */           car = 'F';
/*     */         } else {
/* 675 */           car = 'J';
/*     */         } 
/* 677 */         buf.append(car); return;
/*     */       } 
/* 679 */       if (d.isArray()) {
/* 680 */         buf.append('[');
/* 681 */         d = (Class)d.getComponentType(); continue;
/*     */       }  break;
/* 683 */     }  buf.append('L');
/* 684 */     String name = d.getName();
/* 685 */     int len = name.length();
/* 686 */     for (int i = 0; i < len; i++) {
/* 687 */       char car = name.charAt(i);
/* 688 */       buf.append((car == '.') ? 47 : car);
/*     */     } 
/* 690 */     buf.append(';');
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
/*     */   public int getSize() {
/* 707 */     return (this.sort == 7 || this.sort == 8) ? 2 : 1;
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
/*     */   public int getOpcode(int opcode) {
/* 721 */     if (opcode == 46 || opcode == 79) {
/* 722 */       switch (this.sort) {
/*     */         case 1:
/*     */         case 3:
/* 725 */           return opcode + 5;
/*     */         case 2:
/* 727 */           return opcode + 6;
/*     */         case 4:
/* 729 */           return opcode + 7;
/*     */         case 5:
/* 731 */           return opcode;
/*     */         case 6:
/* 733 */           return opcode + 2;
/*     */         case 7:
/* 735 */           return opcode + 1;
/*     */         case 8:
/* 737 */           return opcode + 3;
/*     */       } 
/*     */ 
/*     */       
/* 741 */       return opcode + 4;
/*     */     } 
/*     */     
/* 744 */     switch (this.sort) {
/*     */       case 0:
/* 746 */         return opcode + 5;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 752 */         return opcode;
/*     */       case 6:
/* 754 */         return opcode + 2;
/*     */       case 7:
/* 756 */         return opcode + 1;
/*     */       case 8:
/* 758 */         return opcode + 3;
/*     */     } 
/*     */ 
/*     */     
/* 762 */     return opcode + 4;
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
/*     */   public boolean equals(Object o) {
/* 778 */     if (this == o) {
/* 779 */       return true;
/*     */     }
/* 781 */     if (!(o instanceof Type)) {
/* 782 */       return false;
/*     */     }
/* 784 */     Type t = (Type)o;
/* 785 */     if (this.sort != t.sort) {
/* 786 */       return false;
/*     */     }
/* 788 */     if (this.sort == 10 || this.sort == 9) {
/* 789 */       if (this.len != t.len) {
/* 790 */         return false;
/*     */       }
/* 792 */       for (int i = this.off, j = t.off, end = i + this.len; i < end; i++, j++) {
/* 793 */         if (this.buf[i] != t.buf[j]) {
/* 794 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 798 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 807 */     int hc = 13 * this.sort;
/* 808 */     if (this.sort == 10 || this.sort == 9) {
/* 809 */       for (int i = this.off, end = i + this.len; i < end; i++) {
/* 810 */         hc = 17 * (hc + this.buf[i]);
/*     */       }
/*     */     }
/* 813 */     return hc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 822 */     return getDescriptor();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/org/objectweb/asm/Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */