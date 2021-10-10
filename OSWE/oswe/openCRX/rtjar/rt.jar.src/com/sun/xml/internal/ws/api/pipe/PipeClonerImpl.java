/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class PipeClonerImpl
/*     */   extends PipeCloner
/*     */ {
/*  48 */   private static final Logger LOGGER = Logger.getLogger(PipeClonerImpl.class.getName());
/*     */ 
/*     */   
/*     */   public PipeClonerImpl() {
/*  52 */     super(new HashMap<>());
/*     */   }
/*     */   
/*     */   protected PipeClonerImpl(Map<Object, Object> master2copy) {
/*  56 */     super(master2copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Pipe> T copy(T p) {
/*  64 */     Pipe r = (Pipe)this.master2copy.get(p);
/*  65 */     if (r == null) {
/*  66 */       r = p.copy(this);
/*     */       
/*  68 */       assert this.master2copy.get(p) == r : "the pipe must call the add(...) method to register itself before start copying other pipes, but " + p + " hasn't done so";
/*     */     } 
/*  70 */     return (T)r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Pipe original, Pipe copy) {
/*  78 */     assert !this.master2copy.containsKey(original);
/*  79 */     assert original != null && copy != null;
/*  80 */     this.master2copy.put(original, copy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(AbstractTubeImpl original, AbstractTubeImpl copy) {
/*  87 */     add((Tube)original, (Tube)copy);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Tube original, Tube copy) {
/*  92 */     assert !this.master2copy.containsKey(original);
/*  93 */     assert original != null && copy != null;
/*  94 */     this.master2copy.put(original, copy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Tube> T copy(T t) {
/* 100 */     Tube r = (Tube)this.master2copy.get(t);
/* 101 */     if (r == null) {
/* 102 */       if (t != null) {
/* 103 */         r = t.copy(this);
/*     */       }
/* 105 */       else if (LOGGER.isLoggable(Level.FINER)) {
/* 106 */         LOGGER.fine("WARNING, tube passed to 'copy' in " + this + " was null, so no copy was made");
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return (T)r;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/PipeClonerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */