/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.rss.feeder.RssFeedCreator;
/*     */ import com.adventnet.rss.impl.Channel;
/*     */ import com.adventnet.rss.impl.Item;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class OpManagerRssFeedServlet extends javax.servlet.http.HttpServlet
/*     */ {
/*  19 */   Channel ch = new Channel();
/*  20 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
/*     */   {
/*     */     try {
/*  25 */       this.ch.setTitle("AdventNet.AppManager");
/*  26 */       String serverName = request.getServerName();
/*  27 */       int serverPort = request.getServerPort();
/*  28 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  29 */       String host = request.getParameter("host");
/*  30 */       String port = request.getParameter("port");
/*  31 */       String protocol = "http";
/*  32 */       String productName = "OpManager";
/*  33 */       String linkToSend = "";
/*  34 */       String loginURL = protocol + "://" + host + ":" + port + "/overview.do";
/*  35 */       if (host != null)
/*     */       {
/*  37 */         String query1 = "select LOGIN_URL from ADDONPRODUCTS_URL where PRODUCT_NAME='OpManager'";
/*  38 */         ResultSet set1 = AMConnectionPool.executeQueryStmt(query1);
/*  39 */         if (set1.first())
/*     */         {
/*  41 */           String updateAddOnProducts = "update ADDONPRODUCTS_URL set HOST = '" + host + "', PORT = '" + port + "' , LOGIN_URL='" + loginURL + "' where PRODUCT_NAME='OpManager'";
/*  42 */           this.mo.executeUpdateStmt(updateAddOnProducts);
/*     */         }
/*     */         else
/*     */         {
/*  46 */           String insertAddOnProducts = "insert into ADDONPRODUCTS_URL (PRODUCT_NAME, PROTOCOL, HOST , PORT, LOGIN_URL) values ('" + productName + "','" + protocol + "','" + host + "','" + port + "','" + loginURL + "')";
/*  47 */           this.mo.executeUpdateStmt(insertAddOnProducts);
/*     */         }
/*     */       }
/*     */       
/*  51 */       String resourcename = "";
/*  52 */       String resourceid = "";
/*  53 */       String type = "";
/*  54 */       String moname = "";
/*  55 */       String IpAddress = request.getParameter("IpAddress");
/*  56 */       String displayname = request.getParameter("displayname");
/*  57 */       if (IpAddress != null)
/*     */       {
/*  59 */         String query1 = "select InetService.TARGETNAME from InetService where  InetService.TARGETADDRESS='" + IpAddress + "'";
/*  60 */         ResultSet set1 = AMConnectionPool.executeQueryStmt(query1);
/*  61 */         if (set1.first())
/*     */         {
/*  63 */           resourcename = set1.getString("TARGETNAME");
/*  64 */           moname = resourcename;
/*  65 */           String query2 = "select RESOURCEID,TYPE from AM_ManagedObject where RESOURCENAME='" + resourcename + "'";
/*  66 */           ResultSet set2 = AMConnectionPool.executeQueryStmt(query2);
/*  67 */           if (set2.first())
/*     */           {
/*  69 */             resourceid = set2.getString(1);
/*  70 */             type = set2.getString(2);
/*     */           }
/*     */         }
/*     */       }
/*  74 */       else if (displayname != null)
/*     */       {
/*  76 */         resourcename = displayname;
/*  77 */         moname = resourcename;
/*  78 */         String query3 = "select RESOURCEID,TYPE from AM_ManagedObject where RESOURCENAME='" + displayname + "'";
/*  79 */         ResultSet set3 = AMConnectionPool.executeQueryStmt(query3);
/*  80 */         if (set3.first())
/*     */         {
/*  82 */           resourceid = set3.getString(1);
/*  83 */           type = set3.getString(2);
/*     */         }
/*     */       }
/*  86 */       linkToSend = protocol + "://" + serverName + ":" + serverPort + "/showresource.do?resourceid=" + resourceid + "&#38;type=" + type + "&#38;moname=" + moname + "&#38;method=showdetails&#38;resourcename=" + resourcename + "&#38;toSend=opmanager";
/*  87 */       Collection col = new Vector();
/*  88 */       addItem(col, "SnapShot", linkToSend);
/*  89 */       this.ch.setItems(col);
/*  90 */       PrintWriter out = response.getWriter();
/*  91 */       if (this.ch == null)
/*     */       {
/*  93 */         out.println("The channel is null");
/*  94 */         return;
/*     */       }
/*  96 */       RssFeedCreator creator = new RssFeedCreator();
/*  97 */       StringBuffer buffer = creator.createRssFeed(this.ch);
/*  98 */       response.setContentType("text/xml");
/*  99 */       out.println(buffer.toString());
/* 100 */       out.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void addItem(Collection col, String title, String linkToSend)
/*     */     throws Exception
/*     */   {
/* 111 */     String guid = title + ":GUID";
/* 112 */     Item it = new Item();
/* 113 */     it.setTitle(title);
/* 114 */     it.setGuid(guid);
/* 115 */     it.setPermaLink(false);
/* 116 */     it.setLink(new URL(linkToSend));
/* 117 */     it.setDescription("These are the list of Monitors in this System");
/* 118 */     col.add(it);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\OpManagerRssFeedServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */