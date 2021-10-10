/*    */ package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.generic.ALOAD;
/*    */ import com.sun.org.apache.bcel.internal.generic.ASTORE;
/*    */ import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
/*    */ import com.sun.org.apache.bcel.internal.generic.Instruction;
/*    */ import com.sun.org.apache.bcel.internal.generic.InstructionList;
/*    */ import com.sun.org.apache.bcel.internal.generic.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NamedMethodGenerator
/*    */   extends MethodGenerator
/*    */ {
/*    */   protected static final int CURRENT_INDEX = 4;
/*    */   private static final int PARAM_START_INDEX = 5;
/*    */   
/*    */   public NamedMethodGenerator(int access_flags, Type return_type, Type[] arg_types, String[] arg_names, String method_name, String class_name, InstructionList il, ConstantPoolGen cp) {
/* 49 */     super(access_flags, return_type, arg_types, arg_names, method_name, class_name, il, cp);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLocalIndex(String name) {
/* 54 */     if (name.equals("current")) {
/* 55 */       return 4;
/*    */     }
/* 57 */     return super.getLocalIndex(name);
/*    */   }
/*    */   
/*    */   public Instruction loadParameter(int index) {
/* 61 */     return new ALOAD(index + 5);
/*    */   }
/*    */   
/*    */   public Instruction storeParameter(int index) {
/* 65 */     return new ASTORE(index + 5);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/xsltc/compiler/util/NamedMethodGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */