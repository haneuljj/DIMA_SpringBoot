2024년 2월 29일

*** 첫번째 프로젝트 생성
- index(첫화면) 구동하기

*** 작업 시 주의 사항
- 모든 프로젝트의 요청 경로는 동일하면 안됨!!!
- 동일하게 할 경우 요청 방식이 달라야함!!!
- 요청방식: @GetMapping, @PostMapping, @DeleteMapping, @PutMapping
- @GetMapping("/")
- @PostMapping("/") --> 요청방식이 달라서 가능
- @GetMapping("/") --> 요청경로도 같고 요청방식도 같아서 불가능

