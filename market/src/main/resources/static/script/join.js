// 회원가입 검증
document.getElementById('submitBtn').addEventListener('click', function() {
    let memberId = document.getElementById("memberId").value;
    if(memberId.trim().length < 3 || memberId.trim().length >10) {
        alert("ID는 3 ~ 10자로 입력하세요.");
        document.getElementById("memberId").select();
        return;
    }

    let memberPw = document.getElementById("memberPw").value;
    if(memberPw.trim().length < 3 || memberPw.trim().length >10) {
        alert("비밀번호는 3 ~ 10자로 입력하세요.");
        document.getElementById("memberPw").select();
        return;
    }

    let memberName = document.getElementById("memberName");
    if(memberName.trim().length == 0) {
        alert("이름을 입력하세요.");
        document.getElementById("memberName").select();
        return;
    }

    let phone = document.getElementById("phone");
    if(phone.trim().length == 0) {
        alert("전화번호를 입력하세요.");
        document.getElementById("phone").select();
        return;
    }
})


