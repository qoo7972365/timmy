/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.bcel.internal.Constants;
/*     */ import com.sun.org.apache.bcel.internal.classfile.Utility;
/*     */ import com.sun.org.apache.bcel.internal.generic.AllocationInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.ArrayInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.BranchHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.BranchInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
/*     */ import com.sun.org.apache.bcel.internal.generic.CPInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.CodeExceptionGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.ConstantPushInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.EmptyVisitor;
/*     */ import com.sun.org.apache.bcel.internal.generic.FieldInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.IINC;
/*     */ import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
/*     */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
/*     */ import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
/*     */ import com.sun.org.apache.bcel.internal.generic.InvokeInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.LDC;
/*     */ import com.sun.org.apache.bcel.internal.generic.LDC2_W;
/*     */ import com.sun.org.apache.bcel.internal.generic.LocalVariableInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.MULTIANEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.MethodGen;
/*     */ import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
/*     */ import com.sun.org.apache.bcel.internal.generic.ObjectType;
/*     */ import com.sun.org.apache.bcel.internal.generic.RET;
/*     */ import com.sun.org.apache.bcel.internal.generic.ReturnInstruction;
/*     */ import com.sun.org.apache.bcel.internal.generic.Select;
/*     */ import com.sun.org.apache.bcel.internal.generic.Type;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BCELFactory
/*     */   extends EmptyVisitor
/*     */ {
/*     */   private MethodGen _mg;
/*     */   private PrintWriter _out;
/*     */   private ConstantPoolGen _cp;
/*     */   private HashMap branch_map;
/*     */   private ArrayList branches;
/*     */   
/*     */   public void start() {
/*     */     if (!this._mg.isAbstract() && !this._mg.isNative()) {
/*     */       InstructionHandle ih = this._mg.getInstructionList().getStart();
/*     */       for (; ih != null; ih = ih.getNext()) {
/*     */         Instruction i = ih.getInstruction();
/*     */         if (i instanceof BranchInstruction)
/*     */           this.branch_map.put(i, ih); 
/*     */         if (ih.hasTargeters()) {
/*     */           if (i instanceof BranchInstruction) {
/*     */             this._out.println("    InstructionHandle ih_" + ih.getPosition() + ";");
/*     */           } else {
/*     */             this._out.print("    InstructionHandle ih_" + ih.getPosition() + " = ");
/*     */           } 
/*     */         } else {
/*     */           this._out.print("    ");
/*     */         } 
/*     */         if (!visitInstruction(i))
/*     */           i.accept(this); 
/*     */       } 
/*     */       updateBranchTargets();
/*     */       updateExceptionHandlers();
/*     */     } 
/*     */   }
/*     */   
/*     */   BCELFactory(MethodGen mg, PrintWriter out) {
/*  85 */     this.branch_map = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     this.branches = new ArrayList(); this._mg = mg; this._cp = mg.getConstantPool(); this._out = out;
/*     */   }
/*     */   private boolean visitInstruction(Instruction i) { short opcode = i.getOpcode(); if (InstructionConstants.INSTRUCTIONS[opcode] != null && !(i instanceof ConstantPushInstruction) && !(i instanceof ReturnInstruction)) { this._out.println("il.append(InstructionConstants." + i.getName().toUpperCase() + ");"); return true; }  return false; }
/* 263 */   public void visitLocalVariableInstruction(LocalVariableInstruction i) { short opcode = i.getOpcode(); Type type = i.getType(this._cp); if (opcode == 132) { this._out.println("il.append(new IINC(" + i.getIndex() + ", " + ((IINC)i).getIncrement() + "));"); } else { String kind = (opcode < 54) ? "Load" : "Store"; this._out.println("il.append(_factory.create" + kind + "(" + BCELifier.printType(type) + ", " + i.getIndex() + "));"); }  } public void visitArrayInstruction(ArrayInstruction i) { short opcode = i.getOpcode(); Type type = i.getType(this._cp); String kind = (opcode < 79) ? "Load" : "Store"; this._out.println("il.append(_factory.createArray" + kind + "(" + BCELifier.printType(type) + "));"); } public void visitFieldInstruction(FieldInstruction i) { short opcode = i.getOpcode(); String class_name = i.getClassName(this._cp); String field_name = i.getFieldName(this._cp); Type type = i.getFieldType(this._cp); this._out.println("il.append(_factory.createFieldAccess(\"" + class_name + "\", \"" + field_name + "\", " + BCELifier.printType(type) + ", Constants." + Constants.OPCODE_NAMES[opcode].toUpperCase() + "));"); } public void visitInvokeInstruction(InvokeInstruction i) { short opcode = i.getOpcode(); String class_name = i.getClassName(this._cp); String method_name = i.getMethodName(this._cp); Type type = i.getReturnType(this._cp); Type[] arg_types = i.getArgumentTypes(this._cp); this._out.println("il.append(_factory.createInvoke(\"" + class_name + "\", \"" + method_name + "\", " + BCELifier.printType(type) + ", " + BCELifier.printArgumentTypes(arg_types) + ", Constants." + Constants.OPCODE_NAMES[opcode].toUpperCase() + "));"); } public void visitAllocationInstruction(AllocationInstruction i) { Type type; if (i instanceof CPInstruction) { type = ((CPInstruction)i).getType(this._cp); } else { type = ((NEWARRAY)i).getType(); }  short opcode = ((Instruction)i).getOpcode(); int dim = 1; switch (opcode) { case 187: this._out.println("il.append(_factory.createNew(\"" + ((ObjectType)type).getClassName() + "\"));"); return;case 197: dim = ((MULTIANEWARRAY)i).getDimensions();case 188: case 189: this._out.println("il.append(_factory.createNewArray(" + BCELifier.printType(type) + ", (short) " + dim + "));"); return; }  throw new RuntimeException("Oops: " + opcode); } private void createConstant(Object value) { String embed = value.toString(); if (value instanceof String) { embed = '"' + Utility.convertString(value.toString()) + '"'; } else if (value instanceof Character) { embed = "(char)0x" + Integer.toHexString(((Character)value).charValue()); }  this._out.println("il.append(new PUSH(_cp, " + embed + "));"); } public void visitLDC(LDC i) { createConstant(i.getValue(this._cp)); } public void visitLDC2_W(LDC2_W i) { createConstant(i.getValue(this._cp)); } public void visitConstantPushInstruction(ConstantPushInstruction i) { createConstant(i.getValue()); } public void visitINSTANCEOF(INSTANCEOF i) { Type type = i.getType(this._cp); this._out.println("il.append(new INSTANCEOF(_cp.addClass(" + BCELifier.printType(type) + ")));"); } public void visitCHECKCAST(CHECKCAST i) { Type type = i.getType(this._cp); this._out.println("il.append(_factory.createCheckCast(" + BCELifier.printType(type) + "));"); } public void visitReturnInstruction(ReturnInstruction i) { Type type = i.getType(this._cp); this._out.println("il.append(_factory.createReturn(" + BCELifier.printType(type) + "));"); } public void visitBranchInstruction(BranchInstruction bi) { BranchHandle bh = (BranchHandle)this.branch_map.get(bi);
/* 264 */     int pos = bh.getPosition();
/* 265 */     String name = bi.getName() + "_" + pos;
/*     */     
/* 267 */     if (bi instanceof Select) {
/* 268 */       Select s = (Select)bi;
/* 269 */       this.branches.add(bi);
/*     */       
/* 271 */       StringBuffer args = new StringBuffer("new int[] { ");
/* 272 */       int[] matchs = s.getMatchs();
/*     */       int i;
/* 274 */       for (i = 0; i < matchs.length; i++) {
/* 275 */         args.append(matchs[i]);
/*     */         
/* 277 */         if (i < matchs.length - 1) {
/* 278 */           args.append(", ");
/*     */         }
/*     */       } 
/* 281 */       args.append(" }");
/*     */       
/* 283 */       this._out.print("    Select " + name + " = new " + bi
/* 284 */           .getName().toUpperCase() + "(" + args + ", new InstructionHandle[] { ");
/*     */ 
/*     */       
/* 287 */       for (i = 0; i < matchs.length; i++) {
/* 288 */         this._out.print("null");
/*     */         
/* 290 */         if (i < matchs.length - 1) {
/* 291 */           this._out.print(", ");
/*     */         }
/*     */       } 
/* 294 */       this._out.println(");");
/*     */     } else {
/* 296 */       String target; int t_pos = bh.getTarget().getPosition();
/*     */ 
/*     */       
/* 299 */       if (pos > t_pos) {
/* 300 */         target = "ih_" + t_pos;
/*     */       } else {
/* 302 */         this.branches.add(bi);
/* 303 */         target = "null";
/*     */       } 
/*     */       
/* 306 */       this._out.println("    BranchInstruction " + name + " = _factory.createBranchInstruction(Constants." + bi
/*     */           
/* 308 */           .getName().toUpperCase() + ", " + target + ");");
/*     */     } 
/*     */ 
/*     */     
/* 312 */     if (bh.hasTargeters()) {
/* 313 */       this._out.println("    ih_" + pos + " = il.append(" + name + ");");
/*     */     } else {
/* 315 */       this._out.println("    il.append(" + name + ");");
/*     */     }  }
/*     */   
/*     */   public void visitRET(RET i) {
/* 319 */     this._out.println("il.append(new RET(" + i.getIndex() + ")));");
/*     */   }
/*     */   
/*     */   private void updateBranchTargets() {
/* 323 */     for (Iterator<BranchInstruction> i = this.branches.iterator(); i.hasNext(); ) {
/* 324 */       BranchInstruction bi = i.next();
/* 325 */       BranchHandle bh = (BranchHandle)this.branch_map.get(bi);
/* 326 */       int pos = bh.getPosition();
/* 327 */       String name = bi.getName() + "_" + pos;
/* 328 */       int t_pos = bh.getTarget().getPosition();
/*     */       
/* 330 */       this._out.println("    " + name + ".setTarget(ih_" + t_pos + ");");
/*     */       
/* 332 */       if (bi instanceof Select) {
/* 333 */         InstructionHandle[] ihs = ((Select)bi).getTargets();
/*     */         
/* 335 */         for (int j = 0; j < ihs.length; j++) {
/* 336 */           t_pos = ihs[j].getPosition();
/*     */           
/* 338 */           this._out.println("    " + name + ".setTarget(" + j + ", ih_" + t_pos + ");");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateExceptionHandlers() {
/* 346 */     CodeExceptionGen[] handlers = this._mg.getExceptionHandlers();
/*     */     
/* 348 */     for (int i = 0; i < handlers.length; i++) {
/* 349 */       CodeExceptionGen h = handlers[i];
/*     */       
/* 351 */       String type = (h.getCatchType() == null) ? "null" : BCELifier.printType(h.getCatchType());
/*     */       
/* 353 */       this._out.println("    method.addExceptionHandler(ih_" + h
/* 354 */           .getStartPC().getPosition() + ", ih_" + h
/* 355 */           .getEndPC().getPosition() + ", ih_" + h
/* 356 */           .getHandlerPC().getPosition() + ", " + type + ");");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/BCELFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */