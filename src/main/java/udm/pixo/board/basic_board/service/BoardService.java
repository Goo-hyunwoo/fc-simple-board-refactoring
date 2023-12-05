package udm.pixo.board.basic_board.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import udm.pixo.board.basic_board.db.BoardRepository;
import udm.pixo.board.basic_board.model.BoardDto;
import udm.pixo.board.common.Pagination;

@Slf4j
@Service
@AllArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final BoardConvertor boardConvertor;
	
	public void show(Object o) {
		log.info("{}", o);
	}
	
	public BoardDto create(BoardDto dto) {
		var entity = boardRepository.save(boardConvertor.toEntity(dto));
		return boardConvertor.toDto(entity);
	}
	
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
	
	public Pagination<BoardDto> getAll(Pageable pageable) {
		
		var entityList = boardRepository.findAll(pageable);
		
		var data = entityList.get()
				.map(entity -> {
					return boardConvertor.toDto(entity);
				})
				.toList();
		
		var response = Pagination.<BoardDto>builder()
				.data(data)
				.page(entityList.getNumber())
				.size(entityList.getSize())
				.totalElements(entityList.getTotalElements())
				.totalPage(entityList.getTotalPages())
				.currentElements(entityList.getNumberOfElements())
				.build();
		
		
		return response;
	}
	
	
}
