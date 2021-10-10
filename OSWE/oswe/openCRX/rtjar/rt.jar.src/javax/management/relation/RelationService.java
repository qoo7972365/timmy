/*      */ package javax.management.relation;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.atomic.AtomicLong;
/*      */ import javax.management.Attribute;
/*      */ import javax.management.AttributeNotFoundException;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.InvalidAttributeValueException;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanNotificationInfo;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.MBeanServerDelegate;
/*      */ import javax.management.MBeanServerNotification;
/*      */ import javax.management.Notification;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.NotificationListener;
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
/*      */ public class RelationService
/*      */   extends NotificationBroadcasterSupport
/*      */   implements RelationServiceMBean, MBeanRegistration, NotificationListener
/*      */ {
/*   84 */   private Map<String, Object> myRelId2ObjMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*   88 */   private Map<String, String> myRelId2RelTypeMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*   92 */   private Map<ObjectName, String> myRelMBeanObjName2RelIdMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   private Map<String, RelationType> myRelType2ObjMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   private Map<String, List<String>> myRelType2RelIdsMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   private final Map<ObjectName, Map<String, List<String>>> myRefedMBeanObjName2RelIdsMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean myPurgeFlag = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  126 */   private final AtomicLong atomicSeqNo = new AtomicLong();
/*      */ 
/*      */   
/*  129 */   private ObjectName myObjName = null;
/*      */ 
/*      */   
/*  132 */   private MBeanServer myMBeanServer = null;
/*      */ 
/*      */ 
/*      */   
/*  136 */   private MBeanServerNotificationFilter myUnregNtfFilter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private List<MBeanServerNotification> myUnregNtfList = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RelationService(boolean paramBoolean) {
/*  160 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "RelationService");
/*      */ 
/*      */     
/*  163 */     setPurgeFlag(paramBoolean);
/*      */     
/*  165 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "RelationService");
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
/*      */   public void isActive() throws RelationServiceNotRegisteredException {
/*  180 */     if (this.myMBeanServer == null) {
/*      */ 
/*      */       
/*  183 */       String str = "Relation Service not registered in the MBean Server.";
/*      */       
/*  185 */       throw new RelationServiceNotRegisteredException(str);
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
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  201 */     this.myMBeanServer = paramMBeanServer;
/*  202 */     this.myObjName = paramObjectName;
/*  203 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   public void preDeregister() throws Exception {}
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   public boolean getPurgeFlag() {
/*  239 */     return this.myPurgeFlag;
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
/*      */   public void setPurgeFlag(boolean paramBoolean) {
/*  256 */     this.myPurgeFlag = paramBoolean;
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
/*      */   public void createRelationType(String paramString, RoleInfo[] paramArrayOfRoleInfo) throws IllegalArgumentException, InvalidRelationTypeException {
/*  284 */     if (paramString == null || paramArrayOfRoleInfo == null) {
/*  285 */       String str = "Invalid parameter.";
/*  286 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  289 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "createRelationType", paramString);
/*      */ 
/*      */ 
/*      */     
/*  293 */     RelationTypeSupport relationTypeSupport = new RelationTypeSupport(paramString, paramArrayOfRoleInfo);
/*      */ 
/*      */     
/*  296 */     addRelationTypeInt(relationTypeSupport);
/*      */     
/*  298 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "createRelationType");
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
/*      */   public void addRelationType(RelationType paramRelationType) throws IllegalArgumentException, InvalidRelationTypeException {
/*  323 */     if (paramRelationType == null) {
/*  324 */       String str = "Invalid parameter.";
/*  325 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  328 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "addRelationType");
/*      */ 
/*      */ 
/*      */     
/*  332 */     List<RoleInfo> list = paramRelationType.getRoleInfos();
/*  333 */     if (list == null) {
/*  334 */       String str = "No role info provided.";
/*  335 */       throw new InvalidRelationTypeException(str);
/*      */     } 
/*      */     
/*  338 */     RoleInfo[] arrayOfRoleInfo = new RoleInfo[list.size()];
/*  339 */     byte b = 0;
/*  340 */     for (RoleInfo roleInfo : list) {
/*  341 */       arrayOfRoleInfo[b] = roleInfo;
/*  342 */       b++;
/*      */     } 
/*      */     
/*  345 */     RelationTypeSupport.checkRoleInfos(arrayOfRoleInfo);
/*      */     
/*  347 */     addRelationTypeInt(paramRelationType);
/*      */     
/*  349 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "addRelationType");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAllRelationTypeNames() {
/*      */     ArrayList<String> arrayList;
/*  361 */     synchronized (this.myRelType2ObjMap) {
/*  362 */       arrayList = new ArrayList(this.myRelType2ObjMap.keySet());
/*      */     } 
/*  364 */     return arrayList;
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
/*      */   public List<RoleInfo> getRoleInfos(String paramString) throws IllegalArgumentException, RelationTypeNotFoundException {
/*  383 */     if (paramString == null) {
/*  384 */       String str = "Invalid parameter.";
/*  385 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  388 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRoleInfos", paramString);
/*      */ 
/*      */ 
/*      */     
/*  392 */     RelationType relationType = getRelationType(paramString);
/*      */     
/*  394 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRoleInfos");
/*      */     
/*  396 */     return relationType.getRoleInfos();
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
/*      */   public RoleInfo getRoleInfo(String paramString1, String paramString2) throws IllegalArgumentException, RelationTypeNotFoundException, RoleInfoNotFoundException {
/*  419 */     if (paramString1 == null || paramString2 == null) {
/*  420 */       String str = "Invalid parameter.";
/*  421 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  424 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRoleInfo", new Object[] { paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */     
/*  428 */     RelationType relationType = getRelationType(paramString1);
/*      */ 
/*      */     
/*  431 */     RoleInfo roleInfo = relationType.getRoleInfo(paramString2);
/*      */     
/*  433 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRoleInfo");
/*      */     
/*  435 */     return roleInfo;
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
/*      */   public void removeRelationType(String paramString) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationTypeNotFoundException {
/*  457 */     isActive();
/*      */     
/*  459 */     if (paramString == null) {
/*  460 */       String str = "Invalid parameter.";
/*  461 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  464 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "removeRelationType", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  469 */     RelationType relationType = getRelationType(paramString);
/*      */ 
/*      */     
/*  472 */     ArrayList arrayList = null;
/*  473 */     synchronized (this.myRelType2RelIdsMap) {
/*      */ 
/*      */ 
/*      */       
/*  477 */       List<?> list = this.myRelType2RelIdsMap.get(paramString);
/*  478 */       if (list != null) {
/*  479 */         arrayList = new ArrayList(list);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  484 */     synchronized (this.myRelType2ObjMap) {
/*  485 */       this.myRelType2ObjMap.remove(paramString);
/*      */     } 
/*  487 */     synchronized (this.myRelType2RelIdsMap) {
/*  488 */       this.myRelType2RelIdsMap.remove(paramString);
/*      */     } 
/*      */ 
/*      */     
/*  492 */     if (arrayList != null) {
/*  493 */       for (String str : arrayList) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */           
/*  500 */           removeRelation(str);
/*  501 */         } catch (RelationNotFoundException relationNotFoundException) {
/*  502 */           throw new RuntimeException(relationNotFoundException.getMessage());
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  507 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "removeRelationType");
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
/*      */   public void createRelation(String paramString1, String paramString2, RoleList paramRoleList) throws RelationServiceNotRegisteredException, IllegalArgumentException, RoleNotFoundException, InvalidRelationIdException, RelationTypeNotFoundException, InvalidRoleValueException {
/*  561 */     isActive();
/*      */     
/*  563 */     if (paramString1 == null || paramString2 == null) {
/*      */       
/*  565 */       String str = "Invalid parameter.";
/*  566 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  569 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "createRelation", new Object[] { paramString1, paramString2, paramRoleList });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  575 */     RelationSupport relationSupport = new RelationSupport(paramString1, this.myObjName, paramString2, paramRoleList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  585 */     addRelationInt(true, relationSupport, null, paramString1, paramString2, paramRoleList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  591 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "createRelation");
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
/*      */   public void addRelation(ObjectName paramObjectName) throws IllegalArgumentException, RelationServiceNotRegisteredException, NoSuchMethodException, InvalidRelationIdException, InstanceNotFoundException, InvalidRelationServiceException, RelationTypeNotFoundException, RoleNotFoundException, InvalidRoleValueException {
/*      */     String str1;
/*      */     ObjectName objectName;
/*      */     String str2;
/*      */     RoleList roleList;
/*  652 */     if (paramObjectName == null) {
/*  653 */       str1 = "Invalid parameter.";
/*  654 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/*  657 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "addRelation", paramObjectName);
/*      */ 
/*      */ 
/*      */     
/*  661 */     isActive();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  666 */     if (!this.myMBeanServer.isInstanceOf(paramObjectName, "javax.management.relation.Relation")) {
/*  667 */       str1 = "This MBean does not implement the Relation interface.";
/*  668 */       throw new NoSuchMethodException(str1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  677 */       str1 = (String)this.myMBeanServer.getAttribute(paramObjectName, "RelationId");
/*      */     
/*      */     }
/*  680 */     catch (MBeanException mBeanException) {
/*  681 */       throw new RuntimeException(mBeanException
/*  682 */           .getTargetException().getMessage());
/*  683 */     } catch (ReflectionException reflectionException) {
/*  684 */       throw new RuntimeException(reflectionException.getMessage());
/*  685 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/*  686 */       throw new RuntimeException(attributeNotFoundException.getMessage());
/*      */     } 
/*      */     
/*  689 */     if (str1 == null) {
/*  690 */       String str = "This MBean does not provide a relation id.";
/*  691 */       throw new InvalidRelationIdException(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  701 */       objectName = (ObjectName)this.myMBeanServer.getAttribute(paramObjectName, "RelationServiceName");
/*      */     
/*      */     }
/*  704 */     catch (MBeanException mBeanException) {
/*  705 */       throw new RuntimeException(mBeanException
/*  706 */           .getTargetException().getMessage());
/*  707 */     } catch (ReflectionException reflectionException) {
/*  708 */       throw new RuntimeException(reflectionException.getMessage());
/*  709 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/*  710 */       throw new RuntimeException(attributeNotFoundException.getMessage());
/*      */     } 
/*      */     
/*  713 */     boolean bool = false;
/*  714 */     if (objectName == null) {
/*  715 */       bool = true;
/*      */     }
/*  717 */     else if (!objectName.equals(this.myObjName)) {
/*  718 */       bool = true;
/*      */     } 
/*  720 */     if (bool) {
/*  721 */       str2 = "The Relation Service referenced in the MBean is not the current one.";
/*  722 */       throw new InvalidRelationServiceException(str2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  730 */       str2 = (String)this.myMBeanServer.getAttribute(paramObjectName, "RelationTypeName");
/*      */     
/*      */     }
/*  733 */     catch (MBeanException mBeanException) {
/*  734 */       throw new RuntimeException(mBeanException
/*  735 */           .getTargetException().getMessage());
/*  736 */     } catch (ReflectionException reflectionException) {
/*  737 */       throw new RuntimeException(reflectionException.getMessage());
/*  738 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/*  739 */       throw new RuntimeException(attributeNotFoundException.getMessage());
/*      */     } 
/*  741 */     if (str2 == null) {
/*  742 */       String str = "No relation type provided.";
/*  743 */       throw new RelationTypeNotFoundException(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  751 */       roleList = (RoleList)this.myMBeanServer.invoke(paramObjectName, "retrieveAllRoles", null, null);
/*      */ 
/*      */     
/*      */     }
/*  755 */     catch (MBeanException mBeanException) {
/*  756 */       throw new RuntimeException(mBeanException
/*  757 */           .getTargetException().getMessage());
/*  758 */     } catch (ReflectionException reflectionException) {
/*  759 */       throw new RuntimeException(reflectionException.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  764 */     addRelationInt(false, null, paramObjectName, str1, str2, roleList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  771 */     synchronized (this.myRelMBeanObjName2RelIdMap) {
/*  772 */       this.myRelMBeanObjName2RelIdMap.put(paramObjectName, str1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  780 */       this.myMBeanServer.setAttribute(paramObjectName, new Attribute("RelationServiceManagementFlag", Boolean.TRUE));
/*      */ 
/*      */     
/*      */     }
/*  784 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  790 */     ArrayList<ObjectName> arrayList = new ArrayList();
/*  791 */     arrayList.add(paramObjectName);
/*  792 */     updateUnregistrationListener(arrayList, null);
/*      */     
/*  794 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "addRelation");
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
/*      */   public ObjectName isRelationMBean(String paramString) throws IllegalArgumentException, RelationNotFoundException {
/*  817 */     if (paramString == null) {
/*  818 */       String str = "Invalid parameter.";
/*  819 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  822 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "isRelationMBean", paramString);
/*      */ 
/*      */ 
/*      */     
/*  826 */     Object object = getRelation(paramString);
/*  827 */     if (object instanceof ObjectName) {
/*  828 */       return (ObjectName)object;
/*      */     }
/*  830 */     return null;
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
/*      */   public String isRelation(ObjectName paramObjectName) throws IllegalArgumentException {
/*  848 */     if (paramObjectName == null) {
/*  849 */       String str1 = "Invalid parameter.";
/*  850 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/*  853 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "isRelation", paramObjectName);
/*      */ 
/*      */     
/*  856 */     String str = null;
/*  857 */     synchronized (this.myRelMBeanObjName2RelIdMap) {
/*  858 */       String str1 = this.myRelMBeanObjName2RelIdMap.get(paramObjectName);
/*  859 */       if (str1 != null) {
/*  860 */         str = str1;
/*      */       }
/*      */     } 
/*  863 */     return str;
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
/*      */   public Boolean hasRelation(String paramString) throws IllegalArgumentException {
/*  879 */     if (paramString == null) {
/*  880 */       String str = "Invalid parameter.";
/*  881 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  884 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "hasRelation", paramString);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  889 */       Object object = getRelation(paramString);
/*  890 */       return Boolean.valueOf(true);
/*  891 */     } catch (RelationNotFoundException relationNotFoundException) {
/*  892 */       return Boolean.valueOf(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getAllRelationIds() {
/*      */     ArrayList<String> arrayList;
/*  904 */     synchronized (this.myRelId2ObjMap) {
/*  905 */       arrayList = new ArrayList(this.myRelId2ObjMap.keySet());
/*      */     } 
/*  907 */     return arrayList;
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
/*      */   public Integer checkRoleReading(String paramString1, String paramString2) throws IllegalArgumentException, RelationTypeNotFoundException {
/*      */     Integer integer;
/*  931 */     if (paramString1 == null || paramString2 == null) {
/*  932 */       String str = "Invalid parameter.";
/*  933 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/*  936 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "checkRoleReading", new Object[] { paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  942 */     RelationType relationType = getRelationType(paramString2);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  947 */       RoleInfo roleInfo = relationType.getRoleInfo(paramString1);
/*      */       
/*  949 */       integer = checkRoleInt(1, paramString1, null, roleInfo, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  955 */     catch (RoleInfoNotFoundException roleInfoNotFoundException) {
/*  956 */       integer = Integer.valueOf(1);
/*      */     } 
/*      */     
/*  959 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleReading");
/*      */     
/*  961 */     return integer;
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
/*      */   public Integer checkRoleWriting(Role paramRole, String paramString, Boolean paramBoolean) throws IllegalArgumentException, RelationTypeNotFoundException {
/*      */     RoleInfo roleInfo;
/*  991 */     if (paramRole == null || paramString == null || paramBoolean == null) {
/*      */ 
/*      */       
/*  994 */       String str1 = "Invalid parameter.";
/*  995 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/*  998 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "checkRoleWriting", new Object[] { paramRole, paramString, paramBoolean });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1003 */     RelationType relationType = getRelationType(paramString);
/*      */     
/* 1005 */     String str = paramRole.getRoleName();
/* 1006 */     List<ObjectName> list = paramRole.getRoleValue();
/* 1007 */     boolean bool = true;
/* 1008 */     if (paramBoolean.booleanValue()) {
/* 1009 */       bool = false;
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1014 */       roleInfo = relationType.getRoleInfo(str);
/* 1015 */     } catch (RoleInfoNotFoundException roleInfoNotFoundException) {
/* 1016 */       JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleWriting");
/*      */       
/* 1018 */       return Integer.valueOf(1);
/*      */     } 
/*      */     
/* 1021 */     Integer integer = checkRoleInt(2, str, list, roleInfo, bool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleWriting");
/*      */     
/* 1029 */     return integer;
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
/*      */   public void sendRelationCreationNotification(String paramString) throws IllegalArgumentException, RelationNotFoundException {
/* 1053 */     if (paramString == null) {
/* 1054 */       String str = "Invalid parameter.";
/* 1055 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1058 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "sendRelationCreationNotification", paramString);
/*      */ 
/*      */ 
/*      */     
/* 1062 */     StringBuilder stringBuilder = new StringBuilder("Creation of relation ");
/* 1063 */     stringBuilder.append(paramString);
/*      */ 
/*      */     
/* 1066 */     sendNotificationInt(1, stringBuilder
/* 1067 */         .toString(), paramString, null, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "sendRelationCreationNotification");
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
/*      */   public void sendRoleUpdateNotification(String paramString, Role paramRole, List<ObjectName> paramList) throws IllegalArgumentException, RelationNotFoundException {
/* 1107 */     if (paramString == null || paramRole == null || paramList == null) {
/*      */ 
/*      */       
/* 1110 */       String str = "Invalid parameter.";
/* 1111 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1114 */     if (!(paramList instanceof ArrayList)) {
/* 1115 */       paramList = new ArrayList<>(paramList);
/*      */     }
/* 1117 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "sendRoleUpdateNotification", new Object[] { paramString, paramRole, paramList });
/*      */ 
/*      */ 
/*      */     
/* 1121 */     String str1 = paramRole.getRoleName();
/* 1122 */     List<ObjectName> list = paramRole.getRoleValue();
/*      */ 
/*      */     
/* 1125 */     String str2 = Role.roleValueToString(list);
/* 1126 */     String str3 = Role.roleValueToString(paramList);
/* 1127 */     StringBuilder stringBuilder = new StringBuilder("Value of role ");
/* 1128 */     stringBuilder.append(str1);
/* 1129 */     stringBuilder.append(" has changed\nOld value:\n");
/* 1130 */     stringBuilder.append(str3);
/* 1131 */     stringBuilder.append("\nNew value:\n");
/* 1132 */     stringBuilder.append(str2);
/*      */ 
/*      */     
/* 1135 */     sendNotificationInt(2, stringBuilder
/* 1136 */         .toString(), paramString, null, str1, list, paramList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1143 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "sendRoleUpdateNotification");
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
/*      */   public void sendRelationRemovalNotification(String paramString, List<ObjectName> paramList) throws IllegalArgumentException, RelationNotFoundException {
/* 1170 */     if (paramString == null) {
/* 1171 */       String str = "Invalid parameter";
/* 1172 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1175 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "sendRelationRemovalNotification", new Object[] { paramString, paramList });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1180 */     sendNotificationInt(3, "Removal of relation " + paramString, paramString, paramList, null, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1189 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "sendRelationRemovalNotification");
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
/*      */   public void updateRoleMap(String paramString, Role paramRole, List<ObjectName> paramList) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationNotFoundException {
/* 1222 */     if (paramString == null || paramRole == null || paramList == null) {
/*      */ 
/*      */       
/* 1225 */       String str1 = "Invalid parameter.";
/* 1226 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 1229 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "updateRoleMap", new Object[] { paramString, paramRole, paramList });
/*      */ 
/*      */ 
/*      */     
/* 1233 */     isActive();
/*      */ 
/*      */ 
/*      */     
/* 1237 */     Object object = getRelation(paramString);
/*      */     
/* 1239 */     String str = paramRole.getRoleName();
/* 1240 */     List<ObjectName> list = paramRole.getRoleValue();
/*      */ 
/*      */     
/* 1243 */     ArrayList<ObjectName> arrayList1 = new ArrayList<>(paramList);
/*      */ 
/*      */ 
/*      */     
/* 1247 */     ArrayList<ObjectName> arrayList2 = new ArrayList();
/*      */     
/* 1249 */     for (ObjectName objectName : list) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1255 */       int i = arrayList1.indexOf(objectName);
/*      */       
/* 1257 */       if (i == -1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1263 */         boolean bool = addNewMBeanReference(objectName, paramString, str);
/*      */ 
/*      */ 
/*      */         
/* 1267 */         if (bool)
/*      */         {
/* 1269 */           arrayList2.add(objectName);
/*      */         }
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/* 1277 */       arrayList1.remove(i);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1282 */     ArrayList<ObjectName> arrayList3 = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/* 1286 */     for (ObjectName objectName : arrayList1) {
/*      */ 
/*      */ 
/*      */       
/* 1290 */       boolean bool = removeMBeanReference(objectName, paramString, str, false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1295 */       if (bool)
/*      */       {
/* 1297 */         arrayList3.add(objectName);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1304 */     updateUnregistrationListener(arrayList2, arrayList3);
/*      */     
/* 1306 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "updateRoleMap");
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
/*      */   public void removeRelation(String paramString) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationNotFoundException {
/*      */     String str;
/* 1334 */     isActive();
/*      */     
/* 1336 */     if (paramString == null) {
/* 1337 */       String str1 = "Invalid parameter.";
/* 1338 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 1341 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "removeRelation", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1346 */     Object object = getRelation(paramString);
/*      */ 
/*      */     
/* 1349 */     if (object instanceof ObjectName) {
/* 1350 */       ArrayList<ObjectName> arrayList = new ArrayList();
/* 1351 */       arrayList.add((ObjectName)object);
/*      */       
/* 1353 */       updateUnregistrationListener(null, arrayList);
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
/*      */ 
/*      */ 
/*      */     
/* 1367 */     sendRelationRemovalNotification(paramString, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1379 */     ArrayList<ObjectName> arrayList1 = new ArrayList();
/*      */ 
/*      */     
/* 1382 */     ArrayList<ObjectName> arrayList2 = new ArrayList();
/*      */     
/* 1384 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/*      */ 
/*      */       
/* 1387 */       for (ObjectName objectName : this.myRefedMBeanObjName2RelIdsMap.keySet()) {
/*      */ 
/*      */ 
/*      */         
/* 1391 */         Map map = this.myRefedMBeanObjName2RelIdsMap.get(objectName);
/*      */         
/* 1393 */         if (map.containsKey(paramString)) {
/* 1394 */           map.remove(paramString);
/* 1395 */           arrayList1.add(objectName);
/*      */         } 
/*      */         
/* 1398 */         if (map.isEmpty())
/*      */         {
/*      */ 
/*      */           
/* 1402 */           arrayList2.add(objectName);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1408 */       for (ObjectName objectName : arrayList2) {
/* 1409 */         this.myRefedMBeanObjName2RelIdsMap.remove(objectName);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1414 */     synchronized (this.myRelId2ObjMap) {
/* 1415 */       this.myRelId2ObjMap.remove(paramString);
/*      */     } 
/*      */     
/* 1418 */     if (object instanceof ObjectName)
/*      */     {
/* 1420 */       synchronized (this.myRelMBeanObjName2RelIdMap) {
/* 1421 */         this.myRelMBeanObjName2RelIdMap.remove(object);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1428 */     synchronized (this.myRelId2RelTypeMap) {
/* 1429 */       str = this.myRelId2RelTypeMap.get(paramString);
/* 1430 */       this.myRelId2RelTypeMap.remove(paramString);
/*      */     } 
/*      */     
/* 1433 */     synchronized (this.myRelType2RelIdsMap) {
/* 1434 */       List list = this.myRelType2RelIdsMap.get(str);
/* 1435 */       if (list != null) {
/*      */         
/* 1437 */         list.remove(paramString);
/* 1438 */         if (list.isEmpty())
/*      */         {
/* 1440 */           this.myRelType2RelIdsMap.remove(str);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1445 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "removeRelation");
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
/*      */   public void purgeRelations() throws RelationServiceNotRegisteredException {
/*      */     ArrayList<MBeanServerNotification> arrayList;
/* 1479 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "purgeRelations");
/*      */ 
/*      */ 
/*      */     
/* 1483 */     isActive();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1495 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/* 1496 */       arrayList = new ArrayList<>(this.myUnregNtfList);
/*      */ 
/*      */       
/* 1499 */       this.myUnregNtfList = new ArrayList<>();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1509 */     ArrayList<ObjectName> arrayList1 = new ArrayList();
/*      */ 
/*      */     
/* 1512 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/* 1515 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/* 1516 */       for (MBeanServerNotification mBeanServerNotification : arrayList) {
/*      */         
/* 1518 */         ObjectName objectName = mBeanServerNotification.getMBeanName();
/*      */ 
/*      */ 
/*      */         
/* 1522 */         arrayList1.add(objectName);
/*      */ 
/*      */ 
/*      */         
/* 1526 */         Map map = this.myRefedMBeanObjName2RelIdsMap.get(objectName);
/* 1527 */         hashMap.put(objectName, map);
/*      */         
/* 1529 */         this.myRefedMBeanObjName2RelIdsMap.remove(objectName);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1535 */     updateUnregistrationListener(null, arrayList1);
/*      */     
/* 1537 */     for (MBeanServerNotification mBeanServerNotification : arrayList) {
/*      */       
/* 1539 */       ObjectName objectName = mBeanServerNotification.getMBeanName();
/*      */ 
/*      */ 
/*      */       
/* 1543 */       Map map = (Map)hashMap.get(objectName);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1548 */       for (Map.Entry entry : map.entrySet()) {
/* 1549 */         String str = (String)entry.getKey();
/*      */ 
/*      */         
/* 1552 */         List<String> list = (List)entry.getValue();
/*      */ 
/*      */ 
/*      */ 
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
/* 1566 */           handleReferenceUnregistration(str, objectName, list);
/*      */         
/*      */         }
/* 1569 */         catch (RelationNotFoundException relationNotFoundException) {
/* 1570 */           throw new RuntimeException(relationNotFoundException.getMessage());
/* 1571 */         } catch (RoleNotFoundException roleNotFoundException) {
/* 1572 */           throw new RuntimeException(roleNotFoundException.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1577 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "purgeRelations");
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
/*      */   public Map<String, List<String>> findReferencingRelations(ObjectName paramObjectName, String paramString1, String paramString2) throws IllegalArgumentException {
/* 1608 */     if (paramObjectName == null) {
/* 1609 */       String str = "Invalid parameter.";
/* 1610 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1613 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "findReferencingRelations", new Object[] { paramObjectName, paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */     
/* 1617 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */     
/* 1619 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/*      */ 
/*      */ 
/*      */       
/* 1623 */       Map map = this.myRefedMBeanObjName2RelIdsMap.get(paramObjectName);
/*      */       
/* 1625 */       if (map != null) {
/*      */         ArrayList<String> arrayList;
/*      */         
/* 1628 */         Set<?> set = map.keySet();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1633 */         if (paramString1 == null) {
/*      */           
/* 1635 */           arrayList = new ArrayList(set);
/*      */         }
/*      */         else {
/*      */           
/* 1639 */           arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */           
/* 1643 */           for (String str1 : set) {
/*      */             String str2;
/*      */ 
/*      */             
/* 1647 */             synchronized (this.myRelId2RelTypeMap) {
/*      */               
/* 1649 */               str2 = this.myRelId2RelTypeMap.get(str1);
/*      */             } 
/*      */             
/* 1652 */             if (str2.equals(paramString1))
/*      */             {
/* 1654 */               arrayList.add(str1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1663 */         for (String str : arrayList) {
/*      */ 
/*      */ 
/*      */           
/* 1667 */           List<?> list = (List)map.get(str);
/*      */           
/* 1669 */           if (paramString2 == null) {
/*      */ 
/*      */ 
/*      */             
/* 1673 */             hashMap.put(str, new ArrayList(list));
/*      */             continue;
/*      */           } 
/* 1676 */           if (list.contains(paramString2)) {
/*      */ 
/*      */             
/* 1679 */             ArrayList<String> arrayList1 = new ArrayList();
/* 1680 */             arrayList1.add(paramString2);
/* 1681 */             hashMap.put(str, arrayList1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1687 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "findReferencingRelations");
/*      */     
/* 1689 */     return (Map)hashMap;
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
/*      */   public Map<ObjectName, List<String>> findAssociatedMBeans(ObjectName paramObjectName, String paramString1, String paramString2) throws IllegalArgumentException {
/* 1718 */     if (paramObjectName == null) {
/* 1719 */       String str = "Invalid parameter.";
/* 1720 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1723 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "findAssociatedMBeans", new Object[] { paramObjectName, paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1730 */     Map<String, List<String>> map = findReferencingRelations(paramObjectName, paramString1, paramString2);
/*      */ 
/*      */ 
/*      */     
/* 1734 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */ 
/*      */     
/* 1737 */     for (String str : map.keySet()) {
/*      */       Map<ObjectName, List<String>> map1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1745 */         map1 = getReferencedMBeans(str);
/* 1746 */       } catch (RelationNotFoundException relationNotFoundException) {
/* 1747 */         throw new RuntimeException(relationNotFoundException.getMessage());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1752 */       for (ObjectName objectName : map1.keySet()) {
/*      */         
/* 1754 */         if (!objectName.equals(paramObjectName)) {
/*      */ 
/*      */ 
/*      */           
/* 1758 */           List<String> list = (List)hashMap.get(objectName);
/* 1759 */           if (list == null) {
/*      */             
/* 1761 */             list = new ArrayList();
/* 1762 */             list.add(str);
/* 1763 */             hashMap.put(objectName, list);
/*      */             continue;
/*      */           } 
/* 1766 */           list.add(str);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1772 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "findAssociatedMBeans");
/*      */     
/* 1774 */     return (Map)hashMap;
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
/*      */   public List<String> findRelationsOfType(String paramString) throws IllegalArgumentException, RelationTypeNotFoundException {
/*      */     ArrayList<String> arrayList;
/* 1792 */     if (paramString == null) {
/* 1793 */       String str = "Invalid parameter.";
/* 1794 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1797 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "findRelationsOfType");
/*      */ 
/*      */ 
/*      */     
/* 1801 */     RelationType relationType = getRelationType(paramString);
/*      */ 
/*      */     
/* 1804 */     synchronized (this.myRelType2RelIdsMap) {
/* 1805 */       List<?> list = this.myRelType2RelIdsMap.get(paramString);
/* 1806 */       if (list == null) {
/* 1807 */         arrayList = new ArrayList();
/*      */       } else {
/* 1809 */         arrayList = new ArrayList(list);
/*      */       } 
/*      */     } 
/* 1812 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "findRelationsOfType");
/*      */     
/* 1814 */     return arrayList;
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
/*      */   public List<ObjectName> getRole(String paramString1, String paramString2) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationNotFoundException, RoleNotFoundException {
/*      */     List<ObjectName> list;
/* 1843 */     if (paramString1 == null || paramString2 == null) {
/* 1844 */       String str = "Invalid parameter.";
/* 1845 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1848 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRole", new Object[] { paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */     
/* 1852 */     isActive();
/*      */ 
/*      */     
/* 1855 */     Object object = getRelation(paramString1);
/*      */ 
/*      */ 
/*      */     
/* 1859 */     if (object instanceof RelationSupport) {
/*      */ 
/*      */       
/* 1862 */       list = Util.<List>cast(((RelationSupport)object)
/* 1863 */           .getRoleInt(paramString2, true, this, false));
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1870 */       Object[] arrayOfObject = new Object[1];
/* 1871 */       arrayOfObject[0] = paramString2;
/* 1872 */       String[] arrayOfString = new String[1];
/* 1873 */       arrayOfString[0] = "java.lang.String";
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1879 */         List<?> list1 = Util.<List>cast(this.myMBeanServer
/* 1880 */             .invoke((ObjectName)object, "getRole", arrayOfObject, arrayOfString));
/*      */ 
/*      */ 
/*      */         
/* 1884 */         if (list1 == null || list1 instanceof ArrayList)
/* 1885 */         { list = (List)list1; }
/*      */         else
/* 1887 */         { list = new ArrayList(list1); } 
/* 1888 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/* 1889 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 1890 */       } catch (ReflectionException reflectionException) {
/* 1891 */         throw new RuntimeException(reflectionException.getMessage());
/* 1892 */       } catch (MBeanException mBeanException) {
/* 1893 */         Exception exception = mBeanException.getTargetException();
/* 1894 */         if (exception instanceof RoleNotFoundException) {
/* 1895 */           throw (RoleNotFoundException)exception;
/*      */         }
/* 1897 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1902 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRole");
/* 1903 */     return list;
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
/*      */   public RoleResult getRoles(String paramString, String[] paramArrayOfString) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationNotFoundException {
/*      */     RoleResult roleResult;
/* 1929 */     if (paramString == null || paramArrayOfString == null) {
/* 1930 */       String str = "Invalid parameter.";
/* 1931 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 1934 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRoles", paramString);
/*      */ 
/*      */ 
/*      */     
/* 1938 */     isActive();
/*      */ 
/*      */     
/* 1941 */     Object object = getRelation(paramString);
/*      */ 
/*      */ 
/*      */     
/* 1945 */     if (object instanceof RelationSupport) {
/*      */       
/* 1947 */       roleResult = ((RelationSupport)object).getRolesInt(paramArrayOfString, true, this);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1952 */       Object[] arrayOfObject = new Object[1];
/* 1953 */       arrayOfObject[0] = paramArrayOfString;
/* 1954 */       String[] arrayOfString = new String[1];
/*      */       try {
/* 1956 */         arrayOfString[0] = paramArrayOfString.getClass().getName();
/* 1957 */       } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1965 */         roleResult = (RoleResult)this.myMBeanServer.invoke((ObjectName)object, "getRoles", arrayOfObject, arrayOfString);
/*      */ 
/*      */       
/*      */       }
/* 1969 */       catch (InstanceNotFoundException instanceNotFoundException) {
/* 1970 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 1971 */       } catch (ReflectionException reflectionException) {
/* 1972 */         throw new RuntimeException(reflectionException.getMessage());
/* 1973 */       } catch (MBeanException mBeanException) {
/* 1974 */         throw new RuntimeException(mBeanException
/* 1975 */             .getTargetException().getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 1979 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRoles");
/* 1980 */     return roleResult;
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
/*      */   public RoleResult getAllRoles(String paramString) throws IllegalArgumentException, RelationNotFoundException, RelationServiceNotRegisteredException {
/*      */     RoleResult roleResult;
/* 2002 */     if (paramString == null) {
/* 2003 */       String str = "Invalid parameter.";
/* 2004 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2007 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRoles", paramString);
/*      */ 
/*      */ 
/*      */     
/* 2011 */     Object object = getRelation(paramString);
/*      */ 
/*      */ 
/*      */     
/* 2015 */     if (object instanceof RelationSupport) {
/*      */       
/* 2017 */       roleResult = ((RelationSupport)object).getAllRolesInt(true, this);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2024 */         roleResult = (RoleResult)this.myMBeanServer.getAttribute((ObjectName)object, "AllRoles");
/*      */       }
/* 2026 */       catch (Exception exception) {
/* 2027 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 2031 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRoles");
/* 2032 */     return roleResult;
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
/*      */   public Integer getRoleCardinality(String paramString1, String paramString2) throws IllegalArgumentException, RelationNotFoundException, RoleNotFoundException {
/*      */     Integer integer;
/* 2053 */     if (paramString1 == null || paramString2 == null) {
/* 2054 */       String str = "Invalid parameter.";
/* 2055 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2058 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRoleCardinality", new Object[] { paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */     
/* 2062 */     Object object = getRelation(paramString1);
/*      */ 
/*      */ 
/*      */     
/* 2066 */     if (object instanceof RelationSupport) {
/*      */ 
/*      */       
/* 2069 */       integer = ((RelationSupport)object).getRoleCardinality(paramString2);
/*      */     }
/*      */     else {
/*      */       
/* 2073 */       Object[] arrayOfObject = new Object[1];
/* 2074 */       arrayOfObject[0] = paramString2;
/* 2075 */       String[] arrayOfString = new String[1];
/* 2076 */       arrayOfString[0] = "java.lang.String";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2083 */         integer = (Integer)this.myMBeanServer.invoke((ObjectName)object, "getRoleCardinality", arrayOfObject, arrayOfString);
/*      */ 
/*      */       
/*      */       }
/* 2087 */       catch (InstanceNotFoundException instanceNotFoundException) {
/* 2088 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 2089 */       } catch (ReflectionException reflectionException) {
/* 2090 */         throw new RuntimeException(reflectionException.getMessage());
/* 2091 */       } catch (MBeanException mBeanException) {
/* 2092 */         Exception exception = mBeanException.getTargetException();
/* 2093 */         if (exception instanceof RoleNotFoundException) {
/* 2094 */           throw (RoleNotFoundException)exception;
/*      */         }
/* 2096 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2101 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRoleCardinality");
/*      */     
/* 2103 */     return integer;
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
/*      */   public void setRole(String paramString, Role paramRole) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationNotFoundException, RoleNotFoundException, InvalidRoleValueException {
/* 2145 */     if (paramString == null || paramRole == null) {
/* 2146 */       String str = "Invalid parameter.";
/* 2147 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2150 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "setRole", new Object[] { paramString, paramRole });
/*      */ 
/*      */ 
/*      */     
/* 2154 */     isActive();
/*      */ 
/*      */     
/* 2157 */     Object object = getRelation(paramString);
/*      */     
/* 2159 */     if (object instanceof RelationSupport) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2168 */         ((RelationSupport)object).setRoleInt(paramRole, true, this, false);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 2173 */       catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 2174 */         throw new RuntimeException(relationTypeNotFoundException.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2179 */       Object[] arrayOfObject = new Object[1];
/* 2180 */       arrayOfObject[0] = paramRole;
/* 2181 */       String[] arrayOfString = new String[1];
/* 2182 */       arrayOfString[0] = "javax.management.relation.Role";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2190 */         this.myMBeanServer.setAttribute((ObjectName)object, new Attribute("Role", paramRole));
/*      */       
/*      */       }
/* 2193 */       catch (InstanceNotFoundException instanceNotFoundException) {
/* 2194 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 2195 */       } catch (ReflectionException reflectionException) {
/* 2196 */         throw new RuntimeException(reflectionException.getMessage());
/* 2197 */       } catch (MBeanException mBeanException) {
/* 2198 */         Exception exception = mBeanException.getTargetException();
/* 2199 */         if (exception instanceof RoleNotFoundException)
/* 2200 */           throw (RoleNotFoundException)exception; 
/* 2201 */         if (exception instanceof InvalidRoleValueException) {
/* 2202 */           throw (InvalidRoleValueException)exception;
/*      */         }
/* 2204 */         throw new RuntimeException(exception.getMessage());
/*      */       
/*      */       }
/* 2207 */       catch (AttributeNotFoundException attributeNotFoundException) {
/* 2208 */         throw new RuntimeException(attributeNotFoundException.getMessage());
/* 2209 */       } catch (InvalidAttributeValueException invalidAttributeValueException) {
/* 2210 */         throw new RuntimeException(invalidAttributeValueException.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 2214 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "setRole");
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
/*      */   public RoleResult setRoles(String paramString, RoleList paramRoleList) throws RelationServiceNotRegisteredException, IllegalArgumentException, RelationNotFoundException {
/*      */     RoleResult roleResult;
/* 2245 */     if (paramString == null || paramRoleList == null) {
/* 2246 */       String str = "Invalid parameter.";
/* 2247 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2250 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "setRoles", new Object[] { paramString, paramRoleList });
/*      */ 
/*      */ 
/*      */     
/* 2254 */     isActive();
/*      */ 
/*      */     
/* 2257 */     Object object = getRelation(paramString);
/*      */ 
/*      */ 
/*      */     
/* 2261 */     if (object instanceof RelationSupport) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 2268 */         roleResult = ((RelationSupport)object).setRolesInt(paramRoleList, true, this);
/*      */       
/*      */       }
/* 2271 */       catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 2272 */         throw new RuntimeException(relationTypeNotFoundException.getMessage());
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2277 */       Object[] arrayOfObject = new Object[1];
/* 2278 */       arrayOfObject[0] = paramRoleList;
/* 2279 */       String[] arrayOfString = new String[1];
/* 2280 */       arrayOfString[0] = "javax.management.relation.RoleList";
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2285 */         roleResult = (RoleResult)this.myMBeanServer.invoke((ObjectName)object, "setRoles", arrayOfObject, arrayOfString);
/*      */ 
/*      */       
/*      */       }
/* 2289 */       catch (InstanceNotFoundException instanceNotFoundException) {
/* 2290 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 2291 */       } catch (ReflectionException reflectionException) {
/* 2292 */         throw new RuntimeException(reflectionException.getMessage());
/* 2293 */       } catch (MBeanException mBeanException) {
/* 2294 */         throw new RuntimeException(mBeanException
/* 2295 */             .getTargetException().getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 2299 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "setRoles");
/* 2300 */     return roleResult;
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
/*      */   public Map<ObjectName, List<String>> getReferencedMBeans(String paramString) throws IllegalArgumentException, RelationNotFoundException {
/*      */     Map<ObjectName, List<String>> map;
/* 2320 */     if (paramString == null) {
/* 2321 */       String str = "Invalid parameter.";
/* 2322 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2325 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getReferencedMBeans", paramString);
/*      */ 
/*      */ 
/*      */     
/* 2329 */     Object object = getRelation(paramString);
/*      */ 
/*      */ 
/*      */     
/* 2333 */     if (object instanceof RelationSupport) {
/*      */       
/* 2335 */       map = ((RelationSupport)object).getReferencedMBeans();
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 2341 */         map = Util.<Map>cast(this.myMBeanServer
/* 2342 */             .getAttribute((ObjectName)object, "ReferencedMBeans"));
/*      */       }
/* 2344 */       catch (Exception exception) {
/* 2345 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 2349 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getReferencedMBeans");
/*      */     
/* 2351 */     return map;
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
/*      */   public String getRelationTypeName(String paramString) throws IllegalArgumentException, RelationNotFoundException {
/*      */     String str;
/* 2369 */     if (paramString == null) {
/* 2370 */       String str1 = "Invalid parameter.";
/* 2371 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 2374 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRelationTypeName", paramString);
/*      */ 
/*      */ 
/*      */     
/* 2378 */     Object object = getRelation(paramString);
/*      */ 
/*      */ 
/*      */     
/* 2382 */     if (object instanceof RelationSupport) {
/*      */       
/* 2384 */       str = ((RelationSupport)object).getRelationTypeName();
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 2391 */         str = (String)this.myMBeanServer.getAttribute((ObjectName)object, "RelationTypeName");
/*      */       }
/* 2393 */       catch (Exception exception) {
/* 2394 */         throw new RuntimeException(exception.getMessage());
/*      */       } 
/*      */     } 
/*      */     
/* 2398 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRelationTypeName");
/*      */     
/* 2400 */     return str;
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
/*      */   public void handleNotification(Notification paramNotification, Object paramObject) {
/* 2419 */     if (paramNotification == null) {
/* 2420 */       String str = "Invalid parameter.";
/* 2421 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2424 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "handleNotification", paramNotification);
/*      */ 
/*      */     
/* 2427 */     if (paramNotification instanceof MBeanServerNotification) {
/*      */       
/* 2429 */       MBeanServerNotification mBeanServerNotification = (MBeanServerNotification)paramNotification;
/* 2430 */       String str = paramNotification.getType();
/*      */       
/* 2432 */       if (str.equals("JMX.mbean.unregistered")) {
/*      */         String str1;
/*      */         
/* 2435 */         ObjectName objectName = ((MBeanServerNotification)paramNotification).getMBeanName();
/*      */ 
/*      */ 
/*      */         
/* 2439 */         boolean bool = false;
/* 2440 */         synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/*      */           
/* 2442 */           if (this.myRefedMBeanObjName2RelIdsMap.containsKey(objectName)) {
/*      */             
/* 2444 */             synchronized (this.myUnregNtfList) {
/* 2445 */               this.myUnregNtfList.add(mBeanServerNotification);
/*      */             } 
/* 2447 */             bool = true;
/*      */           } 
/* 2449 */           if (bool && this.myPurgeFlag) {
/*      */             
/*      */             try {
/*      */ 
/*      */               
/* 2454 */               purgeRelations();
/* 2455 */             } catch (Exception exception) {
/* 2456 */               throw new RuntimeException(exception.getMessage());
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2464 */         synchronized (this.myRelMBeanObjName2RelIdMap) {
/* 2465 */           str1 = this.myRelMBeanObjName2RelIdMap.get(objectName);
/*      */         } 
/* 2467 */         if (str1 != null) {
/*      */           
/*      */           try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2475 */             removeRelation(str1);
/* 2476 */           } catch (Exception exception) {
/* 2477 */             throw new RuntimeException(exception.getMessage());
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2483 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "handleNotification");
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
/*      */   public MBeanNotificationInfo[] getNotificationInfo() {
/* 2498 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getNotificationInfo");
/*      */ 
/*      */     
/* 2501 */     String str1 = "javax.management.relation.RelationNotification";
/*      */     
/* 2503 */     String[] arrayOfString = { "jmx.relation.creation.basic", "jmx.relation.creation.mbean", "jmx.relation.update.basic", "jmx.relation.update.mbean", "jmx.relation.removal.basic", "jmx.relation.removal.mbean" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2512 */     String str2 = "Sent when a relation is created, updated or deleted.";
/*      */     
/* 2514 */     MBeanNotificationInfo mBeanNotificationInfo = new MBeanNotificationInfo(arrayOfString, str1, str2);
/*      */ 
/*      */     
/* 2517 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getNotificationInfo");
/*      */     
/* 2519 */     return new MBeanNotificationInfo[] { mBeanNotificationInfo };
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
/*      */   private void addRelationTypeInt(RelationType paramRelationType) throws IllegalArgumentException, InvalidRelationTypeException {
/* 2537 */     if (paramRelationType == null) {
/* 2538 */       String str1 = "Invalid parameter.";
/* 2539 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 2542 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "addRelationTypeInt");
/*      */ 
/*      */     
/* 2545 */     String str = paramRelationType.getRelationTypeName();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2551 */       RelationType relationType = getRelationType(str);
/*      */       
/* 2553 */       if (relationType != null) {
/* 2554 */         String str1 = "There is already a relation type in the Relation Service with name ";
/* 2555 */         StringBuilder stringBuilder = new StringBuilder(str1);
/* 2556 */         stringBuilder.append(str);
/* 2557 */         throw new InvalidRelationTypeException(stringBuilder.toString());
/*      */       }
/*      */     
/* 2560 */     } catch (RelationTypeNotFoundException relationTypeNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2565 */     synchronized (this.myRelType2ObjMap) {
/* 2566 */       this.myRelType2ObjMap.put(str, paramRelationType);
/*      */     } 
/*      */     
/* 2569 */     if (paramRelationType instanceof RelationTypeSupport) {
/* 2570 */       ((RelationTypeSupport)paramRelationType).setRelationServiceFlag(true);
/*      */     }
/*      */     
/* 2573 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "addRelationTypeInt");
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
/*      */   RelationType getRelationType(String paramString) throws IllegalArgumentException, RelationTypeNotFoundException {
/*      */     RelationType relationType;
/* 2593 */     if (paramString == null) {
/* 2594 */       String str = "Invalid parameter.";
/* 2595 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2598 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRelationType", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2603 */     synchronized (this.myRelType2ObjMap) {
/* 2604 */       relationType = this.myRelType2ObjMap.get(paramString);
/*      */     } 
/*      */     
/* 2607 */     if (relationType == null) {
/* 2608 */       String str = "No relation type created in the Relation Service with the name ";
/* 2609 */       StringBuilder stringBuilder = new StringBuilder(str);
/* 2610 */       stringBuilder.append(paramString);
/* 2611 */       throw new RelationTypeNotFoundException(stringBuilder.toString());
/*      */     } 
/*      */     
/* 2614 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRelationType");
/*      */     
/* 2616 */     return relationType;
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
/*      */   Object getRelation(String paramString) throws IllegalArgumentException, RelationNotFoundException {
/*      */     Object object;
/* 2637 */     if (paramString == null) {
/* 2638 */       object = "Invalid parameter.";
/* 2639 */       throw new IllegalArgumentException(object);
/*      */     } 
/*      */     
/* 2642 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "getRelation", paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2647 */     synchronized (this.myRelId2ObjMap) {
/* 2648 */       object = this.myRelId2ObjMap.get(paramString);
/*      */     } 
/*      */     
/* 2651 */     if (object == null) {
/* 2652 */       String str = "No relation associated to relation id " + paramString;
/* 2653 */       throw new RelationNotFoundException(str);
/*      */     } 
/*      */     
/* 2656 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "getRelation");
/*      */     
/* 2658 */     return object;
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
/*      */   private boolean addNewMBeanReference(ObjectName paramObjectName, String paramString1, String paramString2) throws IllegalArgumentException {
/* 2680 */     if (paramObjectName == null || paramString1 == null || paramString2 == null) {
/*      */ 
/*      */       
/* 2683 */       String str = "Invalid parameter.";
/* 2684 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2687 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "addNewMBeanReference", new Object[] { paramObjectName, paramString1, paramString2 });
/*      */ 
/*      */ 
/*      */     
/* 2691 */     boolean bool = false;
/*      */     
/* 2693 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2698 */       Map<Object, Object> map = (Map)this.myRefedMBeanObjName2RelIdsMap.get(paramObjectName);
/*      */       
/* 2700 */       if (map == null) {
/*      */ 
/*      */         
/* 2703 */         bool = true;
/*      */ 
/*      */ 
/*      */         
/* 2707 */         ArrayList<String> arrayList = new ArrayList();
/* 2708 */         arrayList.add(paramString2);
/*      */ 
/*      */         
/* 2711 */         map = new HashMap<>();
/* 2712 */         map.put(paramString1, arrayList);
/*      */         
/* 2714 */         this.myRefedMBeanObjName2RelIdsMap.put(paramObjectName, map);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2720 */         List<String> list = (List)map.get(paramString1);
/*      */         
/* 2722 */         if (list == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2727 */           list = new ArrayList();
/* 2728 */           list.add(paramString2);
/*      */ 
/*      */           
/* 2731 */           map.put(paramString1, list);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 2737 */           list.add(paramString2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2742 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "addNewMBeanReference");
/*      */     
/* 2744 */     return bool;
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
/*      */   private boolean removeMBeanReference(ObjectName paramObjectName, String paramString1, String paramString2, boolean paramBoolean) throws IllegalArgumentException {
/* 2768 */     if (paramObjectName == null || paramString1 == null || paramString2 == null) {
/*      */ 
/*      */       
/* 2771 */       String str = "Invalid parameter.";
/* 2772 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 2775 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "removeMBeanReference", new Object[] { paramObjectName, paramString1, paramString2, 
/*      */           
/* 2777 */           Boolean.valueOf(paramBoolean) });
/*      */     
/* 2779 */     boolean bool = false;
/*      */     
/* 2781 */     synchronized (this.myRefedMBeanObjName2RelIdsMap) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2789 */       Map map = this.myRefedMBeanObjName2RelIdsMap.get(paramObjectName);
/*      */       
/* 2791 */       if (map == null) {
/*      */         
/* 2793 */         JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "removeMBeanReference");
/*      */         
/* 2795 */         return true;
/*      */       } 
/*      */       
/* 2798 */       List list = null;
/* 2799 */       if (!paramBoolean) {
/*      */ 
/*      */         
/* 2802 */         list = (List)map.get(paramString1);
/*      */ 
/*      */         
/* 2805 */         int i = list.indexOf(paramString2);
/* 2806 */         if (i != -1) {
/* 2807 */           list.remove(i);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2813 */       if (list.isEmpty() || paramBoolean)
/*      */       {
/*      */         
/* 2816 */         map.remove(paramString1);
/*      */       }
/*      */ 
/*      */       
/* 2820 */       if (map.isEmpty()) {
/*      */         
/* 2822 */         this.myRefedMBeanObjName2RelIdsMap.remove(paramObjectName);
/* 2823 */         bool = true;
/*      */       } 
/*      */     } 
/*      */     
/* 2827 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "removeMBeanReference");
/*      */     
/* 2829 */     return bool;
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
/*      */   private void updateUnregistrationListener(List<ObjectName> paramList1, List<ObjectName> paramList2) throws RelationServiceNotRegisteredException {
/* 2846 */     if (paramList1 != null && paramList2 != null && 
/* 2847 */       paramList1.isEmpty() && paramList2.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2853 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "updateUnregistrationListener", new Object[] { paramList1, paramList2 });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2858 */     isActive();
/*      */     
/* 2860 */     if (paramList1 != null || paramList2 != null) {
/*      */       
/* 2862 */       boolean bool = false;
/* 2863 */       if (this.myUnregNtfFilter == null) {
/*      */         
/* 2865 */         this.myUnregNtfFilter = new MBeanServerNotificationFilter();
/* 2866 */         bool = true;
/*      */       } 
/*      */       
/* 2869 */       synchronized (this.myUnregNtfFilter) {
/*      */ 
/*      */         
/* 2872 */         if (paramList1 != null) {
/* 2873 */           for (ObjectName objectName : paramList1) {
/* 2874 */             this.myUnregNtfFilter.enableObjectName(objectName);
/*      */           }
/*      */         }
/* 2877 */         if (paramList2 != null)
/*      */         {
/* 2879 */           for (ObjectName objectName : paramList2) {
/* 2880 */             this.myUnregNtfFilter.disableObjectName(objectName);
/*      */           }
/*      */         }
/*      */         
/* 2884 */         if (bool) {
/*      */           try {
/* 2886 */             this.myMBeanServer.addNotificationListener(MBeanServerDelegate.DELEGATE_NAME, this, this.myUnregNtfFilter, (Object)null);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 2891 */           catch (InstanceNotFoundException instanceNotFoundException) {
/* 2892 */             throw new RelationServiceNotRegisteredException(instanceNotFoundException
/* 2893 */                 .getMessage());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2939 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "updateUnregistrationListener");
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
/*      */   private void addRelationInt(boolean paramBoolean, RelationSupport paramRelationSupport, ObjectName paramObjectName, String paramString1, String paramString2, RoleList paramRoleList) throws IllegalArgumentException, RelationServiceNotRegisteredException, RoleNotFoundException, InvalidRelationIdException, RelationTypeNotFoundException, InvalidRoleValueException {
/* 2994 */     if (paramString1 == null || paramString2 == null || (paramBoolean && (paramRelationSupport == null || paramObjectName != null)) || (!paramBoolean && (paramObjectName == null || paramRelationSupport != null))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3002 */       String str = "Invalid parameter.";
/* 3003 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 3006 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "addRelationInt", new Object[] {
/* 3007 */           Boolean.valueOf(paramBoolean), paramRelationSupport, paramObjectName, paramString1, paramString2, paramRoleList
/*      */         });
/*      */ 
/*      */     
/* 3011 */     isActive();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3016 */       Object object = getRelation(paramString1);
/*      */       
/* 3018 */       if (object != null) {
/*      */         
/* 3020 */         String str = "There is already a relation with id ";
/* 3021 */         StringBuilder stringBuilder = new StringBuilder(str);
/* 3022 */         stringBuilder.append(paramString1);
/* 3023 */         throw new InvalidRelationIdException(stringBuilder.toString());
/*      */       } 
/* 3025 */     } catch (RelationNotFoundException relationNotFoundException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3031 */     RelationType relationType = getRelationType(paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3039 */     ArrayList<RoleInfo> arrayList = new ArrayList<>(relationType.getRoleInfos());
/*      */     
/* 3041 */     if (paramRoleList != null)
/*      */     {
/* 3043 */       for (Role role : paramRoleList.asList()) {
/* 3044 */         RoleInfo roleInfo; String str = role.getRoleName();
/* 3045 */         List<ObjectName> list = role.getRoleValue();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3051 */           roleInfo = relationType.getRoleInfo(str);
/* 3052 */         } catch (RoleInfoNotFoundException roleInfoNotFoundException) {
/* 3053 */           throw new RoleNotFoundException(roleInfoNotFoundException.getMessage());
/*      */         } 
/*      */ 
/*      */         
/* 3057 */         Integer integer = checkRoleInt(2, str, list, roleInfo, false);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3062 */         int i = integer.intValue();
/* 3063 */         if (i != 0)
/*      */         {
/*      */           
/* 3066 */           throwRoleProblemException(i, str);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3071 */         int j = arrayList.indexOf(roleInfo);
/*      */         
/* 3073 */         arrayList.remove(j);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3079 */     initializeMissingRoles(paramBoolean, paramRelationSupport, paramObjectName, paramString1, paramString2, arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3090 */     synchronized (this.myRelId2ObjMap) {
/* 3091 */       if (paramBoolean) {
/*      */         
/* 3093 */         this.myRelId2ObjMap.put(paramString1, paramRelationSupport);
/*      */       } else {
/* 3095 */         this.myRelId2ObjMap.put(paramString1, paramObjectName);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3100 */     synchronized (this.myRelId2RelTypeMap) {
/* 3101 */       this.myRelId2RelTypeMap.put(paramString1, paramString2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3106 */     synchronized (this.myRelType2RelIdsMap) {
/*      */       
/* 3108 */       List<String> list = this.myRelType2RelIdsMap.get(paramString2);
/* 3109 */       boolean bool = false;
/* 3110 */       if (list == null) {
/* 3111 */         bool = true;
/* 3112 */         list = new ArrayList();
/*      */       } 
/* 3114 */       list.add(paramString1);
/* 3115 */       if (bool) {
/* 3116 */         this.myRelType2RelIdsMap.put(paramString2, list);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3124 */     for (Role role : paramRoleList.asList()) {
/*      */ 
/*      */       
/* 3127 */       ArrayList<ObjectName> arrayList1 = new ArrayList();
/*      */ 
/*      */       
/*      */       try {
/* 3131 */         updateRoleMap(paramString1, role, arrayList1);
/*      */       }
/* 3133 */       catch (RelationNotFoundException relationNotFoundException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3141 */       sendRelationCreationNotification(paramString1);
/*      */     }
/* 3143 */     catch (RelationNotFoundException relationNotFoundException) {}
/*      */ 
/*      */ 
/*      */     
/* 3147 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "addRelationInt");
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
/*      */   private Integer checkRoleInt(int paramInt, String paramString, List<ObjectName> paramList, RoleInfo paramRoleInfo, boolean paramBoolean) throws IllegalArgumentException {
/* 3181 */     if (paramString == null || paramRoleInfo == null || (paramInt == 2 && paramList == null)) {
/*      */ 
/*      */       
/* 3184 */       String str = "Invalid parameter.";
/* 3185 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 3188 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "checkRoleInt", new Object[] {
/* 3189 */           Integer.valueOf(paramInt), paramString, paramList, paramRoleInfo, 
/* 3190 */           Boolean.valueOf(paramBoolean)
/*      */         });
/*      */     
/* 3193 */     String str1 = paramRoleInfo.getName();
/* 3194 */     if (!paramString.equals(str1)) {
/* 3195 */       JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */       
/* 3197 */       return Integer.valueOf(1);
/*      */     } 
/*      */ 
/*      */     
/* 3201 */     if (paramInt == 1) {
/* 3202 */       boolean bool = paramRoleInfo.isReadable();
/* 3203 */       if (!bool) {
/* 3204 */         JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */         
/* 3206 */         return Integer.valueOf(2);
/*      */       } 
/*      */       
/* 3209 */       JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */       
/* 3211 */       return new Integer(0);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3216 */     if (paramBoolean) {
/* 3217 */       boolean bool = paramRoleInfo.isWritable();
/* 3218 */       if (!bool) {
/* 3219 */         JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */         
/* 3221 */         return new Integer(3);
/*      */       } 
/*      */     } 
/*      */     
/* 3225 */     int i = paramList.size();
/*      */ 
/*      */     
/* 3228 */     boolean bool1 = paramRoleInfo.checkMinDegree(i);
/* 3229 */     if (!bool1) {
/* 3230 */       JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */       
/* 3232 */       return new Integer(4);
/*      */     } 
/*      */ 
/*      */     
/* 3236 */     boolean bool2 = paramRoleInfo.checkMaxDegree(i);
/* 3237 */     if (!bool2) {
/* 3238 */       JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */       
/* 3240 */       return new Integer(5);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3249 */     String str2 = paramRoleInfo.getRefMBeanClassName();
/*      */     
/* 3251 */     for (ObjectName objectName : paramList) {
/*      */ 
/*      */       
/* 3254 */       if (objectName == null) {
/* 3255 */         JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */         
/* 3257 */         return new Integer(7);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 3263 */         boolean bool = this.myMBeanServer.isInstanceOf(objectName, str2);
/*      */         
/* 3265 */         if (!bool) {
/* 3266 */           JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */           
/* 3268 */           return new Integer(6);
/*      */         }
/*      */       
/* 3271 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/* 3272 */         JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */         
/* 3274 */         return new Integer(7);
/*      */       } 
/*      */     } 
/*      */     
/* 3278 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "checkRoleInt");
/*      */     
/* 3280 */     return new Integer(0);
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
/*      */   private void initializeMissingRoles(boolean paramBoolean, RelationSupport paramRelationSupport, ObjectName paramObjectName, String paramString1, String paramString2, List<RoleInfo> paramList) throws IllegalArgumentException, RelationServiceNotRegisteredException, InvalidRoleValueException {
/* 3318 */     if ((paramBoolean && (paramRelationSupport == null || paramObjectName != null)) || (!paramBoolean && (paramObjectName == null || paramRelationSupport != null)) || paramString1 == null || paramString2 == null || paramList == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3327 */       String str = "Invalid parameter.";
/* 3328 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */     
/* 3331 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "initializeMissingRoles", new Object[] {
/* 3332 */           Boolean.valueOf(paramBoolean), paramRelationSupport, paramObjectName, paramString1, paramString2, paramList
/*      */         });
/*      */ 
/*      */ 
/*      */     
/* 3337 */     isActive();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3344 */     for (RoleInfo roleInfo : paramList) {
/*      */       
/* 3346 */       String str = roleInfo.getName();
/*      */ 
/*      */       
/* 3349 */       ArrayList<ObjectName> arrayList = new ArrayList();
/*      */       
/* 3351 */       Role role = new Role(str, arrayList);
/*      */       
/* 3353 */       if (paramBoolean) {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3362 */           paramRelationSupport.setRoleInt(role, true, this, false);
/*      */         }
/* 3364 */         catch (RoleNotFoundException roleNotFoundException) {
/* 3365 */           throw new RuntimeException(roleNotFoundException.getMessage());
/* 3366 */         } catch (RelationNotFoundException relationNotFoundException) {
/* 3367 */           throw new RuntimeException(relationNotFoundException.getMessage());
/* 3368 */         } catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 3369 */           throw new RuntimeException(relationTypeNotFoundException.getMessage());
/*      */         } 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 3376 */       Object[] arrayOfObject = new Object[1];
/* 3377 */       arrayOfObject[0] = role;
/* 3378 */       String[] arrayOfString = new String[1];
/* 3379 */       arrayOfString[0] = "javax.management.relation.Role";
/*      */ 
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
/* 3391 */         this.myMBeanServer.setAttribute(paramObjectName, new Attribute("Role", role));
/*      */       
/*      */       }
/* 3394 */       catch (InstanceNotFoundException instanceNotFoundException) {
/* 3395 */         throw new RuntimeException(instanceNotFoundException.getMessage());
/* 3396 */       } catch (ReflectionException reflectionException) {
/* 3397 */         throw new RuntimeException(reflectionException.getMessage());
/* 3398 */       } catch (MBeanException mBeanException) {
/* 3399 */         Exception exception = mBeanException.getTargetException();
/* 3400 */         if (exception instanceof InvalidRoleValueException) {
/* 3401 */           throw (InvalidRoleValueException)exception;
/*      */         }
/* 3403 */         throw new RuntimeException(exception.getMessage());
/*      */       }
/* 3405 */       catch (AttributeNotFoundException attributeNotFoundException) {
/* 3406 */         throw new RuntimeException(attributeNotFoundException.getMessage());
/* 3407 */       } catch (InvalidAttributeValueException invalidAttributeValueException) {
/* 3408 */         throw new RuntimeException(invalidAttributeValueException.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3413 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "initializeMissingRoles");
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
/*      */   static void throwRoleProblemException(int paramInt, String paramString) throws IllegalArgumentException, RoleNotFoundException, InvalidRoleValueException {
/* 3439 */     if (paramString == null) {
/* 3440 */       String str = "Invalid parameter.";
/* 3441 */       throw new IllegalArgumentException(str);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3446 */     byte b = 0;
/*      */     
/* 3448 */     String str1 = null;
/*      */     
/* 3450 */     switch (paramInt) {
/*      */       case 1:
/* 3452 */         str1 = " does not exist in relation.";
/* 3453 */         b = 1;
/*      */         break;
/*      */       case 2:
/* 3456 */         str1 = " is not readable.";
/* 3457 */         b = 1;
/*      */         break;
/*      */       case 3:
/* 3460 */         str1 = " is not writable.";
/* 3461 */         b = 1;
/*      */         break;
/*      */       case 4:
/* 3464 */         str1 = " has a number of MBean references less than the expected minimum degree.";
/* 3465 */         b = 2;
/*      */         break;
/*      */       case 5:
/* 3468 */         str1 = " has a number of MBean references greater than the expected maximum degree.";
/* 3469 */         b = 2;
/*      */         break;
/*      */       case 6:
/* 3472 */         str1 = " has an MBean reference to an MBean not of the expected class of references for that role.";
/* 3473 */         b = 2;
/*      */         break;
/*      */       case 7:
/* 3476 */         str1 = " has a reference to null or to an MBean not registered.";
/* 3477 */         b = 2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 3482 */     StringBuilder stringBuilder = new StringBuilder(paramString);
/* 3483 */     stringBuilder.append(str1);
/* 3484 */     String str2 = stringBuilder.toString();
/* 3485 */     if (b == 1) {
/* 3486 */       throw new RoleNotFoundException(str2);
/*      */     }
/* 3488 */     if (b == 2) {
/* 3489 */       throw new InvalidRoleValueException(str2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendNotificationInt(int paramInt, String paramString1, String paramString2, List<ObjectName> paramList1, String paramString3, List<ObjectName> paramList2, List<ObjectName> paramList3) throws IllegalArgumentException, RelationNotFoundException {
/*      */     String str1;
/* 3520 */     if (paramString1 == null || paramString2 == null || (paramInt != 3 && paramList1 != null) || (paramInt == 2 && (paramString3 == null || paramList2 == null || paramList3 == null))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3527 */       str1 = "Invalid parameter.";
/* 3528 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 3531 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "sendNotificationInt", new Object[] {
/* 3532 */           Integer.valueOf(paramInt), paramString1, paramString2, paramList1, paramString3, paramList2, paramList3
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3539 */     synchronized (this.myRelId2RelTypeMap) {
/* 3540 */       str1 = this.myRelId2RelTypeMap.get(paramString2);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3545 */     ObjectName objectName = isRelationMBean(paramString2);
/*      */     
/* 3547 */     String str2 = null;
/* 3548 */     if (objectName != null) {
/* 3549 */       switch (paramInt) {
/*      */         case 1:
/* 3551 */           str2 = "jmx.relation.creation.mbean";
/*      */           break;
/*      */         case 2:
/* 3554 */           str2 = "jmx.relation.update.mbean";
/*      */           break;
/*      */         case 3:
/* 3557 */           str2 = "jmx.relation.removal.mbean";
/*      */           break;
/*      */       } 
/*      */     } else {
/* 3561 */       switch (paramInt) {
/*      */         case 1:
/* 3563 */           str2 = "jmx.relation.creation.basic";
/*      */           break;
/*      */         case 2:
/* 3566 */           str2 = "jmx.relation.update.basic";
/*      */           break;
/*      */         case 3:
/* 3569 */           str2 = "jmx.relation.removal.basic";
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 3575 */     Long long_ = Long.valueOf(this.atomicSeqNo.incrementAndGet());
/*      */ 
/*      */     
/* 3578 */     Date date = new Date();
/* 3579 */     long l = date.getTime();
/*      */     
/* 3581 */     RelationNotification relationNotification = null;
/*      */     
/* 3583 */     if (str2.equals("jmx.relation.creation.basic") || str2
/* 3584 */       .equals("jmx.relation.creation.mbean") || str2
/* 3585 */       .equals("jmx.relation.removal.basic") || str2
/* 3586 */       .equals("jmx.relation.removal.mbean")) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3591 */       relationNotification = new RelationNotification(str2, this, long_.longValue(), l, paramString1, paramString2, str1, objectName, paramList1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 3599 */     else if (str2.equals("jmx.relation.update.basic") || str2
/*      */       
/* 3601 */       .equals("jmx.relation.update.mbean")) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3606 */       relationNotification = new RelationNotification(str2, this, long_.longValue(), l, paramString1, paramString2, str1, objectName, paramString3, paramList2, paramList3);
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
/* 3617 */     sendNotification(relationNotification);
/*      */     
/* 3619 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "sendNotificationInt");
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
/*      */   private void handleReferenceUnregistration(String paramString, ObjectName paramObjectName, List<String> paramList) throws IllegalArgumentException, RelationServiceNotRegisteredException, RelationNotFoundException, RoleNotFoundException {
/* 3650 */     if (paramString == null || paramList == null || paramObjectName == null) {
/*      */ 
/*      */       
/* 3653 */       String str1 = "Invalid parameter.";
/* 3654 */       throw new IllegalArgumentException(str1);
/*      */     } 
/*      */     
/* 3657 */     JmxProperties.RELATION_LOGGER.entering(RelationService.class.getName(), "handleReferenceUnregistration", new Object[] { paramString, paramObjectName, paramList });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3662 */     isActive();
/*      */ 
/*      */ 
/*      */     
/* 3666 */     String str = getRelationTypeName(paramString);
/*      */ 
/*      */ 
/*      */     
/* 3670 */     Object object = getRelation(paramString);
/*      */ 
/*      */     
/* 3673 */     boolean bool = false;
/*      */     
/* 3675 */     for (String str1 : paramList) {
/*      */       RoleInfo roleInfo;
/* 3677 */       if (bool) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3687 */       int i = getRoleCardinality(paramString, str1).intValue();
/*      */ 
/*      */       
/* 3690 */       int j = i - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 3698 */         roleInfo = getRoleInfo(str, str1);
/*      */       }
/* 3700 */       catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 3701 */         throw new RuntimeException(relationTypeNotFoundException.getMessage());
/* 3702 */       } catch (RoleInfoNotFoundException roleInfoNotFoundException) {
/* 3703 */         throw new RuntimeException(roleInfoNotFoundException.getMessage());
/*      */       } 
/*      */ 
/*      */       
/* 3707 */       boolean bool1 = roleInfo.checkMinDegree(j);
/*      */       
/* 3709 */       if (!bool1)
/*      */       {
/* 3711 */         bool = true;
/*      */       }
/*      */     } 
/*      */     
/* 3715 */     if (bool) {
/*      */       
/* 3717 */       removeRelation(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3732 */       for (String str1 : paramList) {
/*      */         
/* 3734 */         if (object instanceof RelationSupport) {
/*      */ 
/*      */           
/*      */           try {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3744 */             ((RelationSupport)object).handleMBeanUnregistrationInt(paramObjectName, str1, true, this);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 3749 */           catch (RelationTypeNotFoundException relationTypeNotFoundException) {
/* 3750 */             throw new RuntimeException(relationTypeNotFoundException.getMessage());
/* 3751 */           } catch (InvalidRoleValueException invalidRoleValueException) {
/* 3752 */             throw new RuntimeException(invalidRoleValueException.getMessage());
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 3757 */         Object[] arrayOfObject = new Object[2];
/* 3758 */         arrayOfObject[0] = paramObjectName;
/* 3759 */         arrayOfObject[1] = str1;
/* 3760 */         String[] arrayOfString = new String[2];
/* 3761 */         arrayOfString[0] = "javax.management.ObjectName";
/* 3762 */         arrayOfString[1] = "java.lang.String";
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3768 */           this.myMBeanServer.invoke((ObjectName)object, "handleMBeanUnregistration", arrayOfObject, arrayOfString);
/*      */ 
/*      */         
/*      */         }
/* 3772 */         catch (InstanceNotFoundException instanceNotFoundException) {
/* 3773 */           throw new RuntimeException(instanceNotFoundException.getMessage());
/* 3774 */         } catch (ReflectionException reflectionException) {
/* 3775 */           throw new RuntimeException(reflectionException.getMessage());
/* 3776 */         } catch (MBeanException mBeanException) {
/* 3777 */           Exception exception = mBeanException.getTargetException();
/* 3778 */           throw new RuntimeException(exception.getMessage());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3785 */     JmxProperties.RELATION_LOGGER.exiting(RelationService.class.getName(), "handleReferenceUnregistration");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/relation/RelationService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */