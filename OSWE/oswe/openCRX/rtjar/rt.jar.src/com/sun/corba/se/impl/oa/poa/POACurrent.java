/*     */ package com.sun.corba.se.impl.oa.poa;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.POASystemException;
/*     */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.util.EmptyStackException;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.PortableServer.Current;
/*     */ import org.omg.PortableServer.CurrentPackage.NoContext;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
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
/*     */ public class POACurrent
/*     */   extends ObjectImpl
/*     */   implements Current
/*     */ {
/*     */   private ORB orb;
/*     */   private POASystemException wrapper;
/*     */   
/*     */   public POACurrent(ORB paramORB) {
/*  54 */     this.orb = paramORB;
/*  55 */     this.wrapper = POASystemException.get(paramORB, "oa.invocation");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  61 */     String[] arrayOfString = new String[1];
/*  62 */     arrayOfString[0] = "IDL:omg.org/PortableServer/Current:1.0";
/*  63 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public POA get_POA() throws NoContext {
/*  74 */     POA pOA = (POA)peekThrowNoContext().oa();
/*  75 */     throwNoContextIfNull(pOA);
/*  76 */     return pOA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] get_object_id() throws NoContext {
/*  83 */     byte[] arrayOfByte = peekThrowNoContext().id();
/*  84 */     throwNoContextIfNull(arrayOfByte);
/*  85 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectAdapter getOA() {
/*  94 */     ObjectAdapter objectAdapter = peekThrowInternal().oa();
/*  95 */     throwInternalIfNull(objectAdapter);
/*  96 */     return objectAdapter;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getObjectId() {
/* 101 */     byte[] arrayOfByte = peekThrowInternal().id();
/* 102 */     throwInternalIfNull(arrayOfByte);
/* 103 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   Servant getServant() {
/* 108 */     return (Servant)peekThrowInternal().getServantContainer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CookieHolder getCookieHolder() {
/* 117 */     CookieHolder cookieHolder = peekThrowInternal().getCookieHolder();
/* 118 */     throwInternalIfNull(cookieHolder);
/* 119 */     return cookieHolder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperation() {
/* 127 */     String str = peekThrowInternal().getOperation();
/* 128 */     throwInternalIfNull(str);
/* 129 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   void setServant(Servant paramServant) {
/* 134 */     peekThrowInternal().setServant(paramServant);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OAInvocationInfo peekThrowNoContext() throws NoContext {
/* 145 */     OAInvocationInfo oAInvocationInfo = null;
/*     */     try {
/* 147 */       oAInvocationInfo = this.orb.peekInvocationInfo();
/* 148 */     } catch (EmptyStackException emptyStackException) {
/* 149 */       throw new NoContext();
/*     */     } 
/* 151 */     return oAInvocationInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private OAInvocationInfo peekThrowInternal() {
/* 156 */     OAInvocationInfo oAInvocationInfo = null;
/*     */     try {
/* 158 */       oAInvocationInfo = this.orb.peekInvocationInfo();
/* 159 */     } catch (EmptyStackException emptyStackException) {
/*     */ 
/*     */       
/* 162 */       throw this.wrapper.poacurrentUnbalancedStack(emptyStackException);
/*     */     } 
/* 164 */     return oAInvocationInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwNoContextIfNull(Object paramObject) throws NoContext {
/* 171 */     if (paramObject == null) {
/* 172 */       throw new NoContext();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void throwInternalIfNull(Object paramObject) {
/* 178 */     if (paramObject == null)
/* 179 */       throw this.wrapper.poacurrentNullField(CompletionStatus.COMPLETED_MAYBE); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/poa/POACurrent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */