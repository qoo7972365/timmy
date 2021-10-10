/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AlgorithmParameters;
/*     */ import java.security.CryptoPrimitive;
/*     */ import java.security.Key;
/*     */ import java.security.cert.CertPathValidatorException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class DisabledAlgorithmConstraints
/*     */   extends AbstractAlgorithmConstraints
/*     */ {
/*  61 */   private static final Debug debug = Debug.getInstance("certpath");
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PROPERTY_CERTPATH_DISABLED_ALGS = "jdk.certpath.disabledAlgorithms";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PROPERTY_TLS_DISABLED_ALGS = "jdk.tls.disabledAlgorithms";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String PROPERTY_JAR_DISABLED_ALGS = "jdk.jar.disabledAlgorithms";
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] disabledAlgorithms;
/*     */ 
/*     */   
/*     */   private final Constraints algorithmConstraints;
/*     */ 
/*     */ 
/*     */   
/*     */   public DisabledAlgorithmConstraints(String paramString) {
/*  85 */     this(paramString, new AlgorithmDecomposer());
/*     */   }
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
/*     */   public DisabledAlgorithmConstraints(String paramString, AlgorithmDecomposer paramAlgorithmDecomposer) {
/*  98 */     super(paramAlgorithmDecomposer);
/*  99 */     this.disabledAlgorithms = getAlgorithms(paramString);
/* 100 */     this.algorithmConstraints = new Constraints(this.disabledAlgorithms);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean permits(Set<CryptoPrimitive> paramSet, String paramString, AlgorithmParameters paramAlgorithmParameters) {
/* 110 */     if (!checkAlgorithm(this.disabledAlgorithms, paramString, this.decomposer)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     if (paramAlgorithmParameters != null) {
/* 115 */       return this.algorithmConstraints.permits(paramString, paramAlgorithmParameters);
/*     */     }
/*     */     
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean permits(Set<CryptoPrimitive> paramSet, Key paramKey) {
/* 127 */     return checkConstraints(paramSet, "", paramKey, (AlgorithmParameters)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean permits(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters) {
/* 138 */     if (paramString == null || paramString.length() == 0) {
/* 139 */       throw new IllegalArgumentException("No algorithm name specified");
/*     */     }
/*     */     
/* 142 */     return checkConstraints(paramSet, paramString, paramKey, paramAlgorithmParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void permits(ConstraintsParameters paramConstraintsParameters) throws CertPathValidatorException {
/* 147 */     permits(paramConstraintsParameters.getAlgorithm(), paramConstraintsParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void permits(String paramString1, Key paramKey, AlgorithmParameters paramAlgorithmParameters, String paramString2) throws CertPathValidatorException {
/* 153 */     permits(paramString1, new ConstraintsParameters(paramString1, paramAlgorithmParameters, paramKey, (paramString2 == null) ? "generic" : paramString2));
/*     */   }
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
/*     */   public final void permits(String paramString, ConstraintsParameters paramConstraintsParameters) throws CertPathValidatorException {
/* 167 */     this.algorithmConstraints.permits(paramString, paramConstraintsParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkProperty(String paramString) {
/* 172 */     paramString = paramString.toLowerCase(Locale.ENGLISH);
/* 173 */     for (String str : this.disabledAlgorithms) {
/* 174 */       if (str.toLowerCase(Locale.ENGLISH).indexOf(paramString) >= 0) {
/* 175 */         return true;
/*     */       }
/*     */     } 
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkConstraints(Set<CryptoPrimitive> paramSet, String paramString, Key paramKey, AlgorithmParameters paramAlgorithmParameters) {
/* 186 */     if (paramKey == null) {
/* 187 */       throw new IllegalArgumentException("The key cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (paramString != null && paramString.length() != 0 && 
/* 192 */       !permits(paramSet, paramString, paramAlgorithmParameters)) {
/* 193 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 198 */     if (!permits(paramSet, paramKey.getAlgorithm(), (AlgorithmParameters)null)) {
/* 199 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 203 */     return this.algorithmConstraints.permits(paramKey);
/*     */   }
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
/*     */   private static class Constraints
/*     */   {
/* 226 */     private Map<String, List<DisabledAlgorithmConstraints.Constraint>> constraintsMap = new HashMap<>();
/*     */     
/*     */     private static class Holder {
/* 229 */       private static final Pattern DENY_AFTER_PATTERN = Pattern.compile("denyAfter\\s+(\\d{4})-(\\d{2})-(\\d{2})");
/*     */     }
/*     */ 
/*     */     
/*     */     public Constraints(String[] param1ArrayOfString) {
/* 234 */       for (String str : param1ArrayOfString) {
/* 235 */         if (str != null && !str.isEmpty()) {
/*     */ 
/*     */ 
/*     */           
/* 239 */           str = str.trim();
/* 240 */           if (DisabledAlgorithmConstraints.debug != null) {
/* 241 */             DisabledAlgorithmConstraints.debug.println("Constraints: " + str);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 246 */           int i = str.indexOf(' ');
/* 247 */           String str1 = AlgorithmDecomposer.hashName(((i > 0) ? str
/* 248 */               .substring(0, i) : str)
/*     */               
/* 250 */               .toUpperCase(Locale.ENGLISH));
/*     */           
/* 252 */           List<DisabledAlgorithmConstraints.Constraint> list = this.constraintsMap.getOrDefault(str1, new ArrayList<>(1));
/*     */ 
/*     */ 
/*     */           
/* 256 */           for (String str2 : AlgorithmDecomposer.getAliases(str1)) {
/* 257 */             this.constraintsMap.putIfAbsent(str2, list);
/*     */           }
/*     */           
/* 260 */           if (i <= 0) {
/* 261 */             list.add(new DisabledAlgorithmConstraints.DisabledConstraint(str1));
/*     */           }
/*     */           else {
/*     */             
/* 265 */             String str2 = str.substring(i + 1);
/*     */ 
/*     */             
/* 268 */             DisabledAlgorithmConstraints.UsageConstraint usageConstraint = null;
/*     */             
/* 270 */             boolean bool1 = false;
/*     */             
/* 272 */             boolean bool2 = false;
/*     */             
/* 274 */             for (String str3 : str2.split("&")) {
/* 275 */               DisabledAlgorithmConstraints.UsageConstraint usageConstraint1; str3 = str3.trim();
/*     */ 
/*     */               
/* 278 */               if (str3.startsWith("keySize")) {
/* 279 */                 if (DisabledAlgorithmConstraints.debug != null) {
/* 280 */                   DisabledAlgorithmConstraints.debug.println("Constraints set to keySize: " + str3);
/*     */                 }
/*     */                 
/* 283 */                 StringTokenizer stringTokenizer = new StringTokenizer(str3);
/* 284 */                 if (!"keySize".equals(stringTokenizer.nextToken())) {
/* 285 */                   throw new IllegalArgumentException("Error in security property. Constraint unknown: " + str3);
/*     */                 }
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 291 */                 DisabledAlgorithmConstraints.KeySizeConstraint keySizeConstraint = new DisabledAlgorithmConstraints.KeySizeConstraint(str1, DisabledAlgorithmConstraints.Constraint.Operator.of(stringTokenizer.nextToken()), Integer.parseInt(stringTokenizer.nextToken()));
/*     */               }
/* 293 */               else if (str3.equalsIgnoreCase("jdkCA")) {
/* 294 */                 if (DisabledAlgorithmConstraints.debug != null) {
/* 295 */                   DisabledAlgorithmConstraints.debug.println("Constraints set to jdkCA.");
/*     */                 }
/* 297 */                 if (bool1) {
/* 298 */                   throw new IllegalArgumentException("Only one jdkCA entry allowed in property. Constraint: " + str);
/*     */                 }
/*     */ 
/*     */                 
/* 302 */                 DisabledAlgorithmConstraints.jdkCAConstraint jdkCAConstraint = new DisabledAlgorithmConstraints.jdkCAConstraint(str1);
/* 303 */                 bool1 = true;
/*     */               } else {
/* 305 */                 Matcher matcher; if (str3.startsWith("denyAfter") && (
/* 306 */                   matcher = Holder.DENY_AFTER_PATTERN.matcher(str3))
/* 307 */                   .matches()) {
/* 308 */                   if (DisabledAlgorithmConstraints.debug != null) {
/* 309 */                     DisabledAlgorithmConstraints.debug.println("Constraints set to denyAfter");
/*     */                   }
/* 311 */                   if (bool2) {
/* 312 */                     throw new IllegalArgumentException("Only one denyAfter entry allowed in property. Constraint: " + str);
/*     */                   }
/*     */ 
/*     */                   
/* 316 */                   int j = Integer.parseInt(matcher.group(1));
/* 317 */                   int k = Integer.parseInt(matcher.group(2));
/* 318 */                   int m = Integer.parseInt(matcher.group(3));
/* 319 */                   DisabledAlgorithmConstraints.DenyAfterConstraint denyAfterConstraint = new DisabledAlgorithmConstraints.DenyAfterConstraint(str1, j, k, m);
/*     */                   
/* 321 */                   bool2 = true;
/* 322 */                 } else if (str3.startsWith("usage")) {
/* 323 */                   String[] arrayOfString = str3.substring(5).trim().split(" ");
/* 324 */                   usageConstraint1 = new DisabledAlgorithmConstraints.UsageConstraint(str1, arrayOfString);
/* 325 */                   if (DisabledAlgorithmConstraints.debug != null) {
/* 326 */                     DisabledAlgorithmConstraints.debug.println("Constraints usage length is " + arrayOfString.length);
/*     */                   }
/*     */                 } else {
/* 329 */                   throw new IllegalArgumentException("Error in security property. Constraint unknown: " + str3);
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */               
/* 335 */               if (usageConstraint == null) {
/* 336 */                 list.add(usageConstraint1);
/*     */               } else {
/* 338 */                 usageConstraint.nextConstraint = usageConstraint1;
/*     */               } 
/* 340 */               usageConstraint = usageConstraint1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     private List<DisabledAlgorithmConstraints.Constraint> getConstraints(String param1String) {
/* 347 */       return this.constraintsMap.get(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean permits(Key param1Key) {
/* 352 */       List<DisabledAlgorithmConstraints.Constraint> list = getConstraints(param1Key.getAlgorithm());
/* 353 */       if (list == null) {
/* 354 */         return true;
/*     */       }
/* 356 */       for (DisabledAlgorithmConstraints.Constraint constraint : list) {
/* 357 */         if (!constraint.permits(param1Key)) {
/* 358 */           if (DisabledAlgorithmConstraints.debug != null) {
/* 359 */             DisabledAlgorithmConstraints.debug.println("keySizeConstraint: failed key constraint check " + 
/* 360 */                 KeyUtil.getKeySize(param1Key));
/*     */           }
/* 362 */           return false;
/*     */         } 
/*     */       } 
/* 365 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean permits(String param1String, AlgorithmParameters param1AlgorithmParameters) {
/* 370 */       List<DisabledAlgorithmConstraints.Constraint> list = getConstraints(param1String);
/* 371 */       if (list == null) {
/* 372 */         return true;
/*     */       }
/*     */       
/* 375 */       for (DisabledAlgorithmConstraints.Constraint constraint : list) {
/* 376 */         if (!constraint.permits(param1AlgorithmParameters)) {
/* 377 */           if (DisabledAlgorithmConstraints.debug != null) {
/* 378 */             DisabledAlgorithmConstraints.debug.println("keySizeConstraint: failed algorithm parameters constraint check " + param1AlgorithmParameters);
/*     */           }
/*     */ 
/*     */           
/* 382 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/* 386 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void permits(String param1String, ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 392 */       X509Certificate x509Certificate = param1ConstraintsParameters.getCertificate();
/*     */       
/* 394 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 395 */         DisabledAlgorithmConstraints.debug.println("Constraints.permits(): " + param1String + " Variant: " + param1ConstraintsParameters
/* 396 */             .getVariant());
/*     */       }
/*     */ 
/*     */       
/* 400 */       HashSet<String> hashSet = new HashSet();
/* 401 */       if (param1String != null) {
/* 402 */         hashSet.addAll(AlgorithmDecomposer.decomposeOneHash(param1String));
/*     */       }
/*     */ 
/*     */       
/* 406 */       if (x509Certificate != null) {
/* 407 */         hashSet.add(x509Certificate.getPublicKey().getAlgorithm());
/*     */       }
/* 409 */       if (param1ConstraintsParameters.getPublicKey() != null) {
/* 410 */         hashSet.add(param1ConstraintsParameters.getPublicKey().getAlgorithm());
/*     */       }
/*     */       
/* 413 */       for (String str : hashSet) {
/* 414 */         List<DisabledAlgorithmConstraints.Constraint> list = getConstraints(str);
/* 415 */         if (list == null) {
/*     */           continue;
/*     */         }
/* 418 */         for (DisabledAlgorithmConstraints.Constraint constraint : list) {
/* 419 */           constraint.permits(param1ConstraintsParameters);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class Constraint
/*     */   {
/*     */     String algorithm;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     Constraint nextConstraint = null;
/*     */     
/*     */     enum Operator
/*     */     {
/* 440 */       EQ,
/* 441 */       NE,
/* 442 */       LT,
/* 443 */       LE,
/* 444 */       GT,
/* 445 */       GE;
/*     */       
/*     */       static Operator of(String param2String) {
/* 448 */         switch (param2String) {
/*     */           case "==":
/* 450 */             return EQ;
/*     */           case "!=":
/* 452 */             return NE;
/*     */           case "<":
/* 454 */             return LT;
/*     */           case "<=":
/* 456 */             return LE;
/*     */           case ">":
/* 458 */             return GT;
/*     */           case ">=":
/* 460 */             return GE;
/*     */         } 
/*     */         
/* 463 */         throw new IllegalArgumentException("Error in security property. " + param2String + " is not a legal Operator");
/*     */       }
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
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean permits(Key param1Key) {
/* 479 */       return true;
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
/*     */     public boolean permits(AlgorithmParameters param1AlgorithmParameters) {
/* 491 */       return true;
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
/*     */     boolean next(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 527 */       if (this.nextConstraint != null) {
/* 528 */         this.nextConstraint.permits(param1ConstraintsParameters);
/* 529 */         return true;
/*     */       } 
/* 531 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean next(Key param1Key) {
/* 549 */       if (this.nextConstraint != null && this.nextConstraint.permits(param1Key)) {
/* 550 */         return true;
/*     */       }
/* 552 */       return false;
/*     */     }
/*     */     
/*     */     String extendedMsg(ConstraintsParameters param1ConstraintsParameters) {
/* 556 */       return (param1ConstraintsParameters.getCertificate() == null) ? "." : (" used with certificate: " + param1ConstraintsParameters
/*     */         
/* 558 */         .getCertificate().getSubjectX500Principal() + (
/* 559 */         (param1ConstraintsParameters.getVariant() != "generic") ? (".  Usage was " + param1ConstraintsParameters
/* 560 */         .getVariant()) : "."));
/*     */     }
/*     */     
/*     */     private Constraint() {}
/*     */     
/*     */     public abstract void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException;
/*     */   }
/*     */   
/*     */   private static class jdkCAConstraint extends Constraint {
/*     */     jdkCAConstraint(String param1String) {
/* 570 */       this.algorithm = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 581 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 582 */         DisabledAlgorithmConstraints.debug.println("jdkCAConstraints.permits(): " + this.algorithm);
/*     */       }
/*     */ 
/*     */       
/* 586 */       if (param1ConstraintsParameters.isTrustedMatch()) {
/* 587 */         if (next(param1ConstraintsParameters)) {
/*     */           return;
/*     */         }
/* 590 */         throw new CertPathValidatorException("Algorithm constraints check failed on certificate anchor limits. " + this.algorithm + 
/*     */             
/* 592 */             extendedMsg(param1ConstraintsParameters), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DenyAfterConstraint
/*     */     extends Constraint
/*     */   {
/*     */     private Date denyAfterDate;
/*     */     
/* 604 */     private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d HH:mm:ss z yyyy");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DenyAfterConstraint(String param1String, int param1Int1, int param1Int2, int param1Int3) {
/* 610 */       this.algorithm = param1String;
/*     */       
/* 612 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 613 */         DisabledAlgorithmConstraints.debug.println("DenyAfterConstraint read in as:  year " + param1Int1 + ", month = " + param1Int2 + ", day = " + param1Int3);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 618 */       Calendar calendar = (new Calendar.Builder()).setTimeZone(TimeZone.getTimeZone("GMT")).setDate(param1Int1, param1Int2 - 1, param1Int3).build();
/*     */       
/* 620 */       if (param1Int1 > calendar.getActualMaximum(1) || param1Int1 < calendar
/* 621 */         .getActualMinimum(1)) {
/* 622 */         throw new IllegalArgumentException("Invalid year given in constraint: " + param1Int1);
/*     */       }
/*     */       
/* 625 */       if (param1Int2 - 1 > calendar.getActualMaximum(2) || param1Int2 - 1 < calendar
/* 626 */         .getActualMinimum(2)) {
/* 627 */         throw new IllegalArgumentException("Invalid month given in constraint: " + param1Int2);
/*     */       }
/*     */       
/* 630 */       if (param1Int3 > calendar.getActualMaximum(5) || param1Int3 < calendar
/* 631 */         .getActualMinimum(5)) {
/* 632 */         throw new IllegalArgumentException("Invalid Day of Month given in constraint: " + param1Int3);
/*     */       }
/*     */ 
/*     */       
/* 636 */       this.denyAfterDate = calendar.getTime();
/* 637 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 638 */         DisabledAlgorithmConstraints.debug.println("DenyAfterConstraint date set to: " + dateFormat
/* 639 */             .format(this.denyAfterDate));
/*     */       }
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
/*     */ 
/*     */ 
/*     */     
/*     */     public void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/*     */       Date date;
/*     */       String str;
/* 657 */       if (param1ConstraintsParameters.getJARTimestamp() != null) {
/* 658 */         date = param1ConstraintsParameters.getJARTimestamp().getTimestamp();
/* 659 */         str = "JAR Timestamp date: ";
/* 660 */       } else if (param1ConstraintsParameters.getPKIXParamDate() != null) {
/* 661 */         date = param1ConstraintsParameters.getPKIXParamDate();
/* 662 */         str = "PKIXParameter date: ";
/*     */       } else {
/* 664 */         date = new Date();
/* 665 */         str = "Current date: ";
/*     */       } 
/*     */       
/* 668 */       if (!this.denyAfterDate.after(date)) {
/* 669 */         if (next(param1ConstraintsParameters)) {
/*     */           return;
/*     */         }
/* 672 */         throw new CertPathValidatorException("denyAfter constraint check failed: " + this.algorithm + " used with Constraint date: " + dateFormat
/*     */ 
/*     */             
/* 675 */             .format(this.denyAfterDate) + "; " + str + dateFormat
/* 676 */             .format(date) + extendedMsg(param1ConstraintsParameters), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean permits(Key param1Key) {
/* 687 */       if (next(param1Key)) {
/* 688 */         return true;
/*     */       }
/* 690 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 691 */         DisabledAlgorithmConstraints.debug.println("DenyAfterConstraints.permits(): " + this.algorithm);
/*     */       }
/*     */       
/* 694 */       return this.denyAfterDate.after(new Date());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class UsageConstraint
/*     */     extends Constraint
/*     */   {
/*     */     String[] usages;
/*     */ 
/*     */     
/*     */     UsageConstraint(String param1String, String[] param1ArrayOfString) {
/* 706 */       this.algorithm = param1String;
/* 707 */       this.usages = param1ArrayOfString;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 713 */       for (String str1 : this.usages) {
/*     */         
/* 715 */         String str2 = null;
/* 716 */         if (str1.compareToIgnoreCase("TLSServer") == 0) {
/* 717 */           str2 = "tls server";
/* 718 */         } else if (str1.compareToIgnoreCase("TLSClient") == 0) {
/* 719 */           str2 = "tls client";
/* 720 */         } else if (str1.compareToIgnoreCase("SignedJAR") == 0) {
/* 721 */           str2 = "plugin code signing";
/*     */         } 
/*     */         
/* 724 */         if (DisabledAlgorithmConstraints.debug != null) {
/* 725 */           DisabledAlgorithmConstraints.debug.println("Checking if usage constraint \"" + str2 + "\" matches \"" + param1ConstraintsParameters
/* 726 */               .getVariant() + "\"");
/*     */ 
/*     */           
/* 729 */           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 730 */           PrintStream printStream = new PrintStream(byteArrayOutputStream);
/* 731 */           (new Exception()).printStackTrace(printStream);
/* 732 */           DisabledAlgorithmConstraints.debug.println(byteArrayOutputStream.toString());
/*     */         } 
/* 734 */         if (param1ConstraintsParameters.getVariant().compareTo(str2) == 0) {
/* 735 */           if (next(param1ConstraintsParameters)) {
/*     */             return;
/*     */           }
/* 738 */           throw new CertPathValidatorException("Usage constraint " + str1 + " check failed: " + this.algorithm + 
/*     */               
/* 740 */               extendedMsg(param1ConstraintsParameters), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class KeySizeConstraint
/*     */     extends Constraint
/*     */   {
/*     */     private int minSize;
/*     */     
/*     */     private int maxSize;
/*     */     
/* 755 */     private int prohibitedSize = -1;
/*     */     private int size;
/*     */     
/*     */     public KeySizeConstraint(String param1String, DisabledAlgorithmConstraints.Constraint.Operator param1Operator, int param1Int) {
/* 759 */       this.algorithm = param1String;
/* 760 */       switch (param1Operator) {
/*     */         case EQ:
/* 762 */           this.minSize = 0;
/* 763 */           this.maxSize = Integer.MAX_VALUE;
/* 764 */           this.prohibitedSize = param1Int;
/*     */           return;
/*     */         case NE:
/* 767 */           this.minSize = param1Int;
/* 768 */           this.maxSize = param1Int;
/*     */           return;
/*     */         case LT:
/* 771 */           this.minSize = param1Int;
/* 772 */           this.maxSize = Integer.MAX_VALUE;
/*     */           return;
/*     */         case LE:
/* 775 */           this.minSize = param1Int + 1;
/* 776 */           this.maxSize = Integer.MAX_VALUE;
/*     */           return;
/*     */         case GT:
/* 779 */           this.minSize = 0;
/* 780 */           this.maxSize = param1Int;
/*     */           return;
/*     */         case GE:
/* 783 */           this.minSize = 0;
/* 784 */           this.maxSize = (param1Int > 1) ? (param1Int - 1) : 0;
/*     */           return;
/*     */       } 
/*     */       
/* 788 */       this.minSize = Integer.MAX_VALUE;
/* 789 */       this.maxSize = -1;
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
/*     */ 
/*     */     
/*     */     public void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 803 */       Key key = null;
/* 804 */       if (param1ConstraintsParameters.getPublicKey() != null) {
/* 805 */         key = param1ConstraintsParameters.getPublicKey();
/* 806 */       } else if (param1ConstraintsParameters.getCertificate() != null) {
/* 807 */         key = param1ConstraintsParameters.getCertificate().getPublicKey();
/*     */       } 
/* 809 */       if (key != null && !permitsImpl(key)) {
/* 810 */         if (this.nextConstraint != null) {
/* 811 */           this.nextConstraint.permits(param1ConstraintsParameters);
/*     */           return;
/*     */         } 
/* 814 */         throw new CertPathValidatorException("Algorithm constraints check failed on keysize limits. " + this.algorithm + " " + 
/*     */             
/* 816 */             KeyUtil.getKeySize(key) + "bit key" + 
/* 817 */             extendedMsg(param1ConstraintsParameters), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean permits(Key param1Key) {
/* 829 */       if (this.nextConstraint != null && this.nextConstraint.permits(param1Key)) {
/* 830 */         return true;
/*     */       }
/* 832 */       if (DisabledAlgorithmConstraints.debug != null) {
/* 833 */         DisabledAlgorithmConstraints.debug.println("KeySizeConstraints.permits(): " + this.algorithm);
/*     */       }
/*     */       
/* 836 */       return permitsImpl(param1Key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean permits(AlgorithmParameters param1AlgorithmParameters) {
/* 841 */       String str = param1AlgorithmParameters.getAlgorithm();
/* 842 */       if (!this.algorithm.equalsIgnoreCase(param1AlgorithmParameters.getAlgorithm())) {
/*     */ 
/*     */         
/* 845 */         Collection<String> collection = AlgorithmDecomposer.getAliases(this.algorithm);
/* 846 */         if (!collection.contains(str)) {
/* 847 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 851 */       int i = KeyUtil.getKeySize(param1AlgorithmParameters);
/* 852 */       if (i == 0)
/* 853 */         return false; 
/* 854 */       if (i > 0) {
/* 855 */         return (i >= this.minSize && i <= this.maxSize && this.prohibitedSize != i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 860 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean permitsImpl(Key param1Key) {
/* 865 */       if (this.algorithm.compareToIgnoreCase(param1Key.getAlgorithm()) != 0) {
/* 866 */         return true;
/*     */       }
/*     */       
/* 869 */       this.size = KeyUtil.getKeySize(param1Key);
/* 870 */       if (this.size == 0)
/* 871 */         return false; 
/* 872 */       if (this.size > 0) {
/* 873 */         return (this.size >= this.minSize && this.size <= this.maxSize && this.prohibitedSize != this.size);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 878 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DisabledConstraint
/*     */     extends Constraint
/*     */   {
/*     */     DisabledConstraint(String param1String) {
/* 887 */       this.algorithm = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void permits(ConstraintsParameters param1ConstraintsParameters) throws CertPathValidatorException {
/* 893 */       throw new CertPathValidatorException("Algorithm constraints check failed on disabled algorithm: " + this.algorithm + 
/*     */           
/* 895 */           extendedMsg(param1ConstraintsParameters), null, null, -1, CertPathValidatorException.BasicReason.ALGORITHM_CONSTRAINED);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean permits(Key param1Key) {
/* 901 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DisabledAlgorithmConstraints.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */