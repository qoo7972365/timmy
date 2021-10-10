/*     */ package java.util;
/*     */ 
/*     */ import sun.util.ResourceBundleEnumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ListResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   public final Object handleGetObject(String paramString) {
/* 129 */     if (this.lookup == null) {
/* 130 */       loadLookup();
/*     */     }
/* 132 */     if (paramString == null) {
/* 133 */       throw new NullPointerException();
/*     */     }
/* 135 */     return this.lookup.get(paramString);
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
/*     */   public Enumeration<String> getKeys() {
/* 148 */     if (this.lookup == null) {
/* 149 */       loadLookup();
/*     */     }
/*     */     
/* 152 */     ResourceBundle resourceBundle = this.parent;
/* 153 */     return new ResourceBundleEnumeration(this.lookup.keySet(), (resourceBundle != null) ? resourceBundle
/* 154 */         .getKeys() : null);
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
/*     */   protected Set<String> handleKeySet() {
/* 167 */     if (this.lookup == null) {
/* 168 */       loadLookup();
/*     */     }
/* 170 */     return this.lookup.keySet();
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
/*     */   protected abstract Object[][] getContents();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void loadLookup() {
/* 192 */     if (this.lookup != null) {
/*     */       return;
/*     */     }
/* 195 */     Object[][] arrayOfObject = getContents();
/* 196 */     HashMap<Object, Object> hashMap = new HashMap<>(arrayOfObject.length);
/* 197 */     for (byte b = 0; b < arrayOfObject.length; b++) {
/*     */       
/* 199 */       String str = (String)arrayOfObject[b][0];
/* 200 */       Object object = arrayOfObject[b][1];
/* 201 */       if (str == null || object == null) {
/* 202 */         throw new NullPointerException();
/*     */       }
/* 204 */       hashMap.put(str, object);
/*     */     } 
/* 206 */     this.lookup = (Map)hashMap;
/*     */   }
/*     */   
/* 209 */   private Map<String, Object> lookup = null;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/ListResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */