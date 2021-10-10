/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelectableChannel
/*     */   extends SelectableChannel
/*     */ {
/*     */   private final SelectorProvider provider;
/*  68 */   private SelectionKey[] keys = null;
/*  69 */   private int keyCount = 0;
/*     */ 
/*     */   
/*  72 */   private final Object keyLock = new Object();
/*     */ 
/*     */   
/*  75 */   private final Object regLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean nonBlocking;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSelectableChannel(SelectorProvider paramSelectorProvider) {
/*  87 */     this.provider = paramSelectorProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SelectorProvider provider() {
/*  96 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addKey(SelectionKey paramSelectionKey) {
/* 103 */     assert Thread.holdsLock(this.keyLock);
/* 104 */     int i = 0;
/* 105 */     if (this.keys != null && this.keyCount < this.keys.length) {
/*     */       
/* 107 */       for (i = 0; i < this.keys.length && 
/* 108 */         this.keys[i] != null; i++);
/*     */     }
/* 110 */     else if (this.keys == null) {
/* 111 */       this.keys = new SelectionKey[3];
/*     */     } else {
/*     */       
/* 114 */       int j = this.keys.length * 2;
/* 115 */       SelectionKey[] arrayOfSelectionKey = new SelectionKey[j];
/* 116 */       for (i = 0; i < this.keys.length; i++)
/* 117 */         arrayOfSelectionKey[i] = this.keys[i]; 
/* 118 */       this.keys = arrayOfSelectionKey;
/* 119 */       i = this.keyCount;
/*     */     } 
/* 121 */     this.keys[i] = paramSelectionKey;
/* 122 */     this.keyCount++;
/*     */   }
/*     */   
/*     */   private SelectionKey findKey(Selector paramSelector) {
/* 126 */     synchronized (this.keyLock) {
/* 127 */       if (this.keys == null)
/* 128 */         return null; 
/* 129 */       for (byte b = 0; b < this.keys.length; b++) {
/* 130 */         if (this.keys[b] != null && this.keys[b].selector() == paramSelector)
/* 131 */           return this.keys[b]; 
/* 132 */       }  return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   void removeKey(SelectionKey paramSelectionKey) {
/* 137 */     synchronized (this.keyLock) {
/* 138 */       for (byte b = 0; b < this.keys.length; b++) {
/* 139 */         if (this.keys[b] == paramSelectionKey) {
/* 140 */           this.keys[b] = null;
/* 141 */           this.keyCount--;
/*     */         } 
/* 143 */       }  ((AbstractSelectionKey)paramSelectionKey).invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean haveValidKeys() {
/* 148 */     synchronized (this.keyLock) {
/* 149 */       if (this.keyCount == 0)
/* 150 */         return false; 
/* 151 */       for (byte b = 0; b < this.keys.length; b++) {
/* 152 */         if (this.keys[b] != null && this.keys[b].isValid())
/* 153 */           return true; 
/*     */       } 
/* 155 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isRegistered() {
/* 163 */     synchronized (this.keyLock) {
/* 164 */       return (this.keyCount != 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final SelectionKey keyFor(Selector paramSelector) {
/* 169 */     return findKey(paramSelector);
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
/*     */   public final SelectionKey register(Selector paramSelector, int paramInt, Object paramObject) throws ClosedChannelException {
/* 202 */     synchronized (this.regLock) {
/* 203 */       if (!isOpen())
/* 204 */         throw new ClosedChannelException(); 
/* 205 */       if ((paramInt & (validOps() ^ 0xFFFFFFFF)) != 0)
/* 206 */         throw new IllegalArgumentException(); 
/* 207 */       if (isBlocking())
/* 208 */         throw new IllegalBlockingModeException(); 
/* 209 */       SelectionKey selectionKey = findKey(paramSelector);
/* 210 */       if (selectionKey != null) {
/* 211 */         selectionKey.interestOps(paramInt);
/* 212 */         selectionKey.attach(paramObject);
/*     */       } 
/* 214 */       if (selectionKey == null)
/*     */       {
/* 216 */         synchronized (this.keyLock) {
/* 217 */           if (!isOpen())
/* 218 */             throw new ClosedChannelException(); 
/* 219 */           selectionKey = ((AbstractSelector)paramSelector).register(this, paramInt, paramObject);
/* 220 */           addKey(selectionKey);
/*     */         } 
/*     */       }
/* 223 */       return selectionKey;
/*     */     } 
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
/*     */   protected final void implCloseChannel() throws IOException {
/* 241 */     implCloseSelectableChannel();
/* 242 */     synchronized (this.keyLock) {
/* 243 */       byte b1 = (this.keys == null) ? 0 : this.keys.length;
/* 244 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 245 */         SelectionKey selectionKey = this.keys[b2];
/* 246 */         if (selectionKey != null) {
/* 247 */           selectionKey.cancel();
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */   protected abstract void implCloseSelectableChannel() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isBlocking() {
/* 274 */     return !this.nonBlocking;
/*     */   }
/*     */   
/*     */   public final Object blockingLock() {
/* 278 */     return this.regLock;
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
/*     */   public final SelectableChannel configureBlocking(boolean paramBoolean) throws IOException {
/* 292 */     synchronized (this.regLock) {
/* 293 */       if (!isOpen())
/* 294 */         throw new ClosedChannelException(); 
/* 295 */       boolean bool = !this.nonBlocking;
/* 296 */       if (paramBoolean != bool) {
/* 297 */         if (paramBoolean && haveValidKeys())
/* 298 */           throw new IllegalBlockingModeException(); 
/* 299 */         implConfigureBlocking(paramBoolean);
/* 300 */         this.nonBlocking = !paramBoolean;
/*     */       } 
/*     */     } 
/* 303 */     return this;
/*     */   }
/*     */   
/*     */   protected abstract void implConfigureBlocking(boolean paramBoolean) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/channels/spi/AbstractSelectableChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */