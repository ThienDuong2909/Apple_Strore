const dropdownIcon = document.querySelector('#dropdown__icon');
const dropdownMenu = document.querySelector('#wrapper__dropdown');
const manageOrder= document.querySelector('#wrapper__manage__order');
const logoutBtn = document.querySelector('#wrapper__logout__btn');
const confirmLogout = document.querySelector('#outside__logout__form');
const cancelLogout = document.querySelector('#cancel__logout__btn');

dropdownIcon.addEventListener('click', (e) => {
    e.preventDefault()
})

dropdownIcon.addEventListener('mouseover', () => {
    dropdownMenu.style.display = 'flex';
})

dropdownIcon.addEventListener('mouseleave', () => {
    dropdownMenu.style.display = 'none';
})

dropdownMenu.addEventListener('mouseenter', () => {
    dropdownMenu.style.display = 'flex';
})

dropdownMenu.addEventListener('mouseleave', () => {
    dropdownMenu.style.display = 'none';
})

logoutBtn.addEventListener('click', () => {
    confirmLogout.style.display = 'flex';
})

cancelLogout.addEventListener('click', (e) => {
    e.preventDefault()
    confirmLogout.style.display = 'none';
})

confirmLogout.addEventListener('mouseleave', () => {
    confirmLogout.style.display = 'none';
})