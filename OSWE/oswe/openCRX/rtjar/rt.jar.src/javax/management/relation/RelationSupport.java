/*      */ package javax.management.relation;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.ReflectionException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RelationSupport
/*      */   implements RelationSupportMBean, MBeanRegistration
/*      */ {
/*   79 */   private String myRelId = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   87 */   private ObjectName myRelServiceName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   private MBeanServer myRelServiceMBeanServer = null;
/*      */ 
/*      */ 
/*      */   
/*  103 */   private String myRelTypeName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private final Map<String, Role> myRoleName2ValueMap = new HashMap<>();
/*      */ 
/*      */   
/*  114 */   private final AtomicBoolean myInRelServFlg = new AtomicBoolean();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RelationSupport(String paramString1, ObjectName paramObjectName, String paramString2, RoleList paramRoleList) throws InvalidRoleValueException, IllegalArgumentException {
/*  165 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "RelationSupport");
/*      */ 
/*      */ 
/*      */     
/*  169 */     initMembers(paramString1, paramObjectName, null, paramString2, paramRoleList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  175 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "RelationSupport");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RelationSupport(String paramString1, ObjectName paramObjectName, MBeanServer paramMBeanServer, String paramString2, RoleList paramRoleList) throws InvalidRoleValueException, IllegalArgumentException {
/*  237 */     if (paramMBeanServer == null) {
/*  238 */       String str = "Invalid parameter.";
/*  239 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  242 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "RelationSupport");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  247 */     initMembers(paramString1, paramObjectName, paramMBeanServer, paramString2, paramRoleList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  253 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "RelationSupport");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ObjectName> getRole(String paramString) throws IllegalArgumentException, RoleNotFoundException, RelationServiceNotRegisteredException {
/*  284 */     if (paramString == null) {
/*  285 */       String str = "Invalid parameter.";
/*  286 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  289 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getRole", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  294 */     List<ObjectName> list = Util.<List>cast(
/*  295 */         getRoleInt(paramString, false, null, false));
/*      */     
/*  297 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getRole");
/*  298 */     return list;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RoleResult getRoles(String[] paramArrayOfString) throws IllegalArgumentException, RelationServiceNotRegisteredException {
/*  322 */     if (paramArrayOfString == null) {
/*  323 */       String str = "Invalid parameter.";
/*  324 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  327 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getRoles");
/*      */ 
/*      */     
/*  330 */     RoleResult roleResult = getRolesInt(paramArrayOfString, false, null);
/*      */     
/*  332 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getRoles");
/*  333 */     return roleResult;
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
/*      */   
/*      */   public RoleResult getAllRoles() throws RelationServiceNotRegisteredException {
/*  349 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getAllRoles");
/*      */ 
/*      */     
/*  352 */     RoleResult roleResult = null;
/*      */     try {
/*  354 */       roleResult = getAllRolesInt(false, null);
/*  355 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */ 
/*      */     
/*  359 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getAllRoles");
/*  360 */     return roleResult;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RoleList retrieveAllRoles() {
/*      */     RoleList roleList;
/*  370 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "retrieveAllRoles");
/*      */ 
/*      */ 
/*      */     
/*  374 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/*  376 */       roleList = new RoleList(new ArrayList<>(this.myRoleName2ValueMap.values()));
/*      */     } 
/*      */     
/*  379 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "retrieveAllRoles");
/*      */     
/*  381 */     return roleList;
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
/*      */   
/*      */   public Integer getRoleCardinality(String paramString) throws IllegalArgumentException, RoleNotFoundException {
/*      */     Role role;
/*  398 */     if (paramString == null) {
/*  399 */       String str = "Invalid parameter.";
/*  400 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  403 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getRoleCardinality", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/*  410 */       role = this.myRoleName2ValueMap.get(paramString);
/*      */     } 
/*  412 */     if (role == null) {
/*  413 */       boolean bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  419 */         RelationService.throwRoleProblemException(bool, paramString);
/*      */       }
/*  421 */       catch (InvalidRoleValueException invalidRoleValueException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  427 */     List<ObjectName> list = role.getRoleValue();
/*      */     
/*  429 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getRoleCardinality");
/*      */     
/*  431 */     return Integer.valueOf(list.size());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRole(Role paramRole) throws IllegalArgumentException, RoleNotFoundException, RelationTypeNotFoundException, InvalidRoleValueException, RelationServiceNotRegisteredException, RelationNotFoundException {
/*  474 */     if (paramRole == null) {
/*  475 */       String str = "Invalid parameter.";
/*  476 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  479 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "setRole", paramRole);
/*      */ 
/*      */ 
/*      */     
/*  483 */     Object object = setRoleInt(paramRole, false, null, false);
/*      */     
/*  485 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "setRole");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RoleResult setRoles(RoleList paramRoleList) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationTypeNotFoundException, RelationNotFoundException {
/*  519 */     if (paramRoleList == null) {
/*  520 */       String str = "Invalid parameter.";
/*  521 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  524 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "setRoles", paramRoleList);
/*      */ 
/*      */     
/*  527 */     RoleResult roleResult = setRolesInt(paramRoleList, false, null);
/*      */     
/*  529 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "setRoles");
/*  530 */     return roleResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMBeanUnregistration(ObjectName paramObjectName, String paramString) throws IllegalArgumentException, RoleNotFoundException, InvalidRoleValueException, RelationServiceNotRegisteredException, RelationTypeNotFoundException, RelationNotFoundException {
/*  568 */     if (paramObjectName == null || paramString == null) {
/*  569 */       String str = "Invalid parameter.";
/*  570 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  573 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "handleMBeanUnregistration", new Object[] { paramObjectName, paramString });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     handleMBeanUnregistrationInt(paramObjectName, paramString, false, null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "handleMBeanUnregistration");
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
/*      */   public Map<ObjectName, List<String>> getReferencedMBeans() {
/*  597 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getReferencedMBeans");
/*      */ 
/*      */     
/*  600 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/*  603 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/*  605 */       for (Role role : this.myRoleName2ValueMap.values()) {
/*      */         
/*  607 */         String str = role.getRoleName();
/*      */         
/*  609 */         List<ObjectName> list = role.getRoleValue();
/*      */         
/*  611 */         for (ObjectName objectName : list) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  616 */           List<String> list1 = (List)hashMap.get(objectName);
/*      */           
/*  618 */           boolean bool = false;
/*  619 */           if (list1 == null) {
/*  620 */             bool = true;
/*  621 */             list1 = new ArrayList();
/*      */           } 
/*  623 */           list1.add(str);
/*  624 */           if (bool) {
/*  625 */             hashMap.put(objectName, list1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  631 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getReferencedMBeans");
/*      */     
/*  633 */     return (Map)hashMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRelationTypeName() {
/*  640 */     return this.myRelTypeName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectName getRelationServiceName() {
/*  649 */     return this.myRelServiceName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRelationId() {
/*  659 */     return this.myRelId;
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
/*      */ 
/*      */   
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  676 */     this.myRelServiceMBeanServer = paramMBeanServer;
/*  677 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postRegister(Boolean paramBoolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preDeregister() throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postDeregister() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isInRelationService() {
/*  705 */     return Boolean.valueOf(this.myInRelServFlg.get());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRelationServiceManagementFlag(Boolean paramBoolean) throws IllegalArgumentException {
/*  711 */     if (paramBoolean == null) {
/*  712 */       String str = "Invalid parameter.";
/*  713 */       throw new IllegalArgumentException(str);
/*      */     } 
/*  715 */     this.myInRelServFlg.set(paramBoolean.booleanValue());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object getRoleInt(String paramString, boolean paramBoolean1, RelationService paramRelationService, boolean paramBoolean2) throws IllegalArgumentException, RoleNotFoundException, RelationServiceNotRegisteredException {
/*      */     Role role;
/*      */     RoleUnresolved roleUnresolved;
/*  776 */     if (paramString == null || (paramBoolean1 && paramRelationService == null)) {
/*      */       
/*  778 */       String str = "Invalid parameter.";
/*  779 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  782 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getRoleInt", paramString);
/*      */ 
/*      */     
/*  785 */     int i = 0;
/*      */ 
/*      */     
/*  788 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/*  790 */       role = this.myRoleName2ValueMap.get(paramString);
/*      */     } 
/*      */     
/*  793 */     if (role == null) {
/*  794 */       i = 1;
/*      */     } else {
/*      */       Integer integer;
/*      */ 
/*      */ 
/*      */       
/*  800 */       if (paramBoolean1) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/*  806 */           integer = paramRelationService.checkRoleReading(paramString, this.myRelTypeName);
/*      */         }
/*  808 */         catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/*  809 */           throw new RuntimeException(relationTypeNotFoundException.getMessage());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  817 */         Object[] arrayOfObject = new Object[2];
/*  818 */         arrayOfObject[0] = paramString;
/*  819 */         arrayOfObject[1] = this.myRelTypeName;
/*  820 */         String[] arrayOfString = new String[2];
/*  821 */         arrayOfString[0] = "java.lang.String";
/*  822 */         arrayOfString[1] = "java.lang.String";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  831 */           integer = (Integer)this.myRelServiceMBeanServer.invoke(this.myRelServiceName, "checkRoleReading", arrayOfObject, arrayOfString);
/*      */ 
/*      */         
/*      */         }
/*  835 */         catch (MBeanException mBeanException) {
/*  836 */           throw new RuntimeException("incorrect relation type");
/*  837 */         } catch (ReflectionException reflectionException) {
/*  838 */           throw new RuntimeException(reflectionException.getMessage());
/*  839 */         } catch (InstanceNotFoundException instanceNotFoundException) {
/*  840 */           throw new RelationServiceNotRegisteredException(instanceNotFoundException
/*  841 */               .getMessage());
/*      */         } 
/*      */       } 
/*      */       
/*  845 */       i = integer.intValue();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  850 */     if (i == 0) {
/*      */ 
/*      */       
/*  853 */       if (!paramBoolean2)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  858 */         ArrayList<ObjectName> arrayList = new ArrayList<>(role.getRoleValue());
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*  863 */         Role role1 = (Role)role.clone();
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  869 */       if (!paramBoolean2) {
/*      */         
/*      */         try {
/*      */           
/*  873 */           RelationService.throwRoleProblemException(i, paramString);
/*      */ 
/*      */           
/*  876 */           return null;
/*  877 */         } catch (InvalidRoleValueException invalidRoleValueException) {
/*  878 */           throw new RuntimeException(invalidRoleValueException.getMessage());
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  884 */       roleUnresolved = new RoleUnresolved(paramString, null, i);
/*      */     } 
/*      */ 
/*      */     
/*  888 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getRoleInt");
/*  889 */     return roleUnresolved;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   RoleResult getRolesInt(String[] paramArrayOfString, boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RelationServiceNotRegisteredException {
/*  918 */     if (paramArrayOfString == null || (paramBoolean && paramRelationService == null)) {
/*      */       
/*  920 */       String str = "Invalid parameter.";
/*  921 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  924 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getRolesInt");
/*      */ 
/*      */     
/*  927 */     RoleList roleList = new RoleList();
/*  928 */     RoleUnresolvedList roleUnresolvedList = new RoleUnresolvedList();
/*      */     
/*  930 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/*  931 */       Object object; String str = paramArrayOfString[b];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  939 */         object = getRoleInt(str, paramBoolean, paramRelationService, true);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  944 */       catch (RoleNotFoundException roleNotFoundException) {
/*  945 */         return null;
/*      */       } 
/*      */       
/*  948 */       if (object instanceof Role) {
/*      */ 
/*      */         
/*      */         try {
/*  952 */           roleList.add((Role)object);
/*  953 */         } catch (IllegalArgumentException illegalArgumentException) {
/*  954 */           throw new RuntimeException(illegalArgumentException.getMessage());
/*      */         }
/*      */       
/*  957 */       } else if (object instanceof RoleUnresolved) {
/*      */ 
/*      */         
/*      */         try {
/*  961 */           roleUnresolvedList.add((RoleUnresolved)object);
/*  962 */         } catch (IllegalArgumentException illegalArgumentException) {
/*  963 */           throw new RuntimeException(illegalArgumentException.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  968 */     RoleResult roleResult = new RoleResult(roleList, roleUnresolvedList);
/*  969 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getRolesInt");
/*      */     
/*  971 */     return roleResult;
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
/*      */ 
/*      */   
/*      */   RoleResult getAllRolesInt(boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RelationServiceNotRegisteredException {
/*      */     ArrayList arrayList;
/*  989 */     if (paramBoolean && paramRelationService == null) {
/*  990 */       String str = "Invalid parameter.";
/*  991 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  994 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "getAllRolesInt");
/*      */ 
/*      */ 
/*      */     
/*  998 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/* 1000 */       arrayList = new ArrayList(this.myRoleName2ValueMap.keySet());
/*      */     } 
/* 1002 */     String[] arrayOfString = new String[arrayList.size()];
/* 1003 */     arrayList.toArray((Object[])arrayOfString);
/*      */     
/* 1005 */     RoleResult roleResult = getRolesInt(arrayOfString, paramBoolean, paramRelationService);
/*      */ 
/*      */ 
/*      */     
/* 1009 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "getAllRolesInt");
/*      */     
/* 1011 */     return roleResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object setRoleInt(Role paramRole, boolean paramBoolean1, RelationService paramRelationService, boolean paramBoolean2) throws IllegalArgumentException, RoleNotFoundException, InvalidRoleValueException, RelationServiceNotRegisteredException, RelationTypeNotFoundException, RelationNotFoundException {
/*      */     Role role1;
/*      */     List<ObjectName> list;
/*      */     Boolean bool;
/*      */     RoleUnresolved roleUnresolved;
/* 1080 */     if (paramRole == null || (paramBoolean1 && paramRelationService == null)) {
/*      */       
/* 1082 */       String str1 = "Invalid parameter.";
/* 1083 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 1086 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "setRoleInt", new Object[] { paramRole, 
/* 1087 */           Boolean.valueOf(paramBoolean1), paramRelationService, 
/* 1088 */           Boolean.valueOf(paramBoolean2) });
/*      */     
/* 1090 */     String str = paramRole.getRoleName();
/* 1091 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1099 */     synchronized (this.myRoleName2ValueMap) {
/* 1100 */       role1 = this.myRoleName2ValueMap.get(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1106 */     if (role1 == null) {
/* 1107 */       bool = Boolean.valueOf(true);
/* 1108 */       list = new ArrayList();
/*      */     } else {
/*      */       
/* 1111 */       bool = Boolean.valueOf(false);
/* 1112 */       list = role1.getRoleValue();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*      */       Integer integer;
/*      */ 
/*      */       
/* 1120 */       if (paramBoolean1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1126 */         integer = paramRelationService.checkRoleWriting(paramRole, this.myRelTypeName, bool);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1135 */         Object[] arrayOfObject = new Object[3];
/* 1136 */         arrayOfObject[0] = paramRole;
/* 1137 */         arrayOfObject[1] = this.myRelTypeName;
/* 1138 */         arrayOfObject[2] = bool;
/* 1139 */         String[] arrayOfString = new String[3];
/* 1140 */         arrayOfString[0] = "javax.management.relation.Role";
/* 1141 */         arrayOfString[1] = "java.lang.String";
/* 1142 */         arrayOfString[2] = "java.lang.Boolean";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1153 */         integer = (Integer)this.myRelServiceMBeanServer.invoke(this.myRelServiceName, "checkRoleWriting", arrayOfObject, arrayOfString);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1159 */       i = integer.intValue();
/*      */     }
/* 1161 */     catch (MBeanException mBeanException) {
/*      */ 
/*      */       
/* 1164 */       Exception exception = mBeanException.getTargetException();
/* 1165 */       if (exception instanceof RelationTypeNotFoundException) {
/* 1166 */         throw (RelationTypeNotFoundException)exception;
/*      */       }
/*      */       
/* 1169 */       throw new RuntimeException(exception.getMessage());
/*      */     
/*      */     }
/* 1172 */     catch (ReflectionException reflectionException) {
/* 1173 */       throw new RuntimeException(reflectionException.getMessage());
/*      */     }
/* 1175 */     catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 1176 */       throw new RuntimeException(relationTypeNotFoundException.getMessage());
/*      */     }
/* 1178 */     catch (InstanceNotFoundException instanceNotFoundException) {
/* 1179 */       throw new RelationServiceNotRegisteredException(instanceNotFoundException.getMessage());
/*      */     } 
/*      */     
/* 1182 */     Role role2 = null;
/*      */     
/* 1184 */     if (i == 0) {
/*      */       
/* 1186 */       if (!bool.booleanValue()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1197 */         sendRoleUpdateNotification(paramRole, list, paramBoolean1, paramRelationService);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1204 */         updateRelationServiceMap(paramRole, list, paramBoolean1, paramRelationService);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1212 */       synchronized (this.myRoleName2ValueMap) {
/* 1213 */         this.myRoleName2ValueMap.put(str, (Role)paramRole
/* 1214 */             .clone());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1219 */       if (paramBoolean2)
/*      */       {
/* 1221 */         role2 = paramRole;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1228 */       if (!paramBoolean2) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1233 */         RelationService.throwRoleProblemException(i, str);
/*      */ 
/*      */         
/* 1236 */         return null;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1242 */       roleUnresolved = new RoleUnresolved(str, paramRole.getRoleValue(), i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1247 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "setRoleInt");
/* 1248 */     return roleUnresolved;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendRoleUpdateNotification(Role paramRole, List<ObjectName> paramList, boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationNotFoundException {
/* 1282 */     if (paramRole == null || paramList == null || (paramBoolean && paramRelationService == null)) {
/*      */ 
/*      */       
/* 1285 */       String str = "Invalid parameter.";
/* 1286 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1289 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "sendRoleUpdateNotification", new Object[] { paramRole, paramList, 
/*      */           
/* 1291 */           Boolean.valueOf(paramBoolean), paramRelationService });
/*      */     
/* 1293 */     if (paramBoolean) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 1298 */         paramRelationService.sendRoleUpdateNotification(this.myRelId, paramRole, paramList);
/*      */       
/*      */       }
/* 1301 */       catch (RelationNotFoundException relationNotFoundException) {
/* 1302 */         throw new RuntimeException(relationNotFoundException.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1307 */       Object[] arrayOfObject = new Object[3];
/* 1308 */       arrayOfObject[0] = this.myRelId;
/* 1309 */       arrayOfObject[1] = paramRole;
/* 1310 */       arrayOfObject[2] = paramList;
/* 1311 */       String[] arrayOfString = new String[3];
/* 1312 */       arrayOfString[0] = "java.lang.String";
/* 1313 */       arrayOfString[1] = "javax.management.relation.Role";
/* 1314 */       arrayOfString[2] = "java.util.List";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1325 */         this.myRelServiceMBeanServer.invoke(this.myRelServiceName, "sendRoleUpdateNotification", arrayOfObject, arrayOfString);
/*      */ 
/*      */       
/*      */       }
/* 1329 */       catch (ReflectionException reflectionException) {
/* 1330 */         throw new RuntimeException(reflectionException.getMessage());
/* 1331 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/* 1332 */         throw new RelationServiceNotRegisteredException(instanceNotFoundException
/* 1333 */             .getMessage());
/* 1334 */       } catch (MBeanException mBeanException) {
/* 1335 */         Exception exception = mBeanException.getTargetException();
/* 1336 */         if (exception instanceof RelationNotFoundException) {
/* 1337 */           throw (RelationNotFoundException)exception;
/*      */         }
/* 1339 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1344 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "sendRoleUpdateNotification");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateRelationServiceMap(Role paramRole, List<ObjectName> paramList, boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationNotFoundException {
/* 1379 */     if (paramRole == null || paramList == null || (paramBoolean && paramRelationService == null)) {
/*      */ 
/*      */       
/* 1382 */       String str = "Invalid parameter.";
/* 1383 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1386 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "updateRelationServiceMap", new Object[] { paramRole, paramList, 
/*      */           
/* 1388 */           Boolean.valueOf(paramBoolean), paramRelationService });
/*      */     
/* 1390 */     if (paramBoolean) {
/*      */ 
/*      */       
/*      */       try {
/* 1394 */         paramRelationService.updateRoleMap(this.myRelId, paramRole, paramList);
/*      */       
/*      */       }
/* 1397 */       catch (RelationNotFoundException relationNotFoundException) {
/* 1398 */         throw new RuntimeException(relationNotFoundException.getMessage());
/*      */       } 
/*      */     } else {
/*      */       
/* 1402 */       Object[] arrayOfObject = new Object[3];
/* 1403 */       arrayOfObject[0] = this.myRelId;
/* 1404 */       arrayOfObject[1] = paramRole;
/* 1405 */       arrayOfObject[2] = paramList;
/* 1406 */       String[] arrayOfString = new String[3];
/* 1407 */       arrayOfString[0] = "java.lang.String";
/* 1408 */       arrayOfString[1] = "javax.management.relation.Role";
/* 1409 */       arrayOfString[2] = "java.util.List";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1417 */         this.myRelServiceMBeanServer.invoke(this.myRelServiceName, "updateRoleMap", arrayOfObject, arrayOfString);
/*      */ 
/*      */       
/*      */       }
/* 1421 */       catch (ReflectionException reflectionException) {
/* 1422 */         throw new RuntimeException(reflectionException.getMessage());
/* 1423 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/* 1424 */         throw new RelationServiceNotRegisteredException(instanceNotFoundException
/* 1425 */             .getMessage());
/* 1426 */       } catch (MBeanException mBeanException) {
/* 1427 */         Exception exception = mBeanException.getTargetException();
/* 1428 */         if (exception instanceof RelationNotFoundException) {
/* 1429 */           throw (RelationNotFoundException)exception;
/*      */         }
/* 1431 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1436 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "updateRelationServiceMap");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   RoleResult setRolesInt(RoleList paramRoleList, boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationTypeNotFoundException, RelationNotFoundException {
/* 1481 */     if (paramRoleList == null || (paramBoolean && paramRelationService == null)) {
/*      */       
/* 1483 */       String str = "Invalid parameter.";
/* 1484 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1487 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "setRolesInt", new Object[] { paramRoleList, 
/*      */           
/* 1489 */           Boolean.valueOf(paramBoolean), paramRelationService });
/*      */     
/* 1491 */     RoleList roleList = new RoleList();
/* 1492 */     RoleUnresolvedList roleUnresolvedList = new RoleUnresolvedList();
/*      */     
/* 1494 */     for (Role role : paramRoleList.asList()) {
/*      */       
/* 1496 */       Object object = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1505 */         object = setRoleInt(role, paramBoolean, paramRelationService, true);
/*      */ 
/*      */       
/*      */       }
/* 1509 */       catch (RoleNotFoundException roleNotFoundException) {
/*      */       
/* 1511 */       } catch (InvalidRoleValueException invalidRoleValueException) {}
/*      */ 
/*      */ 
/*      */       
/* 1515 */       if (object instanceof Role) {
/*      */ 
/*      */         
/*      */         try {
/* 1519 */           roleList.add((Role)object);
/* 1520 */         } catch (IllegalArgumentException illegalArgumentException) {
/* 1521 */           throw new RuntimeException(illegalArgumentException.getMessage());
/*      */         }  continue;
/*      */       } 
/* 1524 */       if (object instanceof RoleUnresolved) {
/*      */         
/*      */         try {
/*      */           
/* 1528 */           roleUnresolvedList.add((RoleUnresolved)object);
/* 1529 */         } catch (IllegalArgumentException illegalArgumentException) {
/* 1530 */           throw new RuntimeException(illegalArgumentException.getMessage());
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1535 */     RoleResult roleResult = new RoleResult(roleList, roleUnresolvedList);
/*      */     
/* 1537 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "setRolesInt");
/* 1538 */     return roleResult;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMembers(String paramString1, ObjectName paramObjectName, MBeanServer paramMBeanServer, String paramString2, RoleList paramRoleList) throws InvalidRoleValueException, IllegalArgumentException {
/* 1574 */     if (paramString1 == null || paramObjectName == null || paramString2 == null) {
/*      */ 
/*      */       
/* 1577 */       String str = "Invalid parameter.";
/* 1578 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1581 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "initMembers", new Object[] { paramString1, paramObjectName, paramMBeanServer, paramString2, paramRoleList });
/*      */ 
/*      */ 
/*      */     
/* 1585 */     this.myRelId = paramString1;
/* 1586 */     this.myRelServiceName = paramObjectName;
/* 1587 */     this.myRelServiceMBeanServer = paramMBeanServer;
/* 1588 */     this.myRelTypeName = paramString2;
/*      */     
/* 1590 */     initRoleMap(paramRoleList);
/*      */     
/* 1592 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "initMembers");
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
/*      */   private void initRoleMap(RoleList paramRoleList) throws InvalidRoleValueException {
/* 1607 */     if (paramRoleList == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1611 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "initRoleMap", paramRoleList);
/*      */ 
/*      */     
/* 1614 */     synchronized (this.myRoleName2ValueMap) {
/*      */       
/* 1616 */       for (Role role : paramRoleList.asList()) {
/*      */ 
/*      */ 
/*      */         
/* 1620 */         String str = role.getRoleName();
/*      */         
/* 1622 */         if (this.myRoleName2ValueMap.containsKey(str)) {
/*      */           
/* 1624 */           StringBuilder stringBuilder = new StringBuilder("Role name ");
/* 1625 */           stringBuilder.append(str);
/* 1626 */           stringBuilder.append(" used for two roles.");
/* 1627 */           throw new InvalidRoleValueException(stringBuilder.toString());
/*      */         } 
/*      */         
/* 1630 */         this.myRoleName2ValueMap.put(str, (Role)role
/* 1631 */             .clone());
/*      */       } 
/*      */     } 
/*      */     
/* 1635 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "initRoleMap");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void handleMBeanUnregistrationInt(ObjectName paramObjectName, String paramString, boolean paramBoolean, RelationService paramRelationService) throws IllegalArgumentException, RoleNotFoundException, InvalidRoleValueException, RelationServiceNotRegisteredException, RelationTypeNotFoundException, RelationNotFoundException {
/*      */     Role role1;
/* 1688 */     if (paramObjectName == null || paramString == null || (paramBoolean && paramRelationService == null)) {
/*      */ 
/*      */       
/* 1691 */       String str = "Invalid parameter.";
/* 1692 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1695 */     JmxProperties.RELATION_LOGGER.entering(RelationSupport.class.getName(), "handleMBeanUnregistrationInt", new Object[] { paramObjectName, paramString, 
/*      */           
/* 1697 */           Boolean.valueOf(paramBoolean), paramRelationService });
/*      */ 
/*      */ 
/*      */     
/* 1701 */     synchronized (this.myRoleName2ValueMap) {
/* 1702 */       role1 = this.myRoleName2ValueMap.get(paramString);
/*      */     } 
/*      */     
/* 1705 */     if (role1 == null) {
/* 1706 */       StringBuilder stringBuilder = new StringBuilder();
/* 1707 */       String str = "No role with name ";
/* 1708 */       stringBuilder.append(str);
/* 1709 */       stringBuilder.append(paramString);
/* 1710 */       throw new RoleNotFoundException(stringBuilder.toString());
/*      */     } 
/* 1712 */     List<ObjectName> list = role1.getRoleValue();
/*      */ 
/*      */ 
/*      */     
/* 1716 */     ArrayList<ObjectName> arrayList = new ArrayList<>(list);
/* 1717 */     arrayList.remove(paramObjectName);
/* 1718 */     Role role2 = new Role(paramString, arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1724 */     Object object = setRoleInt(role2, paramBoolean, paramRelationService, false);
/*      */     
/* 1726 */     JmxProperties.RELATION_LOGGER.exiting(RelationSupport.class.getName(), "handleMBeanUnregistrationInt");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RelationSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */