package com.ecom.entity;

public class OrderStatusUpdateEntity {
	    private Integer orderId;
	    private String status;
	    private String item;
	    private String data;

		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public Integer getOrderId() {
			return orderId;
		}
		public String getItem() {
			return item;
		}
		public void setItem(String item) {
			this.item = item;
		}
		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

}
