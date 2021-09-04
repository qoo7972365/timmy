/*     */ package com.adventnet.appmanager.struts.form;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ 
/*     */ public class ProxyConfiguration extends org.apache.struts.action.ActionForm
/*     */ {
/*  17 */   private String host = "";
/*  18 */   private String port = "";
/*  19 */   private String userid = null;
/*  20 */   private String password = null;
/*  21 */   private String loginid = null;
/*     */   
/*  23 */   private boolean useproxy = false;
/*  24 */   private boolean bypassproxy = true;
/*  25 */   private String dontProxyfor = "127.0.0.1;";
/*     */   
/*     */ 
/*     */ 
/*     */   public String getLoginId()
/*     */   {
/*  31 */     return this.loginid;
/*     */   }
/*     */   
/*     */   public void setLoginId(String loginId)
/*     */   {
/*  36 */     this.loginid = loginId;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  40 */     return this.password;
/*     */   }
/*     */   
/*     */   public void setPassword(String password) {
/*  44 */     this.password = password;
/*     */   }
/*     */   
/*     */ 
/*     */   public void reset(ActionMapping mapping, HttpServletRequest request)
/*     */   {
/*  50 */     ManagedApplication mo = new ManagedApplication();
/*  51 */     HttpSession session = request.getSession(true);
/*  52 */     this.loginid = ((String)session.getAttribute("userName"));
/*  53 */     Properties props = mo.getProxyProps(this.loginid);
/*  54 */     if (props != null)
/*     */     {
/*  56 */       this.host = props.getProperty("host");
/*  57 */       this.port = props.getProperty("port");
/*  58 */       this.userid = props.getProperty("username");
/*  59 */       request.setAttribute("host", this.host);
/*  60 */       request.setAttribute("userid", this.userid);
/*     */     }
/*     */     
/*  63 */     ArrayList rows = mo.getRows("select * from AM_GLOBALCONFIG where NAME='bypassproxyforlocal' OR NAME='useproxy' OR NAME='bypassproxyaddress'");
/*  64 */     for (int i = 0; i < rows.size(); i++)
/*     */     {
/*  66 */       ArrayList row = new ArrayList(3);
/*  67 */       row = (ArrayList)rows.get(i);
/*  68 */       String name = (String)row.get(0);
/*  69 */       String value = (String)row.get(1);
/*  70 */       if (name.equals("useproxy"))
/*     */       {
/*  72 */         this.useproxy = value.equals("true");
/*  73 */       } else if (name.equals("bypassproxyforlocal"))
/*     */       {
/*  75 */         this.bypassproxy = value.equals("true");
/*     */       }
/*  77 */       else if (name.equals("bypassproxyaddress"))
/*     */       {
/*  79 */         this.dontProxyfor = value;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String getUserId()
/*     */   {
/*  86 */     return this.userid;
/*     */   }
/*     */   
/*     */   public void setUserId(String userid) {
/*  90 */     this.userid = userid;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  94 */     return this.host;
/*     */   }
/*     */   
/*     */   public void setHost(String host) {
/*  98 */     this.host = host;
/*     */   }
/*     */   
/*     */   public String getPort() {
/* 102 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setPort(String port) {
/* 106 */     this.port = port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
/*     */   {
/* 114 */     if (!this.useproxy)
/*     */     {
/* 116 */       return null;
/*     */     }
/* 118 */     ActionErrors errors = new ActionErrors();
/* 119 */     if ((this.userid == null) || (this.userid.length() < 1))
/* 120 */       errors.add("username", new ActionError("error.username.required"));
/* 121 */     if ((this.password == null) || (this.password.length() < 1))
/* 122 */       errors.add("password", new ActionError("error.password.required"));
/* 123 */     if ((this.host == null) || (this.host.length() < 1))
/*     */     {
/* 125 */       errors.add("host", new ActionError("error.host.required"));
/*     */ 
/*     */ 
/*     */     }
/* 129 */     else if (!validHost(this.host))
/*     */     {
/* 131 */       errors.add("host", new ActionError("error.host.invalid"));
/*     */     }
/*     */     
/* 134 */     if ((this.port == null) || (this.port.length() < 1))
/* 135 */       errors.add("port", new ActionError("error.port.required"));
/* 136 */     return errors;
/*     */   }
/*     */   
/*     */   public boolean validHost(String host)
/*     */   {
/*     */     try
/*     */     {
/* 143 */       InetAddress add = InetAddress.getByName(host);
/* 144 */       this.host = add.getHostAddress();
/* 145 */       return true;
/*     */     }
/*     */     catch (UnknownHostException ee) {}
/*     */     
/* 149 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBypassproxy()
/*     */   {
/* 157 */     System.out.print("Inside isbypassproxy" + this.bypassproxy);
/* 158 */     return this.bypassproxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBypassproxy(boolean bypassproxy)
/*     */   {
/* 165 */     this.bypassproxy = bypassproxy;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDontProxyfor()
/*     */   {
/* 171 */     return this.dontProxyfor;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDontProxyfor(String dontProxyfor)
/*     */   {
/* 177 */     this.dontProxyfor = dontProxyfor;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isUseproxy()
/*     */   {
/* 183 */     return this.useproxy;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setUseproxy(boolean useproxy)
/*     */   {
/* 189 */     this.useproxy = useproxy;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\ProxyConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */