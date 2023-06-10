package com.matheuscordeiro.servicereaderjob.domain;

import java.util.List;

public class ResponseUser {
	public List<User> data;

	public ResponseUser() {

	}

	public List<User> getData() {
		return data;
	}

	public void setData(List<User> data) {
		this.data = data;
	}
}
