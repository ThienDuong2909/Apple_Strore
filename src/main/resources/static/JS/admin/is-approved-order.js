const deliveredOrderBtn = document.getElementsByClassName('wrapper__accept__icon');
const cancelDeliveredBtn = document.querySelector(".cancel__btn");
const confirmDeliveredBtn = document.querySelector(".confirm__btn");
const wrapperDeliveredOrder = document.querySelector('.wrapper__delivered__order');
const deliveredOrder = document.querySelector('.delivered__order');
const deliveredOrderForm = document.querySelector('.delivered__order__form');
const content = document.querySelector('#content__modal');

var orderId;

for (let i = 0; i < deliveredOrderBtn.length; i++) {
    deliveredOrderBtn[i].addEventListener('click', (e) => {
        orderId = deliveredOrderBtn[i].dataset.id;
        wrapperDeliveredOrder.style.display = 'flex';
        content.textContent = "Bạn có chắc chắn xác nhận đã giao đơn hàng có mã " + orderId + " ?";
        console.log(orderId);
    });
}

confirmDeliveredBtn.addEventListener('click', (e) => {
    e.preventDefault();
    deliveredOrderForm.action = `/admin/complete/orderid=` + orderId;
    deliveredOrderForm.submit();
});

cancelDeliveredBtn.addEventListener('click', (e) => {
    e.stopPropagation();
    wrapperDeliveredOrder.style.display = 'none';
})

deliveredOrder.addEventListener('click', (e) => {
    e.stopPropagation();
})

wrapperDeliveredOrder.addEventListener('click', (e) => {
    wrapperDeliveredOrder.style.display = 'none';
})


