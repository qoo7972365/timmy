/*      */ package com.sun.org.apache.bcel.internal.generic;
/*      */ 
/*      */ import com.sun.org.apache.bcel.internal.classfile.Attribute;
/*      */ import com.sun.org.apache.bcel.internal.classfile.Code;
/*      */ import com.sun.org.apache.bcel.internal.classfile.CodeException;
/*      */ import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;
/*      */ import com.sun.org.apache.bcel.internal.classfile.LineNumber;
/*      */ import com.sun.org.apache.bcel.internal.classfile.LineNumberTable;
/*      */ import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
/*      */ import com.sun.org.apache.bcel.internal.classfile.LocalVariableTable;
/*      */ import com.sun.org.apache.bcel.internal.classfile.LocalVariableTypeTable;
/*      */ import com.sun.org.apache.bcel.internal.classfile.Method;
/*      */ import com.sun.org.apache.bcel.internal.classfile.Utility;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Stack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MethodGen
/*      */   extends FieldGenOrMethodGen
/*      */ {
/*      */   private String class_name;
/*      */   private Type[] arg_types;
/*      */   private String[] arg_names;
/*      */   private int max_locals;
/*      */   private int max_stack;
/*      */   private InstructionList il;
/*      */   private boolean strip_attributes;
/*   89 */   private ArrayList variable_vec = new ArrayList();
/*   90 */   private ArrayList type_vec = new ArrayList();
/*   91 */   private ArrayList line_number_vec = new ArrayList();
/*   92 */   private ArrayList exception_vec = new ArrayList();
/*   93 */   private ArrayList throws_vec = new ArrayList();
/*   94 */   private ArrayList code_attrs_vec = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList observers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodGen(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp) {
/*  121 */     setAccessFlags(access_flags);
/*  122 */     setType(return_type);
/*  123 */     setArgumentTypes(arg_types);
/*  124 */     setArgumentNames(arg_names);
/*  125 */     setName(method_name);
/*  126 */     setClassName(class_name);
/*  127 */     setInstructionList(il);
/*  128 */     setConstantPool(cp);
/*      */     
/*  130 */     boolean abstract_ = (isAbstract() || isNative());
/*  131 */     InstructionHandle start = null;
/*  132 */     InstructionHandle end = null;
/*      */     
/*  134 */     if (!abstract_) {
/*  135 */       start = il.getStart();
/*  136 */       end = il.getEnd();
/*      */ 
/*      */ 
/*      */       
/*  140 */       if (!isStatic() && class_name != null) {
/*  141 */         addLocalVariable("this", new ObjectType(class_name), start, end);
/*      */       }
/*      */     } 
/*      */     
/*  145 */     if (arg_types != null) {
/*  146 */       int size = arg_types.length;
/*      */       int i;
/*  148 */       for (i = 0; i < size; i++) {
/*  149 */         if (Type.VOID == arg_types[i]) {
/*  150 */           throw new ClassGenException("'void' is an illegal argument type for a method");
/*      */         }
/*      */       } 
/*      */       
/*  154 */       if (arg_names != null) {
/*  155 */         if (size != arg_names.length) {
/*  156 */           throw new ClassGenException("Mismatch in argument array lengths: " + size + " vs. " + arg_names.length);
/*      */         }
/*      */       } else {
/*  159 */         arg_names = new String[size];
/*      */         
/*  161 */         for (i = 0; i < size; i++) {
/*  162 */           arg_names[i] = "arg" + i;
/*      */         }
/*  164 */         setArgumentNames(arg_names);
/*      */       } 
/*      */       
/*  167 */       if (!abstract_) {
/*  168 */         for (i = 0; i < size; i++) {
/*  169 */           addLocalVariable(arg_names[i], arg_types[i], start, end);
/*      */         }
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
/*      */   
/*      */   public MethodGen(Method m, String class_name, ConstantPoolGen cp) {
/*  183 */     this(m.getAccessFlags(), Type.getReturnType(m.getSignature()), 
/*  184 */         Type.getArgumentTypes(m.getSignature()), (String[])null, m
/*  185 */         .getName(), class_name, 
/*  186 */         ((m.getAccessFlags() & 0x500) == 0) ? new InstructionList(m
/*  187 */           .getCode().getCode()) : null, cp);
/*      */ 
/*      */     
/*  190 */     Attribute[] attributes = m.getAttributes();
/*  191 */     for (int i = 0; i < attributes.length; i++) {
/*  192 */       Attribute a = attributes[i];
/*      */       
/*  194 */       if (a instanceof Code) {
/*  195 */         Code c = (Code)a;
/*  196 */         setMaxStack(c.getMaxStack());
/*  197 */         setMaxLocals(c.getMaxLocals());
/*      */         
/*  199 */         CodeException[] ces = c.getExceptionTable();
/*      */         
/*  201 */         if (ces != null) {
/*  202 */           for (int k = 0; k < ces.length; k++) {
/*  203 */             InstructionHandle end; CodeException ce = ces[k];
/*  204 */             int type = ce.getCatchType();
/*  205 */             ObjectType c_type = null;
/*      */             
/*  207 */             if (type > 0) {
/*  208 */               String cen = m.getConstantPool().getConstantString(type, (byte)7);
/*  209 */               c_type = new ObjectType(cen);
/*      */             } 
/*      */             
/*  212 */             int end_pc = ce.getEndPC();
/*  213 */             int length = (m.getCode().getCode()).length;
/*      */ 
/*      */ 
/*      */             
/*  217 */             if (length == end_pc) {
/*  218 */               end = this.il.getEnd();
/*      */             } else {
/*  220 */               end = this.il.findHandle(end_pc);
/*  221 */               end = end.getPrev();
/*      */             } 
/*      */             
/*  224 */             addExceptionHandler(this.il.findHandle(ce.getStartPC()), end, this.il
/*  225 */                 .findHandle(ce.getHandlerPC()), c_type);
/*      */           } 
/*      */         }
/*      */         
/*  229 */         Attribute[] c_attributes = c.getAttributes();
/*  230 */         for (int j = 0; j < c_attributes.length; j++) {
/*  231 */           a = c_attributes[j];
/*      */           
/*  233 */           if (a instanceof LineNumberTable)
/*  234 */           { LineNumber[] ln = ((LineNumberTable)a).getLineNumberTable();
/*      */             
/*  236 */             for (int k = 0; k < ln.length; k++) {
/*  237 */               LineNumber l = ln[k];
/*  238 */               addLineNumber(this.il.findHandle(l.getStartPC()), l.getLineNumber());
/*      */             }  }
/*  240 */           else if (a instanceof LocalVariableTable)
/*  241 */           { LocalVariable[] lv = ((LocalVariableTable)a).getLocalVariableTable();
/*      */             
/*  243 */             removeLocalVariables();
/*      */             
/*  245 */             for (int k = 0; k < lv.length; k++) {
/*  246 */               LocalVariable l = lv[k];
/*  247 */               InstructionHandle start = this.il.findHandle(l.getStartPC());
/*  248 */               InstructionHandle end = this.il.findHandle(l.getStartPC() + l.getLength());
/*      */ 
/*      */               
/*  251 */               if (null == start) {
/*  252 */                 start = this.il.getStart();
/*      */               }
/*      */               
/*  255 */               if (null == end) {
/*  256 */                 end = this.il.getEnd();
/*      */               }
/*      */               
/*  259 */               addLocalVariable(l.getName(), Type.getType(l.getSignature()), l
/*  260 */                   .getIndex(), start, end);
/*      */             }  }
/*  262 */           else if (a instanceof LocalVariableTypeTable)
/*  263 */           { LocalVariable[] lv = ((LocalVariableTypeTable)a).getLocalVariableTypeTable();
/*  264 */             removeLocalVariableTypes();
/*  265 */             for (int k = 0; k < lv.length; k++) {
/*  266 */               LocalVariable l = lv[k];
/*  267 */               InstructionHandle start = this.il.findHandle(l.getStartPC());
/*  268 */               InstructionHandle end = this.il.findHandle(l.getStartPC() + l.getLength());
/*      */               
/*  270 */               if (null == start) {
/*  271 */                 start = this.il.getStart();
/*      */               }
/*  273 */               if (null == end) {
/*  274 */                 end = this.il.getEnd();
/*      */               }
/*  276 */               addLocalVariableType(l.getName(), Type.getType(l.getSignature()), l
/*  277 */                   .getIndex(), start, end);
/*      */             }  }
/*      */           else
/*  280 */           { addCodeAttribute(a); } 
/*      */         } 
/*  282 */       } else if (a instanceof ExceptionTable) {
/*  283 */         String[] names = ((ExceptionTable)a).getExceptionNames();
/*  284 */         for (int j = 0; j < names.length; j++)
/*  285 */           addException(names[j]); 
/*      */       } else {
/*  287 */         addAttribute(a);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalVariableGen addLocalVariable(String name, Type type, int slot, InstructionHandle start, InstructionHandle end) {
/*  306 */     byte t = type.getType();
/*      */     
/*  308 */     if (t != 16) {
/*  309 */       int add = type.getSize();
/*      */       
/*  311 */       if (slot + add > this.max_locals) {
/*  312 */         this.max_locals = slot + add;
/*      */       }
/*  314 */       LocalVariableGen l = new LocalVariableGen(slot, name, type, start, end);
/*      */       
/*      */       int i;
/*  317 */       if ((i = this.variable_vec.indexOf(l)) >= 0) {
/*  318 */         this.variable_vec.set(i, l);
/*      */       } else {
/*  320 */         this.variable_vec.add(l);
/*      */       } 
/*  322 */       return l;
/*      */     } 
/*  324 */     throw new IllegalArgumentException("Can not use " + type + " as type for local variable");
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
/*      */   public LocalVariableGen addLocalVariable(String name, Type type, InstructionHandle start, InstructionHandle end) {
/*  345 */     return addLocalVariable(name, type, this.max_locals, start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLocalVariable(LocalVariableGen l) {
/*  353 */     this.variable_vec.remove(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLocalVariables() {
/*  360 */     this.variable_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final void sort(LocalVariableGen[] vars, int l, int r) {
/*  367 */     int i = l, j = r;
/*  368 */     int m = vars[(l + r) / 2].getIndex();
/*      */ 
/*      */     
/*      */     do {
/*  372 */       for (; vars[i].getIndex() < m; i++);
/*  373 */       for (; m < vars[j].getIndex(); j--);
/*      */       
/*  375 */       if (i > j)
/*  376 */         continue;  LocalVariableGen h = vars[i]; vars[i] = vars[j]; vars[j] = h;
/*  377 */       i++; j--;
/*      */     }
/*  379 */     while (i <= j);
/*      */     
/*  381 */     if (l < j) sort(vars, l, j); 
/*  382 */     if (i < r) sort(vars, i, r);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalVariableGen[] getLocalVariables() {
/*  392 */     int size = this.variable_vec.size();
/*  393 */     LocalVariableGen[] lg = new LocalVariableGen[size];
/*  394 */     this.variable_vec.toArray((Object[])lg);
/*      */     
/*  396 */     for (int i = 0; i < size; i++) {
/*  397 */       if (lg[i].getStart() == null) {
/*  398 */         lg[i].setStart(this.il.getStart());
/*      */       }
/*  400 */       if (lg[i].getEnd() == null) {
/*  401 */         lg[i].setEnd(this.il.getEnd());
/*      */       }
/*      */     } 
/*  404 */     if (size > 1) {
/*  405 */       sort(lg, 0, size - 1);
/*      */     }
/*  407 */     return lg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LocalVariableGen[] getLocalVariableTypes() {
/*  417 */     int size = this.type_vec.size();
/*  418 */     LocalVariableGen[] lg = new LocalVariableGen[size];
/*  419 */     this.type_vec.toArray((Object[])lg);
/*      */     
/*  421 */     for (int i = 0; i < size; i++) {
/*  422 */       if (lg[i].getStart() == null) {
/*  423 */         lg[i].setStart(this.il.getStart());
/*      */       }
/*  425 */       if (lg[i].getEnd() == null) {
/*  426 */         lg[i].setEnd(this.il.getEnd());
/*      */       }
/*      */     } 
/*  429 */     if (size > 1) {
/*  430 */       sort(lg, 0, size - 1);
/*      */     }
/*  432 */     return lg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalVariableTable getLocalVariableTable(ConstantPoolGen cp) {
/*  439 */     LocalVariableGen[] lg = getLocalVariables();
/*  440 */     int size = lg.length;
/*  441 */     LocalVariable[] lv = new LocalVariable[size];
/*      */     
/*  443 */     for (int i = 0; i < size; i++) {
/*  444 */       lv[i] = lg[i].getLocalVariable(cp);
/*      */     }
/*  446 */     return new LocalVariableTable(cp.addUtf8("LocalVariableTable"), 2 + lv.length * 10, lv, cp
/*  447 */         .getConstantPool());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LocalVariableTypeTable getLocalVariableTypeTable(ConstantPoolGen cp) {
/*  455 */     LocalVariableGen[] lg = getLocalVariableTypes();
/*  456 */     int size = lg.length;
/*  457 */     LocalVariable[] lv = new LocalVariable[size];
/*      */     
/*  459 */     for (int i = 0; i < size; i++) {
/*  460 */       lv[i] = lg[i].getLocalVariable(cp);
/*      */     }
/*  462 */     return new LocalVariableTypeTable(cp.addUtf8("LocalVariableTypeTable"), 2 + lv.length * 10, lv, cp
/*  463 */         .getConstantPool());
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
/*      */   private LocalVariableGen addLocalVariableType(String name, Type type, int slot, InstructionHandle start, InstructionHandle end) {
/*  481 */     byte t = type.getType();
/*      */     
/*  483 */     if (t != 16) {
/*  484 */       int add = type.getSize();
/*      */       
/*  486 */       if (slot + add > this.max_locals) {
/*  487 */         this.max_locals = slot + add;
/*      */       }
/*  489 */       LocalVariableGen l = new LocalVariableGen(slot, name, type, start, end);
/*      */       
/*      */       int i;
/*  492 */       if ((i = this.type_vec.indexOf(l)) >= 0) {
/*  493 */         this.type_vec.set(i, l);
/*      */       } else {
/*  495 */         this.type_vec.add(l);
/*      */       } 
/*  497 */       return l;
/*      */     } 
/*  499 */     throw new IllegalArgumentException("Can not use " + type + " as type for local variable");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeLocalVariableTypes() {
/*  509 */     this.type_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LineNumberGen addLineNumber(InstructionHandle ih, int src_line) {
/*  520 */     LineNumberGen l = new LineNumberGen(ih, src_line);
/*  521 */     this.line_number_vec.add(l);
/*  522 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLineNumber(LineNumberGen l) {
/*  529 */     this.line_number_vec.remove(l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLineNumbers() {
/*  536 */     this.line_number_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LineNumberGen[] getLineNumbers() {
/*  543 */     LineNumberGen[] lg = new LineNumberGen[this.line_number_vec.size()];
/*  544 */     this.line_number_vec.toArray((Object[])lg);
/*  545 */     return lg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LineNumberTable getLineNumberTable(ConstantPoolGen cp) {
/*  552 */     int size = this.line_number_vec.size();
/*  553 */     LineNumber[] ln = new LineNumber[size];
/*      */     
/*      */     try {
/*  556 */       for (int i = 0; i < size; i++)
/*  557 */         ln[i] = ((LineNumberGen)this.line_number_vec.get(i)).getLineNumber(); 
/*  558 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     
/*  560 */     return new LineNumberTable(cp.addUtf8("LineNumberTable"), 2 + ln.length * 4, ln, cp
/*  561 */         .getConstantPool());
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
/*      */   public CodeExceptionGen addExceptionHandler(InstructionHandle start_pc, InstructionHandle end_pc, InstructionHandle handler_pc, ObjectType catch_type) {
/*  579 */     if (start_pc == null || end_pc == null || handler_pc == null) {
/*  580 */       throw new ClassGenException("Exception handler target is null instruction");
/*      */     }
/*  582 */     CodeExceptionGen c = new CodeExceptionGen(start_pc, end_pc, handler_pc, catch_type);
/*      */     
/*  584 */     this.exception_vec.add(c);
/*  585 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExceptionHandler(CodeExceptionGen c) {
/*  592 */     this.exception_vec.remove(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExceptionHandlers() {
/*  599 */     this.exception_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CodeExceptionGen[] getExceptionHandlers() {
/*  606 */     CodeExceptionGen[] cg = new CodeExceptionGen[this.exception_vec.size()];
/*  607 */     this.exception_vec.toArray((Object[])cg);
/*  608 */     return cg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CodeException[] getCodeExceptions() {
/*  615 */     int size = this.exception_vec.size();
/*  616 */     CodeException[] c_exc = new CodeException[size];
/*      */     
/*      */     try {
/*  619 */       for (int i = 0; i < size; i++) {
/*  620 */         CodeExceptionGen c = this.exception_vec.get(i);
/*  621 */         c_exc[i] = c.getCodeException(this.cp);
/*      */       } 
/*  623 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     
/*  625 */     return c_exc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addException(String class_name) {
/*  634 */     this.throws_vec.add(class_name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeException(String c) {
/*  641 */     this.throws_vec.remove(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeExceptions() {
/*  648 */     this.throws_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getExceptions() {
/*  655 */     String[] e = new String[this.throws_vec.size()];
/*  656 */     this.throws_vec.toArray((Object[])e);
/*  657 */     return e;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExceptionTable getExceptionTable(ConstantPoolGen cp) {
/*  664 */     int size = this.throws_vec.size();
/*  665 */     int[] ex = new int[size];
/*      */     
/*      */     try {
/*  668 */       for (int i = 0; i < size; i++)
/*  669 */         ex[i] = cp.addClass((String)this.throws_vec.get(i)); 
/*  670 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     
/*  672 */     return new ExceptionTable(cp.addUtf8("Exceptions"), 2 + 2 * size, ex, cp
/*  673 */         .getConstantPool());
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
/*      */   public void addCodeAttribute(Attribute a) {
/*  685 */     this.code_attrs_vec.add(a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeCodeAttribute(Attribute a) {
/*  690 */     this.code_attrs_vec.remove(a);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeCodeAttributes() {
/*  696 */     this.code_attrs_vec.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attribute[] getCodeAttributes() {
/*  703 */     Attribute[] attributes = new Attribute[this.code_attrs_vec.size()];
/*  704 */     this.code_attrs_vec.toArray((Object[])attributes);
/*  705 */     return attributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Method getMethod() {
/*  715 */     String signature = getSignature();
/*  716 */     int name_index = this.cp.addUtf8(this.name);
/*  717 */     int signature_index = this.cp.addUtf8(signature);
/*      */ 
/*      */ 
/*      */     
/*  721 */     byte[] byte_code = null;
/*      */     
/*  723 */     if (this.il != null) {
/*  724 */       byte_code = this.il.getByteCode();
/*      */     }
/*  726 */     LineNumberTable lnt = null;
/*  727 */     LocalVariableTable lvt = null;
/*  728 */     LocalVariableTypeTable lvtt = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  733 */     if (this.variable_vec.size() > 0 && !this.strip_attributes) {
/*  734 */       addCodeAttribute(lvt = getLocalVariableTable(this.cp));
/*      */     }
/*  736 */     if (this.type_vec.size() > 0 && !this.strip_attributes) {
/*  737 */       addCodeAttribute(lvtt = getLocalVariableTypeTable(this.cp));
/*      */     }
/*  739 */     if (this.line_number_vec.size() > 0 && !this.strip_attributes) {
/*  740 */       addCodeAttribute(lnt = getLineNumberTable(this.cp));
/*      */     }
/*  742 */     Attribute[] code_attrs = getCodeAttributes();
/*      */ 
/*      */ 
/*      */     
/*  746 */     int attrs_len = 0;
/*  747 */     for (int i = 0; i < code_attrs.length; i++) {
/*  748 */       attrs_len += code_attrs[i].getLength() + 6;
/*      */     }
/*  750 */     CodeException[] c_exc = getCodeExceptions();
/*  751 */     int exc_len = c_exc.length * 8;
/*      */     
/*  753 */     Code code = null;
/*      */     
/*  755 */     if (this.il != null && !isAbstract()) {
/*      */       
/*  757 */       Attribute[] attributes = getAttributes();
/*  758 */       for (int j = 0; j < attributes.length; j++) {
/*  759 */         Attribute a = attributes[j];
/*      */         
/*  761 */         if (a instanceof Code) {
/*  762 */           removeAttribute(a);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       code = new Code(this.cp.addUtf8("Code"), 8 + byte_code.length + 2 + exc_len + 2 + attrs_len, this.max_stack, this.max_locals, byte_code, c_exc, code_attrs, this.cp.getConstantPool());
/*      */       
/*  774 */       addAttribute(code);
/*      */     } 
/*      */     
/*  777 */     ExceptionTable et = null;
/*      */     
/*  779 */     if (this.throws_vec.size() > 0) {
/*  780 */       addAttribute(et = getExceptionTable(this.cp));
/*      */     }
/*      */     
/*  783 */     Method m = new Method(this.access_flags, name_index, signature_index, getAttributes(), this.cp.getConstantPool());
/*      */ 
/*      */     
/*  786 */     if (lvt != null) removeCodeAttribute(lvt); 
/*  787 */     if (lvtt != null) removeCodeAttribute(lvtt); 
/*  788 */     if (lnt != null) removeCodeAttribute(lnt); 
/*  789 */     if (code != null) removeAttribute(code); 
/*  790 */     if (et != null) removeAttribute(et);
/*      */     
/*  792 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNOPs() {
/*  801 */     if (this.il != null)
/*      */     {
/*      */ 
/*      */       
/*  805 */       for (InstructionHandle ih = this.il.getStart(); ih != null; ih = next) {
/*  806 */         InstructionHandle next = ih.next;
/*      */         
/*  808 */         if (next != null && ih.getInstruction() instanceof NOP) {
/*      */           try {
/*  810 */             this.il.delete(ih);
/*  811 */           } catch (TargetLostException e) {
/*  812 */             InstructionHandle[] targets = e.getTargets();
/*      */             
/*  814 */             for (int i = 0; i < targets.length; i++) {
/*  815 */               InstructionTargeter[] targeters = targets[i].getTargeters();
/*      */               
/*  817 */               for (int j = 0; j < targeters.length; j++) {
/*  818 */                 targeters[j].updateTarget(targets[i], next);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxLocals(int m) {
/*  829 */     this.max_locals = m; } public int getMaxLocals() {
/*  830 */     return this.max_locals;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxStack(int m) {
/*  835 */     this.max_stack = m; } public int getMaxStack() {
/*  836 */     return this.max_stack;
/*      */   }
/*      */   
/*      */   public String getClassName() {
/*  840 */     return this.class_name; } public void setClassName(String class_name) {
/*  841 */     this.class_name = class_name;
/*      */   }
/*  843 */   public void setReturnType(Type return_type) { setType(return_type); } public Type getReturnType() {
/*  844 */     return getType();
/*      */   }
/*  846 */   public void setArgumentTypes(Type[] arg_types) { this.arg_types = arg_types; }
/*  847 */   public Type[] getArgumentTypes() { return (Type[])this.arg_types.clone(); }
/*  848 */   public void setArgumentType(int i, Type type) { this.arg_types[i] = type; } public Type getArgumentType(int i) {
/*  849 */     return this.arg_types[i];
/*      */   }
/*  851 */   public void setArgumentNames(String[] arg_names) { this.arg_names = arg_names; }
/*  852 */   public String[] getArgumentNames() { return (String[])this.arg_names.clone(); }
/*  853 */   public void setArgumentName(int i, String name) { this.arg_names[i] = name; } public String getArgumentName(int i) {
/*  854 */     return this.arg_names[i];
/*      */   }
/*  856 */   public InstructionList getInstructionList() { return this.il; } public void setInstructionList(InstructionList il) {
/*  857 */     this.il = il;
/*      */   }
/*      */   public String getSignature() {
/*  860 */     return Type.getMethodSignature(this.type, this.arg_types);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxStack() {
/*  867 */     if (this.il != null) {
/*  868 */       this.max_stack = getMaxStack(this.cp, this.il, getExceptionHandlers());
/*      */     } else {
/*  870 */       this.max_stack = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxLocals() {
/*  877 */     if (this.il != null) {
/*  878 */       int max = isStatic() ? 0 : 1;
/*      */       
/*  880 */       if (this.arg_types != null)
/*  881 */         for (int i = 0; i < this.arg_types.length; i++) {
/*  882 */           max += this.arg_types[i].getSize();
/*      */         } 
/*  884 */       for (InstructionHandle ih = this.il.getStart(); ih != null; ih = ih.getNext()) {
/*  885 */         Instruction ins = ih.getInstruction();
/*      */         
/*  887 */         if (ins instanceof LocalVariableInstruction || ins instanceof RET || ins instanceof IINC) {
/*      */ 
/*      */ 
/*      */           
/*  891 */           int index = ((IndexedInstruction)ins).getIndex() + ((TypedInstruction)ins).getType(this.cp).getSize();
/*      */           
/*  893 */           if (index > max) {
/*  894 */             max = index;
/*      */           }
/*      */         } 
/*      */       } 
/*  898 */       this.max_locals = max;
/*      */     } else {
/*  900 */       this.max_locals = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void stripAttributes(boolean flag) {
/*  906 */     this.strip_attributes = flag;
/*      */   }
/*      */   
/*      */   static final class BranchTarget { InstructionHandle target;
/*      */     int stackDepth;
/*      */     
/*      */     BranchTarget(InstructionHandle target, int stackDepth) {
/*  913 */       this.target = target;
/*  914 */       this.stackDepth = stackDepth;
/*      */     } }
/*      */ 
/*      */   
/*      */   static final class BranchStack {
/*  919 */     Stack branchTargets = new Stack();
/*  920 */     Hashtable visitedTargets = new Hashtable<>();
/*      */     
/*      */     public void push(InstructionHandle target, int stackDepth) {
/*  923 */       if (visited(target)) {
/*      */         return;
/*      */       }
/*  926 */       this.branchTargets.push(visit(target, stackDepth));
/*      */     }
/*      */     
/*      */     public MethodGen.BranchTarget pop() {
/*  930 */       if (!this.branchTargets.empty()) {
/*  931 */         MethodGen.BranchTarget bt = this.branchTargets.pop();
/*  932 */         return bt;
/*      */       } 
/*      */       
/*  935 */       return null;
/*      */     }
/*      */     
/*      */     private final MethodGen.BranchTarget visit(InstructionHandle target, int stackDepth) {
/*  939 */       MethodGen.BranchTarget bt = new MethodGen.BranchTarget(target, stackDepth);
/*  940 */       this.visitedTargets.put(target, bt);
/*      */       
/*  942 */       return bt;
/*      */     }
/*      */     
/*      */     private final boolean visited(InstructionHandle target) {
/*  946 */       return (this.visitedTargets.get(target) != null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getMaxStack(ConstantPoolGen cp, InstructionList il, CodeExceptionGen[] et) {
/*  956 */     BranchStack branchTargets = new BranchStack();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  963 */     for (int i = 0; i < et.length; i++) {
/*  964 */       InstructionHandle handler_pc = et[i].getHandlerPC();
/*  965 */       if (handler_pc != null) {
/*  966 */         branchTargets.push(handler_pc, 1);
/*      */       }
/*      */     } 
/*  969 */     int stackDepth = 0, maxStackDepth = 0;
/*  970 */     InstructionHandle ih = il.getStart();
/*      */     
/*  972 */     while (ih != null) {
/*  973 */       Instruction instruction = ih.getInstruction();
/*  974 */       short opcode = instruction.getOpcode();
/*  975 */       int delta = instruction.produceStack(cp) - instruction.consumeStack(cp);
/*      */       
/*  977 */       stackDepth += delta;
/*  978 */       if (stackDepth > maxStackDepth) {
/*  979 */         maxStackDepth = stackDepth;
/*      */       }
/*      */       
/*  982 */       if (instruction instanceof BranchInstruction) {
/*  983 */         BranchInstruction branch = (BranchInstruction)instruction;
/*  984 */         if (instruction instanceof Select) {
/*      */           
/*  986 */           Select select = (Select)branch;
/*  987 */           InstructionHandle[] targets = select.getTargets();
/*  988 */           for (int j = 0; j < targets.length; j++) {
/*  989 */             branchTargets.push(targets[j], stackDepth);
/*      */           }
/*  991 */           ih = null;
/*  992 */         } else if (!(branch instanceof IfInstruction)) {
/*      */ 
/*      */           
/*  995 */           if (opcode == 168 || opcode == 201)
/*  996 */             branchTargets.push(ih.getNext(), stackDepth - 1); 
/*  997 */           ih = null;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1002 */         branchTargets.push(branch.getTarget(), stackDepth);
/*      */       
/*      */       }
/* 1005 */       else if (opcode == 191 || opcode == 169 || (opcode >= 172 && opcode <= 177)) {
/*      */         
/* 1007 */         ih = null;
/*      */       } 
/*      */       
/* 1010 */       if (ih != null) {
/* 1011 */         ih = ih.getNext();
/*      */       }
/* 1013 */       if (ih == null) {
/* 1014 */         BranchTarget bt = branchTargets.pop();
/* 1015 */         if (bt != null) {
/* 1016 */           ih = bt.target;
/* 1017 */           stackDepth = bt.stackDepth;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1022 */     return maxStackDepth;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addObserver(MethodObserver o) {
/* 1030 */     if (this.observers == null) {
/* 1031 */       this.observers = new ArrayList();
/*      */     }
/* 1033 */     this.observers.add(o);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeObserver(MethodObserver o) {
/* 1039 */     if (this.observers != null) {
/* 1040 */       this.observers.remove(o);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/* 1048 */     if (this.observers != null) {
/* 1049 */       for (Iterator<MethodObserver> e = this.observers.iterator(); e.hasNext();) {
/* 1050 */         ((MethodObserver)e.next()).notify(this);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String toString() {
/* 1060 */     String access = Utility.accessToString(this.access_flags);
/* 1061 */     String signature = Type.getMethodSignature(this.type, this.arg_types);
/*      */     
/* 1063 */     signature = Utility.methodSignatureToString(signature, this.name, access, true, 
/* 1064 */         getLocalVariableTable(this.cp));
/*      */     
/* 1066 */     StringBuffer buf = new StringBuffer(signature);
/*      */     
/* 1068 */     if (this.throws_vec.size() > 0) {
/* 1069 */       for (Iterator<String> e = this.throws_vec.iterator(); e.hasNext();) {
/* 1070 */         buf.append("\n\t\tthrows " + e.next());
/*      */       }
/*      */     }
/* 1073 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MethodGen copy(String class_name, ConstantPoolGen cp) {
/* 1079 */     Method m = ((MethodGen)clone()).getMethod();
/* 1080 */     MethodGen mg = new MethodGen(m, class_name, this.cp);
/*      */     
/* 1082 */     if (this.cp != cp) {
/* 1083 */       mg.setConstantPool(cp);
/* 1084 */       mg.getInstructionList().replaceConstantPool(this.cp, cp);
/*      */     } 
/*      */     
/* 1087 */     return mg;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/generic/MethodGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */