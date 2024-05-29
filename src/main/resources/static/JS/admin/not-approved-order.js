const cancelConfirmBtn = document.getElementsByClassName("cancel__btn")
const accept = document.querySelectorAll('.confirm__btn')
const acceptOrderBtn = document.querySelectorAll('.wrapper__accept__icon')
const acceptForm = document.querySelectorAll('.confirm__order__form')
const confirmOrder = document.querySelectorAll('.confirm__order')


var orderId

for (let i = 0; i < acceptOrderBtn.length; i++) {
    acceptOrderBtn[i].addEventListener('click', (e) => {
        confirmOrder[i].style.display = 'flex';
        orderId = acceptOrderBtn[i].dataset.id
        console.log(orderId)
        accept[i].addEventListener('click', (e) => {
            e.preventDefault()
            acceptForm[i].action = `/admin/accept/orderid=` + orderId
            acceptForm[i].submit()
            console.log(accept)
        })
        cancelConfirmBtn[i].addEventListener('click', (e) => {
            e.stopPropagation()
            confirmOrder[i].style.display = 'none';
            enabledBtn()
        })
        disabledBtn(i)
    })
}

function disabledBtn(index) {
    for (let i = 0; i < acceptOrderBtn.length; i++) {
       if (i !== index) {
           acceptOrderBtn[i].disabled = true;
           acceptOrderBtn[i].style.opacity = 0.5;
       }
    }
}

function enabledBtn() {
    for (let i = 0; i < acceptOrderBtn.length; i++) {
        acceptOrderBtn[i].disabled = false;
        acceptOrderBtn[i].style.opacity = 1;
    }
}



// function toast({ title, message, type }) {
//     const main = document.querySelector("#toast")
//     const icons = {
//         success: '<i class="fa-solid fa-circle-check"></i>',
//         error: '<i class="fa-solid fa-circle-exclamation"></i>',
//         warning: '<i class="fa-solid fa-triangle-exclamation"></i>'
//     }
//     if (main) {
//         const toast = document.createElement("div")
//         toast.classList.add('toast', `toast__${type}`, 'center')
//         toast.innerHTML = `
//             <div class="toast__icon">
//                 ${icons[type]}
//             </div>
//             <div class="toast__body">
//                 <h3 class="toast__title">${title}</h3>
//                 <p class="toast__message">${message}</p>
//             </div>
//             <div class="toast__close">
//                 <i class="fa-solid fa-xmark" style="color: gray;"></i>
//             </div>
//         `
//         main.appendChild(toast)
//         // Auto close
//         const autoCloseId = setTimeout(function () {
//             main.removeChild(toast)
//         }, 4000)
//         // Close when click
//         toast.onclick = function () {
//             // toast.style.opacity = 0
//             setTimeout(function () {
//                 main.removeChild(toast)
//             }, 300)
//             clearTimeout(autoCloseId)
//         }
//     }
// }