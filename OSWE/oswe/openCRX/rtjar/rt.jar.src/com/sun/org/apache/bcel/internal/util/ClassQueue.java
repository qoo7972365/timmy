/*    */ package com.sun.org.apache.bcel.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedList;
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
/*    */ 
/*    */ public class ClassQueue
/*    */   implements Serializable
/*    */ {
/* 71 */   protected LinkedList vec = new LinkedList();
/*    */   public void enqueue(JavaClass clazz) {
/* 73 */     this.vec.addLast(clazz);
/*    */   }
/*    */   public JavaClass dequeue() {
/* 76 */     return this.vec.removeFirst();
/*    */   }
/*    */   public boolean empty() {
/* 79 */     return this.vec.isEmpty();
/*    */   }
/*    */   public String toString() {
/* 82 */     return this.vec.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ClassQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */