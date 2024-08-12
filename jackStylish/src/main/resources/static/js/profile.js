document.addEventListener('DOMContentLoaded', function () {

    let token = localStorage.getItem('access_token');

    if (token) {
        changeToUserprofile();
    } else {
        changeToSignin();
    }

    function changeToSignup() {
        document.getElementById('user-container').innerHTML = `
            <div id="signup">
                <h1>Sign Up</h1>
                <form>
                    <label>name:</label>
                    <br>
                    <input id="signup-name">
                    <br>
                    <label>email:</label>
                    <br>
                    <input id="signup-email">
                    <br>
                    <label>password:</label>
                    <br>
                    <input id="signup-password">
                    <br>
                    <button id="button-signup" type="submit">註冊</button>
                </form>
                <button id="change-signin">登入</button>
            </div>
        `;

        document.getElementById('signup').addEventListener('submit', signup)

        document.getElementById('change-signin').addEventListener('click', function (event) {
            changeToSignin();
        });
    }

    function changeToSignin() {
        document.getElementById('user-container').innerHTML = `
            <div id="signin">
                <h1>Sign In</h1>
                <form>
                    <label>email:</label>
                    <br>
                    <input id="signin-email">
                    <br>
                    <label>password:</label>
                    <br>
                    <input id="signin-password">
                    <br>
                    <button id="button-signin" type="submit">登入</button>
                </form>
                <button id="change-signup">註冊</button>
            </div>
        `;

        document.getElementById('signin').addEventListener('submit', signin)

        document.getElementById('change-signup').addEventListener('click', function (event) {
            changeToSignup();
        });
    }

    function changeToUserprofile() {

        token = localStorage.getItem('access_token');

        document.getElementById('user-container').innerHTML = `
            <div id="profile">
                <h1>User Profile</h1>
                <h2 id="profile-name">user name: </h2>
                <h2 id="profile-email">email: </h2>
                <button id="signout">登出</button>
            </div>
        `;

        document.getElementById('signout').addEventListener('click', function (event) {
            localStorage.removeItem('access_token');
            changeToSignin();
        });

        fetch('http://54.248.138.109/api/1.0/user/profile', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            // .then(response => {
            //     if (response.status != 200) {
            //         localStorage.removeItem('access_token');
            //         changeToSignin();
            //     }
            // })
            .then(data => {
                console.log(data.data);

                document.getElementById('profile-name').textContent = "user name: " + data.data.name;
                document.getElementById('profile-email').textContent = "password: " + data.data.email;

            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    function signup() {
        event.preventDefault();

        const name = document.getElementById('signup-name').value;
        const email = document.getElementById('signup-email').value;
        const password = document.getElementById('signup-password').value;

        const data = {
            name: name,
            email: email,
            password: password
        };

        fetch('http://54.248.138.109/api/1.0/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                const token = data.data.access_token;
                localStorage.setItem('access_token', token);
                console.log(token);
                console.log('Success:', data);
                changeToUserprofile();
            })
            .catch(error => {
                console.error('Error:', error);
                alert("不得填空且需符合email規格，若註冊過請直接登入");
            });
    }

    function signin() {
        event.preventDefault();

        const email = document.getElementById('signin-email').value;
        const password = document.getElementById('signin-password').value;

        const data = {
            provider: "native",
            email: email,
            password: password
        };

        fetch('http://54.248.138.109/api/1.0/user/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(data => {
                const token = data.data.access_token;
                localStorage.setItem('access_token', token);
                console.log(token);
                console.log('Success:', data);
                changeToUserprofile();
            })
            .catch(error => {
                console.error('Error:', error);
                alert("帳號或密碼錯誤");
            });
    }

});







