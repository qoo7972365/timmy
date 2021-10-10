/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.GcInfo;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.MemoryUsage;
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
/*     */ public class GcInfoBuilder
/*     */ {
/*     */   private final GarbageCollectorMXBean gc;
/*     */   private final String[] poolNames;
/*     */   private String[] allItemNames;
/*     */   private CompositeType gcInfoCompositeType;
/*     */   private final int gcExtItemCount;
/*     */   private final String[] gcExtItemNames;
/*     */   private final String[] gcExtItemDescs;
/*     */   private final char[] gcExtItemTypes;
/*     */   
/*     */   GcInfoBuilder(GarbageCollectorMXBean paramGarbageCollectorMXBean, String[] paramArrayOfString) {
/*  60 */     this.gc = paramGarbageCollectorMXBean;
/*  61 */     this.poolNames = paramArrayOfString;
/*  62 */     this.gcExtItemCount = getNumGcExtAttributes(paramGarbageCollectorMXBean);
/*  63 */     this.gcExtItemNames = new String[this.gcExtItemCount];
/*  64 */     this.gcExtItemDescs = new String[this.gcExtItemCount];
/*  65 */     this.gcExtItemTypes = new char[this.gcExtItemCount];
/*     */ 
/*     */     
/*  68 */     fillGcAttributeInfo(paramGarbageCollectorMXBean, this.gcExtItemCount, this.gcExtItemNames, this.gcExtItemTypes, this.gcExtItemDescs);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.gcInfoCompositeType = null;
/*     */   }
/*     */   
/*     */   GcInfo getLastGcInfo() {
/*  77 */     MemoryUsage[] arrayOfMemoryUsage1 = new MemoryUsage[this.poolNames.length];
/*  78 */     MemoryUsage[] arrayOfMemoryUsage2 = new MemoryUsage[this.poolNames.length];
/*  79 */     Object[] arrayOfObject = new Object[this.gcExtItemCount];
/*     */     
/*  81 */     return getLastGcInfo0(this.gc, this.gcExtItemCount, arrayOfObject, this.gcExtItemTypes, arrayOfMemoryUsage1, arrayOfMemoryUsage2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getPoolNames() {
/*  86 */     return this.poolNames;
/*     */   }
/*     */   
/*     */   int getGcExtItemCount() {
/*  90 */     return this.gcExtItemCount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized CompositeType getGcInfoCompositeType() {
/*  96 */     if (this.gcInfoCompositeType != null) {
/*  97 */       return this.gcInfoCompositeType;
/*     */     }
/*     */     
/* 100 */     String[] arrayOfString1 = GcInfoCompositeData.getBaseGcInfoItemNames();
/* 101 */     OpenType[] arrayOfOpenType1 = GcInfoCompositeData.getBaseGcInfoItemTypes();
/* 102 */     int i = arrayOfString1.length;
/*     */     
/* 104 */     int j = i + this.gcExtItemCount;
/* 105 */     this.allItemNames = new String[j];
/* 106 */     String[] arrayOfString2 = new String[j];
/* 107 */     OpenType[] arrayOfOpenType2 = new OpenType[j];
/*     */     
/* 109 */     System.arraycopy(arrayOfString1, 0, this.allItemNames, 0, i);
/* 110 */     System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
/* 111 */     System.arraycopy(arrayOfOpenType1, 0, arrayOfOpenType2, 0, i);
/*     */ 
/*     */     
/* 114 */     if (this.gcExtItemCount > 0) {
/* 115 */       fillGcAttributeInfo(this.gc, this.gcExtItemCount, this.gcExtItemNames, this.gcExtItemTypes, this.gcExtItemDescs);
/*     */       
/* 117 */       System.arraycopy(this.gcExtItemNames, 0, this.allItemNames, i, this.gcExtItemCount);
/*     */       
/* 119 */       System.arraycopy(this.gcExtItemDescs, 0, arrayOfString2, i, this.gcExtItemCount); int k;
/*     */       byte b;
/* 121 */       for (k = i, b = 0; b < this.gcExtItemCount; k++, b++) {
/* 122 */         switch (this.gcExtItemTypes[b]) {
/*     */           case 'Z':
/* 124 */             arrayOfOpenType2[k] = SimpleType.BOOLEAN;
/*     */             break;
/*     */           case 'B':
/* 127 */             arrayOfOpenType2[k] = SimpleType.BYTE;
/*     */             break;
/*     */           case 'C':
/* 130 */             arrayOfOpenType2[k] = SimpleType.CHARACTER;
/*     */             break;
/*     */           case 'S':
/* 133 */             arrayOfOpenType2[k] = SimpleType.SHORT;
/*     */             break;
/*     */           case 'I':
/* 136 */             arrayOfOpenType2[k] = SimpleType.INTEGER;
/*     */             break;
/*     */           case 'J':
/* 139 */             arrayOfOpenType2[k] = SimpleType.LONG;
/*     */             break;
/*     */           case 'F':
/* 142 */             arrayOfOpenType2[k] = SimpleType.FLOAT;
/*     */             break;
/*     */           case 'D':
/* 145 */             arrayOfOpenType2[k] = SimpleType.DOUBLE;
/*     */             break;
/*     */           default:
/* 148 */             throw new AssertionError("Unsupported type [" + this.gcExtItemTypes[k] + "]");
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 154 */     CompositeType compositeType = null;
/*     */     
/*     */     try {
/* 157 */       String str = "sun.management." + this.gc.getName() + ".GcInfoCompositeType";
/*     */ 
/*     */ 
/*     */       
/* 161 */       compositeType = new CompositeType(str, "CompositeType for GC info for " + this.gc.getName(), this.allItemNames, arrayOfString2, (OpenType<?>[])arrayOfOpenType2);
/*     */ 
/*     */     
/*     */     }
/* 165 */     catch (OpenDataException openDataException) {
/*     */       
/* 167 */       throw Util.newException(openDataException);
/*     */     } 
/* 169 */     this.gcInfoCompositeType = compositeType;
/*     */     
/* 171 */     return this.gcInfoCompositeType;
/*     */   }
/*     */   
/*     */   synchronized String[] getItemNames() {
/* 175 */     if (this.allItemNames == null)
/*     */     {
/* 177 */       getGcInfoCompositeType();
/*     */     }
/* 179 */     return this.allItemNames;
/*     */   }
/*     */   
/*     */   private native int getNumGcExtAttributes(GarbageCollectorMXBean paramGarbageCollectorMXBean);
/*     */   
/*     */   private native void fillGcAttributeInfo(GarbageCollectorMXBean paramGarbageCollectorMXBean, int paramInt, String[] paramArrayOfString1, char[] paramArrayOfchar, String[] paramArrayOfString2);
/*     */   
/*     */   private native GcInfo getLastGcInfo0(GarbageCollectorMXBean paramGarbageCollectorMXBean, int paramInt, Object[] paramArrayOfObject, char[] paramArrayOfchar, MemoryUsage[] paramArrayOfMemoryUsage1, MemoryUsage[] paramArrayOfMemoryUsage2);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/GcInfoBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */