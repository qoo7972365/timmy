/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.istack.internal.SAXException2;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.TODO;
/*     */ import com.sun.xml.internal.bind.v2.model.core.Adapter;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Patcher;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import java.util.TreeSet;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public abstract class Lister<BeanT, PropT, ItemT, PackT>
/*     */ {
/*     */   public static <BeanT, PropT, ItemT, PackT> Lister<BeanT, PropT, ItemT, PackT> create(Type fieldType, ID idness, Adapter<Type, Class<?>> adapter) {
/*     */     // Byte code:
/*     */     //   0: getstatic com/sun/xml/internal/bind/v2/runtime/reflect/Utils.REFLECTION_NAVIGATOR : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   3: aload_0
/*     */     //   4: invokeinterface erasure : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   9: checkcast java/lang/Class
/*     */     //   12: astore_3
/*     */     //   13: aload_3
/*     */     //   14: invokevirtual isArray : ()Z
/*     */     //   17: ifeq -> 36
/*     */     //   20: aload_3
/*     */     //   21: invokevirtual getComponentType : ()Ljava/lang/Class;
/*     */     //   24: astore #4
/*     */     //   26: aload #4
/*     */     //   28: invokestatic getArrayLister : (Ljava/lang/Class;)Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;
/*     */     //   31: astore #5
/*     */     //   33: goto -> 119
/*     */     //   36: ldc java/util/Collection
/*     */     //   38: aload_3
/*     */     //   39: invokevirtual isAssignableFrom : (Ljava/lang/Class;)Z
/*     */     //   42: ifeq -> 117
/*     */     //   45: getstatic com/sun/xml/internal/bind/v2/runtime/reflect/Utils.REFLECTION_NAVIGATOR : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   48: aload_0
/*     */     //   49: ldc java/util/Collection
/*     */     //   51: invokeinterface getBaseClass : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   56: checkcast java/lang/reflect/Type
/*     */     //   59: astore #6
/*     */     //   61: aload #6
/*     */     //   63: instanceof java/lang/reflect/ParameterizedType
/*     */     //   66: ifeq -> 97
/*     */     //   69: getstatic com/sun/xml/internal/bind/v2/runtime/reflect/Utils.REFLECTION_NAVIGATOR : Lcom/sun/xml/internal/bind/v2/model/nav/Navigator;
/*     */     //   72: aload #6
/*     */     //   74: checkcast java/lang/reflect/ParameterizedType
/*     */     //   77: invokeinterface getActualTypeArguments : ()[Ljava/lang/reflect/Type;
/*     */     //   82: iconst_0
/*     */     //   83: aaload
/*     */     //   84: invokeinterface erasure : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   89: checkcast java/lang/Class
/*     */     //   92: astore #4
/*     */     //   94: goto -> 101
/*     */     //   97: ldc java/lang/Object
/*     */     //   99: astore #4
/*     */     //   101: new com/sun/xml/internal/bind/v2/runtime/reflect/Lister$CollectionLister
/*     */     //   104: dup
/*     */     //   105: aload_3
/*     */     //   106: invokestatic getImplClass : (Ljava/lang/Class;)Ljava/lang/Class;
/*     */     //   109: invokespecial <init> : (Ljava/lang/Class;)V
/*     */     //   112: astore #5
/*     */     //   114: goto -> 119
/*     */     //   117: aconst_null
/*     */     //   118: areturn
/*     */     //   119: aload_1
/*     */     //   120: getstatic com/sun/xml/internal/bind/v2/model/core/ID.IDREF : Lcom/sun/xml/internal/bind/v2/model/core/ID;
/*     */     //   123: if_acmpne -> 139
/*     */     //   126: new com/sun/xml/internal/bind/v2/runtime/reflect/Lister$IDREFS
/*     */     //   129: dup
/*     */     //   130: aload #5
/*     */     //   132: aload #4
/*     */     //   134: invokespecial <init> : (Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;Ljava/lang/Class;)V
/*     */     //   137: astore #5
/*     */     //   139: aload_2
/*     */     //   140: ifnull -> 161
/*     */     //   143: new com/sun/xml/internal/bind/v2/runtime/reflect/AdaptedLister
/*     */     //   146: dup
/*     */     //   147: aload #5
/*     */     //   149: aload_2
/*     */     //   150: getfield adapterType : Ljava/lang/Object;
/*     */     //   153: checkcast java/lang/Class
/*     */     //   156: invokespecial <init> : (Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;Ljava/lang/Class;)V
/*     */     //   159: astore #5
/*     */     //   161: aload #5
/*     */     //   163: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #118	-> 0
/*     */     //   #122	-> 13
/*     */     //   #123	-> 20
/*     */     //   #124	-> 26
/*     */     //   #126	-> 36
/*     */     //   #127	-> 45
/*     */     //   #128	-> 61
/*     */     //   #129	-> 69
/*     */     //   #131	-> 97
/*     */     //   #132	-> 101
/*     */     //   #133	-> 114
/*     */     //   #134	-> 117
/*     */     //   #136	-> 119
/*     */     //   #137	-> 126
/*     */     //   #139	-> 139
/*     */     //   #140	-> 143
/*     */     //   #142	-> 161
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   26	10	4	itemType	Ljava/lang/Class;
/*     */     //   33	3	5	l	Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;
/*     */     //   94	3	4	itemType	Ljava/lang/Class;
/*     */     //   61	53	6	bt	Ljava/lang/reflect/Type;
/*     */     //   101	16	4	itemType	Ljava/lang/Class;
/*     */     //   114	3	5	l	Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;
/*     */     //   0	164	0	fieldType	Ljava/lang/reflect/Type;
/*     */     //   0	164	1	idness	Lcom/sun/xml/internal/bind/v2/model/core/ID;
/*     */     //   0	164	2	adapter	Lcom/sun/xml/internal/bind/v2/model/core/Adapter;
/*     */     //   13	151	3	rawType	Ljava/lang/Class;
/*     */     //   119	45	4	itemType	Ljava/lang/Class;
/*     */     //   119	45	5	l	Lcom/sun/xml/internal/bind/v2/runtime/reflect/Lister;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	164	2	adapter	Lcom/sun/xml/internal/bind/v2/model/core/Adapter<Ljava/lang/reflect/Type;Ljava/lang/Class;>;
/*     */   }
/*     */   
/*     */   private static Class getImplClass(Class<?> fieldType) {
/* 146 */     return ClassFactory.inferImplClass(fieldType, COLLECTION_IMPL_CLASSES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   private static final Map<Class, WeakReference<Lister>> arrayListerCache = Collections.synchronizedMap(new WeakHashMap<>());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Lister getArrayLister(Class<?> componentType) {
/* 159 */     Lister<Object, Object> l = null;
/* 160 */     if (componentType.isPrimitive()) {
/* 161 */       l = primitiveArrayListers.get(componentType);
/*     */     } else {
/* 163 */       WeakReference<Lister> wr = arrayListerCache.get(componentType);
/* 164 */       if (wr != null)
/* 165 */         l = wr.get(); 
/* 166 */       if (l == null) {
/* 167 */         l = (Lister<Object, Object>)new ArrayLister<>(componentType);
/* 168 */         arrayListerCache.put(componentType, new WeakReference<>(l));
/*     */       } 
/*     */     } 
/* 171 */     assert l != null;
/* 172 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ArrayLister<BeanT, ItemT>
/*     */     extends Lister<BeanT, ItemT[], ItemT, Pack<ItemT>>
/*     */   {
/*     */     private final Class<ItemT> itemType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayLister(Class<ItemT> itemType) {
/* 187 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ListIterator<ItemT> iterator(final ItemT[] objects, XMLSerializer context) {
/* 191 */       return new ListIterator<ItemT>() {
/* 192 */           int idx = 0;
/*     */           public boolean hasNext() {
/* 194 */             return (this.idx < objects.length);
/*     */           }
/*     */           
/*     */           public ItemT next() {
/* 198 */             return (ItemT)objects[this.idx++];
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public Lister.Pack startPacking(BeanT current, Accessor<BeanT, ItemT[]> acc) {
/* 204 */       return new Lister.Pack<>(this.itemType);
/*     */     }
/*     */     
/*     */     public void addToPack(Lister.Pack<ItemT> objects, ItemT o) {
/* 208 */       objects.add(o);
/*     */     }
/*     */     
/*     */     public void endPacking(Lister.Pack<ItemT> pack, BeanT bean, Accessor<BeanT, ItemT[]> acc) throws AccessorException {
/* 212 */       acc.set(bean, pack.build());
/*     */     }
/*     */     
/*     */     public void reset(BeanT o, Accessor<BeanT, ItemT[]> acc) throws AccessorException {
/* 216 */       acc.set(o, (ItemT[])Array.newInstance(this.itemType, 0));
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Pack<ItemT>
/*     */     extends ArrayList<ItemT> {
/*     */     private final Class<ItemT> itemType;
/*     */     
/*     */     public Pack(Class<ItemT> itemType) {
/* 225 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ItemT[] build() {
/* 229 */       return toArray((ItemT[])Array.newInstance(this.itemType, size()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   static final Map<Class, Lister> primitiveArrayListers = (Map)new HashMap<>();
/*     */ 
/*     */   
/*     */   static {
/* 240 */     PrimitiveArrayListerBoolean.register();
/* 241 */     PrimitiveArrayListerByte.register();
/* 242 */     PrimitiveArrayListerCharacter.register();
/* 243 */     PrimitiveArrayListerDouble.register();
/* 244 */     PrimitiveArrayListerFloat.register();
/* 245 */     PrimitiveArrayListerInteger.register();
/* 246 */     PrimitiveArrayListerLong.register();
/* 247 */     PrimitiveArrayListerShort.register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class CollectionLister<BeanT, T extends Collection>
/*     */     extends Lister<BeanT, T, Object, T>
/*     */   {
/*     */     private final Class<? extends T> implClass;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CollectionLister(Class<? extends T> implClass) {
/* 262 */       this.implClass = implClass;
/*     */     }
/*     */     
/*     */     public ListIterator iterator(T collection, XMLSerializer context) {
/* 266 */       final Iterator itr = collection.iterator();
/* 267 */       return new ListIterator() {
/*     */           public boolean hasNext() {
/* 269 */             return itr.hasNext();
/*     */           }
/*     */           public Object next() {
/* 272 */             return itr.next();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public T startPacking(BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/* 278 */       Collection collection = (Collection)acc.get(bean);
/* 279 */       if (collection == null) {
/* 280 */         collection = (Collection)ClassFactory.create(this.implClass);
/* 281 */         if (!acc.isAdapted())
/* 282 */           acc.set(bean, (T)collection); 
/*     */       } 
/* 284 */       collection.clear();
/* 285 */       return (T)collection;
/*     */     }
/*     */     
/*     */     public void addToPack(T collection, Object o) {
/* 289 */       collection.add(o);
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
/*     */     public void endPacking(T collection, BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/*     */       try {
/* 302 */         if (acc.isAdapted()) {
/* 303 */           acc.set(bean, collection);
/*     */         }
/* 305 */       } catch (AccessorException ae) {
/* 306 */         if (acc.isAdapted()) throw ae; 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void reset(BeanT bean, Accessor<BeanT, T> acc) throws AccessorException {
/* 311 */       Collection collection = (Collection)acc.get(bean);
/* 312 */       if (collection == null) {
/*     */         return;
/*     */       }
/* 315 */       collection.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class IDREFS<BeanT, PropT>
/*     */     extends Lister<BeanT, PropT, String, IDREFS<BeanT, PropT>.Pack>
/*     */   {
/*     */     private final Lister<BeanT, PropT, Object, Object> core;
/*     */     
/*     */     private final Class itemType;
/*     */ 
/*     */     
/*     */     public IDREFS(Lister<BeanT, PropT, Object, Object> core, Class itemType) {
/* 330 */       this.core = core;
/* 331 */       this.itemType = itemType;
/*     */     }
/*     */     
/*     */     public ListIterator<String> iterator(PropT prop, XMLSerializer context) {
/* 335 */       ListIterator i = this.core.iterator(prop, context);
/*     */       
/* 337 */       return new Lister.IDREFSIterator(i, context);
/*     */     }
/*     */     
/*     */     public Pack startPacking(BeanT bean, Accessor<BeanT, PropT> acc) {
/* 341 */       return new Pack(bean, acc);
/*     */     }
/*     */     
/*     */     public void addToPack(Pack pack, String item) {
/* 345 */       pack.add(item);
/*     */     }
/*     */ 
/*     */     
/*     */     public void endPacking(Pack pack, BeanT bean, Accessor<BeanT, PropT> acc) {}
/*     */     
/*     */     public void reset(BeanT bean, Accessor<BeanT, PropT> acc) throws AccessorException {
/* 352 */       this.core.reset(bean, acc);
/*     */     }
/*     */ 
/*     */     
/*     */     private class Pack
/*     */       implements Patcher
/*     */     {
/*     */       private final BeanT bean;
/* 360 */       private final List<String> idrefs = new ArrayList<>();
/*     */       private final UnmarshallingContext context;
/*     */       private final Accessor<BeanT, PropT> acc;
/*     */       private final LocatorEx location;
/*     */       
/*     */       public Pack(BeanT bean, Accessor<BeanT, PropT> acc) {
/* 366 */         this.bean = bean;
/* 367 */         this.acc = acc;
/* 368 */         this.context = UnmarshallingContext.getInstance();
/* 369 */         this.location = (LocatorEx)new LocatorEx.Snapshot(this.context.getLocator());
/* 370 */         this.context.addPatcher(this);
/*     */       }
/*     */       
/*     */       public void add(String item) {
/* 374 */         this.idrefs.add(item);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void run() throws SAXException {
/*     */         try {
/* 382 */           Object pack = Lister.IDREFS.this.core.startPacking(this.bean, this.acc);
/*     */           
/* 384 */           for (String id : this.idrefs) {
/* 385 */             Object t; Callable callable = this.context.getObjectFromId(id, Lister.IDREFS.this.itemType);
/*     */ 
/*     */             
/*     */             try {
/* 389 */               t = (callable != null) ? callable.call() : null;
/* 390 */             } catch (SAXException e) {
/* 391 */               throw e;
/* 392 */             } catch (Exception e) {
/* 393 */               throw new SAXException2(e);
/*     */             } 
/*     */             
/* 396 */             if (t == null) {
/* 397 */               this.context.errorUnresolvedIDREF(this.bean, id, this.location); continue;
/*     */             } 
/* 399 */             TODO.prototype();
/* 400 */             Lister.IDREFS.this.core.addToPack(pack, t);
/*     */           } 
/*     */ 
/*     */           
/* 404 */           Lister.IDREFS.this.core.endPacking(pack, this.bean, this.acc);
/* 405 */         } catch (AccessorException e) {
/* 406 */           this.context.handleError((Exception)e);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class IDREFSIterator
/*     */     implements ListIterator<String>
/*     */   {
/*     */     private final ListIterator i;
/*     */     
/*     */     private final XMLSerializer context;
/*     */     
/*     */     private Object last;
/*     */ 
/*     */     
/*     */     private IDREFSIterator(ListIterator i, XMLSerializer context) {
/* 425 */       this.i = i;
/* 426 */       this.context = context;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 430 */       return this.i.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object last() {
/* 437 */       return this.last;
/*     */     }
/*     */     
/*     */     public String next() throws SAXException, JAXBException {
/* 441 */       this.last = this.i.next();
/* 442 */       String id = this.context.grammar.getBeanInfo(this.last, true).getId(this.last, this.context);
/* 443 */       if (id == null) {
/* 444 */         this.context.errorMissingId(this.last);
/*     */       }
/* 446 */       return id;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, B, C, D> Lister<A, B, C, D> getErrorInstance() {
/* 455 */     return ERROR;
/*     */   }
/*     */   
/* 458 */   public static final Lister ERROR = new Lister() {
/*     */       public ListIterator iterator(Object o, XMLSerializer context) {
/* 460 */         return Lister.EMPTY_ITERATOR;
/*     */       }
/*     */       
/*     */       public Object startPacking(Object o, Accessor accessor) {
/* 464 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void addToPack(Object o, Object o1) {}
/*     */ 
/*     */       
/*     */       public void endPacking(Object o, Object o1, Accessor accessor) {}
/*     */ 
/*     */       
/*     */       public void reset(Object o, Accessor accessor) {}
/*     */     };
/*     */   
/* 477 */   private static final ListIterator EMPTY_ITERATOR = new ListIterator() {
/*     */       public boolean hasNext() {
/* 479 */         return false;
/*     */       }
/*     */       
/*     */       public Object next() {
/* 483 */         throw new IllegalStateException();
/*     */       }
/*     */     };
/*     */   
/* 487 */   private static final Class[] COLLECTION_IMPL_CLASSES = new Class[] { ArrayList.class, LinkedList.class, HashSet.class, TreeSet.class, Stack.class };
/*     */   
/*     */   public abstract ListIterator<ItemT> iterator(PropT paramPropT, XMLSerializer paramXMLSerializer);
/*     */   
/*     */   public abstract PackT startPacking(BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */   
/*     */   public abstract void addToPack(PackT paramPackT, ItemT paramItemT) throws AccessorException;
/*     */   
/*     */   public abstract void endPacking(PackT paramPackT, BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */   
/*     */   public abstract void reset(BeanT paramBeanT, Accessor<BeanT, PropT> paramAccessor) throws AccessorException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/Lister.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */