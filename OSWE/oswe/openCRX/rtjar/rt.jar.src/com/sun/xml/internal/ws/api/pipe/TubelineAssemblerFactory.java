/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.assembler.MetroConfigName;
/*     */ import com.sun.xml.internal.ws.assembler.MetroTubelineAssembler;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TubelineAssemblerFactory
/*     */ {
/*     */   public abstract TubelineAssembler doCreate(BindingID paramBindingID);
/*     */   
/*     */   public static TubelineAssembler create(ClassLoader classLoader, BindingID bindingId) {
/*  68 */     return create(classLoader, bindingId, null);
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
/*     */   public static TubelineAssembler create(ClassLoader classLoader, BindingID bindingId, @Nullable Container container) {
/*  84 */     if (container != null) {
/*     */       
/*  86 */       TubelineAssemblerFactory taf = (TubelineAssemblerFactory)container.getSPI(TubelineAssemblerFactory.class);
/*  87 */       if (taf != null) {
/*  88 */         TubelineAssembler a = taf.doCreate(bindingId);
/*  89 */         if (a != null) {
/*  90 */           return a;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     for (TubelineAssemblerFactory factory : ServiceFinder.find(TubelineAssemblerFactory.class, classLoader)) {
/*  96 */       TubelineAssembler assembler = factory.doCreate(bindingId);
/*  97 */       if (assembler != null) {
/*  98 */         logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), assembler });
/*  99 */         return assembler;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 104 */     for (PipelineAssemblerFactory factory : ServiceFinder.find(PipelineAssemblerFactory.class, classLoader)) {
/* 105 */       PipelineAssembler assembler = factory.doCreate(bindingId);
/* 106 */       if (assembler != null) {
/* 107 */         logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), assembler });
/* 108 */         return new TubelineAssemblerAdapter(assembler);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 113 */     return (TubelineAssembler)new MetroTubelineAssembler(bindingId, (MetroConfigName)MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);
/*     */   }
/*     */   
/*     */   private static class TubelineAssemblerAdapter implements TubelineAssembler {
/*     */     private PipelineAssembler assembler;
/*     */     
/*     */     TubelineAssemblerAdapter(PipelineAssembler assembler) {
/* 120 */       this.assembler = assembler;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @NotNull
/*     */     public Tube createClient(@NotNull ClientTubeAssemblerContext context) {
/* 127 */       ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(context.getAddress(), context.getWsdlModel(), context.getService(), context.getBinding(), context.getContainer());
/* 128 */       return PipeAdapter.adapt(this.assembler.createClient(ctxt));
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     public Tube createServer(@NotNull ServerTubeAssemblerContext context) {
/* 133 */       if (!(context instanceof ServerPipeAssemblerContext)) {
/* 134 */         throw new IllegalArgumentException("{0} is not instance of ServerPipeAssemblerContext");
/*     */       }
/* 136 */       return PipeAdapter.adapt(this.assembler.createServer((ServerPipeAssemblerContext)context));
/*     */     }
/*     */   }
/*     */   
/* 140 */   private static final Logger logger = Logger.getLogger(TubelineAssemblerFactory.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/TubelineAssemblerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */