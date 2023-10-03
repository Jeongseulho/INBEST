package com.jrjr.invest.invest.socket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.jrjr.invest.trading.entity.FinancialDataCompany;
import com.jrjr.invest.trading.repository.FinancialDataCompanyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class USATradingPriceSocket {
	private String approvalkey = "b45e080c-f5bb-4a26-9ee6-fc59320b8224";
	private final USATradingPriceSocketHander usaTradingPriceSocketHander;
	private final FinancialDataCompanyRepository financialDataCompanyRepository;

	WebSocketSession session;
	@PostConstruct
	public void init() throws IOException, ExecutionException, InterruptedException {
		sendMessage();
	}

	@Scheduled(cron = "0 0/5 * * * ?")
	public void sendMessage() throws ExecutionException, InterruptedException, IOException {
		if(session == null){
			StandardWebSocketClient client = new StandardWebSocketClient();
			session = client.doHandshake(usaTradingPriceSocketHander,
				"ws://ops.koreainvestment.com:21000/tryitout/HDFSASP0").get();
			log.info("연결되지 않은 소켓입니다.");
			return ;
		}
		List<FinancialDataCompany> financialDataCompanyList = financialDataCompanyRepository.findAllByCompanyRealIndustryCode("Nas");

		String[] tr_key_prefixs = {"DNYS","DNAS","DAMS","RBAY","RBAQ","RBAA"};

		for(FinancialDataCompany company : financialDataCompanyList){
			log.info(company.getCompanyStockCode()+"호가 요청");
			for(String tr_key_prefix : tr_key_prefixs){
				String tr_key = tr_key_prefix+company.getCompanyStockCode();

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
			}
		}
	}
}
