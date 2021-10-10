/*     */ package com.sun.corba.se.impl.naming.cosnaming;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.INITIALIZE;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.PortableServer.IdAssignmentPolicyValue;
/*     */ import org.omg.PortableServer.LifespanPolicyValue;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.ServantRetentionPolicyValue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransientNameService
/*     */ {
/*     */   private Object theInitialNamingContext;
/*     */   
/*     */   public TransientNameService(ORB paramORB) throws INITIALIZE {
/*  80 */     initialize(paramORB, "NameService");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransientNameService(ORB paramORB, String paramString) throws INITIALIZE {
/*  97 */     initialize(paramORB, paramString);
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
/*     */   private void initialize(ORB paramORB, String paramString) throws INITIALIZE {
/* 109 */     NamingSystemException namingSystemException = NamingSystemException.get(paramORB, "naming");
/*     */ 
/*     */     
/*     */     try {
/* 113 */       POA pOA1 = (POA)paramORB.resolve_initial_references("RootPOA");
/*     */       
/* 115 */       pOA1.the_POAManager().activate();
/*     */       
/* 117 */       byte b = 0;
/* 118 */       Policy[] arrayOfPolicy = new Policy[3];
/* 119 */       arrayOfPolicy[b++] = (Policy)pOA1.create_lifespan_policy(LifespanPolicyValue.TRANSIENT);
/*     */       
/* 121 */       arrayOfPolicy[b++] = (Policy)pOA1.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID);
/*     */       
/* 123 */       arrayOfPolicy[b++] = (Policy)pOA1.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN);
/*     */ 
/*     */       
/* 126 */       POA pOA2 = pOA1.create_POA("TNameService", null, arrayOfPolicy);
/* 127 */       pOA2.the_POAManager().activate();
/*     */ 
/*     */       
/* 130 */       TransientNamingContext transientNamingContext = new TransientNamingContext(paramORB, null, pOA2);
/*     */       
/* 132 */       byte[] arrayOfByte = pOA2.activate_object((Servant)transientNamingContext);
/* 133 */       transientNamingContext
/* 134 */         .localRoot = pOA2.id_to_reference(arrayOfByte);
/* 135 */       this.theInitialNamingContext = transientNamingContext.localRoot;
/* 136 */       paramORB.register_initial_reference(paramString, this.theInitialNamingContext);
/*     */     }
/* 138 */     catch (SystemException systemException) {
/* 139 */       throw namingSystemException.transNsCannotCreateInitialNcSys(systemException);
/* 140 */     } catch (Exception exception) {
/* 141 */       throw namingSystemException.transNsCannotCreateInitialNc(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object initialNamingContext() {
/* 152 */     return this.theInitialNamingContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/TransientNameService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */