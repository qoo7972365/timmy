/*    */ package com.sun.corba.se.spi.servicecontext;
/*    */ 
/*    */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Vector;
/*    */ import org.omg.CORBA.BAD_PARAM;
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
/*    */ public class ServiceContextRegistry
/*    */ {
/*    */   private ORB orb;
/*    */   private Vector scCollection;
/*    */   
/*    */   private void dprint(String paramString) {
/* 42 */     ORBUtility.dprint(this, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public ServiceContextRegistry(ORB paramORB) {
/* 47 */     this.scCollection = new Vector();
/* 48 */     this.orb = paramORB;
/*    */   }
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
/*    */   public void register(Class paramClass) {
/* 64 */     if (ORB.ORBInitDebug) {
/* 65 */       dprint("Registering service context class " + paramClass);
/*    */     }
/* 67 */     ServiceContextData serviceContextData = new ServiceContextData(paramClass);
/*    */     
/* 69 */     if (findServiceContextData(serviceContextData.getId()) == null) {
/* 70 */       this.scCollection.addElement(serviceContextData);
/*    */     } else {
/* 72 */       throw new BAD_PARAM("Tried to register duplicate service context");
/*    */     } 
/*    */   }
/*    */   
/*    */   public ServiceContextData findServiceContextData(int paramInt) {
/* 77 */     if (ORB.ORBInitDebug) {
/* 78 */       dprint("Searching registry for service context id " + paramInt);
/*    */     }
/* 80 */     Enumeration<ServiceContextData> enumeration = this.scCollection.elements();
/* 81 */     while (enumeration.hasMoreElements()) {
/*    */       
/* 83 */       ServiceContextData serviceContextData = enumeration.nextElement();
/* 84 */       if (serviceContextData.getId() == paramInt) {
/* 85 */         if (ORB.ORBInitDebug) {
/* 86 */           dprint("Service context data found: " + serviceContextData);
/*    */         }
/* 88 */         return serviceContextData;
/*    */       } 
/*    */     } 
/*    */     
/* 92 */     if (ORB.ORBInitDebug) {
/* 93 */       dprint("Service context data not found");
/*    */     }
/* 95 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/servicecontext/ServiceContextRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */