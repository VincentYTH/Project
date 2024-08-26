package com.bootcamp.project.project_yahoo_finance.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.project.project_yahoo_finance.entity.StocksListEntity;

@Repository
public interface StocksListRepository extends JpaRepository<StocksListEntity, Long> {
  
  List<StocksListEntity> findBySymbol(String symbol);
}
