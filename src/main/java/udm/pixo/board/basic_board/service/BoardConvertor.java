package udm.pixo.board.basic_board.service;

import org.springframework.stereotype.Component;

import udm.pixo.board.basic_board.db.BoardEntity;
import udm.pixo.board.basic_board.model.BoardDto;

@Component
public class BoardConvertor {

	public BoardDto toDto(BoardEntity entity) {
		return BoardDto.builder()
				.id(entity.getId())
				.boardName(entity.getBoardName())
				.status(entity.getStatus())
				.build();
	}
	
	public BoardEntity toEntity(BoardDto dto) {
		return BoardEntity.builder()
				.id(dto.getId())
				.boardName(dto.getBoardName())
				.status(dto.getStatus())
				.build();
	}
}
