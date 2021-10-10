/*     */ package java.lang.invoke;
/*     */ 
/*     */ import java.lang.invoke.TypeConvertingMethodAdapter;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import sun.invoke.util.BytecodeDescriptor;
/*     */ import sun.invoke.util.Wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TypeConvertingMethodAdapter
/*     */   extends MethodVisitor
/*     */ {
/*     */   TypeConvertingMethodAdapter(MethodVisitor paramMethodVisitor) {
/*  38 */     super(327680, paramMethodVisitor);
/*     */   }
/*     */   
/*  41 */   private static final int NUM_WRAPPERS = (Wrapper.values()).length;
/*     */ 
/*     */   
/*     */   private static final String NAME_OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String WRAPPER_PREFIX = "Ljava/lang/";
/*     */   
/*     */   private static final String NAME_BOX_METHOD = "valueOf";
/*     */   
/*  50 */   private static final int[][] wideningOpcodes = new int[NUM_WRAPPERS][NUM_WRAPPERS];
/*     */   
/*  52 */   private static final Wrapper[] FROM_WRAPPER_NAME = new Wrapper[16];
/*     */ 
/*     */   
/*  55 */   private static final Wrapper[] FROM_TYPE_SORT = new Wrapper[16];
/*     */   
/*     */   static {
/*  58 */     for (Wrapper wrapper : Wrapper.values()) {
/*  59 */       if (wrapper.basicTypeChar() != 'L') {
/*  60 */         int i = hashWrapperName(wrapper.wrapperSimpleName());
/*  61 */         assert FROM_WRAPPER_NAME[i] == null;
/*  62 */         FROM_WRAPPER_NAME[i] = wrapper;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     for (byte b = 0; b < NUM_WRAPPERS; b++) {
/*  67 */       for (byte b1 = 0; b1 < NUM_WRAPPERS; b1++) {
/*  68 */         wideningOpcodes[b][b1] = 0;
/*     */       }
/*     */     } 
/*     */     
/*  72 */     initWidening(Wrapper.LONG, 133, new Wrapper[] { Wrapper.BYTE, Wrapper.SHORT, Wrapper.INT, Wrapper.CHAR });
/*  73 */     initWidening(Wrapper.LONG, 140, new Wrapper[] { Wrapper.FLOAT });
/*  74 */     initWidening(Wrapper.FLOAT, 134, new Wrapper[] { Wrapper.BYTE, Wrapper.SHORT, Wrapper.INT, Wrapper.CHAR });
/*  75 */     initWidening(Wrapper.FLOAT, 137, new Wrapper[] { Wrapper.LONG });
/*  76 */     initWidening(Wrapper.DOUBLE, 135, new Wrapper[] { Wrapper.BYTE, Wrapper.SHORT, Wrapper.INT, Wrapper.CHAR });
/*  77 */     initWidening(Wrapper.DOUBLE, 141, new Wrapper[] { Wrapper.FLOAT });
/*  78 */     initWidening(Wrapper.DOUBLE, 138, new Wrapper[] { Wrapper.LONG });
/*     */     
/*  80 */     FROM_TYPE_SORT[3] = Wrapper.BYTE;
/*  81 */     FROM_TYPE_SORT[4] = Wrapper.SHORT;
/*  82 */     FROM_TYPE_SORT[5] = Wrapper.INT;
/*  83 */     FROM_TYPE_SORT[7] = Wrapper.LONG;
/*  84 */     FROM_TYPE_SORT[2] = Wrapper.CHAR;
/*  85 */     FROM_TYPE_SORT[6] = Wrapper.FLOAT;
/*  86 */     FROM_TYPE_SORT[8] = Wrapper.DOUBLE;
/*  87 */     FROM_TYPE_SORT[1] = Wrapper.BOOLEAN;
/*     */   }
/*     */   
/*     */   private static void initWidening(Wrapper paramWrapper, int paramInt, Wrapper... paramVarArgs) {
/*  91 */     for (Wrapper wrapper : paramVarArgs) {
/*  92 */       wideningOpcodes[wrapper.ordinal()][paramWrapper.ordinal()] = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hashWrapperName(String paramString) {
/* 102 */     if (paramString.length() < 3) {
/* 103 */       return 0;
/*     */     }
/* 105 */     return (3 * paramString.charAt(1) + paramString.charAt(2)) % 16;
/*     */   }
/*     */   
/*     */   private Wrapper wrapperOrNullFromDescriptor(String paramString) {
/* 109 */     if (!paramString.startsWith("Ljava/lang/"))
/*     */     {
/*     */       
/* 112 */       return null;
/*     */     }
/*     */     
/* 115 */     String str = paramString.substring("Ljava/lang/".length(), paramString.length() - 1);
/*     */     
/* 117 */     Wrapper wrapper = FROM_WRAPPER_NAME[hashWrapperName(str)];
/* 118 */     if (wrapper == null || wrapper.wrapperSimpleName().equals(str)) {
/* 119 */       return wrapper;
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String wrapperName(Wrapper paramWrapper) {
/* 126 */     return "java/lang/" + paramWrapper.wrapperSimpleName();
/*     */   }
/*     */   
/*     */   private static String unboxMethod(Wrapper paramWrapper) {
/* 130 */     return paramWrapper.primitiveSimpleName() + "Value";
/*     */   }
/*     */   
/*     */   private static String boxingDescriptor(Wrapper paramWrapper) {
/* 134 */     return String.format("(%s)L%s;", new Object[] { Character.valueOf(paramWrapper.basicTypeChar()), wrapperName(paramWrapper) });
/*     */   }
/*     */   
/*     */   private static String unboxingDescriptor(Wrapper paramWrapper) {
/* 138 */     return "()" + paramWrapper.basicTypeChar();
/*     */   }
/*     */   
/*     */   void boxIfTypePrimitive(Type paramType) {
/* 142 */     Wrapper wrapper = FROM_TYPE_SORT[paramType.getSort()];
/* 143 */     if (wrapper != null) {
/* 144 */       box(wrapper);
/*     */     }
/*     */   }
/*     */   
/*     */   void widen(Wrapper paramWrapper1, Wrapper paramWrapper2) {
/* 149 */     if (paramWrapper1 != paramWrapper2) {
/* 150 */       int i = wideningOpcodes[paramWrapper1.ordinal()][paramWrapper2.ordinal()];
/* 151 */       if (i != 0) {
/* 152 */         visitInsn(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void box(Wrapper paramWrapper) {
/* 158 */     visitMethodInsn(184, 
/* 159 */         wrapperName(paramWrapper), "valueOf", 
/*     */         
/* 161 */         boxingDescriptor(paramWrapper), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void unbox(String paramString, Wrapper paramWrapper) {
/* 170 */     visitMethodInsn(182, paramString, 
/*     */         
/* 172 */         unboxMethod(paramWrapper), 
/* 173 */         unboxingDescriptor(paramWrapper), false);
/*     */   }
/*     */   
/*     */   private String descriptorToName(String paramString) {
/* 177 */     int i = paramString.length() - 1;
/* 178 */     if (paramString.charAt(0) == 'L' && paramString.charAt(i) == ';')
/*     */     {
/* 180 */       return paramString.substring(1, i);
/*     */     }
/*     */     
/* 183 */     return paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   void cast(String paramString1, String paramString2) {
/* 188 */     String str1 = descriptorToName(paramString1);
/* 189 */     String str2 = descriptorToName(paramString2);
/* 190 */     if (!str2.equals(str1) && !str2.equals("java/lang/Object")) {
/* 191 */       visitTypeInsn(192, str2);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isPrimitive(Wrapper paramWrapper) {
/* 196 */     return (paramWrapper != Wrapper.OBJECT);
/*     */   }
/*     */   
/*     */   private Wrapper toWrapper(String paramString) {
/* 200 */     char c = paramString.charAt(0);
/* 201 */     if (c == '[' || c == '(') {
/* 202 */       c = 'L';
/*     */     }
/* 204 */     return Wrapper.forBasicType(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void convertType(Class<?> paramClass1, Class<?> paramClass2, Class<?> paramClass3) {
/* 215 */     if (paramClass1.equals(paramClass2) && paramClass1.equals(paramClass3)) {
/*     */       return;
/*     */     }
/* 218 */     if (paramClass1 == void.class || paramClass2 == void.class) {
/*     */       return;
/*     */     }
/* 221 */     if (paramClass1.isPrimitive()) {
/* 222 */       Wrapper wrapper = Wrapper.forPrimitiveType(paramClass1);
/* 223 */       if (paramClass2.isPrimitive()) {
/*     */         
/* 225 */         widen(wrapper, Wrapper.forPrimitiveType(paramClass2));
/*     */       } else {
/*     */         
/* 228 */         String str = BytecodeDescriptor.unparse(paramClass2);
/* 229 */         Wrapper wrapper1 = wrapperOrNullFromDescriptor(str);
/* 230 */         if (wrapper1 != null) {
/*     */           
/* 232 */           widen(wrapper, wrapper1);
/* 233 */           box(wrapper1);
/*     */         } else {
/*     */           
/* 236 */           box(wrapper);
/* 237 */           cast(wrapperName(wrapper), str);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 241 */       String str2, str1 = BytecodeDescriptor.unparse(paramClass1);
/*     */       
/* 243 */       if (paramClass3.isPrimitive()) {
/* 244 */         str2 = str1;
/*     */       } else {
/*     */         
/* 247 */         str2 = BytecodeDescriptor.unparse(paramClass3);
/* 248 */         cast(str1, str2);
/*     */       } 
/* 250 */       String str3 = BytecodeDescriptor.unparse(paramClass2);
/* 251 */       if (paramClass2.isPrimitive()) {
/* 252 */         Wrapper wrapper1 = toWrapper(str3);
/*     */         
/* 254 */         Wrapper wrapper2 = wrapperOrNullFromDescriptor(str2);
/* 255 */         if (wrapper2 != null) {
/* 256 */           if (wrapper2.isSigned() || wrapper2.isFloating()) {
/*     */             
/* 258 */             unbox(wrapperName(wrapper2), wrapper1);
/*     */           } else {
/*     */             
/* 261 */             unbox(wrapperName(wrapper2), wrapper2);
/* 262 */             widen(wrapper2, wrapper1);
/*     */           } 
/*     */         } else {
/*     */           String str;
/*     */ 
/*     */           
/* 268 */           if (wrapper1.isSigned() || wrapper1.isFloating()) {
/*     */             
/* 270 */             str = "java/lang/Number";
/*     */           } else {
/*     */             
/* 273 */             str = wrapperName(wrapper1);
/*     */           } 
/* 275 */           cast(str2, str);
/* 276 */           unbox(str, wrapper1);
/*     */         } 
/*     */       } else {
/*     */         
/* 280 */         cast(str2, str3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void iconst(int paramInt) {
/* 292 */     if (paramInt >= -1 && paramInt <= 5) {
/* 293 */       this.mv.visitInsn(3 + paramInt);
/* 294 */     } else if (paramInt >= -128 && paramInt <= 127) {
/* 295 */       this.mv.visitIntInsn(16, paramInt);
/* 296 */     } else if (paramInt >= -32768 && paramInt <= 32767) {
/* 297 */       this.mv.visitIntInsn(17, paramInt);
/*     */     } else {
/* 299 */       this.mv.visitLdcInsn(Integer.valueOf(paramInt));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/invoke/TypeConvertingMethodAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */