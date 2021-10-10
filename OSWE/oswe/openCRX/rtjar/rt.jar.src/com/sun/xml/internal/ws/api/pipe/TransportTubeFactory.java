/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TransportTubeFactory
/*     */ {
/*     */   public abstract Tube doCreate(@NotNull ClientTubeAssemblerContext paramClientTubeAssemblerContext);
/*     */   
/*  98 */   private static final TransportTubeFactory DEFAULT = new DefaultTransportTubeFactory();
/*     */   
/*     */   private static class DefaultTransportTubeFactory
/*     */     extends TransportTubeFactory {
/*     */     public Tube doCreate(ClientTubeAssemblerContext context) {
/* 103 */       return createDefault(context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private DefaultTransportTubeFactory() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Tube create(@Nullable ClassLoader classLoader, @NotNull ClientTubeAssemblerContext context) {
/* 117 */     for (TransportTubeFactory factory : ServiceFinder.find(TransportTubeFactory.class, classLoader, (Component)context.getContainer())) {
/* 118 */       Tube tube = factory.doCreate(context);
/* 119 */       if (tube != null) {
/* 120 */         if (logger.isLoggable(Level.FINE)) {
/* 121 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), tube });
/*     */         }
/* 123 */         return tube;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(context.getAddress(), context.getWsdlModel(), context.getService(), context.getBinding(), context.getContainer());
/* 131 */     ctxt.setCodec(context.getCodec());
/* 132 */     for (TransportPipeFactory factory : ServiceFinder.find(TransportPipeFactory.class, classLoader)) {
/* 133 */       Pipe pipe = factory.doCreate(ctxt);
/* 134 */       if (pipe != null) {
/* 135 */         if (logger.isLoggable(Level.FINE)) {
/* 136 */           logger.log(Level.FINE, "{0} successfully created {1}", new Object[] { factory.getClass(), pipe });
/*     */         }
/* 138 */         return PipeAdapter.adapt(pipe);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return DEFAULT.createDefault(ctxt);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Tube createDefault(ClientTubeAssemblerContext context) {
/* 147 */     String scheme = context.getAddress().getURI().getScheme();
/* 148 */     if (scheme != null && (
/* 149 */       scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
/* 150 */       return createHttpTransport(context);
/*     */     }
/* 152 */     throw new WebServiceException("Unsupported endpoint address: " + context.getAddress());
/*     */   }
/*     */   
/*     */   protected Tube createHttpTransport(ClientTubeAssemblerContext context) {
/* 156 */     return (Tube)new HttpTransportPipe(context.getCodec(), context.getBinding());
/*     */   }
/*     */   
/* 159 */   private static final Logger logger = Logger.getLogger(TransportTubeFactory.class.getName());
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/TransportTubeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */