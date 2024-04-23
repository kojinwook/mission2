package com.example.ms1.note; // com.example.ms1.note 패키지에 속한 클래스입니다.

import com.example.ms1.note.note.Note; // Note 클래스를 import 합니다.
import com.example.ms1.note.note.NoteRepository; // NoteRepository 클래스를 import 합니다.
import com.example.ms1.note.note.NoteService; // NoteService 클래스를 import 합니다.
import com.example.ms1.note.notebook.Notebook; // Notebook 클래스를 import 합니다.
import com.example.ms1.note.notebook.NotebookRepository; // NotebookRepository 클래스를 import 합니다.
import lombok.RequiredArgsConstructor; // Lombok 어노테이션을 사용하기 위해 import 합니다.
import org.springframework.stereotype.Controller; // 스프링 컨트롤러를 정의하기 위해 import 합니다.
import org.springframework.ui.Model; // 스프링 모델을 사용하기 위해 import 합니다.
import org.springframework.web.bind.annotation.RequestMapping; // 요청 매핑을 정의하기 위해 import 합니다.

import java.util.List; // 리스트를 사용하기 위해 import 합니다.

@Controller // 스프링 컨트롤러임을 나타냅니다.
@RequiredArgsConstructor // Lombok을 사용하여 생성자를 자동으로 생성합니다.
public class MainController {

    private final NotebookRepository notebookRepository; // NotebookRepository 의존성을 주입합니다.
    private final NoteRepository noteRepository; // NoteRepository 의존성을 주입합니다.
    private final NoteService noteService; // NoteService 의존성을 주입합니다.

    @RequestMapping("/") // "/" 경로로 들어오는 요청을 처리합니다.
    public String main(Model model) { // main 메서드, Model 객체를 매개변수로 받습니다.

        List<Notebook> notebookList = notebookRepository.findAll(); // 모든 Notebook을 가져옵니다.

        if (notebookList.isEmpty()) { // Notebook이 비어있을 경우
            Notebook notebook = new Notebook(); // 새 Notebook 객체를 생성합니다.
            notebook.setName("새노트"); // 노트북의 이름을 설정합니다.
            notebookRepository.save(notebook); // 새 노트북을 저장합니다.

            return "redirect:/"; // 다시 "/" 경로로 리다이렉트합니다.
        }

        Notebook targetNotebook = notebookList.get(0); // 첫 번째 노트북을 대상 노트북으로 설정합니다.
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook); // 대상 노트북에 속한 노트를 가져옵니다.

        if (noteList.isEmpty()) { // 노트가 비어있을 경우
            noteService.saveDefault(targetNotebook); // 기본 노트를 생성하여 저장합니다.
            return "redirect:/"; // 다시 "/" 경로로 리다이렉트합니다.
        }

        // 모델에 속성을 추가합니다.
        model.addAttribute("noteList", noteList); // 노트 목록을 모델에 추가합니다.
        model.addAttribute("targetNote", noteList.get(0)); // 대상 노트를 모델에 추가합니다.
        model.addAttribute("notebookList", notebookList); // 노트북 목록을 모델에 추가합니다.
        model.addAttribute("targetNotebook", targetNotebook); // 대상 노트북을 모델에 추가합니다.

        return "main"; // main.html을 렌더링합니다.
    }
}
