/*     */ package sun.management.jdp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdpJmxPacket
/*     */   extends JdpGenericPacket
/*     */   implements JdpPacket
/*     */ {
/*     */   public static final String UUID_KEY = "DISCOVERABLE_SESSION_UUID";
/*     */   public static final String MAIN_CLASS_KEY = "MAIN_CLASS";
/*     */   public static final String JMX_SERVICE_URL_KEY = "JMX_SERVICE_URL";
/*     */   public static final String INSTANCE_NAME_KEY = "INSTANCE_NAME";
/*     */   public static final String PROCESS_ID_KEY = "PROCESS_ID";
/*     */   public static final String RMI_HOSTNAME_KEY = "RMI_HOSTNAME";
/*     */   public static final String BROADCAST_INTERVAL_KEY = "BROADCAST_INTERVAL";
/*     */   private UUID id;
/*     */   private String mainClass;
/*     */   private String jmxServiceUrl;
/*     */   private String instanceName;
/*     */   private String processId;
/*     */   private String rmiHostname;
/*     */   private String broadcastInterval;
/*     */   
/*     */   public JdpJmxPacket(UUID paramUUID, String paramString) {
/*  96 */     this.id = paramUUID;
/*  97 */     this.jmxServiceUrl = paramString;
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
/*     */   public JdpJmxPacket(byte[] paramArrayOfbyte) throws JdpException {
/* 110 */     JdpPacketReader jdpPacketReader = new JdpPacketReader(paramArrayOfbyte);
/* 111 */     Map<String, String> map = jdpPacketReader.getDiscoveryDataAsMap();
/*     */     
/* 113 */     String str = map.get("DISCOVERABLE_SESSION_UUID");
/* 114 */     this.id = (str == null) ? null : UUID.fromString(str);
/* 115 */     this.jmxServiceUrl = map.get("JMX_SERVICE_URL");
/* 116 */     this.mainClass = map.get("MAIN_CLASS");
/* 117 */     this.instanceName = map.get("INSTANCE_NAME");
/* 118 */     this.processId = map.get("PROCESS_ID");
/* 119 */     this.rmiHostname = map.get("RMI_HOSTNAME");
/* 120 */     this.broadcastInterval = map.get("BROADCAST_INTERVAL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMainClass(String paramString) {
/* 129 */     this.mainClass = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInstanceName(String paramString) {
/* 138 */     this.instanceName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getId() {
/* 145 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMainClass() {
/* 153 */     return this.mainClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJmxServiceUrl() {
/* 161 */     return this.jmxServiceUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInstanceName() {
/* 169 */     return this.instanceName;
/*     */   }
/*     */   
/*     */   public String getProcessId() {
/* 173 */     return this.processId;
/*     */   }
/*     */   
/*     */   public void setProcessId(String paramString) {
/* 177 */     this.processId = paramString;
/*     */   }
/*     */   
/*     */   public String getRmiHostname() {
/* 181 */     return this.rmiHostname;
/*     */   }
/*     */   
/*     */   public void setRmiHostname(String paramString) {
/* 185 */     this.rmiHostname = paramString;
/*     */   }
/*     */   
/*     */   public String getBroadcastInterval() {
/* 189 */     return this.broadcastInterval;
/*     */   }
/*     */   
/*     */   public void setBroadcastInterval(String paramString) {
/* 193 */     this.broadcastInterval = paramString;
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
/*     */   public byte[] getPacketData() throws IOException {
/* 205 */     JdpPacketWriter jdpPacketWriter = new JdpPacketWriter();
/* 206 */     jdpPacketWriter.addEntry("DISCOVERABLE_SESSION_UUID", (this.id == null) ? null : this.id.toString());
/* 207 */     jdpPacketWriter.addEntry("MAIN_CLASS", this.mainClass);
/* 208 */     jdpPacketWriter.addEntry("JMX_SERVICE_URL", this.jmxServiceUrl);
/* 209 */     jdpPacketWriter.addEntry("INSTANCE_NAME", this.instanceName);
/* 210 */     jdpPacketWriter.addEntry("PROCESS_ID", this.processId);
/* 211 */     jdpPacketWriter.addEntry("RMI_HOSTNAME", this.rmiHostname);
/* 212 */     jdpPacketWriter.addEntry("BROADCAST_INTERVAL", this.broadcastInterval);
/*     */     
/* 214 */     return jdpPacketWriter.getPacketBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 223 */     int i = 1;
/* 224 */     i = i * 31 + this.id.hashCode();
/* 225 */     i = i * 31 + this.jmxServiceUrl.hashCode();
/* 226 */     return i;
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
/*     */   public boolean equals(Object paramObject) {
/* 238 */     if (paramObject == null || !(paramObject instanceof JdpJmxPacket)) {
/* 239 */       return false;
/*     */     }
/*     */     
/* 242 */     JdpJmxPacket jdpJmxPacket = (JdpJmxPacket)paramObject;
/* 243 */     return (Objects.equals(this.id, jdpJmxPacket.getId()) && Objects.equals(this.jmxServiceUrl, jdpJmxPacket.getJmxServiceUrl()));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jdp/JdpJmxPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */