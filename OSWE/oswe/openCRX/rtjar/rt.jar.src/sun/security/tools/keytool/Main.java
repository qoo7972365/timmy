/*      */ package sun.security.tools.keytool;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.security.AlgorithmParameters;
/*      */ import java.security.CodeSigner;
/*      */ import java.security.CryptoPrimitive;
/*      */ import java.security.Key;
/*      */ import java.security.KeyStore;
/*      */ import java.security.KeyStoreException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivateKey;
/*      */ import java.security.Provider;
/*      */ import java.security.PublicKey;
/*      */ import java.security.Signature;
/*      */ import java.security.Timestamp;
/*      */ import java.security.UnrecoverableEntryException;
/*      */ import java.security.UnrecoverableKeyException;
/*      */ import java.security.cert.CRL;
/*      */ import java.security.cert.CertStore;
/*      */ import java.security.cert.CertStoreException;
/*      */ import java.security.cert.Certificate;
/*      */ import java.security.cert.CertificateException;
/*      */ import java.security.cert.CertificateFactory;
/*      */ import java.security.cert.X509CRL;
/*      */ import java.security.cert.X509CRLEntry;
/*      */ import java.security.cert.X509CRLSelector;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.text.Collator;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Base64;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Enumeration;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Random;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.jar.JarEntry;
/*      */ import java.util.jar.JarFile;
/*      */ import javax.crypto.KeyGenerator;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.SecretKeyFactory;
/*      */ import javax.crypto.spec.PBEKeySpec;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.security.pkcs.PKCS9Attribute;
/*      */ import sun.security.pkcs10.PKCS10;
/*      */ import sun.security.pkcs10.PKCS10Attribute;
/*      */ import sun.security.provider.certpath.CertStoreHelper;
/*      */ import sun.security.tools.KeyStoreUtil;
/*      */ import sun.security.tools.PathList;
/*      */ import sun.security.util.DisabledAlgorithmConstraints;
/*      */ import sun.security.util.KeyUtil;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.util.Password;
/*      */ import sun.security.util.Pem;
/*      */ import sun.security.util.SecurityProviderConstants;
/*      */ import sun.security.x509.AccessDescription;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ import sun.security.x509.BasicConstraintsExtension;
/*      */ import sun.security.x509.CRLDistributionPointsExtension;
/*      */ import sun.security.x509.CRLExtensions;
/*      */ import sun.security.x509.CertificateExtensions;
/*      */ import sun.security.x509.CertificateSerialNumber;
/*      */ import sun.security.x509.CertificateValidity;
/*      */ import sun.security.x509.CertificateVersion;
/*      */ import sun.security.x509.DNSName;
/*      */ import sun.security.x509.DistributionPoint;
/*      */ import sun.security.x509.ExtendedKeyUsageExtension;
/*      */ import sun.security.x509.Extension;
/*      */ import sun.security.x509.GeneralName;
/*      */ import sun.security.x509.GeneralNames;
/*      */ import sun.security.x509.IPAddressName;
/*      */ import sun.security.x509.IssuerAlternativeNameExtension;
/*      */ import sun.security.x509.KeyIdentifier;
/*      */ import sun.security.x509.KeyUsageExtension;
/*      */ import sun.security.x509.OIDName;
/*      */ import sun.security.x509.PKIXExtensions;
/*      */ import sun.security.x509.RFC822Name;
/*      */ import sun.security.x509.URIName;
/*      */ import sun.security.x509.X500Name;
/*      */ import sun.security.x509.X509CRLEntryImpl;
/*      */ import sun.security.x509.X509CRLImpl;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ import sun.security.x509.X509CertInfo;
/*      */ 
/*      */ public final class Main {
/*  110 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*      */   
/*      */   private boolean debug = false;
/*  113 */   private Command command = null;
/*  114 */   private String sigAlgName = null;
/*  115 */   private String keyAlgName = null;
/*      */   private boolean verbose = false;
/*  117 */   private int keysize = -1;
/*      */   private boolean rfc = false;
/*  119 */   private long validity = 90L;
/*  120 */   private String alias = null;
/*  121 */   private String dname = null;
/*  122 */   private String dest = null;
/*  123 */   private String filename = null;
/*  124 */   private String infilename = null;
/*  125 */   private String outfilename = null;
/*  126 */   private String srcksfname = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   private Set<Pair<String, String>> providers = null;
/*  135 */   private String storetype = null;
/*  136 */   private String srcProviderName = null;
/*  137 */   private String providerName = null;
/*  138 */   private String pathlist = null;
/*  139 */   private char[] storePass = null;
/*  140 */   private char[] storePassNew = null;
/*  141 */   private char[] keyPass = null;
/*  142 */   private char[] keyPassNew = null;
/*  143 */   private char[] newPass = null;
/*  144 */   private char[] destKeyPass = null;
/*  145 */   private char[] srckeyPass = null;
/*  146 */   private String ksfname = null;
/*  147 */   private File ksfile = null;
/*  148 */   private InputStream ksStream = null;
/*  149 */   private String sslserver = null;
/*  150 */   private String jarfile = null;
/*  151 */   private KeyStore keyStore = null;
/*      */   private boolean token = false;
/*      */   private boolean nullStream = false;
/*      */   private boolean kssave = false;
/*      */   private boolean noprompt = false;
/*      */   private boolean trustcacerts = false;
/*      */   private boolean nowarn = false;
/*      */   private boolean protectedPath = false;
/*      */   private boolean srcprotectedPath = false;
/*  160 */   private CertificateFactory cf = null;
/*  161 */   private KeyStore caks = null;
/*  162 */   private char[] srcstorePass = null;
/*  163 */   private String srcstoretype = null;
/*  164 */   private Set<char[]> passwords = (Set)new HashSet<>();
/*  165 */   private String startDate = null;
/*      */   
/*  167 */   private List<String> ids = new ArrayList<>();
/*  168 */   private List<String> v3ext = new ArrayList<>();
/*      */ 
/*      */   
/*      */   private boolean inplaceImport = false;
/*      */   
/*  173 */   private String inplaceBackupName = null;
/*      */ 
/*      */   
/*  176 */   private List<String> weakWarnings = new ArrayList<>();
/*      */   
/*  178 */   private static final DisabledAlgorithmConstraints DISABLED_CHECK = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  183 */   private static final Set<CryptoPrimitive> SIG_PRIMITIVE_SET = Collections.unmodifiableSet(EnumSet.of(CryptoPrimitive.SIGNATURE));
/*      */   
/*      */   enum Command {
/*  186 */     CERTREQ("Generates.a.certificate.request", new Main.Option[] { Main.Option.ALIAS, Main.Option.SIGALG, Main.Option.FILEOUT, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.DNAME, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  190 */     CHANGEALIAS("Changes.an.entry.s.alias", new Main.Option[] { Main.Option.ALIAS, Main.Option.DESTALIAS, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  194 */     DELETE("Deletes.an.entry", new Main.Option[] { Main.Option.ALIAS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  198 */     EXPORTCERT("Exports.certificate", new Main.Option[] { Main.Option.RFC, Main.Option.ALIAS, Main.Option.FILEOUT, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  202 */     GENKEYPAIR("Generates.a.key.pair", new Main.Option[] { Main.Option.ALIAS, Main.Option.KEYALG, Main.Option.KEYSIZE, Main.Option.SIGALG, Main.Option.DESTALIAS, Main.Option.DNAME, Main.Option.STARTDATE, Main.Option.EXT, Main.Option.VALIDITY, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  207 */     GENSECKEY("Generates.a.secret.key", new Main.Option[] { Main.Option.ALIAS, Main.Option.KEYPASS, Main.Option.KEYALG, Main.Option.KEYSIZE, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  211 */     GENCERT("Generates.certificate.from.a.certificate.request", new Main.Option[] { Main.Option.RFC, Main.Option.INFILE, Main.Option.OUTFILE, Main.Option.ALIAS, Main.Option.SIGALG, Main.Option.DNAME, Main.Option.STARTDATE, Main.Option.EXT, Main.Option.VALIDITY, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  216 */     IMPORTCERT("Imports.a.certificate.or.a.certificate.chain", new Main.Option[] { Main.Option.NOPROMPT, Main.Option.TRUSTCACERTS, Main.Option.PROTECTED, Main.Option.ALIAS, Main.Option.FILEIN, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  221 */     IMPORTPASS("Imports.a.password", new Main.Option[] { Main.Option.ALIAS, Main.Option.KEYPASS, Main.Option.KEYALG, Main.Option.KEYSIZE, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  225 */     IMPORTKEYSTORE("Imports.one.or.all.entries.from.another.keystore", new Main.Option[] { Main.Option.SRCKEYSTORE, Main.Option.DESTKEYSTORE, Main.Option.SRCSTORETYPE, Main.Option.DESTSTORETYPE, Main.Option.SRCSTOREPASS, Main.Option.DESTSTOREPASS, Main.Option.SRCPROTECTED, Main.Option.SRCPROVIDERNAME, Main.Option.DESTPROVIDERNAME, Main.Option.SRCALIAS, Main.Option.DESTALIAS, Main.Option.SRCKEYPASS, Main.Option.DESTKEYPASS, Main.Option.NOPROMPT, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  232 */     KEYPASSWD("Changes.the.key.password.of.an.entry", new Main.Option[] { Main.Option.ALIAS, Main.Option.KEYPASS, Main.Option.NEW, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */       
/*      */       }),
/*  236 */     LIST("Lists.entries.in.a.keystore", new Main.Option[] { Main.Option.RFC, Main.Option.ALIAS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */       
/*      */       }),
/*  240 */     PRINTCERT("Prints.the.content.of.a.certificate", new Main.Option[] { Main.Option.RFC, Main.Option.FILEIN, Main.Option.SSLSERVER, Main.Option.JARFILE, Main.Option.V
/*      */       }),
/*  242 */     PRINTCERTREQ("Prints.the.content.of.a.certificate.request", new Main.Option[] { Main.Option.FILEIN, Main.Option.V
/*      */       }),
/*  244 */     PRINTCRL("Prints.the.content.of.a.CRL.file", new Main.Option[] { Main.Option.FILEIN, Main.Option.V
/*      */       }),
/*  246 */     STOREPASSWD("Changes.the.store.password.of.a.keystore", new Main.Option[] { Main.Option.NEW, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  252 */     KEYCLONE("Clones.a.key.entry", new Main.Option[] { Main.Option.ALIAS, Main.Option.DESTALIAS, Main.Option.KEYPASS, Main.Option.NEW, Main.Option.STORETYPE, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */       
/*      */       }),
/*  256 */     SELFCERT("Generates.a.self.signed.certificate", new Main.Option[] { Main.Option.ALIAS, Main.Option.SIGALG, Main.Option.DNAME, Main.Option.STARTDATE, Main.Option.VALIDITY, Main.Option.KEYPASS, Main.Option.STORETYPE, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V
/*      */ 
/*      */       
/*      */       }),
/*  260 */     GENCRL("Generates.CRL", new Main.Option[] { Main.Option.RFC, Main.Option.FILEOUT, Main.Option.ID, Main.Option.ALIAS, Main.Option.SIGALG, Main.Option.EXT, Main.Option.KEYPASS, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.STORETYPE, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V, Main.Option.PROTECTED
/*      */ 
/*      */ 
/*      */       
/*      */       }),
/*  265 */     IDENTITYDB("Imports.entries.from.a.JDK.1.1.x.style.identity.database", new Main.Option[] { Main.Option.FILEIN, Main.Option.STORETYPE, Main.Option.KEYSTORE, Main.Option.STOREPASS, Main.Option.PROVIDERNAME, Main.Option.PROVIDERCLASS, Main.Option.PROVIDERARG, Main.Option.PROVIDERPATH, Main.Option.V });
/*      */     
/*      */     final String description;
/*      */     
/*      */     final Main.Option[] options;
/*      */     
/*      */     Command(String param1String1, Main.Option... param1VarArgs) {
/*  272 */       this.description = param1String1;
/*  273 */       this.options = param1VarArgs;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  277 */       return "-" + name().toLowerCase(Locale.ENGLISH);
/*      */     }
/*      */   }
/*      */   
/*      */   enum Option {
/*  282 */     ALIAS("alias", "<alias>", "alias.name.of.the.entry.to.process"),
/*  283 */     DESTALIAS("destalias", "<destalias>", "destination.alias"),
/*  284 */     DESTKEYPASS("destkeypass", "<arg>", "destination.key.password"),
/*  285 */     DESTKEYSTORE("destkeystore", "<destkeystore>", "destination.keystore.name"),
/*  286 */     DESTPROTECTED("destprotected", null, "destination.keystore.password.protected"),
/*  287 */     DESTPROVIDERNAME("destprovidername", "<destprovidername>", "destination.keystore.provider.name"),
/*  288 */     DESTSTOREPASS("deststorepass", "<arg>", "destination.keystore.password"),
/*  289 */     DESTSTORETYPE("deststoretype", "<deststoretype>", "destination.keystore.type"),
/*  290 */     DNAME("dname", "<dname>", "distinguished.name"),
/*  291 */     EXT("ext", "<value>", "X.509.extension"),
/*  292 */     FILEOUT("file", "<filename>", "output.file.name"),
/*  293 */     FILEIN("file", "<filename>", "input.file.name"),
/*  294 */     ID("id", "<id:reason>", "Serial.ID.of.cert.to.revoke"),
/*  295 */     INFILE("infile", "<filename>", "input.file.name"),
/*  296 */     KEYALG("keyalg", "<keyalg>", "key.algorithm.name"),
/*  297 */     KEYPASS("keypass", "<arg>", "key.password"),
/*  298 */     KEYSIZE("keysize", "<keysize>", "key.bit.size"),
/*  299 */     KEYSTORE("keystore", "<keystore>", "keystore.name"),
/*  300 */     NEW("new", "<arg>", "new.password"),
/*  301 */     NOPROMPT("noprompt", null, "do.not.prompt"),
/*  302 */     OUTFILE("outfile", "<filename>", "output.file.name"),
/*  303 */     PROTECTED("protected", null, "password.through.protected.mechanism"),
/*  304 */     PROVIDERARG("providerarg", "<arg>", "provider.argument"),
/*  305 */     PROVIDERCLASS("providerclass", "<providerclass>", "provider.class.name"),
/*  306 */     PROVIDERNAME("providername", "<providername>", "provider.name"),
/*  307 */     PROVIDERPATH("providerpath", "<pathlist>", "provider.classpath"),
/*  308 */     RFC("rfc", null, "output.in.RFC.style"),
/*  309 */     SIGALG("sigalg", "<sigalg>", "signature.algorithm.name"),
/*  310 */     SRCALIAS("srcalias", "<srcalias>", "source.alias"),
/*  311 */     SRCKEYPASS("srckeypass", "<arg>", "source.key.password"),
/*  312 */     SRCKEYSTORE("srckeystore", "<srckeystore>", "source.keystore.name"),
/*  313 */     SRCPROTECTED("srcprotected", null, "source.keystore.password.protected"),
/*  314 */     SRCPROVIDERNAME("srcprovidername", "<srcprovidername>", "source.keystore.provider.name"),
/*  315 */     SRCSTOREPASS("srcstorepass", "<arg>", "source.keystore.password"),
/*  316 */     SRCSTORETYPE("srcstoretype", "<srcstoretype>", "source.keystore.type"),
/*  317 */     SSLSERVER("sslserver", "<server[:port]>", "SSL.server.host.and.port"),
/*  318 */     JARFILE("jarfile", "<filename>", "signed.jar.file"),
/*  319 */     STARTDATE("startdate", "<startdate>", "certificate.validity.start.date.time"),
/*  320 */     STOREPASS("storepass", "<arg>", "keystore.password"),
/*  321 */     STORETYPE("storetype", "<storetype>", "keystore.type"),
/*  322 */     TRUSTCACERTS("trustcacerts", null, "trust.certificates.from.cacerts"),
/*  323 */     V("v", null, "verbose.output"),
/*  324 */     VALIDITY("validity", "<valDays>", "validity.number.of.days"); final String name; final String arg;
/*      */     final String description;
/*      */     
/*      */     Option(String param1String1, String param1String2, String param1String3) {
/*  328 */       this.name = param1String1;
/*  329 */       this.arg = param1String2;
/*  330 */       this.description = param1String3;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  334 */       return "-" + this.name;
/*      */     }
/*      */   }
/*      */   
/*  338 */   private static final Class<?>[] PARAM_STRING = new Class[] { String.class };
/*      */   
/*      */   private static final String NONE = "NONE";
/*      */   
/*      */   private static final String P11KEYSTORE = "PKCS11";
/*      */   
/*      */   private static final String P12KEYSTORE = "PKCS12";
/*      */   
/*      */   private static final String keyAlias = "mykey";
/*  347 */   private static final ResourceBundle rb = ResourceBundle.getBundle("sun.security.tools.keytool.Resources");
/*      */   
/*  349 */   private static final Collator collator = Collator.getInstance();
/*      */   
/*      */   static {
/*  352 */     collator.setStrength(0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] paramArrayOfString) throws Exception {
/*  358 */     Main main = new Main();
/*  359 */     main.run(paramArrayOfString, System.out);
/*      */   }
/*      */   
/*      */   private void run(String[] paramArrayOfString, PrintStream paramPrintStream) throws Exception {
/*      */     try {
/*  364 */       parseArgs(paramArrayOfString);
/*  365 */       if (this.command != null) {
/*  366 */         doCommands(paramPrintStream);
/*      */       }
/*  368 */     } catch (Exception exception) {
/*  369 */       System.out.println(rb.getString("keytool.error.") + exception);
/*  370 */       if (this.verbose) {
/*  371 */         exception.printStackTrace(System.out);
/*      */       }
/*  373 */       if (!this.debug) {
/*  374 */         System.exit(1);
/*      */       } else {
/*  376 */         throw exception;
/*      */       } 
/*      */     } finally {
/*  379 */       printWeakWarnings(false);
/*  380 */       for (char[] arrayOfChar : this.passwords) {
/*  381 */         if (arrayOfChar != null) {
/*  382 */           Arrays.fill(arrayOfChar, ' ');
/*  383 */           arrayOfChar = null;
/*      */         } 
/*      */       } 
/*      */       
/*  387 */       if (this.ksStream != null) {
/*  388 */         this.ksStream.close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void parseArgs(String[] paramArrayOfString) {
/*  398 */     byte b = 0;
/*  399 */     boolean bool = (paramArrayOfString.length == 0) ? true : false;
/*      */     
/*  401 */     for (b = 0; b < paramArrayOfString.length && paramArrayOfString[b].startsWith("-"); b++) {
/*      */       
/*  403 */       String str1 = paramArrayOfString[b];
/*      */ 
/*      */       
/*  406 */       if (b == paramArrayOfString.length - 1) {
/*  407 */         for (Option option : Option.values()) {
/*      */           
/*  409 */           if (collator.compare(str1, option.toString()) == 0) {
/*  410 */             if (option.arg != null) errorNeedArgument(str1);
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  419 */       String str2 = null;
/*  420 */       int i = str1.indexOf(':');
/*  421 */       if (i > 0) {
/*  422 */         str2 = str1.substring(i + 1);
/*  423 */         str1 = str1.substring(0, i);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  428 */       boolean bool1 = false;
/*  429 */       for (Command command : Command.values()) {
/*  430 */         if (collator.compare(str1, command.toString()) == 0) {
/*  431 */           this.command = command;
/*  432 */           bool1 = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  437 */       if (!bool1)
/*      */       {
/*  439 */         if (collator.compare(str1, "-export") == 0) {
/*  440 */           this.command = Command.EXPORTCERT;
/*  441 */         } else if (collator.compare(str1, "-genkey") == 0) {
/*  442 */           this.command = Command.GENKEYPAIR;
/*  443 */         } else if (collator.compare(str1, "-import") == 0) {
/*  444 */           this.command = Command.IMPORTCERT;
/*  445 */         } else if (collator.compare(str1, "-importpassword") == 0) {
/*  446 */           this.command = Command.IMPORTPASS;
/*  447 */         } else if (collator.compare(str1, "-help") == 0) {
/*  448 */           bool = true;
/*  449 */         } else if (collator.compare(str1, "-nowarn") == 0) {
/*  450 */           this.nowarn = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  456 */         else if (collator.compare(str1, "-keystore") == 0 || collator
/*  457 */           .compare(str1, "-destkeystore") == 0) {
/*  458 */           this.ksfname = paramArrayOfString[++b];
/*  459 */         } else if (collator.compare(str1, "-storepass") == 0 || collator
/*  460 */           .compare(str1, "-deststorepass") == 0) {
/*  461 */           this.storePass = getPass(str2, paramArrayOfString[++b]);
/*  462 */           this.passwords.add(this.storePass);
/*  463 */         } else if (collator.compare(str1, "-storetype") == 0 || collator
/*  464 */           .compare(str1, "-deststoretype") == 0) {
/*  465 */           this.storetype = KeyStoreUtil.niceStoreTypeName(paramArrayOfString[++b]);
/*  466 */         } else if (collator.compare(str1, "-srcstorepass") == 0) {
/*  467 */           this.srcstorePass = getPass(str2, paramArrayOfString[++b]);
/*  468 */           this.passwords.add(this.srcstorePass);
/*  469 */         } else if (collator.compare(str1, "-srcstoretype") == 0) {
/*  470 */           this.srcstoretype = KeyStoreUtil.niceStoreTypeName(paramArrayOfString[++b]);
/*  471 */         } else if (collator.compare(str1, "-srckeypass") == 0) {
/*  472 */           this.srckeyPass = getPass(str2, paramArrayOfString[++b]);
/*  473 */           this.passwords.add(this.srckeyPass);
/*  474 */         } else if (collator.compare(str1, "-srcprovidername") == 0) {
/*  475 */           this.srcProviderName = paramArrayOfString[++b];
/*  476 */         } else if (collator.compare(str1, "-providername") == 0 || collator
/*  477 */           .compare(str1, "-destprovidername") == 0) {
/*  478 */           this.providerName = paramArrayOfString[++b];
/*  479 */         } else if (collator.compare(str1, "-providerpath") == 0) {
/*  480 */           this.pathlist = paramArrayOfString[++b];
/*  481 */         } else if (collator.compare(str1, "-keypass") == 0) {
/*  482 */           this.keyPass = getPass(str2, paramArrayOfString[++b]);
/*  483 */           this.passwords.add(this.keyPass);
/*  484 */         } else if (collator.compare(str1, "-new") == 0) {
/*  485 */           this.newPass = getPass(str2, paramArrayOfString[++b]);
/*  486 */           this.passwords.add(this.newPass);
/*  487 */         } else if (collator.compare(str1, "-destkeypass") == 0) {
/*  488 */           this.destKeyPass = getPass(str2, paramArrayOfString[++b]);
/*  489 */           this.passwords.add(this.destKeyPass);
/*  490 */         } else if (collator.compare(str1, "-alias") == 0 || collator
/*  491 */           .compare(str1, "-srcalias") == 0) {
/*  492 */           this.alias = paramArrayOfString[++b];
/*  493 */         } else if (collator.compare(str1, "-dest") == 0 || collator
/*  494 */           .compare(str1, "-destalias") == 0) {
/*  495 */           this.dest = paramArrayOfString[++b];
/*  496 */         } else if (collator.compare(str1, "-dname") == 0) {
/*  497 */           this.dname = paramArrayOfString[++b];
/*  498 */         } else if (collator.compare(str1, "-keysize") == 0) {
/*  499 */           this.keysize = Integer.parseInt(paramArrayOfString[++b]);
/*  500 */         } else if (collator.compare(str1, "-keyalg") == 0) {
/*  501 */           this.keyAlgName = paramArrayOfString[++b];
/*  502 */         } else if (collator.compare(str1, "-sigalg") == 0) {
/*  503 */           this.sigAlgName = paramArrayOfString[++b];
/*  504 */         } else if (collator.compare(str1, "-startdate") == 0) {
/*  505 */           this.startDate = paramArrayOfString[++b];
/*  506 */         } else if (collator.compare(str1, "-validity") == 0) {
/*  507 */           this.validity = Long.parseLong(paramArrayOfString[++b]);
/*  508 */         } else if (collator.compare(str1, "-ext") == 0) {
/*  509 */           this.v3ext.add(paramArrayOfString[++b]);
/*  510 */         } else if (collator.compare(str1, "-id") == 0) {
/*  511 */           this.ids.add(paramArrayOfString[++b]);
/*  512 */         } else if (collator.compare(str1, "-file") == 0) {
/*  513 */           this.filename = paramArrayOfString[++b];
/*  514 */         } else if (collator.compare(str1, "-infile") == 0) {
/*  515 */           this.infilename = paramArrayOfString[++b];
/*  516 */         } else if (collator.compare(str1, "-outfile") == 0) {
/*  517 */           this.outfilename = paramArrayOfString[++b];
/*  518 */         } else if (collator.compare(str1, "-sslserver") == 0) {
/*  519 */           this.sslserver = paramArrayOfString[++b];
/*  520 */         } else if (collator.compare(str1, "-jarfile") == 0) {
/*  521 */           this.jarfile = paramArrayOfString[++b];
/*  522 */         } else if (collator.compare(str1, "-srckeystore") == 0) {
/*  523 */           this.srcksfname = paramArrayOfString[++b];
/*  524 */         } else if (collator.compare(str1, "-provider") == 0 || collator
/*  525 */           .compare(str1, "-providerclass") == 0) {
/*  526 */           if (this.providers == null) {
/*  527 */             this.providers = new HashSet<>(3);
/*      */           }
/*  529 */           String str3 = paramArrayOfString[++b];
/*  530 */           String str4 = null;
/*      */           
/*  532 */           if (paramArrayOfString.length > b + 1) {
/*  533 */             str1 = paramArrayOfString[b + 1];
/*  534 */             if (collator.compare(str1, "-providerarg") == 0) {
/*  535 */               if (paramArrayOfString.length == b + 2) errorNeedArgument(str1); 
/*  536 */               str4 = paramArrayOfString[b + 2];
/*  537 */               b += 2;
/*      */             } 
/*      */           } 
/*  540 */           this.providers.add(
/*  541 */               Pair.of(str3, str4));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  547 */         else if (collator.compare(str1, "-v") == 0) {
/*  548 */           this.verbose = true;
/*  549 */         } else if (collator.compare(str1, "-debug") == 0) {
/*  550 */           this.debug = true;
/*  551 */         } else if (collator.compare(str1, "-rfc") == 0) {
/*  552 */           this.rfc = true;
/*  553 */         } else if (collator.compare(str1, "-noprompt") == 0) {
/*  554 */           this.noprompt = true;
/*  555 */         } else if (collator.compare(str1, "-trustcacerts") == 0) {
/*  556 */           this.trustcacerts = true;
/*  557 */         } else if (collator.compare(str1, "-protected") == 0 || collator
/*  558 */           .compare(str1, "-destprotected") == 0) {
/*  559 */           this.protectedPath = true;
/*  560 */         } else if (collator.compare(str1, "-srcprotected") == 0) {
/*  561 */           this.srcprotectedPath = true;
/*      */         } else {
/*  563 */           System.err.println(rb.getString("Illegal.option.") + str1);
/*  564 */           tinyHelp();
/*      */         } 
/*      */       }
/*      */     } 
/*  568 */     if (b < paramArrayOfString.length) {
/*  569 */       System.err.println(rb.getString("Illegal.option.") + paramArrayOfString[b]);
/*  570 */       tinyHelp();
/*      */     } 
/*      */     
/*  573 */     if (this.command == null) {
/*  574 */       if (bool) {
/*  575 */         usage();
/*      */       } else {
/*  577 */         System.err.println(rb.getString("Usage.error.no.command.provided"));
/*  578 */         tinyHelp();
/*      */       } 
/*  580 */     } else if (bool) {
/*  581 */       usage();
/*  582 */       this.command = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean isKeyStoreRelated(Command paramCommand) {
/*  587 */     return (paramCommand != Command.PRINTCERT && paramCommand != Command.PRINTCERTREQ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doCommands(PrintStream paramPrintStream) throws Exception {
/*  595 */     if ("PKCS11".equalsIgnoreCase(this.storetype) || 
/*  596 */       KeyStoreUtil.isWindowsKeyStore(this.storetype)) {
/*  597 */       this.token = true;
/*  598 */       if (this.ksfname == null) {
/*  599 */         this.ksfname = "NONE";
/*      */       }
/*      */     } 
/*  602 */     if ("NONE".equals(this.ksfname)) {
/*  603 */       this.nullStream = true;
/*      */     }
/*      */     
/*  606 */     if (this.token && !this.nullStream) {
/*  607 */       System.err.println(MessageFormat.format(rb
/*  608 */             .getString(".keystore.must.be.NONE.if.storetype.is.{0}"), new Object[] { this.storetype }));
/*  609 */       System.err.println();
/*  610 */       tinyHelp();
/*      */     } 
/*      */     
/*  613 */     if (this.token && (this.command == Command.KEYPASSWD || this.command == Command.STOREPASSWD))
/*      */     {
/*  615 */       throw new UnsupportedOperationException(MessageFormat.format(rb
/*  616 */             .getString(".storepasswd.and.keypasswd.commands.not.supported.if.storetype.is.{0}"), new Object[] { this.storetype }));
/*      */     }
/*      */     
/*  619 */     if (this.token && (this.keyPass != null || this.newPass != null || this.destKeyPass != null)) {
/*  620 */       throw new IllegalArgumentException(MessageFormat.format(rb
/*  621 */             .getString(".keypass.and.new.can.not.be.specified.if.storetype.is.{0}"), new Object[] { this.storetype }));
/*      */     }
/*      */     
/*  624 */     if (this.protectedPath && (
/*  625 */       this.storePass != null || this.keyPass != null || this.newPass != null || this.destKeyPass != null))
/*      */     {
/*  627 */       throw new IllegalArgumentException(rb
/*  628 */           .getString("if.protected.is.specified.then.storepass.keypass.and.new.must.not.be.specified"));
/*      */     }
/*      */ 
/*      */     
/*  632 */     if (this.srcprotectedPath && (
/*  633 */       this.srcstorePass != null || this.srckeyPass != null)) {
/*  634 */       throw new IllegalArgumentException(rb
/*  635 */           .getString("if.srcprotected.is.specified.then.srcstorepass.and.srckeypass.must.not.be.specified"));
/*      */     }
/*      */ 
/*      */     
/*  639 */     if (KeyStoreUtil.isWindowsKeyStore(this.storetype) && (
/*  640 */       this.storePass != null || this.keyPass != null || this.newPass != null || this.destKeyPass != null))
/*      */     {
/*  642 */       throw new IllegalArgumentException(rb
/*  643 */           .getString("if.keystore.is.not.password.protected.then.storepass.keypass.and.new.must.not.be.specified"));
/*      */     }
/*      */ 
/*      */     
/*  647 */     if (KeyStoreUtil.isWindowsKeyStore(this.srcstoretype) && (
/*  648 */       this.srcstorePass != null || this.srckeyPass != null)) {
/*  649 */       throw new IllegalArgumentException(rb
/*  650 */           .getString("if.source.keystore.is.not.password.protected.then.srcstorepass.and.srckeypass.must.not.be.specified"));
/*      */     }
/*      */ 
/*      */     
/*  654 */     if (this.validity <= 0L) {
/*  655 */       throw new Exception(rb
/*  656 */           .getString("Validity.must.be.greater.than.zero"));
/*      */     }
/*      */ 
/*      */     
/*  660 */     if (this.providers != null) {
/*  661 */       ClassLoader classLoader = null;
/*  662 */       if (this.pathlist != null) {
/*  663 */         String str = null;
/*  664 */         str = PathList.appendPath(str, 
/*  665 */             System.getProperty("java.class.path"));
/*  666 */         str = PathList.appendPath(str, 
/*  667 */             System.getProperty("env.class.path"));
/*  668 */         str = PathList.appendPath(str, this.pathlist);
/*      */         
/*  670 */         URL[] arrayOfURL = PathList.pathToURLs(str);
/*  671 */         classLoader = new URLClassLoader(arrayOfURL);
/*      */       } else {
/*  673 */         classLoader = ClassLoader.getSystemClassLoader();
/*      */       } 
/*      */       
/*  676 */       for (Pair<String, String> pair : this.providers) {
/*  677 */         Class<?> clazz; Object object; String str1 = (String)pair.fst;
/*      */         
/*  679 */         if (classLoader != null) {
/*  680 */           clazz = classLoader.loadClass(str1);
/*      */         } else {
/*  682 */           clazz = Class.forName(str1);
/*      */         } 
/*      */         
/*  685 */         String str2 = (String)pair.snd;
/*      */         
/*  687 */         if (str2 == null) {
/*  688 */           object = clazz.newInstance();
/*      */         } else {
/*  690 */           Constructor<?> constructor = clazz.getConstructor(PARAM_STRING);
/*  691 */           object = constructor.newInstance(new Object[] { str2 });
/*      */         } 
/*  693 */         if (!(object instanceof Provider)) {
/*      */           
/*  695 */           MessageFormat messageFormat = new MessageFormat(rb.getString("provName.not.a.provider"));
/*  696 */           Object[] arrayOfObject = { str1 };
/*  697 */           throw new Exception(messageFormat.format(arrayOfObject));
/*      */         } 
/*  699 */         Security.addProvider((Provider)object);
/*      */       } 
/*      */     } 
/*      */     
/*  703 */     if (this.command == Command.LIST && this.verbose && this.rfc) {
/*  704 */       System.err.println(rb
/*  705 */           .getString("Must.not.specify.both.v.and.rfc.with.list.command"));
/*  706 */       tinyHelp();
/*      */     } 
/*      */ 
/*      */     
/*  710 */     if (this.command == Command.GENKEYPAIR && this.keyPass != null && this.keyPass.length < 6) {
/*  711 */       throw new Exception(rb
/*  712 */           .getString("Key.password.must.be.at.least.6.characters"));
/*      */     }
/*  714 */     if (this.newPass != null && this.newPass.length < 6) {
/*  715 */       throw new Exception(rb
/*  716 */           .getString("New.password.must.be.at.least.6.characters"));
/*      */     }
/*  718 */     if (this.destKeyPass != null && this.destKeyPass.length < 6) {
/*  719 */       throw new Exception(rb
/*  720 */           .getString("New.password.must.be.at.least.6.characters"));
/*      */     }
/*      */ 
/*      */     
/*  724 */     if (this.ksfname == null) {
/*  725 */       this.ksfname = System.getProperty("user.home") + File.separator + ".keystore";
/*      */     }
/*      */ 
/*      */     
/*  729 */     KeyStore keyStore = null;
/*  730 */     if (this.command == Command.IMPORTKEYSTORE) {
/*  731 */       this.inplaceImport = inplaceImportCheck();
/*  732 */       if (this.inplaceImport) {
/*      */ 
/*      */         
/*  735 */         keyStore = loadSourceKeyStore();
/*  736 */         if (this.storePass == null) {
/*  737 */           this.storePass = this.srcstorePass;
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
/*  750 */     if (isKeyStoreRelated(this.command) && !this.nullStream && !this.inplaceImport) {
/*      */       try {
/*  752 */         this.ksfile = new File(this.ksfname);
/*      */         
/*  754 */         if (this.ksfile.exists() && this.ksfile.length() == 0L) {
/*  755 */           throw new Exception(rb
/*  756 */               .getString("Keystore.file.exists.but.is.empty.") + this.ksfname);
/*      */         }
/*  758 */         this.ksStream = new FileInputStream(this.ksfile);
/*  759 */       } catch (FileNotFoundException fileNotFoundException) {
/*  760 */         if (this.command != Command.GENKEYPAIR && this.command != Command.GENSECKEY && this.command != Command.IDENTITYDB && this.command != Command.IMPORTCERT && this.command != Command.IMPORTPASS && this.command != Command.IMPORTKEYSTORE && this.command != Command.PRINTCRL)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  767 */           throw new Exception(rb
/*  768 */               .getString("Keystore.file.does.not.exist.") + this.ksfname);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  773 */     if ((this.command == Command.KEYCLONE || this.command == Command.CHANGEALIAS) && this.dest == null) {
/*      */       
/*  775 */       this.dest = getAlias("destination");
/*  776 */       if ("".equals(this.dest)) {
/*  777 */         throw new Exception(rb
/*  778 */             .getString("Must.specify.destination.alias"));
/*      */       }
/*      */     } 
/*      */     
/*  782 */     if (this.command == Command.DELETE && this.alias == null) {
/*  783 */       this.alias = getAlias(null);
/*  784 */       if ("".equals(this.alias)) {
/*  785 */         throw new Exception(rb.getString("Must.specify.alias"));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  790 */     if (this.storetype == null) {
/*  791 */       this.storetype = KeyStore.getDefaultType();
/*      */     }
/*  793 */     if (this.providerName == null) {
/*  794 */       this.keyStore = KeyStore.getInstance(this.storetype);
/*      */     } else {
/*  796 */       this.keyStore = KeyStore.getInstance(this.storetype, this.providerName);
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
/*  819 */     if (!this.nullStream) {
/*  820 */       if (this.inplaceImport) {
/*  821 */         this.keyStore.load(null, this.storePass);
/*      */       } else {
/*  823 */         this.keyStore.load(this.ksStream, this.storePass);
/*      */       } 
/*  825 */       if (this.ksStream != null) {
/*  826 */         this.ksStream.close();
/*      */       }
/*      */     } 
/*      */     
/*  830 */     if ("PKCS12".equalsIgnoreCase(this.storetype) && this.command == Command.KEYPASSWD) {
/*  831 */       throw new UnsupportedOperationException(rb
/*  832 */           .getString(".keypasswd.commands.not.supported.if.storetype.is.PKCS12"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  838 */     if (this.nullStream && this.storePass != null) {
/*  839 */       this.keyStore.load(null, this.storePass);
/*  840 */     } else if (!this.nullStream && this.storePass != null) {
/*      */ 
/*      */       
/*  843 */       if (this.ksStream == null && this.storePass.length < 6) {
/*  844 */         throw new Exception(rb
/*  845 */             .getString("Keystore.password.must.be.at.least.6.characters"));
/*      */       }
/*  847 */     } else if (this.storePass == null) {
/*      */ 
/*      */ 
/*      */       
/*  851 */       if (!this.protectedPath && !KeyStoreUtil.isWindowsKeyStore(this.storetype) && (this.command == Command.CERTREQ || this.command == Command.DELETE || this.command == Command.GENKEYPAIR || this.command == Command.GENSECKEY || this.command == Command.IMPORTCERT || this.command == Command.IMPORTPASS || this.command == Command.IMPORTKEYSTORE || this.command == Command.KEYCLONE || this.command == Command.CHANGEALIAS || this.command == Command.SELFCERT || this.command == Command.STOREPASSWD || this.command == Command.KEYPASSWD || this.command == Command.IDENTITYDB)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  865 */         byte b = 0;
/*      */         do {
/*  867 */           if (this.command == Command.IMPORTKEYSTORE) {
/*  868 */             System.err
/*  869 */               .print(rb.getString("Enter.destination.keystore.password."));
/*      */           } else {
/*  871 */             System.err
/*  872 */               .print(rb.getString("Enter.keystore.password."));
/*      */           } 
/*  874 */           System.err.flush();
/*  875 */           this.storePass = Password.readPassword(System.in);
/*  876 */           this.passwords.add(this.storePass);
/*      */ 
/*      */ 
/*      */           
/*  880 */           if (!this.nullStream && (this.storePass == null || this.storePass.length < 6)) {
/*  881 */             System.err.println(rb
/*  882 */                 .getString("Keystore.password.is.too.short.must.be.at.least.6.characters"));
/*  883 */             this.storePass = null;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  888 */           if (this.storePass != null && !this.nullStream && this.ksStream == null) {
/*  889 */             System.err.print(rb.getString("Re.enter.new.password."));
/*  890 */             char[] arrayOfChar = Password.readPassword(System.in);
/*  891 */             this.passwords.add(arrayOfChar);
/*  892 */             if (!Arrays.equals(this.storePass, arrayOfChar)) {
/*  893 */               System.err
/*  894 */                 .println(rb.getString("They.don.t.match.Try.again"));
/*  895 */               this.storePass = null;
/*      */             } 
/*      */           } 
/*      */           
/*  899 */           b++;
/*  900 */         } while (this.storePass == null && b < 3);
/*      */ 
/*      */         
/*  903 */         if (this.storePass == null) {
/*  904 */           System.err
/*  905 */             .println(rb.getString("Too.many.failures.try.later"));
/*      */           return;
/*      */         } 
/*  908 */       } else if (!this.protectedPath && 
/*  909 */         !KeyStoreUtil.isWindowsKeyStore(this.storetype) && 
/*  910 */         isKeyStoreRelated(this.command)) {
/*      */         
/*  912 */         if (this.command != Command.PRINTCRL) {
/*  913 */           System.err.print(rb.getString("Enter.keystore.password."));
/*  914 */           System.err.flush();
/*  915 */           this.storePass = Password.readPassword(System.in);
/*  916 */           this.passwords.add(this.storePass);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  922 */       if (this.nullStream) {
/*  923 */         this.keyStore.load(null, this.storePass);
/*  924 */       } else if (this.ksStream != null) {
/*  925 */         this.ksStream = new FileInputStream(this.ksfile);
/*  926 */         this.keyStore.load(this.ksStream, this.storePass);
/*  927 */         this.ksStream.close();
/*      */       } 
/*      */     } 
/*      */     
/*  931 */     if (this.storePass != null && "PKCS12".equalsIgnoreCase(this.storetype)) {
/*  932 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Warning.Different.store.and.key.passwords.not.supported.for.PKCS12.KeyStores.Ignoring.user.specified.command.value."));
/*      */       
/*  934 */       if (this.keyPass != null && !Arrays.equals(this.storePass, this.keyPass)) {
/*  935 */         Object[] arrayOfObject = { "-keypass" };
/*  936 */         System.err.println(messageFormat.format(arrayOfObject));
/*  937 */         this.keyPass = this.storePass;
/*      */       } 
/*  939 */       if (this.newPass != null && !Arrays.equals(this.storePass, this.newPass)) {
/*  940 */         Object[] arrayOfObject = { "-new" };
/*  941 */         System.err.println(messageFormat.format(arrayOfObject));
/*  942 */         this.newPass = this.storePass;
/*      */       } 
/*  944 */       if (this.destKeyPass != null && !Arrays.equals(this.storePass, this.destKeyPass)) {
/*  945 */         Object[] arrayOfObject = { "-destkeypass" };
/*  946 */         System.err.println(messageFormat.format(arrayOfObject));
/*  947 */         this.destKeyPass = this.storePass;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  952 */     if (this.command == Command.PRINTCERT || this.command == Command.IMPORTCERT || this.command == Command.IDENTITYDB || this.command == Command.PRINTCRL)
/*      */     {
/*  954 */       this.cf = CertificateFactory.getInstance("X509");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  960 */     if (this.command != Command.IMPORTCERT) {
/*  961 */       this.trustcacerts = false;
/*      */     }
/*      */     
/*  964 */     if (this.trustcacerts) {
/*  965 */       this.caks = KeyStoreUtil.getCacertsKeyStore();
/*      */     }
/*      */ 
/*      */     
/*  969 */     if (this.command == Command.CERTREQ) {
/*  970 */       if (this.filename != null) {
/*  971 */         try (PrintStream null = new PrintStream(new FileOutputStream(this.filename))) {
/*      */           
/*  973 */           doCertReq(this.alias, this.sigAlgName, printStream);
/*      */         } 
/*      */       } else {
/*  976 */         doCertReq(this.alias, this.sigAlgName, paramPrintStream);
/*      */       } 
/*  978 */       if (this.verbose && this.filename != null) {
/*      */         
/*  980 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Certification.request.stored.in.file.filename."));
/*  981 */         Object[] arrayOfObject = { this.filename };
/*  982 */         System.err.println(messageFormat.format(arrayOfObject));
/*  983 */         System.err.println(rb.getString("Submit.this.to.your.CA"));
/*      */       } 
/*  985 */     } else if (this.command == Command.DELETE) {
/*  986 */       doDeleteEntry(this.alias);
/*  987 */       this.kssave = true;
/*  988 */     } else if (this.command == Command.EXPORTCERT) {
/*  989 */       if (this.filename != null) {
/*  990 */         try (PrintStream null = new PrintStream(new FileOutputStream(this.filename))) {
/*      */           
/*  992 */           doExportCert(this.alias, printStream);
/*      */         } 
/*      */       } else {
/*  995 */         doExportCert(this.alias, paramPrintStream);
/*      */       } 
/*  997 */       if (this.filename != null) {
/*      */         
/*  999 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.stored.in.file.filename."));
/* 1000 */         Object[] arrayOfObject = { this.filename };
/* 1001 */         System.err.println(messageFormat.format(arrayOfObject));
/*      */       } 
/* 1003 */     } else if (this.command == Command.GENKEYPAIR) {
/* 1004 */       if (this.keyAlgName == null) {
/* 1005 */         this.keyAlgName = "DSA";
/*      */       }
/* 1007 */       doGenKeyPair(this.alias, this.dname, this.keyAlgName, this.keysize, this.sigAlgName);
/* 1008 */       this.kssave = true;
/* 1009 */     } else if (this.command == Command.GENSECKEY) {
/* 1010 */       if (this.keyAlgName == null) {
/* 1011 */         this.keyAlgName = "DES";
/*      */       }
/* 1013 */       doGenSecretKey(this.alias, this.keyAlgName, this.keysize);
/* 1014 */       this.kssave = true;
/* 1015 */     } else if (this.command == Command.IMPORTPASS) {
/* 1016 */       if (this.keyAlgName == null) {
/* 1017 */         this.keyAlgName = "PBE";
/*      */       }
/*      */       
/* 1020 */       doGenSecretKey(this.alias, this.keyAlgName, this.keysize);
/* 1021 */       this.kssave = true;
/* 1022 */     } else if (this.command == Command.IDENTITYDB) {
/* 1023 */       if (this.filename != null) {
/* 1024 */         try (FileInputStream null = new FileInputStream(this.filename)) {
/* 1025 */           doImportIdentityDatabase(fileInputStream);
/*      */         } 
/*      */       } else {
/* 1028 */         doImportIdentityDatabase(System.in);
/*      */       } 
/* 1030 */     } else if (this.command == Command.IMPORTCERT) {
/* 1031 */       InputStream inputStream = System.in;
/* 1032 */       if (this.filename != null) {
/* 1033 */         inputStream = new FileInputStream(this.filename);
/*      */       }
/* 1035 */       String str = (this.alias != null) ? this.alias : "mykey";
/*      */       try {
/* 1037 */         if (this.keyStore.entryInstanceOf(str, (Class)KeyStore.PrivateKeyEntry.class)) {
/*      */           
/* 1039 */           this.kssave = installReply(str, inputStream);
/* 1040 */           if (this.kssave) {
/* 1041 */             System.err.println(rb
/* 1042 */                 .getString("Certificate.reply.was.installed.in.keystore"));
/*      */           } else {
/* 1044 */             System.err.println(rb
/* 1045 */                 .getString("Certificate.reply.was.not.installed.in.keystore"));
/*      */           } 
/* 1047 */         } else if (!this.keyStore.containsAlias(str) || this.keyStore
/* 1048 */           .entryInstanceOf(str, (Class)KeyStore.TrustedCertificateEntry.class)) {
/*      */           
/* 1050 */           this.kssave = addTrustedCert(str, inputStream);
/* 1051 */           if (this.kssave) {
/* 1052 */             System.err.println(rb
/* 1053 */                 .getString("Certificate.was.added.to.keystore"));
/*      */           } else {
/* 1055 */             System.err.println(rb
/* 1056 */                 .getString("Certificate.was.not.added.to.keystore"));
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1060 */         if (inputStream != System.in) {
/* 1061 */           inputStream.close();
/*      */         }
/*      */       } 
/* 1064 */     } else if (this.command == Command.IMPORTKEYSTORE) {
/*      */       
/* 1066 */       if (keyStore == null) {
/* 1067 */         keyStore = loadSourceKeyStore();
/*      */       }
/* 1069 */       doImportKeyStore(keyStore);
/* 1070 */       this.kssave = true;
/* 1071 */     } else if (this.command == Command.KEYCLONE) {
/* 1072 */       this.keyPassNew = this.newPass;
/*      */ 
/*      */       
/* 1075 */       if (this.alias == null) {
/* 1076 */         this.alias = "mykey";
/*      */       }
/* 1078 */       if (!this.keyStore.containsAlias(this.alias)) {
/*      */         
/* 1080 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 1081 */         Object[] arrayOfObject = { this.alias };
/* 1082 */         throw new Exception(messageFormat.format(arrayOfObject));
/*      */       } 
/* 1084 */       if (!this.keyStore.entryInstanceOf(this.alias, (Class)KeyStore.PrivateKeyEntry.class)) {
/* 1085 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.references.an.entry.type.that.is.not.a.private.key.entry.The.keyclone.command.only.supports.cloning.of.private.key"));
/*      */         
/* 1087 */         Object[] arrayOfObject = { this.alias };
/* 1088 */         throw new Exception(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */       
/* 1091 */       doCloneEntry(this.alias, this.dest, true);
/* 1092 */       this.kssave = true;
/* 1093 */     } else if (this.command == Command.CHANGEALIAS) {
/* 1094 */       if (this.alias == null) {
/* 1095 */         this.alias = "mykey";
/*      */       }
/* 1097 */       doCloneEntry(this.alias, this.dest, false);
/*      */       
/* 1099 */       if (this.keyStore.containsAlias(this.alias)) {
/* 1100 */         doDeleteEntry(this.alias);
/*      */       }
/* 1102 */       this.kssave = true;
/* 1103 */     } else if (this.command == Command.KEYPASSWD) {
/* 1104 */       this.keyPassNew = this.newPass;
/* 1105 */       doChangeKeyPasswd(this.alias);
/* 1106 */       this.kssave = true;
/* 1107 */     } else if (this.command == Command.LIST) {
/* 1108 */       if (this.storePass == null && 
/* 1109 */         !KeyStoreUtil.isWindowsKeyStore(this.storetype)) {
/* 1110 */         printNoIntegrityWarning();
/*      */       }
/*      */       
/* 1113 */       if (this.alias != null) {
/* 1114 */         doPrintEntry(rb.getString("the.certificate"), this.alias, paramPrintStream);
/*      */       } else {
/* 1116 */         doPrintEntries(paramPrintStream);
/*      */       } 
/* 1118 */     } else if (this.command == Command.PRINTCERT) {
/* 1119 */       doPrintCert(paramPrintStream);
/* 1120 */     } else if (this.command == Command.SELFCERT) {
/* 1121 */       doSelfCert(this.alias, this.dname, this.sigAlgName);
/* 1122 */       this.kssave = true;
/* 1123 */     } else if (this.command == Command.STOREPASSWD) {
/* 1124 */       this.storePassNew = this.newPass;
/* 1125 */       if (this.storePassNew == null) {
/* 1126 */         this.storePassNew = getNewPasswd("keystore password", this.storePass);
/*      */       }
/* 1128 */       this.kssave = true;
/* 1129 */     } else if (this.command == Command.GENCERT) {
/* 1130 */       if (this.alias == null) {
/* 1131 */         this.alias = "mykey";
/*      */       }
/* 1133 */       InputStream inputStream = System.in;
/* 1134 */       if (this.infilename != null) {
/* 1135 */         inputStream = new FileInputStream(this.infilename);
/*      */       }
/* 1137 */       PrintStream printStream = null;
/* 1138 */       if (this.outfilename != null) {
/* 1139 */         printStream = new PrintStream(new FileOutputStream(this.outfilename));
/* 1140 */         paramPrintStream = printStream;
/*      */       } 
/*      */       try {
/* 1143 */         doGenCert(this.alias, this.sigAlgName, inputStream, paramPrintStream);
/*      */       } finally {
/* 1145 */         if (inputStream != System.in) {
/* 1146 */           inputStream.close();
/*      */         }
/* 1148 */         if (printStream != null) {
/* 1149 */           printStream.close();
/*      */         }
/*      */       } 
/* 1152 */     } else if (this.command == Command.GENCRL) {
/* 1153 */       if (this.alias == null) {
/* 1154 */         this.alias = "mykey";
/*      */       }
/* 1156 */       if (this.filename != null) {
/* 1157 */         try (PrintStream null = new PrintStream(new FileOutputStream(this.filename))) {
/*      */           
/* 1159 */           doGenCRL(printStream);
/*      */         } 
/*      */       } else {
/* 1162 */         doGenCRL(paramPrintStream);
/*      */       } 
/* 1164 */     } else if (this.command == Command.PRINTCERTREQ) {
/* 1165 */       if (this.filename != null) {
/* 1166 */         try (FileInputStream null = new FileInputStream(this.filename)) {
/* 1167 */           doPrintCertReq(fileInputStream, paramPrintStream);
/*      */         } 
/*      */       } else {
/* 1170 */         doPrintCertReq(System.in, paramPrintStream);
/*      */       } 
/* 1172 */     } else if (this.command == Command.PRINTCRL) {
/* 1173 */       doPrintCRL(this.filename, paramPrintStream);
/*      */     } 
/*      */ 
/*      */     
/* 1177 */     if (this.kssave) {
/* 1178 */       if (this.verbose) {
/*      */         
/* 1180 */         MessageFormat messageFormat = new MessageFormat(rb.getString(".Storing.ksfname."));
/* 1181 */         Object[] arrayOfObject = { this.nullStream ? "keystore" : this.ksfname };
/* 1182 */         System.err.println(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */       
/* 1185 */       if (this.token) {
/* 1186 */         this.keyStore.store(null, null);
/*      */       } else {
/* 1188 */         char[] arrayOfChar = (this.storePassNew != null) ? this.storePassNew : this.storePass;
/* 1189 */         if (this.nullStream) {
/* 1190 */           this.keyStore.store(null, arrayOfChar);
/*      */         } else {
/* 1192 */           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 1193 */           this.keyStore.store(byteArrayOutputStream, arrayOfChar);
/* 1194 */           try (FileOutputStream null = new FileOutputStream(this.ksfname)) {
/* 1195 */             fileOutputStream.write(byteArrayOutputStream.toByteArray());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1201 */     if (isKeyStoreRelated(this.command) && !this.token && !this.nullStream && this.ksfname != null) {
/*      */ 
/*      */ 
/*      */       
/* 1205 */       File file = new File(this.ksfname);
/* 1206 */       if (file.exists()) {
/*      */ 
/*      */         
/* 1209 */         String str = keyStoreType(file);
/* 1210 */         if (str.equalsIgnoreCase("JKS") || str
/* 1211 */           .equalsIgnoreCase("JCEKS")) {
/* 1212 */           boolean bool = true;
/* 1213 */           for (String str1 : Collections.<String>list(this.keyStore.aliases())) {
/* 1214 */             if (!this.keyStore.entryInstanceOf(str1, (Class)KeyStore.TrustedCertificateEntry.class)) {
/*      */               
/* 1216 */               bool = false;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/* 1221 */           if (!bool) {
/* 1222 */             this.weakWarnings.add(String.format(rb
/* 1223 */                   .getString("jks.storetype.warning"), new Object[] { str, this.ksfname }));
/*      */           }
/*      */         } 
/*      */         
/* 1227 */         if (this.inplaceImport) {
/*      */           
/* 1229 */           String str1 = keyStoreType(new File(this.inplaceBackupName));
/*      */ 
/*      */ 
/*      */           
/* 1233 */           String str2 = str.equalsIgnoreCase(str1) ? rb.getString("backup.keystore.warning") : rb.getString("migrate.keystore.warning");
/* 1234 */           this.weakWarnings.add(
/* 1235 */               String.format(str2, new Object[] { this.srcksfname, str1, this.inplaceBackupName, str }));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String keyStoreType(File paramFile) throws IOException {
/* 1246 */     int i = -17957139;
/* 1247 */     int j = -825307442;
/* 1248 */     try (DataInputStream null = new DataInputStream(new FileInputStream(paramFile))) {
/*      */       
/* 1250 */       int k = dataInputStream.readInt();
/* 1251 */       if (k == i)
/* 1252 */         return "JKS"; 
/* 1253 */       if (k == j) {
/* 1254 */         return "JCEKS";
/*      */       }
/* 1256 */       return "Non JKS/JCEKS";
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
/*      */   private void doGenCert(String paramString1, String paramString2, InputStream paramInputStream, PrintStream paramPrintStream) throws Exception {
/* 1270 */     if (!this.keyStore.containsAlias(paramString1)) {
/*      */       
/* 1272 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 1273 */       Object[] arrayOfObject = { paramString1 };
/* 1274 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 1276 */     Certificate certificate = this.keyStore.getCertificate(paramString1);
/* 1277 */     byte[] arrayOfByte1 = certificate.getEncoded();
/* 1278 */     X509CertImpl x509CertImpl1 = new X509CertImpl(arrayOfByte1);
/* 1279 */     X509CertInfo x509CertInfo1 = (X509CertInfo)x509CertImpl1.get("x509.info");
/*      */     
/* 1281 */     X500Name x500Name = (X500Name)x509CertInfo1.get("subject.dname");
/*      */ 
/*      */     
/* 1284 */     Date date1 = getStartDate(this.startDate);
/* 1285 */     Date date2 = new Date();
/* 1286 */     date2.setTime(date1.getTime() + this.validity * 1000L * 24L * 60L * 60L);
/* 1287 */     CertificateValidity certificateValidity = new CertificateValidity(date1, date2);
/*      */ 
/*      */ 
/*      */     
/* 1291 */     PrivateKey privateKey = (PrivateKey)(recoverKey(paramString1, this.storePass, this.keyPass)).fst;
/* 1292 */     if (paramString2 == null) {
/* 1293 */       paramString2 = getCompatibleSigAlgName(privateKey.getAlgorithm());
/*      */     }
/* 1295 */     Signature signature = Signature.getInstance(paramString2);
/* 1296 */     signature.initSign(privateKey);
/*      */     
/* 1298 */     X509CertInfo x509CertInfo2 = new X509CertInfo();
/* 1299 */     x509CertInfo2.set("validity", certificateValidity);
/* 1300 */     x509CertInfo2.set("serialNumber", new CertificateSerialNumber((new Random())
/* 1301 */           .nextInt() & Integer.MAX_VALUE));
/* 1302 */     x509CertInfo2.set("version", new CertificateVersion(2));
/*      */     
/* 1304 */     x509CertInfo2.set("algorithmID", new CertificateAlgorithmId(
/*      */           
/* 1306 */           AlgorithmId.get(paramString2)));
/* 1307 */     x509CertInfo2.set("issuer", x500Name);
/*      */     
/* 1309 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/* 1310 */     boolean bool = false;
/* 1311 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     while (true) {
/* 1313 */       String str = bufferedReader.readLine();
/* 1314 */       if (str == null) {
/*      */         break;
/*      */       }
/* 1317 */       if (str.startsWith("-----BEGIN") && str.indexOf("REQUEST") >= 0) {
/* 1318 */         bool = true; continue;
/*      */       } 
/* 1320 */       if (str.startsWith("-----END") && str.indexOf("REQUEST") >= 0)
/*      */         break; 
/* 1322 */       if (bool) {
/* 1323 */         stringBuffer.append(str);
/*      */       }
/*      */     } 
/* 1326 */     byte[] arrayOfByte2 = Pem.decode(new String(stringBuffer));
/* 1327 */     PKCS10 pKCS10 = new PKCS10(arrayOfByte2);
/*      */     
/* 1329 */     checkWeak(rb.getString("the.certificate.request"), pKCS10);
/*      */     
/* 1331 */     x509CertInfo2.set("key", new CertificateX509Key(pKCS10.getSubjectPublicKeyInfo()));
/* 1332 */     x509CertInfo2.set("subject", (this.dname == null) ? pKCS10
/* 1333 */         .getSubjectName() : new X500Name(this.dname));
/* 1334 */     CertificateExtensions certificateExtensions1 = null;
/* 1335 */     Iterator<PKCS10Attribute> iterator = pKCS10.getAttributes().getAttributes().iterator();
/* 1336 */     while (iterator.hasNext()) {
/* 1337 */       PKCS10Attribute pKCS10Attribute = iterator.next();
/* 1338 */       if (pKCS10Attribute.getAttributeId().equals(PKCS9Attribute.EXTENSION_REQUEST_OID)) {
/* 1339 */         certificateExtensions1 = (CertificateExtensions)pKCS10Attribute.getAttributeValue();
/*      */       }
/*      */     } 
/* 1342 */     CertificateExtensions certificateExtensions2 = createV3Extensions(certificateExtensions1, null, this.v3ext, pKCS10
/*      */ 
/*      */ 
/*      */         
/* 1346 */         .getSubjectPublicKeyInfo(), certificate
/* 1347 */         .getPublicKey());
/* 1348 */     x509CertInfo2.set("extensions", certificateExtensions2);
/* 1349 */     X509CertImpl x509CertImpl2 = new X509CertImpl(x509CertInfo2);
/* 1350 */     x509CertImpl2.sign(privateKey, paramString2);
/* 1351 */     dumpCert(x509CertImpl2, paramPrintStream);
/* 1352 */     for (Certificate certificate1 : this.keyStore.getCertificateChain(paramString1)) {
/* 1353 */       if (certificate1 instanceof X509Certificate) {
/* 1354 */         X509Certificate x509Certificate = (X509Certificate)certificate1;
/* 1355 */         if (!KeyStoreUtil.isSelfSigned(x509Certificate)) {
/* 1356 */           dumpCert(x509Certificate, paramPrintStream);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1361 */     checkWeak(rb.getString("the.issuer"), this.keyStore.getCertificateChain(paramString1));
/* 1362 */     checkWeak(rb.getString("the.generated.certificate"), x509CertImpl2);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doGenCRL(PrintStream paramPrintStream) throws Exception {
/* 1367 */     if (this.ids == null) {
/* 1368 */       throw new Exception("Must provide -id when -gencrl");
/*      */     }
/* 1370 */     Certificate certificate = this.keyStore.getCertificate(this.alias);
/* 1371 */     byte[] arrayOfByte = certificate.getEncoded();
/* 1372 */     X509CertImpl x509CertImpl = new X509CertImpl(arrayOfByte);
/* 1373 */     X509CertInfo x509CertInfo = (X509CertInfo)x509CertImpl.get("x509.info");
/*      */     
/* 1375 */     X500Name x500Name = (X500Name)x509CertInfo.get("subject.dname");
/*      */ 
/*      */     
/* 1378 */     Date date1 = getStartDate(this.startDate);
/* 1379 */     Date date2 = (Date)date1.clone();
/* 1380 */     date2.setTime(date2.getTime() + this.validity * 1000L * 24L * 60L * 60L);
/* 1381 */     CertificateValidity certificateValidity = new CertificateValidity(date1, date2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1386 */     PrivateKey privateKey = (PrivateKey)(recoverKey(this.alias, this.storePass, this.keyPass)).fst;
/* 1387 */     if (this.sigAlgName == null) {
/* 1388 */       this.sigAlgName = getCompatibleSigAlgName(privateKey.getAlgorithm());
/*      */     }
/*      */     
/* 1391 */     X509CRLEntry[] arrayOfX509CRLEntry = new X509CRLEntry[this.ids.size()];
/* 1392 */     for (byte b = 0; b < this.ids.size(); b++) {
/* 1393 */       String str = this.ids.get(b);
/* 1394 */       int i = str.indexOf(':');
/* 1395 */       if (i >= 0) {
/* 1396 */         CRLExtensions cRLExtensions = new CRLExtensions();
/* 1397 */         cRLExtensions.set("Reason", new CRLReasonCodeExtension(Integer.parseInt(str.substring(i + 1))));
/* 1398 */         arrayOfX509CRLEntry[b] = new X509CRLEntryImpl(new BigInteger(str.substring(0, i)), date1, cRLExtensions);
/*      */       } else {
/*      */         
/* 1401 */         arrayOfX509CRLEntry[b] = new X509CRLEntryImpl(new BigInteger(this.ids.get(b)), date1);
/*      */       } 
/*      */     } 
/* 1404 */     X509CRLImpl x509CRLImpl = new X509CRLImpl(x500Name, date1, date2, arrayOfX509CRLEntry);
/* 1405 */     x509CRLImpl.sign(privateKey, this.sigAlgName);
/* 1406 */     if (this.rfc) {
/* 1407 */       paramPrintStream.println("-----BEGIN X509 CRL-----");
/* 1408 */       paramPrintStream.println(Base64.getMimeEncoder(64, CRLF).encodeToString(x509CRLImpl.getEncodedInternal()));
/* 1409 */       paramPrintStream.println("-----END X509 CRL-----");
/*      */     } else {
/* 1411 */       paramPrintStream.write(x509CRLImpl.getEncodedInternal());
/*      */     } 
/* 1413 */     checkWeak(rb.getString("the.generated.crl"), x509CRLImpl, privateKey);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doCertReq(String paramString1, String paramString2, PrintStream paramPrintStream) throws Exception {
/* 1423 */     if (paramString1 == null) {
/* 1424 */       paramString1 = "mykey";
/*      */     }
/*      */     
/* 1427 */     Pair<Key, char[]> pair = recoverKey(paramString1, this.storePass, this.keyPass);
/* 1428 */     PrivateKey privateKey = (PrivateKey)pair.fst;
/* 1429 */     if (this.keyPass == null) {
/* 1430 */       this.keyPass = (char[])pair.snd;
/*      */     }
/*      */     
/* 1433 */     Certificate certificate = this.keyStore.getCertificate(paramString1);
/* 1434 */     if (certificate == null) {
/*      */       
/* 1436 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias.has.no.public.key.certificate."));
/* 1437 */       Object[] arrayOfObject = { paramString1 };
/* 1438 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 1440 */     PKCS10 pKCS10 = new PKCS10(certificate.getPublicKey());
/* 1441 */     CertificateExtensions certificateExtensions = createV3Extensions(null, null, this.v3ext, certificate.getPublicKey(), null);
/*      */     
/* 1443 */     pKCS10.getAttributes().setAttribute("extensions", new PKCS10Attribute(PKCS9Attribute.EXTENSION_REQUEST_OID, certificateExtensions));
/*      */ 
/*      */ 
/*      */     
/* 1447 */     if (paramString2 == null) {
/* 1448 */       paramString2 = getCompatibleSigAlgName(privateKey.getAlgorithm());
/*      */     }
/*      */     
/* 1451 */     Signature signature = Signature.getInstance(paramString2);
/* 1452 */     signature.initSign(privateKey);
/*      */     
/* 1454 */     X500Name x500Name = (this.dname == null) ? new X500Name(((X509Certificate)certificate).getSubjectDN().toString()) : new X500Name(this.dname);
/*      */ 
/*      */ 
/*      */     
/* 1458 */     pKCS10.encodeAndSign(x500Name, signature);
/* 1459 */     pKCS10.print(paramPrintStream);
/*      */     
/* 1461 */     checkWeak(rb.getString("the.generated.certificate.request"), pKCS10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doDeleteEntry(String paramString) throws Exception {
/* 1468 */     if (!this.keyStore.containsAlias(paramString)) {
/*      */       
/* 1470 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 1471 */       Object[] arrayOfObject = { paramString };
/* 1472 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 1474 */     this.keyStore.deleteEntry(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doExportCert(String paramString, PrintStream paramPrintStream) throws Exception {
/* 1483 */     if (this.storePass == null && 
/* 1484 */       !KeyStoreUtil.isWindowsKeyStore(this.storetype)) {
/* 1485 */       printNoIntegrityWarning();
/*      */     }
/* 1487 */     if (paramString == null) {
/* 1488 */       paramString = "mykey";
/*      */     }
/* 1490 */     if (!this.keyStore.containsAlias(paramString)) {
/*      */       
/* 1492 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 1493 */       Object[] arrayOfObject = { paramString };
/* 1494 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 1497 */     X509Certificate x509Certificate = (X509Certificate)this.keyStore.getCertificate(paramString);
/* 1498 */     if (x509Certificate == null) {
/*      */       
/* 1500 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.has.no.certificate"));
/* 1501 */       Object[] arrayOfObject = { paramString };
/* 1502 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 1504 */     dumpCert(x509Certificate, paramPrintStream);
/* 1505 */     checkWeak(rb.getString("the.certificate"), x509Certificate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] promptForKeyPass(String paramString1, String paramString2, char[] paramArrayOfchar) throws Exception {
/* 1515 */     if ("PKCS12".equalsIgnoreCase(this.storetype))
/* 1516 */       return paramArrayOfchar; 
/* 1517 */     if (!this.token && !this.protectedPath) {
/*      */       byte b;
/*      */       
/* 1520 */       for (b = 0; b < 3; b++) {
/*      */         
/* 1522 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Enter.key.password.for.alias."));
/* 1523 */         Object[] arrayOfObject = { paramString1 };
/* 1524 */         System.err.println(messageFormat.format(arrayOfObject));
/* 1525 */         if (paramString2 == null) {
/* 1526 */           System.err.print(rb
/* 1527 */               .getString(".RETURN.if.same.as.keystore.password."));
/*      */         } else {
/*      */           
/* 1530 */           messageFormat = new MessageFormat(rb.getString(".RETURN.if.same.as.for.otherAlias."));
/* 1531 */           Object[] arrayOfObject1 = { paramString2 };
/* 1532 */           System.err.print(messageFormat.format(arrayOfObject1));
/*      */         } 
/* 1534 */         System.err.flush();
/* 1535 */         char[] arrayOfChar = Password.readPassword(System.in);
/* 1536 */         this.passwords.add(arrayOfChar);
/* 1537 */         if (arrayOfChar == null)
/* 1538 */           return paramArrayOfchar; 
/* 1539 */         if (arrayOfChar.length >= 6)
/* 1540 */         { System.err.print(rb.getString("Re.enter.new.password."));
/* 1541 */           char[] arrayOfChar1 = Password.readPassword(System.in);
/* 1542 */           this.passwords.add(arrayOfChar1);
/* 1543 */           if (!Arrays.equals(arrayOfChar, arrayOfChar1)) {
/* 1544 */             System.err
/* 1545 */               .println(rb.getString("They.don.t.match.Try.again"));
/*      */           } else {
/*      */             
/* 1548 */             return arrayOfChar;
/*      */           }  }
/* 1550 */         else { System.err.println(rb
/* 1551 */               .getString("Key.password.is.too.short.must.be.at.least.6.characters")); }
/*      */       
/*      */       } 
/* 1554 */       if (b == 3) {
/* 1555 */         if (this.command == Command.KEYCLONE) {
/* 1556 */           throw new Exception(rb
/* 1557 */               .getString("Too.many.failures.Key.entry.not.cloned"));
/*      */         }
/* 1559 */         throw new Exception(rb
/* 1560 */             .getString("Too.many.failures.key.not.added.to.keystore"));
/*      */       } 
/*      */     } 
/*      */     
/* 1564 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] promptForCredential() throws Exception {
/* 1572 */     if (System.console() == null) {
/* 1573 */       char[] arrayOfChar = Password.readPassword(System.in);
/* 1574 */       this.passwords.add(arrayOfChar);
/* 1575 */       return arrayOfChar;
/*      */     } 
/*      */     
/*      */     byte b;
/* 1579 */     for (b = 0; b < 3; b++) {
/* 1580 */       System.err.print(rb
/* 1581 */           .getString("Enter.the.password.to.be.stored."));
/* 1582 */       System.err.flush();
/* 1583 */       char[] arrayOfChar1 = Password.readPassword(System.in);
/* 1584 */       this.passwords.add(arrayOfChar1);
/* 1585 */       System.err.print(rb.getString("Re.enter.password."));
/* 1586 */       char[] arrayOfChar2 = Password.readPassword(System.in);
/* 1587 */       this.passwords.add(arrayOfChar2);
/* 1588 */       if (!Arrays.equals(arrayOfChar1, arrayOfChar2)) {
/* 1589 */         System.err.println(rb.getString("They.don.t.match.Try.again"));
/*      */       } else {
/*      */         
/* 1592 */         return arrayOfChar1;
/*      */       } 
/*      */     } 
/* 1595 */     if (b == 3) {
/* 1596 */       throw new Exception(rb
/* 1597 */           .getString("Too.many.failures.key.not.added.to.keystore"));
/*      */     }
/*      */     
/* 1600 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doGenSecretKey(String paramString1, String paramString2, int paramInt) throws Exception {
/* 1610 */     if (paramString1 == null) {
/* 1611 */       paramString1 = "mykey";
/*      */     }
/* 1613 */     if (this.keyStore.containsAlias(paramString1)) {
/*      */       
/* 1615 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Secret.key.not.generated.alias.alias.already.exists"));
/* 1616 */       Object[] arrayOfObject = { paramString1 };
/* 1617 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */     
/* 1621 */     boolean bool = true;
/* 1622 */     SecretKey secretKey = null;
/*      */     
/* 1624 */     if (paramString2.toUpperCase(Locale.ENGLISH).startsWith("PBE")) {
/* 1625 */       SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBE");
/*      */ 
/*      */ 
/*      */       
/* 1629 */       secretKey = secretKeyFactory.generateSecret(new PBEKeySpec(promptForCredential()));
/*      */ 
/*      */       
/* 1632 */       if (!"PBE".equalsIgnoreCase(paramString2)) {
/* 1633 */         bool = false;
/*      */       }
/*      */       
/* 1636 */       if (this.verbose) {
/* 1637 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Generated.keyAlgName.secret.key"));
/*      */ 
/*      */         
/* 1640 */         Object[] arrayOfObject = { bool ? "PBE" : secretKey.getAlgorithm() };
/* 1641 */         System.err.println(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */     } else {
/* 1644 */       KeyGenerator keyGenerator = KeyGenerator.getInstance(paramString2);
/* 1645 */       if (paramInt == -1) {
/* 1646 */         if ("DES".equalsIgnoreCase(paramString2)) {
/* 1647 */           paramInt = 56;
/* 1648 */         } else if ("DESede".equalsIgnoreCase(paramString2)) {
/* 1649 */           paramInt = 168;
/*      */         } else {
/* 1651 */           throw new Exception(rb
/* 1652 */               .getString("Please.provide.keysize.for.secret.key.generation"));
/*      */         } 
/*      */       }
/* 1655 */       keyGenerator.init(paramInt);
/* 1656 */       secretKey = keyGenerator.generateKey();
/*      */       
/* 1658 */       if (this.verbose) {
/*      */         
/* 1660 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Generated.keysize.bit.keyAlgName.secret.key"));
/*      */         
/* 1662 */         Object[] arrayOfObject = { new Integer(paramInt), secretKey.getAlgorithm() };
/* 1663 */         System.err.println(messageFormat.format(arrayOfObject));
/*      */       } 
/*      */     } 
/*      */     
/* 1667 */     if (this.keyPass == null) {
/* 1668 */       this.keyPass = promptForKeyPass(paramString1, null, this.storePass);
/*      */     }
/*      */     
/* 1671 */     if (bool) {
/* 1672 */       this.keyStore.setKeyEntry(paramString1, secretKey, this.keyPass, null);
/*      */     } else {
/* 1674 */       this.keyStore.setEntry(paramString1, new KeyStore.SecretKeyEntry(secretKey), new KeyStore.PasswordProtection(this.keyPass, paramString2, null));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getCompatibleSigAlgName(String paramString) throws Exception {
/* 1685 */     if ("DSA".equalsIgnoreCase(paramString))
/* 1686 */       return "SHA256WithDSA"; 
/* 1687 */     if ("RSA".equalsIgnoreCase(paramString))
/* 1688 */       return "SHA256WithRSA"; 
/* 1689 */     if ("EC".equalsIgnoreCase(paramString)) {
/* 1690 */       return "SHA256withECDSA";
/*      */     }
/* 1692 */     throw new Exception(rb
/* 1693 */         .getString("Cannot.derive.signature.algorithm"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doGenKeyPair(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4) throws Exception {
/*      */     X500Name x500Name;
/* 1703 */     if (paramInt == -1) {
/* 1704 */       if ("EC".equalsIgnoreCase(paramString3)) {
/* 1705 */         paramInt = SecurityProviderConstants.DEF_EC_KEY_SIZE;
/* 1706 */       } else if ("RSA".equalsIgnoreCase(paramString3)) {
/* 1707 */         paramInt = SecurityProviderConstants.DEF_RSA_KEY_SIZE;
/* 1708 */       } else if ("DSA".equalsIgnoreCase(paramString3)) {
/* 1709 */         paramInt = SecurityProviderConstants.DEF_DSA_KEY_SIZE;
/*      */       } 
/*      */     }
/*      */     
/* 1713 */     if (paramString1 == null) {
/* 1714 */       paramString1 = "mykey";
/*      */     }
/*      */     
/* 1717 */     if (this.keyStore.containsAlias(paramString1)) {
/*      */       
/* 1719 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Key.pair.not.generated.alias.alias.already.exists"));
/* 1720 */       Object[] arrayOfObject = { paramString1 };
/* 1721 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 1724 */     if (paramString4 == null) {
/* 1725 */       paramString4 = getCompatibleSigAlgName(paramString3);
/*      */     }
/* 1727 */     CertAndKeyGen certAndKeyGen = new CertAndKeyGen(paramString3, paramString4, this.providerName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1733 */     if (paramString2 == null) {
/* 1734 */       x500Name = getX500Name();
/*      */     } else {
/* 1736 */       x500Name = new X500Name(paramString2);
/*      */     } 
/*      */     
/* 1739 */     certAndKeyGen.generate(paramInt);
/* 1740 */     PrivateKey privateKey = certAndKeyGen.getPrivateKey();
/*      */     
/* 1742 */     CertificateExtensions certificateExtensions = createV3Extensions(null, null, this.v3ext, certAndKeyGen
/*      */ 
/*      */ 
/*      */         
/* 1746 */         .getPublicKeyAnyway(), null);
/*      */ 
/*      */     
/* 1749 */     X509Certificate[] arrayOfX509Certificate = new X509Certificate[1];
/* 1750 */     arrayOfX509Certificate[0] = certAndKeyGen.getSelfCertificate(x500Name, 
/* 1751 */         getStartDate(this.startDate), this.validity * 24L * 60L * 60L, certificateExtensions);
/*      */     
/* 1753 */     if (this.verbose) {
/*      */       
/* 1755 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Generating.keysize.bit.keyAlgName.key.pair.and.self.signed.certificate.sigAlgName.with.a.validity.of.validality.days.for"));
/*      */ 
/*      */       
/* 1758 */       Object[] arrayOfObject = { new Integer(paramInt), privateKey.getAlgorithm(), arrayOfX509Certificate[0].getSigAlgName(), new Long(this.validity), x500Name };
/*      */ 
/*      */       
/* 1761 */       System.err.println(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 1764 */     if (this.keyPass == null) {
/* 1765 */       this.keyPass = promptForKeyPass(paramString1, null, this.storePass);
/*      */     }
/* 1767 */     checkWeak(rb.getString("the.generated.certificate"), arrayOfX509Certificate[0]);
/* 1768 */     this.keyStore.setKeyEntry(paramString1, privateKey, this.keyPass, (Certificate[])arrayOfX509Certificate);
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
/*      */   private void doCloneEntry(String paramString1, String paramString2, boolean paramBoolean) throws Exception {
/* 1780 */     if (paramString1 == null) {
/* 1781 */       paramString1 = "mykey";
/*      */     }
/*      */     
/* 1784 */     if (this.keyStore.containsAlias(paramString2)) {
/*      */       
/* 1786 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Destination.alias.dest.already.exists"));
/* 1787 */       Object[] arrayOfObject = { paramString2 };
/* 1788 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 1791 */     Pair<KeyStore.Entry, char[]> pair = recoverEntry(this.keyStore, paramString1, this.storePass, this.keyPass);
/* 1792 */     KeyStore.Entry entry = (KeyStore.Entry)pair.fst;
/* 1793 */     this.keyPass = (char[])pair.snd;
/*      */     
/* 1795 */     KeyStore.PasswordProtection passwordProtection = null;
/*      */     
/* 1797 */     if (this.keyPass != null) {
/* 1798 */       if (!paramBoolean || "PKCS12".equalsIgnoreCase(this.storetype)) {
/* 1799 */         this.keyPassNew = this.keyPass;
/*      */       }
/* 1801 */       else if (this.keyPassNew == null) {
/* 1802 */         this.keyPassNew = promptForKeyPass(paramString2, paramString1, this.keyPass);
/*      */       } 
/*      */       
/* 1805 */       passwordProtection = new KeyStore.PasswordProtection(this.keyPassNew);
/*      */     } 
/* 1807 */     this.keyStore.setEntry(paramString2, entry, passwordProtection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doChangeKeyPasswd(String paramString) throws Exception {
/* 1816 */     if (paramString == null) {
/* 1817 */       paramString = "mykey";
/*      */     }
/* 1819 */     Pair<Key, char[]> pair = recoverKey(paramString, this.storePass, this.keyPass);
/* 1820 */     Key key = (Key)pair.fst;
/* 1821 */     if (this.keyPass == null) {
/* 1822 */       this.keyPass = (char[])pair.snd;
/*      */     }
/*      */     
/* 1825 */     if (this.keyPassNew == null) {
/*      */       
/* 1827 */       MessageFormat messageFormat = new MessageFormat(rb.getString("key.password.for.alias."));
/* 1828 */       Object[] arrayOfObject = { paramString };
/* 1829 */       this.keyPassNew = getNewPasswd(messageFormat.format(arrayOfObject), this.keyPass);
/*      */     } 
/* 1831 */     this.keyStore.setKeyEntry(paramString, key, this.keyPassNew, this.keyStore
/* 1832 */         .getCertificateChain(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doImportIdentityDatabase(InputStream paramInputStream) throws Exception {
/* 1843 */     System.err.println(rb
/* 1844 */         .getString("No.entries.from.identity.database.added"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doPrintEntry(String paramString1, String paramString2, PrintStream paramPrintStream) throws Exception {
/* 1853 */     if (!this.keyStore.containsAlias(paramString2)) {
/*      */       
/* 1855 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 1856 */       Object[] arrayOfObject = { paramString2 };
/* 1857 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 1860 */     if (this.verbose || this.rfc || this.debug) {
/*      */       
/* 1862 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.name.alias"));
/* 1863 */       Object[] arrayOfObject = { paramString2 };
/* 1864 */       paramPrintStream.println(messageFormat.format(arrayOfObject));
/*      */       
/* 1866 */       if (!this.token)
/*      */       {
/* 1868 */         messageFormat = new MessageFormat(rb.getString("Creation.date.keyStore.getCreationDate.alias."));
/* 1869 */         Object[] arrayOfObject1 = { this.keyStore.getCreationDate(paramString2) };
/* 1870 */         paramPrintStream.println(messageFormat.format(arrayOfObject1));
/*      */       }
/*      */     
/* 1873 */     } else if (!this.token) {
/*      */       
/* 1875 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias.keyStore.getCreationDate.alias."));
/* 1876 */       Object[] arrayOfObject = { paramString2, this.keyStore.getCreationDate(paramString2) };
/* 1877 */       paramPrintStream.print(messageFormat.format(arrayOfObject));
/*      */     } else {
/*      */       
/* 1880 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias."));
/* 1881 */       Object[] arrayOfObject = { paramString2 };
/* 1882 */       paramPrintStream.print(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */     
/* 1886 */     if (this.keyStore.entryInstanceOf(paramString2, (Class)KeyStore.SecretKeyEntry.class)) {
/* 1887 */       if (this.verbose || this.rfc || this.debug) {
/* 1888 */         Object[] arrayOfObject = { "SecretKeyEntry" };
/* 1889 */         paramPrintStream.println((new MessageFormat(rb
/* 1890 */               .getString("Entry.type.type."))).format(arrayOfObject));
/*      */       } else {
/* 1892 */         paramPrintStream.println("SecretKeyEntry, ");
/*      */       } 
/* 1894 */     } else if (this.keyStore.entryInstanceOf(paramString2, (Class)KeyStore.PrivateKeyEntry.class)) {
/* 1895 */       if (this.verbose || this.rfc || this.debug) {
/* 1896 */         Object[] arrayOfObject = { "PrivateKeyEntry" };
/* 1897 */         paramPrintStream.println((new MessageFormat(rb
/* 1898 */               .getString("Entry.type.type."))).format(arrayOfObject));
/*      */       } else {
/* 1900 */         paramPrintStream.println("PrivateKeyEntry, ");
/*      */       } 
/*      */ 
/*      */       
/* 1904 */       Certificate[] arrayOfCertificate = this.keyStore.getCertificateChain(paramString2);
/* 1905 */       if (arrayOfCertificate != null) {
/* 1906 */         if (this.verbose || this.rfc || this.debug) {
/* 1907 */           paramPrintStream.println(rb
/* 1908 */               .getString("Certificate.chain.length.") + arrayOfCertificate.length);
/* 1909 */           for (byte b = 0; b < arrayOfCertificate.length; b++) {
/*      */             
/* 1911 */             MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.i.1."));
/* 1912 */             Object[] arrayOfObject = { new Integer(b + 1) };
/* 1913 */             paramPrintStream.println(messageFormat.format(arrayOfObject));
/* 1914 */             if (this.verbose && arrayOfCertificate[b] instanceof X509Certificate) {
/* 1915 */               printX509Cert((X509Certificate)arrayOfCertificate[b], paramPrintStream);
/* 1916 */             } else if (this.debug) {
/* 1917 */               paramPrintStream.println(arrayOfCertificate[b].toString());
/*      */             } else {
/* 1919 */               dumpCert(arrayOfCertificate[b], paramPrintStream);
/*      */             } 
/* 1921 */             checkWeak(paramString1, arrayOfCertificate[b]);
/*      */           } 
/*      */         } else {
/*      */           
/* 1925 */           paramPrintStream
/* 1926 */             .println(rb.getString("Certificate.fingerprint.SHA1.") + 
/* 1927 */               getCertFingerPrint("SHA1", arrayOfCertificate[0]));
/* 1928 */           checkWeak(paramString1, arrayOfCertificate[0]);
/*      */         } 
/*      */       }
/* 1931 */     } else if (this.keyStore.entryInstanceOf(paramString2, (Class)KeyStore.TrustedCertificateEntry.class)) {
/*      */ 
/*      */       
/* 1934 */       Certificate certificate = this.keyStore.getCertificate(paramString2);
/* 1935 */       Object[] arrayOfObject = { "trustedCertEntry" };
/*      */       
/* 1937 */       String str = (new MessageFormat(rb.getString("Entry.type.type."))).format(arrayOfObject) + "\n";
/* 1938 */       if (this.verbose && certificate instanceof X509Certificate) {
/* 1939 */         paramPrintStream.println(str);
/* 1940 */         printX509Cert((X509Certificate)certificate, paramPrintStream);
/* 1941 */       } else if (this.rfc) {
/* 1942 */         paramPrintStream.println(str);
/* 1943 */         dumpCert(certificate, paramPrintStream);
/* 1944 */       } else if (this.debug) {
/* 1945 */         paramPrintStream.println(certificate.toString());
/*      */       } else {
/* 1947 */         paramPrintStream.println("trustedCertEntry, ");
/* 1948 */         paramPrintStream.println(rb.getString("Certificate.fingerprint.SHA1.") + 
/* 1949 */             getCertFingerPrint("SHA1", certificate));
/*      */       } 
/* 1951 */       checkWeak(paramString1, certificate);
/*      */     } else {
/* 1953 */       paramPrintStream.println(rb.getString("Unknown.Entry.Type"));
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean inplaceImportCheck() throws Exception {
/* 1958 */     if ("PKCS11".equalsIgnoreCase(this.srcstoretype) || 
/* 1959 */       KeyStoreUtil.isWindowsKeyStore(this.srcstoretype)) {
/* 1960 */       return false;
/*      */     }
/*      */     
/* 1963 */     if (this.srcksfname != null) {
/* 1964 */       File file = new File(this.srcksfname);
/* 1965 */       if (file.exists() && file.length() == 0L) {
/* 1966 */         throw new Exception(rb
/* 1967 */             .getString("Source.keystore.file.exists.but.is.empty.") + this.srcksfname);
/*      */       }
/*      */       
/* 1970 */       if (file.getCanonicalFile()
/* 1971 */         .equals((new File(this.ksfname)).getCanonicalFile())) {
/* 1972 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1976 */       System.err.println(String.format(rb.getString("importing.keystore.status"), new Object[] { this.srcksfname, this.ksfname }));
/*      */       
/* 1978 */       return false;
/*      */     } 
/*      */     
/* 1981 */     throw new Exception(rb
/* 1982 */         .getString("Please.specify.srckeystore"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   KeyStore loadSourceKeyStore() throws Exception {
/*      */     KeyStore keyStore;
/* 1992 */     FileInputStream fileInputStream = null;
/* 1993 */     File file = null;
/*      */     
/* 1995 */     if ("PKCS11".equalsIgnoreCase(this.srcstoretype) || 
/* 1996 */       KeyStoreUtil.isWindowsKeyStore(this.srcstoretype)) {
/* 1997 */       if (!"NONE".equals(this.srcksfname)) {
/* 1998 */         System.err.println(MessageFormat.format(rb
/* 1999 */               .getString(".keystore.must.be.NONE.if.storetype.is.{0}"), new Object[] { this.srcstoretype }));
/* 2000 */         System.err.println();
/* 2001 */         tinyHelp();
/*      */       } 
/*      */     } else {
/* 2004 */       file = new File(this.srcksfname);
/* 2005 */       fileInputStream = new FileInputStream(file);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 2010 */       if (this.srcstoretype == null) {
/* 2011 */         this.srcstoretype = KeyStore.getDefaultType();
/*      */       }
/* 2013 */       if (this.srcProviderName == null) {
/* 2014 */         keyStore = KeyStore.getInstance(this.srcstoretype);
/*      */       } else {
/* 2016 */         keyStore = KeyStore.getInstance(this.srcstoretype, this.srcProviderName);
/*      */       } 
/*      */       
/* 2019 */       if (this.srcstorePass == null && !this.srcprotectedPath && 
/*      */         
/* 2021 */         !KeyStoreUtil.isWindowsKeyStore(this.srcstoretype)) {
/* 2022 */         System.err.print(rb.getString("Enter.source.keystore.password."));
/* 2023 */         System.err.flush();
/* 2024 */         this.srcstorePass = Password.readPassword(System.in);
/* 2025 */         this.passwords.add(this.srcstorePass);
/*      */       } 
/*      */ 
/*      */       
/* 2029 */       if ("PKCS12".equalsIgnoreCase(this.srcstoretype) && 
/* 2030 */         this.srckeyPass != null && this.srcstorePass != null && 
/* 2031 */         !Arrays.equals(this.srcstorePass, this.srckeyPass)) {
/* 2032 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Warning.Different.store.and.key.passwords.not.supported.for.PKCS12.KeyStores.Ignoring.user.specified.command.value."));
/*      */         
/* 2034 */         Object[] arrayOfObject = { "-srckeypass" };
/* 2035 */         System.err.println(messageFormat.format(arrayOfObject));
/* 2036 */         this.srckeyPass = this.srcstorePass;
/*      */       } 
/*      */ 
/*      */       
/* 2040 */       keyStore.load(fileInputStream, this.srcstorePass);
/*      */     } finally {
/* 2042 */       if (fileInputStream != null) {
/* 2043 */         fileInputStream.close();
/*      */       }
/*      */     } 
/*      */     
/* 2047 */     if (this.srcstorePass == null && 
/* 2048 */       !KeyStoreUtil.isWindowsKeyStore(this.srcstoretype)) {
/*      */ 
/*      */       
/* 2051 */       System.err.println();
/* 2052 */       System.err.println(rb
/* 2053 */           .getString(".WARNING.WARNING.WARNING."));
/* 2054 */       System.err.println(rb
/* 2055 */           .getString(".The.integrity.of.the.information.stored.in.the.srckeystore."));
/* 2056 */       System.err.println(rb
/* 2057 */           .getString(".WARNING.WARNING.WARNING."));
/* 2058 */       System.err.println();
/*      */     } 
/*      */     
/* 2061 */     return keyStore;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doImportKeyStore(KeyStore paramKeyStore) throws Exception {
/* 2071 */     if (this.alias != null) {
/* 2072 */       doImportKeyStoreSingle(paramKeyStore, this.alias);
/*      */     } else {
/* 2074 */       if (this.dest != null || this.srckeyPass != null) {
/* 2075 */         throw new Exception(rb.getString("if.alias.not.specified.destalias.and.srckeypass.must.not.be.specified"));
/*      */       }
/*      */       
/* 2078 */       doImportKeyStoreAll(paramKeyStore);
/*      */     } 
/*      */     
/* 2081 */     if (this.inplaceImport)
/*      */     {
/*      */       
/* 2084 */       for (byte b = 1;; b++) {
/* 2085 */         this.inplaceBackupName = this.srcksfname + ".old" + ((b == 1) ? "" : (String)Integer.valueOf(b));
/* 2086 */         File file = new File(this.inplaceBackupName);
/* 2087 */         if (!file.exists()) {
/* 2088 */           Files.copy(Paths.get(this.srcksfname, new String[0]), file.toPath(), new java.nio.file.CopyOption[0]);
/*      */           break;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int doImportKeyStoreSingle(KeyStore paramKeyStore, String paramString) throws Exception {
/* 2113 */     String str = (this.dest == null) ? paramString : this.dest;
/*      */     
/* 2115 */     if (this.keyStore.containsAlias(str)) {
/* 2116 */       Object[] arrayOfObject = { paramString };
/* 2117 */       if (this.noprompt) {
/* 2118 */         System.err.println((new MessageFormat(rb.getString("Warning.Overwriting.existing.alias.alias.in.destination.keystore")))
/* 2119 */             .format(arrayOfObject));
/*      */       } else {
/* 2121 */         String str1 = getYesNoReply((new MessageFormat(rb.getString("Existing.entry.alias.alias.exists.overwrite.no.")))
/* 2122 */             .format(arrayOfObject));
/* 2123 */         if ("NO".equals(str1)) {
/* 2124 */           str = inputStringFromStdin(rb
/* 2125 */               .getString("Enter.new.alias.name.RETURN.to.cancel.import.for.this.entry."));
/* 2126 */           if ("".equals(str)) {
/* 2127 */             System.err.println((new MessageFormat(rb.getString("Entry.for.alias.alias.not.imported.")))
/* 2128 */                 .format(arrayOfObject));
/*      */             
/* 2130 */             return 0;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2136 */     Pair<KeyStore.Entry, char[]> pair = recoverEntry(paramKeyStore, paramString, this.srcstorePass, this.srckeyPass);
/* 2137 */     KeyStore.Entry entry = (KeyStore.Entry)pair.fst;
/*      */     
/* 2139 */     KeyStore.PasswordProtection passwordProtection = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2145 */     char[] arrayOfChar = null;
/* 2146 */     if (this.destKeyPass != null) {
/* 2147 */       arrayOfChar = this.destKeyPass;
/* 2148 */       passwordProtection = new KeyStore.PasswordProtection(this.destKeyPass);
/* 2149 */     } else if (pair.snd != null) {
/* 2150 */       arrayOfChar = (char[])pair.snd;
/* 2151 */       passwordProtection = new KeyStore.PasswordProtection((char[])pair.snd);
/*      */     } 
/*      */     
/*      */     try {
/* 2155 */       Certificate certificate = paramKeyStore.getCertificate(paramString);
/* 2156 */       if (certificate != null) {
/* 2157 */         checkWeak("<" + str + ">", certificate);
/*      */       }
/* 2159 */       this.keyStore.setEntry(str, entry, passwordProtection);
/*      */ 
/*      */       
/* 2162 */       if ("PKCS12".equalsIgnoreCase(this.storetype) && 
/* 2163 */         arrayOfChar != null && !Arrays.equals(arrayOfChar, this.storePass)) {
/* 2164 */         throw new Exception(rb.getString("The.destination.pkcs12.keystore.has.different.storepass.and.keypass.Please.retry.with.destkeypass.specified."));
/*      */       }
/*      */ 
/*      */       
/* 2168 */       return 1;
/* 2169 */     } catch (KeyStoreException keyStoreException) {
/* 2170 */       Object[] arrayOfObject = { paramString, keyStoreException.toString() };
/* 2171 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Problem.importing.entry.for.alias.alias.exception.Entry.for.alias.alias.not.imported."));
/*      */       
/* 2173 */       System.err.println(messageFormat.format(arrayOfObject));
/* 2174 */       return 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void doImportKeyStoreAll(KeyStore paramKeyStore) throws Exception {
/* 2180 */     byte b = 0;
/* 2181 */     int i = paramKeyStore.size();
/* 2182 */     Enumeration<String> enumeration = paramKeyStore.aliases();
/* 2183 */     while (enumeration.hasMoreElements()) {
/* 2184 */       String str = enumeration.nextElement();
/* 2185 */       int j = doImportKeyStoreSingle(paramKeyStore, str);
/* 2186 */       if (j == 1) {
/* 2187 */         b++;
/* 2188 */         Object[] arrayOfObject1 = { str };
/* 2189 */         MessageFormat messageFormat1 = new MessageFormat(rb.getString("Entry.for.alias.alias.successfully.imported."));
/* 2190 */         System.err.println(messageFormat1.format(arrayOfObject1)); continue;
/* 2191 */       }  if (j == 2 && 
/* 2192 */         !this.noprompt) {
/* 2193 */         String str1 = getYesNoReply("Do you want to quit the import process? [no]:  ");
/* 2194 */         if ("YES".equals(str1)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2200 */     Object[] arrayOfObject = { Integer.valueOf(b), Integer.valueOf(i - b) };
/* 2201 */     MessageFormat messageFormat = new MessageFormat(rb.getString("Import.command.completed.ok.entries.successfully.imported.fail.entries.failed.or.cancelled"));
/*      */     
/* 2203 */     System.err.println(messageFormat.format(arrayOfObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doPrintEntries(PrintStream paramPrintStream) throws Exception {
/* 2213 */     String str = this.keyStore.getType();
/* 2214 */     if ("JKS".equalsIgnoreCase(str) && 
/* 2215 */       this.ksfile != null && this.ksfile.exists()) {
/* 2216 */       String str1 = keyStoreType(this.ksfile);
/*      */ 
/*      */       
/* 2219 */       if (!"JKS".equalsIgnoreCase(str1)) {
/* 2220 */         str = "PKCS12";
/*      */       }
/*      */     } 
/*      */     
/* 2224 */     paramPrintStream.println(rb.getString("Keystore.type.") + str);
/* 2225 */     paramPrintStream.println(rb.getString("Keystore.provider.") + this.keyStore
/* 2226 */         .getProvider().getName());
/* 2227 */     paramPrintStream.println();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2234 */     MessageFormat messageFormat = (this.keyStore.size() == 1) ? new MessageFormat(rb.getString("Your.keystore.contains.keyStore.size.entry")) : new MessageFormat(rb.getString("Your.keystore.contains.keyStore.size.entries"));
/* 2235 */     Object[] arrayOfObject = { new Integer(this.keyStore.size()) };
/* 2236 */     paramPrintStream.println(messageFormat.format(arrayOfObject));
/* 2237 */     paramPrintStream.println();
/*      */     
/* 2239 */     Enumeration<String> enumeration = this.keyStore.aliases();
/* 2240 */     while (enumeration.hasMoreElements()) {
/* 2241 */       String str1 = enumeration.nextElement();
/* 2242 */       doPrintEntry("<" + str1 + ">", str1, paramPrintStream);
/* 2243 */       if (this.verbose || this.rfc) {
/* 2244 */         paramPrintStream.println(rb.getString("NEWLINE"));
/* 2245 */         paramPrintStream.println(rb
/* 2246 */             .getString("STAR"));
/* 2247 */         paramPrintStream.println(rb
/* 2248 */             .getString("STARNN"));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static <T> Iterable<T> e2i(final Enumeration<T> e) {
/* 2254 */     return new Iterable<T>()
/*      */       {
/*      */         public Iterator<T> iterator() {
/* 2257 */           return new Iterator()
/*      */             {
/*      */               public boolean hasNext() {
/* 2260 */                 return e.hasMoreElements();
/*      */               }
/*      */               
/*      */               public T next() {
/* 2264 */                 return e.nextElement();
/*      */               }
/*      */               public void remove() {
/* 2267 */                 throw new UnsupportedOperationException("Not supported yet.");
/*      */               }
/*      */             };
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Collection<? extends CRL> loadCRLs(String paramString) throws Exception {
/* 2280 */     InputStream inputStream = null;
/* 2281 */     URI uRI = null;
/* 2282 */     if (paramString == null) {
/* 2283 */       inputStream = System.in;
/*      */     } else {
/*      */       try {
/* 2286 */         uRI = new URI(paramString);
/* 2287 */         if (!uRI.getScheme().equals("ldap"))
/*      */         {
/*      */           
/* 2290 */           inputStream = uRI.toURL().openStream();
/*      */         }
/* 2292 */       } catch (Exception exception) {
/*      */         try {
/* 2294 */           inputStream = new FileInputStream(paramString);
/* 2295 */         } catch (Exception exception1) {
/* 2296 */           if (uRI == null || uRI.getScheme() == null) {
/* 2297 */             throw exception1;
/*      */           }
/* 2299 */           throw exception;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2304 */     if (inputStream != null) {
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 2310 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 2311 */         byte[] arrayOfByte = new byte[4096];
/*      */         while (true) {
/* 2313 */           int i = inputStream.read(arrayOfByte);
/* 2314 */           if (i < 0)
/* 2315 */             break;  byteArrayOutputStream.write(arrayOfByte, 0, i);
/*      */         } 
/* 2317 */         return CertificateFactory.getInstance("X509").generateCRLs(new ByteArrayInputStream(byteArrayOutputStream
/* 2318 */               .toByteArray()));
/*      */       } finally {
/* 2320 */         if (inputStream != System.in) {
/* 2321 */           inputStream.close();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2326 */     CertStoreHelper certStoreHelper = CertStoreHelper.getInstance("LDAP");
/* 2327 */     String str = uRI.getPath();
/* 2328 */     if (str.charAt(0) == '/') str = str.substring(1); 
/* 2329 */     CertStore certStore = certStoreHelper.getCertStore(uRI);
/*      */     
/* 2331 */     X509CRLSelector x509CRLSelector = certStoreHelper.wrap(new X509CRLSelector(), null, str);
/* 2332 */     return certStore.getCRLs(x509CRLSelector);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<CRL> readCRLsFromCert(X509Certificate paramX509Certificate) throws Exception {
/* 2342 */     ArrayList<CRL> arrayList = new ArrayList();
/*      */     
/* 2344 */     CRLDistributionPointsExtension cRLDistributionPointsExtension = X509CertImpl.toImpl(paramX509Certificate).getCRLDistributionPointsExtension();
/* 2345 */     if (cRLDistributionPointsExtension == null) return arrayList;
/*      */     
/* 2347 */     List<DistributionPoint> list = cRLDistributionPointsExtension.get("points");
/* 2348 */     for (DistributionPoint distributionPoint : list) {
/* 2349 */       GeneralNames generalNames = distributionPoint.getFullName();
/* 2350 */       if (generalNames != null) {
/* 2351 */         label22: for (GeneralName generalName : generalNames.names()) {
/* 2352 */           if (generalName.getType() == 6) {
/* 2353 */             URIName uRIName = (URIName)generalName.getName();
/* 2354 */             Iterator<? extends CRL> iterator = loadCRLs(uRIName.getName()).iterator();
/*      */ 
/*      */             
/*      */             break label22;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2364 */     return arrayList;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String verifyCRL(KeyStore paramKeyStore, CRL paramCRL) throws Exception {
/* 2369 */     X509CRLImpl x509CRLImpl = (X509CRLImpl)paramCRL;
/* 2370 */     X500Principal x500Principal = x509CRLImpl.getIssuerX500Principal();
/* 2371 */     for (String str : e2i(paramKeyStore.aliases())) {
/* 2372 */       Certificate certificate = paramKeyStore.getCertificate(str);
/* 2373 */       if (certificate instanceof X509Certificate) {
/* 2374 */         X509Certificate x509Certificate = (X509Certificate)certificate;
/* 2375 */         if (x509Certificate.getSubjectX500Principal().equals(x500Principal)) {
/*      */           try {
/* 2377 */             ((X509CRLImpl)paramCRL).verify(certificate.getPublicKey());
/* 2378 */             return str;
/* 2379 */           } catch (Exception exception) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2384 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void doPrintCRL(String paramString, PrintStream paramPrintStream) throws Exception {
/* 2389 */     for (CRL cRL : loadCRLs(paramString)) {
/* 2390 */       printCRL(cRL, paramPrintStream);
/* 2391 */       String str = null;
/* 2392 */       Certificate certificate = null;
/* 2393 */       if (this.caks != null) {
/* 2394 */         str = verifyCRL(this.caks, cRL);
/* 2395 */         if (str != null) {
/* 2396 */           certificate = this.caks.getCertificate(str);
/* 2397 */           paramPrintStream.printf(rb.getString("verified.by.s.in.s.weak"), new Object[] { str, "cacerts", 
/*      */ 
/*      */ 
/*      */                 
/* 2401 */                 withWeak(certificate.getPublicKey()) });
/* 2402 */           paramPrintStream.println();
/*      */         } 
/*      */       } 
/* 2405 */       if (str == null && this.keyStore != null) {
/* 2406 */         str = verifyCRL(this.keyStore, cRL);
/* 2407 */         if (str != null) {
/* 2408 */           certificate = this.keyStore.getCertificate(str);
/* 2409 */           paramPrintStream.printf(rb.getString("verified.by.s.in.s.weak"), new Object[] { str, "keystore", 
/*      */ 
/*      */ 
/*      */                 
/* 2413 */                 withWeak(certificate.getPublicKey()) });
/* 2414 */           paramPrintStream.println();
/*      */         } 
/*      */       } 
/* 2417 */       if (str == null) {
/* 2418 */         paramPrintStream.println(rb
/* 2419 */             .getString("STAR"));
/* 2420 */         paramPrintStream.println(rb
/* 2421 */             .getString("warning.not.verified.make.sure.keystore.is.correct"));
/* 2422 */         paramPrintStream.println(rb
/* 2423 */             .getString("STARNN"));
/*      */       } 
/* 2425 */       checkWeak(rb.getString("the.crl"), cRL, (certificate == null) ? null : certificate.getPublicKey());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void printCRL(CRL paramCRL, PrintStream paramPrintStream) throws Exception {
/* 2431 */     X509CRL x509CRL = (X509CRL)paramCRL;
/* 2432 */     if (this.rfc) {
/* 2433 */       paramPrintStream.println("-----BEGIN X509 CRL-----");
/* 2434 */       paramPrintStream.println(Base64.getMimeEncoder(64, CRLF).encodeToString(x509CRL.getEncoded()));
/* 2435 */       paramPrintStream.println("-----END X509 CRL-----");
/*      */     } else {
/*      */       String str;
/* 2438 */       if (paramCRL instanceof X509CRLImpl) {
/* 2439 */         X509CRLImpl x509CRLImpl = (X509CRLImpl)paramCRL;
/* 2440 */         str = x509CRLImpl.toStringWithAlgName(withWeak("" + x509CRLImpl.getSigAlgId()));
/*      */       } else {
/* 2442 */         str = paramCRL.toString();
/*      */       } 
/* 2444 */       paramPrintStream.println(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void doPrintCertReq(InputStream paramInputStream, PrintStream paramPrintStream) throws Exception {
/* 2451 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
/* 2452 */     StringBuffer stringBuffer = new StringBuffer();
/* 2453 */     boolean bool = false;
/*      */     while (true) {
/* 2455 */       String str = bufferedReader.readLine();
/* 2456 */       if (str == null)
/* 2457 */         break;  if (!bool) {
/* 2458 */         if (str.startsWith("-----"))
/* 2459 */           bool = true; 
/*      */         continue;
/*      */       } 
/* 2462 */       if (str.startsWith("-----")) {
/*      */         break;
/*      */       }
/* 2465 */       stringBuffer.append(str);
/*      */     } 
/*      */     
/* 2468 */     PKCS10 pKCS10 = new PKCS10(Pem.decode(new String(stringBuffer)));
/*      */     
/* 2470 */     PublicKey publicKey = pKCS10.getSubjectPublicKeyInfo();
/* 2471 */     paramPrintStream.printf(rb.getString("PKCS.10.with.weak"), new Object[] { pKCS10
/* 2472 */           .getSubjectName(), publicKey
/* 2473 */           .getFormat(), 
/* 2474 */           withWeak(publicKey), 
/* 2475 */           withWeak(pKCS10.getSigAlg()) });
/* 2476 */     for (PKCS10Attribute pKCS10Attribute : pKCS10.getAttributes().getAttributes()) {
/* 2477 */       ObjectIdentifier objectIdentifier = pKCS10Attribute.getAttributeId();
/* 2478 */       if (objectIdentifier.equals(PKCS9Attribute.EXTENSION_REQUEST_OID)) {
/* 2479 */         CertificateExtensions certificateExtensions = (CertificateExtensions)pKCS10Attribute.getAttributeValue();
/* 2480 */         if (certificateExtensions != null)
/* 2481 */           printExtensions(rb.getString("Extension.Request."), certificateExtensions, paramPrintStream); 
/*      */         continue;
/*      */       } 
/* 2484 */       paramPrintStream.println("Attribute: " + pKCS10Attribute.getAttributeId());
/*      */ 
/*      */       
/* 2487 */       PKCS9Attribute pKCS9Attribute = new PKCS9Attribute(pKCS10Attribute.getAttributeId(), pKCS10Attribute.getAttributeValue());
/* 2488 */       paramPrintStream.print(pKCS9Attribute.getName() + ": ");
/* 2489 */       Object object = pKCS10Attribute.getAttributeValue();
/* 2490 */       paramPrintStream.println((object instanceof String[]) ? 
/* 2491 */           Arrays.toString((Object[])object) : object);
/*      */     } 
/*      */ 
/*      */     
/* 2495 */     if (this.debug) {
/* 2496 */       paramPrintStream.println(pKCS10);
/*      */     }
/* 2498 */     checkWeak(rb.getString("the.certificate.request"), pKCS10);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printCertFromStream(InputStream paramInputStream, PrintStream paramPrintStream) throws Exception {
/* 2508 */     Collection<? extends Certificate> collection = null;
/*      */     try {
/* 2510 */       collection = this.cf.generateCertificates(paramInputStream);
/* 2511 */     } catch (CertificateException certificateException) {
/* 2512 */       throw new Exception(rb.getString("Failed.to.parse.input"), certificateException);
/*      */     } 
/* 2514 */     if (collection.isEmpty()) {
/* 2515 */       throw new Exception(rb.getString("Empty.input"));
/*      */     }
/* 2517 */     Certificate[] arrayOfCertificate = collection.<Certificate>toArray(new Certificate[collection.size()]);
/* 2518 */     for (byte b = 0; b < arrayOfCertificate.length; b++) {
/* 2519 */       X509Certificate x509Certificate = null;
/*      */       try {
/* 2521 */         x509Certificate = (X509Certificate)arrayOfCertificate[b];
/* 2522 */       } catch (ClassCastException classCastException) {
/* 2523 */         throw new Exception(rb.getString("Not.X.509.certificate"));
/*      */       } 
/* 2525 */       if (arrayOfCertificate.length > 1) {
/*      */         
/* 2527 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.i.1."));
/* 2528 */         Object[] arrayOfObject = { new Integer(b + 1) };
/* 2529 */         paramPrintStream.println(messageFormat.format(arrayOfObject));
/*      */       } 
/* 2531 */       if (this.rfc) {
/* 2532 */         dumpCert(x509Certificate, paramPrintStream);
/*      */       } else {
/* 2534 */         printX509Cert(x509Certificate, paramPrintStream);
/* 2535 */       }  if (b < arrayOfCertificate.length - 1) {
/* 2536 */         paramPrintStream.println();
/*      */       }
/* 2538 */       checkWeak(oneInMany(rb.getString("the.certificate"), b, arrayOfCertificate.length), x509Certificate);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String oneInMany(String paramString, int paramInt1, int paramInt2) {
/* 2543 */     if (paramInt2 == 1) {
/* 2544 */       return paramString;
/*      */     }
/* 2546 */     return String.format(rb.getString("one.in.many"), new Object[] { paramString, Integer.valueOf(paramInt1 + 1), Integer.valueOf(paramInt2) });
/*      */   }
/*      */ 
/*      */   
/*      */   private void doPrintCert(PrintStream paramPrintStream) throws Exception {
/* 2551 */     if (this.jarfile != null) {
/* 2552 */       JarFile jarFile = new JarFile(this.jarfile, true);
/* 2553 */       Enumeration<JarEntry> enumeration = jarFile.entries();
/* 2554 */       HashSet<CodeSigner> hashSet = new HashSet();
/* 2555 */       byte[] arrayOfByte = new byte[8192];
/* 2556 */       byte b = 0;
/* 2557 */       while (enumeration.hasMoreElements()) {
/* 2558 */         JarEntry jarEntry = enumeration.nextElement();
/* 2559 */         try (InputStream null = jarFile.getInputStream(jarEntry)) {
/* 2560 */           while (inputStream.read(arrayOfByte) != -1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2566 */         CodeSigner[] arrayOfCodeSigner = jarEntry.getCodeSigners();
/* 2567 */         if (arrayOfCodeSigner != null) {
/* 2568 */           for (CodeSigner codeSigner : arrayOfCodeSigner) {
/* 2569 */             if (!hashSet.contains(codeSigner)) {
/* 2570 */               hashSet.add(codeSigner);
/* 2571 */               paramPrintStream.printf(rb.getString("Signer.d."), new Object[] { Integer.valueOf(++b) });
/* 2572 */               paramPrintStream.println();
/* 2573 */               paramPrintStream.println();
/* 2574 */               paramPrintStream.println(rb.getString("Signature."));
/* 2575 */               paramPrintStream.println();
/*      */ 
/*      */               
/* 2578 */               List<? extends Certificate> list = codeSigner.getSignerCertPath().getCertificates();
/* 2579 */               byte b1 = 0;
/* 2580 */               for (Certificate certificate : list) {
/* 2581 */                 X509Certificate x509Certificate = (X509Certificate)certificate;
/* 2582 */                 if (this.rfc) {
/* 2583 */                   paramPrintStream.println(rb.getString("Certificate.owner.") + x509Certificate.getSubjectDN() + "\n");
/* 2584 */                   dumpCert(x509Certificate, paramPrintStream);
/*      */                 } else {
/* 2586 */                   printX509Cert(x509Certificate, paramPrintStream);
/*      */                 } 
/* 2588 */                 paramPrintStream.println();
/* 2589 */                 checkWeak(oneInMany(rb.getString("the.certificate"), b1++, list.size()), x509Certificate);
/*      */               } 
/* 2591 */               Timestamp timestamp = codeSigner.getTimestamp();
/* 2592 */               if (timestamp != null) {
/* 2593 */                 paramPrintStream.println(rb.getString("Timestamp."));
/* 2594 */                 paramPrintStream.println();
/* 2595 */                 list = timestamp.getSignerCertPath().getCertificates();
/* 2596 */                 b1 = 0;
/* 2597 */                 for (Certificate certificate : list) {
/* 2598 */                   X509Certificate x509Certificate = (X509Certificate)certificate;
/* 2599 */                   if (this.rfc) {
/* 2600 */                     paramPrintStream.println(rb.getString("Certificate.owner.") + x509Certificate.getSubjectDN() + "\n");
/* 2601 */                     dumpCert(x509Certificate, paramPrintStream);
/*      */                   } else {
/* 2603 */                     printX509Cert(x509Certificate, paramPrintStream);
/*      */                   } 
/* 2605 */                   paramPrintStream.println();
/* 2606 */                   checkWeak(oneInMany(rb.getString("the.tsa.certificate"), b1++, list.size()), x509Certificate);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 2613 */       jarFile.close();
/* 2614 */       if (hashSet.isEmpty()) {
/* 2615 */         paramPrintStream.println(rb.getString("Not.a.signed.jar.file"));
/*      */       }
/* 2617 */     } else if (this.sslserver != null) {
/*      */       Collection<? extends Certificate> collection;
/* 2619 */       CertStoreHelper certStoreHelper = CertStoreHelper.getInstance("SSLServer");
/* 2620 */       CertStore certStore = certStoreHelper.getCertStore(new URI("https://" + this.sslserver));
/*      */       
/*      */       try {
/* 2623 */         collection = certStore.getCertificates(null);
/* 2624 */         if (collection.isEmpty())
/*      */         {
/*      */           
/* 2627 */           throw new Exception(rb.getString("No.certificate.from.the.SSL.server"));
/*      */         }
/*      */       }
/* 2630 */       catch (CertStoreException certStoreException) {
/* 2631 */         if (certStoreException.getCause() instanceof IOException) {
/* 2632 */           throw new Exception(rb.getString("No.certificate.from.the.SSL.server"), certStoreException
/*      */               
/* 2634 */               .getCause());
/*      */         }
/* 2636 */         throw certStoreException;
/*      */       } 
/*      */ 
/*      */       
/* 2640 */       byte b = 0;
/* 2641 */       for (Certificate certificate : collection) {
/*      */         try {
/* 2643 */           if (this.rfc) {
/* 2644 */             dumpCert(certificate, paramPrintStream);
/*      */           } else {
/* 2646 */             paramPrintStream.println("Certificate #" + b);
/* 2647 */             paramPrintStream.println("====================================");
/* 2648 */             printX509Cert((X509Certificate)certificate, paramPrintStream);
/* 2649 */             paramPrintStream.println();
/*      */           } 
/* 2651 */           checkWeak(oneInMany(rb.getString("the.certificate"), b++, collection.size()), certificate);
/* 2652 */         } catch (Exception exception) {
/* 2653 */           if (this.debug) {
/* 2654 */             exception.printStackTrace();
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/* 2659 */     } else if (this.filename != null) {
/* 2660 */       try (FileInputStream null = new FileInputStream(this.filename)) {
/* 2661 */         printCertFromStream(fileInputStream, paramPrintStream);
/*      */       } 
/*      */     } else {
/* 2664 */       printCertFromStream(System.in, paramPrintStream);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSelfCert(String paramString1, String paramString2, String paramString3) throws Exception {
/*      */     X500Name x500Name;
/* 2675 */     if (paramString1 == null) {
/* 2676 */       paramString1 = "mykey";
/*      */     }
/*      */     
/* 2679 */     Pair<Key, char[]> pair = recoverKey(paramString1, this.storePass, this.keyPass);
/* 2680 */     PrivateKey privateKey = (PrivateKey)pair.fst;
/* 2681 */     if (this.keyPass == null) {
/* 2682 */       this.keyPass = (char[])pair.snd;
/*      */     }
/*      */     
/* 2685 */     if (paramString3 == null) {
/* 2686 */       paramString3 = getCompatibleSigAlgName(privateKey.getAlgorithm());
/*      */     }
/*      */ 
/*      */     
/* 2690 */     Certificate certificate = this.keyStore.getCertificate(paramString1);
/* 2691 */     if (certificate == null) {
/*      */       
/* 2693 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias.has.no.public.key"));
/* 2694 */       Object[] arrayOfObject = { paramString1 };
/* 2695 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 2697 */     if (!(certificate instanceof X509Certificate)) {
/*      */       
/* 2699 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias.has.no.X.509.certificate"));
/* 2700 */       Object[] arrayOfObject = { paramString1 };
/* 2701 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2706 */     byte[] arrayOfByte = certificate.getEncoded();
/* 2707 */     X509CertImpl x509CertImpl1 = new X509CertImpl(arrayOfByte);
/* 2708 */     X509CertInfo x509CertInfo = (X509CertInfo)x509CertImpl1.get("x509.info");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2713 */     Date date1 = getStartDate(this.startDate);
/* 2714 */     Date date2 = new Date();
/* 2715 */     date2.setTime(date1.getTime() + this.validity * 1000L * 24L * 60L * 60L);
/* 2716 */     CertificateValidity certificateValidity = new CertificateValidity(date1, date2);
/*      */     
/* 2718 */     x509CertInfo.set("validity", certificateValidity);
/*      */ 
/*      */     
/* 2721 */     x509CertInfo.set("serialNumber", new CertificateSerialNumber((new Random())
/* 2722 */           .nextInt() & Integer.MAX_VALUE));
/*      */ 
/*      */ 
/*      */     
/* 2726 */     if (paramString2 == null) {
/*      */       
/* 2728 */       x500Name = (X500Name)x509CertInfo.get("subject.dname");
/*      */     }
/*      */     else {
/*      */       
/* 2732 */       x500Name = new X500Name(paramString2);
/* 2733 */       x509CertInfo.set("subject.dname", x500Name);
/*      */     } 
/*      */ 
/*      */     
/* 2737 */     x509CertInfo.set("issuer.dname", x500Name);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2744 */     X509CertImpl x509CertImpl2 = new X509CertImpl(x509CertInfo);
/* 2745 */     x509CertImpl2.sign(privateKey, paramString3);
/* 2746 */     AlgorithmId algorithmId = (AlgorithmId)x509CertImpl2.get("x509.algorithm");
/* 2747 */     x509CertInfo.set("algorithmID.algorithm", algorithmId);
/*      */ 
/*      */     
/* 2750 */     x509CertInfo.set("version", new CertificateVersion(2));
/*      */ 
/*      */     
/* 2753 */     CertificateExtensions certificateExtensions = createV3Extensions(null, (CertificateExtensions)x509CertInfo
/*      */         
/* 2755 */         .get("extensions"), this.v3ext, certificate
/*      */         
/* 2757 */         .getPublicKey(), null);
/*      */     
/* 2759 */     x509CertInfo.set("extensions", certificateExtensions);
/*      */     
/* 2761 */     x509CertImpl2 = new X509CertImpl(x509CertInfo);
/* 2762 */     x509CertImpl2.sign(privateKey, paramString3);
/*      */ 
/*      */     
/* 2765 */     this.keyStore.setKeyEntry(paramString1, privateKey, (this.keyPass != null) ? this.keyPass : this.storePass, new Certificate[] { x509CertImpl2 });
/*      */ 
/*      */ 
/*      */     
/* 2769 */     if (this.verbose) {
/* 2770 */       System.err.println(rb.getString("New.certificate.self.signed."));
/* 2771 */       System.err.print(x509CertImpl2.toString());
/* 2772 */       System.err.println();
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
/*      */   private boolean installReply(String paramString, InputStream paramInputStream) throws Exception {
/*      */     Certificate[] arrayOfCertificate2;
/* 2793 */     if (paramString == null) {
/* 2794 */       paramString = "mykey";
/*      */     }
/*      */     
/* 2797 */     Pair<Key, char[]> pair = recoverKey(paramString, this.storePass, this.keyPass);
/* 2798 */     PrivateKey privateKey = (PrivateKey)pair.fst;
/* 2799 */     if (this.keyPass == null) {
/* 2800 */       this.keyPass = (char[])pair.snd;
/*      */     }
/*      */     
/* 2803 */     Certificate certificate = this.keyStore.getCertificate(paramString);
/* 2804 */     if (certificate == null) {
/*      */       
/* 2806 */       MessageFormat messageFormat = new MessageFormat(rb.getString("alias.has.no.public.key.certificate."));
/* 2807 */       Object[] arrayOfObject = { paramString };
/* 2808 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */     
/* 2812 */     Collection<? extends Certificate> collection = this.cf.generateCertificates(paramInputStream);
/* 2813 */     if (collection.isEmpty()) {
/* 2814 */       throw new Exception(rb.getString("Reply.has.no.certificates"));
/*      */     }
/* 2816 */     Certificate[] arrayOfCertificate1 = collection.<Certificate>toArray(new Certificate[collection.size()]);
/*      */     
/* 2818 */     if (arrayOfCertificate1.length == 1) {
/*      */       
/* 2820 */       arrayOfCertificate2 = establishCertChain(certificate, arrayOfCertificate1[0]);
/*      */     } else {
/*      */       
/* 2823 */       arrayOfCertificate2 = validateReply(paramString, certificate, arrayOfCertificate1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2828 */     if (arrayOfCertificate2 != null) {
/* 2829 */       this.keyStore.setKeyEntry(paramString, privateKey, (this.keyPass != null) ? this.keyPass : this.storePass, arrayOfCertificate2);
/*      */ 
/*      */       
/* 2832 */       return true;
/*      */     } 
/* 2834 */     return false;
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
/*      */   private boolean addTrustedCert(String paramString, InputStream paramInputStream) throws Exception {
/* 2846 */     if (paramString == null) {
/* 2847 */       throw new Exception(rb.getString("Must.specify.alias"));
/*      */     }
/* 2849 */     if (this.keyStore.containsAlias(paramString)) {
/*      */       
/* 2851 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.not.imported.alias.alias.already.exists"));
/* 2852 */       Object[] arrayOfObject = { paramString };
/* 2853 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */ 
/*      */     
/* 2857 */     X509Certificate x509Certificate = null;
/*      */     try {
/* 2859 */       x509Certificate = (X509Certificate)this.cf.generateCertificate(paramInputStream);
/* 2860 */     } catch (ClassCastException|CertificateException classCastException) {
/* 2861 */       throw new Exception(rb.getString("Input.not.an.X.509.certificate"));
/*      */     } 
/*      */     
/* 2864 */     if (this.noprompt) {
/* 2865 */       checkWeak(rb.getString("the.input"), x509Certificate);
/* 2866 */       this.keyStore.setCertificateEntry(paramString, x509Certificate);
/* 2867 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2871 */     boolean bool = false;
/* 2872 */     if (KeyStoreUtil.isSelfSigned(x509Certificate)) {
/* 2873 */       x509Certificate.verify(x509Certificate.getPublicKey());
/* 2874 */       bool = true;
/*      */     } 
/*      */ 
/*      */     
/* 2878 */     String str1 = null;
/* 2879 */     String str2 = this.keyStore.getCertificateAlias(x509Certificate);
/* 2880 */     if (str2 != null) {
/*      */       
/* 2882 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.already.exists.in.keystore.under.alias.trustalias."));
/* 2883 */       Object[] arrayOfObject = { str2 };
/* 2884 */       System.err.println(messageFormat.format(arrayOfObject));
/* 2885 */       checkWeak(rb.getString("the.input"), x509Certificate);
/* 2886 */       printWeakWarnings(true);
/*      */       
/* 2888 */       str1 = getYesNoReply(rb.getString("Do.you.still.want.to.add.it.no."));
/* 2889 */     } else if (bool) {
/* 2890 */       if (this.trustcacerts && this.caks != null && (
/* 2891 */         str2 = this.caks.getCertificateAlias(x509Certificate)) != null) {
/*      */         
/* 2893 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.already.exists.in.system.wide.CA.keystore.under.alias.trustalias."));
/* 2894 */         Object[] arrayOfObject = { str2 };
/* 2895 */         System.err.println(messageFormat.format(arrayOfObject));
/* 2896 */         checkWeak(rb.getString("the.input"), x509Certificate);
/* 2897 */         printWeakWarnings(true);
/*      */         
/* 2899 */         str1 = getYesNoReply(rb.getString("Do.you.still.want.to.add.it.to.your.own.keystore.no."));
/*      */       } 
/* 2901 */       if (str2 == null) {
/*      */ 
/*      */         
/* 2904 */         printX509Cert(x509Certificate, System.out);
/* 2905 */         checkWeak(rb.getString("the.input"), x509Certificate);
/* 2906 */         printWeakWarnings(true);
/*      */         
/* 2908 */         str1 = getYesNoReply(rb.getString("Trust.this.certificate.no."));
/*      */       } 
/*      */     } 
/* 2911 */     if (str1 != null) {
/* 2912 */       if ("YES".equals(str1)) {
/* 2913 */         this.keyStore.setCertificateEntry(paramString, x509Certificate);
/* 2914 */         return true;
/*      */       } 
/* 2916 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2923 */       Certificate[] arrayOfCertificate = establishCertChain(null, x509Certificate);
/* 2924 */       if (arrayOfCertificate != null) {
/* 2925 */         this.keyStore.setCertificateEntry(paramString, x509Certificate);
/* 2926 */         return true;
/*      */       } 
/* 2928 */     } catch (Exception exception) {
/*      */ 
/*      */       
/* 2931 */       printX509Cert(x509Certificate, System.out);
/* 2932 */       checkWeak(rb.getString("the.input"), x509Certificate);
/* 2933 */       printWeakWarnings(true);
/*      */       
/* 2935 */       str1 = getYesNoReply(rb.getString("Trust.this.certificate.no."));
/* 2936 */       if ("YES".equals(str1)) {
/* 2937 */         this.keyStore.setCertificateEntry(paramString, x509Certificate);
/* 2938 */         return true;
/*      */       } 
/* 2940 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 2944 */     return false;
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
/*      */   private char[] getNewPasswd(String paramString, char[] paramArrayOfchar) throws Exception {
/* 2957 */     char[] arrayOfChar1 = null;
/* 2958 */     char[] arrayOfChar2 = null;
/*      */     
/* 2960 */     for (byte b = 0; b < 3; b++) {
/*      */       
/* 2962 */       MessageFormat messageFormat = new MessageFormat(rb.getString("New.prompt."));
/* 2963 */       Object[] arrayOfObject = { paramString };
/* 2964 */       System.err.print(messageFormat.format(arrayOfObject));
/* 2965 */       arrayOfChar1 = Password.readPassword(System.in);
/* 2966 */       this.passwords.add(arrayOfChar1);
/* 2967 */       if (arrayOfChar1 == null || arrayOfChar1.length < 6) {
/* 2968 */         System.err.println(rb
/* 2969 */             .getString("Password.is.too.short.must.be.at.least.6.characters"));
/* 2970 */       } else if (Arrays.equals(arrayOfChar1, paramArrayOfchar)) {
/* 2971 */         System.err.println(rb.getString("Passwords.must.differ"));
/*      */       } else {
/*      */         
/* 2974 */         messageFormat = new MessageFormat(rb.getString("Re.enter.new.prompt."));
/* 2975 */         Object[] arrayOfObject1 = { paramString };
/* 2976 */         System.err.print(messageFormat.format(arrayOfObject1));
/* 2977 */         arrayOfChar2 = Password.readPassword(System.in);
/* 2978 */         this.passwords.add(arrayOfChar2);
/* 2979 */         if (!Arrays.equals(arrayOfChar1, arrayOfChar2)) {
/* 2980 */           System.err
/* 2981 */             .println(rb.getString("They.don.t.match.Try.again"));
/*      */         } else {
/* 2983 */           Arrays.fill(arrayOfChar2, ' ');
/* 2984 */           return arrayOfChar1;
/*      */         } 
/*      */       } 
/* 2987 */       if (arrayOfChar1 != null) {
/* 2988 */         Arrays.fill(arrayOfChar1, ' ');
/* 2989 */         arrayOfChar1 = null;
/*      */       } 
/* 2991 */       if (arrayOfChar2 != null) {
/* 2992 */         Arrays.fill(arrayOfChar2, ' ');
/* 2993 */         arrayOfChar2 = null;
/*      */       } 
/*      */     } 
/* 2996 */     throw new Exception(rb.getString("Too.many.failures.try.later"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getAlias(String paramString) throws Exception {
/* 3005 */     if (paramString != null) {
/*      */       
/* 3007 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Enter.prompt.alias.name."));
/* 3008 */       Object[] arrayOfObject = { paramString };
/* 3009 */       System.err.print(messageFormat.format(arrayOfObject));
/*      */     } else {
/* 3011 */       System.err.print(rb.getString("Enter.alias.name."));
/*      */     } 
/* 3013 */     return (new BufferedReader(new InputStreamReader(System.in)))
/* 3014 */       .readLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String inputStringFromStdin(String paramString) throws Exception {
/* 3023 */     System.err.print(paramString);
/* 3024 */     return (new BufferedReader(new InputStreamReader(System.in)))
/* 3025 */       .readLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private char[] getKeyPasswd(String paramString1, String paramString2, char[] paramArrayOfchar) throws Exception {
/* 3036 */     byte b = 0;
/* 3037 */     char[] arrayOfChar = null;
/*      */     
/*      */     do {
/* 3040 */       if (paramArrayOfchar != null) {
/*      */         
/* 3042 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Enter.key.password.for.alias."));
/* 3043 */         Object[] arrayOfObject1 = { paramString1 };
/* 3044 */         System.err.println(messageFormat.format(arrayOfObject1));
/*      */ 
/*      */         
/* 3047 */         messageFormat = new MessageFormat(rb.getString(".RETURN.if.same.as.for.otherAlias."));
/* 3048 */         Object[] arrayOfObject2 = { paramString2 };
/* 3049 */         System.err.print(messageFormat.format(arrayOfObject2));
/*      */       } else {
/*      */         
/* 3052 */         MessageFormat messageFormat = new MessageFormat(rb.getString("Enter.key.password.for.alias."));
/* 3053 */         Object[] arrayOfObject = { paramString1 };
/* 3054 */         System.err.print(messageFormat.format(arrayOfObject));
/*      */       } 
/* 3056 */       System.err.flush();
/* 3057 */       arrayOfChar = Password.readPassword(System.in);
/* 3058 */       this.passwords.add(arrayOfChar);
/* 3059 */       if (arrayOfChar == null) {
/* 3060 */         arrayOfChar = paramArrayOfchar;
/*      */       }
/* 3062 */       b++;
/* 3063 */     } while (arrayOfChar == null && b < 3);
/*      */     
/* 3065 */     if (arrayOfChar == null) {
/* 3066 */       throw new Exception(rb.getString("Too.many.failures.try.later"));
/*      */     }
/*      */     
/* 3069 */     return arrayOfChar;
/*      */   }
/*      */   
/*      */   private String withWeak(String paramString) {
/* 3073 */     if (DISABLED_CHECK.permits(SIG_PRIMITIVE_SET, paramString, (AlgorithmParameters)null)) {
/* 3074 */       return paramString;
/*      */     }
/* 3076 */     return String.format(rb.getString("with.weak"), new Object[] { paramString });
/*      */   }
/*      */ 
/*      */   
/*      */   private String withWeak(PublicKey paramPublicKey) {
/* 3081 */     if (DISABLED_CHECK.permits(SIG_PRIMITIVE_SET, paramPublicKey))
/* 3082 */       return String.format(rb.getString("key.bit"), new Object[] {
/* 3083 */             Integer.valueOf(KeyUtil.getKeySize(paramPublicKey)), paramPublicKey.getAlgorithm()
/*      */           }); 
/* 3085 */     return String.format(rb.getString("key.bit.weak"), new Object[] {
/* 3086 */           Integer.valueOf(KeyUtil.getKeySize(paramPublicKey)), paramPublicKey.getAlgorithm()
/*      */         });
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
/*      */   private void printX509Cert(X509Certificate paramX509Certificate, PrintStream paramPrintStream) throws Exception {
/* 3115 */     MessageFormat messageFormat = new MessageFormat(rb.getString(".PATTERN.printX509Cert.with.weak"));
/* 3116 */     PublicKey publicKey = paramX509Certificate.getPublicKey();
/* 3117 */     String str = paramX509Certificate.getSigAlgName();
/*      */     
/* 3119 */     if (!isTrustedCert(paramX509Certificate)) {
/* 3120 */       str = withWeak(str);
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
/* 3132 */     Object[] arrayOfObject = { paramX509Certificate.getSubjectDN().toString(), paramX509Certificate.getIssuerDN().toString(), paramX509Certificate.getSerialNumber().toString(16), paramX509Certificate.getNotBefore().toString(), paramX509Certificate.getNotAfter().toString(), getCertFingerPrint("MD5", paramX509Certificate), getCertFingerPrint("SHA1", paramX509Certificate), getCertFingerPrint("SHA-256", paramX509Certificate), str, withWeak(publicKey), Integer.valueOf(paramX509Certificate.getVersion()) };
/*      */     
/* 3134 */     paramPrintStream.println(messageFormat.format(arrayOfObject));
/*      */     
/* 3136 */     if (paramX509Certificate instanceof X509CertImpl) {
/* 3137 */       X509CertImpl x509CertImpl = (X509CertImpl)paramX509Certificate;
/* 3138 */       X509CertInfo x509CertInfo = (X509CertInfo)x509CertImpl.get("x509.info");
/*      */ 
/*      */ 
/*      */       
/* 3142 */       CertificateExtensions certificateExtensions = (CertificateExtensions)x509CertInfo.get("extensions");
/* 3143 */       if (certificateExtensions != null) {
/* 3144 */         printExtensions(rb.getString("Extensions."), certificateExtensions, paramPrintStream);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printExtensions(String paramString, CertificateExtensions paramCertificateExtensions, PrintStream paramPrintStream) throws Exception {
/* 3151 */     byte b = 0;
/* 3152 */     Iterator<Extension> iterator1 = paramCertificateExtensions.getAllExtensions().iterator();
/* 3153 */     Iterator<Extension> iterator2 = paramCertificateExtensions.getUnparseableExtensions().values().iterator();
/* 3154 */     while (iterator1.hasNext() || iterator2.hasNext()) {
/* 3155 */       Extension extension = iterator1.hasNext() ? iterator1.next() : iterator2.next();
/* 3156 */       if (!b) {
/* 3157 */         paramPrintStream.println();
/* 3158 */         paramPrintStream.println(paramString);
/* 3159 */         paramPrintStream.println();
/*      */       } 
/* 3161 */       paramPrintStream.print("#" + ++b + ": " + extension);
/* 3162 */       if (extension.getClass() == Extension.class) {
/* 3163 */         byte[] arrayOfByte = extension.getExtensionValue();
/* 3164 */         if (arrayOfByte.length == 0) {
/* 3165 */           paramPrintStream.println(rb.getString(".Empty.value."));
/*      */         } else {
/* 3167 */           (new HexDumpEncoder()).encodeBuffer(extension.getExtensionValue(), paramPrintStream);
/* 3168 */           paramPrintStream.println();
/*      */         } 
/*      */       } 
/* 3171 */       paramPrintStream.println();
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
/*      */   private static Pair<String, Certificate> getSigner(Certificate paramCertificate, KeyStore paramKeyStore) throws Exception {
/* 3186 */     if (paramKeyStore.getCertificateAlias(paramCertificate) != null) {
/* 3187 */       return new Pair<>("", paramCertificate);
/*      */     }
/* 3189 */     Enumeration<String> enumeration = paramKeyStore.aliases();
/* 3190 */     while (enumeration.hasMoreElements()) {
/* 3191 */       String str = enumeration.nextElement();
/* 3192 */       Certificate certificate = paramKeyStore.getCertificate(str);
/* 3193 */       if (certificate != null) {
/*      */         try {
/* 3195 */           paramCertificate.verify(certificate.getPublicKey());
/* 3196 */           return new Pair<>(str, certificate);
/* 3197 */         } catch (Exception exception) {}
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 3202 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private X500Name getX500Name() throws IOException {
/*      */     X500Name x500Name;
/* 3210 */     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
/* 3211 */     String str1 = "Unknown";
/* 3212 */     String str2 = "Unknown";
/* 3213 */     String str3 = "Unknown";
/* 3214 */     String str4 = "Unknown";
/* 3215 */     String str5 = "Unknown";
/* 3216 */     String str6 = "Unknown";
/*      */     
/* 3218 */     String str7 = null;
/*      */     
/* 3220 */     byte b = 20;
/*      */     do {
/* 3222 */       if (b-- < 0) {
/* 3223 */         throw new RuntimeException(rb.getString("Too.many.retries.program.terminated"));
/*      */       }
/*      */       
/* 3226 */       str1 = inputString(bufferedReader, rb
/* 3227 */           .getString("What.is.your.first.and.last.name."), str1);
/*      */       
/* 3229 */       str2 = inputString(bufferedReader, rb
/*      */           
/* 3231 */           .getString("What.is.the.name.of.your.organizational.unit."), str2);
/*      */       
/* 3233 */       str3 = inputString(bufferedReader, rb
/* 3234 */           .getString("What.is.the.name.of.your.organization."), str3);
/*      */       
/* 3236 */       str4 = inputString(bufferedReader, rb
/* 3237 */           .getString("What.is.the.name.of.your.City.or.Locality."), str4);
/*      */       
/* 3239 */       str5 = inputString(bufferedReader, rb
/* 3240 */           .getString("What.is.the.name.of.your.State.or.Province."), str5);
/*      */       
/* 3242 */       str6 = inputString(bufferedReader, rb
/*      */           
/* 3244 */           .getString("What.is.the.two.letter.country.code.for.this.unit."), str6);
/*      */       
/* 3246 */       x500Name = new X500Name(str1, str2, str3, str4, str5, str6);
/*      */ 
/*      */       
/* 3249 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Is.name.correct."));
/* 3250 */       Object[] arrayOfObject = { x500Name };
/*      */       
/* 3252 */       str7 = inputString(bufferedReader, messageFormat.format(arrayOfObject), rb.getString("no"));
/* 3253 */     } while (collator.compare(str7, rb.getString("yes")) != 0 && collator
/* 3254 */       .compare(str7, rb.getString("y")) != 0);
/*      */     
/* 3256 */     System.err.println();
/* 3257 */     return x500Name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String inputString(BufferedReader paramBufferedReader, String paramString1, String paramString2) throws IOException {
/* 3264 */     System.err.println(paramString1);
/*      */     
/* 3266 */     MessageFormat messageFormat = new MessageFormat(rb.getString(".defaultValue."));
/* 3267 */     Object[] arrayOfObject = { paramString2 };
/* 3268 */     System.err.print(messageFormat.format(arrayOfObject));
/* 3269 */     System.err.flush();
/*      */     
/* 3271 */     String str = paramBufferedReader.readLine();
/* 3272 */     if (str == null || collator.compare(str, "") == 0) {
/* 3273 */       str = paramString2;
/*      */     }
/* 3275 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dumpCert(Certificate paramCertificate, PrintStream paramPrintStream) throws IOException, CertificateException {
/* 3285 */     if (this.rfc) {
/* 3286 */       paramPrintStream.println("-----BEGIN CERTIFICATE-----");
/* 3287 */       paramPrintStream.println(Base64.getMimeEncoder(64, CRLF).encodeToString(paramCertificate.getEncoded()));
/* 3288 */       paramPrintStream.println("-----END CERTIFICATE-----");
/*      */     } else {
/* 3290 */       paramPrintStream.write(paramCertificate.getEncoded());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void byte2hex(byte paramByte, StringBuffer paramStringBuffer) {
/* 3298 */     char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */     
/* 3300 */     int i = (paramByte & 0xF0) >> 4;
/* 3301 */     int j = paramByte & 0xF;
/* 3302 */     paramStringBuffer.append(arrayOfChar[i]);
/* 3303 */     paramStringBuffer.append(arrayOfChar[j]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String toHexString(byte[] paramArrayOfbyte) {
/* 3310 */     StringBuffer stringBuffer = new StringBuffer();
/* 3311 */     int i = paramArrayOfbyte.length;
/* 3312 */     for (byte b = 0; b < i; b++) {
/* 3313 */       byte2hex(paramArrayOfbyte[b], stringBuffer);
/* 3314 */       if (b < i - 1) {
/* 3315 */         stringBuffer.append(":");
/*      */       }
/*      */     } 
/* 3318 */     return stringBuffer.toString();
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
/*      */   private Pair<Key, char[]> recoverKey(String paramString, char[] paramArrayOfchar1, char[] paramArrayOfchar2) throws Exception {
/* 3332 */     Key key = null;
/*      */     
/* 3334 */     if (!this.keyStore.containsAlias(paramString)) {
/*      */       
/* 3336 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 3337 */       Object[] arrayOfObject = { paramString };
/* 3338 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/* 3340 */     if (!this.keyStore.entryInstanceOf(paramString, (Class)KeyStore.PrivateKeyEntry.class) && 
/* 3341 */       !this.keyStore.entryInstanceOf(paramString, (Class)KeyStore.SecretKeyEntry.class)) {
/*      */       
/* 3343 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.has.no.key"));
/* 3344 */       Object[] arrayOfObject = { paramString };
/* 3345 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 3348 */     if (paramArrayOfchar2 == null) {
/*      */       
/*      */       try {
/* 3351 */         key = this.keyStore.getKey(paramString, paramArrayOfchar1);
/*      */         
/* 3353 */         paramArrayOfchar2 = paramArrayOfchar1;
/* 3354 */         this.passwords.add(paramArrayOfchar2);
/* 3355 */       } catch (UnrecoverableKeyException unrecoverableKeyException) {
/*      */         
/* 3357 */         if (!this.token) {
/* 3358 */           paramArrayOfchar2 = getKeyPasswd(paramString, null, null);
/* 3359 */           key = this.keyStore.getKey(paramString, paramArrayOfchar2);
/*      */         } else {
/* 3361 */           throw unrecoverableKeyException;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 3365 */       key = this.keyStore.getKey(paramString, paramArrayOfchar2);
/*      */     } 
/*      */     
/* 3368 */     return (Pair)Pair.of(key, paramArrayOfchar2);
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
/*      */   private Pair<KeyStore.Entry, char[]> recoverEntry(KeyStore paramKeyStore, String paramString, char[] paramArrayOfchar1, char[] paramArrayOfchar2) throws Exception {
/*      */     KeyStore.Entry entry;
/* 3383 */     if (!paramKeyStore.containsAlias(paramString)) {
/*      */       
/* 3385 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Alias.alias.does.not.exist"));
/* 3386 */       Object[] arrayOfObject = { paramString };
/* 3387 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 3390 */     KeyStore.ProtectionParameter protectionParameter = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 3397 */       entry = paramKeyStore.getEntry(paramString, protectionParameter);
/* 3398 */       paramArrayOfchar2 = null;
/* 3399 */     } catch (UnrecoverableEntryException unrecoverableEntryException) {
/*      */       
/* 3401 */       if ("PKCS11".equalsIgnoreCase(paramKeyStore.getType()) || 
/* 3402 */         KeyStoreUtil.isWindowsKeyStore(paramKeyStore.getType()))
/*      */       {
/* 3404 */         throw unrecoverableEntryException;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3409 */       if (paramArrayOfchar2 != null) {
/*      */ 
/*      */ 
/*      */         
/* 3413 */         protectionParameter = new KeyStore.PasswordProtection(paramArrayOfchar2);
/* 3414 */         entry = paramKeyStore.getEntry(paramString, protectionParameter);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 3421 */           protectionParameter = new KeyStore.PasswordProtection(paramArrayOfchar1);
/* 3422 */           entry = paramKeyStore.getEntry(paramString, protectionParameter);
/* 3423 */           paramArrayOfchar2 = paramArrayOfchar1;
/* 3424 */         } catch (UnrecoverableEntryException unrecoverableEntryException1) {
/* 3425 */           if ("PKCS12".equalsIgnoreCase(paramKeyStore.getType()))
/*      */           {
/*      */ 
/*      */ 
/*      */             
/* 3430 */             throw unrecoverableEntryException1;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 3435 */           paramArrayOfchar2 = getKeyPasswd(paramString, null, null);
/* 3436 */           protectionParameter = new KeyStore.PasswordProtection(paramArrayOfchar2);
/* 3437 */           entry = paramKeyStore.getEntry(paramString, protectionParameter);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3443 */     return (Pair)Pair.of(entry, paramArrayOfchar2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCertFingerPrint(String paramString, Certificate paramCertificate) throws Exception {
/* 3451 */     byte[] arrayOfByte1 = paramCertificate.getEncoded();
/* 3452 */     MessageDigest messageDigest = MessageDigest.getInstance(paramString);
/* 3453 */     byte[] arrayOfByte2 = messageDigest.digest(arrayOfByte1);
/* 3454 */     return toHexString(arrayOfByte2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printNoIntegrityWarning() {
/* 3461 */     System.err.println();
/* 3462 */     System.err.println(rb
/* 3463 */         .getString(".WARNING.WARNING.WARNING."));
/* 3464 */     System.err.println(rb
/* 3465 */         .getString(".The.integrity.of.the.information.stored.in.your.keystore."));
/* 3466 */     System.err.println(rb
/* 3467 */         .getString(".WARNING.WARNING.WARNING."));
/* 3468 */     System.err.println();
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
/*      */   private Certificate[] validateReply(String paramString, Certificate paramCertificate, Certificate[] paramArrayOfCertificate) throws Exception {
/* 3486 */     checkWeak(rb.getString("reply"), paramArrayOfCertificate);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3492 */     PublicKey publicKey = paramCertificate.getPublicKey(); byte b;
/* 3493 */     for (b = 0; b < paramArrayOfCertificate.length && 
/* 3494 */       !publicKey.equals(paramArrayOfCertificate[b].getPublicKey()); b++);
/*      */ 
/*      */ 
/*      */     
/* 3498 */     if (b == paramArrayOfCertificate.length) {
/*      */       
/* 3500 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Certificate.reply.does.not.contain.public.key.for.alias."));
/* 3501 */       Object[] arrayOfObject = { paramString };
/* 3502 */       throw new Exception(messageFormat.format(arrayOfObject));
/*      */     } 
/*      */     
/* 3505 */     Certificate certificate1 = paramArrayOfCertificate[0];
/* 3506 */     paramArrayOfCertificate[0] = paramArrayOfCertificate[b];
/* 3507 */     paramArrayOfCertificate[b] = certificate1;
/*      */     
/* 3509 */     X509Certificate x509Certificate = (X509Certificate)paramArrayOfCertificate[0];
/*      */     
/* 3511 */     for (b = 1; b < paramArrayOfCertificate.length - 1; b++) {
/*      */       byte b1;
/*      */       
/* 3514 */       for (b1 = b; b1 < paramArrayOfCertificate.length; b1++) {
/* 3515 */         if (KeyStoreUtil.signedBy(x509Certificate, (X509Certificate)paramArrayOfCertificate[b1])) {
/* 3516 */           certificate1 = paramArrayOfCertificate[b];
/* 3517 */           paramArrayOfCertificate[b] = paramArrayOfCertificate[b1];
/* 3518 */           paramArrayOfCertificate[b1] = certificate1;
/* 3519 */           x509Certificate = (X509Certificate)paramArrayOfCertificate[b];
/*      */           break;
/*      */         } 
/*      */       } 
/* 3523 */       if (b1 == paramArrayOfCertificate.length) {
/* 3524 */         throw new Exception(rb
/* 3525 */             .getString("Incomplete.certificate.chain.in.reply"));
/*      */       }
/*      */     } 
/*      */     
/* 3529 */     if (this.noprompt) {
/* 3530 */       return paramArrayOfCertificate;
/*      */     }
/*      */ 
/*      */     
/* 3534 */     Certificate certificate2 = paramArrayOfCertificate[paramArrayOfCertificate.length - 1];
/* 3535 */     boolean bool = true;
/* 3536 */     Pair<String, Certificate> pair = getSigner(certificate2, this.keyStore);
/* 3537 */     if (pair == null && this.trustcacerts && this.caks != null) {
/* 3538 */       pair = getSigner(certificate2, this.caks);
/* 3539 */       bool = false;
/*      */     } 
/* 3541 */     if (pair == null) {
/* 3542 */       System.err.println();
/* 3543 */       System.err
/* 3544 */         .println(rb.getString("Top.level.certificate.in.reply."));
/* 3545 */       printX509Cert((X509Certificate)certificate2, System.out);
/* 3546 */       System.err.println();
/* 3547 */       System.err.print(rb.getString(".is.not.trusted."));
/* 3548 */       printWeakWarnings(true);
/*      */       
/* 3550 */       String str = getYesNoReply(rb.getString("Install.reply.anyway.no."));
/* 3551 */       if ("NO".equals(str)) {
/* 3552 */         return null;
/*      */       }
/*      */     }
/* 3555 */     else if (pair.snd != certificate2) {
/*      */       
/* 3557 */       Certificate[] arrayOfCertificate = new Certificate[paramArrayOfCertificate.length + 1];
/*      */       
/* 3559 */       System.arraycopy(paramArrayOfCertificate, 0, arrayOfCertificate, 0, paramArrayOfCertificate.length);
/*      */       
/* 3561 */       arrayOfCertificate[arrayOfCertificate.length - 1] = (Certificate)pair.snd;
/* 3562 */       paramArrayOfCertificate = arrayOfCertificate;
/* 3563 */       checkWeak(String.format(rb.getString(bool ? "alias.in.keystore" : "alias.in.cacerts"), new Object[] { pair.fst }), (Certificate)pair.snd);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3570 */     return paramArrayOfCertificate;
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
/*      */   private Certificate[] establishCertChain(Certificate paramCertificate1, Certificate paramCertificate2) throws Exception {
/* 3589 */     if (paramCertificate1 != null) {
/*      */ 
/*      */       
/* 3592 */       PublicKey publicKey1 = paramCertificate1.getPublicKey();
/* 3593 */       PublicKey publicKey2 = paramCertificate2.getPublicKey();
/* 3594 */       if (!publicKey1.equals(publicKey2)) {
/* 3595 */         throw new Exception(rb
/* 3596 */             .getString("Public.keys.in.reply.and.keystore.don.t.match"));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3601 */       if (paramCertificate2.equals(paramCertificate1)) {
/* 3602 */         throw new Exception(rb
/* 3603 */             .getString("Certificate.reply.and.certificate.in.keystore.are.identical"));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3611 */     Hashtable<Object, Object> hashtable = null;
/* 3612 */     if (this.keyStore.size() > 0) {
/* 3613 */       hashtable = new Hashtable<>(11);
/* 3614 */       keystorecerts2Hashtable(this.keyStore, (Hashtable)hashtable);
/*      */     } 
/* 3616 */     if (this.trustcacerts && 
/* 3617 */       this.caks != null && this.caks.size() > 0) {
/* 3618 */       if (hashtable == null) {
/* 3619 */         hashtable = new Hashtable<>(11);
/*      */       }
/* 3621 */       keystorecerts2Hashtable(this.caks, (Hashtable)hashtable);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3626 */     Vector<Pair<String, X509Certificate>> vector = new Vector(2);
/* 3627 */     if (buildChain(new Pair<>(rb
/* 3628 */           .getString("the.input"), (X509Certificate)paramCertificate2), vector, (Hashtable)hashtable)) {
/*      */ 
/*      */       
/* 3631 */       for (Pair<String, X509Certificate> pair : vector) {
/* 3632 */         checkWeak((String)pair.fst, (Certificate)pair.snd);
/*      */       }
/*      */       
/* 3635 */       Certificate[] arrayOfCertificate = new Certificate[vector.size()];
/*      */ 
/*      */ 
/*      */       
/* 3639 */       byte b = 0;
/* 3640 */       for (int i = vector.size() - 1; i >= 0; i--) {
/* 3641 */         arrayOfCertificate[b] = (Certificate)((Pair)vector.elementAt(i)).snd;
/* 3642 */         b++;
/*      */       } 
/* 3644 */       return arrayOfCertificate;
/*      */     } 
/* 3646 */     throw new Exception(rb
/* 3647 */         .getString("Failed.to.establish.chain.from.reply"));
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
/*      */   private boolean buildChain(Pair<String, X509Certificate> paramPair, Vector<Pair<String, X509Certificate>> paramVector, Hashtable<Principal, Vector<Pair<String, X509Certificate>>> paramHashtable) {
/* 3673 */     if (KeyStoreUtil.isSelfSigned((X509Certificate)paramPair.snd)) {
/*      */ 
/*      */       
/* 3676 */       paramVector.addElement(paramPair);
/* 3677 */       return true;
/*      */     } 
/*      */     
/* 3680 */     Principal principal = ((X509Certificate)paramPair.snd).getIssuerDN();
/*      */ 
/*      */     
/* 3683 */     Vector vector = paramHashtable.get(principal);
/* 3684 */     if (vector == null) {
/* 3685 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3691 */     Enumeration<Pair> enumeration = vector.elements();
/* 3692 */     while (enumeration.hasMoreElements()) {
/* 3693 */       Pair<String, X509Certificate> pair = enumeration.nextElement();
/* 3694 */       PublicKey publicKey = ((X509Certificate)pair.snd).getPublicKey();
/*      */       try {
/* 3696 */         ((X509Certificate)paramPair.snd).verify(publicKey);
/* 3697 */       } catch (Exception exception) {
/*      */         continue;
/*      */       } 
/* 3700 */       if (buildChain(pair, paramVector, paramHashtable)) {
/* 3701 */         paramVector.addElement(paramPair);
/* 3702 */         return true;
/*      */       } 
/*      */     } 
/* 3705 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getYesNoReply(String paramString) throws IOException {
/* 3716 */     String str = null;
/* 3717 */     byte b = 20;
/*      */     while (true) {
/* 3719 */       if (b-- < 0) {
/* 3720 */         throw new RuntimeException(rb.getString("Too.many.retries.program.terminated"));
/*      */       }
/*      */       
/* 3723 */       System.err.print(paramString);
/* 3724 */       System.err.flush();
/*      */       
/* 3726 */       str = (new BufferedReader(new InputStreamReader(System.in))).readLine();
/* 3727 */       if (collator.compare(str, "") == 0 || collator
/* 3728 */         .compare(str, rb.getString("n")) == 0 || collator
/* 3729 */         .compare(str, rb.getString("no")) == 0) {
/* 3730 */         str = "NO";
/* 3731 */       } else if (collator.compare(str, rb.getString("y")) == 0 || collator
/* 3732 */         .compare(str, rb.getString("yes")) == 0) {
/* 3733 */         str = "YES";
/*      */       } else {
/* 3735 */         System.err.println(rb.getString("Wrong.answer.try.again"));
/* 3736 */         str = null;
/*      */       } 
/* 3738 */       if (str != null) {
/* 3739 */         return str;
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
/*      */   private void keystorecerts2Hashtable(KeyStore paramKeyStore, Hashtable<Principal, Vector<Pair<String, X509Certificate>>> paramHashtable) throws Exception {
/* 3752 */     Enumeration<String> enumeration = paramKeyStore.aliases();
/* 3753 */     while (enumeration.hasMoreElements()) {
/* 3754 */       String str = enumeration.nextElement();
/* 3755 */       Certificate certificate = paramKeyStore.getCertificate(str);
/* 3756 */       if (certificate != null) {
/* 3757 */         Principal principal = ((X509Certificate)certificate).getSubjectDN();
/*      */         
/* 3759 */         Pair<String, X509Certificate> pair = new Pair<>(String.format(rb
/* 3760 */               .getString((paramKeyStore == this.caks) ? "alias.in.cacerts" : "alias.in.keystore"), new Object[] { str }), (X509Certificate)certificate);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3765 */         Vector<Pair<String, X509Certificate>> vector = paramHashtable.get(principal);
/* 3766 */         if (vector == null) {
/* 3767 */           vector = new Vector();
/* 3768 */           vector.addElement(pair);
/*      */         }
/* 3770 */         else if (!vector.contains(pair)) {
/* 3771 */           vector.addElement(pair);
/*      */         } 
/*      */         
/* 3774 */         paramHashtable.put(principal, vector);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date getStartDate(String paramString) throws IOException {
/* 3784 */     GregorianCalendar gregorianCalendar = new GregorianCalendar();
/* 3785 */     if (paramString != null) {
/*      */       
/* 3787 */       IOException iOException = new IOException(rb.getString("Illegal.startdate.value"));
/* 3788 */       int i = paramString.length();
/* 3789 */       if (i == 0) {
/* 3790 */         throw iOException;
/*      */       }
/* 3792 */       if (paramString.charAt(0) == '-' || paramString.charAt(0) == '+') {
/*      */         
/* 3794 */         int j = 0;
/* 3795 */         while (j < i) {
/* 3796 */           byte b = 0;
/* 3797 */           switch (paramString.charAt(j)) { case '+':
/* 3798 */               b = 1; break;
/* 3799 */             case '-': b = -1; break;
/* 3800 */             default: throw iOException; }
/*      */           
/* 3802 */           int k = j + 1;
/* 3803 */           for (; k < i; k++) {
/* 3804 */             char c = paramString.charAt(k);
/* 3805 */             if (c < '0' || c > '9')
/*      */               break; 
/* 3807 */           }  if (k == j + 1) throw iOException; 
/* 3808 */           int m = Integer.parseInt(paramString.substring(j + 1, k));
/* 3809 */           if (k >= i) throw iOException; 
/* 3810 */           byte b1 = 0;
/* 3811 */           switch (paramString.charAt(k)) { case 'y':
/* 3812 */               b1 = 1; break;
/* 3813 */             case 'm': b1 = 2; break;
/* 3814 */             case 'd': b1 = 5; break;
/* 3815 */             case 'H': b1 = 10; break;
/* 3816 */             case 'M': b1 = 12; break;
/* 3817 */             case 'S': b1 = 13; break;
/* 3818 */             default: throw iOException; }
/*      */           
/* 3820 */           gregorianCalendar.add(b1, b * m);
/* 3821 */           j = k + 1;
/*      */         } 
/*      */       } else {
/*      */         
/* 3825 */         String str1 = null, str2 = null;
/* 3826 */         if (i == 19) {
/* 3827 */           str1 = paramString.substring(0, 10);
/* 3828 */           str2 = paramString.substring(11);
/* 3829 */           if (paramString.charAt(10) != ' ')
/* 3830 */             throw iOException; 
/* 3831 */         } else if (i == 10) {
/* 3832 */           str1 = paramString;
/* 3833 */         } else if (i == 8) {
/* 3834 */           str2 = paramString;
/*      */         } else {
/* 3836 */           throw iOException;
/*      */         } 
/* 3838 */         if (str1 != null) {
/* 3839 */           if (str1.matches("\\d\\d\\d\\d\\/\\d\\d\\/\\d\\d")) {
/* 3840 */             gregorianCalendar.set(Integer.valueOf(str1.substring(0, 4)).intValue(), 
/* 3841 */                 Integer.valueOf(str1.substring(5, 7)).intValue() - 1, 
/* 3842 */                 Integer.valueOf(str1.substring(8, 10)).intValue());
/*      */           } else {
/* 3844 */             throw iOException;
/*      */           } 
/*      */         }
/* 3847 */         if (str2 != null) {
/* 3848 */           if (str2.matches("\\d\\d:\\d\\d:\\d\\d")) {
/* 3849 */             gregorianCalendar.set(11, Integer.valueOf(str2.substring(0, 2)).intValue());
/* 3850 */             gregorianCalendar.set(12, Integer.valueOf(str2.substring(0, 2)).intValue());
/* 3851 */             gregorianCalendar.set(13, Integer.valueOf(str2.substring(0, 2)).intValue());
/* 3852 */             gregorianCalendar.set(14, 0);
/*      */           } else {
/* 3854 */             throw iOException;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 3859 */     return gregorianCalendar.getTime();
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
/*      */   private static int oneOf(String paramString, String... paramVarArgs) throws Exception {
/* 3872 */     int[] arrayOfInt = new int[paramVarArgs.length];
/* 3873 */     byte b1 = 0;
/* 3874 */     int i = Integer.MAX_VALUE;
/* 3875 */     for (byte b2 = 0; b2 < paramVarArgs.length; b2++) {
/* 3876 */       String str = paramVarArgs[b2];
/* 3877 */       if (str == null) {
/* 3878 */         i = b2;
/*      */       
/*      */       }
/* 3881 */       else if (str.toLowerCase(Locale.ENGLISH)
/* 3882 */         .startsWith(paramString.toLowerCase(Locale.ENGLISH))) {
/* 3883 */         arrayOfInt[b1++] = b2;
/*      */       } else {
/* 3885 */         StringBuffer stringBuffer1 = new StringBuffer();
/* 3886 */         boolean bool = true;
/* 3887 */         for (char c : str.toCharArray()) {
/* 3888 */           if (bool) {
/* 3889 */             stringBuffer1.append(c);
/* 3890 */             bool = false;
/*      */           }
/* 3892 */           else if (!Character.isLowerCase(c)) {
/* 3893 */             stringBuffer1.append(c);
/*      */           } 
/*      */         } 
/*      */         
/* 3897 */         if (stringBuffer1.toString().equalsIgnoreCase(paramString)) {
/* 3898 */           arrayOfInt[b1++] = b2;
/*      */         }
/*      */       } 
/*      */     } 
/* 3902 */     if (b1 == 0)
/* 3903 */       return -1; 
/* 3904 */     if (b1 == 1) {
/* 3905 */       return arrayOfInt[0];
/*      */     }
/*      */     
/* 3908 */     if (arrayOfInt[1] > i) {
/* 3909 */       return arrayOfInt[0];
/*      */     }
/* 3911 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 3913 */     MessageFormat messageFormat = new MessageFormat(rb.getString("command.{0}.is.ambiguous."));
/* 3914 */     Object[] arrayOfObject = { paramString };
/* 3915 */     stringBuffer.append(messageFormat.format(arrayOfObject));
/* 3916 */     stringBuffer.append("\n    ");
/* 3917 */     for (byte b3 = 0; b3 < b1 && arrayOfInt[b3] < i; b3++) {
/* 3918 */       stringBuffer.append(' ');
/* 3919 */       stringBuffer.append(paramVarArgs[arrayOfInt[b3]]);
/*      */     } 
/* 3921 */     throw new Exception(stringBuffer.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GeneralName createGeneralName(String paramString1, String paramString2) throws Exception {
/*      */     RFC822Name rFC822Name;
/*      */     URIName uRIName;
/*      */     DNSName dNSName;
/*      */     IPAddressName iPAddressName;
/* 3934 */     int i = oneOf(paramString1, new String[] { "EMAIL", "URI", "DNS", "IP", "OID" });
/* 3935 */     if (i < 0) {
/* 3936 */       throw new Exception(rb.getString("Unrecognized.GeneralName.type.") + paramString1);
/*      */     }
/*      */     
/* 3939 */     switch (i) { case 0:
/* 3940 */         rFC822Name = new RFC822Name(paramString2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3946 */         return new GeneralName(rFC822Name);case 1: uRIName = new URIName(paramString2); return new GeneralName(uRIName);case 2: dNSName = new DNSName(paramString2); return new GeneralName(dNSName);case 3: iPAddressName = new IPAddressName(paramString2); return new GeneralName(iPAddressName); }  OIDName oIDName = new OIDName(paramString2); return new GeneralName(oIDName);
/*      */   }
/*      */   
/* 3949 */   private static final String[] extSupported = new String[] { "BasicConstraints", "KeyUsage", "ExtendedKeyUsage", "SubjectAlternativeName", "IssuerAlternativeName", "SubjectInfoAccess", "AuthorityInfoAccess", null, "CRLDistributionPoints" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectIdentifier findOidForExtName(String paramString) throws Exception {
/* 3963 */     switch (oneOf(paramString, extSupported)) { case 0:
/* 3964 */         return PKIXExtensions.BasicConstraints_Id;
/* 3965 */       case 1: return PKIXExtensions.KeyUsage_Id;
/* 3966 */       case 2: return PKIXExtensions.ExtendedKeyUsage_Id;
/* 3967 */       case 3: return PKIXExtensions.SubjectAlternativeName_Id;
/* 3968 */       case 4: return PKIXExtensions.IssuerAlternativeName_Id;
/* 3969 */       case 5: return PKIXExtensions.SubjectInfoAccess_Id;
/* 3970 */       case 6: return PKIXExtensions.AuthInfoAccess_Id;
/* 3971 */       case 8: return PKIXExtensions.CRLDistributionPoints_Id; }
/* 3972 */      return new ObjectIdentifier(paramString);
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
/*      */   private CertificateExtensions createV3Extensions(CertificateExtensions paramCertificateExtensions1, CertificateExtensions paramCertificateExtensions2, List<String> paramList, PublicKey paramPublicKey1, PublicKey paramPublicKey2) throws Exception {
/* 3995 */     if (paramCertificateExtensions2 != null && paramCertificateExtensions1 != null)
/*      */     {
/* 3997 */       throw new Exception("One of request and original should be null.");
/*      */     }
/* 3999 */     if (paramCertificateExtensions2 == null) paramCertificateExtensions2 = new CertificateExtensions();
/*      */ 
/*      */     
/*      */     try {
/* 4003 */       if (paramCertificateExtensions1 != null) {
/* 4004 */         for (String str : paramList) {
/* 4005 */           if (str.toLowerCase(Locale.ENGLISH).startsWith("honored=")) {
/* 4006 */             List<String> list = Arrays.asList(str
/* 4007 */                 .toLowerCase(Locale.ENGLISH).substring(8).split(","));
/*      */             
/* 4009 */             if (list.contains("all")) {
/* 4010 */               paramCertificateExtensions2 = paramCertificateExtensions1;
/*      */             }
/*      */             
/* 4013 */             for (String str1 : list) {
/* 4014 */               if (str1.equals("all")) {
/*      */                 continue;
/*      */               }
/* 4017 */               boolean bool = true;
/*      */               
/* 4019 */               int i = -1;
/* 4020 */               String str2 = null;
/* 4021 */               if (str1.startsWith("-")) {
/* 4022 */                 bool = false;
/* 4023 */                 str2 = str1.substring(1);
/*      */               } else {
/* 4025 */                 int j = str1.indexOf(':');
/* 4026 */                 if (j >= 0) {
/* 4027 */                   str2 = str1.substring(0, j);
/* 4028 */                   i = oneOf(str1.substring(j + 1), new String[] { "critical", "non-critical" });
/*      */                   
/* 4030 */                   if (i == -1) {
/* 4031 */                     throw new Exception(rb
/* 4032 */                         .getString("Illegal.value.") + str1);
/*      */                   }
/*      */                 } 
/*      */               } 
/* 4036 */               String str3 = paramCertificateExtensions1.getNameByOid(findOidForExtName(str2));
/* 4037 */               if (bool) {
/* 4038 */                 Extension extension = paramCertificateExtensions1.get(str3);
/* 4039 */                 if ((!extension.isCritical() && i == 0) || (extension
/* 4040 */                   .isCritical() && i == 1)) {
/* 4041 */                   extension = Extension.newExtension(extension
/* 4042 */                       .getExtensionId(), 
/* 4043 */                       !extension.isCritical(), extension
/* 4044 */                       .getExtensionValue());
/* 4045 */                   paramCertificateExtensions2.set(str3, extension);
/*      */                 }  continue;
/*      */               } 
/* 4048 */               paramCertificateExtensions2.delete(str3);
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 4055 */       for (String str1 : paramList) {
/*      */         String str2, str3; int m; boolean bool1; ObjectIdentifier objectIdentifier; byte[] arrayOfByte;
/* 4057 */         boolean bool = false;
/*      */         
/* 4059 */         int i = str1.indexOf('=');
/* 4060 */         if (i >= 0) {
/* 4061 */           str2 = str1.substring(0, i);
/* 4062 */           str3 = str1.substring(i + 1);
/*      */         } else {
/* 4064 */           str2 = str1;
/* 4065 */           str3 = null;
/*      */         } 
/*      */         
/* 4068 */         int j = str2.indexOf(':');
/* 4069 */         if (j >= 0) {
/* 4070 */           if (oneOf(str2.substring(j + 1), new String[] { "critical" }) == 0) {
/* 4071 */             bool = true;
/*      */           }
/* 4073 */           str2 = str2.substring(0, j);
/*      */         } 
/*      */         
/* 4076 */         if (str2.equalsIgnoreCase("honored")) {
/*      */           continue;
/*      */         }
/* 4079 */         int k = oneOf(str2, extSupported);
/* 4080 */         switch (k) {
/*      */           case 0:
/* 4082 */             m = -1;
/* 4083 */             bool1 = false;
/* 4084 */             if (str3 == null) {
/* 4085 */               bool1 = true;
/*      */             } else {
/*      */               try {
/* 4088 */                 m = Integer.parseInt(str3);
/* 4089 */                 bool1 = true;
/* 4090 */               } catch (NumberFormatException numberFormatException) {
/*      */                 
/* 4092 */                 for (String str : str3.split(",")) {
/* 4093 */                   String[] arrayOfString = str.split(":");
/* 4094 */                   if (arrayOfString.length != 2) {
/* 4095 */                     throw new Exception(rb
/* 4096 */                         .getString("Illegal.value.") + str1);
/*      */                   }
/* 4098 */                   if (arrayOfString[0].equalsIgnoreCase("ca")) {
/* 4099 */                     bool1 = Boolean.parseBoolean(arrayOfString[1]);
/* 4100 */                   } else if (arrayOfString[0].equalsIgnoreCase("pathlen")) {
/* 4101 */                     m = Integer.parseInt(arrayOfString[1]);
/*      */                   } else {
/* 4103 */                     throw new Exception(rb
/* 4104 */                         .getString("Illegal.value.") + str1);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/* 4110 */             paramCertificateExtensions2.set("BasicConstraints", new BasicConstraintsExtension(
/* 4111 */                   Boolean.valueOf(bool), bool1, m));
/*      */             continue;
/*      */           
/*      */           case 1:
/* 4115 */             if (str3 != null) {
/* 4116 */               boolean[] arrayOfBoolean = new boolean[9];
/* 4117 */               for (String str : str3.split(",")) {
/* 4118 */                 int n = oneOf(str, new String[] { "digitalSignature", "nonRepudiation", "keyEncipherment", "dataEncipherment", "keyAgreement", "keyCertSign", "cRLSign", "encipherOnly", "decipherOnly", "contentCommitment" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4130 */                 if (n < 0) {
/* 4131 */                   throw new Exception(rb.getString("Unknown.keyUsage.type.") + str);
/*      */                 }
/* 4133 */                 if (n == 9) n = 1; 
/* 4134 */                 arrayOfBoolean[n] = true;
/*      */               } 
/* 4136 */               KeyUsageExtension keyUsageExtension = new KeyUsageExtension(arrayOfBoolean);
/*      */ 
/*      */               
/* 4139 */               paramCertificateExtensions2.set("KeyUsage", Extension.newExtension(keyUsageExtension
/* 4140 */                     .getExtensionId(), bool, keyUsageExtension
/*      */                     
/* 4142 */                     .getExtensionValue())); continue;
/*      */             } 
/* 4144 */             throw new Exception(rb
/* 4145 */                 .getString("Illegal.value.") + str1);
/*      */ 
/*      */           
/*      */           case 2:
/* 4149 */             if (str3 != null) {
/* 4150 */               Vector<ObjectIdentifier> vector = new Vector();
/* 4151 */               for (String str : str3.split(",")) {
/* 4152 */                 int n = oneOf(str, new String[] { "anyExtendedKeyUsage", "serverAuth", "clientAuth", "codeSigning", "emailProtection", "", "", "", "timeStamping", "OCSPSigning" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4164 */                 if (n < 0) {
/*      */                   try {
/* 4166 */                     vector.add(new ObjectIdentifier(str));
/* 4167 */                   } catch (Exception exception) {
/* 4168 */                     throw new Exception(rb.getString("Unknown.extendedkeyUsage.type.") + str);
/*      */                   }
/*      */                 
/* 4171 */                 } else if (n == 0) {
/* 4172 */                   vector.add(new ObjectIdentifier("2.5.29.37.0"));
/*      */                 } else {
/* 4174 */                   vector.add(new ObjectIdentifier("1.3.6.1.5.5.7.3." + n));
/*      */                 } 
/*      */               } 
/* 4177 */               paramCertificateExtensions2.set("ExtendedKeyUsage", new ExtendedKeyUsageExtension(
/* 4178 */                     Boolean.valueOf(bool), vector)); continue;
/*      */             } 
/* 4180 */             throw new Exception(rb
/* 4181 */                 .getString("Illegal.value.") + str1);
/*      */ 
/*      */           
/*      */           case 3:
/*      */           case 4:
/* 4186 */             if (str3 != null) {
/* 4187 */               String[] arrayOfString = str3.split(",");
/* 4188 */               GeneralNames generalNames = new GeneralNames();
/* 4189 */               for (String str4 : arrayOfString) {
/* 4190 */                 j = str4.indexOf(':');
/* 4191 */                 if (j < 0) {
/* 4192 */                   throw new Exception("Illegal item " + str4 + " in " + str1);
/*      */                 }
/* 4194 */                 String str5 = str4.substring(0, j);
/* 4195 */                 String str6 = str4.substring(j + 1);
/* 4196 */                 generalNames.add(createGeneralName(str5, str6));
/*      */               } 
/* 4198 */               if (k == 3) {
/* 4199 */                 paramCertificateExtensions2.set("SubjectAlternativeName", new SubjectAlternativeNameExtension(
/*      */                       
/* 4201 */                       Boolean.valueOf(bool), generalNames)); continue;
/*      */               } 
/* 4203 */               paramCertificateExtensions2.set("IssuerAlternativeName", new IssuerAlternativeNameExtension(
/*      */                     
/* 4205 */                     Boolean.valueOf(bool), generalNames));
/*      */               continue;
/*      */             } 
/* 4208 */             throw new Exception(rb
/* 4209 */                 .getString("Illegal.value.") + str1);
/*      */ 
/*      */           
/*      */           case 5:
/*      */           case 6:
/* 4214 */             if (bool) {
/* 4215 */               throw new Exception(rb.getString("This.extension.cannot.be.marked.as.critical.") + str1);
/*      */             }
/*      */             
/* 4218 */             if (str3 != null) {
/* 4219 */               ArrayList<AccessDescription> arrayList = new ArrayList();
/*      */               
/* 4221 */               String[] arrayOfString = str3.split(",");
/* 4222 */               for (String str4 : arrayOfString) {
/* 4223 */                 ObjectIdentifier objectIdentifier1; j = str4.indexOf(':');
/* 4224 */                 int n = str4.indexOf(':', j + 1);
/* 4225 */                 if (j < 0 || n < 0) {
/* 4226 */                   throw new Exception(rb
/* 4227 */                       .getString("Illegal.value.") + str1);
/*      */                 }
/* 4229 */                 String str5 = str4.substring(0, j);
/* 4230 */                 String str6 = str4.substring(j + 1, n);
/* 4231 */                 String str7 = str4.substring(n + 1);
/* 4232 */                 int i1 = oneOf(str5, new String[] { "", "ocsp", "caIssuers", "timeStamping", "", "caRepository" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4241 */                 if (i1 < 0) {
/*      */                   try {
/* 4243 */                     objectIdentifier1 = new ObjectIdentifier(str5);
/* 4244 */                   } catch (Exception exception) {
/* 4245 */                     throw new Exception(rb.getString("Unknown.AccessDescription.type.") + str5);
/*      */                   } 
/*      */                 } else {
/*      */                   
/* 4249 */                   objectIdentifier1 = new ObjectIdentifier("1.3.6.1.5.5.7.48." + i1);
/*      */                 } 
/* 4251 */                 arrayList.add(new AccessDescription(objectIdentifier1, 
/* 4252 */                       createGeneralName(str6, str7)));
/*      */               } 
/* 4254 */               if (k == 5) {
/* 4255 */                 paramCertificateExtensions2.set("SubjectInfoAccess", new SubjectInfoAccessExtension(arrayList));
/*      */                 continue;
/*      */               } 
/* 4258 */               paramCertificateExtensions2.set("AuthorityInfoAccess", new AuthorityInfoAccessExtension(arrayList));
/*      */               
/*      */               continue;
/*      */             } 
/* 4262 */             throw new Exception(rb
/* 4263 */                 .getString("Illegal.value.") + str1);
/*      */ 
/*      */           
/*      */           case 8:
/* 4267 */             if (str3 != null) {
/* 4268 */               String[] arrayOfString = str3.split(",");
/* 4269 */               GeneralNames generalNames = new GeneralNames();
/* 4270 */               for (String str4 : arrayOfString) {
/* 4271 */                 j = str4.indexOf(':');
/* 4272 */                 if (j < 0) {
/* 4273 */                   throw new Exception("Illegal item " + str4 + " in " + str1);
/*      */                 }
/* 4275 */                 String str5 = str4.substring(0, j);
/* 4276 */                 String str6 = str4.substring(j + 1);
/* 4277 */                 generalNames.add(createGeneralName(str5, str6));
/*      */               } 
/* 4279 */               paramCertificateExtensions2.set("CRLDistributionPoints", new CRLDistributionPointsExtension(bool, 
/*      */                     
/* 4281 */                     Collections.singletonList(new DistributionPoint(generalNames, null, null))));
/*      */               continue;
/*      */             } 
/* 4284 */             throw new Exception(rb
/* 4285 */                 .getString("Illegal.value.") + str1);
/*      */ 
/*      */           
/*      */           case -1:
/* 4289 */             objectIdentifier = new ObjectIdentifier(str2);
/* 4290 */             arrayOfByte = null;
/* 4291 */             if (str3 != null) {
/* 4292 */               arrayOfByte = new byte[str3.length() / 2 + 1];
/* 4293 */               byte b = 0;
/* 4294 */               for (char c : str3.toCharArray()) {
/*      */                 int n;
/* 4296 */                 if (c >= '0' && c <= '9') {
/* 4297 */                   n = c - 48;
/* 4298 */                 } else if (c >= 'A' && c <= 'F') {
/* 4299 */                   n = c - 65 + 10;
/* 4300 */                 } else if (c >= 'a' && c <= 'f') {
/* 4301 */                   n = c - 97 + 10;
/*      */                 } else {
/*      */                   continue;
/*      */                 } 
/* 4305 */                 if (b % 2 == 0) {
/* 4306 */                   arrayOfByte[b / 2] = (byte)(n << 4);
/*      */                 } else {
/* 4308 */                   arrayOfByte[b / 2] = (byte)(arrayOfByte[b / 2] + n);
/*      */                 } 
/* 4310 */                 b++; continue;
/*      */               } 
/* 4312 */               if (b % 2 != 0) {
/* 4313 */                 throw new Exception(rb.getString("Odd.number.of.hex.digits.found.") + str1);
/*      */               }
/*      */               
/* 4316 */               arrayOfByte = Arrays.copyOf(arrayOfByte, b / 2);
/*      */             } else {
/* 4318 */               arrayOfByte = new byte[0];
/*      */             } 
/* 4320 */             paramCertificateExtensions2.set(objectIdentifier.toString(), new Extension(objectIdentifier, bool, (new DerValue((byte)4, arrayOfByte))
/*      */                   
/* 4322 */                   .toByteArray()));
/*      */             continue;
/*      */         } 
/* 4325 */         throw new Exception(rb.getString("Unknown.extension.type.") + str1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 4330 */       paramCertificateExtensions2.set("SubjectKeyIdentifier", new SubjectKeyIdentifierExtension((new KeyIdentifier(paramPublicKey1))
/*      */             
/* 4332 */             .getIdentifier()));
/* 4333 */       if (paramPublicKey2 != null && !paramPublicKey1.equals(paramPublicKey2)) {
/* 4334 */         paramCertificateExtensions2.set("AuthorityKeyIdentifier", new AuthorityKeyIdentifierExtension(new KeyIdentifier(paramPublicKey2), null, null));
/*      */       
/*      */       }
/*      */     }
/* 4338 */     catch (IOException iOException) {
/* 4339 */       throw new RuntimeException(iOException);
/*      */     } 
/* 4341 */     return paramCertificateExtensions2;
/*      */   }
/*      */   
/*      */   private boolean isTrustedCert(Certificate paramCertificate) throws KeyStoreException {
/* 4345 */     if (this.caks != null && this.caks.getCertificateAlias(paramCertificate) != null) {
/* 4346 */       return true;
/*      */     }
/* 4348 */     String str = this.keyStore.getCertificateAlias(paramCertificate);
/* 4349 */     return (str != null && this.keyStore.isCertificateEntry(str));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkWeak(String paramString1, String paramString2, Key paramKey) {
/* 4355 */     if (paramString2 != null && !DISABLED_CHECK.permits(SIG_PRIMITIVE_SET, paramString2, (AlgorithmParameters)null))
/*      */     {
/* 4357 */       this.weakWarnings.add(String.format(rb
/* 4358 */             .getString("whose.sigalg.risk"), new Object[] { paramString1, paramString2 }));
/*      */     }
/* 4360 */     if (paramKey != null && !DISABLED_CHECK.permits(SIG_PRIMITIVE_SET, paramKey)) {
/* 4361 */       this.weakWarnings.add(String.format(rb
/* 4362 */             .getString("whose.key.risk"), new Object[] { paramString1, 
/*      */               
/* 4364 */               String.format(rb.getString("key.bit"), new Object[] {
/* 4365 */                   Integer.valueOf(KeyUtil.getKeySize(paramKey)), paramKey.getAlgorithm()
/*      */                 }) }));
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkWeak(String paramString, Certificate[] paramArrayOfCertificate) throws KeyStoreException {
/* 4371 */     for (byte b = 0; b < paramArrayOfCertificate.length; b++) {
/* 4372 */       Certificate certificate = paramArrayOfCertificate[b];
/* 4373 */       if (certificate instanceof X509Certificate) {
/* 4374 */         X509Certificate x509Certificate = (X509Certificate)certificate;
/* 4375 */         String str = paramString;
/* 4376 */         if (paramArrayOfCertificate.length > 1) {
/* 4377 */           str = oneInMany(paramString, b, paramArrayOfCertificate.length);
/*      */         }
/* 4379 */         checkWeak(str, x509Certificate);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkWeak(String paramString, Certificate paramCertificate) throws KeyStoreException {
/* 4386 */     if (paramCertificate instanceof X509Certificate) {
/* 4387 */       X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*      */       
/* 4389 */       String str = isTrustedCert(paramCertificate) ? null : x509Certificate.getSigAlgName();
/* 4390 */       checkWeak(paramString, str, x509Certificate.getPublicKey());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkWeak(String paramString, PKCS10 paramPKCS10) {
/* 4395 */     checkWeak(paramString, paramPKCS10.getSigAlg(), paramPKCS10.getSubjectPublicKeyInfo());
/*      */   }
/*      */   
/*      */   private void checkWeak(String paramString, CRL paramCRL, Key paramKey) {
/* 4399 */     if (paramCRL instanceof X509CRLImpl) {
/* 4400 */       X509CRLImpl x509CRLImpl = (X509CRLImpl)paramCRL;
/* 4401 */       checkWeak(paramString, x509CRLImpl.getSigAlgName(), paramKey);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void printWeakWarnings(boolean paramBoolean) {
/* 4406 */     if (!this.weakWarnings.isEmpty() && !this.nowarn) {
/* 4407 */       System.err.println("\nWarning:");
/* 4408 */       for (String str : this.weakWarnings) {
/* 4409 */         System.err.println(str);
/*      */       }
/* 4411 */       if (paramBoolean)
/*      */       {
/* 4413 */         System.err.println();
/*      */       }
/*      */     } 
/* 4416 */     this.weakWarnings.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void usage() {
/* 4423 */     if (this.command != null) {
/* 4424 */       System.err.println("keytool " + this.command + rb
/* 4425 */           .getString(".OPTION."));
/* 4426 */       System.err.println();
/* 4427 */       System.err.println(rb.getString(this.command.description));
/* 4428 */       System.err.println();
/* 4429 */       System.err.println(rb.getString("Options."));
/* 4430 */       System.err.println();
/*      */ 
/*      */       
/* 4433 */       String[] arrayOfString1 = new String[this.command.options.length];
/* 4434 */       String[] arrayOfString2 = new String[this.command.options.length];
/*      */ 
/*      */       
/* 4437 */       boolean bool = false;
/*      */ 
/*      */       
/* 4440 */       int i = 0; byte b;
/* 4441 */       for (b = 0; b < arrayOfString1.length; b++) {
/* 4442 */         Option option = this.command.options[b];
/* 4443 */         arrayOfString1[b] = option.toString();
/* 4444 */         if (option.arg != null) arrayOfString1[b] = arrayOfString1[b] + " " + option.arg; 
/* 4445 */         if (arrayOfString1[b].length() > i) {
/* 4446 */           i = arrayOfString1[b].length();
/*      */         }
/* 4448 */         arrayOfString2[b] = rb.getString(option.description);
/*      */       } 
/* 4450 */       for (b = 0; b < arrayOfString1.length; b++) {
/* 4451 */         System.err.printf(" %-" + i + "s  %s\n", new Object[] { arrayOfString1[b], arrayOfString2[b] });
/*      */       } 
/*      */       
/* 4454 */       System.err.println();
/* 4455 */       System.err.println(rb.getString("Use.keytool.help.for.all.available.commands"));
/*      */     } else {
/*      */       
/* 4458 */       System.err.println(rb.getString("Key.and.Certificate.Management.Tool"));
/*      */       
/* 4460 */       System.err.println();
/* 4461 */       System.err.println(rb.getString("Commands."));
/* 4462 */       System.err.println();
/* 4463 */       for (Command command : Command.values()) {
/* 4464 */         if (command == Command.KEYCLONE)
/* 4465 */           break;  System.err.printf(" %-20s%s\n", new Object[] { command, rb.getString(command.description) });
/*      */       } 
/* 4467 */       System.err.println();
/* 4468 */       System.err.println(rb.getString("Use.keytool.command.name.help.for.usage.of.command.name"));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void tinyHelp() {
/* 4474 */     usage();
/* 4475 */     if (this.debug) {
/* 4476 */       throw new RuntimeException("NO BIG ERROR, SORRY");
/*      */     }
/* 4478 */     System.exit(1);
/*      */   }
/*      */ 
/*      */   
/*      */   private void errorNeedArgument(String paramString) {
/* 4483 */     Object[] arrayOfObject = { paramString };
/* 4484 */     System.err.println((new MessageFormat(rb
/* 4485 */           .getString("Command.option.flag.needs.an.argument."))).format(arrayOfObject));
/* 4486 */     tinyHelp();
/*      */   }
/*      */   
/*      */   private char[] getPass(String paramString1, String paramString2) {
/* 4490 */     char[] arrayOfChar = KeyStoreUtil.getPassWithModifier(paramString1, paramString2, rb);
/* 4491 */     if (arrayOfChar != null) return arrayOfChar; 
/* 4492 */     tinyHelp();
/* 4493 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/tools/keytool/Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */