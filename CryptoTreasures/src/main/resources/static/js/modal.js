// JavaScript за модалния прозорец
let modal = document.getElementById('articleModal');
let closeBtn = document.querySelector('.close-button');

closeBtn.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// функция, която отваря модалния прозорец със съдържанието на конкретна статия
function openArticleModal(btnElement) {
    var title = btnElement.getAttribute('data-article-title');
    var content = btnElement.getAttribute('data-article-content');
    var articleId = btnElement.getAttribute('data-article-id'); // Предполага се, че има такъв атрибут
    var modal = document.getElementById('articleModal');
    var articleIdInput = document.getElementById('articleId'); // Уверете се, че във вашата форма има input с id='articleId'

    document.getElementById('modalTitle').textContent = title;
    document.getElementById('modalContent').textContent = content;

    if (articleIdInput) {
        articleIdInput.value = articleId; // Задайте идентификатора на статията на скритото поле
    }

    modal.style.display = "block";
}

function closeArticleModal() {
    var modal = document.getElementById('articleModal'); // Трябва да получите референцията към модала отново тук
    modal.style.display = "none";
}