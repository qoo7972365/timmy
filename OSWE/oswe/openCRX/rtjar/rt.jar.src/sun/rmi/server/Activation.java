/*      */ package sun.rmi.server;
/*      */ 
/*      */ import com.sun.rmi.rmid.ExecOptionPermission;
/*      */ import com.sun.rmi.rmid.ExecPermission;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.InetAddress;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.SocketException;
/*      */ import java.nio.channels.Channel;
/*      */ import java.nio.channels.ServerSocketChannel;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.rmi.AccessException;
/*      */ import java.rmi.AlreadyBoundException;
/*      */ import java.rmi.ConnectException;
/*      */ import java.rmi.ConnectIOException;
/*      */ import java.rmi.MarshalledObject;
/*      */ import java.rmi.NoSuchObjectException;
/*      */ import java.rmi.NotBoundException;
/*      */ import java.rmi.Remote;
/*      */ import java.rmi.RemoteException;
/*      */ import java.rmi.activation.ActivationDesc;
/*      */ import java.rmi.activation.ActivationException;
/*      */ import java.rmi.activation.ActivationGroup;
/*      */ import java.rmi.activation.ActivationGroupDesc;
/*      */ import java.rmi.activation.ActivationGroupID;
/*      */ import java.rmi.activation.ActivationID;
/*      */ import java.rmi.activation.ActivationInstantiator;
/*      */ import java.rmi.activation.ActivationMonitor;
/*      */ import java.rmi.activation.ActivationSystem;
/*      */ import java.rmi.activation.Activator;
/*      */ import java.rmi.activation.UnknownGroupException;
/*      */ import java.rmi.activation.UnknownObjectException;
/*      */ import java.rmi.registry.Registry;
/*      */ import java.rmi.server.ObjID;
/*      */ import java.rmi.server.RMIClassLoader;
/*      */ import java.rmi.server.RMIClientSocketFactory;
/*      */ import java.rmi.server.RMIServerSocketFactory;
/*      */ import java.rmi.server.RemoteObject;
/*      */ import java.rmi.server.RemoteServer;
/*      */ import java.rmi.server.UnicastRemoteObject;
/*      */ import java.security.AccessControlException;
/*      */ import java.security.AccessController;
/*      */ import java.security.CodeSource;
/*      */ import java.security.Permission;
/*      */ import java.security.PermissionCollection;
/*      */ import java.security.Permissions;
/*      */ import java.security.Policy;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.cert.Certificate;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import sun.rmi.log.LogHandler;
/*      */ import sun.rmi.log.ReliableLog;
/*      */ import sun.rmi.registry.RegistryImpl;
/*      */ import sun.rmi.transport.LiveRef;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.action.GetIntegerAction;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Activation
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 2921265612698155191L;
/*      */   private static final byte MAJOR_VERSION = 1;
/*      */   private static final byte MINOR_VERSION = 0;
/*      */   private static Object execPolicy;
/*      */   private static Method execPolicyMethod;
/*      */   private static boolean debugExec;
/*  151 */   private Map<ActivationID, ActivationGroupID> idTable = new ConcurrentHashMap<>();
/*      */ 
/*      */   
/*  154 */   private Map<ActivationGroupID, GroupEntry> groupTable = new ConcurrentHashMap<>();
/*      */ 
/*      */   
/*  157 */   private byte majorVersion = 1;
/*  158 */   private byte minorVersion = 0;
/*      */ 
/*      */   
/*      */   private transient int groupSemaphore;
/*      */ 
/*      */   
/*      */   private transient int groupCounter;
/*      */ 
/*      */   
/*      */   private transient ReliableLog log;
/*      */ 
/*      */   
/*      */   private transient int numUpdates;
/*      */   
/*      */   private transient String[] command;
/*      */   
/*  174 */   private static final long groupTimeout = getInt("sun.rmi.activation.groupTimeout", 60000);
/*      */ 
/*      */   
/*  177 */   private static final int snapshotInterval = getInt("sun.rmi.activation.snapshotInterval", 200);
/*      */ 
/*      */   
/*  180 */   private static final long execTimeout = getInt("sun.rmi.activation.execTimeout", 30000);
/*      */   
/*  182 */   private static final Object initLock = new Object(); private static boolean initDone = false;
/*      */   private transient Activator activator;
/*      */   private transient Activator activatorStub;
/*      */   
/*      */   private static int getInt(String paramString, int paramInt) {
/*  187 */     return ((Integer)AccessController.<Integer>doPrivileged(new GetIntegerAction(paramString, paramInt))).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private transient ActivationSystem system;
/*      */   
/*      */   private transient ActivationSystem systemStub;
/*      */   
/*      */   private transient ActivationMonitor monitor;
/*      */   private transient Registry registry;
/*      */   private volatile transient boolean shuttingDown = false;
/*      */   private volatile transient Object startupLock;
/*      */   private transient Thread shutdownHook;
/*  200 */   private static ResourceBundle resources = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void startActivation(int paramInt, RMIServerSocketFactory paramRMIServerSocketFactory, String paramString, String[] paramArrayOfString) throws Exception {
/*  219 */     ReliableLog reliableLog = new ReliableLog(paramString, new ActLogHandler());
/*  220 */     Activation activation = (Activation)reliableLog.recover();
/*  221 */     activation.init(paramInt, paramRMIServerSocketFactory, reliableLog, paramArrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(int paramInt, RMIServerSocketFactory paramRMIServerSocketFactory, ReliableLog paramReliableLog, String[] paramArrayOfString) throws Exception {
/*  235 */     this.log = paramReliableLog;
/*  236 */     this.numUpdates = 0;
/*  237 */     this.shutdownHook = new ShutdownHook();
/*  238 */     this.groupSemaphore = getInt("sun.rmi.activation.groupThrottle", 3);
/*  239 */     this.groupCounter = 0;
/*  240 */     Runtime.getRuntime().addShutdownHook(this.shutdownHook);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  245 */     ActivationGroupID[] arrayOfActivationGroupID = (ActivationGroupID[])this.groupTable.keySet().toArray((Object[])new ActivationGroupID[0]);
/*      */     
/*  247 */     synchronized (this.startupLock = new Object()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  252 */       this.activator = new ActivatorImpl(paramInt, paramRMIServerSocketFactory);
/*  253 */       this.activatorStub = (Activator)RemoteObject.toStub((Remote)this.activator);
/*  254 */       this.system = new ActivationSystemImpl(paramInt, paramRMIServerSocketFactory);
/*  255 */       this.systemStub = (ActivationSystem)RemoteObject.toStub((Remote)this.system);
/*  256 */       this.monitor = new ActivationMonitorImpl(paramInt, paramRMIServerSocketFactory);
/*  257 */       initCommand(paramArrayOfString);
/*  258 */       this.registry = new SystemRegistryImpl(paramInt, null, paramRMIServerSocketFactory, this.systemStub);
/*      */       
/*  260 */       if (paramRMIServerSocketFactory != null) {
/*  261 */         synchronized (initLock) {
/*  262 */           initDone = true;
/*  263 */           initLock.notifyAll();
/*      */         } 
/*      */       }
/*      */     } 
/*  267 */     this.startupLock = null;
/*      */ 
/*      */     
/*  270 */     for (int i = arrayOfActivationGroupID.length; --i >= 0;) {
/*      */       try {
/*  272 */         getGroupEntry(arrayOfActivationGroupID[i]).restartServices();
/*  273 */       } catch (UnknownGroupException unknownGroupException) {
/*  274 */         System.err.println(
/*  275 */             getTextResource("rmid.restart.group.warning"));
/*  276 */         unknownGroupException.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  289 */     paramObjectInputStream.defaultReadObject();
/*  290 */     if (!(this.groupTable instanceof ConcurrentHashMap)) {
/*  291 */       this.groupTable = new ConcurrentHashMap<>(this.groupTable);
/*      */     }
/*  293 */     if (!(this.idTable instanceof ConcurrentHashMap))
/*  294 */       this.idTable = new ConcurrentHashMap<>(this.idTable); 
/*      */   }
/*      */   
/*      */   private static class SystemRegistryImpl
/*      */     extends RegistryImpl
/*      */   {
/*  300 */     private static final String NAME = ActivationSystem.class.getName();
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 4877330021609408794L;
/*      */ 
/*      */     
/*      */     private final ActivationSystem systemStub;
/*      */ 
/*      */     
/*      */     SystemRegistryImpl(int param1Int, RMIClientSocketFactory param1RMIClientSocketFactory, RMIServerSocketFactory param1RMIServerSocketFactory, ActivationSystem param1ActivationSystem) throws RemoteException {
/*  310 */       super(param1Int, param1RMIClientSocketFactory, param1RMIServerSocketFactory);
/*  311 */       this.systemStub = param1ActivationSystem;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Remote lookup(String param1String) throws RemoteException, NotBoundException {
/*  323 */       if (param1String.equals(NAME)) {
/*  324 */         return (Remote)this.systemStub;
/*      */       }
/*  326 */       return super.lookup(param1String);
/*      */     }
/*      */ 
/*      */     
/*      */     public String[] list() throws RemoteException {
/*  331 */       String[] arrayOfString1 = super.list();
/*  332 */       int i = arrayOfString1.length;
/*  333 */       String[] arrayOfString2 = new String[i + 1];
/*  334 */       if (i > 0) {
/*  335 */         System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
/*      */       }
/*  337 */       arrayOfString2[i] = NAME;
/*  338 */       return arrayOfString2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void bind(String param1String, Remote param1Remote) throws RemoteException, AlreadyBoundException, AccessException {
/*  344 */       if (param1String.equals(NAME)) {
/*  345 */         throw new AccessException("binding ActivationSystem is disallowed");
/*      */       }
/*      */       
/*  348 */       RegistryImpl.checkAccess("ActivationSystem.bind");
/*  349 */       super.bind(param1String, param1Remote);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unbind(String param1String) throws RemoteException, NotBoundException, AccessException {
/*  356 */       if (param1String.equals(NAME)) {
/*  357 */         throw new AccessException("unbinding ActivationSystem is disallowed");
/*      */       }
/*      */       
/*  360 */       RegistryImpl.checkAccess("ActivationSystem.unbind");
/*  361 */       super.unbind(param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void rebind(String param1String, Remote param1Remote) throws RemoteException, AccessException {
/*  369 */       if (param1String.equals(NAME)) {
/*  370 */         throw new AccessException("binding ActivationSystem is disallowed");
/*      */       }
/*      */       
/*  373 */       RegistryImpl.checkAccess("ActivationSystem.rebind");
/*  374 */       super.rebind(param1String, param1Remote);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class ActivatorImpl
/*      */     extends RemoteServer
/*      */     implements Activator
/*      */   {
/*      */     private static final long serialVersionUID = -3654244726254566136L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ActivatorImpl(int param1Int, RMIServerSocketFactory param1RMIServerSocketFactory) throws RemoteException {
/*  397 */       LiveRef liveRef = new LiveRef(new ObjID(1), param1Int, null, param1RMIServerSocketFactory);
/*      */       
/*  399 */       UnicastServerRef unicastServerRef = new UnicastServerRef(liveRef);
/*  400 */       this.ref = unicastServerRef;
/*  401 */       unicastServerRef.exportObject(this, null, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MarshalledObject<? extends Remote> activate(ActivationID param1ActivationID, boolean param1Boolean) throws ActivationException, UnknownObjectException, RemoteException {
/*  408 */       Activation.this.checkShutdown();
/*  409 */       return Activation.this.getGroupEntry(param1ActivationID).activate(param1ActivationID, param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class ActivationMonitorImpl
/*      */     extends UnicastRemoteObject
/*      */     implements ActivationMonitor
/*      */   {
/*      */     private static final long serialVersionUID = -6214940464757948867L;
/*      */     
/*      */     ActivationMonitorImpl(int param1Int, RMIServerSocketFactory param1RMIServerSocketFactory) throws RemoteException {
/*  421 */       super(param1Int, null, param1RMIServerSocketFactory);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void inactiveObject(ActivationID param1ActivationID) throws UnknownObjectException, RemoteException {
/*      */       try {
/*  428 */         Activation.this.checkShutdown();
/*  429 */       } catch (ActivationException activationException) {
/*      */         return;
/*      */       } 
/*  432 */       RegistryImpl.checkAccess("Activator.inactiveObject");
/*  433 */       Activation.this.getGroupEntry(param1ActivationID).inactiveObject(param1ActivationID);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void activeObject(ActivationID param1ActivationID, MarshalledObject<? extends Remote> param1MarshalledObject) throws UnknownObjectException, RemoteException {
/*      */       try {
/*  441 */         Activation.this.checkShutdown();
/*  442 */       } catch (ActivationException activationException) {
/*      */         return;
/*      */       } 
/*  445 */       RegistryImpl.checkAccess("ActivationSystem.activeObject");
/*  446 */       Activation.this.getGroupEntry(param1ActivationID).activeObject(param1ActivationID, param1MarshalledObject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void inactiveGroup(ActivationGroupID param1ActivationGroupID, long param1Long) throws UnknownGroupException, RemoteException {
/*      */       try {
/*  454 */         Activation.this.checkShutdown();
/*  455 */       } catch (ActivationException activationException) {
/*      */         return;
/*      */       } 
/*  458 */       RegistryImpl.checkAccess("ActivationMonitor.inactiveGroup");
/*  459 */       Activation.this.getGroupEntry(param1ActivationGroupID).inactiveGroup(param1Long, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class SameHostOnlyServerRef
/*      */     extends UnicastServerRef
/*      */   {
/*      */     private static final long serialVersionUID = 1234L;
/*      */ 
/*      */ 
/*      */     
/*      */     private String accessKind;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SameHostOnlyServerRef(LiveRef param1LiveRef, String param1String) {
/*  480 */       super(param1LiveRef);
/*  481 */       this.accessKind = param1String;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void unmarshalCustomCallData(ObjectInput param1ObjectInput) throws IOException, ClassNotFoundException {
/*  486 */       RegistryImpl.checkAccess(this.accessKind);
/*  487 */       super.unmarshalCustomCallData(param1ObjectInput);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class ActivationSystemImpl
/*      */     extends RemoteServer
/*      */     implements ActivationSystem
/*      */   {
/*      */     private static final long serialVersionUID = 9100152600327688967L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ActivationSystemImpl(int param1Int, RMIServerSocketFactory param1RMIServerSocketFactory) throws RemoteException {
/*  507 */       LiveRef liveRef = new LiveRef(new ObjID(4), param1Int, null, param1RMIServerSocketFactory);
/*  508 */       Activation.SameHostOnlyServerRef sameHostOnlyServerRef = new Activation.SameHostOnlyServerRef(liveRef, "ActivationSystem.nonLocalAccess");
/*      */       
/*  510 */       this.ref = sameHostOnlyServerRef;
/*  511 */       sameHostOnlyServerRef.exportObject(this, null);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationID registerObject(ActivationDesc param1ActivationDesc) throws ActivationException, UnknownGroupException, RemoteException {
/*  517 */       Activation.this.checkShutdown();
/*      */ 
/*      */       
/*  520 */       ActivationGroupID activationGroupID = param1ActivationDesc.getGroupID();
/*  521 */       ActivationID activationID = new ActivationID(Activation.this.activatorStub);
/*  522 */       Activation.this.getGroupEntry(activationGroupID).registerObject(activationID, param1ActivationDesc, true);
/*  523 */       return activationID;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void unregisterObject(ActivationID param1ActivationID) throws ActivationException, UnknownObjectException, RemoteException {
/*  529 */       Activation.this.checkShutdown();
/*      */ 
/*      */       
/*  532 */       Activation.this.getGroupEntry(param1ActivationID).unregisterObject(param1ActivationID, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationGroupID registerGroup(ActivationGroupDesc param1ActivationGroupDesc) throws ActivationException, RemoteException {
/*  538 */       Thread.dumpStack();
/*  539 */       Activation.this.checkShutdown();
/*      */ 
/*      */       
/*  542 */       Activation.this.checkArgs(param1ActivationGroupDesc, null);
/*      */       
/*  544 */       ActivationGroupID activationGroupID = new ActivationGroupID(Activation.this.systemStub);
/*  545 */       Activation.GroupEntry groupEntry = new Activation.GroupEntry(activationGroupID, param1ActivationGroupDesc);
/*      */       
/*  547 */       Activation.this.groupTable.put(activationGroupID, groupEntry);
/*  548 */       Activation.this.addLogRecord(new Activation.LogRegisterGroup(activationGroupID, param1ActivationGroupDesc));
/*  549 */       return activationGroupID;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationMonitor activeGroup(ActivationGroupID param1ActivationGroupID, ActivationInstantiator param1ActivationInstantiator, long param1Long) throws ActivationException, UnknownGroupException, RemoteException {
/*  557 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */       
/*  561 */       Activation.this.getGroupEntry(param1ActivationGroupID).activeGroup(param1ActivationInstantiator, param1Long);
/*  562 */       return Activation.this.monitor;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void unregisterGroup(ActivationGroupID param1ActivationGroupID) throws ActivationException, UnknownGroupException, RemoteException {
/*  568 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  574 */       Activation.this.removeGroupEntry(param1ActivationGroupID).unregisterGroup(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationDesc setActivationDesc(ActivationID param1ActivationID, ActivationDesc param1ActivationDesc) throws ActivationException, UnknownObjectException, RemoteException {
/*  581 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */       
/*  585 */       if (!Activation.this.getGroupID(param1ActivationID).equals(param1ActivationDesc.getGroupID())) {
/*  586 */         throw new ActivationException("ActivationDesc contains wrong group");
/*      */       }
/*      */       
/*  589 */       return Activation.this.getGroupEntry(param1ActivationID).setActivationDesc(param1ActivationID, param1ActivationDesc, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationGroupDesc setActivationGroupDesc(ActivationGroupID param1ActivationGroupID, ActivationGroupDesc param1ActivationGroupDesc) throws ActivationException, UnknownGroupException, RemoteException {
/*  596 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */       
/*  600 */       Activation.this.checkArgs(param1ActivationGroupDesc, null);
/*  601 */       return Activation.this.getGroupEntry(param1ActivationGroupID).setActivationGroupDesc(param1ActivationGroupID, param1ActivationGroupDesc, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationDesc getActivationDesc(ActivationID param1ActivationID) throws ActivationException, UnknownObjectException, RemoteException {
/*  607 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */       
/*  611 */       return Activation.this.getGroupEntry(param1ActivationID).getActivationDesc(param1ActivationID);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ActivationGroupDesc getActivationGroupDesc(ActivationGroupID param1ActivationGroupID) throws ActivationException, UnknownGroupException, RemoteException {
/*  617 */       Activation.this.checkShutdown();
/*      */ 
/*      */ 
/*      */       
/*  621 */       return (Activation.this.getGroupEntry(param1ActivationGroupID)).desc;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void shutdown() throws AccessException {
/*  632 */       Object object = Activation.this.startupLock;
/*  633 */       if (object != null) {
/*  634 */         synchronized (object) {
/*      */         
/*      */         } 
/*      */       }
/*      */       
/*  639 */       synchronized (Activation.this) {
/*  640 */         if (!Activation.this.shuttingDown) {
/*  641 */           Activation.this.shuttingDown = true;
/*  642 */           (new Activation.Shutdown()).start();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkShutdown() throws ActivationException {
/*  651 */     Object object = this.startupLock;
/*  652 */     if (object != null) {
/*  653 */       synchronized (object) {
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*  658 */     if (this.shuttingDown == true) {
/*  659 */       throw new ActivationException("activation system shutting down");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void unexport(Remote paramRemote) {
/*      */     while (true) {
/*      */       try {
/*  667 */         if (UnicastRemoteObject.unexportObject(paramRemote, false) == true) {
/*      */           break;
/*      */         }
/*  670 */         Thread.sleep(100L);
/*      */       }
/*  672 */       catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Shutdown
/*      */     extends Thread
/*      */   {
/*      */     Shutdown() {
/*  683 */       super("rmid Shutdown");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/*  691 */         Activation.unexport((Remote)Activation.this.activator);
/*  692 */         Activation.unexport((Remote)Activation.this.system);
/*      */ 
/*      */         
/*  695 */         for (Activation.GroupEntry groupEntry : Activation.this.groupTable.values()) {
/*  696 */           groupEntry.shutdown();
/*      */         }
/*      */         
/*  699 */         Runtime.getRuntime().removeShutdownHook(Activation.this.shutdownHook);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  704 */         Activation.unexport((Remote)Activation.this.monitor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  715 */           synchronized (Activation.this.log) {
/*  716 */             Activation.this.log.close();
/*      */           } 
/*  718 */         } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  728 */         System.err.println(Activation.getTextResource("rmid.daemon.shutdown"));
/*  729 */         System.exit(0);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ShutdownHook
/*      */     extends Thread {
/*      */     ShutdownHook() {
/*  737 */       super("rmid ShutdownHook");
/*      */     }
/*      */     
/*      */     public void run() {
/*  741 */       synchronized (Activation.this) {
/*  742 */         Activation.this.shuttingDown = true;
/*      */       } 
/*      */ 
/*      */       
/*  746 */       for (Activation.GroupEntry groupEntry : Activation.this.groupTable.values()) {
/*  747 */         groupEntry.shutdownFast();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ActivationGroupID getGroupID(ActivationID paramActivationID) throws UnknownObjectException {
/*  759 */     ActivationGroupID activationGroupID = this.idTable.get(paramActivationID);
/*  760 */     if (activationGroupID != null) {
/*  761 */       return activationGroupID;
/*      */     }
/*  763 */     throw new UnknownObjectException("unknown object: " + paramActivationID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GroupEntry getGroupEntry(ActivationGroupID paramActivationGroupID, boolean paramBoolean) throws UnknownGroupException {
/*  773 */     if (paramActivationGroupID.getClass() == ActivationGroupID.class) {
/*      */       GroupEntry groupEntry;
/*  775 */       if (paramBoolean) {
/*  776 */         groupEntry = this.groupTable.remove(paramActivationGroupID);
/*      */       } else {
/*  778 */         groupEntry = this.groupTable.get(paramActivationGroupID);
/*      */       } 
/*  780 */       if (groupEntry != null && !groupEntry.removed) {
/*  781 */         return groupEntry;
/*      */       }
/*      */     } 
/*  784 */     throw new UnknownGroupException("group unknown");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GroupEntry getGroupEntry(ActivationGroupID paramActivationGroupID) throws UnknownGroupException {
/*  794 */     return getGroupEntry(paramActivationGroupID, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GroupEntry removeGroupEntry(ActivationGroupID paramActivationGroupID) throws UnknownGroupException {
/*  804 */     return getGroupEntry(paramActivationGroupID, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GroupEntry getGroupEntry(ActivationID paramActivationID) throws UnknownObjectException {
/*  815 */     ActivationGroupID activationGroupID = getGroupID(paramActivationID);
/*  816 */     GroupEntry groupEntry = this.groupTable.get(activationGroupID);
/*  817 */     if (groupEntry != null && !groupEntry.removed) {
/*  818 */       return groupEntry;
/*      */     }
/*  820 */     throw new UnknownObjectException("object's group removed");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class GroupEntry
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7222464070032993304L;
/*      */ 
/*      */     
/*      */     private static final int MAX_TRIES = 2;
/*      */ 
/*      */     
/*      */     private static final int NORMAL = 0;
/*      */ 
/*      */     
/*      */     private static final int CREATING = 1;
/*      */ 
/*      */     
/*      */     private static final int TERMINATE = 2;
/*      */     
/*      */     private static final int TERMINATING = 3;
/*      */     
/*  844 */     ActivationGroupDesc desc = null;
/*  845 */     ActivationGroupID groupID = null;
/*  846 */     long incarnation = 0L;
/*  847 */     Map<ActivationID, Activation.ObjectEntry> objects = new HashMap<>();
/*  848 */     Set<ActivationID> restartSet = new HashSet<>();
/*      */     
/*  850 */     transient ActivationInstantiator group = null;
/*  851 */     transient int status = 0;
/*  852 */     transient long waitTime = 0L;
/*  853 */     transient String groupName = null;
/*  854 */     transient Process child = null;
/*      */     transient boolean removed = false;
/*  856 */     transient Watchdog watchdog = null;
/*      */     
/*      */     GroupEntry(ActivationGroupID param1ActivationGroupID, ActivationGroupDesc param1ActivationGroupDesc) {
/*  859 */       this.groupID = param1ActivationGroupID;
/*  860 */       this.desc = param1ActivationGroupDesc;
/*      */     }
/*      */     
/*      */     void restartServices() {
/*  864 */       Iterator<?> iterator = null;
/*      */       
/*  866 */       synchronized (this) {
/*  867 */         if (this.restartSet.isEmpty()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  877 */         iterator = (new HashSet(this.restartSet)).iterator();
/*      */       } 
/*      */       
/*  880 */       while (iterator.hasNext()) {
/*  881 */         ActivationID activationID = (ActivationID)iterator.next();
/*      */         try {
/*  883 */           activate(activationID, true);
/*  884 */         } catch (Exception exception) {
/*  885 */           if (Activation.this.shuttingDown) {
/*      */             return;
/*      */           }
/*  888 */           System.err.println(Activation
/*  889 */               .getTextResource("rmid.restart.service.warning"));
/*  890 */           exception.printStackTrace();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void activeGroup(ActivationInstantiator param1ActivationInstantiator, long param1Long) throws ActivationException, UnknownGroupException {
/*  899 */       if (this.incarnation != param1Long) {
/*  900 */         throw new ActivationException("invalid incarnation");
/*      */       }
/*      */       
/*  903 */       if (this.group != null) {
/*  904 */         if (this.group.equals(param1ActivationInstantiator)) {
/*      */           return;
/*      */         }
/*  907 */         throw new ActivationException("group already active");
/*      */       } 
/*      */ 
/*      */       
/*  911 */       if (this.child != null && this.status != 1) {
/*  912 */         throw new ActivationException("group not being created");
/*      */       }
/*      */       
/*  915 */       this.group = param1ActivationInstantiator;
/*  916 */       this.status = 0;
/*  917 */       notifyAll();
/*      */     }
/*      */     
/*      */     private void checkRemoved() throws UnknownGroupException {
/*  921 */       if (this.removed) {
/*  922 */         throw new UnknownGroupException("group removed");
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private Activation.ObjectEntry getObjectEntry(ActivationID param1ActivationID) throws UnknownObjectException {
/*  929 */       if (this.removed) {
/*  930 */         throw new UnknownObjectException("object's group removed");
/*      */       }
/*  932 */       Activation.ObjectEntry objectEntry = this.objects.get(param1ActivationID);
/*  933 */       if (objectEntry == null) {
/*  934 */         throw new UnknownObjectException("object unknown");
/*      */       }
/*  936 */       return objectEntry;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void registerObject(ActivationID param1ActivationID, ActivationDesc param1ActivationDesc, boolean param1Boolean) throws UnknownGroupException, ActivationException {
/*  944 */       checkRemoved();
/*  945 */       this.objects.put(param1ActivationID, new Activation.ObjectEntry(param1ActivationDesc));
/*  946 */       if (param1ActivationDesc.getRestartMode() == true) {
/*  947 */         this.restartSet.add(param1ActivationID);
/*      */       }
/*      */ 
/*      */       
/*  951 */       Activation.this.idTable.put(param1ActivationID, this.groupID);
/*      */       
/*  953 */       if (param1Boolean) {
/*  954 */         Activation.this.addLogRecord(new Activation.LogRegisterObject(param1ActivationID, param1ActivationDesc));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void unregisterObject(ActivationID param1ActivationID, boolean param1Boolean) throws UnknownGroupException, ActivationException {
/*  961 */       Activation.ObjectEntry objectEntry = getObjectEntry(param1ActivationID);
/*  962 */       objectEntry.removed = true;
/*  963 */       this.objects.remove(param1ActivationID);
/*  964 */       if (objectEntry.desc.getRestartMode() == true) {
/*  965 */         this.restartSet.remove(param1ActivationID);
/*      */       }
/*      */ 
/*      */       
/*  969 */       Activation.this.idTable.remove(param1ActivationID);
/*  970 */       if (param1Boolean) {
/*  971 */         Activation.this.addLogRecord(new Activation.LogUnregisterObject(param1ActivationID));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void unregisterGroup(boolean param1Boolean) throws UnknownGroupException, ActivationException {
/*  978 */       checkRemoved();
/*  979 */       this.removed = true;
/*      */       
/*  981 */       for (Map.Entry<ActivationID, Activation.ObjectEntry> entry : this.objects.entrySet()) {
/*      */         
/*  983 */         ActivationID activationID = (ActivationID)entry.getKey();
/*  984 */         Activation.this.idTable.remove(activationID);
/*  985 */         Activation.ObjectEntry objectEntry = (Activation.ObjectEntry)entry.getValue();
/*  986 */         objectEntry.removed = true;
/*      */       } 
/*  988 */       this.objects.clear();
/*  989 */       this.restartSet.clear();
/*  990 */       reset();
/*  991 */       childGone();
/*      */ 
/*      */       
/*  994 */       if (param1Boolean) {
/*  995 */         Activation.this.addLogRecord(new Activation.LogUnregisterGroup(this.groupID));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized ActivationDesc setActivationDesc(ActivationID param1ActivationID, ActivationDesc param1ActivationDesc, boolean param1Boolean) throws UnknownObjectException, UnknownGroupException, ActivationException {
/* 1005 */       Activation.ObjectEntry objectEntry = getObjectEntry(param1ActivationID);
/* 1006 */       ActivationDesc activationDesc = objectEntry.desc;
/* 1007 */       objectEntry.desc = param1ActivationDesc;
/* 1008 */       if (param1ActivationDesc.getRestartMode() == true) {
/* 1009 */         this.restartSet.add(param1ActivationID);
/*      */       } else {
/* 1011 */         this.restartSet.remove(param1ActivationID);
/*      */       } 
/*      */       
/* 1014 */       if (param1Boolean) {
/* 1015 */         Activation.this.addLogRecord(new Activation.LogUpdateDesc(param1ActivationID, param1ActivationDesc));
/*      */       }
/*      */       
/* 1018 */       return activationDesc;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized ActivationDesc getActivationDesc(ActivationID param1ActivationID) throws UnknownObjectException, UnknownGroupException {
/* 1024 */       return (getObjectEntry(param1ActivationID)).desc;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized ActivationGroupDesc setActivationGroupDesc(ActivationGroupID param1ActivationGroupID, ActivationGroupDesc param1ActivationGroupDesc, boolean param1Boolean) throws UnknownGroupException, ActivationException {
/* 1033 */       checkRemoved();
/* 1034 */       ActivationGroupDesc activationGroupDesc = this.desc;
/* 1035 */       this.desc = param1ActivationGroupDesc;
/*      */       
/* 1037 */       if (param1Boolean) {
/* 1038 */         Activation.this.addLogRecord(new Activation.LogUpdateGroupDesc(param1ActivationGroupID, param1ActivationGroupDesc));
/*      */       }
/* 1040 */       return activationGroupDesc;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void inactiveGroup(long param1Long, boolean param1Boolean) throws UnknownGroupException {
/* 1046 */       checkRemoved();
/* 1047 */       if (this.incarnation != param1Long) {
/* 1048 */         throw new UnknownGroupException("invalid incarnation");
/*      */       }
/*      */       
/* 1051 */       reset();
/* 1052 */       if (param1Boolean) {
/* 1053 */         terminate();
/* 1054 */       } else if (this.child != null && this.status == 0) {
/* 1055 */         this.status = 2;
/* 1056 */         this.watchdog.noRestart();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void activeObject(ActivationID param1ActivationID, MarshalledObject<? extends Remote> param1MarshalledObject) throws UnknownObjectException {
/* 1064 */       (getObjectEntry(param1ActivationID)).stub = param1MarshalledObject;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized void inactiveObject(ActivationID param1ActivationID) throws UnknownObjectException {
/* 1070 */       getObjectEntry(param1ActivationID).reset();
/*      */     }
/*      */     
/*      */     private synchronized void reset() {
/* 1074 */       this.group = null;
/* 1075 */       for (Activation.ObjectEntry objectEntry : this.objects.values()) {
/* 1076 */         objectEntry.reset();
/*      */       }
/*      */     }
/*      */     
/*      */     private void childGone() {
/* 1081 */       if (this.child != null) {
/* 1082 */         this.child = null;
/* 1083 */         this.watchdog.dispose();
/* 1084 */         this.watchdog = null;
/* 1085 */         this.status = 0;
/* 1086 */         notifyAll();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void terminate() {
/* 1091 */       if (this.child != null && this.status != 3) {
/* 1092 */         this.child.destroy();
/* 1093 */         this.status = 3;
/* 1094 */         this.waitTime = System.currentTimeMillis() + Activation.groupTimeout;
/* 1095 */         notifyAll();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void await() {
/*      */       while (true) {
/* 1106 */         switch (this.status) {
/*      */           case 0:
/*      */             return;
/*      */           case 2:
/* 1110 */             terminate();
/*      */           case 3:
/*      */             try {
/* 1113 */               this.child.exitValue();
/* 1114 */             } catch (IllegalThreadStateException illegalThreadStateException) {
/* 1115 */               long l = System.currentTimeMillis();
/* 1116 */               if (this.waitTime > l) {
/*      */                 try {
/* 1118 */                   wait(this.waitTime - l);
/* 1119 */                 } catch (InterruptedException interruptedException) {}
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */             } 
/*      */             
/* 1125 */             childGone();
/*      */             return;
/*      */           case 1:
/*      */             try {
/* 1129 */               wait();
/* 1130 */             } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void shutdownFast() {
/* 1138 */       Process process = this.child;
/* 1139 */       if (process != null) {
/* 1140 */         process.destroy();
/*      */       }
/*      */     }
/*      */     
/*      */     synchronized void shutdown() {
/* 1145 */       reset();
/* 1146 */       terminate();
/* 1147 */       await();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MarshalledObject<? extends Remote> activate(ActivationID param1ActivationID, boolean param1Boolean) throws ActivationException {
/* 1154 */       RemoteException remoteException = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1160 */       for (byte b = 2; b > 0; b--) {
/*      */         InactiveGroupException inactiveGroupException;
/*      */         
/*      */         ActivationInstantiator activationInstantiator;
/*      */         long l;
/*      */         Activation.ObjectEntry objectEntry;
/* 1166 */         synchronized (this) {
/* 1167 */           objectEntry = getObjectEntry(param1ActivationID);
/*      */           
/* 1169 */           if (!param1Boolean && objectEntry.stub != null) {
/* 1170 */             return objectEntry.stub;
/*      */           }
/* 1172 */           activationInstantiator = getInstantiator(this.groupID);
/* 1173 */           l = this.incarnation;
/*      */         } 
/*      */         
/* 1176 */         boolean bool1 = false;
/* 1177 */         boolean bool2 = false;
/*      */         
/*      */         try {
/* 1180 */           return objectEntry.activate(param1ActivationID, param1Boolean, activationInstantiator);
/* 1181 */         } catch (NoSuchObjectException noSuchObjectException) {
/* 1182 */           bool1 = true;
/* 1183 */           remoteException = noSuchObjectException;
/* 1184 */         } catch (ConnectException connectException) {
/* 1185 */           bool1 = true;
/* 1186 */           bool2 = true;
/* 1187 */           remoteException = connectException;
/* 1188 */         } catch (ConnectIOException connectIOException) {
/* 1189 */           bool1 = true;
/* 1190 */           bool2 = true;
/* 1191 */           remoteException = connectIOException;
/* 1192 */         } catch (InactiveGroupException inactiveGroupException1) {
/* 1193 */           bool1 = true;
/* 1194 */           inactiveGroupException = inactiveGroupException1;
/* 1195 */         } catch (RemoteException remoteException1) {
/*      */           
/* 1197 */           if (inactiveGroupException == null) {
/* 1198 */             remoteException = remoteException1;
/*      */           }
/*      */         } 
/*      */         
/* 1202 */         if (bool1) {
/*      */           
/*      */           try {
/* 1205 */             System.err.println(
/* 1206 */                 MessageFormat.format(Activation
/* 1207 */                   .getTextResource("rmid.group.inactive"), new Object[] { remoteException
/* 1208 */                     .toString() }));
/* 1209 */             remoteException.printStackTrace();
/* 1210 */             Activation.this.getGroupEntry(this.groupID)
/* 1211 */               .inactiveGroup(l, bool2);
/* 1212 */           } catch (UnknownGroupException unknownGroupException) {}
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1223 */       throw new ActivationException("object activation failed after 2 tries", remoteException);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ActivationInstantiator getInstantiator(ActivationGroupID param1ActivationGroupID) throws ActivationException {
/* 1235 */       assert Thread.holdsLock(this);
/*      */       
/* 1237 */       await();
/* 1238 */       if (this.group != null) {
/* 1239 */         return this.group;
/*      */       }
/* 1241 */       checkRemoved();
/* 1242 */       boolean bool = false;
/*      */       
/*      */       try {
/* 1245 */         this.groupName = Activation.this.Pstartgroup();
/* 1246 */         bool = true;
/* 1247 */         String[] arrayOfString = Activation.this.activationArgs(this.desc);
/* 1248 */         Activation.this.checkArgs(this.desc, arrayOfString);
/*      */         
/* 1250 */         if (Activation.debugExec) {
/* 1251 */           StringBuffer stringBuffer = new StringBuffer(arrayOfString[0]);
/*      */           
/* 1253 */           for (byte b = 1; b < arrayOfString.length; b++) {
/* 1254 */             stringBuffer.append(' ');
/* 1255 */             stringBuffer.append(arrayOfString[b]);
/*      */           } 
/* 1257 */           System.err.println(
/* 1258 */               MessageFormat.format(Activation
/* 1259 */                 .getTextResource("rmid.exec.command"), new Object[] { stringBuffer
/* 1260 */                   .toString() }));
/*      */         } 
/*      */         
/*      */         try {
/* 1264 */           this.child = Runtime.getRuntime().exec(arrayOfString);
/* 1265 */           this.status = 1;
/* 1266 */           this.incarnation++;
/* 1267 */           this.watchdog = new Watchdog();
/* 1268 */           this.watchdog.start();
/* 1269 */           Activation.this.addLogRecord(new Activation.LogGroupIncarnation(param1ActivationGroupID, this.incarnation));
/*      */ 
/*      */ 
/*      */           
/* 1273 */           PipeWriter.plugTogetherPair(this.child.getInputStream(), System.out, this.child
/* 1274 */               .getErrorStream(), System.err);
/* 1275 */           try (MarshalOutputStream null = new MarshalOutputStream(this.child
/* 1276 */                 .getOutputStream())) {
/* 1277 */             marshalOutputStream.writeObject(param1ActivationGroupID);
/* 1278 */             marshalOutputStream.writeObject(this.desc);
/* 1279 */             marshalOutputStream.writeLong(this.incarnation);
/* 1280 */             marshalOutputStream.flush();
/*      */           }
/*      */         
/*      */         }
/* 1284 */         catch (IOException iOException) {
/* 1285 */           terminate();
/* 1286 */           throw new ActivationException("unable to create activation group", iOException);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/* 1291 */           long l1 = System.currentTimeMillis();
/* 1292 */           long l2 = l1 + Activation.execTimeout;
/*      */           do {
/* 1294 */             wait(l2 - l1);
/* 1295 */             if (this.group != null) {
/* 1296 */               return this.group;
/*      */             }
/* 1298 */             l1 = System.currentTimeMillis();
/* 1299 */           } while (this.status == 1 && l1 < l2);
/* 1300 */         } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */         
/* 1303 */         terminate();
/* 1304 */         throw new ActivationException(this.removed ? "activation group unregistered" : "timeout creating child process");
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/* 1309 */         if (bool) {
/* 1310 */           Activation.this.Vstartgroup();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private class Watchdog
/*      */       extends Thread
/*      */     {
/* 1319 */       private final Process groupProcess = Activation.GroupEntry.this.child;
/* 1320 */       private final long groupIncarnation = Activation.GroupEntry.this.incarnation;
/*      */       private boolean canInterrupt = true;
/*      */       private boolean shouldQuit = false;
/*      */       private boolean shouldRestart = true;
/*      */       
/*      */       Watchdog() {
/* 1326 */         super("WatchDog-" + Activation.GroupEntry.this.groupName + "-" + Activation.GroupEntry.this.incarnation);
/* 1327 */         setDaemon(true);
/*      */       }
/*      */ 
/*      */       
/*      */       public void run() {
/* 1332 */         if (this.shouldQuit) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1340 */           this.groupProcess.waitFor();
/* 1341 */         } catch (InterruptedException interruptedException) {
/*      */           return;
/*      */         } 
/*      */         
/* 1345 */         boolean bool = false;
/* 1346 */         synchronized (Activation.GroupEntry.this) {
/* 1347 */           if (this.shouldQuit) {
/*      */             return;
/*      */           }
/* 1350 */           this.canInterrupt = false;
/* 1351 */           interrupted();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1356 */           if (this.groupIncarnation == Activation.GroupEntry.this.incarnation) {
/* 1357 */             bool = (this.shouldRestart && !Activation.this.shuttingDown) ? true : false;
/* 1358 */             Activation.GroupEntry.this.reset();
/* 1359 */             Activation.GroupEntry.this.childGone();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1367 */         if (bool) {
/* 1368 */           Activation.GroupEntry.this.restartServices();
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void dispose() {
/* 1378 */         this.shouldQuit = true;
/* 1379 */         if (this.canInterrupt) {
/* 1380 */           interrupt();
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void noRestart() {
/* 1388 */         this.shouldRestart = false;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String[] activationArgs(ActivationGroupDesc paramActivationGroupDesc) {
/* 1395 */     ActivationGroupDesc.CommandEnvironment commandEnvironment = paramActivationGroupDesc.getCommandEnvironment();
/*      */ 
/*      */     
/* 1398 */     ArrayList<String> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 1401 */     arrayList.add((commandEnvironment != null && commandEnvironment.getCommandPath() != null) ? commandEnvironment
/* 1402 */         .getCommandPath() : this.command[0]);
/*      */ 
/*      */ 
/*      */     
/* 1406 */     if (commandEnvironment != null && commandEnvironment.getCommandOptions() != null) {
/* 1407 */       arrayList.addAll(Arrays.asList(commandEnvironment.getCommandOptions()));
/*      */     }
/*      */ 
/*      */     
/* 1411 */     Properties properties = paramActivationGroupDesc.getPropertyOverrides();
/* 1412 */     if (properties != null) {
/* 1413 */       Enumeration<?> enumeration = properties.propertyNames();
/* 1414 */       while (enumeration.hasMoreElements()) {
/*      */         
/* 1416 */         String str = (String)enumeration.nextElement();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1422 */         arrayList.add("-D" + str + "=" + properties.getProperty(str));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1429 */     for (byte b = 1; b < this.command.length; b++) {
/* 1430 */       arrayList.add(this.command[b]);
/*      */     }
/*      */     
/* 1433 */     String[] arrayOfString = new String[arrayList.size()];
/* 1434 */     System.arraycopy(arrayList.toArray(), 0, arrayOfString, 0, arrayOfString.length);
/*      */     
/* 1436 */     return arrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkArgs(ActivationGroupDesc paramActivationGroupDesc, String[] paramArrayOfString) throws SecurityException, ActivationException {
/* 1445 */     if (execPolicyMethod != null) {
/* 1446 */       if (paramArrayOfString == null) {
/* 1447 */         paramArrayOfString = activationArgs(paramActivationGroupDesc);
/*      */       }
/*      */       try {
/* 1450 */         execPolicyMethod.invoke(execPolicy, new Object[] { paramActivationGroupDesc, paramArrayOfString });
/* 1451 */       } catch (InvocationTargetException invocationTargetException) {
/* 1452 */         Throwable throwable = invocationTargetException.getTargetException();
/* 1453 */         if (throwable instanceof SecurityException) {
/* 1454 */           throw (SecurityException)throwable;
/*      */         }
/* 1456 */         throw new ActivationException(execPolicyMethod
/* 1457 */             .getName() + ": unexpected exception", invocationTargetException);
/*      */       
/*      */       }
/* 1460 */       catch (Exception exception) {
/* 1461 */         throw new ActivationException(execPolicyMethod
/* 1462 */             .getName() + ": unexpected exception", exception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ObjectEntry
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -5500114225321357856L;
/*      */     
/*      */     ActivationDesc desc;
/* 1474 */     volatile transient MarshalledObject<? extends Remote> stub = null;
/*      */     volatile transient boolean removed = false;
/*      */     
/*      */     ObjectEntry(ActivationDesc param1ActivationDesc) {
/* 1478 */       this.desc = param1ActivationDesc;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     synchronized MarshalledObject<? extends Remote> activate(ActivationID param1ActivationID, boolean param1Boolean, ActivationInstantiator param1ActivationInstantiator) throws RemoteException, ActivationException {
/* 1487 */       MarshalledObject<? extends Remote> marshalledObject = this.stub;
/* 1488 */       if (this.removed)
/* 1489 */         throw new UnknownObjectException("object removed"); 
/* 1490 */       if (!param1Boolean && marshalledObject != null) {
/* 1491 */         return marshalledObject;
/*      */       }
/*      */       
/* 1494 */       marshalledObject = param1ActivationInstantiator.newInstance(param1ActivationID, this.desc);
/* 1495 */       this.stub = marshalledObject;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1500 */       return marshalledObject;
/*      */     }
/*      */     
/*      */     void reset() {
/* 1504 */       this.stub = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addLogRecord(LogRecord paramLogRecord) throws ActivationException {
/* 1513 */     synchronized (this.log) {
/* 1514 */       checkShutdown();
/*      */       try {
/* 1516 */         this.log.update(paramLogRecord, true);
/* 1517 */       } catch (Exception exception) {
/* 1518 */         this.numUpdates = snapshotInterval;
/* 1519 */         System.err.println(getTextResource("rmid.log.update.warning"));
/* 1520 */         exception.printStackTrace();
/*      */       } 
/* 1522 */       if (++this.numUpdates < snapshotInterval) {
/*      */         return;
/*      */       }
/*      */       try {
/* 1526 */         this.log.snapshot(this);
/* 1527 */         this.numUpdates = 0;
/* 1528 */       } catch (Exception exception) {
/* 1529 */         System.err.println(
/* 1530 */             getTextResource("rmid.log.snapshot.warning"));
/* 1531 */         exception.printStackTrace();
/*      */         
/*      */         try {
/* 1534 */           this.system.shutdown();
/* 1535 */         } catch (RemoteException remoteException) {}
/*      */ 
/*      */ 
/*      */         
/* 1539 */         throw new ActivationException("log snapshot failed", exception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ActLogHandler
/*      */     extends LogHandler
/*      */   {
/*      */     public Object initialSnapshot() {
/* 1559 */       return new Activation();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object applyUpdate(Object param1Object1, Object param1Object2) throws Exception {
/* 1565 */       return ((Activation.LogRecord)param1Object1).apply(param1Object2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class LogRecord
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 8395140512322687529L;
/*      */ 
/*      */     
/*      */     private LogRecord() {}
/*      */ 
/*      */     
/*      */     abstract Object apply(Object param1Object) throws Exception;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogRegisterObject
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = -6280336276146085143L;
/*      */     
/*      */     private ActivationID id;
/*      */     private ActivationDesc desc;
/*      */     
/*      */     LogRegisterObject(ActivationID param1ActivationID, ActivationDesc param1ActivationDesc) {
/* 1592 */       this.id = param1ActivationID;
/* 1593 */       this.desc = param1ActivationDesc;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/*      */       try {
/* 1598 */         ((Activation)param1Object).getGroupEntry(this.desc.getGroupID())
/* 1599 */           .registerObject(this.id, this.desc, false);
/* 1600 */       } catch (Exception exception) {
/* 1601 */         System.err.println(
/* 1602 */             MessageFormat.format(Activation
/* 1603 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogRegisterObject" }));
/*      */         
/* 1605 */         exception.printStackTrace();
/*      */       } 
/* 1607 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogUnregisterObject
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = 6269824097396935501L;
/*      */     
/*      */     private ActivationID id;
/*      */     
/*      */     LogUnregisterObject(ActivationID param1ActivationID) {
/* 1620 */       this.id = param1ActivationID;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/*      */       try {
/* 1625 */         ((Activation)param1Object).getGroupEntry(this.id)
/* 1626 */           .unregisterObject(this.id, false);
/* 1627 */       } catch (Exception exception) {
/* 1628 */         System.err.println(
/* 1629 */             MessageFormat.format(Activation
/* 1630 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogUnregisterObject" }));
/*      */         
/* 1632 */         exception.printStackTrace();
/*      */       } 
/* 1634 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogRegisterGroup
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = -1966827458515403625L;
/*      */     
/*      */     private ActivationGroupID id;
/*      */     private ActivationGroupDesc desc;
/*      */     
/*      */     LogRegisterGroup(ActivationGroupID param1ActivationGroupID, ActivationGroupDesc param1ActivationGroupDesc) {
/* 1648 */       this.id = param1ActivationGroupID;
/* 1649 */       this.desc = param1ActivationGroupDesc;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     Object apply(Object param1Object) {
/* 1655 */       ((Activation)param1Object).getClass(); ((Activation)param1Object).groupTable.put(this.id, new Activation.GroupEntry(this.id, this.desc));
/*      */       
/* 1657 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogUpdateDesc
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = 545511539051179885L;
/*      */     
/*      */     private ActivationID id;
/*      */     
/*      */     private ActivationDesc desc;
/*      */     
/*      */     LogUpdateDesc(ActivationID param1ActivationID, ActivationDesc param1ActivationDesc) {
/* 1672 */       this.id = param1ActivationID;
/* 1673 */       this.desc = param1ActivationDesc;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/*      */       try {
/* 1678 */         ((Activation)param1Object).getGroupEntry(this.id)
/* 1679 */           .setActivationDesc(this.id, this.desc, false);
/* 1680 */       } catch (Exception exception) {
/* 1681 */         System.err.println(
/* 1682 */             MessageFormat.format(Activation
/* 1683 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogUpdateDesc" }));
/*      */         
/* 1685 */         exception.printStackTrace();
/*      */       } 
/* 1687 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogUpdateGroupDesc
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = -1271300989218424337L;
/*      */     
/*      */     private ActivationGroupID id;
/*      */     private ActivationGroupDesc desc;
/*      */     
/*      */     LogUpdateGroupDesc(ActivationGroupID param1ActivationGroupID, ActivationGroupDesc param1ActivationGroupDesc) {
/* 1701 */       this.id = param1ActivationGroupID;
/* 1702 */       this.desc = param1ActivationGroupDesc;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/*      */       try {
/* 1707 */         ((Activation)param1Object).getGroupEntry(this.id)
/* 1708 */           .setActivationGroupDesc(this.id, this.desc, false);
/* 1709 */       } catch (Exception exception) {
/* 1710 */         System.err.println(
/* 1711 */             MessageFormat.format(Activation
/* 1712 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogUpdateGroupDesc" }));
/*      */         
/* 1714 */         exception.printStackTrace();
/*      */       } 
/* 1716 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogUnregisterGroup
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = -3356306586522147344L;
/*      */     
/*      */     private ActivationGroupID id;
/*      */     
/*      */     LogUnregisterGroup(ActivationGroupID param1ActivationGroupID) {
/* 1729 */       this.id = param1ActivationGroupID;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/* 1733 */       Activation.GroupEntry groupEntry = (Activation.GroupEntry)((Activation)param1Object).groupTable.remove(this.id);
/*      */       try {
/* 1735 */         groupEntry.unregisterGroup(false);
/* 1736 */       } catch (Exception exception) {
/* 1737 */         System.err.println(
/* 1738 */             MessageFormat.format(Activation
/* 1739 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogUnregisterGroup" }));
/*      */         
/* 1741 */         exception.printStackTrace();
/*      */       } 
/* 1743 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LogGroupIncarnation
/*      */     extends LogRecord
/*      */   {
/*      */     private static final long serialVersionUID = 4146872747377631897L;
/*      */     
/*      */     private ActivationGroupID id;
/*      */     private long inc;
/*      */     
/*      */     LogGroupIncarnation(ActivationGroupID param1ActivationGroupID, long param1Long) {
/* 1757 */       this.id = param1ActivationGroupID;
/* 1758 */       this.inc = param1Long;
/*      */     }
/*      */     
/*      */     Object apply(Object param1Object) {
/*      */       try {
/* 1763 */         Activation.GroupEntry groupEntry = ((Activation)param1Object).getGroupEntry(this.id);
/* 1764 */         groupEntry.incarnation = this.inc;
/* 1765 */       } catch (Exception exception) {
/* 1766 */         System.err.println(
/* 1767 */             MessageFormat.format(Activation
/* 1768 */               .getTextResource("rmid.log.recover.warning"), new Object[] { "LogGroupIncarnation" }));
/*      */         
/* 1770 */         exception.printStackTrace();
/*      */       } 
/* 1772 */       return param1Object;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initCommand(String[] paramArrayOfString) {
/* 1780 */     this.command = new String[paramArrayOfString.length + 2];
/* 1781 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() {
/*      */             try {
/* 1784 */               Activation.this.command[0] = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
/*      */             }
/* 1786 */             catch (Exception exception) {
/* 1787 */               System.err.println(Activation
/* 1788 */                   .getTextResource("rmid.unfound.java.home.property"));
/* 1789 */               Activation.this.command[0] = "java";
/*      */             } 
/* 1791 */             return null;
/*      */           }
/*      */         });
/* 1794 */     System.arraycopy(paramArrayOfString, 0, this.command, 1, paramArrayOfString.length);
/* 1795 */     this.command[this.command.length - 1] = "sun.rmi.server.ActivationGroupInit";
/*      */   }
/*      */   
/*      */   private static void bomb(String paramString) {
/* 1799 */     System.err.println("rmid: " + paramString);
/* 1800 */     System.err.println(MessageFormat.format(getTextResource("rmid.usage"), new Object[] { "rmid" }));
/*      */     
/* 1802 */     System.exit(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DefaultExecPolicy
/*      */   {
/*      */     public void checkExecCommand(ActivationGroupDesc param1ActivationGroupDesc, String[] param1ArrayOfString) throws SecurityException {
/* 1815 */       PermissionCollection permissionCollection = getExecPermissions();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1820 */       Properties properties = param1ActivationGroupDesc.getPropertyOverrides();
/* 1821 */       if (properties != null) {
/* 1822 */         Enumeration<?> enumeration = properties.propertyNames();
/* 1823 */         while (enumeration.hasMoreElements()) {
/* 1824 */           String str1 = (String)enumeration.nextElement();
/* 1825 */           String str2 = properties.getProperty(str1);
/* 1826 */           String str3 = "-D" + str1 + "=" + str2;
/*      */           try {
/* 1828 */             checkPermission(permissionCollection, (Permission)new ExecOptionPermission(str3));
/*      */           }
/* 1830 */           catch (AccessControlException accessControlException) {
/* 1831 */             if (str2.equals("")) {
/* 1832 */               checkPermission(permissionCollection, (Permission)new ExecOptionPermission("-D" + str1));
/*      */               continue;
/*      */             } 
/* 1835 */             throw accessControlException;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1845 */       String str = param1ActivationGroupDesc.getClassName();
/* 1846 */       if ((str != null && 
/* 1847 */         !str.equals(ActivationGroupImpl.class
/* 1848 */           .getName())) || param1ActivationGroupDesc
/* 1849 */         .getLocation() != null || param1ActivationGroupDesc
/* 1850 */         .getData() != null)
/*      */       {
/* 1852 */         throw new AccessControlException("access denied (custom group implementation not allowed)");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1861 */       ActivationGroupDesc.CommandEnvironment commandEnvironment = param1ActivationGroupDesc.getCommandEnvironment();
/* 1862 */       if (commandEnvironment != null) {
/* 1863 */         String str1 = commandEnvironment.getCommandPath();
/* 1864 */         if (str1 != null) {
/* 1865 */           checkPermission(permissionCollection, (Permission)new ExecPermission(str1));
/*      */         }
/*      */         
/* 1868 */         String[] arrayOfString = commandEnvironment.getCommandOptions();
/* 1869 */         if (arrayOfString != null) {
/* 1870 */           for (String str2 : arrayOfString) {
/* 1871 */             checkPermission(permissionCollection, (Permission)new ExecOptionPermission(str2));
/*      */           }
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void checkConfiguration() {
/* 1885 */       Policy policy = AccessController.<Policy>doPrivileged(new PrivilegedAction<Policy>() {
/*      */             public Policy run() {
/* 1887 */               return Policy.getPolicy();
/*      */             }
/*      */           });
/* 1890 */       if (!(policy instanceof sun.security.provider.PolicyFile)) {
/*      */         return;
/*      */       }
/* 1893 */       PermissionCollection permissionCollection = getExecPermissions();
/* 1894 */       Enumeration<Permission> enumeration = permissionCollection.elements();
/* 1895 */       while (enumeration.hasMoreElements()) {
/*      */         
/* 1897 */         Permission permission = enumeration.nextElement();
/* 1898 */         if (permission instanceof java.security.AllPermission || permission instanceof ExecPermission || permission instanceof ExecOptionPermission) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1905 */       System.err.println(Activation.getTextResource("rmid.exec.perms.inadequate"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static PermissionCollection getExecPermissions() {
/* 1916 */       return AccessController.<PermissionCollection>doPrivileged(new PrivilegedAction<PermissionCollection>()
/*      */           {
/*      */             public PermissionCollection run() {
/* 1919 */               CodeSource codeSource = new CodeSource(null, (Certificate[])null);
/*      */               
/* 1921 */               Policy policy = Policy.getPolicy();
/* 1922 */               if (policy != null) {
/* 1923 */                 return policy.getPermissions(codeSource);
/*      */               }
/* 1925 */               return new Permissions();
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void checkPermission(PermissionCollection param1PermissionCollection, Permission param1Permission) throws AccessControlException {
/* 1937 */       if (!param1PermissionCollection.implies(param1Permission)) {
/* 1938 */         throw new AccessControlException("access denied " + param1Permission
/* 1939 */             .toString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] paramArrayOfString) {
/* 1949 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */     
/* 1953 */     if (System.getSecurityManager() == null) {
/* 1954 */       System.setSecurityManager(new SecurityManager());
/*      */     }
/*      */     
/*      */     try {
/* 1958 */       int i = 1098;
/* 1959 */       ActivationServerSocketFactory activationServerSocketFactory = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1966 */       Channel channel = AccessController.<Channel>doPrivileged(new PrivilegedExceptionAction<Channel>()
/*      */           {
/*      */             public Channel run() throws IOException {
/* 1969 */               return System.inheritedChannel();
/*      */             }
/*      */           });
/*      */       
/* 1973 */       if (channel != null && channel instanceof ServerSocketChannel) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1979 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */             {
/*      */               public Void run() throws IOException
/*      */               {
/* 1983 */                 File file = Files.createTempFile("rmid-err", null, (FileAttribute<?>[])new FileAttribute[0]).toFile();
/* 1984 */                 PrintStream printStream = new PrintStream(new FileOutputStream(file));
/*      */                 
/* 1986 */                 System.setErr(printStream);
/* 1987 */                 return null;
/*      */               }
/*      */             });
/*      */ 
/*      */         
/* 1992 */         ServerSocket serverSocket = ((ServerSocketChannel)channel).socket();
/* 1993 */         i = serverSocket.getLocalPort();
/* 1994 */         activationServerSocketFactory = new ActivationServerSocketFactory(serverSocket);
/*      */         
/* 1996 */         System.err.println(new Date());
/* 1997 */         System.err.println(getTextResource("rmid.inherited.channel.info") + ": " + channel);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2002 */       String str1 = null;
/* 2003 */       ArrayList<String> arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2008 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 2009 */         if (paramArrayOfString[b].equals("-port")) {
/* 2010 */           if (activationServerSocketFactory != null) {
/* 2011 */             bomb(getTextResource("rmid.syntax.port.badarg"));
/*      */           }
/* 2013 */           if (b + 1 < paramArrayOfString.length) {
/*      */             try {
/* 2015 */               i = Integer.parseInt(paramArrayOfString[++b]);
/* 2016 */             } catch (NumberFormatException numberFormatException) {
/* 2017 */               bomb(getTextResource("rmid.syntax.port.badnumber"));
/*      */             } 
/*      */           } else {
/* 2020 */             bomb(getTextResource("rmid.syntax.port.missing"));
/*      */           }
/*      */         
/* 2023 */         } else if (paramArrayOfString[b].equals("-log")) {
/* 2024 */           if (b + 1 < paramArrayOfString.length) {
/* 2025 */             str1 = paramArrayOfString[++b];
/*      */           } else {
/* 2027 */             bomb(getTextResource("rmid.syntax.log.missing"));
/*      */           }
/*      */         
/* 2030 */         } else if (paramArrayOfString[b].equals("-stop")) {
/* 2031 */           bool = true;
/*      */         }
/* 2033 */         else if (paramArrayOfString[b].startsWith("-C")) {
/* 2034 */           arrayList.add(paramArrayOfString[b].substring(2));
/*      */         } else {
/*      */           
/* 2037 */           bomb(MessageFormat.format(
/* 2038 */                 getTextResource("rmid.syntax.illegal.option"), new Object[] { paramArrayOfString[b] }));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2043 */       if (str1 == null) {
/* 2044 */         if (activationServerSocketFactory != null) {
/* 2045 */           bomb(getTextResource("rmid.syntax.log.required"));
/*      */         } else {
/* 2047 */           str1 = "log";
/*      */         } 
/*      */       }
/*      */       
/* 2051 */       debugExec = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.server.activation.debugExec"))).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2057 */       String str2 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.activation.execPolicy", null));
/*      */       
/* 2059 */       if (str2 == null) {
/* 2060 */         if (!bool) {
/* 2061 */           DefaultExecPolicy.checkConfiguration();
/*      */         }
/* 2063 */         str2 = "default";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2069 */       if (!str2.equals("none")) {
/* 2070 */         if (str2.equals("") || str2
/* 2071 */           .equals("default"))
/*      */         {
/* 2073 */           str2 = DefaultExecPolicy.class.getName();
/*      */         }
/*      */         
/*      */         try {
/* 2077 */           Class<?> clazz = getRMIClass(str2);
/* 2078 */           execPolicy = clazz.newInstance();
/*      */           
/* 2080 */           execPolicyMethod = clazz.getMethod("checkExecCommand", new Class[] { ActivationGroupDesc.class, String[].class });
/*      */         
/*      */         }
/* 2083 */         catch (Exception exception) {
/* 2084 */           if (debugExec) {
/* 2085 */             System.err.println(
/* 2086 */                 getTextResource("rmid.exec.policy.exception"));
/* 2087 */             exception.printStackTrace();
/*      */           } 
/* 2089 */           bomb(getTextResource("rmid.exec.policy.invalid"));
/*      */         } 
/*      */       } 
/*      */       
/* 2093 */       if (bool == true) {
/* 2094 */         final int finalPort = i;
/* 2095 */         AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */               public Void run() {
/* 2097 */                 System.setProperty("java.rmi.activation.port", 
/* 2098 */                     Integer.toString(finalPort));
/* 2099 */                 return null;
/*      */               }
/*      */             });
/* 2102 */         ActivationSystem activationSystem = ActivationGroup.getSystem();
/* 2103 */         activationSystem.shutdown();
/* 2104 */         System.exit(0);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2120 */       startActivation(i, activationServerSocketFactory, str1, arrayList
/* 2121 */           .<String>toArray(new String[arrayList.size()]));
/*      */       
/*      */       while (true) {
/*      */         try {
/*      */           while (true)
/* 2126 */             Thread.sleep(Long.MAX_VALUE);  break;
/* 2127 */         } catch (InterruptedException interruptedException) {}
/*      */       }
/*      */     
/* 2130 */     } catch (Exception exception) {
/* 2131 */       System.err.println(
/* 2132 */           MessageFormat.format(
/* 2133 */             getTextResource("rmid.unexpected.exception"), new Object[] { exception }));
/* 2134 */       exception.printStackTrace();
/*      */       
/* 2136 */       System.exit(1);
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getTextResource(String paramString) {
/* 2143 */     if (resources == null) {
/*      */       try {
/* 2145 */         resources = ResourceBundle.getBundle("sun.rmi.server.resources.rmid");
/*      */       }
/* 2147 */       catch (MissingResourceException missingResourceException) {}
/*      */       
/* 2149 */       if (resources == null)
/*      */       {
/* 2151 */         return "[missing resource file: " + paramString + "]";
/*      */       }
/*      */     } 
/*      */     
/* 2155 */     String str = null;
/*      */     try {
/* 2157 */       str = resources.getString(paramString);
/* 2158 */     } catch (MissingResourceException missingResourceException) {}
/*      */ 
/*      */     
/* 2161 */     if (str == null) {
/* 2162 */       return "[missing resource: " + paramString + "]";
/*      */     }
/* 2164 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Class<?> getRMIClass(String paramString) throws Exception {
/* 2170 */     return RMIClassLoader.loadClass(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized String Pstartgroup() throws ActivationException {
/*      */     while (true) {
/* 2185 */       checkShutdown();
/*      */       
/* 2187 */       if (this.groupSemaphore > 0) {
/* 2188 */         this.groupSemaphore--;
/* 2189 */         return "Group-" + this.groupCounter++;
/*      */       } 
/*      */       
/*      */       try {
/* 2193 */         wait();
/* 2194 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void Vstartgroup() {
/* 2206 */     this.groupSemaphore++;
/* 2207 */     notifyAll();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Activation() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ActivationServerSocketFactory
/*      */     implements RMIServerSocketFactory
/*      */   {
/*      */     private final ServerSocket serverSocket;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ActivationServerSocketFactory(ServerSocket param1ServerSocket) {
/* 2230 */       this.serverSocket = param1ServerSocket;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ServerSocket createServerSocket(int param1Int) throws IOException {
/* 2240 */       return new Activation.DelayedAcceptServerSocket(this.serverSocket);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class DelayedAcceptServerSocket
/*      */     extends ServerSocket
/*      */   {
/*      */     private final ServerSocket serverSocket;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DelayedAcceptServerSocket(ServerSocket param1ServerSocket) throws IOException {
/* 2258 */       this.serverSocket = param1ServerSocket;
/*      */     }
/*      */     
/*      */     public void bind(SocketAddress param1SocketAddress) throws IOException {
/* 2262 */       this.serverSocket.bind(param1SocketAddress);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void bind(SocketAddress param1SocketAddress, int param1Int) throws IOException {
/* 2268 */       this.serverSocket.bind(param1SocketAddress, param1Int);
/*      */     }
/*      */     
/*      */     public InetAddress getInetAddress() {
/* 2272 */       return AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>()
/*      */           {
/*      */             public InetAddress run()
/*      */             {
/* 2276 */               return Activation.DelayedAcceptServerSocket.this.serverSocket.getInetAddress();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*      */     public int getLocalPort() {
/* 2282 */       return this.serverSocket.getLocalPort();
/*      */     }
/*      */     
/*      */     public SocketAddress getLocalSocketAddress() {
/* 2286 */       return AccessController.<SocketAddress>doPrivileged(new PrivilegedAction<SocketAddress>()
/*      */           {
/*      */             public SocketAddress run()
/*      */             {
/* 2290 */               return Activation.DelayedAcceptServerSocket.this.serverSocket.getLocalSocketAddress();
/*      */             }
/*      */           });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Socket accept() throws IOException {
/* 2300 */       synchronized (Activation.initLock) { while (true) {
/*      */           try {
/* 2302 */             if (!Activation.initDone) {
/* 2303 */               Activation.initLock.wait(); continue;
/*      */             } 
/* 2305 */           } catch (InterruptedException interruptedException) {
/* 2306 */             throw new AssertionError(interruptedException);
/*      */           }  break;
/*      */         }  }
/* 2309 */        return this.serverSocket.accept();
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 2313 */       this.serverSocket.close();
/*      */     }
/*      */     
/*      */     public ServerSocketChannel getChannel() {
/* 2317 */       return this.serverSocket.getChannel();
/*      */     }
/*      */     
/*      */     public boolean isBound() {
/* 2321 */       return this.serverSocket.isBound();
/*      */     }
/*      */     
/*      */     public boolean isClosed() {
/* 2325 */       return this.serverSocket.isClosed();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSoTimeout(int param1Int) throws SocketException {
/* 2331 */       this.serverSocket.setSoTimeout(param1Int);
/*      */     }
/*      */     
/*      */     public int getSoTimeout() throws IOException {
/* 2335 */       return this.serverSocket.getSoTimeout();
/*      */     }
/*      */     
/*      */     public void setReuseAddress(boolean param1Boolean) throws SocketException {
/* 2339 */       this.serverSocket.setReuseAddress(param1Boolean);
/*      */     }
/*      */     
/*      */     public boolean getReuseAddress() throws SocketException {
/* 2343 */       return this.serverSocket.getReuseAddress();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2347 */       return this.serverSocket.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReceiveBufferSize(int param1Int) throws SocketException {
/* 2353 */       this.serverSocket.setReceiveBufferSize(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getReceiveBufferSize() throws SocketException {
/* 2359 */       return this.serverSocket.getReceiveBufferSize();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/Activation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */