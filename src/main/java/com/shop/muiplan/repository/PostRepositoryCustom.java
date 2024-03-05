package com.shop.muiplan.repository;

import com.shop.muiplan.domain.Item;
import com.shop.muiplan.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    public List<Item> getList(int page);

    public List<Item> getList(PostSearch postSearch);
}
