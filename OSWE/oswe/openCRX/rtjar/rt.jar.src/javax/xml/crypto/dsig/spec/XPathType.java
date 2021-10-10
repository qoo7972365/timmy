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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPathType
/*     */ {
/*     */   private final String expression;
/*     */   private final Filter filter;
/*     */   private Map<String, String> nsMap;
/*     */   
/*     */   public static class Filter
/*     */   {
/*     */     private final String operation;
/*     */     
/*     */     private Filter(String param1String) {
/*  79 */       this.operation = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  88 */       return this.operation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     public static final Filter INTERSECT = new Filter("intersect");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     public static final Filter SUBTRACT = new Filter("subtract");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     public static final Filter UNION = new Filter("union");
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
/*     */   public XPathType(String paramString, Filter paramFilter) {
/* 122 */     if (paramString == null) {
/* 123 */       throw new NullPointerException("expression cannot be null");
/*     */     }
/* 125 */     if (paramFilter == null) {
/* 126 */       throw new NullPointerException("filter cannot be null");
/*     */     }
/* 128 */     this.expression = paramString;
/* 129 */     this.filter = paramFilter;
/* 130 */     this.nsMap = Collections.emptyMap();
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
/*     */   public XPathType(String paramString, Filter paramFilter, Map<?, ?> paramMap) {
/* 152 */     this(paramString, paramFilter);
/* 153 */     if (paramMap == null) {
/* 154 */       throw new NullPointerException("namespaceMap cannot be null");
/*     */     }
/* 156 */     HashMap<Object, Object> hashMap1 = new HashMap<>(paramMap);
/* 157 */     Iterator<Map.Entry> iterator = hashMap1.entrySet().iterator();
/* 158 */     while (iterator.hasNext()) {
/* 159 */       Map.Entry entry = iterator.next();
/* 160 */       if (!(entry.getKey() instanceof String) || 
/* 161 */         !(entry.getValue() instanceof String)) {
/* 162 */         throw new ClassCastException("not a String");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 167 */     HashMap<Object, Object> hashMap2 = hashMap1;
/*     */     
/* 169 */     this.nsMap = Collections.unmodifiableMap(hashMap2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpression() {
/* 178 */     return this.expression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getFilter() {
/* 187 */     return this.filter;
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
/* 203 */     return this.nsMap;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/crypto/dsig/spec/XPathType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */