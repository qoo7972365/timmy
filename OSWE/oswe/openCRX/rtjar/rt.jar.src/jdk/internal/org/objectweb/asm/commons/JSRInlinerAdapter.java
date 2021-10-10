/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LookupSwitchInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TableSwitchInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSRInlinerAdapter
/*     */   extends MethodNode
/*     */   implements Opcodes
/*     */ {
/*     */   private static final boolean LOGGING = false;
/* 101 */   private final Map<LabelNode, BitSet> subroutineHeads = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private final BitSet mainSubroutine = new BitSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   final BitSet dualCitizens = new BitSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSRInlinerAdapter(MethodVisitor paramMethodVisitor, int paramInt, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 145 */     this(327680, paramMethodVisitor, paramInt, paramString1, paramString2, paramString3, paramArrayOfString);
/* 146 */     if (getClass() != JSRInlinerAdapter.class) {
/* 147 */       throw new IllegalStateException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JSRInlinerAdapter(int paramInt1, MethodVisitor paramMethodVisitor, int paramInt2, String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/* 178 */     super(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramArrayOfString);
/* 179 */     this.mv = paramMethodVisitor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int paramInt, Label paramLabel) {
/* 188 */     super.visitJumpInsn(paramInt, paramLabel);
/* 189 */     LabelNode labelNode = ((JumpInsnNode)this.instructions.getLast()).label;
/* 190 */     if (paramInt == 168 && !this.subroutineHeads.containsKey(labelNode)) {
/* 191 */       this.subroutineHeads.put(labelNode, new BitSet());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 201 */     if (!this.subroutineHeads.isEmpty()) {
/* 202 */       markSubroutines();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       emitCode();
/*     */     } 
/*     */ 
/*     */     
/* 215 */     if (this.mv != null) {
/* 216 */       accept(this.mv);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void markSubroutines() {
/* 225 */     BitSet bitSet = new BitSet();
/*     */ 
/*     */ 
/*     */     
/* 229 */     markSubroutineWalk(this.mainSubroutine, 0, bitSet);
/*     */ 
/*     */ 
/*     */     
/* 233 */     for (Map.Entry<LabelNode, BitSet> entry : this.subroutineHeads
/* 234 */       .entrySet()) {
/*     */       
/* 236 */       LabelNode labelNode = (LabelNode)entry.getKey();
/* 237 */       BitSet bitSet1 = (BitSet)entry.getValue();
/* 238 */       int i = this.instructions.indexOf(labelNode);
/* 239 */       markSubroutineWalk(bitSet1, i, bitSet);
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
/*     */   private void markSubroutineWalk(BitSet paramBitSet1, int paramInt, BitSet paramBitSet2) {
/* 266 */     markSubroutineWalkDFS(paramBitSet1, paramInt, paramBitSet2);
/*     */ 
/*     */     
/* 269 */     boolean bool = true;
/* 270 */     while (bool) {
/* 271 */       bool = false;
/* 272 */       Iterator<TryCatchBlockNode> iterator = this.tryCatchBlocks.iterator();
/* 273 */       while (iterator.hasNext()) {
/* 274 */         TryCatchBlockNode tryCatchBlockNode = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 282 */         int i = this.instructions.indexOf(tryCatchBlockNode.handler);
/* 283 */         if (paramBitSet1.get(i)) {
/*     */           continue;
/*     */         }
/*     */         
/* 287 */         int j = this.instructions.indexOf(tryCatchBlockNode.start);
/* 288 */         int k = this.instructions.indexOf(tryCatchBlockNode.end);
/* 289 */         int m = paramBitSet1.nextSetBit(j);
/* 290 */         if (m != -1 && m < k) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 296 */           markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/* 297 */           bool = true;
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void markSubroutineWalkDFS(BitSet paramBitSet1, int paramInt, BitSet paramBitSet2) {
/*     */     do {
/* 319 */       AbstractInsnNode abstractInsnNode = this.instructions.get(paramInt);
/*     */ 
/*     */       
/* 322 */       if (paramBitSet1.get(paramInt)) {
/*     */         return;
/*     */       }
/* 325 */       paramBitSet1.set(paramInt);
/*     */ 
/*     */       
/* 328 */       if (paramBitSet2.get(paramInt)) {
/* 329 */         this.dualCitizens.set(paramInt);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 334 */       paramBitSet2.set(paramInt);
/*     */       
/* 336 */       if (abstractInsnNode.getType() == 7 && abstractInsnNode
/* 337 */         .getOpcode() != 168) {
/*     */ 
/*     */         
/* 340 */         JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;
/* 341 */         int i = this.instructions.indexOf(jumpInsnNode.label);
/* 342 */         markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/*     */       } 
/* 344 */       if (abstractInsnNode.getType() == 11) {
/* 345 */         TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode)abstractInsnNode;
/* 346 */         int i = this.instructions.indexOf(tableSwitchInsnNode.dflt);
/* 347 */         markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/* 348 */         for (int j = tableSwitchInsnNode.labels.size() - 1; j >= 0; j--) {
/* 349 */           LabelNode labelNode = tableSwitchInsnNode.labels.get(j);
/* 350 */           i = this.instructions.indexOf(labelNode);
/* 351 */           markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/*     */         } 
/*     */       } 
/* 354 */       if (abstractInsnNode.getType() == 12) {
/* 355 */         LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode)abstractInsnNode;
/* 356 */         int i = this.instructions.indexOf(lookupSwitchInsnNode.dflt);
/* 357 */         markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/* 358 */         for (int j = lookupSwitchInsnNode.labels.size() - 1; j >= 0; j--) {
/* 359 */           LabelNode labelNode = lookupSwitchInsnNode.labels.get(j);
/* 360 */           i = this.instructions.indexOf(labelNode);
/* 361 */           markSubroutineWalkDFS(paramBitSet1, i, paramBitSet2);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 367 */       switch (this.instructions.get(paramInt).getOpcode()) {
/*     */         case 167:
/*     */         case 169:
/*     */         case 170:
/*     */         case 171:
/*     */         case 172:
/*     */         case 173:
/*     */         case 174:
/*     */         case 175:
/*     */         case 176:
/*     */         case 177:
/*     */         case 191:
/*     */           return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 388 */       ++paramInt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 397 */     while (paramInt < this.instructions.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void emitCode() {
/* 408 */     LinkedList<Instantiation> linkedList = new LinkedList();
/*     */ 
/*     */     
/* 411 */     linkedList.add(new Instantiation(null, this.mainSubroutine));
/*     */ 
/*     */ 
/*     */     
/* 415 */     InsnList insnList = new InsnList();
/* 416 */     ArrayList<TryCatchBlockNode> arrayList = new ArrayList();
/* 417 */     ArrayList<LocalVariableNode> arrayList1 = new ArrayList();
/* 418 */     while (!linkedList.isEmpty()) {
/* 419 */       Instantiation instantiation = linkedList.removeFirst();
/* 420 */       emitSubroutine(instantiation, linkedList, insnList, arrayList, arrayList1);
/*     */     } 
/*     */     
/* 423 */     this.instructions = insnList;
/* 424 */     this.tryCatchBlocks = arrayList;
/* 425 */     this.localVariables = arrayList1;
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
/*     */   private void emitSubroutine(Instantiation paramInstantiation, List<Instantiation> paramList, InsnList paramInsnList, List<TryCatchBlockNode> paramList1, List<LocalVariableNode> paramList2) {
/* 449 */     LabelNode labelNode = null;
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/* 458 */     for (b = 0, i = this.instructions.size(); b < i; b++) {
/* 459 */       AbstractInsnNode abstractInsnNode = this.instructions.get(b);
/* 460 */       Instantiation instantiation = paramInstantiation.findOwner(b);
/*     */ 
/*     */       
/* 463 */       if (abstractInsnNode.getType() == 8) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 468 */         LabelNode labelNode1 = (LabelNode)abstractInsnNode;
/* 469 */         LabelNode labelNode2 = paramInstantiation.rangeLabel(labelNode1);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         if (labelNode2 != labelNode) {
/* 475 */           paramInsnList.add(labelNode2);
/* 476 */           labelNode = labelNode2;
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 486 */       else if (instantiation == paramInstantiation) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 494 */         if (abstractInsnNode.getOpcode() == 169) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 503 */           LabelNode labelNode1 = null;
/* 504 */           for (Instantiation instantiation1 = paramInstantiation; instantiation1 != null; instantiation1 = instantiation1.previous) {
/* 505 */             if (instantiation1.subroutine.get(b)) {
/* 506 */               labelNode1 = instantiation1.returnLabel;
/*     */             }
/*     */           } 
/* 509 */           if (labelNode1 == null)
/*     */           {
/*     */ 
/*     */             
/* 513 */             throw new RuntimeException("Instruction #" + b + " is a RET not owned by any subroutine");
/*     */           }
/*     */           
/* 516 */           paramInsnList.add(new JumpInsnNode(167, labelNode1));
/* 517 */         } else if (abstractInsnNode.getOpcode() == 168) {
/* 518 */           LabelNode labelNode1 = ((JumpInsnNode)abstractInsnNode).label;
/* 519 */           BitSet bitSet = this.subroutineHeads.get(labelNode1);
/* 520 */           Instantiation instantiation1 = new Instantiation(paramInstantiation, bitSet);
/* 521 */           LabelNode labelNode2 = instantiation1.gotoLabel(labelNode1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 532 */           paramInsnList.add(new InsnNode(1));
/* 533 */           paramInsnList.add(new JumpInsnNode(167, labelNode2));
/* 534 */           paramInsnList.add(instantiation1.returnLabel);
/*     */ 
/*     */ 
/*     */           
/* 538 */           paramList.add(instantiation1);
/*     */         } else {
/* 540 */           paramInsnList.add(abstractInsnNode.clone(paramInstantiation));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 545 */     Iterator<TryCatchBlockNode> iterator = this.tryCatchBlocks.iterator();
/* 546 */     while (iterator.hasNext()) {
/* 547 */       TryCatchBlockNode tryCatchBlockNode = iterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 555 */       LabelNode labelNode1 = paramInstantiation.rangeLabel(tryCatchBlockNode.start);
/* 556 */       LabelNode labelNode2 = paramInstantiation.rangeLabel(tryCatchBlockNode.end);
/*     */ 
/*     */       
/* 559 */       if (labelNode1 == labelNode2) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 566 */       LabelNode labelNode3 = paramInstantiation.gotoLabel(tryCatchBlockNode.handler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 574 */       if (labelNode1 == null || labelNode2 == null || labelNode3 == null) {
/* 575 */         throw new RuntimeException("Internal error!");
/*     */       }
/*     */       
/* 578 */       paramList1.add(new TryCatchBlockNode(labelNode1, labelNode2, labelNode3, tryCatchBlockNode.type));
/*     */     } 
/*     */ 
/*     */     
/* 582 */     iterator = (Iterator)this.localVariables.iterator();
/* 583 */     while (iterator.hasNext()) {
/* 584 */       LocalVariableNode localVariableNode = (LocalVariableNode)iterator.next();
/*     */ 
/*     */ 
/*     */       
/* 588 */       LabelNode labelNode1 = paramInstantiation.rangeLabel(localVariableNode.start);
/* 589 */       LabelNode labelNode2 = paramInstantiation.rangeLabel(localVariableNode.end);
/* 590 */       if (labelNode1 == labelNode2) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 596 */       paramList2.add(new LocalVariableNode(localVariableNode.name, localVariableNode.desc, localVariableNode.signature, labelNode1, labelNode2, localVariableNode.index));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void log(String paramString) {
/* 602 */     System.err.println(paramString);
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
/*     */   private class Instantiation
/*     */     extends AbstractMap<LabelNode, LabelNode>
/*     */   {
/*     */     final Instantiation previous;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final BitSet subroutine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 640 */     public final Map<LabelNode, LabelNode> rangeTable = new HashMap<>();
/*     */ 
/*     */     
/*     */     public final LabelNode returnLabel;
/*     */ 
/*     */ 
/*     */     
/*     */     Instantiation(Instantiation param1Instantiation, BitSet param1BitSet) {
/* 648 */       this.previous = param1Instantiation;
/* 649 */       this.subroutine = param1BitSet; Instantiation instantiation;
/* 650 */       for (instantiation = param1Instantiation; instantiation != null; instantiation = instantiation.previous) {
/* 651 */         if (instantiation.subroutine == param1BitSet) {
/* 652 */           throw new RuntimeException("Recursive invocation of " + param1BitSet);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 658 */       if (param1Instantiation != null) {
/* 659 */         this.returnLabel = new LabelNode();
/*     */       } else {
/* 661 */         this.returnLabel = null;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 670 */       instantiation = null; byte b; int i;
/* 671 */       for (b = 0, i = JSRInlinerAdapter.this.instructions.size(); b < i; b++) {
/* 672 */         AbstractInsnNode abstractInsnNode = JSRInlinerAdapter.this.instructions.get(b);
/*     */         
/* 674 */         if (abstractInsnNode.getType() == 8) {
/* 675 */           LabelNode labelNode1, labelNode2 = (LabelNode)abstractInsnNode;
/*     */           
/* 677 */           if (instantiation == null)
/*     */           {
/*     */             
/* 680 */             labelNode1 = new LabelNode();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 686 */           this.rangeTable.put(labelNode2, labelNode1);
/* 687 */         } else if (findOwner(b) == this) {
/*     */ 
/*     */ 
/*     */           
/* 691 */           instantiation = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Instantiation findOwner(int param1Int) {
/* 720 */       if (!this.subroutine.get(param1Int)) {
/* 721 */         return null;
/*     */       }
/* 723 */       if (!JSRInlinerAdapter.this.dualCitizens.get(param1Int)) {
/* 724 */         return this;
/*     */       }
/* 726 */       Instantiation instantiation1 = this;
/* 727 */       for (Instantiation instantiation2 = this.previous; instantiation2 != null; instantiation2 = instantiation2.previous) {
/* 728 */         if (instantiation2.subroutine.get(param1Int)) {
/* 729 */           instantiation1 = instantiation2;
/*     */         }
/*     */       } 
/* 732 */       return instantiation1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LabelNode gotoLabel(LabelNode param1LabelNode) {
/* 749 */       Instantiation instantiation = findOwner(JSRInlinerAdapter.this.instructions.indexOf(param1LabelNode));
/* 750 */       return instantiation.rangeTable.get(param1LabelNode);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LabelNode rangeLabel(LabelNode param1LabelNode) {
/* 766 */       return this.rangeTable.get(param1LabelNode);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<LabelNode, LabelNode>> entrySet() {
/* 773 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public LabelNode get(Object param1Object) {
/* 778 */       return gotoLabel((LabelNode)param1Object);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/JSRInlinerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */