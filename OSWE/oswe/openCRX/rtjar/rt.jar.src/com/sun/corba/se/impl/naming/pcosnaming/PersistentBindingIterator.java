/*     */ package com.sun.corba.se.impl.naming.pcosnaming;
/*     */ 
/*     */ import com.sun.corba.se.impl.naming.cosnaming.BindingIteratorImpl;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.INTERNAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CosNaming.Binding;
/*     */ import org.omg.CosNaming.BindingHolder;
/*     */ import org.omg.CosNaming.BindingType;
/*     */ import org.omg.CosNaming.NameComponent;
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
/*     */ public class PersistentBindingIterator
/*     */   extends BindingIteratorImpl
/*     */ {
/*     */   private POA biPOA;
/*     */   private int currentSize;
/*     */   private Hashtable theHashtable;
/*     */   private Enumeration theEnumeration;
/*     */   private ORB orb;
/*     */   
/*     */   public PersistentBindingIterator(ORB paramORB, Hashtable paramHashtable, POA paramPOA) throws Exception {
/*  74 */     super(paramORB);
/*  75 */     this.orb = paramORB;
/*  76 */     this.theHashtable = paramHashtable;
/*  77 */     this.theEnumeration = this.theHashtable.keys();
/*  78 */     this.currentSize = this.theHashtable.size();
/*  79 */     this.biPOA = paramPOA;
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
/*  92 */     boolean bool = this.theEnumeration.hasMoreElements();
/*  93 */     if (bool) {
/*     */       
/*  95 */       InternalBindingKey internalBindingKey = this.theEnumeration.nextElement();
/*     */       
/*  97 */       InternalBindingValue internalBindingValue = (InternalBindingValue)this.theHashtable.get(internalBindingKey);
/*  98 */       NameComponent nameComponent = new NameComponent(internalBindingKey.id, internalBindingKey.kind);
/*  99 */       NameComponent[] arrayOfNameComponent = new NameComponent[1];
/* 100 */       arrayOfNameComponent[0] = nameComponent;
/* 101 */       BindingType bindingType = internalBindingValue.theBindingType;
/*     */       
/* 103 */       paramBindingHolder.value = new Binding(arrayOfNameComponent, bindingType);
/*     */     }
/*     */     else {
/*     */       
/* 107 */       paramBindingHolder.value = new Binding(new NameComponent[0], BindingType.nobject);
/*     */     } 
/* 109 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void Destroy() {
/*     */     try {
/* 120 */       byte[] arrayOfByte = this.biPOA.servant_to_id((Servant)this);
/* 121 */       if (arrayOfByte != null) {
/* 122 */         this.biPOA.deactivate_object(arrayOfByte);
/*     */       }
/*     */     }
/* 125 */     catch (Exception exception) {
/* 126 */       throw new INTERNAL("Exception in BindingIterator.Destroy " + exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int RemainingElements() {
/* 135 */     return this.currentSize;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/PersistentBindingIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */