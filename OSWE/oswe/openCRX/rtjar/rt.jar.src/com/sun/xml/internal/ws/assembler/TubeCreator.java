/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.internal.ws.assembler.dev.TubeFactory;
/*     */ import com.sun.xml.internal.ws.assembler.dev.TubelineAssemblyContextUpdater;
/*     */ import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubeFactoryConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TubeCreator
/*     */ {
/*  43 */   private static final Logger LOGGER = Logger.getLogger(TubeCreator.class);
/*     */   private final TubeFactory factory;
/*     */   private final String msgDumpPropertyBase;
/*     */   
/*     */   TubeCreator(TubeFactoryConfig config, ClassLoader tubeFactoryClassLoader) {
/*  48 */     String className = config.getClassName();
/*     */     try {
/*     */       Class<?> factoryClass;
/*  51 */       if (isJDKInternal(className)) {
/*  52 */         factoryClass = Class.forName(className, true, null);
/*     */       } else {
/*  54 */         factoryClass = Class.forName(className, true, tubeFactoryClassLoader);
/*     */       } 
/*  56 */       if (TubeFactory.class.isAssignableFrom(factoryClass)) {
/*     */ 
/*     */         
/*  59 */         Class<TubeFactory> typedClass = (Class)factoryClass;
/*  60 */         this.factory = typedClass.newInstance();
/*  61 */         this.msgDumpPropertyBase = this.factory.getClass().getName() + ".dump";
/*     */       } else {
/*  63 */         throw new RuntimeException(TubelineassemblyMessages.MASM_0015_CLASS_DOES_NOT_IMPLEMENT_INTERFACE(factoryClass.getName(), TubeFactory.class.getName()));
/*     */       } 
/*  65 */     } catch (InstantiationException ex) {
/*  66 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0016_UNABLE_TO_INSTANTIATE_TUBE_FACTORY(className), ex), true);
/*  67 */     } catch (IllegalAccessException ex) {
/*  68 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0016_UNABLE_TO_INSTANTIATE_TUBE_FACTORY(className), ex), true);
/*  69 */     } catch (ClassNotFoundException ex) {
/*  70 */       throw (RuntimeException)LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0017_UNABLE_TO_LOAD_TUBE_FACTORY_CLASS(className), ex), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   Tube createTube(DefaultClientTubelineAssemblyContext context) {
/*  76 */     return this.factory.createTube(context);
/*     */   }
/*     */ 
/*     */   
/*     */   Tube createTube(DefaultServerTubelineAssemblyContext context) {
/*  81 */     return this.factory.createTube(context);
/*     */   }
/*     */   
/*     */   void updateContext(ClientTubelineAssemblyContext context) {
/*  85 */     if (this.factory instanceof TubelineAssemblyContextUpdater) {
/*  86 */       ((TubelineAssemblyContextUpdater)this.factory).prepareContext(context);
/*     */     }
/*     */   }
/*     */   
/*     */   void updateContext(DefaultServerTubelineAssemblyContext context) {
/*  91 */     if (this.factory instanceof TubelineAssemblyContextUpdater) {
/*  92 */       ((TubelineAssemblyContextUpdater)this.factory).prepareContext(context);
/*     */     }
/*     */   }
/*     */   
/*     */   String getMessageDumpPropertyBase() {
/*  97 */     return this.msgDumpPropertyBase;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isJDKInternal(String className) {
/* 102 */     return className.startsWith("com.sun.xml.internal.ws");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/TubeCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */