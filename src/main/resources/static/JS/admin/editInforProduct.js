const editFormWrapper = document.querySelector('.back-ground-wrapper')
const edit_btn = document.querySelector('.editBtn')
const addNewFormWrapper = document.querySelector('.back-ground-wrapper-add')
const confirmDialog = document.querySelector('.back-ground-wrapper-confirm')
const cancel_btn = document.querySelector(".btn-second")
const backgroundAddNew = document.querySelector('.back-ground-wrapper-add')
const addingWrapper = document.querySelector('.adding-form-wrapper')
const backgroundEdit = document.querySelector('.back-ground-wrapper')
const wrapperEditForm = document.querySelector('#edit-form-wrapper')
const backgroundConfirm = document.querySelector('.back-ground-wrapper-confirm')
const wrapperConfirmForm = document.querySelector('.confirm-form-wrapper')

backgroundAddNew.addEventListener('click', () => {
    backgroundAddNew.style.display = 'none'
})

addingWrapper.addEventListener('click', (e) => {
    e.stopPropagation()
})

backgroundEdit.addEventListener('click', () => {
    backgroundEdit.style.display = 'none'
})

wrapperEditForm.addEventListener('click', (e) => {
    e.stopPropagation()
})

backgroundConfirm.addEventListener('click', () => {
    backgroundConfirm.style.display = 'none'
})

wrapperConfirmForm.addEventListener('click', (e) => {
    e.stopPropagation()
})

function openEditModal (wrapper){
    wrapper.style.display = 'flex';
}

function closeEditModal (wrapper) {
    wrapper.style.display = 'none';
}

$(".filterColorBtn").click(function () {
    $(".colorComboBoxForm").submit()
})

$("#search-icon").click(function () {
    $("#search-form").submit();
})

$('.searchBox').keydown(function (e) {
    if (e.keyCode === 13) {
        console.log("Enter key")
        $("#search-form").submit();
    }
})

$("#comboBox").change(function () {
    console.log("Clicked: " + $('#comboBox').val())
    $("#sort-form").submit();
})

$('.editBtn').click(function () {
    const product = {
        id: $(this).data('id'),
        name: $(this).data('name'),
        des: $(this).data('des'),
        price: $(this).data('price'),
        stock: $(this).data('stock'),
        color: $(this).data('color')
    };

    console.log(parseFloat(product.price.replaceAll(",", "").replace(" ₫", "")))

    $('#editForm input[name="name"]').val(product.name);
    $('#editForm input[name="id"]').val(product.id);
    $('#editForm textarea[name="description"]').val(product.des);
    $('#editForm input[name="price"]').val(parseFloat(product.price.replaceAll(",", "").replace(" ₫", "")));
    $('#editForm input[name="stock"]').val(product.stock);
    $('#editForm input[name="color"]').val(product.color);
});

$(document).ready(function() {
    $("#upload-img").change(function() {
        $('#upload-label').text("")
        $('#success-icon').css("display", "flex")
        $('.upload-btn').css("border", "2px solid #54B435");
    });
});
var id = ''
$('.deleteBtn').click(function () {
    id = $(this).data('product_id')
    console.log(id)
})

$('#yes-btn').click(function () {
    const form = $('#confirm-form')
    form.attr('action', '/admin/delete/id=' + id)
    form.submit();
})

$(document).ready(function() {
    // Add numbering to STT column
    $(".productTable tbody tr").each(function(index) {
        $(this).find(".stt-column").text(index);
    });
});

$("#rollback-wrapper").click(function () {
    setTimeout(function() {
        console.log("Click")
        $("#reload-form").submit()
    }, 200);
})

