package com.jrjr.inbest.board.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.BoardImgDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;
import com.jrjr.inbest.board.entity.UserEntity;
import com.jrjr.inbest.board.repository.BoardImgRepository;
import com.jrjr.inbest.board.repository.BoardRepository;
import com.jrjr.inbest.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
	private final BoardRepository boardRepository;
	private final BoardImgRepository boardImgRepository;
	private final AmazonS3 amazonS3;
	private final UserService userService;
	private final UserRepository userRepository;

	@Value(value = "${cloud.aws.s3.bucket}")
	private String bucketName;

	@Value(value = "${cloud.aws.s3.uri}")
	private String bucketUrl;

	public void insertBoard(BoardDTO boardDTO) throws Exception {
		BoardEntity board = boardDTO.toBoardEntity();
		log.info(board.toString());
		ArrayList<BoardImgEntity> boardImgEntities = new ArrayList<>();

		for(MultipartFile file : boardDTO.getFiles()){
			BoardImgDTO boardImgDTO = new BoardImgDTO();
			String originalName = "";
			File backgroundImgFile;
			String backgroundImgSearchName="";
			UUID uuid = UUID.randomUUID();
			String extend = "";

			originalName = file.getOriginalFilename();
			extend = originalName.substring(originalName.lastIndexOf('.'));
			// #2 - 원본 파일 이름 저장
			boardImgDTO.setOriginalName(originalName.substring(0,originalName.indexOf('.')));
			boardImgDTO.setExtend(extend);

			// #3 - 저장용 랜덤 파일 이름 저장
			backgroundImgSearchName = uuid.toString();

			// #4 - 파일 임시 저장
			//파일이 있으면 임시 파일 저장
			backgroundImgFile = File.createTempFile(backgroundImgSearchName,extend);
			FileUtils.copyInputStreamToFile(file.getInputStream(),backgroundImgFile);
			//5 - 이미지 서버 저장
			amazonS3.putObject(bucketName, "board/"+backgroundImgSearchName+extend, backgroundImgFile);
			// #6 - DB 저장
			boardImgDTO.setSearchName(backgroundImgSearchName);
			BoardImgEntity boardImgEntity = boardImgDTO.toBoardImgEntity();
			boardImgRepository.save(boardImgEntity);
			boardImgEntities.add(boardImgEntity);

			backgroundImgFile.delete();
		}

		board.setImgList(boardImgEntities);

		boardRepository.save(board);
	}
	public List<BoardDTO> findAllBoards(int page,int size){
		Page<BoardEntity> boardEntityList = boardRepository.findAll(
			PageRequest.of(page-1,size, Sort.Direction.DESC,"createdDate"));
		List<BoardDTO> boardDTOList = new ArrayList<>();

		for(BoardEntity boardEntity : boardEntityList.getContent()){
			BoardDTO boardDTO = boardEntity.toBoardDTO();
			if(boardDTO.getUserSeq() !=null){
				boardDTO.setWriter(userService.findBySeq(boardDTO.getUserSeq()));
			}
			boardDTOList.add(boardEntity.toBoardDTO());
		}

		return boardDTOList;
	}
	public BoardDTO findBySeq(String id){
		BoardEntity boardEntity= boardRepository.findById(id).orElse(null);

		if(boardEntity == null){
			return new BoardDTO();
		}

		UserDTO userDTO = userService.findBySeq(boardEntity.getUserSeq());
		BoardDTO boardDTO = boardEntity.toBoardDTO();
		boardDTO.setWriter(userDTO);

		return boardDTO;
	}
	public BoardDTO updateLikes(Long userSeq,String boardId)throws Exception {
		BoardEntity boardEntity= boardRepository.findById(boardId).orElse(null);

		//게시물 있는지 확인
		if(boardEntity == null){
			throw new Exception("대상 게시물이 없습니다.");
		}

		UserEntity userEntity = userRepository.findBySeq(userSeq);
		//유저가 있는지 확인
		if(userEntity== null){
			throw new Exception("대상 유저가 없습니다.");
		}

		boardEntity.updateLikeUserList(userEntity);
		boardRepository.save(boardEntity);
		return boardEntity.toBoardDTO();
	}
	public List<BoardDTO> findPopularPosts(int pageSize, int period) {
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.minusDays(period);

		// 좋아요(likes)가 많은 순으로 정렬하고 상위 10개를 가져옵니다.
		PageRequest pageRequest =
			PageRequest.of(0, pageSize, Sort.by(Sort.Direction.DESC, "likes"));

		List<BoardEntity> boardEntityList = boardRepository.findByCreatedDateBetween(start, end, pageRequest);

		ArrayList<BoardDTO> boardDTOAList = new ArrayList<>();

		for(BoardEntity boardEntity : boardEntityList){
			boardDTOAList.add(boardEntity.toBoardDTO());
		}

		return boardDTOAList;
	}
}