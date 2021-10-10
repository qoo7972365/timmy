/*     */ package sun.management;
/*     */ 
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.util.Set;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
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
/*     */ public class MonitorInfoCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final MonitorInfo lock;
/*     */   private static final CompositeType monitorInfoCompositeType;
/*     */   private static final String[] monitorInfoItemNames;
/*     */   private static final String CLASS_NAME = "className";
/*     */   private static final String IDENTITY_HASH_CODE = "identityHashCode";
/*     */   private static final String LOCKED_STACK_FRAME = "lockedStackFrame";
/*     */   private static final String LOCKED_STACK_DEPTH = "lockedStackDepth";
/*     */   private static final long serialVersionUID = -5825215591822908529L;
/*     */   
/*     */   private MonitorInfoCompositeData(MonitorInfo paramMonitorInfo) {
/*  44 */     this.lock = paramMonitorInfo;
/*     */   }
/*     */   
/*     */   public MonitorInfo getMonitorInfo() {
/*  48 */     return this.lock;
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(MonitorInfo paramMonitorInfo) {
/*  52 */     MonitorInfoCompositeData monitorInfoCompositeData = new MonitorInfoCompositeData(paramMonitorInfo);
/*  53 */     return monitorInfoCompositeData.getCompositeData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/*  60 */     int i = monitorInfoItemNames.length;
/*  61 */     Object[] arrayOfObject = new Object[i];
/*  62 */     CompositeData compositeData = LockInfoCompositeData.toCompositeData(this.lock);
/*     */     
/*  64 */     for (byte b = 0; b < i; b++) {
/*  65 */       String str = monitorInfoItemNames[b];
/*  66 */       if (str.equals("lockedStackFrame")) {
/*  67 */         StackTraceElement stackTraceElement = this.lock.getLockedStackFrame();
/*  68 */         arrayOfObject[b] = (stackTraceElement != null) ? 
/*  69 */           StackTraceElementCompositeData.toCompositeData(stackTraceElement) : null;
/*     */       }
/*  71 */       else if (str.equals("lockedStackDepth")) {
/*  72 */         arrayOfObject[b] = new Integer(this.lock.getLockedStackDepth());
/*     */       } else {
/*  74 */         arrayOfObject[b] = compositeData.get(str);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/*  79 */       return new CompositeDataSupport(monitorInfoCompositeType, monitorInfoItemNames, arrayOfObject);
/*     */     
/*     */     }
/*  82 */     catch (OpenDataException openDataException) {
/*     */       
/*  84 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  93 */       monitorInfoCompositeType = (CompositeType)MappedMXBeanType.toOpenType(MonitorInfo.class);
/*  94 */       Set<String> set = monitorInfoCompositeType.keySet();
/*  95 */       monitorInfoItemNames = set.<String>toArray(new String[0]);
/*  96 */     } catch (OpenDataException openDataException) {
/*     */       
/*  98 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */   
/*     */   static CompositeType getMonitorInfoCompositeType() {
/* 103 */     return monitorInfoCompositeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClassName(CompositeData paramCompositeData) {
/* 112 */     return getString(paramCompositeData, "className");
/*     */   }
/*     */   
/*     */   public static int getIdentityHashCode(CompositeData paramCompositeData) {
/* 116 */     return getInt(paramCompositeData, "identityHashCode");
/*     */   }
/*     */   
/*     */   public static StackTraceElement getLockedStackFrame(CompositeData paramCompositeData) {
/* 120 */     CompositeData compositeData = (CompositeData)paramCompositeData.get("lockedStackFrame");
/* 121 */     if (compositeData != null) {
/* 122 */       return StackTraceElementCompositeData.from(compositeData);
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getLockedStackDepth(CompositeData paramCompositeData) {
/* 129 */     return getInt(paramCompositeData, "lockedStackDepth");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCompositeData(CompositeData paramCompositeData) {
/* 137 */     if (paramCompositeData == null) {
/* 138 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 141 */     if (!isTypeMatched(monitorInfoCompositeType, paramCompositeData.getCompositeType()))
/* 142 */       throw new IllegalArgumentException("Unexpected composite type for MonitorInfo"); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/MonitorInfoCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */