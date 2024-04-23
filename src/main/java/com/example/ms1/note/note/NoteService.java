package com.example.ms1.note.note;

import com.example.ms1.note.notebook.Notebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // 스프링의 서비스 빈임을 나타냅니다.
@RequiredArgsConstructor // Lombok을 사용하여 생성자를 자동으로 생성합니다.
public class NoteService {
    private final NoteRepository noteRepository; // 의존성 주입을 위한 NoteRepository 필드입니다.

    // 새로운 노트를 생성하여 저장하는 메서드입니다.
    public Note saveDefault(Notebook notebook) {
        // 새 Note 객체를 생성합니다.
        Note note = new Note();
        // 노트의 제목을 설정합니다.
        note.setTitle("new title..");
        // 노트의 내용을 설정합니다.
        note.setContent("");
        // 노트의 생성 날짜와 시간을 현재 시간으로 설정합니다.
        note.setCreateDate(LocalDateTime.now());
        // 노트가 속한 노트북을 설정합니다.
        note.setNotebook(notebook);

        // 생성된 노트를 저장하고 반환합니다.
        return noteRepository.save(note);
    }
}
