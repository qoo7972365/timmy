/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeType
/*     */   extends OpenType<CompositeData>
/*     */ {
/*     */   static final long serialVersionUID = -5366242454346948798L;
/*     */   private TreeMap<String, String> nameToDescription;
/*     */   private TreeMap<String, OpenType<?>> nameToType;
/*  65 */   private transient Integer myHashCode = null;
/*  66 */   private transient String myToString = null;
/*  67 */   private transient Set<String> myNamesSet = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeType(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, OpenType<?>[] paramArrayOfOpenType) throws OpenDataException {
/* 126 */     super(CompositeData.class.getName(), paramString1, paramString2, false);
/*     */ 
/*     */ 
/*     */     
/* 130 */     checkForNullElement((Object[])paramArrayOfString1, "itemNames");
/* 131 */     checkForNullElement((Object[])paramArrayOfString2, "itemDescriptions");
/* 132 */     checkForNullElement((Object[])paramArrayOfOpenType, "itemTypes");
/* 133 */     checkForEmptyString(paramArrayOfString1, "itemNames");
/* 134 */     checkForEmptyString(paramArrayOfString2, "itemDescriptions");
/*     */ 
/*     */ 
/*     */     
/* 138 */     if (paramArrayOfString1.length != paramArrayOfString2.length || paramArrayOfString1.length != paramArrayOfOpenType.length) {
/* 139 */       throw new IllegalArgumentException("Array arguments itemNames[], itemDescriptions[] and itemTypes[] should be of same length (got " + paramArrayOfString1.length + ", " + paramArrayOfString2.length + " and " + paramArrayOfOpenType.length + ").");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.nameToDescription = new TreeMap<>();
/* 148 */     this.nameToType = new TreeMap<>();
/*     */     
/* 150 */     for (byte b = 0; b < paramArrayOfString1.length; b++) {
/* 151 */       String str = paramArrayOfString1[b].trim();
/* 152 */       if (this.nameToDescription.containsKey(str)) {
/* 153 */         throw new OpenDataException("Argument's element itemNames[" + b + "]=\"" + paramArrayOfString1[b] + "\" duplicates a previous item names.");
/*     */       }
/*     */       
/* 156 */       this.nameToDescription.put(str, paramArrayOfString2[b].trim());
/* 157 */       this.nameToType.put(str, paramArrayOfOpenType[b]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkForNullElement(Object[] paramArrayOfObject, String paramString) {
/* 162 */     if (paramArrayOfObject == null || paramArrayOfObject.length == 0) {
/* 163 */       throw new IllegalArgumentException("Argument " + paramString + "[] cannot be null or empty.");
/*     */     }
/* 165 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 166 */       if (paramArrayOfObject[b] == null) {
/* 167 */         throw new IllegalArgumentException("Argument's element " + paramString + "[" + b + "] cannot be null.");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkForEmptyString(String[] paramArrayOfString, String paramString) {
/* 173 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 174 */       if (paramArrayOfString[b].trim().equals("")) {
/* 175 */         throw new IllegalArgumentException("Argument's element " + paramString + "[" + b + "] cannot be an empty string.");
/*     */       }
/*     */     } 
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
/*     */   public boolean containsKey(String paramString) {
/* 192 */     if (paramString == null) {
/* 193 */       return false;
/*     */     }
/* 195 */     return this.nameToDescription.containsKey(paramString);
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
/*     */   public String getDescription(String paramString) {
/* 209 */     if (paramString == null) {
/* 210 */       return null;
/*     */     }
/* 212 */     return this.nameToDescription.get(paramString);
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
/*     */   public OpenType<?> getType(String paramString) {
/* 226 */     if (paramString == null) {
/* 227 */       return null;
/*     */     }
/* 229 */     return this.nameToType.get(paramString);
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
/*     */   public Set<String> keySet() {
/* 241 */     if (this.myNamesSet == null) {
/* 242 */       this.myNamesSet = Collections.unmodifiableSet(this.nameToDescription.keySet());
/*     */     }
/*     */     
/* 245 */     return this.myNamesSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValue(Object paramObject) {
/* 305 */     if (!(paramObject instanceof CompositeData)) {
/* 306 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 311 */     CompositeData compositeData = (CompositeData)paramObject;
/*     */ 
/*     */ 
/*     */     
/* 315 */     CompositeType compositeType = compositeData.getCompositeType();
/* 316 */     return isAssignableFrom(compositeType);
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
/*     */   boolean isAssignableFrom(OpenType<?> paramOpenType) {
/* 333 */     if (!(paramOpenType instanceof CompositeType))
/* 334 */       return false; 
/* 335 */     CompositeType compositeType = (CompositeType)paramOpenType;
/* 336 */     if (!compositeType.getTypeName().equals(getTypeName()))
/* 337 */       return false; 
/* 338 */     for (String str : keySet()) {
/* 339 */       OpenType<?> openType1 = compositeType.getType(str);
/* 340 */       OpenType<?> openType2 = getType(str);
/* 341 */       if (openType1 == null || 
/* 342 */         !openType2.isAssignableFrom(openType1))
/* 343 */         return false; 
/*     */     } 
/* 345 */     return true;
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
/*     */   public boolean equals(Object paramObject) {
/*     */     CompositeType compositeType;
/* 369 */     if (paramObject == null) {
/* 370 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 377 */       compositeType = (CompositeType)paramObject;
/* 378 */     } catch (ClassCastException classCastException) {
/* 379 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 386 */     if (!getTypeName().equals(compositeType.getTypeName())) {
/* 387 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 391 */     if (!this.nameToType.equals(compositeType.nameToType)) {
/* 392 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 397 */     return true;
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
/*     */   public int hashCode() {
/* 420 */     if (this.myHashCode == null) {
/* 421 */       int i = 0;
/* 422 */       i += getTypeName().hashCode();
/* 423 */       for (String str : this.nameToDescription.keySet()) {
/* 424 */         i += str.hashCode();
/* 425 */         i += ((OpenType)this.nameToType.get(str)).hashCode();
/*     */       } 
/* 427 */       this.myHashCode = Integer.valueOf(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 432 */     return this.myHashCode.intValue();
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
/*     */   public String toString() {
/* 451 */     if (this.myToString == null) {
/* 452 */       StringBuilder stringBuilder = new StringBuilder();
/* 453 */       stringBuilder.append(getClass().getName());
/* 454 */       stringBuilder.append("(name=");
/* 455 */       stringBuilder.append(getTypeName());
/* 456 */       stringBuilder.append(",items=(");
/* 457 */       byte b = 0;
/* 458 */       Iterator<String> iterator = this.nameToType.keySet().iterator();
/*     */       
/* 460 */       while (iterator.hasNext()) {
/* 461 */         String str = iterator.next();
/* 462 */         if (b) stringBuilder.append(","); 
/* 463 */         stringBuilder.append("(itemName=");
/* 464 */         stringBuilder.append(str);
/* 465 */         stringBuilder.append(",itemType=");
/* 466 */         stringBuilder.append(((OpenType)this.nameToType.get(str)).toString() + ")");
/* 467 */         b++;
/*     */       } 
/* 469 */       stringBuilder.append("))");
/* 470 */       this.myToString = stringBuilder.toString();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 475 */     return this.myToString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/CompositeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */