/*     */ package jdk.internal.org.objectweb.asm.tree.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.IincInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InvokeDynamicInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.VarInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Frame<V extends Value>
/*     */ {
/*     */   private V returnValue;
/*     */   private V[] values;
/*     */   private int locals;
/*     */   private int top;
/*     */   
/*     */   public Frame(int paramInt1, int paramInt2) {
/* 117 */     this.values = (V[])new Value[paramInt1 + paramInt2];
/* 118 */     this.locals = paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame(Frame<? extends V> paramFrame) {
/* 128 */     this(paramFrame.locals, paramFrame.values.length - paramFrame.locals);
/* 129 */     init(paramFrame);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame<V> init(Frame<? extends V> paramFrame) {
/* 140 */     this.returnValue = paramFrame.returnValue;
/* 141 */     System.arraycopy(paramFrame.values, 0, this.values, 0, this.values.length);
/* 142 */     this.top = paramFrame.top;
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturn(V paramV) {
/* 154 */     this.returnValue = paramV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLocals() {
/* 163 */     return this.locals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 172 */     return this.values.length - this.locals;
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
/*     */   public V getLocal(int paramInt) throws IndexOutOfBoundsException {
/* 185 */     if (paramInt >= this.locals) {
/* 186 */       throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
/*     */     }
/*     */     
/* 189 */     return this.values[paramInt];
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
/*     */   public void setLocal(int paramInt, V paramV) throws IndexOutOfBoundsException {
/* 204 */     if (paramInt >= this.locals) {
/* 205 */       throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + paramInt);
/*     */     }
/*     */     
/* 208 */     this.values[paramInt] = paramV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStackSize() {
/* 218 */     return this.top;
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
/*     */   public V getStack(int paramInt) throws IndexOutOfBoundsException {
/* 231 */     return this.values[paramInt + this.locals];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearStack() {
/* 238 */     this.top = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V pop() throws IndexOutOfBoundsException {
/* 249 */     if (this.top == 0) {
/* 250 */       throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
/*     */     }
/*     */     
/* 253 */     return this.values[--this.top + this.locals];
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
/*     */   public void push(V paramV) throws IndexOutOfBoundsException {
/* 265 */     if (this.top + this.locals >= this.values.length) {
/* 266 */       throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
/*     */     }
/*     */     
/* 269 */     this.values[this.top++ + this.locals] = paramV; } public void execute(AbstractInsnNode paramAbstractInsnNode, Interpreter<V> paramInterpreter) throws AnalyzerException {
/*     */     V v1;
/*     */     V v2;
/*     */     V v3;
/*     */     ArrayList<V> arrayList;
/*     */     int i;
/*     */     String str;
/*     */     int j;
/*     */     int k;
/* 278 */     switch (paramAbstractInsnNode.getOpcode()) {
/*     */       case 0:
/*     */         return;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/* 299 */         push(paramInterpreter.newOperation(paramAbstractInsnNode));
/*     */       
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/* 306 */         push(paramInterpreter.copyOperation(paramAbstractInsnNode, 
/* 307 */               getLocal(((VarInsnNode)paramAbstractInsnNode).var)));
/*     */       
/*     */       case 46:
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/* 317 */         v2 = pop();
/* 318 */         v1 = pop();
/* 319 */         push(paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2));
/*     */       
/*     */       case 54:
/*     */       case 55:
/*     */       case 56:
/*     */       case 57:
/*     */       case 58:
/* 326 */         v1 = paramInterpreter.copyOperation(paramAbstractInsnNode, pop());
/* 327 */         i = ((VarInsnNode)paramAbstractInsnNode).var;
/* 328 */         setLocal(i, v1);
/* 329 */         if (v1.getSize() == 2) {
/* 330 */           setLocal(i + 1, paramInterpreter.newValue(null));
/*     */         }
/* 332 */         if (i > 0) {
/* 333 */           V v = getLocal(i - 1);
/* 334 */           if (v != null && v.getSize() == 2) {
/* 335 */             setLocal(i - 1, paramInterpreter.newValue(null));
/*     */           }
/*     */         } 
/*     */       
/*     */       case 79:
/*     */       case 80:
/*     */       case 81:
/*     */       case 82:
/*     */       case 83:
/*     */       case 84:
/*     */       case 85:
/*     */       case 86:
/* 347 */         v3 = pop();
/* 348 */         v2 = pop();
/* 349 */         v1 = pop();
/* 350 */         paramInterpreter.ternaryOperation(paramAbstractInsnNode, v1, v2, v3);
/*     */       
/*     */       case 87:
/* 353 */         if (pop().getSize() == 2) {
/* 354 */           throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of POP");
/*     */         }
/*     */       
/*     */       case 88:
/* 358 */         if (pop().getSize() == 1 && 
/* 359 */           pop().getSize() != 1) {
/* 360 */           throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of POP2");
/*     */         }
/*     */ 
/*     */       
/*     */       case 89:
/* 365 */         v1 = pop();
/* 366 */         if (v1.getSize() != 1) {
/* 367 */           throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP");
/*     */         }
/* 369 */         push(v1);
/* 370 */         push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/*     */       
/*     */       case 90:
/* 373 */         v1 = pop();
/* 374 */         v2 = pop();
/* 375 */         if (v1.getSize() != 1 || v2.getSize() != 1) {
/* 376 */           throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP_X1");
/*     */         }
/* 378 */         push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/* 379 */         push(v2);
/* 380 */         push(v1);
/*     */       
/*     */       case 91:
/* 383 */         v1 = pop();
/* 384 */         if (v1.getSize() == 1)
/* 385 */         { v2 = pop();
/* 386 */           if (v2.getSize() == 1)
/* 387 */           { v3 = pop();
/* 388 */             if (v3.getSize() == 1)
/* 389 */             { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/* 390 */               push(v3);
/* 391 */               push(v2);
/* 392 */               push(v1);
/*     */ 
/*     */               
/*     */                }
/*     */             
/*     */             else
/*     */             
/*     */             { 
/*     */ 
/*     */               
/* 402 */               throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP_X2"); }  } else { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); push(v2); push(v1); }  } else { throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP_X2"); } 
/*     */       case 92:
/* 404 */         v1 = pop();
/* 405 */         if (v1.getSize() == 1)
/* 406 */         { v2 = pop();
/* 407 */           if (v2.getSize() == 1)
/* 408 */           { push(v2);
/* 409 */             push(v1);
/* 410 */             push(paramInterpreter.copyOperation(paramAbstractInsnNode, v2));
/* 411 */             push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/*     */             
/*     */              }
/*     */           
/*     */           else
/*     */           
/*     */           { 
/*     */             
/* 419 */             throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2"); }  } else { push(v1); push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); } 
/*     */       case 93:
/* 421 */         v1 = pop();
/* 422 */         if (v1.getSize() == 1)
/* 423 */         { v2 = pop();
/* 424 */           if (v2.getSize() == 1)
/* 425 */           { v3 = pop();
/* 426 */             if (v3.getSize() == 1)
/* 427 */             { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v2));
/* 428 */               push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/* 429 */               push(v3);
/* 430 */               push(v2);
/* 431 */               push(v1);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */                }
/*     */             
/*     */             else
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */               
/* 444 */               throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X1"); }  } else { throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X1"); }  } else { v2 = pop(); if (v2.getSize() == 1) { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); push(v2); push(v1); } else { throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X1"); }  } 
/*     */       case 94:
/* 446 */         v1 = pop();
/* 447 */         if (v1.getSize() == 1)
/* 448 */         { v2 = pop();
/* 449 */           if (v2.getSize() == 1)
/* 450 */           { v3 = pop();
/* 451 */             if (v3.getSize() == 1)
/* 452 */             { V v = pop();
/* 453 */               if (v.getSize() == 1)
/* 454 */               { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v2));
/* 455 */                 push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/* 456 */                 push(v);
/* 457 */                 push(v3);
/* 458 */                 push(v2);
/* 459 */                 push(v1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                  }
/*     */               
/*     */               else
/*     */               
/*     */               { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 489 */                 throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X2"); }  } else { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v2)); push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); push(v3); push(v2); push(v1); }  } else { throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X2"); }  } else { v2 = pop(); if (v2.getSize() == 1) { v3 = pop(); if (v3.getSize() == 1) { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); push(v3); push(v2); push(v1); } else { throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of DUP2_X2"); }  } else { push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1)); push(v2); push(v1); }  } 
/*     */       case 95:
/* 491 */         v2 = pop();
/* 492 */         v1 = pop();
/* 493 */         if (v1.getSize() != 1 || v2.getSize() != 1) {
/* 494 */           throw new AnalyzerException(paramAbstractInsnNode, "Illegal use of SWAP");
/*     */         }
/* 496 */         push(paramInterpreter.copyOperation(paramAbstractInsnNode, v2));
/* 497 */         push(paramInterpreter.copyOperation(paramAbstractInsnNode, v1));
/*     */       
/*     */       case 96:
/*     */       case 97:
/*     */       case 98:
/*     */       case 99:
/*     */       case 100:
/*     */       case 101:
/*     */       case 102:
/*     */       case 103:
/*     */       case 104:
/*     */       case 105:
/*     */       case 106:
/*     */       case 107:
/*     */       case 108:
/*     */       case 109:
/*     */       case 110:
/*     */       case 111:
/*     */       case 112:
/*     */       case 113:
/*     */       case 114:
/*     */       case 115:
/* 519 */         v2 = pop();
/* 520 */         v1 = pop();
/* 521 */         push(paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2));
/*     */       
/*     */       case 116:
/*     */       case 117:
/*     */       case 118:
/*     */       case 119:
/* 527 */         push(paramInterpreter.unaryOperation(paramAbstractInsnNode, pop()));
/*     */       
/*     */       case 120:
/*     */       case 121:
/*     */       case 122:
/*     */       case 123:
/*     */       case 124:
/*     */       case 125:
/*     */       case 126:
/*     */       case 127:
/*     */       case 128:
/*     */       case 129:
/*     */       case 130:
/*     */       case 131:
/* 541 */         v2 = pop();
/* 542 */         v1 = pop();
/* 543 */         push(paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2));
/*     */       
/*     */       case 132:
/* 546 */         i = ((IincInsnNode)paramAbstractInsnNode).var;
/* 547 */         setLocal(i, paramInterpreter.unaryOperation(paramAbstractInsnNode, getLocal(i)));
/*     */       
/*     */       case 133:
/*     */       case 134:
/*     */       case 135:
/*     */       case 136:
/*     */       case 137:
/*     */       case 138:
/*     */       case 139:
/*     */       case 140:
/*     */       case 141:
/*     */       case 142:
/*     */       case 143:
/*     */       case 144:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/* 564 */         push(paramInterpreter.unaryOperation(paramAbstractInsnNode, pop()));
/*     */       
/*     */       case 148:
/*     */       case 149:
/*     */       case 150:
/*     */       case 151:
/*     */       case 152:
/* 571 */         v2 = pop();
/* 572 */         v1 = pop();
/* 573 */         push(paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2));
/*     */       
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/* 581 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */       
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/*     */       case 165:
/*     */       case 166:
/* 591 */         v2 = pop();
/* 592 */         v1 = pop();
/* 593 */         paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2);
/*     */       
/*     */       case 167:
/*     */         return;
/*     */       case 168:
/* 598 */         push(paramInterpreter.newOperation(paramAbstractInsnNode));
/*     */       
/*     */       case 169:
/*     */         return;
/*     */       case 170:
/*     */       case 171:
/* 604 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */       
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/* 611 */         v1 = pop();
/* 612 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, v1);
/* 613 */         paramInterpreter.returnOperation(paramAbstractInsnNode, v1, this.returnValue);
/*     */       
/*     */       case 177:
/* 616 */         if (this.returnValue != null) {
/* 617 */           throw new AnalyzerException(paramAbstractInsnNode, "Incompatible return type");
/*     */         }
/*     */       
/*     */       case 178:
/* 621 */         push(paramInterpreter.newOperation(paramAbstractInsnNode));
/*     */       
/*     */       case 179:
/* 624 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */       
/*     */       case 180:
/* 627 */         push(paramInterpreter.unaryOperation(paramAbstractInsnNode, pop()));
/*     */       
/*     */       case 181:
/* 630 */         v2 = pop();
/* 631 */         v1 = pop();
/* 632 */         paramInterpreter.binaryOperation(paramAbstractInsnNode, v1, v2);
/*     */       
/*     */       case 182:
/*     */       case 183:
/*     */       case 184:
/*     */       case 185:
/* 638 */         arrayList = new ArrayList();
/* 639 */         str = ((MethodInsnNode)paramAbstractInsnNode).desc;
/* 640 */         for (k = (Type.getArgumentTypes(str)).length; k > 0; k--) {
/* 641 */           arrayList.add(0, pop());
/*     */         }
/* 643 */         if (paramAbstractInsnNode.getOpcode() != 184) {
/* 644 */           arrayList.add(0, pop());
/*     */         }
/* 646 */         if (Type.getReturnType(str) == Type.VOID_TYPE) {
/* 647 */           paramInterpreter.naryOperation(paramAbstractInsnNode, arrayList);
/*     */         } else {
/* 649 */           push(paramInterpreter.naryOperation(paramAbstractInsnNode, arrayList));
/*     */         } 
/*     */ 
/*     */       
/*     */       case 186:
/* 654 */         arrayList = new ArrayList<>();
/* 655 */         str = ((InvokeDynamicInsnNode)paramAbstractInsnNode).desc;
/* 656 */         for (k = (Type.getArgumentTypes(str)).length; k > 0; k--) {
/* 657 */           arrayList.add(0, pop());
/*     */         }
/* 659 */         if (Type.getReturnType(str) == Type.VOID_TYPE) {
/* 660 */           paramInterpreter.naryOperation(paramAbstractInsnNode, arrayList);
/*     */         } else {
/* 662 */           push(paramInterpreter.naryOperation(paramAbstractInsnNode, arrayList));
/*     */         } 
/*     */ 
/*     */       
/*     */       case 187:
/* 667 */         push(paramInterpreter.newOperation(paramAbstractInsnNode));
/*     */       
/*     */       case 188:
/*     */       case 189:
/*     */       case 190:
/* 672 */         push(paramInterpreter.unaryOperation(paramAbstractInsnNode, pop()));
/*     */       
/*     */       case 191:
/* 675 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */       
/*     */       case 192:
/*     */       case 193:
/* 679 */         push(paramInterpreter.unaryOperation(paramAbstractInsnNode, pop()));
/*     */       
/*     */       case 194:
/*     */       case 195:
/* 683 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */       
/*     */       case 197:
/* 686 */         arrayList = new ArrayList<>();
/* 687 */         for (j = ((MultiANewArrayInsnNode)paramAbstractInsnNode).dims; j > 0; j--) {
/* 688 */           arrayList.add(0, pop());
/*     */         }
/* 690 */         push(paramInterpreter.naryOperation(paramAbstractInsnNode, arrayList));
/*     */       
/*     */       case 198:
/*     */       case 199:
/* 694 */         paramInterpreter.unaryOperation(paramAbstractInsnNode, pop());
/*     */     } 
/*     */     
/* 697 */     throw new RuntimeException("Illegal opcode " + paramAbstractInsnNode.getOpcode());
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
/*     */   public boolean merge(Frame<? extends V> paramFrame, Interpreter<V> paramInterpreter) throws AnalyzerException {
/* 715 */     if (this.top != paramFrame.top) {
/* 716 */       throw new AnalyzerException(null, "Incompatible stack heights");
/*     */     }
/* 718 */     boolean bool = false;
/* 719 */     for (byte b = 0; b < this.locals + this.top; b++) {
/* 720 */       V v = paramInterpreter.merge(this.values[b], paramFrame.values[b]);
/* 721 */       if (!v.equals(this.values[b])) {
/* 722 */         this.values[b] = v;
/* 723 */         bool = true;
/*     */       } 
/*     */     } 
/* 726 */     return bool;
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
/*     */   public boolean merge(Frame<? extends V> paramFrame, boolean[] paramArrayOfboolean) {
/* 741 */     boolean bool = false;
/* 742 */     for (byte b = 0; b < this.locals; b++) {
/* 743 */       if (!paramArrayOfboolean[b] && !this.values[b].equals(paramFrame.values[b])) {
/* 744 */         this.values[b] = paramFrame.values[b];
/* 745 */         bool = true;
/*     */       } 
/*     */     } 
/* 748 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 758 */     StringBuilder stringBuilder = new StringBuilder(); byte b;
/* 759 */     for (b = 0; b < getLocals(); b++) {
/* 760 */       stringBuilder.append(getLocal(b));
/*     */     }
/* 762 */     stringBuilder.append(' ');
/* 763 */     for (b = 0; b < getStackSize(); b++) {
/* 764 */       stringBuilder.append(getStack(b).toString());
/*     */     }
/* 766 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/analysis/Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */