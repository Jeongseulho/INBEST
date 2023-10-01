package com.jrjr.inbest.trading.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.trading.entity.Trading;
import com.jrjr.inbest.user.dto.IndustryDTO;

@Repository
public interface TradingRepository extends CrudRepository<Trading, Long> {

	@Query(
		"SELECT NEW com.jrjr.inbest.user.dto.IndustryDTO(fc.companyIndustry, sum(t.price * t.amount)) "
			+ "FROM Trading t "
			+ "         JOIN FinancialdataCompany fc "
			+ "              ON t.stockType = fc.companyStockType "
			+ "                  AND t.stockCode = fc.companyStockCode "
			+ "WHERE t.userSeq = :userSeq "
			+ "  AND t.tradingType = 1 "
			+ "  AND t.conclusionType = 1 "
			+ "GROUP BY fc.companyIndustry")
	List<IndustryDTO> calculatePurchaseAmountByIndustry(@Param("userSeq") Long userSeq);

	@Query("SELECT fc.companyIndustry "
		+ "FROM Trading t "
		+ "         JOIN FinancialdataCompany fc "
		+ "              ON t.stockType = fc.companyStockType "
		+ "                  AND t.stockCode = fc.companyStockCode "
		+ "WHERE t.userSeq = :userSeq "
		+ "  AND t.simulationSeq = :simulationSeq "
		+ "  AND t.tradingType = 1 "
		+ "  AND t.conclusionType = 1 "
		+ "GROUP BY fc.companyIndustry "
		+ "ORDER BY sum(t.price * t.amount) DESC")
	List<String> getTopNIndustry(@Param("simulationSeq") Long simulation_seq, @Param("userSeq") Long user_seq,
		Pageable pageable);
}
