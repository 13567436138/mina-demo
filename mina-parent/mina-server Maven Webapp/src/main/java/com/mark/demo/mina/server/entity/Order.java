package com.mark.demo.mina.server.entity;

/*
*hxp(huang.xp@topcheer.com)
*2017年9月2日
*
*/
public class Order {
	private String item;
	private Double price;
	private int number;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
