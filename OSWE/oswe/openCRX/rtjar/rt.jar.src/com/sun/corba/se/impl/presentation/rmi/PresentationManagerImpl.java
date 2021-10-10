/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.graph.Graph;
/*     */ import com.sun.corba.se.impl.orbutil.graph.GraphImpl;
/*     */ import com.sun.corba.se.impl.orbutil.graph.Node;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
/*     */ import com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
/*     */ import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.rmi.CORBA.Tie;
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
/*     */ public final class PresentationManagerImpl
/*     */   implements PresentationManager
/*     */ {
/*     */   private Map classToClassData;
/*     */   private Map methodToDMM;
/*     */   private PresentationManager.StubFactoryFactory staticStubFactoryFactory;
/*     */   private PresentationManager.StubFactoryFactory dynamicStubFactoryFactory;
/*  65 */   private ORBUtilSystemException wrapper = null;
/*     */   
/*     */   private boolean useDynamicStubs;
/*     */   
/*     */   public PresentationManagerImpl(boolean paramBoolean) {
/*  70 */     this.useDynamicStubs = paramBoolean;
/*  71 */     this.wrapper = ORBUtilSystemException.get("rpc.presentation");
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.classToClassData = new HashMap<>();
/*  76 */     this.methodToDMM = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DynamicMethodMarshaller getDynamicMethodMarshaller(Method paramMethod) {
/*  86 */     if (paramMethod == null) {
/*  87 */       return null;
/*     */     }
/*     */     
/*  90 */     DynamicMethodMarshaller dynamicMethodMarshaller = (DynamicMethodMarshaller)this.methodToDMM.get(paramMethod);
/*  91 */     if (dynamicMethodMarshaller == null) {
/*  92 */       dynamicMethodMarshaller = new DynamicMethodMarshallerImpl(paramMethod);
/*  93 */       this.methodToDMM.put(paramMethod, dynamicMethodMarshaller);
/*     */     } 
/*     */     
/*  96 */     return dynamicMethodMarshaller;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized PresentationManager.ClassData getClassData(Class<?> paramClass) {
/* 101 */     PresentationManager.ClassData classData = (PresentationManager.ClassData)this.classToClassData.get(paramClass);
/* 102 */     if (classData == null) {
/* 103 */       classData = new ClassDataImpl(paramClass);
/* 104 */       this.classToClassData.put(paramClass, classData);
/*     */     } 
/*     */     
/* 107 */     return classData;
/*     */   }
/*     */   
/*     */   private class ClassDataImpl
/*     */     implements PresentationManager.ClassData
/*     */   {
/*     */     private Class cls;
/*     */     private IDLNameTranslator nameTranslator;
/*     */     private String[] typeIds;
/*     */     private PresentationManager.StubFactory sfactory;
/*     */     private InvocationHandlerFactory ihfactory;
/*     */     private Map dictionary;
/*     */     
/*     */     public ClassDataImpl(Class param1Class) {
/* 121 */       this.cls = param1Class;
/* 122 */       GraphImpl graphImpl = new GraphImpl();
/* 123 */       PresentationManagerImpl.NodeImpl nodeImpl = new PresentationManagerImpl.NodeImpl(param1Class);
/* 124 */       Set set = PresentationManagerImpl.this.getRootSet(param1Class, nodeImpl, (Graph)graphImpl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       Class[] arrayOfClass = PresentationManagerImpl.this.getInterfaces(set);
/* 131 */       this.nameTranslator = IDLNameTranslatorImpl.get(arrayOfClass);
/* 132 */       this.typeIds = PresentationManagerImpl.this.makeTypeIds(nodeImpl, (Graph)graphImpl, set);
/* 133 */       this.ihfactory = new InvocationHandlerFactoryImpl(PresentationManagerImpl.this, this);
/*     */       
/* 135 */       this.dictionary = new HashMap<>();
/*     */     }
/*     */ 
/*     */     
/*     */     public Class getMyClass() {
/* 140 */       return this.cls;
/*     */     }
/*     */ 
/*     */     
/*     */     public IDLNameTranslator getIDLNameTranslator() {
/* 145 */       return this.nameTranslator;
/*     */     }
/*     */ 
/*     */     
/*     */     public String[] getTypeIds() {
/* 150 */       return this.typeIds;
/*     */     }
/*     */ 
/*     */     
/*     */     public InvocationHandlerFactory getInvocationHandlerFactory() {
/* 155 */       return this.ihfactory;
/*     */     }
/*     */ 
/*     */     
/*     */     public Map getDictionary() {
/* 160 */       return this.dictionary;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PresentationManager.StubFactoryFactory getStubFactoryFactory(boolean paramBoolean) {
/* 167 */     if (paramBoolean) {
/* 168 */       return this.dynamicStubFactoryFactory;
/*     */     }
/* 170 */     return this.staticStubFactoryFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStubFactoryFactory(boolean paramBoolean, PresentationManager.StubFactoryFactory paramStubFactoryFactory) {
/* 176 */     if (paramBoolean) {
/* 177 */       this.dynamicStubFactoryFactory = paramStubFactoryFactory;
/*     */     } else {
/* 179 */       this.staticStubFactoryFactory = paramStubFactoryFactory;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Tie getTie() {
/* 184 */     return this.dynamicStubFactoryFactory.getTie(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useDynamicStubs() {
/* 189 */     return this.useDynamicStubs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set getRootSet(Class paramClass, NodeImpl paramNodeImpl, Graph paramGraph) {
/* 198 */     Set set = null;
/*     */     
/* 200 */     if (paramClass.isInterface()) {
/* 201 */       paramGraph.add(paramNodeImpl);
/* 202 */       set = paramGraph.getRoots();
/*     */     } else {
/*     */       
/* 205 */       Class clazz = paramClass;
/* 206 */       HashSet<NodeImpl> hashSet = new HashSet();
/* 207 */       while (clazz != null && !clazz.equals(Object.class)) {
/* 208 */         NodeImpl nodeImpl = new NodeImpl(clazz);
/* 209 */         paramGraph.add(nodeImpl);
/* 210 */         hashSet.add(nodeImpl);
/* 211 */         clazz = clazz.getSuperclass();
/*     */       } 
/*     */ 
/*     */       
/* 215 */       paramGraph.getRoots();
/*     */ 
/*     */       
/* 218 */       paramGraph.removeAll(hashSet);
/* 219 */       set = paramGraph.getRoots();
/*     */     } 
/*     */     
/* 222 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private Class[] getInterfaces(Set paramSet) {
/* 227 */     Class[] arrayOfClass = new Class[paramSet.size()];
/* 228 */     Iterator<NodeImpl> iterator = paramSet.iterator();
/* 229 */     byte b = 0;
/* 230 */     while (iterator.hasNext()) {
/* 231 */       NodeImpl nodeImpl = iterator.next();
/* 232 */       arrayOfClass[b++] = nodeImpl.getInterface();
/*     */     } 
/*     */     
/* 235 */     return arrayOfClass;
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] makeTypeIds(NodeImpl paramNodeImpl, Graph paramGraph, Set<?> paramSet) {
/* 240 */     HashSet hashSet = new HashSet((Collection<?>)paramGraph);
/* 241 */     hashSet.removeAll(paramSet);
/*     */ 
/*     */     
/* 244 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/* 246 */     if (paramSet.size() > 1)
/*     */     {
/*     */ 
/*     */       
/* 250 */       arrayList.add(paramNodeImpl.getTypeId());
/*     */     }
/*     */     
/* 253 */     addNodes(arrayList, paramSet);
/* 254 */     addNodes(arrayList, hashSet);
/*     */     
/* 256 */     return arrayList.<String>toArray(new String[arrayList.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNodes(List<String> paramList, Set paramSet) {
/* 261 */     Iterator<NodeImpl> iterator = paramSet.iterator();
/* 262 */     while (iterator.hasNext()) {
/* 263 */       NodeImpl nodeImpl = iterator.next();
/* 264 */       String str = nodeImpl.getTypeId();
/* 265 */       paramList.add(str);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class NodeImpl
/*     */     implements Node
/*     */   {
/*     */     private Class interf;
/*     */     
/*     */     public Class getInterface() {
/* 275 */       return this.interf;
/*     */     }
/*     */ 
/*     */     
/*     */     public NodeImpl(Class param1Class) {
/* 280 */       this.interf = param1Class;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTypeId() {
/* 285 */       return "RMI:" + this.interf.getName() + ":0000000000000000";
/*     */     }
/*     */ 
/*     */     
/*     */     public Set getChildren() {
/* 290 */       HashSet<NodeImpl> hashSet = new HashSet();
/* 291 */       Class[] arrayOfClass = this.interf.getInterfaces();
/* 292 */       for (byte b = 0; b < arrayOfClass.length; b++) {
/* 293 */         Class<?> clazz = arrayOfClass[b];
/* 294 */         if (Remote.class.isAssignableFrom(clazz) && 
/* 295 */           !Remote.class.equals(clazz)) {
/* 296 */           hashSet.add(new NodeImpl(clazz));
/*     */         }
/*     */       } 
/* 299 */       return hashSet;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 304 */       return "NodeImpl[" + this.interf + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 309 */       return this.interf.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 314 */       if (this == param1Object) {
/* 315 */         return true;
/*     */       }
/* 317 */       if (!(param1Object instanceof NodeImpl)) {
/* 318 */         return false;
/*     */       }
/* 320 */       NodeImpl nodeImpl = (NodeImpl)param1Object;
/*     */       
/* 322 */       return nodeImpl.interf.equals(this.interf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/PresentationManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */