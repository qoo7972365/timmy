/*     */ package com.sun.corba.se.impl.naming.cosnaming;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CosNaming.Binding;
/*     */ import org.omg.CosNaming.BindingHolder;
/*     */ import org.omg.CosNaming.BindingType;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
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
/*     */ public class TransientBindingIterator
/*     */   extends BindingIteratorImpl
/*     */ {
/*     */   private POA nsPOA;
/*     */   private int currentSize;
/*     */   private Hashtable theHashtable;
/*     */   private Enumeration theEnumeration;
/*     */   
/*     */   public TransientBindingIterator(ORB paramORB, Hashtable paramHashtable, POA paramPOA) throws Exception {
/*  74 */     super(paramORB);
/*  75 */     this.theHashtable = paramHashtable;
/*  76 */     this.theEnumeration = this.theHashtable.elements();
/*  77 */     this.currentSize = this.theHashtable.size();
/*  78 */     this.nsPOA = paramPOA;
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
/*     */   
/*     */   public final boolean NextOne(BindingHolder paramBindingHolder) {
/*  91 */     boolean bool = this.theEnumeration.hasMoreElements();
/*  92 */     if (bool) {
/*  93 */       paramBindingHolder
/*  94 */         .value = ((InternalBindingValue)this.theEnumeration.nextElement()).theBinding;
/*  95 */       this.currentSize--;
/*     */     } else {
/*     */       
/*  98 */       paramBindingHolder.value = new Binding(new org.omg.CosNaming.NameComponent[0], BindingType.nobject);
/*     */     } 
/* 100 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void Destroy() {
/*     */     try {
/* 112 */       byte[] arrayOfByte = this.nsPOA.servant_to_id((Servant)this);
/* 113 */       if (arrayOfByte != null) {
/* 114 */         this.nsPOA.deactivate_object(arrayOfByte);
/*     */       }
/*     */     }
/* 117 */     catch (Exception exception) {
/* 118 */       NamingUtils.errprint("BindingIterator.Destroy():caught exception:");
/* 119 */       NamingUtils.printException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int RemainingElements() {
/* 128 */     return this.currentSize;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/TransientBindingIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */