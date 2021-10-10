/*    */ package com.sun.jmx.remote.protocol.iiop;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.util.Map;
/*    */ import javax.management.MBeanServer;
/*    */ import javax.management.remote.JMXConnectorServer;
/*    */ import javax.management.remote.JMXConnectorServerProvider;
/*    */ import javax.management.remote.JMXServiceURL;
/*    */ import javax.management.remote.rmi.RMIConnectorServer;
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
/*    */ public class ServerProvider
/*    */   implements JMXConnectorServerProvider
/*    */ {
/*    */   public JMXConnectorServer newJMXConnectorServer(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap, MBeanServer paramMBeanServer) throws IOException {
/* 44 */     if (!paramJMXServiceURL.getProtocol().equals("iiop")) {
/* 45 */       throw new MalformedURLException("Protocol not iiop: " + paramJMXServiceURL
/* 46 */           .getProtocol());
/*    */     }
/* 48 */     return new RMIConnectorServer(paramJMXServiceURL, paramMap, paramMBeanServer);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/protocol/iiop/ServerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */