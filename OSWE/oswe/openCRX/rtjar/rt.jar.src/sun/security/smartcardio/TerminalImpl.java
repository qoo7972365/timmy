/*     */ package sun.security.smartcardio;
/*     */ 
/*     */ import javax.smartcardio.Card;
/*     */ import javax.smartcardio.CardException;
/*     */ import javax.smartcardio.CardNotPresentException;
/*     */ import javax.smartcardio.CardPermission;
/*     */ import javax.smartcardio.CardTerminal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TerminalImpl
/*     */   extends CardTerminal
/*     */ {
/*     */   final long contextId;
/*     */   final String name;
/*     */   private CardImpl card;
/*     */   
/*     */   TerminalImpl(long paramLong, String paramString) {
/*  51 */     this.contextId = paramLong;
/*  52 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  56 */     return this.name;
/*     */   }
/*     */   
/*     */   public synchronized Card connect(String paramString) throws CardException {
/*  60 */     SecurityManager securityManager = System.getSecurityManager();
/*  61 */     if (securityManager != null) {
/*  62 */       securityManager.checkPermission(new CardPermission(this.name, "connect"));
/*     */     }
/*  64 */     if (this.card != null) {
/*  65 */       if (this.card.isValid()) {
/*  66 */         String str = this.card.getProtocol();
/*  67 */         if (paramString.equals("*") || paramString.equalsIgnoreCase(str)) {
/*  68 */           return this.card;
/*     */         }
/*  70 */         throw new CardException("Cannot connect using " + paramString + ", connection already established using " + str);
/*     */       } 
/*     */ 
/*     */       
/*  74 */       this.card = null;
/*     */     } 
/*     */     
/*     */     try {
/*  78 */       this.card = new CardImpl(this, paramString);
/*  79 */       return this.card;
/*  80 */     } catch (PCSCException pCSCException) {
/*  81 */       if (pCSCException.code == -2146434967 || pCSCException.code == -2146435060) {
/*  82 */         throw new CardNotPresentException("No card present", pCSCException);
/*     */       }
/*  84 */       throw new CardException("connect() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCardPresent() throws CardException {
/*     */     try {
/*  91 */       int[] arrayOfInt = PCSC.SCardGetStatusChange(this.contextId, 0L, new int[] { 0 }, new String[] { this.name });
/*     */       
/*  93 */       return ((arrayOfInt[0] & 0x20) != 0);
/*  94 */     } catch (PCSCException pCSCException) {
/*  95 */       throw new CardException("isCardPresent() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean waitForCard(boolean paramBoolean, long paramLong) throws CardException {
/* 100 */     if (paramLong < 0L) {
/* 101 */       throw new IllegalArgumentException("timeout must not be negative");
/*     */     }
/* 103 */     if (paramLong == 0L) {
/* 104 */       paramLong = -1L;
/*     */     }
/* 106 */     int[] arrayOfInt = { 0 };
/* 107 */     String[] arrayOfString = { this.name };
/*     */     
/*     */     try {
/* 110 */       arrayOfInt = PCSC.SCardGetStatusChange(this.contextId, 0L, arrayOfInt, arrayOfString);
/* 111 */       boolean bool = ((arrayOfInt[0] & 0x20) != 0);
/* 112 */       if (paramBoolean == bool) {
/* 113 */         return true;
/*     */       }
/*     */       
/* 116 */       long l = System.currentTimeMillis() + paramLong;
/* 117 */       while (paramBoolean != bool && paramLong != 0L) {
/*     */         
/* 119 */         if (paramLong != -1L) {
/* 120 */           paramLong = Math.max(l - System.currentTimeMillis(), 0L);
/*     */         }
/* 122 */         arrayOfInt = PCSC.SCardGetStatusChange(this.contextId, paramLong, arrayOfInt, arrayOfString);
/* 123 */         bool = ((arrayOfInt[0] & 0x20) != 0);
/*     */       } 
/* 125 */       return (paramBoolean == bool);
/* 126 */     } catch (PCSCException pCSCException) {
/* 127 */       if (pCSCException.code == -2146435062) {
/* 128 */         return false;
/*     */       }
/* 130 */       throw new CardException("waitForCard() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean waitForCardPresent(long paramLong) throws CardException {
/* 136 */     return waitForCard(true, paramLong);
/*     */   }
/*     */   
/*     */   public boolean waitForCardAbsent(long paramLong) throws CardException {
/* 140 */     return waitForCard(false, paramLong);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 144 */     return "PC/SC terminal " + this.name;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/TerminalImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */