package com.example.cgc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tc_client")
public class TcClient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long clientId;

	@NotNull
	private String clientDesc;

	@NotNull
	private byte statusId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private TcUser tcUser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	private String nit;

	@NotNull
	private String address;

	private String phone;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date birthday;

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public TcUser getTcUser() {
		return tcUser;
	}

	public void setTcUser(TcUser tcUser) {
		this.tcUser = tcUser;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
