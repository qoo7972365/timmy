/*     */ package javax.script;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CompiledScript
/*     */ {
/*     */   public abstract Object eval(ScriptContext paramScriptContext) throws ScriptException;
/*     */   
/*     */   public Object eval(Bindings paramBindings) throws ScriptException {
/*  79 */     ScriptContext scriptContext = getEngine().getContext();
/*     */     
/*  81 */     if (paramBindings != null) {
/*  82 */       SimpleScriptContext simpleScriptContext = new SimpleScriptContext();
/*  83 */       simpleScriptContext.setBindings(paramBindings, 100);
/*  84 */       simpleScriptContext.setBindings(scriptContext.getBindings(200), 200);
/*     */       
/*  86 */       simpleScriptContext.setWriter(scriptContext.getWriter());
/*  87 */       simpleScriptContext.setReader(scriptContext.getReader());
/*  88 */       simpleScriptContext.setErrorWriter(scriptContext.getErrorWriter());
/*  89 */       scriptContext = simpleScriptContext;
/*     */     } 
/*     */     
/*  92 */     return eval(scriptContext);
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
/*     */   public Object eval() throws ScriptException {
/* 106 */     return eval(getEngine().getContext());
/*     */   }
/*     */   
/*     */   public abstract ScriptEngine getEngine();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/script/CompiledScript.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */