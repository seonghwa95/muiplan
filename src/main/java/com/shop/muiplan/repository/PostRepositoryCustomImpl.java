package com.shop.muiplan.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.muiplan.domain.Item;
import com.shop.muiplan.domain.QItem;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> getList(int page) {
        return jpaQueryFactory.selectFrom(QItem.item)
                .limit(10)
                .offset((long) (page - 1) * 10)
                .orderBy(QItem.item.id.desc())
                .fetch();
    }
}
