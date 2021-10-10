/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TypeInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicInterpreter
/*     */   extends Interpreter<BasicValue>
/*     */   implements Opcodes
/*     */ {
/*     */   public BasicInterpreter() {
/*  85 */     super(327680);
/*     */   }
/*     */   
/*     */   protected BasicInterpreter(int paramInt) {
/*  89 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue newValue(Type paramType) {
/*  94 */     if (paramType == null) {
/*  95 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/*  97 */     switch (paramType.getSort()) {
/*     */       case 0:
/*  99 */         return null;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 105 */         return BasicValue.INT_VALUE;
/*     */       case 6:
/* 107 */         return BasicValue.FLOAT_VALUE;
/*     */       case 7:
/* 109 */         return BasicValue.LONG_VALUE;
/*     */       case 8:
/* 111 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 9:
/*     */       case 10:
/* 114 */         return BasicValue.REFERENCE_VALUE;
/*     */     } 
/* 116 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue newOperation(AbstractInsnNode paramAbstractInsnNode) throws AnalyzerException {
/*     */     Object object;
/* 123 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 1:
/* 125 */         return newValue(Type.getObjectType("null"));
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 133 */         return BasicValue.INT_VALUE;
/*     */       case 9:
/*     */       case 10:
/* 136 */         return BasicValue.LONG_VALUE;
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/* 140 */         return BasicValue.FLOAT_VALUE;
/*     */       case 14:
/*     */       case 15:
/* 143 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 16:
/*     */       case 17:
/* 146 */         return BasicValue.INT_VALUE;
/*     */       case 18:
/* 148 */         object = ((LdcInsnNode)paramAbstractInsnNode).cst;
/* 149 */         if (object instanceof Integer)
/* 150 */           return BasicValue.INT_VALUE; 
/* 151 */         if (object instanceof Float)
/* 152 */           return BasicValue.FLOAT_VALUE; 
/* 153 */         if (object instanceof Long)
/* 154 */           return BasicValue.LONG_VALUE; 
/* 155 */         if (object instanceof Double)
/* 156 */           return BasicValue.DOUBLE_VALUE; 
/* 157 */         if (object instanceof String)
/* 158 */           return newValue(Type.getObjectType("java/lang/String")); 
/* 159 */         if (object instanceof Type) {
/* 160 */           int i = ((Type)object).getSort();
/* 161 */           if (i == 10 || i == 9)
/* 162 */             return newValue(Type.getObjectType("java/lang/Class")); 
/* 163 */           if (i == 11) {
/* 164 */             return newValue(
/* 165 */                 Type.getObjectType("java/lang/invoke/MethodType"));
/*     */           }
/* 167 */           throw new IllegalArgumentException("Illegal LDC constant " + object);
/*     */         } 
/*     */         
/* 170 */         if (object instanceof jdk.internal.org.objectweb.asm.Handle) {
/* 171 */           return newValue(
/* 172 */               Type.getObjectType("java/lang/invoke/MethodHandle"));
/*     */         }
/* 174 */         throw new IllegalArgumentException("Illegal LDC constant " + object);
/*     */ 
/*     */       
/*     */       case 168:
/* 178 */         return BasicValue.RETURNADDRESS_VALUE;
/*     */       case 178:
/* 180 */         return newValue(Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc));
/*     */       case 187:
/* 182 */         return newValue(Type.getObjectType(((TypeInsnNode)paramAbstractInsnNode).desc));
/*     */     } 
/* 184 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue copyOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue) throws AnalyzerException {
/* 191 */     return paramBasicValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue unaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue) throws AnalyzerException {
/*     */     String str;
/* 197 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 116:
/*     */       case 132:
/*     */       case 136:
/*     */       case 139:
/*     */       case 142:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/* 206 */         return BasicValue.INT_VALUE;
/*     */       case 118:
/*     */       case 134:
/*     */       case 137:
/*     */       case 144:
/* 211 */         return BasicValue.FLOAT_VALUE;
/*     */       case 117:
/*     */       case 133:
/*     */       case 140:
/*     */       case 143:
/* 216 */         return BasicValue.LONG_VALUE;
/*     */       case 119:
/*     */       case 135:
/*     */       case 138:
/*     */       case 141:
/* 221 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/*     */       case 179:
/* 236 */         return null;
/*     */       case 180:
/* 238 */         return newValue(Type.getType(((FieldInsnNode)paramAbstractInsnNode).desc));
/*     */       case 188:
/* 240 */         switch (((IntInsnNode)paramAbstractInsnNode).operand) {
/*     */           case 4:
/* 242 */             return newValue(Type.getType("[Z"));
/*     */           case 5:
/* 244 */             return newValue(Type.getType("[C"));
/*     */           case 8:
/* 246 */             return newValue(Type.getType("[B"));
/*     */           case 9:
/* 248 */             return newValue(Type.getType("[S"));
/*     */           case 10:
/* 250 */             return newValue(Type.getType("[I"));
/*     */           case 6:
/* 252 */             return newValue(Type.getType("[F"));
/*     */           case 7:
/* 254 */             return newValue(Type.getType("[D"));
/*     */           case 11:
/* 256 */             return newValue(Type.getType("[J"));
/*     */         } 
/* 258 */         throw new AnalyzerException(paramAbstractInsnNode, "Invalid array type");
/*     */       
/*     */       case 189:
/* 261 */         str = ((TypeInsnNode)paramAbstractInsnNode).desc;
/* 262 */         return newValue(Type.getType("[" + Type.getObjectType(str)));
/*     */       case 190:
/* 264 */         return BasicValue.INT_VALUE;
/*     */       case 191:
/* 266 */         return null;
/*     */       case 192:
/* 268 */         str = ((TypeInsnNode)paramAbstractInsnNode).desc;
/* 269 */         return newValue(Type.getObjectType(str));
/*     */       case 193:
/* 271 */         return BasicValue.INT_VALUE;
/*     */       case 194:
/*     */       case 195:
/*     */       case 198:
/*     */       case 199:
/* 276 */         return null;
/*     */     } 
/* 278 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue binaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2) throws AnalyzerException {
/* 286 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 46:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
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
/* 302 */         return BasicValue.INT_VALUE;
/*     */       case 48:
/*     */       case 98:
/*     */       case 102:
/*     */       case 106:
/*     */       case 110:
/*     */       case 114:
/* 309 */         return BasicValue.FLOAT_VALUE;
/*     */       case 47:
/*     */       case 97:
/*     */       case 101:
/*     */       case 105:
/*     */       case 109:
/*     */       case 113:
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/* 322 */         return BasicValue.LONG_VALUE;
/*     */       case 49:
/*     */       case 99:
/*     */       case 103:
/*     */       case 107:
/*     */       case 111:
/*     */       case 115:
/* 329 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 50:
/* 331 */         return BasicValue.REFERENCE_VALUE;
/*     */       case 148:
/*     */       case 149:
/*     */       case 150:
/*     */       case 151:
/*     */       case 152:
/* 337 */         return BasicValue.INT_VALUE;
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/*     */       case 165:
/*     */       case 166:
/*     */       case 181:
/* 347 */         return null;
/*     */     } 
/* 349 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue ternaryOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2, BasicValue paramBasicValue3) throws AnalyzerException {
/* 357 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue naryOperation(AbstractInsnNode paramAbstractInsnNode, List<? extends BasicValue> paramList) throws AnalyzerException {
/* 363 */     int i = paramAbstractInsnNode.getOpcode();
/* 364 */     if (i == 197)
/* 365 */       return newValue(Type.getType(((MultiANewArrayInsnNode)paramAbstractInsnNode).desc)); 
/* 366 */     if (i == 186) {
/* 367 */       return newValue(
/* 368 */           Type.getReturnType(((InvokeDynamicInsnNode)paramAbstractInsnNode).desc));
/*     */     }
/* 370 */     return newValue(Type.getReturnType(((MethodInsnNode)paramAbstractInsnNode).desc));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode paramAbstractInsnNode, BasicValue paramBasicValue1, BasicValue paramBasicValue2) throws AnalyzerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue merge(BasicValue paramBasicValue1, BasicValue paramBasicValue2) {
/* 382 */     if (!paramBasicValue1.equals(paramBasicValue2)) {
/* 383 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/* 385 */     return paramBasicValue1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/BasicInterpreter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */