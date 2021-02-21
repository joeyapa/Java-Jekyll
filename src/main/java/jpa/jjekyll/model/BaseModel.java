package jpa.jjekyll.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author joeyapa
 */
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -7451825533801856555L;
	
	protected String id;
	protected String status;
	protected String json;
	protected Integer versionNo;
	protected String lastupdateBy;
	protected Date lastupdateDate;
	protected String createBy;
	protected Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public String getLastupdateBy() {
		return lastupdateBy;
	}

	public void setLastupdateBy(String lastupdateBy) {
		this.lastupdateBy = lastupdateBy;
	}

	public Date getLastupdateDate() {
		return lastupdateDate;
	}

	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}

	public String getCreatedBy() {
		return createBy;
	}

	public void setCreatedBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
