package com.example.ms1.note.note; // com.example.ms1.note.note 패키지에 속한 클래스입니다.

import com.example.ms1.note.notebook.Notebook; // Notebook 클래스를 import 합니다.
import com.example.ms1.note.notebook.NotebookRepository; // NotebookRepository 클래스를 import 합니다.
import lombok.RequiredArgsConstructor; // Lombok 어노테이션을 사용하기 위해 import 합니다.
import org.springframework.stereotype.Controller; // 스프링 컨트롤러를 정의하기 위해 import 합니다.
import org.springframework.ui.Model; // 스프링 모델을 사용하기 위해 import 합니다.
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 처리하기 위해 import 합니다.
import org.springframework.web.bind.annotation.PathVariable; // 경로 변수를 받기 위해 import 합니다.
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하기 위해 import 합니다.
import org.springframework.web.bind.annotation.RequestMapping; // 요청 매핑을 정의하기 위해 import 합니다.

import java.util.List; // 리스트를 사용하기 위해 import 합니다.

@Controller // 스프링 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor // Lombok을 사용하여 생성자를 자동으로 생성합니다.
@RequestMapping("/books/{notebookId}/notes") // 해당 컨트롤러의 기본 URL을 설정합니다.
public class NoteController {

    private final NoteRepository noteRepository; // NoteRepository 의존성을 주입합니다.
    private final NotebookRepository notebookRepository; // NotebookRepository 의존성을 주입합니다.
    private final NoteService noteService; // NoteService 의존성을 주입합니다.

    @PostMapping("/write") // POST 메서드를 처리하는 요청 매핑을 정의합니다.
    public String write(@PathVariable("notebookId") Long notebookId) { // 노트북 ID를 경로 변수로 받습니다.
        Notebook notebook = notebookRepository.findById(notebookId).orElseThrow(); // 노트북 ID로 노트북을 조회합니다.
        noteService.saveDefault(notebook); // 기본 노트를 생성하여 저장합니다.
        return "redirect:/"; // 홈 페이지로 리다이렉트합니다.
    }

    @GetMapping("/{id}") // GET 메서드를 처리하는 요청 매핑을 정의합니다.
    public String detail(Model model, @PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id) {
        // 모델 객체와 노트북 ID, 노트 ID를 경로 변수로 받습니다.

        Note note = noteRepository.findById(id).get(); // 주어진 ID로 노트를 조회합니다.

        // 모든 노트북과 대상 노트북에 속한 노트 목록을 가져옵니다.
        List<Notebook> notebookList = notebookRepository.findAll();
        Notebook targetNotebook = notebookRepository.findById(notebookId).get();
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);

        // 모델에 필요한 속성을 추가합니다.
        model.addAttribute("notebookList", notebookList); // 노트북 목록을 모델에 추가합니다.
        model.addAttribute("targetNotebook", targetNotebook); // 대상 노트북을 모델에 추가합니다.
        model.addAttribute("targetNote", note); // 대상 노트를 모델에 추가합니다.
        model.addAttribute("noteList", noteList); // 노트 목록을 모델에 추가합니다.

        return "main"; // main.html을 렌더링합니다.
    }

    @PostMapping("/{id}/update") // POST 메서드를 처리하는 요청 매핑을 정의합니다.
    public String update(@PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id, String title, String content) {
        // 노트북 ID, 노트 ID, 제목, 내용을 경로 변수와 파라미터로 받습니다.

        Note note = noteRepository.findById(id).get(); // 주어진 ID로 노트를 조회합니다.

        if(title.trim().length() == 0) { // 제목이 공백인 경우
            title = "제목 없음"; // 기본 제목을 설정합니다.
        }

        // 노트의 제목과 내용을 업데이트합니다.
        note.setTitle(title);
        note.setContent(content);

        noteRepository.save(note); // 노트를 저장합니다.
        return "redirect:/books/%d/notes/%d".formatted(notebookId, id); // 해당 노트의 상세 페이지로 리다이렉트합니다.
    }

    @PostMapping("/{id}/delete") // POST 메서드를 처리하는 요청 매핑을 정의합니다.
    public String delete(@PathVariable("notebookId") Long notebookId, @PathVariable("id") Long id) {
        // 노트북 ID와 노트 ID를 경로 변수로 받습니다.

        noteRepository.deleteById(id); // 주어진 ID로 노트를 삭제합니다.
        return "redirect:/"; // 홈 페이지로 리다이렉트합니다.
    }
}
