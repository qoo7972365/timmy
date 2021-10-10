/*      */ package com.sun.jndi.cosnaming;
/*      */ 
/*      */ import com.sun.jndi.toolkit.corba.CorbaUtils;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.Hashtable;
/*      */ import javax.naming.Binding;
/*      */ import javax.naming.CannotProceedException;
/*      */ import javax.naming.CommunicationException;
/*      */ import javax.naming.CompositeName;
/*      */ import javax.naming.ConfigurationException;
/*      */ import javax.naming.Context;
/*      */ import javax.naming.InvalidNameException;
/*      */ import javax.naming.Name;
/*      */ import javax.naming.NameClassPair;
/*      */ import javax.naming.NameNotFoundException;
/*      */ import javax.naming.NameParser;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.NotContextException;
/*      */ import javax.naming.RefAddr;
/*      */ import javax.naming.Reference;
/*      */ import javax.naming.spi.NamingManager;
/*      */ import javax.naming.spi.ResolveResult;
/*      */ import org.omg.CORBA.BAD_PARAM;
/*      */ import org.omg.CORBA.COMM_FAILURE;
/*      */ import org.omg.CORBA.INV_OBJREF;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.ORBPackage.InvalidName;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CosNaming.NameComponent;
/*      */ import org.omg.CosNaming.NamingContext;
/*      */ import org.omg.CosNaming.NamingContextHelper;
/*      */ import org.omg.CosNaming.NamingContextPackage.NotFound;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CNCtx
/*      */   implements Context
/*      */ {
/*      */   private static final boolean debug = false;
/*      */   private static ORB _defaultOrb;
/*      */   ORB _orb;
/*      */   public NamingContext _nc;
/*      */   
/*      */   private static synchronized ORB getDefaultOrb() {
/*   72 */     if (_defaultOrb == null) {
/*   73 */       _defaultOrb = CorbaUtils.getOrb(null, -1, new Hashtable<>());
/*      */     }
/*      */     
/*   76 */     return _defaultOrb;
/*      */   }
/*      */   
/*   79 */   private NameComponent[] _name = null;
/*      */   
/*      */   Hashtable<String, Object> _env;
/*   82 */   static final CNNameParser parser = new CNNameParser();
/*      */ 
/*      */   
/*      */   private static final String FED_PROP = "com.sun.jndi.cosnaming.federation";
/*      */ 
/*      */   
/*      */   boolean federation = false;
/*      */   
/*      */   public static final boolean trustURLCodebase;
/*      */ 
/*      */   
/*      */   static {
/*   94 */     PrivilegedAction<String> privilegedAction = () -> System.getProperty("com.sun.jndi.cosnaming.object.trustURLCodebase", "false");
/*      */     
/*   96 */     String str = AccessController.<String>doPrivileged(privilegedAction);
/*   97 */     trustURLCodebase = "true".equalsIgnoreCase(str);
/*      */   }
/*      */ 
/*      */   
/*  101 */   OrbReuseTracker orbTracker = null;
/*      */ 
/*      */ 
/*      */   
/*      */   int enumCount;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isCloseCalled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CNCtx(Hashtable<?, ?> paramHashtable) throws NamingException {
/*  115 */     if (paramHashtable != null) {
/*  116 */       paramHashtable = (Hashtable<?, ?>)paramHashtable.clone();
/*      */     }
/*  118 */     this._env = (Hashtable)paramHashtable;
/*  119 */     this.federation = "true".equals((paramHashtable != null) ? paramHashtable.get("com.sun.jndi.cosnaming.federation") : null);
/*  120 */     initOrbAndRootContext(paramHashtable);
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
/*      */   public static ResolveResult createUsingURL(String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  132 */     CNCtx cNCtx = new CNCtx();
/*  133 */     if (paramHashtable != null) {
/*  134 */       paramHashtable = (Hashtable<?, ?>)paramHashtable.clone();
/*      */     }
/*  136 */     cNCtx._env = (Hashtable)paramHashtable;
/*  137 */     String str = cNCtx.initUsingUrl((paramHashtable != null) ? (ORB)paramHashtable
/*      */         
/*  139 */         .get("java.naming.corba.orb") : null, paramString, paramHashtable);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  149 */     return new ResolveResult(cNCtx, parser.parse(str));
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
/*      */   CNCtx(ORB paramORB, OrbReuseTracker paramOrbReuseTracker, NamingContext paramNamingContext, Hashtable<String, Object> paramHashtable, NameComponent[] paramArrayOfNameComponent) throws NamingException {
/*  165 */     if (paramORB == null || paramNamingContext == null) {
/*  166 */       throw new ConfigurationException("Must supply ORB or NamingContext");
/*      */     }
/*  168 */     if (paramORB != null) {
/*  169 */       this._orb = paramORB;
/*      */     } else {
/*  171 */       this._orb = getDefaultOrb();
/*      */     } 
/*  173 */     this._nc = paramNamingContext;
/*  174 */     this._env = paramHashtable;
/*  175 */     this._name = paramArrayOfNameComponent;
/*  176 */     this.federation = "true".equals((paramHashtable != null) ? paramHashtable.get("com.sun.jndi.cosnaming.federation") : null);
/*      */   }
/*      */   
/*      */   NameComponent[] makeFullName(NameComponent[] paramArrayOfNameComponent) {
/*  180 */     if (this._name == null || this._name.length == 0) {
/*  181 */       return paramArrayOfNameComponent;
/*      */     }
/*  183 */     NameComponent[] arrayOfNameComponent = new NameComponent[this._name.length + paramArrayOfNameComponent.length];
/*      */ 
/*      */     
/*  186 */     System.arraycopy(this._name, 0, arrayOfNameComponent, 0, this._name.length);
/*      */ 
/*      */     
/*  189 */     System.arraycopy(paramArrayOfNameComponent, 0, arrayOfNameComponent, this._name.length, paramArrayOfNameComponent.length);
/*  190 */     return arrayOfNameComponent;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNameInNamespace() throws NamingException {
/*  195 */     if (this._name == null || this._name.length == 0) {
/*  196 */       return "";
/*      */     }
/*  198 */     return CNNameParser.cosNameToInsString(this._name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isCorbaUrl(String paramString) {
/*  206 */     return (paramString.startsWith("iiop://") || paramString
/*  207 */       .startsWith("iiopname://") || paramString
/*  208 */       .startsWith("corbaname:"));
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
/*      */   private void initOrbAndRootContext(Hashtable<?, ?> paramHashtable) throws NamingException {
/*  242 */     ORB oRB = null;
/*  243 */     String str1 = null;
/*      */     
/*  245 */     if (oRB == null && paramHashtable != null) {
/*  246 */       oRB = (ORB)paramHashtable.get("java.naming.corba.orb");
/*      */     }
/*      */     
/*  249 */     if (oRB == null) {
/*  250 */       oRB = getDefaultOrb();
/*      */     }
/*      */     
/*  253 */     String str2 = null;
/*  254 */     if (paramHashtable != null) {
/*  255 */       str2 = (String)paramHashtable.get("java.naming.provider.url");
/*      */     }
/*      */     
/*  258 */     if (str2 != null && !isCorbaUrl(str2)) {
/*      */ 
/*      */       
/*  261 */       str1 = getStringifiedIor(str2);
/*  262 */       setOrbAndRootContext(oRB, str1);
/*  263 */     } else if (str2 != null) {
/*      */ 
/*      */       
/*  266 */       String str = initUsingUrl(oRB, str2, paramHashtable);
/*      */ 
/*      */       
/*  269 */       if (str.length() > 0) {
/*  270 */         this._name = CNNameParser.nameToCosName(parser.parse(str));
/*      */         try {
/*  272 */           Object object = this._nc.resolve(this._name);
/*  273 */           this._nc = NamingContextHelper.narrow(object);
/*  274 */           if (this._nc == null) {
/*  275 */             throw new ConfigurationException(str + " does not name a NamingContext");
/*      */           }
/*      */         }
/*  278 */         catch (BAD_PARAM bAD_PARAM) {
/*  279 */           throw new ConfigurationException(str + " does not name a NamingContext");
/*      */         }
/*  281 */         catch (Exception exception) {
/*  282 */           throw ExceptionMapper.mapException(exception, this, this._name);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  290 */       setOrbAndRootContext(oRB, (String)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String initUsingUrl(ORB paramORB, String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  297 */     if (paramString.startsWith("iiop://") || paramString.startsWith("iiopname://")) {
/*  298 */       return initUsingIiopUrl(paramORB, paramString, paramHashtable);
/*      */     }
/*  300 */     return initUsingCorbanameUrl(paramORB, paramString, paramHashtable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String initUsingIiopUrl(ORB paramORB, String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  310 */     if (paramORB == null) {
/*  311 */       paramORB = getDefaultOrb();
/*      */     }
/*      */     try {
/*  314 */       IiopUrl iiopUrl = new IiopUrl(paramString);
/*      */       
/*  316 */       NamingException namingException = null;
/*      */       
/*  318 */       for (IiopUrl.Address address : iiopUrl.getAddresses()) {
/*      */ 
/*      */         
/*      */         try {
/*  322 */           String str = "corbaloc:iiop:" + address.host + ":" + address.port + "/NameService";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  328 */           Object object = paramORB.string_to_object(str);
/*  329 */           setOrbAndRootContext(paramORB, object);
/*  330 */           return iiopUrl.getStringName();
/*  331 */         } catch (Exception exception) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  340 */           setOrbAndRootContext(paramORB, (String)null);
/*  341 */           return iiopUrl.getStringName();
/*      */         }
/*  343 */         catch (NamingException namingException1) {
/*  344 */           namingException = namingException1;
/*      */         } 
/*      */       } 
/*  347 */       if (namingException != null) {
/*  348 */         throw namingException;
/*      */       }
/*  350 */       throw new ConfigurationException("Problem with URL: " + paramString);
/*      */     }
/*  352 */     catch (MalformedURLException malformedURLException) {
/*  353 */       throw new ConfigurationException(malformedURLException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String initUsingCorbanameUrl(ORB paramORB, String paramString, Hashtable<?, ?> paramHashtable) throws NamingException {
/*  363 */     if (paramORB == null) {
/*  364 */       paramORB = getDefaultOrb();
/*      */     }
/*      */     try {
/*  367 */       CorbanameUrl corbanameUrl = new CorbanameUrl(paramString);
/*      */       
/*  369 */       String str1 = corbanameUrl.getLocation();
/*  370 */       String str2 = corbanameUrl.getStringName();
/*      */       
/*  372 */       setOrbAndRootContext(paramORB, str1);
/*      */       
/*  374 */       return corbanameUrl.getStringName();
/*  375 */     } catch (MalformedURLException malformedURLException) {
/*  376 */       throw new ConfigurationException(malformedURLException.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOrbAndRootContext(ORB paramORB, String paramString) throws NamingException {
/*  382 */     this._orb = paramORB;
/*      */     try {
/*      */       Object object;
/*  385 */       if (paramString != null) {
/*      */ 
/*      */ 
/*      */         
/*  389 */         object = this._orb.string_to_object(paramString);
/*      */       } else {
/*  391 */         object = this._orb.resolve_initial_references("NameService");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  396 */       this._nc = NamingContextHelper.narrow(object);
/*  397 */       if (this._nc == null) {
/*  398 */         if (paramString != null) {
/*  399 */           throw new ConfigurationException("Cannot convert IOR to a NamingContext: " + paramString);
/*      */         }
/*      */         
/*  402 */         throw new ConfigurationException("ORB.resolve_initial_references(\"NameService\") does not return a NamingContext");
/*      */       }
/*      */     
/*      */     }
/*  406 */     catch (InvalidName invalidName) {
/*  407 */       ConfigurationException configurationException = new ConfigurationException("COS Name Service not registered with ORB under the name 'NameService'");
/*      */ 
/*      */       
/*  410 */       configurationException.setRootCause((Throwable)invalidName);
/*  411 */       throw configurationException;
/*  412 */     } catch (COMM_FAILURE cOMM_FAILURE) {
/*  413 */       CommunicationException communicationException = new CommunicationException("Cannot connect to ORB");
/*      */       
/*  415 */       communicationException.setRootCause((Throwable)cOMM_FAILURE);
/*  416 */       throw communicationException;
/*  417 */     } catch (BAD_PARAM bAD_PARAM) {
/*  418 */       ConfigurationException configurationException = new ConfigurationException("Invalid URL or IOR: " + paramString);
/*      */       
/*  420 */       configurationException.setRootCause((Throwable)bAD_PARAM);
/*  421 */       throw configurationException;
/*  422 */     } catch (INV_OBJREF iNV_OBJREF) {
/*  423 */       ConfigurationException configurationException = new ConfigurationException("Invalid object reference: " + paramString);
/*      */       
/*  425 */       configurationException.setRootCause((Throwable)iNV_OBJREF);
/*  426 */       throw configurationException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOrbAndRootContext(ORB paramORB, Object paramObject) throws NamingException {
/*  432 */     this._orb = paramORB;
/*      */     try {
/*  434 */       this._nc = NamingContextHelper.narrow(paramObject);
/*  435 */       if (this._nc == null) {
/*  436 */         throw new ConfigurationException("Cannot convert object reference to NamingContext: " + paramObject);
/*      */       }
/*      */     }
/*  439 */     catch (COMM_FAILURE cOMM_FAILURE) {
/*  440 */       CommunicationException communicationException = new CommunicationException("Cannot connect to ORB");
/*      */       
/*  442 */       communicationException.setRootCause((Throwable)cOMM_FAILURE);
/*  443 */       throw communicationException;
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getStringifiedIor(String paramString) throws NamingException {
/*  448 */     if (paramString.startsWith("IOR:") || paramString.startsWith("corbaloc:")) {
/*  449 */       return paramString;
/*      */     }
/*  451 */     InputStream inputStream = null;
/*      */     try {
/*  453 */       URL uRL = new URL(paramString);
/*  454 */       inputStream = uRL.openStream();
/*  455 */       if (inputStream != null) {
/*  456 */         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "8859_1"));
/*      */         
/*      */         String str;
/*  459 */         while ((str = bufferedReader.readLine()) != null) {
/*  460 */           if (str.startsWith("IOR:")) {
/*  461 */             return str;
/*      */           }
/*      */         } 
/*      */       } 
/*  465 */     } catch (IOException iOException) {
/*  466 */       ConfigurationException configurationException = new ConfigurationException("Invalid URL: " + paramString);
/*      */       
/*  468 */       configurationException.setRootCause(iOException);
/*  469 */       throw configurationException;
/*      */     } finally {
/*      */       try {
/*  472 */         if (inputStream != null) {
/*  473 */           inputStream.close();
/*      */         }
/*  475 */       } catch (IOException iOException) {
/*  476 */         ConfigurationException configurationException = new ConfigurationException("Invalid URL: " + paramString);
/*      */         
/*  478 */         configurationException.setRootCause(iOException);
/*  479 */         throw configurationException;
/*      */       } 
/*      */     } 
/*  482 */     throw new ConfigurationException(paramString + " does not contain an IOR");
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
/*      */   Object callResolve(NameComponent[] paramArrayOfNameComponent) throws NamingException {
/*      */     try {
/*  501 */       Object object = this._nc.resolve(paramArrayOfNameComponent);
/*      */       
/*      */       try {
/*  504 */         NamingContext namingContext = NamingContextHelper.narrow(object);
/*  505 */         if (namingContext != null) {
/*  506 */           return new CNCtx(this._orb, this.orbTracker, namingContext, this._env, 
/*  507 */               makeFullName(paramArrayOfNameComponent));
/*      */         }
/*  509 */         return object;
/*      */       }
/*  511 */       catch (SystemException systemException) {
/*  512 */         return object;
/*      */       } 
/*  514 */     } catch (Exception exception) {
/*  515 */       throw ExceptionMapper.mapException(exception, this, paramArrayOfNameComponent);
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
/*      */   public Object lookup(String paramString) throws NamingException {
/*  532 */     return lookup(new CompositeName(paramString));
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
/*      */   public Object lookup(Name paramName) throws NamingException {
/*  546 */     if (this._nc == null) {
/*  547 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*  549 */     if (paramName.size() == 0)
/*  550 */       return this; 
/*  551 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*  552 */     Object object = null;
/*      */     
/*      */     try {
/*  555 */       object = callResolve(arrayOfNameComponent);
/*      */       
/*      */       try {
/*  558 */         if (CorbaUtils.isObjectFactoryTrusted(object)) {
/*  559 */           object = NamingManager.getObjectInstance(object, paramName, this, this._env);
/*      */         }
/*      */       }
/*  562 */       catch (NamingException namingException) {
/*  563 */         throw namingException;
/*  564 */       } catch (Exception exception) {
/*  565 */         NamingException namingException = new NamingException("problem generating object using object factory");
/*      */         
/*  567 */         namingException.setRootCause(exception);
/*  568 */         throw namingException;
/*      */       } 
/*  570 */     } catch (CannotProceedException cannotProceedException) {
/*  571 */       Context context = getContinuationContext(cannotProceedException);
/*  572 */       return context.lookup(cannotProceedException.getRemainingName());
/*      */     } 
/*  574 */     return object;
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
/*      */   private void callBindOrRebind(NameComponent[] paramArrayOfNameComponent, Name paramName, Object paramObject, boolean paramBoolean) throws NamingException {
/*  595 */     if (this._nc == null) {
/*  596 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*      */     
/*      */     try {
/*  600 */       paramObject = NamingManager.getStateToBind(paramObject, paramName, this, this._env);
/*      */       
/*  602 */       if (paramObject instanceof CNCtx)
/*      */       {
/*  604 */         paramObject = ((CNCtx)paramObject)._nc;
/*      */       }
/*      */       
/*  607 */       if (paramObject instanceof NamingContext) {
/*      */         
/*  609 */         NamingContext namingContext = NamingContextHelper.narrow((Object)paramObject);
/*  610 */         if (paramBoolean) {
/*  611 */           this._nc.rebind_context(paramArrayOfNameComponent, namingContext);
/*      */         } else {
/*  613 */           this._nc.bind_context(paramArrayOfNameComponent, namingContext);
/*      */         } 
/*  615 */       } else if (paramObject instanceof Object) {
/*  616 */         if (paramBoolean) {
/*  617 */           this._nc.rebind(paramArrayOfNameComponent, (Object)paramObject);
/*      */         } else {
/*  619 */           this._nc.bind(paramArrayOfNameComponent, (Object)paramObject);
/*      */         } 
/*      */       } else {
/*  622 */         throw new IllegalArgumentException("Only instances of org.omg.CORBA.Object can be bound");
/*      */       } 
/*  624 */     } catch (BAD_PARAM bAD_PARAM) {
/*      */       
/*  626 */       NotContextException notContextException = new NotContextException(paramName.toString());
/*  627 */       notContextException.setRootCause((Throwable)bAD_PARAM);
/*  628 */       throw notContextException;
/*  629 */     } catch (Exception exception) {
/*  630 */       throw ExceptionMapper.mapException(exception, this, paramArrayOfNameComponent);
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
/*      */   public void bind(Name paramName, Object paramObject) throws NamingException {
/*  645 */     if (paramName.size() == 0) {
/*  646 */       throw new InvalidNameException("Name is empty");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  652 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*      */     
/*      */     try {
/*  655 */       callBindOrRebind(arrayOfNameComponent, paramName, paramObject, false);
/*  656 */     } catch (CannotProceedException cannotProceedException) {
/*  657 */       Context context = getContinuationContext(cannotProceedException);
/*  658 */       context.bind(cannotProceedException.getRemainingName(), paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Context getContinuationContext(CannotProceedException paramCannotProceedException) throws NamingException {
/*      */     try {
/*  666 */       return NamingManager.getContinuationContext(paramCannotProceedException);
/*  667 */     } catch (CannotProceedException cannotProceedException) {
/*  668 */       Object object = cannotProceedException.getResolvedObj();
/*  669 */       if (object instanceof Reference) {
/*  670 */         Reference reference = (Reference)object;
/*  671 */         RefAddr refAddr = reference.get("nns");
/*  672 */         if (refAddr.getContent() instanceof Context) {
/*  673 */           NameNotFoundException nameNotFoundException = new NameNotFoundException("No object reference bound for specified name");
/*      */           
/*  675 */           nameNotFoundException.setRootCause(paramCannotProceedException.getRootCause());
/*  676 */           nameNotFoundException.setRemainingName(paramCannotProceedException.getRemainingName());
/*  677 */           throw nameNotFoundException;
/*      */         } 
/*      */       } 
/*  680 */       throw cannotProceedException;
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
/*      */   public void bind(String paramString, Object paramObject) throws NamingException {
/*  693 */     bind(new CompositeName(paramString), paramObject);
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
/*      */   public void rebind(Name paramName, Object paramObject) throws NamingException {
/*  708 */     if (paramName.size() == 0) {
/*  709 */       throw new InvalidNameException("Name is empty");
/*      */     }
/*  711 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*      */     try {
/*  713 */       callBindOrRebind(arrayOfNameComponent, paramName, paramObject, true);
/*  714 */     } catch (CannotProceedException cannotProceedException) {
/*  715 */       Context context = getContinuationContext(cannotProceedException);
/*  716 */       context.rebind(cannotProceedException.getRemainingName(), paramObject);
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
/*      */   public void rebind(String paramString, Object paramObject) throws NamingException {
/*  730 */     rebind(new CompositeName(paramString), paramObject);
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
/*      */   private void callUnbind(NameComponent[] paramArrayOfNameComponent) throws NamingException {
/*  743 */     if (this._nc == null) {
/*  744 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*      */     try {
/*  747 */       this._nc.unbind(paramArrayOfNameComponent);
/*  748 */     } catch (NotFound notFound) {
/*      */ 
/*      */ 
/*      */       
/*  752 */       if (!leafNotFound(notFound, paramArrayOfNameComponent[paramArrayOfNameComponent.length - 1]))
/*      */       {
/*      */         
/*  755 */         throw ExceptionMapper.mapException(notFound, this, paramArrayOfNameComponent);
/*      */       }
/*  757 */     } catch (Exception exception) {
/*  758 */       throw ExceptionMapper.mapException(exception, this, paramArrayOfNameComponent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean leafNotFound(NotFound paramNotFound, NameComponent paramNameComponent) {
/*      */     NameComponent nameComponent;
/*  770 */     return (paramNotFound.why.value() == 0 && paramNotFound.rest_of_name.length == 1 && (nameComponent = paramNotFound.rest_of_name[0]).id
/*      */       
/*  772 */       .equals(paramNameComponent.id) && (nameComponent.kind == paramNameComponent.kind || (nameComponent.kind != null && nameComponent.kind
/*      */       
/*  774 */       .equals(paramNameComponent.kind))));
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
/*      */   public void unbind(String paramString) throws NamingException {
/*  787 */     unbind(new CompositeName(paramString));
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
/*      */   public void unbind(Name paramName) throws NamingException {
/*  799 */     if (paramName.size() == 0)
/*  800 */       throw new InvalidNameException("Name is empty"); 
/*  801 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*      */     try {
/*  803 */       callUnbind(arrayOfNameComponent);
/*  804 */     } catch (CannotProceedException cannotProceedException) {
/*  805 */       Context context = getContinuationContext(cannotProceedException);
/*  806 */       context.unbind(cannotProceedException.getRemainingName());
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
/*      */   public void rename(String paramString1, String paramString2) throws NamingException {
/*  820 */     rename(new CompositeName(paramString1), new CompositeName(paramString2));
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
/*      */   public void rename(Name paramName1, Name paramName2) throws NamingException {
/*  833 */     if (this._nc == null) {
/*  834 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*  836 */     if (paramName1.size() == 0 || paramName2.size() == 0)
/*  837 */       throw new InvalidNameException("One or both names empty"); 
/*  838 */     Object object = lookup(paramName1);
/*  839 */     bind(paramName2, object);
/*  840 */     unbind(paramName1);
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
/*      */   public NamingEnumeration<NameClassPair> list(String paramString) throws NamingException {
/*  852 */     return list(new CompositeName(paramString));
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
/*      */   public NamingEnumeration<NameClassPair> list(Name paramName) throws NamingException {
/*  865 */     return (NamingEnumeration)listBindings(paramName);
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
/*      */   public NamingEnumeration<Binding> listBindings(String paramString) throws NamingException {
/*  877 */     return listBindings(new CompositeName(paramString));
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
/*      */   public NamingEnumeration<Binding> listBindings(Name paramName) throws NamingException {
/*  889 */     if (this._nc == null) {
/*  890 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*  892 */     if (paramName.size() > 0) {
/*      */       try {
/*  894 */         Object object = lookup(paramName);
/*  895 */         if (object instanceof CNCtx) {
/*  896 */           return new CNBindingEnumeration((CNCtx)object, true, this._env);
/*      */         }
/*      */         
/*  899 */         throw new NotContextException(paramName.toString());
/*      */       }
/*  901 */       catch (NamingException namingException) {
/*  902 */         throw namingException;
/*  903 */       } catch (BAD_PARAM bAD_PARAM) {
/*      */         
/*  905 */         NotContextException notContextException = new NotContextException(paramName.toString());
/*  906 */         notContextException.setRootCause((Throwable)bAD_PARAM);
/*  907 */         throw notContextException;
/*      */       } 
/*      */     }
/*  910 */     return new CNBindingEnumeration(this, false, this._env);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void callDestroy(NamingContext paramNamingContext) throws NamingException {
/*  920 */     if (this._nc == null) {
/*  921 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*      */     try {
/*  924 */       paramNamingContext.destroy();
/*  925 */     } catch (Exception exception) {
/*  926 */       throw ExceptionMapper.mapException(exception, this, null);
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
/*      */   public void destroySubcontext(String paramString) throws NamingException {
/*  938 */     destroySubcontext(new CompositeName(paramString));
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
/*      */   public void destroySubcontext(Name paramName) throws NamingException {
/*  950 */     if (this._nc == null) {
/*  951 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*  953 */     NamingContext namingContext = this._nc;
/*  954 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*  955 */     if (paramName.size() > 0) {
/*      */       
/*      */       try {
/*  958 */         Context context = (Context)callResolve(arrayOfNameComponent);
/*  959 */         CNCtx cNCtx = (CNCtx)context;
/*  960 */         namingContext = cNCtx._nc;
/*  961 */         cNCtx.close();
/*  962 */       } catch (ClassCastException classCastException) {
/*  963 */         throw new NotContextException(paramName.toString());
/*  964 */       } catch (CannotProceedException cannotProceedException) {
/*  965 */         Context context = getContinuationContext(cannotProceedException);
/*  966 */         context.destroySubcontext(cannotProceedException.getRemainingName());
/*      */         return;
/*  968 */       } catch (NameNotFoundException nameNotFoundException) {
/*      */ 
/*      */ 
/*      */         
/*  972 */         if (nameNotFoundException.getRootCause() instanceof NotFound && 
/*  973 */           leafNotFound((NotFound)nameNotFoundException.getRootCause(), arrayOfNameComponent[arrayOfNameComponent.length - 1])) {
/*      */           return;
/*      */         }
/*      */         
/*  977 */         throw nameNotFoundException;
/*  978 */       } catch (NamingException namingException) {
/*  979 */         throw namingException;
/*      */       } 
/*      */     }
/*  982 */     callDestroy(namingContext);
/*  983 */     callUnbind(arrayOfNameComponent);
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
/*      */   private Context callBindNewContext(NameComponent[] paramArrayOfNameComponent) throws NamingException {
/*  997 */     if (this._nc == null) {
/*  998 */       throw new ConfigurationException("Context does not have a corresponding NamingContext");
/*      */     }
/*      */     try {
/* 1001 */       NamingContext namingContext = this._nc.bind_new_context(paramArrayOfNameComponent);
/* 1002 */       return new CNCtx(this._orb, this.orbTracker, namingContext, this._env, 
/* 1003 */           makeFullName(paramArrayOfNameComponent));
/* 1004 */     } catch (Exception exception) {
/* 1005 */       throw ExceptionMapper.mapException(exception, this, paramArrayOfNameComponent);
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
/*      */   public Context createSubcontext(String paramString) throws NamingException {
/* 1018 */     return createSubcontext(new CompositeName(paramString));
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
/*      */   public Context createSubcontext(Name paramName) throws NamingException {
/* 1030 */     if (paramName.size() == 0)
/* 1031 */       throw new InvalidNameException("Name is empty"); 
/* 1032 */     NameComponent[] arrayOfNameComponent = CNNameParser.nameToCosName(paramName);
/*      */     try {
/* 1034 */       return callBindNewContext(arrayOfNameComponent);
/* 1035 */     } catch (CannotProceedException cannotProceedException) {
/* 1036 */       Context context = getContinuationContext(cannotProceedException);
/* 1037 */       return context.createSubcontext(cannotProceedException.getRemainingName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object lookupLink(String paramString) throws NamingException {
/* 1048 */     return lookupLink(new CompositeName(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object lookupLink(Name paramName) throws NamingException {
/* 1058 */     return lookup(paramName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameParser getNameParser(String paramString) throws NamingException {
/* 1069 */     return parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameParser getNameParser(Name paramName) throws NamingException {
/* 1080 */     return parser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hashtable<String, Object> getEnvironment() throws NamingException {
/* 1089 */     if (this._env == null) {
/* 1090 */       return new Hashtable<>(5, 0.75F);
/*      */     }
/* 1092 */     return (Hashtable<String, Object>)this._env.clone();
/*      */   }
/*      */ 
/*      */   
/*      */   public String composeName(String paramString1, String paramString2) throws NamingException {
/* 1097 */     return composeName(new CompositeName(paramString1), new CompositeName(paramString2))
/* 1098 */       .toString();
/*      */   }
/*      */   
/*      */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 1102 */     Name name = (Name)paramName2.clone();
/* 1103 */     return name.addAll(paramName1);
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
/*      */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 1118 */     if (this._env == null) {
/* 1119 */       this._env = new Hashtable<>(7, 0.75F);
/*      */     } else {
/*      */       
/* 1122 */       this._env = (Hashtable<String, Object>)this._env.clone();
/*      */     } 
/*      */     
/* 1125 */     return this._env.put(paramString, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 1132 */     if (this._env != null && this._env.get(paramString) != null) {
/*      */       
/* 1134 */       this._env = (Hashtable<String, Object>)this._env.clone();
/* 1135 */       return this._env.remove(paramString);
/*      */     } 
/* 1137 */     return null;
/*      */   }
/*      */   
/*      */   public synchronized void incEnumCount() {
/* 1141 */     this.enumCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void decEnumCount() throws NamingException {
/* 1149 */     this.enumCount--;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     if (this.enumCount == 0 && this.isCloseCalled) {
/* 1155 */       close();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized void close() throws NamingException {
/* 1161 */     if (this.enumCount > 0) {
/* 1162 */       this.isCloseCalled = true;
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*      */     try {
/* 1173 */       close();
/* 1174 */     } catch (NamingException namingException) {}
/*      */   }
/*      */   
/*      */   private CNCtx() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/cosnaming/CNCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */