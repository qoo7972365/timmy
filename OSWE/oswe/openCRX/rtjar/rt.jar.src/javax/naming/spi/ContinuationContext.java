/*     */ package javax.naming.spi;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.Binding;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NameClassPair;
/*     */ import javax.naming.NameParser;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ContinuationContext
/*     */   implements Context, Resolver
/*     */ {
/*     */   protected CannotProceedException cpe;
/*     */   protected Hashtable<?, ?> env;
/*  42 */   protected Context contCtx = null;
/*     */ 
/*     */   
/*     */   protected ContinuationContext(CannotProceedException paramCannotProceedException, Hashtable<?, ?> paramHashtable) {
/*  46 */     this.cpe = paramCannotProceedException;
/*  47 */     this.env = paramHashtable;
/*     */   }
/*     */   
/*     */   protected Context getTargetContext() throws NamingException {
/*  51 */     if (this.contCtx == null) {
/*  52 */       if (this.cpe.getResolvedObj() == null) {
/*  53 */         throw (NamingException)this.cpe.fillInStackTrace();
/*     */       }
/*  55 */       this.contCtx = NamingManager.getContext(this.cpe.getResolvedObj(), this.cpe
/*  56 */           .getAltName(), this.cpe
/*  57 */           .getAltNameCtx(), this.env);
/*     */       
/*  59 */       if (this.contCtx == null)
/*  60 */         throw (NamingException)this.cpe.fillInStackTrace(); 
/*     */     } 
/*  62 */     return this.contCtx;
/*     */   }
/*     */   
/*     */   public Object lookup(Name paramName) throws NamingException {
/*  66 */     Context context = getTargetContext();
/*  67 */     return context.lookup(paramName);
/*     */   }
/*     */   
/*     */   public Object lookup(String paramString) throws NamingException {
/*  71 */     Context context = getTargetContext();
/*  72 */     return context.lookup(paramString);
/*     */   }
/*     */   
/*     */   public void bind(Name paramName, Object paramObject) throws NamingException {
/*  76 */     Context context = getTargetContext();
/*  77 */     context.bind(paramName, paramObject);
/*     */   }
/*     */   
/*     */   public void bind(String paramString, Object paramObject) throws NamingException {
/*  81 */     Context context = getTargetContext();
/*  82 */     context.bind(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/*  86 */     Context context = getTargetContext();
/*  87 */     context.rebind(paramName, paramObject);
/*     */   }
/*     */   public void rebind(String paramString, Object paramObject) throws NamingException {
/*  90 */     Context context = getTargetContext();
/*  91 */     context.rebind(paramString, paramObject);
/*     */   }
/*     */   
/*     */   public void unbind(Name paramName) throws NamingException {
/*  95 */     Context context = getTargetContext();
/*  96 */     context.unbind(paramName);
/*     */   }
/*     */   public void unbind(String paramString) throws NamingException {
/*  99 */     Context context = getTargetContext();
/* 100 */     context.unbind(paramString);
/*     */   }
/*     */   
/*     */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/* 104 */     Context context = getTargetContext();
/* 105 */     context.rename(paramName1, paramName2);
/*     */   }
/*     */   public void rename(String paramString1, String paramString2) throws NamingException {
/* 108 */     Context context = getTargetContext();
/* 109 */     context.rename(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/* 113 */     Context context = getTargetContext();
/* 114 */     return context.list(paramName);
/*     */   }
/*     */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/* 117 */     Context context = getTargetContext();
/* 118 */     return context.list(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/* 125 */     Context context = getTargetContext();
/* 126 */     return context.listBindings(paramName);
/*     */   }
/*     */   
/*     */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/* 130 */     Context context = getTargetContext();
/* 131 */     return context.listBindings(paramString);
/*     */   }
/*     */   
/*     */   public void destroySubcontext(Name paramName) throws NamingException {
/* 135 */     Context context = getTargetContext();
/* 136 */     context.destroySubcontext(paramName);
/*     */   }
/*     */   public void destroySubcontext(String paramString) throws NamingException {
/* 139 */     Context context = getTargetContext();
/* 140 */     context.destroySubcontext(paramString);
/*     */   }
/*     */   
/*     */   public Context createSubcontext(Name paramName) throws NamingException {
/* 144 */     Context context = getTargetContext();
/* 145 */     return context.createSubcontext(paramName);
/*     */   }
/*     */   public Context createSubcontext(String paramString) throws NamingException {
/* 148 */     Context context = getTargetContext();
/* 149 */     return context.createSubcontext(paramString);
/*     */   }
/*     */   
/*     */   public Object lookupLink(Name paramName) throws NamingException {
/* 153 */     Context context = getTargetContext();
/* 154 */     return context.lookupLink(paramName);
/*     */   }
/*     */   public Object lookupLink(String paramString) throws NamingException {
/* 157 */     Context context = getTargetContext();
/* 158 */     return context.lookupLink(paramString);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 162 */     Context context = getTargetContext();
/* 163 */     return context.getNameParser(paramName);
/*     */   }
/*     */   
/*     */   public NameParser getNameParser(String paramString) throws NamingException {
/* 167 */     Context context = getTargetContext();
/* 168 */     return context.getNameParser(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 174 */     Context context = getTargetContext();
/* 175 */     return context.composeName(paramName1, paramName2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 180 */     Context context = getTargetContext();
/* 181 */     return context.composeName(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 186 */     Context context = getTargetContext();
/* 187 */     return context.addToEnvironment(paramString, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 192 */     Context context = getTargetContext();
/* 193 */     return context.removeFromEnvironment(paramString);
/*     */   }
/*     */   
/*     */   public Hashtable<?, ?> getEnvironment() throws NamingException {
/* 197 */     Context context = getTargetContext();
/* 198 */     return context.getEnvironment();
/*     */   }
/*     */   
/*     */   public String getNameInNamespace() throws NamingException {
/* 202 */     Context context = getTargetContext();
/* 203 */     return context.getNameInNamespace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResolveResult resolveToClass(Name paramName, Class<? extends Context> paramClass) throws NamingException {
/* 210 */     if (this.cpe.getResolvedObj() == null) {
/* 211 */       throw (NamingException)this.cpe.fillInStackTrace();
/*     */     }
/* 213 */     Resolver resolver = NamingManager.getResolver(this.cpe.getResolvedObj(), this.cpe
/* 214 */         .getAltName(), this.cpe
/* 215 */         .getAltNameCtx(), this.env);
/*     */     
/* 217 */     if (resolver == null)
/* 218 */       throw (NamingException)this.cpe.fillInStackTrace(); 
/* 219 */     return resolver.resolveToClass(paramName, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResolveResult resolveToClass(String paramString, Class<? extends Context> paramClass) throws NamingException {
/* 226 */     if (this.cpe.getResolvedObj() == null) {
/* 227 */       throw (NamingException)this.cpe.fillInStackTrace();
/*     */     }
/* 229 */     Resolver resolver = NamingManager.getResolver(this.cpe.getResolvedObj(), this.cpe
/* 230 */         .getAltName(), this.cpe
/* 231 */         .getAltNameCtx(), this.env);
/*     */     
/* 233 */     if (resolver == null)
/* 234 */       throw (NamingException)this.cpe.fillInStackTrace(); 
/* 235 */     return resolver.resolveToClass(paramString, paramClass);
/*     */   }
/*     */   
/*     */   public void close() throws NamingException {
/* 239 */     this.cpe = null;
/* 240 */     this.env = null;
/* 241 */     if (this.contCtx != null) {
/* 242 */       this.contCtx.close();
/* 243 */       this.contCtx = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/ContinuationContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */