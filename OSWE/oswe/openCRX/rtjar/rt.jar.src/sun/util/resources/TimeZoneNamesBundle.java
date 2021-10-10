/*     */ package sun.util.resources;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TimeZoneNamesBundle
/*     */   extends OpenListResourceBundle
/*     */ {
/*     */   public Object handleGetObject(String paramString) {
/*  82 */     String[] arrayOfString1 = (String[])super.handleGetObject(paramString);
/*  83 */     if (Objects.isNull(arrayOfString1)) {
/*  84 */       return null;
/*     */     }
/*  86 */     int i = arrayOfString1.length;
/*  87 */     String[] arrayOfString2 = new String[7];
/*  88 */     arrayOfString2[0] = paramString;
/*  89 */     System.arraycopy(arrayOfString1, 0, arrayOfString2, 1, i);
/*  90 */     return arrayOfString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <K, V> Map<K, V> createMap(int paramInt) {
/*  98 */     return new LinkedHashMap<>(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <E> Set<E> createSet() {
/* 108 */     return new LinkedHashSet<>();
/*     */   }
/*     */   
/*     */   protected abstract Object[][] getContents();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/resources/TimeZoneNamesBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */