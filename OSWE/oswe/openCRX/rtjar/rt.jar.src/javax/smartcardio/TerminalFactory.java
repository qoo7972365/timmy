/*     */ package javax.smartcardio;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.NoSuchProviderException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ import sun.security.jca.GetInstance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TerminalFactory
/*     */ {
/*     */   private static final String PROP_NAME = "javax.smartcardio.TerminalFactory.DefaultType";
/*     */   private static final String defaultType;
/*     */   private static final TerminalFactory defaultFactory;
/*     */   private final TerminalFactorySpi spi;
/*     */   private final Provider provider;
/*     */   private final String type;
/*     */   
/*     */   static {
/* 103 */     String str = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("javax.smartcardio.TerminalFactory.DefaultType", "PC/SC"))).trim();
/* 104 */     TerminalFactory terminalFactory = null;
/*     */     try {
/* 106 */       terminalFactory = getInstance(str, null);
/* 107 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 110 */     if (terminalFactory == null) {
/*     */       
/*     */       try {
/* 113 */         str = "PC/SC";
/* 114 */         Provider provider = Security.getProvider("SunPCSC");
/* 115 */         if (provider == null) {
/* 116 */           Class<?> clazz = Class.forName("sun.security.smartcardio.SunPCSC");
/* 117 */           provider = (Provider)clazz.newInstance();
/*     */         } 
/* 119 */         terminalFactory = getInstance(str, (Object)null, provider);
/* 120 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 124 */     if (terminalFactory == null) {
/* 125 */       str = "None";
/* 126 */       terminalFactory = new TerminalFactory(NoneFactorySpi.INSTANCE, NoneProvider.INSTANCE, "None");
/*     */     } 
/*     */     
/* 129 */     defaultType = str;
/* 130 */     defaultFactory = terminalFactory;
/*     */   }
/*     */   
/*     */   private static final class NoneProvider
/*     */     extends Provider {
/*     */     private static final long serialVersionUID = 2745808869881593918L;
/* 136 */     static final Provider INSTANCE = new NoneProvider();
/*     */     private NoneProvider() {
/* 138 */       super("None", 1.0D, "none");
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class NoneFactorySpi extends TerminalFactorySpi {
/* 143 */     static final TerminalFactorySpi INSTANCE = new NoneFactorySpi();
/*     */ 
/*     */ 
/*     */     
/*     */     protected CardTerminals engineTerminals() {
/* 148 */       return TerminalFactory.NoneCardTerminals.INSTANCE;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class NoneCardTerminals extends CardTerminals {
/* 153 */     static final CardTerminals INSTANCE = new NoneCardTerminals();
/*     */ 
/*     */ 
/*     */     
/*     */     public List<CardTerminal> list(CardTerminals.State param1State) throws CardException {
/* 158 */       if (param1State == null) {
/* 159 */         throw new NullPointerException();
/*     */       }
/* 161 */       return Collections.emptyList();
/*     */     }
/*     */     public boolean waitForChange(long param1Long) throws CardException {
/* 164 */       throw new IllegalStateException("no terminals");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TerminalFactory(TerminalFactorySpi paramTerminalFactorySpi, Provider paramProvider, String paramString) {
/* 175 */     this.spi = paramTerminalFactorySpi;
/* 176 */     this.provider = paramProvider;
/* 177 */     this.type = paramString;
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
/*     */   public static String getDefaultType() {
/* 201 */     return defaultType;
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
/*     */   public static TerminalFactory getDefault() {
/* 214 */     return defaultFactory;
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
/*     */   public static TerminalFactory getInstance(String paramString, Object paramObject) throws NoSuchAlgorithmException {
/* 245 */     GetInstance.Instance instance = GetInstance.getInstance("TerminalFactory", TerminalFactorySpi.class, paramString, paramObject);
/*     */     
/* 247 */     return new TerminalFactory((TerminalFactorySpi)instance.impl, instance.provider, paramString);
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
/*     */   public static TerminalFactory getInstance(String paramString1, Object paramObject, String paramString2) throws NoSuchAlgorithmException, NoSuchProviderException {
/* 284 */     GetInstance.Instance instance = GetInstance.getInstance("TerminalFactory", TerminalFactorySpi.class, paramString1, paramObject, paramString2);
/*     */     
/* 286 */     return new TerminalFactory((TerminalFactorySpi)instance.impl, instance.provider, paramString1);
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
/*     */   public static TerminalFactory getInstance(String paramString, Object paramObject, Provider paramProvider) throws NoSuchAlgorithmException {
/* 316 */     GetInstance.Instance instance = GetInstance.getInstance("TerminalFactory", TerminalFactorySpi.class, paramString, paramObject, paramProvider);
/*     */     
/* 318 */     return new TerminalFactory((TerminalFactorySpi)instance.impl, instance.provider, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Provider getProvider() {
/* 328 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 338 */     return this.type;
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
/*     */   public CardTerminals terminals() {
/* 351 */     return this.spi.engineTerminals();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 360 */     return "TerminalFactory for type " + this.type + " from provider " + this.provider
/* 361 */       .getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/smartcardio/TerminalFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */