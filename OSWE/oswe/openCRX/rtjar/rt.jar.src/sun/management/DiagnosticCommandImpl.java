/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.DiagnosticCommandMBean;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.Permission;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.ListenerNotFoundException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanNotificationInfo;
/*     */ import javax.management.MBeanOperationInfo;
/*     */ import javax.management.MBeanParameterInfo;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.Notification;
/*     */ import javax.management.NotificationFilter;
/*     */ import javax.management.NotificationListener;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
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
/*     */ class DiagnosticCommandImpl
/*     */   extends NotificationEmitterSupport
/*     */   implements DiagnosticCommandMBean
/*     */ {
/*     */   private final VMManagement jvm;
/*  44 */   private volatile Map<String, Wrapper> wrappers = null;
/*  45 */   private static final String strClassName = "".getClass().getName();
/*  46 */   private static final String strArrayClassName = String[].class.getName();
/*     */   
/*     */   private final boolean isSupported;
/*     */   private static final String notifName = "javax.management.Notification";
/*     */   
/*     */   public Object getAttribute(String paramString) throws AttributeNotFoundException, MBeanException, ReflectionException {
/*  52 */     throw new AttributeNotFoundException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(Attribute paramAttribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/*  58 */     throw new AttributeNotFoundException(paramAttribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeList getAttributes(String[] paramArrayOfString) {
/*  63 */     return new AttributeList();
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeList setAttributes(AttributeList paramAttributeList) {
/*  68 */     return new AttributeList();
/*     */   }
/*     */ 
/*     */   
/*     */   private class Wrapper
/*     */   {
/*     */     String name;
/*     */     String cmd;
/*     */     DiagnosticCommandInfo info;
/*     */     Permission permission;
/*     */     
/*     */     Wrapper(String param1String1, String param1String2, DiagnosticCommandInfo param1DiagnosticCommandInfo) throws InstantiationException {
/*  80 */       this.name = param1String1;
/*  81 */       this.cmd = param1String2;
/*  82 */       this.info = param1DiagnosticCommandInfo;
/*  83 */       this.permission = null;
/*  84 */       InstantiationException instantiationException = null;
/*  85 */       if (param1DiagnosticCommandInfo.getPermissionClass() != null) {
/*     */         try {
/*  87 */           Class<?> clazz = Class.forName(param1DiagnosticCommandInfo.getPermissionClass());
/*  88 */           if (param1DiagnosticCommandInfo.getPermissionAction() == null) {
/*     */             try {
/*  90 */               Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class });
/*  91 */               this.permission = (Permission)constructor.newInstance(new Object[] { param1DiagnosticCommandInfo.getPermissionName() });
/*     */             }
/*  93 */             catch (InstantiationException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException instantiationException1) {
/*     */ 
/*     */               
/*  96 */               instantiationException = instantiationException1;
/*     */             } 
/*     */           }
/*  99 */           if (this.permission == null) {
/*     */             try {
/* 101 */               Constructor<?> constructor = clazz.getConstructor(new Class[] { String.class, String.class });
/* 102 */               this.permission = (Permission)constructor.newInstance(new Object[] { param1DiagnosticCommandInfo
/* 103 */                     .getPermissionName(), param1DiagnosticCommandInfo
/* 104 */                     .getPermissionAction() });
/* 105 */             } catch (InstantiationException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException instantiationException1) {
/*     */ 
/*     */               
/* 108 */               instantiationException = instantiationException1;
/*     */             } 
/*     */           }
/* 111 */         } catch (ClassNotFoundException classNotFoundException) {}
/* 112 */         if (this.permission == null) {
/* 113 */           InstantiationException instantiationException1 = new InstantiationException("Unable to instantiate required permission");
/*     */           
/* 115 */           instantiationException1.initCause(instantiationException);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public String execute(String[] param1ArrayOfString) {
/* 121 */       if (this.permission != null) {
/* 122 */         SecurityManager securityManager = System.getSecurityManager();
/* 123 */         if (securityManager != null) {
/* 124 */           securityManager.checkPermission(this.permission);
/*     */         }
/*     */       } 
/* 127 */       if (param1ArrayOfString == null) {
/* 128 */         return DiagnosticCommandImpl.this.executeDiagnosticCommand(this.cmd);
/*     */       }
/* 130 */       StringBuilder stringBuilder = new StringBuilder();
/* 131 */       stringBuilder.append(this.cmd);
/* 132 */       for (byte b = 0; b < param1ArrayOfString.length; b++) {
/* 133 */         if (param1ArrayOfString[b] == null) {
/* 134 */           throw new IllegalArgumentException("Invalid null argument");
/*     */         }
/* 136 */         stringBuilder.append(" ");
/* 137 */         stringBuilder.append(param1ArrayOfString[b]);
/*     */       } 
/* 139 */       return DiagnosticCommandImpl.this.executeDiagnosticCommand(stringBuilder.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class OperationInfoComparator
/*     */     implements Comparator<MBeanOperationInfo>
/*     */   {
/*     */     private OperationInfoComparator() {}
/*     */ 
/*     */     
/*     */     public int compare(MBeanOperationInfo param1MBeanOperationInfo1, MBeanOperationInfo param1MBeanOperationInfo2) {
/* 152 */       return param1MBeanOperationInfo1.getName().compareTo(param1MBeanOperationInfo2.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public MBeanInfo getMBeanInfo() {
/*     */     Map<? extends String, ? extends Wrapper> map;
/* 158 */     TreeSet<MBeanOperationInfo> treeSet = new TreeSet(new OperationInfoComparator());
/*     */     
/* 160 */     if (!this.isSupported) {
/* 161 */       map = Collections.EMPTY_MAP;
/*     */     } else {
/*     */       try {
/* 164 */         String[] arrayOfString = getDiagnosticCommands();
/* 165 */         DiagnosticCommandInfo[] arrayOfDiagnosticCommandInfo = getDiagnosticCommandInfo(arrayOfString);
/* 166 */         MBeanParameterInfo[] arrayOfMBeanParameterInfo = { new MBeanParameterInfo("arguments", strArrayClassName, "Array of Diagnostic Commands Arguments and Options") };
/*     */ 
/*     */ 
/*     */         
/* 170 */         map = (Map)new HashMap<>();
/* 171 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 172 */           String str = transform(arrayOfString[b]);
/*     */           try {
/* 174 */             Wrapper wrapper = new Wrapper(str, arrayOfString[b], arrayOfDiagnosticCommandInfo[b]);
/* 175 */             map.put(str, wrapper);
/* 176 */             treeSet.add(new MBeanOperationInfo(wrapper.name, wrapper.info
/*     */                   
/* 178 */                   .getDescription(), (wrapper.info
/* 179 */                   .getArgumentsInfo() == null || wrapper.info
/* 180 */                   .getArgumentsInfo().isEmpty()) ? null : arrayOfMBeanParameterInfo, strClassName, 2, 
/*     */ 
/*     */ 
/*     */                   
/* 184 */                   commandDescriptor(wrapper)));
/* 185 */           } catch (InstantiationException instantiationException) {}
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 191 */       catch (IllegalArgumentException|UnsupportedOperationException illegalArgumentException) {
/* 192 */         map = Collections.EMPTY_MAP;
/*     */       } 
/*     */     } 
/* 195 */     this.wrappers = Collections.unmodifiableMap(map);
/* 196 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 197 */     hashMap.put("immutableInfo", "false");
/* 198 */     hashMap.put("interfaceClassName", "com.sun.management.DiagnosticCommandMBean");
/* 199 */     hashMap.put("mxbean", "false");
/* 200 */     ImmutableDescriptor immutableDescriptor = new ImmutableDescriptor((Map)hashMap);
/* 201 */     return new MBeanInfo(
/* 202 */         getClass().getName(), "Diagnostic Commands", null, null, (MBeanOperationInfo[])treeSet
/*     */ 
/*     */ 
/*     */         
/* 206 */         .toArray((Object[])new MBeanOperationInfo[treeSet.size()]), 
/* 207 */         getNotificationInfo(), immutableDescriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws MBeanException, ReflectionException {
/* 214 */     if (!this.isSupported) {
/* 215 */       throw new UnsupportedOperationException();
/*     */     }
/* 217 */     if (this.wrappers == null) {
/* 218 */       getMBeanInfo();
/*     */     }
/* 220 */     Wrapper wrapper = this.wrappers.get(paramString);
/* 221 */     if (wrapper != null) {
/* 222 */       if (wrapper.info.getArgumentsInfo().isEmpty() && (paramArrayOfObject == null || paramArrayOfObject.length == 0) && (paramArrayOfString == null || paramArrayOfString.length == 0))
/*     */       {
/*     */         
/* 225 */         return wrapper.execute(null); } 
/* 226 */       if (paramArrayOfObject != null && paramArrayOfObject.length == 1 && paramArrayOfString != null && paramArrayOfString.length == 1 && paramArrayOfString[0] != null && paramArrayOfString[0]
/*     */ 
/*     */         
/* 229 */         .compareTo(strArrayClassName) == 0) {
/* 230 */         return wrapper.execute((String[])paramArrayOfObject[0]);
/*     */       }
/*     */     } 
/* 233 */     throw new ReflectionException(new NoSuchMethodException(paramString));
/*     */   }
/*     */   
/*     */   private static String transform(String paramString) {
/* 237 */     StringBuilder stringBuilder = new StringBuilder();
/* 238 */     boolean bool1 = true;
/* 239 */     boolean bool2 = false;
/* 240 */     for (byte b = 0; b < paramString.length(); b++) {
/* 241 */       char c = paramString.charAt(b);
/* 242 */       if (c == '.' || c == '_') {
/* 243 */         bool1 = false;
/* 244 */         bool2 = true;
/*     */       }
/* 246 */       else if (bool2) {
/* 247 */         bool2 = false;
/* 248 */         stringBuilder.append(Character.toUpperCase(c));
/* 249 */       } else if (bool1) {
/* 250 */         stringBuilder.append(Character.toLowerCase(c));
/*     */       } else {
/* 252 */         stringBuilder.append(c);
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private Descriptor commandDescriptor(Wrapper paramWrapper) throws IllegalArgumentException {
/* 260 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 261 */     hashMap.put("dcmd.name", paramWrapper.info.getName());
/* 262 */     hashMap.put("dcmd.description", paramWrapper.info.getDescription());
/* 263 */     hashMap.put("dcmd.vmImpact", paramWrapper.info.getImpact());
/* 264 */     hashMap.put("dcmd.permissionClass", paramWrapper.info.getPermissionClass());
/* 265 */     hashMap.put("dcmd.permissionName", paramWrapper.info.getPermissionName());
/* 266 */     hashMap.put("dcmd.permissionAction", paramWrapper.info.getPermissionAction());
/* 267 */     hashMap.put("dcmd.enabled", Boolean.valueOf(paramWrapper.info.isEnabled()));
/* 268 */     StringBuilder stringBuilder = new StringBuilder();
/* 269 */     stringBuilder.append("help ");
/* 270 */     stringBuilder.append(paramWrapper.info.getName());
/* 271 */     hashMap.put("dcmd.help", executeDiagnosticCommand(stringBuilder.toString()));
/* 272 */     if (paramWrapper.info.getArgumentsInfo() != null && !paramWrapper.info.getArgumentsInfo().isEmpty()) {
/* 273 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 274 */       for (DiagnosticCommandArgumentInfo diagnosticCommandArgumentInfo : paramWrapper.info.getArgumentsInfo()) {
/* 275 */         HashMap<Object, Object> hashMap2 = new HashMap<>();
/* 276 */         hashMap2.put("dcmd.arg.name", diagnosticCommandArgumentInfo.getName());
/* 277 */         hashMap2.put("dcmd.arg.type", diagnosticCommandArgumentInfo.getType());
/* 278 */         hashMap2.put("dcmd.arg.description", diagnosticCommandArgumentInfo.getDescription());
/* 279 */         hashMap2.put("dcmd.arg.isMandatory", Boolean.valueOf(diagnosticCommandArgumentInfo.isMandatory()));
/* 280 */         hashMap2.put("dcmd.arg.isMultiple", Boolean.valueOf(diagnosticCommandArgumentInfo.isMultiple()));
/* 281 */         boolean bool = diagnosticCommandArgumentInfo.isOption();
/* 282 */         hashMap2.put("dcmd.arg.isOption", Boolean.valueOf(bool));
/* 283 */         if (!bool) {
/* 284 */           hashMap2.put("dcmd.arg.position", Integer.valueOf(diagnosticCommandArgumentInfo.getPosition()));
/*     */         } else {
/* 286 */           hashMap2.put("dcmd.arg.position", Integer.valueOf(-1));
/*     */         } 
/* 288 */         hashMap1.put(diagnosticCommandArgumentInfo.getName(), new ImmutableDescriptor((Map)hashMap2));
/*     */       } 
/* 290 */       hashMap.put("dcmd.arguments", new ImmutableDescriptor((Map)hashMap1));
/*     */     } 
/* 292 */     return new ImmutableDescriptor((Map)hashMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 298 */   private static final String[] diagFramNotifTypes = new String[] { "jmx.mbean.info.changed" };
/*     */   private MBeanNotificationInfo[] notifInfo;
/*     */   
/*     */   DiagnosticCommandImpl(VMManagement paramVMManagement) {
/* 302 */     this.notifInfo = null;
/*     */     this.jvm = paramVMManagement;
/*     */     this.isSupported = paramVMManagement.isRemoteDiagnosticCommandsSupported();
/*     */   } public MBeanNotificationInfo[] getNotificationInfo() {
/* 306 */     synchronized (this) {
/* 307 */       if (this.notifInfo == null) {
/* 308 */         this.notifInfo = new MBeanNotificationInfo[1];
/* 309 */         this.notifInfo[0] = new MBeanNotificationInfo(diagFramNotifTypes, "javax.management.Notification", "Diagnostic Framework Notification");
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 315 */     return (MBeanNotificationInfo[])this.notifInfo.clone();
/*     */   }
/*     */   
/* 318 */   private static long seqNumber = 0L;
/*     */   private static long getNextSeqNumber() {
/* 320 */     return ++seqNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createDiagnosticFrameworkNotification() {
/* 325 */     if (!hasListeners()) {
/*     */       return;
/*     */     }
/* 328 */     ObjectName objectName = null;
/*     */     try {
/* 330 */       objectName = ObjectName.getInstance("com.sun.management:type=DiagnosticCommand");
/* 331 */     } catch (MalformedObjectNameException malformedObjectNameException) {}
/*     */ 
/*     */     
/* 334 */     Notification notification = new Notification("jmx.mbean.info.changed", objectName, getNextSeqNumber());
/* 335 */     notification.setUserData(getMBeanInfo());
/* 336 */     sendNotification(notification);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) {
/* 343 */     boolean bool1 = hasListeners();
/* 344 */     super.addNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/* 345 */     boolean bool2 = hasListeners();
/* 346 */     if (!bool1 && bool2) {
/* 347 */       setNotificationEnabled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/* 354 */     boolean bool1 = hasListeners();
/* 355 */     super.removeNotificationListener(paramNotificationListener);
/* 356 */     boolean bool2 = hasListeners();
/* 357 */     if (bool1 && !bool2) {
/* 358 */       setNotificationEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException {
/* 367 */     boolean bool1 = hasListeners();
/* 368 */     super.removeNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/* 369 */     boolean bool2 = hasListeners();
/* 370 */     if (bool1 && !bool2)
/* 371 */       setNotificationEnabled(false); 
/*     */   }
/*     */   
/*     */   private native void setNotificationEnabled(boolean paramBoolean);
/*     */   
/*     */   private native String[] getDiagnosticCommands();
/*     */   
/*     */   private native DiagnosticCommandInfo[] getDiagnosticCommandInfo(String[] paramArrayOfString);
/*     */   
/*     */   private native String executeDiagnosticCommand(String paramString);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/DiagnosticCommandImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */