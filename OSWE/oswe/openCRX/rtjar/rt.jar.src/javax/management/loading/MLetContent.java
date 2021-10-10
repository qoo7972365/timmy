/*     */ package javax.management.loading;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLetContent
/*     */ {
/*     */   private Map<String, String> attributes;
/*     */   private List<String> types;
/*     */   private List<String> values;
/*     */   private URL documentURL;
/*     */   private URL baseURL;
/*     */   
/*     */   public MLetContent(URL paramURL, Map<String, String> paramMap, List<String> paramList1, List<String> paramList2) {
/*  92 */     this.documentURL = paramURL;
/*  93 */     this.attributes = Collections.unmodifiableMap(paramMap);
/*  94 */     this.types = Collections.unmodifiableList(paramList1);
/*  95 */     this.values = Collections.unmodifiableList(paramList2);
/*     */ 
/*     */ 
/*     */     
/*  99 */     String str = getParameter("codebase");
/* 100 */     if (str != null) {
/* 101 */       if (!str.endsWith("/")) {
/* 102 */         str = str + "/";
/*     */       }
/*     */       try {
/* 105 */         this.baseURL = new URL(this.documentURL, str);
/* 106 */       } catch (MalformedURLException malformedURLException) {}
/*     */     } 
/*     */ 
/*     */     
/* 110 */     if (this.baseURL == null) {
/* 111 */       String str1 = this.documentURL.getFile();
/* 112 */       int i = str1.lastIndexOf('/');
/* 113 */       if (i >= 0 && i < str1.length() - 1) {
/*     */         try {
/* 115 */           this.baseURL = new URL(this.documentURL, str1.substring(0, i + 1));
/* 116 */         } catch (MalformedURLException malformedURLException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (this.baseURL == null) {
/* 122 */       this.baseURL = this.documentURL;
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
/*     */   public Map<String, String> getAttributes() {
/* 138 */     return this.attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getDocumentBase() {
/* 146 */     return this.documentURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getCodeBase() {
/* 154 */     return this.baseURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJarFiles() {
/* 163 */     return getParameter("archive");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCode() {
/* 173 */     return getParameter("code");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSerializedObject() {
/* 183 */     return getParameter("object");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 193 */     return getParameter("name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 204 */     return getParameter("version");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getParameterTypes() {
/* 214 */     return this.types;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getParameterValues() {
/* 224 */     return this.values;
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
/*     */   private String getParameter(String paramString) {
/* 236 */     return this.attributes.get(paramString.toLowerCase());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/MLetContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */