/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.ClosedWatchServiceException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPoller
/*     */   implements Runnable
/*     */ {
/*  50 */   private final LinkedList<Request> requestList = new LinkedList<>();
/*     */ 
/*     */   
/*     */   private boolean shutdown = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  58 */     final AbstractPoller thisRunnable = this;
/*  59 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*  62 */             Thread thread = new Thread(thisRunnable);
/*  63 */             thread.setDaemon(true);
/*  64 */             thread.start();
/*  65 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void wakeup() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract Object implRegister(Path paramPath, Set<? extends WatchEvent.Kind<?>> paramSet, WatchEvent.Modifier... paramVarArgs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implCancelKey(WatchKey paramWatchKey);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void implCloseAll();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final WatchKey register(Path paramPath, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException {
/* 101 */     if (paramPath == null)
/* 102 */       throw new NullPointerException(); 
/* 103 */     HashSet<WatchEvent.Kind<?>> hashSet = new HashSet(paramArrayOfKind.length);
/* 104 */     for (WatchEvent.Kind<?> kind : paramArrayOfKind) {
/*     */       
/* 106 */       if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY || kind == StandardWatchEventKinds.ENTRY_DELETE) {
/*     */ 
/*     */ 
/*     */         
/* 110 */         hashSet.add(kind);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 115 */       else if (kind != StandardWatchEventKinds.OVERFLOW) {
/*     */ 
/*     */ 
/*     */         
/* 119 */         if (kind == null)
/* 120 */           throw new NullPointerException("An element in event set is 'null'"); 
/* 121 */         throw new UnsupportedOperationException(kind.name());
/*     */       } 
/* 123 */     }  if (hashSet.isEmpty())
/* 124 */       throw new IllegalArgumentException("No events to register"); 
/* 125 */     return (WatchKey)invoke(RequestType.REGISTER, new Object[] { paramPath, hashSet, paramVarArgs });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void cancel(WatchKey paramWatchKey) {
/*     */     try {
/* 133 */       invoke(RequestType.CANCEL, new Object[] { paramWatchKey });
/* 134 */     } catch (IOException iOException) {
/*     */       
/* 136 */       throw new AssertionError(iOException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void close() throws IOException {
/* 144 */     invoke(RequestType.CLOSE, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private enum RequestType
/*     */   {
/* 151 */     REGISTER,
/* 152 */     CANCEL,
/* 153 */     CLOSE;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Request
/*     */   {
/*     */     private final AbstractPoller.RequestType type;
/*     */     
/*     */     private final Object[] params;
/*     */     
/*     */     private boolean completed = false;
/* 164 */     private Object result = null;
/*     */     
/*     */     Request(AbstractPoller.RequestType param1RequestType, Object... param1VarArgs) {
/* 167 */       this.type = param1RequestType;
/* 168 */       this.params = param1VarArgs;
/*     */     }
/*     */     
/*     */     AbstractPoller.RequestType type() {
/* 172 */       return this.type;
/*     */     }
/*     */     
/*     */     Object[] parameters() {
/* 176 */       return this.params;
/*     */     }
/*     */     
/*     */     void release(Object param1Object) {
/* 180 */       synchronized (this) {
/* 181 */         this.completed = true;
/* 182 */         this.result = param1Object;
/* 183 */         notifyAll();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object awaitResult() {
/* 192 */       boolean bool = false;
/* 193 */       synchronized (this) {
/* 194 */         while (!this.completed) {
/*     */           try {
/* 196 */             wait();
/* 197 */           } catch (InterruptedException interruptedException) {
/* 198 */             bool = true;
/*     */           } 
/*     */         } 
/* 201 */         if (bool)
/* 202 */           Thread.currentThread().interrupt(); 
/* 203 */         return this.result;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object invoke(RequestType paramRequestType, Object... paramVarArgs) throws IOException {
/* 213 */     Request request = new Request(paramRequestType, paramVarArgs);
/* 214 */     synchronized (this.requestList) {
/* 215 */       if (this.shutdown) {
/* 216 */         throw new ClosedWatchServiceException();
/*     */       }
/* 218 */       this.requestList.add(request);
/*     */     } 
/*     */ 
/*     */     
/* 222 */     wakeup();
/*     */ 
/*     */     
/* 225 */     Object object = request.awaitResult();
/*     */     
/* 227 */     if (object instanceof RuntimeException)
/* 228 */       throw (RuntimeException)object; 
/* 229 */     if (object instanceof IOException)
/* 230 */       throw (IOException)object; 
/* 231 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean processRequests() {
/* 241 */     synchronized (this.requestList) {
/*     */       Request request;
/* 243 */       while ((request = this.requestList.poll()) != null) {
/*     */         Object[] arrayOfObject; Path path; WatchKey watchKey; Set<? extends WatchEvent.Kind<?>> set; WatchEvent.Modifier[] arrayOfModifier;
/* 245 */         if (this.shutdown) {
/* 246 */           request.release(new ClosedWatchServiceException());
/*     */         }
/*     */         
/* 249 */         switch (request.type()) {
/*     */ 
/*     */ 
/*     */           
/*     */           case REGISTER:
/* 254 */             arrayOfObject = request.parameters();
/* 255 */             path = (Path)arrayOfObject[0];
/* 256 */             set = (Set)arrayOfObject[1];
/*     */             
/* 258 */             arrayOfModifier = (WatchEvent.Modifier[])arrayOfObject[2];
/*     */             
/* 260 */             request.release(implRegister(path, set, arrayOfModifier));
/*     */             continue;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case CANCEL:
/* 267 */             arrayOfObject = request.parameters();
/* 268 */             watchKey = (WatchKey)arrayOfObject[0];
/* 269 */             implCancelKey(watchKey);
/* 270 */             request.release(null);
/*     */             continue;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case CLOSE:
/* 277 */             implCloseAll();
/* 278 */             request.release(null);
/* 279 */             this.shutdown = true;
/*     */             continue;
/*     */         } 
/*     */ 
/*     */         
/* 284 */         request.release(new IOException("request not recognized"));
/*     */       } 
/*     */     } 
/*     */     
/* 288 */     return this.shutdown;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/AbstractPoller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */