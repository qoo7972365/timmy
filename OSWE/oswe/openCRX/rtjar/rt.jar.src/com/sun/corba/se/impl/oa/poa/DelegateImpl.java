/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.EmptyStackException;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.portable.Delegate;
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
/*     */ public class DelegateImpl
/*     */   implements Delegate
/*     */ {
/*     */   private ORB orb;
/*     */   private POASystemException wrapper;
/*     */   private POAFactory factory;
/*     */   
/*     */   public DelegateImpl(ORB paramORB, POAFactory paramPOAFactory) {
/*  44 */     this.orb = paramORB;
/*  45 */     this.wrapper = POASystemException.get(paramORB, "oa");
/*     */     
/*  47 */     this.factory = paramPOAFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public ORB orb(Servant paramServant) {
/*  52 */     return (ORB)this.orb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object this_object(Servant paramServant) {
/*     */     try {
/*  60 */       byte[] arrayOfByte = this.orb.peekInvocationInfo().id();
/*  61 */       POA pOA = (POA)this.orb.peekInvocationInfo().oa();
/*  62 */       String str = paramServant._all_interfaces(pOA, arrayOfByte)[0];
/*  63 */       return pOA.create_reference_with_id(arrayOfByte, str);
/*  64 */     } catch (EmptyStackException emptyStackException) {
/*     */       
/*  66 */       POAImpl pOAImpl = null;
/*     */       try {
/*  68 */         pOAImpl = (POAImpl)paramServant._default_POA();
/*  69 */       } catch (ClassCastException classCastException) {
/*  70 */         throw this.wrapper.defaultPoaNotPoaimpl(classCastException);
/*     */       } 
/*     */       
/*     */       try {
/*  74 */         if (pOAImpl.getPolicies().isImplicitlyActivated() || (pOAImpl
/*  75 */           .getPolicies().isUniqueIds() && pOAImpl
/*  76 */           .getPolicies().retainServants())) {
/*  77 */           return pOAImpl.servant_to_reference(paramServant);
/*     */         }
/*  79 */         throw this.wrapper.wrongPoliciesForThisObject();
/*     */       }
/*  81 */       catch (ServantNotActive servantNotActive) {
/*  82 */         throw this.wrapper.thisObjectServantNotActive(servantNotActive);
/*  83 */       } catch (WrongPolicy wrongPolicy) {
/*  84 */         throw this.wrapper.thisObjectWrongPolicy(wrongPolicy);
/*     */       } 
/*  86 */     } catch (ClassCastException classCastException) {
/*  87 */       throw this.wrapper.defaultPoaNotPoaimpl(classCastException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public POA poa(Servant paramServant) {
/*     */     try {
/*  94 */       return (POA)this.orb.peekInvocationInfo().oa();
/*  95 */     } catch (EmptyStackException emptyStackException) {
/*  96 */       POA pOA = this.factory.lookupPOA(paramServant);
/*  97 */       if (pOA != null) {
/*  98 */         return pOA;
/*     */       }
/*     */       
/* 101 */       throw this.wrapper.noContext(emptyStackException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] object_id(Servant paramServant) {
/*     */     try {
/* 108 */       return this.orb.peekInvocationInfo().id();
/* 109 */     } catch (EmptyStackException emptyStackException) {
/* 110 */       throw this.wrapper.noContext(emptyStackException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public POA default_POA(Servant paramServant) {
/* 116 */     return this.factory.getRootPOA();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is_a(Servant paramServant, String paramString) {
/* 121 */     String[] arrayOfString = paramServant._all_interfaces(poa(paramServant), object_id(paramServant));
/* 122 */     for (byte b = 0; b < arrayOfString.length; b++) {
/* 123 */       if (paramString.equals(arrayOfString[b]))
/* 124 */         return true; 
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean non_existent(Servant paramServant) {
/*     */     try {
/* 133 */       byte[] arrayOfByte = this.orb.peekInvocationInfo().id();
/* 134 */       if (arrayOfByte == null) return true; 
/* 135 */       return false;
/* 136 */     } catch (EmptyStackException emptyStackException) {
/* 137 */       throw this.wrapper.noContext(emptyStackException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get_interface_def(Servant paramServant) {
/* 145 */     throw this.wrapper.methodNotImplemented();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/DelegateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */