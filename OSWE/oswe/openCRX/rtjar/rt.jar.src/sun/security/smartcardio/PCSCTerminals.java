/*     */ package sun.security.smartcardio;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.smartcardio.CardException;
/*     */ import javax.smartcardio.CardTerminal;
/*     */ import javax.smartcardio.CardTerminals;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PCSCTerminals
/*     */   extends CardTerminals
/*     */ {
/*     */   private static long contextId;
/*     */   private Map<String, ReaderState> stateMap;
/*     */   
/*     */   static synchronized void initContext() throws PCSCException {
/*  55 */     if (contextId == 0L) {
/*  56 */       contextId = PCSC.SCardEstablishContext(0);
/*     */     }
/*     */   }
/*     */   
/*  60 */   private static final Map<String, Reference<TerminalImpl>> terminals = new HashMap<>();
/*     */ 
/*     */   
/*     */   private static synchronized TerminalImpl implGetTerminal(String paramString) {
/*  64 */     Reference<TerminalImpl> reference = terminals.get(paramString);
/*  65 */     TerminalImpl terminalImpl = (reference != null) ? reference.get() : null;
/*  66 */     if (terminalImpl != null) {
/*  67 */       return terminalImpl;
/*     */     }
/*  69 */     terminalImpl = new TerminalImpl(contextId, paramString);
/*  70 */     terminals.put(paramString, new WeakReference<>(terminalImpl));
/*  71 */     return terminalImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List<CardTerminal> list(CardTerminals.State paramState) throws CardException {
/*  76 */     if (paramState == null) {
/*  77 */       throw new NullPointerException();
/*     */     }
/*     */     try {
/*  80 */       String[] arrayOfString = PCSC.SCardListReaders(contextId);
/*  81 */       ArrayList<TerminalImpl> arrayList = new ArrayList(arrayOfString.length);
/*  82 */       if (this.stateMap == null)
/*     */       {
/*     */         
/*  85 */         if (paramState == CardTerminals.State.CARD_INSERTION) {
/*  86 */           paramState = CardTerminals.State.CARD_PRESENT;
/*  87 */         } else if (paramState == CardTerminals.State.CARD_REMOVAL) {
/*  88 */           paramState = CardTerminals.State.CARD_ABSENT;
/*     */         } 
/*     */       }
/*  91 */       for (String str : arrayOfString) {
/*  92 */         ReaderState readerState; TerminalImpl terminalImpl = implGetTerminal(str);
/*     */         
/*  94 */         switch (paramState) {
/*     */           case ALL:
/*  96 */             arrayList.add(terminalImpl);
/*     */             break;
/*     */           case CARD_PRESENT:
/*  99 */             if (terminalImpl.isCardPresent()) {
/* 100 */               arrayList.add(terminalImpl);
/*     */             }
/*     */             break;
/*     */           case CARD_ABSENT:
/* 104 */             if (!terminalImpl.isCardPresent()) {
/* 105 */               arrayList.add(terminalImpl);
/*     */             }
/*     */             break;
/*     */           case CARD_INSERTION:
/* 109 */             readerState = this.stateMap.get(str);
/* 110 */             if (readerState != null && readerState.isInsertion()) {
/* 111 */               arrayList.add(terminalImpl);
/*     */             }
/*     */             break;
/*     */           case CARD_REMOVAL:
/* 115 */             readerState = this.stateMap.get(str);
/* 116 */             if (readerState != null && readerState.isRemoval()) {
/* 117 */               arrayList.add(terminalImpl);
/*     */             }
/*     */             break;
/*     */           default:
/* 121 */             throw new CardException("Unknown state: " + paramState);
/*     */         } 
/*     */       } 
/* 124 */       return Collections.unmodifiableList((List)arrayList);
/* 125 */     } catch (PCSCException pCSCException) {
/* 126 */       throw new CardException("list() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ReaderState
/*     */   {
/* 133 */     private int current = 0;
/* 134 */     private int previous = 0;
/*     */     
/*     */     int get() {
/* 137 */       return this.current;
/*     */     }
/*     */     void update(int param1Int) {
/* 140 */       this.previous = this.current;
/* 141 */       this.current = param1Int;
/*     */     }
/*     */     boolean isInsertion() {
/* 144 */       return (!present(this.previous) && present(this.current));
/*     */     }
/*     */     boolean isRemoval() {
/* 147 */       return (present(this.previous) && !present(this.current));
/*     */     }
/*     */     static boolean present(int param1Int) {
/* 150 */       return ((param1Int & 0x20) != 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized boolean waitForChange(long paramLong) throws CardException {
/* 155 */     if (paramLong < 0L) {
/* 156 */       throw new IllegalArgumentException("Timeout must not be negative: " + paramLong);
/*     */     }
/*     */     
/* 159 */     if (this.stateMap == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       this.stateMap = new HashMap<>();
/* 165 */       waitForChange(0L);
/*     */     } 
/* 167 */     if (paramLong == 0L) {
/* 168 */       paramLong = -1L;
/*     */     }
/*     */     try {
/* 171 */       String[] arrayOfString = PCSC.SCardListReaders(contextId);
/* 172 */       int i = arrayOfString.length;
/* 173 */       if (i == 0) {
/* 174 */         throw new IllegalStateException("No terminals available");
/*     */       }
/* 176 */       int[] arrayOfInt = new int[i];
/* 177 */       ReaderState[] arrayOfReaderState = new ReaderState[i]; byte b;
/* 178 */       for (b = 0; b < arrayOfString.length; b++) {
/* 179 */         String str = arrayOfString[b];
/* 180 */         ReaderState readerState = this.stateMap.get(str);
/* 181 */         if (readerState == null) {
/* 182 */           readerState = new ReaderState();
/*     */         }
/* 184 */         arrayOfReaderState[b] = readerState;
/* 185 */         arrayOfInt[b] = readerState.get();
/*     */       } 
/* 187 */       arrayOfInt = PCSC.SCardGetStatusChange(contextId, paramLong, arrayOfInt, arrayOfString);
/* 188 */       this.stateMap.clear();
/* 189 */       for (b = 0; b < i; b++) {
/* 190 */         ReaderState readerState = arrayOfReaderState[b];
/* 191 */         readerState.update(arrayOfInt[b]);
/* 192 */         this.stateMap.put(arrayOfString[b], readerState);
/*     */       } 
/* 194 */       return true;
/* 195 */     } catch (PCSCException pCSCException) {
/* 196 */       if (pCSCException.code == -2146435062) {
/* 197 */         return false;
/*     */       }
/* 199 */       throw new CardException("waitForChange() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static List<CardTerminal> waitForCards(List<? extends CardTerminal> paramList, long paramLong, boolean paramBoolean) throws CardException {
/*     */     long l;
/* 210 */     if (paramLong == 0L) {
/* 211 */       paramLong = -1L;
/* 212 */       l = -1L;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 217 */       l = 0L;
/*     */     } 
/*     */     
/* 220 */     String[] arrayOfString = new String[paramList.size()];
/* 221 */     byte b = 0;
/* 222 */     for (CardTerminal cardTerminal : paramList) {
/* 223 */       if (!(cardTerminal instanceof TerminalImpl)) {
/* 224 */         throw new IllegalArgumentException("Invalid terminal type: " + cardTerminal
/* 225 */             .getClass().getName());
/*     */       }
/* 227 */       TerminalImpl terminalImpl = (TerminalImpl)cardTerminal;
/* 228 */       arrayOfString[b++] = terminalImpl.name;
/*     */     } 
/*     */     
/* 231 */     int[] arrayOfInt = new int[arrayOfString.length];
/* 232 */     Arrays.fill(arrayOfInt, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/* 240 */         arrayOfInt = PCSC.SCardGetStatusChange(contextId, l, arrayOfInt, arrayOfString);
/* 241 */         l = paramLong;
/*     */         
/* 243 */         ArrayList<TerminalImpl> arrayList = null;
/* 244 */         for (b = 0; b < arrayOfString.length; b++) {
/* 245 */           boolean bool = ((arrayOfInt[b] & 0x20) != 0);
/* 246 */           if (bool == paramBoolean) {
/* 247 */             if (arrayList == null) {
/* 248 */               arrayList = new ArrayList();
/*     */             }
/* 250 */             arrayList.add(implGetTerminal(arrayOfString[b]));
/*     */           } 
/*     */         } 
/*     */         
/* 254 */         if (arrayList != null) {
/* 255 */           return Collections.unmodifiableList((List)arrayList);
/*     */         }
/*     */       } 
/* 258 */     } catch (PCSCException pCSCException) {
/* 259 */       if (pCSCException.code == -2146435062) {
/* 260 */         return Collections.emptyList();
/*     */       }
/* 262 */       throw new CardException("waitForCard() failed", pCSCException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/PCSCTerminals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */