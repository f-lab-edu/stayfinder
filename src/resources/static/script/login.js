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
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            window.location.href = '/dashboard';
        } else {
            alert(data.message || '로그인에 실패 했습니다. 이메일과 비밀번호를 확인해주세요.');
        }
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        alert('재시도 해주세요. 문제가 지속되면 관리자에게 문의하세요.');
    });
}