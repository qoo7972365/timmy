/*     */ package sun.management.jmxremote;
/*     */ 
/*     */ import java.rmi.AccessException;
/*     */ import java.rmi.NotBoundException;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.server.RMIClientSocketFactory;
/*     */ import java.rmi.server.RMIServerSocketFactory;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.registry.RegistryImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingleEntryRegistry
/*     */   extends RegistryImpl
/*     */ {
/*     */   private final String name;
/*     */   private final Remote object;
/*     */   private static final long serialVersionUID = -4897238949499730950L;
/*     */   
/*     */   SingleEntryRegistry(int paramInt, String paramString, Remote paramRemote) throws RemoteException {
/*  49 */     super(paramInt, null, null, SingleEntryRegistry::singleRegistryFilter);
/*  50 */     this.name = paramString;
/*  51 */     this.object = paramRemote;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SingleEntryRegistry(int paramInt, RMIClientSocketFactory paramRMIClientSocketFactory, RMIServerSocketFactory paramRMIServerSocketFactory, String paramString, Remote paramRemote) throws RemoteException {
/*  60 */     super(paramInt, paramRMIClientSocketFactory, paramRMIServerSocketFactory, SingleEntryRegistry::singleRegistryFilter);
/*  61 */     this.name = paramString;
/*  62 */     this.object = paramRemote;
/*     */   }
/*     */   
/*     */   public String[] list() {
/*  66 */     return new String[] { this.name };
/*     */   }
/*     */   
/*     */   public Remote lookup(String paramString) throws NotBoundException {
/*  70 */     if (paramString.equals(this.name))
/*  71 */       return this.object; 
/*  72 */     throw new NotBoundException("Not bound: \"" + paramString + "\" (only bound name is \"" + this.name + "\")");
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(String paramString, Remote paramRemote) throws AccessException {
/*  77 */     throw new AccessException("Cannot modify this registry");
/*     */   }
/*     */   
/*     */   public void rebind(String paramString, Remote paramRemote) throws AccessException {
/*  81 */     throw new AccessException("Cannot modify this registry");
/*     */   }
/*     */   
/*     */   public void unbind(String paramString) throws AccessException {
/*  85 */     throw new AccessException("Cannot modify this registry");
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
/*     */   private static ObjectInputFilter.Status singleRegistryFilter(ObjectInputFilter.FilterInfo paramFilterInfo) {
/*  97 */     return (paramFilterInfo.serialClass() != null || paramFilterInfo
/*  98 */       .depth() > 2L || paramFilterInfo
/*  99 */       .references() > 4L || paramFilterInfo
/* 100 */       .arrayLength() >= 0L) ? ObjectInputFilter.Status.REJECTED : ObjectInputFilter.Status.ALLOWED;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jmxremote/SingleEntryRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */