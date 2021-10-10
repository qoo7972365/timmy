/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.rmi.MarshalledObject;
/*     */ import java.util.Arrays;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ActivationGroupDesc
/*     */   implements Serializable
/*     */ {
/*     */   private String className;
/*     */   private String location;
/*     */   private MarshalledObject<?> data;
/*     */   private CommandEnvironment env;
/*     */   private Properties props;
/*     */   private static final long serialVersionUID = -4936225423168276595L;
/*     */   
/*     */   public ActivationGroupDesc(Properties paramProperties, CommandEnvironment paramCommandEnvironment) {
/* 113 */     this(null, null, null, paramProperties, paramCommandEnvironment);
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
/*     */   public ActivationGroupDesc(String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject, Properties paramProperties, CommandEnvironment paramCommandEnvironment) {
/* 140 */     this.props = paramProperties;
/* 141 */     this.env = paramCommandEnvironment;
/* 142 */     this.data = paramMarshalledObject;
/* 143 */     this.location = paramString2;
/* 144 */     this.className = paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName() {
/* 155 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocation() {
/* 164 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MarshalledObject<?> getData() {
/* 173 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties getPropertyOverrides() {
/* 182 */     return (this.props != null) ? (Properties)this.props.clone() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandEnvironment getCommandEnvironment() {
/* 191 */     return this.env;
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
/*     */   public static class CommandEnvironment
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 6165754737887770191L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String command;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String[] options;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CommandEnvironment(String param1String, String[] param1ArrayOfString) {
/* 233 */       this.command = param1String;
/*     */ 
/*     */       
/* 236 */       if (param1ArrayOfString == null) {
/* 237 */         this.options = new String[0];
/*     */       } else {
/* 239 */         this.options = new String[param1ArrayOfString.length];
/* 240 */         System.arraycopy(param1ArrayOfString, 0, this.options, 0, param1ArrayOfString.length);
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
/*     */     public String getCommandPath() {
/* 252 */       return this.command;
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
/*     */     public String[] getCommandOptions() {
/* 266 */       return (String[])this.options.clone();
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
/*     */     public boolean equals(Object param1Object) {
/* 279 */       if (param1Object instanceof CommandEnvironment) {
/* 280 */         CommandEnvironment commandEnvironment = (CommandEnvironment)param1Object;
/* 281 */         if ((this.command == null) ? (commandEnvironment.command == null) : this.command
/*     */           
/* 283 */           .equals(commandEnvironment.command))
/* 284 */           if (Arrays.equals((Object[])this.options, (Object[])commandEnvironment.options));  return false;
/*     */       } 
/* 286 */       return false;
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
/*     */     public int hashCode() {
/* 299 */       return (this.command == null) ? 0 : this.command.hashCode();
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
/*     */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 316 */       param1ObjectInputStream.defaultReadObject();
/* 317 */       if (this.options == null) {
/* 318 */         this.options = new String[0];
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
/*     */   public boolean equals(Object paramObject) {
/* 333 */     if (paramObject instanceof ActivationGroupDesc) {
/* 334 */       ActivationGroupDesc activationGroupDesc = (ActivationGroupDesc)paramObject;
/* 335 */       if ((this.className == null) ? (activationGroupDesc.className == null) : this.className
/*     */         
/* 337 */         .equals(activationGroupDesc.className)) if ((this.location == null) ? (activationGroupDesc.location == null) : this.location
/*     */           
/* 339 */           .equals(activationGroupDesc.location)) if (((this.data == null) ? (activationGroupDesc.data == null) : this.data
/* 340 */             .equals(activationGroupDesc.data)) && ((this.env == null) ? (activationGroupDesc.env == null) : this.env
/* 341 */             .equals(activationGroupDesc.env)) && ((this.props == null) ? (activationGroupDesc.props == null) : this.props
/*     */             
/* 343 */             .equals(activationGroupDesc.props)));   return false;
/*     */     } 
/* 345 */     return false;
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
/*     */   public int hashCode() {
/* 357 */     return ((this.location == null) ? 0 : (this.location
/*     */       
/* 359 */       .hashCode() << 24)) ^ ((this.env == null) ? 0 : (this.env
/*     */ 
/*     */       
/* 362 */       .hashCode() << 16)) ^ ((this.className == null) ? 0 : (this.className
/*     */ 
/*     */       
/* 365 */       .hashCode() << 8)) ^ ((this.data == null) ? 0 : this.data
/*     */ 
/*     */       
/* 368 */       .hashCode());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/rmi/activation/ActivationGroupDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */