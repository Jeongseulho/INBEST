package com.jrjr.invest.invest.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jrjr.invest.invest.dto.RequestHanTuAccessTokenDTO;
import com.jrjr.invest.invest.dto.ResponseHanTuAccessTokenDTO;
import com.jrjr.invest.rank.service.SimulationRankService;
import com.jrjr.invest.simulation.dto.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.GroupDTO;
import com.jrjr.invest.simulation.dto.InProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.JoinableGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.MyInProgressGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.MyWaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.dto.RedisSimulationUserDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.dto.WaitingGroupDetailsDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.entity.SimulationUser;
import com.jrjr.invest.simulation.entity.User;
import com.jrjr.invest.simulation.repository.LoginHistoryRepository;
import com.jrjr.invest.simulation.repository.SimulationRepository;
import com.jrjr.invest.simulation.repository.SimulationUserRepository;
import com.jrjr.invest.simulation.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class InvestService {
	@Value("${token.hantu.appkey}")
	String appkey;
	@Value("${token.hantu.appsecret}")
	String appsecret;

	private final RedisTemplate<String, ResponseHanTuAccessTokenDTO> hanTuAccessTokenRedisTemplate;

	public ResponseHanTuAccessTokenDTO getAccessToken() throws Exception {
		log.info("엑세스 토큰 발급 시작");

		HashOperations<String,String,ResponseHanTuAccessTokenDTO> hashOperations = hanTuAccessTokenRedisTemplate.opsForHash();
		ResponseHanTuAccessTokenDTO accessToken = hashOperations.get("APIaccessToken","HanTu");

		if(accessToken == null){
			log.info("엑세스 토큰 재발급 시작");
			String accessTokenUrl = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

			RequestHanTuAccessTokenDTO requestHanTuAccessToken
				= RequestHanTuAccessTokenDTO.builder()
				.appkey(appkey)
				.grant_type("client_credentials")
				.appsecret(appsecret)
				.build();

			WebClient webClient = WebClient.create();

			accessToken = webClient.post()
				.uri(accessTokenUrl)
				.bodyValue(requestHanTuAccessToken)
				.retrieve()
				.bodyToMono(ResponseHanTuAccessTokenDTO.class)
				.block();

			if(accessToken == null){
				throw new Exception("엑세스 토큰 발급을 실패했습니다.");
			}

			//redis에 엑세스토큰 저장(연산 시간을 고려하여 20초 빼기)
			hanTuAccessTokenRedisTemplate.expire("APIaccessToken",accessToken.getExpires_in()-20, TimeUnit.SECONDS);
			hashOperations = hanTuAccessTokenRedisTemplate.opsForHash();
			hashOperations.put("APIaccessToken","HanTu",accessToken);
		}

		return accessToken;
	}
}
