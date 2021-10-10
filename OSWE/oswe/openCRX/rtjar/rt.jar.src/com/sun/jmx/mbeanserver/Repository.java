/*     */ package com.sun.jmx.mbeanserver;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.QueryExp;
/*     */ import javax.management.RuntimeOperationsException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Repository
/*     */ {
/*     */   private final Map<String, Map<String, NamedObject>> domainTb;
/*  89 */   private volatile int nbElements = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String domain;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReentrantReadWriteLock lock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ObjectNamePattern
/*     */   {
/*     */     private final String[] keys;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String[] values;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String properties;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean isPropertyListPattern;
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean isPropertyValuePattern;
/*     */ 
/*     */     
/*     */     public final ObjectName pattern;
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectNamePattern(ObjectName param1ObjectName) {
/* 128 */       this(param1ObjectName.isPropertyListPattern(), param1ObjectName
/* 129 */           .isPropertyValuePattern(), param1ObjectName
/* 130 */           .getCanonicalKeyPropertyListString(), param1ObjectName
/* 131 */           .getKeyPropertyList(), param1ObjectName);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectNamePattern(boolean param1Boolean1, boolean param1Boolean2, String param1String, Map<String, String> param1Map, ObjectName param1ObjectName) {
/* 149 */       this.isPropertyListPattern = param1Boolean1;
/* 150 */       this.isPropertyValuePattern = param1Boolean2;
/* 151 */       this.properties = param1String;
/* 152 */       int i = param1Map.size();
/* 153 */       this.keys = new String[i];
/* 154 */       this.values = new String[i];
/* 155 */       byte b = 0;
/* 156 */       for (Map.Entry<String, String> entry : param1Map.entrySet()) {
/* 157 */         this.keys[b] = (String)entry.getKey();
/* 158 */         this.values[b] = (String)entry.getValue();
/* 159 */         b++;
/*     */       } 
/* 161 */       this.pattern = param1ObjectName;
/*     */     }
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
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matchKeys(ObjectName param1ObjectName) {
/* 177 */       if (this.isPropertyValuePattern && !this.isPropertyListPattern && param1ObjectName
/*     */         
/* 179 */         .getKeyPropertyList().size() != this.keys.length) {
/* 180 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 185 */       if (this.isPropertyValuePattern || this.isPropertyListPattern) {
/* 186 */         for (int i = this.keys.length - 1; i >= 0; i--) {
/*     */ 
/*     */ 
/*     */           
/* 190 */           String str = param1ObjectName.getKeyProperty(this.keys[i]);
/*     */ 
/*     */           
/* 193 */           if (str == null) return false;
/*     */ 
/*     */           
/* 196 */           if (this.isPropertyValuePattern && this.pattern
/* 197 */             .isPropertyValuePattern(this.keys[i])) {
/*     */ 
/*     */ 
/*     */             
/* 201 */             if (!Util.wildmatch(str, this.values[i]))
/*     */             {
/*     */               
/* 204 */               return false;
/*     */             }
/* 206 */           } else if (!str.equals(this.values[i])) {
/* 207 */             return false;
/*     */           } 
/* 209 */         }  return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 214 */       String str1 = param1ObjectName.getCanonicalKeyPropertyListString();
/* 215 */       String str2 = this.properties;
/* 216 */       return str1.equals(str2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAllMatching(Map<String, NamedObject> paramMap, Set<NamedObject> paramSet, ObjectNamePattern paramObjectNamePattern) {
/* 229 */     synchronized (paramMap) {
/* 230 */       for (NamedObject namedObject : paramMap.values()) {
/* 231 */         ObjectName objectName = namedObject.getName();
/*     */         
/* 233 */         if (paramObjectNamePattern.matchKeys(objectName)) paramSet.add(namedObject);
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addNewDomMoi(DynamicMBean paramDynamicMBean, String paramString, ObjectName paramObjectName, RegistrationContext paramRegistrationContext) {
/* 242 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */     
/* 244 */     String str = paramObjectName.getCanonicalKeyPropertyListString();
/* 245 */     addMoiToTb(paramDynamicMBean, paramObjectName, str, (Map)hashMap, paramRegistrationContext);
/* 246 */     this.domainTb.put(paramString, hashMap);
/* 247 */     this.nbElements++;
/*     */   }
/*     */   
/*     */   private void registering(RegistrationContext paramRegistrationContext) {
/* 251 */     if (paramRegistrationContext == null)
/*     */       return;  try {
/* 253 */       paramRegistrationContext.registering();
/* 254 */     } catch (RuntimeOperationsException runtimeOperationsException) {
/* 255 */       throw runtimeOperationsException;
/* 256 */     } catch (RuntimeException runtimeException) {
/* 257 */       throw new RuntimeOperationsException(runtimeException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unregistering(RegistrationContext paramRegistrationContext, ObjectName paramObjectName) {
/* 262 */     if (paramRegistrationContext == null)
/*     */       return;  try {
/* 264 */       paramRegistrationContext.unregistered();
/* 265 */     } catch (Exception exception) {
/*     */       
/* 267 */       JmxProperties.MBEANSERVER_LOGGER.log(Level.FINE, "Unexpected exception while unregistering " + paramObjectName, exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMoiToTb(DynamicMBean paramDynamicMBean, ObjectName paramObjectName, String paramString, Map<String, NamedObject> paramMap, RegistrationContext paramRegistrationContext) {
/* 278 */     registering(paramRegistrationContext);
/* 279 */     paramMap.put(paramString, new NamedObject(paramObjectName, paramDynamicMBean));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NamedObject retrieveNamedObject(ObjectName paramObjectName) {
/* 289 */     if (paramObjectName.isPattern()) return null;
/*     */ 
/*     */     
/* 292 */     String str = paramObjectName.getDomain().intern();
/*     */ 
/*     */     
/* 295 */     if (str.length() == 0) {
/* 296 */       str = this.domain;
/*     */     }
/*     */     
/* 299 */     Map map = this.domainTb.get(str);
/* 300 */     if (map == null) {
/* 301 */       return null;
/*     */     }
/*     */     
/* 304 */     return (NamedObject)map.get(paramObjectName.getCanonicalKeyPropertyListString());
/*     */   }
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
/*     */ 
/*     */   
/*     */   public Repository(String paramString) {
/* 319 */     this(paramString, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Repository(String paramString, boolean paramBoolean) {
/* 326 */     this.lock = new ReentrantReadWriteLock(paramBoolean);
/*     */     
/* 328 */     this.domainTb = new HashMap<>(5);
/*     */     
/* 330 */     if (paramString != null && paramString.length() != 0) {
/* 331 */       this.domain = paramString.intern();
/*     */     } else {
/* 333 */       this.domain = "DefaultDomain";
/*     */     } 
/*     */     
/* 336 */     this.domainTb.put(this.domain, new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getDomains() {
/*     */     ArrayList arrayList;
/* 346 */     this.lock.readLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 350 */       arrayList = new ArrayList(this.domainTb.size());
/*     */       
/* 352 */       for (Map.Entry<String, Map<String, NamedObject>> entry : this.domainTb.entrySet()) {
/*     */ 
/*     */ 
/*     */         
/* 356 */         Map map = (Map)entry.getValue();
/* 357 */         if (map != null && map.size() != 0)
/* 358 */           arrayList.add(entry.getKey()); 
/*     */       } 
/*     */     } finally {
/* 361 */       this.lock.readLock().unlock();
/*     */     } 
/*     */ 
/*     */     
/* 365 */     return (String[])arrayList.toArray((Object[])new String[arrayList.size()]);
/*     */   }
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
/*     */   public void addMBean(DynamicMBean paramDynamicMBean, ObjectName paramObjectName, RegistrationContext paramRegistrationContext) throws InstanceAlreadyExistsException {
/* 388 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 389 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(), "addMBean", "name = " + paramObjectName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 394 */     String str = paramObjectName.getDomain().intern();
/* 395 */     boolean bool = false;
/*     */ 
/*     */     
/* 398 */     if (str.length() == 0) {
/* 399 */       paramObjectName = Util.newObjectName(this.domain + paramObjectName.toString());
/*     */     }
/*     */     
/* 402 */     if (str == this.domain) {
/* 403 */       bool = true;
/* 404 */       str = this.domain;
/*     */     } else {
/* 406 */       bool = false;
/*     */     } 
/*     */ 
/*     */     
/* 410 */     if (paramObjectName.isPattern()) {
/* 411 */       throw new RuntimeOperationsException(new IllegalArgumentException("Repository: cannot add mbean for pattern name " + paramObjectName
/*     */             
/* 413 */             .toString()));
/*     */     }
/*     */     
/* 416 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 419 */       if (!bool && str
/* 420 */         .equals("JMImplementation") && this.domainTb
/* 421 */         .containsKey("JMImplementation")) {
/* 422 */         throw new RuntimeOperationsException(new IllegalArgumentException("Repository: domain name cannot be JMImplementation"));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 428 */       Map<String, NamedObject> map = this.domainTb.get(str);
/* 429 */       if (map == null) {
/* 430 */         addNewDomMoi(paramDynamicMBean, str, paramObjectName, paramRegistrationContext);
/*     */         
/*     */         return;
/*     */       } 
/* 434 */       String str1 = paramObjectName.getCanonicalKeyPropertyListString();
/* 435 */       NamedObject namedObject = (NamedObject)map.get(str1);
/* 436 */       if (namedObject != null) {
/* 437 */         throw new InstanceAlreadyExistsException(paramObjectName.toString());
/*     */       }
/* 439 */       this.nbElements++;
/* 440 */       addMoiToTb(paramDynamicMBean, paramObjectName, str1, map, paramRegistrationContext);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 445 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
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
/*     */   public boolean contains(ObjectName paramObjectName) {
/* 459 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 460 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(), "contains", " name = " + paramObjectName);
/*     */     }
/*     */     
/* 463 */     this.lock.readLock().lock();
/*     */     try {
/* 465 */       return (retrieveNamedObject(paramObjectName) != null);
/*     */     } finally {
/* 467 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
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
/*     */   public DynamicMBean retrieve(ObjectName paramObjectName) {
/* 481 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 482 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(), "retrieve", "name = " + paramObjectName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 487 */     this.lock.readLock().lock();
/*     */     try {
/* 489 */       NamedObject namedObject = retrieveNamedObject(paramObjectName);
/* 490 */       if (namedObject == null) return null; 
/* 491 */       return namedObject.getObject();
/*     */     } finally {
/* 493 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<NamedObject> query(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*     */     ObjectName objectName;
/* 513 */     HashSet<NamedObject> hashSet = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 522 */     if (paramObjectName == null || paramObjectName
/* 523 */       .getCanonicalName().length() == 0 || paramObjectName
/* 524 */       .equals(ObjectName.WILDCARD))
/* 525 */     { objectName = ObjectName.WILDCARD; }
/* 526 */     else { objectName = paramObjectName; }
/*     */     
/* 528 */     this.lock.readLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 532 */       if (!objectName.isPattern()) {
/* 533 */         NamedObject namedObject = retrieveNamedObject(objectName);
/* 534 */         if (namedObject != null) hashSet.add(namedObject); 
/* 535 */         return hashSet;
/*     */       } 
/*     */ 
/*     */       
/* 539 */       if (objectName == ObjectName.WILDCARD) {
/* 540 */         for (Map<String, NamedObject> map : this.domainTb.values()) {
/* 541 */           hashSet.addAll(map.values());
/*     */         }
/* 543 */         return hashSet;
/*     */       } 
/*     */ 
/*     */       
/* 547 */       String str1 = objectName.getCanonicalKeyPropertyListString();
/*     */       
/* 549 */       boolean bool = (str1.length() == 0) ? true : false;
/* 550 */       ObjectNamePattern objectNamePattern = bool ? null : new ObjectNamePattern(objectName);
/*     */ 
/*     */ 
/*     */       
/* 554 */       if (objectName.getDomain().length() == 0) {
/* 555 */         Map<String, NamedObject> map = this.domainTb.get(this.domain);
/* 556 */         if (bool) {
/* 557 */           hashSet.addAll(map.values());
/*     */         } else {
/* 559 */           addAllMatching(map, hashSet, objectNamePattern);
/* 560 */         }  return hashSet;
/*     */       } 
/*     */       
/* 563 */       if (!objectName.isDomainPattern()) {
/* 564 */         Map<String, NamedObject> map = this.domainTb.get(objectName.getDomain());
/* 565 */         if (map == null) return (Set)Collections.emptySet(); 
/* 566 */         if (bool) {
/* 567 */           hashSet.addAll(map.values());
/*     */         } else {
/* 569 */           addAllMatching(map, hashSet, objectNamePattern);
/* 570 */         }  return hashSet;
/*     */       } 
/*     */ 
/*     */       
/* 574 */       String str2 = objectName.getDomain();
/* 575 */       for (String str : this.domainTb.keySet()) {
/* 576 */         if (Util.wildmatch(str, str2)) {
/* 577 */           Map<String, NamedObject> map = this.domainTb.get(str);
/* 578 */           if (bool) {
/* 579 */             hashSet.addAll(map.values()); continue;
/*     */           } 
/* 581 */           addAllMatching(map, hashSet, objectNamePattern);
/*     */         } 
/*     */       } 
/* 584 */       return hashSet;
/*     */     } finally {
/* 586 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
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
/*     */ 
/*     */   
/*     */   public void remove(ObjectName paramObjectName, RegistrationContext paramRegistrationContext) throws InstanceNotFoundException {
/* 612 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 613 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, Repository.class.getName(), "remove", "name = " + paramObjectName);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 618 */     String str = paramObjectName.getDomain().intern();
/*     */ 
/*     */     
/* 621 */     if (str.length() == 0) str = this.domain;
/*     */     
/* 623 */     this.lock.writeLock().lock();
/*     */     
/*     */     try {
/* 626 */       Map map = this.domainTb.get(str);
/* 627 */       if (map == null) {
/* 628 */         throw new InstanceNotFoundException(paramObjectName.toString());
/*     */       }
/*     */ 
/*     */       
/* 632 */       if (map.remove(paramObjectName.getCanonicalKeyPropertyListString()) == null) {
/* 633 */         throw new InstanceNotFoundException(paramObjectName.toString());
/*     */       }
/*     */ 
/*     */       
/* 637 */       this.nbElements--;
/*     */ 
/*     */       
/* 640 */       if (map.isEmpty()) {
/* 641 */         this.domainTb.remove(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 647 */         if (str == this.domain) {
/* 648 */           this.domainTb.put(this.domain, new HashMap<>());
/*     */         }
/*     */       } 
/* 651 */       unregistering(paramRegistrationContext, paramObjectName);
/*     */     } finally {
/*     */       
/* 654 */       this.lock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getCount() {
/* 664 */     return Integer.valueOf(this.nbElements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultDomain() {
/* 674 */     return this.domain;
/*     */   }
/*     */   
/*     */   public static interface RegistrationContext {
/*     */     void registering();
/*     */     
/*     */     void unregistered();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/Repository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */