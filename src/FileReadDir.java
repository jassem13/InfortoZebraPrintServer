

import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.device.ZebraIllegalArgumentException;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.remote.comm.RemoteConnection;


import com.zebra.sdk.printer.ZebraPrinterLinkOs;


import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.zebra.sdk.settings.Setting;
import com.zebra.sdk.settings.SettingsException;


import java.io.BufferedWriter;

import java.io.FileWriter;
/**
 * Servlet implementation class FileReadDir
 */
@WebServlet("/FileReadDir")
public class FileReadDir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	

	    
    public FileReadDir() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void listFilesForFolder(final File folder) {
		int filenumber = 0;
//		FileInputStream stream = null;
	    for (final File fileEntry : folder.listFiles()) {
	    	
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	           
	        } else {
	        	
	        	filenumber += 1;
	     //declare variables
	        	String pserialNumber=null;
	        	int pQuantity=0;
	        	String pTYPE=null;
	        	String pSTORECODE=null;
	        	String pSTORENAME=null;
	        	String pDATE=null;
	        	String pQTY=null;
	        	String pBAR=null;
	        	
// File Stream Read      
//	            try {
//	                stream = new FileInputStream(fileEntry.getPath());
//	            } catch (FileNotFoundException e) {
//	                e.printStackTrace();
//	            }
//	            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//	            String strLine;
//	            ArrayList<String> lines = new ArrayList<String>();
//	            try {
//	                while ((strLine = reader.readLine()) != null) {
//	                    String lastWord = strLine.substring(strLine.lastIndexOf(" ")+1);
//	                    lines.add(lastWord);
//	                }
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
//	            try {
//	                reader.close();
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	            }
	        	try {
	                File inputFile = new File(fileEntry.getPath());
	                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	                Document doc = dBuilder.parse(inputFile);
	                doc.getDocumentElement().normalize();
	                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	               
	                NodeList nList2 = doc.getElementsByTagName("Header");
	                Node nNode2 = nList2.item(0);
	                   System.out.println("\nCurrent Element :" +Node.ELEMENT_NODE+nNode2.getNodeType()+nList2.getLength() + nNode2.getNodeName());
	              
	                   
		                   
		                   
		                   if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
		                      Element eElement2 = (Element) nNode2;
		                      pserialNumber = eElement2.getElementsByTagName("Serial").item(0).getTextContent();
		                      pQuantity = Integer.parseInt(eElement2.getElementsByTagName("QUANTITY").item(0).getTextContent());
		                      System.out.println("SERIAL : " + pserialNumber);
		                      System.out.println("QUANTITY : " + pQuantity);
		                      
		                   }
	                
	                System.out.println("----------------------------");
	                NodeList nList = doc.getElementsByTagName("Data");
	                for (int temp = 0; temp < nList.getLength(); temp+=1) {
	                   Node nNode = nList.item(temp);
	                   System.out.println("\nCurrent Element :" +nList.getLength()+temp + nNode.getNodeName());
	                   
	                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                      Element eElement = (Element) nNode;
	                      pTYPE=eElement.getElementsByTagName("TYPE").item(0).getTextContent();
	      	        	pSTORECODE=eElement.getElementsByTagName("STORECODE").item(0).getTextContent();
	      	        	pDATE=eElement.getElementsByTagName("DATE").item(0).getTextContent();
	      	        	pSTORENAME=eElement.getElementsByTagName("STORENAME").item(0).getTextContent();
	      	        	pQTY=eElement.getElementsByTagName("QTY").item(0).getTextContent();
	      	        	pBAR=eElement.getElementsByTagName("BAR").item(0).getTextContent();
//	                      System.out.println("TYPE : " 
//	                         + eElement.getAttribute("rollno"));
	                      System.out.println("TYPE : "  + pTYPE);
	                      System.out.println("STORECODE : " + pSTORECODE);
	                      System.out.println("DATE : " + pDATE);
	                      System.out.println("STORENAME : " + pSTORENAME);
	                      System.out.println("QTY : " + pQTY);
	                      System.out.println("BAR : " + pBAR);
	                          String NAME_BADGE_FORMAT = 
	                        		  "^XA\n"+
	                        		  "^XFE:STORE.ZPL^FS\n"+
	                        		  "^FN1^FD"+pTYPE+"^FS \n"+
	                        		  "^FN2^FD"+pSTORECODE+"^FS\n"+
	                        		  "^FN3^FD"+pDATE+"^FS\n"+
	                        		  "^FN4^FD"+pQTY+"^FS\n"+
	                        		  "^FN5^FD"+pSTORENAME+"^FS\n"+
	                        		  "^FN6^FDMA0"+pBAR+"^FS\n"+
	                        		  "^FN7^FD"+pBAR+"^FS\n"+
	                        		  "^PQ"+pQuantity+"\n"+
	                        		  "^XZ\n";
	                          try{
	                              // Create new file
	                              String content = NAME_BADGE_FORMAT;
	                              String path="E:/PrintZPLTest/temp.zpl";
	                              File file = new File(path);

	                              // If file doesn't exists, then create it
	                              if (!file.exists()) {
	                                  file.createNewFile();
	                              }

	                              FileWriter fw = new FileWriter(file.getAbsoluteFile());
	                              BufferedWriter bw = new BufferedWriter(fw);

	                              // Write in file
	                              bw.write(content);

	                              // Close connection
	                              bw.close();
	                          }
	                          catch(Exception e){
	                              System.out.println(e);
	                          } 
	                          try {
	                  			//Get the serial number off the request.
	                  			String serialNumber = pserialNumber;
	                  			//Create a RemoteConnection on port 11995
	                  			RemoteConnection connection = new RemoteConnection(serialNumber, 11995);
	                  			connection.open();
	                  			
	                  			ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
	                  			//Decorate the printer as a LinkOsPrinter and print the configuration label.
	                  			printer.sendFileContents("E:/PrintZPLTest/temp.zpl");
	                              String[] fileNames = printer.retrieveFileNames();
	                              for (String filename : fileNames) {
	                                  System.out.println(filename);
	                              }
	                              } catch (ConnectionException e) {
	                                  e.printStackTrace();
	                              } catch (ZebraPrinterLanguageUnknownException e) {
	                                  e.printStackTrace();
	                              } catch (ZebraIllegalArgumentException e) {
	                                  e.printStackTrace();
	                  	}
	                   }
	                }
	             } catch (Exception e) {
	                e.printStackTrace();
	             }
	            
	            
	            System.out.println(fileEntry.getPath() + filenumber);
	            
//	            File myObj = new File(fileEntry.getPath()); 
//	            if (myObj.delete()) { 
//	              System.out.println("Deleted the file: " + myObj.getName());
//	            } else {
//	              System.out.println("Failed to delete the file."+ myObj.getName());
//	            } 
	        }
	    }
	}
	 
	 


	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final File folder = new File("E:/PrintFolderTest");
		listFilesForFolder(folder);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
