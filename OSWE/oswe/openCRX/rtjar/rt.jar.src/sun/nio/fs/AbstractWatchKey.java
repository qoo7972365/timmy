/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.Watchable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractWatchKey
/*     */   implements WatchKey
/*     */ {
/*     */   static final int MAX_EVENT_LIST_SIZE = 512;
/*     */   private final AbstractWatchService watcher;
/*     */   private final Path dir;
/*     */   private State state;
/*  45 */   static final Event<Object> OVERFLOW_EVENT = new Event(StandardWatchEventKinds.OVERFLOW, null);
/*     */   private List<WatchEvent<?>> events;
/*     */   private Map<Object, WatchEvent<?>> lastModifyEvents;
/*     */   
/*     */   private enum State
/*     */   {
/*  51 */     READY, SIGNALLED;
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
/*     */   protected AbstractWatchKey(Path paramPath, AbstractWatchService paramAbstractWatchService) {
/*  70 */     this.watcher = paramAbstractWatchService;
/*  71 */     this.dir = paramPath;
/*  72 */     this.state = State.READY;
/*  73 */     this.events = new ArrayList<>();
/*  74 */     this.lastModifyEvents = new HashMap<>();
/*     */   }
/*     */   
/*     */   final AbstractWatchService watcher() {
/*  78 */     return this.watcher;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path watchable() {
/*  86 */     return this.dir;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void signal() {
/*  93 */     synchronized (this) {
/*  94 */       if (this.state == State.READY) {
/*  95 */         this.state = State.SIGNALLED;
/*  96 */         this.watcher.enqueueKey(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void signalEvent(WatchEvent.Kind<?> paramKind, Object paramObject) {
/* 106 */     boolean bool = (paramKind == StandardWatchEventKinds.ENTRY_MODIFY) ? true : false;
/* 107 */     synchronized (this) {
/* 108 */       int i = this.events.size();
/* 109 */       if (i > 0) {
/*     */ 
/*     */         
/* 112 */         WatchEvent<Object> watchEvent = (WatchEvent)this.events.get(i - 1);
/* 113 */         if (watchEvent.kind() == StandardWatchEventKinds.OVERFLOW || (paramKind == watchEvent
/* 114 */           .kind() && 
/* 115 */           Objects.equals(paramObject, watchEvent.context()))) {
/*     */           
/* 117 */           ((Event)watchEvent).increment();
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 123 */         if (!this.lastModifyEvents.isEmpty()) {
/* 124 */           if (bool) {
/* 125 */             WatchEvent<Path> watchEvent1 = (WatchEvent)this.lastModifyEvents.get(paramObject);
/* 126 */             if (watchEvent1 != null) {
/* 127 */               assert watchEvent1.kind() == StandardWatchEventKinds.ENTRY_MODIFY;
/* 128 */               ((Event)watchEvent1).increment();
/*     */ 
/*     */               
/*     */               return;
/*     */             } 
/*     */           } else {
/* 134 */             this.lastModifyEvents.remove(paramObject);
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 140 */         if (i >= 512) {
/* 141 */           paramKind = StandardWatchEventKinds.OVERFLOW;
/* 142 */           bool = false;
/* 143 */           paramObject = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 148 */       Event<?> event = new Event(paramKind, paramObject);
/*     */       
/* 150 */       if (bool) {
/* 151 */         this.lastModifyEvents.put(paramObject, event);
/* 152 */       } else if (paramKind == StandardWatchEventKinds.OVERFLOW) {
/*     */         
/* 154 */         this.events.clear();
/* 155 */         this.lastModifyEvents.clear();
/*     */       } 
/* 157 */       this.events.add(event);
/* 158 */       signal();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<WatchEvent<?>> pollEvents() {
/* 164 */     synchronized (this) {
/* 165 */       List<WatchEvent<?>> list = this.events;
/* 166 */       this.events = new ArrayList<>();
/* 167 */       this.lastModifyEvents.clear();
/* 168 */       return list;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean reset() {
/* 174 */     synchronized (this) {
/* 175 */       if (this.state == State.SIGNALLED && isValid()) {
/* 176 */         if (this.events.isEmpty()) {
/* 177 */           this.state = State.READY;
/*     */         } else {
/*     */           
/* 180 */           this.watcher.enqueueKey(this);
/*     */         } 
/*     */       }
/* 183 */       return isValid();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Event<T>
/*     */     implements WatchEvent<T>
/*     */   {
/*     */     private final WatchEvent.Kind<T> kind;
/*     */     
/*     */     private final T context;
/*     */     
/*     */     private int count;
/*     */     
/*     */     Event(WatchEvent.Kind<T> param1Kind, T param1T) {
/* 198 */       this.kind = param1Kind;
/* 199 */       this.context = param1T;
/* 200 */       this.count = 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public WatchEvent.Kind<T> kind() {
/* 205 */       return this.kind;
/*     */     }
/*     */ 
/*     */     
/*     */     public T context() {
/* 210 */       return this.context;
/*     */     }
/*     */ 
/*     */     
/*     */     public int count() {
/* 215 */       return this.count;
/*     */     }
/*     */ 
/*     */     
/*     */     void increment() {
/* 220 */       this.count++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/AbstractWatchKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */