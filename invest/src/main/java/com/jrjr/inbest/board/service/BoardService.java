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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.BoardImgDTO;
import com.jrjr.inbest.board.dto.CommentDTO;
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

	public Long countBoard(String keyword) {
		Long count = 0L;

		if (keyword.equals("")) {
			count = boardRepository.count();
		} else {
			count = boardRepository.countByTitleContainingOrContextContaining(keyword, keyword);
		}

		return count;
	}

	@Transactional
	public void insertBoard(BoardDTO boardDTO) throws Exception {
		BoardEntity board = boardDTO.toBoardEntity();
		log.info(board.toString());
		ArrayList<BoardImgEntity> boardImgEntities = new ArrayList<>();

		if (boardDTO.getFiles() != null) {
			for (MultipartFile file : boardDTO.getFiles()) {
				BoardImgDTO boardImgDTO = new BoardImgDTO();
				String originalName = "";
				File backgroundImgFile;
				String backgroundImgSearchName = "";
				UUID uuid = UUID.randomUUID();
				String extend = "";

				originalName = file.getOriginalFilename();
				extend = originalName.substring(originalName.lastIndexOf('.'));
				// #2 - 원본 파일 이름 저장
				boardImgDTO.setOriginalName(originalName.substring(0, originalName.indexOf('.')));
				boardImgDTO.setExtend(extend);

				// #3 - 저장용 랜덤 파일 이름 저장
				backgroundImgSearchName = uuid.toString();

				// #4 - 파일 임시 저장
				//파일이 있으면 임시 파일 저장
				backgroundImgFile = File.createTempFile(backgroundImgSearchName, extend);
				FileUtils.copyInputStreamToFile(file.getInputStream(), backgroundImgFile);
				//5 - 이미지 서버 저장
				amazonS3.putObject(bucketName, "board/" + backgroundImgSearchName + extend, backgroundImgFile);
				// #6 - DB 저장
				boardImgDTO.setSearchName(backgroundImgSearchName);
				BoardImgEntity boardImgEntity = boardImgDTO.toBoardImgEntity();
				boardImgRepository.save(boardImgEntity);
				boardImgEntities.add(boardImgEntity);

				backgroundImgFile.delete();
			}
		}

		board.setImgList(boardImgEntities);
		boardRepository.save(board);
	}

	@Transactional
	public BoardDTO updateBoard(BoardDTO boardDTO) throws Exception {
		BoardEntity boardEntity = boardRepository.findById(boardDTO.getSeq()).orElse(null);

		if (boardEntity == null) {
			throw new Exception("해당 게시물(" + boardDTO.getSeq() + ")이 없습니다.");
		}

		//제목, 글내용 바꾸기
		boardEntity.updateBoard(boardDTO);
		boardRepository.save(boardEntity);

		return boardDTO;
	}

	@Transactional
	public void deleteBoard(String boardId) throws Exception {
		BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);

		if (boardEntity == null) {
			throw new Exception("해당 게시물(" + boardId + ")이 없습니다.");
		}

		//제목, 글내용 바꾸기
		boardRepository.delete(boardEntity);

		return;
	}

	public List<BoardDTO> findAllBoards(int page, int size, String keyword) throws Exception {
		Page<BoardEntity> boardEntityList;

		if (keyword == null || keyword.equals("")) {
			boardEntityList = boardRepository.findAll(
				PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate"));
		} else {
			boardEntityList = boardRepository.findAllByTitleContainingOrContextContaining(keyword, keyword,
				PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate"));
		}

		List<BoardDTO> boardDTOList = new ArrayList<>();

		log.info("========== 대상 게시물 ==========");
		for (BoardEntity boardEntity : boardEntityList.getContent()) {
			BoardDTO boardDTO = boardEntity.toBoardDTO();

			log.info(boardDTO.toString());
			if (boardDTO.getUserSeq() != null) {
				UserDTO userDTO = userService.findBySeq(boardDTO.getUserSeq());
				log.info("작성자 : " + userDTO.toString());
				boardDTO.setWriter(userDTO);
			}
			boardDTOList.add(boardDTO);
		}

		return boardDTOList;
	}

	public BoardDTO findBySeq(String id) throws Exception {
		BoardEntity boardEntity = boardRepository.findById(id).orElse(null);

		if (boardEntity == null) {
			return new BoardDTO();
		}

		UserDTO userDTO = userService.findBySeq(boardEntity.getUserSeq());
		BoardDTO boardDTO = boardEntity.toBoardDTO();
		boardDTO.setWriter(userDTO);

		log.info(userDTO + " ");

		//댓글 유저 가져오기
		for (CommentDTO commentDTO : boardDTO.getCommentList()) {
			log.info("덧글 : " + commentDTO);
			UserEntity commentWriter = userRepository.findBySeq(commentDTO.getUserSeq());
			UserDTO commentWriterDTO;

			if (commentWriter == null) {
				commentWriterDTO = UserDTO.builder()
					.profileImgSearchName("https://in-best.s3.ap-northeast-2.amazonaws.com/profile/DefaultProfile.png")
					.profileImgOriginalName("DefaultProfile.png")
					.name("이름없음")
					.nickname("이름없음")
					.build();
			} else {
				commentWriterDTO = commentWriter.toUserDTO();
			}
			commentDTO.setWriter(commentWriterDTO);

			//대댓글 유저 가져오기
			for (CommentDTO cocomentDTO : commentDTO.getCocommentList()) {
				log.info("대댓글 : " + cocomentDTO);
				UserEntity cocommentWriter = userRepository.findBySeq(cocomentDTO.getUserSeq());
				UserDTO cocommentWriterDTO;

				if (cocommentWriter == null) {
					cocommentWriterDTO = UserDTO.builder()
						.profileImgSearchName(
							"https://in-best.s3.ap-northeast-2.amazonaws.com/profile/DefaultProfile.png")
						.profileImgOriginalName("DefaultProfile.png")
						.name("이름없음")
						.nickname("이름없음")
						.build();
				} else {
					cocommentWriterDTO = cocommentWriter.toUserDTO();
				}
				cocomentDTO.setWriter(cocommentWriterDTO);
			}
		}

		boardEntity.updateView();
		boardRepository.save(boardEntity);

		return boardDTO;
	}

	@Transactional
	public BoardDTO updateLikes(Long userSeq, String boardId) throws Exception {
		BoardEntity boardEntity = boardRepository.findById(boardId).orElse(null);

		//게시물 있는지 확인
		if (boardEntity == null) {
			throw new Exception("대상 게시물이 없습니다.");
		}

		UserEntity userEntity = userRepository.findBySeq(userSeq);
		//유저가 있는지 확인
		if (userEntity == null) {
			throw new Exception("대상 유저가 없습니다.");
		}

		boardEntity.updateLikeUserList(userEntity);
		boardRepository.save(boardEntity);
		return boardEntity.toBoardDTO();
	}

	public BoardDTO setBoardInfo(BoardDTO boardDTO, String loginEmail) {
		int likesCount = 0;
		int commentCount = 0;
		if (boardDTO.getLikesUserList() != null) {
			likesCount = boardDTO.getLikesUserList().size();
			for (UserDTO likeUserDTO : boardDTO.getLikesUserList()) {
				if (likeUserDTO.getEmail().equals(loginEmail)) {
					boardDTO.setLoginLike(true);
					break;
				}
			}
		}
		//게시물의 댓글/대댓글 좋아요를 눌렀는지 확인
		if (boardDTO.getCommentList() != null) {
			commentCount += boardDTO.getCommentList().size();
			for (CommentDTO commentDTO : boardDTO.getCommentList()) {
				//유저가 댓글 좋아요를 눌렀는지 확인
				if (commentDTO.getLikeUserList() != null) {
					for (UserDTO likeUserDTO : commentDTO.getLikeUserList()) {
						if (likeUserDTO.getEmail().equals(loginEmail)) {
							commentDTO.setLoginLike(true);
							break;
						}
					}
				}
				//대댓글이 있는지 확인
				if (commentDTO.getCocommentList() != null) {
					commentCount += commentDTO.getCocommentList().size();
					if (commentDTO.getCocommentList() != null) {
						for (CommentDTO cocommentDTO : commentDTO.getCocommentList()) {
							//유저가 대댓글 좋아요를 했는지 확인
							if (cocommentDTO.getLikeUserList() != null) {
								for (UserDTO likeUserDTO : cocommentDTO.getLikeUserList()) {
									if (likeUserDTO.getEmail().equals(loginEmail)) {
										cocommentDTO.setLoginLike(true);
										break;
									}
								}
							}
						}
					}

				}
			}
		}
		boardDTO.setCommentCount(commentCount);
		return boardDTO;
	}

	public List<BoardDTO> findMostLikesPosts(int pageNo, int pageSize, int period) throws Exception {
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.minusDays(period);

		// 좋아요(likes)가 많은 순으로 정렬하고 상위 10개를 가져옵니다.
		PageRequest pageRequest =
			PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "likes"));

		List<BoardEntity> boardEntityList = boardRepository.findByCreatedDateBetween(start, end, pageRequest);

		ArrayList<BoardDTO> boardDTOList = new ArrayList<>();

		for (BoardEntity boardEntity : boardEntityList) {
			BoardDTO boardDTO = boardEntity.toBoardDTO();

			if (boardDTO.getUserSeq() != null) {
				UserDTO userDTO = userService.findBySeq(boardDTO.getUserSeq());
				log.info("작성자 : " + userDTO.toString());
				boardDTO.setWriter(userDTO);
			}

			boardDTOList.add(boardDTO);
		}

		return boardDTOList;
	}

	public List<BoardDTO> findMostViewPosts(int pageNo, int pageSize, int period) throws Exception {
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.minusDays(period);

		// 좋아요(likes)가 많은 순으로 정렬하고 상위 10개를 가져옵니다.
		PageRequest pageRequest =
			PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "view"));

		List<BoardEntity> boardEntityList = boardRepository.findByCreatedDateBetween(start, end, pageRequest);
		ArrayList<BoardDTO> boardDTOList = new ArrayList<>();

		for (BoardEntity boardEntity : boardEntityList) {
			BoardDTO boardDTO = boardEntity.toBoardDTO();

			if (boardDTO.getUserSeq() != null) {
				UserDTO userDTO = userService.findBySeq(boardDTO.getUserSeq());
				log.info("작성자 : " + userDTO.toString());
				boardDTO.setWriter(userDTO);
			}
			boardDTOList.add(boardDTO);
		}

		return boardDTOList;
	}
}