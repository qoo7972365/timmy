/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabularType
/*     */   extends OpenType<TabularData>
/*     */ {
/*     */   static final long serialVersionUID = 6554071860220659261L;
/*     */   private CompositeType rowType;
/*     */   private List<String> indexNames;
/*  65 */   private transient Integer myHashCode = null;
/*  66 */   private transient String myToString = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabularType(String paramString1, String paramString2, CompositeType paramCompositeType, String[] paramArrayOfString) throws OpenDataException {
/* 111 */     super(TabularData.class.getName(), paramString1, paramString2, false);
/*     */ 
/*     */ 
/*     */     
/* 115 */     if (paramCompositeType == null) {
/* 116 */       throw new IllegalArgumentException("Argument rowType cannot be null.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     checkForNullElement((Object[])paramArrayOfString, "indexNames");
/* 122 */     checkForEmptyString(paramArrayOfString, "indexNames");
/*     */ 
/*     */ 
/*     */     
/* 126 */     for (byte b1 = 0; b1 < paramArrayOfString.length; b1++) {
/* 127 */       if (!paramCompositeType.containsKey(paramArrayOfString[b1])) {
/* 128 */         throw new OpenDataException("Argument's element value indexNames[" + b1 + "]=\"" + paramArrayOfString[b1] + "\" is not a valid item name for rowType.");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     this.rowType = paramCompositeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     ArrayList<String> arrayList = new ArrayList(paramArrayOfString.length + 1);
/* 142 */     for (byte b2 = 0; b2 < paramArrayOfString.length; b2++) {
/* 143 */       arrayList.add(paramArrayOfString[b2]);
/*     */     }
/* 145 */     this.indexNames = Collections.unmodifiableList(arrayList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkForNullElement(Object[] paramArrayOfObject, String paramString) {
/* 153 */     if (paramArrayOfObject == null || paramArrayOfObject.length == 0) {
/* 154 */       throw new IllegalArgumentException("Argument " + paramString + "[] cannot be null or empty.");
/*     */     }
/* 156 */     for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 157 */       if (paramArrayOfObject[b] == null) {
/* 158 */         throw new IllegalArgumentException("Argument's element " + paramString + "[" + b + "] cannot be null.");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkForEmptyString(String[] paramArrayOfString, String paramString) {
/* 167 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 168 */       if (paramArrayOfString[b].trim().equals("")) {
/* 169 */         throw new IllegalArgumentException("Argument's element " + paramString + "[" + b + "] cannot be an empty string.");
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
/*     */   public CompositeType getRowType() {
/* 185 */     return this.rowType;
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
/*     */   public List<String> getIndexNames() {
/* 201 */     return this.indexNames;
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
/*     */   public boolean isValue(Object paramObject) {
/* 229 */     if (!(paramObject instanceof TabularData)) {
/* 230 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 234 */     TabularData tabularData = (TabularData)paramObject;
/* 235 */     TabularType tabularType = tabularData.getTabularType();
/* 236 */     return isAssignableFrom(tabularType);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isAssignableFrom(OpenType<?> paramOpenType) {
/* 241 */     if (!(paramOpenType instanceof TabularType))
/* 242 */       return false; 
/* 243 */     TabularType tabularType = (TabularType)paramOpenType;
/* 244 */     if (!getTypeName().equals(tabularType.getTypeName()) || 
/* 245 */       !getIndexNames().equals(tabularType.getIndexNames()))
/* 246 */       return false; 
/* 247 */     return getRowType().isAssignableFrom(tabularType.getRowType());
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
/*     */   public boolean equals(Object paramObject) {
/*     */     TabularType tabularType;
/* 272 */     if (paramObject == null) {
/* 273 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 280 */       tabularType = (TabularType)paramObject;
/* 281 */     } catch (ClassCastException classCastException) {
/* 282 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (!getTypeName().equals(tabularType.getTypeName())) {
/* 290 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 294 */     if (!this.rowType.equals(tabularType.rowType)) {
/* 295 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (!this.indexNames.equals(tabularType.indexNames)) {
/* 300 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 305 */     return true;
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
/* 328 */     if (this.myHashCode == null) {
/* 329 */       int i = 0;
/* 330 */       i += getTypeName().hashCode();
/* 331 */       i += this.rowType.hashCode();
/* 332 */       for (String str : this.indexNames)
/* 333 */         i += str.hashCode(); 
/* 334 */       this.myHashCode = Integer.valueOf(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 339 */     return this.myHashCode.intValue();
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
/* 358 */     if (this.myToString == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       StringBuilder stringBuilder = (new StringBuilder()).append(getClass().getName()).append("(name=").append(getTypeName()).append(",rowType=").append(this.rowType.toString()).append(",indexNames=(");
/* 366 */       String str = "";
/* 367 */       for (String str1 : this.indexNames) {
/* 368 */         stringBuilder.append(str).append(str1);
/* 369 */         str = ",";
/*     */       } 
/* 371 */       stringBuilder.append("))");
/* 372 */       this.myToString = stringBuilder.toString();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 377 */     return this.myToString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/TabularType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */