/*      */ package javax.management.loading;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import java.io.Externalizable;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.net.URLClassLoader;
/*      */ import java.net.URLStreamHandlerFactory;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.InstanceAlreadyExistsException;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanRegistrationException;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.NotCompliantMBeanException;
/*      */ import javax.management.ObjectInstance;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.ReflectionException;
/*      */ import javax.management.ServiceNotFoundException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MLet
/*      */   extends URLClassLoader
/*      */   implements MLetMBean, MBeanRegistration, Externalizable
/*      */ {
/*      */   private static final long serialVersionUID = 3636148327800330130L;
/*  186 */   private MBeanServer server = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   private List<MLetContent> mletList = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String libraryDirectory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  207 */   private ObjectName mletObjectName = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  213 */   private URL[] myUrls = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient ClassLoaderRepository currentClr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean delegateToCLR;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  230 */   private Map<String, Class<?>> primitiveClasses = new HashMap<>(8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MLet() {
/*  264 */     this(new URL[0]);
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
/*      */   public MLet(URL[] paramArrayOfURL) {
/*  277 */     this(paramArrayOfURL, true);
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
/*      */   public MLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader) {
/*  292 */     this(paramArrayOfURL, paramClassLoader, true);
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
/*      */   public MLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader, URLStreamHandlerFactory paramURLStreamHandlerFactory) {
/*  310 */     this(paramArrayOfURL, paramClassLoader, paramURLStreamHandlerFactory, true);
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
/*      */   public MLet(URL[] paramArrayOfURL, boolean paramBoolean) {
/*  326 */     super(paramArrayOfURL); this.primitiveClasses.put(boolean.class.toString(), Boolean.class); this.primitiveClasses.put(char.class.toString(), Character.class); this.primitiveClasses.put(byte.class.toString(), Byte.class); this.primitiveClasses.put(short.class.toString(), Short.class); this.primitiveClasses.put(int.class.toString(), Integer.class); this.primitiveClasses.put(long.class.toString(), Long.class); this.primitiveClasses.put(float.class.toString(), Float.class); this.primitiveClasses.put(double.class.toString(), Double.class);
/*  327 */     init(paramBoolean);
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
/*      */   public MLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader, boolean paramBoolean) {
/*  345 */     super(paramArrayOfURL, paramClassLoader); this.primitiveClasses.put(boolean.class.toString(), Boolean.class); this.primitiveClasses.put(char.class.toString(), Character.class); this.primitiveClasses.put(byte.class.toString(), Byte.class); this.primitiveClasses.put(short.class.toString(), Short.class); this.primitiveClasses.put(int.class.toString(), Integer.class); this.primitiveClasses.put(long.class.toString(), Long.class); this.primitiveClasses.put(float.class.toString(), Float.class); this.primitiveClasses.put(double.class.toString(), Double.class);
/*  346 */     init(paramBoolean);
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
/*      */   public MLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader, URLStreamHandlerFactory paramURLStreamHandlerFactory, boolean paramBoolean) {
/*  368 */     super(paramArrayOfURL, paramClassLoader, paramURLStreamHandlerFactory); this.primitiveClasses.put(boolean.class.toString(), Boolean.class); this.primitiveClasses.put(char.class.toString(), Character.class); this.primitiveClasses.put(byte.class.toString(), Byte.class); this.primitiveClasses.put(short.class.toString(), Short.class); this.primitiveClasses.put(int.class.toString(), Integer.class); this.primitiveClasses.put(long.class.toString(), Long.class); this.primitiveClasses.put(float.class.toString(), Float.class); this.primitiveClasses.put(double.class.toString(), Double.class);
/*  369 */     init(paramBoolean);
/*      */   }
/*      */   
/*      */   private void init(boolean paramBoolean) {
/*  373 */     this.delegateToCLR = paramBoolean;
/*      */     
/*      */     try {
/*  376 */       this.libraryDirectory = System.getProperty("jmx.mlet.library.dir");
/*  377 */       if (this.libraryDirectory == null)
/*  378 */         this.libraryDirectory = getTmpDir(); 
/*  379 */     } catch (SecurityException securityException) {}
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
/*      */   public void addURL(URL paramURL) {
/*  400 */     if (!Arrays.<URL>asList(getURLs()).contains(paramURL)) {
/*  401 */       super.addURL(paramURL);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addURL(String paramString) throws ServiceNotFoundException {
/*      */     try {
/*  411 */       URL uRL = new URL(paramString);
/*  412 */       if (!Arrays.<URL>asList(getURLs()).contains(uRL))
/*  413 */         super.addURL(uRL); 
/*  414 */     } catch (MalformedURLException malformedURLException) {
/*  415 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/*  416 */         JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "addUrl", "Malformed URL: " + paramString, malformedURLException);
/*      */       }
/*      */       
/*  419 */       throw new ServiceNotFoundException("The specified URL is malformed");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URL[] getURLs() {
/*  429 */     return super.getURLs();
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
/*      */   public Set<Object> getMBeansFromURL(URL paramURL) throws ServiceNotFoundException {
/*  452 */     if (paramURL == null) {
/*  453 */       throw new ServiceNotFoundException("The specified URL is null");
/*      */     }
/*  455 */     return getMBeansFromURL(paramURL.toString());
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
/*      */   public Set<Object> getMBeansFromURL(String paramString) throws ServiceNotFoundException {
/*  484 */     String str = "getMBeansFromURL";
/*      */     
/*  486 */     if (this.server == null) {
/*  487 */       throw new IllegalStateException("This MLet MBean is not registered with an MBeanServer.");
/*      */     }
/*      */ 
/*      */     
/*  491 */     if (paramString == null) {
/*  492 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "URL is null");
/*      */       
/*  494 */       throw new ServiceNotFoundException("The specified URL is null");
/*      */     } 
/*  496 */     paramString = paramString.replace(File.separatorChar, '/');
/*      */     
/*  498 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/*  499 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "<URL = " + paramString + ">");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  505 */       MLetParser mLetParser = new MLetParser();
/*  506 */       this.mletList = mLetParser.parseURL(paramString);
/*  507 */     } catch (Exception exception) {
/*      */ 
/*      */       
/*  510 */       String str1 = "Problems while parsing URL [" + paramString + "], got exception [" + exception.toString() + "]";
/*  511 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, str1);
/*  512 */       throw (ServiceNotFoundException)EnvHelp.initCause(new ServiceNotFoundException(str1), exception);
/*      */     } 
/*      */ 
/*      */     
/*  516 */     if (this.mletList.size() == 0) {
/*  517 */       String str1 = "File " + paramString + " not found or MLET tag not defined in file";
/*      */       
/*  519 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, str1);
/*  520 */       throw new ServiceNotFoundException(str1);
/*      */     } 
/*      */ 
/*      */     
/*  524 */     HashSet<Exception> hashSet = new HashSet();
/*  525 */     for (MLetContent mLetContent : this.mletList) {
/*      */       ObjectInstance objectInstance;
/*  527 */       String str1 = mLetContent.getCode();
/*  528 */       if (str1 != null && 
/*  529 */         str1.endsWith(".class")) {
/*  530 */         str1 = str1.substring(0, str1.length() - 6);
/*      */       }
/*      */       
/*  533 */       String str2 = mLetContent.getName();
/*  534 */       URL uRL1 = mLetContent.getCodeBase();
/*  535 */       String str3 = mLetContent.getVersion();
/*  536 */       String str4 = mLetContent.getSerializedObject();
/*  537 */       String str5 = mLetContent.getJarFiles();
/*  538 */       URL uRL2 = mLetContent.getDocumentBase();
/*      */ 
/*      */       
/*  541 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  550 */         StringBuilder stringBuilder = (new StringBuilder()).append("\n\tMLET TAG     = ").append(mLetContent.getAttributes()).append("\n\tCODEBASE     = ").append(uRL1).append("\n\tARCHIVE      = ").append(str5).append("\n\tCODE         = ").append(str1).append("\n\tOBJECT       = ").append(str4).append("\n\tNAME         = ").append(str2).append("\n\tVERSION      = ").append(str3).append("\n\tDOCUMENT URL = ").append(uRL2);
/*  551 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, stringBuilder
/*  552 */             .toString());
/*      */       } 
/*      */ 
/*      */       
/*  556 */       StringTokenizer stringTokenizer = new StringTokenizer(str5, ",", false);
/*  557 */       while (stringTokenizer.hasMoreTokens()) {
/*  558 */         String str6 = stringTokenizer.nextToken().trim();
/*  559 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/*  560 */           JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "Load archive for codebase <" + uRL1 + ">, file <" + str6 + ">");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  571 */           uRL1 = check(str3, uRL1, str6, mLetContent);
/*  572 */         } catch (Exception exception) {
/*  573 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), str, "Got unexpected exception", exception);
/*      */           
/*  575 */           hashSet.add(exception);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  583 */           if (!Arrays.<URL>asList(getURLs()).contains(new URL(uRL1.toString() + str6))) {
/*  584 */             addURL(uRL1 + str6);
/*      */           }
/*  586 */         } catch (MalformedURLException malformedURLException) {}
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
/*  598 */       if (str1 != null && str4 != null) {
/*      */ 
/*      */ 
/*      */         
/*  602 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "CODE and OBJECT parameters cannot be specified at the same time in tag MLET");
/*  603 */         hashSet.add(new Error("CODE and OBJECT parameters cannot be specified at the same time in tag MLET"));
/*      */         continue;
/*      */       } 
/*  606 */       if (str1 == null && str4 == null) {
/*      */ 
/*      */ 
/*      */         
/*  610 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "Either CODE or OBJECT parameter must be specified in tag MLET");
/*  611 */         hashSet.add(new Error("Either CODE or OBJECT parameter must be specified in tag MLET"));
/*      */         continue;
/*      */       } 
/*      */       try {
/*  615 */         if (str1 != null) {
/*      */           
/*  617 */           List<String> list1 = mLetContent.getParameterTypes();
/*  618 */           List<String> list2 = mLetContent.getParameterValues();
/*  619 */           ArrayList<Object> arrayList = new ArrayList();
/*      */           
/*  621 */           for (byte b = 0; b < list1.size(); b++) {
/*  622 */             arrayList.add(constructParameter(list2.get(b), list1
/*  623 */                   .get(b)));
/*      */           }
/*  625 */           if (list1.isEmpty()) {
/*  626 */             if (str2 == null) {
/*  627 */               objectInstance = this.server.createMBean(str1, null, this.mletObjectName);
/*      */             } else {
/*      */               
/*  630 */               objectInstance = this.server.createMBean(str1, new ObjectName(str2), this.mletObjectName);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  635 */             Object[] arrayOfObject = arrayList.toArray();
/*  636 */             String[] arrayOfString = new String[list1.size()];
/*  637 */             list1.toArray(arrayOfString);
/*  638 */             if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/*  639 */               StringBuilder stringBuilder = new StringBuilder();
/*  640 */               for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*  641 */                 stringBuilder.append("\n\tSignature     = ")
/*  642 */                   .append(arrayOfString[b1])
/*  643 */                   .append("\t\nParams        = ")
/*  644 */                   .append(arrayOfObject[b1]);
/*      */               }
/*  646 */               JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class
/*  647 */                   .getName(), str, stringBuilder
/*  648 */                   .toString());
/*      */             } 
/*  650 */             if (str2 == null)
/*      */             {
/*  652 */               objectInstance = this.server.createMBean(str1, null, this.mletObjectName, arrayOfObject, arrayOfString);
/*      */             }
/*      */             else
/*      */             {
/*  656 */               objectInstance = this.server.createMBean(str1, new ObjectName(str2), this.mletObjectName, arrayOfObject, arrayOfString);
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/*  662 */           Object object = loadSerializedObject(uRL1, str4);
/*  663 */           if (str2 == null) {
/*  664 */             this.server.registerMBean(object, null);
/*      */           } else {
/*  666 */             this.server.registerMBean(object, new ObjectName(str2));
/*      */           } 
/*  668 */           objectInstance = new ObjectInstance(str2, object.getClass().getName());
/*      */         } 
/*  670 */       } catch (ReflectionException reflectionException) {
/*  671 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "ReflectionException", reflectionException);
/*      */         
/*  673 */         hashSet.add(reflectionException);
/*      */         continue;
/*  675 */       } catch (InstanceAlreadyExistsException instanceAlreadyExistsException) {
/*  676 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "InstanceAlreadyExistsException", instanceAlreadyExistsException);
/*      */         
/*  678 */         hashSet.add(instanceAlreadyExistsException);
/*      */         continue;
/*  680 */       } catch (MBeanRegistrationException mBeanRegistrationException) {
/*  681 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "MBeanRegistrationException", mBeanRegistrationException);
/*      */         
/*  683 */         hashSet.add(mBeanRegistrationException);
/*      */         continue;
/*  685 */       } catch (MBeanException mBeanException) {
/*  686 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "MBeanException", mBeanException);
/*      */         
/*  688 */         hashSet.add(mBeanException);
/*      */         continue;
/*  690 */       } catch (NotCompliantMBeanException notCompliantMBeanException) {
/*  691 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "NotCompliantMBeanException", notCompliantMBeanException);
/*      */         
/*  693 */         hashSet.add(notCompliantMBeanException);
/*      */         continue;
/*  695 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/*  696 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "InstanceNotFoundException", instanceNotFoundException);
/*      */         
/*  698 */         hashSet.add(instanceNotFoundException);
/*      */         continue;
/*  700 */       } catch (IOException iOException) {
/*  701 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "IOException", iOException);
/*      */         
/*  703 */         hashSet.add(iOException);
/*      */         continue;
/*  705 */       } catch (SecurityException securityException) {
/*  706 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "SecurityException", securityException);
/*      */         
/*  708 */         hashSet.add(securityException);
/*      */         continue;
/*  710 */       } catch (Exception exception) {
/*  711 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "Exception", exception);
/*      */         
/*  713 */         hashSet.add(exception);
/*      */         continue;
/*  715 */       } catch (Error error) {
/*  716 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str, "Error", error);
/*      */         
/*  718 */         hashSet.add(error);
/*      */         continue;
/*      */       } 
/*  721 */       hashSet.add(objectInstance);
/*      */     } 
/*  723 */     return (Set)hashSet;
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
/*      */   public synchronized String getLibraryDirectory() {
/*  738 */     return this.libraryDirectory;
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
/*      */   public synchronized void setLibraryDirectory(String paramString) {
/*  753 */     this.libraryDirectory = paramString;
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
/*      */   public ObjectName preRegister(MBeanServer paramMBeanServer, ObjectName paramObjectName) throws Exception {
/*  774 */     setMBeanServer(paramMBeanServer);
/*      */ 
/*      */     
/*  777 */     if (paramObjectName == null) {
/*  778 */       paramObjectName = new ObjectName(paramMBeanServer.getDefaultDomain() + ":" + "type=MLet");
/*      */     }
/*      */     
/*  781 */     this.mletObjectName = paramObjectName;
/*  782 */     return this.mletObjectName;
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
/*      */   public void postRegister(Boolean paramBoolean) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeExternal(ObjectOutput paramObjectOutput) throws IOException, UnsupportedOperationException {
/*  836 */     throw new UnsupportedOperationException("MLet.writeExternal");
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
/*      */   public void readExternal(ObjectInput paramObjectInput) throws IOException, ClassNotFoundException, UnsupportedOperationException {
/*  862 */     throw new UnsupportedOperationException("MLet.readExternal");
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
/*      */   public synchronized Class<?> loadClass(String paramString, ClassLoaderRepository paramClassLoaderRepository) throws ClassNotFoundException {
/*  891 */     ClassLoaderRepository classLoaderRepository = this.currentClr;
/*      */     try {
/*  893 */       this.currentClr = paramClassLoaderRepository;
/*  894 */       return loadClass(paramString);
/*      */     } finally {
/*  896 */       this.currentClr = classLoaderRepository;
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
/*      */   protected Class<?> findClass(String paramString) throws ClassNotFoundException {
/*  922 */     return findClass(paramString, this.currentClr);
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
/*      */   Class<?> findClass(String paramString, ClassLoaderRepository paramClassLoaderRepository) throws ClassNotFoundException {
/*  939 */     Class<?> clazz = null;
/*  940 */     JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), "findClass", paramString);
/*      */     
/*      */     try {
/*  943 */       clazz = super.findClass(paramString);
/*  944 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/*  945 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), "findClass", "Class " + paramString + " loaded through MLet classloader");
/*      */       
/*      */       }
/*      */     }
/*  949 */     catch (ClassNotFoundException classNotFoundException) {
/*      */       
/*  951 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/*  952 */         JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "findClass", "Class " + paramString + " not found locally");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  958 */     if (clazz == null && this.delegateToCLR && paramClassLoaderRepository != null) {
/*      */       
/*      */       try {
/*      */         
/*  962 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/*  963 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "findClass", "Class " + paramString + " : looking in CLR");
/*      */         }
/*      */ 
/*      */         
/*  967 */         clazz = paramClassLoaderRepository.loadClassBefore(this, paramString);
/*      */ 
/*      */         
/*  970 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/*  971 */           JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), "findClass", "Class " + paramString + " loaded through the default classloader repository");
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  976 */       catch (ClassNotFoundException classNotFoundException) {
/*      */         
/*  978 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/*  979 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "findClass", "Class " + paramString + " not found in CLR");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  985 */     if (clazz == null) {
/*  986 */       JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "findClass", "Failed to load class " + paramString);
/*      */       
/*  988 */       throw new ClassNotFoundException(paramString);
/*      */     } 
/*  990 */     return clazz;
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
/*      */   protected String findLibrary(String paramString) {
/* 1035 */     String str2 = "findLibrary";
/*      */ 
/*      */ 
/*      */     
/* 1039 */     String str3 = System.mapLibraryName(paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1044 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1045 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, "Search " + paramString + " in all JAR files");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1054 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1055 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, "loadLibraryAsResource(" + str3 + ")");
/*      */     }
/*      */     
/* 1058 */     String str1 = loadLibraryAsResource(str3);
/* 1059 */     if (str1 != null) {
/* 1060 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1061 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, str3 + " loaded, absolute path = " + str1);
/*      */       }
/*      */       
/* 1064 */       return str1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     str3 = removeSpace(System.getProperty("os.name")) + File.separator + removeSpace(System.getProperty("os.arch")) + File.separator + removeSpace(System.getProperty("os.version")) + File.separator + "lib" + File.separator + str3;
/*      */     
/* 1076 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1077 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, "loadLibraryAsResource(" + str3 + ")");
/*      */     }
/*      */ 
/*      */     
/* 1081 */     str1 = loadLibraryAsResource(str3);
/* 1082 */     if (str1 != null) {
/* 1083 */       if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1084 */         JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, str3 + " loaded, absolute path = " + str1);
/*      */       }
/*      */       
/* 1087 */       return str1;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1095 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, paramString + " not found in any JAR file");
/*      */       
/* 1097 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), str2, "Search " + paramString + " along the path specified as the java.library.path property");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1105 */     return null;
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
/*      */   private String getTmpDir() {
/* 1117 */     String str = System.getProperty("java.io.tmpdir");
/* 1118 */     if (str != null) return str;
/*      */ 
/*      */     
/* 1121 */     File file = null;
/*      */     
/*      */     try {
/* 1124 */       file = File.createTempFile("tmp", "jmx");
/* 1125 */       if (file == null) return null; 
/* 1126 */       File file1 = file.getParentFile();
/* 1127 */       if (file1 == null) return null; 
/* 1128 */       return file1.getAbsolutePath();
/* 1129 */     } catch (Exception exception) {
/* 1130 */       JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "getTmpDir", "Failed to determine system temporary dir");
/*      */       
/* 1132 */       return null;
/*      */     } finally {
/*      */       
/* 1135 */       if (file != null) {
/*      */         try {
/* 1137 */           boolean bool = file.delete();
/* 1138 */           if (!bool) {
/* 1139 */             JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "getTmpDir", "Failed to delete temp file");
/*      */           }
/*      */         }
/* 1142 */         catch (Exception exception) {
/* 1143 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "getTmpDir", "Failed to delete temporary file", exception);
/*      */         } 
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
/*      */   private synchronized String loadLibraryAsResource(String paramString) {
/*      */     try {
/* 1158 */       InputStream inputStream = getResourceAsStream(paramString
/* 1159 */           .replace(File.separatorChar, '/'));
/* 1160 */       if (inputStream != null) {
/*      */         try {
/* 1162 */           File file1 = new File(this.libraryDirectory);
/* 1163 */           file1.mkdirs();
/*      */ 
/*      */           
/* 1166 */           File file2 = Files.createTempFile(file1.toPath(), paramString + ".", null, (FileAttribute<?>[])new FileAttribute[0]).toFile();
/* 1167 */           file2.deleteOnExit();
/* 1168 */           FileOutputStream fileOutputStream = new FileOutputStream(file2);
/*      */           try {
/* 1170 */             byte[] arrayOfByte = new byte[4096];
/*      */             int i;
/* 1172 */             while ((i = inputStream.read(arrayOfByte)) >= 0) {
/* 1173 */               fileOutputStream.write(arrayOfByte, 0, i);
/*      */             }
/*      */           } finally {
/* 1176 */             fileOutputStream.close();
/*      */           } 
/* 1178 */           if (file2.exists()) {
/* 1179 */             return file2.getAbsolutePath();
/*      */           }
/*      */         } finally {
/* 1182 */           inputStream.close();
/*      */         } 
/*      */       }
/* 1185 */     } catch (Exception exception) {
/* 1186 */       JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "loadLibraryAsResource", "Failed to load library : " + paramString, exception);
/*      */ 
/*      */       
/* 1189 */       return null;
/*      */     } 
/* 1191 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String removeSpace(String paramString) {
/* 1199 */     return paramString.trim().replace(" ", "");
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
/*      */   protected URL check(String paramString1, URL paramURL, String paramString2, MLetContent paramMLetContent) throws Exception {
/* 1231 */     return paramURL;
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
/*      */   private Object loadSerializedObject(URL paramURL, String paramString) throws IOException, ClassNotFoundException {
/* 1248 */     if (paramString != null) {
/* 1249 */       paramString = paramString.replace(File.separatorChar, '/');
/*      */     }
/* 1251 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINER)) {
/* 1252 */       JmxProperties.MLET_LOGGER.logp(Level.FINER, MLet.class.getName(), "loadSerializedObject", paramURL
/* 1253 */           .toString() + paramString);
/*      */     }
/* 1255 */     InputStream inputStream = getResourceAsStream(paramString);
/* 1256 */     if (inputStream != null) {
/*      */       try {
/* 1258 */         MLetObjectInputStream mLetObjectInputStream = new MLetObjectInputStream(inputStream, this);
/* 1259 */         Object object = mLetObjectInputStream.readObject();
/* 1260 */         mLetObjectInputStream.close();
/* 1261 */         return object;
/* 1262 */       } catch (IOException iOException) {
/* 1263 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/* 1264 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "loadSerializedObject", "Exception while deserializing " + paramString, iOException);
/*      */         }
/*      */ 
/*      */         
/* 1268 */         throw iOException;
/* 1269 */       } catch (ClassNotFoundException classNotFoundException) {
/* 1270 */         if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/* 1271 */           JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "loadSerializedObject", "Exception while deserializing " + paramString, classNotFoundException);
/*      */         }
/*      */ 
/*      */         
/* 1275 */         throw classNotFoundException;
/*      */       } 
/*      */     }
/* 1278 */     if (JmxProperties.MLET_LOGGER.isLoggable(Level.FINEST)) {
/* 1279 */       JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "loadSerializedObject", "Error: File " + paramString + " containing serialized object not found");
/*      */     }
/*      */ 
/*      */     
/* 1283 */     throw new Error("File " + paramString + " containing serialized object not found");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object constructParameter(String paramString1, String paramString2) {
/* 1293 */     Class clazz = this.primitiveClasses.get(paramString2);
/* 1294 */     if (clazz != null) {
/*      */       
/*      */       try {
/* 1297 */         Constructor constructor = clazz.getConstructor(new Class[] { String.class });
/* 1298 */         Object[] arrayOfObject = new Object[1];
/* 1299 */         arrayOfObject[0] = paramString1;
/* 1300 */         return constructor.newInstance(arrayOfObject);
/*      */       }
/* 1302 */       catch (Exception exception) {
/* 1303 */         JmxProperties.MLET_LOGGER.logp(Level.FINEST, MLet.class.getName(), "constructParameter", "Got unexpected exception", exception);
/*      */       } 
/*      */     }
/*      */     
/* 1307 */     if (paramString2.compareTo("java.lang.Boolean") == 0)
/* 1308 */       return Boolean.valueOf(paramString1); 
/* 1309 */     if (paramString2.compareTo("java.lang.Byte") == 0)
/* 1310 */       return new Byte(paramString1); 
/* 1311 */     if (paramString2.compareTo("java.lang.Short") == 0)
/* 1312 */       return new Short(paramString1); 
/* 1313 */     if (paramString2.compareTo("java.lang.Long") == 0)
/* 1314 */       return new Long(paramString1); 
/* 1315 */     if (paramString2.compareTo("java.lang.Integer") == 0)
/* 1316 */       return new Integer(paramString1); 
/* 1317 */     if (paramString2.compareTo("java.lang.Float") == 0)
/* 1318 */       return new Float(paramString1); 
/* 1319 */     if (paramString2.compareTo("java.lang.Double") == 0)
/* 1320 */       return new Double(paramString1); 
/* 1321 */     if (paramString2.compareTo("java.lang.String") == 0) {
/* 1322 */       return paramString1;
/*      */     }
/* 1324 */     return paramString1;
/*      */   }
/*      */   
/*      */   private synchronized void setMBeanServer(final MBeanServer server) {
/* 1328 */     this.server = server;
/* 1329 */     PrivilegedAction<ClassLoaderRepository> privilegedAction = new PrivilegedAction<ClassLoaderRepository>()
/*      */       {
/*      */         public ClassLoaderRepository run() {
/* 1332 */           return server.getClassLoaderRepository();
/*      */         }
/*      */       };
/* 1335 */     this.currentClr = AccessController.<ClassLoaderRepository>doPrivileged(privilegedAction);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/MLet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */