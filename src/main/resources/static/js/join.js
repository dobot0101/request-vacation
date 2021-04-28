/**
 * 회원가입 전 입력 값 확인
 */
document.getElementById('submitBtn').addEventListener('click', function () {
    if (document.getElementById('email').value.length === 0) {
        alert('이메일을 입력하세요.');
        return;
    }
    if (document.getElementById('password').value.length === 0) {
        alert('암호를 입력하세요.');
        return;
    }
    if (document.getElementById('name').value.length === 0) {
        alert('이름을 입력하세요.');
        return;
    }
    document.getElementById('joinForm').submit();
});

/**
 * 페이지 로딩 시 서버에서 보낸 메시지가 있으면 알림
 */
document.addEventListener('DOMContentLoaded', function () {
    if (document.getElementById('msg').value) {
        alert(document.getElementById('msg').value);
    }
});