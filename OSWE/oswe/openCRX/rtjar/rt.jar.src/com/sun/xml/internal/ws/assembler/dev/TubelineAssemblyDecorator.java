/*     */ package com.sun.xml.internal.ws.assembler.dev;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TubelineAssemblyDecorator
/*     */ {
/*     */   public static TubelineAssemblyDecorator composite(Iterable<TubelineAssemblyDecorator> decorators) {
/*  45 */     return new CompositeTubelineAssemblyDecorator(decorators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
/*  55 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateClientHead(Tube tube, ClientTubelineAssemblyContext context) {
/*  66 */     return tube;
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
/*     */   public Tube decorateClientTail(Tube tube, ClientTubelineAssemblyContext context) {
/*  78 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
/*  88 */     return tube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube decorateServerTail(Tube tube, ServerTubelineAssemblyContext context) {
/*  99 */     return tube;
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
/*     */   public Tube decorateServerHead(Tube tube, ServerTubelineAssemblyContext context) {
/* 111 */     return tube;
/*     */   }
/*     */   
/*     */   private static class CompositeTubelineAssemblyDecorator extends TubelineAssemblyDecorator {
/* 115 */     private Collection<TubelineAssemblyDecorator> decorators = new ArrayList<>();
/*     */     
/*     */     public CompositeTubelineAssemblyDecorator(Iterable<TubelineAssemblyDecorator> decorators) {
/* 118 */       for (TubelineAssemblyDecorator decorator : decorators) {
/* 119 */         this.decorators.add(decorator);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Tube decorateClient(Tube tube, ClientTubelineAssemblyContext context) {
/* 125 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 126 */         tube = decorator.decorateClient(tube, context);
/*     */       }
/* 128 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateClientHead(Tube tube, ClientTubelineAssemblyContext context) {
/* 134 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 135 */         tube = decorator.decorateClientHead(tube, context);
/*     */       }
/* 137 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateClientTail(Tube tube, ClientTubelineAssemblyContext context) {
/* 144 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 145 */         tube = decorator.decorateClientTail(tube, context);
/*     */       }
/* 147 */       return tube;
/*     */     }
/*     */     
/*     */     public Tube decorateServer(Tube tube, ServerTubelineAssemblyContext context) {
/* 151 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 152 */         tube = decorator.decorateServer(tube, context);
/*     */       }
/* 154 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateServerTail(Tube tube, ServerTubelineAssemblyContext context) {
/* 160 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 161 */         tube = decorator.decorateServerTail(tube, context);
/*     */       }
/* 163 */       return tube;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Tube decorateServerHead(Tube tube, ServerTubelineAssemblyContext context) {
/* 170 */       for (TubelineAssemblyDecorator decorator : this.decorators) {
/* 171 */         tube = decorator.decorateServerHead(tube, context);
/*     */       }
/* 173 */       return tube;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/dev/TubelineAssemblyDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */