package com.example.cgc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tc_detail_sale")
public class TcDetailSale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long detailDetailSaleId;

	@NotNull
	private byte statusId;

	@ManyToOne
	@JoinColumn(name = "sale_id")
	@JsonBackReference
	private TcSale tcSale;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private TcProduct tcProduct;

	private float price;

	private float amount;

	private float total;

	public long getDetailDetailSaleId() {
		return detailDetailSaleId;
	}

	public void setDetailDetailSaleId(long detailDetailSaleId) {
		this.detailDetailSaleId = detailDetailSaleId;
	}

	public byte getStatusId() {
		return statusId;
	}

	public void setStatusId(byte statusId) {
		this.statusId = statusId;
	}

	public TcSale getTcSale() {
		return tcSale;
	}

	public void setTcSale(TcSale tcSale) {
		this.tcSale = tcSale;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public TcProduct getTcProduct() {
		return tcProduct;
	}

	public void setTcProduct(TcProduct tcProduct) {
		this.tcProduct = tcProduct;
	}

}
