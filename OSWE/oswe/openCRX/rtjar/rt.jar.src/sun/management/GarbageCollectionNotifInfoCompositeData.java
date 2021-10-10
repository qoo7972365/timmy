/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.GarbageCollectionNotificationInfo;
/*     */ import com.sun.management.GcInfo;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeDataSupport;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenDataException;
/*     */ import javax.management.openmbean.OpenType;
/*     */ import javax.management.openmbean.SimpleType;
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
/*     */ public class GarbageCollectionNotifInfoCompositeData
/*     */   extends LazyCompositeData
/*     */ {
/*     */   private final GarbageCollectionNotificationInfo gcNotifInfo;
/*     */   private static final String GC_NAME = "gcName";
/*     */   private static final String GC_ACTION = "gcAction";
/*     */   private static final String GC_CAUSE = "gcCause";
/*     */   private static final String GC_INFO = "gcInfo";
/*     */   
/*     */   public GarbageCollectionNotifInfoCompositeData(GarbageCollectionNotificationInfo paramGarbageCollectionNotificationInfo) {
/*  51 */     this.gcNotifInfo = paramGarbageCollectionNotificationInfo;
/*     */   }
/*     */   
/*     */   public GarbageCollectionNotificationInfo getGarbageCollectionNotifInfo() {
/*  55 */     return this.gcNotifInfo;
/*     */   }
/*     */   
/*     */   public static CompositeData toCompositeData(GarbageCollectionNotificationInfo paramGarbageCollectionNotificationInfo) {
/*  59 */     GarbageCollectionNotifInfoCompositeData garbageCollectionNotifInfoCompositeData = new GarbageCollectionNotifInfoCompositeData(paramGarbageCollectionNotificationInfo);
/*     */     
/*  61 */     return garbageCollectionNotifInfoCompositeData.getCompositeData();
/*     */   }
/*     */   
/*     */   private CompositeType getCompositeTypeByBuilder() {
/*  65 */     GcInfoBuilder gcInfoBuilder = AccessController.<GcInfoBuilder>doPrivileged(new PrivilegedAction<GcInfoBuilder>() {
/*     */           public GcInfoBuilder run() {
/*     */             try {
/*  68 */               Class<?> clazz = Class.forName("com.sun.management.GcInfo");
/*  69 */               Field field = clazz.getDeclaredField("builder");
/*  70 */               field.setAccessible(true);
/*  71 */               return (GcInfoBuilder)field.get(GarbageCollectionNotifInfoCompositeData.this.gcNotifInfo.getGcInfo());
/*  72 */             } catch (ClassNotFoundException|NoSuchFieldException|IllegalAccessException classNotFoundException) {
/*  73 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*  77 */     CompositeType compositeType = null;
/*  78 */     synchronized (compositeTypeByBuilder) {
/*  79 */       compositeType = compositeTypeByBuilder.get(gcInfoBuilder);
/*  80 */       if (compositeType == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  85 */         OpenType[] arrayOfOpenType = { SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, gcInfoBuilder.getGcInfoCompositeType() };
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  90 */           compositeType = new CompositeType("sun.management.GarbageCollectionNotifInfoCompositeType", "CompositeType for GC notification info", gcNotifInfoItemNames, gcNotifInfoItemNames, (OpenType<?>[])arrayOfOpenType);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  95 */           compositeTypeByBuilder.put(gcInfoBuilder, compositeType);
/*  96 */         } catch (OpenDataException openDataException) {
/*     */           
/*  98 */           throw Util.newException(openDataException);
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     return compositeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CompositeData getCompositeData() {
/* 113 */     Object[] arrayOfObject = { this.gcNotifInfo.getGcName(), this.gcNotifInfo.getGcAction(), this.gcNotifInfo.getGcCause(), GcInfoCompositeData.toCompositeData(this.gcNotifInfo.getGcInfo()) };
/*     */ 
/*     */     
/* 116 */     CompositeType compositeType = getCompositeTypeByBuilder();
/*     */     
/*     */     try {
/* 119 */       return new CompositeDataSupport(compositeType, gcNotifInfoItemNames, arrayOfObject);
/*     */     
/*     */     }
/* 122 */     catch (OpenDataException openDataException) {
/*     */       
/* 124 */       throw new AssertionError(openDataException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   private static final String[] gcNotifInfoItemNames = new String[] { "gcName", "gcAction", "gcCause", "gcInfo" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   private static HashMap<GcInfoBuilder, CompositeType> compositeTypeByBuilder = new HashMap<>();
/*     */ 
/*     */   
/*     */   public static String getGcName(CompositeData paramCompositeData) {
/* 143 */     String str = getString(paramCompositeData, "gcName");
/* 144 */     if (str == null) {
/* 145 */       throw new IllegalArgumentException("Invalid composite data: Attribute gcName has null value");
/*     */     }
/*     */     
/* 148 */     return str;
/*     */   }
/*     */   
/*     */   public static String getGcAction(CompositeData paramCompositeData) {
/* 152 */     String str = getString(paramCompositeData, "gcAction");
/* 153 */     if (str == null) {
/* 154 */       throw new IllegalArgumentException("Invalid composite data: Attribute gcAction has null value");
/*     */     }
/*     */     
/* 157 */     return str;
/*     */   }
/*     */   
/*     */   public static String getGcCause(CompositeData paramCompositeData) {
/* 161 */     String str = getString(paramCompositeData, "gcCause");
/* 162 */     if (str == null) {
/* 163 */       throw new IllegalArgumentException("Invalid composite data: Attribute gcCause has null value");
/*     */     }
/*     */     
/* 166 */     return str;
/*     */   }
/*     */   
/*     */   public static GcInfo getGcInfo(CompositeData paramCompositeData) {
/* 170 */     CompositeData compositeData = (CompositeData)paramCompositeData.get("gcInfo");
/* 171 */     return GcInfo.from(compositeData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateCompositeData(CompositeData paramCompositeData) {
/* 179 */     if (paramCompositeData == null) {
/* 180 */       throw new NullPointerException("Null CompositeData");
/*     */     }
/*     */     
/* 183 */     if (!isTypeMatched(getBaseGcNotifInfoCompositeType(), paramCompositeData.getCompositeType())) {
/* 184 */       throw new IllegalArgumentException("Unexpected composite type for GarbageCollectionNotificationInfo");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 190 */   private static CompositeType baseGcNotifInfoCompositeType = null;
/*     */   private static synchronized CompositeType getBaseGcNotifInfoCompositeType() {
/* 192 */     if (baseGcNotifInfoCompositeType == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 198 */         OpenType[] arrayOfOpenType = { SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, GcInfoCompositeData.getBaseGcInfoCompositeType() };
/*     */         
/* 200 */         baseGcNotifInfoCompositeType = new CompositeType("sun.management.BaseGarbageCollectionNotifInfoCompositeType", "CompositeType for Base GarbageCollectionNotificationInfo", gcNotifInfoItemNames, gcNotifInfoItemNames, (OpenType<?>[])arrayOfOpenType);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 206 */       catch (OpenDataException openDataException) {
/*     */         
/* 208 */         throw Util.newException(openDataException);
/*     */       } 
/*     */     }
/* 211 */     return baseGcNotifInfoCompositeType;
/*     */   }
/*     */   
/*     */   private static final long serialVersionUID = -1805123446483771292L;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/GarbageCollectionNotifInfoCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */