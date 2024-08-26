package com.bootcamp.project.project_yahoo_finance.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bootcamp.project.project_yahoo_finance.entity.HistoryStockEntity;

@Repository
public interface HistoryStockRepository extends JpaRepository<HistoryStockEntity, Long> {
  
  List<HistoryStockEntity> findBySymbol(String symbol);
}
