/*     */ package javax.naming.spi;
/*     */ 
/*     */ import com.sun.naming.internal.FactoryEnumeration;
/*     */ import com.sun.naming.internal.ResourceManager;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.CannotProceedException;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.Name;
/*     */ import javax.naming.NamingException;
/*     */ import javax.naming.Reference;
/*     */ import javax.naming.Referenceable;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectoryManager
/*     */   extends NamingManager
/*     */ {
/*     */   public static DirContext getContinuationDirContext(CannotProceedException paramCannotProceedException) throws NamingException {
/*  91 */     Hashtable<?, ?> hashtable = paramCannotProceedException.getEnvironment();
/*  92 */     if (hashtable == null) {
/*  93 */       hashtable = new Hashtable<>(7);
/*     */     } else {
/*     */       
/*  96 */       hashtable = (Hashtable<?, ?>)hashtable.clone();
/*     */     } 
/*  98 */     hashtable.put("java.naming.spi.CannotProceedException", paramCannotProceedException);
/*     */     
/* 100 */     return new ContinuationDirContext(paramCannotProceedException, hashtable);
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
/*     */   
/*     */   public static Object getObjectInstance(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable, Attributes paramAttributes) throws Exception {
/* 161 */     ObjectFactoryBuilder objectFactoryBuilder = getObjectFactoryBuilder();
/* 162 */     if (objectFactoryBuilder != null) {
/*     */       
/* 164 */       ObjectFactory objectFactory = objectFactoryBuilder.createObjectFactory(paramObject, paramHashtable);
/* 165 */       if (objectFactory instanceof DirObjectFactory) {
/* 166 */         return ((DirObjectFactory)objectFactory).getObjectInstance(paramObject, paramName, paramContext, paramHashtable, paramAttributes);
/*     */       }
/*     */       
/* 169 */       return objectFactory.getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     Reference reference = null;
/* 176 */     if (paramObject instanceof Reference) {
/* 177 */       reference = (Reference)paramObject;
/* 178 */     } else if (paramObject instanceof Referenceable) {
/* 179 */       reference = ((Referenceable)paramObject).getReference();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     if (reference != null) {
/* 185 */       String str = reference.getFactoryClassName();
/* 186 */       if (str != null) {
/*     */ 
/*     */         
/* 189 */         ObjectFactory objectFactory = getObjectFactoryFromReference(reference, str);
/* 190 */         if (objectFactory instanceof DirObjectFactory) {
/* 191 */           return ((DirObjectFactory)objectFactory).getObjectInstance(reference, paramName, paramContext, paramHashtable, paramAttributes);
/*     */         }
/* 193 */         if (objectFactory != null) {
/* 194 */           return objectFactory.getObjectInstance(reference, paramName, paramContext, paramHashtable);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         return paramObject;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 207 */       Object object1 = processURLAddrs(reference, paramName, paramContext, paramHashtable);
/* 208 */       if (object1 != null) {
/* 209 */         return object1;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 215 */     Object object = createObjectFromFactories(paramObject, paramName, paramContext, paramHashtable, paramAttributes);
/*     */     
/* 217 */     return (object != null) ? object : paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object createObjectFromFactories(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable, Attributes paramAttributes) throws Exception {
/* 224 */     FactoryEnumeration factoryEnumeration = ResourceManager.getFactories("java.naming.factory.object", paramHashtable, paramContext);
/*     */ 
/*     */     
/* 227 */     if (factoryEnumeration == null) {
/* 228 */       return null;
/*     */     }
/*     */     
/* 231 */     Object object = null;
/*     */     
/* 233 */     while (object == null && factoryEnumeration.hasMore()) {
/* 234 */       ObjectFactory objectFactory = (ObjectFactory)factoryEnumeration.next();
/* 235 */       if (objectFactory instanceof DirObjectFactory) {
/*     */         
/* 237 */         object = ((DirObjectFactory)objectFactory).getObjectInstance(paramObject, paramName, paramContext, paramHashtable, paramAttributes);
/*     */         continue;
/*     */       } 
/* 240 */       object = objectFactory.getObjectInstance(paramObject, paramName, paramContext, paramHashtable);
/*     */     } 
/*     */     
/* 243 */     return object;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DirStateFactory.Result getStateToBind(Object paramObject, Name paramName, Context paramContext, Hashtable<?, ?> paramHashtable, Attributes paramAttributes) throws NamingException {
/* 309 */     FactoryEnumeration factoryEnumeration = ResourceManager.getFactories("java.naming.factory.state", paramHashtable, paramContext);
/*     */ 
/*     */     
/* 312 */     if (factoryEnumeration == null)
/*     */     {
/* 314 */       return new DirStateFactory.Result(paramObject, paramAttributes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     DirStateFactory.Result result = null;
/* 321 */     while (result == null && factoryEnumeration.hasMore()) {
/* 322 */       StateFactory stateFactory = (StateFactory)factoryEnumeration.next();
/* 323 */       if (stateFactory instanceof DirStateFactory) {
/*     */         
/* 325 */         result = ((DirStateFactory)stateFactory).getStateToBind(paramObject, paramName, paramContext, paramHashtable, paramAttributes);
/*     */         continue;
/*     */       } 
/* 328 */       Object object = stateFactory.getStateToBind(paramObject, paramName, paramContext, paramHashtable);
/* 329 */       if (object != null) {
/* 330 */         result = new DirStateFactory.Result(object, paramAttributes);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 335 */     return (result != null) ? result : new DirStateFactory.Result(paramObject, paramAttributes);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/spi/DirectoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */