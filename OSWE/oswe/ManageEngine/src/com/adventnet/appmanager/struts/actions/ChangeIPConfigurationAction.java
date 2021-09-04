/*      */ package com.adventnet.appmanager.struts.actions;
/*      */ 
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import org.apache.struts.action.ActionErrors;
/*      */ import org.apache.struts.action.ActionForm;
/*      */ import org.apache.struts.action.ActionForward;
/*      */ import org.apache.struts.action.ActionMapping;
/*      */ import org.apache.struts.action.ActionMessages;
/*      */ import org.apache.struts.actions.DispatchAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ChangeIPConfigurationAction
/*      */   extends DispatchAction
/*      */ {
/*      */   private String inFace;
/*      */   
/*      */   public ActionForward SaveIPConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*   94 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/*   96 */       ActionMessages messages = new ActionMessages();
/*   97 */       ActionErrors errors = new ActionErrors();
/*   98 */       AMActionForm af = (AMActionForm)form;
/*   99 */       String hostname = af.getJanuary();
/*  100 */       String ipaddress = af.getFebruary();
/*  101 */       String network = af.getMarch();
/*  102 */       String dnsserver = af.getApril();
/*  103 */       String gateway = af.getMay();
/*  104 */       String intface = af.getJuly();
/*      */       
/*  106 */       setInterfaceName(intface);
/*  107 */       request.setAttribute("hostname", hostname);
/*  108 */       request.setAttribute("ipaddress", ipaddress);
/*  109 */       request.setAttribute("network", network);
/*  110 */       request.setAttribute("dnsserver", dnsserver);
/*  111 */       request.setAttribute("gateway", gateway);
/*  112 */       request.setAttribute("interface", intface);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  118 */       ex.printStackTrace();
/*      */     }
/*  120 */     return new ActionForward("/jsp/ChangeIPAddress.jsp?showDiv=true");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward getIPConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  133 */       response.setContentType("text/html; charset=UTF-8");
/*  134 */       String inface = request.getParameter("inface");
/*      */       
/*      */ 
/*  137 */       if (inface == null) {
/*  138 */         inface = "eth1";
/*      */       }
/*  140 */       Properties p = getAllInfo(inface);
/*  141 */       String s1 = p.getProperty("host");
/*  142 */       String s2 = p.getProperty("ip");
/*  143 */       String s3 = p.getProperty("net");
/*  144 */       String s4 = p.getProperty("gate");
/*  145 */       String s5 = p.getProperty("DNS");
/*  146 */       String formit = s1 + "#" + s2 + "#" + s3 + "#" + s4 + "#" + s5;
/*      */       
/*  148 */       PrintWriter pw = response.getWriter();
/*  149 */       pw.print(formit);
/*      */     } catch (Exception ex) {
/*  151 */       ex.printStackTrace();
/*      */     }
/*      */     
/*  154 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward SetIPConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  166 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/*  168 */       ActionMessages messages = new ActionMessages();
/*  169 */       ActionErrors errors = new ActionErrors();
/*  170 */       AMActionForm af = (AMActionForm)form;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  178 */       String inf = "eth1";
/*  179 */       inf = readInFile();
/*      */       
/*  181 */       Properties p1 = getAllInfo(inf);
/*  182 */       String hostname = p1.getProperty("host");
/*  183 */       String dnsserver = p1.getProperty("DNS");
/*      */       
/*  185 */       String ipaddress = p1.getProperty("ip");
/*  186 */       String network = p1.getProperty("net");
/*  187 */       String gateway = p1.getProperty("gate");
/*      */       
/*  189 */       af.setJanuary(hostname);
/*  190 */       af.setFebruary(ipaddress);
/*  191 */       af.setMarch(network);
/*  192 */       af.setApril(dnsserver);
/*  193 */       af.setMay(gateway);
/*  194 */       af.setJuly(inf);
/*      */       
/*  196 */       af.setMonday(getCurrentTime());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  209 */       ex.printStackTrace();
/*      */     }
/*  211 */     return new ActionForward("/jsp/ChangeIPAddress.jsp");
/*      */   }
/*      */   
/*      */ 
/*      */   public String getCurrentTime()
/*      */   {
/*  217 */     String date = null;
/*      */     try
/*      */     {
/*  220 */       Calendar cal = new GregorianCalendar();
/*  221 */       int year = cal.get(1);
/*  222 */       int month = cal.get(2);
/*  223 */       String mon = null;
/*  224 */       String dd = null;
/*  225 */       String hh = null;
/*  226 */       String mm = null;
/*  227 */       if (month + 1 < 10)
/*      */       {
/*  229 */         mon = "0" + (month + 1);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  234 */         mon = String.valueOf(month + 1);
/*      */       }
/*  236 */       int day = cal.get(5);
/*  237 */       if (day < 10)
/*      */       {
/*  239 */         dd = "0" + day;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  244 */         dd = String.valueOf(day);
/*      */       }
/*      */       
/*  247 */       int hour = cal.get(11);
/*  248 */       if (hour < 10)
/*      */       {
/*  250 */         hh = "0" + hour;
/*      */       }
/*      */       else
/*      */       {
/*  254 */         hh = String.valueOf(hour);
/*      */       }
/*  256 */       int min = cal.get(12);
/*  257 */       if (min < 10)
/*      */       {
/*  259 */         mm = "0" + min;
/*      */       }
/*      */       else
/*      */       {
/*  263 */         mm = String.valueOf(min);
/*      */       }
/*      */       
/*  266 */       date = String.valueOf(year) + "-" + mon + "-" + dd + " " + hh + ":" + mm;
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  271 */       ex.printStackTrace();
/*      */     }
/*  273 */     return date;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward ChangeIPConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/*  284 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/*  286 */       ActionMessages messages = new ActionMessages();
/*  287 */       ActionErrors errors = new ActionErrors();
/*  288 */       AMActionForm af = (AMActionForm)form;
/*  289 */       String hostname = request.getParameter("hostname");
/*  290 */       String intface = request.getParameter("interface");
/*      */       
/*  292 */       String ipaddress = request.getParameter("ipaddress");
/*  293 */       String s1 = ipaddress;
/*  294 */       String[] temp = s1.split(":");
/*  295 */       if (temp.length != 2)
/*      */       {
/*  297 */         s1 = ipaddress;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  302 */         s1 = temp[1];
/*      */       }
/*  304 */       String netmask = request.getParameter("netmask");
/*      */       
/*  306 */       String dnsserver = request.getParameter("dnsserver");
/*      */       
/*  308 */       String gateway = request.getParameter("gateway");
/*      */       
/*  310 */       String m1 = changeHostName(s1, hostname);
/*  311 */       String m2 = readDNS(dnsserver);
/*  312 */       ArrayList a2 = readIpAddress(ipaddress, netmask, gateway, intface);
/*  313 */       Properties p1 = new Properties();
/*  314 */       Properties p2 = new Properties();
/*  315 */       p2.setProperty("hostname", hostname);
/*  316 */       p2.setProperty("ipaddress", s1);
/*  317 */       p2.setProperty("dnsserver", dnsserver);
/*  318 */       p2.setProperty("netmask", netmask);
/*  319 */       p2.setProperty("gateway", gateway);
/*  320 */       p2.setProperty("interface", intface);
/*      */       
/*  322 */       int size = a2.size();
/*  323 */       if ((size > 0) && (size < 4))
/*      */       {
/*  325 */         p1.setProperty("ipsuccess", FormatUtil.getString("am.webclient.ipconfig.ipfailed.text", new String[] { (String)a2.get(0) }));
/*  326 */         p1.setProperty("intersucess", FormatUtil.getString("am.webclient.ipconfig.interfacefailed.text", new String[] { (String)a2.get(0) }));
/*  327 */         p1.setProperty("netsuccess", FormatUtil.getString("am.webclient.ipconfig.netfailed.text", new String[] { (String)a2.get(0) }));
/*  328 */         p1.setProperty("gatesuccess", FormatUtil.getString("am.webclient.ipconfig.gatefailed.text", new String[] { (String)a2.get(0) }));
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  333 */         p1.setProperty("ipsuccess", "true");
/*  334 */         p1.setProperty("intersucess", "true");
/*  335 */         p1.setProperty("netsuccess", "true");
/*  336 */         p1.setProperty("gatesuccess", "true");
/*      */       }
/*  338 */       p1.setProperty("hostsuccess", m1);
/*  339 */       p1.setProperty("dnssuccess", m2);
/*      */       
/*  341 */       request.setAttribute("message", p1);
/*  342 */       request.setAttribute("props", p2);
/*  343 */       String inf = "eth1";
/*  344 */       inf = readInFile();
/*      */       
/*  346 */       Properties p11 = getAllInfo(inf);
/*      */       
/*  348 */       String hostname1 = p11.getProperty("host");
/*  349 */       String dnsserver1 = p11.getProperty("DNS");
/*      */       
/*  351 */       String ipaddress1 = p11.getProperty("ip");
/*  352 */       String network1 = p11.getProperty("net");
/*  353 */       String gateway1 = p11.getProperty("gate");
/*      */       
/*      */ 
/*  356 */       af.setJanuary(hostname1);
/*  357 */       af.setFebruary(ipaddress1);
/*  358 */       af.setMarch(network1);
/*  359 */       af.setApril(dnsserver1);
/*  360 */       af.setMay(gateway1);
/*  361 */       af.setJuly(inf);
/*  362 */       af.setMonday(getCurrentTime());
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  367 */       ex.printStackTrace();
/*      */     }
/*  369 */     return new ActionForward("/jsp/ChangeIPAddress.jsp");
/*      */   }
/*      */   
/*      */   public String changeHostName(String ip, String hostname)
/*      */   {
/*  374 */     String message = null;
/*      */     
/*      */     try
/*      */     {
/*  378 */       File f2 = new File("/etc/hostname");
/*  379 */       File f3 = new File("/etc/hosts");
/*  380 */       Process p; if (f2.exists())
/*      */       {
/*  382 */         BufferedWriter out = new BufferedWriter(new FileWriter(f2));
/*  383 */         out.write(hostname);
/*  384 */         out.close();
/*      */         
/*  386 */         message = "true";
/*  387 */         p = Runtime.getRuntime().exec("/bin/hostname " + hostname);
/*      */       }
/*      */       else
/*      */       {
/*  391 */         message = FormatUtil.getString("am.webclient.ipconfig.hostfailed.file.text");
/*      */       }
/*  393 */       if (f3.exists())
/*      */       {
/*  395 */         BufferedReader bufreader = new BufferedReader(new FileReader(f3));
/*  396 */         String line = null;
/*  397 */         StringBuffer buffer = new StringBuffer();
/*      */         
/*  399 */         while ((line = bufreader.readLine()) != null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  404 */           buffer.append(line + "\n");
/*      */           
/*      */ 
/*  407 */           if (line.indexOf("127.0.0.1") != -1)
/*      */           {
/*  409 */             buffer.append(ip + "             " + hostname + "\n");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  415 */         byte[] b = buffer.toString().getBytes();
/*  416 */         FileOutputStream fos = new FileOutputStream(f3);
/*  417 */         fos.write(b);
/*  418 */         bufreader.close();
/*  419 */         fos.close();
/*  420 */         message = "true";
/*      */       }
/*      */       else
/*      */       {
/*  424 */         message = FormatUtil.getString("am.webclient.ipconfig.hostsfailed.text");
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*  432 */       message = FormatUtil.getString("am.webclient.ipconfig.hostfailed.exception.text");
/*  433 */       String m1 = ex.getMessage();
/*  434 */       if (m1.indexOf("Permission denied") != -1)
/*      */       {
/*  436 */         message = FormatUtil.getString("am.webclient.ipconfig.hostfailed.file.exception.text");
/*      */       }
/*  438 */       ex.printStackTrace();
/*      */     }
/*  440 */     return message;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void rebootNetwork()
/*      */   {
/*      */     try
/*      */     {
/*  449 */       String interfaceName = getInterfaceName();
/*  450 */       String[] interfaceList = { "eth1", "eth2", "eth3", "eth4", "eth5", "eth6", "eth7" };
/*  451 */       for (int i = 0; i <= interfaceList.length - 1; i++)
/*      */       {
/*  453 */         if (!interfaceName.equals(interfaceList[i])) {
/*  454 */           AMLog.debug("ChangeIpConfiguration>>rebootNetwork>>Runtime.getRuntime().exec(\"ifconfig " + interfaceList[i] + " down\")");
/*      */           try {
/*  456 */             process = Runtime.getRuntime().exec("ifconfig " + interfaceList[i] + " down");
/*      */           }
/*      */           catch (Exception ex) {
/*      */             Process process;
/*  460 */             AMLog.debug("ChangeIpConfiguration>>Exception executing command::Runtime.getRuntime().exec(\"ifconfig " + interfaceList[i] + " down\")");
/*  461 */             ex.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  475 */       Process p = Runtime.getRuntime().exec("/etc/init.d/networking restart");
/*  476 */       AMLog.debug("Network restarted sucessfully");
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  480 */       System.out.println("Exception occured while restarting network");
/*  481 */       ex.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public Properties getAllInfo(String Interface) {
/*  486 */     Properties props = new Properties();
/*  487 */     String ip = "";
/*  488 */     String net = "";
/*  489 */     String gate = "";
/*      */     try
/*      */     {
/*  492 */       File F = new File("/etc/network/interfaces");
/*      */       
/*  494 */       String ifname = "eth1";
/*  495 */       if (F.exists())
/*      */       {
/*  497 */         BufferedReader buf1 = new BufferedReader(new FileReader(F));
/*  498 */         String line1 = null;
/*  499 */         StringBuffer sbuf = new StringBuffer();
/*  500 */         while ((line1 = buf1.readLine()) != null)
/*      */         {
/*      */ 
/*  503 */           if (line1.startsWith("#")) {
/*  504 */             line1 = line1.substring(1);
/*      */           }
/*  506 */           if (line1.indexOf("auto") != -1)
/*      */           {
/*  508 */             ifname = line1.substring(4).trim();
/*      */             
/*      */ 
/*  511 */             if ((!ifname.equals(Interface)) && (!"eth0".equals(ifname)) && (!"lo".equals(ifname))) {
/*  512 */               sbuf.append("#" + ifname + "\n");
/*      */             }
/*      */             else
/*      */             {
/*  516 */               sbuf.append(line1 + "\n");
/*      */             }
/*      */           }
/*  519 */           else if ((line1.indexOf(Interface) != -1) && (line1.indexOf("iface") == -1))
/*      */           {
/*  521 */             ifname = line1;
/*  522 */             sbuf.append("auto " + line1 + "\n");
/*      */ 
/*      */ 
/*      */           }
/*  526 */           else if ((line1.startsWith("eth1")) || (line1.startsWith("eth2")) || (line1.startsWith("eth3")) || (line1.startsWith("eth4")) || (line1.startsWith("eth5")) || (line1.startsWith("eth6")) || (line1.startsWith("eth7")))
/*      */           {
/*      */ 
/*  529 */             ifname = line1;
/*  530 */             sbuf.append("#" + line1 + "\n");
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  535 */           else if ((ifname != null) && (ifname.equals(Interface)))
/*      */           {
/*  537 */             if (line1.indexOf("iface") != -1)
/*      */             {
/*  539 */               sbuf.append(line1 + "\n");
/*      */             }
/*      */             
/*  542 */             if (line1.indexOf("address") != -1)
/*      */             {
/*  544 */               ip = line1.substring(7).trim();
/*  545 */               props.setProperty("ip", ip);
/*      */             }
/*  547 */             if (line1.indexOf("netmask") != -1)
/*      */             {
/*  549 */               net = line1.substring(7).trim();
/*  550 */               props.setProperty("net", net);
/*      */             }
/*  552 */             if (line1.indexOf("gateway") != -1)
/*      */             {
/*  554 */               gate = line1.substring(7).trim();
/*  555 */               props.setProperty("gate", gate);
/*      */             }
/*      */           }
/*  558 */           else if ((ifname != null) && ((ifname.equals("eth0")) || (ifname.equals("lo")))) {
/*  559 */             if (!line1.equals("")) {
/*  560 */               sbuf.append(line1 + "\n");
/*      */             }
/*  562 */             if (line1.indexOf("gateway") != -1) {
/*  563 */               sbuf.append("\n");
/*      */             }
/*  565 */           } else if (!line1.equals("")) {
/*  566 */             sbuf.append("#" + line1 + "\n");
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  573 */       props.setProperty("DNS", readDNSName());
/*  574 */       props.setProperty("host", readHostName());
/*  575 */       System.out.println("The Propertis in Get ALLINfo is====>" + props);
/*      */     } catch (Exception ex) {
/*  577 */       ex.printStackTrace();
/*      */     }
/*  579 */     return props;
/*      */   }
/*      */   
/*  582 */   public String readDNS(String dnsadd) { String message = null;
/*      */     try
/*      */     {
/*  585 */       File f = new File("/etc/resolv.conf");
/*      */       
/*  587 */       if (f.exists())
/*      */       {
/*      */ 
/*  590 */         BufferedReader bufreader = new BufferedReader(new FileReader(f));
/*  591 */         String line = null;
/*  592 */         StringBuffer buffer = new StringBuffer();
/*      */         
/*  594 */         while ((line = bufreader.readLine()) != null)
/*      */         {
/*  596 */           if ((!line.equals("")) && (line.indexOf("nameserver") == -1))
/*      */           {
/*      */ 
/*  599 */             buffer.append(line + "\n");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  608 */         String[] te = dnsadd.split(",");
/*  609 */         if (te.length <= 0) {
/*  610 */           if (buffer.indexOf("nameserver " + dnsadd) == -1)
/*      */           {
/*  612 */             buffer.append("nameserver " + dnsadd + " " + "\n");
/*      */           }
/*      */         } else {
/*  615 */           for (int r = 0; r < te.length; r++) {
/*  616 */             buffer.append("nameserver " + te[r] + " " + "\n");
/*      */           }
/*      */         }
/*      */         
/*  620 */         byte[] b = buffer.toString().getBytes();
/*  621 */         FileOutputStream fos = new FileOutputStream(f);
/*  622 */         fos.write(b);
/*  623 */         bufreader.close();
/*  624 */         fos.close();
/*  625 */         message = "true";
/*      */       }
/*      */       else
/*      */       {
/*  629 */         message = FormatUtil.getString("am.webclient.ipconfig.dnsfailed.file.text");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  635 */       message = FormatUtil.getString("am.webclient.ipconfig.dnsfailed.exception.text");
/*  636 */       String m1 = ex.getMessage();
/*  637 */       if (m1.indexOf("Permission denied") != -1)
/*      */       {
/*  639 */         message = FormatUtil.getString("am.webclient.ipconfig.dnsfailed.file.exception.text");
/*      */       }
/*  641 */       ex.printStackTrace();
/*      */     }
/*  643 */     return message;
/*      */   }
/*      */   
/*      */   public ArrayList readIpAddress(String ipadd, String netadd, String gateadd, String intface) {
/*  647 */     ArrayList a1 = new ArrayList();
/*  648 */     String message = null;
/*      */     try
/*      */     {
/*  651 */       String inface = "eth1";
/*      */       
/*      */ 
/*      */ 
/*  655 */       inface = intface;
/*      */       
/*      */ 
/*  658 */       File f1 = new File("/etc/network/interfaces");
/*      */       
/*      */ 
/*  661 */       if (f1.exists())
/*      */       {
/*  663 */         BufferedReader buf1 = new BufferedReader(new FileReader(f1));
/*  664 */         String line1 = null;
/*  665 */         StringBuffer sbuf = new StringBuffer();
/*  666 */         String ifname = null;
/*  667 */         boolean t = writeInFile(intface);
/*  668 */         if (!t) {
/*  669 */           System.out.println("=======PROBLEM IN WRITING THE LOCAL INTERFACE FILE=======");
/*      */         }
/*  671 */         while ((line1 = buf1.readLine()) != null)
/*      */         {
/*      */ 
/*      */ 
/*  675 */           if (line1.startsWith("#")) {
/*  676 */             line1 = line1.substring(1);
/*      */           }
/*  678 */           if (line1.indexOf("auto") != -1)
/*      */           {
/*  680 */             ifname = line1.substring(4).trim();
/*      */             
/*      */ 
/*  683 */             if ((!ifname.equals(inface)) && (!"eth0".equals(ifname)) && (!"lo".equals(ifname))) {
/*  684 */               sbuf.append("#" + ifname + "\n");
/*      */             }
/*      */             else {
/*  687 */               message = "The Interface set to " + ifname;
/*  688 */               sbuf.append(line1 + "\n");
/*  689 */               a1.add("true");
/*      */             }
/*  691 */           } else if ((line1.indexOf(inface) != -1) && (line1.indexOf("iface") == -1))
/*      */           {
/*  693 */             ifname = line1;
/*  694 */             sbuf.append("auto " + line1 + "\n");
/*      */             
/*  696 */             message = "The Interface set to " + ifname;
/*  697 */             a1.add("true");
/*      */           }
/*  699 */           else if ((line1.startsWith("eth1")) || (line1.startsWith("eth2")) || (line1.startsWith("eth3")) || (line1.startsWith("eth4")) || (line1.startsWith("eth5")) || (line1.startsWith("eth6")) || (line1.startsWith("eth7")))
/*      */           {
/*      */ 
/*  702 */             ifname = line1;
/*  703 */             sbuf.append("#" + line1 + "\n");
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  708 */           else if ((ifname != null) && (ifname.equals(inface)))
/*      */           {
/*  710 */             if (line1.indexOf("iface") != -1)
/*      */             {
/*  712 */               sbuf.append(line1 + "\n");
/*      */             }
/*      */             
/*  715 */             if (line1.indexOf("address") != -1)
/*      */             {
/*  717 */               sbuf.append("address " + ipadd + " " + "\n");
/*  718 */               message = message + ": The IP Address changed Sucessfully";
/*  719 */               a1.add("true");
/*      */             }
/*  721 */             if (line1.indexOf("netmask") != -1)
/*      */             {
/*  723 */               sbuf.append("netmask " + netadd + " " + "\n");
/*  724 */               message = message + ": The Netmask Address changed Sucessfully";
/*  725 */               a1.add("true");
/*      */             }
/*      */             
/*  728 */             if (line1.indexOf("gateway") != -1)
/*      */             {
/*  730 */               sbuf.append("gateway " + gateadd + " " + "\n");
/*  731 */               message = message + ": The Gateway Address changed Sucessfully";
/*  732 */               a1.add("true");
/*  733 */               sbuf.append("\n");
/*      */             }
/*      */           }
/*  736 */           else if ((ifname != null) && ((ifname.equals("eth0")) || (ifname.equals("lo")))) {
/*  737 */             if (!line1.equals("")) {
/*  738 */               sbuf.append(line1 + "\n");
/*      */             }
/*  740 */             if (line1.indexOf("gateway") != -1) {
/*  741 */               sbuf.append("\n");
/*      */             }
/*  743 */           } else if (!line1.equals("")) {
/*  744 */             sbuf.append("#" + line1 + "\n");
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  837 */         byte[] b1 = sbuf.toString().getBytes();
/*  838 */         FileOutputStream fo = new FileOutputStream(f1);
/*  839 */         fo.write(b1);
/*  840 */         buf1.close();
/*  841 */         fo.close();
/*      */         try
/*      */         {
/*  844 */           rebootNetwork();
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  848 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  854 */         a1.add(FormatUtil.getString("am.webclient.ipconfig.ipfailed.file.text"));
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  860 */       String m1 = ex.getMessage();
/*  861 */       if (m1.indexOf("Permission denied") != -1)
/*      */       {
/*  863 */         message = "Exception occured while writing '/etc/resolv.conf' file.Permission denied";
/*  864 */         a1.add(FormatUtil.getString("am.webclient.ipconfig.ipfailed.file.exception.text"));
/*      */       }
/*  866 */       a1.add(FormatUtil.getString("am.webclient.ipconfig.ipfailed.exception.text"));
/*  867 */       ex.printStackTrace();
/*      */     }
/*  869 */     return a1;
/*      */   }
/*      */   
/*      */   public String readHostName()
/*      */   {
/*  874 */     String hostname = "";
/*      */     try
/*      */     {
/*  877 */       File F = new File("/etc/hostname");
/*      */       
/*  879 */       if (F.exists())
/*      */       {
/*  881 */         BufferedReader buf = new BufferedReader(new FileReader(F));
/*  882 */         String line = null;
/*  883 */         while ((line = buf.readLine()) != null)
/*      */         {
/*  885 */           if (!line.equals("")) {
/*  886 */             hostname = line;
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  894 */         System.out.println("******The file '/etc/hostname' doesnt exists.");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  900 */       ex.printStackTrace();
/*      */     }
/*  902 */     return hostname;
/*      */   }
/*      */   
/*      */   public String readDNSName() {
/*  906 */     String dnsname = "";
/*      */     try
/*      */     {
/*  909 */       File F = new File("/etc/resolv.conf");
/*      */       
/*      */ 
/*  912 */       if (F.exists())
/*      */       {
/*  914 */         BufferedReader buf = new BufferedReader(new FileReader(F));
/*  915 */         String line = null;
/*  916 */         int i = 0;
/*  917 */         while ((line = buf.readLine()) != null)
/*      */         {
/*  919 */           if (line.indexOf("nameserver") != -1)
/*      */           {
/*  921 */             if (i == 0)
/*      */             {
/*  923 */               dnsname = line.substring(10).trim();
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  928 */               dnsname = dnsname + "," + line.substring(10).trim();
/*      */             }
/*  930 */             i++;
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  938 */         System.out.println("*********The File '/etc/resolv.conf' doenst exists ");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  944 */       ex.printStackTrace();
/*      */     }
/*  946 */     return dnsname;
/*      */   }
/*      */   
/*      */   public Properties readInterfaceFile() {
/*  950 */     Properties props = new Properties();
/*      */     try
/*      */     {
/*  953 */       File F = new File("/etc/network/interfaces");
/*      */       
/*      */ 
/*  956 */       if (F.exists())
/*      */       {
/*  958 */         BufferedReader buf = new BufferedReader(new FileReader(F));
/*  959 */         String line = null;
/*  960 */         String ifname = "eth1";
/*  961 */         boolean isValid = false;
/*  962 */         while ((line = buf.readLine()) != null)
/*      */         {
/*  964 */           if (line.indexOf("auto") != -1)
/*      */           {
/*  966 */             ifname = line.substring(4).trim();
/*  967 */             if (!"eth0".equals(ifname)) {
/*  968 */               isValid = true;
/*  969 */               props.setProperty("interface", ifname);
/*      */             }
/*      */           }
/*      */           
/*  973 */           if ((isValid) && (line.indexOf("address") != -1))
/*      */           {
/*      */ 
/*  976 */             String s1 = line.trim().substring(7);
/*      */             
/*  978 */             props.setProperty("ipadd", s1.trim());
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  983 */           else if ((isValid) && (line.indexOf("netmask") != -1))
/*      */           {
/*      */ 
/*  986 */             String s2 = line.trim().substring(7);
/*      */             
/*  988 */             props.setProperty("netadd", s2.trim());
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*  993 */           else if ((isValid) && (line.indexOf("gateway") != -1))
/*      */           {
/*      */ 
/*  996 */             String s3 = line.trim().substring(7);
/*      */             
/*  998 */             props.setProperty("gateway", s3.trim());
/*  999 */             isValid = false;
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 1009 */         System.out.println("******The File '/etc/network/interfaces' doesnt exists");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1015 */       ex.printStackTrace();
/*      */     }
/* 1017 */     return props;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward SaveDateConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1028 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/* 1030 */       ActionMessages messages = new ActionMessages();
/* 1031 */       ActionErrors errors = new ActionErrors();
/* 1032 */       AMActionForm af = (AMActionForm)form;
/* 1033 */       String dates = af.getMonday();
/*      */       
/* 1035 */       request.setAttribute("dates", dates);
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/* 1041 */       ex.printStackTrace();
/*      */     }
/* 1043 */     return new ActionForward("/jsp/ChangeIPAddress.jsp?type=dates");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public ActionForward ConfirmDateConfiguration(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*      */     throws Exception
/*      */   {
/*      */     try
/*      */     {
/* 1054 */       response.setContentType("text/html; charset=UTF-8");
/*      */       
/* 1056 */       ActionMessages messages = new ActionMessages();
/* 1057 */       ActionErrors errors = new ActionErrors();
/* 1058 */       AMActionForm af = (AMActionForm)form;
/* 1059 */       String dates = request.getParameter("dates");
/*      */       
/* 1061 */       String[] temp = dates.split(" ");
/* 1062 */       String[] year = temp[0].split("-");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1069 */       String[] time = temp[1].split(":");
/*      */       
/*      */ 
/*      */ 
/* 1073 */       String s1 = null;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/* 1103 */         File f = new File("DateFile.sh");
/* 1104 */         if (f.exists())
/*      */         {
/*      */ 
/* 1107 */           System.out.println("The Date file already exists");
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/* 1113 */           f.createNewFile();
/*      */         }
/* 1115 */         StringBuffer sbuf = new StringBuffer();
/* 1116 */         sbuf.append("date --set " + temp[0]);
/* 1117 */         sbuf.append("\n");
/* 1118 */         sbuf.append("date --set " + temp[1] + ":59");
/* 1119 */         byte[] b = sbuf.toString().getBytes();
/* 1120 */         FileOutputStream fo = new FileOutputStream(f);
/* 1121 */         fo.write(b);
/* 1122 */         fo.close();
/*      */ 
/*      */       }
/*      */       catch (Exception es)
/*      */       {
/*      */ 
/* 1128 */         es.printStackTrace();
/*      */       }
/*      */       
/* 1131 */       s1 = FormatUtil.getString("am.webclient.systemsettings.datesettings.sucess.text");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1165 */       request.setAttribute("datemessage", s1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1184 */       System.out.println("Exception occures in date settings");
/* 1185 */       ex.printStackTrace();
/*      */     }
/*      */     
/* 1188 */     return new ActionForward("Tile.ShutdownServer");
/*      */   }
/*      */   
/*      */   public boolean writeInFile(String inf) {
/* 1192 */     boolean ret = false;
/*      */     
/*      */ 
/*      */     try
/*      */     {
/* 1197 */       String Root_Dir = System.getProperty("webnms.rootdir");
/*      */       
/*      */ 
/* 1200 */       File f = new File(Root_Dir + File.separator + "Interface.txt");
/* 1201 */       boolean b; if (f.exists()) {
/* 1202 */         System.out.println("The file Interface.txt exists");
/*      */       }
/*      */       else {
/* 1205 */         b = f.createNewFile();
/*      */       }
/*      */       
/* 1208 */       StringBuffer sbuf = new StringBuffer();
/*      */       
/* 1210 */       sbuf.append(inf);
/* 1211 */       byte[] b = sbuf.toString().getBytes();
/* 1212 */       FileOutputStream fo = new FileOutputStream(f);
/* 1213 */       fo.write(b);
/* 1214 */       fo.close();
/* 1215 */       ret = true;
/*      */ 
/*      */     }
/*      */     catch (Exception es)
/*      */     {
/*      */ 
/* 1221 */       es.printStackTrace();
/* 1222 */       ret = false;
/*      */     }
/* 1224 */     return ret;
/*      */   }
/*      */   
/*      */   public String readInFile() {
/* 1228 */     String inface = "";
/*      */     try
/*      */     {
/* 1231 */       String Root_Dir = System.getProperty("webnms.rootdir");
/*      */       
/* 1233 */       File F = new File(Root_Dir + File.separator + "Interface.txt");
/*      */       
/* 1235 */       if (F.exists())
/*      */       {
/* 1237 */         BufferedReader buf = new BufferedReader(new FileReader(F));
/* 1238 */         String line = null;
/* 1239 */         while ((line = buf.readLine()) != null)
/*      */         {
/* 1241 */           if (!line.equals("")) {
/* 1242 */             inface = line;
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/* 1250 */         inface = "eth1";
/* 1251 */         System.out.println("******The file 'Interface.txt' doesnt exists.");
/*      */       }
/*      */       
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1257 */       ex.printStackTrace();
/*      */     }
/* 1259 */     return inface;
/*      */   }
/*      */   
/*      */   public String getInterfaceName()
/*      */   {
/* 1264 */     return this.inFace;
/*      */   }
/*      */   
/* 1267 */   public void setInterfaceName(String m) { this.inFace = m; }
/*      */   
/*      */   public static void main(String[] args) {}
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ChangeIPConfigurationAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */