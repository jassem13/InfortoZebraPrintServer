1	Introduction

Infor WMS has already Loftware Automation as a primary solution for label printing. This is for those who are looking for alternative solution.

1.1	Purpose
-	To create a label printing solution for Infor WMS.
-	To create alternative solution for loftware (no license, free software)
  
1.2	Method
-	User will input serial of his designated printer, and proceed to trigger printing function of Infor WMS
-	Infor WMS will generate XML containing label and printer serial details as inputted by user to a configured local or external path
-	User can process printing by sending Get request on Webapp server to process printing
-	Webapp will scan files on the configured local or external path
-	Webapp will create a temp zpl files for every XML files generated
-	Webapp will send zpl files on respective printers
-	Webapp will delete files after processing

2	Requirements

2.1	Infor WMS
-	Set WM_PRINT_LABELS to YES
-	If XML will be saved on infor server, set WM_LABEL_STAGE and WM_LABEL_PRINT paths
-	If XML will be using FTP, set WM_LABEL_USE_FTP, FTP.WM_FTP_SERVER, FTP.WM_USER_ID, FTP.WM_PASSWORD, FTP.WM_REMOTE_DIR, FTP.WM_INTERVAL, FTP.WM_ENCODING
-	Refer to
https://docs.infor.com/wms/2022.x/en-us/useradminlib/default.html?helpcontent=sceprintug/mak1612893771400.html


2.2	Mobile Printers
-	Set the SGD "weblink.ip.conn1.location" to https://<FQDN>:443/zebra/weblink/", where <FQDN> is your development machine's fully qualified domain name on your DNS server
-	Upgrade Link OS version to latest
-	Use Specific Label size, properly calibrated
-	Configure Wireless or LAN connection, and DNS to connect on WEBAPP server
-	Copy label files, SSL key (HTTPS_Cert.NRD, HTTPS_Key.NRD, WEBLINK1_CA.NRD)
-	Only Server should be using the printer ports
-	Upload ZPL template to be used
  
2.3	RF Scanner
-	Run infor on supported browser
-	Has a shortcut trigger function for Webapp server to process printing
-	Install SSL key for webapp server
  
2.4	DNS Server
-	Set proper routing for webapp server
  
2.5	Webapp Server
-	Must be accessible to all RF and mobile printer
-	Has connection to specified Infor local/FTP folder
-	Generate SSL key with full qualified Domain name for mobile printers and RF Scanner
-	Install Tomcat Apache Tomcat/9.0.86, JVM Version 18.0.1.1+2-6, Windows 10
-	Set autorun service
-	Deploy Webapp Warfile
-	Set Apache Software Foundation\Tomcat 9.0\conf\server.xml
```xml
<Connector SSLEnabled="true" acceptorThreadCount="5" clientAuth="false" keyAlias="tomcat" keystoreFile="REPLACEPATH/certROOTNAME.p12" keystorePass="REPLACEPASSWORD" keystoreType="pkcs12" maxConnections="-1" maxThreads="2500" port="443" protocol="org.apache.coyote.http11.Http11NioProtocol" scheme="https" secure="true" sessionTimeout="0" socket.soKeepAlive="true" sslProtocol="TLS"/>
```


2.6	Label XML
-	Must contain printer serial to be used
-	Must be in xml format
Sample XML
```xml
<LABELS>
	<Header>
		<Serial>XXRBJ194601164</Serial>
		<QUANTITY>1</QUANTITY>
	</Header>

	<Data>
		<TYPE> STOCK</TYPE>
		<STORECODE>214 </STORECODE>
		<DATE>03-FEB-2024</DATE>
		<STORENAME>RSC PAMPANGA</STORENAME>
		<QTY>12</QTY>
		<BAR>XXXX</BAR>
	</Data>

	<Data>
		<TYPE> XDOCK</TYPE>
		<STORECODE>2124 </STORECODE>
		<DATE>03-FEB-2024</DATE>
		<STORENAME>RSC PAMPANGA</STORENAME>
		<QTY>12</QTY>
		<BAR>XXXX</BAR>
	</Data>
</LABELS>
```

3	For Improvement

3.1	XML parsing

3.2	Selection of printer

3.3	Validation

3.4	Alert Errors


