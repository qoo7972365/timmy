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
/*      */ class MethodWriter
/*      */   extends MethodVisitor
/*      */ {
/*      */   static final int ACC_CONSTRUCTOR = 524288;
/*      */   static final int SAME_FRAME = 0;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
/*      */   static final int RESERVED = 128;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
/*      */   static final int CHOP_FRAME = 248;
/*      */   static final int SAME_FRAME_EXTENDED = 251;
/*      */   static final int APPEND_FRAME = 252;
/*      */   static final int FULL_FRAME = 255;
/*      */   private static final int FRAMES = 0;
/*      */   private static final int MAXS = 1;
/*      */   private static final int NOTHING = 2;
/*      */   final ClassWriter cw;
/*      */   private int access;
/*      */   private final int name;
/*      */   private final int desc;
/*      */   private final String descriptor;
/*      */   String signature;
/*      */   int classReaderOffset;
/*      */   int classReaderLength;
/*      */   int exceptionCount;
/*      */   int[] exceptions;
/*      */   private ByteVector annd;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private AnnotationWriter tanns;
/*      */   private AnnotationWriter itanns;
/*      */   private AnnotationWriter[] panns;
/*      */   private AnnotationWriter[] ipanns;
/*      */   private int synthetics;
/*      */   private Attribute attrs;
/*  260 */   private ByteVector code = new ByteVector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxLocals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentLocals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int frameCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector stackMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int previousFrameOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] previousFrame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] frame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handlerCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler firstHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler lastHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int methodParametersCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector methodParameters;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarTypeCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVarType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lineNumberCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector lineNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastCodeOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationWriter ctanns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationWriter ictanns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute cattrs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean resize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subroutines;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int compute;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label labels;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label previousBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label currentBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int stackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodWriter(ClassWriter paramClassWriter, int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, boolean paramBoolean1, boolean paramBoolean2) {
/*  485 */     super(327680);
/*  486 */     if (paramClassWriter.firstMethod == null) {
/*  487 */       paramClassWriter.firstMethod = this;
/*      */     } else {
/*  489 */       paramClassWriter.lastMethod.mv = this;
/*      */     } 
/*  491 */     paramClassWriter.lastMethod = this;
/*  492 */     this.cw = paramClassWriter;
/*  493 */     this.access = paramInt;
/*  494 */     if ("<init>".equals(paramString1)) {
/*  495 */       this.access |= 0x80000;
/*      */     }
/*  497 */     this.name = paramClassWriter.newUTF8(paramString1);
/*  498 */     this.desc = paramClassWriter.newUTF8(paramString2);
/*  499 */     this.descriptor = paramString2;
/*      */     
/*  501 */     this.signature = paramString3;
/*      */     
/*  503 */     if (paramArrayOfString != null && paramArrayOfString.length > 0) {
/*  504 */       this.exceptionCount = paramArrayOfString.length;
/*  505 */       this.exceptions = new int[this.exceptionCount];
/*  506 */       for (byte b = 0; b < this.exceptionCount; b++) {
/*  507 */         this.exceptions[b] = paramClassWriter.newClass(paramArrayOfString[b]);
/*      */       }
/*      */     } 
/*  510 */     this.compute = paramBoolean2 ? 0 : (paramBoolean1 ? 1 : 2);
/*  511 */     if (paramBoolean1 || paramBoolean2) {
/*      */       
/*  513 */       int i = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
/*  514 */       if ((paramInt & 0x8) != 0) {
/*  515 */         i--;
/*      */       }
/*  517 */       this.maxLocals = i;
/*  518 */       this.currentLocals = i;
/*      */       
/*  520 */       this.labels = new Label();
/*  521 */       this.labels.status |= 0x8;
/*  522 */       visitLabel(this.labels);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String paramString, int paramInt) {
/*  532 */     if (this.methodParameters == null) {
/*  533 */       this.methodParameters = new ByteVector();
/*      */     }
/*  535 */     this.methodParametersCount++;
/*  536 */     this.methodParameters.putShort((paramString == null) ? 0 : this.cw.newUTF8(paramString))
/*  537 */       .putShort(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotationDefault() {
/*  545 */     this.annd = new ByteVector();
/*  546 */     return new AnnotationWriter(this.cw, false, this.annd, null, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/*  555 */     ByteVector byteVector = new ByteVector();
/*      */     
/*  557 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/*  558 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
/*  559 */     if (paramBoolean) {
/*  560 */       annotationWriter.next = this.anns;
/*  561 */       this.anns = annotationWriter;
/*      */     } else {
/*  563 */       annotationWriter.next = this.ianns;
/*  564 */       this.ianns = annotationWriter;
/*      */     } 
/*  566 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/*  575 */     ByteVector byteVector = new ByteVector();
/*      */     
/*  577 */     AnnotationWriter.putTarget(paramInt, paramTypePath, byteVector);
/*      */     
/*  579 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/*  580 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
/*      */     
/*  582 */     if (paramBoolean) {
/*  583 */       annotationWriter.next = this.tanns;
/*  584 */       this.tanns = annotationWriter;
/*      */     } else {
/*  586 */       annotationWriter.next = this.itanns;
/*  587 */       this.itanns = annotationWriter;
/*      */     } 
/*  589 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitParameterAnnotation(int paramInt, String paramString, boolean paramBoolean) {
/*  598 */     ByteVector byteVector = new ByteVector();
/*  599 */     if ("Ljava/lang/Synthetic;".equals(paramString)) {
/*      */ 
/*      */       
/*  602 */       this.synthetics = Math.max(this.synthetics, paramInt + 1);
/*  603 */       return new AnnotationWriter(this.cw, false, byteVector, null, 0);
/*      */     } 
/*      */     
/*  606 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/*  607 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
/*  608 */     if (paramBoolean) {
/*  609 */       if (this.panns == null) {
/*  610 */         this.panns = new AnnotationWriter[(Type.getArgumentTypes(this.descriptor)).length];
/*      */       }
/*  612 */       annotationWriter.next = this.panns[paramInt];
/*  613 */       this.panns[paramInt] = annotationWriter;
/*      */     } else {
/*  615 */       if (this.ipanns == null) {
/*  616 */         this.ipanns = new AnnotationWriter[(Type.getArgumentTypes(this.descriptor)).length];
/*      */       }
/*  618 */       annotationWriter.next = this.ipanns[paramInt];
/*  619 */       this.ipanns[paramInt] = annotationWriter;
/*      */     } 
/*  621 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute paramAttribute) {
/*  626 */     if (paramAttribute.isCodeAttribute()) {
/*  627 */       paramAttribute.next = this.cattrs;
/*  628 */       this.cattrs = paramAttribute;
/*      */     } else {
/*  630 */       paramAttribute.next = this.attrs;
/*  631 */       this.attrs = paramAttribute;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitCode() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/*  642 */     if (this.compute == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  646 */     if (paramInt1 == -1) {
/*  647 */       if (this.previousFrame == null) {
/*  648 */         visitImplicitFirstFrame();
/*      */       }
/*  650 */       this.currentLocals = paramInt2;
/*  651 */       int i = startFrame(this.code.length, paramInt2, paramInt3); byte b;
/*  652 */       for (b = 0; b < paramInt2; b++) {
/*  653 */         if (paramArrayOfObject1[b] instanceof String) {
/*  654 */           this.frame[i++] = 0x1700000 | this.cw
/*  655 */             .addType((String)paramArrayOfObject1[b]);
/*  656 */         } else if (paramArrayOfObject1[b] instanceof Integer) {
/*  657 */           this.frame[i++] = ((Integer)paramArrayOfObject1[b]).intValue();
/*      */         } else {
/*  659 */           this.frame[i++] = 0x1800000 | this.cw
/*  660 */             .addUninitializedType("", ((Label)paramArrayOfObject1[b]).position);
/*      */         } 
/*      */       } 
/*      */       
/*  664 */       for (b = 0; b < paramInt3; b++) {
/*  665 */         if (paramArrayOfObject2[b] instanceof String) {
/*  666 */           this.frame[i++] = 0x1700000 | this.cw
/*  667 */             .addType((String)paramArrayOfObject2[b]);
/*  668 */         } else if (paramArrayOfObject2[b] instanceof Integer) {
/*  669 */           this.frame[i++] = ((Integer)paramArrayOfObject2[b]).intValue();
/*      */         } else {
/*  671 */           this.frame[i++] = 0x1800000 | this.cw
/*  672 */             .addUninitializedType("", ((Label)paramArrayOfObject2[b]).position);
/*      */         } 
/*      */       } 
/*      */       
/*  676 */       endFrame();
/*      */     } else {
/*      */       int i; byte b;
/*  679 */       if (this.stackMap == null) {
/*  680 */         this.stackMap = new ByteVector();
/*  681 */         i = this.code.length;
/*      */       } else {
/*  683 */         i = this.code.length - this.previousFrameOffset - 1;
/*  684 */         if (i < 0) {
/*  685 */           if (paramInt1 == 3) {
/*      */             return;
/*      */           }
/*  688 */           throw new IllegalStateException();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  693 */       switch (paramInt1) {
/*      */         case 0:
/*  695 */           this.currentLocals = paramInt2;
/*  696 */           this.stackMap.putByte(255).putShort(i).putShort(paramInt2);
/*  697 */           for (b = 0; b < paramInt2; b++) {
/*  698 */             writeFrameType(paramArrayOfObject1[b]);
/*      */           }
/*  700 */           this.stackMap.putShort(paramInt3);
/*  701 */           for (b = 0; b < paramInt3; b++) {
/*  702 */             writeFrameType(paramArrayOfObject2[b]);
/*      */           }
/*      */           break;
/*      */         case 1:
/*  706 */           this.currentLocals += paramInt2;
/*  707 */           this.stackMap.putByte(251 + paramInt2).putShort(i);
/*  708 */           for (b = 0; b < paramInt2; b++) {
/*  709 */             writeFrameType(paramArrayOfObject1[b]);
/*      */           }
/*      */           break;
/*      */         case 2:
/*  713 */           this.currentLocals -= paramInt2;
/*  714 */           this.stackMap.putByte(251 - paramInt2).putShort(i);
/*      */           break;
/*      */         case 3:
/*  717 */           if (i < 64) {
/*  718 */             this.stackMap.putByte(i); break;
/*      */           } 
/*  720 */           this.stackMap.putByte(251).putShort(i);
/*      */           break;
/*      */         
/*      */         case 4:
/*  724 */           if (i < 64) {
/*  725 */             this.stackMap.putByte(64 + i);
/*      */           } else {
/*  727 */             this.stackMap.putByte(247)
/*  728 */               .putShort(i);
/*      */           } 
/*  730 */           writeFrameType(paramArrayOfObject2[0]);
/*      */           break;
/*      */       } 
/*      */       
/*  734 */       this.previousFrameOffset = this.code.length;
/*  735 */       this.frameCount++;
/*      */     } 
/*      */     
/*  738 */     this.maxStack = Math.max(this.maxStack, paramInt3);
/*  739 */     this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int paramInt) {
/*  744 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  746 */     this.code.putByte(paramInt);
/*      */ 
/*      */     
/*  749 */     if (this.currentBlock != null) {
/*  750 */       if (this.compute == 0) {
/*  751 */         this.currentBlock.frame.execute(paramInt, 0, null, null);
/*      */       } else {
/*      */         
/*  754 */         int i = this.stackSize + Frame.SIZE[paramInt];
/*  755 */         if (i > this.maxStackSize) {
/*  756 */           this.maxStackSize = i;
/*      */         }
/*  758 */         this.stackSize = i;
/*      */       } 
/*      */       
/*  761 */       if ((paramInt >= 172 && paramInt <= 177) || paramInt == 191)
/*      */       {
/*  763 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int paramInt1, int paramInt2) {
/*  770 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  772 */     if (this.currentBlock != null) {
/*  773 */       if (this.compute == 0) {
/*  774 */         this.currentBlock.frame.execute(paramInt1, paramInt2, null, null);
/*  775 */       } else if (paramInt1 != 188) {
/*      */ 
/*      */         
/*  778 */         int i = this.stackSize + 1;
/*  779 */         if (i > this.maxStackSize) {
/*  780 */           this.maxStackSize = i;
/*      */         }
/*  782 */         this.stackSize = i;
/*      */       } 
/*      */     }
/*      */     
/*  786 */     if (paramInt1 == 17) {
/*  787 */       this.code.put12(paramInt1, paramInt2);
/*      */     } else {
/*  789 */       this.code.put11(paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int paramInt1, int paramInt2) {
/*  795 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  797 */     if (this.currentBlock != null) {
/*  798 */       if (this.compute == 0) {
/*  799 */         this.currentBlock.frame.execute(paramInt1, paramInt2, null, null);
/*      */       
/*      */       }
/*  802 */       else if (paramInt1 == 169) {
/*      */         
/*  804 */         this.currentBlock.status |= 0x100;
/*      */ 
/*      */         
/*  807 */         this.currentBlock.inputStackTop = this.stackSize;
/*  808 */         noSuccessor();
/*      */       } else {
/*  810 */         int i = this.stackSize + Frame.SIZE[paramInt1];
/*  811 */         if (i > this.maxStackSize) {
/*  812 */           this.maxStackSize = i;
/*      */         }
/*  814 */         this.stackSize = i;
/*      */       } 
/*      */     }
/*      */     
/*  818 */     if (this.compute != 2) {
/*      */       int i;
/*      */       
/*  821 */       if (paramInt1 == 22 || paramInt1 == 24 || paramInt1 == 55 || paramInt1 == 57) {
/*      */         
/*  823 */         i = paramInt2 + 2;
/*      */       } else {
/*  825 */         i = paramInt2 + 1;
/*      */       } 
/*  827 */       if (i > this.maxLocals) {
/*  828 */         this.maxLocals = i;
/*      */       }
/*      */     } 
/*      */     
/*  832 */     if (paramInt2 < 4 && paramInt1 != 169) {
/*      */       int i;
/*  834 */       if (paramInt1 < 54) {
/*      */         
/*  836 */         i = 26 + (paramInt1 - 21 << 2) + paramInt2;
/*      */       } else {
/*      */         
/*  839 */         i = 59 + (paramInt1 - 54 << 2) + paramInt2;
/*      */       } 
/*  841 */       this.code.putByte(i);
/*  842 */     } else if (paramInt2 >= 256) {
/*  843 */       this.code.putByte(196).put12(paramInt1, paramInt2);
/*      */     } else {
/*  845 */       this.code.put11(paramInt1, paramInt2);
/*      */     } 
/*  847 */     if (paramInt1 >= 54 && this.compute == 0 && this.handlerCount > 0) {
/*  848 */       visitLabel(new Label());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int paramInt, String paramString) {
/*  854 */     this.lastCodeOffset = this.code.length;
/*  855 */     Item item = this.cw.newClassItem(paramString);
/*      */     
/*  857 */     if (this.currentBlock != null) {
/*  858 */       if (this.compute == 0) {
/*  859 */         this.currentBlock.frame.execute(paramInt, this.code.length, this.cw, item);
/*  860 */       } else if (paramInt == 187) {
/*      */ 
/*      */         
/*  863 */         int i = this.stackSize + 1;
/*  864 */         if (i > this.maxStackSize) {
/*  865 */           this.maxStackSize = i;
/*      */         }
/*  867 */         this.stackSize = i;
/*      */       } 
/*      */     }
/*      */     
/*  871 */     this.code.put12(paramInt, item.index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int paramInt, String paramString1, String paramString2, String paramString3) {
/*  877 */     this.lastCodeOffset = this.code.length;
/*  878 */     Item item = this.cw.newFieldItem(paramString1, paramString2, paramString3);
/*      */     
/*  880 */     if (this.currentBlock != null) {
/*  881 */       if (this.compute == 0) {
/*  882 */         this.currentBlock.frame.execute(paramInt, 0, this.cw, item);
/*      */       } else {
/*      */         int i;
/*      */         
/*  886 */         char c = paramString3.charAt(0);
/*  887 */         switch (paramInt) {
/*      */           case 178:
/*  889 */             i = this.stackSize + ((c == 'D' || c == 'J') ? 2 : 1);
/*      */             break;
/*      */           case 179:
/*  892 */             i = this.stackSize + ((c == 'D' || c == 'J') ? -2 : -1);
/*      */             break;
/*      */           case 180:
/*  895 */             i = this.stackSize + ((c == 'D' || c == 'J') ? 1 : 0);
/*      */             break;
/*      */           
/*      */           default:
/*  899 */             i = this.stackSize + ((c == 'D' || c == 'J') ? -3 : -2);
/*      */             break;
/*      */         } 
/*      */         
/*  903 */         if (i > this.maxStackSize) {
/*  904 */           this.maxStackSize = i;
/*      */         }
/*  906 */         this.stackSize = i;
/*      */       } 
/*      */     }
/*      */     
/*  910 */     this.code.put12(paramInt, item.index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/*  916 */     this.lastCodeOffset = this.code.length;
/*  917 */     Item item = this.cw.newMethodItem(paramString1, paramString2, paramString3, paramBoolean);
/*  918 */     int i = item.intVal;
/*      */     
/*  920 */     if (this.currentBlock != null) {
/*  921 */       if (this.compute == 0) {
/*  922 */         this.currentBlock.frame.execute(paramInt, 0, this.cw, item);
/*      */       } else {
/*      */         int j;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  932 */         if (i == 0) {
/*      */ 
/*      */           
/*  935 */           i = Type.getArgumentsAndReturnSizes(paramString3);
/*      */ 
/*      */           
/*  938 */           item.intVal = i;
/*      */         } 
/*      */         
/*  941 */         if (paramInt == 184) {
/*  942 */           j = this.stackSize - (i >> 2) + (i & 0x3) + 1;
/*      */         } else {
/*  944 */           j = this.stackSize - (i >> 2) + (i & 0x3);
/*      */         } 
/*      */         
/*  947 */         if (j > this.maxStackSize) {
/*  948 */           this.maxStackSize = j;
/*      */         }
/*  950 */         this.stackSize = j;
/*      */       } 
/*      */     }
/*      */     
/*  954 */     if (paramInt == 185) {
/*  955 */       if (i == 0) {
/*  956 */         i = Type.getArgumentsAndReturnSizes(paramString3);
/*  957 */         item.intVal = i;
/*      */       } 
/*  959 */       this.code.put12(185, item.index).put11(i >> 2, 0);
/*      */     } else {
/*  961 */       this.code.put12(paramInt, item.index);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String paramString1, String paramString2, Handle paramHandle, Object... paramVarArgs) {
/*  968 */     this.lastCodeOffset = this.code.length;
/*  969 */     Item item = this.cw.newInvokeDynamicItem(paramString1, paramString2, paramHandle, paramVarArgs);
/*  970 */     int i = item.intVal;
/*      */     
/*  972 */     if (this.currentBlock != null) {
/*  973 */       if (this.compute == 0) {
/*  974 */         this.currentBlock.frame.execute(186, 0, this.cw, item);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  984 */         if (i == 0) {
/*      */ 
/*      */           
/*  987 */           i = Type.getArgumentsAndReturnSizes(paramString2);
/*      */ 
/*      */           
/*  990 */           item.intVal = i;
/*      */         } 
/*  992 */         int j = this.stackSize - (i >> 2) + (i & 0x3) + 1;
/*      */ 
/*      */         
/*  995 */         if (j > this.maxStackSize) {
/*  996 */           this.maxStackSize = j;
/*      */         }
/*  998 */         this.stackSize = j;
/*      */       } 
/*      */     }
/*      */     
/* 1002 */     this.code.put12(186, item.index);
/* 1003 */     this.code.putShort(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 1008 */     this.lastCodeOffset = this.code.length;
/* 1009 */     Label label = null;
/*      */     
/* 1011 */     if (this.currentBlock != null) {
/* 1012 */       if (this.compute == 0) {
/* 1013 */         this.currentBlock.frame.execute(paramInt, 0, null, null);
/*      */         
/* 1015 */         (paramLabel.getFirst()).status |= 0x10;
/*      */         
/* 1017 */         addSuccessor(0, paramLabel);
/* 1018 */         if (paramInt != 167)
/*      */         {
/* 1020 */           label = new Label();
/*      */         }
/*      */       }
/* 1023 */       else if (paramInt == 168) {
/* 1024 */         if ((paramLabel.status & 0x200) == 0) {
/* 1025 */           paramLabel.status |= 0x200;
/* 1026 */           this.subroutines++;
/*      */         } 
/* 1028 */         this.currentBlock.status |= 0x80;
/* 1029 */         addSuccessor(this.stackSize + 1, paramLabel);
/*      */         
/* 1031 */         label = new Label();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1042 */         this.stackSize += Frame.SIZE[paramInt];
/* 1043 */         addSuccessor(this.stackSize, paramLabel);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1048 */     if ((paramLabel.status & 0x2) != 0 && paramLabel.position - this.code.length < -32768) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1057 */       if (paramInt == 167) {
/* 1058 */         this.code.putByte(200);
/* 1059 */       } else if (paramInt == 168) {
/* 1060 */         this.code.putByte(201);
/*      */       }
/*      */       else {
/*      */         
/* 1064 */         if (label != null) {
/* 1065 */           label.status |= 0x10;
/*      */         }
/* 1067 */         this.code.putByte((paramInt <= 166) ? ((paramInt + 1 ^ 0x1) - 1) : (paramInt ^ 0x1));
/*      */         
/* 1069 */         this.code.putShort(8);
/* 1070 */         this.code.putByte(200);
/*      */       } 
/* 1072 */       paramLabel.put(this, this.code, this.code.length - 1, true);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 1080 */       this.code.putByte(paramInt);
/* 1081 */       paramLabel.put(this, this.code, this.code.length - 1, false);
/*      */     } 
/* 1083 */     if (this.currentBlock != null) {
/* 1084 */       if (label != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1089 */         visitLabel(label);
/*      */       }
/* 1091 */       if (paramInt == 167) {
/* 1092 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLabel(Label paramLabel) {
/* 1100 */     this.resize |= paramLabel.resolve(this, this.code.length, this.code.data);
/*      */     
/* 1102 */     if ((paramLabel.status & 0x1) != 0) {
/*      */       return;
/*      */     }
/* 1105 */     if (this.compute == 0) {
/* 1106 */       if (this.currentBlock != null) {
/* 1107 */         if (paramLabel.position == this.currentBlock.position) {
/*      */           
/* 1109 */           this.currentBlock.status |= paramLabel.status & 0x10;
/* 1110 */           paramLabel.frame = this.currentBlock.frame;
/*      */           
/*      */           return;
/*      */         } 
/* 1114 */         addSuccessor(0, paramLabel);
/*      */       } 
/*      */       
/* 1117 */       this.currentBlock = paramLabel;
/* 1118 */       if (paramLabel.frame == null) {
/* 1119 */         paramLabel.frame = new Frame();
/* 1120 */         paramLabel.frame.owner = paramLabel;
/*      */       } 
/*      */       
/* 1123 */       if (this.previousBlock != null) {
/* 1124 */         if (paramLabel.position == this.previousBlock.position) {
/* 1125 */           this.previousBlock.status |= paramLabel.status & 0x10;
/* 1126 */           paramLabel.frame = this.previousBlock.frame;
/* 1127 */           this.currentBlock = this.previousBlock;
/*      */           return;
/*      */         } 
/* 1130 */         this.previousBlock.successor = paramLabel;
/*      */       } 
/* 1132 */       this.previousBlock = paramLabel;
/* 1133 */     } else if (this.compute == 1) {
/* 1134 */       if (this.currentBlock != null) {
/*      */         
/* 1136 */         this.currentBlock.outputStackMax = this.maxStackSize;
/* 1137 */         addSuccessor(this.stackSize, paramLabel);
/*      */       } 
/*      */       
/* 1140 */       this.currentBlock = paramLabel;
/*      */       
/* 1142 */       this.stackSize = 0;
/* 1143 */       this.maxStackSize = 0;
/*      */       
/* 1145 */       if (this.previousBlock != null) {
/* 1146 */         this.previousBlock.successor = paramLabel;
/*      */       }
/* 1148 */       this.previousBlock = paramLabel;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object paramObject) {
/* 1154 */     this.lastCodeOffset = this.code.length;
/* 1155 */     Item item = this.cw.newConstItem(paramObject);
/*      */     
/* 1157 */     if (this.currentBlock != null) {
/* 1158 */       if (this.compute == 0) {
/* 1159 */         this.currentBlock.frame.execute(18, 0, this.cw, item);
/*      */       } else {
/*      */         int j;
/*      */         
/* 1163 */         if (item.type == 5 || item.type == 6) {
/* 1164 */           j = this.stackSize + 2;
/*      */         } else {
/* 1166 */           j = this.stackSize + 1;
/*      */         } 
/*      */         
/* 1169 */         if (j > this.maxStackSize) {
/* 1170 */           this.maxStackSize = j;
/*      */         }
/* 1172 */         this.stackSize = j;
/*      */       } 
/*      */     }
/*      */     
/* 1176 */     int i = item.index;
/* 1177 */     if (item.type == 5 || item.type == 6) {
/* 1178 */       this.code.put12(20, i);
/* 1179 */     } else if (i >= 256) {
/* 1180 */       this.code.put12(19, i);
/*      */     } else {
/* 1182 */       this.code.put11(18, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 1188 */     this.lastCodeOffset = this.code.length;
/* 1189 */     if (this.currentBlock != null && 
/* 1190 */       this.compute == 0) {
/* 1191 */       this.currentBlock.frame.execute(132, paramInt1, null, null);
/*      */     }
/*      */     
/* 1194 */     if (this.compute != 2) {
/*      */       
/* 1196 */       int i = paramInt1 + 1;
/* 1197 */       if (i > this.maxLocals) {
/* 1198 */         this.maxLocals = i;
/*      */       }
/*      */     } 
/*      */     
/* 1202 */     if (paramInt1 > 255 || paramInt2 > 127 || paramInt2 < -128) {
/* 1203 */       this.code.putByte(196).put12(132, paramInt1)
/* 1204 */         .putShort(paramInt2);
/*      */     } else {
/* 1206 */       this.code.putByte(132).put11(paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int paramInt1, int paramInt2, Label paramLabel, Label... paramVarArgs) {
/* 1213 */     this.lastCodeOffset = this.code.length;
/*      */     
/* 1215 */     int i = this.code.length;
/* 1216 */     this.code.putByte(170);
/* 1217 */     this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
/* 1218 */     paramLabel.put(this, this.code, i, true);
/* 1219 */     this.code.putInt(paramInt1).putInt(paramInt2);
/* 1220 */     for (byte b = 0; b < paramVarArgs.length; b++) {
/* 1221 */       paramVarArgs[b].put(this, this.code, i, true);
/*      */     }
/*      */     
/* 1224 */     visitSwitchInsn(paramLabel, paramVarArgs);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label paramLabel, int[] paramArrayOfint, Label[] paramArrayOfLabel) {
/* 1230 */     this.lastCodeOffset = this.code.length;
/*      */     
/* 1232 */     int i = this.code.length;
/* 1233 */     this.code.putByte(171);
/* 1234 */     this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
/* 1235 */     paramLabel.put(this, this.code, i, true);
/* 1236 */     this.code.putInt(paramArrayOfLabel.length);
/* 1237 */     for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/* 1238 */       this.code.putInt(paramArrayOfint[b]);
/* 1239 */       paramArrayOfLabel[b].put(this, this.code, i, true);
/*      */     } 
/*      */     
/* 1242 */     visitSwitchInsn(paramLabel, paramArrayOfLabel);
/*      */   }
/*      */ 
/*      */   
/*      */   private void visitSwitchInsn(Label paramLabel, Label[] paramArrayOfLabel) {
/* 1247 */     if (this.currentBlock != null) {
/* 1248 */       if (this.compute == 0) {
/* 1249 */         this.currentBlock.frame.execute(171, 0, null, null);
/*      */         
/* 1251 */         addSuccessor(0, paramLabel);
/* 1252 */         (paramLabel.getFirst()).status |= 0x10;
/* 1253 */         for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/* 1254 */           addSuccessor(0, paramArrayOfLabel[b]);
/* 1255 */           (paramArrayOfLabel[b].getFirst()).status |= 0x10;
/*      */         } 
/*      */       } else {
/*      */         
/* 1259 */         this.stackSize--;
/*      */         
/* 1261 */         addSuccessor(this.stackSize, paramLabel);
/* 1262 */         for (byte b = 0; b < paramArrayOfLabel.length; b++) {
/* 1263 */           addSuccessor(this.stackSize, paramArrayOfLabel[b]);
/*      */         }
/*      */       } 
/*      */       
/* 1267 */       noSuccessor();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String paramString, int paramInt) {
/* 1273 */     this.lastCodeOffset = this.code.length;
/* 1274 */     Item item = this.cw.newClassItem(paramString);
/*      */     
/* 1276 */     if (this.currentBlock != null) {
/* 1277 */       if (this.compute == 0) {
/* 1278 */         this.currentBlock.frame.execute(197, paramInt, this.cw, item);
/*      */       }
/*      */       else {
/*      */         
/* 1282 */         this.stackSize += 1 - paramInt;
/*      */       } 
/*      */     }
/*      */     
/* 1286 */     this.code.put12(197, item.index).putByte(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitInsnAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 1295 */     ByteVector byteVector = new ByteVector();
/*      */     
/* 1297 */     paramInt = paramInt & 0xFF0000FF | this.lastCodeOffset << 8;
/* 1298 */     AnnotationWriter.putTarget(paramInt, paramTypePath, byteVector);
/*      */     
/* 1300 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/* 1301 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
/*      */     
/* 1303 */     if (paramBoolean) {
/* 1304 */       annotationWriter.next = this.ctanns;
/* 1305 */       this.ctanns = annotationWriter;
/*      */     } else {
/* 1307 */       annotationWriter.next = this.ictanns;
/* 1308 */       this.ictanns = annotationWriter;
/*      */     } 
/* 1310 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label paramLabel1, Label paramLabel2, Label paramLabel3, String paramString) {
/* 1316 */     this.handlerCount++;
/* 1317 */     Handler handler = new Handler();
/* 1318 */     handler.start = paramLabel1;
/* 1319 */     handler.end = paramLabel2;
/* 1320 */     handler.handler = paramLabel3;
/* 1321 */     handler.desc = paramString;
/* 1322 */     handler.type = (paramString != null) ? this.cw.newClass(paramString) : 0;
/* 1323 */     if (this.lastHandler == null) {
/* 1324 */       this.firstHandler = handler;
/*      */     } else {
/* 1326 */       this.lastHandler.next = handler;
/*      */     } 
/* 1328 */     this.lastHandler = handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTryCatchAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 1337 */     ByteVector byteVector = new ByteVector();
/*      */     
/* 1339 */     AnnotationWriter.putTarget(paramInt, paramTypePath, byteVector);
/*      */     
/* 1341 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/* 1342 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
/*      */     
/* 1344 */     if (paramBoolean) {
/* 1345 */       annotationWriter.next = this.ctanns;
/* 1346 */       this.ctanns = annotationWriter;
/*      */     } else {
/* 1348 */       annotationWriter.next = this.ictanns;
/* 1349 */       this.ictanns = annotationWriter;
/*      */     } 
/* 1351 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 1358 */     if (paramString3 != null) {
/* 1359 */       if (this.localVarType == null) {
/* 1360 */         this.localVarType = new ByteVector();
/*      */       }
/* 1362 */       this.localVarTypeCount++;
/* 1363 */       this.localVarType.putShort(paramLabel1.position)
/* 1364 */         .putShort(paramLabel2.position - paramLabel1.position)
/* 1365 */         .putShort(this.cw.newUTF8(paramString1)).putShort(this.cw.newUTF8(paramString3))
/* 1366 */         .putShort(paramInt);
/*      */     } 
/* 1368 */     if (this.localVar == null) {
/* 1369 */       this.localVar = new ByteVector();
/*      */     }
/* 1371 */     this.localVarCount++;
/* 1372 */     this.localVar.putShort(paramLabel1.position)
/* 1373 */       .putShort(paramLabel2.position - paramLabel1.position)
/* 1374 */       .putShort(this.cw.newUTF8(paramString1)).putShort(this.cw.newUTF8(paramString2))
/* 1375 */       .putShort(paramInt);
/* 1376 */     if (this.compute != 2) {
/*      */       
/* 1378 */       char c = paramString2.charAt(0);
/* 1379 */       int i = paramInt + ((c == 'J' || c == 'D') ? 2 : 1);
/* 1380 */       if (i > this.maxLocals) {
/* 1381 */         this.maxLocals = i;
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
/*      */   public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 1393 */     ByteVector byteVector = new ByteVector();
/*      */     
/* 1395 */     byteVector.putByte(paramInt >>> 24).putShort(paramArrayOfLabel1.length); int i;
/* 1396 */     for (i = 0; i < paramArrayOfLabel1.length; i++) {
/* 1397 */       byteVector.putShort((paramArrayOfLabel1[i]).position)
/* 1398 */         .putShort((paramArrayOfLabel2[i]).position - (paramArrayOfLabel1[i]).position)
/* 1399 */         .putShort(paramArrayOfint[i]);
/*      */     }
/* 1401 */     if (paramTypePath == null) {
/* 1402 */       byteVector.putByte(0);
/*      */     } else {
/* 1404 */       i = paramTypePath.b[paramTypePath.offset] * 2 + 1;
/* 1405 */       byteVector.putByteArray(paramTypePath.b, paramTypePath.offset, i);
/*      */     } 
/*      */     
/* 1408 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/* 1409 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
/*      */     
/* 1411 */     if (paramBoolean) {
/* 1412 */       annotationWriter.next = this.ctanns;
/* 1413 */       this.ctanns = annotationWriter;
/*      */     } else {
/* 1415 */       annotationWriter.next = this.ictanns;
/* 1416 */       this.ictanns = annotationWriter;
/*      */     } 
/* 1418 */     return annotationWriter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int paramInt, Label paramLabel) {
/* 1423 */     if (this.lineNumber == null) {
/* 1424 */       this.lineNumber = new ByteVector();
/*      */     }
/* 1426 */     this.lineNumberCount++;
/* 1427 */     this.lineNumber.putShort(paramLabel.position);
/* 1428 */     this.lineNumber.putShort(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 1433 */     if (this.resize)
/*      */     {
/*      */       
/* 1436 */       resizeInstructions();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1441 */     if (this.compute == 0) {
/*      */       
/* 1443 */       Handler handler = this.firstHandler;
/* 1444 */       while (handler != null) {
/* 1445 */         Label label3 = handler.start.getFirst();
/* 1446 */         Label label4 = handler.handler.getFirst();
/* 1447 */         Label label5 = handler.end.getFirst();
/*      */         
/* 1449 */         String str = (handler.desc == null) ? "java/lang/Throwable" : handler.desc;
/*      */         
/* 1451 */         int j = 0x1700000 | this.cw.addType(str);
/*      */         
/* 1453 */         label4.status |= 0x10;
/*      */         
/* 1455 */         while (label3 != label5) {
/*      */           
/* 1457 */           Edge edge = new Edge();
/* 1458 */           edge.info = j;
/* 1459 */           edge.successor = label4;
/*      */           
/* 1461 */           edge.next = label3.successors;
/* 1462 */           label3.successors = edge;
/*      */           
/* 1464 */           label3 = label3.successor;
/*      */         } 
/* 1466 */         handler = handler.next;
/*      */       } 
/*      */ 
/*      */       
/* 1470 */       Frame frame = this.labels.frame;
/* 1471 */       Type[] arrayOfType = Type.getArgumentTypes(this.descriptor);
/* 1472 */       frame.initInputFrame(this.cw, this.access, arrayOfType, this.maxLocals);
/* 1473 */       visitFrame(frame);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1481 */       int i = 0;
/* 1482 */       Label label1 = this.labels;
/* 1483 */       while (label1 != null) {
/*      */         
/* 1485 */         Label label = label1;
/* 1486 */         label1 = label1.next;
/* 1487 */         label.next = null;
/* 1488 */         frame = label.frame;
/*      */         
/* 1490 */         if ((label.status & 0x10) != 0) {
/* 1491 */           label.status |= 0x20;
/*      */         }
/*      */         
/* 1494 */         label.status |= 0x40;
/*      */         
/* 1496 */         int j = frame.inputStack.length + label.outputStackMax;
/* 1497 */         if (j > i) {
/* 1498 */           i = j;
/*      */         }
/*      */         
/* 1501 */         Edge edge = label.successors;
/* 1502 */         while (edge != null) {
/* 1503 */           Label label3 = edge.successor.getFirst();
/* 1504 */           boolean bool = frame.merge(this.cw, label3.frame, edge.info);
/* 1505 */           if (bool && label3.next == null) {
/*      */ 
/*      */             
/* 1508 */             label3.next = label1;
/* 1509 */             label1 = label3;
/*      */           } 
/* 1511 */           edge = edge.next;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1516 */       Label label2 = this.labels;
/* 1517 */       while (label2 != null) {
/* 1518 */         frame = label2.frame;
/* 1519 */         if ((label2.status & 0x20) != 0) {
/* 1520 */           visitFrame(frame);
/*      */         }
/* 1522 */         if ((label2.status & 0x40) == 0) {
/*      */           
/* 1524 */           Label label = label2.successor;
/* 1525 */           int j = label2.position;
/* 1526 */           int k = ((label == null) ? this.code.length : label.position) - 1;
/*      */           
/* 1528 */           if (k >= j) {
/* 1529 */             i = Math.max(i, 1);
/*      */             int m;
/* 1531 */             for (m = j; m < k; m++) {
/* 1532 */               this.code.data[m] = 0;
/*      */             }
/* 1534 */             this.code.data[k] = -65;
/*      */             
/* 1536 */             m = startFrame(j, 0, 1);
/* 1537 */             this.frame[m] = 0x1700000 | this.cw
/* 1538 */               .addType("java/lang/Throwable");
/* 1539 */             endFrame();
/*      */ 
/*      */             
/* 1542 */             this.firstHandler = Handler.remove(this.firstHandler, label2, label);
/*      */           } 
/*      */         } 
/* 1545 */         label2 = label2.successor;
/*      */       } 
/*      */       
/* 1548 */       handler = this.firstHandler;
/* 1549 */       this.handlerCount = 0;
/* 1550 */       while (handler != null) {
/* 1551 */         this.handlerCount++;
/* 1552 */         handler = handler.next;
/*      */       } 
/*      */       
/* 1555 */       this.maxStack = i;
/* 1556 */     } else if (this.compute == 1) {
/*      */       
/* 1558 */       Handler handler = this.firstHandler;
/* 1559 */       while (handler != null) {
/* 1560 */         Label label1 = handler.start;
/* 1561 */         Label label2 = handler.handler;
/* 1562 */         Label label3 = handler.end;
/*      */         
/* 1564 */         while (label1 != label3) {
/*      */           
/* 1566 */           Edge edge = new Edge();
/* 1567 */           edge.info = Integer.MAX_VALUE;
/* 1568 */           edge.successor = label2;
/*      */           
/* 1570 */           if ((label1.status & 0x80) == 0) {
/* 1571 */             edge.next = label1.successors;
/* 1572 */             label1.successors = edge;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1577 */             edge.next = label1.successors.next.next;
/* 1578 */             label1.successors.next.next = edge;
/*      */           } 
/*      */           
/* 1581 */           label1 = label1.successor;
/*      */         } 
/* 1583 */         handler = handler.next;
/*      */       } 
/*      */       
/* 1586 */       if (this.subroutines > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1593 */         byte b = 0;
/* 1594 */         this.labels.visitSubroutine(null, 1L, this.subroutines);
/*      */         
/* 1596 */         Label label1 = this.labels;
/* 1597 */         while (label1 != null) {
/* 1598 */           if ((label1.status & 0x80) != 0) {
/*      */             
/* 1600 */             Label label2 = label1.successors.next.successor;
/*      */             
/* 1602 */             if ((label2.status & 0x400) == 0) {
/*      */               
/* 1604 */               b++;
/* 1605 */               label2.visitSubroutine(null, b / 32L << 32L | 1L << b % 32, this.subroutines);
/*      */             } 
/*      */           } 
/*      */           
/* 1609 */           label1 = label1.successor;
/*      */         } 
/*      */         
/* 1612 */         label1 = this.labels;
/* 1613 */         while (label1 != null) {
/* 1614 */           if ((label1.status & 0x80) != 0) {
/* 1615 */             Label label2 = this.labels;
/* 1616 */             while (label2 != null) {
/* 1617 */               label2.status &= 0xFFFFF7FF;
/* 1618 */               label2 = label2.successor;
/*      */             } 
/*      */             
/* 1621 */             Label label3 = label1.successors.next.successor;
/* 1622 */             label3.visitSubroutine(label1, 0L, this.subroutines);
/*      */           } 
/* 1624 */           label1 = label1.successor;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1638 */       int i = 0;
/* 1639 */       Label label = this.labels;
/* 1640 */       while (label != null) {
/*      */         
/* 1642 */         Label label1 = label;
/* 1643 */         label = label.next;
/*      */         
/* 1645 */         int j = label1.inputStackTop;
/* 1646 */         int k = j + label1.outputStackMax;
/*      */         
/* 1648 */         if (k > i) {
/* 1649 */           i = k;
/*      */         }
/*      */         
/* 1652 */         Edge edge = label1.successors;
/* 1653 */         if ((label1.status & 0x80) != 0)
/*      */         {
/* 1655 */           edge = edge.next;
/*      */         }
/* 1657 */         while (edge != null) {
/* 1658 */           label1 = edge.successor;
/*      */           
/* 1660 */           if ((label1.status & 0x8) == 0) {
/*      */             
/* 1662 */             label1.inputStackTop = (edge.info == Integer.MAX_VALUE) ? 1 : (j + edge.info);
/*      */ 
/*      */             
/* 1665 */             label1.status |= 0x8;
/* 1666 */             label1.next = label;
/* 1667 */             label = label1;
/*      */           } 
/* 1669 */           edge = edge.next;
/*      */         } 
/*      */       } 
/* 1672 */       this.maxStack = Math.max(paramInt1, i);
/*      */     } else {
/* 1674 */       this.maxStack = paramInt1;
/* 1675 */       this.maxLocals = paramInt2;
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
/*      */   public void visitEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addSuccessor(int paramInt, Label paramLabel) {
/* 1697 */     Edge edge = new Edge();
/* 1698 */     edge.info = paramInt;
/* 1699 */     edge.successor = paramLabel;
/*      */     
/* 1701 */     edge.next = this.currentBlock.successors;
/* 1702 */     this.currentBlock.successors = edge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void noSuccessor() {
/* 1710 */     if (this.compute == 0) {
/* 1711 */       Label label = new Label();
/* 1712 */       label.frame = new Frame();
/* 1713 */       label.frame.owner = label;
/* 1714 */       label.resolve(this, this.code.length, this.code.data);
/* 1715 */       this.previousBlock.successor = label;
/* 1716 */       this.previousBlock = label;
/*      */     } else {
/* 1718 */       this.currentBlock.outputStackMax = this.maxStackSize;
/*      */     } 
/* 1720 */     this.currentBlock = null;
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
/*      */   private void visitFrame(Frame paramFrame) {
/* 1735 */     byte b2 = 0;
/* 1736 */     int i = 0;
/* 1737 */     byte b3 = 0;
/* 1738 */     int[] arrayOfInt1 = paramFrame.inputLocals;
/* 1739 */     int[] arrayOfInt2 = paramFrame.inputStack;
/*      */     
/*      */     byte b1;
/* 1742 */     for (b1 = 0; b1 < arrayOfInt1.length; b1++) {
/* 1743 */       int k = arrayOfInt1[b1];
/* 1744 */       if (k == 16777216) {
/* 1745 */         b2++;
/*      */       } else {
/* 1747 */         i += b2 + 1;
/* 1748 */         b2 = 0;
/*      */       } 
/* 1750 */       if (k == 16777220 || k == 16777219) {
/* 1751 */         b1++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1756 */     for (b1 = 0; b1 < arrayOfInt2.length; b1++) {
/* 1757 */       int k = arrayOfInt2[b1];
/* 1758 */       b3++;
/* 1759 */       if (k == 16777220 || k == 16777219) {
/* 1760 */         b1++;
/*      */       }
/*      */     } 
/*      */     
/* 1764 */     int j = startFrame(paramFrame.owner.position, i, b3);
/* 1765 */     for (b1 = 0; i > 0; b1++, i--) {
/* 1766 */       int k = arrayOfInt1[b1];
/* 1767 */       this.frame[j++] = k;
/* 1768 */       if (k == 16777220 || k == 16777219) {
/* 1769 */         b1++;
/*      */       }
/*      */     } 
/* 1772 */     for (b1 = 0; b1 < arrayOfInt2.length; b1++) {
/* 1773 */       int k = arrayOfInt2[b1];
/* 1774 */       this.frame[j++] = k;
/* 1775 */       if (k == 16777220 || k == 16777219) {
/* 1776 */         b1++;
/*      */       }
/*      */     } 
/* 1779 */     endFrame();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void visitImplicitFirstFrame() {
/* 1787 */     int i = startFrame(0, this.descriptor.length() + 1, 0);
/* 1788 */     if ((this.access & 0x8) == 0) {
/* 1789 */       if ((this.access & 0x80000) == 0) {
/* 1790 */         this.frame[i++] = 0x1700000 | this.cw.addType(this.cw.thisName);
/*      */       } else {
/* 1792 */         this.frame[i++] = 6;
/*      */       } 
/*      */     }
/* 1795 */     byte b = 1;
/*      */     while (true) {
/* 1797 */       byte b1 = b;
/* 1798 */       switch (this.descriptor.charAt(b++)) {
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'I':
/*      */         case 'S':
/*      */         case 'Z':
/* 1804 */           this.frame[i++] = 1;
/*      */           continue;
/*      */         case 'F':
/* 1807 */           this.frame[i++] = 2;
/*      */           continue;
/*      */         case 'J':
/* 1810 */           this.frame[i++] = 4;
/*      */           continue;
/*      */         case 'D':
/* 1813 */           this.frame[i++] = 3;
/*      */           continue;
/*      */         case '[':
/* 1816 */           while (this.descriptor.charAt(b) == '[') {
/* 1817 */             b++;
/*      */           }
/* 1819 */           if (this.descriptor.charAt(b) == 'L') {
/* 1820 */             b++;
/* 1821 */             while (this.descriptor.charAt(b) != ';') {
/* 1822 */               b++;
/*      */             }
/*      */           } 
/* 1825 */           this.frame[i++] = 0x1700000 | this.cw
/* 1826 */             .addType(this.descriptor.substring(b1, ++b));
/*      */           continue;
/*      */         case 'L':
/* 1829 */           while (this.descriptor.charAt(b) != ';') {
/* 1830 */             b++;
/*      */           }
/* 1832 */           this.frame[i++] = 0x1700000 | this.cw
/* 1833 */             .addType(this.descriptor.substring(b1 + 1, b++));
/*      */           continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/* 1839 */     this.frame[1] = i - 3;
/* 1840 */     endFrame();
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
/*      */   private int startFrame(int paramInt1, int paramInt2, int paramInt3) {
/* 1855 */     int i = 3 + paramInt2 + paramInt3;
/* 1856 */     if (this.frame == null || this.frame.length < i) {
/* 1857 */       this.frame = new int[i];
/*      */     }
/* 1859 */     this.frame[0] = paramInt1;
/* 1860 */     this.frame[1] = paramInt2;
/* 1861 */     this.frame[2] = paramInt3;
/* 1862 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endFrame() {
/* 1870 */     if (this.previousFrame != null) {
/* 1871 */       if (this.stackMap == null) {
/* 1872 */         this.stackMap = new ByteVector();
/*      */       }
/* 1874 */       writeFrame();
/* 1875 */       this.frameCount++;
/*      */     } 
/* 1877 */     this.previousFrame = this.frame;
/* 1878 */     this.frame = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeFrame() {
/* 1886 */     int n, i = this.frame[1];
/* 1887 */     int j = this.frame[2];
/* 1888 */     if ((this.cw.version & 0xFFFF) < 50) {
/* 1889 */       this.stackMap.putShort(this.frame[0]).putShort(i);
/* 1890 */       writeFrameTypes(3, 3 + i);
/* 1891 */       this.stackMap.putShort(j);
/* 1892 */       writeFrameTypes(3 + i, 3 + i + j);
/*      */       return;
/*      */     } 
/* 1895 */     int k = this.previousFrame[1];
/* 1896 */     char c = 'ÿ';
/* 1897 */     int m = 0;
/*      */     
/* 1899 */     if (this.frameCount == 0) {
/* 1900 */       n = this.frame[0];
/*      */     } else {
/* 1902 */       n = this.frame[0] - this.previousFrame[0] - 1;
/*      */     } 
/* 1904 */     if (j == 0) {
/* 1905 */       m = i - k;
/* 1906 */       switch (m) {
/*      */         case -3:
/*      */         case -2:
/*      */         case -1:
/* 1910 */           c = 'ø';
/* 1911 */           k = i;
/*      */           break;
/*      */         case 0:
/* 1914 */           c = (n < 64) ? Character.MIN_VALUE : 'û';
/*      */           break;
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/* 1919 */           c = 'ü';
/*      */           break;
/*      */       } 
/* 1922 */     } else if (i == k && j == 1) {
/* 1923 */       c = (n < 63) ? '@' : '÷';
/*      */     } 
/*      */     
/* 1926 */     if (c != 'ÿ') {
/*      */       
/* 1928 */       byte b1 = 3;
/* 1929 */       for (byte b2 = 0; b2 < k; b2++) {
/* 1930 */         if (this.frame[b1] != this.previousFrame[b1]) {
/* 1931 */           c = 'ÿ';
/*      */           break;
/*      */         } 
/* 1934 */         b1++;
/*      */       } 
/*      */     } 
/* 1937 */     switch (c) {
/*      */       case '\000':
/* 1939 */         this.stackMap.putByte(n);
/*      */         return;
/*      */       case '@':
/* 1942 */         this.stackMap.putByte(64 + n);
/* 1943 */         writeFrameTypes(3 + i, 4 + i);
/*      */         return;
/*      */       case '÷':
/* 1946 */         this.stackMap.putByte(247).putShort(n);
/*      */         
/* 1948 */         writeFrameTypes(3 + i, 4 + i);
/*      */         return;
/*      */       case 'û':
/* 1951 */         this.stackMap.putByte(251).putShort(n);
/*      */         return;
/*      */       case 'ø':
/* 1954 */         this.stackMap.putByte(251 + m).putShort(n);
/*      */         return;
/*      */       case 'ü':
/* 1957 */         this.stackMap.putByte(251 + m).putShort(n);
/* 1958 */         writeFrameTypes(3 + k, 3 + i);
/*      */         return;
/*      */     } 
/*      */     
/* 1962 */     this.stackMap.putByte(255).putShort(n).putShort(i);
/* 1963 */     writeFrameTypes(3, 3 + i);
/* 1964 */     this.stackMap.putShort(j);
/* 1965 */     writeFrameTypes(3 + i, 3 + i + j);
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
/*      */   private void writeFrameTypes(int paramInt1, int paramInt2) {
/* 1981 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1982 */       int j = this.frame[i];
/* 1983 */       int k = j & 0xF0000000;
/* 1984 */       if (k == 0) {
/* 1985 */         int m = j & 0xFFFFF;
/* 1986 */         switch (j & 0xFF00000) {
/*      */           case 24117248:
/* 1988 */             this.stackMap.putByte(7).putShort(this.cw
/* 1989 */                 .newClass((this.cw.typeTable[m]).strVal1));
/*      */             break;
/*      */           case 25165824:
/* 1992 */             this.stackMap.putByte(8).putShort((this.cw.typeTable[m]).intVal);
/*      */             break;
/*      */           default:
/* 1995 */             this.stackMap.putByte(m); break;
/*      */         } 
/*      */       } else {
/* 1998 */         StringBuilder stringBuilder = new StringBuilder();
/* 1999 */         k >>= 28;
/* 2000 */         while (k-- > 0) {
/* 2001 */           stringBuilder.append('[');
/*      */         }
/* 2003 */         if ((j & 0xFF00000) == 24117248) {
/* 2004 */           stringBuilder.append('L');
/* 2005 */           stringBuilder.append((this.cw.typeTable[j & 0xFFFFF]).strVal1);
/* 2006 */           stringBuilder.append(';');
/*      */         } else {
/* 2008 */           switch (j & 0xF) {
/*      */             case 1:
/* 2010 */               stringBuilder.append('I');
/*      */               break;
/*      */             case 2:
/* 2013 */               stringBuilder.append('F');
/*      */               break;
/*      */             case 3:
/* 2016 */               stringBuilder.append('D');
/*      */               break;
/*      */             case 9:
/* 2019 */               stringBuilder.append('Z');
/*      */               break;
/*      */             case 10:
/* 2022 */               stringBuilder.append('B');
/*      */               break;
/*      */             case 11:
/* 2025 */               stringBuilder.append('C');
/*      */               break;
/*      */             case 12:
/* 2028 */               stringBuilder.append('S');
/*      */               break;
/*      */             default:
/* 2031 */               stringBuilder.append('J'); break;
/*      */           } 
/*      */         } 
/* 2034 */         this.stackMap.putByte(7).putShort(this.cw.newClass(stringBuilder.toString()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeFrameType(Object paramObject) {
/* 2040 */     if (paramObject instanceof String) {
/* 2041 */       this.stackMap.putByte(7).putShort(this.cw.newClass((String)paramObject));
/* 2042 */     } else if (paramObject instanceof Integer) {
/* 2043 */       this.stackMap.putByte(((Integer)paramObject).intValue());
/*      */     } else {
/* 2045 */       this.stackMap.putByte(8).putShort(((Label)paramObject).position);
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
/*      */   final int getSize() {
/* 2059 */     if (this.classReaderOffset != 0) {
/* 2060 */       return 6 + this.classReaderLength;
/*      */     }
/* 2062 */     int i = 8;
/* 2063 */     if (this.code.length > 0) {
/* 2064 */       if (this.code.length > 65536) {
/* 2065 */         throw new RuntimeException("Method code too large!");
/*      */       }
/* 2067 */       this.cw.newUTF8("Code");
/* 2068 */       i += 18 + this.code.length + 8 * this.handlerCount;
/* 2069 */       if (this.localVar != null) {
/* 2070 */         this.cw.newUTF8("LocalVariableTable");
/* 2071 */         i += 8 + this.localVar.length;
/*      */       } 
/* 2073 */       if (this.localVarType != null) {
/* 2074 */         this.cw.newUTF8("LocalVariableTypeTable");
/* 2075 */         i += 8 + this.localVarType.length;
/*      */       } 
/* 2077 */       if (this.lineNumber != null) {
/* 2078 */         this.cw.newUTF8("LineNumberTable");
/* 2079 */         i += 8 + this.lineNumber.length;
/*      */       } 
/* 2081 */       if (this.stackMap != null) {
/* 2082 */         boolean bool = ((this.cw.version & 0xFFFF) >= 50) ? true : false;
/* 2083 */         this.cw.newUTF8(bool ? "StackMapTable" : "StackMap");
/* 2084 */         i += 8 + this.stackMap.length;
/*      */       } 
/* 2086 */       if (this.ctanns != null) {
/* 2087 */         this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 2088 */         i += 8 + this.ctanns.getSize();
/*      */       } 
/* 2090 */       if (this.ictanns != null) {
/* 2091 */         this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 2092 */         i += 8 + this.ictanns.getSize();
/*      */       } 
/* 2094 */       if (this.cattrs != null) {
/* 2095 */         i += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */     } 
/*      */     
/* 2099 */     if (this.exceptionCount > 0) {
/* 2100 */       this.cw.newUTF8("Exceptions");
/* 2101 */       i += 8 + 2 * this.exceptionCount;
/*      */     } 
/* 2103 */     if ((this.access & 0x1000) != 0 && ((
/* 2104 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
/*      */       
/* 2106 */       this.cw.newUTF8("Synthetic");
/* 2107 */       i += 6;
/*      */     } 
/*      */     
/* 2110 */     if ((this.access & 0x20000) != 0) {
/* 2111 */       this.cw.newUTF8("Deprecated");
/* 2112 */       i += 6;
/*      */     } 
/* 2114 */     if (this.signature != null) {
/* 2115 */       this.cw.newUTF8("Signature");
/* 2116 */       this.cw.newUTF8(this.signature);
/* 2117 */       i += 8;
/*      */     } 
/* 2119 */     if (this.methodParameters != null) {
/* 2120 */       this.cw.newUTF8("MethodParameters");
/* 2121 */       i += 7 + this.methodParameters.length;
/*      */     } 
/* 2123 */     if (this.annd != null) {
/* 2124 */       this.cw.newUTF8("AnnotationDefault");
/* 2125 */       i += 6 + this.annd.length;
/*      */     } 
/* 2127 */     if (this.anns != null) {
/* 2128 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 2129 */       i += 8 + this.anns.getSize();
/*      */     } 
/* 2131 */     if (this.ianns != null) {
/* 2132 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 2133 */       i += 8 + this.ianns.getSize();
/*      */     } 
/* 2135 */     if (this.tanns != null) {
/* 2136 */       this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 2137 */       i += 8 + this.tanns.getSize();
/*      */     } 
/* 2139 */     if (this.itanns != null) {
/* 2140 */       this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 2141 */       i += 8 + this.itanns.getSize();
/*      */     } 
/* 2143 */     if (this.panns != null) {
/* 2144 */       this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
/* 2145 */       i += 7 + 2 * (this.panns.length - this.synthetics);
/* 2146 */       for (int j = this.panns.length - 1; j >= this.synthetics; j--) {
/* 2147 */         i += (this.panns[j] == null) ? 0 : this.panns[j].getSize();
/*      */       }
/*      */     } 
/* 2150 */     if (this.ipanns != null) {
/* 2151 */       this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
/* 2152 */       i += 7 + 2 * (this.ipanns.length - this.synthetics);
/* 2153 */       for (int j = this.ipanns.length - 1; j >= this.synthetics; j--) {
/* 2154 */         i += (this.ipanns[j] == null) ? 0 : this.ipanns[j].getSize();
/*      */       }
/*      */     } 
/* 2157 */     if (this.attrs != null) {
/* 2158 */       i += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*      */     }
/* 2160 */     return i;
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
/*      */   final void put(ByteVector paramByteVector) {
/* 2172 */     int i = 0xE0000 | (this.access & 0x40000) / 64;
/*      */ 
/*      */     
/* 2175 */     paramByteVector.putShort(this.access & (i ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.desc);
/* 2176 */     if (this.classReaderOffset != 0) {
/* 2177 */       paramByteVector.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
/*      */       return;
/*      */     } 
/* 2180 */     int j = 0;
/* 2181 */     if (this.code.length > 0) {
/* 2182 */       j++;
/*      */     }
/* 2184 */     if (this.exceptionCount > 0) {
/* 2185 */       j++;
/*      */     }
/* 2187 */     if ((this.access & 0x1000) != 0 && ((
/* 2188 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*      */     {
/* 2190 */       j++;
/*      */     }
/*      */     
/* 2193 */     if ((this.access & 0x20000) != 0) {
/* 2194 */       j++;
/*      */     }
/* 2196 */     if (this.signature != null) {
/* 2197 */       j++;
/*      */     }
/* 2199 */     if (this.methodParameters != null) {
/* 2200 */       j++;
/*      */     }
/* 2202 */     if (this.annd != null) {
/* 2203 */       j++;
/*      */     }
/* 2205 */     if (this.anns != null) {
/* 2206 */       j++;
/*      */     }
/* 2208 */     if (this.ianns != null) {
/* 2209 */       j++;
/*      */     }
/* 2211 */     if (this.tanns != null) {
/* 2212 */       j++;
/*      */     }
/* 2214 */     if (this.itanns != null) {
/* 2215 */       j++;
/*      */     }
/* 2217 */     if (this.panns != null) {
/* 2218 */       j++;
/*      */     }
/* 2220 */     if (this.ipanns != null) {
/* 2221 */       j++;
/*      */     }
/* 2223 */     if (this.attrs != null) {
/* 2224 */       j += this.attrs.getCount();
/*      */     }
/* 2226 */     paramByteVector.putShort(j);
/* 2227 */     if (this.code.length > 0) {
/* 2228 */       int k = 12 + this.code.length + 8 * this.handlerCount;
/* 2229 */       if (this.localVar != null) {
/* 2230 */         k += 8 + this.localVar.length;
/*      */       }
/* 2232 */       if (this.localVarType != null) {
/* 2233 */         k += 8 + this.localVarType.length;
/*      */       }
/* 2235 */       if (this.lineNumber != null) {
/* 2236 */         k += 8 + this.lineNumber.length;
/*      */       }
/* 2238 */       if (this.stackMap != null) {
/* 2239 */         k += 8 + this.stackMap.length;
/*      */       }
/* 2241 */       if (this.ctanns != null) {
/* 2242 */         k += 8 + this.ctanns.getSize();
/*      */       }
/* 2244 */       if (this.ictanns != null) {
/* 2245 */         k += 8 + this.ictanns.getSize();
/*      */       }
/* 2247 */       if (this.cattrs != null) {
/* 2248 */         k += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */       
/* 2251 */       paramByteVector.putShort(this.cw.newUTF8("Code")).putInt(k);
/* 2252 */       paramByteVector.putShort(this.maxStack).putShort(this.maxLocals);
/* 2253 */       paramByteVector.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
/* 2254 */       paramByteVector.putShort(this.handlerCount);
/* 2255 */       if (this.handlerCount > 0) {
/* 2256 */         Handler handler = this.firstHandler;
/* 2257 */         while (handler != null) {
/* 2258 */           paramByteVector.putShort(handler.start.position).putShort(handler.end.position)
/* 2259 */             .putShort(handler.handler.position).putShort(handler.type);
/* 2260 */           handler = handler.next;
/*      */         } 
/*      */       } 
/* 2263 */       j = 0;
/* 2264 */       if (this.localVar != null) {
/* 2265 */         j++;
/*      */       }
/* 2267 */       if (this.localVarType != null) {
/* 2268 */         j++;
/*      */       }
/* 2270 */       if (this.lineNumber != null) {
/* 2271 */         j++;
/*      */       }
/* 2273 */       if (this.stackMap != null) {
/* 2274 */         j++;
/*      */       }
/* 2276 */       if (this.ctanns != null) {
/* 2277 */         j++;
/*      */       }
/* 2279 */       if (this.ictanns != null) {
/* 2280 */         j++;
/*      */       }
/* 2282 */       if (this.cattrs != null) {
/* 2283 */         j += this.cattrs.getCount();
/*      */       }
/* 2285 */       paramByteVector.putShort(j);
/* 2286 */       if (this.localVar != null) {
/* 2287 */         paramByteVector.putShort(this.cw.newUTF8("LocalVariableTable"));
/* 2288 */         paramByteVector.putInt(this.localVar.length + 2).putShort(this.localVarCount);
/* 2289 */         paramByteVector.putByteArray(this.localVar.data, 0, this.localVar.length);
/*      */       } 
/* 2291 */       if (this.localVarType != null) {
/* 2292 */         paramByteVector.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
/* 2293 */         paramByteVector.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
/* 2294 */         paramByteVector.putByteArray(this.localVarType.data, 0, this.localVarType.length);
/*      */       } 
/* 2296 */       if (this.lineNumber != null) {
/* 2297 */         paramByteVector.putShort(this.cw.newUTF8("LineNumberTable"));
/* 2298 */         paramByteVector.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
/* 2299 */         paramByteVector.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
/*      */       } 
/* 2301 */       if (this.stackMap != null) {
/* 2302 */         boolean bool = ((this.cw.version & 0xFFFF) >= 50) ? true : false;
/* 2303 */         paramByteVector.putShort(this.cw.newUTF8(bool ? "StackMapTable" : "StackMap"));
/* 2304 */         paramByteVector.putInt(this.stackMap.length + 2).putShort(this.frameCount);
/* 2305 */         paramByteVector.putByteArray(this.stackMap.data, 0, this.stackMap.length);
/*      */       } 
/* 2307 */       if (this.ctanns != null) {
/* 2308 */         paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 2309 */         this.ctanns.put(paramByteVector);
/*      */       } 
/* 2311 */       if (this.ictanns != null) {
/* 2312 */         paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 2313 */         this.ictanns.put(paramByteVector);
/*      */       } 
/* 2315 */       if (this.cattrs != null) {
/* 2316 */         this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, paramByteVector);
/*      */       }
/*      */     } 
/* 2319 */     if (this.exceptionCount > 0) {
/* 2320 */       paramByteVector.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
/*      */       
/* 2322 */       paramByteVector.putShort(this.exceptionCount);
/* 2323 */       for (byte b = 0; b < this.exceptionCount; b++) {
/* 2324 */         paramByteVector.putShort(this.exceptions[b]);
/*      */       }
/*      */     } 
/* 2327 */     if ((this.access & 0x1000) != 0 && ((
/* 2328 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*      */     {
/* 2330 */       paramByteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*      */     }
/*      */     
/* 2333 */     if ((this.access & 0x20000) != 0) {
/* 2334 */       paramByteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*      */     }
/* 2336 */     if (this.signature != null) {
/* 2337 */       paramByteVector.putShort(this.cw.newUTF8("Signature")).putInt(2)
/* 2338 */         .putShort(this.cw.newUTF8(this.signature));
/*      */     }
/* 2340 */     if (this.methodParameters != null) {
/* 2341 */       paramByteVector.putShort(this.cw.newUTF8("MethodParameters"));
/* 2342 */       paramByteVector.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
/*      */       
/* 2344 */       paramByteVector.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
/*      */     } 
/* 2346 */     if (this.annd != null) {
/* 2347 */       paramByteVector.putShort(this.cw.newUTF8("AnnotationDefault"));
/* 2348 */       paramByteVector.putInt(this.annd.length);
/* 2349 */       paramByteVector.putByteArray(this.annd.data, 0, this.annd.length);
/*      */     } 
/* 2351 */     if (this.anns != null) {
/* 2352 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 2353 */       this.anns.put(paramByteVector);
/*      */     } 
/* 2355 */     if (this.ianns != null) {
/* 2356 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 2357 */       this.ianns.put(paramByteVector);
/*      */     } 
/* 2359 */     if (this.tanns != null) {
/* 2360 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 2361 */       this.tanns.put(paramByteVector);
/*      */     } 
/* 2363 */     if (this.itanns != null) {
/* 2364 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 2365 */       this.itanns.put(paramByteVector);
/*      */     } 
/* 2367 */     if (this.panns != null) {
/* 2368 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
/* 2369 */       AnnotationWriter.put(this.panns, this.synthetics, paramByteVector);
/*      */     } 
/* 2371 */     if (this.ipanns != null) {
/* 2372 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
/* 2373 */       AnnotationWriter.put(this.ipanns, this.synthetics, paramByteVector);
/*      */     } 
/* 2375 */     if (this.attrs != null) {
/* 2376 */       this.attrs.put(this.cw, null, 0, -1, -1, paramByteVector);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resizeInstructions() {
/* 2402 */     byte[] arrayOfByte = this.code.data;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2430 */     int[] arrayOfInt1 = new int[0];
/* 2431 */     int[] arrayOfInt2 = new int[0];
/*      */ 
/*      */ 
/*      */     
/* 2435 */     boolean[] arrayOfBoolean = new boolean[this.code.length];
/*      */ 
/*      */     
/* 2438 */     byte b = 3;
/*      */     do {
/* 2440 */       if (b == 3) {
/* 2441 */         b = 2;
/*      */       }
/* 2443 */       int k = 0;
/* 2444 */       while (k < arrayOfByte.length) {
/* 2445 */         int m, n, i1 = arrayOfByte[k] & 0xFF;
/* 2446 */         int i2 = 0;
/*      */         
/* 2448 */         switch (ClassWriter.TYPE[i1]) {
/*      */           case 0:
/*      */           case 4:
/* 2451 */             k++;
/*      */             break;
/*      */           case 9:
/* 2454 */             if (i1 > 201) {
/*      */ 
/*      */ 
/*      */               
/* 2458 */               i1 = (i1 < 218) ? (i1 - 49) : (i1 - 20);
/* 2459 */               m = k + readUnsignedShort(arrayOfByte, k + 1);
/*      */             } else {
/* 2461 */               m = k + readShort(arrayOfByte, k + 1);
/*      */             } 
/* 2463 */             n = getNewOffset(arrayOfInt1, arrayOfInt2, k, m);
/* 2464 */             if (n < -32768 || n > 32767)
/*      */             {
/* 2466 */               if (!arrayOfBoolean[k]) {
/* 2467 */                 if (i1 == 167 || i1 == 168) {
/*      */ 
/*      */ 
/*      */                   
/* 2471 */                   i2 = 2;
/*      */ 
/*      */                 
/*      */                 }
/*      */                 else {
/*      */ 
/*      */ 
/*      */                   
/* 2479 */                   i2 = 5;
/*      */                 } 
/* 2481 */                 arrayOfBoolean[k] = true;
/*      */               } 
/*      */             }
/* 2484 */             k += 3;
/*      */             break;
/*      */           case 10:
/* 2487 */             k += 5;
/*      */             break;
/*      */           case 14:
/* 2490 */             if (b == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2498 */               n = getNewOffset(arrayOfInt1, arrayOfInt2, 0, k);
/* 2499 */               i2 = -(n & 0x3);
/* 2500 */             } else if (!arrayOfBoolean[k]) {
/*      */ 
/*      */ 
/*      */               
/* 2504 */               i2 = k & 0x3;
/* 2505 */               arrayOfBoolean[k] = true;
/*      */             } 
/*      */             
/* 2508 */             k = k + 4 - (k & 0x3);
/* 2509 */             k += 4 * (readInt(arrayOfByte, k + 8) - readInt(arrayOfByte, k + 4) + 1) + 12;
/*      */             break;
/*      */           case 15:
/* 2512 */             if (b == 1) {
/*      */               
/* 2514 */               n = getNewOffset(arrayOfInt1, arrayOfInt2, 0, k);
/* 2515 */               i2 = -(n & 0x3);
/* 2516 */             } else if (!arrayOfBoolean[k]) {
/*      */               
/* 2518 */               i2 = k & 0x3;
/* 2519 */               arrayOfBoolean[k] = true;
/*      */             } 
/*      */             
/* 2522 */             k = k + 4 - (k & 0x3);
/* 2523 */             k += 8 * readInt(arrayOfByte, k + 4) + 8;
/*      */             break;
/*      */           case 17:
/* 2526 */             i1 = arrayOfByte[k + 1] & 0xFF;
/* 2527 */             if (i1 == 132) {
/* 2528 */               k += 6; break;
/*      */             } 
/* 2530 */             k += 4;
/*      */             break;
/*      */           
/*      */           case 1:
/*      */           case 3:
/*      */           case 11:
/* 2536 */             k += 2;
/*      */             break;
/*      */           case 2:
/*      */           case 5:
/*      */           case 6:
/*      */           case 12:
/*      */           case 13:
/* 2543 */             k += 3;
/*      */             break;
/*      */           case 7:
/*      */           case 8:
/* 2547 */             k += 5;
/*      */             break;
/*      */           
/*      */           default:
/* 2551 */             k += 4;
/*      */             break;
/*      */         } 
/* 2554 */         if (i2 != 0) {
/*      */ 
/*      */           
/* 2557 */           int[] arrayOfInt3 = new int[arrayOfInt1.length + 1];
/* 2558 */           int[] arrayOfInt4 = new int[arrayOfInt2.length + 1];
/* 2559 */           System.arraycopy(arrayOfInt1, 0, arrayOfInt3, 0, arrayOfInt1.length);
/*      */           
/* 2561 */           System.arraycopy(arrayOfInt2, 0, arrayOfInt4, 0, arrayOfInt2.length);
/* 2562 */           arrayOfInt3[arrayOfInt1.length] = k;
/* 2563 */           arrayOfInt4[arrayOfInt2.length] = i2;
/* 2564 */           arrayOfInt1 = arrayOfInt3;
/* 2565 */           arrayOfInt2 = arrayOfInt4;
/* 2566 */           if (i2 > 0) {
/* 2567 */             b = 3;
/*      */           }
/*      */         } 
/*      */       } 
/* 2571 */       if (b >= 3)
/* 2572 */         continue;  b--;
/*      */     }
/* 2574 */     while (b != 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2580 */     ByteVector byteVector = new ByteVector(this.code.length);
/*      */     
/* 2582 */     int i = 0;
/* 2583 */     while (i < this.code.length) {
/* 2584 */       int k, m, n, i1, i2 = arrayOfByte[i] & 0xFF;
/* 2585 */       switch (ClassWriter.TYPE[i2]) {
/*      */         case 0:
/*      */         case 4:
/* 2588 */           byteVector.putByte(i2);
/* 2589 */           i++;
/*      */           continue;
/*      */         case 9:
/* 2592 */           if (i2 > 201) {
/*      */ 
/*      */ 
/*      */             
/* 2596 */             i2 = (i2 < 218) ? (i2 - 49) : (i2 - 20);
/* 2597 */             m = i + readUnsignedShort(arrayOfByte, i + 1);
/*      */           } else {
/* 2599 */             m = i + readShort(arrayOfByte, i + 1);
/*      */           } 
/* 2601 */           i1 = getNewOffset(arrayOfInt1, arrayOfInt2, i, m);
/* 2602 */           if (arrayOfBoolean[i]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2608 */             if (i2 == 167) {
/* 2609 */               byteVector.putByte(200);
/* 2610 */             } else if (i2 == 168) {
/* 2611 */               byteVector.putByte(201);
/*      */             } else {
/* 2613 */               byteVector.putByte((i2 <= 166) ? ((i2 + 1 ^ 0x1) - 1) : (i2 ^ 0x1));
/*      */               
/* 2615 */               byteVector.putShort(8);
/* 2616 */               byteVector.putByte(200);
/*      */               
/* 2618 */               i1 -= 3;
/*      */             } 
/* 2620 */             byteVector.putInt(i1);
/*      */           } else {
/* 2622 */             byteVector.putByte(i2);
/* 2623 */             byteVector.putShort(i1);
/*      */           } 
/* 2625 */           i += 3;
/*      */           continue;
/*      */         case 10:
/* 2628 */           m = i + readInt(arrayOfByte, i + 1);
/* 2629 */           i1 = getNewOffset(arrayOfInt1, arrayOfInt2, i, m);
/* 2630 */           byteVector.putByte(i2);
/* 2631 */           byteVector.putInt(i1);
/* 2632 */           i += 5;
/*      */           continue;
/*      */         
/*      */         case 14:
/* 2636 */           k = i;
/* 2637 */           i = i + 4 - (k & 0x3);
/*      */           
/* 2639 */           byteVector.putByte(170);
/* 2640 */           byteVector.putByteArray(null, 0, (4 - byteVector.length % 4) % 4);
/* 2641 */           m = k + readInt(arrayOfByte, i);
/* 2642 */           i += 4;
/* 2643 */           i1 = getNewOffset(arrayOfInt1, arrayOfInt2, k, m);
/* 2644 */           byteVector.putInt(i1);
/* 2645 */           n = readInt(arrayOfByte, i);
/* 2646 */           i += 4;
/* 2647 */           byteVector.putInt(n);
/* 2648 */           n = readInt(arrayOfByte, i) - n + 1;
/* 2649 */           i += 4;
/* 2650 */           byteVector.putInt(readInt(arrayOfByte, i - 4));
/* 2651 */           for (; n > 0; n--) {
/* 2652 */             m = k + readInt(arrayOfByte, i);
/* 2653 */             i += 4;
/* 2654 */             i1 = getNewOffset(arrayOfInt1, arrayOfInt2, k, m);
/* 2655 */             byteVector.putInt(i1);
/*      */           } 
/*      */           continue;
/*      */         
/*      */         case 15:
/* 2660 */           k = i;
/* 2661 */           i = i + 4 - (k & 0x3);
/*      */           
/* 2663 */           byteVector.putByte(171);
/* 2664 */           byteVector.putByteArray(null, 0, (4 - byteVector.length % 4) % 4);
/* 2665 */           m = k + readInt(arrayOfByte, i);
/* 2666 */           i += 4;
/* 2667 */           i1 = getNewOffset(arrayOfInt1, arrayOfInt2, k, m);
/* 2668 */           byteVector.putInt(i1);
/* 2669 */           n = readInt(arrayOfByte, i);
/* 2670 */           i += 4;
/* 2671 */           byteVector.putInt(n);
/* 2672 */           for (; n > 0; n--) {
/* 2673 */             byteVector.putInt(readInt(arrayOfByte, i));
/* 2674 */             i += 4;
/* 2675 */             m = k + readInt(arrayOfByte, i);
/* 2676 */             i += 4;
/* 2677 */             i1 = getNewOffset(arrayOfInt1, arrayOfInt2, k, m);
/* 2678 */             byteVector.putInt(i1);
/*      */           } 
/*      */           continue;
/*      */         case 17:
/* 2682 */           i2 = arrayOfByte[i + 1] & 0xFF;
/* 2683 */           if (i2 == 132) {
/* 2684 */             byteVector.putByteArray(arrayOfByte, i, 6);
/* 2685 */             i += 6; continue;
/*      */           } 
/* 2687 */           byteVector.putByteArray(arrayOfByte, i, 4);
/* 2688 */           i += 4;
/*      */           continue;
/*      */         
/*      */         case 1:
/*      */         case 3:
/*      */         case 11:
/* 2694 */           byteVector.putByteArray(arrayOfByte, i, 2);
/* 2695 */           i += 2;
/*      */           continue;
/*      */         case 2:
/*      */         case 5:
/*      */         case 6:
/*      */         case 12:
/*      */         case 13:
/* 2702 */           byteVector.putByteArray(arrayOfByte, i, 3);
/* 2703 */           i += 3;
/*      */           continue;
/*      */         case 7:
/*      */         case 8:
/* 2707 */           byteVector.putByteArray(arrayOfByte, i, 5);
/* 2708 */           i += 5;
/*      */           continue;
/*      */       } 
/*      */       
/* 2712 */       byteVector.putByteArray(arrayOfByte, i, 4);
/* 2713 */       i += 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2719 */     if (this.compute == 0) {
/* 2720 */       Label label = this.labels;
/* 2721 */       while (label != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2730 */         i = label.position - 3;
/* 2731 */         if (i >= 0 && arrayOfBoolean[i]) {
/* 2732 */           label.status |= 0x10;
/*      */         }
/* 2734 */         getNewOffset(arrayOfInt1, arrayOfInt2, label);
/* 2735 */         label = label.successor;
/*      */       } 
/*      */       
/* 2738 */       for (byte b1 = 0; b1 < this.cw.typeTable.length; b1++) {
/* 2739 */         Item item = this.cw.typeTable[b1];
/* 2740 */         if (item != null && item.type == 31) {
/* 2741 */           item.intVal = getNewOffset(arrayOfInt1, arrayOfInt2, 0, item.intVal);
/*      */         
/*      */         }
/*      */       }
/*      */     
/*      */     }
/* 2747 */     else if (this.frameCount > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2761 */       this.cw.invalidFrames = true;
/*      */     } 
/*      */     
/* 2764 */     Handler handler = this.firstHandler;
/* 2765 */     while (handler != null) {
/* 2766 */       getNewOffset(arrayOfInt1, arrayOfInt2, handler.start);
/* 2767 */       getNewOffset(arrayOfInt1, arrayOfInt2, handler.end);
/* 2768 */       getNewOffset(arrayOfInt1, arrayOfInt2, handler.handler);
/* 2769 */       handler = handler.next;
/*      */     } 
/*      */     
/*      */     int j;
/* 2773 */     for (j = 0; j < 2; j++) {
/* 2774 */       ByteVector byteVector1 = (j == 0) ? this.localVar : this.localVarType;
/* 2775 */       if (byteVector1 != null) {
/* 2776 */         arrayOfByte = byteVector1.data;
/* 2777 */         i = 0;
/* 2778 */         while (i < byteVector1.length) {
/* 2779 */           int k = readUnsignedShort(arrayOfByte, i);
/* 2780 */           int m = getNewOffset(arrayOfInt1, arrayOfInt2, 0, k);
/* 2781 */           writeShort(arrayOfByte, i, m);
/* 2782 */           k += readUnsignedShort(arrayOfByte, i + 2);
/* 2783 */           m = getNewOffset(arrayOfInt1, arrayOfInt2, 0, k) - m;
/*      */           
/* 2785 */           writeShort(arrayOfByte, i + 2, m);
/* 2786 */           i += 10;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2790 */     if (this.lineNumber != null) {
/* 2791 */       arrayOfByte = this.lineNumber.data;
/* 2792 */       i = 0;
/* 2793 */       while (i < this.lineNumber.length) {
/* 2794 */         writeShort(arrayOfByte, i, 
/*      */ 
/*      */             
/* 2797 */             getNewOffset(arrayOfInt1, arrayOfInt2, 0, 
/* 2798 */               readUnsignedShort(arrayOfByte, i)));
/* 2799 */         i += 4;
/*      */       } 
/*      */     } 
/*      */     
/* 2803 */     Attribute attribute = this.cattrs;
/* 2804 */     while (attribute != null) {
/* 2805 */       Label[] arrayOfLabel = attribute.getLabels();
/* 2806 */       if (arrayOfLabel != null) {
/* 2807 */         for (j = arrayOfLabel.length - 1; j >= 0; j--) {
/* 2808 */           getNewOffset(arrayOfInt1, arrayOfInt2, arrayOfLabel[j]);
/*      */         }
/*      */       }
/* 2811 */       attribute = attribute.next;
/*      */     } 
/*      */ 
/*      */     
/* 2815 */     this.code = byteVector;
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
/*      */   static int readUnsignedShort(byte[] paramArrayOfbyte, int paramInt) {
/* 2828 */     return (paramArrayOfbyte[paramInt] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 1] & 0xFF;
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
/*      */   static short readShort(byte[] paramArrayOfbyte, int paramInt) {
/* 2841 */     return (short)((paramArrayOfbyte[paramInt] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 1] & 0xFF);
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
/*      */   static int readInt(byte[] paramArrayOfbyte, int paramInt) {
/* 2854 */     return (paramArrayOfbyte[paramInt] & 0xFF) << 24 | (paramArrayOfbyte[paramInt + 1] & 0xFF) << 16 | (paramArrayOfbyte[paramInt + 2] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 3] & 0xFF;
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
/*      */   static void writeShort(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 2869 */     paramArrayOfbyte[paramInt1] = (byte)(paramInt2 >>> 8);
/* 2870 */     paramArrayOfbyte[paramInt1 + 1] = (byte)paramInt2;
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
/*      */   static int getNewOffset(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, int paramInt2) {
/* 2902 */     int i = paramInt2 - paramInt1;
/* 2903 */     for (byte b = 0; b < paramArrayOfint1.length; b++) {
/* 2904 */       if (paramInt1 < paramArrayOfint1[b] && paramArrayOfint1[b] <= paramInt2) {
/*      */         
/* 2906 */         i += paramArrayOfint2[b];
/* 2907 */       } else if (paramInt2 < paramArrayOfint1[b] && paramArrayOfint1[b] <= paramInt1) {
/*      */         
/* 2909 */         i -= paramArrayOfint2[b];
/*      */       } 
/*      */     } 
/* 2912 */     return i;
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
/*      */   static void getNewOffset(int[] paramArrayOfint1, int[] paramArrayOfint2, Label paramLabel) {
/* 2937 */     if ((paramLabel.status & 0x4) == 0) {
/* 2938 */       paramLabel.position = getNewOffset(paramArrayOfint1, paramArrayOfint2, 0, paramLabel.position);
/* 2939 */       paramLabel.status |= 0x4;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/MethodWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */