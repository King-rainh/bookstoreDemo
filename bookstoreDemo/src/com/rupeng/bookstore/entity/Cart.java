package com.rupeng.bookstore.entity;

import java.util.ArrayList;
import java.util.List;

//购物车，存放在内存中，并不存储在数据库中
public class Cart {

    private List<CartItem> cartItemList = new ArrayList<CartItem>();// 购物车中的购物车项

    // 追加购物车项，如果原本不在购物车中，则新增，如果原本在，则追加数量
    public void addItem(Book book, int count) {
        for (CartItem cartItem : cartItemList) {
            if (book.getId().equals(cartItem.getBookId())) {
                cartItem.setCount(cartItem.getCount() + count);
                return;
            }
        }
        // 如果这里面没有这个图书的信息
        CartItem cartItem = new CartItem();
        cartItem.setBookId(book.getId());
        cartItem.setBookName(book.getName());
        cartItem.setCount(count);
        cartItem.setPrice(book.getPrice());

        cartItemList.add(cartItem);
    }

    // 根据bookId删除购物车中的某个购物车项
    public void deleteItem(int bookId) {
        for (CartItem cartItem : cartItemList) {
            if (bookId == cartItem.getBookId()) {
                cartItemList.remove(cartItem);
                return;
            }
        }
    }

    // 修改购物车项中的图书数量
    public void updateItem(int bookId, int count) {
        for (CartItem cartItem : cartItemList) {
            if (bookId == cartItem.getBookId()) {
                cartItem.setCount(count);
                return;
            }
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getTotalPrice();
        }
        return totalPrice;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public int getTotalCount() {
        int totalCount = 0;
        for (CartItem cartItem : cartItemList) {
            totalCount += cartItem.getCount();
        }
        return totalCount;
    }

}
