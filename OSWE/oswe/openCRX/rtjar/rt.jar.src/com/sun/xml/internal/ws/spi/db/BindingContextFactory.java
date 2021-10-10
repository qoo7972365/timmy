/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import com.sun.xml.internal.ws.db.glassfish.JAXBRIContextFactory;
/*     */ import com.sun.xml.internal.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
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
/*     */ public abstract class BindingContextFactory
/*     */ {
/*     */   public static final String DefaultDatabindingMode = "glassfish.jaxb";
/*  49 */   public static final String JAXB_CONTEXT_FACTORY_PROPERTY = BindingContextFactory.class.getName();
/*  50 */   public static final Logger LOGGER = Logger.getLogger(BindingContextFactory.class.getName());
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterator<BindingContextFactory> serviceIterator() {
/*  55 */     ServiceFinder<BindingContextFactory> sf = ServiceFinder.find(BindingContextFactory.class);
/*  56 */     final Iterator<BindingContextFactory> ibcf = sf.iterator();
/*     */     
/*  58 */     return new Iterator<BindingContextFactory>() {
/*     */         private BindingContextFactory bcf;
/*     */         
/*     */         public boolean hasNext() {
/*     */           while (true) {
/*     */             try {
/*  64 */               if (ibcf.hasNext()) {
/*  65 */                 this.bcf = ibcf.next();
/*  66 */                 return true;
/*     */               } 
/*  68 */               return false;
/*  69 */             } catch (ServiceConfigurationError e) {
/*  70 */               BindingContextFactory.LOGGER.warning("skipping factory: ServiceConfigurationError: " + e
/*  71 */                   .getMessage());
/*  72 */             } catch (NoClassDefFoundError ncdfe) {
/*  73 */               BindingContextFactory.LOGGER.fine("skipping factory: NoClassDefFoundError: " + ncdfe
/*  74 */                   .getMessage());
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         public BindingContextFactory next() {
/*  80 */           if (BindingContextFactory.LOGGER.isLoggable(Level.FINER))
/*  81 */             BindingContextFactory.LOGGER.finer("SPI found provider: " + this.bcf
/*  82 */                 .getClass().getName()); 
/*  83 */           return this.bcf;
/*     */         }
/*     */         
/*     */         public void remove() {
/*  87 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static List<BindingContextFactory> factories() {
/*  93 */     List<BindingContextFactory> factories = new ArrayList<>();
/*  94 */     Iterator<BindingContextFactory> ibcf = serviceIterator();
/*  95 */     while (ibcf.hasNext()) {
/*  96 */       factories.add(ibcf.next());
/*     */     }
/*     */     
/*  99 */     if (factories.isEmpty()) {
/* 100 */       if (LOGGER.isLoggable(Level.FINER))
/* 101 */         LOGGER.log(Level.FINER, "No SPI providers for BindingContextFactory found, adding: " + JAXBRIContextFactory.class
/* 102 */             .getName()); 
/* 103 */       factories.add(new JAXBRIContextFactory());
/*     */     } 
/* 105 */     return factories;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext newContext(JAXBContext paramJAXBContext);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext newContext(BindingInfo paramBindingInfo);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isFor(String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract BindingContext getContext(Marshaller paramMarshaller);
/*     */ 
/*     */   
/*     */   private static BindingContextFactory getFactory(String mode) {
/* 126 */     for (BindingContextFactory f : factories()) {
/* 127 */       if (f.isFor(mode))
/* 128 */         return f; 
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   public static BindingContext create(JAXBContext context) throws DatabindingException {
/* 134 */     return getJAXBFactory(context).newContext(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BindingContext create(BindingInfo bi) {
/* 140 */     String mode = bi.getDatabindingMode();
/* 141 */     if (mode != null) {
/* 142 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 143 */         LOGGER.log(Level.FINE, "Using SEI-configured databindng mode: " + mode);
/*     */       }
/* 145 */     } else if ((mode = System.getProperty("BindingContextFactory")) != null) {
/*     */ 
/*     */       
/* 148 */       bi.setDatabindingMode(mode);
/* 149 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 150 */         LOGGER.log(Level.FINE, "Using databindng: " + mode + " based on 'BindingContextFactory' System property");
/*     */       }
/* 152 */     } else if ((mode = System.getProperty(JAXB_CONTEXT_FACTORY_PROPERTY)) != null) {
/* 153 */       bi.setDatabindingMode(mode);
/* 154 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 155 */         LOGGER.log(Level.FINE, "Using databindng: " + mode + " based on '" + JAXB_CONTEXT_FACTORY_PROPERTY + "' System property");
/*     */       
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 161 */       Iterator<BindingContextFactory> iterator = factories().iterator(); if (iterator.hasNext()) { BindingContextFactory factory = iterator.next();
/* 162 */         if (LOGGER.isLoggable(Level.FINE)) {
/* 163 */           LOGGER.log(Level.FINE, "Using SPI-determined databindng mode: " + factory
/*     */               
/* 165 */               .getClass().getName());
/*     */         }
/* 167 */         return factory.newContext(bi); }
/*     */ 
/*     */ 
/*     */       
/* 171 */       LOGGER.log(Level.SEVERE, "No Binding Context Factories found.");
/* 172 */       throw new DatabindingException("No Binding Context Factories found.");
/*     */     } 
/* 174 */     BindingContextFactory f = getFactory(mode);
/* 175 */     if (f != null)
/* 176 */       return f.newContext(bi); 
/* 177 */     LOGGER.severe("Unknown Databinding mode: " + mode);
/* 178 */     throw new DatabindingException("Unknown Databinding mode: " + mode);
/*     */   }
/*     */   
/*     */   public static boolean isContextSupported(Object o) {
/* 182 */     if (o == null) return false; 
/* 183 */     String pkgName = o.getClass().getPackage().getName();
/* 184 */     for (BindingContextFactory f : factories()) { if (f.isFor(pkgName)) return true;  }
/* 185 */      return false;
/*     */   }
/*     */   
/*     */   static BindingContextFactory getJAXBFactory(Object o) {
/* 189 */     String pkgName = o.getClass().getPackage().getName();
/* 190 */     BindingContextFactory f = getFactory(pkgName);
/* 191 */     if (f != null) return f; 
/* 192 */     throw new DatabindingException("Unknown JAXBContext implementation: " + o.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BindingContext getBindingContext(Marshaller m) {
/* 200 */     return getJAXBFactory(m).getContext(m);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/BindingContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */