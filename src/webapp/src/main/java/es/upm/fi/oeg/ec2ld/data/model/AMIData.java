package es.upm.fi.oeg.ec2ld.data.model;

public class AMIData {
	
	public AMIData (String id, String url) {
		this.id = id;
		this.url = url;
	}
	
	private String id;
	private String url;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
