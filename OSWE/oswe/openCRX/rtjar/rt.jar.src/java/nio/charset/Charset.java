/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.spi.CharsetProvider;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.ServiceConfigurationError;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import sun.misc.ASCIICaseInsensitiveComparator;
/*     */ import sun.misc.VM;
/*     */ import sun.nio.cs.StandardCharsets;
/*     */ import sun.nio.cs.ThreadLocalCoders;
/*     */ import sun.security.action.GetPropertyAction;
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
/*     */ public abstract class Charset
/*     */   implements Comparable<Charset>
/*     */ {
/* 277 */   private static volatile String bugLevel = null;
/*     */   
/*     */   static boolean atBugLevel(String paramString) {
/* 280 */     String str = bugLevel;
/* 281 */     if (str == null) {
/* 282 */       if (!VM.isBooted())
/* 283 */         return false; 
/* 284 */       bugLevel = str = AccessController.doPrivileged(new GetPropertyAction("sun.nio.cs.bugLevel", ""));
/*     */     } 
/*     */     
/* 287 */     return str.equals(paramString);
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
/*     */   private static void checkName(String paramString) {
/* 300 */     int i = paramString.length();
/* 301 */     if (!atBugLevel("1.4") && 
/* 302 */       i == 0) {
/* 303 */       throw new IllegalCharsetNameException(paramString);
/*     */     }
/* 305 */     for (byte b = 0; b < i; ) {
/* 306 */       char c = paramString.charAt(b);
/* 307 */       if ((c >= 'A' && c <= 'Z') || (
/* 308 */         c >= 'a' && c <= 'z') || (
/* 309 */         c >= '0' && c <= '9') || (
/* 310 */         c == '-' && b != 0) || (
/* 311 */         c == '+' && b != 0) || (
/* 312 */         c == ':' && b != 0) || (
/* 313 */         c == '_' && b != 0) || (
/* 314 */         c == '.' && b != 0)) { b++; continue; }
/* 315 */        throw new IllegalCharsetNameException(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 320 */   private static CharsetProvider standardProvider = new StandardCharsets();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 325 */   private static volatile Object[] cache1 = null;
/* 326 */   private static volatile Object[] cache2 = null;
/*     */   
/*     */   private static void cache(String paramString, Charset paramCharset) {
/* 329 */     cache2 = cache1;
/* 330 */     cache1 = new Object[] { paramString, paramCharset };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterator<CharsetProvider> providers() {
/* 338 */     return new Iterator<CharsetProvider>()
/*     */       {
/* 340 */         ClassLoader cl = ClassLoader.getSystemClassLoader();
/*     */         
/* 342 */         ServiceLoader<CharsetProvider> sl = ServiceLoader.load(CharsetProvider.class, this.cl);
/* 343 */         Iterator<CharsetProvider> i = this.sl.iterator();
/*     */         
/* 345 */         CharsetProvider next = null;
/*     */         
/*     */         private boolean getNext() {
/* 348 */           while (this.next == null) {
/*     */             try {
/* 350 */               if (!this.i.hasNext())
/* 351 */                 return false; 
/* 352 */               this.next = this.i.next();
/* 353 */             } catch (ServiceConfigurationError serviceConfigurationError) {
/* 354 */               if (serviceConfigurationError.getCause() instanceof SecurityException) {
/*     */                 continue;
/*     */               }
/*     */               
/* 358 */               throw serviceConfigurationError;
/*     */             } 
/*     */           } 
/* 361 */           return true;
/*     */         }
/*     */         
/*     */         public boolean hasNext() {
/* 365 */           return getNext();
/*     */         }
/*     */         
/*     */         public CharsetProvider next() {
/* 369 */           if (!getNext())
/* 370 */             throw new NoSuchElementException(); 
/* 371 */           CharsetProvider charsetProvider = this.next;
/* 372 */           this.next = null;
/* 373 */           return charsetProvider;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 377 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 384 */   private static ThreadLocal<ThreadLocal<?>> gate = new ThreadLocal<>();
/*     */ 
/*     */   
/*     */   private static volatile Charset defaultCharset;
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */   
/*     */   private final String[] aliases;
/*     */ 
/*     */   
/*     */   private static Charset lookupViaProviders(final String charsetName) {
/* 397 */     if (!VM.isBooted()) {
/* 398 */       return null;
/*     */     }
/* 400 */     if (gate.get() != null)
/*     */     {
/* 402 */       return null; } 
/*     */     try {
/* 404 */       gate.set(gate);
/*     */       
/* 406 */       return AccessController.<Charset>doPrivileged(new PrivilegedAction<Charset>()
/*     */           {
/*     */             public Charset run() {
/* 409 */               Iterator<CharsetProvider> iterator = Charset.providers();
/* 410 */               while (iterator.hasNext()) {
/* 411 */                 CharsetProvider charsetProvider = iterator.next();
/* 412 */                 Charset charset = charsetProvider.charsetForName(charsetName);
/* 413 */                 if (charset != null)
/* 414 */                   return charset; 
/*     */               } 
/* 416 */               return null;
/*     */             }
/*     */           });
/*     */     } finally {
/*     */       
/* 421 */       gate.set(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ExtendedProviderHolder
/*     */   {
/* 427 */     static final CharsetProvider extendedProvider = extendedProvider();
/*     */     
/*     */     private static CharsetProvider extendedProvider() {
/* 430 */       return AccessController.<CharsetProvider>doPrivileged(new PrivilegedAction<CharsetProvider>()
/*     */           {
/*     */             public CharsetProvider run()
/*     */             {
/*     */               try {
/* 435 */                 Class<?> clazz = Class.forName("sun.nio.cs.ext.ExtendedCharsets");
/* 436 */                 return (CharsetProvider)clazz.newInstance();
/* 437 */               } catch (ClassNotFoundException classNotFoundException) {
/*     */ 
/*     */               
/* 440 */               } catch (InstantiationException|IllegalAccessException instantiationException) {
/*     */                 
/* 442 */                 throw new Error(instantiationException);
/*     */               } 
/* 444 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private static Charset lookupExtendedCharset(String paramString) {
/* 451 */     CharsetProvider charsetProvider = ExtendedProviderHolder.extendedProvider;
/* 452 */     return (charsetProvider != null) ? charsetProvider.charsetForName(paramString) : null;
/*     */   }
/*     */   
/*     */   private static Charset lookup(String paramString) {
/* 456 */     if (paramString == null)
/* 457 */       throw new IllegalArgumentException("Null charset name"); 
/*     */     Object[] arrayOfObject;
/* 459 */     if ((arrayOfObject = cache1) != null && paramString.equals(arrayOfObject[0])) {
/* 460 */       return (Charset)arrayOfObject[1];
/*     */     }
/*     */ 
/*     */     
/* 464 */     return lookup2(paramString);
/*     */   }
/*     */   
/*     */   private static Charset lookup2(String paramString) {
/*     */     Object[] arrayOfObject;
/* 469 */     if ((arrayOfObject = cache2) != null && paramString.equals(arrayOfObject[0])) {
/* 470 */       cache2 = cache1;
/* 471 */       cache1 = arrayOfObject;
/* 472 */       return (Charset)arrayOfObject[1];
/*     */     } 
/*     */     Charset charset;
/* 475 */     if ((charset = standardProvider.charsetForName(paramString)) != null || (
/* 476 */       charset = lookupExtendedCharset(paramString)) != null || (
/* 477 */       charset = lookupViaProviders(paramString)) != null) {
/*     */       
/* 479 */       cache(paramString, charset);
/* 480 */       return charset;
/*     */     } 
/*     */ 
/*     */     
/* 484 */     checkName(paramString);
/* 485 */     return null;
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
/*     */   public static boolean isSupported(String paramString) {
/* 505 */     return (lookup(paramString) != null);
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
/*     */   
/*     */   public static Charset forName(String paramString) {
/* 528 */     Charset charset = lookup(paramString);
/* 529 */     if (charset != null)
/* 530 */       return charset; 
/* 531 */     throw new UnsupportedCharsetException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void put(Iterator<Charset> paramIterator, Map<String, Charset> paramMap) {
/* 538 */     while (paramIterator.hasNext()) {
/* 539 */       Charset charset = paramIterator.next();
/* 540 */       if (!paramMap.containsKey(charset.name())) {
/* 541 */         paramMap.put(charset.name(), charset);
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SortedMap<String, Charset> availableCharsets() {
/* 572 */     return AccessController.<SortedMap<String, Charset>>doPrivileged(new PrivilegedAction<SortedMap<String, Charset>>()
/*     */         {
/*     */           public SortedMap<String, Charset> run() {
/* 575 */             TreeMap<Object, Object> treeMap = new TreeMap<>(ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */             
/* 578 */             Charset.put(Charset.standardProvider.charsets(), (Map)treeMap);
/* 579 */             CharsetProvider charsetProvider = Charset.ExtendedProviderHolder.extendedProvider;
/* 580 */             if (charsetProvider != null)
/* 581 */               Charset.put(charsetProvider.charsets(), (Map)treeMap); 
/* 582 */             for (Iterator<CharsetProvider> iterator = Charset.providers(); iterator.hasNext(); ) {
/* 583 */               CharsetProvider charsetProvider1 = iterator.next();
/* 584 */               Charset.put(charsetProvider1.charsets(), (Map)treeMap);
/*     */             } 
/* 586 */             return (SortedMap)Collections.unmodifiableSortedMap(treeMap);
/*     */           }
/*     */         });
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
/*     */   public static Charset defaultCharset() {
/* 605 */     if (defaultCharset == null)
/* 606 */       synchronized (Charset.class) {
/* 607 */         String str = AccessController.<String>doPrivileged(new GetPropertyAction("file.encoding"));
/*     */         
/* 609 */         Charset charset = lookup(str);
/* 610 */         if (charset != null) {
/* 611 */           defaultCharset = charset;
/*     */         } else {
/* 613 */           defaultCharset = forName("UTF-8");
/*     */         } 
/*     */       }  
/* 616 */     return defaultCharset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 624 */   private Set<String> aliasSet = null;
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
/*     */   protected Charset(String paramString, String[] paramArrayOfString) {
/* 640 */     checkName(paramString);
/* 641 */     String[] arrayOfString = (paramArrayOfString == null) ? new String[0] : paramArrayOfString;
/* 642 */     for (byte b = 0; b < arrayOfString.length; b++)
/* 643 */       checkName(arrayOfString[b]); 
/* 644 */     this.name = paramString;
/* 645 */     this.aliases = arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String name() {
/* 654 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Set<String> aliases() {
/* 663 */     if (this.aliasSet != null)
/* 664 */       return this.aliasSet; 
/* 665 */     int i = this.aliases.length;
/* 666 */     HashSet<String> hashSet = new HashSet(i);
/* 667 */     for (byte b = 0; b < i; b++)
/* 668 */       hashSet.add(this.aliases[b]); 
/* 669 */     this.aliasSet = Collections.unmodifiableSet(hashSet);
/* 670 */     return this.aliasSet;
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
/*     */   public String displayName() {
/* 683 */     return this.name;
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
/*     */   public final boolean isRegistered() {
/* 695 */     return (!this.name.startsWith("X-") && !this.name.startsWith("x-"));
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
/*     */   public String displayName(Locale paramLocale) {
/* 711 */     return this.name;
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
/*     */   public boolean canEncode() {
/* 774 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CharBuffer decode(ByteBuffer paramByteBuffer) {
/*     */     try {
/* 804 */       return ThreadLocalCoders.decoderFor(this)
/* 805 */         .onMalformedInput(CodingErrorAction.REPLACE)
/* 806 */         .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 807 */         .decode(paramByteBuffer);
/* 808 */     } catch (CharacterCodingException characterCodingException) {
/* 809 */       throw new Error(characterCodingException);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBuffer encode(CharBuffer paramCharBuffer) {
/*     */     try {
/* 840 */       return ThreadLocalCoders.encoderFor(this)
/* 841 */         .onMalformedInput(CodingErrorAction.REPLACE)
/* 842 */         .onUnmappableCharacter(CodingErrorAction.REPLACE)
/* 843 */         .encode(paramCharBuffer);
/* 844 */     } catch (CharacterCodingException characterCodingException) {
/* 845 */       throw new Error(characterCodingException);
/*     */     } 
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
/*     */   public final ByteBuffer encode(String paramString) {
/* 863 */     return encode(CharBuffer.wrap(paramString));
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
/*     */   public final int compareTo(Charset paramCharset) {
/* 879 */     return name().compareToIgnoreCase(paramCharset.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 888 */     return name().hashCode();
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
/*     */   public final boolean equals(Object paramObject) {
/* 901 */     if (!(paramObject instanceof Charset))
/* 902 */       return false; 
/* 903 */     if (this == paramObject)
/* 904 */       return true; 
/* 905 */     return this.name.equals(((Charset)paramObject).name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 914 */     return name();
/*     */   }
/*     */   
/*     */   public abstract boolean contains(Charset paramCharset);
/*     */   
/*     */   public abstract CharsetDecoder newDecoder();
/*     */   
/*     */   public abstract CharsetEncoder newEncoder();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/charset/Charset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */