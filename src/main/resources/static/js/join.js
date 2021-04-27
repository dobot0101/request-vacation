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