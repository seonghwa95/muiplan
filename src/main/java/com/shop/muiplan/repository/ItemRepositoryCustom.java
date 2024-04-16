package com.shop.muiplan.repository;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.request.ItemSearch;

import java.util.List;

public interface ItemRepositoryCustom {

    public List<Item> getList(int page);

    public List<Item> getList(ItemSearch itemSearch);
}
