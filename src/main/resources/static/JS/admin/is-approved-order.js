const deliveredOrderBtn = document.getElementsByClassName('wrapper__accept__icon');
const cancelDeliveredBtn = document.getElementsByClassName("cancel__btn");
const confirmDeliveredBtn = document.getElementsByClassName("confirm__btn");
const deliveredOrder = document.querySelectorAll('.delivered__order');
const deliveredOrderForm = document.querySelectorAll('.delivered__order__form');

var orderId;


for (let i = 0; i < deliveredOrderBtn.length; i++) {
    deliveredOrderBtn[i].addEventListener('click', (e) => {
        deliveredOrder[i].style.display = 'flex';
        orderId = deliveredOrderBtn[i].dataset.id;
        console.log(orderId);
        confirmDeliveredBtn[i].addEventListener('click', (e) => {
            e.preventDefault();
            deliveredOrderForm[i].action = `/admin/complete/orderid=` + orderId;
            deliveredOrderForm[i].submit();
        });

        cancelDeliveredBtn[i].addEventListener('click', (e) => {
            e.stopPropagation();
            deliveredOrder[i].style.display = 'none';
            enabledBtn();
        })
        disabledBtn(i);
    });
}

function disabledBtn(index) {
    for (let i = 0; i < deliveredOrderBtn.length; i++) {
        if (i !== index) {
            deliveredOrderBtn[i].disabled = true;
            deliveredOrderBtn[i].style.opacity = 0.5;
        }
    }
}

function enabledBtn() {
    for (let i = 0; i < deliveredOrderBtn.length; i++) {
        deliveredOrderBtn[i].disabled = false;
        deliveredOrderBtn[i].style.opacity = 1;
    }
}

