/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.signature.SignatureReader;
/*     */ import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
/*     */ import jdk.internal.org.objectweb.asm.signature.SignatureWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Remapper
/*     */ {
/*     */   public String mapDesc(String paramString) {
/*     */     String str1;
/*     */     byte b;
/*     */     String str2;
/*  83 */     Type type = Type.getType(paramString);
/*  84 */     switch (type.getSort()) {
/*     */       case 9:
/*  86 */         str1 = mapDesc(type.getElementType().getDescriptor());
/*  87 */         for (b = 0; b < type.getDimensions(); b++) {
/*  88 */           str1 = '[' + str1;
/*     */         }
/*  90 */         return str1;
/*     */       case 10:
/*  92 */         str2 = map(type.getInternalName());
/*  93 */         if (str2 != null)
/*  94 */           return 'L' + str2 + ';'; 
/*     */         break;
/*     */     } 
/*  97 */     return paramString;
/*     */   } private Type mapType(Type paramType) {
/*     */     String str;
/*     */     byte b;
/* 101 */     switch (paramType.getSort()) {
/*     */       case 9:
/* 103 */         str = mapDesc(paramType.getElementType().getDescriptor());
/* 104 */         for (b = 0; b < paramType.getDimensions(); b++) {
/* 105 */           str = '[' + str;
/*     */         }
/* 107 */         return Type.getType(str);
/*     */       case 10:
/* 109 */         str = map(paramType.getInternalName());
/* 110 */         return (str != null) ? Type.getObjectType(str) : paramType;
/*     */       case 11:
/* 112 */         return Type.getMethodType(mapMethodDesc(paramType.getDescriptor()));
/*     */     } 
/* 114 */     return paramType;
/*     */   }
/*     */   
/*     */   public String mapType(String paramString) {
/* 118 */     if (paramString == null) {
/* 119 */       return null;
/*     */     }
/* 121 */     return mapType(Type.getObjectType(paramString)).getInternalName();
/*     */   }
/*     */   
/*     */   public String[] mapTypes(String[] paramArrayOfString) {
/* 125 */     String[] arrayOfString = null;
/* 126 */     boolean bool = false;
/* 127 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 128 */       String str1 = paramArrayOfString[b];
/* 129 */       String str2 = map(str1);
/* 130 */       if (str2 != null && arrayOfString == null) {
/* 131 */         arrayOfString = new String[paramArrayOfString.length];
/* 132 */         if (b > 0) {
/* 133 */           System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, b);
/*     */         }
/* 135 */         bool = true;
/*     */       } 
/* 137 */       if (bool) {
/* 138 */         arrayOfString[b] = (str2 == null) ? str1 : str2;
/*     */       }
/*     */     } 
/* 141 */     return bool ? arrayOfString : paramArrayOfString;
/*     */   }
/*     */   
/*     */   public String mapMethodDesc(String paramString) {
/* 145 */     if ("()V".equals(paramString)) {
/* 146 */       return paramString;
/*     */     }
/*     */     
/* 149 */     Type[] arrayOfType = Type.getArgumentTypes(paramString);
/* 150 */     StringBuilder stringBuilder = new StringBuilder("(");
/* 151 */     for (byte b = 0; b < arrayOfType.length; b++) {
/* 152 */       stringBuilder.append(mapDesc(arrayOfType[b].getDescriptor()));
/*     */     }
/* 154 */     Type type = Type.getReturnType(paramString);
/* 155 */     if (type == Type.VOID_TYPE) {
/* 156 */       stringBuilder.append(")V");
/* 157 */       return stringBuilder.toString();
/*     */     } 
/* 159 */     stringBuilder.append(')').append(mapDesc(type.getDescriptor()));
/* 160 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public Object mapValue(Object paramObject) {
/* 164 */     if (paramObject instanceof Type) {
/* 165 */       return mapType((Type)paramObject);
/*     */     }
/* 167 */     if (paramObject instanceof Handle) {
/* 168 */       Handle handle = (Handle)paramObject;
/* 169 */       return new Handle(handle.getTag(), mapType(handle.getOwner()), mapMethodName(handle
/* 170 */             .getOwner(), handle.getName(), handle.getDesc()), 
/* 171 */           mapMethodDesc(handle.getDesc()));
/*     */     } 
/* 173 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String mapSignature(String paramString, boolean paramBoolean) {
/* 184 */     if (paramString == null) {
/* 185 */       return null;
/*     */     }
/* 187 */     SignatureReader signatureReader = new SignatureReader(paramString);
/* 188 */     SignatureWriter signatureWriter = new SignatureWriter();
/* 189 */     SignatureVisitor signatureVisitor = createRemappingSignatureAdapter(signatureWriter);
/* 190 */     if (paramBoolean) {
/* 191 */       signatureReader.acceptType(signatureVisitor);
/*     */     } else {
/* 193 */       signatureReader.accept(signatureVisitor);
/*     */     } 
/* 195 */     return signatureWriter.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor paramSignatureVisitor) {
/* 200 */     return new RemappingSignatureAdapter(paramSignatureVisitor, this);
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
/*     */   public String mapMethodName(String paramString1, String paramString2, String paramString3) {
/* 215 */     return paramString2;
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
/*     */   public String mapInvokeDynamicMethodName(String paramString1, String paramString2) {
/* 228 */     return paramString1;
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
/*     */   public String mapFieldName(String paramString1, String paramString2, String paramString3) {
/* 243 */     return paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String map(String paramString) {
/* 250 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/Remapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */