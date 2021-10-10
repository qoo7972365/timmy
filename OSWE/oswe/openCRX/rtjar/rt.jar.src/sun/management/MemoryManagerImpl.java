/*    */ package sun.management;
/*    */ 
/*    */ import java.lang.management.MemoryManagerMXBean;
/*    */ import java.lang.management.MemoryPoolMXBean;
/*    */ import javax.management.MBeanNotificationInfo;
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
/*    */ class MemoryManagerImpl
/*    */   extends NotificationEmitterSupport
/*    */   implements MemoryManagerMXBean
/*    */ {
/*    */   private final String name;
/*    */   private final boolean isValid;
/*    */   private MemoryPoolMXBean[] pools;
/*    */   private MBeanNotificationInfo[] notifInfo;
/*    */   
/*    */   MemoryManagerImpl(String paramString) {
/* 81 */     this.notifInfo = null; this.name = paramString; this.isValid = true; this.pools = null;
/*    */   }
/* 83 */   public String getName() { return this.name; } public boolean isValid() { return this.isValid; } public MBeanNotificationInfo[] getNotificationInfo() { synchronized (this) {
/* 84 */       if (this.notifInfo == null) {
/* 85 */         this.notifInfo = new MBeanNotificationInfo[0];
/*    */       }
/*    */     } 
/* 88 */     return this.notifInfo; }
/*    */   public String[] getMemoryPoolNames() { MemoryPoolMXBean[] arrayOfMemoryPoolMXBean = getMemoryPools(); String[] arrayOfString = new String[arrayOfMemoryPoolMXBean.length]; for (byte b = 0; b < arrayOfMemoryPoolMXBean.length; b++)
/*    */       arrayOfString[b] = arrayOfMemoryPoolMXBean[b].getName();  return arrayOfString; }
/*    */   synchronized MemoryPoolMXBean[] getMemoryPools() { if (this.pools == null)
/* 92 */       this.pools = getMemoryPools0();  return this.pools; } private native MemoryPoolMXBean[] getMemoryPools0(); public ObjectName getObjectName() { return Util.newObjectName("java.lang:type=MemoryManager", getName()); }
/*    */ 
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MemoryManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */