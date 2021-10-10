/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicVerifier
/*     */   extends BasicInterpreter
/*     */ {
/*     */   public BasicVerifier() {
/*  79 */     super(327680);
/*     */   }
/*     */   
/*     */   protected BasicVerifier(int paramInt) {
/*  83 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue copyOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue) throws AnalyzerException {
/*     */     BasicValue basicValue;
/*  90 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 21:
/*     */       case 54:
/*  93 */         basicValue = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 23:
/*     */       case 56:
/*  97 */         basicValue = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 22:
/*     */       case 55:
/* 101 */         basicValue = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 24:
/*     */       case 57:
/* 105 */         basicValue = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 25:
/* 108 */         if (!paramBasicValue.isReference()) {
/* 109 */           throw new AnalyzerException(paramAbstractInsnNode, null, "an object reference", paramBasicValue);
/*     */         }
/*     */         
/* 112 */         return paramBasicValue;
/*     */       case 58:
/* 114 */         if (!paramBasicValue.isReference() && 
/* 115 */           !BasicValue.RETURNADDRESS_VALUE.equals(paramBasicValue)) {
/* 116 */           throw new AnalyzerException(paramAbstractInsnNode, null, "an object reference or a return address", paramBasicValue);
/*     */         }
/*     */         
/* 119 */         return paramBasicValue;
/*     */       default:
/* 121 */         return paramBasicValue;
/*     */     } 
/* 123 */     if (!basicValue.equals(paramBasicValue)) {
/* 124 */       throw new AnalyzerException(paramAbstractInsnNode, null, basicValue, paramBasicValue);
/*     */     }
/* 126 */     return paramBasicValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue unaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue) throws AnalyzerException {
/*     */     BasicValue basicValue;
/* 133 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 116:
/*     */       case 132:
/*     */       case 133:
/*     */       case 134:
/*     */       case 135:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 188:
/*     */       case 189:
/* 153 */         basicValue = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 118:
/*     */       case 139:
/*     */       case 140:
/*     */       case 141:
/*     */       case 174:
/* 160 */         basicValue = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 117:
/*     */       case 136:
/*     */       case 137:
/*     */       case 138:
/*     */       case 173:
/* 167 */         basicValue = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 119:
/*     */       case 142:
/*     */       case 143:
/*     */       case 144:
/*     */       case 175:
/* 174 */         basicValue = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 180:
/* 177 */         basicValue = newValue(
/* 178 */             Type.getObjectType(((FieldInsnNode)paramAbstractInsnNode).owner));
/*     */         break;
/*     */       case 192:
/* 181 */         if (!paramBasicValue.isReference()) {
/* 182 */           throw new AnalyzerException(paramAbstractInsnNode, null, "an object reference", paramBasicValue);
/*     */         }
/*     */         
/* 185 */         return super.unaryOperation(paramAbstractInsnNode, paramBasicValue);
/*     */       case 190:
/* 187 */         if (!isArrayValue(paramBasicValue)) {
/* 188 */           throw new AnalyzerException(paramAbstractInsnNode, null, "an array reference", paramBasicValue);
/*     */         }
/*     */         
/* 191 */         return super.unaryOperation(paramAbstractInsnNode, paramBasicValue);
/*     */       case 176:
/*     */       case 191:
/*     */       case 193:
/*     */       case 194:
/*     */       case 195:
/*     */       case 198:
/*     */       case 199:
/* 199 */         if (!paramBasicValue.isReference()) {
/* 200 */           throw new AnalyzerException(paramAbstractInsnNode, null, "an object reference", paramBasicValue);
/*     */         }
/*     */         
/* 203 */         return super.unaryOperation(paramAbstractInsnNode, paramBasicValue);
/*     */       case 179:
/* 205 */         basicValue = newValue(Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc));
/*     */         break;
/*     */       default:
/* 208 */         throw new Error("Internal error.");
/*     */     } 
/* 210 */     if (!isSubTypeOf(paramBasicValue, basicValue)) {
/* 211 */       throw new AnalyzerException(paramAbstractInsnNode, null, basicValue, paramBasicValue);
/*     */     }
/* 213 */     return super.unaryOperation(paramAbstractInsnNode, paramBasicValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue binaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2) throws AnalyzerException {
/*     */     BasicValue basicValue1;
/*     */     BasicValue basicValue2;
/*     */     FieldInsnNode fieldInsnNode;
/* 222 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 46:
/* 224 */         basicValue1 = newValue(Type.getType("[I"));
/* 225 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 51:
/* 228 */         if (isSubTypeOf(paramBasicValue1, newValue(Type.getType("[Z")))) {
/* 229 */           basicValue1 = newValue(Type.getType("[Z"));
/*     */         } else {
/* 231 */           basicValue1 = newValue(Type.getType("[B"));
/*     */         } 
/* 233 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 52:
/* 236 */         basicValue1 = newValue(Type.getType("[C"));
/* 237 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 53:
/* 240 */         basicValue1 = newValue(Type.getType("[S"));
/* 241 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 47:
/* 244 */         basicValue1 = newValue(Type.getType("[J"));
/* 245 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 48:
/* 248 */         basicValue1 = newValue(Type.getType("[F"));
/* 249 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 49:
/* 252 */         basicValue1 = newValue(Type.getType("[D"));
/* 253 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 50:
/* 256 */         basicValue1 = newValue(Type.getType("[Ljava/lang/Object;"));
/* 257 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 96:
/*     */       case 100:
/*     */       case 104:
/*     */       case 108:
/*     */       case 112:
/*     */       case 120:
/*     */       case 122:
/*     */       case 124:
/*     */       case 126:
/*     */       case 128:
/*     */       case 130:
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/* 276 */         basicValue1 = BasicValue.INT_VALUE;
/* 277 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 98:
/*     */       case 102:
/*     */       case 106:
/*     */       case 110:
/*     */       case 114:
/*     */       case 149:
/*     */       case 150:
/* 286 */         basicValue1 = BasicValue.FLOAT_VALUE;
/* 287 */         basicValue2 = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 97:
/*     */       case 101:
/*     */       case 105:
/*     */       case 109:
/*     */       case 113:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/*     */       case 148:
/* 298 */         basicValue1 = BasicValue.LONG_VALUE;
/* 299 */         basicValue2 = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/* 304 */         basicValue1 = BasicValue.LONG_VALUE;
/* 305 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 99:
/*     */       case 103:
/*     */       case 107:
/*     */       case 111:
/*     */       case 115:
/*     */       case 151:
/*     */       case 152:
/* 314 */         basicValue1 = BasicValue.DOUBLE_VALUE;
/* 315 */         basicValue2 = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 165:
/*     */       case 166:
/* 319 */         basicValue1 = BasicValue.REFERENCE_VALUE;
/* 320 */         basicValue2 = BasicValue.REFERENCE_VALUE;
/*     */         break;
/*     */       case 181:
/* 323 */         fieldInsnNode = (FieldInsnNode)paramAbstractInsnNode;
/* 324 */         basicValue1 = newValue(Type.getObjectType(fieldInsnNode.owner));
/* 325 */         basicValue2 = newValue(Type.getType(fieldInsnNode.desc));
/*     */         break;
/*     */       default:
/* 328 */         throw new Error("Internal error.");
/*     */     } 
/* 330 */     if (!isSubTypeOf(paramBasicValue1, basicValue1)) {
/* 331 */       throw new AnalyzerException(paramAbstractInsnNode, "First argument", basicValue1, paramBasicValue1);
/*     */     }
/* 333 */     if (!isSubTypeOf(paramBasicValue2, basicValue2)) {
/* 334 */       throw new AnalyzerException(paramAbstractInsnNode, "Second argument", basicValue2, paramBasicValue2);
/*     */     }
/*     */     
/* 337 */     if (paramAbstractInsnNode.getOpcode() == 50) {
/* 338 */       return getElementValue(paramBasicValue1);
/*     */     }
/* 340 */     return super.binaryOperation(paramAbstractInsnNode, paramBasicValue1, paramBasicValue2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue ternaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2, BasicValue paramBasicValue3) throws AnalyzerException {
/*     */     BasicValue basicValue1;
/*     */     BasicValue basicValue2;
/* 350 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 79:
/* 352 */         basicValue1 = newValue(Type.getType("[I"));
/* 353 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 84:
/* 356 */         if (isSubTypeOf(paramBasicValue1, newValue(Type.getType("[Z")))) {
/* 357 */           basicValue1 = newValue(Type.getType("[Z"));
/*     */         } else {
/* 359 */           basicValue1 = newValue(Type.getType("[B"));
/*     */         } 
/* 361 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 85:
/* 364 */         basicValue1 = newValue(Type.getType("[C"));
/* 365 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 86:
/* 368 */         basicValue1 = newValue(Type.getType("[S"));
/* 369 */         basicValue2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 80:
/* 372 */         basicValue1 = newValue(Type.getType("[J"));
/* 373 */         basicValue2 = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 81:
/* 376 */         basicValue1 = newValue(Type.getType("[F"));
/* 377 */         basicValue2 = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 82:
/* 380 */         basicValue1 = newValue(Type.getType("[D"));
/* 381 */         basicValue2 = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 83:
/* 384 */         basicValue1 = paramBasicValue1;
/* 385 */         basicValue2 = BasicValue.REFERENCE_VALUE;
/*     */         break;
/*     */       default:
/* 388 */         throw new Error("Internal error.");
/*     */     } 
/* 390 */     if (!isSubTypeOf(paramBasicValue1, basicValue1)) {
/* 391 */       throw new AnalyzerException(paramAbstractInsnNode, "First argument", "a " + basicValue1 + " array reference", paramBasicValue1);
/*     */     }
/* 393 */     if (!BasicValue.INT_VALUE.equals(paramBasicValue2)) {
/* 394 */       throw new AnalyzerException(paramAbstractInsnNode, "Second argument", BasicValue.INT_VALUE, paramBasicValue2);
/*     */     }
/* 396 */     if (!isSubTypeOf(paramBasicValue3, basicValue2)) {
/* 397 */       throw new AnalyzerException(paramAbstractInsnNode, "Third argument", basicValue2, paramBasicValue3);
/*     */     }
/*     */     
/* 400 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue naryOperation(AbstractInsnNode paramAbstractInsnNode, List<? extends BasicValue> paramList) throws AnalyzerException {
/* 406 */     int i = paramAbstractInsnNode.getOpcode();
/* 407 */     if (i == 197) {
/* 408 */       for (byte b = 0; b < paramList.size(); b++) {
/* 409 */         if (!BasicValue.INT_VALUE.equals(paramList.get(b))) {
/* 410 */           throw new AnalyzerException(paramAbstractInsnNode, null, BasicValue.INT_VALUE, (Value)paramList
/* 411 */               .get(b));
/*     */         }
/*     */       } 
/*     */     } else {
/* 415 */       byte b1 = 0;
/* 416 */       byte b2 = 0;
/* 417 */       if (i != 184 && i != 186) {
/* 418 */         Type type = Type.getObjectType(((MethodInsnNode)paramAbstractInsnNode).owner);
/* 419 */         if (!isSubTypeOf(paramList.get(b1++), newValue(type))) {
/* 420 */           throw new AnalyzerException(paramAbstractInsnNode, "Method owner", 
/* 421 */               newValue(type), (Value)paramList.get(0));
/*     */         }
/*     */       } 
/* 424 */       String str = (i == 186) ? ((InvokeDynamicInsnNode)paramAbstractInsnNode).desc : ((MethodInsnNode)paramAbstractInsnNode).desc;
/*     */       
/* 426 */       Type[] arrayOfType = Type.getArgumentTypes(str);
/* 427 */       while (b1 < paramList.size()) {
/* 428 */         BasicValue basicValue1 = newValue(arrayOfType[b2++]);
/* 429 */         BasicValue basicValue2 = paramList.get(b1++);
/* 430 */         if (!isSubTypeOf(basicValue2, basicValue1)) {
/* 431 */           throw new AnalyzerException(paramAbstractInsnNode, "Argument " + b2, basicValue1, basicValue2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 436 */     return super.naryOperation(paramAbstractInsnNode, paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2) throws AnalyzerException {
/* 443 */     if (!isSubTypeOf(paramBasicValue1, paramBasicValue2)) {
/* 444 */       throw new AnalyzerException(paramAbstractInsnNode, "Incompatible return type", paramBasicValue2, paramBasicValue1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isArrayValue(BasicValue paramBasicValue) {
/* 450 */     return paramBasicValue.isReference();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasicValue getElementValue(BasicValue paramBasicValue) throws AnalyzerException {
/* 455 */     return BasicValue.REFERENCE_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSubTypeOf(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
/* 460 */     return paramBasicValue1.equals(paramBasicValue2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/BasicVerifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */