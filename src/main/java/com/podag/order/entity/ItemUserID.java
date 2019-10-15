//package com.podag.order.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.util.Objects;
//
//@Embeddable
//public class ItemUserID {
//    @Column(name = "itemId")
//    private int ItemID;
//    @Column(name = "username")
//    private String username;
//
//    public ItemUserID(){
//    }
//    public ItemUserID(int ItemID, String username){
//      this.ItemID = ItemID;
//      this.username = username;
//    }
//
//    public int getItemId() {
//        return ItemID;
//    }
//
//    public void setItemId(int itemId) {
//        this.ItemID = itemId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ItemUserID that = (ItemUserID) o;
//        return ItemID == that.ItemID &&
//                username.equals(that.username);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(ItemID, username);
//    }
//}
