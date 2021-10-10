/*     */ package jdk.internal.org.objectweb.asm;
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
/*     */   public static final int METHOD = 11;
/* 136 */   public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final Type FLOAT_TYPE = new Type(6, null, 1174536705, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 178 */   public static final Type LONG_TYPE = new Type(7, null, 1241579778, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
/*     */ 
/*     */ 
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
/*     */   
/*     */   private final char[] buf;
/*     */ 
/*     */ 
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
/*     */ 
/*     */   
/*     */   private Type(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
/* 232 */     this.sort = paramInt1;
/* 233 */     this.buf = paramArrayOfchar;
/* 234 */     this.off = paramInt2;
/* 235 */     this.len = paramInt3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(String paramString) {
/* 246 */     return getType(paramString.toCharArray(), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getObjectType(String paramString) {
/* 257 */     char[] arrayOfChar = paramString.toCharArray();
/* 258 */     return new Type((arrayOfChar[0] == '[') ? 9 : 10, arrayOfChar, 0, arrayOfChar.length);
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
/*     */   public static Type getMethodType(String paramString) {
/* 270 */     return getType(paramString.toCharArray(), 0);
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
/*     */   public static Type getMethodType(Type paramType, Type... paramVarArgs) {
/* 286 */     return getType(getMethodDescriptor(paramType, paramVarArgs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Class<?> paramClass) {
/* 297 */     if (paramClass.isPrimitive()) {
/* 298 */       if (paramClass == int.class)
/* 299 */         return INT_TYPE; 
/* 300 */       if (paramClass == void.class)
/* 301 */         return VOID_TYPE; 
/* 302 */       if (paramClass == boolean.class)
/* 303 */         return BOOLEAN_TYPE; 
/* 304 */       if (paramClass == byte.class)
/* 305 */         return BYTE_TYPE; 
/* 306 */       if (paramClass == char.class)
/* 307 */         return CHAR_TYPE; 
/* 308 */       if (paramClass == short.class)
/* 309 */         return SHORT_TYPE; 
/* 310 */       if (paramClass == double.class)
/* 311 */         return DOUBLE_TYPE; 
/* 312 */       if (paramClass == float.class) {
/* 313 */         return FLOAT_TYPE;
/*     */       }
/* 315 */       return LONG_TYPE;
/*     */     } 
/*     */     
/* 318 */     return getType(getDescriptor(paramClass));
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
/*     */   public static Type getType(Constructor<?> paramConstructor) {
/* 330 */     return getType(getConstructorDescriptor(paramConstructor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Type getType(Method paramMethod) {
/* 341 */     return getType(getMethodDescriptor(paramMethod));
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
/*     */   public static Type[] getArgumentTypes(String paramString) {
/* 354 */     char[] arrayOfChar = paramString.toCharArray();
/* 355 */     int i = 1;
/* 356 */     byte b = 0;
/*     */     while (true) {
/* 358 */       char c = arrayOfChar[i++];
/* 359 */       if (c == ')')
/*     */         break; 
/* 361 */       if (c == 'L') {
/* 362 */         while (arrayOfChar[i++] != ';');
/*     */         
/* 364 */         b++; continue;
/* 365 */       }  if (c != '[') {
/* 366 */         b++;
/*     */       }
/*     */     } 
/* 369 */     Type[] arrayOfType = new Type[b];
/* 370 */     i = 1;
/* 371 */     b = 0;
/* 372 */     while (arrayOfChar[i] != ')') {
/* 373 */       arrayOfType[b] = getType(arrayOfChar, i);
/* 374 */       i += (arrayOfType[b]).len + (((arrayOfType[b]).sort == 10) ? 2 : 0);
/* 375 */       b++;
/*     */     } 
/* 377 */     return arrayOfType;
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
/*     */   public static Type[] getArgumentTypes(Method paramMethod) {
/* 390 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 391 */     Type[] arrayOfType = new Type[arrayOfClass.length];
/* 392 */     for (int i = arrayOfClass.length - 1; i >= 0; i--) {
/* 393 */       arrayOfType[i] = getType(arrayOfClass[i]);
/*     */     }
/* 395 */     return arrayOfType;
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
/*     */   public static Type getReturnType(String paramString) {
/* 408 */     char[] arrayOfChar = paramString.toCharArray();
/* 409 */     return getType(arrayOfChar, paramString.indexOf(')') + 1);
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
/*     */   public static Type getReturnType(Method paramMethod) {
/* 422 */     return getType(paramMethod.getReturnType());
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
/*     */   public static int getArgumentsAndReturnSizes(String paramString) {
/* 437 */     byte b1 = 1;
/* 438 */     byte b2 = 1;
/*     */     while (true) {
/* 440 */       char c = paramString.charAt(b2++);
/* 441 */       if (c == ')') {
/* 442 */         c = paramString.charAt(b2);
/* 443 */         return b1 << 2 | ((c == 'V') ? 0 : ((c == 'D' || c == 'J') ? 2 : 1));
/*     */       } 
/* 445 */       if (c == 'L') {
/* 446 */         while (paramString.charAt(b2++) != ';');
/*     */         
/* 448 */         b1++; continue;
/* 449 */       }  if (c == '[') {
/* 450 */         while ((c = paramString.charAt(b2)) == '[') {
/* 451 */           b2++;
/*     */         }
/* 453 */         if (c == 'D' || c == 'J')
/* 454 */           b1--;  continue;
/*     */       } 
/* 456 */       if (c == 'D' || c == 'J') {
/* 457 */         b1 += 2; continue;
/*     */       } 
/* 459 */       b1++;
/*     */     } 
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
/*     */   private static Type getType(char[] paramArrayOfchar, int paramInt) {
/*     */     byte b;
/* 477 */     switch (paramArrayOfchar[paramInt]) {
/*     */       case 'V':
/* 479 */         return VOID_TYPE;
/*     */       case 'Z':
/* 481 */         return BOOLEAN_TYPE;
/*     */       case 'C':
/* 483 */         return CHAR_TYPE;
/*     */       case 'B':
/* 485 */         return BYTE_TYPE;
/*     */       case 'S':
/* 487 */         return SHORT_TYPE;
/*     */       case 'I':
/* 489 */         return INT_TYPE;
/*     */       case 'F':
/* 491 */         return FLOAT_TYPE;
/*     */       case 'J':
/* 493 */         return LONG_TYPE;
/*     */       case 'D':
/* 495 */         return DOUBLE_TYPE;
/*     */       case '[':
/* 497 */         b = 1;
/* 498 */         while (paramArrayOfchar[paramInt + b] == '[') {
/* 499 */           b++;
/*     */         }
/* 501 */         if (paramArrayOfchar[paramInt + b] == 'L') {
/* 502 */           b++;
/* 503 */           while (paramArrayOfchar[paramInt + b] != ';') {
/* 504 */             b++;
/*     */           }
/*     */         } 
/* 507 */         return new Type(9, paramArrayOfchar, paramInt, b + 1);
/*     */       case 'L':
/* 509 */         b = 1;
/* 510 */         while (paramArrayOfchar[paramInt + b] != ';') {
/* 511 */           b++;
/*     */         }
/* 513 */         return new Type(10, paramArrayOfchar, paramInt + 1, b - 1);
/*     */     } 
/*     */     
/* 516 */     return new Type(11, paramArrayOfchar, paramInt, paramArrayOfchar.length - paramInt);
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
/* 534 */     return this.sort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimensions() {
/* 544 */     byte b = 1;
/* 545 */     while (this.buf[this.off + b] == '[') {
/* 546 */       b++;
/*     */     }
/* 548 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getElementType() {
/* 558 */     return getType(this.buf, this.off + getDimensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/*     */     StringBuilder stringBuilder;
/*     */     int i;
/* 568 */     switch (this.sort) {
/*     */       case 0:
/* 570 */         return "void";
/*     */       case 1:
/* 572 */         return "boolean";
/*     */       case 2:
/* 574 */         return "char";
/*     */       case 3:
/* 576 */         return "byte";
/*     */       case 4:
/* 578 */         return "short";
/*     */       case 5:
/* 580 */         return "int";
/*     */       case 6:
/* 582 */         return "float";
/*     */       case 7:
/* 584 */         return "long";
/*     */       case 8:
/* 586 */         return "double";
/*     */       case 9:
/* 588 */         stringBuilder = new StringBuilder(getElementType().getClassName());
/* 589 */         for (i = getDimensions(); i > 0; i--) {
/* 590 */           stringBuilder.append("[]");
/*     */         }
/* 592 */         return stringBuilder.toString();
/*     */       case 10:
/* 594 */         return (new String(this.buf, this.off, this.len)).replace('/', '.');
/*     */     } 
/* 596 */     return null;
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
/* 609 */     return new String(this.buf, this.off, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getArgumentTypes() {
/* 619 */     return getArgumentTypes(getDescriptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getReturnType() {
/* 629 */     return getReturnType(getDescriptor());
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
/*     */   public int getArgumentsAndReturnSizes() {
/* 644 */     return getArgumentsAndReturnSizes(getDescriptor());
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
/* 657 */     StringBuffer stringBuffer = new StringBuffer();
/* 658 */     getDescriptor(stringBuffer);
/* 659 */     return stringBuffer.toString();
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
/*     */   public static String getMethodDescriptor(Type paramType, Type... paramVarArgs) {
/* 675 */     StringBuffer stringBuffer = new StringBuffer();
/* 676 */     stringBuffer.append('(');
/* 677 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 678 */       paramVarArgs[b].getDescriptor(stringBuffer);
/*     */     }
/* 680 */     stringBuffer.append(')');
/* 681 */     paramType.getDescriptor(stringBuffer);
/* 682 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getDescriptor(StringBuffer paramStringBuffer) {
/* 693 */     if (this.buf == null) {
/*     */ 
/*     */       
/* 696 */       paramStringBuffer.append((char)((this.off & 0xFF000000) >>> 24));
/* 697 */     } else if (this.sort == 10) {
/* 698 */       paramStringBuffer.append('L');
/* 699 */       paramStringBuffer.append(this.buf, this.off, this.len);
/* 700 */       paramStringBuffer.append(';');
/*     */     } else {
/* 702 */       paramStringBuffer.append(this.buf, this.off, this.len);
/*     */     } 
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
/*     */   public static String getInternalName(Class<?> paramClass) {
/* 721 */     return paramClass.getName().replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDescriptor(Class<?> paramClass) {
/* 732 */     StringBuffer stringBuffer = new StringBuffer();
/* 733 */     getDescriptor(stringBuffer, paramClass);
/* 734 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getConstructorDescriptor(Constructor<?> paramConstructor) {
/* 745 */     Class[] arrayOfClass = paramConstructor.getParameterTypes();
/* 746 */     StringBuffer stringBuffer = new StringBuffer();
/* 747 */     stringBuffer.append('(');
/* 748 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 749 */       getDescriptor(stringBuffer, arrayOfClass[b]);
/*     */     }
/* 751 */     return stringBuffer.append(")V").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMethodDescriptor(Method paramMethod) {
/* 762 */     Class[] arrayOfClass = paramMethod.getParameterTypes();
/* 763 */     StringBuffer stringBuffer = new StringBuffer();
/* 764 */     stringBuffer.append('(');
/* 765 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/* 766 */       getDescriptor(stringBuffer, arrayOfClass[b]);
/*     */     }
/* 768 */     stringBuffer.append(')');
/* 769 */     getDescriptor(stringBuffer, paramMethod.getReturnType());
/* 770 */     return stringBuffer.toString();
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
/*     */   private static void getDescriptor(StringBuffer paramStringBuffer, Class<?> paramClass) {
/* 782 */     Class<?> clazz = paramClass;
/*     */     while (true) {
/* 784 */       if (clazz.isPrimitive()) {
/*     */         byte b1;
/* 786 */         if (clazz == int.class) {
/* 787 */           b1 = 73;
/* 788 */         } else if (clazz == void.class) {
/* 789 */           b1 = 86;
/* 790 */         } else if (clazz == boolean.class) {
/* 791 */           b1 = 90;
/* 792 */         } else if (clazz == byte.class) {
/* 793 */           b1 = 66;
/* 794 */         } else if (clazz == char.class) {
/* 795 */           b1 = 67;
/* 796 */         } else if (clazz == short.class) {
/* 797 */           b1 = 83;
/* 798 */         } else if (clazz == double.class) {
/* 799 */           b1 = 68;
/* 800 */         } else if (clazz == float.class) {
/* 801 */           b1 = 70;
/*     */         } else {
/* 803 */           b1 = 74;
/*     */         } 
/* 805 */         paramStringBuffer.append(b1); return;
/*     */       } 
/* 807 */       if (clazz.isArray()) {
/* 808 */         paramStringBuffer.append('[');
/* 809 */         clazz = clazz.getComponentType(); continue;
/*     */       }  break;
/* 811 */     }  paramStringBuffer.append('L');
/* 812 */     String str = clazz.getName();
/* 813 */     int i = str.length();
/* 814 */     for (byte b = 0; b < i; b++) {
/* 815 */       char c = str.charAt(b);
/* 816 */       paramStringBuffer.append((c == '.') ? 47 : c);
/*     */     } 
/* 818 */     paramStringBuffer.append(';');
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
/*     */   public int getSize() {
/* 837 */     return (this.buf == null) ? (this.off & 0xFF) : 1;
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
/*     */   public int getOpcode(int paramInt) {
/* 853 */     if (paramInt == 46 || paramInt == 79)
/*     */     {
/*     */       
/* 856 */       return paramInt + ((this.buf == null) ? ((this.off & 0xFF00) >> 8) : 4);
/*     */     }
/*     */ 
/*     */     
/* 860 */     return paramInt + ((this.buf == null) ? ((this.off & 0xFF0000) >> 16) : 4);
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
/*     */   public boolean equals(Object paramObject) {
/* 877 */     if (this == paramObject) {
/* 878 */       return true;
/*     */     }
/* 880 */     if (!(paramObject instanceof Type)) {
/* 881 */       return false;
/*     */     }
/* 883 */     Type type = (Type)paramObject;
/* 884 */     if (this.sort != type.sort) {
/* 885 */       return false;
/*     */     }
/* 887 */     if (this.sort >= 9) {
/* 888 */       if (this.len != type.len) {
/* 889 */         return false;
/*     */       }
/* 891 */       for (int i = this.off, j = type.off, k = i + this.len; i < k; i++, j++) {
/* 892 */         if (this.buf[i] != type.buf[j]) {
/* 893 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 897 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 907 */     int i = 13 * this.sort;
/* 908 */     if (this.sort >= 9) {
/* 909 */       for (int j = this.off, k = j + this.len; j < k; j++) {
/* 910 */         i = 17 * (i + this.buf[j]);
/*     */       }
/*     */     }
/* 913 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 923 */     return getDescriptor();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */