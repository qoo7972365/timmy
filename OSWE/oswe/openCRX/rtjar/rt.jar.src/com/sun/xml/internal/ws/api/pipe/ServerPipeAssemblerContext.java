/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServerPipeAssemblerContext
/*     */   extends ServerTubeAssemblerContext
/*     */ {
/*     */   public ServerPipeAssemblerContext(@Nullable SEIModel seiModel, @Nullable WSDLPort wsdlModel, @NotNull WSEndpoint endpoint, @NotNull Tube terminal, boolean isSynchronous) {
/*  48 */     super(seiModel, wsdlModel, endpoint, terminal, isSynchronous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createServerMUPipe(@NotNull Pipe next) {
/*  56 */     return PipeAdapter.adapt(createServerMUTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createDumpPipe(String name, PrintStream out, Pipe next) {
/*  63 */     return PipeAdapter.adapt(createDumpTube(name, out, PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createMonitoringPipe(@NotNull Pipe next) {
/*  71 */     return PipeAdapter.adapt(createMonitoringTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createSecurityPipe(@NotNull Pipe next) {
/*  78 */     return PipeAdapter.adapt(createSecurityTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createValidationPipe(@NotNull Pipe next) {
/*  85 */     return PipeAdapter.adapt(createValidationTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createHandlerPipe(@NotNull Pipe next) {
/*  92 */     return PipeAdapter.adapt(createHandlerTube(PipeAdapter.adapt(next)));
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
/*     */   @NotNull
/*     */   public Pipe getTerminalPipe() {
/* 106 */     return PipeAdapter.adapt(getTerminalTube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createWsaPipe(Pipe next) {
/* 113 */     return PipeAdapter.adapt(createWsaTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/ServerPipeAssemblerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */