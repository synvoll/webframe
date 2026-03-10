package com.pharossolutions.svnkitint.models;

public class FormInput {

	private String  packetName;
	private String  version;
	private String  dependencies;
	private boolean serverDeployment;
	private boolean clientDeployment;
	private boolean citrixTsDeployment;
	private String  comment;
	private String  packetType;
	private boolean unifa;
	private boolean fa;
	private boolean fust;
	private boolean gst;
	private boolean sonderpc;
	private boolean entwicklerpc;
	private boolean bap;
	private boolean pruefernb;
	private boolean kassennb;
	private boolean vergnuegungssteuernb;
	private boolean others;
	private boolean update;

	public String getPacketName() {
		return packetName;
	}

	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getDependencies() {
		return dependencies;
	}

	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}

	public boolean isServerDeployment() {
		return serverDeployment;
	}
	
	public boolean isClientDeployment() {
		return clientDeployment;
	}
	
	public boolean isCitrixTsDeployment() {
		return citrixTsDeployment;
	}

	public void setServerDeployment(boolean serverDelivery) {
		this.serverDeployment = serverDelivery;
	}
	
	public void setClientDeployment(boolean clientDelivery) {
		this.clientDeployment = clientDelivery;
	}
	
	public void setCitrixTsDeployment(boolean citrixTsDelivery) {
		this.citrixTsDeployment = citrixTsDelivery;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}	
	
	public String getPacketType() {
		return packetType;
	}

	public void setPacketType(String packetType) {
		this.packetType = packetType;
	}

	public boolean isUnifa() {
		return unifa;
	}

	public void setUnifa(boolean unifa) {
		this.unifa = unifa;
	}

	public boolean isFa() {
		return fa;
	}

	public void setFa(boolean fa) {
		this.fa = fa;
	}

	public boolean isFust() {
		return fust;
	}

	public void setFust(boolean fust) {
		this.fust = fust;
	}

	public boolean isGst() {
		return gst;
	}

	public void setGst(boolean gst) {
		this.gst = gst;
	}

	public boolean isSonderpc() {
		return sonderpc;
	}

	public void setSonderpc(boolean sonderpc) {
		this.sonderpc = sonderpc;
	}

	public boolean isEntwicklerpc() {
		return entwicklerpc;
	}

	public void setEntwicklerpc(boolean entwicklerpc) {
		this.entwicklerpc = entwicklerpc;
	}

	public boolean isBap() {
		return bap;
	}

	public void setBap(boolean bap) {
		this.bap = bap;
	}

	public boolean isPruefernb() {
		return pruefernb;
	}

	public void setPruefernb(boolean pruefernb) {
		this.pruefernb = pruefernb;
	}

	public boolean isKassennb() {
		return kassennb;
	}

	public void setKassennb(boolean kassennb) {
		this.kassennb = kassennb;
	}

	public boolean isVergnuegungssteuernb() {
		return vergnuegungssteuernb;
	}

	public void setVergnuegungssteuernb(boolean vergnuegungssteuernb) {
		this.vergnuegungssteuernb = vergnuegungssteuernb;
	}

	public boolean isOthers() {
		return others;
	}

	public void setOthers(boolean others) {
		this.others = others;
	}
	
	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
}
