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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnalyzerAdapter
/*     */   extends MethodVisitor
/*     */ {
/*     */   public List<Object> locals;
/*     */   public List<Object> stack;
/*     */   private List<Label> labels;
/*     */   public Map<Object, Object> uninitializedTypes;
/*     */   private int maxStack;
/*     */   private int maxLocals;
/*     */   private String owner;
/*     */   
/*     */   public AnalyzerAdapter(String paramString1, int paramInt, String paramString2, String paramString3, MethodVisitor paramMethodVisitor) {
/* 173 */     this(327680, paramString1, paramInt, paramString2, paramString3, paramMethodVisitor);
/* 174 */     if (getClass() != AnalyzerAdapter.class) {
/* 175 */       throw new IllegalStateException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AnalyzerAdapter(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, MethodVisitor paramMethodVisitor) {
/* 200 */     super(paramInt1, paramMethodVisitor);
/* 201 */     this.owner = paramString1;
/* 202 */     this.locals = new ArrayList();
/* 203 */     this.stack = new ArrayList();
/* 204 */     this.uninitializedTypes = new HashMap<>();
/*     */     
/* 206 */     if ((paramInt2 & 0x8) == 0) {
/* 207 */       if ("<init>".equals(paramString2)) {
/* 208 */         this.locals.add(Opcodes.UNINITIALIZED_THIS);
/*     */       } else {
/* 210 */         this.locals.add(paramString1);
/*     */       } 
/*     */     }
/* 213 */     Type[] arrayOfType = Type.getArgumentTypes(paramString3);
/* 214 */     for (byte b = 0; b < arrayOfType.length; b++) {
/* 215 */       Type type = arrayOfType[b];
/* 216 */       switch (type.getSort()) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/* 222 */           this.locals.add(Opcodes.INTEGER);
/*     */           break;
/*     */         case 6:
/* 225 */           this.locals.add(Opcodes.FLOAT);
/*     */           break;
/*     */         case 7:
/* 228 */           this.locals.add(Opcodes.LONG);
/* 229 */           this.locals.add(Opcodes.TOP);
/*     */           break;
/*     */         case 8:
/* 232 */           this.locals.add(Opcodes.DOUBLE);
/* 233 */           this.locals.add(Opcodes.TOP);
/*     */           break;
/*     */         case 9:
/* 236 */           this.locals.add(arrayOfType[b].getDescriptor());
/*     */           break;
/*     */         
/*     */         default:
/* 240 */           this.locals.add(arrayOfType[b].getInternalName()); break;
/*     */       } 
/*     */     } 
/* 243 */     this.maxLocals = this.locals.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 249 */     if (paramInt1 != -1) {
/* 250 */       throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
/*     */     }
/*     */ 
/*     */     
/* 254 */     if (this.mv != null) {
/* 255 */       this.mv.visitFrame(paramInt1, paramInt2, paramArrayOfObject1, paramInt3, paramArrayOfObject2);
/*     */     }
/*     */     
/* 258 */     if (this.locals != null) {
/* 259 */       this.locals.clear();
/* 260 */       this.stack.clear();
/*     */     } else {
/* 262 */       this.locals = new ArrayList();
/* 263 */       this.stack = new ArrayList();
/*     */     } 
/* 265 */     visitFrameTypes(paramInt2, paramArrayOfObject1, this.locals);
/* 266 */     visitFrameTypes(paramInt3, paramArrayOfObject2, this.stack);
/* 267 */     this.maxStack = Math.max(this.maxStack, this.stack.size());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void visitFrameTypes(int paramInt, Object[] paramArrayOfObject, List<Object> paramList) {
/* 272 */     for (byte b = 0; b < paramInt; b++) {
/* 273 */       Object object = paramArrayOfObject[b];
/* 274 */       paramList.add(object);
/* 275 */       if (object == Opcodes.LONG || object == Opcodes.DOUBLE) {
/* 276 */         paramList.add(Opcodes.TOP);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int paramInt) {
/* 283 */     if (this.mv != null) {
/* 284 */       this.mv.visitInsn(paramInt);
/*     */     }
/* 286 */     execute(paramInt, 0, (String)null);
/* 287 */     if ((paramInt >= 172 && paramInt <= 177) || paramInt == 191) {
/*     */       
/* 289 */       this.locals = null;
/* 290 */       this.stack = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int paramInt1, int paramInt2) {
/* 296 */     if (this.mv != null) {
/* 297 */       this.mv.visitIntInsn(paramInt1, paramInt2);
/*     */     }
/* 299 */     execute(paramInt1, paramInt2, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 304 */     if (this.mv != null) {
/* 305 */       this.mv.visitVarInsn(paramInt1, paramInt2);
/*     */     }
/* 307 */     execute(paramInt1, paramInt2, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 312 */     if (paramInt == 187) {
/* 313 */       if (this.labels == null) {
/* 314 */         Label label = new Label();
/* 315 */         this.labels = new ArrayList<>(3);
/* 316 */         this.labels.add(label);
/* 317 */         if (this.mv != null) {
/* 318 */           this.mv.visitLabel(label);
/*     */         }
/*     */       } 
/* 321 */       for (byte b = 0; b < this.labels.size(); b++) {
/* 322 */         this.uninitializedTypes.put(this.labels.get(b), paramString);
/*     */       }
/*     */     } 
/* 325 */     if (this.mv != null) {
/* 326 */       this.mv.visitTypeInsn(paramInt, paramString);
/*     */     }
/* 328 */     execute(paramInt, 0, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 334 */     if (this.mv != null) {
/* 335 */       this.mv.visitFieldInsn(paramInt, paramString1, paramString2, paramString3);
/*     */     }
/* 337 */     execute(paramInt, 0, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 344 */     if (this.api >= 327680) {
/* 345 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 348 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, (paramInt == 185));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 355 */     if (this.api < 327680) {
/* 356 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 359 */     doVisitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doVisitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 364 */     if (this.mv != null) {
/* 365 */       this.mv.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */     }
/* 367 */     if (this.locals == null) {
/* 368 */       this.labels = null;
/*     */       return;
/*     */     } 
/* 371 */     pop(paramString3);
/* 372 */     if (paramInt != 184) {
/* 373 */       Object object = pop();
/* 374 */       if (paramInt == 183 && paramString2.charAt(0) == '<') {
/*     */         Object object1;
/* 376 */         if (object == Opcodes.UNINITIALIZED_THIS) {
/* 377 */           object1 = this.owner;
/*     */         } else {
/* 379 */           object1 = this.uninitializedTypes.get(object);
/*     */         }  byte b;
/* 381 */         for (b = 0; b < this.locals.size(); b++) {
/* 382 */           if (this.locals.get(b) == object) {
/* 383 */             this.locals.set(b, object1);
/*     */           }
/*     */         } 
/* 386 */         for (b = 0; b < this.stack.size(); b++) {
/* 387 */           if (this.stack.get(b) == object) {
/* 388 */             this.stack.set(b, object1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 393 */     pushDesc(paramString3);
/* 394 */     this.labels = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 400 */     if (this.mv != null) {
/* 401 */       this.mv.visitInvokeDynamicInsn(paramString1, paramString2, paramHandle, paramVarArgs);
/*     */     }
/* 403 */     if (this.locals == null) {
/* 404 */       this.labels = null;
/*     */       return;
/*     */     } 
/* 407 */     pop(paramString2);
/* 408 */     pushDesc(paramString2);
/* 409 */     this.labels = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 414 */     if (this.mv != null) {
/* 415 */       this.mv.visitJumpInsn(paramInt, paramLabel);
/*     */     }
/* 417 */     execute(paramInt, 0, (String)null);
/* 418 */     if (paramInt == 167) {
/* 419 */       this.locals = null;
/* 420 */       this.stack = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label paramLabel) {
/* 426 */     if (this.mv != null) {
/* 427 */       this.mv.visitLabel(paramLabel);
/*     */     }
/* 429 */     if (this.labels == null) {
/* 430 */       this.labels = new ArrayList<>(3);
/*     */     }
/* 432 */     this.labels.add(paramLabel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 437 */     if (this.mv != null) {
/* 438 */       this.mv.visitLdcInsn(paramObject);
/*     */     }
/* 440 */     if (this.locals == null) {
/* 441 */       this.labels = null;
/*     */       return;
/*     */     } 
/* 444 */     if (paramObject instanceof Integer) {
/* 445 */       push(Opcodes.INTEGER);
/* 446 */     } else if (paramObject instanceof Long) {
/* 447 */       push(Opcodes.LONG);
/* 448 */       push(Opcodes.TOP);
/* 449 */     } else if (paramObject instanceof Float) {
/* 450 */       push(Opcodes.FLOAT);
/* 451 */     } else if (paramObject instanceof Double) {
/* 452 */       push(Opcodes.DOUBLE);
/* 453 */       push(Opcodes.TOP);
/* 454 */     } else if (paramObject instanceof String) {
/* 455 */       push("java/lang/String");
/* 456 */     } else if (paramObject instanceof Type) {
/* 457 */       int i = ((Type)paramObject).getSort();
/* 458 */       if (i == 10 || i == 9) {
/* 459 */         push("java/lang/Class");
/* 460 */       } else if (i == 11) {
/* 461 */         push("java/lang/invoke/MethodType");
/*     */       } else {
/* 463 */         throw new IllegalArgumentException();
/*     */       } 
/* 465 */     } else if (paramObject instanceof Handle) {
/* 466 */       push("java/lang/invoke/MethodHandle");
/*     */     } else {
/* 468 */       throw new IllegalArgumentException();
/*     */     } 
/* 470 */     this.labels = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 475 */     if (this.mv != null) {
/* 476 */       this.mv.visitIincInsn(paramInt1, paramInt2);
/*     */     }
/* 478 */     execute(132, paramInt1, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 484 */     if (this.mv != null) {
/* 485 */       this.mv.visitTableSwitchInsn(paramInt1, paramInt2, paramLabel, paramVarArgs);
/*     */     }
/* 487 */     execute(170, 0, (String)null);
/* 488 */     this.locals = null;
/* 489 */     this.stack = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 495 */     if (this.mv != null) {
/* 496 */       this.mv.visitLookupSwitchInsn(paramLabel, paramArrayOfint, paramArrayOfLabel);
/*     */     }
/* 498 */     execute(171, 0, (String)null);
/* 499 */     this.locals = null;
/* 500 */     this.stack = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 505 */     if (this.mv != null) {
/* 506 */       this.mv.visitMultiANewArrayInsn(paramString, paramInt);
/*     */     }
/* 508 */     execute(197, paramInt, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 513 */     if (this.mv != null) {
/* 514 */       this.maxStack = Math.max(this.maxStack, paramInt1);
/* 515 */       this.maxLocals = Math.max(this.maxLocals, paramInt2);
/* 516 */       this.mv.visitMaxs(this.maxStack, this.maxLocals);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Object get(int paramInt) {
/* 523 */     this.maxLocals = Math.max(this.maxLocals, paramInt + 1);
/* 524 */     return (paramInt < this.locals.size()) ? this.locals.get(paramInt) : Opcodes.TOP;
/*     */   }
/*     */   
/*     */   private void set(int paramInt, Object paramObject) {
/* 528 */     this.maxLocals = Math.max(this.maxLocals, paramInt + 1);
/* 529 */     while (paramInt >= this.locals.size()) {
/* 530 */       this.locals.add(Opcodes.TOP);
/*     */     }
/* 532 */     this.locals.set(paramInt, paramObject);
/*     */   }
/*     */   
/*     */   private void push(Object paramObject) {
/* 536 */     this.stack.add(paramObject);
/* 537 */     this.maxStack = Math.max(this.maxStack, this.stack.size());
/*     */   }
/*     */   
/*     */   private void pushDesc(String paramString) {
/* 541 */     byte b = (paramString.charAt(0) == '(') ? (paramString.indexOf(')') + 1) : 0;
/* 542 */     switch (paramString.charAt(b)) {
/*     */       case 'V':
/*     */         return;
/*     */       case 'B':
/*     */       case 'C':
/*     */       case 'I':
/*     */       case 'S':
/*     */       case 'Z':
/* 550 */         push(Opcodes.INTEGER);
/*     */         return;
/*     */       case 'F':
/* 553 */         push(Opcodes.FLOAT);
/*     */         return;
/*     */       case 'J':
/* 556 */         push(Opcodes.LONG);
/* 557 */         push(Opcodes.TOP);
/*     */         return;
/*     */       case 'D':
/* 560 */         push(Opcodes.DOUBLE);
/* 561 */         push(Opcodes.TOP);
/*     */         return;
/*     */       case '[':
/* 564 */         if (!b) {
/* 565 */           push(paramString);
/*     */         } else {
/* 567 */           push(paramString.substring(b, paramString.length()));
/*     */         } 
/*     */         return;
/*     */     } 
/*     */     
/* 572 */     if (!b) {
/* 573 */       push(paramString.substring(1, paramString.length() - 1));
/*     */     } else {
/* 575 */       push(paramString.substring(b + 1, paramString.length() - 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Object pop() {
/* 581 */     return this.stack.remove(this.stack.size() - 1);
/*     */   }
/*     */   
/*     */   private void pop(int paramInt) {
/* 585 */     int i = this.stack.size();
/* 586 */     int j = i - paramInt;
/* 587 */     for (int k = i - 1; k >= j; k--) {
/* 588 */       this.stack.remove(k);
/*     */     }
/*     */   }
/*     */   
/*     */   private void pop(String paramString) {
/* 593 */     char c = paramString.charAt(0);
/* 594 */     if (c == '(') {
/* 595 */       int i = 0;
/* 596 */       Type[] arrayOfType = Type.getArgumentTypes(paramString);
/* 597 */       for (byte b = 0; b < arrayOfType.length; b++) {
/* 598 */         i += arrayOfType[b].getSize();
/*     */       }
/* 600 */       pop(i);
/* 601 */     } else if (c == 'J' || c == 'D') {
/* 602 */       pop(2);
/*     */     } else {
/* 604 */       pop(1);
/*     */     } 
/*     */   }
/*     */   private void execute(int paramInt1, int paramInt2, String paramString) {
/*     */     Object object1, object2, object3, object4;
/* 609 */     if (this.locals == null) {
/* 610 */       this.labels = null;
/*     */       
/*     */       return;
/*     */     } 
/* 614 */     switch (paramInt1) {
/*     */       case 0:
/*     */       case 116:
/*     */       case 117:
/*     */       case 118:
/*     */       case 119:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/*     */       case 167:
/*     */       case 177:
/*     */         break;
/*     */       case 1:
/* 627 */         push(Opcodes.NULL);
/*     */         break;
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 16:
/*     */       case 17:
/* 638 */         push(Opcodes.INTEGER);
/*     */         break;
/*     */       case 9:
/*     */       case 10:
/* 642 */         push(Opcodes.LONG);
/* 643 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/* 648 */         push(Opcodes.FLOAT);
/*     */         break;
/*     */       case 14:
/*     */       case 15:
/* 652 */         push(Opcodes.DOUBLE);
/* 653 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 21:
/*     */       case 23:
/*     */       case 25:
/* 658 */         push(get(paramInt2));
/*     */         break;
/*     */       case 22:
/*     */       case 24:
/* 662 */         push(get(paramInt2));
/* 663 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 46:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/* 669 */         pop(2);
/* 670 */         push(Opcodes.INTEGER);
/*     */         break;
/*     */       case 47:
/*     */       case 143:
/* 674 */         pop(2);
/* 675 */         push(Opcodes.LONG);
/* 676 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 48:
/* 679 */         pop(2);
/* 680 */         push(Opcodes.FLOAT);
/*     */         break;
/*     */       case 49:
/*     */       case 138:
/* 684 */         pop(2);
/* 685 */         push(Opcodes.DOUBLE);
/* 686 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 50:
/* 689 */         pop(1);
/* 690 */         object1 = pop();
/* 691 */         if (object1 instanceof String) {
/* 692 */           pushDesc(((String)object1).substring(1)); break;
/*     */         } 
/* 694 */         push("java/lang/Object");
/*     */         break;
/*     */       
/*     */       case 54:
/*     */       case 56:
/*     */       case 58:
/* 700 */         object1 = pop();
/* 701 */         set(paramInt2, object1);
/* 702 */         if (paramInt2 > 0) {
/* 703 */           Object object = get(paramInt2 - 1);
/* 704 */           if (object == Opcodes.LONG || object == Opcodes.DOUBLE) {
/* 705 */             set(paramInt2 - 1, Opcodes.TOP);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case 55:
/*     */       case 57:
/* 711 */         pop(1);
/* 712 */         object1 = pop();
/* 713 */         set(paramInt2, object1);
/* 714 */         set(paramInt2 + 1, Opcodes.TOP);
/* 715 */         if (paramInt2 > 0) {
/* 716 */           Object object = get(paramInt2 - 1);
/* 717 */           if (object == Opcodes.LONG || object == Opcodes.DOUBLE) {
/* 718 */             set(paramInt2 - 1, Opcodes.TOP);
/*     */           }
/*     */         } 
/*     */         break;
/*     */       case 79:
/*     */       case 81:
/*     */       case 83:
/*     */       case 84:
/*     */       case 85:
/*     */       case 86:
/* 728 */         pop(3);
/*     */         break;
/*     */       case 80:
/*     */       case 82:
/* 732 */         pop(4);
/*     */         break;
/*     */       case 87:
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 174:
/*     */       case 176:
/*     */       case 191:
/*     */       case 194:
/*     */       case 195:
/*     */       case 198:
/*     */       case 199:
/* 751 */         pop(1);
/*     */         break;
/*     */       case 88:
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/*     */       case 165:
/*     */       case 166:
/*     */       case 173:
/*     */       case 175:
/* 764 */         pop(2);
/*     */         break;
/*     */       case 89:
/* 767 */         object1 = pop();
/* 768 */         push(object1);
/* 769 */         push(object1);
/*     */         break;
/*     */       case 90:
/* 772 */         object1 = pop();
/* 773 */         object2 = pop();
/* 774 */         push(object1);
/* 775 */         push(object2);
/* 776 */         push(object1);
/*     */         break;
/*     */       case 91:
/* 779 */         object1 = pop();
/* 780 */         object2 = pop();
/* 781 */         object3 = pop();
/* 782 */         push(object1);
/* 783 */         push(object3);
/* 784 */         push(object2);
/* 785 */         push(object1);
/*     */         break;
/*     */       case 92:
/* 788 */         object1 = pop();
/* 789 */         object2 = pop();
/* 790 */         push(object2);
/* 791 */         push(object1);
/* 792 */         push(object2);
/* 793 */         push(object1);
/*     */         break;
/*     */       case 93:
/* 796 */         object1 = pop();
/* 797 */         object2 = pop();
/* 798 */         object3 = pop();
/* 799 */         push(object2);
/* 800 */         push(object1);
/* 801 */         push(object3);
/* 802 */         push(object2);
/* 803 */         push(object1);
/*     */         break;
/*     */       case 94:
/* 806 */         object1 = pop();
/* 807 */         object2 = pop();
/* 808 */         object3 = pop();
/* 809 */         object4 = pop();
/* 810 */         push(object2);
/* 811 */         push(object1);
/* 812 */         push(object4);
/* 813 */         push(object3);
/* 814 */         push(object2);
/* 815 */         push(object1);
/*     */         break;
/*     */       case 95:
/* 818 */         object1 = pop();
/* 819 */         object2 = pop();
/* 820 */         push(object1);
/* 821 */         push(object2);
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
/*     */       case 136:
/*     */       case 142:
/*     */       case 149:
/*     */       case 150:
/* 838 */         pop(2);
/* 839 */         push(Opcodes.INTEGER);
/*     */         break;
/*     */       case 97:
/*     */       case 101:
/*     */       case 105:
/*     */       case 109:
/*     */       case 113:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/* 849 */         pop(4);
/* 850 */         push(Opcodes.LONG);
/* 851 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 98:
/*     */       case 102:
/*     */       case 106:
/*     */       case 110:
/*     */       case 114:
/*     */       case 137:
/*     */       case 144:
/* 860 */         pop(2);
/* 861 */         push(Opcodes.FLOAT);
/*     */         break;
/*     */       case 99:
/*     */       case 103:
/*     */       case 107:
/*     */       case 111:
/*     */       case 115:
/* 868 */         pop(4);
/* 869 */         push(Opcodes.DOUBLE);
/* 870 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/* 875 */         pop(3);
/* 876 */         push(Opcodes.LONG);
/* 877 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 132:
/* 880 */         set(paramInt2, Opcodes.INTEGER);
/*     */         break;
/*     */       case 133:
/*     */       case 140:
/* 884 */         pop(1);
/* 885 */         push(Opcodes.LONG);
/* 886 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 134:
/* 889 */         pop(1);
/* 890 */         push(Opcodes.FLOAT);
/*     */         break;
/*     */       case 135:
/*     */       case 141:
/* 894 */         pop(1);
/* 895 */         push(Opcodes.DOUBLE);
/* 896 */         push(Opcodes.TOP);
/*     */         break;
/*     */       case 139:
/*     */       case 190:
/*     */       case 193:
/* 901 */         pop(1);
/* 902 */         push(Opcodes.INTEGER);
/*     */         break;
/*     */       case 148:
/*     */       case 151:
/*     */       case 152:
/* 907 */         pop(4);
/* 908 */         push(Opcodes.INTEGER);
/*     */         break;
/*     */       case 168:
/*     */       case 169:
/* 912 */         throw new RuntimeException("JSR/RET are not supported");
/*     */       case 178:
/* 914 */         pushDesc(paramString);
/*     */         break;
/*     */       case 179:
/* 917 */         pop(paramString);
/*     */         break;
/*     */       case 180:
/* 920 */         pop(1);
/* 921 */         pushDesc(paramString);
/*     */         break;
/*     */       case 181:
/* 924 */         pop(paramString);
/* 925 */         pop();
/*     */         break;
/*     */       case 187:
/* 928 */         push(this.labels.get(0));
/*     */         break;
/*     */       case 188:
/* 931 */         pop();
/* 932 */         switch (paramInt2) {
/*     */           case 4:
/* 934 */             pushDesc("[Z");
/*     */             break;
/*     */           case 5:
/* 937 */             pushDesc("[C");
/*     */             break;
/*     */           case 8:
/* 940 */             pushDesc("[B");
/*     */             break;
/*     */           case 9:
/* 943 */             pushDesc("[S");
/*     */             break;
/*     */           case 10:
/* 946 */             pushDesc("[I");
/*     */             break;
/*     */           case 6:
/* 949 */             pushDesc("[F");
/*     */             break;
/*     */           case 7:
/* 952 */             pushDesc("[D");
/*     */             break;
/*     */         } 
/*     */         
/* 956 */         pushDesc("[J");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 189:
/* 961 */         pop();
/* 962 */         pushDesc("[" + Type.getObjectType(paramString));
/*     */         break;
/*     */       case 192:
/* 965 */         pop();
/* 966 */         pushDesc(Type.getObjectType(paramString).getDescriptor());
/*     */         break;
/*     */       
/*     */       default:
/* 970 */         pop(paramInt2);
/* 971 */         pushDesc(paramString);
/*     */         break;
/*     */     } 
/* 974 */     this.labels = null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/AnalyzerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */