/*     */ package sun.security.smartcardio;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import javax.smartcardio.ATR;
/*     */ import javax.smartcardio.Card;
/*     */ import javax.smartcardio.CardChannel;
/*     */ import javax.smartcardio.CardException;
/*     */ import javax.smartcardio.CardPermission;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CardImpl
/*     */   extends Card
/*     */ {
/*     */   private final TerminalImpl terminal;
/*     */   final long cardId;
/*     */   private final ATR atr;
/*     */   final int protocol;
/*     */   private final ChannelImpl basicChannel;
/*     */   private volatile State state;
/*     */   private volatile Thread exclusiveThread;
/*     */   private static final boolean isWindows;
/*     */   
/*     */   private enum State
/*     */   {
/*  42 */     OK, REMOVED, DISCONNECTED;
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
/*     */   static {
/*  69 */     String str = AccessController.<String>doPrivileged(() -> System.getProperty("os.name"));
/*     */     
/*  71 */     isWindows = str.startsWith("Windows");
/*     */   }
/*     */   CardImpl(TerminalImpl paramTerminalImpl, String paramString) throws PCSCException {
/*     */     boolean bool;
/*  75 */     this.terminal = paramTerminalImpl;
/*  76 */     byte b = 2;
/*     */     
/*  78 */     if (paramString.equals("*")) {
/*  79 */       bool = true;
/*  80 */     } else if (paramString.equalsIgnoreCase("T=0")) {
/*  81 */       bool = true;
/*  82 */     } else if (paramString.equalsIgnoreCase("T=1")) {
/*  83 */       bool = true;
/*  84 */     } else if (paramString.equalsIgnoreCase("direct")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       bool = isWindows ? false : true;
/*     */       
/*  92 */       b = 3;
/*     */     } else {
/*  94 */       throw new IllegalArgumentException("Unsupported protocol " + paramString);
/*     */     } 
/*  96 */     this.cardId = PCSC.SCardConnect(paramTerminalImpl.contextId, paramTerminalImpl.name, b, bool);
/*     */     
/*  98 */     byte[] arrayOfByte1 = new byte[2];
/*  99 */     byte[] arrayOfByte2 = PCSC.SCardStatus(this.cardId, arrayOfByte1);
/* 100 */     this.atr = new ATR(arrayOfByte2);
/* 101 */     this.protocol = arrayOfByte1[1] & 0xFF;
/* 102 */     this.basicChannel = new ChannelImpl(this, 0);
/* 103 */     this.state = State.OK;
/*     */   }
/*     */   
/*     */   void checkState() {
/* 107 */     State state = this.state;
/* 108 */     if (state == State.DISCONNECTED)
/* 109 */       throw new IllegalStateException("Card has been disconnected"); 
/* 110 */     if (state == State.REMOVED) {
/* 111 */       throw new IllegalStateException("Card has been removed");
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isValid() {
/* 116 */     if (this.state != State.OK) {
/* 117 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 121 */       PCSC.SCardStatus(this.cardId, new byte[2]);
/* 122 */       return true;
/* 123 */     } catch (PCSCException pCSCException) {
/* 124 */       this.state = State.REMOVED;
/* 125 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkSecurity(String paramString) {
/* 130 */     SecurityManager securityManager = System.getSecurityManager();
/* 131 */     if (securityManager != null) {
/* 132 */       securityManager.checkPermission(new CardPermission(this.terminal.name, paramString));
/*     */     }
/*     */   }
/*     */   
/*     */   void handleError(PCSCException paramPCSCException) {
/* 137 */     if (paramPCSCException.code == -2146434967) {
/* 138 */       this.state = State.REMOVED;
/*     */     }
/*     */   }
/*     */   
/*     */   public ATR getATR() {
/* 143 */     return this.atr;
/*     */   }
/*     */   
/*     */   public String getProtocol() {
/* 147 */     switch (this.protocol) {
/*     */       case 1:
/* 149 */         return "T=0";
/*     */       case 2:
/* 151 */         return "T=1";
/*     */     } 
/*     */     
/* 154 */     return "Unknown protocol " + this.protocol;
/*     */   }
/*     */ 
/*     */   
/*     */   public CardChannel getBasicChannel() {
/* 159 */     checkSecurity("getBasicChannel");
/* 160 */     checkState();
/* 161 */     return this.basicChannel;
/*     */   }
/*     */   
/*     */   private static int getSW(byte[] paramArrayOfbyte) {
/* 165 */     if (paramArrayOfbyte.length < 2) {
/* 166 */       return -1;
/*     */     }
/* 168 */     int i = paramArrayOfbyte[paramArrayOfbyte.length - 2] & 0xFF;
/* 169 */     int j = paramArrayOfbyte[paramArrayOfbyte.length - 1] & 0xFF;
/* 170 */     return i << 8 | j;
/*     */   }
/*     */   
/* 173 */   private static byte[] commandOpenChannel = new byte[] { 0, 112, 0, 0, 1 };
/*     */   
/*     */   public CardChannel openLogicalChannel() throws CardException {
/* 176 */     checkSecurity("openLogicalChannel");
/* 177 */     checkState();
/* 178 */     checkExclusive();
/*     */     
/*     */     try {
/* 181 */       byte[] arrayOfByte = PCSC.SCardTransmit(this.cardId, this.protocol, commandOpenChannel, 0, commandOpenChannel.length);
/* 182 */       if (arrayOfByte.length != 3 || getSW(arrayOfByte) != 36864) {
/* 183 */         throw new CardException("openLogicalChannel() failed, card response: " + 
/*     */             
/* 185 */             PCSC.toString(arrayOfByte));
/*     */       }
/* 187 */       return new ChannelImpl(this, arrayOfByte[0]);
/* 188 */     } catch (PCSCException pCSCException) {
/* 189 */       handleError(pCSCException);
/* 190 */       throw new CardException("openLogicalChannel() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */   
/*     */   void checkExclusive() throws CardException {
/* 195 */     Thread thread = this.exclusiveThread;
/* 196 */     if (thread == null) {
/*     */       return;
/*     */     }
/* 199 */     if (thread != Thread.currentThread()) {
/* 200 */       throw new CardException("Exclusive access established by another Thread");
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void beginExclusive() throws CardException {
/* 205 */     checkSecurity("exclusive");
/* 206 */     checkState();
/* 207 */     if (this.exclusiveThread != null) {
/* 208 */       throw new CardException("Exclusive access has already been assigned to Thread " + this.exclusiveThread
/*     */           
/* 210 */           .getName());
/*     */     }
/*     */     try {
/* 213 */       PCSC.SCardBeginTransaction(this.cardId);
/* 214 */     } catch (PCSCException pCSCException) {
/* 215 */       handleError(pCSCException);
/* 216 */       throw new CardException("beginExclusive() failed", pCSCException);
/*     */     } 
/* 218 */     this.exclusiveThread = Thread.currentThread();
/*     */   }
/*     */   
/*     */   public synchronized void endExclusive() throws CardException {
/* 222 */     checkState();
/* 223 */     if (this.exclusiveThread != Thread.currentThread()) {
/* 224 */       throw new IllegalStateException("Exclusive access not assigned to current Thread");
/*     */     }
/*     */     
/*     */     try {
/* 228 */       PCSC.SCardEndTransaction(this.cardId, 0);
/* 229 */     } catch (PCSCException pCSCException) {
/* 230 */       handleError(pCSCException);
/* 231 */       throw new CardException("endExclusive() failed", pCSCException);
/*     */     } finally {
/* 233 */       this.exclusiveThread = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] transmitControlCommand(int paramInt, byte[] paramArrayOfbyte) throws CardException {
/* 239 */     checkSecurity("transmitControl");
/* 240 */     checkState();
/* 241 */     checkExclusive();
/* 242 */     if (paramArrayOfbyte == null) {
/* 243 */       throw new NullPointerException();
/*     */     }
/*     */     try {
/* 246 */       return PCSC.SCardControl(this.cardId, paramInt, paramArrayOfbyte);
/*     */     }
/* 248 */     catch (PCSCException pCSCException) {
/* 249 */       handleError(pCSCException);
/* 250 */       throw new CardException("transmitControlCommand() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 255 */   private static final boolean invertReset = Boolean.parseBoolean(
/* 256 */       AccessController.<String>doPrivileged(new GetPropertyAction("sun.security.smartcardio.invertCardReset", "false")));
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect(boolean paramBoolean) throws CardException {
/* 261 */     if (paramBoolean) {
/* 262 */       checkSecurity("reset");
/*     */     }
/* 264 */     if (this.state != State.OK) {
/*     */       return;
/*     */     }
/* 267 */     checkExclusive();
/*     */     
/* 269 */     if (invertReset) {
/* 270 */       paramBoolean = !paramBoolean;
/*     */     }
/*     */     try {
/* 273 */       PCSC.SCardDisconnect(this.cardId, paramBoolean ? 1 : 0);
/* 274 */     } catch (PCSCException pCSCException) {
/* 275 */       throw new CardException("disconnect() failed", pCSCException);
/*     */     } finally {
/* 277 */       this.state = State.DISCONNECTED;
/* 278 */       this.exclusiveThread = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 283 */     return "PC/SC card in " + this.terminal.name + ", protocol " + 
/* 284 */       getProtocol() + ", state " + this.state;
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/* 289 */       if (this.state == State.OK) {
/* 290 */         this.state = State.DISCONNECTED;
/* 291 */         PCSC.SCardDisconnect(this.cardId, 0);
/*     */       } 
/*     */     } finally {
/* 294 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/CardImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */