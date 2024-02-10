package com.shop.muiplan.repository;

import com.shop.muiplan.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Item, Long> {

}
