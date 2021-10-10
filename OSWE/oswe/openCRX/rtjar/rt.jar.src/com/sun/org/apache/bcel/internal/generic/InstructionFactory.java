/*     */ package com.sun.org.apache.bcel.internal.generic;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InstructionFactory
/*     */   implements InstructionConstants, Serializable
/*     */ {
/*     */   protected ClassGen cg;
/*     */   protected ConstantPoolGen cp;
/*     */   
/*     */   public InstructionFactory(ClassGen cg, ConstantPoolGen cp) {
/*  78 */     this.cg = cg;
/*  79 */     this.cp = cp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InstructionFactory(ClassGen cg) {
/*  85 */     this(cg, cg.getConstantPool());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InstructionFactory(ConstantPoolGen cp) {
/*  91 */     this((ClassGen)null, cp);
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
/*     */   public InvokeInstruction createInvoke(String class_name, String name, Type ret_type, Type[] arg_types, short kind) {
/* 107 */     int index, nargs = 0;
/* 108 */     String signature = Type.getMethodSignature(ret_type, arg_types);
/*     */     
/* 110 */     for (int i = 0; i < arg_types.length; i++) {
/* 111 */       nargs += arg_types[i].getSize();
/*     */     }
/* 113 */     if (kind == 185) {
/* 114 */       index = this.cp.addInterfaceMethodref(class_name, name, signature);
/*     */     } else {
/* 116 */       index = this.cp.addMethodref(class_name, name, signature);
/*     */     } 
/* 118 */     switch (kind) { case 183:
/* 119 */         return new INVOKESPECIAL(index);
/* 120 */       case 182: return new INVOKEVIRTUAL(index);
/* 121 */       case 184: return new INVOKESTATIC(index);
/* 122 */       case 185: return new INVOKEINTERFACE(index, nargs + 1); }
/*     */     
/* 124 */     throw new RuntimeException("Oops: Unknown invoke kind:" + kind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InstructionList createPrintln(String s) {
/* 133 */     InstructionList il = new InstructionList();
/* 134 */     int out = this.cp.addFieldref("java.lang.System", "out", "Ljava/io/PrintStream;");
/*     */     
/* 136 */     int println = this.cp.addMethodref("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
/*     */ 
/*     */     
/* 139 */     il.append(new GETSTATIC(out));
/* 140 */     il.append(new PUSH(this.cp, s));
/* 141 */     il.append(new INVOKEVIRTUAL(println));
/*     */     
/* 143 */     return il;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction createConstant(Object value) {
/*     */     PUSH push;
/* 152 */     if (value instanceof Number) {
/* 153 */       push = new PUSH(this.cp, (Number)value);
/* 154 */     } else if (value instanceof String) {
/* 155 */       push = new PUSH(this.cp, (String)value);
/* 156 */     } else if (value instanceof Boolean) {
/* 157 */       push = new PUSH(this.cp, (Boolean)value);
/* 158 */     } else if (value instanceof Character) {
/* 159 */       push = new PUSH(this.cp, (Character)value);
/*     */     } else {
/* 161 */       throw new ClassGenException("Illegal type: " + value.getClass());
/*     */     } 
/* 163 */     return push.getInstruction();
/*     */   }
/*     */   
/*     */   private static class MethodObject {
/*     */     Type[] arg_types;
/*     */     Type result_type;
/*     */     String[] arg_names;
/*     */     String class_name;
/*     */     String name;
/*     */     int access;
/*     */     
/*     */     MethodObject(String c, String n, Type r, Type[] a, int acc) {
/* 175 */       this.class_name = c;
/* 176 */       this.name = n;
/* 177 */       this.result_type = r;
/* 178 */       this.arg_types = a;
/* 179 */       this.access = acc;
/*     */     }
/*     */   }
/*     */   
/*     */   private InvokeInstruction createInvoke(MethodObject m, short kind) {
/* 184 */     return createInvoke(m.class_name, m.name, m.result_type, m.arg_types, kind);
/*     */   }
/*     */   
/* 187 */   private static MethodObject[] append_mos = new MethodObject[] { new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.STRING }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.OBJECT }, 1), null, null, new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.BOOLEAN }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.CHAR }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.FLOAT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.DOUBLE }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.INT }, 1), new MethodObject("java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] { Type.LONG }, 1) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean isString(Type type) {
/* 212 */     return (type instanceof ObjectType && ((ObjectType)type)
/* 213 */       .getClassName().equals("java.lang.String"));
/*     */   }
/*     */   
/*     */   public Instruction createAppend(Type type) {
/* 217 */     byte t = type.getType();
/*     */     
/* 219 */     if (isString(type)) {
/* 220 */       return createInvoke(append_mos[0], (short)182);
/*     */     }
/* 222 */     switch (t) {
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/* 231 */         return createInvoke(append_mos[t], (short)182);
/*     */       case 13:
/*     */       case 14:
/* 234 */         return createInvoke(append_mos[1], (short)182);
/*     */     } 
/* 236 */     throw new RuntimeException("Oops: No append for this type? " + type);
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
/*     */   public FieldInstruction createFieldAccess(String class_name, String name, Type type, short kind) {
/* 250 */     String signature = type.getSignature();
/*     */     
/* 252 */     int index = this.cp.addFieldref(class_name, name, signature);
/*     */     
/* 254 */     switch (kind) { case 180:
/* 255 */         return new GETFIELD(index);
/* 256 */       case 181: return new PUTFIELD(index);
/* 257 */       case 178: return new GETSTATIC(index);
/* 258 */       case 179: return new PUTSTATIC(index); }
/*     */ 
/*     */     
/* 261 */     throw new RuntimeException("Oops: Unknown getfield kind:" + kind);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instruction createThis() {
/* 268 */     return new ALOAD(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReturnInstruction createReturn(Type type) {
/* 274 */     switch (type.getType()) { case 13:
/*     */       case 14:
/* 276 */         return ARETURN;
/*     */       case 4: case 5:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 281 */         return IRETURN;
/* 282 */       case 6: return FRETURN;
/* 283 */       case 7: return DRETURN;
/* 284 */       case 11: return LRETURN;
/* 285 */       case 12: return RETURN; }
/*     */ 
/*     */     
/* 288 */     throw new RuntimeException("Invalid type: " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final ArithmeticInstruction createBinaryIntOp(char first, String op) {
/* 293 */     switch (first) { case '-':
/* 294 */         return ISUB;
/* 295 */       case '+': return IADD;
/* 296 */       case '%': return IREM;
/* 297 */       case '*': return IMUL;
/* 298 */       case '/': return IDIV;
/* 299 */       case '&': return IAND;
/* 300 */       case '|': return IOR;
/* 301 */       case '^': return IXOR;
/* 302 */       case '<': return ISHL;
/* 303 */       case '>': return op.equals(">>>") ? IUSHR : ISHR; }
/*     */     
/* 305 */     throw new RuntimeException("Invalid operand " + op);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final ArithmeticInstruction createBinaryLongOp(char first, String op) {
/* 310 */     switch (first) { case '-':
/* 311 */         return LSUB;
/* 312 */       case '+': return LADD;
/* 313 */       case '%': return LREM;
/* 314 */       case '*': return LMUL;
/* 315 */       case '/': return LDIV;
/* 316 */       case '&': return LAND;
/* 317 */       case '|': return LOR;
/* 318 */       case '^': return LXOR;
/* 319 */       case '<': return LSHL;
/* 320 */       case '>': return op.equals(">>>") ? LUSHR : LSHR; }
/*     */     
/* 322 */     throw new RuntimeException("Invalid operand " + op);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final ArithmeticInstruction createBinaryFloatOp(char op) {
/* 327 */     switch (op) { case '-':
/* 328 */         return FSUB;
/* 329 */       case '+': return FADD;
/* 330 */       case '*': return FMUL;
/* 331 */       case '/': return FDIV; }
/* 332 */      throw new RuntimeException("Invalid operand " + op);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final ArithmeticInstruction createBinaryDoubleOp(char op) {
/* 337 */     switch (op) { case '-':
/* 338 */         return DSUB;
/* 339 */       case '+': return DADD;
/* 340 */       case '*': return DMUL;
/* 341 */       case '/': return DDIV; }
/* 342 */      throw new RuntimeException("Invalid operand " + op);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArithmeticInstruction createBinaryOperation(String op, Type type) {
/* 352 */     char first = op.toCharArray()[0];
/*     */     
/* 354 */     switch (type.getType()) { case 5:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 358 */         return createBinaryIntOp(first, op);
/* 359 */       case 11: return createBinaryLongOp(first, op);
/* 360 */       case 6: return createBinaryFloatOp(first);
/* 361 */       case 7: return createBinaryDoubleOp(first); }
/* 362 */      throw new RuntimeException("Invalid type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StackInstruction createPop(int size) {
/* 370 */     return (size == 2) ? POP2 : POP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StackInstruction createDup(int size) {
/* 378 */     return (size == 2) ? DUP2 : DUP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StackInstruction createDup_2(int size) {
/* 386 */     return (size == 2) ? DUP2_X2 : DUP_X2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StackInstruction createDup_1(int size) {
/* 394 */     return (size == 2) ? DUP2_X1 : DUP_X1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocalVariableInstruction createStore(Type type, int index) {
/* 402 */     switch (type.getType()) { case 4:
/*     */       case 5:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 407 */         return new ISTORE(index);
/* 408 */       case 6: return new FSTORE(index);
/* 409 */       case 7: return new DSTORE(index);
/* 410 */       case 11: return new LSTORE(index);
/*     */       case 13: case 14:
/* 412 */         return new ASTORE(index); }
/* 413 */      throw new RuntimeException("Invalid type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LocalVariableInstruction createLoad(Type type, int index) {
/* 421 */     switch (type.getType()) { case 4:
/*     */       case 5:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 426 */         return new ILOAD(index);
/* 427 */       case 6: return new FLOAD(index);
/* 428 */       case 7: return new DLOAD(index);
/* 429 */       case 11: return new LLOAD(index);
/*     */       case 13: case 14:
/* 431 */         return new ALOAD(index); }
/* 432 */      throw new RuntimeException("Invalid type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayInstruction createArrayLoad(Type type) {
/* 440 */     switch (type.getType()) { case 4:
/*     */       case 8:
/* 442 */         return BALOAD;
/* 443 */       case 5: return CALOAD;
/* 444 */       case 9: return SALOAD;
/* 445 */       case 10: return IALOAD;
/* 446 */       case 6: return FALOAD;
/* 447 */       case 7: return DALOAD;
/* 448 */       case 11: return LALOAD;
/*     */       case 13: case 14:
/* 450 */         return AALOAD; }
/* 451 */      throw new RuntimeException("Invalid type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayInstruction createArrayStore(Type type) {
/* 459 */     switch (type.getType()) { case 4:
/*     */       case 8:
/* 461 */         return BASTORE;
/* 462 */       case 5: return CASTORE;
/* 463 */       case 9: return SASTORE;
/* 464 */       case 10: return IASTORE;
/* 465 */       case 6: return FASTORE;
/* 466 */       case 7: return DASTORE;
/* 467 */       case 11: return LASTORE;
/*     */       case 13: case 14:
/* 469 */         return AASTORE; }
/* 470 */      throw new RuntimeException("Invalid type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction createCast(Type src_type, Type dest_type) {
/* 479 */     if (src_type instanceof BasicType && dest_type instanceof BasicType) {
/* 480 */       byte dest = dest_type.getType();
/* 481 */       byte src = src_type.getType();
/*     */       
/* 483 */       if (dest == 11 && (src == 5 || src == 8 || src == 9))
/*     */       {
/* 485 */         src = 10;
/*     */       }
/* 487 */       String[] short_names = { "C", "F", "D", "B", "S", "I", "L" };
/*     */       
/* 489 */       String name = "com.sun.org.apache.bcel.internal.generic." + short_names[src - 5] + "2" + short_names[dest - 5];
/*     */ 
/*     */       
/* 492 */       Instruction i = null;
/*     */       try {
/* 494 */         i = (Instruction)Class.forName(name).newInstance();
/* 495 */       } catch (Exception e) {
/* 496 */         throw new RuntimeException("Could not find instruction: " + name);
/*     */       } 
/*     */       
/* 499 */       return i;
/* 500 */     }  if (src_type instanceof ReferenceType && dest_type instanceof ReferenceType) {
/* 501 */       if (dest_type instanceof ArrayType) {
/* 502 */         return new CHECKCAST(this.cp.addArrayClass((ArrayType)dest_type));
/*     */       }
/* 504 */       return new CHECKCAST(this.cp.addClass(((ObjectType)dest_type).getClassName()));
/*     */     } 
/*     */     
/* 507 */     throw new RuntimeException("Can not cast " + src_type + " to " + dest_type);
/*     */   }
/*     */   
/*     */   public GETFIELD createGetField(String class_name, String name, Type t) {
/* 511 */     return new GETFIELD(this.cp.addFieldref(class_name, name, t.getSignature()));
/*     */   }
/*     */   
/*     */   public GETSTATIC createGetStatic(String class_name, String name, Type t) {
/* 515 */     return new GETSTATIC(this.cp.addFieldref(class_name, name, t.getSignature()));
/*     */   }
/*     */   
/*     */   public PUTFIELD createPutField(String class_name, String name, Type t) {
/* 519 */     return new PUTFIELD(this.cp.addFieldref(class_name, name, t.getSignature()));
/*     */   }
/*     */   
/*     */   public PUTSTATIC createPutStatic(String class_name, String name, Type t) {
/* 523 */     return new PUTSTATIC(this.cp.addFieldref(class_name, name, t.getSignature()));
/*     */   }
/*     */   
/*     */   public CHECKCAST createCheckCast(ReferenceType t) {
/* 527 */     if (t instanceof ArrayType) {
/* 528 */       return new CHECKCAST(this.cp.addArrayClass((ArrayType)t));
/*     */     }
/* 530 */     return new CHECKCAST(this.cp.addClass((ObjectType)t));
/*     */   }
/*     */   
/*     */   public INSTANCEOF createInstanceOf(ReferenceType t) {
/* 534 */     if (t instanceof ArrayType) {
/* 535 */       return new INSTANCEOF(this.cp.addArrayClass((ArrayType)t));
/*     */     }
/* 537 */     return new INSTANCEOF(this.cp.addClass((ObjectType)t));
/*     */   }
/*     */   
/*     */   public NEW createNew(ObjectType t) {
/* 541 */     return new NEW(this.cp.addClass(t));
/*     */   }
/*     */   
/*     */   public NEW createNew(String s) {
/* 545 */     return createNew(new ObjectType(s));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Instruction createNewArray(Type t, short dim) {
/*     */     ArrayType at;
/* 552 */     if (dim == 1) {
/* 553 */       if (t instanceof ObjectType)
/* 554 */         return new ANEWARRAY(this.cp.addClass((ObjectType)t)); 
/* 555 */       if (t instanceof ArrayType) {
/* 556 */         return new ANEWARRAY(this.cp.addArrayClass((ArrayType)t));
/*     */       }
/* 558 */       return new NEWARRAY(((BasicType)t).getType());
/*     */     } 
/*     */ 
/*     */     
/* 562 */     if (t instanceof ArrayType) {
/* 563 */       at = (ArrayType)t;
/*     */     } else {
/* 565 */       at = new ArrayType(t, dim);
/*     */     } 
/* 567 */     return new MULTIANEWARRAY(this.cp.addArrayClass(at), dim);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Instruction createNull(Type type) {
/* 574 */     switch (type.getType()) { case 13:
/*     */       case 14:
/* 576 */         return ACONST_NULL;
/*     */       case 4: case 5:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/* 581 */         return ICONST_0;
/* 582 */       case 6: return FCONST_0;
/* 583 */       case 7: return DCONST_0;
/* 584 */       case 11: return LCONST_0;
/* 585 */       case 12: return NOP; }
/*     */ 
/*     */     
/* 588 */     throw new RuntimeException("Invalid type: " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BranchInstruction createBranchInstruction(short opcode, InstructionHandle target) {
/* 596 */     switch (opcode) { case 153:
/* 597 */         return new IFEQ(target);
/* 598 */       case 154: return new IFNE(target);
/* 599 */       case 155: return new IFLT(target);
/* 600 */       case 156: return new IFGE(target);
/* 601 */       case 157: return new IFGT(target);
/* 602 */       case 158: return new IFLE(target);
/* 603 */       case 159: return new IF_ICMPEQ(target);
/* 604 */       case 160: return new IF_ICMPNE(target);
/* 605 */       case 161: return new IF_ICMPLT(target);
/* 606 */       case 162: return new IF_ICMPGE(target);
/* 607 */       case 163: return new IF_ICMPGT(target);
/* 608 */       case 164: return new IF_ICMPLE(target);
/* 609 */       case 165: return new IF_ACMPEQ(target);
/* 610 */       case 166: return new IF_ACMPNE(target);
/* 611 */       case 167: return new GOTO(target);
/* 612 */       case 168: return new JSR(target);
/* 613 */       case 198: return new IFNULL(target);
/* 614 */       case 199: return new IFNONNULL(target);
/* 615 */       case 200: return new GOTO_W(target);
/* 616 */       case 201: return new JSR_W(target); }
/*     */     
/* 618 */     throw new RuntimeException("Invalid opcode: " + opcode);
/*     */   }
/*     */   
/*     */   public void setClassGen(ClassGen c) {
/* 622 */     this.cg = c;
/* 623 */   } public ClassGen getClassGen() { return this.cg; }
/* 624 */   public void setConstantPool(ConstantPoolGen c) { this.cp = c; } public ConstantPoolGen getConstantPool() {
/* 625 */     return this.cp;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/InstructionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */