/*    */ package com.sun.org.apache.bcel.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*    */ import java.io.Serializable;
/*    */ import java.util.Stack;
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
/*    */ public class ClassStack
/*    */   implements Serializable
/*    */ {
/* 70 */   private Stack stack = new Stack();
/*    */   
/* 72 */   public void push(JavaClass clazz) { this.stack.push(clazz); }
/* 73 */   public JavaClass pop() { return this.stack.pop(); }
/* 74 */   public JavaClass top() { return this.stack.peek(); } public boolean empty() {
/* 75 */     return this.stack.empty();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ClassStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */