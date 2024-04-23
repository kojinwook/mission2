package com.example.ms1.note.note;

import com.example.ms1.note.notebook.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // JpaRepository를 확장하여 Note 엔티티를 관리하는 Repository 인터페이스를 정의합니다.
    // Note 엔티티의 기본 키는 Long 형식입니다.

    List<Note> findByNotebook(Notebook notebook);
    // 노트북에 속한 노트를 검색하기 위한 메서드를 선언합니다.
    // 메서드 이름에 따라 Spring Data JPA가 자동으로 쿼리를 생성합니다.
    // findByNotebook: 노트북에 속한 노트를 검색합니다.
    // 이 메서드는 노트북(Notebook) 객체를 매개변수로 받아 해당 노트북에 속한 모든 노트를 반환합니다.
}