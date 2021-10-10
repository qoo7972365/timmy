/*    */ package com.sun.xml.internal.ws.addressing.policy;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.internal.ws.policy.spi.PrefixMapper;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddressingPrefixMapper
/*    */   implements PrefixMapper
/*    */ {
/* 43 */   private static final Map<String, String> prefixMap = new HashMap<>();
/*    */   
/*    */   static {
/* 46 */     prefixMap.put(AddressingVersion.MEMBER.policyNsUri, "wsap");
/* 47 */     prefixMap.put(AddressingVersion.MEMBER.nsUri, "wsa");
/* 48 */     prefixMap.put("http://www.w3.org/2007/05/addressing/metadata", "wsam");
/*    */   }
/*    */   
/*    */   public Map<String, String> getPrefixMap() {
/* 52 */     return prefixMap;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/policy/AddressingPrefixMapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */