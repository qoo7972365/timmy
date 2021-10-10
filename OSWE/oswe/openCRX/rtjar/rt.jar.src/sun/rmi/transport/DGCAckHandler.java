/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.rmi.server.UID;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.rmi.runtime.RuntimeUtil;
/*     */ import sun.security.action.GetLongAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DGCAckHandler
/*     */ {
/*  68 */   private static final long dgcAckTimeout = ((Long)AccessController.<Long>doPrivileged(new GetLongAction("sun.rmi.dgc.ackTimeout", 300000L))).longValue();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private static final ScheduledExecutorService scheduler = ((RuntimeUtil)AccessController.<RuntimeUtil>doPrivileged(new RuntimeUtil.GetInstanceAction()))
/*  74 */     .getScheduler();
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static final Map<UID, DGCAckHandler> idTable = Collections.synchronizedMap(new HashMap<>());
/*     */   
/*     */   private final UID id;
/*  81 */   private List<Object> objList = new ArrayList();
/*  82 */   private Future<?> task = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DGCAckHandler(UID paramUID) {
/*  96 */     this.id = paramUID;
/*  97 */     if (paramUID != null) {
/*  98 */       assert !idTable.containsKey(paramUID);
/*  99 */       idTable.put(paramUID, this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void add(Object paramObject) {
/* 107 */     if (this.objList != null) {
/* 108 */       this.objList.add(paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void startTimer() {
/* 118 */     if (this.objList != null && this.task == null) {
/* 119 */       this.task = scheduler.schedule(new Runnable() {
/*     */             public void run() {
/* 121 */               if (DGCAckHandler.this.id != null) {
/* 122 */                 DGCAckHandler.idTable.remove(DGCAckHandler.this.id);
/*     */               }
/* 124 */               DGCAckHandler.this.release();
/*     */             }
/*     */           },  dgcAckTimeout, TimeUnit.MILLISECONDS);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void release() {
/* 134 */     if (this.task != null) {
/* 135 */       this.task.cancel(false);
/* 136 */       this.task = null;
/*     */     } 
/* 138 */     this.objList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void received(UID paramUID) {
/* 146 */     DGCAckHandler dGCAckHandler = idTable.remove(paramUID);
/* 147 */     if (dGCAckHandler != null)
/* 148 */       dGCAckHandler.release(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/DGCAckHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */