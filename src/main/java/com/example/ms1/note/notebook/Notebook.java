package com.example.ms1.note.notebook;

import com.example.ms1.note.note.Note;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA 엔티티임을 나타냅니다.
@Getter // Lombok을 사용하여 Getter 메서드를 자동으로 생성합니다.
@Setter // Lombok을 사용하여 Setter 메서드를 자동으로 생성합니다.
public class Notebook {

    @Id // 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 기본 키를 사용하며, 데이터베이스에 의존합니다.
    private Long id; // 노트북의 식별자(primary key)입니다.

    private String name; // 노트북의 이름을 나타냅니다.

    @OneToMany(mappedBy = "notebook") // 일대다(1:N) 관계를 나타냅니다. 노트와 연관됩니다.
    List<Note> noteList = new ArrayList<>(); // 노트북에 속한 노트의 목록을 나타냅니다.
}
