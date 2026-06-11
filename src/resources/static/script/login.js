function login() {
    const email = document.querySelector('#email').value;
    const password = document.querySelector('#password').value;

    console.log("Email:", email);
    console.log("Password:", password);

    if (email === "" || password === "") {
        alert("Email and password cannot be empty.");
        return;
    }


    fetch(`http://localhost:8081/api/v1/user/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if(!response.ok) {
           return response.json().then(errData => {
            // 여기서 에러 메시지를 바로 던짐
            throw new Error(errData.message || '알 수 없는 오류가 발생했습니다.');
        });
    }
    return response.json();
})
.then(data => {
    if (data.success) {
        window.location.href = '/dashboard';
    } else {
        console.error('Login failed:', data);
        alert(data.message || '로그인에 실패 했습니다. 이메일과 비밀번호를 확인해주세요.');
    }
})
.catch(error => {
    // 여기서 error.message를 출력
    alert(error.message || '재시도 해주세요. 문제가 지속되면 관리자에게 문의하세요.');
});
}