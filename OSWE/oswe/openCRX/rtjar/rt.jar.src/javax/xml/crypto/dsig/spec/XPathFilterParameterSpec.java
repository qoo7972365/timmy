/*     */ package javax.xml.crypto.dsig.spec;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XPathFilterParameterSpec
/*     */   implements TransformParameterSpec
/*     */ {
/*     */   private String xPath;
/*     */   private Map<String, String> nsMap;
/*     */   
/*     */   public XPathFilterParameterSpec(String paramString) {
/*  65 */     if (paramString == null) {
/*  66 */       throw new NullPointerException();
/*     */     }
/*  68 */     this.xPath = paramString;
/*  69 */     this.nsMap = Collections.emptyMap();
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
/*     */   public XPathFilterParameterSpec(String paramString, Map<?, ?> paramMap) {
/*  88 */     if (paramString == null || paramMap == null) {
/*  89 */       throw new NullPointerException();
/*     */     }
/*  91 */     this.xPath = paramString;
/*  92 */     HashMap<Object, Object> hashMap1 = new HashMap<>(paramMap);
/*  93 */     Iterator<Map.Entry> iterator = hashMap1.entrySet().iterator();
/*  94 */     while (iterator.hasNext()) {
/*  95 */       Map.Entry entry = iterator.next();
/*  96 */       if (!(entry.getKey() instanceof String) || 
/*  97 */         !(entry.getValue() instanceof String)) {
/*  98 */         throw new ClassCastException("not a String");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 103 */     HashMap<Object, Object> hashMap2 = hashMap1;
/*     */     
/* 105 */     this.nsMap = Collections.unmodifiableMap(hashMap2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getXPath() {
/* 114 */     return this.xPath;
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
/*     */   public Map getNamespaceMap() {
/* 130 */     return this.nsMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/spec/XPathFilterParameterSpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */