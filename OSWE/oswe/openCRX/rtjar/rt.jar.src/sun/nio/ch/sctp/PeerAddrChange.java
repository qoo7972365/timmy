/*     */ package sun.nio.ch.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.PeerAddressChangeNotification;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PeerAddrChange
/*     */   extends PeerAddressChangeNotification
/*     */   implements SctpNotification
/*     */ {
/*     */   private static final int SCTP_ADDR_AVAILABLE = 1;
/*     */   private static final int SCTP_ADDR_UNREACHABLE = 2;
/*     */   private static final int SCTP_ADDR_REMOVED = 3;
/*     */   private static final int SCTP_ADDR_ADDED = 4;
/*     */   private static final int SCTP_ADDR_MADE_PRIM = 5;
/*     */   private static final int SCTP_ADDR_CONFIRMED = 6;
/*     */   private Association association;
/*     */   private int assocId;
/*     */   private SocketAddress address;
/*     */   private PeerAddressChangeNotification.AddressChangeEvent event;
/*     */   
/*     */   private PeerAddrChange(int paramInt1, SocketAddress paramSocketAddress, int paramInt2) {
/*  56 */     switch (paramInt2) {
/*     */       case 1:
/*  58 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_AVAILABLE;
/*     */         break;
/*     */       case 2:
/*  61 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_UNREACHABLE;
/*     */         break;
/*     */       case 3:
/*  64 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_REMOVED;
/*     */         break;
/*     */       case 4:
/*  67 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_ADDED;
/*     */         break;
/*     */       case 5:
/*  70 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_MADE_PRIMARY;
/*     */         break;
/*     */       case 6:
/*  73 */         this.event = PeerAddressChangeNotification.AddressChangeEvent.ADDR_CONFIRMED;
/*     */         break;
/*     */       default:
/*  76 */         throw new AssertionError("Unknown event type");
/*     */     } 
/*  78 */     this.assocId = paramInt1;
/*  79 */     this.address = paramSocketAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public int assocId() {
/*  84 */     return this.assocId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAssociation(Association paramAssociation) {
/*  89 */     this.association = paramAssociation;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress address() {
/*  94 */     assert this.address != null;
/*  95 */     return this.address;
/*     */   }
/*     */ 
/*     */   
/*     */   public Association association() {
/* 100 */     assert this.association != null;
/* 101 */     return this.association;
/*     */   }
/*     */ 
/*     */   
/*     */   public PeerAddressChangeNotification.AddressChangeEvent event() {
/* 106 */     assert this.event != null;
/* 107 */     return this.event;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     StringBuilder stringBuilder = new StringBuilder();
/* 113 */     stringBuilder.append(super.toString()).append(" [");
/* 114 */     stringBuilder.append("Address: ").append(this.address);
/* 115 */     stringBuilder.append(", Association:").append(this.association);
/* 116 */     stringBuilder.append(", Event: ").append(this.event).append("]");
/* 117 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/PeerAddrChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */