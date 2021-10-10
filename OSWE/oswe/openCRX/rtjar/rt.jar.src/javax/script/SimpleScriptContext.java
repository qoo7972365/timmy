/*     */ package javax.script;
/*     */ 
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleScriptContext
/*     */   implements ScriptContext
/*     */ {
/*  89 */   protected Bindings engineScope = new SimpleBindings();
/*  90 */   protected Bindings globalScope = null;
/*  91 */   protected Reader reader = new InputStreamReader(System.in);
/*  92 */   protected Writer writer = new PrintWriter(System.out, true);
/*  93 */   protected Writer errorWriter = new PrintWriter(System.err, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindings(Bindings paramBindings, int paramInt) {
/* 112 */     switch (paramInt) {
/*     */       
/*     */       case 100:
/* 115 */         if (paramBindings == null) {
/* 116 */           throw new NullPointerException("Engine scope cannot be null.");
/*     */         }
/* 118 */         this.engineScope = paramBindings;
/*     */         return;
/*     */       case 200:
/* 121 */         this.globalScope = paramBindings;
/*     */         return;
/*     */     } 
/* 124 */     throw new IllegalArgumentException("Invalid scope value.");
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
/*     */   public Object getAttribute(String paramString) {
/* 143 */     checkName(paramString);
/* 144 */     if (this.engineScope.containsKey(paramString))
/* 145 */       return getAttribute(paramString, 100); 
/* 146 */     if (this.globalScope != null && this.globalScope.containsKey(paramString)) {
/* 147 */       return getAttribute(paramString, 200);
/*     */     }
/*     */     
/* 150 */     return null;
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
/*     */   public Object getAttribute(String paramString, int paramInt) {
/* 166 */     checkName(paramString);
/* 167 */     switch (paramInt) {
/*     */       
/*     */       case 100:
/* 170 */         return this.engineScope.get(paramString);
/*     */       
/*     */       case 200:
/* 173 */         if (this.globalScope != null) {
/* 174 */           return this.globalScope.get(paramString);
/*     */         }
/* 176 */         return null;
/*     */     } 
/*     */     
/* 179 */     throw new IllegalArgumentException("Illegal scope value.");
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
/*     */   public Object removeAttribute(String paramString, int paramInt) {
/* 195 */     checkName(paramString);
/* 196 */     switch (paramInt) {
/*     */       
/*     */       case 100:
/* 199 */         if (getBindings(100) != null) {
/* 200 */           return getBindings(100).remove(paramString);
/*     */         }
/* 202 */         return null;
/*     */       
/*     */       case 200:
/* 205 */         if (getBindings(200) != null) {
/* 206 */           return getBindings(200).remove(paramString);
/*     */         }
/* 208 */         return null;
/*     */     } 
/*     */     
/* 211 */     throw new IllegalArgumentException("Illegal scope value.");
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
/*     */   public void setAttribute(String paramString, Object paramObject, int paramInt) {
/* 227 */     checkName(paramString);
/* 228 */     switch (paramInt) {
/*     */       
/*     */       case 100:
/* 231 */         this.engineScope.put(paramString, paramObject);
/*     */         return;
/*     */       
/*     */       case 200:
/* 235 */         if (this.globalScope != null) {
/* 236 */           this.globalScope.put(paramString, paramObject);
/*     */         }
/*     */         return;
/*     */     } 
/*     */     
/* 241 */     throw new IllegalArgumentException("Illegal scope value.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer getWriter() {
/* 247 */     return this.writer;
/*     */   }
/*     */ 
/*     */   
/*     */   public Reader getReader() {
/* 252 */     return this.reader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReader(Reader paramReader) {
/* 257 */     this.reader = paramReader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWriter(Writer paramWriter) {
/* 262 */     this.writer = paramWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public Writer getErrorWriter() {
/* 267 */     return this.errorWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setErrorWriter(Writer paramWriter) {
/* 272 */     this.errorWriter = paramWriter;
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
/*     */   public int getAttributesScope(String paramString) {
/* 285 */     checkName(paramString);
/* 286 */     if (this.engineScope.containsKey(paramString))
/* 287 */       return 100; 
/* 288 */     if (this.globalScope != null && this.globalScope.containsKey(paramString)) {
/* 289 */       return 200;
/*     */     }
/* 291 */     return -1;
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
/*     */   public Bindings getBindings(int paramInt) {
/* 305 */     if (paramInt == 100)
/* 306 */       return this.engineScope; 
/* 307 */     if (paramInt == 200) {
/* 308 */       return this.globalScope;
/*     */     }
/* 310 */     throw new IllegalArgumentException("Illegal scope value.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getScopes() {
/* 316 */     return scopes;
/*     */   }
/*     */   
/*     */   private void checkName(String paramString) {
/* 320 */     Objects.requireNonNull(paramString);
/* 321 */     if (paramString.isEmpty()) {
/* 322 */       throw new IllegalArgumentException("name cannot be empty");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 328 */   private static List<Integer> scopes = new ArrayList<>(2); static {
/* 329 */     scopes.add(Integer.valueOf(100));
/* 330 */     scopes.add(Integer.valueOf(200));
/* 331 */     scopes = Collections.unmodifiableList(scopes);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/SimpleScriptContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */