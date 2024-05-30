const loginBtn = document.getElementById('login__btn');
const inputUsername = document.getElementById('username');
const inputPassword = document.getElementById('password');
const emptyUsername = document.getElementById('fill__username');
const emptyPassword = document.getElementById('fill__password');
const showPassword = document.getElementById('show__password');
const hidePassword = document.getElementById('hide__password');

loginBtn.addEventListener('mousedown', (e) => {
    loginBtn.style.scale = 0.95
})

loginBtn.addEventListener('mouseup', (e) => {
    loginBtn.style.scale = 1
})


loginBtn.addEventListener('click', (e) => {
    e.preventDefault();
    if (inputUsername.value === '') {
        emptyUsername.style.opacity = 1;
    } else {
        emptyUsername.style.opacity = 0;
    }
    if (inputPassword.value === '') {
        emptyPassword.style.opacity = 1;
    } else {
        emptyPassword.style.opacity = 0;
    }
    if (inputUsername.value !== '' && inputPassword.value !== '') {
        document.getElementById('login__form').submit();
    }
})

if (window.location.href.split('/')[3].includes("error=true")) {
    emptyPassword.textContent = "Tên đăng nhập hoặc mật khẩu không đúng"
    emptyPassword.style.opacity = 1;
} else {
    emptyPassword.style.opacity = 0;
}

showPassword.addEventListener('click', (e) => {
    if (inputPassword.type === 'password') {
        inputPassword.type = 'text';
        showPassword.hidden = true
        hidePassword.hidden = false
    }
})

hidePassword.addEventListener('click', (e) => {
    if (inputPassword.type === 'text') {
        inputPassword.type = 'password'
        showPassword.hidden = false
        hidePassword.hidden = true
    }
})

function toast({ title, message, type }) {
    console.log("Toast was called")
    const main = document.querySelector("#toast")
    const icons = {
        success: '<i class="fa-solid fa-circle-check"></i>',
        error: '<i class="fa-solid fa-circle-exclamation"></i>',
        warning: '<i class="fa-solid fa-triangle-exclamation"></i>'
    }
    if (main) {
        const toast = document.createElement("div")
        toast.classList.add('toast', `toast__${type}`, 'center')
        toast.innerHTML = `
            <div class="toast__icon">
                ${icons[type]}
            </div>
            <div class="toast__body">
                <h3 class="toast__title">${title}</h3>
                <p class="toast__message">${message}</p>
            </div>
            <div class="toast__close">
                <i class="fa-solid fa-xmark" style="color: gray;"></i>
            </div>
        `
        main.appendChild(toast)
        // Auto close
        const autoCloseId = setTimeout(function () {
            main.removeChild(toast)
        }, 4000)
        // Close when click
        toast.onclick = function () {
            // toast.style.opacity = 0
            setTimeout(function () {
                main.removeChild(toast)
            }, 300)
            clearTimeout(autoCloseId)
        }
    }
}

toast({
    title: "Thành công",
    message: "Đã xác nhận đơn hàng",
    type: "success"
})

document.getElementById("toast").innerHTML = "<h2>Hello</h2>".join('')