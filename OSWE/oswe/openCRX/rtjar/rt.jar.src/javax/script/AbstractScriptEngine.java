/*     */ package javax.script;
/*     */ 
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractScriptEngine
/*     */   implements ScriptEngine
/*     */ {
/*  64 */   protected ScriptContext context = new SimpleScriptContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractScriptEngine() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractScriptEngine(Bindings paramBindings) {
/*  77 */     this();
/*  78 */     if (paramBindings == null) {
/*  79 */       throw new NullPointerException("n is null");
/*     */     }
/*  81 */     this.context.setBindings(paramBindings, 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContext(ScriptContext paramScriptContext) {
/*  92 */     if (paramScriptContext == null) {
/*  93 */       throw new NullPointerException("null context");
/*     */     }
/*  95 */     this.context = paramScriptContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScriptContext getContext() {
/* 104 */     return this.context;
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
/*     */   public Bindings getBindings(int paramInt) {
/* 120 */     if (paramInt == 200)
/* 121 */       return this.context.getBindings(200); 
/* 122 */     if (paramInt == 100) {
/* 123 */       return this.context.getBindings(100);
/*     */     }
/* 125 */     throw new IllegalArgumentException("Invalid scope value.");
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
/*     */   public void setBindings(Bindings paramBindings, int paramInt) {
/* 143 */     if (paramInt == 200) {
/* 144 */       this.context.setBindings(paramBindings, 200);
/* 145 */     } else if (paramInt == 100) {
/* 146 */       this.context.setBindings(paramBindings, 100);
/*     */     } else {
/* 148 */       throw new IllegalArgumentException("Invalid scope value.");
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
/*     */   public void put(String paramString, Object paramObject) {
/* 164 */     Bindings bindings = getBindings(100);
/* 165 */     if (bindings != null) {
/* 166 */       bindings.put(paramString, paramObject);
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
/*     */   public Object get(String paramString) {
/* 182 */     Bindings bindings = getBindings(100);
/* 183 */     if (bindings != null) {
/* 184 */       return bindings.get(paramString);
/*     */     }
/*     */     
/* 187 */     return null;
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
/*     */   public Object eval(Reader paramReader, Bindings paramBindings) throws ScriptException {
/* 210 */     ScriptContext scriptContext = getScriptContext(paramBindings);
/*     */     
/* 212 */     return eval(paramReader, scriptContext);
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
/*     */   public Object eval(String paramString, Bindings paramBindings) throws ScriptException {
/* 231 */     ScriptContext scriptContext = getScriptContext(paramBindings);
/*     */     
/* 233 */     return eval(paramString, scriptContext);
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
/*     */   public Object eval(Reader paramReader) throws ScriptException {
/* 249 */     return eval(paramReader, this.context);
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
/*     */   public Object eval(String paramString) throws ScriptException {
/* 264 */     return eval(paramString, this.context);
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
/*     */   protected ScriptContext getScriptContext(Bindings paramBindings) {
/* 290 */     SimpleScriptContext simpleScriptContext = new SimpleScriptContext();
/* 291 */     Bindings bindings = getBindings(200);
/*     */     
/* 293 */     if (bindings != null) {
/* 294 */       simpleScriptContext.setBindings(bindings, 200);
/*     */     }
/*     */     
/* 297 */     if (paramBindings != null) {
/* 298 */       simpleScriptContext.setBindings(paramBindings, 100);
/*     */     } else {
/*     */       
/* 301 */       throw new NullPointerException("Engine scope Bindings may not be null.");
/*     */     } 
/*     */     
/* 304 */     simpleScriptContext.setReader(this.context.getReader());
/* 305 */     simpleScriptContext.setWriter(this.context.getWriter());
/* 306 */     simpleScriptContext.setErrorWriter(this.context.getErrorWriter());
/*     */     
/* 308 */     return simpleScriptContext;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/AbstractScriptEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */