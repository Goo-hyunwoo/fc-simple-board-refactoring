package udm.pixo.board.basic_board.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import udm.pixo.board.basic_board.model.BoardDto;
import udm.pixo.board.basic_board.model.BoardSaveRequest;
import udm.pixo.board.basic_board.service.BoardService;
import udm.pixo.board.common.Pagination;

@RestController
@RequestMapping("/api/board")
@AllArgsConstructor
public class BoardApiController {
	
	private final BoardService boardService;
	
	
	@PostMapping("/save")
	public BoardDto create(
			@Valid
			@RequestBody
			BoardSaveRequest boardSaveRequest
			) {
		var entity = BoardDto.builder().boardName(boardSaveRequest.getBoardName()).status("CREATED").build();
		var dto = boardService.create(entity);
		return dto;
	}
	
	@GetMapping("/all")
	public Pagination<BoardDto> getAll(
			@PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.DESC)
			Pageable pageable
			) {
		return boardService.getAll(pageable);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		boardService.delete(id);
	}
	
}
