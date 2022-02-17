package com.enode.myapplicationjava;

public class PhoneEvent {

  private int code;
  private String phone;

  public PhoneEvent(int code, String phone) {
    this.code = code;
    this.phone = phone;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
