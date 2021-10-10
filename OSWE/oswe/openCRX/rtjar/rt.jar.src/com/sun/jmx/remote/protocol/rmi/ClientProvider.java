/*    */ package com.sun.jmx.remote.protocol.rmi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Map;
/*    */ import javax.management.remote.JMXConnector;
/*    */ import javax.management.remote.JMXConnectorProvider;
/*    */ import javax.management.remote.JMXServiceURL;
/*    */ import javax.management.remote.rmi.RMIConnector;
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
/*    */ public class ClientProvider
/*    */   implements JMXConnectorProvider
/*    */ {
/*    */   public JMXConnector newJMXConnector(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) throws IOException {
/* 42 */     if (!paramJMXServiceURL.getProtocol().equals("rmi")) {
/* 43 */       throw new MalformedURLException("Protocol not rmi: " + paramJMXServiceURL
/* 44 */           .getProtocol());
/*    */     }
/* 46 */     return new RMIConnector(paramJMXServiceURL, paramMap);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/protocol/rmi/ClientProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */