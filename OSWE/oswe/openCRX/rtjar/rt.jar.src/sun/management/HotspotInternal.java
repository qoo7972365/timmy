/*    */ package sun.management;
/*    */ 
/*    */ import javax.management.MBeanRegistration;
/*    */ import javax.management.MBeanServer;
/*    */ import javax.management.ObjectName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HotspotInternal
/*    */   implements HotspotInternalMBean, MBeanRegistration
/*    */ {
/*    */   private static final String HOTSPOT_INTERNAL_MBEAN_NAME = "sun.management:type=HotspotInternal";
/* 44 */   private static ObjectName objName = Util.newObjectName("sun.management:type=HotspotInternal");
/* 45 */   private MBeanServer server = null;
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
/*    */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/* 58 */     ManagementFactoryHelper.registerInternalMBeans(paramMBeanServer);
/* 59 */     this.server = paramMBeanServer;
/* 60 */     return objName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void postRegister(Boolean paramBoolean) {}
/*    */   
/*    */   public void preDeregister() throws Exception {
/* 67 */     ManagementFactoryHelper.unregisterInternalMBeans(this.server);
/*    */   }
/*    */   
/*    */   public void postDeregister() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/HotspotInternal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */