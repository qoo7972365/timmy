/*      */ package com.sun.security.auth.module;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import javax.security.auth.DestroyFailedException;
/*      */ import javax.security.auth.RefreshFailedException;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.kerberos.KerberosKey;
/*      */ import javax.security.auth.kerberos.KerberosPrincipal;
/*      */ import javax.security.auth.kerberos.KerberosTicket;
/*      */ import javax.security.auth.kerberos.KeyTab;
/*      */ import javax.security.auth.login.LoginException;
/*      */ import javax.security.auth.spi.LoginModule;
/*      */ import jdk.Exported;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.jgss.krb5.Krb5Util;
/*      */ import sun.security.krb5.Config;
/*      */ import sun.security.krb5.Credentials;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.KerberosSecrets;
/*      */ import sun.security.krb5.KrbAsReqBuilder;
/*      */ import sun.security.krb5.KrbException;
/*      */ import sun.security.krb5.PrincipalName;
/*      */ import sun.security.krb5.internal.ktab.KeyTab;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Exported
/*      */ public class Krb5LoginModule
/*      */   implements LoginModule
/*      */ {
/*      */   private Subject subject;
/*      */   private CallbackHandler callbackHandler;
/*      */   private Map<String, Object> sharedState;
/*      */   private Map<String, ?> options;
/*      */   private boolean debug = false;
/*      */   private boolean storeKey = false;
/*      */   private boolean doNotPrompt = false;
/*      */   private boolean useTicketCache = false;
/*      */   private boolean useKeyTab = false;
/*  398 */   private String ticketCacheName = null;
/*  399 */   private String keyTabName = null;
/*  400 */   private String princName = null;
/*      */   
/*      */   private boolean useFirstPass = false;
/*      */   
/*      */   private boolean tryFirstPass = false;
/*      */   
/*      */   private boolean storePass = false;
/*      */   
/*      */   private boolean clearPass = false;
/*      */   
/*      */   private boolean refreshKrb5Config = false;
/*      */   
/*      */   private boolean renewTGT = false;
/*      */   
/*      */   private boolean isInitiator = true;
/*      */   
/*      */   private boolean succeeded = false;
/*      */   
/*      */   private boolean commitSucceeded = false;
/*      */   private String username;
/*  420 */   private EncryptionKey[] encKeys = null;
/*      */   
/*  422 */   KeyTab ktab = null;
/*      */   
/*  424 */   private Credentials cred = null;
/*      */   
/*  426 */   private PrincipalName principal = null;
/*  427 */   private KerberosPrincipal kerbClientPrinc = null;
/*  428 */   private KerberosTicket kerbTicket = null;
/*  429 */   private KerberosKey[] kerbKeys = null;
/*  430 */   private StringBuffer krb5PrincName = null;
/*      */   private boolean unboundServer = false;
/*  432 */   private char[] password = null;
/*      */   private static final String NAME = "javax.security.auth.login.name";
/*      */   private static final String PWD = "javax.security.auth.login.password";
/*      */   
/*  436 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*      */       {
/*      */         public ResourceBundle run() {
/*  439 */           return ResourceBundle.getBundle("sun.security.util.AuthResources");
/*      */         }
/*      */       });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/*  471 */     this.subject = paramSubject;
/*  472 */     this.callbackHandler = paramCallbackHandler;
/*  473 */     this.sharedState = (Map)paramMap1;
/*  474 */     this.options = paramMap2;
/*      */ 
/*      */ 
/*      */     
/*  478 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
/*  479 */     this.storeKey = "true".equalsIgnoreCase((String)paramMap2.get("storeKey"));
/*  480 */     this.doNotPrompt = "true".equalsIgnoreCase((String)paramMap2
/*  481 */         .get("doNotPrompt"));
/*  482 */     this.useTicketCache = "true".equalsIgnoreCase((String)paramMap2
/*  483 */         .get("useTicketCache"));
/*  484 */     this.useKeyTab = "true".equalsIgnoreCase((String)paramMap2.get("useKeyTab"));
/*  485 */     this.ticketCacheName = (String)paramMap2.get("ticketCache");
/*  486 */     this.keyTabName = (String)paramMap2.get("keyTab");
/*  487 */     if (this.keyTabName != null) {
/*  488 */       this.keyTabName = KeyTab.normalize(this.keyTabName);
/*      */     }
/*      */     
/*  491 */     this.princName = (String)paramMap2.get("principal");
/*  492 */     this
/*  493 */       .refreshKrb5Config = "true".equalsIgnoreCase((String)paramMap2.get("refreshKrb5Config"));
/*  494 */     this
/*  495 */       .renewTGT = "true".equalsIgnoreCase((String)paramMap2.get("renewTGT"));
/*      */ 
/*      */     
/*  498 */     String str = (String)paramMap2.get("isInitiator");
/*  499 */     if (str != null)
/*      */     {
/*      */       
/*  502 */       this.isInitiator = "true".equalsIgnoreCase(str);
/*      */     }
/*      */     
/*  505 */     this
/*      */       
/*  507 */       .tryFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("tryFirstPass"));
/*  508 */     this
/*      */       
/*  510 */       .useFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("useFirstPass"));
/*  511 */     this
/*  512 */       .storePass = "true".equalsIgnoreCase((String)paramMap2.get("storePass"));
/*  513 */     this
/*  514 */       .clearPass = "true".equalsIgnoreCase((String)paramMap2.get("clearPass"));
/*  515 */     if (this.debug) {
/*  516 */       System.out.print("Debug is  " + this.debug + " storeKey " + this.storeKey + " useTicketCache " + this.useTicketCache + " useKeyTab " + this.useKeyTab + " doNotPrompt " + this.doNotPrompt + " ticketCache is " + this.ticketCacheName + " isInitiator " + this.isInitiator + " KeyTab is " + this.keyTabName + " refreshKrb5Config is " + this.refreshKrb5Config + " principal is " + this.princName + " tryFirstPass is " + this.tryFirstPass + " useFirstPass is " + this.useFirstPass + " storePass is " + this.storePass + " clearPass is " + this.clearPass + "\n");
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
/*      */ 
/*      */   
/*      */   public boolean login() throws LoginException {
/*  549 */     if (this.refreshKrb5Config) {
/*      */       try {
/*  551 */         if (this.debug) {
/*  552 */           System.out.println("Refreshing Kerberos configuration");
/*      */         }
/*  554 */         Config.refresh();
/*  555 */       } catch (KrbException krbException) {
/*  556 */         LoginException loginException = new LoginException(krbException.getMessage());
/*  557 */         loginException.initCause(krbException);
/*  558 */         throw loginException;
/*      */       } 
/*      */     }
/*      */     
/*  562 */     String str = System.getProperty("sun.security.krb5.principal");
/*  563 */     if (str != null) {
/*  564 */       this.krb5PrincName = new StringBuffer(str);
/*      */     }
/*  566 */     else if (this.princName != null) {
/*  567 */       this.krb5PrincName = new StringBuffer(this.princName);
/*      */     } 
/*      */ 
/*      */     
/*  571 */     validateConfiguration();
/*      */     
/*  573 */     if (this.krb5PrincName != null && this.krb5PrincName.toString().equals("*")) {
/*  574 */       this.unboundServer = true;
/*      */     }
/*      */     
/*  577 */     if (this.tryFirstPass) {
/*      */       try {
/*  579 */         attemptAuthentication(true);
/*  580 */         if (this.debug) {
/*  581 */           System.out.println("\t\t[Krb5LoginModule] authentication succeeded");
/*      */         }
/*  583 */         this.succeeded = true;
/*  584 */         cleanState();
/*  585 */         return true;
/*  586 */       } catch (LoginException loginException) {
/*      */         
/*  588 */         cleanState();
/*  589 */         if (this.debug) {
/*  590 */           System.out.println("\t\t[Krb5LoginModule] tryFirstPass failed with:" + loginException
/*      */               
/*  592 */               .getMessage());
/*      */         }
/*      */       } 
/*  595 */     } else if (this.useFirstPass) {
/*      */       try {
/*  597 */         attemptAuthentication(true);
/*  598 */         this.succeeded = true;
/*  599 */         cleanState();
/*  600 */         return true;
/*  601 */       } catch (LoginException loginException) {
/*      */         
/*  603 */         if (this.debug) {
/*  604 */           System.out.println("\t\t[Krb5LoginModule] authentication failed \n" + loginException
/*      */               
/*  606 */               .getMessage());
/*      */         }
/*  608 */         this.succeeded = false;
/*  609 */         cleanState();
/*  610 */         throw loginException;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  618 */       attemptAuthentication(false);
/*  619 */       this.succeeded = true;
/*  620 */       cleanState();
/*  621 */       return true;
/*  622 */     } catch (LoginException loginException) {
/*      */       
/*  624 */       if (this.debug) {
/*  625 */         System.out.println("\t\t[Krb5LoginModule] authentication failed \n" + loginException
/*      */             
/*  627 */             .getMessage());
/*      */       }
/*  629 */       this.succeeded = false;
/*  630 */       cleanState();
/*  631 */       throw loginException;
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
/*      */   private void attemptAuthentication(boolean paramBoolean) throws LoginException {
/*  648 */     if (this.krb5PrincName != null) {
/*      */       try {
/*  650 */         this
/*  651 */           .principal = new PrincipalName(this.krb5PrincName.toString(), 1);
/*      */       }
/*  653 */       catch (KrbException krbException) {
/*  654 */         LoginException loginException = new LoginException(krbException.getMessage());
/*  655 */         loginException.initCause(krbException);
/*  656 */         throw loginException;
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  661 */       if (this.useTicketCache) {
/*      */         
/*  663 */         if (this.debug)
/*  664 */           System.out.println("Acquire TGT from Cache"); 
/*  665 */         this
/*  666 */           .cred = Credentials.acquireTGTFromCache(this.principal, this.ticketCacheName);
/*      */         
/*  668 */         if (this.cred != null) {
/*  669 */           if (this.renewTGT && isOld(this.cred)) {
/*      */             
/*  671 */             Credentials credentials = renewCredentials(this.cred);
/*  672 */             if (credentials != null) {
/*  673 */               credentials.setProxy(this.cred.getProxy());
/*  674 */               this.cred = credentials;
/*      */             } 
/*      */           } 
/*  677 */           if (!isCurrent(this.cred)) {
/*      */             
/*  679 */             this.cred = null;
/*  680 */             if (this.debug) {
/*  681 */               System.out.println("Credentials are no longer valid");
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  686 */         if (this.cred != null)
/*      */         {
/*  688 */           if (this.principal == null) {
/*  689 */             this.principal = this.cred.getClient();
/*      */           }
/*      */         }
/*  692 */         if (this.debug) {
/*  693 */           System.out.println("Principal is " + this.principal);
/*  694 */           if (this.cred == null) {
/*  695 */             System.out
/*  696 */               .println("null credentials from Ticket Cache");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  704 */       if (this.cred == null) {
/*      */         KrbAsReqBuilder krbAsReqBuilder;
/*      */         
/*  707 */         if (this.principal == null) {
/*  708 */           promptForName(paramBoolean);
/*  709 */           this
/*  710 */             .principal = new PrincipalName(this.krb5PrincName.toString(), 1);
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
/*  733 */         if (this.useKeyTab) {
/*  734 */           if (!this.unboundServer) {
/*      */             
/*  736 */             KerberosPrincipal kerberosPrincipal = new KerberosPrincipal(this.principal.getName());
/*  737 */             this
/*      */               
/*  739 */               .ktab = (this.keyTabName == null) ? KeyTab.getInstance(kerberosPrincipal) : KeyTab.getInstance(kerberosPrincipal, new File(this.keyTabName));
/*      */           } else {
/*  741 */             this
/*      */               
/*  743 */               .ktab = (this.keyTabName == null) ? KeyTab.getUnboundInstance() : KeyTab.getUnboundInstance(new File(this.keyTabName));
/*      */           } 
/*  745 */           if (this.isInitiator && (
/*  746 */             Krb5Util.keysFromJavaxKeyTab(this.ktab, this.principal)).length == 0) {
/*      */             
/*  748 */             this.ktab = null;
/*  749 */             if (this.debug) {
/*  750 */               System.out
/*  751 */                 .println("Key for the principal " + this.principal + " not available in " + ((this.keyTabName == null) ? "default key tab" : this.keyTabName));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  763 */         if (this.ktab == null) {
/*  764 */           promptForPass(paramBoolean);
/*  765 */           krbAsReqBuilder = new KrbAsReqBuilder(this.principal, this.password);
/*  766 */           if (this.isInitiator)
/*      */           {
/*      */ 
/*      */             
/*  770 */             this.cred = krbAsReqBuilder.action().getCreds();
/*      */           }
/*  772 */           if (this.storeKey) {
/*  773 */             this.encKeys = krbAsReqBuilder.getKeys(this.isInitiator);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  778 */           krbAsReqBuilder = new KrbAsReqBuilder(this.principal, this.ktab);
/*  779 */           if (this.isInitiator) {
/*  780 */             this.cred = krbAsReqBuilder.action().getCreds();
/*      */           }
/*      */         } 
/*  783 */         krbAsReqBuilder.destroy();
/*      */         
/*  785 */         if (this.debug) {
/*  786 */           System.out.println("principal is " + this.principal);
/*  787 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  788 */           if (this.ktab != null) {
/*  789 */             System.out.println("Will use keytab");
/*  790 */           } else if (this.storeKey) {
/*  791 */             for (byte b = 0; b < this.encKeys.length; b++) {
/*  792 */               System.out.println("EncryptionKey: keyType=" + this.encKeys[b]
/*  793 */                   .getEType() + " keyBytes (hex dump)=" + hexDumpEncoder
/*      */                   
/*  795 */                   .encodeBuffer(this.encKeys[b].getBytes()));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  801 */         if (this.isInitiator && this.cred == null) {
/*  802 */           throw new LoginException("TGT Can not be obtained from the KDC ");
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  807 */     catch (KrbException krbException) {
/*  808 */       LoginException loginException = new LoginException(krbException.getMessage());
/*  809 */       loginException.initCause(krbException);
/*  810 */       throw loginException;
/*  811 */     } catch (IOException iOException) {
/*  812 */       LoginException loginException = new LoginException(iOException.getMessage());
/*  813 */       loginException.initCause(iOException);
/*  814 */       throw loginException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void promptForName(boolean paramBoolean) throws LoginException {
/*  820 */     this.krb5PrincName = new StringBuffer("");
/*  821 */     if (paramBoolean) {
/*      */       
/*  823 */       this.username = (String)this.sharedState.get("javax.security.auth.login.name");
/*  824 */       if (this.debug) {
/*  825 */         System.out
/*  826 */           .println("username from shared state is " + this.username + "\n");
/*      */       }
/*  828 */       if (this.username == null) {
/*  829 */         System.out
/*  830 */           .println("username from shared state is null\n");
/*  831 */         throw new LoginException("Username can not be obtained from sharedstate ");
/*      */       } 
/*      */       
/*  834 */       if (this.debug) {
/*  835 */         System.out
/*  836 */           .println("username from shared state is " + this.username + "\n");
/*      */       }
/*  838 */       if (this.username != null && this.username.length() > 0) {
/*  839 */         this.krb5PrincName.insert(0, this.username);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  844 */     if (this.doNotPrompt) {
/*  845 */       throw new LoginException("Unable to obtain Principal Name for authentication ");
/*      */     }
/*      */     
/*  848 */     if (this.callbackHandler == null) {
/*  849 */       throw new LoginException("No CallbackHandler available to garner authentication information from the user");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  854 */       String str = System.getProperty("user.name");
/*      */       
/*  856 */       Callback[] arrayOfCallback = new Callback[1];
/*      */       
/*  858 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Kerberos.username.defUsername."));
/*      */       
/*  860 */       Object[] arrayOfObject = { str };
/*  861 */       arrayOfCallback[0] = new NameCallback(messageFormat.format(arrayOfObject));
/*  862 */       this.callbackHandler.handle(arrayOfCallback);
/*  863 */       this.username = ((NameCallback)arrayOfCallback[0]).getName();
/*  864 */       if (this.username == null || this.username.length() == 0)
/*  865 */         this.username = str; 
/*  866 */       this.krb5PrincName.insert(0, this.username);
/*      */     }
/*  868 */     catch (IOException iOException) {
/*  869 */       throw new LoginException(iOException.getMessage());
/*  870 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/*  871 */       throw new LoginException(unsupportedCallbackException
/*  872 */           .getMessage() + " not available to garner  authentication information  from the user");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void promptForPass(boolean paramBoolean) throws LoginException {
/*  883 */     if (paramBoolean) {
/*      */       
/*  885 */       this.password = (char[])this.sharedState.get("javax.security.auth.login.password");
/*  886 */       if (this.password == null) {
/*  887 */         if (this.debug) {
/*  888 */           System.out
/*  889 */             .println("Password from shared state is null");
/*      */         }
/*  891 */         throw new LoginException("Password can not be obtained from sharedstate ");
/*      */       } 
/*      */       
/*  894 */       if (this.debug) {
/*  895 */         System.out
/*  896 */           .println("password is " + new String(this.password));
/*      */       }
/*      */       return;
/*      */     } 
/*  900 */     if (this.doNotPrompt) {
/*  901 */       throw new LoginException("Unable to obtain password from user\n");
/*      */     }
/*      */     
/*  904 */     if (this.callbackHandler == null) {
/*  905 */       throw new LoginException("No CallbackHandler available to garner authentication information from the user");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  910 */       Callback[] arrayOfCallback = new Callback[1];
/*  911 */       String str = this.krb5PrincName.toString();
/*      */       
/*  913 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Kerberos.password.for.username."));
/*      */       
/*  915 */       Object[] arrayOfObject = { str };
/*  916 */       arrayOfCallback[0] = new PasswordCallback(messageFormat
/*  917 */           .format(arrayOfObject), false);
/*      */       
/*  919 */       this.callbackHandler.handle(arrayOfCallback);
/*      */       
/*  921 */       char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[0]).getPassword();
/*  922 */       if (arrayOfChar == null) {
/*  923 */         throw new LoginException("No password provided");
/*      */       }
/*  925 */       this.password = new char[arrayOfChar.length];
/*  926 */       System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
/*      */       
/*  928 */       ((PasswordCallback)arrayOfCallback[0]).clearPassword();
/*      */ 
/*      */ 
/*      */       
/*  932 */       for (byte b = 0; b < arrayOfChar.length; b++)
/*  933 */         arrayOfChar[b] = ' '; 
/*  934 */       arrayOfChar = null;
/*  935 */       if (this.debug) {
/*  936 */         System.out.println("\t\t[Krb5LoginModule] user entered username: " + this.krb5PrincName);
/*      */ 
/*      */         
/*  939 */         System.out.println();
/*      */       } 
/*  941 */     } catch (IOException iOException) {
/*  942 */       throw new LoginException(iOException.getMessage());
/*  943 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/*  944 */       throw new LoginException(unsupportedCallbackException.getMessage() + " not available to garner  authentication information from the user");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateConfiguration() throws LoginException {
/*  953 */     if (this.doNotPrompt && !this.useTicketCache && !this.useKeyTab && !this.tryFirstPass && !this.useFirstPass)
/*      */     {
/*  955 */       throw new LoginException("Configuration Error - either doNotPrompt should be  false or at least one of useTicketCache,  useKeyTab, tryFirstPass and useFirstPass should be true");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  961 */     if (this.ticketCacheName != null && !this.useTicketCache) {
/*  962 */       throw new LoginException("Configuration Error  - useTicketCache should be set to true to use the ticket cache" + this.ticketCacheName);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  967 */     if ((((this.keyTabName != null) ? 1 : 0) & (!this.useKeyTab ? 1 : 0)) != 0) {
/*  968 */       throw new LoginException("Configuration Error - useKeyTab should be set to true to use the keytab" + this.keyTabName);
/*      */     }
/*      */     
/*  971 */     if (this.storeKey && this.doNotPrompt && !this.useKeyTab && !this.tryFirstPass && !this.useFirstPass)
/*      */     {
/*  973 */       throw new LoginException("Configuration Error - either doNotPrompt should be set to  false or at least one of tryFirstPass, useFirstPass or useKeyTab must be set to true for storeKey option");
/*      */     }
/*      */ 
/*      */     
/*  977 */     if (this.renewTGT && !this.useTicketCache) {
/*  978 */       throw new LoginException("Configuration Error - either useTicketCache should be  true or renewTGT should be false");
/*      */     }
/*      */ 
/*      */     
/*  982 */     if (this.krb5PrincName != null && this.krb5PrincName.toString().equals("*") && 
/*  983 */       this.isInitiator) {
/*  984 */       throw new LoginException("Configuration Error - principal cannot be * when isInitiator is true");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isCurrent(Credentials paramCredentials) {
/*  993 */     Date date = paramCredentials.getEndTime();
/*  994 */     if (date != null) {
/*  995 */       return (System.currentTimeMillis() <= date.getTime());
/*      */     }
/*  997 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isOld(Credentials paramCredentials) {
/* 1002 */     Date date = paramCredentials.getEndTime();
/* 1003 */     if (date != null) {
/* 1004 */       Date date1 = paramCredentials.getAuthTime();
/* 1005 */       long l = System.currentTimeMillis();
/* 1006 */       if (date1 != null)
/*      */       {
/* 1008 */         return (l - date1.getTime() > date.getTime() - l);
/*      */       }
/*      */       
/* 1011 */       return (l <= date.getTime() - 7200000L);
/*      */     } 
/*      */     
/* 1014 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private Credentials renewCredentials(Credentials paramCredentials) {
/*      */     Credentials credentials;
/*      */     try {
/* 1021 */       if (!paramCredentials.isRenewable()) {
/* 1022 */         throw new RefreshFailedException("This ticket is not renewable");
/*      */       }
/* 1024 */       if (paramCredentials.getRenewTill() == null)
/*      */       {
/* 1026 */         return paramCredentials;
/*      */       }
/* 1028 */       if (System.currentTimeMillis() > this.cred.getRenewTill().getTime()) {
/* 1029 */         throw new RefreshFailedException("This ticket is past its last renewal time.");
/*      */       }
/* 1031 */       credentials = paramCredentials.renew();
/* 1032 */       if (this.debug)
/* 1033 */         System.out.println("Renewed Kerberos Ticket"); 
/* 1034 */     } catch (Exception exception) {
/* 1035 */       credentials = null;
/* 1036 */       if (this.debug)
/* 1037 */         System.out.println("Ticket could not be renewed : " + exception
/* 1038 */             .getMessage()); 
/*      */     } 
/* 1040 */     return credentials;
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
/*      */   public boolean commit() throws LoginException {
/* 1074 */     if (!this.succeeded) {
/* 1075 */       return false;
/*      */     }
/*      */     
/* 1078 */     if (this.isInitiator && this.cred == null) {
/* 1079 */       this.succeeded = false;
/* 1080 */       throw new LoginException("Null Client Credential");
/*      */     } 
/*      */     
/* 1083 */     if (this.subject.isReadOnly()) {
/* 1084 */       cleanKerberosCred();
/* 1085 */       throw new LoginException("Subject is Readonly");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1095 */     Set<Object> set = this.subject.getPrivateCredentials();
/* 1096 */     Set<Principal> set1 = this.subject.getPrincipals();
/* 1097 */     this.kerbClientPrinc = new KerberosPrincipal(this.principal.getName());
/*      */ 
/*      */     
/* 1100 */     if (this.isInitiator) {
/* 1101 */       this.kerbTicket = Krb5Util.credsToTicket(this.cred);
/* 1102 */       if (this.cred.getProxy() != null) {
/* 1103 */         KerberosSecrets.getJavaxSecurityAuthKerberosAccess()
/* 1104 */           .kerberosTicketSetProxy(this.kerbTicket, Krb5Util.credsToTicket(this.cred.getProxy()));
/*      */       }
/*      */     } 
/*      */     
/* 1108 */     if (this.storeKey && this.encKeys != null) {
/* 1109 */       if (this.encKeys.length == 0) {
/* 1110 */         this.succeeded = false;
/* 1111 */         throw new LoginException("Null Server Key ");
/*      */       } 
/*      */       
/* 1114 */       this.kerbKeys = new KerberosKey[this.encKeys.length];
/* 1115 */       for (byte b = 0; b < this.encKeys.length; b++) {
/* 1116 */         Integer integer = this.encKeys[b].getKeyVersionNumber();
/* 1117 */         this.kerbKeys[b] = new KerberosKey(this.kerbClientPrinc, this.encKeys[b]
/* 1118 */             .getBytes(), this.encKeys[b]
/* 1119 */             .getEType(), (integer == null) ? 0 : integer
/*      */             
/* 1121 */             .intValue());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1129 */     if (!this.unboundServer && 
/* 1130 */       !set1.contains(this.kerbClientPrinc)) {
/* 1131 */       set1.add(this.kerbClientPrinc);
/*      */     }
/*      */ 
/*      */     
/* 1135 */     if (this.kerbTicket != null && 
/* 1136 */       !set.contains(this.kerbTicket)) {
/* 1137 */       set.add(this.kerbTicket);
/*      */     }
/*      */     
/* 1140 */     if (this.storeKey) {
/* 1141 */       if (this.encKeys == null) {
/* 1142 */         if (this.ktab != null) {
/* 1143 */           if (!set.contains(this.ktab)) {
/* 1144 */             set.add(this.ktab);
/*      */           }
/*      */         } else {
/* 1147 */           this.succeeded = false;
/* 1148 */           throw new LoginException("No key to store");
/*      */         } 
/*      */       } else {
/* 1151 */         for (byte b = 0; b < this.kerbKeys.length; b++) {
/* 1152 */           if (!set.contains(this.kerbKeys[b])) {
/* 1153 */             set.add(this.kerbKeys[b]);
/*      */           }
/* 1155 */           this.encKeys[b].destroy();
/* 1156 */           this.encKeys[b] = null;
/* 1157 */           if (this.debug) {
/* 1158 */             System.out.println("Added server's key" + this.kerbKeys[b]);
/*      */             
/* 1160 */             System.out.println("\t\t[Krb5LoginModule] added Krb5Principal  " + this.kerbClientPrinc
/*      */                 
/* 1162 */                 .toString() + " to Subject");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1169 */     this.commitSucceeded = true;
/* 1170 */     if (this.debug)
/* 1171 */       System.out.println("Commit Succeeded \n"); 
/* 1172 */     return true;
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
/*      */   public boolean abort() throws LoginException {
/* 1195 */     if (!this.succeeded)
/* 1196 */       return false; 
/* 1197 */     if (this.succeeded == true && !this.commitSucceeded) {
/*      */       
/* 1199 */       this.succeeded = false;
/* 1200 */       cleanKerberosCred();
/*      */     }
/*      */     else {
/*      */       
/* 1204 */       logout();
/*      */     } 
/* 1206 */     return true;
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
/*      */   public boolean logout() throws LoginException {
/* 1224 */     if (this.debug) {
/* 1225 */       System.out.println("\t\t[Krb5LoginModule]: Entering logout");
/*      */     }
/*      */ 
/*      */     
/* 1229 */     if (this.subject.isReadOnly()) {
/* 1230 */       cleanKerberosCred();
/* 1231 */       throw new LoginException("Subject is Readonly");
/*      */     } 
/*      */     
/* 1234 */     this.subject.getPrincipals().remove(this.kerbClientPrinc);
/*      */     
/* 1236 */     Iterator<Object> iterator = this.subject.getPrivateCredentials().iterator();
/* 1237 */     while (iterator.hasNext()) {
/* 1238 */       Object object = iterator.next();
/* 1239 */       if (object instanceof KerberosTicket || object instanceof KerberosKey || object instanceof KeyTab)
/*      */       {
/*      */         
/* 1242 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */     
/* 1246 */     cleanKerberosCred();
/*      */     
/* 1248 */     this.succeeded = false;
/* 1249 */     this.commitSucceeded = false;
/* 1250 */     if (this.debug) {
/* 1251 */       System.out.println("\t\t[Krb5LoginModule]: logged out Subject");
/*      */     }
/*      */     
/* 1254 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanKerberosCred() throws LoginException {
/*      */     try {
/* 1263 */       if (this.kerbTicket != null)
/* 1264 */         this.kerbTicket.destroy(); 
/* 1265 */       if (this.kerbKeys != null) {
/* 1266 */         for (byte b = 0; b < this.kerbKeys.length; b++) {
/* 1267 */           this.kerbKeys[b].destroy();
/*      */         }
/*      */       }
/* 1270 */     } catch (DestroyFailedException destroyFailedException) {
/* 1271 */       throw new LoginException("Destroy Failed on Kerberos Private Credentials");
/*      */     } 
/*      */     
/* 1274 */     this.kerbTicket = null;
/* 1275 */     this.kerbKeys = null;
/* 1276 */     this.kerbClientPrinc = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanState() {
/* 1286 */     if (this.succeeded) {
/* 1287 */       if (this.storePass && 
/* 1288 */         !this.sharedState.containsKey("javax.security.auth.login.name") && 
/* 1289 */         !this.sharedState.containsKey("javax.security.auth.login.password")) {
/* 1290 */         this.sharedState.put("javax.security.auth.login.name", this.username);
/* 1291 */         this.sharedState.put("javax.security.auth.login.password", this.password);
/*      */       } 
/*      */     } else {
/*      */       
/* 1295 */       this.encKeys = null;
/* 1296 */       this.ktab = null;
/* 1297 */       this.principal = null;
/*      */     } 
/* 1299 */     this.username = null;
/* 1300 */     this.password = null;
/* 1301 */     if (this.krb5PrincName != null && this.krb5PrincName.length() != 0)
/* 1302 */       this.krb5PrincName.delete(0, this.krb5PrincName.length()); 
/* 1303 */     this.krb5PrincName = null;
/* 1304 */     if (this.clearPass) {
/* 1305 */       this.sharedState.remove("javax.security.auth.login.name");
/* 1306 */       this.sharedState.remove("javax.security.auth.login.password");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/security/auth/module/Krb5LoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */