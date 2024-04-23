package com.example.ms1.note.note;

import com.example.ms1.note.notebook.Notebook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // JPA 엔티티임을 나타냅니다.
@Setter // Lombok을 사용하여 Setter 메서드를 자동으로 생성합니다.
@Getter // Lombok을 사용하여 Getter 메서드를 자동으로 생성합니다.
public class Note {

    @Id // 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 기본 키를 사용하며, 데이터베이스에 의존합니다.
    private Long id; // 노트의 식별자(primary key)입니다.

    private String title; // 노트의 제목을 나타냅니다.
    private String content; // 노트의 내용을 나타냅니다.
    private LocalDateTime createDate; // 노트의 생성 날짜와 시간을 나타냅니다.

    @ManyToOne(fetch = FetchType.LAZY) // 다대일(N:1) 관계를 나타냅니다. LAZY 로딩을 사용합니다.
    private Notebook notebook; // 해당 노트가 속한 노트북을 나타냅니다.

}
