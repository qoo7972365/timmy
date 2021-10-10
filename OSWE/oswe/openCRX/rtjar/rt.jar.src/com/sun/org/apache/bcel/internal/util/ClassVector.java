/*    */ package com.sun.org.apache.bcel.internal.util;
/*    */ 
/*    */ import com.sun.org.apache.bcel.internal.classfile.JavaClass;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
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
/*    */ public class ClassVector
/*    */   implements Serializable
/*    */ {
/* 71 */   protected ArrayList vec = new ArrayList();
/*    */   
/* 73 */   public void addElement(JavaClass clazz) { this.vec.add(clazz); }
/* 74 */   public JavaClass elementAt(int index) { return this.vec.get(index); } public void removeElementAt(int index) {
/* 75 */     this.vec.remove(index);
/*    */   }
/*    */   public JavaClass[] toArray() {
/* 78 */     JavaClass[] classes = new JavaClass[this.vec.size()];
/* 79 */     this.vec.toArray((Object[])classes);
/* 80 */     return classes;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/ClassVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */