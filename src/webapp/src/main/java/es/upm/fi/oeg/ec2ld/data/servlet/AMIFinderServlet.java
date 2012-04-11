package es.upm.fi.oeg.ec2ld.data.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.fi.oeg.ec2ld.data.model.AMIData;

public class AMIFinderServlet extends HttpServlet {

	private static final long serialVersionUID = -268795200170815135L;
	
	private static Map<String, Map<String, List<AMIData>>> dataSet;
	
	static {
		dataSet = new HashMap<String, Map<String, List<AMIData>>>();
		
		Map<String, List<AMIData>> windows = new HashMap<String, List<AMIData>>();
		List<AMIData> win64 = new ArrayList<AMIData>();
		win64.add(new AMIData("ami-b73ae4de", "https://aws.amazon.com/amis/windows-server-2008-r2-sp1-english-64bit-windows-media-services-4-1-2012-03-15"));
		win64.add(new AMIData("ami-403ee229", "https://aws.amazon.com/amis/amazon-public-images-windows-server-2008-64-bit-with-sql-server-2008-express-iis-asp-net"));		
		windows.put("64", win64);
		
		List<AMIData> win32 = new ArrayList<AMIData>();
		win32.add(new AMIData("ami-443fe32d","https://aws.amazon.com/amis/amazon-public-images-basic-microsoft-windows-server-2008-32-bit"));
		
		windows.put("64-bit", win64);
		windows.put("32-bit", win32);
		
		dataSet.put("Windows", windows);
		
		Map<String, List<AMIData>> ubuntu = new HashMap<String, List<AMIData>>();
		List<AMIData> ubuntu64 = new ArrayList<AMIData>();
		List<AMIData> ubuntu32 = new ArrayList<AMIData>();
		
		ubuntu64.add(new AMIData("ami-87f225ee", "https://aws.amazon.com/amis/bitnami-liferay-stack-6-1-0-0-ebs-64bits-ubuntu-10-04-1331172825"));
		
		ubuntu32.add(new AMIData("ami-b1ae79d8", "https://aws.amazon.com/amis/bitnami-liferay-stack-6-1-0-0-ubuntu-10-04"));
		
		ubuntu.put("64-bit", ubuntu64);
		ubuntu.put("32-bit", ubuntu32);
		
		dataSet.put("Ubuntu", ubuntu);
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String os = request.getParameter("os");
		String arch = request.getParameter("arch");
		
		List<AMIData> amiList = getAMIList(os, arch);
		String xml = serializeXML(amiList);

		PrintWriter out = response.getWriter();
		out.println(xml);
		out.flush();
		out.close();
	}
	

	
	private List<AMIData> getAMIList(String os, String arch) {
		
		Map<String, List<AMIData>> amiListForOS = dataSet.get(os);
		
		if (amiListForOS == null) {
			return new ArrayList<AMIData>();
		}
		
		List<AMIData> amiListForArch = amiListForOS.get(arch);
		if (amiListForArch == null) {
			return new ArrayList<AMIData>();
		} else {
			return amiListForArch;
		}
	}
	
	/*
	 * Serialize the AMI data 
	 * @param amiData
	 * @return serializedXML as a String
	 */
	
	private String serializeXML(List<AMIData> amiData) {
		
		/*
		 * Example XML
		 * <results>
	     * 		<ami>
		 *			<id>ami-1af12273</id>
		 *			<url>https://aws.amazon.com/amis/panda-project-0-1-0</url>
		 *		</ami>
		 * </results>
		 * 
		 */
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		xml += "<results>";
		
		for (AMIData ami : amiData) {
			xml += "<ami>";
			xml += "<id>"+ ami.getId() + "</id>";
			xml += "<url>"+ ami.getUrl() + "</url>";
			xml +="</ami>";			
		}
		
		xml += "</results>";
		return xml;
		
	}


}
