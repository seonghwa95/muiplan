package com.shop.muiplan.repository;

import com.shop.muiplan.domain.Item;

import java.util.List;

public interface PostRepositoryCustom {

    public List<Item> getList(int page);
}
