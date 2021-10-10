/*      */ package com.sun.jndi.ldap;
/*      */ 
/*      */ import com.sun.jndi.ldap.ext.StartTlsResponseImpl;
/*      */ import com.sun.jndi.toolkit.ctx.ComponentDirContext;
/*      */ import com.sun.jndi.toolkit.ctx.Continuation;
/*      */ import com.sun.jndi.toolkit.dir.HierMemDirCtx;
/*      */ import com.sun.jndi.toolkit.dir.SearchFilter;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.naming.AuthenticationException;
/*      */ import javax.naming.AuthenticationNotSupportedException;
/*      */ import javax.naming.Binding;
/*      */ import javax.naming.CommunicationException;
/*      */ import javax.naming.CompositeName;
/*      */ import javax.naming.ConfigurationException;
/*      */ import javax.naming.Context;
/*      */ import javax.naming.ContextNotEmptyException;
/*      */ import javax.naming.InvalidNameException;
/*      */ import javax.naming.LimitExceededException;
/*      */ import javax.naming.Name;
/*      */ import javax.naming.NameAlreadyBoundException;
/*      */ import javax.naming.NameClassPair;
/*      */ import javax.naming.NameNotFoundException;
/*      */ import javax.naming.NameParser;
/*      */ import javax.naming.NamingEnumeration;
/*      */ import javax.naming.NamingException;
/*      */ import javax.naming.NoPermissionException;
/*      */ import javax.naming.OperationNotSupportedException;
/*      */ import javax.naming.PartialResultException;
/*      */ import javax.naming.ServiceUnavailableException;
/*      */ import javax.naming.SizeLimitExceededException;
/*      */ import javax.naming.TimeLimitExceededException;
/*      */ import javax.naming.directory.Attribute;
/*      */ import javax.naming.directory.AttributeInUseException;
/*      */ import javax.naming.directory.Attributes;
/*      */ import javax.naming.directory.BasicAttribute;
/*      */ import javax.naming.directory.BasicAttributes;
/*      */ import javax.naming.directory.DirContext;
/*      */ import javax.naming.directory.InvalidAttributeIdentifierException;
/*      */ import javax.naming.directory.InvalidAttributeValueException;
/*      */ import javax.naming.directory.InvalidSearchFilterException;
/*      */ import javax.naming.directory.ModificationItem;
/*      */ import javax.naming.directory.NoSuchAttributeException;
/*      */ import javax.naming.directory.SchemaViolationException;
/*      */ import javax.naming.directory.SearchControls;
/*      */ import javax.naming.directory.SearchResult;
/*      */ import javax.naming.event.EventDirContext;
/*      */ import javax.naming.event.NamingListener;
/*      */ import javax.naming.ldap.Control;
/*      */ import javax.naming.ldap.ControlFactory;
/*      */ import javax.naming.ldap.ExtendedRequest;
/*      */ import javax.naming.ldap.ExtendedResponse;
/*      */ import javax.naming.ldap.LdapContext;
/*      */ import javax.naming.ldap.LdapName;
/*      */ import javax.naming.ldap.Rdn;
/*      */ import javax.naming.spi.DirectoryManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LdapCtx
/*      */   extends ComponentDirContext
/*      */   implements EventDirContext, LdapContext
/*      */ {
/*      */   private static final boolean debug = false;
/*      */   private static final boolean HARD_CLOSE = true;
/*      */   private static final boolean SOFT_CLOSE = false;
/*      */   public static final int DEFAULT_PORT = 389;
/*      */   public static final int DEFAULT_SSL_PORT = 636;
/*      */   public static final String DEFAULT_HOST = "localhost";
/*      */   private static final boolean DEFAULT_DELETE_RDN = true;
/*      */   private static final boolean DEFAULT_TYPES_ONLY = false;
/*      */   private static final int DEFAULT_DEREF_ALIASES = 3;
/*      */   private static final int DEFAULT_LDAP_VERSION = 32;
/*      */   private static final int DEFAULT_BATCH_SIZE = 1;
/*      */   private static final int DEFAULT_REFERRAL_MODE = 3;
/*      */   private static final char DEFAULT_REF_SEPARATOR = '#';
/*      */   static final String DEFAULT_SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
/*      */   private static final int DEFAULT_REFERRAL_LIMIT = 10;
/*      */   private static final String STARTTLS_REQ_OID = "1.3.6.1.4.1.1466.20037";
/*      */   
/*      */   static final class SearchArgs
/*      */   {
/*      */     Name name;
/*      */     String filter;
/*      */     SearchControls cons;
/*      */     String[] reqAttrs;
/*      */     
/*      */     SearchArgs(Name param1Name, String param1String, SearchControls param1SearchControls, String[] param1ArrayOfString) {
/*  105 */       this.name = param1Name;
/*  106 */       this.filter = param1String;
/*  107 */       this.cons = param1SearchControls;
/*  108 */       this.reqAttrs = param1ArrayOfString;
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
/*      */   
/*  138 */   private static final String[] SCHEMA_ATTRIBUTES = new String[] { "objectClasses", "attributeTypes", "matchingRules", "ldapSyntaxes" };
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String VERSION = "java.naming.ldap.version";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String BINARY_ATTRIBUTES = "java.naming.ldap.attributes.binary";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DELETE_RDN = "java.naming.ldap.deleteRDN";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DEREF_ALIASES = "java.naming.ldap.derefAliases";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String TYPES_ONLY = "java.naming.ldap.typesOnly";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String REF_SEPARATOR = "java.naming.ldap.ref.separator";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SOCKET_FACTORY = "java.naming.ldap.factory.socket";
/*      */ 
/*      */ 
/*      */   
/*      */   static final String BIND_CONTROLS = "java.naming.ldap.control.connect";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String REFERRAL_LIMIT = "java.naming.ldap.referral.limit";
/*      */ 
/*      */   
/*      */   private static final String TRACE_BER = "com.sun.jndi.ldap.trace.ber";
/*      */ 
/*      */   
/*      */   private static final String NETSCAPE_SCHEMA_BUG = "com.sun.jndi.ldap.netscape.schemaBugs";
/*      */ 
/*      */   
/*      */   private static final String OLD_NETSCAPE_SCHEMA_BUG = "com.sun.naming.netscape.schemaBugs";
/*      */ 
/*      */   
/*      */   private static final String CONNECT_TIMEOUT = "com.sun.jndi.ldap.connect.timeout";
/*      */ 
/*      */   
/*      */   private static final String READ_TIMEOUT = "com.sun.jndi.ldap.read.timeout";
/*      */ 
/*      */   
/*      */   private static final String ENABLE_POOL = "com.sun.jndi.ldap.connect.pool";
/*      */ 
/*      */   
/*      */   private static final String DOMAIN_NAME = "com.sun.jndi.ldap.domainname";
/*      */ 
/*      */   
/*      */   private static final String WAIT_FOR_REPLY = "com.sun.jndi.ldap.search.waitForReply";
/*      */ 
/*      */   
/*      */   private static final String REPLY_QUEUE_SIZE = "com.sun.jndi.ldap.search.replyQueueSize";
/*      */ 
/*      */   
/*  204 */   private static final NameParser parser = new LdapNameParser();
/*      */ 
/*      */   
/*  207 */   private static final ControlFactory myResponseControlFactory = new DefaultResponseControlFactory();
/*      */   
/*  209 */   private static final Control manageReferralControl = new ManageReferralControl(false);
/*      */ 
/*      */   
/*  212 */   private static final HierMemDirCtx EMPTY_SCHEMA = new HierMemDirCtx(); int port_number;
/*      */   static {
/*  214 */     EMPTY_SCHEMA.setReadOnly(new SchemaViolationException("Cannot update schema object"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  224 */   String hostname = null;
/*      */   
/*  226 */   LdapClient clnt = null;
/*  227 */   Hashtable<String, Object> envprops = null;
/*  228 */   int handleReferrals = 3;
/*      */   
/*      */   boolean hasLdapsScheme = false;
/*      */   
/*      */   String currentDN;
/*      */   
/*      */   Name currentParsedDN;
/*      */   
/*  236 */   Vector<Control> respCtls = null;
/*  237 */   Control[] reqCtls = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   private OutputStream trace = null;
/*      */   private boolean netscapeSchemaBug = false;
/*  246 */   private Control[] bindCtls = null;
/*  247 */   private int referralHopLimit = 10;
/*  248 */   private Hashtable<String, DirContext> schemaTrees = null;
/*  249 */   private int batchSize = 1;
/*      */   private boolean deleteRDN = true;
/*      */   private boolean typesOnly = false;
/*  252 */   private int derefAliases = 3;
/*  253 */   private char addrEncodingSeparator = '#';
/*      */   
/*  255 */   private Hashtable<String, Boolean> binaryAttrs = null;
/*  256 */   private int connectTimeout = -1;
/*  257 */   private int readTimeout = -1;
/*      */   private boolean waitForReply = true;
/*  259 */   private int replyQueueSize = -1;
/*      */ 
/*      */   
/*      */   private boolean useSsl = false;
/*      */   
/*      */   private boolean useDefaultPortNumber = false;
/*      */   
/*      */   private boolean parentIsLdapCtx = false;
/*      */   
/*  268 */   private int hopCount = 1;
/*  269 */   private String url = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EventSupport eventSupport;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean unsolicited = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sharable = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int enumCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean closeRequested;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LdapContext newInstance(Control[] paramArrayOfControl) throws NamingException {
/*  363 */     LdapCtx ldapCtx = new LdapCtx(this, this.currentDN);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  369 */     ldapCtx.setRequestControls(paramArrayOfControl);
/*  370 */     return ldapCtx;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void c_bind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/*  380 */     c_bind(paramName, paramObject, null, paramContinuation);
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
/*      */   protected void c_bind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/*  399 */     paramContinuation.setError(this, paramName);
/*      */     
/*  401 */     Attributes attributes = paramAttributes;
/*      */     try {
/*  403 */       ensureOpen();
/*      */       
/*  405 */       if (paramObject == null) {
/*  406 */         if (paramAttributes == null) {
/*  407 */           throw new IllegalArgumentException("cannot bind null object with no attributes");
/*      */         }
/*      */       } else {
/*      */         
/*  411 */         paramAttributes = Obj.determineBindAttrs(this.addrEncodingSeparator, paramObject, paramAttributes, false, paramName, this, this.envprops);
/*      */       } 
/*      */ 
/*      */       
/*  415 */       String str = fullyQualifiedName(paramName);
/*  416 */       paramAttributes = addRdnAttributes(str, paramAttributes, (attributes != paramAttributes));
/*  417 */       LdapEntry ldapEntry = new LdapEntry(str, paramAttributes);
/*      */       
/*  419 */       LdapResult ldapResult = this.clnt.add(ldapEntry, this.reqCtls);
/*  420 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  422 */       if (ldapResult.status != 0) {
/*  423 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */     }
/*  426 */     catch (LdapReferralException ldapReferralException) {
/*  427 */       if (this.handleReferrals == 2) {
/*  428 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  434 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  439 */           ldapReferralContext.bind(paramName, paramObject, attributes);
/*      */           
/*      */           return;
/*  442 */         } catch (LdapReferralException ldapReferralException1) {
/*  443 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/*  448 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/*  452 */     } catch (IOException iOException) {
/*  453 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  454 */       communicationException.setRootCause(iOException);
/*  455 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/*  457 */     catch (NamingException namingException) {
/*  458 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void c_rebind(Name paramName, Object paramObject, Continuation paramContinuation) throws NamingException {
/*  464 */     c_rebind(paramName, paramObject, null, paramContinuation);
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
/*      */   protected void c_rebind(Name paramName, Object paramObject, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/*  486 */     paramContinuation.setError(this, paramName);
/*      */     
/*  488 */     Attributes attributes = paramAttributes;
/*      */     
/*      */     try {
/*  491 */       Attributes attributes1 = null;
/*      */ 
/*      */       
/*      */       try {
/*  495 */         attributes1 = c_getAttributes(paramName, null, paramContinuation);
/*  496 */       } catch (NameNotFoundException nameNotFoundException) {}
/*      */ 
/*      */       
/*  499 */       if (attributes1 == null) {
/*  500 */         c_bind(paramName, paramObject, paramAttributes, paramContinuation);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  507 */       if (paramAttributes == null && paramObject instanceof DirContext) {
/*  508 */         paramAttributes = ((DirContext)paramObject).getAttributes("");
/*      */       }
/*  510 */       Attributes attributes2 = (Attributes)attributes1.clone();
/*      */       
/*  512 */       if (paramAttributes == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  517 */         Attribute attribute = attributes1.get(Obj.JAVA_ATTRIBUTES[0]);
/*      */         
/*  519 */         if (attribute != null) {
/*      */           
/*  521 */           attribute = (Attribute)attribute.clone();
/*  522 */           for (byte b1 = 0; b1 < Obj.JAVA_OBJECT_CLASSES.length; b1++) {
/*  523 */             attribute.remove(Obj.JAVA_OBJECT_CLASSES_LOWER[b1]);
/*  524 */             attribute.remove(Obj.JAVA_OBJECT_CLASSES[b1]);
/*      */           } 
/*      */           
/*  527 */           attributes1.put(attribute);
/*      */         } 
/*      */ 
/*      */         
/*  531 */         for (byte b = 1; b < Obj.JAVA_ATTRIBUTES.length; b++) {
/*  532 */           attributes1.remove(Obj.JAVA_ATTRIBUTES[b]);
/*      */         }
/*      */         
/*  535 */         paramAttributes = attributes1;
/*      */       } 
/*  537 */       if (paramObject != null)
/*      */       {
/*  539 */         paramAttributes = Obj.determineBindAttrs(this.addrEncodingSeparator, paramObject, paramAttributes, (attributes != paramAttributes), paramName, this, this.envprops);
/*      */       }
/*      */ 
/*      */       
/*  543 */       String str = fullyQualifiedName(paramName);
/*      */       
/*  545 */       LdapResult ldapResult = this.clnt.delete(str, this.reqCtls);
/*  546 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  548 */       if (ldapResult.status != 0) {
/*  549 */         processReturnCode(ldapResult, paramName);
/*      */         
/*      */         return;
/*      */       } 
/*  553 */       NamingException namingException = null;
/*      */       try {
/*  555 */         paramAttributes = addRdnAttributes(str, paramAttributes, (attributes != paramAttributes));
/*      */ 
/*      */         
/*  558 */         LdapEntry ldapEntry = new LdapEntry(str, paramAttributes);
/*  559 */         ldapResult = this.clnt.add(ldapEntry, this.reqCtls);
/*  560 */         if (ldapResult.resControls != null) {
/*  561 */           this.respCtls = appendVector(this.respCtls, ldapResult.resControls);
/*      */         }
/*  563 */       } catch (NamingException|IOException namingException1) {
/*  564 */         namingException = namingException1;
/*      */       } 
/*      */       
/*  567 */       if ((namingException != null && !(namingException instanceof LdapReferralException)) || ldapResult.status != 0) {
/*      */ 
/*      */ 
/*      */         
/*  571 */         LdapResult ldapResult1 = this.clnt.add(new LdapEntry(str, attributes2), this.reqCtls);
/*  572 */         if (ldapResult1.resControls != null) {
/*  573 */           this.respCtls = appendVector(this.respCtls, ldapResult1.resControls);
/*      */         }
/*      */         
/*  576 */         if (namingException == null) {
/*  577 */           processReturnCode(ldapResult, paramName);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  582 */       if (namingException instanceof NamingException)
/*  583 */         throw namingException; 
/*  584 */       if (namingException instanceof IOException) {
/*  585 */         throw (IOException)namingException;
/*      */       }
/*      */     }
/*  588 */     catch (LdapReferralException ldapReferralException) {
/*  589 */       if (this.handleReferrals == 2) {
/*  590 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  596 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  601 */           ldapReferralContext.rebind(paramName, paramObject, attributes);
/*      */           
/*      */           return;
/*  604 */         } catch (LdapReferralException ldapReferralException1) {
/*  605 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/*  610 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/*  614 */     } catch (IOException iOException) {
/*  615 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  616 */       communicationException.setRootCause(iOException);
/*  617 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/*  619 */     catch (NamingException namingException) {
/*  620 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void c_unbind(Name paramName, Continuation paramContinuation) throws NamingException {
/*  626 */     paramContinuation.setError(this, paramName);
/*      */     
/*      */     try {
/*  629 */       ensureOpen();
/*      */       
/*  631 */       String str = fullyQualifiedName(paramName);
/*  632 */       LdapResult ldapResult = this.clnt.delete(str, this.reqCtls);
/*  633 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  635 */       adjustDeleteStatus(str, ldapResult);
/*      */       
/*  637 */       if (ldapResult.status != 0) {
/*  638 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */     }
/*  641 */     catch (LdapReferralException ldapReferralException) {
/*  642 */       if (this.handleReferrals == 2) {
/*  643 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  649 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  654 */           ldapReferralContext.unbind(paramName);
/*      */           
/*      */           return;
/*  657 */         } catch (LdapReferralException ldapReferralException1) {
/*  658 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/*  663 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/*  667 */     } catch (IOException iOException) {
/*  668 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  669 */       communicationException.setRootCause(iOException);
/*  670 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/*  672 */     catch (NamingException namingException) {
/*  673 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void c_rename(Name paramName1, Name paramName2, Continuation paramContinuation) throws NamingException {
/*  682 */     String str1 = null;
/*  683 */     String str2 = null;
/*      */ 
/*      */ 
/*      */     
/*  687 */     paramContinuation.setError(this, paramName1);
/*      */     try {
/*      */       Name name1, name2;
/*  690 */       ensureOpen();
/*      */ 
/*      */       
/*  693 */       if (paramName1.isEmpty()) {
/*  694 */         name2 = parser.parse("");
/*      */       } else {
/*  696 */         Name name = parser.parse(paramName1.get(0));
/*  697 */         name2 = name.getPrefix(name.size() - 1);
/*      */       } 
/*      */       
/*  700 */       if (paramName2 instanceof CompositeName) {
/*  701 */         name1 = parser.parse(paramName2.get(0));
/*      */       } else {
/*  703 */         name1 = paramName2;
/*      */       } 
/*  705 */       Name name3 = name1.getPrefix(name1.size() - 1);
/*      */       
/*  707 */       if (!name2.equals(name3)) {
/*  708 */         if (!this.clnt.isLdapv3) {
/*  709 */           throw new InvalidNameException("LDAPv2 doesn't support changing the parent as a result of a rename");
/*      */         }
/*      */ 
/*      */         
/*  713 */         str2 = fullyQualifiedName(name3.toString());
/*      */       } 
/*      */ 
/*      */       
/*  717 */       str1 = name1.get(name1.size() - 1);
/*      */       
/*  719 */       LdapResult ldapResult = this.clnt.moddn(fullyQualifiedName(paramName1), str1, this.deleteRDN, str2, this.reqCtls);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  724 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  726 */       if (ldapResult.status != 0) {
/*  727 */         processReturnCode(ldapResult, paramName1);
/*      */       }
/*      */     }
/*  730 */     catch (LdapReferralException ldapReferralException) {
/*      */ 
/*      */       
/*  733 */       ldapReferralException.setNewRdn(str1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  738 */       if (str2 != null) {
/*  739 */         PartialResultException partialResultException = new PartialResultException("Cannot continue referral processing when newSuperior is nonempty: " + str2);
/*      */ 
/*      */         
/*  742 */         partialResultException.setRootCause(paramContinuation.fillInException(ldapReferralException));
/*  743 */         throw paramContinuation.fillInException(partialResultException);
/*      */       } 
/*      */       
/*  746 */       if (this.handleReferrals == 2) {
/*  747 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  753 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  758 */           ldapReferralContext.rename(paramName1, paramName2);
/*      */           
/*      */           return;
/*  761 */         } catch (LdapReferralException ldapReferralException1) {
/*  762 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/*  767 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/*  771 */     } catch (IOException iOException) {
/*  772 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  773 */       communicationException.setRootCause(iOException);
/*  774 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/*  776 */     catch (NamingException namingException) {
/*  777 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Context c_createSubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/*  783 */     return c_createSubcontext(paramName, null, paramContinuation);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirContext c_createSubcontext(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/*  789 */     paramContinuation.setError(this, paramName);
/*      */     
/*  791 */     Attributes attributes = paramAttributes;
/*      */     try {
/*  793 */       ensureOpen();
/*  794 */       if (paramAttributes == null) {
/*      */         
/*  796 */         BasicAttribute basicAttribute = new BasicAttribute(Obj.JAVA_ATTRIBUTES[0], Obj.JAVA_OBJECT_CLASSES[0]);
/*      */ 
/*      */         
/*  799 */         basicAttribute.add("top");
/*  800 */         paramAttributes = new BasicAttributes(true);
/*  801 */         paramAttributes.put(basicAttribute);
/*      */       } 
/*  803 */       String str = fullyQualifiedName(paramName);
/*  804 */       paramAttributes = addRdnAttributes(str, paramAttributes, (attributes != paramAttributes));
/*      */       
/*  806 */       LdapEntry ldapEntry = new LdapEntry(str, paramAttributes);
/*      */       
/*  808 */       LdapResult ldapResult = this.clnt.add(ldapEntry, this.reqCtls);
/*  809 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  811 */       if (ldapResult.status != 0) {
/*  812 */         processReturnCode(ldapResult, paramName);
/*  813 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  817 */       return new LdapCtx(this, str);
/*      */     }
/*  819 */     catch (LdapReferralException ldapReferralException) {
/*  820 */       if (this.handleReferrals == 2) {
/*  821 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  827 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  832 */           return ldapReferralContext.createSubcontext(paramName, attributes);
/*      */         }
/*  834 */         catch (LdapReferralException ldapReferralException1) {
/*  835 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/*  840 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/*  844 */     } catch (IOException iOException) {
/*  845 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  846 */       communicationException.setRootCause(iOException);
/*  847 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/*  849 */     catch (NamingException namingException) {
/*  850 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void c_destroySubcontext(Name paramName, Continuation paramContinuation) throws NamingException {
/*  856 */     paramContinuation.setError(this, paramName);
/*      */     
/*      */     try {
/*  859 */       ensureOpen();
/*      */       
/*  861 */       String str = fullyQualifiedName(paramName);
/*  862 */       LdapResult ldapResult = this.clnt.delete(str, this.reqCtls);
/*  863 */       this.respCtls = ldapResult.resControls;
/*      */       
/*  865 */       adjustDeleteStatus(str, ldapResult);
/*      */       
/*  867 */       if (ldapResult.status != 0) {
/*  868 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */     }
/*  871 */     catch (LdapReferralException ldapReferralException) {
/*  872 */       if (this.handleReferrals == 2) {
/*  873 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  879 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  884 */           ldapReferralContext.destroySubcontext(paramName);
/*      */           return;
/*  886 */         } catch (LdapReferralException ldapReferralException1) {
/*  887 */           ldapReferralException = ldapReferralException1;
/*      */         }
/*      */         finally {
/*      */           
/*  891 */           ldapReferralContext.close();
/*      */         } 
/*      */       } 
/*  894 */     } catch (IOException iOException) {
/*  895 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/*  896 */       communicationException.setRootCause(iOException);
/*  897 */       throw paramContinuation.fillInException(communicationException);
/*  898 */     } catch (NamingException namingException) {
/*  899 */       throw paramContinuation.fillInException(namingException);
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
/*      */   private static Attributes addRdnAttributes(String paramString, Attributes paramAttributes, boolean paramBoolean) throws NamingException {
/*  917 */     if (paramString.equals("")) {
/*  918 */       return paramAttributes;
/*      */     }
/*      */ 
/*      */     
/*  922 */     List<Rdn> list = (new LdapName(paramString)).getRdns();
/*      */ 
/*      */     
/*  925 */     Rdn rdn = list.get(list.size() - 1);
/*  926 */     Attributes attributes = rdn.toAttributes();
/*      */ 
/*      */     
/*  929 */     NamingEnumeration<? extends Attribute> namingEnumeration = attributes.getAll();
/*      */     
/*  931 */     while (namingEnumeration.hasMore()) {
/*  932 */       Attribute attribute = namingEnumeration.next();
/*      */ 
/*      */       
/*  935 */       if (paramAttributes.get(attribute.getID()) == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  946 */         if (!paramAttributes.isCaseIgnored() && 
/*  947 */           containsIgnoreCase(paramAttributes.getIDs(), attribute.getID())) {
/*      */           continue;
/*      */         }
/*      */         
/*  951 */         if (!paramBoolean) {
/*  952 */           paramAttributes = (Attributes)paramAttributes.clone();
/*  953 */           paramBoolean = true;
/*      */         } 
/*  955 */         paramAttributes.put(attribute);
/*      */       } 
/*      */     } 
/*      */     
/*  959 */     return paramAttributes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean containsIgnoreCase(NamingEnumeration<String> paramNamingEnumeration, String paramString) throws NamingException {
/*  967 */     while (paramNamingEnumeration.hasMore()) {
/*  968 */       String str = paramNamingEnumeration.next();
/*  969 */       if (str.equalsIgnoreCase(paramString)) {
/*  970 */         return true;
/*      */       }
/*      */     } 
/*  973 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void adjustDeleteStatus(String paramString, LdapResult paramLdapResult) {
/*  978 */     if (paramLdapResult.status == 32 && paramLdapResult.matchedDN != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/*  983 */         Name name1 = parser.parse(paramString);
/*  984 */         Name name2 = parser.parse(paramLdapResult.matchedDN);
/*  985 */         if (name1.size() - name2.size() == 1)
/*  986 */           paramLdapResult.status = 0; 
/*  987 */       } catch (NamingException namingException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T> Vector<T> appendVector(Vector<T> paramVector1, Vector<T> paramVector2) {
/*  996 */     if (paramVector1 == null) {
/*  997 */       paramVector1 = paramVector2;
/*      */     } else {
/*  999 */       for (byte b = 0; b < paramVector2.size(); b++) {
/* 1000 */         paramVector1.addElement(paramVector2.elementAt(b));
/*      */       }
/*      */     } 
/* 1003 */     return paramVector1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object c_lookupLink(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1012 */     return c_lookup(paramName, paramContinuation);
/*      */   }
/*      */   
/*      */   protected Object c_lookup(Name paramName, Continuation paramContinuation) throws NamingException {
/*      */     Attributes attributes;
/* 1017 */     paramContinuation.setError(this, paramName);
/* 1018 */     Object object = null;
/*      */ 
/*      */     
/*      */     try {
/* 1022 */       SearchControls searchControls = new SearchControls();
/* 1023 */       searchControls.setSearchScope(0);
/* 1024 */       searchControls.setReturningAttributes(null);
/* 1025 */       searchControls.setReturningObjFlag(true);
/*      */       
/* 1027 */       LdapResult ldapResult = doSearchOnce(paramName, "(objectClass=*)", searchControls, true);
/* 1028 */       this.respCtls = ldapResult.resControls;
/*      */ 
/*      */ 
/*      */       
/* 1032 */       if (ldapResult.status != 0) {
/* 1033 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */       
/* 1036 */       if (ldapResult.entries == null || ldapResult.entries.size() != 1) {
/*      */         
/* 1038 */         attributes = new BasicAttributes(true);
/*      */       } else {
/* 1040 */         LdapEntry ldapEntry = ldapResult.entries.elementAt(0);
/* 1041 */         attributes = ldapEntry.attributes;
/*      */         
/* 1043 */         Vector<Control> vector = ldapEntry.respCtls;
/* 1044 */         if (vector != null) {
/* 1045 */           appendVector(this.respCtls, vector);
/*      */         }
/*      */       } 
/*      */       
/* 1049 */       if (attributes.get(Obj.JAVA_ATTRIBUTES[2]) != null)
/*      */       {
/* 1051 */         object = Obj.decodeObject(attributes);
/*      */       }
/* 1053 */       if (object == null) {
/* 1054 */         object = new LdapCtx(this, fullyQualifiedName(paramName));
/*      */       }
/* 1056 */     } catch (LdapReferralException ldapReferralException) {
/* 1057 */       if (this.handleReferrals == 2) {
/* 1058 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1064 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */         
/*      */         try {
/* 1068 */           return ldapReferralContext.lookup(paramName);
/*      */         }
/* 1070 */         catch (LdapReferralException ldapReferralException1) {
/* 1071 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1076 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1080 */     } catch (NamingException namingException) {
/* 1081 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */     
/*      */     try {
/* 1085 */       return DirectoryManager.getObjectInstance(object, paramName, this, this.envprops, attributes);
/*      */     
/*      */     }
/* 1088 */     catch (NamingException namingException) {
/* 1089 */       throw paramContinuation.fillInException(namingException);
/*      */     }
/* 1091 */     catch (Exception exception) {
/* 1092 */       NamingException namingException = new NamingException("problem generating object using object factory");
/*      */       
/* 1094 */       namingException.setRootCause(exception);
/* 1095 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<NameClassPair> c_list(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1101 */     SearchControls searchControls = new SearchControls();
/* 1102 */     String[] arrayOfString = new String[2];
/*      */     
/* 1104 */     arrayOfString[0] = Obj.JAVA_ATTRIBUTES[0];
/* 1105 */     arrayOfString[1] = Obj.JAVA_ATTRIBUTES[2];
/* 1106 */     searchControls.setReturningAttributes(arrayOfString);
/*      */ 
/*      */     
/* 1109 */     searchControls.setReturningObjFlag(true);
/*      */     
/* 1111 */     paramContinuation.setError(this, paramName);
/*      */     
/* 1113 */     LdapResult ldapResult = null;
/*      */     
/*      */     try {
/* 1116 */       ldapResult = doSearch(paramName, "(objectClass=*)", searchControls, true, true);
/*      */ 
/*      */       
/* 1119 */       if (ldapResult.status != 0 || ldapResult.referrals != null)
/*      */       {
/* 1121 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */       
/* 1124 */       return new LdapNamingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */     }
/* 1126 */     catch (LdapReferralException ldapReferralException) {
/* 1127 */       if (this.handleReferrals == 2) {
/* 1128 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1134 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1139 */           return ldapReferralContext.list(paramName);
/*      */         }
/* 1141 */         catch (LdapReferralException ldapReferralException1) {
/* 1142 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1147 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1151 */     } catch (LimitExceededException limitExceededException) {
/* 1152 */       LdapNamingEnumeration ldapNamingEnumeration = new LdapNamingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */ 
/*      */       
/* 1155 */       ldapNamingEnumeration.setNamingException(paramContinuation
/* 1156 */           .fillInException(limitExceededException));
/* 1157 */       return ldapNamingEnumeration;
/*      */     }
/* 1159 */     catch (PartialResultException partialResultException) {
/* 1160 */       LdapNamingEnumeration ldapNamingEnumeration = new LdapNamingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */ 
/*      */       
/* 1163 */       ldapNamingEnumeration.setNamingException(paramContinuation
/* 1164 */           .fillInException(partialResultException));
/* 1165 */       return ldapNamingEnumeration;
/*      */     }
/* 1167 */     catch (NamingException namingException) {
/* 1168 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<Binding> c_listBindings(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1175 */     SearchControls searchControls = new SearchControls();
/* 1176 */     searchControls.setReturningAttributes(null);
/* 1177 */     searchControls.setReturningObjFlag(true);
/*      */     
/* 1179 */     paramContinuation.setError(this, paramName);
/*      */     
/* 1181 */     LdapResult ldapResult = null;
/*      */     
/*      */     try {
/* 1184 */       ldapResult = doSearch(paramName, "(objectClass=*)", searchControls, true, true);
/*      */ 
/*      */       
/* 1187 */       if (ldapResult.status != 0 || ldapResult.referrals != null)
/*      */       {
/* 1189 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */       
/* 1192 */       return new LdapBindingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */     }
/* 1194 */     catch (LdapReferralException ldapReferralException) {
/* 1195 */       if (this.handleReferrals == 2) {
/* 1196 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1202 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1207 */           return ldapReferralContext.listBindings(paramName);
/*      */         }
/* 1209 */         catch (LdapReferralException ldapReferralException1) {
/* 1210 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1215 */           ldapReferralContext.close();
/*      */         } 
/*      */       } 
/* 1218 */     } catch (LimitExceededException limitExceededException) {
/* 1219 */       LdapBindingEnumeration ldapBindingEnumeration = new LdapBindingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */ 
/*      */       
/* 1222 */       ldapBindingEnumeration.setNamingException(paramContinuation.fillInException(limitExceededException));
/* 1223 */       return ldapBindingEnumeration;
/*      */     }
/* 1225 */     catch (PartialResultException partialResultException) {
/* 1226 */       LdapBindingEnumeration ldapBindingEnumeration = new LdapBindingEnumeration(this, ldapResult, paramName, paramContinuation);
/*      */ 
/*      */       
/* 1229 */       ldapBindingEnumeration.setNamingException(paramContinuation.fillInException(partialResultException));
/* 1230 */       return ldapBindingEnumeration;
/*      */     }
/* 1232 */     catch (NamingException namingException) {
/* 1233 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NameParser c_getNameParser(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1244 */     paramContinuation.setSuccess();
/* 1245 */     return parser;
/*      */   }
/*      */   
/*      */   public String getNameInNamespace() {
/* 1249 */     return this.currentDN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Name composeName(Name paramName1, Name paramName2) throws NamingException {
/* 1258 */     if (paramName1 instanceof LdapName && paramName2 instanceof LdapName) {
/* 1259 */       Name name1 = (Name)paramName2.clone();
/* 1260 */       name1.addAll(paramName1);
/* 1261 */       return (new CompositeName()).add(name1.toString());
/*      */     } 
/* 1263 */     if (!(paramName1 instanceof CompositeName)) {
/* 1264 */       paramName1 = (new CompositeName()).add(paramName1.toString());
/*      */     }
/* 1266 */     if (!(paramName2 instanceof CompositeName)) {
/* 1267 */       paramName2 = (new CompositeName()).add(paramName2.toString());
/*      */     }
/*      */     
/* 1270 */     int i = paramName2.size() - 1;
/*      */     
/* 1272 */     if (paramName1.isEmpty() || paramName2.isEmpty() || paramName1
/* 1273 */       .get(0).equals("") || paramName2.get(i).equals("")) {
/* 1274 */       return super.composeName(paramName1, paramName2);
/*      */     }
/*      */     
/* 1277 */     Name name = (Name)paramName2.clone();
/* 1278 */     name.addAll(paramName1);
/*      */     
/* 1280 */     if (this.parentIsLdapCtx) {
/* 1281 */       String str = concatNames(name.get(i + 1), name
/* 1282 */           .get(i));
/* 1283 */       name.remove(i + 1);
/* 1284 */       name.remove(i);
/* 1285 */       name.add(i, str);
/*      */     } 
/* 1287 */     return name;
/*      */   }
/*      */   
/*      */   private String fullyQualifiedName(Name paramName) {
/* 1291 */     return paramName.isEmpty() ? this.currentDN : 
/*      */       
/* 1293 */       fullyQualifiedName(paramName.get(0));
/*      */   }
/*      */   
/*      */   private String fullyQualifiedName(String paramString) {
/* 1297 */     return concatNames(paramString, this.currentDN);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String concatNames(String paramString1, String paramString2) {
/* 1302 */     if (paramString1 == null || paramString1.equals(""))
/* 1303 */       return paramString2; 
/* 1304 */     if (paramString2 == null || paramString2.equals("")) {
/* 1305 */       return paramString1;
/*      */     }
/* 1307 */     return paramString1 + "," + paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Attributes c_getAttributes(Name paramName, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/* 1317 */     paramContinuation.setError(this, paramName);
/*      */     
/* 1319 */     SearchControls searchControls = new SearchControls();
/* 1320 */     searchControls.setSearchScope(0);
/* 1321 */     searchControls.setReturningAttributes(paramArrayOfString);
/*      */ 
/*      */     
/*      */     try {
/* 1325 */       LdapResult ldapResult = doSearchOnce(paramName, "(objectClass=*)", searchControls, true);
/* 1326 */       this.respCtls = ldapResult.resControls;
/*      */       
/* 1328 */       if (ldapResult.status != 0) {
/* 1329 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */       
/* 1332 */       if (ldapResult.entries == null || ldapResult.entries.size() != 1) {
/* 1333 */         return new BasicAttributes(true);
/*      */       }
/*      */ 
/*      */       
/* 1337 */       LdapEntry ldapEntry = ldapResult.entries.elementAt(0);
/*      */       
/* 1339 */       Vector<Control> vector = ldapEntry.respCtls;
/* 1340 */       if (vector != null) {
/* 1341 */         appendVector(this.respCtls, vector);
/*      */       }
/*      */ 
/*      */       
/* 1345 */       setParents(ldapEntry.attributes, (Name)paramName.clone());
/*      */       
/* 1347 */       return ldapEntry.attributes;
/*      */     }
/* 1349 */     catch (LdapReferralException ldapReferralException) {
/* 1350 */       if (this.handleReferrals == 2) {
/* 1351 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1357 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1362 */           return ldapReferralContext.getAttributes(paramName, paramArrayOfString);
/*      */         }
/* 1364 */         catch (LdapReferralException ldapReferralException1) {
/* 1365 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1370 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1374 */     } catch (NamingException namingException) {
/* 1375 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void c_modifyAttributes(Name paramName, int paramInt, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 1383 */     paramContinuation.setError(this, paramName);
/*      */     
/*      */     try {
/* 1386 */       ensureOpen();
/*      */       
/* 1388 */       if (paramAttributes == null || paramAttributes.size() == 0) {
/*      */         return;
/*      */       }
/* 1391 */       String str = fullyQualifiedName(paramName);
/* 1392 */       int i = convertToLdapModCode(paramInt);
/*      */ 
/*      */       
/* 1395 */       int[] arrayOfInt = new int[paramAttributes.size()];
/* 1396 */       Attribute[] arrayOfAttribute = new Attribute[paramAttributes.size()];
/*      */       
/* 1398 */       NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 1399 */       for (byte b = 0; b < arrayOfInt.length && namingEnumeration.hasMore(); b++) {
/* 1400 */         arrayOfInt[b] = i;
/* 1401 */         arrayOfAttribute[b] = namingEnumeration.next();
/*      */       } 
/*      */       
/* 1404 */       LdapResult ldapResult = this.clnt.modify(str, arrayOfInt, arrayOfAttribute, this.reqCtls);
/* 1405 */       this.respCtls = ldapResult.resControls;
/*      */       
/* 1407 */       if (ldapResult.status != 0) {
/* 1408 */         processReturnCode(ldapResult, paramName);
/*      */         
/*      */         return;
/*      */       } 
/* 1412 */     } catch (LdapReferralException ldapReferralException) {
/* 1413 */       if (this.handleReferrals == 2) {
/* 1414 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1420 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1425 */           ldapReferralContext.modifyAttributes(paramName, paramInt, paramAttributes);
/*      */           
/*      */           return;
/* 1428 */         } catch (LdapReferralException ldapReferralException1) {
/* 1429 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1434 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1438 */     } catch (IOException iOException) {
/* 1439 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 1440 */       communicationException.setRootCause(iOException);
/* 1441 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/* 1443 */     catch (NamingException namingException) {
/* 1444 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void c_modifyAttributes(Name paramName, ModificationItem[] paramArrayOfModificationItem, Continuation paramContinuation) throws NamingException {
/* 1451 */     paramContinuation.setError(this, paramName);
/*      */     
/*      */     try {
/* 1454 */       ensureOpen();
/*      */       
/* 1456 */       if (paramArrayOfModificationItem == null || paramArrayOfModificationItem.length == 0) {
/*      */         return;
/*      */       }
/* 1459 */       String str = fullyQualifiedName(paramName);
/*      */ 
/*      */       
/* 1462 */       int[] arrayOfInt = new int[paramArrayOfModificationItem.length];
/* 1463 */       Attribute[] arrayOfAttribute = new Attribute[paramArrayOfModificationItem.length];
/*      */       
/* 1465 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 1466 */         ModificationItem modificationItem = paramArrayOfModificationItem[b];
/* 1467 */         arrayOfInt[b] = convertToLdapModCode(modificationItem.getModificationOp());
/* 1468 */         arrayOfAttribute[b] = modificationItem.getAttribute();
/*      */       } 
/*      */       
/* 1471 */       LdapResult ldapResult = this.clnt.modify(str, arrayOfInt, arrayOfAttribute, this.reqCtls);
/* 1472 */       this.respCtls = ldapResult.resControls;
/*      */       
/* 1474 */       if (ldapResult.status != 0) {
/* 1475 */         processReturnCode(ldapResult, paramName);
/*      */       }
/*      */     }
/* 1478 */     catch (LdapReferralException ldapReferralException) {
/* 1479 */       if (this.handleReferrals == 2) {
/* 1480 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1486 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1491 */           ldapReferralContext.modifyAttributes(paramName, paramArrayOfModificationItem);
/*      */           
/*      */           return;
/* 1494 */         } catch (LdapReferralException ldapReferralException1) {
/* 1495 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1500 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1504 */     } catch (IOException iOException) {
/* 1505 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 1506 */       communicationException.setRootCause(iOException);
/* 1507 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/* 1509 */     catch (NamingException namingException) {
/* 1510 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int convertToLdapModCode(int paramInt) {
/* 1515 */     switch (paramInt) {
/*      */       case 1:
/* 1517 */         return 0;
/*      */       
/*      */       case 2:
/* 1520 */         return 2;
/*      */       
/*      */       case 3:
/* 1523 */         return 1;
/*      */     } 
/*      */     
/* 1526 */     throw new IllegalArgumentException("Invalid modification code");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirContext c_getSchema(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1534 */     paramContinuation.setError(this, paramName);
/*      */     try {
/* 1536 */       return getSchemaTree(paramName);
/*      */     }
/* 1538 */     catch (NamingException namingException) {
/* 1539 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected DirContext c_getSchemaClassDefinition(Name paramName, Continuation paramContinuation) throws NamingException {
/* 1546 */     paramContinuation.setError(this, paramName);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1551 */       Attribute attribute = c_getAttributes(paramName, new String[] { "objectclass" }, paramContinuation).get("objectclass");
/* 1552 */       if (attribute == null || attribute.size() == 0) {
/* 1553 */         return EMPTY_SCHEMA;
/*      */       }
/*      */ 
/*      */       
/* 1557 */       Context context = (Context)c_getSchema(paramName, paramContinuation).lookup("ClassDefinition");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1562 */       HierMemDirCtx hierMemDirCtx = new HierMemDirCtx();
/*      */ 
/*      */       
/* 1565 */       NamingEnumeration<?> namingEnumeration = attribute.getAll();
/* 1566 */       while (namingEnumeration.hasMoreElements()) {
/* 1567 */         String str = (String)namingEnumeration.nextElement();
/*      */         
/* 1569 */         DirContext dirContext = (DirContext)context.lookup(str);
/* 1570 */         hierMemDirCtx.bind(str, dirContext);
/*      */       } 
/*      */ 
/*      */       
/* 1574 */       hierMemDirCtx.setReadOnly(new SchemaViolationException("Cannot update schema object"));
/*      */       
/* 1576 */       return hierMemDirCtx;
/*      */     }
/* 1578 */     catch (NamingException namingException) {
/* 1579 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DirContext getSchemaTree(Name paramName) throws NamingException {
/* 1589 */     String str = getSchemaEntry(paramName, true);
/*      */     
/* 1591 */     DirContext dirContext = this.schemaTrees.get(str);
/*      */     
/* 1593 */     if (dirContext == null) {
/*      */       
/* 1595 */       dirContext = buildSchemaTree(str);
/* 1596 */       this.schemaTrees.put(str, dirContext);
/*      */     } 
/*      */     
/* 1599 */     return dirContext;
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
/*      */   private DirContext buildSchemaTree(String paramString) throws NamingException {
/* 1613 */     SearchControls searchControls = new SearchControls(0, 0L, 0, SCHEMA_ATTRIBUTES, true, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1620 */     Name name = (new CompositeName()).add(paramString);
/*      */     
/* 1622 */     NamingEnumeration<SearchResult> namingEnumeration = searchAux(name, "(objectClass=subschema)", searchControls, false, true, new Continuation());
/*      */ 
/*      */     
/* 1625 */     if (!namingEnumeration.hasMore()) {
/* 1626 */       throw new OperationNotSupportedException("Cannot get read subschemasubentry: " + paramString);
/*      */     }
/*      */     
/* 1629 */     SearchResult searchResult = namingEnumeration.next();
/* 1630 */     namingEnumeration.close();
/*      */     
/* 1632 */     Object object = searchResult.getObject();
/* 1633 */     if (!(object instanceof LdapCtx)) {
/* 1634 */       throw new NamingException("Cannot get schema object as DirContext: " + paramString);
/*      */     }
/*      */ 
/*      */     
/* 1638 */     return LdapSchemaCtx.createSchemaTree(this.envprops, paramString, (LdapCtx)object, searchResult
/*      */         
/* 1640 */         .getAttributes(), this.netscapeSchemaBug);
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
/*      */   private String getSchemaEntry(Name paramName, boolean paramBoolean) throws NamingException {
/*      */     NamingEnumeration<SearchResult> namingEnumeration;
/* 1668 */     SearchControls searchControls = new SearchControls(0, 0L, 0, new String[] { "subschemasubentry" }, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1676 */       namingEnumeration = searchAux(paramName, "objectclass=*", searchControls, paramBoolean, true, new Continuation());
/*      */     
/*      */     }
/* 1679 */     catch (NamingException namingException) {
/* 1680 */       if (!this.clnt.isLdapv3 && this.currentDN.length() == 0 && paramName.isEmpty())
/*      */       {
/*      */         
/* 1683 */         throw new OperationNotSupportedException("Cannot get schema information from server");
/*      */       }
/*      */       
/* 1686 */       throw namingException;
/*      */     } 
/*      */ 
/*      */     
/* 1690 */     if (!namingEnumeration.hasMoreElements()) {
/* 1691 */       throw new ConfigurationException("Requesting schema of nonexistent entry: " + paramName);
/*      */     }
/*      */ 
/*      */     
/* 1695 */     SearchResult searchResult = namingEnumeration.next();
/* 1696 */     namingEnumeration.close();
/*      */ 
/*      */     
/* 1699 */     Attribute attribute = searchResult.getAttributes().get("subschemasubentry");
/*      */ 
/*      */     
/* 1702 */     if (attribute == null || attribute.size() < 0) {
/* 1703 */       if (this.currentDN.length() == 0 && paramName.isEmpty())
/*      */       {
/*      */         
/* 1706 */         throw new OperationNotSupportedException("Cannot read subschemasubentry of root DSE");
/*      */       }
/*      */       
/* 1709 */       return getSchemaEntry(new CompositeName(), false);
/*      */     } 
/*      */ 
/*      */     
/* 1713 */     return (String)attribute.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setParents(Attributes paramAttributes, Name paramName) throws NamingException {
/* 1720 */     NamingEnumeration<? extends Attribute> namingEnumeration = paramAttributes.getAll();
/* 1721 */     while (namingEnumeration.hasMore()) {
/* 1722 */       ((LdapAttribute)namingEnumeration.next()).setParent(this, paramName);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String getURL() {
/* 1731 */     if (this.url == null) {
/* 1732 */       this.url = LdapURL.toUrlString(this.hostname, this.port_number, this.currentDN, this.hasLdapsScheme);
/*      */     }
/*      */ 
/*      */     
/* 1736 */     return this.url;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<SearchResult> c_search(Name paramName, Attributes paramAttributes, Continuation paramContinuation) throws NamingException {
/* 1744 */     return c_search(paramName, paramAttributes, (String[])null, paramContinuation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<SearchResult> c_search(Name paramName, Attributes paramAttributes, String[] paramArrayOfString, Continuation paramContinuation) throws NamingException {
/*      */     String str;
/* 1752 */     SearchControls searchControls = new SearchControls();
/* 1753 */     searchControls.setReturningAttributes(paramArrayOfString);
/*      */     
/*      */     try {
/* 1756 */       str = SearchFilter.format(paramAttributes);
/* 1757 */     } catch (NamingException namingException) {
/* 1758 */       paramContinuation.setError(this, paramName);
/* 1759 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/* 1761 */     return c_search(paramName, str, searchControls, paramContinuation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/* 1769 */     return searchAux(paramName, paramString, cloneSearchControls(paramSearchControls), true, this.waitForReply, paramContinuation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NamingEnumeration<SearchResult> c_search(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, Continuation paramContinuation) throws NamingException {
/*      */     String str;
/*      */     try {
/* 1781 */       str = SearchFilter.format(paramString, paramArrayOfObject);
/* 1782 */     } catch (NamingException namingException) {
/* 1783 */       paramContinuation.setError(this, paramName);
/* 1784 */       throw paramContinuation.fillInException(namingException);
/*      */     } 
/* 1786 */     return c_search(paramName, str, paramSearchControls, paramContinuation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   NamingEnumeration<SearchResult> searchAux(Name paramName, String paramString, SearchControls paramSearchControls, boolean paramBoolean1, boolean paramBoolean2, Continuation paramContinuation) throws NamingException {
/* 1796 */     LdapResult ldapResult = null;
/* 1797 */     String[] arrayOfString1 = new String[2];
/*      */ 
/*      */     
/* 1800 */     if (paramSearchControls == null) {
/* 1801 */       paramSearchControls = new SearchControls();
/*      */     }
/* 1803 */     String[] arrayOfString2 = paramSearchControls.getReturningAttributes();
/*      */ 
/*      */ 
/*      */     
/* 1807 */     if (paramSearchControls.getReturningObjFlag() && 
/* 1808 */       arrayOfString2 != null) {
/*      */ 
/*      */       
/* 1811 */       boolean bool = false;
/* 1812 */       for (int i = arrayOfString2.length - 1; i >= 0; i--) {
/* 1813 */         if (arrayOfString2[i].equals("*")) {
/* 1814 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1818 */       if (!bool) {
/* 1819 */         String[] arrayOfString = new String[arrayOfString2.length + Obj.JAVA_ATTRIBUTES.length];
/*      */         
/* 1821 */         System.arraycopy(arrayOfString2, 0, arrayOfString, 0, arrayOfString2.length);
/*      */         
/* 1823 */         System.arraycopy(Obj.JAVA_ATTRIBUTES, 0, arrayOfString, arrayOfString2.length, Obj.JAVA_ATTRIBUTES.length);
/*      */ 
/*      */         
/* 1826 */         paramSearchControls.setReturningAttributes(arrayOfString);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1831 */     SearchArgs searchArgs = new SearchArgs(paramName, paramString, paramSearchControls, arrayOfString2);
/*      */ 
/*      */     
/* 1834 */     paramContinuation.setError(this, paramName);
/*      */     
/*      */     try {
/* 1837 */       if (searchToCompare(paramString, paramSearchControls, arrayOfString1)) {
/*      */         
/* 1839 */         ldapResult = compare(paramName, arrayOfString1[0], arrayOfString1[1]);
/* 1840 */         if (!ldapResult.compareToSearchResult(fullyQualifiedName(paramName))) {
/* 1841 */           processReturnCode(ldapResult, paramName);
/*      */         }
/*      */       } else {
/* 1844 */         ldapResult = doSearch(paramName, paramString, paramSearchControls, paramBoolean1, paramBoolean2);
/*      */         
/* 1846 */         processReturnCode(ldapResult, paramName);
/*      */       } 
/* 1848 */       return new LdapSearchEnumeration(this, ldapResult, 
/* 1849 */           fullyQualifiedName(paramName), searchArgs, paramContinuation);
/*      */     
/*      */     }
/* 1852 */     catch (LdapReferralException ldapReferralException) {
/* 1853 */       if (this.handleReferrals == 2) {
/* 1854 */         throw paramContinuation.fillInException(ldapReferralException);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 1861 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 1866 */           return ldapReferralContext.search(paramName, paramString, paramSearchControls);
/*      */         }
/* 1868 */         catch (LdapReferralException ldapReferralException1) {
/* 1869 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 1874 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 1878 */     } catch (LimitExceededException limitExceededException) {
/*      */       
/* 1880 */       LdapSearchEnumeration ldapSearchEnumeration = new LdapSearchEnumeration(this, ldapResult, fullyQualifiedName(paramName), searchArgs, paramContinuation);
/*      */       
/* 1882 */       ldapSearchEnumeration.setNamingException(limitExceededException);
/* 1883 */       return ldapSearchEnumeration;
/*      */     }
/* 1885 */     catch (PartialResultException partialResultException) {
/*      */       
/* 1887 */       LdapSearchEnumeration ldapSearchEnumeration = new LdapSearchEnumeration(this, ldapResult, fullyQualifiedName(paramName), searchArgs, paramContinuation);
/*      */ 
/*      */       
/* 1890 */       ldapSearchEnumeration.setNamingException(partialResultException);
/* 1891 */       return ldapSearchEnumeration;
/*      */     }
/* 1893 */     catch (IOException iOException) {
/* 1894 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 1895 */       communicationException.setRootCause(iOException);
/* 1896 */       throw paramContinuation.fillInException(communicationException);
/*      */     }
/* 1898 */     catch (NamingException namingException) {
/* 1899 */       throw paramContinuation.fillInException(namingException);
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
/*      */   LdapResult getSearchReply(LdapClient paramLdapClient, LdapResult paramLdapResult) throws NamingException {
/* 1912 */     if (this.clnt != paramLdapClient) {
/* 1913 */       throw new CommunicationException("Context's connection changed; unable to continue enumeration");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1918 */       return paramLdapClient.getSearchReply(this.batchSize, paramLdapResult, this.binaryAttrs);
/* 1919 */     } catch (IOException iOException) {
/* 1920 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 1921 */       communicationException.setRootCause(iOException);
/* 1922 */       throw communicationException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapResult doSearchOnce(Name paramName, String paramString, SearchControls paramSearchControls, boolean paramBoolean) throws NamingException {
/* 1930 */     int i = this.batchSize;
/* 1931 */     this.batchSize = 2;
/*      */     
/* 1933 */     LdapResult ldapResult = doSearch(paramName, paramString, paramSearchControls, paramBoolean, true);
/*      */     
/* 1935 */     this.batchSize = i;
/* 1936 */     return ldapResult;
/*      */   }
/*      */ 
/*      */   
/*      */   private LdapResult doSearch(Name paramName, String paramString, SearchControls paramSearchControls, boolean paramBoolean1, boolean paramBoolean2) throws NamingException {
/* 1941 */     ensureOpen();
/*      */     
/*      */     try {
/*      */       byte b;
/* 1945 */       switch (paramSearchControls.getSearchScope()) {
/*      */         case 0:
/* 1947 */           b = 0;
/*      */           break;
/*      */         
/*      */         default:
/* 1951 */           b = 1;
/*      */           break;
/*      */         case 2:
/* 1954 */           b = 2;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1961 */       String[] arrayOfString = paramSearchControls.getReturningAttributes();
/* 1962 */       if (arrayOfString != null && arrayOfString.length == 0) {
/*      */ 
/*      */         
/* 1965 */         arrayOfString = new String[1];
/* 1966 */         arrayOfString[0] = "1.1";
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1973 */       String str = paramBoolean1 ? fullyQualifiedName(paramName) : (paramName.isEmpty() ? "" : paramName.get(0));
/*      */ 
/*      */ 
/*      */       
/* 1977 */       int i = paramSearchControls.getTimeLimit();
/* 1978 */       int j = 0;
/*      */       
/* 1980 */       if (i > 0) {
/* 1981 */         j = i / 1000 + 1;
/*      */       }
/*      */ 
/*      */       
/* 1985 */       LdapResult ldapResult = this.clnt.search(str, b, this.derefAliases, 
/*      */ 
/*      */           
/* 1988 */           (int)paramSearchControls.getCountLimit(), j, 
/*      */           
/* 1990 */           paramSearchControls.getReturningObjFlag() ? false : this.typesOnly, arrayOfString, paramString, this.batchSize, this.reqCtls, this.binaryAttrs, paramBoolean2, this.replyQueueSize);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1998 */       this.respCtls = ldapResult.resControls;
/* 1999 */       return ldapResult;
/*      */     }
/* 2001 */     catch (IOException iOException) {
/* 2002 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 2003 */       communicationException.setRootCause(iOException);
/* 2004 */       throw communicationException;
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
/*      */   private static boolean searchToCompare(String paramString, SearchControls paramSearchControls, String[] paramArrayOfString) {
/* 2031 */     if (paramSearchControls.getSearchScope() != 0) {
/* 2032 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2036 */     String[] arrayOfString = paramSearchControls.getReturningAttributes();
/* 2037 */     if (arrayOfString == null || arrayOfString.length != 0) {
/* 2038 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2042 */     if (!filterToAssertion(paramString, paramArrayOfString)) {
/* 2043 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2047 */     return true;
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
/*      */   private static boolean filterToAssertion(String paramString, String[] paramArrayOfString) {
/* 2059 */     StringTokenizer stringTokenizer1 = new StringTokenizer(paramString, "=");
/*      */     
/* 2061 */     if (stringTokenizer1.countTokens() != 2) {
/* 2062 */       return false;
/*      */     }
/*      */     
/* 2065 */     paramArrayOfString[0] = stringTokenizer1.nextToken();
/* 2066 */     paramArrayOfString[1] = stringTokenizer1.nextToken();
/*      */ 
/*      */     
/* 2069 */     if (paramArrayOfString[1].indexOf('*') != -1) {
/* 2070 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2074 */     boolean bool = false;
/* 2075 */     int i = paramArrayOfString[1].length();
/*      */     
/* 2077 */     if (paramArrayOfString[0].charAt(0) == '(' && paramArrayOfString[1]
/* 2078 */       .charAt(i - 1) == ')') {
/* 2079 */       bool = true;
/*      */     }
/* 2081 */     else if (paramArrayOfString[0].charAt(0) == '(' || paramArrayOfString[1]
/* 2082 */       .charAt(i - 1) == ')') {
/* 2083 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 2087 */     StringTokenizer stringTokenizer2 = new StringTokenizer(paramArrayOfString[0], "()&|!=~><*", true);
/*      */ 
/*      */     
/* 2090 */     if (stringTokenizer2.countTokens() != (bool ? 2 : 1)) {
/* 2091 */       return false;
/*      */     }
/*      */     
/* 2094 */     stringTokenizer2 = new StringTokenizer(paramArrayOfString[1], "()&|!=~><*", true);
/*      */ 
/*      */     
/* 2097 */     if (stringTokenizer2.countTokens() != (bool ? 2 : 1)) {
/* 2098 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2102 */     if (bool) {
/* 2103 */       paramArrayOfString[0] = paramArrayOfString[0].substring(1);
/* 2104 */       paramArrayOfString[1] = paramArrayOfString[1].substring(0, i - 1);
/*      */     } 
/*      */     
/* 2107 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private LdapResult compare(Name paramName, String paramString1, String paramString2) throws IOException, NamingException {
/* 2113 */     ensureOpen();
/* 2114 */     String str = fullyQualifiedName(paramName);
/*      */     
/* 2116 */     LdapResult ldapResult = this.clnt.compare(str, paramString1, paramString2, this.reqCtls);
/* 2117 */     this.respCtls = ldapResult.resControls;
/*      */     
/* 2119 */     return ldapResult;
/*      */   }
/*      */   
/*      */   private static SearchControls cloneSearchControls(SearchControls paramSearchControls) {
/* 2123 */     if (paramSearchControls == null) {
/* 2124 */       return null;
/*      */     }
/* 2126 */     String[] arrayOfString = paramSearchControls.getReturningAttributes();
/* 2127 */     if (arrayOfString != null) {
/* 2128 */       String[] arrayOfString1 = new String[arrayOfString.length];
/* 2129 */       System.arraycopy(arrayOfString, 0, arrayOfString1, 0, arrayOfString.length);
/* 2130 */       arrayOfString = arrayOfString1;
/*      */     } 
/* 2132 */     return new SearchControls(paramSearchControls.getSearchScope(), paramSearchControls
/* 2133 */         .getCountLimit(), paramSearchControls
/* 2134 */         .getTimeLimit(), arrayOfString, paramSearchControls
/*      */         
/* 2136 */         .getReturningObjFlag(), paramSearchControls
/* 2137 */         .getDerefLinkFlag());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Hashtable<String, Object> p_getEnvironment() {
/* 2146 */     return this.envprops;
/*      */   }
/*      */ 
/*      */   
/*      */   public Hashtable<String, Object> getEnvironment() throws NamingException {
/* 2151 */     return (this.envprops == null) ? new Hashtable<>(5, 0.75F) : (Hashtable<String, Object>)this.envprops
/*      */       
/* 2153 */       .clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object removeFromEnvironment(String paramString) throws NamingException {
/* 2161 */     if (this.envprops == null || this.envprops.get(paramString) == null) {
/* 2162 */       return null;
/*      */     }
/* 2164 */     switch (paramString) {
/*      */       case "java.naming.ldap.ref.separator":
/* 2166 */         this.addrEncodingSeparator = '#';
/*      */         break;
/*      */       case "java.naming.ldap.typesOnly":
/* 2169 */         this.typesOnly = false;
/*      */         break;
/*      */       case "java.naming.ldap.deleteRDN":
/* 2172 */         this.deleteRDN = true;
/*      */         break;
/*      */       case "java.naming.ldap.derefAliases":
/* 2175 */         this.derefAliases = 3;
/*      */         break;
/*      */       case "java.naming.batchsize":
/* 2178 */         this.batchSize = 1;
/*      */         break;
/*      */       case "java.naming.ldap.referral.limit":
/* 2181 */         this.referralHopLimit = 10;
/*      */         break;
/*      */       case "java.naming.referral":
/* 2184 */         setReferralMode(null, true);
/*      */         break;
/*      */       case "java.naming.ldap.attributes.binary":
/* 2187 */         setBinaryAttributes(null);
/*      */         break;
/*      */       case "com.sun.jndi.ldap.connect.timeout":
/* 2190 */         this.connectTimeout = -1;
/*      */         break;
/*      */       case "com.sun.jndi.ldap.read.timeout":
/* 2193 */         this.readTimeout = -1;
/*      */         break;
/*      */       case "com.sun.jndi.ldap.search.waitForReply":
/* 2196 */         this.waitForReply = true;
/*      */         break;
/*      */       case "com.sun.jndi.ldap.search.replyQueueSize":
/* 2199 */         this.replyQueueSize = -1;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case "java.naming.security.protocol":
/* 2205 */         closeConnection(false);
/*      */         
/* 2207 */         if (this.useSsl && !this.hasLdapsScheme) {
/* 2208 */           this.useSsl = false;
/* 2209 */           this.url = null;
/* 2210 */           if (this.useDefaultPortNumber) {
/* 2211 */             this.port_number = 389;
/*      */           }
/*      */         } 
/*      */         break;
/*      */       case "java.naming.ldap.version":
/*      */       case "java.naming.ldap.factory.socket":
/* 2217 */         closeConnection(false);
/*      */         break;
/*      */       case "java.naming.security.authentication":
/*      */       case "java.naming.security.principal":
/*      */       case "java.naming.security.credentials":
/* 2222 */         this.sharable = false;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2227 */     this.envprops = (Hashtable<String, Object>)this.envprops.clone();
/* 2228 */     return this.envprops.remove(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object addToEnvironment(String paramString, Object paramObject) throws NamingException {
/* 2236 */     if (paramObject == null) {
/* 2237 */       return removeFromEnvironment(paramString);
/*      */     }
/* 2239 */     switch (paramString) {
/*      */       case "java.naming.ldap.ref.separator":
/* 2241 */         setRefSeparator((String)paramObject);
/*      */         break;
/*      */       case "java.naming.ldap.typesOnly":
/* 2244 */         setTypesOnly((String)paramObject);
/*      */         break;
/*      */       case "java.naming.ldap.deleteRDN":
/* 2247 */         setDeleteRDN((String)paramObject);
/*      */         break;
/*      */       case "java.naming.ldap.derefAliases":
/* 2250 */         setDerefAliases((String)paramObject);
/*      */         break;
/*      */       case "java.naming.batchsize":
/* 2253 */         setBatchSize((String)paramObject);
/*      */         break;
/*      */       case "java.naming.ldap.referral.limit":
/* 2256 */         setReferralLimit((String)paramObject);
/*      */         break;
/*      */       case "java.naming.referral":
/* 2259 */         setReferralMode((String)paramObject, true);
/*      */         break;
/*      */       case "java.naming.ldap.attributes.binary":
/* 2262 */         setBinaryAttributes((String)paramObject);
/*      */         break;
/*      */       case "com.sun.jndi.ldap.connect.timeout":
/* 2265 */         setConnectTimeout((String)paramObject);
/*      */         break;
/*      */       case "com.sun.jndi.ldap.read.timeout":
/* 2268 */         setReadTimeout((String)paramObject);
/*      */         break;
/*      */       case "com.sun.jndi.ldap.search.waitForReply":
/* 2271 */         setWaitForReply((String)paramObject);
/*      */         break;
/*      */       case "com.sun.jndi.ldap.search.replyQueueSize":
/* 2274 */         setReplyQueueSize((String)paramObject);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case "java.naming.security.protocol":
/* 2280 */         closeConnection(false);
/*      */         
/* 2282 */         if ("ssl".equals(paramObject)) {
/* 2283 */           this.useSsl = true;
/* 2284 */           this.url = null;
/* 2285 */           if (this.useDefaultPortNumber) {
/* 2286 */             this.port_number = 636;
/*      */           }
/*      */         } 
/*      */         break;
/*      */       case "java.naming.ldap.version":
/*      */       case "java.naming.ldap.factory.socket":
/* 2292 */         closeConnection(false);
/*      */         break;
/*      */       case "java.naming.security.authentication":
/*      */       case "java.naming.security.principal":
/*      */       case "java.naming.security.credentials":
/* 2297 */         this.sharable = false;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 2302 */     this
/*      */       
/* 2304 */       .envprops = (this.envprops == null) ? new Hashtable<>(5, 0.75F) : (Hashtable<String, Object>)this.envprops.clone();
/* 2305 */     return this.envprops.put(paramString, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setProviderUrl(String paramString) {
/* 2313 */     if (this.envprops != null) {
/* 2314 */       this.envprops.put("java.naming.provider.url", paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setDomainName(String paramString) {
/* 2324 */     if (this.envprops != null) {
/* 2325 */       this.envprops.put("com.sun.jndi.ldap.domainname", paramString);
/*      */     }
/*      */   }
/*      */   
/*      */   private void initEnv() throws NamingException {
/* 2330 */     if (this.envprops == null) {
/*      */       
/* 2332 */       setReferralMode(null, false);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2337 */     setBatchSize((String)this.envprops.get("java.naming.batchsize"));
/*      */ 
/*      */     
/* 2340 */     setRefSeparator((String)this.envprops.get("java.naming.ldap.ref.separator"));
/*      */ 
/*      */     
/* 2343 */     setDeleteRDN((String)this.envprops.get("java.naming.ldap.deleteRDN"));
/*      */ 
/*      */     
/* 2346 */     setTypesOnly((String)this.envprops.get("java.naming.ldap.typesOnly"));
/*      */ 
/*      */     
/* 2349 */     setDerefAliases((String)this.envprops.get("java.naming.ldap.derefAliases"));
/*      */ 
/*      */     
/* 2352 */     setReferralLimit((String)this.envprops.get("java.naming.ldap.referral.limit"));
/*      */     
/* 2354 */     setBinaryAttributes((String)this.envprops.get("java.naming.ldap.attributes.binary"));
/*      */     
/* 2356 */     this.bindCtls = cloneControls((Control[])this.envprops.get("java.naming.ldap.control.connect"));
/*      */ 
/*      */     
/* 2359 */     setReferralMode((String)this.envprops.get("java.naming.referral"), false);
/*      */ 
/*      */     
/* 2362 */     setConnectTimeout((String)this.envprops.get("com.sun.jndi.ldap.connect.timeout"));
/*      */ 
/*      */     
/* 2365 */     setReadTimeout((String)this.envprops.get("com.sun.jndi.ldap.read.timeout"));
/*      */ 
/*      */ 
/*      */     
/* 2369 */     setWaitForReply((String)this.envprops.get("com.sun.jndi.ldap.search.waitForReply"));
/*      */ 
/*      */     
/* 2372 */     setReplyQueueSize((String)this.envprops.get("com.sun.jndi.ldap.search.replyQueueSize"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDeleteRDN(String paramString) {
/* 2379 */     if (paramString != null && paramString
/* 2380 */       .equalsIgnoreCase("false")) {
/* 2381 */       this.deleteRDN = false;
/*      */     } else {
/* 2383 */       this.deleteRDN = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setTypesOnly(String paramString) {
/* 2388 */     if (paramString != null && paramString
/* 2389 */       .equalsIgnoreCase("true")) {
/* 2390 */       this.typesOnly = true;
/*      */     } else {
/* 2392 */       this.typesOnly = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBatchSize(String paramString) {
/* 2401 */     if (paramString != null) {
/* 2402 */       this.batchSize = Integer.parseInt(paramString);
/*      */     } else {
/* 2404 */       this.batchSize = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setReferralMode(String paramString, boolean paramBoolean) {
/* 2414 */     if (paramString != null) {
/* 2415 */       switch (paramString) {
/*      */         case "follow-scheme":
/* 2417 */           this.handleReferrals = 4;
/*      */           break;
/*      */         case "follow":
/* 2420 */           this.handleReferrals = 1;
/*      */           break;
/*      */         case "throw":
/* 2423 */           this.handleReferrals = 2;
/*      */           break;
/*      */         case "ignore":
/* 2426 */           this.handleReferrals = 3;
/*      */           break;
/*      */         default:
/* 2429 */           throw new IllegalArgumentException("Illegal value for java.naming.referral property.");
/*      */       } 
/*      */     
/*      */     } else {
/* 2433 */       this.handleReferrals = 3;
/*      */     } 
/*      */     
/* 2436 */     if (this.handleReferrals == 3) {
/*      */       
/* 2438 */       this.reqCtls = addControl(this.reqCtls, manageReferralControl);
/*      */     }
/* 2440 */     else if (paramBoolean) {
/*      */ 
/*      */       
/* 2443 */       this.reqCtls = removeControl(this.reqCtls, manageReferralControl);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setDerefAliases(String paramString) {
/* 2452 */     if (paramString != null) {
/* 2453 */       switch (paramString) {
/*      */         case "never":
/* 2455 */           this.derefAliases = 0;
/*      */           return;
/*      */         case "searching":
/* 2458 */           this.derefAliases = 1;
/*      */           return;
/*      */         case "finding":
/* 2461 */           this.derefAliases = 2;
/*      */           return;
/*      */         case "always":
/* 2464 */           this.derefAliases = 3;
/*      */           return;
/*      */       } 
/* 2467 */       throw new IllegalArgumentException("Illegal value for java.naming.ldap.derefAliases property.");
/*      */     } 
/*      */ 
/*      */     
/* 2471 */     this.derefAliases = 3;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setRefSeparator(String paramString) throws NamingException {
/* 2476 */     if (paramString != null && paramString.length() > 0) {
/* 2477 */       this.addrEncodingSeparator = paramString.charAt(0);
/*      */     } else {
/* 2479 */       this.addrEncodingSeparator = '#';
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setReferralLimit(String paramString) {
/* 2488 */     if (paramString != null) {
/* 2489 */       this.referralHopLimit = Integer.parseInt(paramString);
/*      */ 
/*      */       
/* 2492 */       if (this.referralHopLimit == 0)
/* 2493 */         this.referralHopLimit = Integer.MAX_VALUE; 
/*      */     } else {
/* 2495 */       this.referralHopLimit = 10;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void setHopCount(int paramInt) {
/* 2501 */     this.hopCount = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setConnectTimeout(String paramString) {
/* 2508 */     if (paramString != null) {
/* 2509 */       this.connectTimeout = Integer.parseInt(paramString);
/*      */     } else {
/* 2511 */       this.connectTimeout = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setReplyQueueSize(String paramString) {
/* 2519 */     if (paramString != null) {
/* 2520 */       this.replyQueueSize = Integer.parseInt(paramString);
/*      */       
/* 2522 */       if (this.replyQueueSize <= 0) {
/* 2523 */         this.replyQueueSize = -1;
/*      */       }
/*      */     } else {
/* 2526 */       this.replyQueueSize = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setWaitForReply(String paramString) {
/* 2535 */     if (paramString != null && paramString
/* 2536 */       .equalsIgnoreCase("false")) {
/* 2537 */       this.waitForReply = false;
/*      */     } else {
/* 2539 */       this.waitForReply = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setReadTimeout(String paramString) {
/* 2547 */     if (paramString != null) {
/* 2548 */       this.readTimeout = Integer.parseInt(paramString);
/*      */     } else {
/* 2550 */       this.readTimeout = -1;
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
/*      */   private static Vector<Vector<String>> extractURLs(String paramString) {
/* 2566 */     int i = 0;
/* 2567 */     byte b = 0;
/*      */ 
/*      */     
/* 2570 */     while ((i = paramString.indexOf('\n', i)) >= 0) {
/* 2571 */       i++;
/* 2572 */       b++;
/*      */     } 
/*      */     
/* 2575 */     Vector<Vector<String>> vector = new Vector(b);
/*      */     
/* 2577 */     boolean bool = false;
/*      */     
/* 2579 */     i = paramString.indexOf('\n');
/* 2580 */     int j = i + 1;
/* 2581 */     while ((i = paramString.indexOf('\n', j)) >= 0) {
/* 2582 */       Vector<String> vector2 = new Vector(1);
/* 2583 */       vector2.addElement(paramString.substring(j, i));
/* 2584 */       vector.addElement(vector2);
/* 2585 */       j = i + 1;
/*      */     } 
/* 2587 */     Vector<String> vector1 = new Vector(1);
/* 2588 */     vector1.addElement(paramString.substring(j));
/* 2589 */     vector.addElement(vector1);
/*      */     
/* 2591 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setBinaryAttributes(String paramString) {
/* 2599 */     if (paramString == null) {
/* 2600 */       this.binaryAttrs = null;
/*      */     } else {
/* 2602 */       this.binaryAttrs = new Hashtable<>(11, 0.75F);
/*      */       
/* 2604 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString.toLowerCase(Locale.ENGLISH), " ");
/*      */       
/* 2606 */       while (stringTokenizer.hasMoreTokens()) {
/* 2607 */         this.binaryAttrs.put(stringTokenizer.nextToken(), Boolean.TRUE);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() {
/*      */     try {
/* 2616 */       close();
/* 2617 */     } catch (NamingException namingException) {}
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
/*      */   public synchronized void close() throws NamingException {
/* 2629 */     if (this.eventSupport != null) {
/* 2630 */       this.eventSupport.cleanup();
/* 2631 */       removeUnsolicited();
/*      */     } 
/*      */ 
/*      */     
/* 2635 */     if (this.enumCount > 0) {
/*      */ 
/*      */       
/* 2638 */       this.closeRequested = true;
/*      */       return;
/*      */     } 
/* 2641 */     closeConnection(false);
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
/*      */   public void reconnect(Control[] paramArrayOfControl) throws NamingException {
/* 2657 */     this
/*      */       
/* 2659 */       .envprops = (this.envprops == null) ? new Hashtable<>(5, 0.75F) : (Hashtable<String, Object>)this.envprops.clone();
/*      */     
/* 2661 */     if (paramArrayOfControl == null) {
/* 2662 */       this.envprops.remove("java.naming.ldap.control.connect");
/* 2663 */       this.bindCtls = null;
/*      */     } else {
/* 2665 */       this.envprops.put("java.naming.ldap.control.connect", this.bindCtls = cloneControls(paramArrayOfControl));
/*      */     } 
/*      */     
/* 2668 */     this.sharable = false;
/* 2669 */     ensureOpen();
/*      */   }
/*      */   
/*      */   private void ensureOpen() throws NamingException {
/* 2673 */     ensureOpen(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureOpen(boolean paramBoolean) throws NamingException {
/*      */     try {
/* 2679 */       if (this.clnt == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2685 */         this.schemaTrees = new Hashtable<>(11, 0.75F);
/* 2686 */         connect(paramBoolean);
/*      */       }
/* 2688 */       else if (!this.sharable || paramBoolean) {
/*      */         
/* 2690 */         synchronized (this.clnt) {
/* 2691 */           if (!this.clnt.isLdapv3 || this.clnt.referenceCount > 1 || this.clnt
/*      */             
/* 2693 */             .usingSaslStreams()) {
/* 2694 */             closeConnection(false);
/*      */           }
/*      */         } 
/*      */         
/* 2698 */         this.schemaTrees = new Hashtable<>(11, 0.75F);
/* 2699 */         connect(paramBoolean);
/*      */       } 
/*      */     } finally {
/*      */       
/* 2703 */       this.sharable = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void connect(boolean paramBoolean) throws NamingException {
/* 2711 */     String str1 = null;
/* 2712 */     Object object = null;
/* 2713 */     String str2 = null;
/* 2714 */     String str3 = null;
/* 2715 */     String str4 = null;
/* 2716 */     String str5 = null;
/*      */     
/* 2718 */     boolean bool = false;
/*      */     
/* 2720 */     if (this.envprops != null) {
/* 2721 */       str1 = (String)this.envprops.get("java.naming.security.principal");
/* 2722 */       object = this.envprops.get("java.naming.security.credentials");
/* 2723 */       str5 = (String)this.envprops.get("java.naming.ldap.version");
/*      */       
/* 2725 */       str2 = this.useSsl ? "ssl" : (String)this.envprops.get("java.naming.security.protocol");
/* 2726 */       str3 = (String)this.envprops.get("java.naming.ldap.factory.socket");
/*      */       
/* 2728 */       str4 = (String)this.envprops.get("java.naming.security.authentication");
/*      */       
/* 2730 */       bool = "true".equalsIgnoreCase((String)this.envprops.get("com.sun.jndi.ldap.connect.pool"));
/*      */     } 
/*      */     
/* 2733 */     if (str3 == null)
/*      */     {
/* 2735 */       str3 = "ssl".equals(str2) ? "javax.net.ssl.SSLSocketFactory" : null;
/*      */     }
/*      */     
/* 2738 */     if (str4 == null) {
/* 2739 */       str4 = (str1 == null) ? "none" : "simple";
/*      */     }
/*      */     try {
/*      */       byte b;
/* 2743 */       boolean bool1 = (this.clnt == null) ? true : false;
/*      */       
/* 2745 */       if (bool1) {
/* 2746 */         b = (str5 != null) ? Integer.parseInt(str5) : 32;
/*      */ 
/*      */         
/* 2749 */         this.clnt = LdapClient.getInstance(bool, this.hostname, this.port_number, str3, this.connectTimeout, this.readTimeout, this.trace, b, str4, this.bindCtls, str2, str1, object, this.envprops);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2778 */         if (this.clnt.authenticateCalled()) {
/*      */           return;
/*      */         }
/*      */       } else {
/* 2782 */         if (this.sharable && paramBoolean) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2788 */         b = 3;
/*      */       } 
/*      */       
/* 2791 */       LdapResult ldapResult = this.clnt.authenticate(bool1, str1, object, b, str4, this.bindCtls, this.envprops);
/*      */ 
/*      */       
/* 2794 */       this.respCtls = ldapResult.resControls;
/*      */       
/* 2796 */       if (ldapResult.status != 0) {
/* 2797 */         if (bool1) {
/* 2798 */           closeConnection(true);
/*      */         }
/* 2800 */         processReturnCode(ldapResult);
/*      */       }
/*      */     
/* 2803 */     } catch (LdapReferralException ldapReferralException) {
/* 2804 */       if (this.handleReferrals == 2) {
/* 2805 */         throw ldapReferralException;
/*      */       }
/*      */ 
/*      */       
/* 2809 */       NamingException namingException = null;
/*      */ 
/*      */       
/*      */       while (true) {
/*      */         String str;
/*      */         
/* 2815 */         if ((str = ldapReferralException.getNextReferral()) == null) {
/*      */ 
/*      */           
/* 2818 */           if (namingException != null) {
/* 2819 */             throw (NamingException)namingException.fillInStackTrace();
/*      */           }
/*      */           
/* 2822 */           throw new NamingException("Internal error processing referral during connection");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2828 */         LdapURL ldapURL = new LdapURL(str);
/* 2829 */         this.hostname = ldapURL.getHost();
/* 2830 */         if (this.hostname != null && this.hostname.charAt(0) == '[') {
/* 2831 */           this.hostname = this.hostname.substring(1, this.hostname.length() - 1);
/*      */         }
/* 2833 */         this.port_number = ldapURL.getPort();
/*      */ 
/*      */         
/*      */         try {
/* 2837 */           connect(paramBoolean);
/*      */           
/*      */           break;
/* 2840 */         } catch (NamingException namingException1) {
/* 2841 */           namingException = namingException1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void closeConnection(boolean paramBoolean) {
/* 2849 */     removeUnsolicited();
/*      */     
/* 2851 */     if (this.clnt != null) {
/*      */ 
/*      */ 
/*      */       
/* 2855 */       this.clnt.close(this.reqCtls, paramBoolean);
/* 2856 */       this.clnt = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public LdapCtx(String paramString1, String paramString2, int paramInt, Hashtable<?, ?> paramHashtable, boolean paramBoolean) throws NamingException {
/* 2861 */     this.enumCount = 0;
/* 2862 */     this.closeRequested = false; this.useSsl = this.hasLdapsScheme = paramBoolean; if (paramHashtable != null) { this.envprops = (Hashtable<String, Object>)paramHashtable.clone(); if ("ssl".equals(this.envprops.get("java.naming.security.protocol"))) this.useSsl = true;  this.trace = (OutputStream)this.envprops.get("com.sun.jndi.ldap.trace.ber"); if (paramHashtable.get("com.sun.jndi.ldap.netscape.schemaBugs") != null || paramHashtable.get("com.sun.naming.netscape.schemaBugs") != null) this.netscapeSchemaBug = true;  }  this.currentDN = (paramString1 != null) ? paramString1 : ""; this.currentParsedDN = parser.parse(this.currentDN); this.hostname = (paramString2 != null && paramString2.length() > 0) ? paramString2 : "localhost"; if (this.hostname.charAt(0) == '[') this.hostname = this.hostname.substring(1, this.hostname.length() - 1);  if (paramInt > 0) { this.port_number = paramInt; } else { this.port_number = this.useSsl ? 636 : 389; this.useDefaultPortNumber = true; }  this.schemaTrees = new Hashtable<>(11, 0.75F); initEnv(); try { connect(false); } catch (NamingException namingException) { try { close(); } catch (Exception exception) {} throw namingException; }  } LdapCtx(LdapCtx paramLdapCtx, String paramString) throws NamingException { this.enumCount = 0; this.closeRequested = false; this.useSsl = paramLdapCtx.useSsl; this.hasLdapsScheme = paramLdapCtx.hasLdapsScheme; this.useDefaultPortNumber = paramLdapCtx.useDefaultPortNumber; this.hostname = paramLdapCtx.hostname; this.port_number = paramLdapCtx.port_number; this.currentDN = paramString; if (paramLdapCtx.currentDN == this.currentDN) { this.currentParsedDN = paramLdapCtx.currentParsedDN; }
/*      */     else
/*      */     { this.currentParsedDN = parser.parse(this.currentDN); }
/* 2865 */      this.envprops = paramLdapCtx.envprops; this.schemaTrees = paramLdapCtx.schemaTrees; this.clnt = paramLdapCtx.clnt; this.clnt.incRefCount(); this.parentIsLdapCtx = (paramString == null || paramString.equals(paramLdapCtx.currentDN)) ? paramLdapCtx.parentIsLdapCtx : true; this.trace = paramLdapCtx.trace; this.netscapeSchemaBug = paramLdapCtx.netscapeSchemaBug; initEnv(); } synchronized void incEnumCount() { this.enumCount++; }
/*      */ 
/*      */ 
/*      */   
/*      */   synchronized void decEnumCount() {
/* 2870 */     this.enumCount--;
/*      */ 
/*      */     
/* 2873 */     if (this.enumCount == 0 && this.closeRequested) {
/*      */       try {
/* 2875 */         close();
/* 2876 */       } catch (NamingException namingException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processReturnCode(LdapResult paramLdapResult) throws NamingException {
/* 2886 */     processReturnCode(paramLdapResult, null, this, null, this.envprops, null);
/*      */   }
/*      */ 
/*      */   
/*      */   void processReturnCode(LdapResult paramLdapResult, Name paramName) throws NamingException {
/* 2891 */     processReturnCode(paramLdapResult, (new CompositeName())
/* 2892 */         .add(this.currentDN), this, paramName, this.envprops, 
/*      */ 
/*      */ 
/*      */         
/* 2896 */         fullyQualifiedName(paramName));
/*      */   }
/*      */   
/*      */   protected void processReturnCode(LdapResult paramLdapResult, Name paramName1, Object paramObject, Name paramName2, Hashtable<?, ?> paramHashtable, String paramString) throws NamingException {
/*      */     PartialResultException partialResultException;
/*      */     LdapReferralException ldapReferralException1;
/*      */     InvalidNameException invalidNameException;
/* 2903 */     String str = LdapClient.getErrorMessage(paramLdapResult.status, paramLdapResult.errorMessage);
/*      */     
/* 2905 */     LdapReferralException ldapReferralException2 = null;
/*      */     
/* 2907 */     switch (paramLdapResult.status)
/*      */     
/*      */     { 
/*      */       
/*      */       case 0:
/* 2912 */         if (paramLdapResult.referrals != null) {
/*      */           
/* 2914 */           str = "Unprocessed Continuation Reference(s)";
/*      */           
/* 2916 */           if (this.handleReferrals == 3) {
/* 2917 */             partialResultException = new PartialResultException(str);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 2922 */             int i = paramLdapResult.referrals.size();
/* 2923 */             LdapReferralException ldapReferralException3 = null;
/* 2924 */             LdapReferralException ldapReferralException4 = null;
/*      */             
/* 2926 */             str = "Continuation Reference";
/*      */ 
/*      */             
/* 2929 */             for (byte b = 0; b < i; b++) {
/*      */               
/* 2931 */               ldapReferralException2 = new LdapReferralException(paramName1, paramObject, paramName2, str, paramHashtable, paramString, this.handleReferrals, this.reqCtls);
/*      */ 
/*      */               
/* 2934 */               ldapReferralException2.setReferralInfo(paramLdapResult.referrals.elementAt(b), true);
/*      */               
/* 2936 */               if (this.hopCount > 1) {
/* 2937 */                 ldapReferralException2.setHopCount(this.hopCount);
/*      */               }
/*      */               
/* 2940 */               if (ldapReferralException3 == null) {
/* 2941 */                 ldapReferralException3 = ldapReferralException4 = ldapReferralException2;
/*      */               } else {
/* 2943 */                 ldapReferralException4.nextReferralEx = ldapReferralException2;
/* 2944 */                 ldapReferralException4 = ldapReferralException2;
/*      */               } 
/*      */             } 
/* 2947 */             paramLdapResult.referrals = null;
/*      */             
/* 2949 */             if (paramLdapResult.refEx == null) {
/* 2950 */               paramLdapResult.refEx = ldapReferralException3;
/*      */             } else {
/*      */               
/* 2953 */               ldapReferralException4 = paramLdapResult.refEx;
/*      */               
/* 2955 */               while (ldapReferralException4.nextReferralEx != null) {
/* 2956 */                 ldapReferralException4 = ldapReferralException4.nextReferralEx;
/*      */               }
/* 2958 */               ldapReferralException4.nextReferralEx = ldapReferralException3;
/*      */             } 
/*      */ 
/*      */             
/* 2962 */             if (this.hopCount > this.referralHopLimit) {
/* 2963 */               LimitExceededException limitExceededException = new LimitExceededException("Referral limit exceeded");
/*      */               
/* 2965 */               limitExceededException.setRootCause(ldapReferralException2);
/* 2966 */               throw limitExceededException;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */         } else {
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3103 */         partialResultException.setResolvedName(paramName1);
/* 3104 */         partialResultException.setResolvedObj(paramObject);
/* 3105 */         partialResultException.setRemainingName(paramName2);
/* 3106 */         throw partialResultException;case 10: if (this.handleReferrals == 3) { partialResultException = new PartialResultException(str); } else { Vector<?> vector; ldapReferralException2 = new LdapReferralException(paramName1, paramObject, paramName2, str, paramHashtable, paramString, this.handleReferrals, this.reqCtls); if (paramLdapResult.referrals == null) { vector = null; } else if (this.handleReferrals == 4) { vector = new Vector(); for (String str1 : paramLdapResult.referrals.elementAt(0)) { if (str1.startsWith("ldap:")) vector.add(str1);  }  if (vector.isEmpty()) vector = null;  } else { vector = paramLdapResult.referrals.elementAt(0); }  ldapReferralException2.setReferralInfo(vector, false); if (this.hopCount > 1) ldapReferralException2.setHopCount(this.hopCount);  if (this.hopCount > this.referralHopLimit) { LimitExceededException limitExceededException2 = new LimitExceededException("Referral limit exceeded"); limitExceededException2.setRootCause(ldapReferralException2); LimitExceededException limitExceededException1 = limitExceededException2; } else { ldapReferralException1 = ldapReferralException2; }  }  ldapReferralException1.setResolvedName(paramName1); ldapReferralException1.setResolvedObj(paramObject); ldapReferralException1.setRemainingName(paramName2); throw ldapReferralException1;case 9: if (this.handleReferrals == 3) { PartialResultException partialResultException1 = new PartialResultException(str); } else { if (paramLdapResult.errorMessage != null && !paramLdapResult.errorMessage.equals("")) { paramLdapResult.referrals = extractURLs(paramLdapResult.errorMessage); } else { PartialResultException partialResultException1 = new PartialResultException(str); partialResultException1.setResolvedName(paramName1); partialResultException1.setResolvedObj(paramObject); partialResultException1.setRemainingName(paramName2); throw partialResultException1; }  ldapReferralException2 = new LdapReferralException(paramName1, paramObject, paramName2, str, paramHashtable, paramString, this.handleReferrals, this.reqCtls); if (this.hopCount > 1) ldapReferralException2.setHopCount(this.hopCount);  if ((paramLdapResult.entries == null || paramLdapResult.entries.isEmpty()) && paramLdapResult.referrals != null && paramLdapResult.referrals.size() == 1) { ldapReferralException2.setReferralInfo(paramLdapResult.referrals, false); if (this.hopCount > this.referralHopLimit) { LimitExceededException limitExceededException2 = new LimitExceededException("Referral limit exceeded"); limitExceededException2.setRootCause(ldapReferralException2); LimitExceededException limitExceededException1 = limitExceededException2; } else { ldapReferralException1 = ldapReferralException2; }  } else { ldapReferralException2.setReferralInfo(paramLdapResult.referrals, true); paramLdapResult.refEx = ldapReferralException2; return; }  }  ldapReferralException1.setResolvedName(paramName1); ldapReferralException1.setResolvedObj(paramObject); ldapReferralException1.setRemainingName(paramName2); throw ldapReferralException1;case 34: case 64: if (paramName2 != null) { invalidNameException = new InvalidNameException(paramName2.toString() + ": " + str); } else { invalidNameException = new InvalidNameException(str); }  invalidNameException.setResolvedName(paramName1); invalidNameException.setResolvedObj(paramObject); invalidNameException.setRemainingName(paramName2); throw invalidNameException; }  NamingException namingException = mapErrorCode(paramLdapResult.status, paramLdapResult.errorMessage); namingException.setResolvedName(paramName1); namingException.setResolvedObj(paramObject); namingException.setRemainingName(paramName2); throw namingException;
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
/*      */   public static NamingException mapErrorCode(int paramInt, String paramString) {
/* 3121 */     if (paramInt == 0) {
/* 3122 */       return null;
/*      */     }
/* 3124 */     NamingException namingException = null;
/* 3125 */     String str = LdapClient.getErrorMessage(paramInt, paramString);
/*      */     
/* 3127 */     switch (paramInt)
/*      */     
/*      */     { case 36:
/* 3130 */         namingException = new NamingException(str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3260 */         return namingException;case 33: namingException = new NamingException(str); return namingException;case 20: namingException = new AttributeInUseException(str); return namingException;case 7: case 8: case 13: case 48: namingException = new AuthenticationNotSupportedException(str); return namingException;case 68: namingException = new NameAlreadyBoundException(str); return namingException;case 14: case 49: namingException = new AuthenticationException(str); return namingException;case 18: namingException = new InvalidSearchFilterException(str); return namingException;case 50: namingException = new NoPermissionException(str); return namingException;case 19: case 21: namingException = new InvalidAttributeValueException(str); return namingException;case 54: namingException = new NamingException(str); return namingException;case 16: namingException = new NoSuchAttributeException(str); return namingException;case 32: namingException = new NameNotFoundException(str); return namingException;case 65: case 67: case 69: namingException = new SchemaViolationException(str); return namingException;case 66: namingException = new ContextNotEmptyException(str); return namingException;case 1: namingException = new NamingException(str); return namingException;case 80: namingException = new NamingException(str); return namingException;case 2: namingException = new CommunicationException(str); return namingException;case 4: namingException = new SizeLimitExceededException(str); return namingException;case 3: namingException = new TimeLimitExceededException(str); return namingException;case 12: namingException = new OperationNotSupportedException(str); return namingException;case 51: case 52: namingException = new ServiceUnavailableException(str); return namingException;case 17: namingException = new InvalidAttributeIdentifierException(str); return namingException;case 53: namingException = new OperationNotSupportedException(str); return namingException;case 5: case 6: case 35: namingException = new NamingException(str); return namingException;case 11: namingException = new LimitExceededException(str); return namingException;case 10: namingException = new NamingException(str); return namingException;case 9: namingException = new NamingException(str); return namingException;case 34: case 64: namingException = new InvalidNameException(str); return namingException; }  namingException = new NamingException(str); return namingException;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedResponse extendedOperation(ExtendedRequest paramExtendedRequest) throws NamingException {
/* 3268 */     boolean bool = paramExtendedRequest.getID().equals("1.3.6.1.4.1.1466.20037");
/* 3269 */     ensureOpen(bool);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3274 */       LdapResult ldapResult = this.clnt.extendedOp(paramExtendedRequest.getID(), paramExtendedRequest.getEncodedValue(), this.reqCtls, bool);
/*      */       
/* 3276 */       this.respCtls = ldapResult.resControls;
/*      */       
/* 3278 */       if (ldapResult.status != 0) {
/* 3279 */         processReturnCode(ldapResult, new CompositeName());
/*      */       }
/*      */ 
/*      */       
/* 3283 */       boolean bool1 = (ldapResult.extensionValue == null) ? false : ldapResult.extensionValue.length;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3288 */       ExtendedResponse extendedResponse = paramExtendedRequest.createExtendedResponse(ldapResult.extensionId, ldapResult.extensionValue, 0, bool1);
/*      */ 
/*      */       
/* 3291 */       if (extendedResponse instanceof StartTlsResponseImpl) {
/*      */ 
/*      */         
/* 3294 */         String str = (this.envprops != null) ? (String)this.envprops.get("com.sun.jndi.ldap.domainname") : null;
/* 3295 */         ((StartTlsResponseImpl)extendedResponse).setConnection(this.clnt.conn, str);
/*      */       } 
/* 3297 */       return extendedResponse;
/*      */     }
/* 3299 */     catch (LdapReferralException ldapReferralException) {
/*      */       
/* 3301 */       if (this.handleReferrals == 2) {
/* 3302 */         throw ldapReferralException;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/* 3308 */         LdapReferralContext ldapReferralContext = (LdapReferralContext)ldapReferralException.getReferralContext(this.envprops, this.bindCtls);
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3313 */           return ldapReferralContext.extendedOperation(paramExtendedRequest);
/*      */         }
/* 3315 */         catch (LdapReferralException ldapReferralException1) {
/* 3316 */           ldapReferralException = ldapReferralException1;
/*      */         
/*      */         }
/*      */         finally {
/*      */           
/* 3321 */           ldapReferralContext.close();
/*      */         }
/*      */       
/*      */       } 
/* 3325 */     } catch (IOException iOException) {
/* 3326 */       CommunicationException communicationException = new CommunicationException(iOException.getMessage());
/* 3327 */       communicationException.setRootCause(iOException);
/* 3328 */       throw communicationException;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRequestControls(Control[] paramArrayOfControl) throws NamingException {
/* 3333 */     if (this.handleReferrals == 3) {
/* 3334 */       this.reqCtls = addControl(paramArrayOfControl, manageReferralControl);
/*      */     } else {
/* 3336 */       this.reqCtls = cloneControls(paramArrayOfControl);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Control[] getRequestControls() throws NamingException {
/* 3341 */     return cloneControls(this.reqCtls);
/*      */   }
/*      */   
/*      */   public Control[] getConnectControls() throws NamingException {
/* 3345 */     return cloneControls(this.bindCtls);
/*      */   }
/*      */   
/*      */   public Control[] getResponseControls() throws NamingException {
/* 3349 */     return (this.respCtls != null) ? convertControls(this.respCtls) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Control[] convertControls(Vector<Control> paramVector) throws NamingException {
/* 3357 */     int i = paramVector.size();
/*      */     
/* 3359 */     if (i == 0) {
/* 3360 */       return null;
/*      */     }
/*      */     
/* 3363 */     Control[] arrayOfControl = new Control[i];
/*      */     
/* 3365 */     for (byte b = 0; b < i; b++) {
/*      */       
/* 3367 */       arrayOfControl[b] = myResponseControlFactory.getControlInstance(paramVector
/* 3368 */           .elementAt(b));
/*      */ 
/*      */       
/* 3371 */       if (arrayOfControl[b] == null) {
/* 3372 */         arrayOfControl[b] = ControlFactory.getControlInstance(paramVector
/* 3373 */             .elementAt(b), this, this.envprops);
/*      */       }
/*      */     } 
/* 3376 */     return arrayOfControl;
/*      */   }
/*      */   
/*      */   private static Control[] addControl(Control[] paramArrayOfControl, Control paramControl) {
/* 3380 */     if (paramArrayOfControl == null) {
/* 3381 */       return new Control[] { paramControl };
/*      */     }
/*      */ 
/*      */     
/* 3385 */     int i = findControl(paramArrayOfControl, paramControl);
/* 3386 */     if (i != -1) {
/* 3387 */       return paramArrayOfControl;
/*      */     }
/*      */     
/* 3390 */     Control[] arrayOfControl = new Control[paramArrayOfControl.length + 1];
/* 3391 */     System.arraycopy(paramArrayOfControl, 0, arrayOfControl, 0, paramArrayOfControl.length);
/* 3392 */     arrayOfControl[paramArrayOfControl.length] = paramControl;
/* 3393 */     return arrayOfControl;
/*      */   }
/*      */   
/*      */   private static int findControl(Control[] paramArrayOfControl, Control paramControl) {
/* 3397 */     for (byte b = 0; b < paramArrayOfControl.length; b++) {
/* 3398 */       if (paramArrayOfControl[b] == paramControl) {
/* 3399 */         return b;
/*      */       }
/*      */     } 
/* 3402 */     return -1;
/*      */   }
/*      */   
/*      */   private static Control[] removeControl(Control[] paramArrayOfControl, Control paramControl) {
/* 3406 */     if (paramArrayOfControl == null) {
/* 3407 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 3411 */     int i = findControl(paramArrayOfControl, paramControl);
/* 3412 */     if (i == -1) {
/* 3413 */       return paramArrayOfControl;
/*      */     }
/*      */ 
/*      */     
/* 3417 */     Control[] arrayOfControl = new Control[paramArrayOfControl.length - 1];
/* 3418 */     System.arraycopy(paramArrayOfControl, 0, arrayOfControl, 0, i);
/* 3419 */     System.arraycopy(paramArrayOfControl, i + 1, arrayOfControl, i, paramArrayOfControl.length - i - 1);
/*      */     
/* 3421 */     return arrayOfControl;
/*      */   }
/*      */   
/*      */   private static Control[] cloneControls(Control[] paramArrayOfControl) {
/* 3425 */     if (paramArrayOfControl == null) {
/* 3426 */       return null;
/*      */     }
/* 3428 */     Control[] arrayOfControl = new Control[paramArrayOfControl.length];
/* 3429 */     System.arraycopy(paramArrayOfControl, 0, arrayOfControl, 0, paramArrayOfControl.length);
/* 3430 */     return arrayOfControl;
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
/*      */   public void addNamingListener(Name paramName, int paramInt, NamingListener paramNamingListener) throws NamingException {
/* 3443 */     addNamingListener(getTargetName(paramName), paramInt, paramNamingListener);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addNamingListener(String paramString, int paramInt, NamingListener paramNamingListener) throws NamingException {
/* 3448 */     if (this.eventSupport == null)
/* 3449 */       this.eventSupport = new EventSupport(this); 
/* 3450 */     this.eventSupport.addNamingListener(getTargetName(new CompositeName(paramString)), paramInt, paramNamingListener);
/*      */ 
/*      */ 
/*      */     
/* 3454 */     if (paramNamingListener instanceof javax.naming.ldap.UnsolicitedNotificationListener && !this.unsolicited) {
/* 3455 */       addUnsolicited();
/*      */     }
/*      */   }
/*      */   
/*      */   public void removeNamingListener(NamingListener paramNamingListener) throws NamingException {
/* 3460 */     if (this.eventSupport == null) {
/*      */       return;
/*      */     }
/* 3463 */     this.eventSupport.removeNamingListener(paramNamingListener);
/*      */ 
/*      */     
/* 3466 */     if (paramNamingListener instanceof javax.naming.ldap.UnsolicitedNotificationListener && 
/* 3467 */       !this.eventSupport.hasUnsolicited()) {
/* 3468 */       removeUnsolicited();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addNamingListener(String paramString1, String paramString2, SearchControls paramSearchControls, NamingListener paramNamingListener) throws NamingException {
/* 3474 */     if (this.eventSupport == null)
/* 3475 */       this.eventSupport = new EventSupport(this); 
/* 3476 */     this.eventSupport.addNamingListener(getTargetName(new CompositeName(paramString1)), paramString2, 
/* 3477 */         cloneSearchControls(paramSearchControls), paramNamingListener);
/*      */ 
/*      */     
/* 3480 */     if (paramNamingListener instanceof javax.naming.ldap.UnsolicitedNotificationListener && !this.unsolicited) {
/* 3481 */       addUnsolicited();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addNamingListener(Name paramName, String paramString, SearchControls paramSearchControls, NamingListener paramNamingListener) throws NamingException {
/* 3487 */     addNamingListener(getTargetName(paramName), paramString, paramSearchControls, paramNamingListener);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addNamingListener(Name paramName, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls, NamingListener paramNamingListener) throws NamingException {
/* 3492 */     addNamingListener(getTargetName(paramName), paramString, paramArrayOfObject, paramSearchControls, paramNamingListener);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addNamingListener(String paramString1, String paramString2, Object[] paramArrayOfObject, SearchControls paramSearchControls, NamingListener paramNamingListener) throws NamingException {
/* 3497 */     String str = SearchFilter.format(paramString2, paramArrayOfObject);
/* 3498 */     addNamingListener(getTargetName(new CompositeName(paramString1)), str, paramSearchControls, paramNamingListener);
/*      */   }
/*      */   
/*      */   public boolean targetMustExist() {
/* 3502 */     return true;
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
/*      */   private static String getTargetName(Name paramName) throws NamingException {
/* 3514 */     if (paramName instanceof CompositeName) {
/* 3515 */       if (paramName.size() > 1) {
/* 3516 */         throw new InvalidNameException("Target cannot span multiple namespaces: " + paramName);
/*      */       }
/* 3518 */       if (paramName.isEmpty()) {
/* 3519 */         return "";
/*      */       }
/* 3521 */       return paramName.get(0);
/*      */     } 
/*      */ 
/*      */     
/* 3525 */     return paramName.toString();
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
/*      */   private void addUnsolicited() throws NamingException {
/* 3548 */     ensureOpen();
/* 3549 */     synchronized (this.eventSupport) {
/* 3550 */       this.clnt.addUnsolicited(this);
/* 3551 */       this.unsolicited = true;
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
/*      */   private void removeUnsolicited() {
/* 3573 */     if (this.eventSupport == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 3578 */     synchronized (this.eventSupport) {
/* 3579 */       if (this.unsolicited && this.clnt != null) {
/* 3580 */         this.clnt.removeUnsolicited(this);
/*      */       }
/* 3582 */       this.unsolicited = false;
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
/*      */   void fireUnsolicited(Object paramObject) {
/* 3595 */     synchronized (this.eventSupport) {
/* 3596 */       if (this.unsolicited) {
/* 3597 */         this.eventSupport.fireUnsolicited(paramObject);
/*      */         
/* 3599 */         if (paramObject instanceof NamingException)
/* 3600 */           this.unsolicited = false; 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapCtx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */