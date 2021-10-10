/*      */ package com.sun.corba.se.impl.orb;
/*      */ 
/*      */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*      */ import com.sun.corba.se.impl.legacy.connection.USLPort;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.oa.poa.BadServerIdHandler;
/*      */ import com.sun.corba.se.impl.transport.DefaultIORToSocketInfoImpl;
/*      */ import com.sun.corba.se.impl.transport.DefaultSocketFactoryImpl;
/*      */ import com.sun.corba.se.pept.broker.Broker;
/*      */ import com.sun.corba.se.pept.encoding.InputObject;
/*      */ import com.sun.corba.se.pept.encoding.OutputObject;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.transport.Acceptor;
/*      */ import com.sun.corba.se.pept.transport.Connection;
/*      */ import com.sun.corba.se.pept.transport.ContactInfo;
/*      */ import com.sun.corba.se.pept.transport.EventHandler;
/*      */ import com.sun.corba.se.pept.transport.InboundConnectionCache;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.ObjectKey;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.legacy.connection.ORBSocketFactory;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.Operation;
/*      */ import com.sun.corba.se.spi.orb.OperationFactory;
/*      */ import com.sun.corba.se.spi.orb.ParserData;
/*      */ import com.sun.corba.se.spi.orb.ParserDataFactory;
/*      */ import com.sun.corba.se.spi.orb.StringPair;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfoListFactory;
/*      */ import com.sun.corba.se.spi.transport.IIOPPrimaryToContactInfo;
/*      */ import com.sun.corba.se.spi.transport.IORToSocketInfo;
/*      */ import com.sun.corba.se.spi.transport.ORBSocketFactory;
/*      */ import com.sun.corba.se.spi.transport.ReadTimeouts;
/*      */ import com.sun.corba.se.spi.transport.SocketInfo;
/*      */ import com.sun.corba.se.spi.transport.TransportDefault;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.omg.CORBA.LocalObject;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.PortableInterceptor.ORBInitInfo;
/*      */ import org.omg.PortableInterceptor.ORBInitializer;
/*      */ import sun.corba.SharedSecrets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ParserTable
/*      */ {
/*   95 */   private static String MY_CLASS_NAME = ParserTable.class.getName();
/*      */   
/*   97 */   private static ParserTable myInstance = new ParserTable();
/*      */   
/*      */   private ORBUtilSystemException wrapper;
/*      */   private ParserData[] parserData;
/*      */   
/*      */   public static ParserTable get() {
/*  103 */     return myInstance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ParserData[] getParserData() {
/*  110 */     ParserData[] arrayOfParserData = new ParserData[this.parserData.length];
/*  111 */     System.arraycopy(this.parserData, 0, arrayOfParserData, 0, this.parserData.length);
/*  112 */     return arrayOfParserData;
/*      */   }
/*      */   
/*      */   private ParserTable() {
/*  116 */     this.wrapper = ORBUtilSystemException.get("orb.lifecycle");
/*      */     
/*  118 */     String str1 = "65537,65801,65568";
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  123 */     String[] arrayOfString = { "subcontract", "poa", "transport" };
/*      */     
/*  125 */     USLPort[] arrayOfUSLPort = { new USLPort("FOO", 2701), new USLPort("BAR", 3333) };
/*      */ 
/*      */     
/*  128 */     ReadTimeouts readTimeouts = TransportDefault.makeReadTimeoutsFactory().create(100, 3000, 300, 20);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  134 */     ORBInitializer[] arrayOfORBInitializer = { null, new TestORBInitializer1(), new TestORBInitializer2() };
/*      */ 
/*      */ 
/*      */     
/*  138 */     StringPair[] arrayOfStringPair1 = { new StringPair("foo.bar.blech.NonExistent", "dummy"), new StringPair(MY_CLASS_NAME + "$TestORBInitializer1", "dummy"), new StringPair(MY_CLASS_NAME + "$TestORBInitializer2", "dummy") };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  143 */     Acceptor[] arrayOfAcceptor = { new TestAcceptor2(), new TestAcceptor1(), null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  149 */     StringPair[] arrayOfStringPair2 = { new StringPair("foo.bar.blech.NonExistent", "dummy"), new StringPair(MY_CLASS_NAME + "$TestAcceptor1", "dummy"), new StringPair(MY_CLASS_NAME + "$TestAcceptor2", "dummy") };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     StringPair[] arrayOfStringPair3 = { new StringPair("Foo", "ior:930492049394"), new StringPair("Bar", "ior:3453465785633576") };
/*      */ 
/*      */ 
/*      */     
/*  158 */     URL uRL = null;
/*  159 */     String str2 = "corbaloc::camelot/NameService";
/*      */     
/*      */     try {
/*  162 */       uRL = new URL(str2);
/*  163 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     ParserData[] arrayOfParserData = { ParserDataFactory.make("com.sun.CORBA.ORBDebug", OperationFactory.listAction(",", OperationFactory.stringAction()), "debugFlags", new String[0], arrayOfString, "subcontract,poa,transport"), ParserDataFactory.make("org.omg.CORBA.ORBInitialHost", OperationFactory.stringAction(), "ORBInitialHost", "", "Foo", "Foo"), ParserDataFactory.make("org.omg.CORBA.ORBInitialPort", OperationFactory.integerAction(), "ORBInitialPort", new Integer(900), new Integer(27314), "27314"), ParserDataFactory.make("com.sun.CORBA.ORBServerHost", OperationFactory.stringAction(), "ORBServerHost", "", "camelot", "camelot"), ParserDataFactory.make("com.sun.CORBA.ORBServerPort", OperationFactory.integerAction(), "ORBServerPort", new Integer(0), new Integer(38143), "38143"), ParserDataFactory.make("com.sun.CORBA.INTERNAL USE ONLY: listen on all interfaces", OperationFactory.stringAction(), "listenOnAllInterfaces", "com.sun.CORBA.INTERNAL USE ONLY: listen on all interfaces", "foo", "foo"), ParserDataFactory.make("org.omg.CORBA.ORBId", OperationFactory.stringAction(), "orbId", "", "foo", "foo"), ParserDataFactory.make("com.sun.CORBA.ORBid", OperationFactory.stringAction(), "orbId", "", "foo", "foo"), ParserDataFactory.make("org.omg.CORBA.ORBServerId", OperationFactory.integerAction(), "persistentServerId", new Integer(-1), new Integer(1234), "1234"), ParserDataFactory.make("org.omg.CORBA.ORBServerId", OperationFactory.setFlagAction(), "persistentServerIdInitialized", Boolean.FALSE, Boolean.TRUE, "1234"), ParserDataFactory.make("org.omg.CORBA.ORBServerId", OperationFactory.setFlagAction(), "orbServerIdPropertySpecified", Boolean.FALSE, Boolean.TRUE, "1234"), ParserDataFactory.make("com.sun.CORBA.connection.ORBHighWaterMark", OperationFactory.integerAction(), "highWaterMark", new Integer(240), new Integer(3745), "3745"), ParserDataFactory.make("com.sun.CORBA.connection.ORBLowWaterMark", OperationFactory.integerAction(), "lowWaterMark", new Integer(100), new Integer(12), "12"), ParserDataFactory.make("com.sun.CORBA.connection.ORBNumberToReclaim", OperationFactory.integerAction(), "numberToReclaim", new Integer(5), new Integer(231), "231"), ParserDataFactory.make("com.sun.CORBA.giop.ORBGIOPVersion", makeGVOperation(), "giopVersion", GIOPVersion.DEFAULT_VERSION, new GIOPVersion(2, 3), "2.3"), ParserDataFactory.make("com.sun.CORBA.giop.ORBFragmentSize", makeFSOperation(), "giopFragmentSize", new Integer(1024), new Integer(65536), "65536"), ParserDataFactory.make("com.sun.CORBA.giop.ORBBufferSize", OperationFactory.integerAction(), "giopBufferSize", new Integer(1024), new Integer(234000), "234000"), ParserDataFactory.make("com.sun.CORBA.giop.ORBGIOP11BuffMgr", makeBMGROperation(), "giop11BuffMgr", new Integer(0), new Integer(1), "CLCT"), ParserDataFactory.make("com.sun.CORBA.giop.ORBGIOP12BuffMgr", makeBMGROperation(), "giop12BuffMgr", new Integer(2), new Integer(0), "GROW"), ParserDataFactory.make("com.sun.CORBA.giop.ORBTargetAddressing", OperationFactory.compose(OperationFactory.integerRangeAction(0, 3), OperationFactory.convertIntegerToShort()), "giopTargetAddressPreference", new Short((short)3), new Short((short)2), "2"), ParserDataFactory.make("com.sun.CORBA.giop.ORBTargetAddressing", makeADOperation(), "giopAddressDisposition", new Short((short)0), new Short((short)2), "2"), ParserDataFactory.make("com.sun.CORBA.codeset.AlwaysSendCodeSetCtx", OperationFactory.booleanAction(), "alwaysSendCodeSetCtx", Boolean.TRUE, Boolean.FALSE, "false"), ParserDataFactory.make("com.sun.CORBA.codeset.UseByteOrderMarkers", OperationFactory.booleanAction(), "useByteOrderMarkers", Boolean.valueOf(true), Boolean.FALSE, "false"), ParserDataFactory.make("com.sun.CORBA.codeset.UseByteOrderMarkersInEncaps", OperationFactory.booleanAction(), "useByteOrderMarkersInEncaps", Boolean.valueOf(false), Boolean.FALSE, "false"), ParserDataFactory.make("com.sun.CORBA.codeset.charsets", makeCSOperation(), "charData", CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.getCharComponent(), CodeSetComponentInfo.createFromString(str1), str1), ParserDataFactory.make("com.sun.CORBA.codeset.wcharsets", makeCSOperation(), "wcharData", CodeSetComponentInfo.JAVASOFT_DEFAULT_CODESETS.getWCharComponent(), CodeSetComponentInfo.createFromString(str1), str1), ParserDataFactory.make("com.sun.CORBA.ORBAllowLocalOptimization", OperationFactory.booleanAction(), "allowLocalOptimization", Boolean.FALSE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.legacy.connection.ORBSocketFactoryClass", makeLegacySocketFactoryOperation(), "legacySocketFactory", null, new TestLegacyORBSocketFactory(), MY_CLASS_NAME + "$TestLegacyORBSocketFactory"), ParserDataFactory.make("com.sun.CORBA.transport.ORBSocketFactoryClass", makeSocketFactoryOperation(), "socketFactory", new DefaultSocketFactoryImpl(), new TestORBSocketFactory(), MY_CLASS_NAME + "$TestORBSocketFactory"), ParserDataFactory.make("com.sun.CORBA.transport.ORBListenSocket", makeUSLOperation(), "userSpecifiedListenPorts", new USLPort[0], arrayOfUSLPort, "FOO:2701,BAR:3333"), ParserDataFactory.make("com.sun.CORBA.transport.ORBIORToSocketInfoClass", makeIORToSocketInfoOperation(), "iorToSocketInfo", new DefaultIORToSocketInfoImpl(), new TestIORToSocketInfo(), MY_CLASS_NAME + "$TestIORToSocketInfo"), ParserDataFactory.make("com.sun.CORBA.transport.ORBIIOPPrimaryToContactInfoClass", makeIIOPPrimaryToContactInfoOperation(), "iiopPrimaryToContactInfo", null, new TestIIOPPrimaryToContactInfo(), MY_CLASS_NAME + "$TestIIOPPrimaryToContactInfo"), ParserDataFactory.make("com.sun.CORBA.transport.ORBContactInfoList", makeContactInfoListFactoryOperation(), "corbaContactInfoListFactory", null, new TestContactInfoListFactory(), MY_CLASS_NAME + "$TestContactInfoListFactory"), ParserDataFactory.make("com.sun.CORBA.POA.ORBPersistentServerPort", OperationFactory.integerAction(), "persistentServerPort", new Integer(0), new Integer(2743), "2743"), ParserDataFactory.make("com.sun.CORBA.POA.ORBPersistentServerPort", OperationFactory.setFlagAction(), "persistentPortInitialized", Boolean.FALSE, Boolean.TRUE, "2743"), ParserDataFactory.make("com.sun.CORBA.POA.ORBServerId", OperationFactory.integerAction(), "persistentServerId", new Integer(0), new Integer(294), "294"), ParserDataFactory.make("com.sun.CORBA.POA.ORBServerId", OperationFactory.setFlagAction(), "persistentServerIdInitialized", Boolean.FALSE, Boolean.TRUE, "294"), ParserDataFactory.make("com.sun.CORBA.POA.ORBServerId", OperationFactory.setFlagAction(), "orbServerIdPropertySpecified", Boolean.FALSE, Boolean.TRUE, "294"), ParserDataFactory.make("com.sun.CORBA.POA.ORBActivated", OperationFactory.booleanAction(), "serverIsORBActivated", Boolean.FALSE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.POA.ORBBadServerIdHandlerClass", OperationFactory.classAction(), "badServerIdHandlerClass", null, TestBadServerIdHandler.class, MY_CLASS_NAME + "$TestBadServerIdHandler"), ParserDataFactory.make("org.omg.PortableInterceptor.ORBInitializerClass.", makeROIOperation(), "orbInitializers", new ORBInitializer[0], arrayOfORBInitializer, arrayOfStringPair1, ORBInitializer.class), ParserDataFactory.make("com.sun.CORBA.transport.ORBAcceptor", makeAcceptorInstantiationOperation(), "acceptors", new Acceptor[0], arrayOfAcceptor, arrayOfStringPair2, Acceptor.class), ParserDataFactory.make("com.sun.CORBA.transport.ORBAcceptorSocketType", OperationFactory.stringAction(), "acceptorSocketType", "SocketChannel", "foo", "foo"), ParserDataFactory.make("com.sun.CORBA.transport.ORBUseNIOSelectToWait", OperationFactory.booleanAction(), "acceptorSocketUseSelectThreadToWait", Boolean.TRUE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.transport.ORBAcceptorSocketUseWorkerThreadForEvent", OperationFactory.booleanAction(), "acceptorSocketUseWorkerThreadForEvent", Boolean.TRUE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.transport.ORBConnectionSocketType", OperationFactory.stringAction(), "connectionSocketType", "SocketChannel", "foo", "foo"), ParserDataFactory.make("com.sun.CORBA.transport.ORBUseNIOSelectToWait", OperationFactory.booleanAction(), "connectionSocketUseSelectThreadToWait", Boolean.TRUE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.transport.ORBConnectionSocketUseWorkerThreadForEvent", OperationFactory.booleanAction(), "connectionSocketUseWorkerThreadForEvent", Boolean.TRUE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.transport.ORBDisableDirectByteBufferUse", OperationFactory.booleanAction(), "disableDirectByteBufferUse", Boolean.FALSE, Boolean.TRUE, "true"), ParserDataFactory.make("com.sun.CORBA.transport.ORBTCPReadTimeouts", makeTTCPRTOperation(), "readTimeouts", TransportDefault.makeReadTimeoutsFactory().create(100, 3000, 300, 20), readTimeouts, "100:3000:300:20"), ParserDataFactory.make("com.sun.CORBA.encoding.ORBEnableJavaSerialization", OperationFactory.booleanAction(), "enableJavaSerialization", Boolean.FALSE, Boolean.FALSE, "false"), ParserDataFactory.make("com.sun.CORBA.ORBUseRepId", OperationFactory.booleanAction(), "useRepId", Boolean.TRUE, Boolean.TRUE, "true"), ParserDataFactory.make("org.omg.CORBA.ORBInitRef", 
/*  444 */           OperationFactory.identityAction(), "orbInitialReferences", new StringPair[0], arrayOfStringPair3, arrayOfStringPair3, StringPair.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  449 */     this.parserData = arrayOfParserData;
/*      */   }
/*      */   
/*      */   public final class TestBadServerIdHandler
/*      */     implements BadServerIdHandler
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  456 */       return param1Object instanceof TestBadServerIdHandler;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  460 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void handle(ObjectKey param1ObjectKey) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeTTCPRTOperation() {
/*  473 */     Operation[] arrayOfOperation = { OperationFactory.integerAction(), OperationFactory.integerAction(), OperationFactory.integerAction(), OperationFactory.integerAction() };
/*      */     
/*  475 */     Operation operation1 = OperationFactory.sequenceAction(":", arrayOfOperation);
/*      */     
/*  477 */     Operation operation2 = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  480 */           Object[] arrayOfObject = (Object[])param1Object;
/*  481 */           Integer integer1 = (Integer)arrayOfObject[0];
/*  482 */           Integer integer2 = (Integer)arrayOfObject[1];
/*  483 */           Integer integer3 = (Integer)arrayOfObject[2];
/*  484 */           Integer integer4 = (Integer)arrayOfObject[3];
/*  485 */           return TransportDefault.makeReadTimeoutsFactory().create(integer1
/*  486 */               .intValue(), integer2
/*  487 */               .intValue(), integer3
/*  488 */               .intValue(), integer4
/*  489 */               .intValue());
/*      */         }
/*      */       };
/*      */     
/*  493 */     return OperationFactory.compose(operation1, operation2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeUSLOperation() {
/*  500 */     Operation[] arrayOfOperation = { OperationFactory.stringAction(), OperationFactory.integerAction() };
/*  501 */     Operation operation1 = OperationFactory.sequenceAction(":", arrayOfOperation);
/*      */     
/*  503 */     Operation operation2 = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  506 */           Object[] arrayOfObject = (Object[])param1Object;
/*  507 */           String str = (String)arrayOfObject[0];
/*  508 */           Integer integer = (Integer)arrayOfObject[1];
/*  509 */           return new USLPort(str, integer.intValue());
/*      */         }
/*      */       };
/*      */     
/*  513 */     Operation operation3 = OperationFactory.compose(operation1, operation2);
/*  514 */     return OperationFactory.listAction(",", operation3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TestLegacyORBSocketFactory
/*      */     implements ORBSocketFactory
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  523 */       return param1Object instanceof TestLegacyORBSocketFactory;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  527 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public ServerSocket createServerSocket(String param1String, int param1Int) {
/*  532 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public SocketInfo getEndPointInfo(ORB param1ORB, IOR param1IOR, SocketInfo param1SocketInfo) {
/*  538 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Socket createSocket(SocketInfo param1SocketInfo) {
/*  543 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class TestORBSocketFactory
/*      */     implements ORBSocketFactory
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  552 */       return param1Object instanceof TestORBSocketFactory;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  556 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setORB(ORB param1ORB) {}
/*      */ 
/*      */     
/*      */     public ServerSocket createServerSocket(String param1String, InetSocketAddress param1InetSocketAddress) {
/*  565 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Socket createSocket(String param1String, InetSocketAddress param1InetSocketAddress) {
/*  570 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAcceptedSocketOptions(Acceptor param1Acceptor, ServerSocket param1ServerSocket, Socket param1Socket) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TestIORToSocketInfo
/*      */     implements IORToSocketInfo
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  585 */       return param1Object instanceof TestIORToSocketInfo;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  589 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public List getSocketInfo(IOR param1IOR) {
/*  594 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TestIIOPPrimaryToContactInfo
/*      */     implements IIOPPrimaryToContactInfo
/*      */   {
/*      */     public void reset(ContactInfo param1ContactInfo) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext(ContactInfo param1ContactInfo1, ContactInfo param1ContactInfo2, List param1List) {
/*  609 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ContactInfo next(ContactInfo param1ContactInfo1, ContactInfo param1ContactInfo2, List param1List) {
/*  616 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class TestContactInfoListFactory
/*      */     implements CorbaContactInfoListFactory
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  625 */       return param1Object instanceof TestContactInfoListFactory;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  629 */       return 1;
/*      */     }
/*      */     public void setORB(ORB param1ORB) {}
/*      */     
/*      */     public CorbaContactInfoList create(IOR param1IOR) {
/*  634 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   private Operation makeMapOperation(final Map map) {
/*  639 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  642 */           return map.get(param1Object);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private Operation makeBMGROperation() {
/*  649 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  650 */     hashMap.put("GROW", new Integer(0));
/*  651 */     hashMap.put("CLCT", new Integer(1));
/*  652 */     hashMap.put("STRM", new Integer(2));
/*  653 */     return makeMapOperation(hashMap);
/*      */   }
/*      */ 
/*      */   
/*      */   private Operation makeLegacySocketFactoryOperation() {
/*  658 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  661 */           String str = (String)param1Object;
/*      */ 
/*      */           
/*      */           try {
/*  665 */             Class<?> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*      */ 
/*      */ 
/*      */             
/*  669 */             if (ORBSocketFactory.class.isAssignableFrom(clazz)) {
/*  670 */               return clazz.newInstance();
/*      */             }
/*  672 */             throw ParserTable.this.wrapper.illegalSocketFactoryType(clazz.toString());
/*      */           }
/*  674 */           catch (Exception exception) {
/*      */ 
/*      */ 
/*      */             
/*  678 */             throw ParserTable.this.wrapper.badCustomSocketFactory(exception, str);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeSocketFactoryOperation() {
/*  688 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  691 */           String str = (String)param1Object;
/*      */ 
/*      */           
/*      */           try {
/*  695 */             Class<?> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*      */ 
/*      */ 
/*      */             
/*  699 */             if (ORBSocketFactory.class.isAssignableFrom(clazz)) {
/*  700 */               return clazz.newInstance();
/*      */             }
/*  702 */             throw ParserTable.this.wrapper.illegalSocketFactoryType(clazz.toString());
/*      */           }
/*  704 */           catch (Exception exception) {
/*      */ 
/*      */ 
/*      */             
/*  708 */             throw ParserTable.this.wrapper.badCustomSocketFactory(exception, str);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeIORToSocketInfoOperation() {
/*  718 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  721 */           String str = (String)param1Object;
/*      */ 
/*      */           
/*      */           try {
/*  725 */             Class<?> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*      */ 
/*      */ 
/*      */             
/*  729 */             if (IORToSocketInfo.class.isAssignableFrom(clazz)) {
/*  730 */               return clazz.newInstance();
/*      */             }
/*  732 */             throw ParserTable.this.wrapper.illegalIorToSocketInfoType(clazz.toString());
/*      */           }
/*  734 */           catch (Exception exception) {
/*      */ 
/*      */ 
/*      */             
/*  738 */             throw ParserTable.this.wrapper.badCustomIorToSocketInfo(exception, str);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeIIOPPrimaryToContactInfoOperation() {
/*  748 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  751 */           String str = (String)param1Object;
/*      */ 
/*      */           
/*      */           try {
/*  755 */             Class<?> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*      */ 
/*      */ 
/*      */             
/*  759 */             if (IIOPPrimaryToContactInfo.class.isAssignableFrom(clazz)) {
/*  760 */               return clazz.newInstance();
/*      */             }
/*  762 */             throw ParserTable.this.wrapper.illegalIiopPrimaryToContactInfoType(clazz.toString());
/*      */           }
/*  764 */           catch (Exception exception) {
/*      */ 
/*      */ 
/*      */             
/*  768 */             throw ParserTable.this.wrapper.badCustomIiopPrimaryToContactInfo(exception, str);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeContactInfoListFactoryOperation() {
/*  778 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  781 */           String str = (String)param1Object;
/*      */ 
/*      */           
/*      */           try {
/*  785 */             Class<?> clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str);
/*      */ 
/*      */ 
/*      */             
/*  789 */             if (CorbaContactInfoListFactory.class.isAssignableFrom(clazz))
/*      */             {
/*  791 */               return clazz.newInstance();
/*      */             }
/*  793 */             throw ParserTable.this.wrapper.illegalContactInfoListFactoryType(clazz
/*  794 */                 .toString());
/*      */           }
/*  796 */           catch (Exception exception) {
/*      */ 
/*      */ 
/*      */             
/*  800 */             throw ParserTable.this.wrapper.badContactInfoListFactory(exception, str);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeCSOperation() {
/*  810 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  813 */           String str = (String)param1Object;
/*  814 */           return CodeSetComponentInfo.createFromString(str);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeADOperation() {
/*  823 */     Operation operation1 = new Operation() {
/*  824 */         private Integer[] map = new Integer[] { new Integer(0), new Integer(1), new Integer(2), new Integer(0) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public Object operate(Object param1Object) {
/*  832 */           int i = ((Integer)param1Object).intValue();
/*  833 */           return this.map[i];
/*      */         }
/*      */       };
/*      */     
/*  837 */     Operation operation2 = OperationFactory.integerRangeAction(0, 3);
/*  838 */     Operation operation3 = OperationFactory.compose(operation2, operation1);
/*  839 */     return OperationFactory.compose(operation3, OperationFactory.convertIntegerToShort());
/*      */   }
/*      */ 
/*      */   
/*      */   private Operation makeFSOperation() {
/*  844 */     Operation operation = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  847 */           int i = ((Integer)param1Object).intValue();
/*  848 */           if (i < 32) {
/*  849 */             throw ParserTable.this.wrapper.fragmentSizeMinimum(new Integer(i), new Integer(32));
/*      */           }
/*      */ 
/*      */           
/*  853 */           if (i % 8 != 0) {
/*  854 */             throw ParserTable.this.wrapper.fragmentSizeDiv(new Integer(i), new Integer(8));
/*      */           }
/*      */           
/*  857 */           return param1Object;
/*      */         }
/*      */       };
/*      */     
/*  861 */     return OperationFactory.compose(OperationFactory.integerAction(), operation);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeGVOperation() {
/*  867 */     Operation operation1 = OperationFactory.listAction(".", 
/*  868 */         OperationFactory.integerAction());
/*  869 */     Operation operation2 = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  872 */           Object[] arrayOfObject = (Object[])param1Object;
/*  873 */           int i = ((Integer)arrayOfObject[0]).intValue();
/*  874 */           int j = ((Integer)arrayOfObject[1]).intValue();
/*      */           
/*  876 */           return new GIOPVersion(i, j);
/*      */         }
/*      */       };
/*      */     
/*  880 */     return OperationFactory.compose(operation1, operation2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class TestORBInitializer1
/*      */     extends LocalObject
/*      */     implements ORBInitializer
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  889 */       return param1Object instanceof TestORBInitializer1;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  893 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void pre_init(ORBInitInfo param1ORBInitInfo) {}
/*      */ 
/*      */     
/*      */     public void post_init(ORBInitInfo param1ORBInitInfo) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class TestORBInitializer2
/*      */     extends LocalObject
/*      */     implements ORBInitializer
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  910 */       return param1Object instanceof TestORBInitializer2;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  914 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void pre_init(ORBInitInfo param1ORBInitInfo) {}
/*      */ 
/*      */     
/*      */     public void post_init(ORBInitInfo param1ORBInitInfo) {}
/*      */   }
/*      */ 
/*      */   
/*      */   private Operation makeROIOperation() {
/*  927 */     Operation operation1 = OperationFactory.classAction();
/*  928 */     Operation operation2 = OperationFactory.suffixAction();
/*  929 */     Operation operation3 = OperationFactory.compose(operation2, operation1);
/*  930 */     Operation operation4 = OperationFactory.maskErrorAction(operation3);
/*      */     
/*  932 */     Operation operation5 = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/*  935 */           final Class<?> initClass = (Class)param1Object;
/*  936 */           if (clazz == null) {
/*  937 */             return null;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  942 */           if (ORBInitializer.class.isAssignableFrom(clazz)) {
/*      */ 
/*      */ 
/*      */             
/*  946 */             ORBInitializer oRBInitializer = null;
/*      */             
/*      */             try {
/*  949 */               oRBInitializer = AccessController.<ORBInitializer>doPrivileged(new PrivilegedExceptionAction<ORBInitializer>()
/*      */                   {
/*      */                     
/*      */                     public Object run() throws InstantiationException, IllegalAccessException
/*      */                     {
/*  954 */                       return initClass.newInstance();
/*      */                     }
/*      */                   });
/*      */             }
/*  958 */             catch (PrivilegedActionException privilegedActionException) {
/*      */               
/*  960 */               throw ParserTable.this.wrapper.orbInitializerFailure(privilegedActionException.getException(), clazz
/*  961 */                   .getName());
/*  962 */             } catch (Exception exception) {
/*  963 */               throw ParserTable.this.wrapper.orbInitializerFailure(exception, clazz.getName());
/*      */             } 
/*      */             
/*  966 */             return oRBInitializer;
/*      */           } 
/*  968 */           throw ParserTable.this.wrapper.orbInitializerType(clazz.getName());
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*  973 */     return OperationFactory.compose(operation4, operation5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final class TestAcceptor1
/*      */     implements Acceptor
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/*  983 */       return param1Object instanceof TestAcceptor1;
/*      */     }
/*      */     
/*  986 */     public int hashCode() { return 1; }
/*  987 */     public boolean initialize() { return true; }
/*  988 */     public boolean initialized() { return true; } public String getConnectionCacheType() {
/*  989 */       return "FOO";
/*      */     }
/*  991 */     public void setConnectionCache(InboundConnectionCache param1InboundConnectionCache) {} public InboundConnectionCache getConnectionCache() { return null; } public boolean shouldRegisterAcceptEvent() {
/*  992 */       return true;
/*      */     } public void setUseSelectThreadForConnections(boolean param1Boolean) {} public boolean shouldUseSelectThreadForConnections() {
/*  994 */       return true;
/*      */     } public void setUseWorkerThreadForConnections(boolean param1Boolean) {} public boolean shouldUseWorkerThreadForConnections() {
/*  996 */       return true;
/*      */     } public void accept() {} public void close() {}
/*      */     public EventHandler getEventHandler() {
/*  999 */       return null;
/*      */     } public MessageMediator createMessageMediator(Broker param1Broker, Connection param1Connection) {
/* 1001 */       return null;
/*      */     }
/*      */     public MessageMediator finishCreatingMessageMediator(Broker param1Broker, Connection param1Connection, MessageMediator param1MessageMediator) {
/* 1004 */       return null;
/*      */     } public InputObject createInputObject(Broker param1Broker, MessageMediator param1MessageMediator) {
/* 1006 */       return null;
/*      */     } public OutputObject createOutputObject(Broker param1Broker, MessageMediator param1MessageMediator) {
/* 1008 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   public static final class TestAcceptor2
/*      */     implements Acceptor
/*      */   {
/*      */     public boolean equals(Object param1Object) {
/* 1016 */       return param1Object instanceof TestAcceptor2;
/*      */     }
/* 1018 */     public int hashCode() { return 1; }
/* 1019 */     public boolean initialize() { return true; }
/* 1020 */     public boolean initialized() { return true; } public String getConnectionCacheType() {
/* 1021 */       return "FOO";
/*      */     }
/* 1023 */     public void setConnectionCache(InboundConnectionCache param1InboundConnectionCache) {} public InboundConnectionCache getConnectionCache() { return null; } public boolean shouldRegisterAcceptEvent() {
/* 1024 */       return true;
/*      */     } public void setUseSelectThreadForConnections(boolean param1Boolean) {} public boolean shouldUseSelectThreadForConnections() {
/* 1026 */       return true;
/*      */     } public void setUseWorkerThreadForConnections(boolean param1Boolean) {} public boolean shouldUseWorkerThreadForConnections() {
/* 1028 */       return true;
/*      */     } public void accept() {} public void close() {}
/*      */     public EventHandler getEventHandler() {
/* 1031 */       return null;
/*      */     } public MessageMediator createMessageMediator(Broker param1Broker, Connection param1Connection) {
/* 1033 */       return null;
/*      */     }
/*      */     public MessageMediator finishCreatingMessageMediator(Broker param1Broker, Connection param1Connection, MessageMediator param1MessageMediator) {
/* 1036 */       return null;
/*      */     } public InputObject createInputObject(Broker param1Broker, MessageMediator param1MessageMediator) {
/* 1038 */       return null;
/*      */     } public OutputObject createOutputObject(Broker param1Broker, MessageMediator param1MessageMediator) {
/* 1040 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   private Operation makeAcceptorInstantiationOperation() {
/* 1045 */     Operation operation1 = OperationFactory.classAction();
/* 1046 */     Operation operation2 = OperationFactory.suffixAction();
/* 1047 */     Operation operation3 = OperationFactory.compose(operation2, operation1);
/* 1048 */     Operation operation4 = OperationFactory.maskErrorAction(operation3);
/*      */     
/* 1050 */     Operation operation5 = new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object) {
/* 1053 */           final Class<?> initClass = (Class)param1Object;
/* 1054 */           if (clazz == null) {
/* 1055 */             return null;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1060 */           if (Acceptor.class.isAssignableFrom(clazz)) {
/*      */ 
/*      */             
/* 1063 */             Acceptor acceptor = null;
/*      */             
/*      */             try {
/* 1066 */               acceptor = AccessController.<Acceptor>doPrivileged(new PrivilegedExceptionAction<Acceptor>()
/*      */                   {
/*      */                     
/*      */                     public Object run() throws InstantiationException, IllegalAccessException
/*      */                     {
/* 1071 */                       return initClass.newInstance();
/*      */                     }
/*      */                   });
/*      */             }
/* 1075 */             catch (PrivilegedActionException privilegedActionException) {
/*      */               
/* 1077 */               throw ParserTable.this.wrapper.acceptorInstantiationFailure(privilegedActionException.getException(), clazz
/* 1078 */                   .getName());
/* 1079 */             } catch (Exception exception) {
/* 1080 */               throw ParserTable.this.wrapper.acceptorInstantiationFailure(exception, clazz.getName());
/*      */             } 
/*      */             
/* 1083 */             return acceptor;
/*      */           } 
/* 1085 */           throw ParserTable.this.wrapper.acceptorInstantiationTypeFailure(clazz.getName());
/*      */         }
/*      */       };
/*      */ 
/*      */     
/* 1090 */     return OperationFactory.compose(operation4, operation5);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation makeInitRefOperation() {
/* 1096 */     return new Operation()
/*      */       {
/*      */         public Object operate(Object param1Object)
/*      */         {
/* 1100 */           String[] arrayOfString = (String[])param1Object;
/* 1101 */           if (arrayOfString.length != 2) {
/* 1102 */             throw ParserTable.this.wrapper.orbInitialreferenceSyntax();
/*      */           }
/* 1104 */           return arrayOfString[0] + "=" + arrayOfString[1];
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/ParserTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */