/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.RuntimeOperationsException;
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
/*     */ public class NamedObject
/*     */ {
/*     */   private final ObjectName name;
/*     */   private final DynamicMBean object;
/*     */   
/*     */   public NamedObject(ObjectName paramObjectName, DynamicMBean paramDynamicMBean) {
/*  59 */     if (paramObjectName.isPattern()) {
/*  60 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid name->" + paramObjectName.toString()));
/*     */     }
/*  62 */     this.name = paramObjectName;
/*  63 */     this.object = paramDynamicMBean;
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
/*     */   public NamedObject(String paramString, DynamicMBean paramDynamicMBean) throws MalformedObjectNameException {
/*  75 */     ObjectName objectName = new ObjectName(paramString);
/*  76 */     if (objectName.isPattern()) {
/*  77 */       throw new RuntimeOperationsException(new IllegalArgumentException("Invalid name->" + objectName.toString()));
/*     */     }
/*  79 */     this.name = objectName;
/*  80 */     this.object = paramDynamicMBean;
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
/*     */   public boolean equals(Object paramObject) {
/*  92 */     if (this == paramObject) return true; 
/*  93 */     if (paramObject == null) return false; 
/*  94 */     if (!(paramObject instanceof NamedObject)) return false; 
/*  95 */     NamedObject namedObject = (NamedObject)paramObject;
/*  96 */     return this.name.equals(namedObject.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectName getName() {
/* 112 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DynamicMBean getObject() {
/* 119 */     return this.object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/NamedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */