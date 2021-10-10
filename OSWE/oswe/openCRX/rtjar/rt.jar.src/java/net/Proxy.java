/*     */ package java.net;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Proxy
/*     */ {
/*     */   private Type type;
/*     */   private SocketAddress sa;
/*     */   
/*     */   public enum Type
/*     */   {
/*  49 */     DIRECT,
/*     */ 
/*     */ 
/*     */     
/*  53 */     HTTP,
/*     */ 
/*     */ 
/*     */     
/*  57 */     SOCKS;
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
/*  72 */   public static final Proxy NO_PROXY = new Proxy();
/*     */ 
/*     */   
/*     */   private Proxy() {
/*  76 */     this.type = Type.DIRECT;
/*  77 */     this.sa = null;
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
/*     */   public Proxy(Type paramType, SocketAddress paramSocketAddress) {
/*  94 */     if (paramType == Type.DIRECT || !(paramSocketAddress instanceof InetSocketAddress))
/*  95 */       throw new IllegalArgumentException("type " + paramType + " is not compatible with address " + paramSocketAddress); 
/*  96 */     this.type = paramType;
/*  97 */     this.sa = paramSocketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type type() {
/* 106 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress address() {
/* 117 */     return this.sa;
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
/*     */   public String toString() {
/* 129 */     if (type() == Type.DIRECT)
/* 130 */       return "DIRECT"; 
/* 131 */     return type() + " @ " + address();
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
/*     */   public final boolean equals(Object paramObject) {
/* 149 */     if (paramObject == null || !(paramObject instanceof Proxy))
/* 150 */       return false; 
/* 151 */     Proxy proxy = (Proxy)paramObject;
/* 152 */     if (proxy.type() == type()) {
/* 153 */       if (address() == null) {
/* 154 */         return (proxy.address() == null);
/*     */       }
/* 156 */       return address().equals(proxy.address());
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 167 */     if (address() == null)
/* 168 */       return type().hashCode(); 
/* 169 */     return type().hashCode() + address().hashCode();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Proxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */