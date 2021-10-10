/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.management.ObjectName;
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
/*     */ class RuntimeImpl
/*     */   implements RuntimeMXBean
/*     */ {
/*     */   private final VMManagement jvm;
/*     */   private final long vmStartupTime;
/*     */   
/*     */   RuntimeImpl(VMManagement paramVMManagement) {
/*  54 */     this.jvm = paramVMManagement;
/*  55 */     this.vmStartupTime = this.jvm.getStartupTime();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  59 */     return this.jvm.getVmId();
/*     */   }
/*     */   
/*     */   public String getManagementSpecVersion() {
/*  63 */     return this.jvm.getManagementVersion();
/*     */   }
/*     */   
/*     */   public String getVmName() {
/*  67 */     return this.jvm.getVmName();
/*     */   }
/*     */   
/*     */   public String getVmVendor() {
/*  71 */     return this.jvm.getVmVendor();
/*     */   }
/*     */   
/*     */   public String getVmVersion() {
/*  75 */     return this.jvm.getVmVersion();
/*     */   }
/*     */   
/*     */   public String getSpecName() {
/*  79 */     return this.jvm.getVmSpecName();
/*     */   }
/*     */   
/*     */   public String getSpecVendor() {
/*  83 */     return this.jvm.getVmSpecVendor();
/*     */   }
/*     */   
/*     */   public String getSpecVersion() {
/*  87 */     return this.jvm.getVmSpecVersion();
/*     */   }
/*     */   
/*     */   public String getClassPath() {
/*  91 */     return this.jvm.getClassPath();
/*     */   }
/*     */   
/*     */   public String getLibraryPath() {
/*  95 */     return this.jvm.getLibraryPath();
/*     */   }
/*     */   
/*     */   public String getBootClassPath() {
/*  99 */     if (!isBootClassPathSupported()) {
/* 100 */       throw new UnsupportedOperationException("Boot class path mechanism is not supported");
/*     */     }
/*     */     
/* 103 */     Util.checkMonitorAccess();
/* 104 */     return this.jvm.getBootClassPath();
/*     */   }
/*     */   
/*     */   public List<String> getInputArguments() {
/* 108 */     Util.checkMonitorAccess();
/* 109 */     return this.jvm.getVmArguments();
/*     */   }
/*     */   
/*     */   public long getUptime() {
/* 113 */     return this.jvm.getUptime();
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 117 */     return this.vmStartupTime;
/*     */   }
/*     */   
/*     */   public boolean isBootClassPathSupported() {
/* 121 */     return this.jvm.isBootClassPathSupported();
/*     */   }
/*     */   
/*     */   public Map<String, String> getSystemProperties() {
/* 125 */     Properties properties = System.getProperties();
/* 126 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     Set<String> set = properties.stringPropertyNames();
/* 132 */     for (String str1 : set) {
/* 133 */       String str2 = properties.getProperty(str1);
/* 134 */       hashMap.put(str1, str2);
/*     */     } 
/*     */     
/* 137 */     return (Map)hashMap;
/*     */   }
/*     */   
/*     */   public ObjectName getObjectName() {
/* 141 */     return Util.newObjectName("java.lang:type=Runtime");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/RuntimeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */