package com.example.cgc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tc_sale")
public class TcSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long saleId;

	@NotNull
	private byte statusId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();

	@ManyToOne
	@JoinColumn(name = "client_id")
	private TcClient tcClient;

	private float total;

	private float iva;

	@Transient
	List<TcDetailSale> detail;

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public TcClient getTcClient() {
		return tcClient;
	}

	public void setTcClient(TcClient tcClient) {
		this.tcClient = tcClient;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getIva() {
		return iva;
	}

	public void setIva(float iva) {
		this.iva = iva;
	}

	public List<TcDetailSale> getDetail() {
		return detail;
	}

	public void setDetail(List<TcDetailSale> detail) {
		this.detail = detail;
	}

}
