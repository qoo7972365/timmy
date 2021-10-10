/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ public class RepeatedElementBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   XMLBridge<T> delegate;
/*     */   CollectionHandler collectionHandler;
/*     */   
/*     */   public RepeatedElementBridge(TypeInfo typeInfo, XMLBridge<T> xb) {
/*  61 */     this.delegate = xb;
/*  62 */     this.collectionHandler = create(typeInfo);
/*     */   }
/*     */   
/*     */   public CollectionHandler collectionHandler() {
/*  66 */     return this.collectionHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  71 */     return this.delegate.context();
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/*  76 */     this.delegate.marshal(object, output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/*  81 */     this.delegate.marshal(object, output, nsContext, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/*  86 */     this.delegate.marshal(object, output);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/*  91 */     this.delegate.marshal(object, contentHandler, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, Result result) throws JAXBException {
/*  96 */     this.delegate.marshal(object, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 101 */     return this.delegate.unmarshal(in, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 106 */     return this.delegate.unmarshal(in, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(InputStream in) throws JAXBException {
/* 111 */     return this.delegate.unmarshal(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 116 */     return this.delegate.unmarshal(n, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/* 121 */     return this.delegate.getTypeInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 126 */     return this.delegate.supportOutputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   static class BaseCollectionHandler
/*     */     implements CollectionHandler
/*     */   {
/*     */     Class type;
/*     */ 
/*     */     
/*     */     BaseCollectionHandler(Class c) {
/* 137 */       this.type = c;
/*     */     } public int getSize(Object c) {
/* 139 */       return ((Collection)c).size();
/*     */     }
/*     */     public Object convert(List list) {
/*     */       try {
/* 143 */         Object o = this.type.newInstance();
/* 144 */         ((Collection)o).addAll(list);
/* 145 */         return o;
/* 146 */       } catch (Exception e) {
/* 147 */         e.printStackTrace();
/*     */         
/* 149 */         return list;
/*     */       } 
/*     */     } public Iterator iterator(Object c) {
/* 152 */       return ((Collection)c).iterator();
/*     */     } }
/*     */   
/* 155 */   static final CollectionHandler ListHandler = new BaseCollectionHandler(List.class) {
/*     */       public Object convert(List list) {
/* 157 */         return list;
/*     */       }
/*     */     };
/* 160 */   static final CollectionHandler HashSetHandler = new BaseCollectionHandler(HashSet.class) {
/*     */       public Object convert(List<?> list) {
/* 162 */         return new HashSet(list);
/*     */       }
/*     */     };
/*     */   public static CollectionHandler create(TypeInfo ti) {
/* 166 */     Class javaClass = (Class)ti.type;
/* 167 */     if (javaClass.isArray())
/* 168 */       return new ArrayHandler((Class)(ti.getItemType()).type); 
/* 169 */     if (List.class.equals(javaClass) || Collection.class.equals(javaClass))
/* 170 */       return ListHandler; 
/* 171 */     if (Set.class.equals(javaClass) || HashSet.class.equals(javaClass)) {
/* 172 */       return HashSetHandler;
/*     */     }
/* 174 */     return new BaseCollectionHandler(javaClass);
/*     */   }
/*     */   
/*     */   static class ArrayHandler implements CollectionHandler {
/*     */     Class componentClass;
/*     */     
/*     */     public ArrayHandler(Class component) {
/* 181 */       this.componentClass = component;
/*     */     }
/*     */     
/*     */     public int getSize(Object c) {
/* 185 */       return Array.getLength(c);
/*     */     }
/*     */     
/*     */     public Object convert(List list) {
/* 189 */       Object array = Array.newInstance(this.componentClass, list.size());
/* 190 */       for (int i = 0; i < list.size(); i++) {
/* 191 */         Array.set(array, i, list.get(i));
/*     */       }
/* 193 */       return array;
/*     */     }
/*     */     
/*     */     public Iterator iterator(final Object c) {
/* 197 */       return new Iterator() {
/* 198 */           int index = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 201 */             if (c == null || Array.getLength(c) == 0) {
/* 202 */               return false;
/*     */             }
/* 204 */             return (this.index != Array.getLength(c));
/*     */           }
/*     */           
/*     */           public Object next() throws NoSuchElementException {
/* 208 */             Object retVal = null;
/*     */             try {
/* 210 */               retVal = Array.get(c, this.index++);
/* 211 */             } catch (ArrayIndexOutOfBoundsException ex) {
/* 212 */               throw new NoSuchElementException();
/*     */             } 
/* 214 */             return retVal;
/*     */           }
/*     */           
/*     */           public void remove() {}
/*     */         };
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface CollectionHandler {
/*     */     int getSize(Object param1Object);
/*     */     
/*     */     Iterator iterator(Object param1Object);
/*     */     
/*     */     Object convert(List param1List);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/RepeatedElementBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */