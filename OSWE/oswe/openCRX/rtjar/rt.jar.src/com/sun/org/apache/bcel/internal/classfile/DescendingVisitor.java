/*     */ package com.sun.org.apache.bcel.internal.classfile;
/*     */ 
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescendingVisitor
/*     */   implements Visitor
/*     */ {
/*     */   private JavaClass clazz;
/*     */   private Visitor visitor;
/*  73 */   private Stack stack = new Stack();
/*     */ 
/*     */ 
/*     */   
/*     */   public Object predecessor() {
/*  78 */     return predecessor(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object predecessor(int level) {
/*  86 */     int size = this.stack.size();
/*     */     
/*  88 */     if (size < 2 || level < 0) {
/*  89 */       return null;
/*     */     }
/*  91 */     return this.stack.elementAt(size - level + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object current() {
/*  97 */     return this.stack.peek();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DescendingVisitor(JavaClass clazz, Visitor visitor) {
/* 105 */     this.clazz = clazz;
/* 106 */     this.visitor = visitor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit() {
/* 112 */     this.clazz.accept(this);
/*     */   }
/*     */   public void visitJavaClass(JavaClass clazz) {
/* 115 */     this.stack.push(clazz);
/* 116 */     clazz.accept(this.visitor);
/*     */     
/* 118 */     Field[] fields = clazz.getFields();
/* 119 */     for (int i = 0; i < fields.length; i++) {
/* 120 */       fields[i].accept(this);
/*     */     }
/* 122 */     Method[] methods = clazz.getMethods();
/* 123 */     for (int j = 0; j < methods.length; j++) {
/* 124 */       methods[j].accept(this);
/*     */     }
/* 126 */     Attribute[] attributes = clazz.getAttributes();
/* 127 */     for (int k = 0; k < attributes.length; k++) {
/* 128 */       attributes[k].accept(this);
/*     */     }
/* 130 */     clazz.getConstantPool().accept(this);
/* 131 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitField(Field field) {
/* 135 */     this.stack.push(field);
/* 136 */     field.accept(this.visitor);
/*     */     
/* 138 */     Attribute[] attributes = field.getAttributes();
/* 139 */     for (int i = 0; i < attributes.length; i++)
/* 140 */       attributes[i].accept(this); 
/* 141 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantValue(ConstantValue cv) {
/* 145 */     this.stack.push(cv);
/* 146 */     cv.accept(this.visitor);
/* 147 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitMethod(Method method) {
/* 151 */     this.stack.push(method);
/* 152 */     method.accept(this.visitor);
/*     */     
/* 154 */     Attribute[] attributes = method.getAttributes();
/* 155 */     for (int i = 0; i < attributes.length; i++) {
/* 156 */       attributes[i].accept(this);
/*     */     }
/* 158 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitExceptionTable(ExceptionTable table) {
/* 162 */     this.stack.push(table);
/* 163 */     table.accept(this.visitor);
/* 164 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitCode(Code code) {
/* 168 */     this.stack.push(code);
/* 169 */     code.accept(this.visitor);
/*     */     
/* 171 */     CodeException[] table = code.getExceptionTable();
/* 172 */     for (int i = 0; i < table.length; i++) {
/* 173 */       table[i].accept(this);
/*     */     }
/* 175 */     Attribute[] attributes = code.getAttributes();
/* 176 */     for (int j = 0; j < attributes.length; j++)
/* 177 */       attributes[j].accept(this); 
/* 178 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitCodeException(CodeException ce) {
/* 182 */     this.stack.push(ce);
/* 183 */     ce.accept(this.visitor);
/* 184 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitLineNumberTable(LineNumberTable table) {
/* 188 */     this.stack.push(table);
/* 189 */     table.accept(this.visitor);
/*     */     
/* 191 */     LineNumber[] numbers = table.getLineNumberTable();
/* 192 */     for (int i = 0; i < numbers.length; i++)
/* 193 */       numbers[i].accept(this); 
/* 194 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitLineNumber(LineNumber number) {
/* 198 */     this.stack.push(number);
/* 199 */     number.accept(this.visitor);
/* 200 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitLocalVariableTable(LocalVariableTable table) {
/* 204 */     this.stack.push(table);
/* 205 */     table.accept(this.visitor);
/*     */     
/* 207 */     LocalVariable[] vars = table.getLocalVariableTable();
/* 208 */     for (int i = 0; i < vars.length; i++)
/* 209 */       vars[i].accept(this); 
/* 210 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitLocalVariableTypeTable(LocalVariableTypeTable obj) {
/* 214 */     this.stack.push(obj);
/* 215 */     obj.accept(this.visitor);
/*     */     
/* 217 */     LocalVariable[] vars = obj.getLocalVariableTypeTable();
/* 218 */     for (int i = 0; i < vars.length; i++)
/* 219 */       vars[i].accept(this); 
/* 220 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitStackMap(StackMap table) {
/* 224 */     this.stack.push(table);
/* 225 */     table.accept(this.visitor);
/*     */     
/* 227 */     StackMapEntry[] vars = table.getStackMap();
/*     */     
/* 229 */     for (int i = 0; i < vars.length; i++)
/* 230 */       vars[i].accept(this); 
/* 231 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitStackMapEntry(StackMapEntry var) {
/* 235 */     this.stack.push(var);
/* 236 */     var.accept(this.visitor);
/* 237 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitLocalVariable(LocalVariable var) {
/* 241 */     this.stack.push(var);
/* 242 */     var.accept(this.visitor);
/* 243 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantPool(ConstantPool cp) {
/* 247 */     this.stack.push(cp);
/* 248 */     cp.accept(this.visitor);
/*     */     
/* 250 */     Constant[] constants = cp.getConstantPool();
/* 251 */     for (int i = 1; i < constants.length; i++) {
/* 252 */       if (constants[i] != null) {
/* 253 */         constants[i].accept(this);
/*     */       }
/*     */     } 
/* 256 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantClass(ConstantClass constant) {
/* 260 */     this.stack.push(constant);
/* 261 */     constant.accept(this.visitor);
/* 262 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantDouble(ConstantDouble constant) {
/* 266 */     this.stack.push(constant);
/* 267 */     constant.accept(this.visitor);
/* 268 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantFieldref(ConstantFieldref constant) {
/* 272 */     this.stack.push(constant);
/* 273 */     constant.accept(this.visitor);
/* 274 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantFloat(ConstantFloat constant) {
/* 278 */     this.stack.push(constant);
/* 279 */     constant.accept(this.visitor);
/* 280 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantInteger(ConstantInteger constant) {
/* 284 */     this.stack.push(constant);
/* 285 */     constant.accept(this.visitor);
/* 286 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constant) {
/* 290 */     this.stack.push(constant);
/* 291 */     constant.accept(this.visitor);
/* 292 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantLong(ConstantLong constant) {
/* 296 */     this.stack.push(constant);
/* 297 */     constant.accept(this.visitor);
/* 298 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantMethodref(ConstantMethodref constant) {
/* 302 */     this.stack.push(constant);
/* 303 */     constant.accept(this.visitor);
/* 304 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantNameAndType(ConstantNameAndType constant) {
/* 308 */     this.stack.push(constant);
/* 309 */     constant.accept(this.visitor);
/* 310 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantString(ConstantString constant) {
/* 314 */     this.stack.push(constant);
/* 315 */     constant.accept(this.visitor);
/* 316 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitConstantUtf8(ConstantUtf8 constant) {
/* 320 */     this.stack.push(constant);
/* 321 */     constant.accept(this.visitor);
/* 322 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitInnerClasses(InnerClasses ic) {
/* 326 */     this.stack.push(ic);
/* 327 */     ic.accept(this.visitor);
/*     */     
/* 329 */     InnerClass[] ics = ic.getInnerClasses();
/* 330 */     for (int i = 0; i < ics.length; i++)
/* 331 */       ics[i].accept(this); 
/* 332 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitInnerClass(InnerClass inner) {
/* 336 */     this.stack.push(inner);
/* 337 */     inner.accept(this.visitor);
/* 338 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitDeprecated(Deprecated attribute) {
/* 342 */     this.stack.push(attribute);
/* 343 */     attribute.accept(this.visitor);
/* 344 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitSignature(Signature attribute) {
/* 348 */     this.stack.push(attribute);
/* 349 */     attribute.accept(this.visitor);
/* 350 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitSourceFile(SourceFile attribute) {
/* 354 */     this.stack.push(attribute);
/* 355 */     attribute.accept(this.visitor);
/* 356 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitSynthetic(Synthetic attribute) {
/* 360 */     this.stack.push(attribute);
/* 361 */     attribute.accept(this.visitor);
/* 362 */     this.stack.pop();
/*     */   }
/*     */   
/*     */   public void visitUnknown(Unknown attribute) {
/* 366 */     this.stack.push(attribute);
/* 367 */     attribute.accept(this.visitor);
/* 368 */     this.stack.pop();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/classfile/DescendingVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */