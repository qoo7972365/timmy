/*     */ package jdk.internal.org.objectweb.asm.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Attribute;
/*     */ import jdk.internal.org.objectweb.asm.ClassVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Handle;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.TypePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodNode
/*     */   extends MethodVisitor
/*     */ {
/*     */   public int access;
/*     */   public String name;
/*     */   public String desc;
/*     */   public String signature;
/*     */   public List<String> exceptions;
/*     */   public List<ParameterNode> parameters;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   public Object annotationDefault;
/*     */   public List<AnnotationNode>[] visibleParameterAnnotations;
/*     */   public List<AnnotationNode>[] invisibleParameterAnnotations;
/*     */   public InsnList instructions;
/*     */   public List<TryCatchBlockNode> tryCatchBlocks;
/*     */   public int maxStack;
/*     */   public int maxLocals;
/*     */   public List<LocalVariableNode> localVariables;
/*     */   public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
/*     */   public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
/*     */   private boolean visited;
/*     */   
/*     */   public MethodNode() {
/* 252 */     this(327680);
/* 253 */     if (getClass() != MethodNode.class) {
/* 254 */       throw new IllegalStateException();
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
/*     */   public MethodNode(int paramInt) {
/* 266 */     super(paramInt);
/* 267 */     this.instructions = new InsnList();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode(int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 294 */     this(327680, paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/* 295 */     if (getClass() != MethodNode.class) {
/* 296 */       throw new IllegalStateException();
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
/*     */ 
/*     */   
/*     */   public MethodNode(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 323 */     super(paramInt1);
/* 324 */     this.access = paramInt2;
/* 325 */     this.name = paramString1;
/* 326 */     this.desc = paramString2;
/* 327 */     this.signature = paramString3;
/* 328 */     this.exceptions = new ArrayList<>((paramArrayOfString == null) ? 0 : paramArrayOfString.length);
/*     */     
/* 330 */     boolean bool = ((paramInt2 & 0x400) != 0) ? true : false;
/* 331 */     if (!bool) {
/* 332 */       this.localVariables = new ArrayList<>(5);
/*     */     }
/* 334 */     this.tryCatchBlocks = new ArrayList<>();
/* 335 */     if (paramArrayOfString != null) {
/* 336 */       this.exceptions.addAll(Arrays.asList(paramArrayOfString));
/*     */     }
/* 338 */     this.instructions = new InsnList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitParameter(String paramString, int paramInt) {
/* 347 */     if (this.parameters == null) {
/* 348 */       this.parameters = new ArrayList<>(5);
/*     */     }
/* 350 */     this.parameters.add(new ParameterNode(paramString, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/* 356 */     return new AnnotationNode(new ArrayList(0)
/*     */         {
/*     */           public boolean add(Object param1Object) {
/* 359 */             MethodNode.this.annotationDefault = param1Object;
/* 360 */             return super.add(param1Object);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 368 */     AnnotationNode annotationNode = new AnnotationNode(paramString);
/* 369 */     if (paramBoolean) {
/* 370 */       if (this.visibleAnnotations == null) {
/* 371 */         this.visibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 373 */       this.visibleAnnotations.add(annotationNode);
/*     */     } else {
/* 375 */       if (this.invisibleAnnotations == null) {
/* 376 */         this.invisibleAnnotations = new ArrayList<>(1);
/*     */       }
/* 378 */       this.invisibleAnnotations.add(annotationNode);
/*     */     } 
/* 380 */     return annotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 386 */     TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
/* 387 */     if (paramBoolean) {
/* 388 */       if (this.visibleTypeAnnotations == null) {
/* 389 */         this.visibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 391 */       this.visibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } else {
/* 393 */       if (this.invisibleTypeAnnotations == null) {
/* 394 */         this.invisibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/* 396 */       this.invisibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } 
/* 398 */     return typeAnnotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/* 405 */     AnnotationNode annotationNode = new AnnotationNode(paramString);
/* 406 */     if (paramBoolean) {
/* 407 */       if (this.visibleParameterAnnotations == null) {
/* 408 */         int i = (Type.getArgumentTypes(this.desc)).length;
/* 409 */         this.visibleParameterAnnotations = (List<AnnotationNode>[])new List[i];
/*     */       } 
/* 411 */       if (this.visibleParameterAnnotations[paramInt] == null) {
/* 412 */         this.visibleParameterAnnotations[paramInt] = new ArrayList<>(1);
/*     */       }
/*     */       
/* 415 */       this.visibleParameterAnnotations[paramInt].add(annotationNode);
/*     */     } else {
/* 417 */       if (this.invisibleParameterAnnotations == null) {
/* 418 */         int i = (Type.getArgumentTypes(this.desc)).length;
/* 419 */         this.invisibleParameterAnnotations = (List<AnnotationNode>[])new List[i];
/*     */       } 
/* 421 */       if (this.invisibleParameterAnnotations[paramInt] == null) {
/* 422 */         this.invisibleParameterAnnotations[paramInt] = new ArrayList<>(1);
/*     */       }
/*     */       
/* 425 */       this.invisibleParameterAnnotations[paramInt].add(annotationNode);
/*     */     } 
/* 427 */     return annotationNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 432 */     if (this.attrs == null) {
/* 433 */       this.attrs = new ArrayList<>(1);
/*     */     }
/* 435 */     this.attrs.add(paramAttribute);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCode() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 445 */     this.instructions.add(new FrameNode(paramInt1, paramInt2, (paramArrayOfObject1 == null) ? null : 
/* 446 */           getLabelNodes(paramArrayOfObject1), paramInt3, (paramArrayOfObject2 == null) ? null : 
/* 447 */           getLabelNodes(paramArrayOfObject2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int paramInt) {
/* 452 */     this.instructions.add(new InsnNode(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int paramInt1, int paramInt2) {
/* 457 */     this.instructions.add(new IntInsnNode(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/* 462 */     this.instructions.add(new VarInsnNode(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int paramInt, String paramString) {
/* 467 */     this.instructions.add(new TypeInsnNode(paramInt, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 473 */     this.instructions.add(new FieldInsnNode(paramInt, paramString1, paramString2, paramString3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/* 480 */     if (this.api >= 327680) {
/* 481 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3);
/*     */       return;
/*     */     } 
/* 484 */     this.instructions.add(new MethodInsnNode(paramInt, paramString1, paramString2, paramString3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 490 */     if (this.api < 327680) {
/* 491 */       super.visitMethodInsn(paramInt, paramString1, paramString2, paramString3, paramBoolean);
/*     */       return;
/*     */     } 
/* 494 */     this.instructions.add(new MethodInsnNode(paramInt, paramString1, paramString2, paramString3, paramBoolean));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/* 500 */     this.instructions.add(new InvokeDynamicInsnNode(paramString1, paramString2, paramHandle, paramVarArgs));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 505 */     this.instructions.add(new JumpInsnNode(paramInt, getLabelNode(paramLabel)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label paramLabel) {
/* 510 */     this.instructions.add(getLabelNode(paramLabel));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object paramObject) {
/* 515 */     this.instructions.add(new LdcInsnNode(paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 520 */     this.instructions.add(new IincInsnNode(paramInt1, paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 526 */     this.instructions.add(new TableSwitchInsnNode(paramInt1, paramInt2, getLabelNode(paramLabel), 
/* 527 */           getLabelNodes(paramVarArgs)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 533 */     this.instructions.add(new LookupSwitchInsnNode(getLabelNode(paramLabel), paramArrayOfint, 
/* 534 */           getLabelNodes(paramArrayOfLabel)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 539 */     this.instructions.add(new MultiANewArrayInsnNode(paramString, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 547 */     AbstractInsnNode abstractInsnNode = this.instructions.getLast();
/* 548 */     while (abstractInsnNode.getOpcode() == -1) {
/* 549 */       abstractInsnNode = abstractInsnNode.getPrevious();
/*     */     }
/*     */     
/* 552 */     TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
/* 553 */     if (paramBoolean) {
/* 554 */       if (abstractInsnNode.visibleTypeAnnotations == null) {
/* 555 */         abstractInsnNode.visibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 558 */       abstractInsnNode.visibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } else {
/* 560 */       if (abstractInsnNode.invisibleTypeAnnotations == null) {
/* 561 */         abstractInsnNode.invisibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 564 */       abstractInsnNode.invisibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } 
/* 566 */     return typeAnnotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 572 */     this.tryCatchBlocks.add(new TryCatchBlockNode(getLabelNode(paramLabel1), 
/* 573 */           getLabelNode(paramLabel2), getLabelNode(paramLabel3), paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 579 */     TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get((paramInt & 0xFFFF00) >> 8);
/* 580 */     TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(paramInt, paramTypePath, paramString);
/* 581 */     if (paramBoolean) {
/* 582 */       if (tryCatchBlockNode.visibleTypeAnnotations == null) {
/* 583 */         tryCatchBlockNode.visibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 586 */       tryCatchBlockNode.visibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } else {
/* 588 */       if (tryCatchBlockNode.invisibleTypeAnnotations == null) {
/* 589 */         tryCatchBlockNode.invisibleTypeAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 592 */       tryCatchBlockNode.invisibleTypeAnnotations.add(typeAnnotationNode);
/*     */     } 
/* 594 */     return typeAnnotationNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 601 */     this.localVariables.add(new LocalVariableNode(paramString1, paramString2, paramString3, 
/* 602 */           getLabelNode(paramLabel1), getLabelNode(paramLabel2), paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 610 */     LocalVariableAnnotationNode localVariableAnnotationNode = new LocalVariableAnnotationNode(paramInt, paramTypePath, getLabelNodes(paramArrayOfLabel1), getLabelNodes(paramArrayOfLabel2), paramArrayOfint, paramString);
/*     */     
/* 612 */     if (paramBoolean) {
/* 613 */       if (this.visibleLocalVariableAnnotations == null) {
/* 614 */         this.visibleLocalVariableAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 617 */       this.visibleLocalVariableAnnotations.add(localVariableAnnotationNode);
/*     */     } else {
/* 619 */       if (this.invisibleLocalVariableAnnotations == null) {
/* 620 */         this.invisibleLocalVariableAnnotations = new ArrayList<>(1);
/*     */       }
/*     */       
/* 623 */       this.invisibleLocalVariableAnnotations.add(localVariableAnnotationNode);
/*     */     } 
/* 625 */     return localVariableAnnotationNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLineNumber(int paramInt, Label paramLabel) {
/* 630 */     this.instructions.add(new LineNumberNode(paramInt, getLabelNode(paramLabel)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 635 */     this.maxStack = paramInt1;
/* 636 */     this.maxLocals = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LabelNode getLabelNode(Label paramLabel) {
/* 654 */     if (!(paramLabel.info instanceof LabelNode)) {
/* 655 */       paramLabel.info = new LabelNode();
/*     */     }
/* 657 */     return (LabelNode)paramLabel.info;
/*     */   }
/*     */   
/*     */   private LabelNode[] getLabelNodes(Label[] paramArrayOfLabel) {
/* 661 */     LabelNode[] arrayOfLabelNode = new LabelNode[paramArrayOfLabel.length];
/* 662 */     for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/* 663 */       arrayOfLabelNode[b] = getLabelNode(paramArrayOfLabel[b]);
/*     */     }
/* 665 */     return arrayOfLabelNode;
/*     */   }
/*     */   
/*     */   private Object[] getLabelNodes(Object[] paramArrayOfObject) {
/* 669 */     Object[] arrayOfObject = new Object[paramArrayOfObject.length];
/* 670 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 671 */       Object object = paramArrayOfObject[b];
/* 672 */       if (object instanceof Label) {
/* 673 */         object = getLabelNode((Label)object);
/*     */       }
/* 675 */       arrayOfObject[b] = object;
/*     */     } 
/* 677 */     return arrayOfObject;
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
/*     */   public void check(int paramInt) {
/* 695 */     if (paramInt == 262144) {
/* 696 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 697 */         .size() > 0) {
/* 698 */         throw new RuntimeException();
/*     */       }
/* 700 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 701 */         .size() > 0) {
/* 702 */         throw new RuntimeException();
/*     */       }
/* 704 */       byte b1 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(); byte b2;
/* 705 */       for (b2 = 0; b2 < b1; b2++) {
/* 706 */         TryCatchBlockNode tryCatchBlockNode = this.tryCatchBlocks.get(b2);
/* 707 */         if (tryCatchBlockNode.visibleTypeAnnotations != null && tryCatchBlockNode.visibleTypeAnnotations
/* 708 */           .size() > 0) {
/* 709 */           throw new RuntimeException();
/*     */         }
/* 711 */         if (tryCatchBlockNode.invisibleTypeAnnotations != null && tryCatchBlockNode.invisibleTypeAnnotations
/* 712 */           .size() > 0) {
/* 713 */           throw new RuntimeException();
/*     */         }
/*     */       } 
/* 716 */       for (b2 = 0; b2 < this.instructions.size(); b2++) {
/* 717 */         AbstractInsnNode abstractInsnNode = this.instructions.get(b2);
/* 718 */         if (abstractInsnNode.visibleTypeAnnotations != null && abstractInsnNode.visibleTypeAnnotations
/* 719 */           .size() > 0) {
/* 720 */           throw new RuntimeException();
/*     */         }
/* 722 */         if (abstractInsnNode.invisibleTypeAnnotations != null && abstractInsnNode.invisibleTypeAnnotations
/* 723 */           .size() > 0) {
/* 724 */           throw new RuntimeException();
/*     */         }
/* 726 */         if (abstractInsnNode instanceof MethodInsnNode) {
/* 727 */           boolean bool = ((MethodInsnNode)abstractInsnNode).itf;
/* 728 */           if (bool != ((abstractInsnNode.opcode == 185))) {
/* 729 */             throw new RuntimeException();
/*     */           }
/*     */         } 
/*     */       } 
/* 733 */       if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations
/* 734 */         .size() > 0) {
/* 735 */         throw new RuntimeException();
/*     */       }
/* 737 */       if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations
/* 738 */         .size() > 0) {
/* 739 */         throw new RuntimeException();
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
/*     */   public void accept(ClassVisitor paramClassVisitor) {
/* 751 */     String[] arrayOfString = new String[this.exceptions.size()];
/* 752 */     this.exceptions.toArray(arrayOfString);
/* 753 */     MethodVisitor methodVisitor = paramClassVisitor.visitMethod(this.access, this.name, this.desc, this.signature, arrayOfString);
/*     */     
/* 755 */     if (methodVisitor != null) {
/* 756 */       accept(methodVisitor);
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
/*     */   public void accept(MethodVisitor paramMethodVisitor) {
/* 769 */     byte b2 = (this.parameters == null) ? 0 : this.parameters.size(); byte b1;
/* 770 */     for (b1 = 0; b1 < b2; b1++) {
/* 771 */       ParameterNode parameterNode = this.parameters.get(b1);
/* 772 */       paramMethodVisitor.visitParameter(parameterNode.name, parameterNode.access);
/*     */     } 
/*     */     
/* 775 */     if (this.annotationDefault != null) {
/* 776 */       AnnotationVisitor annotationVisitor = paramMethodVisitor.visitAnnotationDefault();
/* 777 */       AnnotationNode.accept(annotationVisitor, null, this.annotationDefault);
/* 778 */       if (annotationVisitor != null) {
/* 779 */         annotationVisitor.visitEnd();
/*     */       }
/*     */     } 
/* 782 */     b2 = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size();
/* 783 */     for (b1 = 0; b1 < b2; b1++) {
/* 784 */       AnnotationNode annotationNode = this.visibleAnnotations.get(b1);
/* 785 */       annotationNode.accept(paramMethodVisitor.visitAnnotation(annotationNode.desc, true));
/*     */     } 
/* 787 */     b2 = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 788 */     for (b1 = 0; b1 < b2; b1++) {
/* 789 */       AnnotationNode annotationNode = this.invisibleAnnotations.get(b1);
/* 790 */       annotationNode.accept(paramMethodVisitor.visitAnnotation(annotationNode.desc, false));
/*     */     } 
/* 792 */     b2 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 793 */     for (b1 = 0; b1 < b2; b1++) {
/* 794 */       TypeAnnotationNode typeAnnotationNode = this.visibleTypeAnnotations.get(b1);
/* 795 */       typeAnnotationNode.accept(paramMethodVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 799 */     b2 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 800 */     for (b1 = 0; b1 < b2; b1++) {
/* 801 */       TypeAnnotationNode typeAnnotationNode = this.invisibleTypeAnnotations.get(b1);
/* 802 */       typeAnnotationNode.accept(paramMethodVisitor.visitTypeAnnotation(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc, false));
/*     */     } 
/*     */     
/* 805 */     b2 = (this.visibleParameterAnnotations == null) ? 0 : this.visibleParameterAnnotations.length;
/*     */     
/* 807 */     for (b1 = 0; b1 < b2; b1++) {
/* 808 */       List<AnnotationNode> list = this.visibleParameterAnnotations[b1];
/* 809 */       if (list != null)
/*     */       {
/*     */         
/* 812 */         for (byte b = 0; b < list.size(); b++) {
/* 813 */           AnnotationNode annotationNode = list.get(b);
/* 814 */           annotationNode.accept(paramMethodVisitor.visitParameterAnnotation(b1, annotationNode.desc, true));
/*     */         }  } 
/*     */     } 
/* 817 */     b2 = (this.invisibleParameterAnnotations == null) ? 0 : this.invisibleParameterAnnotations.length;
/*     */     
/* 819 */     for (b1 = 0; b1 < b2; b1++) {
/* 820 */       List<AnnotationNode> list = this.invisibleParameterAnnotations[b1];
/* 821 */       if (list != null)
/*     */       {
/*     */         
/* 824 */         for (byte b = 0; b < list.size(); b++) {
/* 825 */           AnnotationNode annotationNode = list.get(b);
/* 826 */           annotationNode.accept(paramMethodVisitor.visitParameterAnnotation(b1, annotationNode.desc, false));
/*     */         }  } 
/*     */     } 
/* 829 */     if (this.visited) {
/* 830 */       this.instructions.resetLabels();
/*     */     }
/* 832 */     b2 = (this.attrs == null) ? 0 : this.attrs.size();
/* 833 */     for (b1 = 0; b1 < b2; b1++) {
/* 834 */       paramMethodVisitor.visitAttribute(this.attrs.get(b1));
/*     */     }
/*     */     
/* 837 */     if (this.instructions.size() > 0) {
/* 838 */       paramMethodVisitor.visitCode();
/*     */       
/* 840 */       b2 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size();
/* 841 */       for (b1 = 0; b1 < b2; b1++) {
/* 842 */         ((TryCatchBlockNode)this.tryCatchBlocks.get(b1)).updateIndex(b1);
/* 843 */         ((TryCatchBlockNode)this.tryCatchBlocks.get(b1)).accept(paramMethodVisitor);
/*     */       } 
/*     */       
/* 846 */       this.instructions.accept(paramMethodVisitor);
/*     */       
/* 848 */       b2 = (this.localVariables == null) ? 0 : this.localVariables.size();
/* 849 */       for (b1 = 0; b1 < b2; b1++) {
/* 850 */         ((LocalVariableNode)this.localVariables.get(b1)).accept(paramMethodVisitor);
/*     */       }
/*     */ 
/*     */       
/* 854 */       b2 = (this.visibleLocalVariableAnnotations == null) ? 0 : this.visibleLocalVariableAnnotations.size();
/* 855 */       for (b1 = 0; b1 < b2; b1++) {
/* 856 */         ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(b1)).accept(paramMethodVisitor, true);
/*     */       }
/*     */       
/* 859 */       b2 = (this.invisibleLocalVariableAnnotations == null) ? 0 : this.invisibleLocalVariableAnnotations.size();
/* 860 */       for (b1 = 0; b1 < b2; b1++) {
/* 861 */         ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(b1)).accept(paramMethodVisitor, false);
/*     */       }
/*     */       
/* 864 */       paramMethodVisitor.visitMaxs(this.maxStack, this.maxLocals);
/* 865 */       this.visited = true;
/*     */     } 
/* 867 */     paramMethodVisitor.visitEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/tree/MethodNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */