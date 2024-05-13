const backgroundConfirmOrder = document.querySelector('.background__confirm__order');
const cancelConfirmBtn = document.getElementById("cancel__btn")
const wrapperConfirmOrderBtn = document.getElementById("confirm__btn")
const accept = document.querySelector('#accept')
const acceptOrderBtn = document.querySelector('.wrapper__accept__icon')
const wrapperConfirmOrder = document.querySelector('.wrapper__confirm__order')
const acceptForm = document.querySelector('.confirm__order__form')

var orderId

backgroundConfirmOrder.addEventListener('click', () => {
    backgroundConfirmOrder.style.display = 'none';
})

cancelConfirmBtn.addEventListener('click', (e) => {
    e.stopPropagation()
    backgroundConfirmOrder.style.display = 'none';
})

accept.addEventListener('click', (e) => {
    e.preventDefault()
    acceptForm.action = `/admin/accept/orderid=` + orderId
    acceptForm.submit()
    console.log(accept)
    toast({
        title: "Thành công",
        message: "Đã xác nhận đơn hàng",
        type: "success"
    })
})

toast({
    title: "Thành công",
    message: "Đã xác nhận đơn hàng",
    type: "success"
})

wrapperConfirmOrder.addEventListener('click', (e) => {
    e.stopPropagation()
})

if (acceptOrderBtn) {
    acceptOrderBtn.addEventListener('click', (e) => {
        backgroundConfirmOrder.style.display = 'flex';
        orderId = acceptOrderBtn.dataset.id
        console.log(orderId)
    })
}

function toast({ title, message, type }) {
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