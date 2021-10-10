/*    */ package sun.util;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import java.util.Set;
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
/*    */ public class ResourceBundleEnumeration
/*    */   implements Enumeration<String>
/*    */ {
/*    */   Set<String> set;
/*    */   Iterator<String> iterator;
/*    */   Enumeration<String> enumeration;
/*    */   String next;
/*    */   
/*    */   public ResourceBundleEnumeration(Set<String> paramSet, Enumeration<String> paramEnumeration) {
/* 55 */     this.next = null;
/*    */     this.set = paramSet;
/*    */     this.iterator = paramSet.iterator();
/* 58 */     this.enumeration = paramEnumeration; } public boolean hasMoreElements() { if (this.next == null) {
/* 59 */       if (this.iterator.hasNext()) {
/* 60 */         this.next = this.iterator.next();
/* 61 */       } else if (this.enumeration != null) {
/* 62 */         while (this.next == null && this.enumeration.hasMoreElements()) {
/* 63 */           this.next = this.enumeration.nextElement();
/* 64 */           if (this.set.contains(this.next)) {
/* 65 */             this.next = null;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     }
/* 70 */     return (this.next != null); }
/*    */ 
/*    */   
/*    */   public String nextElement() {
/* 74 */     if (hasMoreElements()) {
/* 75 */       String str = this.next;
/* 76 */       this.next = null;
/* 77 */       return str;
/*    */     } 
/* 79 */     throw new NoSuchElementException();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/ResourceBundleEnumeration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */