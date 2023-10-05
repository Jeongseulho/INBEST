package com.jrjr.invest.invest.socket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.jrjr.invest.invest.entity.FinancialDataCompany;
import com.jrjr.invest.invest.repository.FinancialDataCompanyRepository;
import com.jrjr.invest.invest.service.InvestService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class USATradingPriceSocket {
	private String approvalkey = "";
	private final USATradingPriceSocketHander usaTradingPriceSocketHander;
	private final FinancialDataCompanyRepository financialDataCompanyRepository;
	private final InvestService investService;
	WebSocketSession session;
	@PostConstruct
	public void init() throws Exception {
		approvalkey = investService.getSocketAccessKey();
		StandardWebSocketClient client = new StandardWebSocketClient();
		session = client.doHandshake(usaTradingPriceSocketHander,
			"ws://ops.koreainvestment.com:21000/tryitout/HDFSASP0").get();
		sendMessage();
	}

	// @Scheduled(cron = "0 * * * * ?")
	public void sendMessage() throws Exception {
		log.info("");
		if(session == null){
			approvalkey = investService.getSocketAccessKey();
			StandardWebSocketClient client = new StandardWebSocketClient();
			session = client.doHandshake(usaTradingPriceSocketHander,
				"ws://ops.koreainvestment.com:21000/tryitout/HDFSASP0").get();
			log.info("연결되지 않은 소켓입니다.");
			return ;
		}
		List<FinancialDataCompany> financialDataCompanyList = financialDataCompanyRepository.findAllByCompanyRealIndustryCode("NAS");

		for(FinancialDataCompany company : financialDataCompanyList){
			log.info(company.getCompanyStockCode()+"호가 요청");
				String tr_key = "DNAS"+company.getCompanyStockCode();

				String sendData = String.format(
					"{\"header\"	"
						+ ":{\"approval_key\":\"%s\",\"custtype\":\"%s\",\"tr_type\":\"%s\",\"content-type\":\"utf-8\"}," +
						"\"body\""
						+ ":{\"input\":{\"tr_id\":\"%s\",\"tr_key\":\"%s\"}"
						+ "}"
						+ "}",
					approvalkey, "P", "1", "HDFSASP0",tr_key
				);

				TextMessage message = new TextMessage(sendData);
				session.sendMessage(message);
			 tr_key = "RBAQ"+company.getCompanyStockCode();

			 sendData = String.format(
				"{\"header\"	"
					+ ":{\"approval_key\":\"%s\",\"custtype\":\"%s\",\"tr_type\":\"%s\",\"content-type\":\"utf-8\"}," +
					"\"body\""
					+ ":{\"input\":{\"tr_id\":\"%s\",\"tr_key\":\"%s\"}"
					+ "}"
					+ "}",
				approvalkey, "P", "1", "HDFSASP0",tr_key
			);

			message = new TextMessage(sendData);
			session.sendMessage(message);
				Thread.sleep(10000);
		}
	}
}

