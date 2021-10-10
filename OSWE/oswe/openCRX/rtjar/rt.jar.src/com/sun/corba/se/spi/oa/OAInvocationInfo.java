/*     */ package com.sun.corba.se.spi.oa;
/*     */ 
/*     */ import com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import org.omg.CORBA.portable.ServantObject;
/*     */ import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
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
/*     */ public class OAInvocationInfo
/*     */   extends ServantObject
/*     */ {
/*     */   private Object servantContainer;
/*     */   private ObjectAdapter oa;
/*     */   private byte[] oid;
/*     */   private CookieHolder cookieHolder;
/*     */   private String operation;
/*     */   private ObjectCopierFactory factory;
/*     */   
/*     */   public OAInvocationInfo(ObjectAdapter paramObjectAdapter, byte[] paramArrayOfbyte) {
/*  63 */     this.oa = paramObjectAdapter;
/*  64 */     this.oid = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OAInvocationInfo(OAInvocationInfo paramOAInvocationInfo, String paramString) {
/*  70 */     this.servant = paramOAInvocationInfo.servant;
/*  71 */     this.servantContainer = paramOAInvocationInfo.servantContainer;
/*  72 */     this.cookieHolder = paramOAInvocationInfo.cookieHolder;
/*  73 */     this.oa = paramOAInvocationInfo.oa;
/*  74 */     this.oid = paramOAInvocationInfo.oid;
/*  75 */     this.factory = paramOAInvocationInfo.factory;
/*     */     
/*  77 */     this.operation = paramString;
/*     */   }
/*     */   
/*     */   public ObjectAdapter oa() {
/*  81 */     return this.oa;
/*  82 */   } public byte[] id() { return this.oid; } public Object getServantContainer() {
/*  83 */     return this.servantContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CookieHolder getCookieHolder() {
/*  89 */     if (this.cookieHolder == null) {
/*  90 */       this.cookieHolder = new CookieHolder();
/*     */     }
/*  92 */     return this.cookieHolder;
/*     */   }
/*     */   
/*  95 */   public String getOperation() { return this.operation; } public ObjectCopierFactory getCopierFactory() {
/*  96 */     return this.factory;
/*     */   }
/*     */   
/*  99 */   public void setOperation(String paramString) { this.operation = paramString; } public void setCopierFactory(ObjectCopierFactory paramObjectCopierFactory) {
/* 100 */     this.factory = paramObjectCopierFactory;
/*     */   }
/*     */   
/*     */   public void setServant(Object paramObject) {
/* 104 */     this.servantContainer = paramObject;
/* 105 */     if (paramObject instanceof Tie) {
/* 106 */       this.servant = ((Tie)paramObject).getTarget();
/*     */     } else {
/* 108 */       this.servant = paramObject;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/oa/OAInvocationInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */