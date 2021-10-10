/*      */ package java.security.cert;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.PublicKey;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ import sun.security.x509.CertificatePoliciesExtension;
/*      */ import sun.security.x509.CertificatePolicyId;
/*      */ import sun.security.x509.CertificatePolicySet;
/*      */ import sun.security.x509.DNSName;
/*      */ import sun.security.x509.EDIPartyName;
/*      */ import sun.security.x509.ExtendedKeyUsageExtension;
/*      */ import sun.security.x509.GeneralName;
/*      */ import sun.security.x509.GeneralNameInterface;
/*      */ import sun.security.x509.GeneralNames;
/*      */ import sun.security.x509.GeneralSubtree;
/*      */ import sun.security.x509.GeneralSubtrees;
/*      */ import sun.security.x509.IPAddressName;
/*      */ import sun.security.x509.NameConstraintsExtension;
/*      */ import sun.security.x509.OIDName;
/*      */ import sun.security.x509.OtherName;
/*      */ import sun.security.x509.PolicyInformation;
/*      */ import sun.security.x509.PrivateKeyUsageExtension;
/*      */ import sun.security.x509.RFC822Name;
/*      */ import sun.security.x509.SubjectAlternativeNameExtension;
/*      */ import sun.security.x509.URIName;
/*      */ import sun.security.x509.X400Address;
/*      */ import sun.security.x509.X500Name;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ import sun.security.x509.X509Key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class X509CertSelector
/*      */   implements CertSelector
/*      */ {
/*   88 */   private static final Debug debug = Debug.getInstance("certpath");
/*      */ 
/*      */   
/*   91 */   private static final ObjectIdentifier ANY_EXTENDED_KEY_USAGE = ObjectIdentifier.newInternal(new int[] { 2, 5, 29, 37, 0 }); private BigInteger serialNumber; private X500Principal issuer; private X500Principal subject; private byte[] subjectKeyID; private byte[] authorityKeyID;
/*      */   
/*      */   static {
/*   94 */     CertPathHelperImpl.initialize();
/*      */   }
/*      */ 
/*      */   
/*      */   private Date certificateValid;
/*      */   
/*      */   private Date privateKeyValid;
/*      */   
/*      */   private ObjectIdentifier subjectPublicKeyAlgID;
/*      */   
/*      */   private PublicKey subjectPublicKey;
/*      */   
/*      */   private byte[] subjectPublicKeyBytes;
/*      */   private boolean[] keyUsage;
/*      */   private Set<String> keyPurposeSet;
/*      */   private Set<ObjectIdentifier> keyPurposeOIDSet;
/*      */   private Set<List<?>> subjectAlternativeNames;
/*      */   private Set<GeneralNameInterface> subjectAlternativeGeneralNames;
/*      */   private CertificatePolicySet policy;
/*      */   private Set<String> policySet;
/*      */   private Set<List<?>> pathToNames;
/*      */   private Set<GeneralNameInterface> pathToGeneralNames;
/*      */   private NameConstraintsExtension nc;
/*      */   private byte[] ncBytes;
/*  118 */   private int basicConstraints = -1;
/*      */   
/*      */   private X509Certificate x509Cert;
/*      */   private boolean matchAllSubjectAltNames = true;
/*  122 */   private static final Boolean FALSE = Boolean.FALSE;
/*      */   
/*      */   private static final int PRIVATE_KEY_USAGE_ID = 0;
/*      */   private static final int SUBJECT_ALT_NAME_ID = 1;
/*      */   private static final int NAME_CONSTRAINTS_ID = 2;
/*      */   private static final int CERT_POLICIES_ID = 3;
/*      */   private static final int EXTENDED_KEY_USAGE_ID = 4;
/*      */   private static final int NUM_OF_EXTENSIONS = 5;
/*  130 */   private static final String[] EXTENSION_OIDS = new String[5]; static final int NAME_ANY = 0; static final int NAME_RFC822 = 1; static final int NAME_DNS = 2; static final int NAME_X400 = 3;
/*      */   
/*      */   static {
/*  133 */     EXTENSION_OIDS[0] = "2.5.29.16";
/*  134 */     EXTENSION_OIDS[1] = "2.5.29.17";
/*  135 */     EXTENSION_OIDS[2] = "2.5.29.30";
/*  136 */     EXTENSION_OIDS[3] = "2.5.29.32";
/*  137 */     EXTENSION_OIDS[4] = "2.5.29.37";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_DIRECTORY = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_EDI = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_URI = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_IP = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_OID = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCertificate(X509Certificate paramX509Certificate) {
/*  175 */     this.x509Cert = paramX509Certificate;
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
/*      */   public void setSerialNumber(BigInteger paramBigInteger) {
/*  189 */     this.serialNumber = paramBigInteger;
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
/*      */   public void setIssuer(X500Principal paramX500Principal) {
/*  203 */     this.issuer = paramX500Principal;
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
/*      */   public void setIssuer(String paramString) throws IOException {
/*  227 */     if (paramString == null) {
/*  228 */       this.issuer = null;
/*      */     } else {
/*  230 */       this.issuer = (new X500Name(paramString)).asX500Principal();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIssuer(byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/*  278 */       this.issuer = (paramArrayOfbyte == null) ? null : new X500Principal(paramArrayOfbyte);
/*  279 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  280 */       throw new IOException("Invalid name", illegalArgumentException);
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
/*      */   public void setSubject(X500Principal paramX500Principal) {
/*  295 */     this.subject = paramX500Principal;
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
/*      */   public void setSubject(String paramString) throws IOException {
/*  318 */     if (paramString == null) {
/*  319 */       this.subject = null;
/*      */     } else {
/*  321 */       this.subject = (new X500Name(paramString)).asX500Principal();
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
/*      */   public void setSubject(byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/*  342 */       this.subject = (paramArrayOfbyte == null) ? null : new X500Principal(paramArrayOfbyte);
/*  343 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  344 */       throw new IOException("Invalid name", illegalArgumentException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectKeyIdentifier(byte[] paramArrayOfbyte) {
/*  381 */     if (paramArrayOfbyte == null) {
/*  382 */       this.subjectKeyID = null;
/*      */     } else {
/*  384 */       this.subjectKeyID = (byte[])paramArrayOfbyte.clone();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthorityKeyIdentifier(byte[] paramArrayOfbyte) {
/*  442 */     if (paramArrayOfbyte == null) {
/*  443 */       this.authorityKeyID = null;
/*      */     } else {
/*  445 */       this.authorityKeyID = (byte[])paramArrayOfbyte.clone();
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
/*      */   public void setCertificateValid(Date paramDate) {
/*  462 */     if (paramDate == null) {
/*  463 */       this.certificateValid = null;
/*      */     } else {
/*  465 */       this.certificateValid = (Date)paramDate.clone();
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
/*      */   public void setPrivateKeyValid(Date paramDate) {
/*  483 */     if (paramDate == null) {
/*  484 */       this.privateKeyValid = null;
/*      */     } else {
/*  486 */       this.privateKeyValid = (Date)paramDate.clone();
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
/*      */   public void setSubjectPublicKeyAlgID(String paramString) throws IOException {
/*  506 */     if (paramString == null) {
/*  507 */       this.subjectPublicKeyAlgID = null;
/*      */     } else {
/*  509 */       this.subjectPublicKeyAlgID = new ObjectIdentifier(paramString);
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
/*      */   public void setSubjectPublicKey(PublicKey paramPublicKey) {
/*  522 */     if (paramPublicKey == null) {
/*  523 */       this.subjectPublicKey = null;
/*  524 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  526 */       this.subjectPublicKey = paramPublicKey;
/*  527 */       this.subjectPublicKeyBytes = paramPublicKey.getEncoded();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectPublicKey(byte[] paramArrayOfbyte) throws IOException {
/*  565 */     if (paramArrayOfbyte == null) {
/*  566 */       this.subjectPublicKey = null;
/*  567 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  569 */       this.subjectPublicKeyBytes = (byte[])paramArrayOfbyte.clone();
/*  570 */       this.subjectPublicKey = X509Key.parse(new DerValue(this.subjectPublicKeyBytes));
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
/*      */   public void setKeyUsage(boolean[] paramArrayOfboolean) {
/*  590 */     if (paramArrayOfboolean == null) {
/*  591 */       this.keyUsage = null;
/*      */     } else {
/*  593 */       this.keyUsage = (boolean[])paramArrayOfboolean.clone();
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
/*      */   public void setExtendedKeyUsage(Set<String> paramSet) throws IOException {
/*  617 */     if (paramSet == null || paramSet.isEmpty()) {
/*  618 */       this.keyPurposeSet = null;
/*  619 */       this.keyPurposeOIDSet = null;
/*      */     } else {
/*  621 */       this
/*  622 */         .keyPurposeSet = Collections.unmodifiableSet(new HashSet<>(paramSet));
/*  623 */       this.keyPurposeOIDSet = new HashSet<>();
/*  624 */       for (String str : this.keyPurposeSet) {
/*  625 */         this.keyPurposeOIDSet.add(new ObjectIdentifier(str));
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
/*      */   public void setMatchAllSubjectAltNames(boolean paramBoolean) {
/*  647 */     this.matchAllSubjectAltNames = paramBoolean;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectAlternativeNames(Collection<List<?>> paramCollection) throws IOException {
/*  699 */     if (paramCollection == null) {
/*  700 */       this.subjectAlternativeNames = null;
/*  701 */       this.subjectAlternativeGeneralNames = null;
/*      */     } else {
/*  703 */       if (paramCollection.isEmpty()) {
/*  704 */         this.subjectAlternativeNames = null;
/*  705 */         this.subjectAlternativeGeneralNames = null;
/*      */         return;
/*      */       } 
/*  708 */       Set<List<?>> set = cloneAndCheckNames(paramCollection);
/*      */       
/*  710 */       this.subjectAlternativeGeneralNames = parseNames(set);
/*  711 */       this.subjectAlternativeNames = set;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubjectAlternativeName(int paramInt, String paramString) throws IOException {
/*  755 */     addSubjectAlternativeNameInternal(paramInt, paramString);
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
/*      */   public void addSubjectAlternativeName(int paramInt, byte[] paramArrayOfbyte) throws IOException {
/*  800 */     addSubjectAlternativeNameInternal(paramInt, paramArrayOfbyte.clone());
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
/*      */   private void addSubjectAlternativeNameInternal(int paramInt, Object paramObject) throws IOException {
/*  816 */     GeneralNameInterface generalNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/*  817 */     if (this.subjectAlternativeNames == null) {
/*  818 */       this.subjectAlternativeNames = new HashSet<>();
/*      */     }
/*  820 */     if (this.subjectAlternativeGeneralNames == null) {
/*  821 */       this.subjectAlternativeGeneralNames = new HashSet<>();
/*      */     }
/*  823 */     ArrayList<Integer> arrayList = new ArrayList(2);
/*  824 */     arrayList.add(Integer.valueOf(paramInt));
/*  825 */     arrayList.add(paramObject);
/*  826 */     this.subjectAlternativeNames.add(arrayList);
/*  827 */     this.subjectAlternativeGeneralNames.add(generalNameInterface);
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
/*      */   private static Set<GeneralNameInterface> parseNames(Collection<List<?>> paramCollection) throws IOException {
/*  848 */     HashSet<GeneralNameInterface> hashSet = new HashSet();
/*  849 */     for (List<Object> list : paramCollection) {
/*  850 */       if (list.size() != 2) {
/*  851 */         throw new IOException("name list size not 2");
/*      */       }
/*  853 */       Integer integer = (Integer)list.get(0);
/*  854 */       if (!(integer instanceof Integer)) {
/*  855 */         throw new IOException("expected an Integer");
/*      */       }
/*  857 */       int i = ((Integer)integer).intValue();
/*  858 */       integer = (Integer)list.get(1);
/*  859 */       hashSet.add(makeGeneralNameInterface(i, integer));
/*      */     } 
/*      */     
/*  862 */     return hashSet;
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
/*      */   static boolean equalNames(Collection<?> paramCollection1, Collection<?> paramCollection2) {
/*  876 */     if (paramCollection1 == null || paramCollection2 == null) {
/*  877 */       return (paramCollection1 == paramCollection2);
/*      */     }
/*  879 */     return paramCollection1.equals(paramCollection2);
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
/*      */   static GeneralNameInterface makeGeneralNameInterface(int paramInt, Object paramObject) throws IOException {
/*      */     OIDName oIDName;
/*  900 */     if (debug != null) {
/*  901 */       debug.println("X509CertSelector.makeGeneralNameInterface(" + paramInt + ")...");
/*      */     }
/*      */ 
/*      */     
/*  905 */     if (paramObject instanceof String) {
/*  906 */       RFC822Name rFC822Name; DNSName dNSName; X500Name x500Name; URIName uRIName; IPAddressName iPAddressName; if (debug != null) {
/*  907 */         debug.println("X509CertSelector.makeGeneralNameInterface() name is String: " + paramObject);
/*      */       }
/*      */       
/*  910 */       switch (paramInt) {
/*      */         case 1:
/*  912 */           rFC822Name = new RFC822Name((String)paramObject);
/*      */           break;
/*      */         case 2:
/*  915 */           dNSName = new DNSName((String)paramObject);
/*      */           break;
/*      */         case 4:
/*  918 */           x500Name = new X500Name((String)paramObject);
/*      */           break;
/*      */         case 6:
/*  921 */           uRIName = new URIName((String)paramObject);
/*      */           break;
/*      */         case 7:
/*  924 */           iPAddressName = new IPAddressName((String)paramObject);
/*      */           break;
/*      */         case 8:
/*  927 */           oIDName = new OIDName((String)paramObject);
/*      */           break;
/*      */         default:
/*  930 */           throw new IOException("unable to parse String names of type " + paramInt);
/*      */       } 
/*      */       
/*  933 */       if (debug != null) {
/*  934 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + oIDName
/*  935 */             .toString());
/*      */       }
/*  937 */     } else if (paramObject instanceof byte[]) {
/*  938 */       OtherName otherName; RFC822Name rFC822Name; DNSName dNSName; X400Address x400Address; X500Name x500Name; EDIPartyName eDIPartyName; URIName uRIName; IPAddressName iPAddressName; DerValue derValue = new DerValue((byte[])paramObject);
/*  939 */       if (debug != null) {
/*  940 */         debug
/*  941 */           .println("X509CertSelector.makeGeneralNameInterface() is byte[]");
/*      */       }
/*      */       
/*  944 */       switch (paramInt) {
/*      */         case 0:
/*  946 */           otherName = new OtherName(derValue);
/*      */           break;
/*      */         case 1:
/*  949 */           rFC822Name = new RFC822Name(derValue);
/*      */           break;
/*      */         case 2:
/*  952 */           dNSName = new DNSName(derValue);
/*      */           break;
/*      */         case 3:
/*  955 */           x400Address = new X400Address(derValue);
/*      */           break;
/*      */         case 4:
/*  958 */           x500Name = new X500Name(derValue);
/*      */           break;
/*      */         case 5:
/*  961 */           eDIPartyName = new EDIPartyName(derValue);
/*      */           break;
/*      */         case 6:
/*  964 */           uRIName = new URIName(derValue);
/*      */           break;
/*      */         case 7:
/*  967 */           iPAddressName = new IPAddressName(derValue);
/*      */           break;
/*      */         case 8:
/*  970 */           oIDName = new OIDName(derValue);
/*      */           break;
/*      */         default:
/*  973 */           throw new IOException("unable to parse byte array names of type " + paramInt);
/*      */       } 
/*      */       
/*  976 */       if (debug != null) {
/*  977 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + oIDName
/*  978 */             .toString());
/*      */       }
/*      */     } else {
/*  981 */       if (debug != null) {
/*  982 */         debug.println("X509CertSelector.makeGeneralName() input name not String or byte array");
/*      */       }
/*      */       
/*  985 */       throw new IOException("name not String or byte array");
/*      */     } 
/*  987 */     return oIDName;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNameConstraints(byte[] paramArrayOfbyte) throws IOException {
/* 1040 */     if (paramArrayOfbyte == null) {
/* 1041 */       this.ncBytes = null;
/* 1042 */       this.nc = null;
/*      */     } else {
/* 1044 */       this.ncBytes = (byte[])paramArrayOfbyte.clone();
/* 1045 */       this.nc = new NameConstraintsExtension(FALSE, paramArrayOfbyte);
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
/*      */   public void setBasicConstraints(int paramInt) {
/* 1066 */     if (paramInt < -2) {
/* 1067 */       throw new IllegalArgumentException("basic constraints less than -2");
/*      */     }
/* 1069 */     this.basicConstraints = paramInt;
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
/*      */   public void setPolicy(Set<String> paramSet) throws IOException {
/* 1093 */     if (paramSet == null) {
/* 1094 */       this.policySet = null;
/* 1095 */       this.policy = null;
/*      */     }
/*      */     else {
/*      */       
/* 1099 */       Set<?> set = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */       
/* 1101 */       Iterator<?> iterator = set.iterator();
/* 1102 */       Vector<CertificatePolicyId> vector = new Vector();
/* 1103 */       while (iterator.hasNext()) {
/* 1104 */         Object object = iterator.next();
/* 1105 */         if (!(object instanceof String)) {
/* 1106 */           throw new IOException("non String in certPolicySet");
/*      */         }
/* 1108 */         vector.add(new CertificatePolicyId(new ObjectIdentifier((String)object)));
/*      */       } 
/*      */ 
/*      */       
/* 1112 */       this.policySet = (Set)set;
/* 1113 */       this.policy = new CertificatePolicySet(vector);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPathToNames(Collection<List<?>> paramCollection) throws IOException {
/* 1169 */     if (paramCollection == null || paramCollection.isEmpty()) {
/* 1170 */       this.pathToNames = null;
/* 1171 */       this.pathToGeneralNames = null;
/*      */     } else {
/* 1173 */       Set<List<?>> set = cloneAndCheckNames(paramCollection);
/* 1174 */       this.pathToGeneralNames = parseNames(set);
/*      */       
/* 1176 */       this.pathToNames = set;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setPathToNamesInternal(Set<GeneralNameInterface> paramSet) {
/* 1184 */     this.pathToNames = Collections.emptySet();
/* 1185 */     this.pathToGeneralNames = paramSet;
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
/*      */   public void addPathToName(int paramInt, String paramString) throws IOException {
/* 1222 */     addPathToNameInternal(paramInt, paramString);
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
/*      */   public void addPathToName(int paramInt, byte[] paramArrayOfbyte) throws IOException {
/* 1252 */     addPathToNameInternal(paramInt, paramArrayOfbyte.clone());
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
/*      */   private void addPathToNameInternal(int paramInt, Object paramObject) throws IOException {
/* 1268 */     GeneralNameInterface generalNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/* 1269 */     if (this.pathToGeneralNames == null) {
/* 1270 */       this.pathToNames = new HashSet<>();
/* 1271 */       this.pathToGeneralNames = new HashSet<>();
/*      */     } 
/* 1273 */     ArrayList<Integer> arrayList = new ArrayList(2);
/* 1274 */     arrayList.add(Integer.valueOf(paramInt));
/* 1275 */     arrayList.add(paramObject);
/* 1276 */     this.pathToNames.add(arrayList);
/* 1277 */     this.pathToGeneralNames.add(generalNameInterface);
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
/*      */   public X509Certificate getCertificate() {
/* 1290 */     return this.x509Cert;
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
/*      */   public BigInteger getSerialNumber() {
/* 1304 */     return this.serialNumber;
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
/*      */   public X500Principal getIssuer() {
/* 1318 */     return this.issuer;
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
/*      */   public String getIssuerAsString() {
/* 1340 */     return (this.issuer == null) ? null : this.issuer.getName();
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
/*      */   public byte[] getIssuerAsBytes() throws IOException {
/* 1363 */     return (this.issuer == null) ? null : this.issuer.getEncoded();
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
/*      */   public X500Principal getSubject() {
/* 1377 */     return this.subject;
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
/*      */   public String getSubjectAsString() {
/* 1399 */     return (this.subject == null) ? null : this.subject.getName();
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
/*      */   public byte[] getSubjectAsBytes() throws IOException {
/* 1422 */     return (this.subject == null) ? null : this.subject.getEncoded();
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
/*      */   public byte[] getSubjectKeyIdentifier() {
/* 1438 */     if (this.subjectKeyID == null) {
/* 1439 */       return null;
/*      */     }
/* 1441 */     return (byte[])this.subjectKeyID.clone();
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
/*      */   public byte[] getAuthorityKeyIdentifier() {
/* 1457 */     if (this.authorityKeyID == null) {
/* 1458 */       return null;
/*      */     }
/* 1460 */     return (byte[])this.authorityKeyID.clone();
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
/*      */   public Date getCertificateValid() {
/* 1476 */     if (this.certificateValid == null) {
/* 1477 */       return null;
/*      */     }
/* 1479 */     return (Date)this.certificateValid.clone();
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
/*      */   public Date getPrivateKeyValid() {
/* 1495 */     if (this.privateKeyValid == null) {
/* 1496 */       return null;
/*      */     }
/* 1498 */     return (Date)this.privateKeyValid.clone();
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
/*      */   public String getSubjectPublicKeyAlgID() {
/* 1513 */     if (this.subjectPublicKeyAlgID == null) {
/* 1514 */       return null;
/*      */     }
/* 1516 */     return this.subjectPublicKeyAlgID.toString();
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
/*      */   public PublicKey getSubjectPublicKey() {
/* 1528 */     return this.subjectPublicKey;
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
/*      */   public boolean[] getKeyUsage() {
/* 1546 */     if (this.keyUsage == null) {
/* 1547 */       return null;
/*      */     }
/* 1549 */     return (boolean[])this.keyUsage.clone();
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
/*      */   public Set<String> getExtendedKeyUsage() {
/* 1565 */     return this.keyPurposeSet;
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
/*      */   public boolean getMatchAllSubjectAltNames() {
/* 1585 */     return this.matchAllSubjectAltNames;
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
/*      */   public Collection<List<?>> getSubjectAlternativeNames() {
/* 1622 */     if (this.subjectAlternativeNames == null) {
/* 1623 */       return null;
/*      */     }
/* 1625 */     return cloneNames(this.subjectAlternativeNames);
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
/*      */   private static Set<List<?>> cloneNames(Collection<List<?>> paramCollection) {
/*      */     try {
/* 1650 */       return cloneAndCheckNames(paramCollection);
/* 1651 */     } catch (IOException iOException) {
/* 1652 */       throw new RuntimeException("cloneNames encountered IOException: " + iOException
/* 1653 */           .getMessage());
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
/*      */   private static Set<List<?>> cloneAndCheckNames(Collection<List<?>> paramCollection) throws IOException {
/* 1674 */     HashSet<List<?>> hashSet = new HashSet();
/* 1675 */     for (List<?> list : paramCollection)
/*      */     {
/* 1677 */       hashSet.add(new ArrayList(list));
/*      */     }
/*      */ 
/*      */     
/* 1681 */     for (List<Object> list1 : (Iterable<List<Object>>)hashSet) {
/*      */       
/* 1683 */       List<Object> list2 = list1;
/* 1684 */       if (list2.size() != 2) {
/* 1685 */         throw new IOException("name list size not 2");
/*      */       }
/* 1687 */       Integer integer = (Integer)list2.get(0);
/* 1688 */       if (!(integer instanceof Integer)) {
/* 1689 */         throw new IOException("expected an Integer");
/*      */       }
/* 1691 */       int i = ((Integer)integer).intValue();
/* 1692 */       if (i < 0 || i > 8) {
/* 1693 */         throw new IOException("name type not 0-8");
/*      */       }
/* 1695 */       byte[] arrayOfByte = (byte[])list2.get(1);
/* 1696 */       if (!(arrayOfByte instanceof byte[]) && !(arrayOfByte instanceof String)) {
/*      */         
/* 1698 */         if (debug != null) {
/* 1699 */           debug.println("X509CertSelector.cloneAndCheckNames() name not byte array");
/*      */         }
/*      */         
/* 1702 */         throw new IOException("name not byte array or String");
/*      */       } 
/* 1704 */       if (arrayOfByte instanceof byte[]) {
/* 1705 */         list2.set(1, ((byte[])arrayOfByte).clone());
/*      */       }
/*      */     } 
/* 1708 */     return hashSet;
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
/*      */   public byte[] getNameConstraints() {
/* 1732 */     if (this.ncBytes == null) {
/* 1733 */       return null;
/*      */     }
/* 1735 */     return (byte[])this.ncBytes.clone();
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
/*      */   public int getBasicConstraints() {
/* 1750 */     return this.basicConstraints;
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
/*      */   public Set<String> getPolicy() {
/* 1766 */     return this.policySet;
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
/*      */   public Collection<List<?>> getPathToNames() {
/* 1801 */     if (this.pathToNames == null) {
/* 1802 */       return null;
/*      */     }
/* 1804 */     return cloneNames(this.pathToNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1814 */     StringBuffer stringBuffer = new StringBuffer();
/* 1815 */     stringBuffer.append("X509CertSelector: [\n");
/* 1816 */     if (this.x509Cert != null) {
/* 1817 */       stringBuffer.append("  Certificate: " + this.x509Cert.toString() + "\n");
/*      */     }
/* 1819 */     if (this.serialNumber != null) {
/* 1820 */       stringBuffer.append("  Serial Number: " + this.serialNumber.toString() + "\n");
/*      */     }
/* 1822 */     if (this.issuer != null) {
/* 1823 */       stringBuffer.append("  Issuer: " + getIssuerAsString() + "\n");
/*      */     }
/* 1825 */     if (this.subject != null) {
/* 1826 */       stringBuffer.append("  Subject: " + getSubjectAsString() + "\n");
/*      */     }
/* 1828 */     stringBuffer.append("  matchAllSubjectAltNames flag: " + 
/* 1829 */         String.valueOf(this.matchAllSubjectAltNames) + "\n");
/* 1830 */     if (this.subjectAlternativeNames != null) {
/* 1831 */       stringBuffer.append("  SubjectAlternativeNames:\n");
/* 1832 */       Iterator<List<?>> iterator = this.subjectAlternativeNames.iterator();
/* 1833 */       while (iterator.hasNext()) {
/* 1834 */         List<String> list = (List)iterator.next();
/* 1835 */         stringBuffer.append("    type " + list.get(0) + ", name " + list
/* 1836 */             .get(1) + "\n");
/*      */       } 
/*      */     } 
/* 1839 */     if (this.subjectKeyID != null) {
/* 1840 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 1841 */       stringBuffer.append("  Subject Key Identifier: " + hexDumpEncoder
/* 1842 */           .encodeBuffer(this.subjectKeyID) + "\n");
/*      */     } 
/* 1844 */     if (this.authorityKeyID != null) {
/* 1845 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 1846 */       stringBuffer.append("  Authority Key Identifier: " + hexDumpEncoder
/* 1847 */           .encodeBuffer(this.authorityKeyID) + "\n");
/*      */     } 
/* 1849 */     if (this.certificateValid != null) {
/* 1850 */       stringBuffer.append("  Certificate Valid: " + this.certificateValid
/* 1851 */           .toString() + "\n");
/*      */     }
/* 1853 */     if (this.privateKeyValid != null) {
/* 1854 */       stringBuffer.append("  Private Key Valid: " + this.privateKeyValid
/* 1855 */           .toString() + "\n");
/*      */     }
/* 1857 */     if (this.subjectPublicKeyAlgID != null) {
/* 1858 */       stringBuffer.append("  Subject Public Key AlgID: " + this.subjectPublicKeyAlgID
/* 1859 */           .toString() + "\n");
/*      */     }
/* 1861 */     if (this.subjectPublicKey != null) {
/* 1862 */       stringBuffer.append("  Subject Public Key: " + this.subjectPublicKey
/* 1863 */           .toString() + "\n");
/*      */     }
/* 1865 */     if (this.keyUsage != null) {
/* 1866 */       stringBuffer.append("  Key Usage: " + keyUsageToString(this.keyUsage) + "\n");
/*      */     }
/* 1868 */     if (this.keyPurposeSet != null) {
/* 1869 */       stringBuffer.append("  Extended Key Usage: " + this.keyPurposeSet
/* 1870 */           .toString() + "\n");
/*      */     }
/* 1872 */     if (this.policy != null) {
/* 1873 */       stringBuffer.append("  Policy: " + this.policy.toString() + "\n");
/*      */     }
/* 1875 */     if (this.pathToGeneralNames != null) {
/* 1876 */       stringBuffer.append("  Path to names:\n");
/* 1877 */       Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 1878 */       while (iterator.hasNext()) {
/* 1879 */         stringBuffer.append("    " + iterator.next() + "\n");
/*      */       }
/*      */     } 
/* 1882 */     stringBuffer.append("]");
/* 1883 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String keyUsageToString(boolean[] paramArrayOfboolean) {
/* 1892 */     String str = "KeyUsage [\n";
/*      */     try {
/* 1894 */       if (paramArrayOfboolean[0]) {
/* 1895 */         str = str + "  DigitalSignature\n";
/*      */       }
/* 1897 */       if (paramArrayOfboolean[1]) {
/* 1898 */         str = str + "  Non_repudiation\n";
/*      */       }
/* 1900 */       if (paramArrayOfboolean[2]) {
/* 1901 */         str = str + "  Key_Encipherment\n";
/*      */       }
/* 1903 */       if (paramArrayOfboolean[3]) {
/* 1904 */         str = str + "  Data_Encipherment\n";
/*      */       }
/* 1906 */       if (paramArrayOfboolean[4]) {
/* 1907 */         str = str + "  Key_Agreement\n";
/*      */       }
/* 1909 */       if (paramArrayOfboolean[5]) {
/* 1910 */         str = str + "  Key_CertSign\n";
/*      */       }
/* 1912 */       if (paramArrayOfboolean[6]) {
/* 1913 */         str = str + "  Crl_Sign\n";
/*      */       }
/* 1915 */       if (paramArrayOfboolean[7]) {
/* 1916 */         str = str + "  Encipher_Only\n";
/*      */       }
/* 1918 */       if (paramArrayOfboolean[8]) {
/* 1919 */         str = str + "  Decipher_Only\n";
/*      */       }
/* 1921 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     
/* 1923 */     str = str + "]\n";
/*      */     
/* 1925 */     return str;
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
/*      */   private static Extension getExtensionObject(X509Certificate paramX509Certificate, int paramInt) throws IOException {
/* 1949 */     if (paramX509Certificate instanceof X509CertImpl) {
/* 1950 */       X509CertImpl x509CertImpl = (X509CertImpl)paramX509Certificate;
/* 1951 */       switch (paramInt) {
/*      */         case 0:
/* 1953 */           return x509CertImpl.getPrivateKeyUsageExtension();
/*      */         case 1:
/* 1955 */           return x509CertImpl.getSubjectAlternativeNameExtension();
/*      */         case 2:
/* 1957 */           return x509CertImpl.getNameConstraintsExtension();
/*      */         case 3:
/* 1959 */           return x509CertImpl.getCertificatePoliciesExtension();
/*      */         case 4:
/* 1961 */           return x509CertImpl.getExtendedKeyUsageExtension();
/*      */       } 
/* 1963 */       return null;
/*      */     } 
/*      */     
/* 1966 */     byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue(EXTENSION_OIDS[paramInt]);
/* 1967 */     if (arrayOfByte1 == null) {
/* 1968 */       return null;
/*      */     }
/* 1970 */     DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 1971 */     byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 1972 */     switch (paramInt) {
/*      */       case 0:
/*      */         try {
/* 1975 */           return new PrivateKeyUsageExtension(FALSE, arrayOfByte2);
/* 1976 */         } catch (CertificateException certificateException) {
/* 1977 */           throw new IOException(certificateException.getMessage());
/*      */         } 
/*      */       case 1:
/* 1980 */         return new SubjectAlternativeNameExtension(FALSE, arrayOfByte2);
/*      */       case 2:
/* 1982 */         return new NameConstraintsExtension(FALSE, arrayOfByte2);
/*      */       case 3:
/* 1984 */         return new CertificatePoliciesExtension(FALSE, arrayOfByte2);
/*      */       case 4:
/* 1986 */         return new ExtendedKeyUsageExtension(FALSE, arrayOfByte2);
/*      */     } 
/* 1988 */     return null;
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
/*      */   public boolean match(Certificate paramCertificate) {
/* 2000 */     if (!(paramCertificate instanceof X509Certificate)) {
/* 2001 */       return false;
/*      */     }
/* 2003 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*      */     
/* 2005 */     if (debug != null) {
/* 2006 */       debug.println("X509CertSelector.match(SN: " + x509Certificate
/* 2007 */           .getSerialNumber().toString(16) + "\n  Issuer: " + x509Certificate
/* 2008 */           .getIssuerDN() + "\n  Subject: " + x509Certificate.getSubjectDN() + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2013 */     if (this.x509Cert != null && 
/* 2014 */       !this.x509Cert.equals(x509Certificate)) {
/* 2015 */       if (debug != null) {
/* 2016 */         debug.println("X509CertSelector.match: certs don't match");
/*      */       }
/*      */       
/* 2019 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2024 */     if (this.serialNumber != null && 
/* 2025 */       !this.serialNumber.equals(x509Certificate.getSerialNumber())) {
/* 2026 */       if (debug != null) {
/* 2027 */         debug.println("X509CertSelector.match: serial numbers don't match");
/*      */       }
/*      */       
/* 2030 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2035 */     if (this.issuer != null && 
/* 2036 */       !this.issuer.equals(x509Certificate.getIssuerX500Principal())) {
/* 2037 */       if (debug != null) {
/* 2038 */         debug.println("X509CertSelector.match: issuer DNs don't match");
/*      */       }
/*      */       
/* 2041 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2046 */     if (this.subject != null && 
/* 2047 */       !this.subject.equals(x509Certificate.getSubjectX500Principal())) {
/* 2048 */       if (debug != null) {
/* 2049 */         debug.println("X509CertSelector.match: subject DNs don't match");
/*      */       }
/*      */       
/* 2052 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2057 */     if (this.certificateValid != null) {
/*      */       try {
/* 2059 */         x509Certificate.checkValidity(this.certificateValid);
/* 2060 */       } catch (CertificateException certificateException) {
/* 2061 */         if (debug != null) {
/* 2062 */           debug.println("X509CertSelector.match: certificate not within validity period");
/*      */         }
/*      */         
/* 2065 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2070 */     if (this.subjectPublicKeyBytes != null) {
/* 2071 */       byte[] arrayOfByte = x509Certificate.getPublicKey().getEncoded();
/* 2072 */       if (!Arrays.equals(this.subjectPublicKeyBytes, arrayOfByte)) {
/* 2073 */         if (debug != null) {
/* 2074 */           debug.println("X509CertSelector.match: subject public keys don't match");
/*      */         }
/*      */         
/* 2077 */         return false;
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
/*      */ 
/*      */     
/* 2091 */     boolean bool = (matchBasicConstraints(x509Certificate) && matchKeyUsage(x509Certificate) && matchExtendedKeyUsage(x509Certificate) && matchSubjectKeyID(x509Certificate) && matchAuthorityKeyID(x509Certificate) && matchPrivateKeyValid(x509Certificate) && matchSubjectPublicKeyAlgID(x509Certificate) && matchPolicy(x509Certificate) && matchSubjectAlternativeNames(x509Certificate) && matchPathToNames(x509Certificate) && matchNameConstraints(x509Certificate)) ? true : false;
/*      */     
/* 2093 */     if (bool && debug != null) {
/* 2094 */       debug.println("X509CertSelector.match returning: true");
/*      */     }
/* 2096 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectKeyID(X509Certificate paramX509Certificate) {
/* 2101 */     if (this.subjectKeyID == null) {
/* 2102 */       return true;
/*      */     }
/*      */     try {
/* 2105 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
/* 2106 */       if (arrayOfByte1 == null) {
/* 2107 */         if (debug != null) {
/* 2108 */           debug.println("X509CertSelector.match: no subject key ID extension");
/*      */         }
/*      */         
/* 2111 */         return false;
/*      */       } 
/* 2113 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 2114 */       byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 2115 */       if (arrayOfByte2 == null || 
/* 2116 */         !Arrays.equals(this.subjectKeyID, arrayOfByte2)) {
/* 2117 */         if (debug != null) {
/* 2118 */           debug.println("X509CertSelector.match: subject key IDs don't match\nX509CertSelector.match: subjectKeyID: " + 
/*      */               
/* 2120 */               Arrays.toString(this.subjectKeyID) + "\nX509CertSelector.match: certSubjectKeyID: " + 
/*      */               
/* 2122 */               Arrays.toString(arrayOfByte2));
/*      */         }
/* 2124 */         return false;
/*      */       } 
/* 2126 */     } catch (IOException iOException) {
/* 2127 */       if (debug != null) {
/* 2128 */         debug.println("X509CertSelector.match: exception in subject key ID check");
/*      */       }
/*      */       
/* 2131 */       return false;
/*      */     } 
/* 2133 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchAuthorityKeyID(X509Certificate paramX509Certificate) {
/* 2138 */     if (this.authorityKeyID == null) {
/* 2139 */       return true;
/*      */     }
/*      */     try {
/* 2142 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.35");
/* 2143 */       if (arrayOfByte1 == null) {
/* 2144 */         if (debug != null) {
/* 2145 */           debug.println("X509CertSelector.match: no authority key ID extension");
/*      */         }
/*      */         
/* 2148 */         return false;
/*      */       } 
/* 2150 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 2151 */       byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 2152 */       if (arrayOfByte2 == null || 
/* 2153 */         !Arrays.equals(this.authorityKeyID, arrayOfByte2)) {
/* 2154 */         if (debug != null) {
/* 2155 */           debug.println("X509CertSelector.match: authority key IDs don't match");
/*      */         }
/*      */         
/* 2158 */         return false;
/*      */       } 
/* 2160 */     } catch (IOException iOException) {
/* 2161 */       if (debug != null) {
/* 2162 */         debug.println("X509CertSelector.match: exception in authority key ID check");
/*      */       }
/*      */       
/* 2165 */       return false;
/*      */     } 
/* 2167 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPrivateKeyValid(X509Certificate paramX509Certificate) {
/* 2172 */     if (this.privateKeyValid == null) {
/* 2173 */       return true;
/*      */     }
/* 2175 */     PrivateKeyUsageExtension privateKeyUsageExtension = null;
/*      */     
/*      */     try {
/* 2178 */       privateKeyUsageExtension = (PrivateKeyUsageExtension)getExtensionObject(paramX509Certificate, 0);
/* 2179 */       if (privateKeyUsageExtension != null) {
/* 2180 */         privateKeyUsageExtension.valid(this.privateKeyValid);
/*      */       }
/* 2182 */     } catch (CertificateExpiredException certificateExpiredException) {
/* 2183 */       if (debug != null) {
/* 2184 */         String str = "n/a";
/*      */         try {
/* 2186 */           Date date = privateKeyUsageExtension.get("not_after");
/* 2187 */           str = date.toString();
/* 2188 */         } catch (CertificateException certificateException) {}
/*      */ 
/*      */         
/* 2191 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_After: " + str + "; X509CertSelector: " + 
/*      */ 
/*      */             
/* 2194 */             toString());
/* 2195 */         certificateExpiredException.printStackTrace();
/*      */       } 
/* 2197 */       return false;
/* 2198 */     } catch (CertificateNotYetValidException certificateNotYetValidException) {
/* 2199 */       if (debug != null) {
/* 2200 */         String str = "n/a";
/*      */         try {
/* 2202 */           Date date = privateKeyUsageExtension.get("not_before");
/* 2203 */           str = date.toString();
/* 2204 */         } catch (CertificateException certificateException) {}
/*      */ 
/*      */         
/* 2207 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_BEFORE: " + str + "; X509CertSelector: " + 
/*      */ 
/*      */             
/* 2210 */             toString());
/* 2211 */         certificateNotYetValidException.printStackTrace();
/*      */       } 
/* 2213 */       return false;
/* 2214 */     } catch (IOException iOException) {
/* 2215 */       if (debug != null) {
/* 2216 */         debug.println("X509CertSelector.match: IOException in private key usage check; X509CertSelector: " + 
/*      */             
/* 2218 */             toString());
/* 2219 */         iOException.printStackTrace();
/*      */       } 
/* 2221 */       return false;
/*      */     } 
/* 2223 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectPublicKeyAlgID(X509Certificate paramX509Certificate) {
/* 2228 */     if (this.subjectPublicKeyAlgID == null) {
/* 2229 */       return true;
/*      */     }
/*      */     try {
/* 2232 */       byte[] arrayOfByte = paramX509Certificate.getPublicKey().getEncoded();
/* 2233 */       DerValue derValue = new DerValue(arrayOfByte);
/* 2234 */       if (derValue.tag != 48) {
/* 2235 */         throw new IOException("invalid key format");
/*      */       }
/*      */       
/* 2238 */       AlgorithmId algorithmId = AlgorithmId.parse(derValue.data.getDerValue());
/* 2239 */       if (debug != null) {
/* 2240 */         debug.println("X509CertSelector.match: subjectPublicKeyAlgID = " + this.subjectPublicKeyAlgID + ", xcert subjectPublicKeyAlgID = " + algorithmId
/*      */             
/* 2242 */             .getOID());
/*      */       }
/* 2244 */       if (!this.subjectPublicKeyAlgID.equals(algorithmId.getOID())) {
/* 2245 */         if (debug != null) {
/* 2246 */           debug.println("X509CertSelector.match: subject public key alg IDs don't match");
/*      */         }
/*      */         
/* 2249 */         return false;
/*      */       } 
/* 2251 */     } catch (IOException iOException) {
/* 2252 */       if (debug != null) {
/* 2253 */         debug.println("X509CertSelector.match: IOException in subject public key algorithm OID check");
/*      */       }
/*      */       
/* 2256 */       return false;
/*      */     } 
/* 2258 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchKeyUsage(X509Certificate paramX509Certificate) {
/* 2263 */     if (this.keyUsage == null) {
/* 2264 */       return true;
/*      */     }
/* 2266 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 2267 */     if (arrayOfBoolean != null) {
/* 2268 */       for (byte b = 0; b < this.keyUsage.length; b++) {
/* 2269 */         if (this.keyUsage[b] && (b >= arrayOfBoolean.length || !arrayOfBoolean[b])) {
/*      */           
/* 2271 */           if (debug != null) {
/* 2272 */             debug.println("X509CertSelector.match: key usage bits don't match");
/*      */           }
/*      */           
/* 2275 */           return false;
/*      */         } 
/*      */       } 
/*      */     }
/* 2279 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchExtendedKeyUsage(X509Certificate paramX509Certificate) {
/* 2284 */     if (this.keyPurposeSet == null || this.keyPurposeSet.isEmpty()) {
/* 2285 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2289 */       ExtendedKeyUsageExtension extendedKeyUsageExtension = (ExtendedKeyUsageExtension)getExtensionObject(paramX509Certificate, 4);
/*      */       
/* 2291 */       if (extendedKeyUsageExtension != null) {
/*      */         
/* 2293 */         Vector<ObjectIdentifier> vector = extendedKeyUsageExtension.get("usages");
/* 2294 */         if (!vector.contains(ANY_EXTENDED_KEY_USAGE) && 
/* 2295 */           !vector.containsAll(this.keyPurposeOIDSet)) {
/* 2296 */           if (debug != null) {
/* 2297 */             debug.println("X509CertSelector.match: cert failed extendedKeyUsage criterion");
/*      */           }
/*      */           
/* 2300 */           return false;
/*      */         } 
/*      */       } 
/* 2303 */     } catch (IOException iOException) {
/* 2304 */       if (debug != null) {
/* 2305 */         debug.println("X509CertSelector.match: IOException in extended key usage check");
/*      */       }
/*      */       
/* 2308 */       return false;
/*      */     } 
/* 2310 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectAlternativeNames(X509Certificate paramX509Certificate) {
/* 2315 */     if (this.subjectAlternativeNames == null || this.subjectAlternativeNames.isEmpty()) {
/* 2316 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2320 */       SubjectAlternativeNameExtension subjectAlternativeNameExtension = (SubjectAlternativeNameExtension)getExtensionObject(paramX509Certificate, 1);
/*      */       
/* 2322 */       if (subjectAlternativeNameExtension == null) {
/* 2323 */         if (debug != null) {
/* 2324 */           debug.println("X509CertSelector.match: no subject alternative name extension");
/*      */         }
/*      */         
/* 2327 */         return false;
/*      */       } 
/*      */       
/* 2330 */       GeneralNames generalNames = subjectAlternativeNameExtension.get("subject_name");
/*      */       
/* 2332 */       Iterator<GeneralNameInterface> iterator = this.subjectAlternativeGeneralNames.iterator();
/* 2333 */       while (iterator.hasNext()) {
/* 2334 */         GeneralNameInterface generalNameInterface = iterator.next();
/* 2335 */         boolean bool = false;
/* 2336 */         Iterator<GeneralName> iterator1 = generalNames.iterator();
/* 2337 */         while (iterator1.hasNext() && !bool) {
/* 2338 */           GeneralNameInterface generalNameInterface1 = ((GeneralName)iterator1.next()).getName();
/* 2339 */           bool = generalNameInterface1.equals(generalNameInterface);
/*      */         } 
/* 2341 */         if (!bool && (this.matchAllSubjectAltNames || !iterator.hasNext())) {
/* 2342 */           if (debug != null) {
/* 2343 */             debug.println("X509CertSelector.match: subject alternative name " + generalNameInterface + " not found");
/*      */           }
/*      */           
/* 2346 */           return false;
/* 2347 */         }  if (bool && !this.matchAllSubjectAltNames) {
/*      */           break;
/*      */         }
/*      */       } 
/* 2351 */     } catch (IOException iOException) {
/* 2352 */       if (debug != null) {
/* 2353 */         debug.println("X509CertSelector.match: IOException in subject alternative name check");
/*      */       }
/* 2355 */       return false;
/*      */     } 
/* 2357 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchNameConstraints(X509Certificate paramX509Certificate) {
/* 2362 */     if (this.nc == null) {
/* 2363 */       return true;
/*      */     }
/*      */     try {
/* 2366 */       if (!this.nc.verify(paramX509Certificate)) {
/* 2367 */         if (debug != null) {
/* 2368 */           debug.println("X509CertSelector.match: name constraints not satisfied");
/*      */         }
/*      */         
/* 2371 */         return false;
/*      */       } 
/* 2373 */     } catch (IOException iOException) {
/* 2374 */       if (debug != null) {
/* 2375 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2378 */       return false;
/*      */     } 
/* 2380 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPolicy(X509Certificate paramX509Certificate) {
/* 2385 */     if (this.policy == null) {
/* 2386 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2390 */       CertificatePoliciesExtension certificatePoliciesExtension = (CertificatePoliciesExtension)getExtensionObject(paramX509Certificate, 3);
/* 2391 */       if (certificatePoliciesExtension == null) {
/* 2392 */         if (debug != null) {
/* 2393 */           debug.println("X509CertSelector.match: no certificate policy extension");
/*      */         }
/*      */         
/* 2396 */         return false;
/*      */       } 
/* 2398 */       List<PolicyInformation> list = certificatePoliciesExtension.get("policies");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2403 */       ArrayList<CertificatePolicyId> arrayList = new ArrayList(list.size());
/* 2404 */       for (PolicyInformation policyInformation : list) {
/* 2405 */         arrayList.add(policyInformation.getPolicyIdentifier());
/*      */       }
/* 2407 */       if (this.policy != null) {
/* 2408 */         boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2414 */         if (this.policy.getCertPolicyIds().isEmpty()) {
/* 2415 */           if (arrayList.isEmpty()) {
/* 2416 */             if (debug != null) {
/* 2417 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2420 */             return false;
/*      */           } 
/*      */         } else {
/* 2423 */           for (CertificatePolicyId certificatePolicyId : this.policy.getCertPolicyIds()) {
/* 2424 */             if (arrayList.contains(certificatePolicyId)) {
/* 2425 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 2429 */           if (!bool) {
/* 2430 */             if (debug != null) {
/* 2431 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2434 */             return false;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2438 */     } catch (IOException iOException) {
/* 2439 */       if (debug != null) {
/* 2440 */         debug.println("X509CertSelector.match: IOException in certificate policy ID check");
/*      */       }
/*      */       
/* 2443 */       return false;
/*      */     } 
/* 2445 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPathToNames(X509Certificate paramX509Certificate) {
/* 2450 */     if (this.pathToGeneralNames == null) {
/* 2451 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2455 */       NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension)getExtensionObject(paramX509Certificate, 2);
/* 2456 */       if (nameConstraintsExtension == null) {
/* 2457 */         return true;
/*      */       }
/* 2459 */       if (debug != null && Debug.isOn("certpath")) {
/* 2460 */         debug.println("X509CertSelector.match pathToNames:\n");
/*      */         
/* 2462 */         Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2463 */         while (iterator.hasNext()) {
/* 2464 */           debug.println("    " + iterator.next() + "\n");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2469 */       GeneralSubtrees generalSubtrees1 = nameConstraintsExtension.get("permitted_subtrees");
/*      */       
/* 2471 */       GeneralSubtrees generalSubtrees2 = nameConstraintsExtension.get("excluded_subtrees");
/* 2472 */       if (generalSubtrees2 != null && 
/* 2473 */         !matchExcluded(generalSubtrees2)) {
/* 2474 */         return false;
/*      */       }
/*      */       
/* 2477 */       if (generalSubtrees1 != null && 
/* 2478 */         !matchPermitted(generalSubtrees1)) {
/* 2479 */         return false;
/*      */       }
/*      */     }
/* 2482 */     catch (IOException iOException) {
/* 2483 */       if (debug != null) {
/* 2484 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2487 */       return false;
/*      */     } 
/* 2489 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchExcluded(GeneralSubtrees paramGeneralSubtrees) {
/* 2498 */     for (GeneralSubtree generalSubtree : paramGeneralSubtrees) {
/*      */       
/* 2500 */       GeneralNameInterface generalNameInterface = generalSubtree.getName().getName();
/* 2501 */       Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2502 */       while (iterator.hasNext()) {
/* 2503 */         GeneralNameInterface generalNameInterface1 = iterator.next();
/* 2504 */         if (generalNameInterface.getType() == generalNameInterface1.getType()) {
/* 2505 */           switch (generalNameInterface1.constrains(generalNameInterface)) {
/*      */             case 0:
/*      */             case 2:
/* 2508 */               if (debug != null) {
/* 2509 */                 debug.println("X509CertSelector.match: name constraints inhibit path to specified name");
/*      */                 
/* 2511 */                 debug.println("X509CertSelector.match: excluded name: " + generalNameInterface1);
/*      */               } 
/*      */               
/* 2514 */               return false;
/*      */           } 
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/* 2520 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchPermitted(GeneralSubtrees paramGeneralSubtrees) {
/* 2530 */     Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2531 */     while (iterator.hasNext()) {
/* 2532 */       GeneralNameInterface generalNameInterface = iterator.next();
/* 2533 */       Iterator<GeneralSubtree> iterator1 = paramGeneralSubtrees.iterator();
/* 2534 */       boolean bool1 = false;
/* 2535 */       boolean bool2 = false;
/* 2536 */       String str = "";
/* 2537 */       while (iterator1.hasNext() && !bool1) {
/* 2538 */         GeneralSubtree generalSubtree = iterator1.next();
/* 2539 */         GeneralNameInterface generalNameInterface1 = generalSubtree.getName().getName();
/* 2540 */         if (generalNameInterface1.getType() == generalNameInterface.getType()) {
/* 2541 */           bool2 = true;
/* 2542 */           str = str + "  " + generalNameInterface1;
/* 2543 */           switch (generalNameInterface.constrains(generalNameInterface1)) {
/*      */             case 0:
/*      */             case 2:
/* 2546 */               bool1 = true;
/*      */           } 
/*      */ 
/*      */         
/*      */         } 
/*      */       } 
/* 2552 */       if (!bool1 && bool2) {
/* 2553 */         if (debug != null) {
/* 2554 */           debug.println("X509CertSelector.match: name constraints inhibit path to specified name; permitted names of type " + generalNameInterface
/*      */               
/* 2556 */               .getType() + ": " + str);
/*      */         }
/* 2558 */         return false;
/*      */       } 
/*      */     } 
/* 2561 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchBasicConstraints(X509Certificate paramX509Certificate) {
/* 2566 */     if (this.basicConstraints == -1) {
/* 2567 */       return true;
/*      */     }
/* 2569 */     int i = paramX509Certificate.getBasicConstraints();
/* 2570 */     if (this.basicConstraints == -2) {
/* 2571 */       if (i != -1) {
/* 2572 */         if (debug != null) {
/* 2573 */           debug.println("X509CertSelector.match: not an EE cert");
/*      */         }
/* 2575 */         return false;
/*      */       }
/*      */     
/* 2578 */     } else if (i < this.basicConstraints) {
/* 2579 */       if (debug != null) {
/* 2580 */         debug.println("X509CertSelector.match: cert's maxPathLen is less than the min maxPathLen set by basicConstraints. (" + i + " < " + this.basicConstraints + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2585 */       return false;
/*      */     } 
/*      */     
/* 2588 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> Set<T> cloneSet(Set<T> paramSet) {
/* 2593 */     if (paramSet instanceof HashSet) {
/* 2594 */       Object object = ((HashSet)paramSet).clone();
/* 2595 */       return (Set<T>)object;
/*      */     } 
/* 2597 */     return new HashSet<>(paramSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/* 2608 */       X509CertSelector x509CertSelector = (X509CertSelector)super.clone();
/*      */       
/* 2610 */       if (this.subjectAlternativeNames != null) {
/* 2611 */         x509CertSelector
/* 2612 */           .subjectAlternativeNames = cloneSet(this.subjectAlternativeNames);
/* 2613 */         x509CertSelector
/* 2614 */           .subjectAlternativeGeneralNames = cloneSet(this.subjectAlternativeGeneralNames);
/*      */       } 
/* 2616 */       if (this.pathToGeneralNames != null) {
/* 2617 */         x509CertSelector.pathToNames = cloneSet(this.pathToNames);
/* 2618 */         x509CertSelector.pathToGeneralNames = cloneSet(this.pathToGeneralNames);
/*      */       } 
/* 2620 */       return x509CertSelector;
/* 2621 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 2623 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/security/cert/X509CertSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */