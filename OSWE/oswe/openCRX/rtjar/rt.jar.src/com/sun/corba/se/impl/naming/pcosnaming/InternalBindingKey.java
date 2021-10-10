/*     */ package com.sun.corba.se.impl.naming.pcosnaming;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.omg.CosNaming.NameComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalBindingKey
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5410796631793704055L;
/*     */   public String id;
/*     */   public String kind;
/*     */   
/*     */   public InternalBindingKey() {}
/*     */   
/*     */   public InternalBindingKey(NameComponent paramNameComponent) {
/*  55 */     setup(paramNameComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup(NameComponent paramNameComponent) {
/*  60 */     this.id = paramNameComponent.id;
/*  61 */     this.kind = paramNameComponent.kind;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  66 */     if (paramObject == null)
/*  67 */       return false; 
/*  68 */     if (paramObject instanceof InternalBindingKey) {
/*  69 */       InternalBindingKey internalBindingKey = (InternalBindingKey)paramObject;
/*  70 */       if (this.id != null && internalBindingKey.id != null) {
/*     */         
/*  72 */         if (this.id.length() != internalBindingKey.id.length())
/*     */         {
/*  74 */           return false;
/*     */         }
/*     */         
/*  77 */         if (this.id.length() > 0 && !this.id.equals(internalBindingKey.id))
/*     */         {
/*  79 */           return false;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*  86 */       else if ((this.id == null && internalBindingKey.id != null) || (this.id != null && internalBindingKey.id == null)) {
/*     */ 
/*     */         
/*  89 */         return false;
/*     */       } 
/*     */       
/*  92 */       if (this.kind != null && internalBindingKey.kind != null) {
/*     */         
/*  94 */         if (this.kind.length() != internalBindingKey.kind.length())
/*     */         {
/*  96 */           return false;
/*     */         }
/*     */         
/*  99 */         if (this.kind.length() > 0 && !this.kind.equals(internalBindingKey.kind))
/*     */         {
/* 101 */           return false;
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 108 */       else if ((this.kind == null && internalBindingKey.kind != null) || (this.kind != null && internalBindingKey.kind == null)) {
/*     */ 
/*     */         
/* 111 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 115 */       return true;
/*     */     } 
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     int i = 0;
/* 125 */     if (this.id.length() > 0)
/*     */     {
/* 127 */       i += this.id.hashCode();
/*     */     }
/* 129 */     if (this.kind.length() > 0)
/*     */     {
/* 131 */       i += this.kind.hashCode();
/*     */     }
/* 133 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/InternalBindingKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */