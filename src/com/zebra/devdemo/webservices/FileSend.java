package com.zebra.devdemo.webservices;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.device.ZebraIllegalArgumentException;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.remote.comm.RemoteConnection;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileSend
 */
@WebServlet("/FileSend")
public class FileSend extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Get the serial number off the request.
			String serialNumber = request.getParameter("serialNumber");
			//Create a RemoteConnection on port 11995
			RemoteConnection connection = new RemoteConnection(serialNumber, 11995);
			connection.open();
			
			ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
			//Decorate the printer as a LinkOsPrinter and print the configuration label.
			printer.sendFileContents("//192.168.100.166/Users/Public/Downloads/Labels-Sample.pdf");
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

