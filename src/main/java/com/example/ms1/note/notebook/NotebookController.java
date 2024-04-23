package com.example.ms1.note.notebook; // com.example.ms1.note.notebook 패키지에 속한 클래스입니다.

import com.example.ms1.note.note.Note; // Note 클래스를 import 합니다.
import com.example.ms1.note.note.NoteService; // NoteService 클래스를 import 합니다.
import lombok.RequiredArgsConstructor; // Lombok 어노테이션을 사용하기 위해 import 합니다.
import org.springframework.stereotype.Controller; // 스프링 컨트롤러를 정의하기 위해 import 합니다.
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 처리하기 위해 import 합니다.
import org.springframework.web.bind.annotation.PathVariable; // 경로 변수를 받기 위해 import 합니다.
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하기 위해 import 합니다.

@Controller // 스프링 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor // Lombok을 사용하여 생성자를 자동으로 생성합니다.
public class NotebookController {

    private final NotebookRepository notebookRepository; // NotebookRepository 의존성을 주입합니다.
    private final NoteService noteService; // NoteService 의존성을 주입합니다.

    @PostMapping("/books/write") // POST 메서드를 처리하는 요청 매핑을 정의합니다.
    public String write() { // 새로운 노트북을 생성하는 메서드입니다.

        // 새 Notebook 객체를 생성합니다.
        Notebook notebook = new Notebook();
        // 노트북의 이름을 설정합니다.
        notebook.setName("새노트북");

        // 노트북을 저장합니다.
        notebookRepository.save(notebook);
        // 새 노트를 생성하여 저장합니다.
        noteService.saveDefault(notebook);

        return "redirect:/"; // 홈 페이지로 리다이렉트합니다.
    }

    @GetMapping("/books/{id}") // GET 메서드를 처리하는 요청 매핑을 정의합니다.
    public String detail(@PathVariable("id") Long id) { // 노트북 ID를 경로 변수로 받습니다.

        // 주어진 ID로 노트북을 조회합니다. 만약 존재하지 않는다면 예외를 발생시킵니다.
        Notebook notebook = notebookRepository.findById(id).orElseThrow();
        // 노트북에 속한 첫 번째 노트를 가져옵니다.
        Note note = notebook.getNoteList().get(0);

        // 해당 노트의 상세 페이지로 리다이렉트합니다.
        return "redirect:/books/%d/notes/%d".formatted(id, note.getId());
    }
}
