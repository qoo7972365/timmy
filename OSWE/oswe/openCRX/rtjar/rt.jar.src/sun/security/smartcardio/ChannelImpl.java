/*     */ package sun.security.smartcardio;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.security.AccessController;
/*     */ import javax.smartcardio.Card;
/*     */ import javax.smartcardio.CardChannel;
/*     */ import javax.smartcardio.CardException;
/*     */ import javax.smartcardio.CommandAPDU;
/*     */ import javax.smartcardio.ResponseAPDU;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ChannelImpl
/*     */   extends CardChannel
/*     */ {
/*     */   private final CardImpl card;
/*     */   private final int channel;
/*     */   private volatile boolean isClosed;
/*     */   
/*     */   ChannelImpl(CardImpl paramCardImpl, int paramInt) {
/*  55 */     this.card = paramCardImpl;
/*  56 */     this.channel = paramInt;
/*     */   }
/*     */   
/*     */   void checkClosed() {
/*  60 */     this.card.checkState();
/*  61 */     if (this.isClosed) {
/*  62 */       throw new IllegalStateException("Logical channel has been closed");
/*     */     }
/*     */   }
/*     */   
/*     */   public Card getCard() {
/*  67 */     return this.card;
/*     */   }
/*     */   
/*     */   public int getChannelNumber() {
/*  71 */     checkClosed();
/*  72 */     return this.channel;
/*     */   }
/*     */   
/*     */   private static void checkManageChannel(byte[] paramArrayOfbyte) {
/*  76 */     if (paramArrayOfbyte.length < 4) {
/*  77 */       throw new IllegalArgumentException("Command APDU must be at least 4 bytes long");
/*     */     }
/*     */     
/*  80 */     if (paramArrayOfbyte[0] >= 0 && paramArrayOfbyte[1] == 112) {
/*  81 */       throw new IllegalArgumentException("Manage channel command not allowed, use openLogicalChannel()");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ResponseAPDU transmit(CommandAPDU paramCommandAPDU) throws CardException {
/*  87 */     checkClosed();
/*  88 */     this.card.checkExclusive();
/*  89 */     byte[] arrayOfByte1 = paramCommandAPDU.getBytes();
/*  90 */     byte[] arrayOfByte2 = doTransmit(arrayOfByte1);
/*  91 */     return new ResponseAPDU(arrayOfByte2);
/*     */   }
/*     */   
/*     */   public int transmit(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws CardException {
/*  95 */     checkClosed();
/*  96 */     this.card.checkExclusive();
/*  97 */     if (paramByteBuffer1 == null || paramByteBuffer2 == null) {
/*  98 */       throw new NullPointerException();
/*     */     }
/* 100 */     if (paramByteBuffer2.isReadOnly()) {
/* 101 */       throw new ReadOnlyBufferException();
/*     */     }
/* 103 */     if (paramByteBuffer1 == paramByteBuffer2) {
/* 104 */       throw new IllegalArgumentException("command and response must not be the same object");
/*     */     }
/*     */     
/* 107 */     if (paramByteBuffer2.remaining() < 258) {
/* 108 */       throw new IllegalArgumentException("Insufficient space in response buffer");
/*     */     }
/*     */     
/* 111 */     byte[] arrayOfByte1 = new byte[paramByteBuffer1.remaining()];
/* 112 */     paramByteBuffer1.get(arrayOfByte1);
/* 113 */     byte[] arrayOfByte2 = doTransmit(arrayOfByte1);
/* 114 */     paramByteBuffer2.put(arrayOfByte2);
/* 115 */     return arrayOfByte2.length;
/*     */   }
/*     */ 
/*     */   
/* 119 */   private static final boolean t0GetResponse = getBooleanProperty("sun.security.smartcardio.t0GetResponse", true);
/*     */ 
/*     */   
/* 122 */   private static final boolean t1GetResponse = getBooleanProperty("sun.security.smartcardio.t1GetResponse", true);
/*     */ 
/*     */   
/* 125 */   private static final boolean t1StripLe = getBooleanProperty("sun.security.smartcardio.t1StripLe", false);
/*     */   
/*     */   private static boolean getBooleanProperty(String paramString, boolean paramBoolean) {
/* 128 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction(paramString));
/* 129 */     if (str == null) {
/* 130 */       return paramBoolean;
/*     */     }
/* 132 */     if (str.equalsIgnoreCase("true"))
/* 133 */       return true; 
/* 134 */     if (str.equalsIgnoreCase("false")) {
/* 135 */       return false;
/*     */     }
/* 137 */     throw new IllegalArgumentException(paramString + " must be either 'true' or 'false'");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] concat(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt) {
/* 143 */     int i = paramArrayOfbyte1.length;
/* 144 */     if (i == 0 && paramInt == paramArrayOfbyte2.length) {
/* 145 */       return paramArrayOfbyte2;
/*     */     }
/* 147 */     byte[] arrayOfByte = new byte[i + paramInt];
/* 148 */     System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, i);
/* 149 */     System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, i, paramInt);
/* 150 */     return arrayOfByte;
/*     */   }
/*     */   
/* 153 */   private static final byte[] B0 = new byte[0];
/*     */   
/*     */   private byte[] doTransmit(byte[] paramArrayOfbyte) throws CardException {
/*     */     try {
/*     */       byte[] arrayOfByte2;
/*     */       int j;
/* 159 */       checkManageChannel(paramArrayOfbyte);
/* 160 */       setChannel(paramArrayOfbyte);
/* 161 */       int i = paramArrayOfbyte.length;
/* 162 */       boolean bool1 = (this.card.protocol == 1) ? true : false;
/* 163 */       boolean bool2 = (this.card.protocol == 2) ? true : false;
/* 164 */       if (bool1 && i >= 7 && paramArrayOfbyte[4] == 0) {
/* 165 */         throw new CardException("Extended length forms not supported for T=0");
/*     */       }
/*     */       
/* 168 */       if ((bool1 || (bool2 && t1StripLe)) && i >= 7) {
/* 169 */         int k = paramArrayOfbyte[4] & 0xFF;
/* 170 */         if (k != 0) {
/* 171 */           if (i == k + 6) {
/* 172 */             i--;
/*     */           }
/*     */         } else {
/* 175 */           k = (paramArrayOfbyte[5] & 0xFF) << 8 | paramArrayOfbyte[6] & 0xFF;
/* 176 */           if (i == k + 9) {
/* 177 */             i -= 2;
/*     */           }
/*     */         } 
/*     */       } 
/* 181 */       boolean bool3 = ((bool1 && t0GetResponse) || (bool2 && t1GetResponse)) ? true : false;
/* 182 */       byte b = 0;
/* 183 */       byte[] arrayOfByte1 = B0;
/*     */       while (true) {
/* 185 */         if (++b >= 32) {
/* 186 */           throw new CardException("Could not obtain response");
/*     */         }
/*     */         
/* 189 */         arrayOfByte2 = PCSC.SCardTransmit(this.card.cardId, this.card.protocol, paramArrayOfbyte, 0, i);
/* 190 */         j = arrayOfByte2.length;
/* 191 */         if (bool3 && j >= 2) {
/*     */           
/* 193 */           if (j == 2 && arrayOfByte2[0] == 108) {
/*     */             
/* 195 */             paramArrayOfbyte[i - 1] = arrayOfByte2[1];
/*     */             continue;
/*     */           } 
/* 198 */           if (arrayOfByte2[j - 2] == 97) {
/*     */ 
/*     */             
/* 201 */             if (j > 2) {
/* 202 */               arrayOfByte1 = concat(arrayOfByte1, arrayOfByte2, j - 2);
/*     */             }
/* 204 */             paramArrayOfbyte[1] = -64;
/* 205 */             paramArrayOfbyte[2] = 0;
/* 206 */             paramArrayOfbyte[3] = 0;
/* 207 */             paramArrayOfbyte[4] = arrayOfByte2[j - 1];
/* 208 */             i = 5; continue;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       } 
/* 213 */       arrayOfByte1 = concat(arrayOfByte1, arrayOfByte2, j);
/*     */ 
/*     */       
/* 216 */       return arrayOfByte1;
/* 217 */     } catch (PCSCException pCSCException) {
/* 218 */       this.card.handleError(pCSCException);
/* 219 */       throw new CardException(pCSCException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getSW(byte[] paramArrayOfbyte) throws CardException {
/* 224 */     if (paramArrayOfbyte.length < 2) {
/* 225 */       throw new CardException("Invalid response length: " + paramArrayOfbyte.length);
/*     */     }
/* 227 */     int i = paramArrayOfbyte[paramArrayOfbyte.length - 2] & 0xFF;
/* 228 */     int j = paramArrayOfbyte[paramArrayOfbyte.length - 1] & 0xFF;
/* 229 */     return i << 8 | j;
/*     */   }
/*     */   
/*     */   private static boolean isOK(byte[] paramArrayOfbyte) throws CardException {
/* 233 */     return (paramArrayOfbyte.length == 2 && getSW(paramArrayOfbyte) == 36864);
/*     */   }
/*     */   
/*     */   private void setChannel(byte[] paramArrayOfbyte) {
/* 237 */     byte b = paramArrayOfbyte[0];
/* 238 */     if (b < 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 244 */     if ((b & 0xE0) == 32) {
/*     */       return;
/*     */     }
/*     */     
/* 248 */     if (this.channel <= 3) {
/*     */ 
/*     */       
/* 251 */       paramArrayOfbyte[0] = (byte)(paramArrayOfbyte[0] & 0xBC);
/* 252 */       paramArrayOfbyte[0] = (byte)(paramArrayOfbyte[0] | this.channel);
/* 253 */     } else if (this.channel <= 19) {
/*     */ 
/*     */       
/* 256 */       paramArrayOfbyte[0] = (byte)(paramArrayOfbyte[0] & 0xB0);
/* 257 */       paramArrayOfbyte[0] = (byte)(paramArrayOfbyte[0] | 0x40);
/* 258 */       paramArrayOfbyte[0] = (byte)(paramArrayOfbyte[0] | this.channel - 4);
/*     */     } else {
/* 260 */       throw new RuntimeException("Unsupported channel number: " + this.channel);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws CardException {
/* 265 */     if (getChannelNumber() == 0) {
/* 266 */       throw new IllegalStateException("Cannot close basic logical channel");
/*     */     }
/* 268 */     if (this.isClosed) {
/*     */       return;
/*     */     }
/* 271 */     this.card.checkExclusive();
/*     */     try {
/* 273 */       byte[] arrayOfByte1 = { 0, 112, Byte.MIN_VALUE, 0 };
/* 274 */       arrayOfByte1[3] = (byte)getChannelNumber();
/* 275 */       setChannel(arrayOfByte1);
/* 276 */       byte[] arrayOfByte2 = PCSC.SCardTransmit(this.card.cardId, this.card.protocol, arrayOfByte1, 0, arrayOfByte1.length);
/* 277 */       if (!isOK(arrayOfByte2)) {
/* 278 */         throw new CardException("close() failed: " + PCSC.toString(arrayOfByte2));
/*     */       }
/* 280 */     } catch (PCSCException pCSCException) {
/* 281 */       this.card.handleError(pCSCException);
/* 282 */       throw new CardException("Could not close channel", pCSCException);
/*     */     } finally {
/* 284 */       this.isClosed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 289 */     return "PC/SC channel " + this.channel;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/ChannelImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */