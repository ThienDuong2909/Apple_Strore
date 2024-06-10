const cancelBtn = document.querySelector(".cancel__btn")
const acceptBtn = document.querySelector('.confirm__btn')
const acceptOrderBtn = document.querySelectorAll('.wrapper__accept__icon')
const acceptForm = document.querySelector('.confirm__order__form')
const acceptOrder = document.querySelector('.accept__order')
const content = document.querySelector('#content__modal')
const confirmOrder = document.querySelector('.confirm__order')
const deteleOrder = document.querySelectorAll('.wrapper__delete__icon')
const wrapperReason = document.querySelector('.wrapper__reason')
const inputReason = document.querySelector('#input__reason')
const missReason = document.querySelector('#miss__reason')


let orderId;
let checkType = false;

for (let i = 0; i < acceptOrderBtn.length; i++) {
    acceptOrderBtn[i].addEventListener('click', (e) => {
        checkType = true
        acceptOrder.style.display = 'flex';
        wrapperReason.style.display = 'none';
        orderId = acceptOrderBtn[i].dataset.id
        content.textContent = "Bạn có chắc chắn xác nhận đơn hàng có mã " + orderId + " ?"
        console.log(orderId)
        console.log(checkType)
    })
}

for (let i = 0; i < deteleOrder.length; i++) {
    deteleOrder[i].addEventListener('click', (e) => {
        checkType = false
        acceptOrder.style.display = 'flex';
        wrapperReason.style.display = 'flex';
        orderId = acceptOrderBtn[i].dataset.id
        content.textContent = "Bạn có chắc chắn muốn hủy đơn hàng có mã " + orderId + " ?"
        console.log(orderId)
        console.log(checkType)
    })
}

acceptBtn.addEventListener('click', (e) => {
    if (checkType) {
        e.preventDefault()
        acceptForm.action = `/admin/accept/orderid=` + orderId;
        console.log(acceptBtn)
    } else {
        e.preventDefault()
        if (inputReason.value === "") {
            missReason.textContent = "Vui lòng nhập lý do hủy đơn hàng";
            missReason.style.opacity = "1";
            return;
        }
        if (inputReason.value.length < 5) {
            missReason.textContent = "Vui lòng nhập chi tiết hơn"
            missReason.style.opacity = "1";
            return;
        }
        if (inputReason.value.length > 255) {
            missReason.textContent = "Lý do hủy đơn hàng không được quá 255 ký tự";
            missReason.style.opacity = "1";
            return;
        }
        acceptForm.action = `/admin/cancel/orderid=${orderId}/reason=${inputReason.value}`
        console.log(`/admin/cancel/orderid=${orderId}?reason=${inputReason.value}`)
        console.log(acceptBtn)
        missReason.style.opacity = "0";
    }
    acceptForm.submit()
})

// if (checkType) {
//
// } else {
//     acceptBtn.addEventListener('click', (e) => {
//         e.preventDefault()
//         if (inputReason.value === "") {
//             missReason.textContent = "Vui lòng nhập lý do hủy đơn hàng";
//             missReason.style.opacity = "1";
//             return;
//         }
//         if (inputReason.value.length < 5) {
//             missReason.textContent = "Vui lòng nhập chi tiết hơn"
//             missReason.style.opacity = "1";
//             return;
//         }
//         if (inputReason.value.length > 255) {
//             missReason.textContent = "Lý do hủy đơn hàng không được quá 255 ký tự";
//             missReason.style.opacity = "1";
//             return;
//         }
//         acceptForm.action = `/admin/cancel/orderid=` + orderId
//         acceptForm.submit()
//         console.log(acceptBtn)
//         missReason.style.opacity = "0";
//
//     })
// }

cancelBtn.addEventListener('click', (e) => {
    e.stopPropagation()
    acceptOrder.style.display = 'none';
    missReason.style.opacity = "0";
})

confirmOrder.addEventListener('click', (e) => {
    e.stopPropagation()
})

acceptOrder.addEventListener('click', (e) => {
    acceptOrder.style.display = 'none';
    missReason.style.opacity = "0";
})
