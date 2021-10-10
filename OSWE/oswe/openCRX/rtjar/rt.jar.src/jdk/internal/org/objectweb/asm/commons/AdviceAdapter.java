/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AdviceAdapter
/*     */   extends GeneratorAdapter
/*     */   implements Opcodes
/*     */ {
/*  94 */   private static final Object THIS = new Object();
/*     */   
/*  96 */   private static final Object OTHER = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int methodAccess;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String methodDesc;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean constructor;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean superInitialized;
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object> stackFrame;
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Label, List<Object>> branches;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AdviceAdapter(int paramInt1, MethodVisitor paramMethodVisitor, int paramInt2, String paramString1, String paramString2) {
/* 127 */     super(paramInt1, paramMethodVisitor, paramInt2, paramString1, paramString2);
/* 128 */     this.methodAccess = paramInt2;
/* 129 */     this.methodDesc = paramString2;
/* 130 */     this.constructor = "<init>".equals(paramString1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitCode() {
/* 135 */     this.mv.visitCode();
/* 136 */     if (this.constructor) {
/* 137 */       this.stackFrame = new ArrayList();
/* 138 */       this.branches = new HashMap<>();
/*     */     } else {
/* 140 */       this.superInitialized = true;
/* 141 */       onMethodEnter();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label paramLabel) {
/* 147 */     this.mv.visitLabel(paramLabel);
/* 148 */     if (this.constructor && this.branches != null) {
/* 149 */       List<Object> list = this.branches.get(paramLabel);
/* 150 */       if (list != null) {
/* 151 */         this.stackFrame = list;
/* 152 */         this.branches.remove(paramLabel);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int paramInt) {
/* 159 */     if (this.constructor) {
/*     */       int i;
/* 161 */       switch (paramInt) {
/*     */         case 177:
/* 163 */           onMethodExit(paramInt);
/*     */           break;
/*     */         case 172:
/*     */         case 174:
/*     */         case 176:
/*     */         case 191:
/* 169 */           popValue();
/* 170 */           onMethodExit(paramInt);
/*     */           break;
/*     */         case 173:
/*     */         case 175:
/* 174 */           popValue();
/* 175 */           popValue();
/* 176 */           onMethodExit(paramInt);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 133:
/*     */         case 135:
/*     */         case 140:
/*     */         case 141:
/* 209 */           pushValue(OTHER);
/*     */           break;
/*     */         case 9:
/*     */         case 10:
/*     */         case 14:
/*     */         case 15:
/* 215 */           pushValue(OTHER);
/* 216 */           pushValue(OTHER);
/*     */           break;
/*     */         case 46:
/*     */         case 48:
/*     */         case 50:
/*     */         case 51:
/*     */         case 52:
/*     */         case 53:
/*     */         case 87:
/*     */         case 96:
/*     */         case 98:
/*     */         case 100:
/*     */         case 102:
/*     */         case 104:
/*     */         case 106:
/*     */         case 108:
/*     */         case 110:
/*     */         case 112:
/*     */         case 114:
/*     */         case 120:
/*     */         case 121:
/*     */         case 122:
/*     */         case 123:
/*     */         case 124:
/*     */         case 125:
/*     */         case 126:
/*     */         case 128:
/*     */         case 130:
/*     */         case 136:
/*     */         case 137:
/*     */         case 142:
/*     */         case 144:
/*     */         case 149:
/*     */         case 150:
/*     */         case 194:
/*     */         case 195:
/* 252 */           popValue();
/*     */           break;
/*     */         case 88:
/*     */         case 97:
/*     */         case 99:
/*     */         case 101:
/*     */         case 103:
/*     */         case 105:
/*     */         case 107:
/*     */         case 109:
/*     */         case 111:
/*     */         case 113:
/*     */         case 115:
/*     */         case 127:
/*     */         case 129:
/*     */         case 131:
/* 268 */           popValue();
/* 269 */           popValue();
/*     */           break;
/*     */         case 79:
/*     */         case 81:
/*     */         case 83:
/*     */         case 84:
/*     */         case 85:
/*     */         case 86:
/*     */         case 148:
/*     */         case 151:
/*     */         case 152:
/* 280 */           popValue();
/* 281 */           popValue();
/* 282 */           popValue();
/*     */           break;
/*     */         case 80:
/*     */         case 82:
/* 286 */           popValue();
/* 287 */           popValue();
/* 288 */           popValue();
/* 289 */           popValue();
/*     */           break;
/*     */         case 89:
/* 292 */           pushValue(peekValue());
/*     */           break;
/*     */         case 90:
/* 295 */           i = this.stackFrame.size();
/* 296 */           this.stackFrame.add(i - 2, this.stackFrame.get(i - 1));
/*     */           break;
/*     */         case 91:
/* 299 */           i = this.stackFrame.size();
/* 300 */           this.stackFrame.add(i - 3, this.stackFrame.get(i - 1));
/*     */           break;
/*     */         case 92:
/* 303 */           i = this.stackFrame.size();
/* 304 */           this.stackFrame.add(i - 2, this.stackFrame.get(i - 1));
/* 305 */           this.stackFrame.add(i - 2, this.stackFrame.get(i - 1));
/*     */           break;
/*     */         case 93:
/* 308 */           i = this.stackFrame.size();
/* 309 */           this.stackFrame.add(i - 3, this.stackFrame.get(i - 1));
/* 310 */           this.stackFrame.add(i - 3, this.stackFrame.get(i - 1));
/*     */           break;
/*     */         case 94:
/* 313 */           i = this.stackFrame.size();
/* 314 */           this.stackFrame.add(i - 4, this.stackFrame.get(i - 1));
/* 315 */           this.stackFrame.add(i - 4, this.stackFrame.get(i - 1));
/*     */           break;
/*     */         case 95:
/* 318 */           i = this.stackFrame.size();
/* 319 */           this.stackFrame.add(i - 2, this.stackFrame.get(i - 1));
/* 320 */           this.stackFrame.remove(i);
/*     */           break;
/*     */       } 
/*     */     } else {
/* 324 */       switch (paramInt) {
/*     */         case 172:
/*     */         case 173:
/*     */         case 174:
/*     */         case 175:
/*     */         case 176:
/*     */         case 177:
/*     */         case 191:
/* 332 */           onMethodExit(paramInt);
/*     */           break;
/*     */       } 
/*     */     } 
/* 336 */     this.mv.visitInsn(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 341 */     super.visitVarInsn(paramInt1, paramInt2);
/* 342 */     if (this.constructor) {
/* 343 */       switch (paramInt1) {
/*     */         case 21:
/*     */         case 23:
/* 346 */           pushValue(OTHER);
/*     */           break;
/*     */         case 22:
/*     */         case 24:
/* 350 */           pushValue(OTHER);
/* 351 */           pushValue(OTHER);
/*     */           break;
/*     */         case 25:
/* 354 */           pushValue((paramInt2 == 0) ? THIS : OTHER);
/*     */           break;
/*     */         case 54:
/*     */         case 56:
/*     */         case 58:
/* 359 */           popValue();
/*     */           break;
/*     */         case 55:
/*     */         case 57:
/* 363 */           popValue();
/* 364 */           popValue();
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 373 */     this.mv.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
/* 374 */     if (this.constructor) {
/* 375 */       char c = paramString3.charAt(0);
/* 376 */       boolean bool = (c == 'J' || c == 'D') ? true : false;
/* 377 */       switch (paramInt) {
/*     */         case 178:
/* 379 */           pushValue(OTHER);
/* 380 */           if (bool) {
/* 381 */             pushValue(OTHER);
/*     */           }
/*     */           return;
/*     */         case 179:
/* 385 */           popValue();
/* 386 */           if (bool) {
/* 387 */             popValue();
/*     */           }
/*     */           return;
/*     */         case 181:
/* 391 */           popValue();
/* 392 */           if (bool) {
/* 393 */             popValue();
/* 394 */             popValue();
/*     */           } 
/*     */           return;
/*     */       } 
/*     */       
/* 399 */       if (bool) {
/* 400 */         pushValue(OTHER);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int paramInt1, int paramInt2) {
/* 408 */     this.mv.visitIntInsn(paramInt1, paramInt2);
/* 409 */     if (this.constructor && paramInt1 != 188) {
/* 410 */       pushValue(OTHER);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 416 */     this.mv.visitLdcInsn(paramObject);
/* 417 */     if (this.constructor) {
/* 418 */       pushValue(OTHER);
/* 419 */       if (paramObject instanceof Double || paramObject instanceof Long) {
/* 420 */         pushValue(OTHER);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 427 */     this.mv.visitMultiANewArrayInsn(paramString, paramInt);
/* 428 */     if (this.constructor) {
/* 429 */       for (byte b = 0; b < paramInt; b++) {
/* 430 */         popValue();
/*     */       }
/* 432 */       pushValue(OTHER);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 438 */     this.mv.visitTypeInsn(paramInt, paramString);
/*     */     
/* 440 */     if (this.constructor && paramInt == 187) {
/* 441 */       pushValue(OTHER);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 449 */     if (this.api >= 327680) {
/* 450 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 453 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 460 */     if (this.api < 327680) {
/* 461 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 464 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 469 */     this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/* 470 */     if (this.constructor) {
/* 471 */       Type[] arrayOfType = Type.getArgumentTypes(paramString3);
/* 472 */       for (byte b = 0; b < arrayOfType.length; b++) {
/* 473 */         popValue();
/* 474 */         if (arrayOfType[b].getSize() == 2) {
/* 475 */           popValue();
/*     */         }
/*     */       } 
/* 478 */       switch (paramInt) {
/*     */ 
/*     */         
/*     */         case 182:
/*     */         case 185:
/* 483 */           popValue();
/*     */           break;
/*     */         case 183:
/* 486 */           object = popValue();
/* 487 */           if (object == THIS && !this.superInitialized) {
/* 488 */             onMethodEnter();
/* 489 */             this.superInitialized = true;
/*     */ 
/*     */             
/* 492 */             this.constructor = false;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 497 */       Object object = Type.getReturnType(paramString3);
/* 498 */       if (object != Type.VOID_TYPE) {
/* 499 */         pushValue(OTHER);
/* 500 */         if (object.getSize() == 2) {
/* 501 */           pushValue(OTHER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 510 */     this.mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
/* 511 */     if (this.constructor) {
/* 512 */       Type[] arrayOfType = Type.getArgumentTypes(paramString2);
/* 513 */       for (byte b = 0; b < arrayOfType.length; b++) {
/* 514 */         popValue();
/* 515 */         if (arrayOfType[b].getSize() == 2) {
/* 516 */           popValue();
/*     */         }
/*     */       } 
/*     */       
/* 520 */       Type type = Type.getReturnType(paramString2);
/* 521 */       if (type != Type.VOID_TYPE) {
/* 522 */         pushValue(OTHER);
/* 523 */         if (type.getSize() == 2) {
/* 524 */           pushValue(OTHER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 532 */     this.mv.visitJumpInsn(paramInt, paramLabel);
/* 533 */     if (this.constructor) {
/* 534 */       switch (paramInt) {
/*     */         case 153:
/*     */         case 154:
/*     */         case 155:
/*     */         case 156:
/*     */         case 157:
/*     */         case 158:
/*     */         case 198:
/*     */         case 199:
/* 543 */           popValue();
/*     */           break;
/*     */         case 159:
/*     */         case 160:
/*     */         case 161:
/*     */         case 162:
/*     */         case 163:
/*     */         case 164:
/*     */         case 165:
/*     */         case 166:
/* 553 */           popValue();
/* 554 */           popValue();
/*     */           break;
/*     */         case 168:
/* 557 */           pushValue(OTHER);
/*     */           break;
/*     */       } 
/* 560 */       addBranch(paramLabel);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 567 */     this.mv.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/* 568 */     if (this.constructor) {
/* 569 */       popValue();
/* 570 */       addBranches(paramLabel, paramArrayOfLabel);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 577 */     this.mv.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/* 578 */     if (this.constructor) {
/* 579 */       popValue();
/* 580 */       addBranches(paramLabel, paramVarArgs);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 587 */     super.visitTryCatchBlock(paramLabel1, paramLabel2, paramLabel3, paramString);
/* 588 */     if (this.constructor && !this.branches.containsKey(paramLabel3)) {
/* 589 */       ArrayList<Object> arrayList = new ArrayList();
/* 590 */       arrayList.add(OTHER);
/* 591 */       this.branches.put(paramLabel3, arrayList);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addBranches(Label paramLabel, Label[] paramArrayOfLabel) {
/* 596 */     addBranch(paramLabel);
/* 597 */     for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/* 598 */       addBranch(paramArrayOfLabel[b]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addBranch(Label paramLabel) {
/* 603 */     if (this.branches.containsKey(paramLabel)) {
/*     */       return;
/*     */     }
/* 606 */     this.branches.put(paramLabel, new ArrayList(this.stackFrame));
/*     */   }
/*     */   
/*     */   private Object popValue() {
/* 610 */     return this.stackFrame.remove(this.stackFrame.size() - 1);
/*     */   }
/*     */   
/*     */   private Object peekValue() {
/* 614 */     return this.stackFrame.get(this.stackFrame.size() - 1);
/*     */   }
/*     */   
/*     */   private void pushValue(Object paramObject) {
/* 618 */     this.stackFrame.add(paramObject);
/*     */   }
/*     */   
/*     */   protected void onMethodEnter() {}
/*     */   
/*     */   protected void onMethodExit(int paramInt) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/AdviceAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */