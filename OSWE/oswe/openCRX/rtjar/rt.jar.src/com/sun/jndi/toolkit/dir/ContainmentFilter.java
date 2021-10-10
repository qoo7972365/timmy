/*    */ package com.sun.jndi.toolkit.dir;
/*    */ 
/*    */ import javax.naming.NamingEnumeration;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attribute;
/*    */ import javax.naming.directory.Attributes;
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
/*    */ public class ContainmentFilter
/*    */   implements AttrFilter
/*    */ {
/*    */   private Attributes matchingAttrs;
/*    */   
/*    */   public ContainmentFilter(Attributes paramAttributes) {
/* 44 */     this.matchingAttrs = paramAttributes;
/*    */   }
/*    */   
/*    */   public boolean check(Attributes paramAttributes) throws NamingException {
/* 48 */     return (this.matchingAttrs == null || this.matchingAttrs
/* 49 */       .size() == 0 || 
/* 50 */       contains(paramAttributes, this.matchingAttrs));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean contains(Attributes paramAttributes1, Attributes paramAttributes2) throws NamingException {
/* 56 */     if (paramAttributes2 == null) {
/* 57 */       return true;
/*    */     }
/* 59 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes2.getAll();
/* 60 */     while (namingEnumeration.hasMore()) {
/* 61 */       if (paramAttributes1 == null) {
/* 62 */         return false;
/*    */       }
/* 64 */       Attribute attribute1 = namingEnumeration.next();
/* 65 */       Attribute attribute2 = paramAttributes1.get(attribute1.getID());
/* 66 */       if (attribute2 == null) {
/* 67 */         return false;
/*    */       }
/*    */       
/* 70 */       if (attribute1.size() > 0) {
/* 71 */         NamingEnumeration<?> namingEnumeration1 = attribute1.getAll();
/* 72 */         while (namingEnumeration1.hasMore()) {
/* 73 */           if (!attribute2.contains(namingEnumeration1.next())) {
/* 74 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/ContainmentFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */