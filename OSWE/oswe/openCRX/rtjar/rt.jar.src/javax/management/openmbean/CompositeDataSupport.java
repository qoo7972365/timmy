/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
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
/*     */ public class CompositeDataSupport
/*     */   implements CompositeData, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 8003518976613702244L;
/*     */   private final SortedMap<String, Object> contents;
/*     */   private final CompositeType compositeType;
/*     */   
/*     */   public CompositeDataSupport(CompositeType paramCompositeType, String[] paramArrayOfString, Object[] paramArrayOfObject) throws OpenDataException {
/* 118 */     this(makeMap(paramArrayOfString, paramArrayOfObject), paramCompositeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SortedMap<String, Object> makeMap(String[] paramArrayOfString, Object[] paramArrayOfObject) throws OpenDataException {
/* 125 */     if (paramArrayOfString == null || paramArrayOfObject == null)
/* 126 */       throw new IllegalArgumentException("Null itemNames or itemValues"); 
/* 127 */     if (paramArrayOfString.length == 0 || paramArrayOfObject.length == 0)
/* 128 */       throw new IllegalArgumentException("Empty itemNames or itemValues"); 
/* 129 */     if (paramArrayOfString.length != paramArrayOfObject.length) {
/* 130 */       throw new IllegalArgumentException("Different lengths: itemNames[" + paramArrayOfString.length + "], itemValues[" + paramArrayOfObject.length + "]");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 135 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 136 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 137 */       String str = paramArrayOfString[b];
/* 138 */       if (str == null || str.equals(""))
/* 139 */         throw new IllegalArgumentException("Null or empty item name"); 
/* 140 */       if (treeMap.containsKey(str))
/* 141 */         throw new OpenDataException("Duplicate item name " + str); 
/* 142 */       treeMap.put(paramArrayOfString[b], paramArrayOfObject[b]);
/*     */     } 
/*     */     
/* 145 */     return (SortedMap)treeMap;
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
/*     */   public CompositeDataSupport(CompositeType paramCompositeType, Map<String, ?> paramMap) throws OpenDataException {
/* 176 */     this(makeMap(paramMap), paramCompositeType);
/*     */   }
/*     */   
/*     */   private static SortedMap<String, Object> makeMap(Map<String, ?> paramMap) {
/* 180 */     if (paramMap == null || paramMap.isEmpty()) {
/* 181 */       throw new IllegalArgumentException("Null or empty items map");
/*     */     }
/* 183 */     TreeMap<Object, Object> treeMap = new TreeMap<>();
/* 184 */     for (String str : paramMap.keySet()) {
/* 185 */       if (str == null || str.equals(""))
/* 186 */         throw new IllegalArgumentException("Null or empty item name"); 
/* 187 */       if (!(str instanceof String)) {
/* 188 */         throw new ArrayStoreException("Item name is not string: " + str);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 193 */       treeMap.put(str, paramMap.get(str));
/*     */     } 
/* 195 */     return (SortedMap)treeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompositeDataSupport(SortedMap<String, Object> paramSortedMap, CompositeType paramCompositeType) throws OpenDataException {
/* 204 */     if (paramCompositeType == null) {
/* 205 */       throw new IllegalArgumentException("Argument compositeType cannot be null.");
/*     */     }
/*     */ 
/*     */     
/* 209 */     Set<String> set1 = paramCompositeType.keySet();
/* 210 */     Set<String> set2 = paramSortedMap.keySet();
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (!set1.equals(set2)) {
/* 215 */       TreeSet<String> treeSet1 = new TreeSet<>(set1);
/* 216 */       treeSet1.removeAll(set2);
/* 217 */       TreeSet<String> treeSet2 = new TreeSet<>(set2);
/* 218 */       treeSet2.removeAll(set1);
/* 219 */       if (!treeSet1.isEmpty() || !treeSet2.isEmpty()) {
/* 220 */         throw new OpenDataException("Item names do not match CompositeType: names in items but not in CompositeType: " + treeSet2 + "; names in CompositeType but not in items: " + treeSet1);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     for (String str : set1) {
/* 230 */       Object object = paramSortedMap.get(str);
/* 231 */       if (object != null) {
/* 232 */         OpenType<?> openType = paramCompositeType.getType(str);
/* 233 */         if (!openType.isValue(object)) {
/* 234 */           throw new OpenDataException("Argument value of wrong type for item " + str + ": value " + object + ", type " + openType);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     this.compositeType = paramCompositeType;
/* 244 */     this.contents = paramSortedMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeType getCompositeType() {
/* 252 */     return this.compositeType;
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
/*     */   public Object get(String paramString) {
/* 265 */     if (paramString == null || paramString.trim().equals("")) {
/* 266 */       throw new IllegalArgumentException("Argument key cannot be a null or empty String.");
/*     */     }
/* 268 */     if (!this.contents.containsKey(paramString.trim())) {
/* 269 */       throw new InvalidKeyException("Argument key=\"" + paramString.trim() + "\" is not an existing item name for this CompositeData instance.");
/*     */     }
/* 271 */     return this.contents.get(paramString.trim());
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
/*     */   public Object[] getAll(String[] paramArrayOfString) {
/* 286 */     if (paramArrayOfString == null || paramArrayOfString.length == 0) {
/* 287 */       return new Object[0];
/*     */     }
/* 289 */     Object[] arrayOfObject = new Object[paramArrayOfString.length];
/* 290 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 291 */       arrayOfObject[b] = get(paramArrayOfString[b]);
/*     */     }
/* 293 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(String paramString) {
/* 303 */     if (paramString == null || paramString.trim().equals("")) {
/* 304 */       return false;
/*     */     }
/* 306 */     return this.contents.containsKey(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object paramObject) {
/* 316 */     return this.contents.containsValue(paramObject);
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
/*     */   public Collection<?> values() {
/* 328 */     return Collections.unmodifiableCollection(this.contents.values());
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
/*     */   public boolean equals(Object paramObject) {
/* 360 */     if (this == paramObject) {
/* 361 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 365 */     if (!(paramObject instanceof CompositeData)) {
/* 366 */       return false;
/*     */     }
/*     */     
/* 369 */     CompositeData compositeData = (CompositeData)paramObject;
/*     */ 
/*     */     
/* 372 */     if (!getCompositeType().equals(compositeData.getCompositeType())) {
/* 373 */       return false;
/*     */     }
/*     */     
/* 376 */     if (this.contents.size() != compositeData.values().size()) {
/* 377 */       return false;
/*     */     }
/*     */     
/* 380 */     for (Map.Entry<String, Object> entry : this.contents.entrySet()) {
/* 381 */       Object object1 = entry.getValue();
/* 382 */       Object object2 = compositeData.get((String)entry.getKey());
/*     */       
/* 384 */       if (object1 == object2)
/*     */         continue; 
/* 386 */       if (object1 == null) {
/* 387 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 391 */       boolean bool = object1.getClass().isArray() ? Arrays.deepEquals(new Object[] { object1 }, new Object[] { object2 }) : object1.equals(object2);
/*     */       
/* 393 */       if (!bool) {
/* 394 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 399 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 425 */     int i = this.compositeType.hashCode();
/*     */     
/* 427 */     for (Object[] arrayOfObject : this.contents.values()) {
/* 428 */       if (arrayOfObject instanceof Object[]) {
/* 429 */         i += Arrays.deepHashCode(arrayOfObject); continue;
/* 430 */       }  if (arrayOfObject instanceof byte[]) {
/* 431 */         i += Arrays.hashCode((byte[])arrayOfObject); continue;
/* 432 */       }  if (arrayOfObject instanceof short[]) {
/* 433 */         i += Arrays.hashCode((short[])arrayOfObject); continue;
/* 434 */       }  if (arrayOfObject instanceof int[]) {
/* 435 */         i += Arrays.hashCode((int[])arrayOfObject); continue;
/* 436 */       }  if (arrayOfObject instanceof long[]) {
/* 437 */         i += Arrays.hashCode((long[])arrayOfObject); continue;
/* 438 */       }  if (arrayOfObject instanceof char[]) {
/* 439 */         i += Arrays.hashCode((char[])arrayOfObject); continue;
/* 440 */       }  if (arrayOfObject instanceof float[]) {
/* 441 */         i += Arrays.hashCode((float[])arrayOfObject); continue;
/* 442 */       }  if (arrayOfObject instanceof double[]) {
/* 443 */         i += Arrays.hashCode((double[])arrayOfObject); continue;
/* 444 */       }  if (arrayOfObject instanceof boolean[]) {
/* 445 */         i += Arrays.hashCode((boolean[])arrayOfObject); continue;
/* 446 */       }  if (arrayOfObject != null) {
/* 447 */         i += arrayOfObject.hashCode();
/*     */       }
/*     */     } 
/* 450 */     return i;
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
/*     */   public String toString() {
/* 464 */     return 
/* 465 */       getClass().getName() + "(compositeType=" + 
/* 466 */       this.compositeType
/* 467 */       .toString() + ",contents=" + 
/*     */       
/* 469 */       contentString() + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String contentString() {
/* 475 */     StringBuilder stringBuilder = new StringBuilder("{");
/* 476 */     String str = "";
/* 477 */     for (Map.Entry<String, Object> entry : this.contents.entrySet()) {
/* 478 */       stringBuilder.append(str).append((String)entry.getKey()).append("=");
/* 479 */       String str1 = Arrays.deepToString(new Object[] { entry.getValue() });
/* 480 */       stringBuilder.append(str1.substring(1, str1.length() - 1));
/* 481 */       str = ", ";
/*     */     } 
/* 483 */     stringBuilder.append("}");
/* 484 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/CompositeDataSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */