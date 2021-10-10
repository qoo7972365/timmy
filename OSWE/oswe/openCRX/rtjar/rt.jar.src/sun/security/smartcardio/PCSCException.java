/*    */ package sun.security.smartcardio;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PCSCException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 4181137171979130432L;
/*    */   final int code;
/*    */   
/*    */   PCSCException(int paramInt) {
/* 45 */     super(toErrorString(paramInt));
/* 46 */     this.code = paramInt;
/*    */   }
/*    */   
/*    */   private static String toErrorString(int paramInt) {
/* 50 */     switch (paramInt) { case 0:
/* 51 */         return "SCARD_S_SUCCESS";
/* 52 */       case -2146435070: return "SCARD_E_CANCELLED";
/* 53 */       case -2146435058: return "SCARD_E_CANT_DISPOSE";
/* 54 */       case -2146435064: return "SCARD_E_INSUFFICIENT_BUFFER";
/* 55 */       case -2146435051: return "SCARD_E_INVALID_ATR";
/* 56 */       case -2146435069: return "SCARD_E_INVALID_HANDLE";
/* 57 */       case -2146435068: return "SCARD_E_INVALID_PARAMETER";
/* 58 */       case -2146435067: return "SCARD_E_INVALID_TARGET";
/* 59 */       case -2146435055: return "SCARD_E_INVALID_VALUE";
/* 60 */       case -2146435066: return "SCARD_E_NO_MEMORY";
/* 61 */       case -2146435053: return "SCARD_F_COMM_ERROR";
/* 62 */       case -2146435071: return "SCARD_F_INTERNAL_ERROR";
/* 63 */       case -2146435052: return "SCARD_F_UNKNOWN_ERROR";
/* 64 */       case -2146435065: return "SCARD_F_WAITED_TOO_LONG";
/* 65 */       case -2146435063: return "SCARD_E_UNKNOWN_READER";
/* 66 */       case -2146435062: return "SCARD_E_TIMEOUT";
/* 67 */       case -2146435061: return "SCARD_E_SHARING_VIOLATION";
/* 68 */       case -2146435060: return "SCARD_E_NO_SMARTCARD";
/* 69 */       case -2146435059: return "SCARD_E_UNKNOWN_CARD";
/* 70 */       case -2146435057: return "SCARD_E_PROTO_MISMATCH";
/* 71 */       case -2146435056: return "SCARD_E_NOT_READY";
/* 72 */       case -2146435054: return "SCARD_E_SYSTEM_CANCELLED";
/* 73 */       case -2146435050: return "SCARD_E_NOT_TRANSACTED";
/* 74 */       case -2146435049: return "SCARD_E_READER_UNAVAILABLE";
/*    */       case -2146434971:
/* 76 */         return "SCARD_W_UNSUPPORTED_CARD";
/* 77 */       case -2146434970: return "SCARD_W_UNRESPONSIVE_CARD";
/* 78 */       case -2146434969: return "SCARD_W_UNPOWERED_CARD";
/* 79 */       case -2146434968: return "SCARD_W_RESET_CARD";
/* 80 */       case -2146434967: return "SCARD_W_REMOVED_CARD";
/* 81 */       case -2146434966: return "SCARD_W_INSERTED_CARD";
/*    */       case -2146435041:
/* 83 */         return "SCARD_E_UNSUPPORTED_FEATURE";
/* 84 */       case -2146435047: return "SCARD_E_PCI_TOO_SMALL";
/* 85 */       case -2146435046: return "SCARD_E_READER_UNSUPPORTED";
/* 86 */       case -2146435045: return "SCARD_E_DUPLICATE_READER";
/* 87 */       case -2146435044: return "SCARD_E_CARD_UNSUPPORTED";
/* 88 */       case -2146435043: return "SCARD_E_NO_SERVICE";
/* 89 */       case -2146435042: return "SCARD_E_SERVICE_STOPPED";
/*    */       case -2146435026:
/* 91 */         return "SCARD_E_NO_READERS_AVAILABLE";
/* 92 */       case 6: return "WINDOWS_ERROR_INVALID_HANDLE";
/* 93 */       case 87: return "WINDOWS_ERROR_INVALID_PARAMETER"; }
/*    */     
/* 95 */     return "Unknown error 0x" + Integer.toHexString(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/smartcardio/PCSCException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */