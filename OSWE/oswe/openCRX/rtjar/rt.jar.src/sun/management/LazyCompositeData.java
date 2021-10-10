/*     */ package sun.management;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.management.openmbean.ArrayType;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.OpenType;
/*     */ import javax.management.openmbean.TabularType;
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
/*     */ public abstract class LazyCompositeData
/*     */   implements CompositeData, Serializable
/*     */ {
/*     */   private CompositeData compositeData;
/*     */   private static final long serialVersionUID = -2190411934472666714L;
/*     */   
/*     */   public boolean containsKey(String paramString) {
/*  54 */     return compositeData().containsKey(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/*  59 */     return compositeData().containsValue(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  64 */     return compositeData().equals(paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object get(String paramString) {
/*  69 */     return compositeData().get(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] getAll(String[] paramArrayOfString) {
/*  74 */     return compositeData().getAll(paramArrayOfString);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompositeType getCompositeType() {
/*  79 */     return compositeData().getCompositeType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  84 */     return compositeData().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return compositeData().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<?> values() {
/*  95 */     return compositeData().values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized CompositeData compositeData() {
/* 102 */     if (this.compositeData != null)
/* 103 */       return this.compositeData; 
/* 104 */     this.compositeData = getCompositeData();
/* 105 */     return this.compositeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object writeReplace() throws ObjectStreamException {
/* 115 */     return compositeData();
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
/*     */   static String getString(CompositeData paramCompositeData, String paramString) {
/* 129 */     if (paramCompositeData == null) {
/* 130 */       throw new IllegalArgumentException("Null CompositeData");
/*     */     }
/* 132 */     return (String)paramCompositeData.get(paramString);
/*     */   }
/*     */   
/*     */   static boolean getBoolean(CompositeData paramCompositeData, String paramString) {
/* 136 */     if (paramCompositeData == null) {
/* 137 */       throw new IllegalArgumentException("Null CompositeData");
/*     */     }
/* 139 */     return ((Boolean)paramCompositeData.get(paramString)).booleanValue();
/*     */   }
/*     */   
/*     */   static long getLong(CompositeData paramCompositeData, String paramString) {
/* 143 */     if (paramCompositeData == null) {
/* 144 */       throw new IllegalArgumentException("Null CompositeData");
/*     */     }
/* 146 */     return ((Long)paramCompositeData.get(paramString)).longValue();
/*     */   }
/*     */   
/*     */   static int getInt(CompositeData paramCompositeData, String paramString) {
/* 150 */     if (paramCompositeData == null) {
/* 151 */       throw new IllegalArgumentException("Null CompositeData");
/*     */     }
/* 153 */     return ((Integer)paramCompositeData.get(paramString)).intValue();
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
/*     */   protected static boolean isTypeMatched(CompositeType paramCompositeType1, CompositeType paramCompositeType2) {
/* 166 */     if (paramCompositeType1 == paramCompositeType2) return true;
/*     */ 
/*     */ 
/*     */     
/* 170 */     Set<String> set = paramCompositeType1.keySet();
/*     */ 
/*     */     
/* 173 */     if (!paramCompositeType2.keySet().containsAll(set)) {
/* 174 */       return false;
/*     */     }
/* 176 */     return set.stream().allMatch(paramString -> isTypeMatched(paramCompositeType1.getType(paramString), paramCompositeType2.getType(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isTypeMatched(TabularType paramTabularType1, TabularType paramTabularType2) {
/* 182 */     if (paramTabularType1 == paramTabularType2) return true;
/*     */     
/* 184 */     List<String> list1 = paramTabularType1.getIndexNames();
/* 185 */     List<String> list2 = paramTabularType2.getIndexNames();
/*     */ 
/*     */     
/* 188 */     if (!list1.equals(list2)) {
/* 189 */       return false;
/*     */     }
/* 191 */     return isTypeMatched(paramTabularType1.getRowType(), paramTabularType2.getRowType());
/*     */   }
/*     */   
/*     */   protected static boolean isTypeMatched(ArrayType<?> paramArrayType1, ArrayType<?> paramArrayType2) {
/* 195 */     if (paramArrayType1 == paramArrayType2) return true;
/*     */     
/* 197 */     int i = paramArrayType1.getDimension();
/* 198 */     int j = paramArrayType2.getDimension();
/*     */ 
/*     */     
/* 201 */     if (i != j) {
/* 202 */       return false;
/*     */     }
/* 204 */     return isTypeMatched(paramArrayType1.getElementOpenType(), paramArrayType2.getElementOpenType());
/*     */   }
/*     */   
/*     */   private static boolean isTypeMatched(OpenType<?> paramOpenType1, OpenType<?> paramOpenType2) {
/* 208 */     if (paramOpenType1 instanceof CompositeType) {
/* 209 */       if (!(paramOpenType2 instanceof CompositeType))
/* 210 */         return false; 
/* 211 */       if (!isTypeMatched((CompositeType)paramOpenType1, (CompositeType)paramOpenType2))
/* 212 */         return false; 
/* 213 */     } else if (paramOpenType1 instanceof TabularType) {
/* 214 */       if (!(paramOpenType2 instanceof TabularType))
/* 215 */         return false; 
/* 216 */       if (!isTypeMatched((TabularType)paramOpenType1, (TabularType)paramOpenType2))
/* 217 */         return false; 
/* 218 */     } else if (paramOpenType1 instanceof ArrayType) {
/* 219 */       if (!(paramOpenType2 instanceof ArrayType))
/* 220 */         return false; 
/* 221 */       if (!isTypeMatched((ArrayType)paramOpenType1, (ArrayType)paramOpenType2)) {
/* 222 */         return false;
/*     */       }
/* 224 */     } else if (!paramOpenType1.equals(paramOpenType2)) {
/* 225 */       return false;
/*     */     } 
/* 227 */     return true;
/*     */   }
/*     */   
/*     */   protected abstract CompositeData getCompositeData();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/LazyCompositeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */