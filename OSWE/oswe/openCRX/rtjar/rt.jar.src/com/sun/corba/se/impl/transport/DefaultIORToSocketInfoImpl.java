/*    */ package com.sun.corba.se.impl.transport;
/*    */ 
/*    */ import com.sun.corba.se.spi.ior.IOR;
/*    */ import com.sun.corba.se.spi.ior.iiop.AlternateIIOPAddressComponent;
/*    */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*    */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*    */ import com.sun.corba.se.spi.transport.IORToSocketInfo;
/*    */ import com.sun.corba.se.spi.transport.SocketInfo;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
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
/*    */ public class DefaultIORToSocketInfoImpl
/*    */   implements IORToSocketInfo
/*    */ {
/*    */   public List getSocketInfo(IOR paramIOR) {
/* 49 */     ArrayList<SocketInfo> arrayList = new ArrayList();
/*    */ 
/*    */     
/* 52 */     IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)paramIOR.getProfile().getTaggedProfileTemplate();
/* 53 */     IIOPAddress iIOPAddress = iIOPProfileTemplate.getPrimaryAddress();
/* 54 */     String str = iIOPAddress.getHost().toLowerCase();
/* 55 */     int i = iIOPAddress.getPort();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     SocketInfo socketInfo = createSocketInfo(str, i);
/* 62 */     arrayList.add(socketInfo);
/*    */     
/* 64 */     Iterator<AlternateIIOPAddressComponent> iterator = iIOPProfileTemplate.iteratorById(3);
/*    */ 
/*    */     
/* 67 */     while (iterator.hasNext()) {
/*    */       
/* 69 */       AlternateIIOPAddressComponent alternateIIOPAddressComponent = iterator.next();
/* 70 */       str = alternateIIOPAddressComponent.getAddress().getHost().toLowerCase();
/* 71 */       i = alternateIIOPAddressComponent.getAddress().getPort();
/* 72 */       socketInfo = createSocketInfo(str, i);
/* 73 */       arrayList.add(socketInfo);
/*    */     } 
/* 75 */     return arrayList;
/*    */   }
/*    */ 
/*    */   
/*    */   private SocketInfo createSocketInfo(final String hostname, final int port) {
/* 80 */     return new SocketInfo() {
/* 81 */         public String getType() { return "IIOP_CLEAR_TEXT"; }
/* 82 */         public String getHost() { return hostname; } public int getPort() {
/* 83 */           return port;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/DefaultIORToSocketInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */