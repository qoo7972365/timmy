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
/*      */ final class Frame
/*      */ {
/*      */   static final int DIM = -268435456;
/*      */   static final int ARRAY_OF = 268435456;
/*      */   static final int ELEMENT_OF = -268435456;
/*      */   static final int KIND = 251658240;
/*      */   static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
/*      */   static final int VALUE = 8388607;
/*      */   static final int BASE_KIND = 267386880;
/*      */   static final int BASE_VALUE = 1048575;
/*      */   static final int BASE = 16777216;
/*      */   static final int OBJECT = 24117248;
/*      */   static final int UNINITIALIZED = 25165824;
/*      */   private static final int LOCAL = 33554432;
/*      */   private static final int STACK = 50331648;
/*      */   static final int TOP = 16777216;
/*      */   static final int BOOLEAN = 16777225;
/*      */   static final int BYTE = 16777226;
/*      */   static final int CHAR = 16777227;
/*      */   static final int SHORT = 16777228;
/*      */   static final int INTEGER = 16777217;
/*      */   static final int FLOAT = 16777218;
/*      */   static final int DOUBLE = 16777219;
/*      */   static final int LONG = 16777220;
/*      */   static final int NULL = 16777221;
/*      */   static final int UNINITIALIZED_THIS = 16777222;
/*      */   static final int[] SIZE;
/*      */   Label owner;
/*      */   int[] inputLocals;
/*      */   int[] inputStack;
/*      */   private int[] outputLocals;
/*      */   private int[] outputStack;
/*      */   private int outputStackTop;
/*      */   private int initializationCount;
/*      */   private int[] initializations;
/*      */   
/*      */   static {
/*  268 */     int[] arrayOfInt = new int[202];
/*  269 */     String str = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
/*      */ 
/*      */ 
/*      */     
/*  273 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/*  274 */       arrayOfInt[b] = str.charAt(b) - 69;
/*      */     }
/*  276 */     SIZE = arrayOfInt;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int get(int paramInt) {
/*  560 */     if (this.outputLocals == null || paramInt >= this.outputLocals.length)
/*      */     {
/*      */       
/*  563 */       return 0x2000000 | paramInt;
/*      */     }
/*  565 */     int i = this.outputLocals[paramInt];
/*  566 */     if (i == 0)
/*      */     {
/*      */       
/*  569 */       i = this.outputLocals[paramInt] = 0x2000000 | paramInt;
/*      */     }
/*  571 */     return i;
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
/*      */   private void set(int paramInt1, int paramInt2) {
/*  585 */     if (this.outputLocals == null) {
/*  586 */       this.outputLocals = new int[10];
/*      */     }
/*  588 */     int i = this.outputLocals.length;
/*  589 */     if (paramInt1 >= i) {
/*  590 */       int[] arrayOfInt = new int[Math.max(paramInt1 + 1, 2 * i)];
/*  591 */       System.arraycopy(this.outputLocals, 0, arrayOfInt, 0, i);
/*  592 */       this.outputLocals = arrayOfInt;
/*      */     } 
/*      */     
/*  595 */     this.outputLocals[paramInt1] = paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void push(int paramInt) {
/*  606 */     if (this.outputStack == null) {
/*  607 */       this.outputStack = new int[10];
/*      */     }
/*  609 */     int i = this.outputStack.length;
/*  610 */     if (this.outputStackTop >= i) {
/*  611 */       int[] arrayOfInt = new int[Math.max(this.outputStackTop + 1, 2 * i)];
/*  612 */       System.arraycopy(this.outputStack, 0, arrayOfInt, 0, i);
/*  613 */       this.outputStack = arrayOfInt;
/*      */     } 
/*      */     
/*  616 */     this.outputStack[this.outputStackTop++] = paramInt;
/*      */     
/*  618 */     int j = this.owner.inputStackTop + this.outputStackTop;
/*  619 */     if (j > this.owner.outputStackMax) {
/*  620 */       this.owner.outputStackMax = j;
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
/*      */   private void push(ClassWriter paramClassWriter, String paramString) {
/*  635 */     int i = type(paramClassWriter, paramString);
/*  636 */     if (i != 0) {
/*  637 */       push(i);
/*  638 */       if (i == 16777220 || i == 16777219) {
/*  639 */         push(16777216);
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
/*      */   private static int type(ClassWriter paramClassWriter, String paramString) {
/*  655 */     byte b = (paramString.charAt(0) == '(') ? (paramString.indexOf(')') + 1) : 0;
/*  656 */     switch (paramString.charAt(b)) {
/*      */       case 'V':
/*  658 */         return 0;
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'I':
/*      */       case 'S':
/*      */       case 'Z':
/*  664 */         return 16777217;
/*      */       case 'F':
/*  666 */         return 16777218;
/*      */       case 'J':
/*  668 */         return 16777220;
/*      */       case 'D':
/*  670 */         return 16777219;
/*      */       
/*      */       case 'L':
/*  673 */         str = paramString.substring(b + 1, paramString.length() - 1);
/*  674 */         return 0x1700000 | paramClassWriter.addType(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  679 */     int j = b + 1;
/*  680 */     while (paramString.charAt(j) == '[') {
/*  681 */       j++;
/*      */     }
/*  683 */     switch (paramString.charAt(j))
/*      */     { case 'Z':
/*  685 */         i = 16777225;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  714 */         return j - b << 28 | i;case 'C': i = 16777227; return j - b << 28 | i;case 'B': i = 16777226; return j - b << 28 | i;case 'S': i = 16777228; return j - b << 28 | i;case 'I': i = 16777217; return j - b << 28 | i;case 'F': i = 16777218; return j - b << 28 | i;case 'J': i = 16777220; return j - b << 28 | i;case 'D': i = 16777219; return j - b << 28 | i; }  String str = paramString.substring(j + 1, paramString.length() - 1); int i = 0x1700000 | paramClassWriter.addType(str); return j - b << 28 | i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int pop() {
/*  724 */     if (this.outputStackTop > 0) {
/*  725 */       return this.outputStack[--this.outputStackTop];
/*      */     }
/*      */     
/*  728 */     return 0x3000000 | ---this.owner.inputStackTop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void pop(int paramInt) {
/*  739 */     if (this.outputStackTop >= paramInt) {
/*  740 */       this.outputStackTop -= paramInt;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  745 */       this.owner.inputStackTop -= paramInt - this.outputStackTop;
/*  746 */       this.outputStackTop = 0;
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
/*      */   private void pop(String paramString) {
/*  759 */     char c = paramString.charAt(0);
/*  760 */     if (c == '(') {
/*  761 */       pop((Type.getArgumentsAndReturnSizes(paramString) >> 2) - 1);
/*  762 */     } else if (c == 'J' || c == 'D') {
/*  763 */       pop(2);
/*      */     } else {
/*  765 */       pop(1);
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
/*      */   private void init(int paramInt) {
/*  778 */     if (this.initializations == null) {
/*  779 */       this.initializations = new int[2];
/*      */     }
/*  781 */     int i = this.initializations.length;
/*  782 */     if (this.initializationCount >= i) {
/*  783 */       int[] arrayOfInt = new int[Math.max(this.initializationCount + 1, 2 * i)];
/*  784 */       System.arraycopy(this.initializations, 0, arrayOfInt, 0, i);
/*  785 */       this.initializations = arrayOfInt;
/*      */     } 
/*      */     
/*  788 */     this.initializations[this.initializationCount++] = paramInt;
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
/*      */   private int init(ClassWriter paramClassWriter, int paramInt) {
/*      */     int i;
/*  804 */     if (paramInt == 16777222) {
/*  805 */       i = 0x1700000 | paramClassWriter.addType(paramClassWriter.thisName);
/*  806 */     } else if ((paramInt & 0xFFF00000) == 25165824) {
/*  807 */       String str = (paramClassWriter.typeTable[paramInt & 0xFFFFF]).strVal1;
/*  808 */       i = 0x1700000 | paramClassWriter.addType(str);
/*      */     } else {
/*  810 */       return paramInt;
/*      */     } 
/*  812 */     for (byte b = 0; b < this.initializationCount; b++) {
/*  813 */       int j = this.initializations[b];
/*  814 */       int k = j & 0xF0000000;
/*  815 */       int m = j & 0xF000000;
/*  816 */       if (m == 33554432) {
/*  817 */         j = k + this.inputLocals[j & 0x7FFFFF];
/*  818 */       } else if (m == 50331648) {
/*  819 */         j = k + this.inputStack[this.inputStack.length - (j & 0x7FFFFF)];
/*      */       } 
/*  821 */       if (paramInt == j) {
/*  822 */         return i;
/*      */       }
/*      */     } 
/*  825 */     return paramInt;
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
/*      */   void initInputFrame(ClassWriter paramClassWriter, int paramInt1, Type[] paramArrayOfType, int paramInt2) {
/*  843 */     this.inputLocals = new int[paramInt2];
/*  844 */     this.inputStack = new int[0];
/*  845 */     byte b1 = 0;
/*  846 */     if ((paramInt1 & 0x8) == 0) {
/*  847 */       if ((paramInt1 & 0x80000) == 0) {
/*  848 */         this.inputLocals[b1++] = 0x1700000 | paramClassWriter.addType(paramClassWriter.thisName);
/*      */       } else {
/*  850 */         this.inputLocals[b1++] = 16777222;
/*      */       } 
/*      */     }
/*  853 */     for (byte b2 = 0; b2 < paramArrayOfType.length; b2++) {
/*  854 */       int i = type(paramClassWriter, paramArrayOfType[b2].getDescriptor());
/*  855 */       this.inputLocals[b1++] = i;
/*  856 */       if (i == 16777220 || i == 16777219) {
/*  857 */         this.inputLocals[b1++] = 16777216;
/*      */       }
/*      */     } 
/*  860 */     while (b1 < paramInt2) {
/*  861 */       this.inputLocals[b1++] = 16777216;
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
/*      */   void execute(int paramInt1, int paramInt2, ClassWriter paramClassWriter, Item paramItem) {
/*      */     int i, j, k, m;
/*      */     String str;
/*  880 */     switch (paramInt1) {
/*      */       case 0:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 145:
/*      */       case 146:
/*      */       case 147:
/*      */       case 167:
/*      */       case 177:
/*      */         return;
/*      */       case 1:
/*  893 */         push(16777221);
/*      */       
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 16:
/*      */       case 17:
/*      */       case 21:
/*  905 */         push(16777217);
/*      */       
/*      */       case 9:
/*      */       case 10:
/*      */       case 22:
/*  910 */         push(16777220);
/*  911 */         push(16777216);
/*      */       
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 23:
/*  917 */         push(16777218);
/*      */       
/*      */       case 14:
/*      */       case 15:
/*      */       case 24:
/*  922 */         push(16777219);
/*  923 */         push(16777216);
/*      */       
/*      */       case 18:
/*  926 */         switch (paramItem.type) {
/*      */           case 3:
/*  928 */             push(16777217);
/*      */           
/*      */           case 5:
/*  931 */             push(16777220);
/*  932 */             push(16777216);
/*      */           
/*      */           case 4:
/*  935 */             push(16777218);
/*      */           
/*      */           case 6:
/*  938 */             push(16777219);
/*  939 */             push(16777216);
/*      */           
/*      */           case 7:
/*  942 */             push(0x1700000 | paramClassWriter.addType("java/lang/Class"));
/*      */           
/*      */           case 8:
/*  945 */             push(0x1700000 | paramClassWriter.addType("java/lang/String"));
/*      */           
/*      */           case 16:
/*  948 */             push(0x1700000 | paramClassWriter.addType("java/lang/invoke/MethodType"));
/*      */         } 
/*      */ 
/*      */         
/*  952 */         push(0x1700000 | paramClassWriter.addType("java/lang/invoke/MethodHandle"));
/*      */ 
/*      */       
/*      */       case 25:
/*  956 */         push(get(paramInt2));
/*      */       
/*      */       case 46:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*  962 */         pop(2);
/*  963 */         push(16777217);
/*      */       
/*      */       case 47:
/*      */       case 143:
/*  967 */         pop(2);
/*  968 */         push(16777220);
/*  969 */         push(16777216);
/*      */       
/*      */       case 48:
/*  972 */         pop(2);
/*  973 */         push(16777218);
/*      */       
/*      */       case 49:
/*      */       case 138:
/*  977 */         pop(2);
/*  978 */         push(16777219);
/*  979 */         push(16777216);
/*      */       
/*      */       case 50:
/*  982 */         pop(1);
/*  983 */         i = pop();
/*  984 */         push(-268435456 + i);
/*      */       
/*      */       case 54:
/*      */       case 56:
/*      */       case 58:
/*  989 */         i = pop();
/*  990 */         set(paramInt2, i);
/*  991 */         if (paramInt2 > 0) {
/*  992 */           int n = get(paramInt2 - 1);
/*      */           
/*  994 */           if (n == 16777220 || n == 16777219) {
/*  995 */             set(paramInt2 - 1, 16777216);
/*  996 */           } else if ((n & 0xF000000) != 16777216) {
/*  997 */             set(paramInt2 - 1, n | 0x800000);
/*      */           } 
/*      */         } 
/*      */       
/*      */       case 55:
/*      */       case 57:
/* 1003 */         pop(1);
/* 1004 */         i = pop();
/* 1005 */         set(paramInt2, i);
/* 1006 */         set(paramInt2 + 1, 16777216);
/* 1007 */         if (paramInt2 > 0) {
/* 1008 */           int n = get(paramInt2 - 1);
/*      */           
/* 1010 */           if (n == 16777220 || n == 16777219) {
/* 1011 */             set(paramInt2 - 1, 16777216);
/* 1012 */           } else if ((n & 0xF000000) != 16777216) {
/* 1013 */             set(paramInt2 - 1, n | 0x800000);
/*      */           } 
/*      */         } 
/*      */       
/*      */       case 79:
/*      */       case 81:
/*      */       case 83:
/*      */       case 84:
/*      */       case 85:
/*      */       case 86:
/* 1023 */         pop(3);
/*      */       
/*      */       case 80:
/*      */       case 82:
/* 1027 */         pop(4);
/*      */       
/*      */       case 87:
/*      */       case 153:
/*      */       case 154:
/*      */       case 155:
/*      */       case 156:
/*      */       case 157:
/*      */       case 158:
/*      */       case 170:
/*      */       case 171:
/*      */       case 172:
/*      */       case 174:
/*      */       case 176:
/*      */       case 191:
/*      */       case 194:
/*      */       case 195:
/*      */       case 198:
/*      */       case 199:
/* 1046 */         pop(1);
/*      */       
/*      */       case 88:
/*      */       case 159:
/*      */       case 160:
/*      */       case 161:
/*      */       case 162:
/*      */       case 163:
/*      */       case 164:
/*      */       case 165:
/*      */       case 166:
/*      */       case 173:
/*      */       case 175:
/* 1059 */         pop(2);
/*      */       
/*      */       case 89:
/* 1062 */         i = pop();
/* 1063 */         push(i);
/* 1064 */         push(i);
/*      */       
/*      */       case 90:
/* 1067 */         i = pop();
/* 1068 */         j = pop();
/* 1069 */         push(i);
/* 1070 */         push(j);
/* 1071 */         push(i);
/*      */       
/*      */       case 91:
/* 1074 */         i = pop();
/* 1075 */         j = pop();
/* 1076 */         k = pop();
/* 1077 */         push(i);
/* 1078 */         push(k);
/* 1079 */         push(j);
/* 1080 */         push(i);
/*      */       
/*      */       case 92:
/* 1083 */         i = pop();
/* 1084 */         j = pop();
/* 1085 */         push(j);
/* 1086 */         push(i);
/* 1087 */         push(j);
/* 1088 */         push(i);
/*      */       
/*      */       case 93:
/* 1091 */         i = pop();
/* 1092 */         j = pop();
/* 1093 */         k = pop();
/* 1094 */         push(j);
/* 1095 */         push(i);
/* 1096 */         push(k);
/* 1097 */         push(j);
/* 1098 */         push(i);
/*      */       
/*      */       case 94:
/* 1101 */         i = pop();
/* 1102 */         j = pop();
/* 1103 */         k = pop();
/* 1104 */         m = pop();
/* 1105 */         push(j);
/* 1106 */         push(i);
/* 1107 */         push(m);
/* 1108 */         push(k);
/* 1109 */         push(j);
/* 1110 */         push(i);
/*      */       
/*      */       case 95:
/* 1113 */         i = pop();
/* 1114 */         j = pop();
/* 1115 */         push(i);
/* 1116 */         push(j);
/*      */       
/*      */       case 96:
/*      */       case 100:
/*      */       case 104:
/*      */       case 108:
/*      */       case 112:
/*      */       case 120:
/*      */       case 122:
/*      */       case 124:
/*      */       case 126:
/*      */       case 128:
/*      */       case 130:
/*      */       case 136:
/*      */       case 142:
/*      */       case 149:
/*      */       case 150:
/* 1133 */         pop(2);
/* 1134 */         push(16777217);
/*      */       
/*      */       case 97:
/*      */       case 101:
/*      */       case 105:
/*      */       case 109:
/*      */       case 113:
/*      */       case 127:
/*      */       case 129:
/*      */       case 131:
/* 1144 */         pop(4);
/* 1145 */         push(16777220);
/* 1146 */         push(16777216);
/*      */       
/*      */       case 98:
/*      */       case 102:
/*      */       case 106:
/*      */       case 110:
/*      */       case 114:
/*      */       case 137:
/*      */       case 144:
/* 1155 */         pop(2);
/* 1156 */         push(16777218);
/*      */       
/*      */       case 99:
/*      */       case 103:
/*      */       case 107:
/*      */       case 111:
/*      */       case 115:
/* 1163 */         pop(4);
/* 1164 */         push(16777219);
/* 1165 */         push(16777216);
/*      */       
/*      */       case 121:
/*      */       case 123:
/*      */       case 125:
/* 1170 */         pop(3);
/* 1171 */         push(16777220);
/* 1172 */         push(16777216);
/*      */       
/*      */       case 132:
/* 1175 */         set(paramInt2, 16777217);
/*      */       
/*      */       case 133:
/*      */       case 140:
/* 1179 */         pop(1);
/* 1180 */         push(16777220);
/* 1181 */         push(16777216);
/*      */       
/*      */       case 134:
/* 1184 */         pop(1);
/* 1185 */         push(16777218);
/*      */       
/*      */       case 135:
/*      */       case 141:
/* 1189 */         pop(1);
/* 1190 */         push(16777219);
/* 1191 */         push(16777216);
/*      */       
/*      */       case 139:
/*      */       case 190:
/*      */       case 193:
/* 1196 */         pop(1);
/* 1197 */         push(16777217);
/*      */       
/*      */       case 148:
/*      */       case 151:
/*      */       case 152:
/* 1202 */         pop(4);
/* 1203 */         push(16777217);
/*      */       
/*      */       case 168:
/*      */       case 169:
/* 1207 */         throw new RuntimeException("JSR/RET are not supported with computeFrames option");
/*      */       
/*      */       case 178:
/* 1210 */         push(paramClassWriter, paramItem.strVal3);
/*      */       
/*      */       case 179:
/* 1213 */         pop(paramItem.strVal3);
/*      */       
/*      */       case 180:
/* 1216 */         pop(1);
/* 1217 */         push(paramClassWriter, paramItem.strVal3);
/*      */       
/*      */       case 181:
/* 1220 */         pop(paramItem.strVal3);
/* 1221 */         pop();
/*      */       
/*      */       case 182:
/*      */       case 183:
/*      */       case 184:
/*      */       case 185:
/* 1227 */         pop(paramItem.strVal3);
/* 1228 */         if (paramInt1 != 184) {
/* 1229 */           i = pop();
/* 1230 */           if (paramInt1 == 183 && paramItem.strVal2
/* 1231 */             .charAt(0) == '<') {
/* 1232 */             init(i);
/*      */           }
/*      */         } 
/* 1235 */         push(paramClassWriter, paramItem.strVal3);
/*      */       
/*      */       case 186:
/* 1238 */         pop(paramItem.strVal2);
/* 1239 */         push(paramClassWriter, paramItem.strVal2);
/*      */       
/*      */       case 187:
/* 1242 */         push(0x1800000 | paramClassWriter.addUninitializedType(paramItem.strVal1, paramInt2));
/*      */       
/*      */       case 188:
/* 1245 */         pop();
/* 1246 */         switch (paramInt2) {
/*      */           case 4:
/* 1248 */             push(285212681);
/*      */           
/*      */           case 5:
/* 1251 */             push(285212683);
/*      */           
/*      */           case 8:
/* 1254 */             push(285212682);
/*      */           
/*      */           case 9:
/* 1257 */             push(285212684);
/*      */           
/*      */           case 10:
/* 1260 */             push(285212673);
/*      */           
/*      */           case 6:
/* 1263 */             push(285212674);
/*      */           
/*      */           case 7:
/* 1266 */             push(285212675);
/*      */         } 
/*      */ 
/*      */         
/* 1270 */         push(285212676);
/*      */ 
/*      */ 
/*      */       
/*      */       case 189:
/* 1275 */         str = paramItem.strVal1;
/* 1276 */         pop();
/* 1277 */         if (str.charAt(0) == '[') {
/* 1278 */           push(paramClassWriter, '[' + str);
/*      */         } else {
/* 1280 */           push(0x11700000 | paramClassWriter.addType(str));
/*      */         } 
/*      */       
/*      */       case 192:
/* 1284 */         str = paramItem.strVal1;
/* 1285 */         pop();
/* 1286 */         if (str.charAt(0) == '[') {
/* 1287 */           push(paramClassWriter, str);
/*      */         } else {
/* 1289 */           push(0x1700000 | paramClassWriter.addType(str));
/*      */         } 
/*      */     } 
/*      */ 
/*      */     
/* 1294 */     pop(paramInt2);
/* 1295 */     push(paramClassWriter, paramItem.strVal1);
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
/*      */   boolean merge(ClassWriter paramClassWriter, Frame paramFrame, int paramInt) {
/* 1316 */     boolean bool = false;
/*      */ 
/*      */     
/* 1319 */     int i = this.inputLocals.length;
/* 1320 */     int j = this.inputStack.length;
/* 1321 */     if (paramFrame.inputLocals == null) {
/* 1322 */       paramFrame.inputLocals = new int[i];
/* 1323 */       bool = true;
/*      */     } 
/*      */     byte b;
/* 1326 */     for (b = 0; b < i; b++) {
/* 1327 */       int m; if (this.outputLocals != null && b < this.outputLocals.length) {
/* 1328 */         int n = this.outputLocals[b];
/* 1329 */         if (n == 0) {
/* 1330 */           m = this.inputLocals[b];
/*      */         } else {
/* 1332 */           int i1 = n & 0xF0000000;
/* 1333 */           int i2 = n & 0xF000000;
/* 1334 */           if (i2 == 16777216) {
/* 1335 */             m = n;
/*      */           } else {
/* 1337 */             if (i2 == 33554432) {
/* 1338 */               m = i1 + this.inputLocals[n & 0x7FFFFF];
/*      */             } else {
/* 1340 */               m = i1 + this.inputStack[j - (n & 0x7FFFFF)];
/*      */             } 
/* 1342 */             if ((n & 0x800000) != 0 && (m == 16777220 || m == 16777219))
/*      */             {
/* 1344 */               m = 16777216;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1349 */         m = this.inputLocals[b];
/*      */       } 
/* 1351 */       if (this.initializations != null) {
/* 1352 */         m = init(paramClassWriter, m);
/*      */       }
/* 1354 */       bool |= merge(paramClassWriter, m, paramFrame.inputLocals, b);
/*      */     } 
/*      */     
/* 1357 */     if (paramInt > 0) {
/* 1358 */       for (b = 0; b < i; b++) {
/* 1359 */         int m = this.inputLocals[b];
/* 1360 */         bool |= merge(paramClassWriter, m, paramFrame.inputLocals, b);
/*      */       } 
/* 1362 */       if (paramFrame.inputStack == null) {
/* 1363 */         paramFrame.inputStack = new int[1];
/* 1364 */         bool = true;
/*      */       } 
/* 1366 */       bool |= merge(paramClassWriter, paramInt, paramFrame.inputStack, 0);
/* 1367 */       return bool;
/*      */     } 
/*      */     
/* 1370 */     int k = this.inputStack.length + this.owner.inputStackTop;
/* 1371 */     if (paramFrame.inputStack == null) {
/* 1372 */       paramFrame.inputStack = new int[k + this.outputStackTop];
/* 1373 */       bool = true;
/*      */     } 
/*      */     
/* 1376 */     for (b = 0; b < k; b++) {
/* 1377 */       int m = this.inputStack[b];
/* 1378 */       if (this.initializations != null) {
/* 1379 */         m = init(paramClassWriter, m);
/*      */       }
/* 1381 */       bool |= merge(paramClassWriter, m, paramFrame.inputStack, b);
/*      */     } 
/* 1383 */     for (b = 0; b < this.outputStackTop; b++) {
/* 1384 */       int i2, m = this.outputStack[b];
/* 1385 */       int n = m & 0xF0000000;
/* 1386 */       int i1 = m & 0xF000000;
/* 1387 */       if (i1 == 16777216) {
/* 1388 */         i2 = m;
/*      */       } else {
/* 1390 */         if (i1 == 33554432) {
/* 1391 */           i2 = n + this.inputLocals[m & 0x7FFFFF];
/*      */         } else {
/* 1393 */           i2 = n + this.inputStack[j - (m & 0x7FFFFF)];
/*      */         } 
/* 1395 */         if ((m & 0x800000) != 0 && (i2 == 16777220 || i2 == 16777219))
/*      */         {
/* 1397 */           i2 = 16777216;
/*      */         }
/*      */       } 
/* 1400 */       if (this.initializations != null) {
/* 1401 */         i2 = init(paramClassWriter, i2);
/*      */       }
/* 1403 */       bool |= merge(paramClassWriter, i2, paramFrame.inputStack, k + b);
/*      */     } 
/* 1405 */     return bool;
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
/*      */   private static boolean merge(ClassWriter paramClassWriter, int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 1426 */     int j, i = paramArrayOfint[paramInt2];
/* 1427 */     if (i == paramInt1)
/*      */     {
/* 1429 */       return false;
/*      */     }
/* 1431 */     if ((paramInt1 & 0xFFFFFFF) == 16777221) {
/* 1432 */       if (i == 16777221) {
/* 1433 */         return false;
/*      */       }
/* 1435 */       paramInt1 = 16777221;
/*      */     } 
/* 1437 */     if (i == 0) {
/*      */       
/* 1439 */       paramArrayOfint[paramInt2] = paramInt1;
/* 1440 */       return true;
/*      */     } 
/*      */     
/* 1443 */     if ((i & 0xFF00000) == 24117248 || (i & 0xF0000000) != 0) {
/*      */       
/* 1445 */       if (paramInt1 == 16777221)
/*      */       {
/* 1447 */         return false; } 
/* 1448 */       if ((paramInt1 & 0xFFF00000) == (i & 0xFFF00000)) {
/*      */         
/* 1450 */         if ((i & 0xFF00000) == 24117248) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1455 */           j = paramInt1 & 0xF0000000 | 0x1700000 | paramClassWriter.getMergedType(paramInt1 & 0xFFFFF, i & 0xFFFFF);
/*      */         }
/*      */         else {
/*      */           
/* 1459 */           int k = -268435456 + (i & 0xF0000000);
/* 1460 */           j = k | 0x1700000 | paramClassWriter.addType("java/lang/Object");
/*      */         } 
/* 1462 */       } else if ((paramInt1 & 0xFF00000) == 24117248 || (paramInt1 & 0xF0000000) != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1467 */         int k = (((paramInt1 & 0xF0000000) == 0 || (paramInt1 & 0xFF00000) == 24117248) ? 0 : -268435456) + (paramInt1 & 0xF0000000);
/*      */         
/* 1469 */         int m = (((i & 0xF0000000) == 0 || (i & 0xFF00000) == 24117248) ? 0 : -268435456) + (i & 0xF0000000);
/*      */ 
/*      */         
/* 1472 */         j = Math.min(k, m) | 0x1700000 | paramClassWriter.addType("java/lang/Object");
/*      */       } else {
/*      */         
/* 1475 */         j = 16777216;
/*      */       } 
/* 1477 */     } else if (i == 16777221) {
/*      */ 
/*      */       
/* 1480 */       j = ((paramInt1 & 0xFF00000) == 24117248 || (paramInt1 & 0xF0000000) != 0) ? paramInt1 : 16777216;
/*      */     } else {
/*      */       
/* 1483 */       j = 16777216;
/*      */     } 
/* 1485 */     if (i != j) {
/* 1486 */       paramArrayOfint[paramInt2] = j;
/* 1487 */       return true;
/*      */     } 
/* 1489 */     return false;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */