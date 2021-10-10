/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.net.ProtocolFamily;
/*    */ import java.net.SocketOption;
/*    */ import java.net.StandardProtocolFamily;
/*    */ import java.net.StandardSocketOptions;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ class SocketOptionRegistry
/*    */ {
/*    */   private static class RegistryKey
/*    */   {
/*    */     private final SocketOption<?> name;
/*    */     private final ProtocolFamily family;
/*    */     
/*    */     RegistryKey(SocketOption<?> param1SocketOption, ProtocolFamily param1ProtocolFamily) {
/* 41 */       this.name = param1SocketOption;
/* 42 */       this.family = param1ProtocolFamily;
/*    */     }
/*    */     public int hashCode() {
/* 45 */       return this.name.hashCode() + this.family.hashCode();
/*    */     }
/*    */     public boolean equals(Object param1Object) {
/* 48 */       if (param1Object == null) return false; 
/* 49 */       if (!(param1Object instanceof RegistryKey)) return false; 
/* 50 */       RegistryKey registryKey = (RegistryKey)param1Object;
/* 51 */       if (this.name != registryKey.name) return false; 
/* 52 */       if (this.family != registryKey.family) return false; 
/* 53 */       return true;
/*    */     }
/*    */   }
/*    */   
/* 57 */   private static class LazyInitialization { static final Map<SocketOptionRegistry.RegistryKey, OptionKey> options = options();
/*    */     private static Map<SocketOptionRegistry.RegistryKey, OptionKey> options() {
/* 59 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*    */       
/* 61 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_BROADCAST, Net.UNSPEC), new OptionKey(1, 6));
/* 62 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_KEEPALIVE, Net.UNSPEC), new OptionKey(1, 9));
/* 63 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_LINGER, Net.UNSPEC), new OptionKey(1, 13));
/* 64 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_SNDBUF, Net.UNSPEC), new OptionKey(1, 7));
/* 65 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_RCVBUF, Net.UNSPEC), new OptionKey(1, 8));
/* 66 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.SO_REUSEADDR, Net.UNSPEC), new OptionKey(1, 2));
/* 67 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.TCP_NODELAY, Net.UNSPEC), new OptionKey(6, 1));
/* 68 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET), new OptionKey(0, 1));
/* 69 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET), new OptionKey(0, 32));
/* 70 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET), new OptionKey(0, 33));
/* 71 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET), new OptionKey(0, 34));
/* 72 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_TOS, StandardProtocolFamily.INET6), new OptionKey(41, 67));
/* 73 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_IF, StandardProtocolFamily.INET6), new OptionKey(41, 17));
/* 74 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_TTL, StandardProtocolFamily.INET6), new OptionKey(41, 18));
/* 75 */       hashMap.put(new SocketOptionRegistry.RegistryKey(StandardSocketOptions.IP_MULTICAST_LOOP, StandardProtocolFamily.INET6), new OptionKey(41, 19));
/* 76 */       hashMap.put(new SocketOptionRegistry.RegistryKey(ExtendedSocketOption.SO_OOBINLINE, Net.UNSPEC), new OptionKey(1, 10));
/* 77 */       return (Map)hashMap;
/*    */     } }
/*    */   
/*    */   public static OptionKey findOption(SocketOption<?> paramSocketOption, ProtocolFamily paramProtocolFamily) {
/* 81 */     RegistryKey registryKey = new RegistryKey(paramSocketOption, paramProtocolFamily);
/* 82 */     return LazyInitialization.options.get(registryKey);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/SocketOptionRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */