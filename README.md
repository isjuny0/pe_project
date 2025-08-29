# 📸 PE Project

> 온라인 사진 전시회 플랫폼  

## 🚀 기술 스택
- **Backend**: Spring Boot, Spring Security (JWT), JPA, PostgreSQL
- **Frontend**: React, TailwindCSS
- **Infra**: AWS S3

## ✨ 주요 기능
- 회원가입 / 로그인 (JWT)
- 게시글 작성, 수정, 삭제 (권한 체크)
- 댓글 & 좋아요
- 갤러리 (대표 사진, 테마 설정)
- Kafka + WebSocket 기반 실시간 알림(미구현)

## 📂 프로젝트 구조
```bash
pe_project/
 ┣ back/      # Spring Boot API 서버
 ┣ front/     # React 클라이언트
 ┗ README.md
